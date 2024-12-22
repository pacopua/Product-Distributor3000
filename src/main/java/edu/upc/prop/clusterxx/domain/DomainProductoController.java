package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import javafx.util.Pair;

import java.util.ArrayList;

public class DomainProductoController {

    public double getSinergias(int id1, int id2) {
        return GestorPesistencia.getMatrizAdyacencia().getSinergia(id1, id2);
    }

    public void setSinergias(int id1, int id2, double sinergia) {
        DomainEstadoController.actualizarHistorial();
        GestorPesistencia.getMatrizAdyacencia().modificar_sinergias(id1, id2, sinergia);
    }

    public boolean existeProductoConDiferenteID(int id, String nombre) {

        for (Producto prod : GestorPesistencia.getListaProductos().getListaProductos()) {
            if (id != GestorPesistencia.getListaProductos().getListaProductos().indexOf(prod) && prod.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    //Funcion que devuelve todas las parejas de sinergias
    public ArrayList<Pair<Integer, Integer>> lista_sinergias() {
        ArrayList<Pair<Integer, Integer>> lista = new ArrayList<>();
        for (int i = 0; i < GestorPesistencia.getMatrizAdyacencia().getNumProductos(); i++) {
            for (int j = i + 1; j < GestorPesistencia.getMatrizAdyacencia().getNumProductos(); j++) {
                lista.add(new Pair<>(i, j));
            }
        }
        return lista;
    }

    public boolean cambiarNombreProducto(int id, String nuevoNombre) {
        DomainEstadoController.actualizarHistorial();
        GestorPesistencia.getListaProductos().getListaProductos().get(id).setNombre(nuevoNombre);
        return false;
    }

    public boolean eliminarProductoPorId(int id) {
        if (id >= 0 && id < GestorPesistencia.getListaProductos().getListaProductos().size()) {
            DomainEstadoController.actualizarHistorial();
            GestorPesistencia.getListaProductos().eliminarProducto(id);
            GestorPesistencia.getMatrizAdyacencia().eliminarProducto(id);
            return true;
        }
        return false;
    }

    public String getNombreProductoPorId(int id) {
        if (id >= 0 && id < GestorPesistencia.getListaProductos().getListaProductos().size()) {
            return GestorPesistencia.getListaProductos().getListaProductos().get(id).getNombre();
        }
        return null;
    }

    public void setPrecioProductoPorId(int id, double precio) {
        if (id >= 0 && id < GestorPesistencia.getListaProductos().getListaProductos().size()) {
            DomainEstadoController.actualizarHistorial();
            GestorPesistencia.getListaProductos().getListaProductos().get(id).setPrecio(precio);
        }
    }

    public double getPrecioProductoPorId(int id) {
        if (id >= 0 && id < GestorPesistencia.getListaProductos().getListaProductos().size()) {
            return GestorPesistencia.getListaProductos().getListaProductos().get(id).getPrecio();
        }
        return -1;
    }

    public ArrayList<Integer> getProductsIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < GestorPesistencia.getListaProductos().getListaProductos().size(); i++) {
            ids.add(i);
        }
        return ids;
    }

    public boolean anyadirProducto(String nombre, double precio) {
        Producto producto = new Producto(nombre, precio);

        for (Producto prod : GestorPesistencia.getListaProductos().getListaProductos()) {
            if (prod.getNombre().equals(producto.getNombre())) {
                return true;
            }
        }

        DomainEstadoController.actualizarHistorial();
        GestorPesistencia.getListaProductos().addProducto(producto);

        MatrizAdyacencia matrizNueva = new MatrizAdyacencia(GestorPesistencia.getListaProductos().getCantidadProductos());
        for (int i = 0; i < GestorPesistencia.getMatrizAdyacencia().getNumProductos(); i++)
            for (int j = 0; j < GestorPesistencia.getMatrizAdyacencia().getNumProductos(); j++)
                matrizNueva.modificar_sinergias(i, j, GestorPesistencia.getMatrizAdyacencia().getSinergia(i, j));
        GestorPesistencia.setMatrizAdyacencia(matrizNueva);
        return false;
    }
}
