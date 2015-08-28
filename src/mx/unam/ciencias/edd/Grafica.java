package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase privada para iteradores de gráficas. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Grafica<T>.Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose del diccionario de
         * vértices. */
        public Iterador(Grafica<T> grafica) {
            // Aquí va su código.
	    this.iterador = grafica.vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
	    return iterador.next().elemento;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* Aristas para gráficas; para poder guardar el peso de las aristas. */
    private class Arista {

        /* El vecino del vértice. */
        public Grafica<T>.Vertice vecino;
        /* El peso de arista conectando al vértice con el vecino. */
        public double peso;

        /* Construye una nueva arista con el vértice recibido como vecino y el
         * peso especificado. */
        public Arista(Grafica<T>.Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }
    }

    /* Vertices para gráficas; implementan la interfaz ComparableIndexable y
     * VerticeGrafica */
    private class Vertice implements ComparableIndexable<Vertice>,
        VerticeGrafica<T> {

        /* Iterador para las vecinos del vértice. */
        private class IteradorVecinos implements Iterator<VerticeGrafica<T>> {

            /* Iterador auxiliar. */
            private Iterator<Grafica<T>.Arista> iterador;

            /* Construye un nuevo iterador, auxiliándose del diccionario de
             * aristas. */
            public IteradorVecinos(Iterator<Grafica<T>.Arista> iterador) {
                // Aquí va su código.
		this.iterador = iterador;
            }

            /* Nos dice si hay un siguiente vecino. */
            @Override public boolean hasNext() {
                // Aquí va su código.
		return iterador.hasNext();
            }

            /* Regresa el siguiente vecino. */
            @Override public VerticeGrafica<T> next() {
                // Aquí va su código.
		return iterador.next().vecino;
            }

            /* No lo implementamos: siempre lanza una excepción. */
            @Override public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El diccionario de aristas que conectan al vértice con sus vecinos. */
        public Diccionario<Grafica<T>.Vertice, Grafica<T>.Arista> aristas;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
	    this.elemento = elemento;
	    color = Color.NINGUNO;
	    aristas = new Diccionario<Grafica<T>.Vertice, Grafica<T>.Arista>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T getElemento() {
            // Aquí va su código.
	    return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
	    return aristas.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
	    return color;
        }

        /* Define el color del vértice. */
        @Override public void setColor(Color color) {
            // Aquí va su código.
	    this.color = color;
        }

        /* Regresa un iterador para los vecinos. */
        @Override public Iterator<VerticeGrafica<T>> iterator() {
            return new IteradorVecinos(aristas.iterator());
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
	    this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
	    return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
	    if(distancia < 0 && vertice.distancia < 0)
		return 0;
	    else if(distancia < 0)
		return 1;
	    else if(vertice.distancia < 0)
		return -1;
	    else if(distancia > vertice.distancia)
		return 1;
	    else if(distancia < vertice.distancia)
		return -1;
	    else
		return 0;
        }
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
	vertices = new Diccionario<T, Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
	return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	for(Vertice v : vertices)
	    if(elemento.equals(v.elemento))
		throw new IllegalArgumentException();
	vertices.agrega(elemento, new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
	conecta(a, b, 1.0);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva arista.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
	if(a.equals(b))
	    throw new IllegalArgumentException();
	Vertice va = null;
	Vertice vb = null;
	for(Vertice v : vertices)
	    if(a.equals(v.elemento))
		va = v;
	    else if(b.equals(v.elemento))
		vb = v;
	if(va == null || vb == null)
	    throw new NoSuchElementException();
	for(Arista ar : va.aristas)
	    if(vb == ar.vecino)
		throw new IllegalArgumentException();
	va.aristas.agrega(vb, new Arista(vb, peso));
	vb.aristas.agrega(va, new Arista(va, peso));
	aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
	if(a.equals(b))
	    throw new IllegalArgumentException();
	Vertice va = null;
	Vertice vb = null;
	for(Vertice v : vertices)
	    if(a.equals(v.elemento))
		va = v;
	    else if(b.equals(v.elemento))
		vb = v;
	if(va == null || vb == null)
	    throw new NoSuchElementException();
	boolean bool = false;
	for(Arista ar : va.aristas)
	    if(vb == ar.vecino){
		bool = true;
		break;
	    }
	if(!bool)
	    throw new IllegalArgumentException();
	va.aristas.elimina(vb);
	vb.aristas.elimina(va);
	aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	return vertices.contiene(elemento);
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	Vertice e = null;
	for(Vertice v : vertices)
	    if(elemento.equals(v.elemento))
		e = v;
	if(e == null)
	    throw new NoSuchElementException();
	for(Arista a : e.aristas)
	    for(Arista b : a.vecino.aristas)
		if(b.vecino == e){
		    a.vecino.aristas.elimina(e);
		    aristas--;
		}
	vertices.elimina(elemento);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
	if(a.equals(b))
	    return false;
	Vertice va = null;
	Vertice vb = null;
	for(Vertice v : vertices)
	    if(a.equals(v.elemento))
		va = v;
	    else if(b.equals(v.elemento))
		vb = v;
	if(va == null || vb == null)
	    throw new NoSuchElementException();
	for(Arista ar : va.aristas)
	    if(vb == ar.vecino)
		return true;
	return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos, o -1 si los elementos no están
     *         conectados.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
	if(a.equals(b))
	    return -1;
	Vertice va = null;
	Vertice vb = null;
	for(Vertice v : vertices)
	    if(a.equals(v.elemento))
		va = v;
	    else if(b.equals(v.elemento))
		vb = v;
	if(va == null || vb == null)
	    throw new NoSuchElementException();
	for(Arista ar : va.aristas)
	    if(ar.vecino == vb)
		return ar.peso;
	return -1;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
	for(Vertice v : vertices)
	    if(elemento.equals(v.elemento))
		return v;
	throw new NoSuchElementException();
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	for(Vertice v : vertices)
	    accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	Vertice inicio = null;
	for(Vertice v : vertices)
	    if(elemento.equals(v.elemento)){
		inicio = v;
		break;
	    }
	if(inicio == null)
	    throw new NoSuchElementException();
	Cola<Vertice> cola = new Cola<>();
	inicio.color = Color.ROJO;
	cola.mete(inicio);
	while(!cola.esVacia()){
	    accion.actua(cola.mira());
	    for(Arista ar : cola.saca().aristas)
		if(ar.vecino.color != Color.ROJO){
		    ar.vecino.color = Color.ROJO;
		    cola.mete(ar.vecino);
		}
	}
	for(Vertice v : vertices)
	    v.color = Color.NINGUNO;
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	Vertice inicio = null;
	for(Vertice v : vertices)
	    if(elemento.equals(v.elemento)){
		inicio = v;
		break;
	    }
	if(inicio == null)
	    throw new NoSuchElementException();
	Pila<Vertice> pila = new Pila<>();
	inicio.color = Color.ROJO;
	pila.mete(inicio);
	while(!pila.esVacia()){
	    accion.actua(pila.mira());
	    for(Arista ar : pila.saca().aristas)
		if(ar.vecino.color != Color.ROJO){
		    ar.vecino.color = Color.ROJO;
		    pila.mete(ar.vecino);
		}
	}
	for(Vertice v : vertices)
	    v.color = Color.NINGUNO;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        // Aquí va su código.
	return new Iterador(this);
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
	Vertice o = null;
	Vertice d = null;
	for(Vertice v : vertices)
	    if(origen.equals(v.elemento))
		o = v;
	    else if(destino.equals(v.elemento))
		d = v;
	if(o == null || d == null)
	    throw new NoSuchElementException();
	for(Vertice v : vertices)
	    v.distancia = -1.0;
	o.distancia = 0.0;
	MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<>(vertices.valores());
	while(!monticulo.esVacio()){
	    Vertice actual = monticulo.elimina();
	    for(Arista ar : actual.aristas)
		if(ar.vecino.distancia < 0.0){
		    ar.vecino.distancia = actual.distancia + 1;
		    monticulo.reordena(ar.vecino);
		}
	    if(actual == d)
		break;
	}
	Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
	if(d.distancia == -1.0)
	    return trayectoria;
	trayectoria.agrega(d);
	Vertice tmp = d;
	while(tmp != o){
	    for(Arista ar : tmp.aristas)
		if(ar.vecino.distancia == tmp.distancia - 1){
		    trayectoria.agregaInicio(ar.vecino);
		    tmp = ar.vecino;
		    break;
		}
	}
	for(Vertice v : vertices)
	    v.distancia = 0.0;
	return trayectoria;
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
	Vertice o = null;
	Vertice d = null;
	for(Vertice v : vertices)
	    if(origen.equals(v.elemento))
		o = v;
	    else if(destino.equals(v.elemento))
		d = v;
	if(o == null || d == null)
	    throw new NoSuchElementException();
	for(Vertice v : vertices)
	    v.distancia = -1.0;
	o.distancia = 0.0;
	MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<>(vertices.valores());
	while(!monticulo.esVacio()){
	    Vertice actual = monticulo.elimina();
	    for(Arista ar : actual.aristas)
		if(ar.vecino.distancia < 0 ||
		   ar.vecino.distancia > actual.distancia + ar.peso){
		    ar.vecino.distancia = actual.distancia + ar.peso;
		    monticulo.reordena(ar.vecino);
		}
	    if(actual == d)
		break;
	}
	Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
	if(d.distancia == -1.0)
	    return trayectoria;
	trayectoria.agrega(d);
	Vertice tmp = d;
	while(tmp != o){
	    for(Arista ar : tmp.aristas)
		if(ar.vecino.distancia == tmp.distancia - ar.peso){
		    trayectoria.agregaInicio(ar.vecino);
		    tmp = ar.vecino;
		    break;
		}
	}
	for(Vertice v : vertices)
	    v.distancia = 0.0;
	return trayectoria;
    }
}
