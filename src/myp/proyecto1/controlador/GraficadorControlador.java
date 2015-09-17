package myp.proyecto1.controlador;

import java.io.IOException;
import myp.proyecto1.modelo.GraficadorModelo;
import myp.proyecto1.vista.GraficadorVista;
import javafx.scene.canvas.GraphicsContext;

public class GraficadorControlador{

    private GraficadorModelo modelo;
    private GraphicsContext gc;

    public GraficadorControlador(){
	modelo = new GraficadorModelo();
    }

    public void setGraphicsContext(GraphicsContext gc){
	this.gc = gc;
    }

    public void setFuncion(String funcion){
	modelo.setFuncion(funcion);
    }    

    public void graficaFuncion(){
	if(gc == null)
	    return;
	
    }

    public void generaSvg(File archivo, double ancho, double alto, double x1,
			  double x2, double y1, double y2)
	throws IOException{
	modelo.initGraficadorSvg(ancho, alto, x1, x2, y1, y2)
	modelo.generaGraficaSvg(archivo);
    }

    public void limpia(){
	modelo.limpia();
	gc = null;
    }

}
