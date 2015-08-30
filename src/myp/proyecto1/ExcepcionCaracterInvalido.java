package myp.proyecto1;

/**
 * Clase para excepciones de caracteres inválidos.
 */
public class ExcepcionCaracerInvalido extends Exception{

    /**
     * Constructor vacío.
     */
    public ExcepcionCaracterInvalido(){
	super();
    }

    /**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje Un mensaje que verá el usuario cuando ocurra la excepción.
     */
    public ExcepcionCaracterInvalido(String mensaje){
	super(mensaje);
    }

}
