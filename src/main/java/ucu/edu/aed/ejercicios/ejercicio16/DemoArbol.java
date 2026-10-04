package ucu.edu.aed.ejercicios.ejercicio16;

import java.util.List;

public class DemoArbol {
    public static ArbolGenealogico construirEjemplo() {
        // Raíz
        Persona abuela = new Persona("Abuela", 1940);
        ArbolGenealogico arbol = new ArbolGenealogico(abuela);

        // Generación 1
        NodoPersona hijo1 = new NodoPersona(new Persona("Hijo1", 1960));
        NodoPersona hijo2 = new NodoPersona(new Persona("Hijo2", 1962));
        NodoPersona hija3 = new NodoPersona(new Persona("Hija3", 1965));

        arbol.getRaiz().agregarHijo(hijo1);
        arbol.getRaiz().agregarHijo(hijo2);
        arbol.getRaiz().agregarHijo(hija3);

        // Generación 2
        hijo1.agregarHijo(new NodoPersona(new Persona("Nieto1", 1985)));
        hijo1.agregarHijo(new NodoPersona(new Persona("Nieto2", 1987)));

        hijo2.agregarHijo(new NodoPersona(new Persona("Nieto3", 1990)));

        hija3.agregarHijo(new NodoPersona(new Persona("Nieto4", 1992)));
        hija3.agregarHijo(new NodoPersona(new Persona("Nieto5", 1995)));
        hija3.agregarHijo(new NodoPersona(new Persona("Nieta6", 1998)));

        return arbol;
    }

    public static void main(String[] args) {
        ArbolGenealogico arbol = construirEjemplo();

        System.out.println("Altura: " + arbol.altura());
        System.out.println("Total personas: " + arbol.contarPersonas());

        List<String> nombresGen1 = arbol.personasDeGeneracion(1).stream().map(Persona::getNombre).toList();
        System.out.println("Generacion 1: " + nombresGen1);

        System.out.println("Descendientes de Hijo1: " + arbol.listarDescendientes("Hijo1"));

        System.out.println("Ancestro comun entre Nieto1 y Nieto2: " + arbol.ancestroComunMasCercano("Nieto1", "Nieto2"));
        System.out.println("Ancestro comun entre Nieto1 y Nieto3: " + arbol.ancestroComunMasCercano("Nieto1", "Nieto3"));

        System.out.println("¿Nieto4 es descendiente de Abuela? " + arbol.esDescendienteDe("Nieto4", "Abuela"));
        System.out.println("¿Hijo1 es descendiente de Nieto1? " + arbol.esDescendienteDe("Hijo1", "Nieto1"));
    }
}
