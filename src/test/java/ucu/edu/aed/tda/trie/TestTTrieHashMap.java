package ucu.edu.aed.tda.trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import ucu.edu.aed.tda.trie.impl.TTrieHashMap;

/**
 * Pruebas análogas a {@link TestTTrie} pero sobre la implementación basada
 * en HashMap ({@link TTrieHashMap}), incluyendo casos que verifican que esta
 * implementación admite cualquier alfabeto (a diferencia de la versión con
 * vector fijo de 26 posiciones).
 */
public class TestTTrieHashMap extends TestCase {

    public void testInsertarYBuscar() {
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();

        assertTrue(trie.insertar("hola", 1));

        Entry<Integer> e = trie.buscar("hola");
        assertNotNull(e);
        assertTrue(e.esPalabra());
        assertEquals(Integer.valueOf(1), e.getDato());
    }

    public void testInsertarDuplicadoYBuscarPrefijo() {
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();

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
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();

        trie.insertar("hola", 1);
        trie.insertar("he", 2);

        List<Entry<Integer>> preds = trie.predecir("h");
        assertNotNull(preds);
        Set<String> palabras = new HashSet<>();
        for (Entry<Integer> en : preds) {
            palabras.add(en.getPalabra());
        }
        assertTrue(palabras.contains("hola"));
        assertTrue(palabras.contains("he"));

        final List<String> recorridas = new ArrayList<>();
        trie.recorrer(entry -> recorridas.add(entry.getPalabra()));
        assertEquals(2, recorridas.size());
        assertTrue(recorridas.contains("hola"));
        assertTrue(recorridas.contains("he"));
    }

    public void testPredecirPrefijoInexistente() {
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();
        trie.insertar("hola", 1);

        List<Entry<Integer>> preds = trie.predecir("zzz");
        assertNotNull(preds);
        assertTrue(preds.isEmpty());
    }

    public void testInsertarCadenaVacia() {
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();

        assertTrue(trie.insertar("", 10));
        Entry<Integer> e = trie.buscar("");
        assertNotNull(e);
        assertTrue(e.esPalabra());
        assertEquals(Integer.valueOf(10), e.getDato());
    }

    public void testInsertarNull() {
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();

        try {
            trie.insertar(null, 1);
            fail("Se esperaba NullPointerException al insertar null");
        } catch (NullPointerException ex) {
            // OK
        }
    }

    // =========================
    // Flexibilidad de alfabeto: a diferencia de la implementación con
    // vector fijo, el HashMap admite cualquier caracter sin lanzar
    // excepciones ni desperdiciar memoria.
    // =========================

    public void testAdmiteMayusculasYMinusculasMezcladas() {
        TTrieHashMap<Integer> trie = new TTrieHashMap<>();

        assertTrue(trie.insertar("Hola", 1));
        assertTrue(trie.insertar("HOLA", 2));
        assertTrue(trie.insertar("hola", 3));

        assertEquals(Integer.valueOf(1), trie.buscar("Hola").getDato());
        assertEquals(Integer.valueOf(2), trie.buscar("HOLA").getDato());
        assertEquals(Integer.valueOf(3), trie.buscar("hola").getDato());
    }

    public void testAdmiteDigitosYSimbolos() {
        TTrieHashMap<String> trie = new TTrieHashMap<>();

        assertTrue(trie.insertar("abc123", "alfanumerica"));
        assertTrue(trie.insertar("a-b_c!", "con-simbolos"));
        assertTrue(trie.insertar("ñoño", "con-enie-y-tilde"));

        assertEquals("alfanumerica", trie.buscar("abc123").getDato());
        assertEquals("con-simbolos", trie.buscar("a-b_c!").getDato());
        assertEquals("con-enie-y-tilde", trie.buscar("ñoño").getDato());
    }
}
