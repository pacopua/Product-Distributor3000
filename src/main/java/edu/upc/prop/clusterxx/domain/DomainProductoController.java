package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import javafx.util.Pair;

import java.util.ArrayList;

public class DomainProductoController {
    private static DomainProductoController instance;

    private DomainProductoController() {
    }

    public static DomainProductoController getInstance() {
        if (instance == null) {
            instance = new DomainProductoController();
        }
        return instance;
    }

    public double getSinergias(int id1, int id2) {
        return GestorPesistencia.getInstance().getMatrizAdyacencia().getSinergia(id1, id2);
    }

    public void setSinergias(int id1, int id2, double sinergia) {
        DomainEstadoController.getInstance().actualizarHistorial();
        GestorPesistencia.getInstance().getMatrizAdyacencia().modificar_sinergias(id1, id2, sinergia);
    }

    public boolean existeProductoConDiferenteID(int id, String nombre) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        for (Producto prod : gp.getListaProductos().getListaProductos()) {
            if (id != gp.getListaProductos().getListaProductos().indexOf(prod) && prod.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    //Funcion que devuelve todas las parejas de sinergias
    public ArrayList<Pair<Integer, Integer>> lista_sinergias() {
        ArrayList<Pair<Integer, Integer>> lista = new ArrayList<>();
        GestorPesistencia gp = GestorPesistencia.getInstance();
        for (int i = 0; i < gp.getMatrizAdyacencia().getNumProductos(); i++) {
            for (int j = i + 1; j < gp.getMatrizAdyacencia().getNumProductos(); j++) {
                lista.add(new Pair<>(i, j));
            }
        }
        return lista;
    }

    public boolean cambiarNombreProducto(int id, String nuevoNombre) {
        DomainEstadoController.getInstance().actualizarHistorial();
        GestorPesistencia.getInstance().getListaProductos().getListaProductos().get(id).setNombre(nuevoNombre);
        return false;
    }

    public boolean eliminarProductoPorId(int id) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            DomainEstadoController.getInstance().actualizarHistorial();
            gp.getListaProductos().eliminarProducto(id);
            gp.getMatrizAdyacencia().eliminarProducto(id);
            return true;
        }
        return false;
    }

    public String getNombreProductoPorId(int id) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            return gp.getListaProductos().getListaProductos().get(id).getNombre();
        }
        return null;
    }

    public void setPrecioProductoPorId(int id, double precio) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (precio >= 0 && id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            DomainEstadoController.getInstance().actualizarHistorial();
            gp.getListaProductos().getListaProductos().get(id).setPrecio(precio);
        }
    }

    public double getPrecioProductoPorId(int id) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            return gp.getListaProductos().getListaProductos().get(id).getPrecio();
        }
        return -1;
    }

    public ArrayList<Integer> getProductsIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < GestorPesistencia.getInstance().getListaProductos().getListaProductos().size(); i++) {
            ids.add(i);
        }
        return ids;
    }

    public boolean anyadirProducto(String nombre, double precio) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        Producto producto = new Producto(nombre, precio);

        for (Producto prod : gp.getListaProductos().getListaProductos()) {
            if (prod.getNombre().equals(producto.getNombre())) {
                return true;
            }
        }

        DomainEstadoController.getInstance().actualizarHistorial();
        gp.getListaProductos().addProducto(producto);

        MatrizAdyacencia matrizNueva = new MatrizAdyacencia(gp.getListaProductos().getCantidadProductos());
        for (int i = 0; i < gp.getMatrizAdyacencia().getNumProductos(); i++)
            for (int j = 0; j < gp.getMatrizAdyacencia().getNumProductos(); j++)
                matrizNueva.modificar_sinergias(i, j, gp.getMatrizAdyacencia().getSinergia(i, j));
        gp.setMatrizAdyacencia(matrizNueva);
        return false;
    }
}
