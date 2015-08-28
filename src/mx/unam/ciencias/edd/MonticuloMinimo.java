package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>). Podemos crear un montículo
 * mínimo con <em>n</em> elementos en tiempo <em>O</em>(<em>n</em>), y podemos
 * agregar y actualizar elementos en tiempo <em>O</em>(log <em>n</em>). Eliminar
 * el elemento mínimo también nos toma tiempo <em>O</em>(log <em>n</em>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T> {

    /* Clase privada para iteradores de montículos. */
    private class Iterador<T extends ComparableIndexable<T>>
        implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;
        /* El montículo mínimo. */
        private MonticuloMinimo<T> monticulo;

        /* Construye un nuevo iterador, auxiliándose del montículo mínimo. */
        public Iterador(MonticuloMinimo<T> monticulo) {
            // Aquí va su código.
	    this.monticulo = monticulo;
	    indice = 0;
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return indice < monticulo.siguiente;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
	    return monticulo.arbol[indice++];
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* El siguiente índice dónde agregar un elemento. */
    private int siguiente;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] creaArregloGenerico(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Lista)}, pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
	arbol = creaArregloGenerico(64);
    }

    /**
     * Constructor para montículo mínimo que recibe una lista. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param lista la lista a partir de la cuál queremos construir el
     *              montículo.
     */
    public MonticuloMinimo(Lista<T> lista) {
        // Aquí va su código.
	if(lista == null || lista.getLongitud() == 0)
	    arbol = creaArregloGenerico(1);
	else{
	    arbol = creaArregloGenerico(lista.getLongitud());
	    int i = 0;
	    for(T elemento : lista)
		arbol[i++] = elemento;
	    siguiente = arbol.length;
	    for(int j = arbol.length/2; j >= 0; j--)
		minHeapify(j);
	    i = 0;
	    for(T elemento : arbol)
		elemento.setIndice(i++);
	}
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if(siguiente == arbol.length){
	    T[] nuevoArbol = creaArregloGenerico(arbol.length * 2);
	    int i = 0;
	    for(T e : arbol)
		nuevoArbol[i++] = e;
	    arbol = nuevoArbol;
	}
	elemento.setIndice(siguiente);
	arbol[siguiente] = elemento;
	reordena(siguiente++);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    public T elimina() {
        // Aquí va su código.
	if(siguiente == 0)
	    throw new IllegalStateException();
	T elemento = arbol[0];
	intercambia(0, --siguiente);
	arbol[siguiente] = null;
	minHeapify(0);
	elemento.setIndice(-1);
	return elemento;
    }
    
    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	int i = elemento.getIndice();
	if(i == 0)
	    elimina();
	else{
	    int hijo = i*2 + 1;
	    while(hijo < siguiente){
		if(hijo + 1 < siguiente &&
		   arbol[hijo + 1].compareTo(arbol[hijo]) < 0)
		    hijo++;
		intercambia(i, hijo);
		i = hijo;
		hijo = i*2 + 1;
	    }
	    intercambia(i, --siguiente);
	    arbol[siguiente] = null;
	    elemento.setIndice(-1);
	    if(i != siguiente)
		reordena(i);
	}
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	int i = elemento.getIndice();
	if(i < 0 || i >= siguiente || !arbol[i].equals(elemento))
	    return false;
	return true;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacio() {
        // Aquí va su código.
	return siguiente == 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    public void reordena(T elemento) {
        // Aquí va su código.
	reordena(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return siguiente;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    public T get(int i) {
        // Aquí va su código.
	if(i < 0 || i >= siguiente)
	    throw new NoSuchElementException();
	return arbol[i];
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador<T>(this);
    }

    //--------------------------------------------------------------------------
    /* Métodos auxiliares. */
    
    /* Método auxiliar para intercambiar 2 elementos del montículo, además
     * de actualizar los índices. */
    private void intercambia(int i, int j){
	T elementoI = arbol[i];
	arbol[i] = arbol[j];
	arbol[j] = elementoI;
	arbol[i].setIndice(i);
	arbol[j].setIndice(j);
    }

    /* Método auxiliar para ordenar un elemento en el montículo después de 
     * haber eliminado un elemento. */
    private void minHeapify(int i){
	int hijo = i*2 +1;
	if(hijo < siguiente){
	    if(hijo +1 < siguiente &&
	       arbol[hijo + 1].compareTo(arbol[hijo]) < 0)
		hijo++;
	    if(arbol[hijo].compareTo(arbol[i]) < 0){
		intercambia(i, hijo);
		minHeapify(hijo);
	    }
	}
    }

    /* Método auxiliar para ordenar un elemento en el montículo después de 
     * haberlo agregado. */
    private void reordena(int i){
	int padre = (i-1)/2;
	if(padre >= 0 && arbol[i].compareTo(arbol[padre]) < 0){
	    intercambia(i, padre);
	    reordena(padre);
	}
    }
    
}
