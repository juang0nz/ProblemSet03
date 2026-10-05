package ucu.edu.aed.ejercicios.ejercicio14;

/**
 * Contrato común a las tres estrategias de resolución de colisiones
 * comparadas en el Ejercicio 14, para poder recorrerlas de forma uniforme
 * desde {@link Main}.
 */
public interface TablaHash {

    void insertar(int clave);

    Resultado buscar(int clave);

    int getColisiones();

    /** Representación textual del estado final de la tabla, para impresión. */
    String describirEstado();
}
