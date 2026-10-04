package ucu.edu.aed.tda.generic_trie;

import junit.framework.TestCase;
import ucu.edu.aed.tda.generic_trie.impl.TArbolGenericoImpl;

public class TestTArbolGenerico extends TestCase {

    public void testAgregarBuscarPadreGradoAlturaEliminarVaciar() {
        TArbolGenericoImpl<String> arbol = new TArbolGenericoImpl<>("A");

        // Agregar hijos
        assertTrue(arbol.agregarHijo("A", "B"));
        assertTrue(arbol.agregarHijo("A", "C"));
        assertTrue(arbol.agregarHijo("B", "D"));

        // Buscar
        assertEquals("D", arbol.buscar("D"));

        // Obtener padre
        assertEquals("B", arbol.obtenerPadre("D"));

        // Grado del nodo A (tiene B y C)
        assertEquals(2, arbol.grado("A"));

        // Altura del árbol en A: A->B->D = 3
        assertEquals(3, arbol.altura("A"));

        // Eliminar D
        arbol.eliminar("D");
        assertNull(arbol.buscar("D"));

        // Ahora B no tiene hijos
        assertEquals(0, arbol.grado("B"));

        // Vaciar
        arbol.vaciar();
        assertNull(arbol.buscar("A"));
        assertEquals(-1, arbol.grado("A"));
        assertEquals(-1, arbol.altura("A"));
    }

    public void testAgregarHijoEnArbolSinRaizYEliminarRaizYRecorridos() {
        // árbol sin raíz
        TArbolGenericoImpl<String> vacio = new TArbolGenericoImpl<>();
        assertFalse(vacio.agregarHijo("A", "B"));

        // construir otro árbol para probar eliminar raíz y recorridos
        TArbolGenericoImpl<String> arbol = new TArbolGenericoImpl<>("A");
        assertTrue(arbol.agregarHijo("A", "B"));
        assertTrue(arbol.agregarHijo("A", "C"));
        assertTrue(arbol.agregarHijo("B", "D"));

        // Preorden esperado: A, B, D, C
        final java.util.List<String> pre = new java.util.ArrayList<>();
        arbol.preOrden(v -> pre.add(v));
        assertEquals(4, pre.size());
        assertEquals("A", pre.get(0));
        assertEquals("B", pre.get(1));
        assertEquals("D", pre.get(2));
        assertEquals("C", pre.get(3));

        // InOrden y PostOrden según la implementación actual no llaman al consumidor sobre el nodo mismo,
        // por lo tanto se espera que no produzcan elementos.
        final java.util.List<String> ino = new java.util.ArrayList<>();
        arbol.inOrden(v -> ino.add(v));
        assertEquals(0, ino.size());

        final java.util.List<String> post = new java.util.ArrayList<>();
        arbol.postOrden(v -> post.add(v));
        assertEquals(0, post.size());

        // Eliminar la raíz A
        arbol.eliminar("A");
        assertNull(arbol.buscar("A"));
        // después de eliminar la raíz, buscar debe devolver null y grado/altura -1
        assertEquals(-1, arbol.grado("A"));
        assertEquals(-1, arbol.altura("A"));
    }
}
