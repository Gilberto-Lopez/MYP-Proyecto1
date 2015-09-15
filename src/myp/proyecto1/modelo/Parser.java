package myp.proyecto1;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.IteradorLista;
import mx.unam.ciencias.edd.Pila;

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
     * Notación Polaca Inversa y la devuelve dentro de una lista.
     * @param tokens La lista de tokens de la ecuación matemática.
     * @return Una lista con la ecuación matemática en Notación Polaca Inversa.
     * @throws ExcepcionExpresionInvalida en caso de que la ecuación no sea una
     *         expresión válida.
     */
    public Lista<String> parsea(Lista<String> tokens){
	Lista<String> lista = new Lista<>();
	IteradorLista<String> iterador = tokens.iteradorLista();
	while(iterador.hasNext()){
	    String t = iterador.next();
	    if(Utils.esVariable(t) || Utils.esNumero(t)){
		lista.agrega(t);
		if(iterador.hasNext()){
		    String sig = iterador.next();
		    if(!sig.equals(")") && !Utils.esOperador(sig))
			Utils.excepcion("\"" + t + sig +
					"\" no es una expresión válida.");
		    iterador.previous();
		}
	    }else if(Utils.esFuncion(t)){
		pila.mete(t);
		if(iterador.hasNext()){
		    String sig = iterador.next();
		    if(!sig.equals("("))
			Utils.excepcion("\"" + t + sig +
					"\" no es una expresión válida.");
		    iterador.previous();
		}else
		    //Se acabaron los tokens y t no tiene argumentos.
		    Utils.excepcion("\"" + t + "\" no tiene argumentos.");
	    }else if(Utils.esOperador(t)){
		while(!pila.esVacia() && !t.equals("^") &&
		      Utils.esOperador(pila.mira()) &&
		      Utils.comparaPrecedencia(t, pila.mira()) <= 0)
		    lista.agrega(pila.saca());
		pila.mete(t);
		if(iterador.hasNext()){
		    String sig = iterador.next();
		    if((Utils.esOperador(sig) && !sig.equals("-")) ||
		       sig.equals(")"))
			Utils.excepcion("\"" + t + sig +
					"\" no es una expresión válida.");
		    iterador.previous();
		}else
		    //Se acabaron los tokens y t no tiene operando derecho.
		    Utils.excepcion("\"" + t + "\" no tiene el número correcto de operandos.");
		agregaMenos(iterador, lista);
	    }else if(t.equals("(")){
		pila.mete(t);
		if(iterador.hasNext()){
		    String sig = iterador.next();
		    if((Utils.esOperador(sig) && !sig.equals("-")) ||
		       sig.equals(")"))
			Utils.excepcion("\"(" + sig +
					"\" no es una expresión válida.");
		    iterador.previous();
		}else
		    //Se acabaron los tokens y t no tiene una pareja ")".
		    Utils.excepcion("Los paréntesis no están bien balanceados.");
		agregaMenos(iterador, lista);
	    }else{ //t.equals(")")
		while(!pila.esVacia() && !pila.mira().equals("("))
		    lista.agrega(pila.saca());
		if(!pila.esVacia())
		    pila.saca();
		else
		    //Se acabó la pila sin encontrar un paréntesis "("
		    Utils.excepcion("Los paréntesis no están bien balanceados.");
		if(!pila.esVacia() && Utils.esFuncion(pila.mira()))
		    lista.agrega(pila.saca());
		if(iterador.hasNext()){
		    String sig = iterador.next();
		    if(!Utils.esOperador(sig) && !sig.equals(")"))
			Utils.excepcion("\"" + t + sig +
					"\" no es una expresión válida.");
		    iterador.previous();
		}
	    }
	}
	while(!pila.esVacia()){
	    String t = pila.saca();
	    if(t.equals("(") || t.equals(")"))
		Utils.excepcion("Los paréntesis no están bien balanceados.");
	    lista.agrega(t);
	}
	return lista;
    }

    /* Verifica si hay una secuencia de menos "-" con un operando.
     * Ejemplo: ---x. */
    private void agregaMenos(IteradorLista<String> iterador,
			     Lista<String> lista){
	//Sabemos que el i-ésimo más un token existe.
	int flag = 0;
	while(iterador.hasNext()){
	    String t = iterador.next();
	    if(t.equals("-")){
		flag++;
		lista.agrega("0.0");
		pila.mete("-");
	    }else if(t.equals(")") && flag != 0){
		Utils.excepcion("\"-)\" no es una expresión válida.");
	    }else{
		iterador.previous();
		break;
	    }
	}
	if(!iterador.hasNext() && flag != 0)
	    //Se acabaron los tokens y hay un "-" sin operando derecho.
	    Utils.excepcion("\"-\" no tiene el número correcto de operandos.");
    }
    
}
