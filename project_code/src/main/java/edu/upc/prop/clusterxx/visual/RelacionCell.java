package edu.upc.prop.clusterxx.visual;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;

/**
 * Clase ProductoCell
 * Celda para representar la relación entre dos productos
 */
public class RelacionCell extends ListCell<Pair<Integer, Integer>> {
    /**
     * Nombre del producto 1
     */
    private Label nombre1 ;
    /**
     * Nombre del producto 2
     */
    private Label nombre2 ;
    /**
     * Relación entre los productos
     */
    private TextField relacion ;
    /**
     * Panel de la celda
     */
    private GridPane pane ;
    /**
     * Id del producto 1
     */
    private int id1;
    /**
     * Id del producto 2
     */
    private int id2;

    /**
     * Constructor de la clase RelacionCell
     */
    public RelacionCell() {
        super();

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
                new TextFormatter<>(new DoubleStringConverter(), 0.));
        relacion.setOnAction(event -> {
            String newValue = relacion.getText().trim(); // Allow trimming whitespace
            if (newValue.isEmpty()) {
                relacion.setText(Double.toString(0.0)); // Revert to the original value
                VisualProductoController.ventanaErrorProd("El precio no puede estar vacío.", "Precio vacio");

            }
        });

        relacion.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus lost
                String newValue = relacion.getText().trim();
                try {
                    if (newValue.isEmpty()) {
                        relacion.setText(Double.toString(0.0)); // Revert to the original value
                        throw new NumberFormatException("El precio no puede estar vacío.");
                    }
                    if (Double.parseDouble(newValue) < 0) {
                        relacion.setText(Double.toString(0.0)); // Revert to the original value
                        throw new NumberFormatException("La relación no puede ser negativa.");
                    }
                    PropController.setSinergias(id1, id2, Double.parseDouble(newValue));
                } catch (NumberFormatException e) {
                    VisualProductoController.ventanaErrorProd(e.getMessage(), "Valor de relación incorrecto!");
                }
            }
        });



        pane.add(relacion, 2, 0);
    }

    /**
     * Método que se llama cuando se actualiza la celda
     * @param productos par de productos
     * @param empty si la celda está vacía
     */
    @Override
    public void updateItem(Pair<Integer, Integer> productos, boolean empty) {
        super.updateItem(productos, empty);

        setEditable(false);
        if (productos != null) {
            id1 = productos.getKey();
            id2 = productos.getValue();

            nombre1.setText(PropController.getNombreProducto(id1));
            nombre2.setText(PropController.getNombreProducto(id2));
            double relacionValue = PropController.getSinergia(id1, id2);
            relacion.setText(Double.toString(relacionValue));
            setGraphic(pane);
        } else {
            setGraphic(null);
        }
    }
}
