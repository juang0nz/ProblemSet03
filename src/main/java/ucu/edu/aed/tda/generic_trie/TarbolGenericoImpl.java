package ucu.edu.aed.tda.generic_trie;

import java.util.function.Consumer;




public class TArbolGenericoImpl<T extends Comparable<T>>
        implements TArbolGenerico<T> {

    private TNodoGenericoImpl<T> raiz;


    public TArbolGenericoImpl() {
        this.raiz = null;
    }


    @Override
    public boolean agregarHijo(Comparable<T> padre, T hijo) {
        return false;
    }


    @Override
    public void eliminar(Comparable<T> criterio) {

    }


    @Override
    public T obtenerPadre(Comparable<T> criterio) {
        return null;
    }


    @Override
    public T buscar(Comparable<T> criterio) {
        return null;
    }


    @Override
    public void preOrden(Consumer<T> consumidor) {

    }


    @Override
    public void inOrden(Consumer<T> consumidor) {

    }


    @Override
    public void postOrden(Consumer<T> consumidor) {

    }


    @Override
    public void vaciar() {

    }


    @Override
    public int grado(Comparable<T> nodo) {
        return 0;
    }


    @Override
    public int altura(Comparable<T> nodo) {
        return 0;
    }
}