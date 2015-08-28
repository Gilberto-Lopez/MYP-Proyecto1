package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
	if(elemento == null) return;
	if(elementos == 0)
	    cabeza = rabo = new Nodo(elemento);
	else{
	    Nodo nuevo = new Nodo(elemento);
	    nuevo.siguiente = cabeza;
	    cabeza = nuevo;
	}
	elementos++;
    }
}
