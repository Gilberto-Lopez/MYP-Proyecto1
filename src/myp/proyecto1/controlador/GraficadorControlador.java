package myp.proyecto1.controlador;

import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;
import myp.proyecto1.modelo.GraficadorModelo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.SVGPath;

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

    public void graficaFuncion(double ancho, double alto, double x1,
			       double x2, double y1, double y2){
	if(gc == null)
	    return;
	double dx = (x2-x1)/ancho;
	for(int i = 0; i < ancho; i++)
	    modelo.evaluaFuncion(x1 + dx*i);
	modelo.initGraficadorSvg(ancho, alto, x1, x2, y1, y2);
	gc.appendSVGPath(modelo.getSVGPath());
    }

    public void generaSvg(File archivo, double ancho, double alto, double x1,
			  double x2, double y1, double y2)
	throws IOException{
	modelo.generaGraficaSvg(archivo);
    }

    public void limpia(){
	modelo.limpia();
	gc = null;
    }

}
