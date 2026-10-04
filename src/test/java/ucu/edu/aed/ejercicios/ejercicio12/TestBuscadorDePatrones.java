package ucu.edu.aed.ejercicios.ejercicio12;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class TestBuscadorDePatrones extends TestCase {

    public void testPatronConOcurrenciasSuperpuestas() {
        BuscadorDePatrones buscador = new BuscadorDePatrones("banana");

        List<Integer> posiciones = buscador.buscarPatron("ana");

        assertEquals(Arrays.asList(1, 3), posiciones);
    }

    public void testPatronQueNoOcurre() {
        BuscadorDePatrones buscador = new BuscadorDePatrones("banana");

        List<Integer> posiciones = buscador.buscarPatron("xyz");

        assertNotNull(posiciones);
        assertTrue(posiciones.isEmpty());
    }

    public void testPatronIgualATodoElTexto() {
        BuscadorDePatrones buscador = new BuscadorDePatrones("banana");

        List<Integer> posiciones = buscador.buscarPatron("banana");

        assertEquals(Arrays.asList(0), posiciones);
    }

    public void testPatronDeUnSoloCaracterRepetido() {
        BuscadorDePatrones buscador = new BuscadorDePatrones("aaaa");

        List<Integer> posiciones = buscador.buscarPatron("a");

        assertEquals(Arrays.asList(0, 1, 2, 3), posiciones);
    }

    public void testPatronVacioDevuelveListaVacia() {
        BuscadorDePatrones buscador = new BuscadorDePatrones("banana");

        List<Integer> posiciones = buscador.buscarPatron("");

        assertNotNull(posiciones);
        assertTrue(posiciones.isEmpty());
    }

    public void testPatronAlFinalDelTexto() {
        BuscadorDePatrones buscador = new BuscadorDePatrones("abracadabra");

        List<Integer> posiciones = buscador.buscarPatron("bra");

        assertEquals(Arrays.asList(1, 8), posiciones);
    }
}
