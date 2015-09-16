package myp.proyecto1.modelo.test;

import java.util.Random;
import myp.proyecto1.modelo.Evaluador;
import myp.proyecto1.modelo.Parser;
import myp.proyecto1.modelo.Tokens;
import mx.unam.ciencias.edd.Lista;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para puebas unitarias de la clase {@link Evaluador}.
 */
public class TestEvaluador{

    private Random random;
    private Tokens tokenizador;
    private Parser parser;
    private Evaluador evaluador;

    /**
     * Crea un evaluador para cada prueba unitaria.
     */
    public TestEvaluador(){
	random = new Random();
	tokenizador = new Tokens();
	parser = new Parser();
	evaluador = new Evaluador();
    }

    /**
     * Prueba unitaria para {@link Evaluador#evalua}.
     */
    @Test public void testEvalua(){
	double[] valores10 = valoresX(10);
	//f1(x) = x^4 + 3*x^2 -2 polinomio de grado 4.
	Lista<String> f1 =
	    parser.parsea(tokenizador.obtenerTokens("x^4 + 3*x^2 - 2"));
	for(double d : valores10){
	    double val = Math.pow(d,4.0)+3*Math.pow(d,2.0)-2;
	    Assert.assertTrue(evaluador.evalua(f1, d) == val);
	}
	//f2(x) = 1 función constante 1.
	Lista<String> f2 = parser.parsea(tokenizador.obtenerTokens("1"));
	for(double d : valores10)
	    Assert.assertTrue(evaluador.evalua(f2, d) == 1);
	//f3(x) = sen(x) función impar.
	Lista<String> f3 = parser.parsea(tokenizador.obtenerTokens("sin(x)"));
	for(double d : valores10)
	    Assert.assertTrue(evaluador.evalua(f3, d*Math.PI) ==
			      -evaluador.evalua(f3, -d*Math.PI));
	//f4(x) = cos(x) función par.
	Lista<String> f4 = parser.parsea(tokenizador.obtenerTokens("cos(x)"));
	for(double d : valores10)
	    Assert.assertTrue(evaluador.evalua(f4, d*Math.PI) ==
			      evaluador.evalua(f4, -d*Math.PI));
	//f5(x) = cos(x)^2 + sen(x)^2
	Lista<String> f5 =
	    parser.parsea(tokenizador.obtenerTokens("cos(x)^2+sin(x)^2"));
	for(double d : valores10)
	    Assert.assertTrue(evaluador.evalua(f5, d) ==
			      Math.pow(Math.cos(d), 2) +
			      Math.pow(Math.sin(d), 2));
	//Caso especial ----3 = 3
	Lista<String> f = parser.parsea(tokenizador.obtenerTokens("----3"));
	Assert.assertTrue(evaluador.evalua(f, 0.0) == 3);
	//División entre 0. Numerador distinto de 0
	f = parser.parsea(tokenizador.obtenerTokens("1/0"));
	Assert.assertTrue(Double.isInfinite(evaluador.evalua(f, 0.0)));
	//División entre 0. Numerador 0
	f = parser.parsea(tokenizador.obtenerTokens("0/0"));
	Assert.assertTrue(Double.isNaN(evaluador.evalua(f, 0.0)));
	//NaN.
	f = parser.parsea(tokenizador.obtenerTokens("sqr(-1)"));
	Assert.assertTrue(Double.isNaN(evaluador.evalua(f, 0.0)));
	//Infinito positivo.
	f = parser.parsea(tokenizador.obtenerTokens("2.8^x"));
	Assert.assertTrue(Double.isInfinite(evaluador.evalua(f, Double.POSITIVE_INFINITY)));
    }

    /* Nos da un conjunto de valores de x de una cierta cantidad (tamaño). */
    private double[] valoresX(int tamaño){
	double [] conjunto = new double[tamaño];
	for(int i = 0; i < tamaño; i++)
	    conjunto[i] = i + random.nextDouble()*10 + 1;
	return conjunto;
    }
    
}
