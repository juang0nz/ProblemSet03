package ucu.edu.aed.tda.generic_trie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.tda.generic_trie.TNodoGenerico;

public class TNodoGenericoImpl<T extends Comparable<T>>
        implements TNodoGenerico<T> {

    private T dato;

    private TNodoGenericoImpl<T> primerHijo;

    private TNodoGenericoImpl<T> hermanoDerecho;

    public TNodoGenericoImpl(T dato) {
        this.dato = dato;
        this.primerHijo = null;
        this.hermanoDerecho = null;
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public boolean agregarHijo(T padre, T hijo) {

        TNodoGenericoImpl<T> nodoPadre = (TNodoGenericoImpl<T>) buscar(padre);

        if (nodoPadre == null) {
            return false;
        }

        TNodoGenericoImpl<T> nuevoHijo = new TNodoGenericoImpl<>(hijo);

        // Caso 1: no tiene hijos
        if (nodoPadre.primerHijo == null) {
            nodoPadre.primerHijo = nuevoHijo;
            return true;
        }

        // Caso 2: ya tiene hijos
        TNodoGenericoImpl<T> actual = nodoPadre.primerHijo;

        while (actual.hermanoDerecho != null) {
            actual = actual.hermanoDerecho;
        }

        actual.hermanoDerecho = nuevoHijo;

        return true;
    }

    @Override
    public TNodoGenerico<T> eliminar(Comparable<T> criterio) {

        TNodoGenericoImpl<T> actual = primerHijo;
        TNodoGenericoImpl<T> anterior = null;

        while (actual != null) {

            // ¿Este hijo es el que quiero eliminar?
            if (criterio.compareTo(actual.dato) == 0) {

                if (anterior == null) {
                    primerHijo = actual.hermanoDerecho;
                } else {
                    anterior.hermanoDerecho = actual.hermanoDerecho;
                }

                actual.hermanoDerecho = null;

                return actual;
            }

            // No era él: busco dentro de su subárbol
            TNodoGenerico<T> eliminado = actual.eliminar(criterio);

            if (eliminado != null) {
                return eliminado;
            }

            anterior = actual;
            actual = actual.hermanoDerecho;
        }

        return null;
    }

    @Override
    public TNodoGenerico<T> buscar(Comparable<T> criterio) {
        if (criterio.compareTo(dato) == 0) {
            return this;
        }
        TNodoGenericoImpl<T> unHijo = primerHijo;
        while (unHijo != null) {
            TNodoGenerico<T> encontrado = unHijo.buscar(criterio);

            if (encontrado != null) {
                return encontrado;
            }
            unHijo = unHijo.hermanoDerecho;
        }
        return null;
    }

    @Override
    public TNodoGenerico<T> obtenerPadre(Comparable<T> criterio) {

        TNodoGenericoImpl<T> unHijo = primerHijo;

        while (unHijo != null) {

            // ¿Este hijo es el nodo que estoy buscando?
            if (criterio.compareTo(unHijo.dato) == 0) {
                return this;
            }

            // Si no, busco más abajo
            TNodoGenerico<T> encontrado = unHijo.obtenerPadre(criterio);

            if (encontrado != null) {
                return encontrado;
            }

            // Paso al siguiente hijo
            unHijo = unHijo.hermanoDerecho;
        }

        return null;
    }

    @Override
    public void preOrden(Consumer<TNodoGenerico<T>> consumidor) {

        // Primero me visito a mí mismo
        consumidor.accept(this);

        // Empiezo por mi primer hijo
        TNodoGenericoImpl<T> unHijo = primerHijo;

        // Recorro todos mis hijos
        while (unHijo != null) {

            // Recorro todo el subárbol de este hijo
            unHijo.preOrden(consumidor);

            // Paso al siguiente hijo
            unHijo = unHijo.hermanoDerecho;
        }
    }

    @Override
    public void inOrden(Consumer<TNodoGenerico<T>> consumidor) {
        // Empiezo por mi primer hijo
        TNodoGenericoImpl<T> unHijo = primerHijo;

        // Recorro todos mis hijos
        while (unHijo != null) {

            // Recorro todo el subárbol de este hijo
            unHijo.inOrden(consumidor);

            // Paso al siguiente hijo
            unHijo = unHijo.hermanoDerecho;
        }
    }

    @Override
    public void postOrden(Consumer<TNodoGenerico<T>> consumidor) {
        // Empiezo por mi primer hijo
        TNodoGenericoImpl<T> unHijo = primerHijo;

        // Recorro todos mis hijos
        while (unHijo != null) {

            // Recorro todo el subárbol de este hijo
            unHijo.postOrden(consumidor);

            // Paso al siguiente hijo
            unHijo = unHijo.hermanoDerecho;
        }

    }

    @Override
    public int altura() {
        // Empiezo por mi primer hijo
        TNodoGenericoImpl<T> unHijo = primerHijo;

        int alturaMaxima = 0;

        // Recorro todos mis hijos
        while (unHijo != null) {

            // Calculo la altura del subárbol de este hijo
            int alturaHijo = unHijo.altura();
            if (alturaHijo > alturaMaxima) {
                alturaMaxima = alturaHijo;
            }

            // Paso al siguiente hijo
            unHijo = unHijo.hermanoDerecho;
        }

        // La altura del nodo actual es 1 más que la altura máxima de sus hijos
        return alturaMaxima + 1;
    }

    @Override
    public int grado() {
        // Empiezo por mi primer hijo
        TNodoGenericoImpl<T> unHijo = primerHijo;

        int gradoMaximo = 0;
        int gradoActual = 0;

        // Recorro todos mis hijos
        while (unHijo != null) {
            gradoActual++;
            unHijo = unHijo.hermanoDerecho;
        }

        if (gradoActual > gradoMaximo) {
            gradoMaximo = gradoActual;
        }

        return gradoMaximo;
    }

    @Override
    public void vaciar() {
        primerHijo = null;
    }

    @Override
    public List<T> obtenerHijos() {

        List<T> hijos = new ArrayList<>();
        TNodoGenericoImpl<T> unHijo = primerHijo;
        while (unHijo != null) {
            hijos.add(unHijo.dato);
            unHijo = unHijo.hermanoDerecho;
        }
        return hijos;
    }
}