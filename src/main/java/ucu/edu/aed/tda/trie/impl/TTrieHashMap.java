package ucu.edu.aed.tda.trie.impl;

import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.tda.trie.Entry;

/**
 * Trie cuyos nodos ({@link TNodoTrieHashMap}) almacenan los enlaces a sus
 * subárboles en un {@link java.util.HashMap} en lugar de un vector fijo de 26
 * posiciones. Esto permite usar el Trie con cualquier alfabeto (mayúsculas,
 * dígitos, acentos, símbolos, etc.) sin modificar la implementación.
 */
public class TTrieHashMap<T>
        implements ucu.edu.aed.tda.trie.TTrie<T> {

    private final TNodoTrieHashMap<T> raiz;

    public TTrieHashMap() {
        raiz = new TNodoTrieHashMap<>();
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
