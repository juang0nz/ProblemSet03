package ucu.edu.aed.medible.medibles;

import ucu.edu.aed.medible.lib.Medible;

import java.util.LinkedList;

public class MedicionPredecirLinkedList extends Medible<String> {

    private final LinkedList<String> lista;

    public MedicionPredecirLinkedList(LinkedList<String> lista) {
        this.lista = lista;
    }

    @Override
    public void ejecutar(int repeticiones, String prefijo) {
        for (int i = 0; i < repeticiones; i++) {
            for (String clave : lista) {
                if (clave != null && clave.startsWith(prefijo)) {
                    // acumulación mínima si se quisiera: pero ignoramos
                }
            }
        }
    }

    @Override
    public Object getObjetoAMedirMemoria() {
        return this.lista;
    }
}
