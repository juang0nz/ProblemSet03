package ucu.edu.aed.ejercicios.ejercicio16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;

/**
 * Representación simple de un árbol genealógico construido desde un ancestro
 * común. No se modelan padres explícitos, sólo hijos en cada nodo.
 */
public class ArbolGenealogico {

    private final NodoPersona raiz;

    public ArbolGenealogico(Persona ancestro) {
        this.raiz = new NodoPersona(Objects.requireNonNull(ancestro));
    }

    public NodoPersona getRaiz() {
        return raiz;
    }

    public NodoPersona encontrarNodoPorNombre(String nombre) {
        if (nombre == null) throw new NullPointerException("nombre");
        return encontrarNodoRec(raiz, nombre);
    }

    private NodoPersona encontrarNodoRec(NodoPersona nodo, String nombre) {
        if (nodo.getDato().getNombre().equals(nombre)) return nodo;
        for (NodoPersona h : nodo.getHijos()) {
            NodoPersona r = encontrarNodoRec(h, nombre);
            if (r != null) return r;
        }
        return null;
    }

    /**
     * 1. Listar todos los descendientes de una persona dada (excluye a la
     * persona misma).
     */
    public List<Persona> listarDescendientes(String nombre) {
        NodoPersona nodo = encontrarNodoPorNombre(nombre);
        List<Persona> resultado = new ArrayList<>();
        if (nodo == null) return resultado;
        for (NodoPersona h : nodo.getHijos()) {
            recolectarSubtree(h, resultado);
        }
        return resultado;
    }

    private void recolectarSubtree(NodoPersona nodo, List<Persona> lista) {
        lista.add(nodo.getDato());
        for (NodoPersona h : nodo.getHijos()) {
            recolectarSubtree(h, lista);
        }
    }

    /**
     * 2. Calcular la altura del árbol (cantidad máxima de aristas desde la
     * raíz hasta una hoja). Para un único nodo la altura es 0.
     */
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoPersona nodo) {
        if (nodo.getHijos().isEmpty()) return 0;
        int max = 0;
        for (NodoPersona h : nodo.getHijos()) {
            max = Math.max(max, alturaRec(h));
        }
        return 1 + max;
    }

    /**
     * 3. Contar la cantidad total de personas en el árbol.
     */
    public int contarPersonas() {
        return contarRec(raiz);
    }

    private int contarRec(NodoPersona nodo) {
        int c = 1;
        for (NodoPersona h : nodo.getHijos()) c += contarRec(h);
        return c;
    }

    /**
     * 4. Obtener todas las personas de una generación dada (generación 0 =
     * raíz).
     */
    public List<Persona> personasDeGeneracion(int generacion) {
        if (generacion < 0) throw new IllegalArgumentException("generacion negativa");
        List<Persona> res = new ArrayList<>();
        if (generacion == 0) {
            res.add(raiz.getDato());
            return res;
        }
        // BFS by levels
        Deque<NodoPersona> cola = new ArrayDeque<>();
        cola.add(raiz);
        int nivel = 0;
        while (!cola.isEmpty() && nivel < generacion) {
            int size = cola.size();
            for (int i = 0; i < size; i++) {
                NodoPersona n = cola.removeFirst();
                for (NodoPersona h : n.getHijos()) cola.addLast(h);
            }
            nivel++;
        }
        // Now nivel == generacion or queue empty
        while (!cola.isEmpty()) {
            res.add(cola.removeFirst().getDato());
        }
        return res;
    }

    /**
     * 5. Encontrar el ancestro común más cercano entre dos personas.
     * Retorna null si alguno de los nombres no existe en el árbol.
     */
    public Persona ancestroComunMasCercano(String nombreA, String nombreB) {
        List<NodoPersona> rutaA = rutaDesdeRaiz(nombreA);
        List<NodoPersona> rutaB = rutaDesdeRaiz(nombreB);
        if (rutaA == null || rutaB == null) return null;
        // comparar rutas
        int i = 0;
        NodoPersona ultimoComun = null;
        while (i < rutaA.size() && i < rutaB.size()) {
            if (rutaA.get(i) == rutaB.get(i)) {
                ultimoComun = rutaA.get(i);
            } else break;
            i++;
        }
        return ultimoComun == null ? null : ultimoComun.getDato();
    }

    /**
     * Devuelve la ruta (lista de nodos) desde la raíz hasta el nodo con el
     * nombre dado, inclusive. Retorna null si no se encuentra.
     */
    private List<NodoPersona> rutaDesdeRaiz(String nombre) {
        List<NodoPersona> ruta = new ArrayList<>();
        if (encontrarRutaRec(raiz, nombre, ruta)) return ruta;
        return null;
    }

    private boolean encontrarRutaRec(NodoPersona nodo, String objetivo, List<NodoPersona> ruta) {
        ruta.add(nodo);
        if (nodo.getDato().getNombre().equals(objetivo)) return true;
        for (NodoPersona h : nodo.getHijos()) {
            if (encontrarRutaRec(h, objetivo, ruta)) return true;
        }
        // backtrack
        ruta.remove(ruta.size() - 1);
        return false;
    }

    /**
     * 6. Determinar si una persona es descendiente de otra.
     */
    public boolean esDescendienteDe(String posibleDescendiente, String posibleAncestro) {
        NodoPersona anc = encontrarNodoPorNombre(posibleAncestro);
        if (anc == null) return false;
        return existeEnSubtree(anc, posibleDescendiente);
    }

    private boolean existeEnSubtree(NodoPersona nodo, String nombre) {
        for (NodoPersona h : nodo.getHijos()) {
            if (h.getDato().getNombre().equals(nombre)) return true;
            if (existeEnSubtree(h, nombre)) return true;
        }
        return false;
    }
}
