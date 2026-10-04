package ucu.edu.aed.ejercicios.ejercicio15;

import java.util.HashSet;
import java.util.Set;

public class main {
    public static void main(String[] args) {
        // Creamos un HashSet para almacenar libros
        Set<Libros> biblioteca = new HashSet<>();

        // Dos objetos distintos en memoria pero con el MISMO ISBN
        Libros libro1 = new Libros("978-84-376-0494-7", "Don Quijote de la Mancha", "Miguel de Cervantes", 1605);
        Libros libro2 = new Libros("978-84-376-0494-7", "Don Quijote (Otra edición)", "Miguel de Cervantes", 2020);

        // Los intentamos insertar al HashSet
        biblioteca.add(libro1);
        biblioteca.add(libro2);

        // Verificamos el tamaño del conjunto
        System.out.println("Cantidad de libros en el set: " + biblioteca.size());
        
        // Debería imprimir 1, porque gracias a equals y hashCode basados en el ISBN, 
        // el HashSet detecta que son lógicamente el mismo libro y evita duplicados.
    }
}