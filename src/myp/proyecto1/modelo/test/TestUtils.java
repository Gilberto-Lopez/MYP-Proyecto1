package myp.proyecto1.modelo.test;

import myp.proyecto1.modelo.Utils;
import myp.proyecto1.modelo.ExcepcionExpresionInvalida;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para pruebas unitarias de la clase {@link Utils}.
 */
public class TestUtils{

    /* Funciones válidas. */
    private static String[] funciones = {
	"sin", "cos", "tan", "sec", "csc", "cot", "sqr"
    };
    /* Operadores válidos. */
    private static String[] operadores = {
	"+", "-", "*", "/", "^"
    };
    private Random random;

    /**
     * Inicializa un TestUtils para cada prueba unitaria.
     */
    public TestUtils(){
	random = new Random();
    }

    /**
     * Prueba unitaria para {@link Utils#esVariable}.
     */
    @Test public void testEsVariable(){
	Assert.assertTrue(Utils.esVariable("x"));
	Assert.assertFalse(Utils.esVariable("X"));
	for(String f : funciones)
	    Assert.assertFalse(Utils.esVariable(f));
	for(String op : operadores)
	    Assert.assertFalse(Utils.esVariable(op));
	double[] aleatorios = aleatorios(random.nextInt(50) + 1);
	for(double d : aleatorios)
	    Assert.assertFalse(Utils.esVariable(String.valueOf(d)));
    }

    /**
     * Prueba unitaria para {@link Utils#esNumero}.
     */
    @Test public void testEsNumero(){
	Assert.assertTrue(Utils.esNumero("123456789"));
	Assert.assertTrue(Utils.esNumero("12345.6789"));
	Assert.assertTrue(Utils.esNumero(".123456789"));
	Assert.assertTrue(Utils.esNumero("123456789."));
	double[] aleatorios = aleatorios(random.nextInt(1000) + 1);
	for(int i = 0; i < aleatorios.length; i++){
	    Assert.assertTrue(Utils.esNumero(String.valueOf(aleatorios[i])));
	    if(i+1 < aleatorios.length)
		Assert.assertFalse(Utils.esNumero(String.valueOf(aleatorios[i]) + String.valueOf(aleatorios[i+1])));
	}
	Assert.assertFalse(Utils.esNumero("x"));
	for(String f : funciones)
	    Assert.assertFalse(Utils.esNumero(f));
	for(String op : operadores)
	    Assert.assertFalse(Utils.esNumero(op));
    }

    /**
     * Prueba unitaria para {@link Utils#esFuncion}.
     */
    @Test public void testEsFuncion(){
	for(String f : funciones){
	    Assert.assertTrue(Utils.esFuncion(f));
	    Assert.assertFalse(Utils.esFuncion(f.toUpperCase()));
	}
	Assert.assertFalse(Utils.esFuncion("x"));
	for(String op : operadores)
	    Assert.assertFalse(Utils.esFuncion(op));
	double[] aleatorios = aleatorios(random.nextInt(50) + 1);
	for(double d : aleatorios)
	    Assert.assertFalse(Utils.esFuncion(String.valueOf(d)));
    }

    /**
     * Prueba unitaria para {@link Utils#esOperador}.
     */
    @Test public void testEsOperador(){
	for(String op : operadores)
	    Assert.assertTrue(Utils.esOperador(op));
	Assert.assertFalse(Utils.esFuncion("x"));
	for(String f : funciones)
	    Assert.assertFalse(Utils.esOperador(f));
	double[] aleatorios = aleatorios(random.nextInt(50) + 1);
	for(double d : aleatorios)
	    Assert.assertFalse(Utils.esOperador(String.valueOf(d)));
    }

    /**
     * Prubea unitaria para {@link Utils#excepcion}.
     */
    @Test public void TestExcepcion(){
	for(String f : funciones){
	    try{
		Utils.excepcion(f);
	    }catch(ExcepcionExpresionInvalida eei){
		Assert.assertTrue(eei.getMessage().equals(f));
	    }
	}
	try{
	    Utils.excepcion(null);
	}catch(ExcepcionExpresionInvalida eei){
	    Assert.assertTrue(eei.getMessage() == null);
	}
	try{
	    Utils.excepcion("");
	}catch(ExcepcionExpresionInvalida eei){
	    Assert.assertTrue(eei.getMessage() == null);
	}
    }
    
    /* Genera un arreglo de doubles aleatorios. */
    private double[] aleatorios(int tam){
	double[] aleatorios = new double[tam];
	for(int i = 0; i < tam; i++){
	    aleatorios[i] = (random.nextDouble() + 1) * 1000;
	}
	return aleatorios;
    }
    
}
