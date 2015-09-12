package myp.proyecto1.test;

import java.util.Random
import myp.proyecto1.ArbolLexico;
import myp.proyecto1.ArbolBinarioSeleccion;
import myp.proyecto1.Tokens
import myp.proyecto1.ExcepcionExpresionInvalida;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Pila;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para prueba unitarias de la clase {@link ArbolLexico}.
 */
public class TestArbolLexico{
    
    private Random random;
    private Tokens tokenizador;
    private ArbolLexico arbol;

    /**
     * Crea un árbol léxico para cada prueba unitaria.
     */
    public TestArbolLexico(){
	random = new Random();
	tokenizador = new Tokens();
	arbol = new ArbolLexico();
    }

    /**
     * Prueba unitaria para {@link ArbolLexico#generaArbol}.
     */
    @Test public void TestGeneraArbol(){
	arbol.generaArbol(tokenizador.obtenerTokens("(cos(^x2))"));
	esArbolValido(arbol.getArbol());
	arbol.limpiaArbol();
	//Paréntesis no balanceados.
	try{
	    arbol.generaArbol(tokenizador.obtenerTokens(")cosx("));
	    Assert.fail();
	    arbol.limpiaArbol();
	    arbol.generaArbol(tokenizador.obtenerTokens("()cosx()"));
	    Assert.fail();
	    arbol.limpiaArbol();
	}catch(ExcepcionExpresionInvalida eei){
	    System.err.println(eei.getMessage());
	}
	//Expresión sin sentido.
	try{
	    arbol.generaArbol(tokenizador.obtenerTokens("(//)"));
	    Assert.fail();
	    arbol.limpiaArbol();
	}catch(ExcepciónExpresiónInvalida eei){
	    System.err.println(eei.getMessage());
	}
	//Generación del mismo árbol.
	arbol.generaArbol(tokenizador.obtenerTokens("(sinx)"));
	ArbolLexico arbol2 = new ArbolLexico();
	arbol2.generaArbol(tokenizador.obtenerTokens("(sin(x))"));
	ArbolBinarioSeleccion<String> arbolBS = arbol.getArbol();
	ArbolBinarioSeleccion<String> arbolBS2 = arbol2.getArbol();
	esArbolValido(arbolBS);
	esArbolValido(arbolBs2);
	Assert.assertTrue(arbolBS != arbolBS2);
	Assert.assertTrue(arbolBS.equals(arbolBS2));
    }

    /**
     * Prueba unitaria para {@link ArbolLexico#evaluaArbol}.
     */
    @Test public void testEvaluaArbol(){
	double[] conjunto10 = conjuntoX(10);
	//f1(x) = x^4 + 3*x^2 -2 polinomio de grado 4.
	arbol.generaArbol(tokenizador.obtenerTokens("(+(^x4)(-(*3(^x2))2))"));
	for(double d : conjunto10){
	    double val = Math.pow(d,4.0)+3*Math.pow(d,2.0)-2;
	    Assert.assertTrue(arbol.evaluaArbol(d) == val);
	}
	//f2(x) = 1 función constante 1.
	arbol.limpiaArbol();
	arbol.generaArbol(tokenizador.obtenerTokens("1"));
	for(double d : conjunto10)
	    Assert.assertTrue(arbol.evaluaArbol(random.nextInt(100)) == 1);
	//f3(x) = sen(x) función impar.
	arbol.limpiaArbol()
	arbol.generaArbol(tokenizador.obtenerTokens("(sinx)"));
	for(double d : conjunto10)
	    Assert.assertTrue(arbol.evaluaArbol(d*Math.PI) ==
			      -arbol.evaluaArbol(-d*Math.PI));
	//f4(x) = cos(x) función par.
	arbol.limpiaArbol()
	arbol.generaArbol(tokenizador.obtenerTokens("(cosx)"));
	for(double d : conjunto10)
	    Assert.assertTrue(arbol.evaluaArbol(d*Math.PI) ==
			      arbol.evaluaArbol(-d*Math.PI));
	arbol.limpiaArbol();
	//NaN.
	arbol.generaArbol(tokenizador.obtenerTokens("(sqrt-1)"));
	Assert.assertTrue(arbol.evaluaArbol(0) == Double.NaN);
	arbol.limpiaArbol();
	//Infinito positivo.
	arbol.generaArbol(tokenizador.obtenerTokens("(^2.8x)"));
	Assert.assertTrue(arbol.evaluaArbol(Double.POSITIVE_INFINITY) ==
			  Double.POSITIVE_INFINITY);
	arbol.limpiaArbol();
	//División entre 0.
	arbol.generaArbol(tokenizador.obtenerTokens("(/1 0)"));
	Assert.assertTrue(arbol.evaluaArbol(0) == Double.NaN);
    }

    /**
     * Prueba unitaria para {@link ArbolLexico#limpiaArbol}.
     */
    @Test public void testLimpiaArbol(){
	ArbolBinarioSeleccion<String> arbolin = arbol.getArbol();
	arbolin.creaRaiz("sin");
	Assert.assertTrue(arbol.getArbol().raiz() != null);
	arbol.limpiaArbol();
	Assert.assertTrue(arbolin != arbol.getArbol());
	try{
	    arbol.getArbol.raiz();
	    Assert.fail();
	}catch(NoSuchElementException nsee){}
    }
    
    /* Nos dice si un árbol léxico es válido. */
    private void esArbolValido(ArbolBinarioSeleccion<String> arbol){
	Cola<VerticeArbolBinario<String>> cola = new Cola<>();
	cola.mete(arbol.raiz());
	while(!cola.esVacia()){
	    VerticeArbolBinario<String> tmp = cola.saca();
	    if(tmp.hayIzquierdo())
		cola.mete(tmp.getIzquierdo());
	    if(tmp.hayDerecho())
		cola.mete(tmp.getDerecho());
	    if(esTerminal(tmp.get()))
		Assert.assertTrue(!tmp.hayIzquierdo() && !tmp.hayDerecho());
	    else if(esFuncion(tmp.get()))
		Assert.assertTrue(!tmp.hayIzquierdo() && tmp.hayDerecho());
	    else if(esOperador(tmp.get)){
		Assert.assertTrue(tmp.hayDerecho());
		if(!tmp.get().equals("-"))
		    Assert.assertTrue(tmp.hayIzquierdo());
	    }
	}
    }

    /* Nos dice si un token es una variable. */
    private boolean esVariable(String t){
	return token.equals("x");
    }

    /* Nos dice si un token es un número. */
    private boolean esNumero(String t){
	char[] token = t.toCharArray();
	boolean esNum = false;
	int p = 0;
	for(int i = 0; i < token.length; i++){
	    if(token[i] >= '0' && token[i] <= '9')
		esNum = true;
	    else if(token[i] == '.'){
		p++;
		if(p > 1)
		    return false;
	    }else
		return false;
	}
	return esNum;
    }
    
    /* Nos dice si un token es un token terminal (no puede tener hijos). */
    private boolean esTerminal(String t){
	return esVariable(t) || esNumero(t);
    }

    /* Nos dice si un token es una función. */
    private boolean esFuncion(String t){
	return t.equals("sin") || t.equals("cos") || t.equals("tan") ||
	    t.equals("sec") || t.equals("csc") || t.equals("cot") ||
	    t.equals("sqr");
    }

    /* Nos dice si un token es un operador. */
    private boolean esOperador(String t){
	return t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/")
	    || t.equals("^");
    }

    /* Nos da un conjunto de valores de x en orden de una cierta cantidad
     * (tamaño). */
    private double[] valoresX(int tamaño){
	double [] conjunto = new double[tamaño];
	for(int i = 0; i < tamaño; i++)
	    conjunto[i] = i + random.nextDouble();
	return conjunto;
    }
    
}
