package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.domain.DomainEstadoController;
import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class VisualSolucionController {
    @FXML
    RadioButton rapida;
    @FXML
    RadioButton optima;
    @FXML
    RadioButton ultra_rapida;
    @FXML
    Spinner filas;
    @FXML
    Spinner columnas;
    @FXML
    Button generar;
    @FXML
    ProgressBar barra;
    @FXML
    protected void onGenerarSolucion() {
        try {
            DomainSolucionController solucionController = DomainSolucionController.getInstance();
            DoubleProperty progreso = new SimpleDoubleProperty(0);
            barra.progressProperty().bind(progreso);
            int numFilas = (int)filas.getValue();
            int numColumnas = (int)columnas.getValue();
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                  try {
                      //System.out.println("AAAAAAAAA2141");
                      if (rapida.isSelected()) {
                          //return
                          solucionController.calcularDistribucionRapida(numFilas, numColumnas, progreso);
                      } else if (optima.isSelected()) {
                          //return
                          solucionController.calcularDistribucionOptima(numFilas, numColumnas, progreso);
                      }
                      else if (ultra_rapida.isSelected()) {
                          //return
                          solucionController.calcularDistribucionUltraRapida(numFilas, numColumnas, progreso);
                      }
                  } catch (IllegalArgumentException ex) {
                      throw new Exception("el número de filas y columnas es insuficiente para el número de productos", ex);
                  }
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                //Sistema.setSolucion(task.getValue());
                ((Stage) rapida.getScene().getWindow()).close();
            });

            task.setOnFailed(event -> {
                Alert alerta = new Alert(
                        Alert.AlertType.ERROR,
                        "Ocurrió un error al generar la solución."
                );
                alerta.setTitle("Error de generación");
                alerta.setContentText(task.getException().getMessage());
                alerta.showAndWait();
            });

            Thread t = new Thread(task);
            generar.setDisable(true);
            generar.getScene().getWindow().setOnCloseRequest(e -> solucionController.parar_algoritmo());
            t.start();
        } catch (IllegalArgumentException ex) {
            generar.setDisable(false);
            generar.getScene().getWindow().setOnCloseRequest(e -> {});
            Alert alerta = new Alert(
                    Alert.AlertType.ERROR,
                    "El número de filas y columnas es insuficiente para el número de productos."
            );
            alerta.setTitle("Geometría incompatible");
            alerta.showAndWait();
        }
    }
}
    /*
    protected void onGenerarSolucion() {
        try {
            // Create initial solution
            Solucion s = Sistema.nuevaSolucion((int) filas.getValue(), (int) columnas.getValue());

            // Determine the algorithm to use
            boolean isRapida = rapida.isSelected();
            Task<Solucion> task = new Task<>() {
                @Override
                protected Solucion call() throws Exception {
                    if (isRapida) {
                        AlgoritmoRapido algo = new AlgoritmoRapido(Sistema.getMatrizAdyacencia());
                        return algo.ejecutar(s, 1);
                    } else if (optima.isSelected()) {
                        AlgoritmoOptimo algo = new AlgoritmoOptimo(Sistema.getMatrizAdyacencia());
                        return algo.ejecutar(s);
                    }
                    return s;
                }
            };

            // Handle success or failure on the UI thread
            task.setOnSucceeded(event -> {
                Sistema.setSolucion(task.getValue());
                ((Stage) rapida.getScene().getWindow()).close();
            });

            task.setOnFailed(event -> {
                Alert alerta = new Alert(
                        Alert.AlertType.ERROR,
                        "Ocurrió un error al generar la solución."
                );
                alerta.setTitle("Error de generación");
                alerta.setContentText(task.getException().getMessage());
                alerta.showAndWait();
            });

            // Run the task in a background thread
            new Thread(task).start();

        } catch (IllegalArgumentException ex) {
            Alert alerta = new Alert(
                    Alert.AlertType.ERROR,
                    "El número de filas y columnas es insuficiente para el número de productos."
            );
            alerta.setTitle("Geometría incompatible");
            alerta.showAndWait();
        }
    }
*/

