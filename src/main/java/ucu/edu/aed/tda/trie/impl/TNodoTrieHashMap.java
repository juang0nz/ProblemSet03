package ucu.edu.aed.tda.trie.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import ucu.edu.aed.tda.trie.Entry;

/**
 * Variante de {@link TNodoTrie} que utiliza un {@link HashMap} para almacenar
 * los enlaces a los subárboles en lugar de un vector de tamaño fijo (26
 * posiciones, una por cada letra de 'a' a 'z').
 * <p>
 * Esto permite que el Trie sea independiente del alfabeto utilizado: admite
 * mayúsculas, minúsculas, dígitos, acentos, símbolos, etc. sin necesidad de
 * conocer de antemano el tamaño del alfabeto ni de desperdiciar memoria en
 * posiciones no utilizadas.
 */
public class TNodoTrieHashMap<T>
        implements ucu.edu.aed.tda.trie.TNodoTrie<T> {

    private final Map<Character, TNodoTrieHashMap<T>> hijos;
    private boolean esPalabra;
    private T dato;

    public TNodoTrieHashMap() {
        hijos = new HashMap<>();
        esPalabra = false;
        dato = null;
    }

    @Override
    public boolean insertar(String palabra, T dato) {

        TNodoTrieHashMap<T> actual = this;
        // Recorremos cada caracter de la palabra
        for (int i = 0; i < palabra.length(); i++) {
            char caracter = palabra.charAt(i);
            // Si no existe un nodo hijo para ese caracter, lo creamos.
            // A diferencia del vector fijo, el HashMap solo almacena los
            // caracteres que realmente aparecen, sin importar el alfabeto.
            actual.hijos.putIfAbsent(caracter, new TNodoTrieHashMap<>());
            actual = actual.hijos.get(caracter);
        }
        // Si llegamos hasta aquí, significa que hemos recorrido toda la palabra
        if (actual.esPalabra) {
            return false;
        }
        // Marcamos el nodo actual como palabra y asignamos el dato
        actual.esPalabra = true;
        actual.dato = dato;

        return true;
    }

    @Override
    public Entry<T> buscar(String palabra) {
        TNodoTrieHashMap<T> actual = this;
        // Iteramos sobre cada caracter de la palabra
        for (int i = 0; i < palabra.length(); i++) {
            char caracter = palabra.charAt(i);
            TNodoTrieHashMap<T> siguiente = actual.hijos.get(caracter);
            // Si no existe un nodo hijo para ese caracter, la palabra no está en el trie
            if (siguiente == null) {
                return null;
            }
            actual = siguiente;
        }
        // Hemos recorrido toda la palabra, devolvemos el nodo correspondiente
        return new Entry<>(
                actual.dato,
                actual.esPalabra,
                palabra);
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public boolean esPalabra() {
        return esPalabra;
    }

    @Override
    public void recorrer(Consumer<Entry<T>> consumer) {
        recorrer("", consumer);
    }

    // Método recursivo auxiliar para recorrer el trie a partir de un nodo dado
    private void recorrer(String palabraActual, Consumer<Entry<T>> consumer) {

        if (esPalabra) {
            consumer.accept(
                    new Entry<>(dato, true, palabraActual));
        }

        for (Map.Entry<Character, TNodoTrieHashMap<T>> hijo : hijos.entrySet()) {
            hijo.getValue().recorrer(
                    palabraActual + hijo.getKey(),
                    consumer);
        }
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        List<Entry<T>> resultado = new ArrayList<>();

        TNodoTrieHashMap<T> actual = this;
        // Recorremos cada caracter del prefijo para encontrar el nodo correspondiente
        for (int i = 0; i < prefijo.length(); i++) {
            char caracter = prefijo.charAt(i);
            TNodoTrieHashMap<T> siguiente = actual.hijos.get(caracter);

            if (siguiente == null) {
                return resultado;
            }

            actual = siguiente;
        }
        // Nodo correspondiente al prefijo encontrado
        TNodoTrieHashMap<T> nodoPrefijo = actual;

        nodoPrefijo.recorrer(
                prefijo,
                resultado::add);

        return resultado;
    }
}
