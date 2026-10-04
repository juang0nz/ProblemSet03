package ucu.edu.aed.tda.trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class TestTTrie extends TestCase {

    public void testInsertarYBuscar() {
        ucu.edu.aed.tda.trie.impl.TTrie<Integer> trie = new ucu.edu.aed.tda.trie.impl.TTrie<>();

        assertTrue(trie.insertar("hola", 1));

        Entry<Integer> e = trie.buscar("hola");
        assertNotNull(e);
        assertTrue(e.esPalabra());
        assertEquals(Integer.valueOf(1), e.getDato());
    }

    public void testInsertarDuplicadoYBuscarPrefijo() {
        ucu.edu.aed.tda.trie.impl.TTrie<Integer> trie = new ucu.edu.aed.tda.trie.impl.TTrie<>();

        assertTrue(trie.insertar("hola", 1));
        // insertar la misma palabra debe devolver false
        assertFalse(trie.insertar("hola", 2));

        // buscar un prefijo que no está marcado como palabra debe devolver Entry con esPalabra = false
        Entry<Integer> pref = trie.buscar("hol");
        assertNotNull(pref);
        assertFalse(pref.esPalabra());
        assertNull(pref.getDato());
    }

    public void testPredecirYRecorrer() {
        ucu.edu.aed.tda.trie.impl.TTrie<Integer> trie = new ucu.edu.aed.tda.trie.impl.TTrie<>();

        trie.insertar("hola", 1);
        trie.insertar("he", 2);

        List<Entry<Integer>> preds = trie.predecir("h");
        assertNotNull(preds);
        // Debe contener ambas palabras
        Set<String> palabras = new HashSet<>();
        for (Entry<Integer> en : preds) {
            palabras.add(en.getPalabra());
        }
        assertTrue(palabras.contains("hola"));
        assertTrue(palabras.contains("he"));

        // probar recorrer
        final List<String> recorridas = new ArrayList<>();
        trie.recorrer(entry -> recorridas.add(entry.getPalabra()));
        // recorrer devuelve todas las palabras almacenadas (en este caso 2)
        assertEquals(2, recorridas.size());
        assertTrue(recorridas.contains("hola"));
        assertTrue(recorridas.contains("he"));
    }

    // =========================
    // CASOS BORDE ADICIONALES
    // =========================

    public void testInsertarCadenaVacia() {
        ucu.edu.aed.tda.trie.impl.TTrie<Integer> trie = new ucu.edu.aed.tda.trie.impl.TTrie<>();

        // Insertar cadena vacía: según la implementación, se marca la raíz como palabra
        assertTrue(trie.insertar("", 10));
        Entry<Integer> e = trie.buscar("");
        assertNotNull(e);
        assertTrue(e.esPalabra());
        assertEquals(Integer.valueOf(10), e.getDato());
    }

    public void testCaracteresInvalidos() {
        ucu.edu.aed.tda.trie.impl.TTrie<Integer> trie = new ucu.edu.aed.tda.trie.impl.TTrie<>();

        // Caracteres fuera de 'a'-'z' deben provocar ArrayIndexOutOfBounds en la implementación actual
        try {
            trie.insertar("Hola", 5); // 'H' may cause negative index
            fail("Se esperaba ArrayIndexOutOfBoundsException por caracteres inválidos");
        } catch (ArrayIndexOutOfBoundsException ex) {
            // OK
        }
    }

    public void testInsertarNull() {
        ucu.edu.aed.tda.trie.impl.TTrie<Integer> trie = new ucu.edu.aed.tda.trie.impl.TTrie<>();

        try {
            trie.insertar(null, 1);
            fail("Se esperaba NullPointerException al insertar null");
        } catch (NullPointerException ex) {
            // OK
        }
    }
}
