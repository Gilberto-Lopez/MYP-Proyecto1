package myp.proyecto1;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para obtener tokens (fichas) a partir de un String donde cada token es
 * una expresión dada por la gramática especificada por el proyecto.
 */
public class Tokens{

    /**
     * Método que recibe como entrada un String para posteriormente dividirlo
     * en tokens y colocarlos en una lista, siempre respetando el orden en que
     * aparecen en la cadena de entrada.
     * @param cadena Un String a partir del cual obtendremos los tokens.
     * @return Una lista con los tokens del String <code>cadena</code>.
     * @throws ExcepcionExpresionInvalida en caso de que <code>cadena</code> sea
     *         una cadena vacía, una referencia null o es una expresión
     *         inválida.
     */
    public Lista<String> obtenerTokens(String cadena){
	if(cadena == null || cadena.equals(""))
	    Utils.excepcion("Asegúrese de proporcionar una expresión.");
	char[] caracteres = cadena.toCharArray();
	Lista<String> lista = new Lista<>();
	for(int i = 0; i < caracteres.length; i++){
	    char c = caracteres[i];
	    if(c == ' ')
		continue;
	    if(c == 'x' || c == '+' || c == '-' || c == '*' || c == '/' ||
	       c == '^' || c == '(' || c == ')')
		lista.agrega("" + c);
	    else if((c >= '0' && c <= '9') || c == '.'){
		String num = "" + c;
		int j = i+1;
		int p = (c == '.') ? 1 : 0;
		while(j < caracteres.length &&
		      ((caracteres[j] >= '0' && caracteres[j] <= '9') ||
		       caracteres[j] == '.')){
		    if(caracteres[j] == '.')
			p++;
		    num += caracteres[j];
		    if(p > 1)
			Utils.excepcion("Expresión inválida: \"" + num + "\" -- Contiene dos puntos para decimales.");
		    j++;
		}
		i = j-1;
		lista.agrega(num);
	    }else if(c == 's' || c == 'c' || c == 't'){
		if(i+2 >= caracteres.length)
		    Utils.excepcion("\"" + c + caracteres[i+1] + "\" no es una expresión válida.");
		char c1 = caracteres[i+1];
		char c2 = caracteres[i+2];
		if(c == 's' && c1 == 'i' && c2 == 'n')
		    lista.agrega("sin");
		else if(c == 's' && c1 == 'q' && c2 == 'r')
		    lista.agrega("sqr");
		else if(c == 's' && c1 == 'e' && c2 == 'c')
		    lista.agrega("sec");
		else if(c == 'c' && c1 == 'o' && c2 == 's')
		    lista.agrega("cos");
		else if(c == 'c' && c1 == 's' && c2 == 'c')
		    lista.agrega("csc");
		else if(c == 'c' && c1 == 'o' && c2 == 't')
		    lista.agrega("cot");
		else if(c == 't' && c1 == 'a' && c2 == 'n')
		    lista.agrega("tan");
		else
		    Utils.excepcion("\"" + c + c1 + c2 + "\" no es una expresión válida.");
		i += 2;
	    }else
		Utils.excepcion("\"" + cadena + "\" no es una expresión válida. ");
	}
	return lista;
    }
    
}
