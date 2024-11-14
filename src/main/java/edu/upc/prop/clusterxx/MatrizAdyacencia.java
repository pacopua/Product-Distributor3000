package edu.upc.prop.clusterxx;


import java.io.*;
import java.util.ArrayList;

public class MatrizAdyacencia {
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
     * Modifica la sinergia entre dos productos
     * @param p1 Producto 1
     * @param p2 Producto 2
     * @param nueva_sinergia Nueva sinergia entre los productos
     * @return true si se ha podido modificar la sinergia, false en caso contrario
     */
    public boolean modificar_singergias(int p1, int p2, double nueva_sinergia) {
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            matriz[p1][p2] = nueva_sinergia;
            matriz[p2][p1] = nueva_sinergia;
            return true;
        }
        return false;
    }

    /**
     * Obtiene la matriz de adyacencia
     * @return Matriz de adyacencia
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * Obtiene el número de productos
     * @return Número de productos
     */
    public int getNumProductos() {
        return numProductos;
    }
}
