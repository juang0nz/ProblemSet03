package ucu.edu.aed.tda.hash.impl;

import java.util.ArrayList;
import java.util.List;

import ucu.edu.aed.tda.hash.Entry;
import ucu.edu.aed.tda.hash.Report;
import ucu.edu.aed.tda.hash.THash;
import ucu.edu.aed.tda.hash.TNodoHash;

/**
 * Implementación simple de tabla hash con direccionamiento abierto (linear probing).
 * Soporta redimensionamiento cuando la carga supera el umbral.
 */
public class THashImpl<K, V> extends THash<K, V> {

    private static final double LOAD_FACTOR_THRESHOLD = 0.7;

    public THashImpl(int elementosEsperados) {
        super(elementosEsperados);
    }

    @Override
    public V buscar(K clave, Report report) {
        if (clave == null) {
            throw new NullPointerException("clave nula");
        }

        int capacidad = hashTable.length;
        int pos = functionHashing(clave);
        int inicial = pos;
        while (true) {
            TNodoHash<K, V> nodo = hashTable[pos];
            report.setCantidadComparaciones(report.getCantidadComparaciones() + 1);
            if (nodo == null) {
                return null;
            }
            if (!nodo.isLoteLibre() && clave.equals(nodo.getClave())) {
                return nodo.getValor();
            }
            pos = (pos + 1) % capacidad;
            if (pos == inicial) {
                return null;
            }
        }
    }

    @Override
    public boolean delete(K clave, Report report) {
        if (clave == null) {
            throw new NullPointerException("clave nula");
        }

        int capacidad = hashTable.length;
        int pos = functionHashing(clave);
        int inicial = pos;
        while (true) {
            TNodoHash<K, V> nodo = hashTable[pos];
            report.setCantidadComparaciones(report.getCantidadComparaciones() + 1);
            if (nodo == null) {
                return false;
            }
            if (!nodo.isLoteLibre() && clave.equals(nodo.getClave())) {
                nodo.setLoteLibre(true);
                return true;
            }
            pos = (pos + 1) % capacidad;
            if (pos == inicial) {
                return false;
            }
        }
    }

    @Override
    public boolean insertar(K clave, V valor, Report report) {
        if (clave == null) {
            throw new NullPointerException("clave nula");
        }

        if (valor == null) {
            throw new NullPointerException("valor nulo");
        }

        // redimensionar si es necesario
        if (needsResize()) {
            redimensionar();
        }

        int capacidad = hashTable.length;
        int pos = functionHashing(clave);
        int inicial = pos;
        Integer firstDeleted = null;

        while (true) {
            TNodoHash<K, V> nodo = hashTable[pos];
            report.setCantidadComparaciones(report.getCantidadComparaciones() + 1);
            if (nodo == null) {
                // insertar en el primer sitio borrado si existiera
                int insertPos = (firstDeleted != null) ? firstDeleted : pos;
                hashTable[insertPos] = new TNodoHashImpl<>(clave, valor);
                return true;
            }

            if (nodo.isLoteLibre()) {
                if (firstDeleted == null) {
                    firstDeleted = pos;
                }
            } else if (clave.equals(nodo.getClave())) {
                // clave ya existe (no sobrescribimos)
                return false;
            }

            pos = (pos + 1) % capacidad;
            if (pos == inicial) {
                // tabla llena, redimensionar y volver a intentar
                if (redimensionar()) {
                    return insertar(clave, valor, report);
                } else {
                    return false;
                }
            }
        }
    }

    private boolean needsResize() {
        int occupied = 0;
        for (TNodoHash<K, V> n : hashTable) {
            if (n != null && !n.isLoteLibre()) {
                occupied++;
            }
        }
        return ((double) occupied / hashTable.length) > LOAD_FACTOR_THRESHOLD;
    }

    @Override
    protected int functionHashing(K valor) {
        int raw = valor.hashCode();
        int idx = Math.abs(raw) % hashTable.length;
        return idx;
    }

    @Override
    public boolean esVacio() {
        for (TNodoHash<K, V> n : hashTable) {
            if (n != null && !n.isLoteLibre()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void vaciar() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
    }

    @Override
    protected int calcularCapacidadOptima(int elementosEsperados) {
        int target = Math.max(3, elementosEsperados * 2);
        // buscar siguiente primo >= target
        while (!isPrime(target)) {
            target++;
        }
        return target;
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        int r = (int) Math.sqrt(n);
        for (int i = 3; i <= r; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean redimensionar() {
        int nuevaCapacidad = calcularCapacidadOptima(hashTable.length * 2);
        TNodoHash<K, V>[] old = hashTable;
        @SuppressWarnings("unchecked")
        TNodoHash<K, V>[] nueva = new TNodoHash[nuevaCapacidad];
        hashTable = nueva;

        for (TNodoHash<K, V> n : old) {
            if (n != null && !n.isLoteLibre()) {
                // reinsertar sin report
                K clave = n.getClave();
                V valor = n.getValor();
                int pos = functionHashing(clave);
                while (hashTable[pos] != null) {
                    pos = (pos + 1) % hashTable.length;
                }
                hashTable[pos] = new TNodoHashImpl<>(clave, valor);
            }
        }
        return true;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K, V>> list = new ArrayList<>();
        for (TNodoHash<K, V> n : hashTable) {
            if (n != null && !n.isLoteLibre()) {
                list.add(n.getEntry());
            }
        }
        return list;
    }

    @Override
    public Iterable<K> keys() {
        List<K> list = new ArrayList<>();
        for (TNodoHash<K, V> n : hashTable) {
            if (n != null && !n.isLoteLibre()) {
                list.add(n.getClave());
            }
        }
        return list;
    }

    @Override
    public Iterable<V> values() {
        List<V> list = new ArrayList<>();
        for (TNodoHash<K, V> n : hashTable) {
            if (n != null && !n.isLoteLibre()) {
                list.add(n.getValor());
            }
        }
        return list;
    }
}
