package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las instancias de la clase Lista implementan la interfaz {@link
 * Coleccion}, y por lo tanto también la interfaz {@link Iterator}, por lo que
 * el recorrerlas es muy sencillo:</p>
 *
<pre>
    for (String s : l)
        System.out.println(s);
</pre>
 *
 * <p>Además, se le puede pedir a una lista una instancia de {@link
 * IteradorLista} para recorrerla en ambas direcciones.</p>
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo {

        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con el elemento especificado. */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador<T> implements IteradorLista<T> {

        /* La lista a iterar. */
        Lista<T> lista;
        /* Elemento anterior. */
        private Lista<T>.Nodo anterior;
        /* Elemento siguiente. */
        private Lista<T>.Nodo siguiente;

        /* El constructor recibe una lista para inicializar su siguiente con la
         * cabeza. */
        public Iterador(Lista<T> lista) {
            // Aquí va su código.
	    this.lista = lista;
	    this.siguiente = lista.cabeza;
        }

        /* Existe un siguiente elemento, si siguiente no es nulo. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return siguiente != null;
        }

        /* Regresa el elemento del siguiente, a menos que sea nulo, en cuyo caso
         * lanza la excepción NoSuchElementException. */
        @Override public T next() {
            // Aquí va su código.
	    if(siguiente == null)
		throw new NoSuchElementException();
	    anterior = siguiente;
	    siguiente = siguiente.siguiente;
	    return anterior.elemento;
        }

        /* Existe un elemento anterior, si anterior no es nulo. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
	    return anterior != null;
        }

        /* Regresa el elemento del anterior, a menos que sea nulo, en cuyo caso
         * lanza la excepción NoSuchElementException. */
        @Override public T previous() {
            // Aquí va su código.
	    if(anterior == null)
		throw new NoSuchElementException();
	    siguiente = anterior;
	    anterior = anterior.anterior;
	    return siguiente.elemento;
        }

        /* No implementamos el método remove(); sencillamente lanzamos la
         * excepción UnsupportedOperationException. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }

        /* Mueve el iterador al inicio de la lista; después de llamar este
         * método, y si la lista no es vacía, hasNext() regresa verdadero y
         * next() regresa el primer elemento. */
        @Override public void start() {
            // Aquí va su código.
	    siguiente = lista.cabeza;
	    anterior = null;
        }

        /* Mueve el iterador al final de la lista; después de llamar este
         * método, y si la lista no es vacía, hasPrevious() regresa verdadero y
         * previous() regresa el último elemento. */
        @Override public void end() {
            // Aquí va su código.
	    anterior = lista.rabo;
	    siguiente =  null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
	return getElementos();
    }

    /**
     * Regresa el número de elementos en la lista. El método es idéntico a
     * {@link #getLongitud}.
     * @return el número de elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return longitud;
    }

    /**
     * Agrega un elemento al final de la lista. El método es idéntico a {@link
     * #agregaFinal}.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if(longitud == 0)
	    cabeza = rabo = new Nodo(elemento);
	else{
	    Nodo nuevoRabo = new Nodo(elemento);
	    nuevoRabo.anterior = rabo;
	    rabo.siguiente = nuevoRabo;
	    rabo = nuevoRabo;
	}
	longitud++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y el último a la vez.
     * @param elemento el elemento a agregar.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
	agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y el último a la vez.
     * @param elemento el elemento a agregar.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
	if(longitud == 0)
	    cabeza = rabo = new Nodo(elemento);
	else{
	    Nodo nuevaCabeza = new Nodo(elemento);
	    nuevaCabeza.siguiente = cabeza;
	    cabeza.anterior = nuevaCabeza;
	    cabeza = nuevaCabeza;
	}
	longitud++;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no hace nada. Si el elemento aparece varias veces en la
     * lista, el método elimina el primero.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	Nodo tmp = cabeza;
	while(tmp != null){
	    if(elemento.equals(tmp.elemento))
		break;
	    tmp = tmp.siguiente;
	}
	if(tmp == null) return;
	if(tmp.anterior == null && tmp.siguiente == null)
	    cabeza = rabo = null;
	else if(tmp.anterior == null){
	    cabeza = tmp.siguiente;
	    cabeza.anterior = null;
	}else if(tmp.siguiente == null){
	    rabo = tmp.anterior;
	    rabo.siguiente = null;
	}else{
	    tmp.anterior.siguiente = tmp.siguiente;
	    tmp.siguiente.anterior = tmp.anterior;
	}
	longitud--;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
	T elemento = cabeza.elemento;
	if(longitud == 1)
	    cabeza = rabo = null;
	else{
	    cabeza = cabeza.siguiente;
	    cabeza.anterior = null;
	}
	longitud--;
	return elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
	if(longitud == 1)
	    return eliminaPrimero();
	T elemento = rabo.elemento;
	rabo = rabo.anterior;
	rabo.siguiente = null;
	longitud--;
	return elemento;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	Nodo tmp = cabeza;
	while(tmp != null){
	    if(elemento.equals(tmp.elemento))
		return true;
	    tmp = tmp.siguiente;
	}
	return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa de la que manda llamar el
     *         método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
	Lista<T> reversa = new Lista<>();
	Nodo tmp = this.rabo;
	while(tmp != null){
	    reversa.agrega(tmp.elemento);
	    tmp = tmp.anterior;
	}
	return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copia de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
	Lista<T> lista = new Lista<>();
	Nodo tmp = this.cabeza;
	while(tmp != null){
	    lista.agrega(tmp.elemento);
	    tmp = tmp.siguiente;
	}
	return lista;
    }

    /**
     * Limpia la lista de elementos. El llamar este método es equivalente a
     * eliminar todos los elementos de la lista.
     */
    public void limpia() {
        // Aquí va su código.
	cabeza = rabo = null;
	longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
	if(longitud == 0)
	    throw new NoSuchElementException();
	return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
	if(longitud == 0)
	    throw new NoSuchElementException();
	return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista, si <em>i</em> es mayor
     *         o igual que cero y menor que el número de elementos en la lista.
     * @throws ExcepcionIndiceInvalido si el índice recibido es menor que cero,
     *         o mayor que el número de elementos en la lista menos uno.
     */
    public T get(int i) {
        // Aquí va su código.
	if(i < 0 || i > longitud - 1)
	    throw new ExcepcionIndiceInvalido();
	Nodo tmp = cabeza;
	for(int j = 0; j < i; j++)
	    tmp = tmp.siguiente;
	return tmp.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
	int indice = 0;
	Nodo tmp = cabeza;
	while(tmp != null){
	    if(elemento.equals(tmp.elemento))
		return indice;
	    tmp = tmp.siguiente;
	    indice++;
	}
	return -1;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        // Aquí va su código.
	if(longitud != lista.longitud)
	    return false;
	Nodo tmp1 = this.cabeza;
	Nodo tmp2 = lista.cabeza;
	while(tmp1 != null){
	    if(!tmp1.elemento.equals(tmp2.elemento))
		return false;
	    tmp1 = tmp1.siguiente;
	    tmp2 = tmp2.siguiente;
	}
	return true;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
	if(longitud == 0)
	    return "[]";
	String cadena = "[";
	Nodo tmp = cabeza;
	for(int i = 0; i < longitud -1; i++){
	    cadena += tmp.elemento + ", ";
	    tmp = tmp.siguiente;
	}
	return cadena + tmp.elemento  + "]";
    }

    /**
     * Regresa un iterador para recorrer la lista.
     * @return un iterador para recorrer la lista.
     */
    @Override public Iterator<T> iterator() {
        return iteradorLista();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador<T>(this);
    }
    //-----------------------------------------------------------------------
    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> l) {
        // Aquí va su código.
	if(l.longitud < 2)
	    return l.copia();
	Lista<T> li = new Lista<>();
	Lista<T> ld = new Lista<>();
	Lista<T>.Nodo it = l.cabeza;
	for(int i = 0; i < l.longitud / 2; i++){
	    li.agrega(it.elemento);
	    it = it.siguiente;
	}
	for(int i = l.longitud / 2; i < l.longitud; i++){
	    ld.agrega(it.elemento);
	    it = it.siguiente;
	}
	li = mergeSort(li);
	ld = mergeSort(ld);
	return mezcla(li, ld);
    }

    /* Metodo auxiliar para mergeSort() que realiza la mezcla. */
    private static <T extends Comparable<T>> Lista<T> mezcla(Lista<T> li,
							     Lista<T> ld){
	Lista<T> mezcla = new Lista<>();
	Lista<T>.Nodo tmpi = li.cabeza;
	Lista<T>.Nodo tmpd = ld.cabeza;
	while(tmpi != null && tmpd != null)
	    if(tmpi.elemento.compareTo(tmpd.elemento) < 0){
		mezcla.agrega(tmpi.elemento);
		tmpi = tmpi.siguiente;
	    }else if(tmpi.elemento.compareTo(tmpd.elemento) > 0){
		mezcla.agrega(tmpd.elemento);
		tmpd = tmpd.siguiente;
	    }else{
		mezcla.agrega(tmpi.elemento);
		mezcla.agrega(tmpd.elemento);
		tmpi = tmpi.siguiente;
		tmpd = tmpd.siguiente;
	    }
	while(tmpi != null){
	    mezcla.agrega(tmpi.elemento);
	    tmpi = tmpi.siguiente;
	}
	while(tmpd != null){
	    mezcla.agrega(tmpd.elemento);
	    tmpd = tmpd.siguiente;
	}
	return mezcla;
    }
    
    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista donde se buscará.
     * @param e el elemento a buscar.
     * @return <tt>true</tt> si e está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> l,
								   T e) {
        // Aquí va su código.
	if(l.longitud == 0)
	    return false;
	Lista<T>.Nodo tmp = l.cabeza;
	while(tmp != null){
	    if(e.compareTo(tmp.elemento) < 0)
		return false;
	    if(e.compareTo(tmp.elemento) == 0)
		return true;
	    tmp = tmp.siguiente;
	}
	return false;
    }
}
