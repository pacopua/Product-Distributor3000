package edu.upc.prop.clusterxx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.function.UnaryOperator;

public class ProductoController {
    @FXML
    TextField nombre;
    @FXML
    TextField precio;
    UnaryOperator<TextFormatter.Change> nonEmptyFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches(".+")) {
            return change;
        }
        return null;
    };
    UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("[0-9]+\\.?[0-9]*")) {
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
    @FXML
    protected void onAñadirProducto() {

    }
}
