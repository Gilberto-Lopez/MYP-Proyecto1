package myp.proyecto1.test;

import java.util.Random
import myp.proyecto1.ArbolLexico;
import myp.proyecto1.Tokens
import myp.proyecto1.ExcepcionExpresionInvalida;
import mx.unam.ciencias.edd.Lista;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para prueba unitarias de la clase {@link ArbolLexico}.
 */
public class TestArbolLexico{
    
    private Random random;
    private ArbolLexico arbol;

    public TestArbolLexico(){
	random = new Random();
	arbol = new ArbolLexico();
    }

    /**
     * Prueba unitaria para {@link ArbolLexico#generaArbol}.
     */
    @Test public void TestGeneraArbol(){

    }

    /**
     * Prueba unitaria para {@link ArbolLexico#evaluaArbol}.
     */
    @Test public void testEvaluaArbol(){

    }
}
