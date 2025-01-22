package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Clase DomainProductoController
 * Controlador de productos del dominio
 */
public class DomainProductoController {
    /**
     * Instancia única de DomainProductoController
     */
    private static DomainProductoController instance;

    /**
     * Constructor privado para el singleton
     */
    private DomainProductoController() {
    }

    /**
     * Singleton
     * @return instancia única de DomainProductoController
     */
    public static DomainProductoController getInstance() {
        if (instance == null) {
            instance = new DomainProductoController();
        }
        return instance;
    }

    /**
     * Devuelve la sinergia entre dos productos
     * @param id1 id del primer producto
     * @param id2 id del segundo producto
     * @return sinergia entre los dos productos
     */
    public double getSinergias(int id1, int id2) {
        return GestorPesistencia.getInstance().getMatrizAdyacencia().getSinergia(id1, id2);
    }

    /**
     * Establece la sinergia entre dos productos
     * @param id1 id del primer producto
     * @param id2 id del segundo producto
     * @param sinergia sinergia entre los dos productos
     */
    public void setSinergias(int id1, int id2, double sinergia) {
        DomainEstadoController.getInstance().actualizarHistorial();
        GestorPesistencia.getInstance().getMatrizAdyacencia().modificar_sinergias(id1, id2, sinergia);
    }

    /**
     * Comprueba si existe un producto con un nombre dado
     * @param id id del producto
     * @param nombre nombre del producto
     * @return true si existe un producto con el nombre dado, false en caso contrario
     */
    public boolean existeProductoConDiferenteID(int id, String nombre) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        for (Producto prod : gp.getListaProductos().getListaProductos()) {
            if (id != gp.getListaProductos().getListaProductos().indexOf(prod) && prod.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve una lista con todas las sinergias posibles
     * @return lista con todas las sinergias posibles
     */
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

    /**
     * Cambia el nombre de un producto
     * @param id id del producto
     * @param nuevoNombre nuevo nombre del producto
     */
    public void cambiarNombreProducto(int id, String nuevoNombre) {
        if (!existeProductoConDiferenteID(id, nuevoNombre)) {
            DomainEstadoController.getInstance().actualizarHistorial();
            GestorPesistencia.getInstance().getListaProductos().getListaProductos().get(id).setNombre(nuevoNombre);
        }
    }

    /**
     * Elimina un producto por su id
     * @param id id del producto
     */
    public void eliminarProductoPorId(int id) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            DomainEstadoController.getInstance().actualizarHistorial();
            gp.getListaProductos().eliminarProducto(id);
            gp.getMatrizAdyacencia().eliminarProducto(id);
        }
    }

    /**
     * Devuelve el nombre de un producto por su id
     * @param id id del producto
     * @return nombre del producto
     */
    public String getNombreProductoPorId(int id) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            return gp.getListaProductos().getListaProductos().get(id).getNombre();
        }
        return null;
    }

    /**
     * Establece el precio de un producto por su id
     * @param id id del producto
     * @param precio nuevo precio del producto
     */
    public void setPrecioProductoPorId(int id, double precio) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (precio >= 0 && id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            DomainEstadoController.getInstance().actualizarHistorial();
            gp.getListaProductos().getListaProductos().get(id).setPrecio(precio);
        }
    }

    /**
     * Devuelve el precio de un producto por su id
     * @param id id del producto
     * @return precio del producto
     */
    public double getPrecioProductoPorId(int id) {
        GestorPesistencia gp = GestorPesistencia.getInstance();
        if (id >= 0 && id < gp.getListaProductos().getListaProductos().size()) {
            return gp.getListaProductos().getListaProductos().get(id).getPrecio();
        }
        return -1;
    }

    /**
     * Devuelve una lista con los ids de los productos
     * @return lista con los ids de los productos
     */
    public ArrayList<Integer> getProductsIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < GestorPesistencia.getInstance().getListaProductos().getCantidadProductos(); i++) {
            ids.add(i);
        }
        return ids;
    }

    /**
     * Añade un producto a la lista de productos
     * @param nombre nombre del producto
     * @param precio precio del producto
     * @return -1 si el precio es negativo, 0 si el producto ya existe, 1 si se ha añadido correctamente
     */
    public int anyadirProducto(String nombre, double precio) {
        if (precio < 0) {
            return -1;
        }
        GestorPesistencia gp = GestorPesistencia.getInstance();
        Producto producto = new Producto(nombre, precio);

        for (Producto prod : gp.getListaProductos().getListaProductos()) {
            if (prod.getNombre().equals(producto.getNombre())) {
                return 0;
            }
        }

        DomainEstadoController.getInstance().actualizarHistorial();
        gp.getListaProductos().addProducto(producto);

        MatrizAdyacencia matrizNueva = new MatrizAdyacencia(gp.getListaProductos().getCantidadProductos());
        for (int i = 0; i < gp.getMatrizAdyacencia().getNumProductos(); i++)
            for (int j = 0; j < gp.getMatrizAdyacencia().getNumProductos(); j++)
                matrizNueva.modificar_sinergias(i, j, gp.getMatrizAdyacencia().getSinergia(i, j));
        gp.setMatrizAdyacencia(matrizNueva);
        return 1;
    }
}
