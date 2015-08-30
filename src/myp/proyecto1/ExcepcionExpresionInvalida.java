package myp.proyecto1;

/**
 * Clase para excepciones de expresiones inválidas.
 */
public class ExcepcionExpresionInvalida extends Eception{

    /**
     * Constructor vacío.
     */
    public ExcepcionExpresionInvalida(){
	super();
    }

    /**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje Un mensaje que verá el usuario cuando ocurra la excepción.
     */
    public ExcepcionExpresionInvalida(String mensaje){
	super(mensaje);
    }
    
}
