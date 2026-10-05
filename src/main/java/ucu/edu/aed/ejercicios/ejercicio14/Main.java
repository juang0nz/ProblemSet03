package ucu.edu.aed.ejercicios.ejercicio14;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Ejercicio 14: compara sondeo lineal, sondeo cuadrático y encadenamiento
 * separado para el mismo conjunto de claves enteras.
 *
 * Tamaño de tabla: se eligió M = 19 (primo). Con 12 claves a insertar, un
 * factor de carga de referencia de ~0,7 (igual al usado en {@code THashImpl})
 * da 12/0,7 ≈ 17,1; el primo más próximo mayor o igual a ese valor es 19.
 * Al ser primo, M evita patrones de ciclos cortos tanto en sondeo lineal
 * como en sondeo cuadrático, y deja un factor de carga final de 12/19 ≈ 0,63,
 * razonable para comparar las tres estrategias en igualdad de condiciones.
 *
 * Función hash: h(clave) = clave mod M (módulo simple, rápida y suficiente
 * para claves enteras ya dispersas como las del enunciado).
 */
public class Main {

    private static final int TAMANO_TABLA = 19;

    private static final int[] CLAVES = {45, 12, 37, 82, 29, 54, 31, 76, 18, 93, 11, 68};

    // Claves que NO pertenecen al conjunto insertado, usadas para medir
    // búsquedas sin éxito.
    private static final int[] CLAVES_BUSQUEDA_FALLIDA = {7, 50, 60, 99, 100};

    public static void main(String[] args) {
        Map<String, TablaHash> tablas = new LinkedHashMap<>();
        tablas.put("Sondeo lineal", new TablaSondeoLineal(TAMANO_TABLA));
        tablas.put("Sondeo cuadratico", new TablaSondeoCuadratico(TAMANO_TABLA));
        tablas.put("Encadenamiento separado", new TablaEncadenamiento(TAMANO_TABLA));

        for (TablaHash tabla : tablas.values()) {
            for (int clave : CLAVES) {
                tabla.insertar(clave);
            }
        }

        System.out.printf("Tamaño de tabla M = %d (primo). Factor de carga = %d/%d = %.2f%n",
                TAMANO_TABLA, CLAVES.length, TAMANO_TABLA, (double) CLAVES.length / TAMANO_TABLA);
        System.out.println("Función hash: h(clave) = clave mod " + TAMANO_TABLA);
        System.out.println();

        for (Map.Entry<String, TablaHash> entry : tablas.entrySet()) {
            System.out.println("=== " + entry.getKey() + " (estado final) ===");
            System.out.print(entry.getValue().describirEstado());
            System.out.println();
        }

        System.out.printf("%-26s | %-10s | %-18s | %-20s%n",
                "Estrategia", "Colisiones", "Prom. comp. exito", "Prom. comp. fracaso");
        System.out.println("-".repeat(85));

        String mejorEstrategia = null;
        double mejorPromedioTotal = Double.MAX_VALUE;

        for (Map.Entry<String, TablaHash> entry : tablas.entrySet()) {
            TablaHash tabla = entry.getValue();

            double promExito = promedio(tabla, CLAVES);
            double promFracaso = promedio(tabla, CLAVES_BUSQUEDA_FALLIDA);

            System.out.printf("%-26s | %-10d | %-18.2f | %-20.2f%n",
                    entry.getKey(), tabla.getColisiones(), promExito, promFracaso);

            double promedioTotal = (promExito + promFracaso) / 2.0;
            if (promedioTotal < mejorPromedioTotal) {
                mejorPromedioTotal = promedioTotal;
                mejorEstrategia = entry.getKey();
            }
        }

        System.out.println();
        System.out.println("Claves usadas para búsquedas sin éxito: " + Arrays.toString(CLAVES_BUSQUEDA_FALLIDA));
        System.out.println();
        System.out.printf(
                "Conclusión: para este conjunto de 12 claves y M=%d, la estrategia con menor "
                        + "promedio de comparaciones (éxito+fracaso) fue '%s' (promedio combinado = %.2f).%n",
                TAMANO_TABLA, mejorEstrategia, mejorPromedioTotal);
    }

    private static double promedio(TablaHash tabla, int[] claves) {
        int total = 0;
        for (int clave : claves) {
            total += tabla.buscar(clave).getComparaciones();
        }
        return (double) total / claves.length;
    }
}
