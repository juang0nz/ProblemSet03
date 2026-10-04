package ucu.edu.aed.ejercicios.ejercicio12;

import java.util.List;

import junit.framework.TestCase;

public class TestAutocompletar extends TestCase {

    public void testSugerirDevuelvePalabrasConPrefijoOrdenadas() {
        Autocompletar autocompletar = new Autocompletar();
        autocompletar.agregarPalabra("casa");
        autocompletar.agregarPalabra("casada");
        autocompletar.agregarPalabra("cazar");
        autocompletar.agregarPalabra("perro");

        List<String> sugerencias = autocompletar.sugerir("cas");

        assertEquals(2, sugerencias.size());
        assertEquals("casa", sugerencias.get(0));
        assertEquals("casada", sugerencias.get(1));
    }

    public void testSugerirSinCoincidenciasDevuelveListaVacia() {
        Autocompletar autocompletar = new Autocompletar();
        autocompletar.agregarPalabra("hola");

        List<String> sugerencias = autocompletar.sugerir("zzz");

        assertNotNull(sugerencias);
        assertTrue(sugerencias.isEmpty());
    }

    public void testExiste() {
        Autocompletar autocompletar = new Autocompletar();
        autocompletar.agregarPalabra("programa");
        autocompletar.agregarPalabra("programacion");

        assertTrue(autocompletar.existe("programa"));
        assertTrue(autocompletar.existe("programacion"));
        // "program" es un prefijo válido, pero no fue insertado como palabra
        assertFalse(autocompletar.existe("program"));
        assertFalse(autocompletar.existe("inexistente"));
    }

    public void testCargarDesdeArchivoDeRecursos() {
        Autocompletar autocompletar = new Autocompletar();
        autocompletar.cargarDesdeArchivo("ut03/palabras.txt");

        List<String> sugerencias = autocompletar.sugerir("al");

        assertTrue(sugerencias.contains("ala"));
        assertTrue(sugerencias.contains("alimania"));
        assertTrue(sugerencias.contains("alabastro"));
        assertTrue(sugerencias.contains("alimento"));
    }
}
