package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
	if(elemento == null) return;
	if(elementos == 0)
	    cabeza = rabo = new Nodo(elemento);
	else{
	    Nodo nuevo = new Nodo(elemento);
	    rabo.siguiente = nuevo;
	    rabo = nuevo;
	}
	elementos++;
    }
}
