package myp.proyecto1.modelo;

import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.IteradorLista;
import java.util.NoSuchElementException;

/**
 * Clase para evaluar ecuaciones matemáticas (funciones) en Notación Polaca
 * Inversa.
 */
public class Evaluador{

    /* Pila de valores. */
    private Pila<Double> pila;

    /**
     * Constructor sin parámetros.
     */
    public Evaluador(){
	pila = new Pila<Double>();
    }

    /**
     * Evalua la ecuación matemática (función) dada como entrada en Notación
     * Pola Inversa, sobre un valor de x.
     * @param lista La lista que contiene la función en Notación Polaca Inversa.
     * @param x El valor de x sobre el cual se evaluará la función.
     * @return La función evaluada en x.
     * @throws ExcepcionExpresioninvalida si la función no es una expresión
     *         válida.
     */
    public double evalua(Lista<String> lista, double x){
	IteradorLista<String> iterador = lista.iteradorLista();
	while(iterador.hasNext()){
	    String t = iterador.next();
	    if(Utils.esVariable(t))
		pila.mete(x);
	    else if(Utils.esNumero(t))
		pila.mete(Double.parseDouble(t));
	    else if(Utils.esFuncion(t)){
		if(!pila.esVacia()){
		    double arg = pila.saca();
		    if(t.equals("sin")){
			pila.mete(Math.sin(arg));
		    }else if(t.equals("cos")){
			pila.mete(Math.cos(arg));			
		    }else if(t.equals("tan")){
			pila.mete(Math.tan(arg));
		    }else if(t.equals("sec")){
			double cos = Math.cos(arg);
			if(cos == 0.0 || Double.isNaN(cos) )
			    pila.mete(Double.NaN);
			else
			    pila.mete(1.0 / cos);
		    }else if(t.equals("csc")){
			double sin = Math.sin(arg);
			if(sin == 0.0 || Double.isNaN(sin))
			    pila.mete(Double.NaN);
			else
			    pila.mete(1.0 / sin);
		    }else if(t.equals("cot")){
			double tan = Math.tan(arg);
			if(tan == 0.0 || Double.isNaN(tan))
			    pila.mete(Double.NaN);
			else
			    pila.mete(1.0 / tan);
		    }else{ //t.equals("sqr")
			pila.mete(Math.sqrt(arg));
		    }
		}else
		    Utils.excepcion("La expresión introducida no es válida, la función \"" + t + "\" no tiene argumentos.");
	    }else if(Utils.esOperador(t)){
		if(!pila.esVacia()){
		    double arg2 = pila.saca();
		    if(pila.esVacia() && t.equals("-")){
			pila.mete(-arg2);
			continue;
		    }
		    if(pila.esVacia())
			Utils.excepcion("La expresión introducida no es válida, el operador \"" + t + "\" no tiene el número correcto de operandos.");
		    double arg1 = pila.saca();
		    if(t.equals("+")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2))
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 + arg2);
		    }else if(t.equals("-")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2))
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 - arg2);
		    }else if(t.equals("*")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2))
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 * arg2);
		    }else if(t.equals("/")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2))
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 / arg2);
		    }else{
			pila.mete(Math.pow(arg1, arg2));
		    }

		}else{
		    Utils.excepcion("La expresión introducida no es válida, el operador \"" + t + "\" no tiene el número corecto de operandos.");
		}
	    }
	}
	double resultado = pila.saca();
	if(!pila.esVacia())
	    Utils.excepcion("La expresión introducida no es válida, tiene demasaidos elementos.");
	return resultado;
    }
    
}
