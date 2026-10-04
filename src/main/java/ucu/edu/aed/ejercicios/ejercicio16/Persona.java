package ucu.edu.aed.ejercicios.ejercicio16;

import java.util.Objects;

public class Persona {
    private final String nombre;
    private final int anioNacimiento;

    public Persona(String nombre, int anioNacimiento) {
        if (nombre == null) throw new NullPointerException("nombre");
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    @Override
    public String toString() {
        return nombre + " (" + anioNacimiento + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return anioNacimiento == persona.anioNacimiento && nombre.equals(persona.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, anioNacimiento);
    }
}
