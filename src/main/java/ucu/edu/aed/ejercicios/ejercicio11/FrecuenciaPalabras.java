package ucu.edu.aed.ejercicios.ejercicio11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ejercicio 11: frecuencias de ocurrencia de las palabras de un libro.
 *
 * Elección de estructura: {@code HashMap<String, Integer>}.
 * Justificación: la única operación relevante es "incrementar el contador de
 * la palabra X", que requiere buscar una clave y actualizar su valor. No se
 * necesita orden de iteración (por eso no se elige {@code TreeMap}, que es
 * O(log n) por operación) ni acceso por índice (por eso no se elige una
 * {@code List}, que obligaría a recorrerla para encontrar cada palabra).
 * {@code HashMap} ofrece O(1) amortizado para {@code get}/{@code put}, que es
 * la mejor complejidad posible para construir y consultar un índice de
 * frecuencias sobre un libro con potencialmente muchas palabras distintas.
 */
public class FrecuenciaPalabras {

    private final Map<String, Integer> frecuencias = new HashMap<>();

    /**
     * Procesa una línea de texto: la separa en palabras (ignorando
     * puntuación y mayúsculas) y acumula sus frecuencias.
     */
    public void procesarLinea(String linea) {
        for (String palabra : tokenizar(linea)) {
            frecuencias.merge(palabra, 1, Integer::sum);
        }
    }

    public void procesarTexto(Iterable<String> lineas) {
        for (String linea : lineas) {
            procesarLinea(linea);
        }
    }

    private List<String> tokenizar(String linea) {
        String normalizada = linea.toLowerCase();
        String[] partes = normalizada.split("[^a-záéíóúüñ]+");
        List<String> palabras = new ArrayList<>();
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                palabras.add(parte);
            }
        }
        return palabras;
    }

    public int frecuenciaDe(String palabra) {
        return frecuencias.getOrDefault(palabra.toLowerCase(), 0);
    }

    public int cantidadPalabrasDistintas() {
        return frecuencias.size();
    }

    /**
     * Devuelve las {@code n} palabras con mayor frecuencia, ordenadas en
     * forma descendente.
     */
    public List<Map.Entry<String, Integer>> topN(int n) {
        List<Map.Entry<String, Integer>> entradas = new ArrayList<>(frecuencias.entrySet());
        entradas.sort((a, b) -> b.getValue() - a.getValue());
        return entradas.subList(0, Math.min(n, entradas.size()));
    }
}
