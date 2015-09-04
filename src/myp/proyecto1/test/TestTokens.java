package myp.proyecto1.test;

import java.util.Random;
import myp.proyecto1.Tokens;
import myp.proyecto1.ExcepcionExpresionInvalida;
import mx.unam.ciencias.edd.Lista;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para pruebas unitarias de la clase {@link Tokens}.
 */
public class TestTokens{

    private Random random;
    private Tokens tokenizador;
    private final String[] expresiones = {
	"sin", "cos", "csc", "tan", "sec", "cot", "sqr", "+", "-", "*", "/",
	"^", "(", ")", "x"
    };
    private final String[] numerosValidos = {
	"12345.67890", "232324342.", ".452656462"
    };
    private final String[] numerosInvalidos = {
	"123.456.789", "0.0.0.0", "255.255.255.0"
    };

    /**
     * Crea un tokenizador para la prueba unitaria del método obtenerTokens().
     */
    public TestTokens(){
	random = new Random();
	tokenizador = new Tokens();
    }
    
    /**
     * Prueba unitaria para {@link Tokens#obtenerTokens}.
     */
    @Test public void testObtenerTokens(){
	int i;
	//Prueba fija para números.
	for(String n : numerosValidos){
	    Lista<String> nums = tokenizador.obtenerTokens(n);
	    Assert.assertTrue(nums.getLongitud() == 1);
	    Assert.assertTrue(nums.getPrimero().equals(n));
	}
	for(String n : numerosInvalidos){
	    try{
		Lista<String> num = tokenizador.obtenerTokens(n);
		Assert.fail();
	    }catch(ExcepcionExpresionInvalida eei){}
	}
	
	//Prueba aleatoria para números.
	String[] aleatorios = numerosAleatorios();
	String aleatoriosValidos = "";
	String aleatoriosInvalidos = "";
	for(String n : aleatorios){
	    aleatoriosValidos += n + " ";
	    aleatoriosInvalidos += n;
	}
	Lista<String> aleatoriosLista = tokenizador.obtenerTokens(aleatoriosValidos);
	Assert.assertTrue(aleatoriosLista.getLongitud() == aleatorios.length);
	i = 0;
	for(String n : aleatoriosLista)
	    Assert.assertTrue(n.equals(aleatorios[i++]));
	try{
	    Lista<String> aleatoriosLista2 = tokenizador.obtenerTokens(aleatoriosInvalidos);
	    Assert.fail();
	}catch(ExcepcionExpresionInvalida eei){}
		
	//Prueba fija para cadenas.
	String expr = "";
	String expr2 = "";
	int r = random.nextInt(100);
	for(i = 0; i < r; i++){
	    int rand = random.nextInt(15);
	    expr += expresiones[rand];
	    expr2 += expresiones[rand] + " ";
	}
	Lista<String> tokens = tokenizador.obtenerTokens(expr);
	Lista<String> tokens2 = tokenizador.obtenerTokens(expr2);
	Assert.assertTrue(tokens.equals(tokens2));

	//Prueba aleatoria para cadenas.
	for(i = random.nextInt(50); i > 0; i--){
	    try{
		Lista<String> tokensAleatorios = tokenizador.obtenerTokens(cadenaAleatoria());
		Assert.fail();
	    }catch(ExcepcionExpresionInvalida eei){}
	}
    }

    /* Genera un arreglo con números aleatorios válidos. */
    private String[] numerosAleatorios(){
	int r = random.nextInt(50);
	String[] numerosAleatorios = new String[r];
	for(int i = 0; i < r; i++){
	    String n = String.valueOf(random.nextDouble()*random.nextInt(100));
	    numerosAleatorios[i] = n;
	}
	return numerosAleatorios;
    }

    /* Genera una cadena aleatoria. */
    private String cadenaAleatoria(){
	int r = random.nextInt(50);
	byte[] bytes = new byte[r];
	random.nextBytes(bytes);
	String cadena = "";
	for(int i = 0; i < r; i++){
	    char c = (char)(bytes[i] << (random.nextInt(9)));
	    cadena += c;
	}
	return cadena;
    }
}
