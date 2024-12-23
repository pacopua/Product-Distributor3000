package edu.upc.prop.clusterxx.domain;

import java.io.Serializable;

/**
 * Clase MatrizAdyacencia
 * Representa una matriz de adyacencia entre productos
 */
public class MatrizAdyacencia implements Serializable, Cloneable {
    /**
     * Matriz de adyacencia
     */
    private double[][] matriz;
    /**
     * Número de productos
     */
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
     * Añade un producto a la matriz de adyacencia
     */
    public void anadirProducto() {
        double[][] nueva_matriz = new double[numProductos + 1][numProductos + 1];
        for (int i = 0; i < numProductos; i++) {
            System.arraycopy(matriz[i], 0, nueva_matriz[i], 0, numProductos);
        }
        matriz = nueva_matriz;
        numProductos++;
    }

    /**
     * Elimina un producto de la matriz de adyacencia
     * @param producto Producto a eliminar
     * @return true si se ha podido eliminar el producto, false en caso contrario
     */
    public boolean eliminarProducto(int producto) {
        if (0 <= producto && producto < numProductos) {
            double[][] nueva_matriz = new double[numProductos - 1][numProductos - 1];
            for (int i = 0; i < numProductos; i++) {
                for (int j = 0; j < numProductos; j++) {
                    if (i < producto && j < producto) {
                        nueva_matriz[i][j] = matriz[i][j];
                    } else if (i < producto && j > producto) {
                        nueva_matriz[i][j - 1] = matriz[i][j];
                    } else if (i > producto && j < producto) {
                        nueva_matriz[i - 1][j] = matriz[i][j];
                    } else if (i > producto && j > producto) {
                        nueva_matriz[i - 1][j - 1] = matriz[i][j];
                    }
                }
            }
            matriz = nueva_matriz;
            numProductos--;
            return true;
        }
        return false;
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
        if(p1 == -1 || p2 == -1) return 0;
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

    @Override
    public MatrizAdyacencia clone() {
        try {
            super.clone();
        } catch (Exception e) {
            return null;
        }
        MatrizAdyacencia ma = new MatrizAdyacencia(numProductos);
        ma.matriz = new double[numProductos][numProductos];
        for (int i = 0; i < numProductos; i++) {
            System.arraycopy(matriz[i], 0, ma.matriz[i], 0, numProductos);
        }
        return ma;
    }
}
