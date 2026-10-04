package ucu.edu.aed.tda.trie;


import java.util.List;
import java.util.function.Consumer;

public interface TNodoTrie<T> extends java.io.Serializable {
    void recorrer(Consumer<Entry<T>> consumer);

    /**
     * Retorna un Entry que contiene la palabra buscada y su estado:
     * -1 si no se encuentra "palabra" en el trie
     */
    Entry<T> buscar(String palabra);

    /**
     * retorna true si se agregó la palabra
     */
    boolean insertar(String palabra, T dato);

    /**
     * retorna todas las palabras en el trie que comienzan con prefijo
     */
    List<Entry<T>> predecir(String prefijo);

    /**
     * retorna un dato cuando el nodo es palabra, nulo en otro caso
     */
    T getDato();

    boolean esPalabra();
}
