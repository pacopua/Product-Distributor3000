package edu.upc.prop.clusterxx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class ProductoController {
    @FXML
    TextField nombre;
    @FXML
    TextField precio;
    public static UnaryOperator<TextFormatter.Change> nonEmptyFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches(".+")) {
            return change;
        }
        return null;
    };
    public static UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("[0-9]+\\.?[0-9]?[0-9]?")) {
            return change;
        }
        return null;
    };
    @FXML
    protected void initialize() {
        nombre.setTextFormatter(
                new TextFormatter<>(new DefaultStringConverter(), "Nuevo Producto", nonEmptyFilter));
        precio.setTextFormatter(
                new TextFormatter<>(new DoubleStringConverter(), 0., doubleFilter));
    }
    static void ventanaErrorProd() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "Ya existe un producto con este nombre."
        );
        alerta.setTitle("Producto ya existe");
        alerta.show();
    }
    @FXML
    protected void onAnadirProducto() {
        Producto producto = new Producto(
                (String) nombre.getTextFormatter().getValue(),
                (double) precio.getTextFormatter().getValue()
        );

        boolean existe = false;
        for (Producto prod : Sistema.getListaProductos().getListaProductos()) {
            if (prod.getNombre().equals(producto.getNombre())) {
                existe = true;
                break;
            }
        }

        if (existe) {
            ventanaErrorProd();
            return;
        }

        Sistema.getListaProductos().addProducto(producto);

        MatrizAdyacencia matrizNueva = new MatrizAdyacencia(Sistema.getListaProductos().getCantidadProductos());
        for (int i = 0; i < Sistema.getMatrizAdyacencia().getNumProductos(); i++)
            for (int j = 0; j < Sistema.getMatrizAdyacencia().getNumProductos(); j++)
                matrizNueva.modificar_sinergias(i, j, Sistema.getMatrizAdyacencia().getSinergia(i, j));
        Sistema.setMatrizAdyacencia(matrizNueva);

        Sistema.actualizarDatos();

        ((Stage) nombre.getScene().getWindow()).close();
    }
}
