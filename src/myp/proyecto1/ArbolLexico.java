package myp.proyecto1;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para generar árboles léxicos de funciones a partir de listas de tokens
 * (fichas), donde cada token es una expresión dada por la gramática
 * especificada por el proyecto.
 */
public class ArbolLexico{

    /* ArbolBinarioSeleccion con el cual se creará el árbol léxico para
     * evaluar las funciones. */
    private ArbolBinarioSeleccion<String> arbol;
    /* Variable para contar los paréntesis en el árbol y verificar que estén
     * balanceados. */
    private int p;

    /**
     * Constructor de árboles léxicos.
     */
    public ArbolLexico(){
	ArbolBinarioSeleccion<String> arbol = new ArbolBinarioSeleccion<>();
	p = 0;
    }

    /**
     * Regresa el ArbolBinarioSeleccion que constituye el árbol léxico.
     * @return El ArbolBinarioSeleccion que constituye el árbol léxico.
     */
    public ArbolBinarioSleccion<String> getArbol(){
	return arbol;
    }

    /**
     * Genera el árbol léxico de una función a partir de una lista de tokens.
     * @param tokens La lista de tokens.
     * @throws ExcepcionExpresionInvalida en caso de que la lista de tokens no
     *         constituya una expresión válida.
     */
    public void generaArbol(Lista<String> tokens){

    }

    /**
     * Evalúa el árbol léxico (la función) en un valor dado x.
     * @param x El valor sobre el cual evaluar.
     * @return La función evaluada en x.
     */
    public double evaluaArbol(double x){

    }
    
}
