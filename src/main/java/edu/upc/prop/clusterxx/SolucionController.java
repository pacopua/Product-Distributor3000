package edu.upc.prop.clusterxx;

import javafx.fxml.FXML;
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
        // TODO
        Sistema.nuevaSolucion((int) filas.getValue(), (int) columnas.getValue());
        if (rapida.isSelected()) {
            //Sistema.getSolucion().calcular_solucion_rapida();
        } else if (optima.isSelected()) {
            //Sistema.getSolucion().calcular_solucion_optima();
        }
        ((Stage) rapida.getScene().getWindow()).close();
    }
}
