package ucu.edu.aed.ejercicios.ejercicio14;

/**
 * Resultado de una búsqueda: si la clave fue encontrada y cuántas
 * comparaciones fueron necesarias para determinarlo.
 */
public class Resultado {
    private final boolean encontrado;
    private final int comparaciones;

    public Resultado(boolean encontrado, int comparaciones) {
        this.encontrado = encontrado;
        this.comparaciones = comparaciones;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public int getComparaciones() {
        return comparaciones;
    }
}
