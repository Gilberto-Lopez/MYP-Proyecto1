package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios completos. */
    private class Iterador implements Iterator<T> {

        private Cola<ArbolBinario<T>.Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador(ArbolBinario<T>.Vertice raiz) {
            // Aquí va su código.
	    this.cola = new Cola<>();
	    cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return !cola.esVacia();
        }

        /* Regresa el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
	    ArbolBinario<T>.Vertice siguiente = cola.saca();
	    cola.mete(siguiente.izquierdo);
	    cola.mete(siguiente.derecho);
	    return siguiente.elemento;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if(raiz == null)
	    ultimoAgregado = raiz = nuevoVertice(elemento);
	else{
	    Cola<Vertice> cola = new Cola<>();
	    Vertice tmp;
	    cola.mete(raiz);
	    while(!cola.esVacia()){
		tmp = cola.saca();
		if(tmp.izquierdo == null){
		    ultimoAgregado = tmp.izquierdo = nuevoVertice(elemento);
		    tmp.izquierdo.padre = tmp;
		    break;
		}
		if(tmp.derecho == null){
		    ultimoAgregado = tmp.derecho = nuevoVertice(elemento);
		    tmp.derecho.padre = tmp;
		    break;
		}
		cola.mete(tmp.izquierdo);
		cola.mete(tmp.derecho);
	    }
	}
	elementos++;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	if(elementos == 1 && raiz.elemento.equals(elemento)){
	    ultimoAgregado = raiz = null;
	    elementos--;
	}else if(elementos <= 1) return;
	else{
	    Cola<Vertice> cola = new Cola<>();
	    cola.mete(raiz);
	    Vertice tmp = cola.mira();
	    Vertice tmp2 = cola.mira();
	    byte b = 0;
	    while(!cola.esVacia()){
		tmp = cola.saca();
		cola.mete(tmp.izquierdo);
		cola.mete(tmp.derecho);
		if(!cola.esVacia()){
		    tmp2 = cola.saca();
		    cola.mete(tmp2.izquierdo);
		    cola.mete(tmp2.derecho);
		}
		if(b == 0 && tmp.elemento.equals(elemento)){
		    tmp.elemento = ultimoAgregado.elemento;
		    elementos--;
		    b++;
		}
		if(b == 0 && tmp2.elemento.equals(elemento)){
		    tmp2.elemento = ultimoAgregado.elemento;
		    elementos--;
		    b++;
		}

	    }
	    if(b == 1) eliminaUltimoAgregado(tmp, tmp2);
	}
    }
    
    /* Metodo auxiliar para eliminar el ultimo elemento agregado y pasar
     * la referencia al penultimo elemento agregado. */
    private void eliminaUltimoAgregado(Vertice tmp1, Vertice tmp2){
	if(tmp1 == ultimoAgregado){
	    tmp1 = tmp1.padre;
	    if(tmp1.izquierdo == ultimoAgregado)
		tmp1.izquierdo = null;
	    else
		tmp1.derecho = null;
	    ultimoAgregado = tmp2;
	}else{
	    tmp2 = tmp2.padre;
	    if(tmp2.izquierdo == ultimoAgregado)
		    tmp2.izquierdo = null;
	    else
		tmp2.derecho = null;
	    ultimoAgregado = tmp1;
	}
    }
    
    /**
     * Nos dice si un elemento está en el árbol binario completo.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	if(elementos == 0) return false;
	Cola<Vertice> cola = new Cola<>();
	Vertice tmp;
	cola.mete(raiz);
	while(!cola.esVacia()){
	    tmp = cola.saca();
	    if(tmp.elemento.equals(elemento))
		return true;
	    cola.mete(tmp.izquierdo);
	    cola.mete(tmp.derecho);
	}
	return false;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador(raiz);
    }
}
