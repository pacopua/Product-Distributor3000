package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.PropApp;
import edu.upc.prop.clusterxx.domain.DomainProductoController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;

/**
 * Clase VisualProductoController
 * Controlador de la ventana de producto
 */
public class VisualProductoController {
    /**
     * Campo de texto para el nombre
     */
    @FXML
    TextField nombre;
    /**
     * Campo de texto para el precio
     */
    @FXML
    TextField precio;

    /**
     * Metodo que inicializa la ventana
     */
    @FXML
    protected void initialize() {
        nombre.setTextFormatter(
                new TextFormatter<>(new DefaultStringConverter(), "Nuevo Producto"));
        nombre.setOnAction(event -> {
            String newValue = nombre.getText().trim();
            if(newValue.isEmpty()) {
                nombre.setText("Nuevo Producto");
                VisualProductoController.ventanaErrorProd("El nombre no puede estar vacío.", "Nombre vacío");
            }
            else nombre.setText(newValue);
        });

        nombre.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus lost
                String newValue = nombre.getText().trim();
                if(newValue.isEmpty()) {
                    nombre.setText("Nuevo Producto");
                    VisualProductoController.ventanaErrorProd("El nombre no puede estar vacío.", "Nombre vacío");
                }
                else nombre.setText(newValue);
            }
        });
        precio.setTextFormatter(
                new TextFormatter<>(new DoubleStringConverter(), 0.));
        precio.setOnAction(event -> {
            String newValue = precio.getText().trim(); // Allow trimming whitespace
            if (newValue.isEmpty()) {
                precio.setText(Double.toString(0.0)); // Revert to the original value
                VisualProductoController.ventanaErrorProd("El precio no puede estar vacío.", "Precio vacio");

            }
        });

        precio.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus lost
                String newValue = precio.getText().trim();
                if (newValue.isEmpty()) {
                    precio.setText(Double.toString(0.0)); // Revert to the original value
                    VisualProductoController.ventanaErrorProd("El precio no puede estar vacío.", "Precio vacio");

                }
            }
        });
    }

    /**
     * Metodo para mostrar una ventana de error
     * @param error mensaje de error
     * @param titulo titulo de la ventana
     */
    static void ventanaErrorProd(String error, String titulo) {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                error
        );
        alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
        alerta.setTitle(titulo);
        alerta.show();
    }

    /**
     * Metodo que se llama al pulsar el boton de añadir producto
     */
    @FXML
    protected void onAnadirProducto() {
        DomainProductoController controller = DomainProductoController.getInstance();
        switch (controller.anyadirProducto((String) nombre.getTextFormatter().getValue(), (double) precio.getTextFormatter().getValue())) {
            case -1:
                ventanaErrorProd("El precio no puede ser negativo", "Precio negativo");
                break;
            case 0:
                ventanaErrorProd("El producto ya existe", "Producto ya existente");
                break;
            case 1:
                ((Stage) nombre.getScene().getWindow()).close();
        }
    }
}
