package ucu.edu.aed.medible.medibles;

import ucu.edu.aed.medible.lib.Medible;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class MedicionPredecirTreeMap extends Medible<String> {

    private final TreeMap<String, ?> treeMap;

    public MedicionPredecirTreeMap(TreeMap<String, ?> treeMap) {
        this.treeMap = treeMap;
    }

    @Override
    public void ejecutar(int repeticiones, String prefijo) {
        String fromKey = prefijo;
        // toKey: prefijo + highest possible char to include all keys starting with prefijo
        String toKey = prefijo + Character.MAX_VALUE;

        for (int i = 0; i < repeticiones; i++) {
            NavigableMap<String, ?> sub = treeMap.subMap(fromKey, true, toKey, true);
            // for measurement purposes, iterate the keys
            for (String k : sub.keySet()) {
                // no-op
            }
        }
    }

    @Override
    public Object getObjetoAMedirMemoria() {
        return this.treeMap;
    }
}
