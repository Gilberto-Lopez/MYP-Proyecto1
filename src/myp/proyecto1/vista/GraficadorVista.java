package myp.proyecto1.vista;

import java.io.File;
import myp.proyecto1.controlador.GraficadorControlador;
import java.lang.Exception;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class GraficadorVista extends Application{
    
    @Override public void start(Stage primaryStage){
	GraficadorControlador controlador = new GraficadorControlador();
	
	primaryStage.setTitle("Graficador");
	
	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(10, 10, 10, 10));

	Canvas canvas = new Canvas(600, 400);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.setStroke(Color.BLACK);
	gc.strokeLine(0, 0, 599, 0);
	gc.strokeLine(0, 399, 599, 399);
	gc.strokeLine(0, 0, 0, 399);
	gc.strokeLine(599, 0, 599, 399);
	//ejes x y y
	gc.strokeLine(0, 199, 599, 199);
	gc.strokeLine(299, 0, 299, 399);
	grid.add(canvas, 0, 0, 5, 1);
	
	Label efx = new Label("f(x) = ");
	grid.add(efx, 0, 1);

	TextField campoFx = new TextField();
	grid.add(campoFx, 1, 1, 3, 1);
	
	Label eancho = new Label("Ancho:");
	grid.add(eancho, 0, 2);

	TextField campoAncho = new TextField();
	grid.add(campoAncho, 1, 2);

	Label ealto = new Label("Alto:");
	grid.add(ealto, 2, 2);

	TextField campoAlto = new TextField();
	grid.add(campoAlto, 3, 2);

	Label ex1 = new Label("x₁:");
	grid.add(ex1, 0, 3);

	TextField campoX1 = new TextField();
	grid.add(campoX1, 1, 3);

	Label ex2 = new Label("x₂:");
	grid.add(ex2, 2, 3);

	TextField campoX2 = new TextField();
	grid.add(campoX2, 3, 3);

	Label ey1 = new Label("y₁:");
	grid.add(ey1, 0, 4);

	TextField campoY1 = new TextField();
	grid.add(campoY1, 1, 4);

	Label ey2 = new Label("y₂:");
	grid.add(ey2, 2, 4);

	TextField campoY2 = new TextField();
	grid.add(campoY2, 3, 4);

	Button limpia = new Button("Limpia");
	Button grafica = new Button("Graficar");
	Button svg = new Button("SVG");
	HBox hbBotones = new HBox(20);
	hbBotones.setAlignment(Pos.BOTTOM_RIGHT);
	hbBotones.getChildren().addAll(limpia, grafica, svg);
	grid.add(hbBotones, 0, 5, 4, 1);

	Text actiontarget = new Text();
	actiontarget.setFill(Color.FIREBRICK);
	grid.add(actiontarget, 0, 6, 5, 1);

	svg.setDisable(true);
	
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
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.strokeLine(0, 0, 599, 0);
		gc.strokeLine(0, 399, 599, 399);
		gc.strokeLine(0, 0, 0, 399);
		gc.strokeLine(599, 0, 599, 399);
		gc.strokeLine(0, 199, 599, 199);
		gc.strokeLine(299, 0, 299, 399);
		controlador.limpia();
		svg.setDisable(true);
	    });
	
	grafica.setOnAction((ActionEvent e) -> {
		try{
		    gc.setStroke(Color.RED);
		    controlador.setFuncion(campoFx.getText());
		    controlador.setGraphicsContext(gc);
		    controlador.graficaFuncion(
			       Double.parseDouble(campoAncho.getText()),
			       Double.parseDouble(campoAlto.getText()),
			       Double.parseDouble(campoX1.getText()),
			       Double.parseDouble(campoX2.getText()),
			       Double.parseDouble(campoY1.getText()),
			       Double.parseDouble(campoY2.getText()));
		    gc.stroke();
		    svg.setDisable(false);
		}catch(Exception ex){
		    actiontarget.setText(ex.getMessage() + ex.getClass().toString());
		    ex.printStackTrace();
		}
	    });
	
	svg.setOnAction((ActionEvent e) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Guardar");
		fc.setInitialFileName(campoFx.getText()+".svg");
		File file = fc.showSaveDialog(primaryStage);
		if(file != null){
		    try{
			controlador.generaSvg(file,
			       Double.parseDouble(campoAncho.getText()),
			       Double.parseDouble(campoAlto.getText()),
			       Double.parseDouble(campoX1.getText()),
			       Double.parseDouble(campoX2.getText()),
			       Double.parseDouble(campoY1.getText()),
			       Double.parseDouble(campoY2.getText()));
		    }catch(Exception ex){
			actiontarget.setText("Hubo un error al generar el SVG, inténtelo de nuevo.");
		    }
		}
	    });
	
	Scene scene = new Scene(grid);
	primaryStage.setScene(scene);

	primaryStage.show();
    }

    public static void main(String[] args){
	launch(args);
    }
}
