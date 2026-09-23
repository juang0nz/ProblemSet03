package ucu.edu.aed.tda.generic_trie.impl;

import java.util.function.Consumer;

import ucu.edu.aed.tda.generic_trie.TArbolGenerico;
import ucu.edu.aed.tda.generic_trie.TNodoGenerico;


public class TArbolGenericoImpl<T extends Comparable<T>>
        implements TArbolGenerico<T> {

    private TNodoGenerico<T> raiz;

    public TArbolGenericoImpl() {
        this.raiz = null;
    }

    public TArbolGenericoImpl(T datoRaiz) {
        this.raiz = new TNodoGenericoImpl<>(datoRaiz);
    }


    // =========================
    // AGREGAR HIJO
    // =========================

    @Override
    public boolean agregarHijo(Comparable<T> padre, T hijo) {

        if (raiz == null) {
            return false;
        }

        TNodoGenerico<T> nodoPadre = raiz.buscar(padre);

        if (nodoPadre == null) {
            return false;
        }

        return nodoPadre.agregarHijo(nodoPadre.getDato(), hijo);
    }


    // =========================
    // ELIMINAR
    // =========================

    @Override
    public void eliminar(Comparable<T> criterio) {

        if (raiz == null) {
            return;
        }

        // Si quiero eliminar la raíz,
        // desaparece todo el árbol
        if (criterio.compareTo(raiz.getDato()) == 0) {
            raiz = null;
            return;
        }

        raiz.eliminar(criterio);
    }


    // =========================
    // OBTENER PADRE
    // =========================

    @Override
    public T obtenerPadre(Comparable<T> criterio) {

        if (raiz == null) {
            return null;
        }

        TNodoGenerico<T> padre = raiz.obtenerPadre(criterio);

        if (padre == null) {
            return null;
        }

        return padre.getDato();
    }


    // =========================
    // BUSCAR
    // =========================

    @Override
    public T buscar(Comparable<T> criterio) {

        if (raiz == null) {
            return null;
        }

        TNodoGenerico<T> encontrado = raiz.buscar(criterio);

        if (encontrado == null) {
            return null;
        }

        return encontrado.getDato();
    }


    // =========================
    // PREORDEN
    // =========================

    @Override
    public void preOrden(Consumer<T> consumidor) {

        if (raiz != null) {

            raiz.preOrden(
                    nodo -> consumidor.accept(nodo.getDato())
            );
        }
    }


    // =========================
    // INORDEN
    // =========================

    @Override
    public void inOrden(Consumer<T> consumidor) {

        if (raiz != null) {

            raiz.inOrden(
                    nodo -> consumidor.accept(nodo.getDato())
            );
        }
    }


    // =========================
    // POSTORDEN
    // =========================

    @Override
    public void postOrden(Consumer<T> consumidor) {

        if (raiz != null) {

            raiz.postOrden(
                    nodo -> consumidor.accept(nodo.getDato())
            );
        }
    }


    // =========================
    // VACIAR
    // =========================

    @Override
    public void vaciar() {
        raiz = null;
    }


    // =========================
    // GRADO
    // =========================

    @Override
    public int grado(Comparable<T> nodo) {

        if (raiz == null) {
            return -1;
        }

        TNodoGenerico<T> encontrado = raiz.buscar(nodo);

        if (encontrado == null) {
            return -1;
        }

        return encontrado.grado();
    }


    // =========================
    // ALTURA
    // =========================

    @Override
    public int altura(Comparable<T> nodo) {

        if (raiz == null) {
            return -1;
        }

        TNodoGenerico<T> encontrado = raiz.buscar(nodo);

        if (encontrado == null) {
            return -1;
        }

        return encontrado.altura();
    }
}