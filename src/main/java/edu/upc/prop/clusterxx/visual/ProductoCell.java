package edu.upc.prop.clusterxx.visual;

//import edu.upc.prop.clusterxx.data.GestorPesistencia;
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

public class ProductoCell extends ListCell<Integer> {
    private PropController propController;
    private Button actionBtn;
    private TextField nombre ;
    private TextField precio ;
    private GridPane pane ;
    private int id;
    public ProductoCell(PropController p) {
        super();
        propController = p;
        pane = new GridPane();
        pane.prefWidthProperty().bind(this.widthProperty().subtract(14));
        //pane.setStyle("-fx-background-color: green;");

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
                new TextFormatter<>(new DefaultStringConverter(), "Nuevo Producto"));
        /*
        nombre.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (propController.existeProductoConDiferenteID(id, newValue)) {
                        nombre.textProperty().setValue(oldValue);
                        VisualProductoController.ventanaErrorProd();
                        PropController.actualizarDatos();
                        return;
                    }
                    PropController.cambiarNombreProducto(id, newValue);
                    PropController.actualizarDatos();
                }
        );

         */
        nombre.setOnAction(event -> {
            String newValue = nombre.getText().trim();
            if(newValue.isEmpty()) {
                nombre.setText(PropController.getNombreProducto(id));
                VisualProductoController.ventanaErrorProd("El nombre no puede estar vacío.", "Nombre vacío");
            }
            else if (propController.existeProductoConDiferenteID(id, newValue)) {
                nombre.setText(PropController.getNombreProducto(id)); // Revert to the original value
                VisualProductoController.ventanaErrorProd("El nombre ya existe.", "Nombre ya existe");
            } else {
                PropController.cambiarNombreProducto(id, newValue);
                PropController.actualizarDatos();
            }
        });

        nombre.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus lost
                String newValue = nombre.getText().trim();
                if(newValue.isEmpty()) {
                    nombre.setText(PropController.getNombreProducto(id));
                    VisualProductoController.ventanaErrorProd("El nombre no puede estar vacío.", "Nombre vacío");
                }
                else if (propController.existeProductoConDiferenteID(id, newValue)) {
                    nombre.setText(PropController.getNombreProducto(id)); // Revert to the original value
                    VisualProductoController.ventanaErrorProd("El nombre ya existe.", "Nombre ya existe");
                } else {
                    PropController.cambiarNombreProducto(id, newValue);
                    PropController.actualizarDatos();
                }
            }
        });
        pane.add(nombre, 0, 0);

        precio = new TextField();
        precio.setTextFormatter(
                new TextFormatter<>(new DoubleStringConverter(), 0.));
        /*
        precio.textProperty().addListener(
                (observable, oldValue, newValue) -> GestorPesistencia.getListaProductos().getProducto(id).get().setPrecio(Double.parseDouble(newValue))
        );
         */
        /*
        precio.setOnAction(event -> {
            try {
                double newValue = Double.parseDouble(precio.getText());
                PropController.setPrecioProducto(id, newValue);
            } catch (NumberFormatException e) {
                VisualProductoController.ventanaErrorProd("Error al añadir precio", "Error precio"); // Handle invalid input
                precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
            }
        });

        precio.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus lost
                try {
                    double newValue = Double.parseDouble(precio.getText());
                    PropController.setPrecioProducto(id, newValue);
                } catch (NumberFormatException e) {
                    VisualProductoController.ventanaErrorProd("Error al añadir precio", "Error precio"); // Handle invalid input
                    precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
                }
            }
        });

         */
        precio.setOnAction(event -> {
            String newValue = precio.getText().trim(); // Allow trimming whitespace
            if (newValue.isEmpty()) {
                precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
                VisualProductoController.ventanaErrorProd("El precio no puede estar vacío.", "Precio vacio");

            } else {
                try {
                    double price = Double.parseDouble(newValue);
                    if(price < 0) {
                        throw new NumberFormatException("Negative price");
                    }
                    PropController.setPrecioProducto(id, price);
                } catch (NumberFormatException e) {
                    VisualProductoController.ventanaErrorProd("Debe ingresar un valor numérico válido.", "Error precio");
                    precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
                }
            }
        });

        precio.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (!newFocus) { // Focus lost
                String newValue = precio.getText().trim();
                if (newValue.isEmpty()) {
                    VisualProductoController.ventanaErrorProd("El precio no puede estar vacío.", "Precio vacio");
                    precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
                }
                if (Double.parseDouble(newValue) < 0) {
                    VisualProductoController.ventanaErrorProd("El precio no puede ser negativo.", "Precio negativo");
                    precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
                }
                else {
                    try {
                        double price = Double.parseDouble(newValue);
                        PropController.setPrecioProducto(id, price);
                    } catch (NumberFormatException e) {
                        VisualProductoController.ventanaErrorProd("Debe ingresar un valor numérico válido.", "Error precio");
                        precio.setText(Double.toString(PropController.getPrecioProducto(id))); // Revert to the original value
                    }
                }
            }
        });

        pane.add(precio, 2, 0);

        actionBtn = new Button("Eliminar");
        actionBtn.setOnAction(event -> {
            if(propController.ventanaConfirmar("Esta acción eliminará el producto.\n¿Seguro que desea continuar?", "Eliminar Producto")) {
            PropController.eliminarProducto(id);
            PropController.actualizarDatos();

            propController.ordenarProductosView();
            }
        });
        pane.add(actionBtn, 3, 0);
        //System.out.println("ProductoCell");
        //GestorPesistencia.imprimirListaProductos();
        //System.out.println("AAAA");
        //PropController.actualizarDatos();
    }
    @Override
    public void updateItem(Integer index, boolean empty) {
        super.updateItem(index, empty);
        //System.out.println("BBB");

        setEditable(false);
        if (index != null && !empty) {
            id = index;
            nombre.setText(PropController.getNombreProducto(id));
            precio.setText(Double.toString(PropController.getPrecioProducto(id)));
            setGraphic(pane);
        } else {
            setGraphic(null);
        }
    }
}
