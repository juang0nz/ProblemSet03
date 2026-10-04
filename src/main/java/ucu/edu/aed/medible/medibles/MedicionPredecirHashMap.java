package ucu.edu.aed.medible.medibles;

import ucu.edu.aed.medible.lib.Medible;

import java.util.Map;

public class MedicionPredecirHashMap extends Medible<String> {

    private final Map<String, ?> map;

    public MedicionPredecirHashMap(Map<String, ?> map) {
        this.map = map;
    }

    @Override
    public void ejecutar(int repeticiones, String prefijo) {
        for (int i = 0; i < repeticiones; i++) {
            for (String clave : map.keySet()) {
                if (clave != null && clave.startsWith(prefijo)) {
                    // no acumulamos, solo medimos
                }
            }
        }
    }

    @Override
    public Object getObjetoAMedirMemoria() {
        return this.map;
    }
}
