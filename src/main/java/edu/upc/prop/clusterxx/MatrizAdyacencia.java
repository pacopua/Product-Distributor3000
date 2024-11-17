//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import java.io.Serializable;
import java.util.ArrayList;

public class MatrizAdyacencia implements Serializable {
    private ArrayList<ArrayList<Double>> matriz;
    private int numProductos;

    /**
     * Constructor de la clase MatrizAdyacencia
     * @param n Número de productos
     */
    public MatrizAdyacencia(int n) {
        matriz = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matriz.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                matriz.get(i).add(-1.0);
            }
        }
        numProductos = n;
    }

    /**
     * Constructor de la clase MatrizAdyacencia
     * @param matriz Matriz de adyacencia
     */
    public MatrizAdyacencia(double[][] matriz) {
        // TODO: Implementar
    }

    /**
     * Modifica la sinergia entre dos productos
     *
     * @param p1             Producto 1
     * @param p2             Producto 2
     * @param nueva_sinergia Nueva sinergia entre los productos
     * @return true si se ha podido modificar la sinergia, false en caso contrario
     */
    public void modificar_sinergias(int p1, int p2, double nueva_sinergia) {
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            matriz.get(p1).set(p2, nueva_sinergia);
            matriz.get(p2).set(p1, nueva_sinergia);
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
            return matriz.get(p1).get(p2);
        }
        return -1;
    }

    /**
     * Obtiene la matriz de adyacencia
     * @return Matriz de adyacencia
     */
    public ArrayList<ArrayList<Double>> getMatriz() {
        return matriz;
    }

    /**
     * Establece la matriz de adyacencia
     * @param matriz Matriz de adyacencia
     */
    public void setMatriz(ArrayList<ArrayList<Double>> matriz) {
        this.matriz = matriz;
        numProductos = matriz.size();
    }

    /**
     * Obtiene el número de productos
     * @return Número de productos
     */
    public int getNumProductos() {
        return numProductos;
    }
}
