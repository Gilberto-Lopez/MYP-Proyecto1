package myp.proyecto1;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import java.util.NoSuchElementException;

/**
 * Clase para analizar ecuaciones matemáticas en
 * <a href="https://en.wikipedia.org/wiki/Infix_notation">
 * Notación de infijo</a>, y convertirla en
 * <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">
 * Notación Polaca Inversa (RPN)</a> 
 */
public class Parser{

    /* Pila de operadores. */
    private Pila<String> pila;

    /**
     * Constructor sin parámetros.
     */
    public Parser(){
	pila = new Pila<String>();
    }

    /**
     * Analiza (parsing) la ecuación matématica (función) , la cual ya ha sido
     * divida en tokens (fichas) y es recibida como una lista, la convierte a
     * Notación Polaca Inversa y la devuelve dentro de una cola.
     * @param tokens La lista de tokens de la ecuación matemática.
     * @return Una cola con la ecuación matemática en Notación Polaca Inversa.
     * @throws ExcepcionExpresionInvalida en caso de que la ecuación no sea una
     *         expresión válida.
     */
    public Cola<String> parsea(Lista<String> tokens){
	Cola<String> cola = new Cola<>();
	int l = tokens.getLongitud();
	for(int i = 0; i < l; i++){
	    String t = tokens.get(i);
	    if(Utils.esVariable(t))
		cola.mete(t);
	    else if(Utils.esNumero(t))
		cola.mete(t);
	    else if(Utils.esFuncion(t))
		pila.mete(t);
	    else if(Utils.esOperador(t)){
		while(!pila.esVacia() && Utils.esOperador(pila.mira()) &&
		      ((t.equals("^") &&
			Utils.comparaPrecedencia(t, pila.mira()) < 0) ||
		       (!t.equals("^") &&
			Utils.comparaPrecedencia(t, pila.mira()) <= 0))){
		    cola.mete(pila.saca());
		}
		pila.mete(t);
		while(tokens.get(i+1).equals("-")){
		    i++;
		    cola.mete("0.0");
		    pila.mete("-");
		}
	    }else if(t.equals("(")){
		if(tokens.get(i+1).equals(")"))
		    Utils.excepcion("() no es una expresión válida.");
		pila.mete(t);
		while(tokens.get(i+1).equals("-")){
		    i++;
		    cola.mete("0.0");
		    pila.mete("-");
		}
	    }else if(t.equals(")")){
		try{
		    while(!pila.mira().equals("("))
			cola.mete(pila.saca());
		}catch(NoSuchElementException nsee){
		    Utils.excepcion("Los paréntesis no están bien balanceados.");
		}
		pila.saca();
		try{
		    if(Utils.esFuncion(pila.mira()))
			cola.mete(pila.saca());
		}catch(NoSuchElementException nsee){
		    continue;
		}
	    }else
		Utils.excepcion("\"" + t + "\" no es una expresión válida.");
	}
	while(!pila.esVacia()){
	    String t = pila.saca();
	    if(t.equals("(") || t.equals(")"))
		Utils.excepcion("Los paréntesis no están bien balanceados.");
	    cola.mete(t);
	}
	return cola;
    }

}
