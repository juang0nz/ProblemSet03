package ucu.edu.aed.ejercicios.ejercicio16;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Nodo genérico para un árbol donde cada nodo representa una Persona y puede
 * tener cero o más hijos.
 */
public class NodoPersona {
    private final Persona dato;
    private final List<NodoPersona> hijos;

    public NodoPersona(Persona dato) {
        this.dato = dato;
        this.hijos = new ArrayList<>();
    }

    public Persona getDato() {
        return dato;
    }

    public List<NodoPersona> getHijos() {
        return hijos;
    }

    public void agregarHijo(NodoPersona hijo) {
        hijos.add(hijo);
    }

    public void recorrer(Consumer<Persona> consumidor) {
        consumirRec(this, consumidor);
    }

    private void consumirRec(NodoPersona nodo, Consumer<Persona> consumidor) {
        consumidor.accept(nodo.getDato());
        for (NodoPersona h : nodo.getHijos()) {
            consumirRec(h, consumidor);
        }
    }
}
