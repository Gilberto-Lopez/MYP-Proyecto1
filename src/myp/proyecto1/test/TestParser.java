package myp.proyecto1.test;

import java.util.Random;
import myp.proyecto1.Parser;
import myp.proyecto1.Tokens;
import myp.proyecto1.ExcepcionExpresionInvalida;
import mx.unam.ciencias.edd.Lista;
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
	int i;
	//Casos fijos
	//f1(x) = 12 + x * 9
	i = 0;
	Lista<String> f1 = parser.parsea(tokenizador.obtenerTokens("12+x*9"));
	String[] f1t = {"12","x","9","*","+"};
	for(String t : f1)
	    Assert.assertTrue(t.equals(f1t[i++]));
	//f2(x) = x ^ 9 - 10 / 3
	i = 0;
	Lista<String> f2 =
	    parser.parsea(tokenizador.obtenerTokens("(x^9-(10/3))"));
	String[] f2t = {"x","9","^","10","3","/","-"};
	for(String t : f2)
	    Assert.assertTrue(t.equals(f2t[i++]));
	//f3(x) = cos(-x)
	i = 0;
	Lista<String> f3 = parser.parsea(tokenizador.obtenerTokens("cos(-x)"));
	String[] f3t = {"0.0","x","-","cos"};
	for(String t : f3)
	    Assert.assertTrue(t.equals(f3t[i++]));
	Lista<String> f;
	//Caso especial
	//----3
	i = 0;
	f = parser.parsea(tokenizador.obtenerTokens("----3"));
	String[] ft = {"0.0","0.0","0.0","3","-","-","-","-"};
	for(String t : f)
	    Assert.assertTrue(t.equals(ft[i++]));
	//Entradas no manejables
	//() dos paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("()"));
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){
	    System.out.println(eei.getMessage());
	}
	//*+ dos operadores juntos, salvo "-".
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
	//)x paréntesis de cierre seguido de algo que no es operador o ")".
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
	f = parser.parsea(tokenizador.obtenerTokens("(x^4+(16*x+--3)^3-74*x+16)/2^3"));
	f = parser.parsea(tokenizador.obtenerTokens("x^2+-3*x+(15^2-2)"));
    }

}
