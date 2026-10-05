package ucu.edu.aed.ejercicios.ejercicio5;

/**
 * =====================================================================
 * EJERCICIO 5: OPERACIONES DE TRIE Y NODOTRIE
 * =====================================================================
 *
 * Cada nodo del Trie (TNodoTrie) tiene un arreglo de 26 hijos (uno por
 * letra a..z), un booleano esPalabra y un dato T. Para cada operación se
 * muestra el método del Trie (delega en la raíz) y el del NodoTrie.
 *
 *
 * --- 1. BUSCAR PALABRA COMPLETA ---
 *
 * Se recorre el trie letra por letra desde la raíz, bajando por el hijo
 * que corresponde a cada carácter. Si falta algún hijo en el camino, la
 * palabra no existe. Si se llega al final de la palabra, existe como
 * entrada completa solo si ese nodo está marcado esPalabra.
 *
 * Pre: palabra no es null.
 * Post: no modifica el trie. Devuelve el Entry de la palabra si existe
 * (esPalabra = true) o null/esPalabra=false si no existe.
 *
 *   función Trie.buscar(palabra) -> Entry<T>
 *       retornar raiz.buscar(palabra)
 *
 *   función NodoTrie.buscar(palabra) -> Entry<T>
 *       actual <- este
 *       para cada caracter de palabra hacer
 *           posicion <- caracter - 'a'
 *           si actual.hijos[posicion] == nulo entonces retornar nulo
 *           actual <- actual.hijos[posicion]
 *       retornar Entry(actual.dato, actual.esPalabra, palabra)
 *
 * Orden: O(L), con L = longitud de la palabra. No depende de cuántas
 * palabras tenga el trie.
 *
 *
 * --- 2. OBTENER LISTA DE PALABRAS POR UN PREFIJO (predecir) ---
 *
 * Primero se recorre el trie usando los caracteres del prefijo. Si no
 * existe alguno de ellos, significa que no hay palabras que empiecen
 * con ese prefijo. Si el prefijo existe, se llega a su nodo y desde ahí
 * se recorren todos sus descendientes (DFS), agregando a la lista cada
 * palabra que tenga un nodo marcado como esPalabra.
 *
 * Pre: prefijo no es null (puede ser vacío, y ahí trae todas las palabras).
 * Post: no modifica el trie. Devuelve la lista de palabras (puede ser
 * vacía) que empiezan con el prefijo.
 *
 *   función Trie.predecir(prefijo) -> Lista<Entry<T>>
 *       retornar raiz.predecir(prefijo)
 *
 *   función NodoTrie.predecir(prefijo) -> Lista<Entry<T>>
 *       actual <- este
 *       para cada caracter de prefijo hacer
 *           posicion <- caracter - 'a'
 *           si actual.hijos[posicion] == nulo entonces retornar lista vacía
 *           actual <- actual.hijos[posicion]
 *       retornar recorrer(actual, prefijo)
 *
 *   función recorrer(nodo, palabraActual) -> Lista<Entry<T>>
 *       resultado <- lista vacía
 *       si nodo.esPalabra entonces
 *           resultado.agregar(Entry(nodo.dato, verdadero, palabraActual))
 *       para cada hijo no nulo de nodo (en orden a..z) hacer
 *           resultado.agregarTodo(recorrer(hijo, palabraActual + letra))
 *       retornar resultado
 *
 * Orden: O(P + N), con P = longitud del prefijo y N = cantidad de nodos
 * del subárbol que comienza en el nodo del prefijo.
 *
 *
 * --- 3. INSERTAR UNA PALABRA CON UN DATO ASOCIADO ---
 *
 * Se recorre el trie letra por letra desde la raíz, creando los nodos
 * hijos que falten. Al terminar de recorrer la palabra, si el nodo final
 * ya era esPalabra, la palabra ya existía y no se inserta. Si no, se
 * marca esPalabra y se guarda el dato.
 *
 * Pre: palabra no es null.
 * Post: si la palabra no existía, queda insertada con su dato y se
 * devuelve true. Si ya existía, no cambia nada y se devuelve false.
 *
 *   función Trie.insertar(palabra, dato) -> boolean
 *       retornar raiz.insertar(palabra, dato)
 *
 *   función NodoTrie.insertar(palabra, dato) -> boolean
 *       actual <- este
 *       para cada caracter de palabra hacer
 *           posicion <- caracter - 'a'
 *           si actual.hijos[posicion] == nulo entonces
 *               actual.hijos[posicion] <- nuevo NodoTrie()
 *           actual <- actual.hijos[posicion]
 *       si actual.esPalabra entonces retornar falso
 *       actual.esPalabra <- verdadero
 *       actual.dato <- dato
 *       retornar verdadero
 *
 * Orden: O(L), con L = longitud de la palabra.
 *
 *
 * --- 4. ELIMINAR UNA PALABRA DEL TRIE ---
 *
 * Se busca el nodo final de la palabra igual que en buscar. Si no está
 * marcado como esPalabra, significa que la palabra no estaba almacenada.
 * Si lo está, se desmarca esPalabra y se elimina el dato asociado.
 *
 * Después se recorre nuevamente el camino hacia atrás. Si algún nodo
 * quedó sin hijos y tampoco corresponde al final de otra palabra, se
 * elimina la referencia que su padre tiene hacia él, ya que ese nodo
 * ya no es necesario.
 *
 * Pre: palabra no es null.
 * Post: si la palabra existía, queda desmarcada y se podan los nodos que
 * quedaron sin uso; se devuelve true. Si no existía, no cambia nada y se
 * devuelve false.
 *
 *   función Trie.eliminar(palabra) -> boolean
 *       retornar raiz.eliminar(palabra, 0)
 *
 *   función NodoTrie.eliminar(palabra, indice) -> boolean
 *       si indice == longitud(palabra) entonces
 *           si NO este.esPalabra entonces retornar falso
 *           este.esPalabra <- falso
 *           este.dato <- nulo
 *           retornar verdadero
 *       posicion <- palabra[indice] - 'a'
 *       hijo <- este.hijos[posicion]
 *       si hijo == nulo entonces retornar falso
 *       existia <- hijo.eliminar(palabra, indice + 1)
 *       si existia Y hijo no tiene hijos Y NO hijo.esPalabra entonces
 *           este.hijos[posicion] <- nulo       // eliminamos el nodo que ya no se necesita
 *       retornar existia
 *
 * Orden: O(L), con L = longitud de la palabra (se baja y se vuelve por
 * el mismo camino de L nodos).
 * =====================================================================
 */
public final class Ejercicio5 {
    private Ejercicio5() {
    }
}
