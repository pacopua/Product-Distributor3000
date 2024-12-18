package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

public class DomainProductoController {
    public boolean anyadirProducto(String nombre, double precio) {
        Producto producto = new Producto(nombre, precio);

        for (Producto prod : GestorPesistencia.getListaProductos().getListaProductos()) {
            if (prod.getNombre().equals(producto.getNombre())) {
                return true;
            }
        }

        GestorPesistencia.getListaProductos().addProducto(producto);

        MatrizAdyacencia matrizNueva = new MatrizAdyacencia(GestorPesistencia.getListaProductos().getCantidadProductos());
        for (int i = 0; i < GestorPesistencia.getMatrizAdyacencia().getNumProductos(); i++)
            for (int j = 0; j < GestorPesistencia.getMatrizAdyacencia().getNumProductos(); j++)
                matrizNueva.modificar_sinergias(i, j, GestorPesistencia.getMatrizAdyacencia().getSinergia(i, j));
        GestorPesistencia.setMatrizAdyacencia(matrizNueva);
        return false;
    }
}
