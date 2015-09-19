package myp.proyecto1.vista;

import java.io.File;
import myp.proyecto1.controlador.GraficadorControlador;
import java.lang.Exception;
import java.lang.Number;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.beans.value.ObservableValue;

public class GraficadorVista extends Application{

    private GraficadorControlador controlador = new GraficadorControlador();
    private Canvas canvas;
    private GraphicsContext gc;
    private TextField campoFx = new TextField();
    private TextField campoAncho = new TextField();
    private TextField campoAlto = new TextField();
    private TextField campoX1 = new TextField();
    private TextField campoX2 = new TextField();
    private TextField campoY1 = new TextField();
    private TextField campoY2 = new TextField();
    private Button limpia = new Button("Limpia");
    private Button grafica = new Button("Graficar");
    private Button svg = new Button("SVG");
    private ColorPicker colorPicker = new ColorPicker(Color.RED);
    
    @Override public void start(Stage primaryStage){	
	primaryStage.setTitle("Graficador");

	canvasInicio();
	
	ScrollPane sp = new ScrollPane();
	sp.setFitToHeight(true);
	sp.setFitToWidth(true);
	
	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(10, 10, 10, 10));
	
	canvasInicio();
	
	grid.add(canvas, 0, 0, 5, 1);
	
	final Label efx = new Label("f(x) = ");
	grid.add(efx, 0, 1);

	grid.add(campoFx, 1, 1, 3, 1);
	
	final Label eancho = new Label("Ancho:");
	grid.add(eancho, 0, 2);

	grid.add(campoAncho, 1, 2);

	final Label ealto = new Label("Alto:");
	grid.add(ealto, 2, 2);

	grid.add(campoAlto, 3, 2);

	final Label ex1 = new Label("x₁:");
	grid.add(ex1, 0, 3);

	grid.add(campoX1, 1, 3);

	final Label ex2 = new Label("x₂:");
	grid.add(ex2, 2, 3);

	grid.add(campoX2, 3, 3);

	final Label ey1 = new Label("y₁:");
	grid.add(ey1, 0, 4);

	grid.add(campoY1, 1, 4);

	final Label ey2 = new Label("y₂:");
	grid.add(ey2, 2, 4);

	grid.add(campoY2, 3, 4);

	grid.add(colorPicker, 4, 1);

	HBox hbBotones = new HBox(20);
	hbBotones.setAlignment(Pos.BOTTOM_RIGHT);
	hbBotones.getChildren().addAll(limpia, grafica, svg);
	grid.add(hbBotones, 0, 5, 4, 1);

	final Text actiontarget = new Text();
	actiontarget.setFill(Color.FIREBRICK);
	grid.add(actiontarget, 0, 6, 5, 1);

	svg.setDisable(true);
	limpia.setDisable(true);
	
	campoFx.setOnAction((ActionEvent e) -> {
		actiontarget.setText("Para graficar presione el botón Graficar.");
	    });
	
	limpia.setOnAction((ActionEvent e) -> {
		actiontarget.setText("");
		campoFx.setText("");
		campoAncho.setText("");
		campoAlto.setText("");
		campoX1.setText("");
		campoX2.setText("");
		campoY1.setText("");
		campoY2.setText("");	
		controlador.limpia();
		grid.getChildren().remove(canvas);
		canvasInicio();
		grid.add(canvas, 0, 0, 5, 1);
		svg.setDisable(true);
		grafica.setDisable(false);
		limpia.setDisable(true);
		primaryStage.sizeToScene();
	    });

	colorPicker.setOnAction((ActionEvent e) -> {
		gc.setStroke(colorPicker.getValue());
	    });
	
	grafica.setOnAction((ActionEvent e) -> {
		try{
		    actiontarget.setText("");
		    controlador.setFuncion(campoFx.getText());
		    controlador.setGraphicsContext(gc);
		    double alto = Double.parseDouble(campoAlto.getText());
		    double ancho = Double.parseDouble(campoAncho.getText());
		    double x1 = Double.parseDouble(campoX1.getText());
		    double x2 = Double.parseDouble(campoX2.getText());
		    double y1 = Double.parseDouble(campoY1.getText());
		    double y2 = Double.parseDouble(campoY2.getText());
		    if(x1 > x2){
			double tmp = x1;
			x1 = x2;
			x2 = tmp;
		    }
		    if(y1 > y2){
			double tmp = y1;
			y1 = y2;
			y2 = tmp;
		    }
		    alto = (alto > 400.0) ? alto : 400.0;
		    ancho = (ancho > 600.0) ? ancho : 600.0;
		    canvas.setHeight(alto);
		    canvas.setWidth(ancho);
		    gc.setFill(Color.WHITE);
		    gc.fillRect(0.0, 0.0, ancho, alto);
		    gc.setStroke(Color.BLACK);
		    gc.strokeLine(0.0, 0.0, ancho, 0.0);
		    gc.strokeLine(0.0, alto, ancho, alto);
		    gc.strokeLine(0.0, 0.0, 0.0, alto);
		    gc.strokeLine(ancho, 0.0, ancho, alto);
		    controlador.graficaFuncion(ancho, alto, x1, x2, y1, y2);
		    gc.setStroke(colorPicker.getValue());
		    gc.stroke();
		    svg.setDisable(false);
		    grafica.setDisable(true);
		    limpia.setDisable(false);
		    primaryStage.sizeToScene();
		}catch(Exception ex){
		    actiontarget.setText(ex.getMessage());
		}
	    });
	
	svg.setOnAction((ActionEvent e) -> {
		actiontarget.setText("");
		FileChooser fc = new FileChooser();
		fc.setTitle("Guardar");
		fc.setInitialFileName(campoFx.getText()+".svg");
		File file = fc.showSaveDialog(primaryStage);
		if(file != null){
		    try{
			controlador.generaSvg(file);
		    }catch(Exception ex){
			actiontarget.setText("Hubo un error al generar el SVG, inténtelo de nuevo.");
		    }
		}
	    });


	sp.setContent(grid);

	Scene scene = new Scene(sp);
	primaryStage.setScene(scene);
	primaryStage.setMinWidth(700);
	primaryStage.setResizable(true);
	
	primaryStage.show();
    }

    private void canvasInicio(){
	canvas = new Canvas(600, 400);
	gc = canvas.getGraphicsContext2D();
	gc.setFill(Color.WHITE);
	gc.fillRect(0.0, 0.0, 600.0, 400.0);
	gc.setStroke(Color.BLACK);
	gc.strokeLine(0, 0, 600, 0);
	gc.strokeLine(0, 400, 600, 400);
	gc.strokeLine(0, 0, 0, 400);
	gc.strokeLine(600, 0, 600, 400);
    }

    public static void main(String[] args){
	launch(args);
    }
}
