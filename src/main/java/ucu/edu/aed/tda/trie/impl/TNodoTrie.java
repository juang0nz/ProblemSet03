package ucu.edu.aed.tda.trie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.tda.trie.Entry;

public class TNodoTrie<T>
        implements ucu.edu.aed.tda.trie.TNodoTrie<T> {

    private TNodoTrie<T>[] hijos;
    private boolean esPalabra;
    private T dato;

    @SuppressWarnings("unchecked")
    public TNodoTrie() {
        hijos = new TNodoTrie[26];
        esPalabra = false;
        dato = null;
    }

    @Override
    public boolean insertar(String palabra, T dato) {

        TNodoTrie<T> actual = this;
        // Recorremos cada caracter de la palabra
        for (int i = 0; i < palabra.length(); i++) {
            // Obtenemos el caracter actual de la palabra
            char caracter = palabra.charAt(i);
            // Calculamos la posición en el arreglo de hijos correspondiente al caracter
            int posicion = caracter - 'a';
            // Si no existe un nodo hijo en esa posición, lo creamos
            if (actual.hijos[posicion] == null) {
                actual.hijos[posicion] = new TNodoTrie<>();
            }
            // Nos movemos al nodo hijo correspondiente al caracter actual
            actual = actual.hijos[posicion];
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
        // Recorremos cada caracter de la palabra para buscar el nodo correspondiente
        TNodoTrie<T> actual = this;
        // Iteramos sobre cada caracter de la palabra
        for (int i = 0; i < palabra.length(); i++) {
            // Obtenemos el caracter actual de la palabra y calculamos su posición en el
            // arreglo de hijos
            char caracter = palabra.charAt(i);
            int posicion = caracter - 'a';
            // Si no existe un nodo hijo en esa posición, la palabra no está en el trie
            if (actual.hijos[posicion] == null) {
                return null;
            }
            // Nos movemos al nodo hijo correspondiente al caracter actual
            actual = actual.hijos[posicion];
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

        for (int i = 0; i < hijos.length; i++) {

            if (hijos[i] != null) {

                char caracter = (char) ('a' + i);

                hijos[i].recorrer(
                        palabraActual + caracter,
                        consumer);
            }
        }
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        // Buscamos el nodo correspondiente al prefijo dado
        List<Entry<T>> resultado = new ArrayList<>();

        TNodoTrie<T> actual = this;
        // Recorremos cada caracter del prefijo para encontrar el nodo correspondiente
        for (int i = 0; i < prefijo.length(); i++) {

            char caracter = prefijo.charAt(i);
            int posicion = caracter - 'a';

            if (actual.hijos[posicion] == null) {
                return resultado;
            }

            actual = actual.hijos[posicion];
        }
        // Nodo correspondiente al prefijo encontrado
        TNodoTrie<T> nodoPrefijo = actual;

        nodoPrefijo.recorrer(
                prefijo,
                entry -> resultado.add(entry));

        return resultado;
    }
}
