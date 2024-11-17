package edu.upc.prop.clusterxx;

import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;

import static edu.upc.prop.clusterxx.ProductoController.doubleFilter;
import static edu.upc.prop.clusterxx.ProductoController.nonEmptyFilter;

public class ProductoCell extends ListCell<Producto> {
    private Button actionBtn;
    private TextField nombre ;
    private TextField precio ;
    private GridPane pane ;
    private int id;
    public ProductoCell() {
        super();

        setOnMouseClicked(event -> {
            //do something
        });

        pane = new GridPane();
        pane.prefWidthProperty().bind(this.widthProperty().subtract(14));

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setMaxWidth(Double.MAX_VALUE);
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(60);
        col0.setHalignment(HPos.CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(Double.MAX_VALUE);
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(25);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(Double.MAX_VALUE);
        col2.setHgrow(Priority.NEVER);
        col2.setPercentWidth(15);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints padding = new ColumnConstraints();
        padding.setMinWidth(8);
        pane.getColumnConstraints().add(col0);
        pane.getColumnConstraints().add(padding);
        pane.getColumnConstraints().add(col1);
        pane.getColumnConstraints().add(col2);

        nombre = new TextField();
        nombre.setTextFormatter(
                new TextFormatter<>(new DefaultStringConverter(), "Nuevo Producto", nonEmptyFilter));
        pane.add(nombre, 0, 0);

        precio = new TextField();
        precio.setTextFormatter(
                new TextFormatter<>(new DoubleStringConverter(), 0., doubleFilter));
        pane.add(precio, 2, 0);

        actionBtn = new Button("my action");
        actionBtn.setOnAction(event -> {
            Sistema.getListaProductos().eliminarProducto(id);
            Sistema.actualizarDatos();
        });
        actionBtn.setText("Eliminar");
        pane.add(actionBtn, 3, 0);
    }
    @Override
    public void updateItem(Producto producto, boolean empty) {
        super.updateItem(producto, empty);

        setEditable(false);
        if (producto != null) {
            id = Sistema.getListaProductos().getListaProductos().indexOf(producto);
            nombre.setText(producto.getNombre());
            nombre.textProperty().addListener((observable, oldValue, newValue) -> producto.setNombre(newValue));
            precio.setText(Double.toString(producto.getPrecio()));
            precio.textProperty().addListener((observable, oldValue, newValue) -> producto.setPrecio(Double.parseDouble(newValue)));
            setGraphic(pane);
        } else {
            setGraphic(null);
        }
    }
}
