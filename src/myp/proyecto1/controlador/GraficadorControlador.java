package myp.proyecto1.controlador;

import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;
import myp.proyecto1.modelo.GraficadorModelo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Clase controlador para el Graficador de funciones.Esta clase corresponde al
 * controlador del proyecto, siguiendo el Patrón de diseñoo <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller">MVC</a>.
 */
public class GraficadorControlador{

    private GraficadorModelo modelo;
    private GraphicsContext gc;

    /**
     * Constructor sin parámetros. Inicializa un GraficadorControlador para 
     * comunicar {@link GraficadorVista} y {@link GraficadorModelo}.
     */
    public GraficadorControlador(){
	modelo = new GraficadorModelo();
    }

    /**
     * Agrega el GraphicsContext con el cual se dibuja la gráfica en la Vista.
     * @param gc El GraphicsContext.
     */
    public void setGraphicsContext(GraphicsContext gc){
	this.gc = gc;
    }

    /**
     * Asigna la función a graficar, recibida del usuario en la Vista.
     * @param funcion La función a graficar.
     */
    public void setFuncion(String funcion){
	modelo.setFuncion(funcion);
    }    

    /**
     * Genera la gráfica de la función asignada con {@link #setFuncion}, en la
     * región [x1,x2]x[y1,y2]. La gráfica de la función se genera para
     * graficarse en un espacio de dimensiones ancho x alto.
     * @param ancho El ancho del espacio donde se graficará la función.
     * @param alto El alto del espacio donde se grafiacrá la función.
     * @param x1 El límite inferior del intervalo [x1,x2].
     * @param x2 El límite superior del intervalo [x1,x2].
     * @param y1 El límite inferior del intervalo [y1,y2].
     * @param y2 El límite superior del intervalo [y1,y2].
     */
    public void graficaFuncion(double ancho, double alto, double x1,
			       double x2, double y1, double y2){
	if(gc == null)
	    return;
	double dx = (x2-x1)/ancho;
	double dy = (y1-y2)/alto;
	for(int i = 0; i < ancho; i++)
	    modelo.evaluaFuncion(x1 + dx*i);
	modelo.initGraficadorSvg(ancho, alto, x1, x2, y1, y2);
	gc.setStroke(Color.BLACK);
	gc.strokeLine(-x1/dx, 0, -x1/dx, alto);
	gc.strokeLine(0, -y2/dy, ancho, -y2/dy);
	gc.appendSVGPath(modelo.getSVGPath());
    }

    /**
     * Genera la gráfica de la función y la escribe como un archivo en formato
     * SVG, en el archivo representado por el objeto File <code>archivo</code>
     * que se da como argumento. Antes debe de generarse la gráfica llamando
     * el método {@link #graficaFuncion}.
     * @param archivo El archivo en el cual se escribirá el SVG.
     * @throws IOException en caso de un fallo de E/S.
     */
    public void generaSvg(File archivo)	throws IOException{
	modelo.generaGraficaSvg(archivo);
    }

    /**
     * Limpia el controlador. Su uso es para dejar el controlador listo para
     * generar la gráfica de una nueva función.
     */
    public void limpia(){
	modelo.limpia();
	gc = null;
    }

}
