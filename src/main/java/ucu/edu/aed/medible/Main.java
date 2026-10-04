package ucu.edu.aed.medible;


import ucu.edu.aed.medible.lib.Medible;
import ucu.edu.aed.medible.lib.Medicion;
import ucu.edu.aed.medible.medibles.MedicionBuscarLinkedList;
import ucu.edu.aed.medible.medibles.MedicionBuscarArrayList;
import ucu.edu.aed.medible.medibles.MedicionBuscarTrie;
import ucu.edu.aed.medible.medibles.MedicionBuscarHashMap;
import ucu.edu.aed.medible.medibles.MedicionBuscarTreeMap;
import ucu.edu.aed.medible.medibles.MedicionPredecirTrie;
import ucu.edu.aed.medible.medibles.MedicionPredecirLinkedList;
import ucu.edu.aed.medible.medibles.MedicionPredecirHashMap;
import ucu.edu.aed.medible.medibles.MedicionPredecirTreeMap;
import ucu.edu.aed.utils.FileUtils;
import ucu.edu.aed.tda.trie.impl.TTrie;

import java.util.*;

public class Main {

    // Ajustado a 20 repeticiones según el enunciado
    private static final int REPETICIONES = 20;

    public static void main(String[] args) {
        TTrie<String> trie = new TTrie<>();
        LinkedList<String> linkedList = new LinkedList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> treeMap = new TreeMap<>();

        List<String> palabrasParaAgregar = new LinkedList<>();
        List<String> palabrasParaBuscar = new LinkedList<>();
        FileUtils.leerLineas("./ut03/listado-general-desordenado.txt", palabrasParaAgregar::add);
        FileUtils.leerLineas("./ut03/listado-general-palabrasBuscar.txt", palabrasParaBuscar::add);

        for (String p : palabrasParaAgregar) {
            // insertar la palabra p en el trie
            trie.insertar(p, p);
            // insertar la palabra p en el linkedList
            linkedList.add(p);
            // insertar la palabra p en el arrayList
            arrayList.add(p);
            // insertar la palabra p en el hashMap
            hashMap.put(p, p);
            // insertar la palabra p en el treeMap
            treeMap.put(p, p);
        }

        // ***** Mediciones de búsquedas (Parte 3) *****
        List<Medible<List<String>>> mediblesBuscar = new LinkedList<>();
        mediblesBuscar.add(new MedicionBuscarLinkedList(linkedList));
        mediblesBuscar.add(new MedicionBuscarArrayList(arrayList));
        mediblesBuscar.add(new MedicionBuscarTrie(trie));
        mediblesBuscar.add(new MedicionBuscarHashMap(hashMap));
        mediblesBuscar.add(new MedicionBuscarTreeMap(treeMap));

        StringBuilder sbBuscar = new StringBuilder();
        sbBuscar.append("algoritmo,tiempo,memoria\n");

        for (Medible<List<String>> m : mediblesBuscar) {
            Medicion mi = m.medir(REPETICIONES, palabrasParaBuscar);
            mi.print();
            sbBuscar.append(mi.toCSV()).append("\n");
        }

        FileUtils.escribirLineas("./salida.csv", sbBuscar.toString());

        // ***** Mediciones de predecir / autocompletar (Parte 5) *****
        // Prefijo solicitado en el enunciado: "cas"
        String prefijo = "cas";

        List<Medible<String>> mediblesPredecir = new LinkedList<>();
        mediblesPredecir.add(new MedicionPredecirTrie(trie));
        mediblesPredecir.add(new MedicionPredecirLinkedList(linkedList));
        mediblesPredecir.add(new MedicionPredecirHashMap(hashMap));
        mediblesPredecir.add(new MedicionPredecirTreeMap((TreeMap) treeMap));

        StringBuilder sbPredecir = new StringBuilder();
        sbPredecir.append("algoritmo,prefijo,tiempo,memoria\n");

        for (Medible<String> m : mediblesPredecir) {
            Medicion mi = m.medir(REPETICIONES, prefijo);
            mi.print();
            // añadimos la columna del prefijo para claridad
            sbPredecir.append(String.format("%s,%s,%s,%s\n", mi.getTexto(), prefijo, mi.getMemoria() == null ? "-" : ucu.edu.aed.medible.lib.Formatter.formatMemory(mi.getMemoria()), mi.getTiempoEjecucion() == null ? "-" : ucu.edu.aed.medible.lib.Formatter.formatNanos(mi.getTiempoEjecucion(), 2)));
        }

        FileUtils.escribirLineas("./salida_predecir.csv", sbPredecir.toString());

        System.out.println("Preparadas mediciones de búsqueda y predecir. Archivos escritos: ./salida.csv y ./salida_predecir.csv (no ejecutado automáticamente en CI). No se ejecutaron mediciones adicionales fuera de las 20 repeticiones configuradas.");
    }
}