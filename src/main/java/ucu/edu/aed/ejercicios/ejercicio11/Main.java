package ucu.edu.aed.ejercicios.ejercicio11;

import ucu.edu.aed.utils.FileUtils;

import java.util.List;
import java.util.Map;

/**
 * Punto de entrada del Ejercicio 11: lee «libro.txt», calcula las
 * frecuencias de ocurrencia de cada palabra usando {@link FrecuenciaPalabras}
 * y muestra/grafica las 10 palabras que más ocurren.
 *
 * Uso: Main [ruta-al-libro.txt]
 * Si no se indica ruta, se usa el recurso de ejemplo empaquetado junto a
 * este ejercicio en src/main/resources/ucu/edu/aed/ejercicios/ejercicio11,
 * que termina junto a las clases compiladas de este paquete en
 * target/classes/ucu/edu/aed/ejercicios/ejercicio11/libro.txt.
 */
public class Main {

    private static final String RECURSO_POR_DEFECTO = "ucu/edu/aed/ejercicios/ejercicio11/libro.txt";
    private static final String CSV_SALIDA = "salida_frecuencias.csv";
    private static final int TOP_N = 10;

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : RECURSO_POR_DEFECTO;

        FrecuenciaPalabras contador = new FrecuenciaPalabras();
        FileUtils.leerLineas(path, contador::procesarLinea);

        System.out.println("Palabras distintas encontradas: " + contador.cantidadPalabrasDistintas());
        System.out.println();

        List<Map.Entry<String, Integer>> top10 = contador.topN(TOP_N);

        System.out.printf("%-20s | %-10s | %s%n", "Palabra", "Frecuencia", "Gráfico");
        System.out.println("-".repeat(60));

        int maxFrecuencia = top10.isEmpty() ? 1 : top10.get(0).getValue();
        String[] filasCsv = new String[top10.size() + 1];
        filasCsv[0] = "palabra,frecuencia";

        for (int i = 0; i < top10.size(); i++) {
            Map.Entry<String, Integer> entrada = top10.get(i);
            int barras = (int) Math.round((entrada.getValue() * 20.0) / maxFrecuencia);
            System.out.printf("%-20s | %-10d | %s%n",
                    entrada.getKey(), entrada.getValue(), "*".repeat(Math.max(1, barras)));
            filasCsv[i + 1] = entrada.getKey() + "," + entrada.getValue();
        }

        FileUtils.escribirLineas(CSV_SALIDA, filasCsv);
        System.out.println();
        System.out.println("Resultados de las " + TOP_N + " palabras más frecuentes guardados en " + CSV_SALIDA
                + " (para graficar en una planilla electrónica).");
    }
}
