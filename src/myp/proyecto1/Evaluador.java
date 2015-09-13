package myp.proyecto1;

import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;

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
     * @param cola La cola que contiene la función en Notación Polaca Inversa.
     * @param x El valor de x sobre el cual se evaluará la función.
     * @return La función evaluada en x.
     * @throws ExcepcionExpresioninvalida si la función no es una expresión
     *         válida.
     */
    public double evalua(Cola<String> cola, double x){
	while(!cola.esVacia()){
	    String t = cola.saca();
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
			if(cos != 0.0)
			    pila.mete(1.0 / cos);
			else
			    pila.mete(Double.NaN);
		    }else if(t.equals("csc")){
			double sin = Math.sin(arg);
			if(sin != 0.0)
			    pila.mete(1.0 / sin);
			else
			    pila.mete(Double.NaN);
		    }else if(t.equals("cot")){
			double tan = Math.tan(arg);
			if(tan != 0.0)
			    pila.mete(1.0 / tan);
			else
			    pila.mete(Double.NaN);
		    }else{
			pila.mete(Math.sqrt(arg));
		    }
		}else
		    Utils.excepcion("La expresión introducida no es válida, la función \"" + t + "\" no tiene argumentos.");
	    }else if(Utils.esOperador(t)){
		if(t.equals("-")){
		    
		}
		try{
		    double arg2 = pila.saca();
		    doubel arg1 = pila.saca();
		    if(t.equals("+")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2))
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 + arg2);
		    }else if(t.equals("-")){
			pila.mete(arg1 - arg2);
		    }else if(t.equals("*")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2))
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 * arg2);
		    }else if(t.equals("/")){
			if(Double.isNaN(arg1) || Double.isNaN(arg2) ||
			   arg2 == 0.0)
			    pila.mete(Double.NaN);
			else
			    pila.mete(arg1 / arg2);
		    }else{
			pila.mete(Math.pow(arg1, arg2));
		    }
		}catch(NoSuchElementException nsee){
		    Utils.excepcion("La expresión introducida no es válida, el opeeador \"" + t + "\" no tiene el número corecto de operandos.");
		}
	    }
	}
	double resultado = pila.saca();
	if(!pila.esVacia())
	    Utils.excepcion("La expresión introducida no es válida, tiene demasaidos elementos.");
	return resultado;
    }
    
}
