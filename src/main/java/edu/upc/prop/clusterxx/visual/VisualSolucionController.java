package edu.upc.prop.clusterxx.visual;

//import edu.upc.prop.clusterxx.data.Sistema;
//import edu.upc.prop.clusterxx.domain.Solucion;
import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import javafx.fxml.FXML;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

public class VisualSolucionController {
    @FXML
    RadioButton rapida;
    @FXML
    RadioButton optima;
    @FXML
    Spinner filas;
    @FXML
    Spinner columnas;
    @FXML
    protected void onGenerarSolucion() {
        try {
            DomainSolucionController solucionController = new DomainSolucionController();
            int numFilas = (int)filas.getValue();
            int numColumnas = (int)columnas.getValue();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    if (rapida.isSelected()) {
                        //return
                                solucionController.calcularDistribucionRapida(numFilas, numColumnas);
                    } else if (optima.isSelected()) {
                        //return
                                solucionController.calcularDistribucionOptima(numFilas, numColumnas);
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

