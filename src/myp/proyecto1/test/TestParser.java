package myp.proyecto1.test;

import java.util.Random;
import myp.proyecto1.Parser;
import myp.proyecto1.Tokens;
import myp.proyecto1.ExcepcionExpresionInvalida;
import mx.unam.ciencias.edd.Cola;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para pruebas unitarias de la clase {@link Parser}
 */
public class TestParser{

    private Random random;
    private Tokens tokenizador;
    private Parser parser;

    /**
     * Crea un parser para cada prueba unitaria.
     */
    public TestParser(){
	random = new Random();
	tokenizador = new Tokens();
	parser = new Parser();
    }
    
    /**
     * Prueba unitaria para {@link Parser#parsea}.
     */
    @Test public void testParsea(){
	int i = 0;
	//Casos fijos
	//f1(x) = 12 + x * 9
	Cola<String> f1 = parser.parsea(tokenizador.obtenerTokens("12+x*9"));
	String[] f1t = {"12","x","9","*","+"};
	for(String t : f1t)
	    Assert.assertTrue(t.equals(f1.saca()));
	//f2(x) = x ^ 9 - 10 / 3
	Cola<String> f2 = parser.parsea(tokenizador.obtenerTokens("x^9-10/3"));
	String[] f2t = {"x","9","^","10","3","/","-"};
	for(String t : f2t)
	    Assert.assertTrue(t.equals(f2.saca()));
	//f3(x) = cos(-x)
	Cola<String> f3 = parser.parsea(tokenizador.obtenerTokens("cos(-x)"));
	String[] f3t = {"0.0","x","-","cos"};
	for(String t : f3t)
	    Assert.assertTrue(t.equals(f3.saca()));
	//Entradas no manejables
	Cola<String> f;
	//() dos paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("()"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//+* dos operadores juntos, salvo "-".
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("*+"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//)cos(x) paréntesis de cierre seguido de una función.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens(")cos(x)"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}	
	//cosx función sin paréntesis para su argumento.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("cosx"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//(3*) operador y paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("(3*)"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//(*3) paréntesis y operador.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("(*3)"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//x( variable o número seguido de paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("x("));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//xcos(x) variabele seguida de operador.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("xcos(x)"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//)x paréntesis de ceirre seguido de algo que no es operador.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens(")x"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//+ operador sin operandos.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("+"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
    }

}
