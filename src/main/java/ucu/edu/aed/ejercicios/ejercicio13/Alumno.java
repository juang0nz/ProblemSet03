package ucu.edu.aed.ejercicios.ejercicio13;
import java.util.Objects;

/**
 * =====================================================================
 * EJERCICIO 13: RESPUESTAS TEÓRICAS E INVESTIGACIÓN
 * =====================================================================
 * 
 * --- PARTE 1: Investigación de hashCode en Java ---
 * 1. Object.hashCode():
 *    - Es un método nativo.
 *    - Por defecto se basa en la identidad del objeto en memoria.
 * 
 * 2. Integer.hashCode():
 *    - Retorna directamente el valor primitivo entero que envuelve.
 *    - No requiere cálculos adicionales ya que el número en sí mismo es óptimo.
 * 
 * 3. String.hashCode():
 *    - Utiliza un algoritmo polinómico multiplicando los caracteres por un factor 
 *      primo (31) de forma iterativa: s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1].
 * 
 * ¿Por qué son diferentes?
 * Porque dependen de la naturaleza de los datos. Object evalúa identidad de 
 * referencias, Integer maneja números directos, y String procesa una secuencia 
 * compuesta de caracteres buscando una distribución uniforme para evitar colisiones.
 * 
 * 
 * --- PARTE 2: Estructura interna de un HashMap ---
 * Estructura:
 * - Se compone de un array de "cubetas" (buckets) y utiliza listas enlazadas 
 *   (o árboles rojinegros si la lista supera los 8 elementos en Java 8+) para 
 *   resolver las colisiones por encadenamiento.
 * 
 * Diagrama conceptual de inserción ("Hola", "HolaMundo", "HashMap", "Colecciones"):
 * [ Bucket 0 ] -> null
 * [ Bucket 1 ] -> Node(Key: "Hola") -> null
 * [ Bucket 2 ] -> null
 * [ Bucket 3 ] -> Node(Key: "HashMap") -> null
 * [ Bucket 4 ] -> null
 * [ Bucket 5 ] -> Node(Key: "HolaMundo") -> null
 * ...
 * [ Bucket 12] -> Node(Key: "Colecciones") -> null
 * (Las posiciones exactas dependen del hashcode y de la capacidad inicial del array).
 * 
 * 
 * --- PARTE 3: Contrato general de hashCode ---
 * Características obligatorias para mantener el contrato con equals:
 * 1. Consistencia: Si los atributos de un objeto no cambian, su hashCode debe 
 *    retornar siempre el mismo valor durante la ejecución.
 * 2. Coherencia con equals: Si dos objetos son iguales según el método equals 
 *    (a.equals(b) == true), obligatoriamente deben retornar el mismo hashCode.
 * 3. No exclusividad inversa: Si dos objetos son distintos, no es obligatorio 
 *    que tengan hashCodes diferentes (pueden existir colisiones), aunque lo ideal 
 *    es que sean distintos para favorecer el rendimiento de las estructuras hash.
 * =====================================================================
 */
public class Alumno {
    private int id;
    private String fullName;
    private String email;

    public Alumno(int id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return id == alumno.id &&
               Objects.equals(fullName, alumno.fullName) &&
               Objects.equals(email, alumno.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email);
    }
}