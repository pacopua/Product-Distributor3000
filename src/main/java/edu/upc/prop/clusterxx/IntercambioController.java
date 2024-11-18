package edu.upc.prop.clusterxx;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
            // lista de productos de solucion?
            Sistema.getSolucion().getDistribucionProductos();
            return null;
        }
    };
    @FXML
    protected void initialize() {
    }
    @FXML
    protected void onIntercambiarProducto() {
        // recalcular calidad solucion
    }
}
