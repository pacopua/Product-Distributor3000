package edu.upc.prop.clusterxx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

public class SolucionController {
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
            Solucion s = Sistema.nuevaSolucion((int) filas.getValue(), (int) columnas.getValue());
            if (rapida.isSelected()) {
                AlgoritmoRapido algo = new AlgoritmoRapido(Sistema.getMatrizAdyacencia());
                s = algo.ejecutar(s, 1);
            } else if (optima.isSelected()) {
                AlgoritmoOptimo algo = new AlgoritmoOptimo(Sistema.getMatrizAdyacencia());
                s = algo.ejecutar(s);
            }
            Sistema.setSolucion(s);
            ((Stage) rapida.getScene().getWindow()).close();
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
