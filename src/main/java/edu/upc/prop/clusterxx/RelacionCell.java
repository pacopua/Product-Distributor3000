package edu.upc.prop.clusterxx;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;

import static edu.upc.prop.clusterxx.ProductoController.doubleFilter;

public class RelacionCell extends ListCell<Pair<Producto, Producto>> {
    private Label nombre1 ;
    private Label nombre2 ;
    private TextField relacion ;
    private GridPane pane ;
    private int id1;
    private int id2;
    public RelacionCell() {
        super();

        setOnMouseClicked(event -> {
            //do something
        });

        pane = new GridPane();
        pane.prefWidthProperty().bind(this.widthProperty().subtract(14));

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setMaxWidth(Double.MAX_VALUE);
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(45);
        col0.setHalignment(HPos.CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(Double.MAX_VALUE);
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(45);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(Double.MAX_VALUE);
        col2.setHgrow(Priority.NEVER);
        col2.setPercentWidth(10);
        col2.setHalignment(HPos.CENTER);
        pane.getColumnConstraints().add(col0);
        pane.getColumnConstraints().add(col1);
        pane.getColumnConstraints().add(col2);

        nombre1 = new Label();
        nombre1.setAlignment(Pos.CENTER);
        pane.add(nombre1, 0, 0);

        nombre2 = new Label();
        nombre2.setAlignment(Pos.CENTER);
        pane.add(nombre2, 1, 0);

        relacion = new TextField();
        relacion.setTextFormatter(
                new TextFormatter<>(new DoubleStringConverter(), 0., doubleFilter));
        pane.add(relacion, 2, 0);
    }
    @Override
    public void updateItem(Pair<Producto, Producto> productos, boolean empty) {
        super.updateItem(productos, empty);

        setEditable(false);
        if (productos != null) {
            id1 = Sistema.getListaProductos().getListaProductos().indexOf(productos.getKey());
            id2 = Sistema.getListaProductos().getListaProductos().indexOf(productos.getValue());

            nombre1.setText(productos.getKey().getNombre());
            nombre2.setText(productos.getValue().getNombre());
            double relacionValue = Sistema.getMatrizAdyacencia().getSinergia(id1, id2);
            relacion.setText(Double.toString(relacionValue));
            relacion.textProperty().addListener(
                    (observable, oldValue, newValue) ->
                        Sistema.getMatrizAdyacencia().modificar_singergias(id1, id2, Double.parseDouble(newValue))
            );
            setGraphic(pane);
        } else {
            setGraphic(null);
        }
    }
}
