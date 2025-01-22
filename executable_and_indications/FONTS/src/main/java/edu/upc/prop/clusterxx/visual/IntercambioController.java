package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * Clase IntercambioController
 * Controlador de la ventana de intercambio de productos
 */
public class IntercambioController {
    /**
     * ChoiceBox de producto1
     */
    @FXML
    ChoiceBox<String> producto1;
    /**
     * ChoiceBox de producto2
     */
    @FXML
    ChoiceBox<String> producto2;
    /**
     * Controlador de soluciones
     */
    private final DomainSolucionController solucionController = DomainSolucionController.getInstance();

    /**
     * Inicializa la ventana
     */
    @FXML
    protected void initialize() {
        producto1.setItems(FXCollections.observableArrayList(PropController.getProductsIntercambio()));
        producto2.setItems(FXCollections.observableArrayList(PropController.getProductsIntercambio()));
    }

    /**
     * Intercambia los productos seleccionados
     */
    @FXML
    protected void onIntercambiarProducto() {
        String nombreP1 = producto1.getValue();
        String nombreP2 = producto2.getValue();
        solucionController.intercambiarProductos(nombreP1, nombreP2);
        PropController p = new PropController();
        p.actualizarAfterSwap(nombreP1, nombreP2);
        ((Stage) producto1.getScene().getWindow()).close();
    }
}
