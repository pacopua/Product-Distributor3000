package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.Producto;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;

import static edu.upc.prop.clusterxx.visual.VisualProductoController.doubleFilter;
import static edu.upc.prop.clusterxx.visual.VisualProductoController.nonEmptyFilter;

public class ProductoCell extends ListCell<Producto> {
    private Button actionBtn;
    private TextField nombre ;
    private TextField precio ;
    private GridPane pane ;
    private int id;
    public ProductoCell() {
        super();

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
        nombre.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean existe = false;
                    for (Producto prod : GestorPesistencia.getListaProductos().getListaProductos()) {
                        if (id != GestorPesistencia.getListaProductos().getListaProductos().indexOf(prod) && prod.getNombre().equals(newValue)) {
                            existe = true;
                            break;
                        }
                    }
                    if (existe) {
                        nombre.textProperty().setValue(oldValue);
                        VisualProductoController.ventanaErrorProd();
                        return;
                    }
                    GestorPesistencia.getListaProductos().getProducto(id).get().setNombre(newValue);
                    PropController.actualizarDatos();
                }
        );
        pane.add(nombre, 0, 0);

        precio = new TextField();
        precio.setTextFormatter(
                new TextFormatter<>(new DoubleStringConverter(), 0., doubleFilter));
        precio.textProperty().addListener(
                (observable, oldValue, newValue) -> GestorPesistencia.getListaProductos().getProducto(id).get().setPrecio(Double.parseDouble(newValue))
        );
        pane.add(precio, 2, 0);

        actionBtn = new Button("Eliminar");
        actionBtn.setOnAction(event -> {
            GestorPesistencia.getListaProductos().eliminarProducto(id);
            GestorPesistencia.getMatrizAdyacencia().eliminarProducto(id);
            PropController.actualizarDatos();
        });
        pane.add(actionBtn, 3, 0);
    }
    @Override
    public void updateItem(Producto producto, boolean empty) {
        super.updateItem(producto, empty);

        setEditable(false);
        if (producto != null) {
            id = GestorPesistencia.getListaProductos().getListaProductos().indexOf(producto);
            nombre.setText(producto.getNombre());
            precio.setText(Double.toString(producto.getPrecio()));
            setGraphic(pane);
        } else {
            setGraphic(null);
        }
    }
}
