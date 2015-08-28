package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para emular la pila de ejecución. */
        private Pila<ArbolBinario<T>.Vertice> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador(ArbolBinario<T>.Vertice vertice) {
            // Aquí va su código.
	    this.pila = new Pila<>();
	    llenaPila(vertice);
        }

	/* Llena la pila del iterador in orden en sentido inverso. */
	private void llenaPila(ArbolBinario<T>.Vertice vertice){
	    if(vertice == null) return;
	    llenaPila(vertice.derecho);
	    pila.mete(vertice);
	    llenaPila(vertice.izquierdo);
	}

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return !pila.esVacia();
        }

        /* Regresa el siguiente elemento del árbol en orden. */
        @Override public T next() {
            // Aquí va su código.
	    return pila.saca().elemento;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Constructor sin parámetros. Sencillamente ejecuta el constructor sin
     * parámetros de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de un árbol binario. El
     * árbol binario ordenado tiene los mismos elementos que el árbol recibido,
     * pero ordenados.
     * @param arbol el árbol binario a partir del cuál creamos el
     *        árbol binario ordenado.
     */
    public ArbolBinarioOrdenado(ArbolBinario<T> arbol) {
        // Aquí va su código.
	Cola<Vertice> cola = new Cola<>();
	cola.mete(arbol.raiz);
	Vertice tmp;
	while(!cola.esVacia()){
	    tmp = cola.saca();
	    cola.mete(tmp.izquierdo);
	    cola.mete(tmp.derecho);
	    this.agrega(tmp.elemento);
	}
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	raiz = agrega(elemento, raiz);
    }

    /* Metodo auxiliar recursivo para agrega(). */
    private Vertice agrega(T elemento, Vertice vertice){
	if(vertice == null){
	    ultimoAgregado = vertice = nuevoVertice(elemento);
	    elementos++;
	}else if(elemento.compareTo(vertice.elemento) <= 0){
	    vertice.izquierdo = agrega(elemento, vertice.izquierdo);
	    if(vertice.izquierdo == ultimoAgregado)
		vertice.izquierdo.padre = vertice;
	}else{
	    vertice.derecho = agrega(elemento, vertice.derecho);
	    if(vertice.derecho == ultimoAgregado)
		vertice.derecho.padre = vertice;
	}
	return vertice;
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	raiz = elimina(elemento, raiz);
    }

    /* Metodo auxiliar para elimina(). */
    private Vertice elimina(T elemento, Vertice vertice){
	if(vertice == null) return vertice;
	if(elemento.compareTo(vertice.elemento) < 0)
	    vertice.izquierdo = elimina(elemento, vertice.izquierdo);
	else if(elemento.compareTo(vertice.elemento) > 0)
	    vertice.derecho = elimina(elemento, vertice.derecho);
	else if(vertice.izquierdo != null && vertice.derecho != null){
	    vertice.elemento = maximoEnSubarbol(vertice.izquierdo).elemento;
	    vertice.izquierdo = elimina(vertice.elemento, vertice.izquierdo);
	}else{
	    Vertice p = vertice.padre;
	    vertice = (vertice.izquierdo != null)? vertice.izquierdo :
		vertice.derecho;
	    if(vertice != null)
		vertice.padre = p;
	    elementos--;
	}
	return vertice;
    }

    /**
     * Nos dice si un elemento está contenido en el árbol.
     * @param elemento el elemento que queremos ver si está en el árbol.
     * @return <code>true</code> si el elemento está contenido en el árbol,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	return contiene(elemento, raiz);
    }

    /* Metodo auxiliar recursivo para contiene(). */
    private boolean contiene(T elemento, Vertice vertice){
	if(vertice == null) return false;
	if(elemento.compareTo(vertice.elemento) < 0)
	    return contiene(elemento, vertice.izquierdo);
	if(elemento.compareTo(vertice.elemento) > 0)
	    return contiene(elemento, vertice.derecho);
	return true;
    }
    
    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
	return busca(raiz, elemento);
    }

    /**
     * Busca recursivamente un elemento, a partir del vértice recibido.
     * @param vertice el vértice a partir del cuál comenzar la búsqueda. Puede
     *                ser <code>null</code>.
     * @param elemento el elemento a buscar a partir del vértice.
     * @return el vértice que contiene el elemento a buscar, si se encuentra en
     *         el árbol; <code>null</code> en otro caso.
     */
    @Override protected Vertice busca(Vertice vertice, T elemento) {
        // Aquí va su código.
	if(vertice == null) return null;
	if(elemento.compareTo(vertice.elemento) < 0)
	    return busca(vertice.izquierdo, elemento);
	if(elemento.compareTo(vertice.elemento) > 0)
	    return busca(vertice.derecho, elemento);
	return vertice;
    }

    /**
     * Regresa el vértice máximo en el subárbol cuya raíz es el vértice que
     * recibe.
     * @param vertice el vértice raíz del subárbol del que queremos encontrar el
     *                máximo.
     * @return el vértice máximo el subárbol cuya raíz es el vértice que recibe.
     */
    protected Vertice maximoEnSubarbol(Vertice vertice) {
        // Aquí va su código.
	Vertice tmp = vertice;
	while(tmp.derecho != null)
	    tmp = tmp.derecho;
	return tmp;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador(raiz);
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice);
        giraDerecha(v);
    }

    /* Gira el árbol a la derecha sobre el vértice recibido. */
    private void giraDerecha(Vertice vertice) {
        // Aquí va su código.
	if(vertice.izquierdo == null) return;
	Vertice padre = vertice.padre;
	Vertice izq = vertice.izquierdo;
	vertice.izquierdo = izq.derecho;
	if(izq.derecho != null)
	    izq.derecho.padre = vertice;
	izq.derecho = vertice;
	vertice.padre = izq;
	izq.padre = padre;
	if(padre == null)
	    raiz = vertice.padre;
	else if(padre.izquierdo == vertice)
	    padre.izquierdo = izq;
	else
	    padre.derecho = izq;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice);
        giraIzquierda(v);
    }

    /* Gira el árbol a la izquierda sobre el vértice recibido. */
    private void giraIzquierda(Vertice vertice) {
        // Aquí va su código.
	if(vertice.derecho == null) return;
	Vertice padre = vertice.padre;
	Vertice der = vertice.derecho;
	vertice.derecho = der.izquierdo;
	if(der.izquierdo != null)
	    der.izquierdo.padre = vertice;
	der.izquierdo = vertice;
	vertice.padre = der;
	der.padre = padre;
	if(padre == null){
	    raiz = vertice.padre;
	    return;
	}else if(padre.izquierdo == vertice)
	    padre.izquierdo = der;
	else
	    padre.derecho = der;
    }
}
