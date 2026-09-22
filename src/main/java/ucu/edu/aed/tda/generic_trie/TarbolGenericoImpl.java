package ucu.edu.aed.tda.generic_trie;

import java.util.List;
import java.util.function.Consumer;

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
        return false;
    }


    @Override
    public TNodoGenerico<T> eliminar(Comparable<T> criterio) {
        return null;
    }


    @Override
    public TNodoGenerico<T> buscar(Comparable<T> criterio) {
        return null;
    }


    @Override
    public TNodoGenerico<T> obtenerPadre(Comparable<T> criterio) {
        return null;
    }


    @Override
    public void preOrden(Consumer<TNodoGenerico<T>> consumidor) {

    }


    @Override
    public void inOrden(Consumer<TNodoGenerico<T>> consumidor) {

    }


    @Override
    public void postOrden(Consumer<TNodoGenerico<T>> consumidor) {

    }


    @Override
    public int altura() {
        return 0;
    }


    @Override
    public int grado() {
        return 0;
    }


    @Override
    public void vaciar() {

    }


    @Override
    public List<T> obtenerHijos() {
        return null;
    }
}