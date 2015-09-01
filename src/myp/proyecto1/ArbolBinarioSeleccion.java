package myp.proyecto1;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.uanm.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import java.util.Iterator;

/**
 * <p>Clase para árboles binarios por selección. Los árboles son genéricos.</p>
 * 
 * <p>El usuario de esta clase puede manejar los árboles con mayor libertad,
 * pues queda a decisión del usuario la estructura del árbol en su mayoría, esto
 * es, el usuario decide si al agregar un elemento, éste será hijo izquierdo o
 * hijo derecho de algún vértice del árbol, además de escoger el vértice, de
 * ahí su nombre "por selección".</p>
 */
public class ArbolBinarioSeleccion<T> extends ArbolBinario<T>{

    /* Clase privada para iteradores de árboles binarios por selección. */
    private class Iterador implements Iterator<T>{

	/* Pila para emular la pila de ejecución. */
	private Pila<ArbolBinario<T>.Vertice> pila;

	/* Construye un iterador con el vértice recibido. */
	public Iterador(ArbolBinario<T>.Vertice vertice){
	    pila = new Pila<ArbolBinario<T>.Vertice>();
	    llenaPila(vertice);
	}

	/* Llena la pila del iterador in orden en sentido inverso. */
	private void llenaPila(ArbolBinario<T>.Vertice vertice){
	    if(vertice == null)
		return;
	    llenaPila(vertice.derecho);
	    pila.mete(vertice);
	    llenaPila(vertice.izquierdo);
	}

	/* Nos dice si hay un siguiente elemento. */
	@Override public boolean hasNext(){
	    return !pila.esVacia();
	}

	/* Regresa el siguiente elemento del árbol en orden. */
	@Override public T next(){
	    return pila.saca().elemento;
	}

	/* No lo implementamos: siempre lanza una excepción. */
	@Override public void remove(){
	    throw new UnsupportedOperationException();
	}
	
    }

    /**
     * Constructor sin parámetros. Sencillamente ejecuta el constructor sin
     * parámetros de {@link ArbolBinario}.
     */
    public ArbolBinarioSeleccion(){
	super();
    }

    /**
     * Agrega un elemento al árbol.
     * @deprecated Su uso debe ser evitado pues el método no permite agregar
     *             elementos por selección, restando libertad al usuario en el
     *             uso de instancias de esta clase. Actualmente el método no
     *             está implementado, su uso provocará el lanzamiento de una
     *             excepción {@link UnsupportedOperationException}, en su lugar
     *             use los métodos {@link #agregaIzquierda} y
     *             {@link #agregaDerecha} para agregar elementos al árbol.
     */
    @Deprecated
    @Override public void agrega(T elemento){
	throw new UnsupportedOperationException();
    }

    /**
     * Agrega el primer elemento al árbol, creando la raíz del árbol. Este
     * método debe llamarse antes de agregar elementos mediante
     * {@link #agregaIzquierda} y {@link #agregaDerecha} para evitar errores en
     * tiempo de ejecución, pues dichos métodos requieren de un vertice
     * existente para agregar nuevos elementos. El método regresa un booleano
     * representado si la operación se ralizó con éxito o fracasó. El método
     * puede fracasar si la raíz ya existía.
     * @param elemento El elemento a colocar en la raíz.
     * @return <code>true</code> si el elemento se agregó como raíz del árbol,
     *         <code>false</code> si la raíz ya existía.
     */
    public boolean creaRaiz(T elemento){
	if(raiz != null)
	    return false;
	raiz = ultimoAgregado = nuevoVertice(elemento);
	elementos++;
	return true;
    }

    /**
     * Agrega un elemento al árbol, dado un vértice a su izquierda. El método
     * regresa un booleano representando si la operación se realizó con éxito o
     * fracasó. El método puede fracasar si el vértice <code>vertice</code> ya
     * tenía un hijo izquierdo.
     * @param elemento El elemento a agregar.
     * @param vertice El vértice al cual se agregará el nuevo elemento a su
     *        izquierda.
     * @return <code>true</code> si el elemento se agregó como hijo izquierdo de
     *         <code>vertice</code>, <code>false</code> si <code>vertice</code>
     *         ya tenía hijo izquierdo.
     */
    public boolean agregaIzquierda(T elemento, VerticeArbolBinario<T> vertice){
	Vertice v = vertice(vertice);
	if(v.izquierdo != null)
	    return false;
	ultimoAgregado = v.izquierdo = nuevoVertice(elemento);
	v.izquerdo.padre = v.izquierdo;
	elementos++;
	return true;
    }
    
    /**
     * Agrega un elemento al árbol, dado un vértice a su derecha. El método
     * regresa un booleano representando si la operación se realizó con éxito o
     * fracasó. El método puede fracasar si el vértice <code>vertice</code> ya
     * tenía un hijo derecho.
     * @param elemento El elemento a agregar.
     * @param vertice El vértice al cual se agregará el nuevo elemento a su
     *        derecha.
     * @return <code>true</code> si el elemento se agregó como hijo derecho de
     *         <code>vertice</code>, <code>false</code> si <code>vertice</code>
     *         ya tenía hijo derecho.
     */
    public boolean agregaDerecha(T elemento, VerticeArbolBinario<T> vertice){
	Vertice v = vertice(vertice);
	if(v.derecho != null)
	    return false;
	ultimoAgregado = v.derecho = nuevoVertice(elemento);
	v.derecho.padre = v.derecho;
	elementos++;
	return true;
    }
    
    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre incluyendo todo el
     * subárbol debajo del vértice.
     * @param elemento El elemento a eliminar.
     */
    @Override public void elimina(T elemento){
	Vertice tmp;
	Cola<Vertice> cola = new Cola<>();
	cola.mete(raiz);
	while(!cola.esVacia()){
	    tmp = cola.saca();
	    if(elemento.equals(tmp.elemento)){
		elimina(tmp);
		return;
	    }
	    cola.mete(tmp.izquierdo);
	    cola.mete(tmp.derecho);
	}
    }

    /* Método auxiliar para elimina(). */
    private void elimina(Vertice vertice){
	if(vertice == null)
	    return;
	if(raiz == vertice){
	    raiz = ultimoAgregado = null;
	    elementos = 0;
	}else{
	    int elems = cuentaElementos(vertice);
	    if(esIzquierdo(vertice))
		vertice.padre.izquierdo = null;
	    else
		vertice.padre.derecho = null;
	    elementos -= elems;
	    ultimoAgregado = null;
	}
    }

    /**
     * Nos dice si un elemento está contenido en el árbol.
     * @param elemento El elemento que queremos ver si está en el árbol.
     * @return <code>true</code> si el elemento está contenido en el árbol,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento){
	return contiene(elemento, raiz);
    }

    /* Método auxiliar recursivo para contiene(). */
    private boolean contiene(T elemento, Vertice vertice){
	if(vertice == null)
	    return false;
	if(elemento.equals(vertice.elemento))
	    return true;
	boolean bool = contiene(elemento, vertice.izquierdo);
	if(!bool)
	    bool = contiene(elemento, vertice.derecho);
	return bool;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return Un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator(){
	return new Iterador(raiz);
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice El vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice){
	Vertice v = vertice(vertice);
	giraDerecha(v);
    }

    /* Gira el árbol a la derecha sobre el vértice recibido. */
    private void giraDerecha(Vertice vertice){
	if(vertice.izquierdo == null)
	    return;
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
     * @param vertice El vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice){
	Vertice v = vertice(vertice);
	giraIzquierda(v);
    }

    /* Gira el árbol a la izquierda sobre el vértice recibido. */
    private void giraIzquierda(Vertice vertice){
	if(vertice.derecho == null)
	    return;
	Vertice padre = vertice.padre;
	Vertice der = vertice.derecho;
	vertice.derecho = der.izquierdo;
	if(der.izquierdo != null)
	    der.izquierdo.padre = vertice;
	der.izquierdo = vertice;
	vertice.padre = der;
	der.padre = padre;
	if(padre == null)
	    raiz = vertice.padre;
	else if(padre.izquierdo == vertice)
	    padre.izquierdo = der;
	else
	    padre.derecho = der;
    }

    /* Nos dice si un vértice es hijo izquierdo. */
    private boolean esIzquierdo(Vertice v){
	if(v.padre == null)
	    return false;
	return v.padre.izquierdo == v;
    }

    /**
     * Cuenta la cantidad de elementos de un subárbol.
     * @param vertice Vértice a partir del cual contaremos los elementos de su
     *        subárbol.
     * @return El número de elementos en el subárbol.
     */
    public int contarElementos(VerticeArbolBinario<T> vertice){
	Vertice v = vertice(vertice);
	int elementos = 0;
	Cola<Vertice> cola = new Cola<>();
	cola.mete(v);
	while(!cola.esVacia()){
	    Vertice tmp = cola.saca();
	    cola.mete(tmp.izquierdo);
	    cola.mete(tmp.derecho);
	    elementos++;
	}
	return elementos;
    }
}
