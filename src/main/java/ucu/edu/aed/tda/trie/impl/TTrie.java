package ucu.edu.aed.tda.trie.impl;

import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.tda.trie.Entry;

public class TTrie<T>
        implements ucu.edu.aed.tda.trie.TTrie<T> {

    private TNodoTrie<T> raiz;

    public TTrie() {
        raiz = new TNodoTrie<>();
    }

    @Override
    public boolean insertar(String palabra, T dato) {
        return raiz.insertar(palabra, dato);
    }

    @Override
    public Entry<T> buscar(String palabra) {
        return raiz.buscar(palabra);
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        return raiz.predecir(prefijo);
    }

    @Override
    public void recorrer(Consumer<Entry<T>> consumer) {
        raiz.recorrer(consumer);
    }
}