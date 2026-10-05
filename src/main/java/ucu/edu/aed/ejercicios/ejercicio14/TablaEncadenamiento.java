package ucu.edu.aed.ejercicios.ejercicio14;

import java.util.LinkedList;

/**
 * Tabla hash con resolución de colisiones por encadenamiento separado:
 * cada posición de la tabla guarda una lista con las claves que
 * colisionaron en h(clave).
 */
public class TablaEncadenamiento implements TablaHash {

    private final LinkedList<Integer>[] tabla;
    private final int m;
    private int colisiones = 0;

    @SuppressWarnings("unchecked")
    public TablaEncadenamiento(int m) {
        this.m = m;
        this.tabla = new LinkedList[m];
        for (int i = 0; i < m; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    private int hash(int clave) {
        return Math.floorMod(clave, m);
    }

    @Override
    public void insertar(int clave) {
        int pos = hash(clave);
        if (!tabla[pos].isEmpty()) {
            colisiones++;
        }
        tabla[pos].add(clave);
    }

    @Override
    public Resultado buscar(int clave) {
        int pos = hash(clave);
        int comparaciones = 0;
        for (int valor : tabla[pos]) {
            comparaciones++;
            if (valor == clave) {
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
            sb.append(String.format("  [%2d] %s%n", i, tabla[i].isEmpty() ? "-" : tabla[i]));
        }
        return sb.toString();
    }
}
