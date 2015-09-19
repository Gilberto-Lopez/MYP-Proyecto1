package myp.proyecto1.modelo;

import mx.unam.ciencias.edd.Lista;
import java.io.IOException;
import java.io.File;

/**
 * Clase modelo para el Graficador de funciones. Esta clase corresponde al
 * modelo del proyecto, siguiendo el Patrón de diseño <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller">MVC</a>.
 */
public class GraficadorModelo{

    /* Lista de puntos en 2D. */
    private Lista<double[]> puntos;
    /* La expresión de entrada. */
    private Lista<String> funcion;
    private Tokens tokenizador;
    private Parser parser;
    private Evaluador evaluador;
    private GraficaSvg graficador;

    /**
     * Constructor sin parámetros. Crea una nueva instancia de GraficadorModelo.
     */
    public GraficadorModelo(){
	puntos = new Lista<double[]>();
	tokenizador = new Tokens();
	parser = new Parser();
	evaluador = new Evaluador();
    }

    /**
     * Añade una función al GraficadorModelo para poder ser evaluada y graficada
     * posteriormente. Para asignar una nueva función antes debe llamarse el
     * método {@link #limpia}.
     * @param funcion La función.
     */
    public void setFuncion(String funcion){
	this.funcion = parser.parsea(tokenizador.obtenerTokens(funcion));
    }

    /**
     * Evalua la función del GraficadorModelo en un punto x y guarda el valor
     * en la lista de puntos del GraficadorModelo. Antes debe de asginarse una
     * función con {@link #setFuncion}.
     * @param x El valor de x sobre el cual evaluar la función.
     */
    public void evaluaFuncion(double x){
	double val = evaluador.evalua(funcion, x);
	double[] punto = {x, val};
	puntos.agrega(punto);
    }

    /**
     * Regresa la lista de puntos (x,y) tales que y = f(x), obtenidos
     * previamente con el método {@link #evaluaFuncion}, para su
     * @return Una lista de puntos con los puntos (x,y) que satisface y = f(x)
     */
    public Lista<double[]> getPuntos(){
	return puntos;
    }

    /**
     * Elimina la función del objeto GraficadorModelo y su correspondiente lista
     * de puntos para poder asignarle una nueva función y poder evaluarla y
     * graficarla. También elimina el graficador para generar una imagen SVG en
     * caso de que éste haya sido inicializado con {@link #initGraficadorSvg}.
     */
    public void limpia(){
	puntos.limpia();
	funcion = null;
	graficador = null;
    }

    /**
     * Inicializa un graficador especial para generar la gráfica de la función
     * del objeto GraficadorModelo en formato SVG, especificando sus dimensiones
     * (ancho y alto) y los límites superiores e inferiores del eje x, x1 y x2,
     * y del eje y, y1 y y2, así la gráfica se genera sobre la región
     * [x1,x2]x[y1,y2]. Antes debe de evaluarse la función sobr elos puntos del
     * intervalo [x1,x2] con {@link #evaluaFuncion}.
     * @param ancho El ancho de la imagen SVG.
     * @param alto El alto de la imagen SVG.
     * @param x1 El límite superior sobre el eje x.
     * @param x2 El límite inferior dobre el eje x.
     * @param y1 El límite superior sobre el eje y.
     * @param y2 El límite inferior sobre el eje y.
     */
    public void initGraficadorSvg(double ancho, double alto, double x1,
				  double x2, double y1, double y2){
	graficador = new GraficaSvg(puntos, ancho, alto, x1, x2, y1, y2);
	graficador.generaSVGpath();
    }

    /**
     * Genera la gráfica en formato SVG de la función del objeto
     * GraficadorModelo usando la lista de puntos y la guarda en un archivo.
     * Mientras más puntos se hayan evaluado sobre el intervalo [x1,x2] se
     * tendrá una mejor aproximación de la gráfica de la función. Antes debe de
     * inicializarse el graficador especial con {@link #initGraficadorSvg}.
     * @param archivo El archivo sobre el cual guardar.
     */
    public void generaGraficaSvg(File archivo) throws IOException{
	graficador.escribeSVGpath(archivo);
    }

    /**
     * Regresa un String describiendo la gráfica de la función como un Path de
     * SVG. La gráfica de la función debe de ser generada antes con
     * {@link #generaGraficaSvg}.
     * @return Un String describiendo la gráfica de la función como un Path de
     *         SVG.
     */
    public String getSVGPath(){
	return graficador.getPath();
    }
}
