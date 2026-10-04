package ucu.edu.aed.ejercicios.ejercicio12;

/**
 * Punto de entrada para demostrar las dos aplicaciones del Ejercicio 12
 * basadas en {@link ucu.edu.aed.tda.trie.impl.TTrieHashMap}:
 * autocompletar y búsqueda de patrones sobre un árbol de sufijos.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("===== Autocompletar =====");
        Autocompletar.main(args);

        System.out.println();
        System.out.println("===== Búsqueda de patrones (árbol de sufijos) =====");
        BuscadorDePatrones.main(args);
    }
}
