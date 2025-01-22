package edu.upc.prop.clusterxx.domain;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador de los productos, contiene una lista de todos los productos.
 * El id de un producto es su posición en la lista.
 */
public class ListaProductos implements Serializable, Cloneable {
    /**
     * Lista de productos.
     */
    private ArrayList<Producto> productos;

    /**
     * Constructora, el estado inicial es una lista vacía.
     */
    public ListaProductos() {
        productos = new ArrayList<>();
    }

    /**
     * Añade un solo producto.
     * @param producto producto que añadir.
     */
    public void addProducto(Producto producto) {
        productos.add(producto);
    }

    /**
     * Devuelve el tamaño de la lista
     * @return cantidad de productos.
     */
    public int getCantidadProductos() {
        return productos.size();
    }

    /**
     * @return el arraylist de los productos.
     */
    public List<Producto> getListaProductos() {
        return productos;
    }

    /**
     * @return una copia de la lista de productos.
     */
    @Override
    public ListaProductos clone() {
        ListaProductos copia = new ListaProductos();
        ArrayList<Producto> l = new ArrayList<>();
        for (Producto producto : productos) {
            Producto p = new Producto(producto.getNombre(), producto.getPrecio());
            l.add(p);
        }
        copia.productos = l;
        return copia;
    }

    /**
     * Elimina un producto de la lista
     * @param id identificador del producto que queremos eliminar.
     * @return true si se ha podido eliminar y false si no.
     */
    public boolean eliminarProducto(int id) {
        return productos.remove(id) != null;
    }

    /**
     * Devuelve un producto.
     * @param id identificador del producto que queremos.
     * @return instancia del producto, en caso de que no exista NULL.
     */
    public Optional<Producto> getProducto(int id) {
        return Optional.ofNullable(productos.get(id));
    }

    /**
     * Metodo para devolver la lista de productos como un string
     * @return String con la lista de productos
     */
    @Override
    public String toString() {
        return "ListaProductos{" +
                "productos=" + productos +
                '}';
    }
}

