package edu.upc.prop.clusterxx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class IntercambioController {
    @FXML
    ChoiceBox producto1;
    @FXML
    ChoiceBox producto2;
    private static final StringConverter productoConverter = new StringConverter() {
        @Override
        public String toString(Object object) {
            if (object instanceof Producto) return ((Producto) object).getNombre();
            else return null;
        }

        @Override
        public Object fromString(String string) {
            for (Producto p : Sistema.getSolucion().getListaProductos().getListaProductos()) {
                    if (string.equals(p.getNombre())) return p;
            }
            return null;
        }
    };
    @FXML
    protected void initialize() {
        producto1.setConverter(productoConverter);
        producto2.setConverter(productoConverter);
        producto1.setItems(FXCollections.observableArrayList(Sistema.getSolucion().getListaProductos().getListaProductos()));
        producto2.setItems(FXCollections.observableArrayList(Sistema.getSolucion().getListaProductos().getListaProductos()));
    }
    @FXML
    protected void onIntercambiarProducto() {
        Producto p1 = (Producto) producto1.getValue();
        int x1 = 0;
        int y1 = 0;
        Producto p2 = (Producto) producto2.getValue();
        int x2 = 0;
        int y2 = 0;
        for (int i = 0; i < Sistema.getSolucion().getDistribucionProductos().length; i++) {
            for (int j = 0; j < Sistema.getSolucion().getDistribucionProductos()[0].length; j++) {
                if (p1 == Sistema.getSolucion().getDistribucionProductos()[i][j]) {
                    x1 = i;
                    y1 = j;
                }
                if (p2 == Sistema.getSolucion().getDistribucionProductos()[i][j]) {
                    x2 = i;
                    y2 = j;
                }
            }
        }
        Sistema.getSolucion().getDistribucionProductos()[x1][y1] = p2;
        Sistema.getSolucion().getDistribucionProductos()[x2][y2] = p1;
        Sistema.getSolucion().getDistribucion()[x1][y1] = Sistema.getSolucion().getListaProductos().getListaProductos().indexOf(p2);
        Sistema.getSolucion().getDistribucion()[x2][y2] = Sistema.getSolucion().getListaProductos().getListaProductos().indexOf(p1);

        ((Stage) producto1.getScene().getWindow()).close();
    }
}
