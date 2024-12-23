package edu.upc.prop.clusterxx.domain;

import java.io.Serializable;

/**
 * Clase Producto
 * Representa un producto con un nombre y un precio
 */
public class Producto implements Serializable, Cloneable {
    /**
     * Nombre del producto
     */
    private String nombre;
    /**
     * Precio del producto
     */
    private double precio;

    /**
     * Constructor de la clase Producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     */
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Método que devuelve el nombre del producto
     * @return Nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre del producto
     * @param nombre Nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve el precio del producto
     * @return Precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Método que establece el precio del producto
     * @param precio Precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Método que devuelve el producto como una cadena de texto
     * @return Producto como cadena de texto
     */
    @Override
    public String toString() {
        return "Producto{" +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }

    /**
     * Método clone
     * @return Clon del producto
     */
    @Override
    public Producto clone() {
        try {
            Producto clone = (Producto) super.clone();
            clone.setNombre(nombre);
            clone.setPrecio(precio);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
