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
	Lista<String> f22 =
	    parser.parsea(tokenizador.obtenerTokens("x^9-10/3"));
	String[] f2t = {"x","9","^","10","3","/","-"};
	for(String t : f2)
	    Assert.assertTrue(t.equals(f2t[i++]));
	Assert.assertTrue(f2.equals(f22));
	//f3(x) = cos(-x)
	i = 0;
	Lista<String> f3 = parser.parsea(tokenizador.obtenerTokens("cos(-x)"));
	String[] f3t = {"0.0","x","-","cos"};
	for(String t : f3)
	    Assert.assertTrue(t.equals(f3t[i++]));
	//(3)
	Lista<String> fs = parser.parsea(tokenizador.obtenerTokens("(3)"));
	Assert.assertTrue(fs.getLongitud() == 1 && fs.getPrimero().equals("3"));
	fs.limpia();
	//(-3)
	String[] fst = {"0.0","3","-"};
	fs = parser.parsea(tokenizador.obtenerTokens("(-3)"));
	i = 0;
	for(String t : fs)
	    Assert.assertTrue(t.equals(fst[i++]));
	fs.limpia();
	//Casos más grandes sin falla
	fs = parser.parsea(tokenizador.obtenerTokens("(x^4+(16*x+--3)^3-74*x+16)/2^3"));
	fs.limpia();
	fs = parser.parsea(tokenizador.obtenerTokens("x^2+-3*x+(15^2-2)"));
	fs.limpia();
	fs = parser.parsea(tokenizador.obtenerTokens("-3*x^4+3.1415*cos(2.82+x)-tan(x^2^3)/(74+16*x)+12.89778*x-16^2/--28"));
	fs.limpia();
	//Caso especial
	//----3
	i = 0;
	fs = parser.parsea(tokenizador.obtenerTokens("----3"));
	String[] fstt = {"0.0","0.0","0.0","3","-","-","-","-"};
	for(String t : fs)
	    Assert.assertTrue(t.equals(fstt[i++]));
	fs.limpia();
	entradasNoManejables();
    }

    /* Método para probar por separado los casos no manejables. */
    private void entradasNoManejables(){
	Lista<String> f;
	//Entradas no manejables
	//() dos paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("()"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//*+ dos operadores juntos, salvo "-".
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("*+"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//)cos(x) paréntesis de cierre seguido de una función.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens(")cos(x)"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}	
	//cosx función sin paréntesis para su argumento.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("cosx"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//(3*) operador y paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("(3*)"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//(*3) paréntesis y operador.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("(*3)"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//x( variable o número seguido de paréntesis.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("x("));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//xcos(x) variabele seguida de operador.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("xcos(x)"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//)x paréntesis de cierre seguido de algo que no es operador o ")".
	try{
	    f = parser.parsea(tokenizador.obtenerTokens(")x"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//+ operador sin operandos.
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("+"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	//Casos más grandes con fallas
	//((x^4+(16*x+--3)^3-74*x+16)/2^3
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("((x^4+(16*x+--3)^3-74*x+16)/2^3"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("x^2+-3*x(cos(x))+(15^2-2)"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
	try{
	    f = parser.parsea(tokenizador.obtenerTokens("-3*x^4+3.1415*cos(2.82+x)-tanx^2^3/(74+16*x)+12.89778*x-16^2/--28"));
	    Assert.fail();
	    f.limpia();
	}catch(ExcepcionExpresionInvalida eei){}
    }
}
