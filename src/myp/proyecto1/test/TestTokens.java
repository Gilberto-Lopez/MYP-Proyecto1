package myp.proyecto1.test;

import myp.proyecto1.Tokens;
import mx.unam.ciencias.edd.Lista;

public class TestTokens{

    @Test public void testObtenerTokens(){
	Tokens tokenizador = new Tokens();
	Lista<String> tokens = tokenizador.obtenerTokens("( ^ ( + ( cos x ) ( sen x ) ) 2 )");
	int length = lista.getLongitud();
	assertTrue(length == 15);
	String[] tokensManual = {"(","^","(","+","(","cos","x",")","(","sen","x",")",")","2",")"};
	int i = 0;
	for(String token : tokens)
	    assertTrue(token.equals(tokensManual[i++]));
    }	
    
}
