package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, permitiendo (en general, dependiendo de qué tan bueno
 * sea su método para generar huellas digitales) agregar, eliminar, y buscar
 * valores en tiempo <i>O</i>(1) (amortizado) en cada uno de estos casos.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Clase privada para iteradores de diccionarios. */
    private class Iterador implements Iterator<V> {

        /* En qué lista estamos. */
        private int indice;
        /* Diccionario. */
        private Diccionario<K,V> diccionario;
        /* Iterador auxiliar. */
        private Iterator<Diccionario<K,V>.Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador(Diccionario<K,V> diccionario) {
            // Aquí va su código.
	    this.diccionario = diccionario;
	    while(indice < diccionario.entradas.length &&
		  diccionario.entradas[indice] == null)
		indice++;
	    if(indice < diccionario.entradas.length)
		iterador = diccionario.entradas[indice].iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        public boolean hasNext() {
            // Aquí va su código.
	    return iterador != null;
        }

        /* Regresa el siguiente elemento. */
        public V next() {
            // Aquí va su código.
	    if(iterador == null)
		throw new NoSuchElementException();
	    V r = iterador.next().valor;
	    if(iterador.hasNext())
		return r;
	    iterador = null;
	    indice++;
	    while(indice < diccionario.entradas.length &&
		  diccionario.entradas[indice] == null)
		indice++;
	    if(indice < diccionario.entradas.length)
		iterador = diccionario.entradas[indice].iterator();
	    return r;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* Tamaño mínimo; decidido arbitrariamente a 2^6. */
    private static final int MIN_N = 64;

    /* Máscara para no usar módulo. */
    private int mascara;
    /* Huella digital. */
    private HuellaDigital<K> huella;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores*/
    private int elementos;

    /* Clase para las entradas del diccionario. */
    private class Entrada {
        public K llave;
        public V valor;
        public Entrada(K llave, V valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private Lista<Entrada>[] nuevoArreglo(int n)
    {
        Lista[] arreglo = new Lista[n];
        return (Lista<Entrada>[])arreglo;
    }

    /**
     * Construye un diccionario con un tamaño inicial y huella digital
     * predeterminados.
     */
    public Diccionario() {
        // Aquí va su código.
	huella = o -> o.hashCode();
	mascara = mascara(MIN_N);
	entradas = nuevoArreglo(mascara + 1);
    }

    /**
     * Construye un diccionario con un tamaño inicial definido por el usuario, y
     * una huella digital predeterminada.
     * @param tam el tamaño a utilizar.
     */
    public Diccionario(int tam) {
        // Aquí va su código.
	huella = o -> o.hashCode();
	mascara = (tam > MIN_N) ? mascara(tam) : mascara(MIN_N);
	entradas = nuevoArreglo(mascara + 1);
    }

    /**
     * Construye un diccionario con un tamaño inicial predeterminado, y una
     * huella digital definida por el usuario.
     * @param huella la huella digital a utilizar.
     */
    public Diccionario(HuellaDigital<K> huella) {
        // Aquí va su código.
	this.huella = huella;
	mascara = mascara(MIN_N);
	entradas = nuevoArreglo(mascara + 1);
    }

    /**
     * Construye un diccionario con un tamaño inicial, y un método de huella
     * digital definidos por el usuario.
     * @param tam el tamaño del diccionario.
     * @param huella la huella digital a utilizar.
     */
    public Diccionario(int tam, HuellaDigital<K> huella) {
        // Aquí va su código.
	this.huella = huella;
	mascara = (tam > MIN_N) ? mascara(tam) : mascara(MIN_N);
	entradas = nuevoArreglo(mascara + 1);
    }
    
    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     */
    public void agrega(K llave, V valor) {
        // Aquí va su código.
	int indice = indice(llave);
	Lista<Entrada> lista = getLista(indice, true);
	Entrada entrada = buscaEntrada(llave, lista);
	if(entrada != null)
	    entrada.valor = valor;
	else{
	    entrada = new Entrada(llave, valor);
	    lista.agregaFinal(entrada);
	    elementos++;
	}
	if(carga() > MAXIMA_CARGA)
	    creceArreglo();
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        // Aquí va su código.
	int indice = indice(llave);
	Lista<Entrada> lista = getLista(indice, false);
	if(lista == null)
	    throw new NoSuchElementException();
	Entrada entrada = buscaEntrada(llave, lista);
	if(entrada == null)
	    throw new NoSuchElementException();
	return entrada.valor;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
	int indice = indice(llave);
	Lista<Entrada> lista = getLista(indice, false);
	if(lista == null)
	    return false;
	Entrada entrada = buscaEntrada(llave, lista);
	if(entrada == null)
	    return false;
	return true;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
	int indice = indice(llave);
	Lista<Entrada> lista = getLista(indice, false);
	if(lista == null)
	    throw new NoSuchElementException();
	Entrada entrada = buscaEntrada(llave, lista);
	if(entrada == null)
	    throw new NoSuchElementException();
	lista.elimina(entrada);
	if(lista.getLongitud() == 0)
	    entradas[indice] = null;
	elementos--;
    }

    /**
     * Regresa una lista con todas las llaves con valores asociados en el
     * diccionario. La lista no tiene ningún tipo de orden.
     * @return una lista con todas las llaves.
     */
    public Lista<K> llaves() {
        // Aquí va su código.
	Lista<K> llaves = new Lista<>();
	for(Lista<Entrada> l : entradas)
	    if(l != null)
		for(Entrada e : l)
		    llaves.agrega(e.llave);
	return llaves;
    }

    /**
     * Regresa una lista con todos los valores en el diccionario. La lista no
     * tiene ningún tipo de orden.
     * @return una lista con todos los valores.
     */
    public Lista<V> valores() {
        // Aquí va su código.
	Lista<V> valores = new Lista<>();
	for(Lista<Entrada> l : entradas)
	    if(l != null)
		for(Entrada e : l)
		    valores.agrega(e.valor);
	return valores;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
	int n = 0;
	for(Lista<Entrada> l : entradas)
	    if(l != null)
		if(l.getLongitud() > n)
		    n = l.getLongitud();
	return n - 1;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
	int n = 0;
	for(Lista<Entrada> l : entradas)
	    if(l != null)
		n += l.getLongitud() - 1;
	return n;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
	double e = elementos;
	double l = entradas.length;
	return e / l;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
	return elementos;
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar el diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new Iterador(this);
    }

    //--------------------------------------------------------------------------

    /* Calcula la máscara. */
    private int mascara(int n){
	int m = 1;
	while(m < n)
	    m = (m << 1) | 1;
	return (m << 1) | 1;
    }
    
    /* Regresa el índice dada la lleva. */
    private int indice(K llave){
	return huella.huellaDigital(llave) & mascara;
    }
    
    /* Regresa la lista correspondiente a la llave dada.
     * Si booleano es true y la lista null crea una lista. */
    private Lista<Entrada> getLista(int indice, boolean booleano){
	if(booleano && entradas[indice] == null)
	    entradas[indice] = new Lista<Entrada>();
	return entradas[indice];
    }

    /* Busca la entrada en la lista dada la llave. */
    private Entrada buscaEntrada(K llave, Lista<Entrada> lista){
	for(Entrada e : lista)
	    if(e.llave.equals(llave))
		return e;
	return null;
    }

    /* Crece el arreglo al doble de su tamaño, también cambia la máscara. */
    private void creceArreglo(){
	mascara = mascara(entradas.length);
	Lista<Entrada>[] arreglo = nuevoArreglo(mascara + 1);
	for(Lista<Entrada> l : entradas){
	    if(l != null)
		for(Entrada e : l){
		    int indice = huella.huellaDigital(e.llave) & mascara;
		    if(arreglo[indice] == null)
			arreglo[indice] = new Lista<Entrada>();
		    arreglo[indice].agrega(e);
		}
	}
	entradas = arreglo;
    }
}
