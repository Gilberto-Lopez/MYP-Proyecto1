package myp.proyecto1;

/* Clase con métodos estáticos con distintas utilidades en este paquete. */
class Utils{

    /* Nos dice si un token es una varaible ("x"). */
    public static boolean esVariable(String t){
	return t.equals("x");
    }
    
    /* Nos dice si un token es un número. */
    public static boolean esNumero(String t){
	try{
	    double d = Double.parseDouble(t);
	}catch(NumberFormatException nfe){
	    return false;
	}
	return true;
    }

    /* Nos dice si un token es una función. */
    public static boolean esFuncion(String t){
	return t.equals("sin") || t.equals("cos") || t.equals("tan")
	    || t.equals("sec") || t.equals("csc") || t.equals("cot")
	    || t.equals("sqr");
    }

    /* Nos dice si un token es un operador. */
    public static boolean esOperador(String t){
	return t.equals("+") || t.equals("-") || t.equals("*")
	    || t.equals("/") || t.equals("^");
    }

    /* Compara la precedencia de dos operadores o operadores y funciones:
     * Operador     Precedencia:
     * Función      1
     * "+", "-"     2
     * "*", "/"     3
     * "^"          4
     */
    public static int comparaPrecedencia(String o1, String o2){
	int o1p = 0;
	int o2p = 0;
	if(esFuncion(o1))
	    o1p = 1;
	else if(o1.equals("+") || o1.equals("-"))
	    o1p = 2;
	else if(o1.equals("*") || o1.equals("/"))
	    o1p = 3;
	else
	    o1p = 4;
	if(esFuncion(o2))
	    o2p = 1;
	else if(o2.equals("+") || o2.equals("-"))
	    o2p = 2;
	else if(o2.equals("*") || o2.equals("/"))
	    o2p = 3;
	else
	    o2p = 4;
	return o1p - o2p;
    }

    /* Lanza una ExcepcionExpresionInvalida con el mensaje dado. */
    public static void excepcion(String mensaje){
	throw new ExcepcionExpresionInvalida(mensaje);
    }

}
