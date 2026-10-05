package ucu.edu.aed.ejercicios.ejercicio14;

/**
 * Tabla hash con direccionamiento abierto y sondeo lineal:
 * h(i) = (h(0) + i) mod M.
 */
public class TablaSondeoLineal implements TablaHash {

    private final Integer[] tabla;
    private final int m;
    private int colisiones = 0;

    public TablaSondeoLineal(int m) {
        this.m = m;
        this.tabla = new Integer[m];
    }

    private int hash(int clave) {
        return Math.floorMod(clave, m);
    }

    @Override
    public void insertar(int clave) {
        int pos = hash(clave);
        int intentos = 0;
        while (tabla[pos] != null) {
            colisiones++;
            intentos++;
            if (intentos >= m) {
                throw new IllegalStateException("Tabla llena, no se pudo insertar " + clave);
            }
            pos = (pos + 1) % m;
        }
        tabla[pos] = clave;
    }

    @Override
    public Resultado buscar(int clave) {
        int pos = hash(clave);
        int inicio = pos;
        int comparaciones = 0;
        do {
            comparaciones++;
            if (tabla[pos] == null) {
                return new Resultado(false, comparaciones);
            }
            if (tabla[pos] == clave) {
                return new Resultado(true, comparaciones);
            }
            pos = (pos + 1) % m;
        } while (pos != inicio);
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
