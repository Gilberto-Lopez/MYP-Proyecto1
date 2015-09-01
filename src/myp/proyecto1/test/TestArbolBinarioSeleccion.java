package myp.proyecto1.test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import myp.proyecto1.ArbolBinarioSeleccion;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para pruebas unitarias de la clase {@link ArbolBinarioSeleccion}.
 */
public class TestArbolBinarioSeleccion {

    private int total;
    private Random random;
    private ArbolBinarioSeleccion<Integer> arbol;

    /* Método auxiliar recursivo para llenar una cola con los elementos del
     * árbol recorrido en in-order. */
    private static <T> void llenaColaEnOrden(VerticeArbolBinario<T> v,
					     Cola<T> cola) {
        if (v.hayIzquierdo())
            llenaColaEnOrden(v.getIzquierdo(), cola);
        cola.mete(v.get());
        if (v.hayDerecho())
            llenaColaEnOrden(v.getDerecho(), cola);
    }

    /**
     * Crea un árbol binario para cada prueba.
     */
    public TestArbolBinarioSeleccion() {
        random = new Random();
        arbol = new ArbolBinarioSeleccion<Integer>();
        total = 3 + random.nextInt(100);
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#creaRaiz}.
     */
    @Test public void testCreaRaiz(){
	int n = random.nextInt(100);
	boolean bool = arbol.creaRaiz(n);
	Assert.assertTrue(bool);
	Assert.assertTrue(arbol.getElementos() == 1);
	VerticeArbolBinario<Integer> r = arbol.raiz();
	VerticeArbolBinario<Integer> u = arbol.getUltimoVerticeAgregado();
	Assert.assertTrue(u.get() == n);
	Assert.assertTrue(r.get() == n);
	VerticeArbolBinario<Integer> b = arbol.busca(n);
	Assert.assertTrue(b != null);
	Assert.assertTrue(b.get() == n);
	int n2 = random.nextInt(100);
	while(n2 == n)
	    n2 = random.nextInt(100);
	boolean bool2 = arbol.creaRaiz(n2);
	Assert.assertFalse(bool2);
    }
    
    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#agregaIzquierda}.
     */
    @Test public void testAgregaIzquierda() {
	arbol.creaRaiz(random.nextInt(1000));
	VerticeArbolBinario<Integer> tmp = arbol.raiz();
        for (int i = 0; i < total; i++) {
            int n = random.nextInt(100);
            boolean bool = arbol.agregaIzquierda(n, tmp);
	    Assert.assertTrue(bool);
	    Assert.assertTrue(tmp.hayIzquierdo());
	    tmp = tmp.getIzquierdo();
            VerticeArbolBinario<Integer> v;
            v = arbol.getUltimoVerticeAgregado();
            Assert.assertTrue(arbol.getElementos() == i+2);
            VerticeArbolBinario<Integer> it = arbol.busca(n);
            Assert.assertTrue(it != null);
            Assert.assertTrue(it.get() == n);
            Assert.assertTrue(v.get() == n);
        }
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#agregaDerecha}.
     */
    @Test public void testAgregaDerecha() {
	arbol.creaRaiz(random.nextInt(1000));
	VerticeArbolBinario<Integer> tmp = arbol.raiz();
        for (int i = 0; i < total; i++) {
            int n = random.nextInt(100);
            boolean bool = arbol.agregaDerecha(n, tmp);
	    Assert.assertTrue(bool);
	    Assert.assertTrue(tmp.hayDerecho());
	    tmp = tmp.getDerecho();
            VerticeArbolBinario<Integer> v;
            v = arbol.getUltimoVerticeAgregado();
            Assert.assertTrue(arbol.getElementos() == i+2);
            VerticeArbolBinario<Integer> it = arbol.busca(n);
            Assert.assertTrue(it != null);
            Assert.assertTrue(it.get() == n);
            Assert.assertTrue(v.get() == n);
        }
    }

    /* Llena el árbol con elementos no repetidos. */
    private int[] arregloSinRepetidos() {
        int[] a = new int[total];
        for (int i = 0; i < total; i++) {
            int r;
            boolean repetido = false;
            do {
                r = random.nextInt(1000);
                repetido = false;
                for (int j = 0; j < i; j++)
                    if (r == a[j])
                        repetido = true;
            } while (repetido);
            a[i] = r;
        }
        return a;
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#elimina}.
     */
    @Test public void testElimina() {
        int[] a = arregloSinRepetidos();
	arbol.creaRaiz(a[0]);
	VerticeArbolBinario<Integer> tmp = arbol.raiz();
	for(int i = 1; i < a.length; i++){
	    if(i % 2 == 0){
		arbol.agregaIzquierda(a[i], tmp);
		tmp = tmp.getIzquierdo();
	    }else{
		arbol.agregaDerecha(a[i], tmp);
		tmp = tmp.getDerecho();
	    }
	}
        int n = total;
        while (arbol.getElementos() > 0) {
            Assert.assertTrue(arbol.getElementos() == n);
            int i = random.nextInt(total);
            if (a[i] == -1)
                continue;
            int e = a[i];
            VerticeArbolBinario<Integer> it = arbol.busca(e);
            Assert.assertTrue(it != null);
            Assert.assertTrue(it.get() == e);
	    int elems = arbol.contarElementos(it);
            arbol.elimina(e);
            it = arbol.busca(e);
            Assert.assertTrue(it == null);
	    Assert.assertTrue(arbol.getElementos() == n - elems);
	    n -= elems;
            a[i] = -1;
        }
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#busca}.
     */
    @Test public void testBusca() {
        int[] a = arregloSinRepetidos();
	arbol.creaRaiz(a[0]);
	VerticeArbolBinario<Integer> tmp = arbol.raiz();
	for(int i = 1; i < a.length; i++){
	    if(i % 2 == 0){
		arbol.agregaIzquierda(a[i], tmp);
		tmp = tmp.getIzquierdo();
	    }else{
		arbol.agregaDerecha(a[i], tmp);
		tmp = tmp.getDerecho();
	    }
	}
        for (int i : a) {
            VerticeArbolBinario<Integer> it = arbol.busca(i);
            Assert.assertTrue(it != null);
            Assert.assertTrue(it.get() == i);
        }
        int m = 1500 + random.nextInt(100);
        Assert.assertTrue(arbol.busca(m) == null);
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#profundidad}.
     */
    @Test public void testProfundidad() {
        for (int i = 0; i < total; i++) {
	    if(i == 0)
		arbol.creaRaiz(i);
	    else
		arbol.agregaIzquierda(i, arbol.getUltimoVerticeAgregado());
            int min = (int)Math.floor(Math.log(i+1)/Math.log(2));
            int max = i;
            Assert.assertTrue(arbol.profundidad() >= min &&
                              arbol.profundidad() <= max);
        }
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#getElementos}.
     */
    @Test public void testGetElementos() {
        for (int i = 0; i < total; i++) {
	    if(i == 0)
		arbol.creaRaiz(i);
	    else
		arbol.agregaIzquierda(i, arbol.getUltimoVerticeAgregado());
            Assert.assertTrue(arbol.getElementos() == i+1);
        }
    }
    
    /**
     * Prueba unitaria para {@link ArbolBinario#equals}.
     */
    @Test public void testEquals() {
        arbol = new ArbolBinarioSeleccion<Integer>();
        ArbolBinarioSeleccion<Integer> arbol2 = new ArbolBinarioSeleccion<Integer>();
        Assert.assertTrue(arbol.equals(arbol2));
        for (int i = 0; i < total; i++) {
	    if(i == 0){
		arbol.creaRaiz(i);
		arbol2.creaRaiz(i);
	    }else{
		arbol.agregaIzquierda(i, arbol.getUltimoVerticeAgregado());
		arbol2.agregaIzquierda(i, arbol2.getUltimoVerticeAgregado());
	    }

        }
        Assert.assertFalse(arbol == arbol2);
        Assert.assertTrue(arbol.equals(arbol2));
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#iterator}.
     */
    @Test public void testIterator() {
        Lista<Integer> lista = new Lista<Integer>();
        for (int i = 0; i < total; i++) {
            int n = random.nextInt(100);
	    if(i == 0)
		arbol.creaRaiz(n);
	    else
		arbol.agregaIzquierda(n, arbol.getUltimoVerticeAgregado());
            lista.agregaInicio(n);
        }
        int c = 0;
        Iterator<Integer> i1 = arbol.iterator();
        Iterator<Integer> i2 = lista.iterator();
        while (i1.hasNext() && i2.hasNext())
            Assert.assertTrue(i1.next() == i2.next());
        Assert.assertTrue(!i1.hasNext() && !i2.hasNext());
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#contarElementos}.
     */
    @Test public void testContarElementos(){

    }
    
    /*
    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#giraDerecha}.
     *
    @Test public void testGiraDerecha() {
        if (total == 1)
            total++;
        int[] a = arregloSinRepetidos();
        a[total-2] = 2001;
        a[total-1] = 2000;
        for (int n : a)
            arbol.agrega(n);
        VerticeArbolBinario<Integer> vertice = null;
        int Q = -1;
        do {
            Q = a[random.nextInt(total)];
            vertice = arbol.busca(Q);
            Assert.assertTrue(vertice != null);
            Assert.assertTrue(vertice.get() == Q);
        } while (!vertice.hayIzquierdo());
        vertice = vertice.getIzquierdo();
        int P = vertice.get();
        int A = -1, B = -1, C = -1;
        if (vertice.hayIzquierdo()) {
            vertice = vertice.getIzquierdo();
            A = vertice.get();
            vertice = vertice.getPadre();
        }
        if (vertice.hayDerecho()) {
            vertice = vertice.getDerecho();
            B = vertice.get();
            vertice = vertice.getPadre();
        }
        vertice = vertice.getPadre();
        if (vertice.hayDerecho()) {
            vertice = vertice.getDerecho();
            C = vertice.get();
            vertice = vertice.getPadre();
        }
        arbol.giraDerecha(vertice);
        Assert.assertTrue(arbol.getElementos() == total);
        arbolBinarioOrdenadoValido(arbol);
        for (int n : a)
            Assert.assertTrue(arbol.busca(n) != null);
        Assert.assertTrue(vertice.get() == Q);
        Assert.assertTrue(vertice.hayPadre());
        vertice = vertice.getPadre();
        Assert.assertTrue(vertice.get() == P);
        if (A != -1) {
            Assert.assertTrue(vertice.hayIzquierdo());
            vertice = vertice.getIzquierdo();
            Assert.assertTrue(vertice.get() == A);
            vertice = vertice.getPadre();
        }
        Assert.assertTrue(vertice.hayDerecho());
        vertice = vertice.getDerecho();
        Assert.assertTrue(vertice.get() == Q);
        if (B != -1) {
            Assert.assertTrue(vertice.hayIzquierdo());
            vertice = vertice.getIzquierdo();
            Assert.assertTrue(vertice.get() == B);
            vertice = vertice.getPadre();
        }
        if (C != -1) {
            Assert.assertTrue(vertice.hayDerecho());
            vertice = vertice.getDerecho();
            Assert.assertTrue(vertice.get() == C);
            vertice = vertice.getPadre();
        }
    }

    /**
     * Prueba unitaria para {@link ArbolBinarioSeleccion#giraIzquierda}.
     *
    @Test public void testGiraIzquierda() {
        if (total == 1)
            total++;
        int[] a = arregloSinRepetidos();
        a[total-2] = 2000;
        a[total-1] = 2001;
        for (int n : a)
            arbol.agrega(n);
        VerticeArbolBinario<Integer> vertice = null;
        int P = -1;
        do {
            P = a[random.nextInt(total)];
            vertice = arbol.busca(P);
            Assert.assertTrue(vertice != null);
            Assert.assertTrue(vertice.get() == P);
        } while (!vertice.hayDerecho());
        vertice = vertice.getDerecho();
        int Q = vertice.get();
        int A = -1, B = -1, C = -1;
        if (vertice.hayIzquierdo()) {
            vertice = vertice.getIzquierdo();
            B = vertice.get();
            vertice = vertice.getPadre();
        }
        if (vertice.hayDerecho()) {
            vertice = vertice.getDerecho();
            C = vertice.get();
            vertice = vertice.getPadre();
        }
        vertice = vertice.getPadre();
        if (vertice.hayIzquierdo()) {
            vertice = vertice.getIzquierdo();
            A = vertice.get();
            vertice = vertice.getPadre();
        }
        arbol.giraIzquierda(vertice);
        Assert.assertTrue(arbol.getElementos() == total);
        arbolBinarioOrdenadoValido(arbol);
        for (int n : a)
            Assert.assertTrue(arbol.busca(n) != null);
        Assert.assertTrue(vertice.get() == P);
        Assert.assertTrue(vertice.hayPadre());
        vertice = vertice.getPadre();
        Assert.assertTrue(vertice.get() == Q);
        if (C != -1) {
            Assert.assertTrue(vertice.hayDerecho());
            vertice = vertice.getDerecho();
            Assert.assertTrue(vertice.get() == C);
            vertice = vertice.getPadre();
        }
        Assert.assertTrue(vertice.hayIzquierdo());
        vertice = vertice.getIzquierdo();
        Assert.assertTrue(vertice.get() == P);
        if (A != -1) {
            Assert.assertTrue(vertice.hayIzquierdo());
            vertice = vertice.getIzquierdo();
            Assert.assertTrue(vertice.get() == A);
            vertice = vertice.getPadre();
        }
        if (B != -1) {
            Assert.assertTrue(vertice.hayDerecho());
            vertice = vertice.getDerecho();
            Assert.assertTrue(vertice.get() == B);
            vertice = vertice.getPadre();
        }
    }
    */
}
