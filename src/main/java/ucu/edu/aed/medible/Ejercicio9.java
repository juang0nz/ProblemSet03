package ucu.edu.aed.medible;

import ucu.edu.aed.tda.hash.THash;
import ucu.edu.aed.tda.hash.Report;
import ucu.edu.aed.tda.hash.impl.THashImpl;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio9 {

    public static void main(String[] args) {
        int totalElementos = 10000;
        
        // 1. Generamos 10.000 palabras simuladas en vez de leerlas de un TXT
        List<String> clavesInsertar = new ArrayList<>(totalElementos);
        for (int i = 0; i < totalElementos; i++) {
            clavesInsertar.add("Palabra_" + i);
        }
        
        // 2. Generamos palabras que SEGURO NO ESTÁN para medir el fracaso
        List<String> clavesBuscar = new ArrayList<>(totalElementos);
        for (int i = 0; i < totalElementos; i++) {
            clavesBuscar.add("NoExiste_" + i);
        }

        int[] factoresDeCarga = {70, 75, 80, 85, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99};

        System.out.printf("%-15s | %-20s | %-20s | %-20s%n", 
                "Factor%", "Prom Insercion", "Prom Exito", "Prom Fracaso");
        System.out.println("----------------------------------------------------------------------------------");

        for (int factor : factoresDeCarga) {
            // Calculamos tamaño exacto para forzar el % de carga
            int tamanoRealDeseado = (int) (totalElementos / (factor / 100.0));
            int parametroConstructor = (int) (tamanoRealDeseado * 0.9);
            
            THash<String, String> tabla = new THashImpl<>(parametroConstructor); 
            
            // --- MEDIR INSERCIÓN ---
            long totalCompInsercion = 0;
            for (String palabra : clavesInsertar) {
                Report reporteInsercion = new Report();
                tabla.insertar(palabra, palabra, reporteInsercion); 
                totalCompInsercion += reporteInsercion.getCantidadComparaciones(); 
            }
            double promInsercion = (double) totalCompInsercion / totalElementos;

            // --- MEDIR BÚSQUEDA EXITOSA ---
            long totalCompExito = 0;
            for (String palabra : clavesInsertar) { 
                Report reporteExito = new Report();
                tabla.buscar(palabra, reporteExito); 
                totalCompExito += reporteExito.getCantidadComparaciones();
            }
            double promExito = (double) totalCompExito / totalElementos;

            // --- MEDIR BÚSQUEDA SIN ÉXITO ---
            long totalCompFracaso = 0;
            for (String palabraFantasma : clavesBuscar) { 
                Report reporteFracaso = new Report();
                tabla.buscar(palabraFantasma, reporteFracaso); 
                totalCompFracaso += reporteFracaso.getCantidadComparaciones();
            }
            double promFracaso = (double) totalCompFracaso / clavesBuscar.size();

            // Imprimir fila
            System.out.printf("%-15d | %-20.2f | %-20.2f | %-20.2f%n", 
                    factor, promInsercion, promExito, promFracaso);
        }
    }
}