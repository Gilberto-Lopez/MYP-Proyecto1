package myp.proyecto1.test;

import java.util.Random;
import myp.proyecto1.Evaluador;
import myp.proyecto1.Parser;
import myp.proyecto1.Tokens;
import mx.unam.ciencias.edd.Cola;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para puebas unitarias de la clase {@link Evaluador}.
 */
public class TestEvaluador{

    private Random random;
    private Parser parser;
    private Evaluador evaluador;

    /**
     * Crea un evaluador para cada prueba unitaria.
     */
    public TestEvaluador(){
	random = new Random();
	parser = new Parser();
	evaluador = new Evaluador();
    }

    /**
     * Prueba unitaria para {@link Evaluador#evalua}.
     */
    @Test public void testEvalua(){
	double[] conjunto10 = conjuntoX(10);
	//f1(x) = x^4 + 3*x^2 -2 polinomio de grado 4.
	Cola<String> f1 =
	    parser.parsea(tokenizador.obtenerTokens("x^4 + 3*x^2 - 2"));
	for(double d : conjunto10){
	    double val = Math.pow(d,4.0)+3*Math.pow(d,2.0)-2;
	    Assert.assertTrue(evaluador.evalua(f1, d) == val);
	}
	//f2(x) = 1 función constante 1.
	Cola<String> f2 = parser.parsea(tokenizador.obtenerTokens("1"));
	for(double d : conjunto10)
	    Assert.assertTrue(evaluador.evalua(f2, d) == 1);
	//f3(x) = sen(x) función impar.
	Cola<String> f3 = parser.parsea(tokenizador.obtenerTokens("sin(x)"));
	for(double d : conjunto10)
	    Assert.assertTrue(evaluador.evalua(f3, d*Math.PI) ==
			      -evaluador.evalua(f3, -d*Math.PI));
	//f4(x) = cos(x) función par.
	Cola<String> f4 = parser.parsea(tokenizador.obtenerTokens("cos(x)"));
	for(double d : conjunto10)
	    Assert.assertTrue(evaluador.evalua(f4, d*Math.PI) ==
			      evaluador.evalua(f4, -d*Math.PI));
	//f5(x) = cos(x)^2 + sen(x)^2
	Cola<String> f5 =
	    parser.parsea(tokenizador.obtenerTokens("cos(x)^2+sen(x)^2"));
	for(double d : conjunto10)
	    Assert.assertTrue(evaluador.evalua(f5, d) ==
			      Math.pow(Math.cos(d), 2) +
			      Math.pow(Math.sin(d), 2));
	//Caso especial ----3 = 3
	Cola<String> f = parser.parsea(tokenizador.obtenerTokens("----3"));
	Assert.assertTrue(evaluador.evalua(f, 0.0) == 3);
	//NaN.
	f = parser.parsea(tokenizador.obtenerTokens("sqrt(-1)"));
	Assert.assertTrue(Double.isNaN(evaluador.evalua(f, 0.0)));
	//Infinito positivo.
	f = parser.parsea(tokenizador.obtenerTokens("2.8^x"));
	Assert.assertTrue(evaluador.evalua(f, Double.POSITIVE_INFINITY) ==
			  Double.POSITIVE_INFINITY);
	//División entre 0.
	f = parser.parsea(tokenizador.obtenerTokens("1/0"));
	Assert.assertTrue(evaluador.evalua(f, 0.0) == Double.NaN);
    }

    /* Nos da un conjunto de valores de x en orden de una cierta cantidad
     * (tamaño). */
    private double[] valoresX(int tamaño){
	double [] conjunto = new double[tamaño];
	for(int i = 0; i < tamaño; i++)
	    conjunto[i] = i + random.nextDouble() + 1;
	return conjunto;
    }
    
}
