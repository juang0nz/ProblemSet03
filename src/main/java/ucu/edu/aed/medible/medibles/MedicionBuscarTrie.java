package ucu.edu.aed.medible.medibles;

import ucu.edu.aed.medible.lib.Medible;
import ucu.edu.aed.tda.trie.impl.TTrie;
import ucu.edu.aed.tda.trie.Entry;

import java.util.List;

public class MedicionBuscarTrie extends Medible<List<String>> {

    private final TTrie<String> trie;

    public MedicionBuscarTrie(TTrie<String> trie) {
        this.trie = trie;
    }

    @Override
    public void ejecutar(int repeticiones, List<String> palabras) {
        for (int i = 0; i < repeticiones; i++) {
            for (String palabra : palabras) {
                //noinspection ResultOfMethodCallIgnored
                Entry<String> e = trie.buscar(palabra);
                // No hacemos nada con e, solo medimos
            }
        }
    }

    @Override
    public Object getObjetoAMedirMemoria() {
        return this.trie;
    }
}
