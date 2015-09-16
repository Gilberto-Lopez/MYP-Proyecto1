package myp.proyecto1.modelo;

/**
 * Clase con métodos estáticos con distintas utilidades para otras clases de
 * este paquete.
 */
public class Utils{

    /**
     * Nos dice si un token es una varaible ("x").
     * @param t El token.
     * @return <code>true</code> si el token es una variable, <code>false</code>
     *         en otro caso.
     */
    public static boolean esVariable(String t){
	return t.equals("x");
    }
    
    /**
     * Nos dice si un token es un número.
     * @param t El token.
     * @return <code>true</code> si el token es un número, <code>false</code>
     *         en otro caso.
     */
    public static boolean esNumero(String t){
	try{
	    double d = Double.parseDouble(t);
	}catch(NumberFormatException nfe){
	    return false;
	}
	return true;
    }

    /**
     * Nos dice si un token es una función. Funciones válidas:
     * <ul>
     * <li>sin : Función seno.</li>
     * <li>cos : Función coseno.</li>
     * <li>tan : Función tangente.</li>
     * <li>sec : Función secante.</li>
     * <li>csc : Función cosecante.</li>
     * <li>cot : Función cotangente.</li>
     * <li>sqr : Función raíz cuadrada (sqrt).</li>
     * </ul>
     * @param t El token.
     * @return <code>true</code> si el token es una función, <code>false</code>
     *         en otro caso.
     */
    public static boolean esFuncion(String t){
	return t.equals("sin") || t.equals("cos") || t.equals("tan")
	    || t.equals("sec") || t.equals("csc") || t.equals("cot")
	    || t.equals("sqr");
    }

    /**
     * Nos dice si un token es un operador. Operadores válidos:
     * <ul>
     * <li>+ : Operador de suma.</li>
     * <li>- : Operador de resta.</li>
     * <li>* : Operador de multiplicación.</li>
     * <li>/ : Operador de división.</li>
     * <li>^ : Operador de potenciación.</li>
     * </ul>
     * @param t El token.
     * @return <code>true</code> si el token es un operador, <code>false</code>
     *         en otro caso.
     */
    public static boolean esOperador(String t){
	return t.equals("+") || t.equals("-") || t.equals("*")
	    || t.equals("/") || t.equals("^");
    }

    /**
     * Compara la precedencia de dos operadores, o operador y función:
     * <table border="1">
     *  <tr>
     *   <td><strong>Operador</strong></td>
     *   <td><strong>Precedencia</strong></td>
     *  </tr>
     *  <tr>
     *   <td>Función</td>
     *   <td>1</td>
     *  </tr>
     *  <tr>
     *   <td>"+", "-"</td>
     *   <td>2</td>
     *  </tr>
     *  <tr>
     *   <td>"*", "/"</td>
     *   <td>3</td>
     *  </tr>
     *  <tr>
     *   <td>"^"</td>
     *   <td>4</td>
     *  </tr>
     * </table>
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

    /**
     * Lanza una {@link ExcepcionExpresionInvalida} con el mensaje dado. Si el
     * mensaje es la cadena vacía o <code>null</code> entonces la excepción
     * es lanzada sin un mensaje.
     * @param mensaje El mensaje para la excepción.
     * @throws ExcepcionExpresionInvalida.
     */
    public static void excepcion(String mensaje){
	if(mensaje == null || mensaje.equals(""))
	    throw new ExcepcionExpresionInvalida();
	throw new ExcepcionExpresionInvalida(mensaje);
    }

}
