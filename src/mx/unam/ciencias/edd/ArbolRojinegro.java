package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros son autobalanceados, y por lo tanto las operaciones de
 * inserción, eliminación y búsqueda pueden realizarse en <i>O</i>(log
 * <i>n</i>).
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends ArbolBinario<T>.Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeRojinegro vertice = (VerticeRojinegro)o;
            return color == vertice.color && super.equals(o);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeRojinegro}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice
     *                rojinegro.
     * @return el vértice recibido visto como vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos la altura.
     * @return la altura del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = verticeRojinegro(vertice);
        return v.color;
    }
    
    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	super.agrega(elemento);
	verticeRojinegro(ultimoAgregado).color = Color.ROJO;
	rebalanceaAgregar(verticeRojinegro(ultimoAgregado));
    }
    
    private void rebalanceaAgregar(VerticeRojinegro v){
	/* Caso 1: padre nulo. */
	if(v.padre == null){
	    v.color = Color.NEGRO;
	    return;
	}
	/* Caso 2: el padre es negro. */
	if(esNegro(padre(v))) return;
	/* Caso 3: si el tio existe y es rojo. */
	if(hayTio(v) && !esNegro(getTio(v))){
	    padre(v).color = Color.NEGRO;
	    getTio(v).color = Color.NEGRO;
	    abuelo(v).color = Color.ROJO;
	    rebalanceaAgregar(abuelo(v));
	    return;
	}
	/* Caso 4.1: padre es izquierdo y vertice derecho. */
	if(esIzquierdo(v.padre) && !esIzquierdo(v)){
	    giraIzquierda(v.padre);
	    v = izquierdo(v);
	}
	/* Caso 4.2: padre es derecho y vertice izquierdo. */
	if(!esIzquierdo(v.padre) && esIzquierdo(v)){
	    giraDerecha(v.padre);
	    v = derecho(v);
	}
	/* Caso 5: padre y vertice izquierdos o derechos. */
	padre(v).color = Color.NEGRO;
	abuelo(v).color = Color.ROJO;
	if(esIzquierdo(v))
	    giraDerecha(v.padre.padre);
	else
	    giraIzquierda(v.padre.padre);
    }
    
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	/* Buscamos el elemento a eliminar, si no esta en el arbol acabamos. */
	VerticeRojinegro v = verticeRojinegro(busca(elemento));
	if(v == null) return;
	/* Si v tiene hijo izquierdo, u sera el maximo en el subarbol
	 * izquierdo, intercambiamos sus elementos y v = u. */
	if(v.izquierdo != null){
	    VerticeRojinegro u = verticeRojinegro(maximoEnSubarbol(v.izquierdo));
	    v.elemento = u.elemento;
	    v = u;
	}
	/* Si v no tiene hijos, le agregamos un hijo fantasma. */
	if(v.izquierdo == null && v.derecho == null){
	    v.izquierdo = fantasma();
	}
	/* Eliminamos a v subiendo a su unico hijo h. */
	VerticeRojinegro h = subeHijo(v);
	elementos--;
	/* Si h es rojo lo coloreamos de negro y terminamos. */
	if(!esNegro(h)){
	    h.color = Color.NEGRO;
	    return;
	}
	/* Si v y h son negros rebalanceamos sobre h; */
	if(esNegro(v) && esNegro(h))
	    rebalanceaEliminar(h);
	/* Eliminamos al vertice fantasma si existe. */
	if(esFantasma(h))
	    if(h.padre == null)
		raiz = null;
	    else if(esIzquierdo(h))
		h.padre.izquierdo = null;
	    else
		h.padre.derecho = null;
    }

    private void rebalanceaEliminar(VerticeRojinegro v){
	/* Caso 1: el padre es nulo. */
	if(v.padre == null) return;
	/* Caso 2: si hay hermano y es rojo. */
	if(hayHermano(v) && !esNegro(getHermano(v))){
	    padre(v).color = Color.ROJO;
	    getHermano(v).color = Color.NEGRO;
	    if(esIzquierdo(v))
		giraIzquierda(v.padre);
	    else
		giraDerecha(v.padre);
	}
	/* Caso 3: padre, hermano y sobrinos negros. */
	if(esNegro(padre(v)) && esNegro(getHermano(v)) &&
	   esNegro(izquierdo(getHermano(v))) &&
	   esNegro(derecho(getHermano(v)))){
	    getHermano(v).color = Color.ROJO;
	    rebalanceaEliminar(padre(v));
	    return;
	}    
	/* Caso 4: padre rojo, hermano y sobrinos negros. */
	if(!esNegro(padre(v)) && esNegro(getHermano(v)) &&
	   esNegro(izquierdo(getHermano(v))) &&
	   esNegro(derecho(getHermano(v)))){
	    padre(v).color = Color.NEGRO;
	    getHermano(v).color = Color.ROJO;
	    return;
	}
	/* Caso 5.1: vertice es izquierdo, sobrino derecho negro e izquierdo
	 * rojo. */
	if(esIzquierdo(v) && esNegro(derecho(getHermano(v))) &&
	   !esNegro(izquierdo(getHermano(v)))){
	    getHermano(v).color = Color.ROJO;
	    izquierdo(getHermano(v)).color = Color.NEGRO;
	    giraDerecha(getHermano(v));
	}
	/* Caso 5.2: vertice es derecho, sobrino izquierdo negro y derecho
	 * rojo. */
	if(!esIzquierdo(v) && !esNegro(derecho(getHermano(v)))
	   && esNegro(izquierdo(getHermano(v)))){
	    getHermano(v).color = Color.ROJO;
	    derecho(getHermano(v)).color = Color.NEGRO;
	    giraIzquierda(getHermano(v));
	}
	getHermano(v).color = padre(v).color;	
	padre(v).color = Color.NEGRO;
	/* Caso 6.1: vertice es izquierdo, sorbino izquierdo negro y derecho
	 * rojo. */
	if(esIzquierdo(v)){
	    derecho(getHermano(v)).color = Color.NEGRO;
	    giraIzquierda(v.padre);
	}
	/* Caso 6.2: vertice es derecho, sorbino derecho negro e izquierdo
	 * rojo. */
	else{
	    izquierdo(getHermano(v)).color = Color.NEGRO;
	    giraDerecha(v.padre);
	}
    }
    
    //--------------------------------------------------------------------------
    /* Metodos auxiliares. */
    
    private boolean hayTio(Vertice v){
	if(v.padre == null || v.padre.padre == null) return false;
	Vertice abuelo = v.padre.padre;
	if(abuelo.izquierdo == v.padre && abuelo.derecho != null)
	    return true;
	if(abuelo.derecho == v.padre && abuelo.izquierdo != null)
	    return true;
	return false;
    }

    private VerticeRojinegro getTio(Vertice v){
	Vertice abuelo = v.padre.padre;
	return (abuelo.izquierdo == v.padre)? derecho(abuelo) : izquierdo(abuelo);
    }
    
    private boolean esNegro(VerticeRojinegro v){
	return v == null || v.color == Color.NEGRO;
    }

    private boolean esIzquierdo(Vertice v){
	return (v.padre == null) ? false : v.padre.izquierdo == v;
    }

    private boolean hayHermano(Vertice v){
	if(v.padre == null) return false;
	Vertice padre = v.padre;
	if(padre.izquierdo == v && padre.derecho != null)
	    return true;
	if(padre.derecho == v && padre.izquierdo != null)
	    return true;
	return false;
    }

    private VerticeRojinegro getHermano(Vertice v){
	Vertice padre = v.padre;
	return (padre.izquierdo == v)? derecho(padre) : izquierdo(padre);
    }

    private VerticeRojinegro padre(Vertice v){
	return verticeRojinegro(v.padre);
    }

    private VerticeRojinegro abuelo(Vertice v){
	return verticeRojinegro(v.padre.padre);
    }

    private VerticeRojinegro izquierdo(Vertice v){
	return verticeRojinegro(v.izquierdo);
    }

    private VerticeRojinegro derecho(Vertice v){
	return verticeRojinegro(v.derecho);
    }

    private VerticeRojinegro fantasma(){
	VerticeRojinegro f = verticeRojinegro(nuevoVertice(null));
	f.color = Color.NEGRO;
	return f;
    }

    private boolean esFantasma(Vertice v){
	return v.elemento == null;
    }
    
    private VerticeRojinegro subeHijo(Vertice v){
	Vertice h = v;
	if(v.izquierdo != null){
	    v.izquierdo.padre = v.padre;
	    if(v.padre == null)
		raiz = v.izquierdo;
	    else{
		if(esIzquierdo(v))
		    v.padre.izquierdo = v.izquierdo;
		else
		    v.padre.derecho = v.izquierdo;
	    }
	    h = v.izquierdo;
	}else{
	    v.derecho.padre = v.padre;
	    if(v.padre == null)
		raiz = v.derecho;
	    else{
		if(esIzquierdo(v))
		    v.padre.izquierdo = v.derecho;
		else
		    v.padre.derecho = v.derecho;
	    }
	    h = v.derecho;
	}
	return verticeRojinegro(h);
    }

}
