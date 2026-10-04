package ucu.edu.aed.ejercicios.ejercicio16;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class TestArbolGenealogico extends TestCase {

    private ArbolGenealogico construir() {
        return DemoArbol.construirEjemplo();
    }

    public void testContarYAltura() {
        ArbolGenealogico arbol = construir();
        assertEquals(10, arbol.contarPersonas());
        assertEquals(2, arbol.altura());
    }

    public void testListarDescendientes() {
        ArbolGenealogico arbol = construir();
        List<Persona> desc = arbol.listarDescendientes("Hijo1");
        assertEquals(2, desc.size());
        assertTrue(desc.stream().anyMatch(p -> p.getNombre().equals("Nieto1")));
        assertTrue(desc.stream().anyMatch(p -> p.getNombre().equals("Nieto2")));
    }

    public void testPersonasDeGeneracion() {
        ArbolGenealogico arbol = construir();
        List<Persona> gen0 = arbol.personasDeGeneracion(0);
        assertEquals(1, gen0.size());
        assertEquals("Abuela", gen0.get(0).getNombre());

        List<Persona> gen1 = arbol.personasDeGeneracion(1);
        assertEquals(3, gen1.size());

        List<Persona> gen2 = arbol.personasDeGeneracion(2);
        assertEquals(6, gen2.size());
    }

    public void testAncestroComun() {
        ArbolGenealogico arbol = construir();
        Persona lca1 = arbol.ancestroComunMasCercano("Nieto1", "Nieto2");
        assertNotNull(lca1);
        assertEquals("Hijo1", lca1.getNombre());

        Persona lca2 = arbol.ancestroComunMasCercano("Nieto1", "Nieto3");
        assertNotNull(lca2);
        assertEquals("Abuela", lca2.getNombre());
    }

    public void testEsDescendiente() {
        ArbolGenealogico arbol = construir();
        assertTrue(arbol.esDescendienteDe("Nieto4", "Abuela"));
        assertFalse(arbol.esDescendienteDe("Hijo1", "Nieto1"));
    }
}
