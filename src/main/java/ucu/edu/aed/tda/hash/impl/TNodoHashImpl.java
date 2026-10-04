package ucu.edu.aed.tda.hash.impl;

import ucu.edu.aed.tda.hash.Entry;
import ucu.edu.aed.tda.hash.TNodoHash;

public class TNodoHashImpl<K, V> extends TNodoHash<K, V> {

    public TNodoHashImpl(K clave, V valor) {
        super(clave, valor);
    }

    public boolean coincideCon(K clave) {
        return !isLoteLibre() && getClave().equals(clave);
    }

    public void marcarLoteLibre() {
        setLoteLibre(true);
    }

    @Override
    public Entry<K, V> getEntry() {
        return super.getEntry();
    }
}
