package ucu.edu.aed.ejercicios.ejercicio12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ucu.edu.aed.tda.trie.Entry;
import ucu.edu.aed.tda.trie.impl.TTrieHashMap;

/**
 * Aplicación de búsqueda de patrones en un texto utilizando un Trie de
 * sufijos (árbol de sufijos) implementado con {@link TTrieHashMap}.
 * <p>
 * La idea es sencilla: se insertan en el Trie todos los sufijos del texto,
 * cada uno asociado a la posición (índice de inicio) en la que comienza
 * dentro del texto original. Luego, para buscar un patrón basta con pedirle
 * al Trie que "prediga" (busque por prefijo) ese patrón: cada sufijo que
 * comienza con el patrón corresponde exactamente a una ocurrencia de dicho
 * patrón en el texto, y el dato almacenado en ese nodo es la posición donde
 * ocurre.
 */
public class BuscadorDePatrones {

    private final TTrieHashMap<Integer> trieDeSufijos;
    private final String texto;

    public BuscadorDePatrones(String texto) {
        this.texto = texto;
        this.trieDeSufijos = new TTrieHashMap<>();
        construirTrieDeSufijos();
    }

    private void construirTrieDeSufijos() {
        for (int i = 0; i < texto.length(); i++) {
            // Insertamos el sufijo que comienza en la posición i,
            // guardando como dato la posición de inicio del sufijo.
            trieDeSufijos.insertar(texto.substring(i), i);
        }
    }

    /**
     * Busca todas las posiciones (0-based) del texto en las que ocurre el
     * patrón indicado.
     *
     * @return lista ordenada de posiciones donde ocurre el patrón (vacía si
     *         no ocurre)
     */
    public List<Integer> buscarPatron(String patron) {
        if (patron == null || patron.isEmpty()) {
            return new ArrayList<>();
        }

        List<Entry<Integer>> coincidencias = trieDeSufijos.predecir(patron);

        List<Integer> posiciones = new ArrayList<>();
        for (Entry<Integer> coincidencia : coincidencias) {
            posiciones.add(coincidencia.getDato());
        }
        Collections.sort(posiciones);

        return posiciones;
    }

    public String getTexto() {
        return texto;
    }

    public static void main(String[] args) {
        String texto = "banana";
        BuscadorDePatrones buscador = new BuscadorDePatrones(texto);

        String[] patrones = {"ana", "na", "ban", "banana", "xyz"};

        System.out.println("Texto: \"" + texto + "\"");
        for (String patron : patrones) {
            List<Integer> posiciones = buscador.buscarPatron(patron);
            System.out.println("Patrón \"" + patron + "\" ocurre en las posiciones: " + posiciones);
        }
    }
}
