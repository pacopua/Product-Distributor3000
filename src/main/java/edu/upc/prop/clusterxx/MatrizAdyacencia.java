//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import java.io.Serializable;

public class MatrizAdyacencia implements Serializable {
    private double[][] matriz;
    private int numProductos;

    /**
     * Constructor de la clase MatrizAdyacencia
     * @param n Número de productos
     */
    public MatrizAdyacencia(int n) {
        matriz = new double[n][n];
        numProductos = n;
    }

    /**
     * Constructor de la clase MatrizAdyacencia
     * @param matriz Matriz de adyacencia
     */
    public MatrizAdyacencia(double[][] matriz) {
        this.matriz = matriz;
        numProductos = matriz.length;
    }

    /**
     * Modifica la sinergia entre dos productos
     *
     * @param p1             Producto 1
     * @param p2             Producto 2
     * @param nueva_sinergia Nueva sinergia entre los productos
     */
    public void modificar_sinergias(int p1, int p2, double nueva_sinergia) {
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            matriz[p1][p2] = nueva_sinergia;
            matriz[p2][p1] = nueva_sinergia;
        }
    }

    /**
     * Obtiene la sinergia entre dos productos
     * @param p1 Producto 1
     * @param p2 Producto 2
     * @return Sinergia entre los productos, -1 si son el mismo producto
     */
    public double getSinergia(int p1, int p2) {
        if (p1 != p2
                && 0 <= p1
                && p1 < numProductos
                && 0 <= p2
                && p2 < numProductos) {
            return matriz[p1][p2];
        }
        return -1;
    }

    /**
     * Obtiene la matriz de adyacencia
     * @return Matriz de adyacencia
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * Establece la matriz de adyacencia
     * @param matriz Matriz de adyacencia
     */
    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
        numProductos = matriz.length;
    }

    /**
     * Obtiene el número de productos
     * @return Número de productos
     */
    public int getNumProductos() {
        return numProductos;
    }
}
