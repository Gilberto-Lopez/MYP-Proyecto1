package myp.proyecto1.modelo;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase para generar graficadores de funciones. Crea las gráficas de las
 * funciones como Paths en formato SVG, así como también escribir un archivo
 * SVG con la gráfica de la función.
 */
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

    /**
     * Constructor de Graficadores GraficaSVG. Grafica la función dada una lista
     * de puntos y los intervalos [x1,x2] y [y1,y2] donde se grafica la función.
     * @param puntos Una lista de puntos (x,y) para graficar la función.
     * @param ancho El ancho del SVG.
     * @param alto El alto del SVG.
     * @param x1 El límite inferior del intervalo [x1,x2].
     * @param x2 El límite superior del intervalo [x1,x2].
     * @param y1 El límite inferior del intervalo [y1,y2].
     * @param y2 El límite superior del intervalo [y1,y2].
     */
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
	SVGpath = "";
    }

    /**
     * Genera la gráfica de la función.
     */
    public void generaSVGpath(){
	double dx = (x2-x1)/ancho;
	double dy = (y1-y2)/alto;
	double ceroX = 0.0;
	double ceroY = 0.0;
	if(x1 < 0 && x2 > 0){ //eje y
	    ceroX = -x1/dx;
	    ejes += "<line x1='" + ceroX +
		"' y1='" + 0 +
		"' x2='" + ceroX +
		"' y2='" + (alto - 1) +
		"' style='stroke:black;stroke-width:1' />\n";
	}
	if(y1 < 0 && y2 > 0){ //eje x
	    ceroY = -y2/dy;
	    ejes += "<line x1='" + 0 +
		"' y1='" + ceroY +
		"' x2='" + (ancho - 1) +
		"' y2='" + ceroY +
		"' style='stroke:black;stroke-width:1' />\n";
	}
	Iterator<double[]> iterador = puntos.iterator();
	int i = 0;
	while(iterador.hasNext()){
	    double[] p = iterador.next();
	    if(i == 0 && !Double.isNaN(p[1]))
		SVGpath += "M " + String.valueOf(i) + " " +
		    String.valueOf(ceroY+p[1]/dy) + " ";
	    if(Double.isNaN(p[1]) || Double.isInfinite(p[1])){
		while(Double.isNaN(p[1]) || Double.isInfinite(p[1])){
		    p = iterador.next();
		    i++;
		}
		p = iterador.next();
		SVGpath += "M " + String.valueOf(i) + " " +
		    String.valueOf(ceroY+p[1]/dy) + " ";
	    }else{
		SVGpath += "L " + String.valueOf(i) + " " +
		    String.valueOf(ceroY+p[1]/dy) + " ";
		i++;
	    }
	}
    }

    /**
     * Regresa un String que contiene el Path que describe la gráfica de la
     * función.
     * @return El path que describe la gráfica de la función.
     */
    public String getPath(){
	return SVGpath;
    }

    /** 
     * Genera un archivo en formato SVG con la gráfica de la función con el
     * nombre y ruta dado por el objeto {@link File} de entrada.
     * @param archivo Un objeto file que guarda el nombre y ruta del archivo
     *        donde se escribirá el SVG.
     * @throws IOException en caso de un fallo de E/S al tratar de escribir el
     *         archivo.
     */
    public void escribeSVGpath(File archivo) throws IOException{
	if(SVGpath == null)
	    return;
	BufferedWriter bw = null;
	bw = new BufferedWriter(new FileWriter(archivo));
	String svgFinal =
	    "<svg xmlns='http://www.w3.org/2000/svg' version='1.1' height='"
	    + alto + "' width='"+ ancho + "'>\n<g>\n"
	    + ejes
	    + "<path fill='none' stroke='red' stroke-width='1' d='"
	    + SVGpath
	    + "' />\n"
	    + "</g>\n</svg>";
	bw.write(svgFinal);
	bw.close();
    }

}
