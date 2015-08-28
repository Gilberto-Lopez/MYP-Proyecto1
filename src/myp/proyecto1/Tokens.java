package myp.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Tokens{
    
    public Lista<String> obtenerTokens(String cadena){
	char[] caracteres = cadena.replaceAll(" ","").toCharArray();
	Lista<String> lista = new Lista<>();
	for(int i = 0; i < caracteres.length; i++){
	    char c = caracteres[i];
	    if(c == 'x' || c == '+' || c == '-' || c == '*' || c == '/' ||
	       c == '^' || c == '(' || c == ')')
		lista.agrega(new String(c));
	    else{
		char c1 = caracteres[i+1];
		char c2 = caracteres[i+2];
		if(c == 's' && c1 == 'i' && c2 == 'n')
		    lista.agrega("sin");
		else if(c == 's' && c1 == 'q' && c2 == "r")
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
		i += 2;
	    }
	}
	return lista;
    }
    
}
