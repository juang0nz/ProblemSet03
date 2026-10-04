package ucu.edu.aed.tda.hash.impl; // Tu paquete actual (la carpeta impl)

// ¡ESTO ERA LO QUE FALTABA! Traer las clases de la carpeta anterior
import ucu.edu.aed.tda.hash.Entry;
import ucu.edu.aed.tda.hash.Report;
import ucu.edu.aed.tda.hash.THash;
import ucu.edu.aed.tda.hash.TNodoHash;

import java.util.ArrayList;
import java.util.List;

public class THashImpl<K, V> extends THash<K, V> {

    public THashImpl(int elementosEsperados) {
        super(elementosEsperados); 
    }

    @Override
    protected int calcularCapacidadOptima(int elementosEsperados) {
        return (int) (elementosEsperados / 0.9) + 1;
    }

    @Override
    protected int functionHashing(K clave) {
        int hash = clave.hashCode();
        return (hash & 0x7fffffff) % hashTable.length;
    }

    @Override
    public boolean insertar(K clave, V valor, Report report) {
        int h0 = functionHashing(clave);
        int m = hashTable.length;
        int comparaciones = 0;
        int primeraPosLibre = -1; 

        for (int i = 0; i < m; i++) {
            int pos = (h0 + i) % m;
            comparaciones++;
            
            TNodoHash<K, V> nodo = hashTable[pos];
            
            if (nodo == null) {
                if (primeraPosLibre == -1) {
                    primeraPosLibre = pos;
                }
                break;
            } else if (nodo.isLoteLibre()) {
                if (primeraPosLibre == -1) {
                    primeraPosLibre = pos;
                }
            } else if (nodo.getClave().equals(clave)) {
                report.setCantidadComparaciones(comparaciones);
                return false; 
            }
        }
        
        if (primeraPosLibre != -1) {
            hashTable[primeraPosLibre] = new TNodoHash<>(clave, valor);
            report.setCantidadComparaciones(comparaciones);
            return true;
        }
        
        report.setCantidadComparaciones(comparaciones);
        return false;
    }

    @Override
    public V buscar(K clave, Report report) {
        int h0 = functionHashing(clave);
        int m = hashTable.length;
        int comparaciones = 0;

        for (int i = 0; i < m; i++) {
            int pos = (h0 + i) % m;
            comparaciones++;
            
            TNodoHash<K, V> nodo = hashTable[pos];
            
            if (nodo == null) {
                break; 
            }
            
            if (!nodo.isLoteLibre() && nodo.getClave().equals(clave)) {
                report.setCantidadComparaciones(comparaciones);
                return nodo.getValor();
            }
        }
        
        report.setCantidadComparaciones(comparaciones);
        return null;
    }

    @Override
    public boolean delete(K clave, Report report) {
        int h0 = functionHashing(clave);
        int m = hashTable.length;
        int comparaciones = 0;

        for (int i = 0; i < m; i++) {
            int pos = (h0 + i) % m;
            comparaciones++;
            
            TNodoHash<K, V> nodo = hashTable[pos];
            
            if (nodo == null) {
                break; 
            }
            
            if (!nodo.isLoteLibre() && nodo.getClave().equals(clave)) {
                nodo.setLoteLibre(true);
                report.setCantidadComparaciones(comparaciones);
                return true;
            }
        }
        
        report.setCantidadComparaciones(comparaciones);
        return false;
    }

    @Override
    public boolean esVacio() {
        for (TNodoHash<K, V> nodo : hashTable) {
            if (nodo != null && !nodo.isLoteLibre()) {
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
    protected boolean redimensionar() {
        return false; 
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K, V>> list = new ArrayList<>();
        for (TNodoHash<K, V> nodo : hashTable) {
            if (nodo != null && !nodo.isLoteLibre()) {
                list.add(nodo.getEntry());
            }
        }
        return list;
    }

    @Override
    public Iterable<K> keys() {
        List<K> list = new ArrayList<>();
        for (TNodoHash<K, V> nodo : hashTable) {
            if (nodo != null && !nodo.isLoteLibre()) {
                list.add(nodo.getClave());
            }
        }
        return list;
    }

    @Override
    public Iterable<V> values() {
        List<V> list = new ArrayList<>();
        for (TNodoHash<K, V> nodo : hashTable) {
            if (nodo != null && !nodo.isLoteLibre()) {
                list.add(nodo.getValor());
            }
        }
        return list;
    }
}