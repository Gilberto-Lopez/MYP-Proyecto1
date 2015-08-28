package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho es al menos -1, y a lo más
 * 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends ArbolBinario<T>.Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
	    super(elemento);
	    altura = 0;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            // Aquí va su código.
	    if(!elemento.equals(vertice.elemento))
		return false;
	    if(altura != vertice.altura)
		return false;
	    if(vertice.izquierdo == null && vertice.derecho == null
	       && izquierdo == null && derecho == null)
		return true;
	    if(izquierdo != null && derecho == null)
		return izquierdo.equals(vertice.izquierdo);
	    if(izquierdo == null && derecho != null)
		return derecho.equals(vertice.derecho);
	    return izquierdo.equals(vertice.izquierdo)
		&& derecho.equals(vertice.derecho);
        }
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario. La complejidad en tiempo del método es <i>O</i>(log
     * <i>n</i>) garantizado.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	super.agrega(elemento);
	actualizaAltura(padre(ultimoAgregado));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo. La
     * complejidad en tiempo del método es <i>O</i>(log <i>n</i>) garantizado.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	VerticeAVL v = verticeAVL(busca(elemento));
	if(v == null) return;
	//Si tiene 2 hijos buscamos al máximo a la izquierda
	if(v.izquierdo != null && v.derecho != null){
	    Vertice m = maximoEnSubarbol(v.izquierdo);
	    v.elemento = m.elemento;
	    v = verticeAVL(m);
	}
	//Si no tiene hijos
	if(v.izquierdo == null && v.derecho == null){
	    if(v.padre == null)
		raiz = null;
	    else if(esIzquierdo(v))
		v.padre.izquierdo = null;//es necesario actualizar alturas
	    else
		v.padre.derecho = null;//es necesario actualizar alturas
	    actualizaAlturaB(padre(v));
	    elementos--;
	    return;
	}
	//Sólo tiene un hijo v, por ser AVL su hijo es un único vértice
	Vertice h = (v.izquierdo != null)? v.izquierdo : v.derecho;
	if(v.padre == null)
	    raiz = h;
	else if(esIzquierdo(v))
	    v.padre.izquierdo = h;//es necesario actualizar alturas
	else
	    v.padre.derecho = h;//es necesarrio actualizar alturas
	h.padre = v.padre;
	actualizaAlturaB(padre(v));
	elementos--;
    }
    
    /**
     * Regresa la altura del vértice AVL.
     * @param vertice el vértice del que queremos la altura.
     * @return la altura del vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    public int getAltura(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	return (vertice == null) ? -1 : verticeAVL(vertice).altura;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada. Una vez hecho el giro, las
     * alturas de los vértices se recalculan. Este método no debe ser llamado
     * por los usuarios de la clase; puede desbalancear el árbol.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	VerticeAVL v = verticeAVL(vertice);
	if(v.izquierdo == null) return;
	VerticeAVL padre = padre(v);
	VerticeAVL izq = izquierdo(v);
	v.izquierdo = izq.derecho;
	if(izq.derecho != null)
	    izq.derecho.padre = v;
	izq.derecho = v;
	v.padre = izq;
	izq.padre = padre;
	//Solo se actualizan las alturas locales, las alturas globales son
	//actualizadas por actualizaAltura() y actualizaAlturaB()
	v.altura = Math.max(getAltura(izquierdo(v)), getAltura(derecho(v))) + 1;
	izq.altura = Math.max(getAltura(izquierdo(izq)),
			      getAltura(derecho(izq))) + 1;
	if(padre == null)
	    raiz = v.padre;
	else if(padre.izquierdo == v)
	    padre.izquierdo = izq;
	else
	    padre.derecho = izq;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada. Una vez hecho el giro, las
     * alturas de los vértices se recalculan. Este método no debe ser llamado
     * por los usuarios de la clase; puede desbalancear el árbol.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	VerticeAVL v = verticeAVL(vertice);
	if(v.derecho == null) return;
	VerticeAVL padre = padre(v);
	VerticeAVL der = derecho(v);
	v.derecho = der.izquierdo;
	if(der.izquierdo != null)
	    der.izquierdo.padre = v;
	der.izquierdo = v;
	v.padre = der;
	der.padre = padre;
	//Solo se actualizan las alturas locales, las alturas globales son
	//actualizadas por actualizaAltura() y actualizaAlturaB()
	v.altura = Math.max(getAltura(izquierdo(v)), getAltura(derecho(v))) + 1;
	der.altura = Math.max(getAltura(izquierdo(der)),
			      getAltura(derecho(der))) + 1;
	if(padre == null)
	    raiz = v.padre;
	else if(padre.izquierdo == v)
	    padre.izquierdo = der;
	else
	    padre.derecho = der;
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
	return new VerticeAVL(elemento);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeAVL}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice AVL.
     * @return el vértice recibido visto como vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    protected VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        VerticeAVL v = (VerticeAVL)vertice;
        return v;
    }
    //--------------------------------------------------------------------------
    /* Métodos auxiliares. */

    /*
     * Método para rebalancear el árbol tras agregar o eliminar elementos.
     * El método no es recursivo, una vez que se realizan los giros necesarios
     * el árbol queda balanceado, las alturas se actualizan con los métodos
     * actualizaAltura() y actualizaAlturaB().
     */
    private void rebalancea(VerticeAVL v, int b){
	if(v == null) return;
	if(b == -2){
	    VerticeAVL d = derecho(v);
	    if(getAltura(izquierdo(d)) - getAltura(derecho(d)) == 1)
		giraDerecha(d);
	    giraIzquierda(v);
	}
	if(b == 2){
	    VerticeAVL i = izquierdo (v);
	    if(getAltura(izquierdo(i)) - getAltura(derecho(i)) == -1)
		giraIzquierda(i);
	    giraDerecha(v);
	}
	//No hay recursión para no recorrer la altura del árbol 2 veces
    }
    
    /*
     * Método que actualiza alturas para usarse después de agregar un nuevo
     * elemento al árbol. 
     * El método recorre el árbol desde ultimoAgregado hasta la raíz o hasta que
     * la altura no cambie, actualizando las alturas y verificando los balances
     * para rebalancear en caso de ser necesario. Esto evita tener que recorrer
     * la altura del árbol 2 veces, una para actualizar las alturas y otra para
     * rebalancear.
     */
    private void actualizaAltura(VerticeAVL v){
	if(v == null) return;
	int b = Math.max(getAltura(izquierdo(v)), getAltura(derecho(v))) + 1;
	//Si la altura no cambia es porque el otro subárbol era más grande
	//y por ser AVL está balanceado
	if(v.altura == b) return;
	v.altura = b;
	int a = getAltura(izquierdo(v)) - getAltura(derecho(v));
	if(a == 2 || a == -2)
	    rebalancea(v, a);
	//En el peor de los casos se actualizan las alturas hasta la raíz, pero
	//solo se recorre la altura del árbol 1 vez en cada llamada
	actualizaAltura(padre(v));
    }

    /*
     * Método que actualiza las alturas para usarse después de eliminar un
     * elemento del árbol.
     * El método recorre el árbol desde el padre del elemento eliminado hasta la
     * raíz, actualizando las alturas y verificando los balances para
     * rebalancear en caso de ser necesario. Esto evita tener que recorrer
     * la altura del árbol 2 veces, una para actualizar las alturas y otra para
     * rebalancear.
     */
    private void actualizaAlturaB(VerticeAVL v){
	if(v == null) return;
	int a = getAltura(izquierdo(v)) - getAltura(derecho(v));
	if(a == 2 || a == -2)
	    rebalancea(v, a);
	int b = Math.max(getAltura(izquierdo(v)), getAltura(derecho(v))) + 1;
	//No se detiene la recursión siempre se actualiza la altura hasta
	//la raíz, pero sólo se recorre la altura del árbol una vez en cada
	//llamada
	v.altura = b;
	actualizaAlturaB(padre(v));
    }

    private VerticeAVL izquierdo(Vertice v){
	return verticeAVL(v.izquierdo);
    }

    private VerticeAVL derecho(Vertice v){
	return verticeAVL(v.derecho);
    }

    private VerticeAVL padre(Vertice v){
	return verticeAVL(v.padre);
    }

    private boolean esIzquierdo(Vertice v){
	return (v.padre == null) ? false : v.padre.izquierdo == v;
    }

}
