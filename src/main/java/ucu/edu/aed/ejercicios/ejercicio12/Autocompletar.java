package ucu.edu.aed.ejercicios.ejercicio12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ucu.edu.aed.tda.trie.Entry;
import ucu.edu.aed.tda.trie.impl.TTrieHashMap;
import ucu.edu.aed.utils.FileUtils;

/**
 * Aplicación de "autocompletar" construida sobre {@link TTrieHashMap}.
 * <p>
 * Se cargan palabras de un diccionario en el Trie y, dado un prefijo
 * ingresado por el usuario, se retornan todas las palabras del diccionario
 * que comienzan con ese prefijo (ordenadas alfabéticamente para facilitar su
 * lectura).
 */
public class Autocompletar {

    private final TTrieHashMap<String> diccionario;

    public Autocompletar() {
        this.diccionario = new TTrieHashMap<>();
    }

    /**
     * Agrega una palabra al diccionario utilizado para autocompletar.
     *
     * @return true si la palabra se agregó (no estaba previamente)
     */
    public boolean agregarPalabra(String palabra) {
        return diccionario.insertar(palabra, palabra);
    }

    /**
     * Carga todas las palabras contenidas en un archivo (una por línea).
     */
    public void cargarDesdeArchivo(String path) {
        FileUtils.leerLineas(path, linea -> {
            String palabra = linea.trim();
            if (!palabra.isEmpty()) {
                agregarPalabra(palabra);
            }
        });
    }

    /**
     * Retorna todas las sugerencias (palabras) que comienzan con el prefijo
     * dado, ordenadas alfabéticamente.
     */
    public List<String> sugerir(String prefijo) {
        List<Entry<String>> entradas = diccionario.predecir(prefijo);

        List<String> sugerencias = new ArrayList<>();
        for (Entry<String> entrada : entradas) {
            sugerencias.add(entrada.getPalabra());
        }
        Collections.sort(sugerencias);

        return sugerencias;
    }

    /**
     * Retorna true si la palabra exacta existe en el diccionario.
     */
    public boolean existe(String palabra) {
        Entry<String> entrada = diccionario.buscar(palabra);
        return entrada != null && entrada.esPalabra();
    }

    public static void main(String[] args) {
        Autocompletar autocompletar = new Autocompletar();
        autocompletar.cargarDesdeArchivo("ut03/palabras.txt");

        String[] prefijos = {"al", "ca", "pro", "per", "z"};

        for (String prefijo : prefijos) {
            List<String> sugerencias = autocompletar.sugerir(prefijo);
            System.out.println("Sugerencias para \"" + prefijo + "\": " + sugerencias);
        }
    }
}
