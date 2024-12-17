package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.Sistema;
import javafx.stage.Stage;

public class DomainProductoController {
    public boolean anyadirProducto(String nombre, double precio) {
        Producto producto = new Producto(nombre, precio);

        for (Producto prod : Sistema.getListaProductos().getListaProductos()) {
            if (prod.getNombre().equals(producto.getNombre())) {
                return true;
            }
        }

        Sistema.getListaProductos().addProducto(producto);

        MatrizAdyacencia matrizNueva = new MatrizAdyacencia(Sistema.getListaProductos().getCantidadProductos());
        for (int i = 0; i < Sistema.getMatrizAdyacencia().getNumProductos(); i++)
            for (int j = 0; j < Sistema.getMatrizAdyacencia().getNumProductos(); j++)
                matrizNueva.modificar_sinergias(i, j, Sistema.getMatrizAdyacencia().getSinergia(i, j));
        Sistema.setMatrizAdyacencia(matrizNueva);
        return false;
    }
}
