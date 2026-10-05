package ucu.edu.aed.ejercicios.ejercicio14;

/**
 * Tabla hash con direccionamiento abierto y sondeo cuadrático:
 * h(i) = (h(0) + i^2) mod M.
 */
public class TablaSondeoCuadratico implements TablaHash {

    private final Integer[] tabla;
    private final int m;
    private int colisiones = 0;

    public TablaSondeoCuadratico(int m) {
        this.m = m;
        this.tabla = new Integer[m];
    }

    private int hash(int clave) {
        return Math.floorMod(clave, m);
    }

    @Override
    public void insertar(int clave) {
        int base = hash(clave);
        int i = 0;
        int pos = base;
        while (tabla[pos] != null) {
            colisiones++;
            i++;
            if (i > m) {
                throw new IllegalStateException(
                        "No se encontró posición libre para " + clave + " (tabla de tamaño " + m + ")");
            }
            pos = Math.floorMod(base + i * i, m);
        }
        tabla[pos] = clave;
    }

    @Override
    public Resultado buscar(int clave) {
        int base = hash(clave);
        int comparaciones = 0;
        for (int i = 0; i <= m; i++) {
            int pos = Math.floorMod(base + i * i, m);
            comparaciones++;
            if (tabla[pos] == null) {
                return new Resultado(false, comparaciones);
            }
            if (tabla[pos] == clave) {
                return new Resultado(true, comparaciones);
            }
        }
        return new Resultado(false, comparaciones);
    }

    @Override
    public int getColisiones() {
        return colisiones;
    }

    @Override
    public String describirEstado() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            sb.append(String.format("  [%2d] %s%n", i, tabla[i] == null ? "-" : tabla[i]));
        }
        return sb.toString();
    }
}
