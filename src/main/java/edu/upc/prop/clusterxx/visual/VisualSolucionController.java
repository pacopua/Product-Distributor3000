package edu.upc.prop.clusterxx.visual;

//import edu.upc.prop.clusterxx.data.Sistema;
//import edu.upc.prop.clusterxx.domain.Solucion;
import edu.upc.prop.clusterxx.domain.DomainEstadoController;
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
    RadioButton ultra_rapida;
    @FXML
    Spinner filas;
    @FXML
    Spinner columnas;
    @FXML
    protected void onGenerarSolucion() {
        try {
            DomainSolucionController solucionController = DomainSolucionController.getInstance();
            int numFilas = (int)filas.getValue();
            int numColumnas = (int)columnas.getValue();
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                  try {
                      if (rapida.isSelected()) {
                          solucionController.calcularDistribucionRapida(numFilas, numColumnas);
                      } else if (optima.isSelected()) {
                          solucionController.calcularDistribucionOptima(numFilas, numColumnas);
                      } else if (ultra_rapida.isSelected()) {
                          solucionController.calcularDistribucionUltraRapida(numFilas, numColumnas);
                      }
                  } catch (IllegalArgumentException ex) {
                      throw new Exception("el número de filas y columnas es insuficiente para el número de productos", ex);
                  }
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
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

