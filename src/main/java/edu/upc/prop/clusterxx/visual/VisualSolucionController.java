package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import javafx.fxml.FXML;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Clase VisualSolucionController
 * Controlador de la vista de solución
 */
public class VisualSolucionController {
    /**
     * RadioButton para la solución rápida
     */
    @FXML
    RadioButton rapida;
    /**
     * RadioButton para la solución óptima
     */
    @FXML
    RadioButton optima;
    /**
     * RadioButton para la solución ultra rápida
     */
    @FXML
    RadioButton ultra_rapida;
    /**
     * Spinner para el número de filas
     */
    @FXML
    Spinner filas;
    /**
     * Spinner para el número de columnas
     */
    @FXML
    Spinner columnas;
    /**
     * Botón para generar la solución
     */
    @FXML
    Button generar;
    /**
     * Barra de progreso del calculo de la solución
     */
    @FXML
    ProgressBar barra;

    /**
     * Método que se ejecuta al generar una solución
     */
    @FXML
    protected void onGenerarSolucion() {
        try {
            DomainSolucionController solucionController = DomainSolucionController.getInstance();
            solucionController.bindProgreso(barra);
            int numFilas = (int) filas.getValue();
            int numColumnas = (int) columnas.getValue();

            boolean demasiadoGrande = false;
            int maxEspacios = 0;
            if (rapida.isSelected()) {
                maxEspacios = 10000;
                demasiadoGrande = ((long) numFilas * (long) numColumnas) > maxEspacios;
            } else if (optima.isSelected()) {
                maxEspacios = 12;
                demasiadoGrande = numFilas * numColumnas > maxEspacios;
            }

            if (demasiadoGrande) {
                Alert alerta = new Alert(
                        Alert.AlertType.WARNING,
                        "El número de espacios es muy alto para este tipo de solución, podría tardar mucho o incluso no terminar nunca.\n\n" +
                                "El número de columnas multiplicado por el número de filas no debería ser superior a " + maxEspacios + " para este tipo de solución."
                );
                alerta.setTitle("Geometría no recomendable");
                alerta.showAndWait();
            }

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
                solucionController.parar_algoritmo();
                Alert alerta = new Alert(
                        Alert.AlertType.ERROR,
                        "Ocurrió un error al generar la solución: " + task.getException().getMessage()
                );
                alerta.setTitle("Error de generación");
                alerta.showAndWait();
            });

            new Thread(task).start();
            generar.setDisable(true);
            generar.getScene().getWindow().setOnCloseRequest(e -> solucionController.parar_algoritmo());

        } catch (IllegalArgumentException ex) {
            DomainSolucionController.getInstance().parar_algoritmo();
            generar.setDisable(false);
            generar.getScene().getWindow().setOnCloseRequest(e -> {
            });
            Alert alerta = new Alert(
                    Alert.AlertType.ERROR,
                    "El número de filas y columnas es insuficiente para el número de productos."
            );
            alerta.setTitle("Geometría incompatible");
            alerta.showAndWait();
        } catch (Throwable ex) {
            DomainSolucionController.getInstance().parar_algoritmo();
            generar.setDisable(false);
            generar.getScene().getWindow().setOnCloseRequest(e -> {
            });
            Alert alerta = new Alert(
                    Alert.AlertType.ERROR,
                    "La ejecución se ha detenido por un error inesperado: " + ex.getMessage()
            );
            alerta.setTitle("Error inesperado");
            alerta.showAndWait();
        }
    }
}

