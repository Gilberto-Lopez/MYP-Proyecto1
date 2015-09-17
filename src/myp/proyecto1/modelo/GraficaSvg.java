package myp.proyecto1.modelo;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GraficaSvg{

    private String SVG;
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
    }

    public void generaSVG(){
	double dx = (x2-x1)/ancho;
	double dy = (y1-y2)/alto;
	SVG = "<svg xmlns='http://www.w3.org/2000/svg' version='1.1' height='"
	    + alto + "' width='"+ ancho + "'>\n<g>";
	double ceroX = (0-x1-1)*dx;
	double ceroY = (0-y2-1)*dy;
	String ejeX = "";
	String ejeY = "";
	if(ceroX >= 0)
	    ejeX += "<line x1='0' x2='"+(ancho-1)+"' y1='"+ceroY+"' y2='"+ceroY+"' style='stroke:black;stroke-width:1' />\n";
	else
	    ceroX = x1;
	if(ceroY >= 0)
	    ejeY += "<line x1='"+ceroX+"' x2='"+ceroX+"' y1='0' y2='"+(alto-1)+"' style='stroke:black;stroke-width:1' />\n";
	else
	    ceroY = y2;
	SVG += ejeX + ejeY;
	Iterator<double[]> iterador = puntos.iterator();
	double[] tmp = puntos.getPrimero();
	if(iterador.hasNext())
	    tmp = iterador.next();
	while(iterador.hasNext()){
	    double[] p = iterador.next();
	    SVG += "<line x1='" + (ceroY + tmp[0]*dx) +
		"' y1='" + (ceroX - tmp[1]*dy) +
		"' x2='" + (ceroY + p[0]*dx) +
		"' y2='" + (ceroX - p[1]*dy) +
		"' style='stroke:blue;stroke-width:2' />\n";
	    tmp = p;
	}
	SVG += "</g>\n</svg>";
    }

    public void escribeSVG(File archivo) throws IOException{
	if(SVG == null)
	    return;
	BufferedWriter bw = null;
	bw = new BufferedWriter(new FileWriter(archivo));
	bw.write(SVG);
	bw.close();
    }

}
