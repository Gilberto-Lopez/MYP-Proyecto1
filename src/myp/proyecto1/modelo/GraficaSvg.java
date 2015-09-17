package myp.proyecto1.modelo;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GraficaSvg{

    private String SVGpath;
    private String ejes;
    private Lista<double[]> puntos;
    private double ancho;
    private double alto;
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    
    public GraficaSvg(Lista<double[]> puntos, double ancho, double alto,
		      double x1, double x2, double y1, double y2){
	this.puntos = puntos;
	this.ancho = ancho;
	this.alto = alto;
	this.x1 = x1;
	this.x2 = x2;
	this.y1 = y1;
	this.y2 = y2;
	ejes = "";
    }

    public void generaSVGpath(){
	double dx = (x2-x1)/ancho;
	double dy = (y1-y2)/alto;
	if(x1 < 0 && x2 > 0){ //eje y
	    double cero = (0-x1)/dx;
	    ejes += "<line x1='" + cero +
		"' y1='" + 0 +
		"' x2='" + cero +
		"' y2='" + (alto - 1) +
		"' style='stroke:black;stroke-width:2' />\n";
	}
	if(y1 < 0 && y2 > 0){ //eje x
	    double cero = -y2/dy;
	    ejes += "<line x1='" + 0 +
		"' y1='" + cero +
		"' x2='" + (ancho - 1) +
		"' y2='" + cero +
		"' style='stroke:black;stroke-width:2' />\n";
	}
	SVGpath += "<path stroke='red' stroke-width='3' d='";
	Iterator<double[]> iterador = puntos.iterator();
	int i = 0;
	while(iterador.hasNext()){
	    double[] p = iterador.next();
	    if(Double.isNaN(p[1]) || Double.isInfinite(p[1]))
		SVGpath += "M" + String.valueOf(dx*i) + " " +
		    String.valueOf(p[1]/dy);
	    else
		SVGpath += "L" + String.valueOf(dx*i) + " " +
		    String.valueOf(p[1]/dy);
	    i++;
	}
	SVGpath += "'/>\n";
    }

    public String getPath(){
	return SVGpath;
    }
    
    public void escribeSVGpath(File archivo) throws IOException{
	if(SVGpath == null)
	    return;
	BufferedWriter bw = null;
	bw = new BufferedWriter(new FileWriter(archivo));
	String svgFinal =
	    "<svg xmlns='http://www.w3.org/2000/svg' version='1.1' height='"
	    + alto + "' width='"+ ancho + "'>\n<g>\n"
	    + ejes
	    + SVGpath
	    + "</g>\n</svg>";
	bw.write(svgFinal);
	bw.close();
    }

}
