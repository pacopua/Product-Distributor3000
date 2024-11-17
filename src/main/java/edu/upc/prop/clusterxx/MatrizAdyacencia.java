//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import java.io.Serializable;

public class MatrizAdyacencia implements Serializable {
    private static double[][] matriz;  // por qué era public?
    private static int numProductos;

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
    public boolean modificar_sinergias(int p1, int p2, double nueva_sinergia) { // ponia singergias 😭
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            matriz[p1][p2] = nueva_sinergia;
            matriz[p2][p1] = nueva_sinergia;
            return true;
        }
        return false;
    }

    /**
     * Obtiene la sinergia entre dos productos
     * @param p1 Producto 1
     * @param p2 Producto 2
     * @return Sinergia entre los productos, -1 si son el mismo producto
     */
    public static double getSinergia(int p1, int p2) {
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            return matriz[p1][p2];
        }
        else if (p1 == -1 || p2 == -1) return 0;
        else if(p1 == p2) return -1;
        return -1;
    }

    /**
     * Obtiene la matriz de adyacencia
     * @return Matriz de adyacencia
     */
    public static double[][] getMatriz() {
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
