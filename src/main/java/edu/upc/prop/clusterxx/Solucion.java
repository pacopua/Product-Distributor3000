package edu.upc.prop.clusterxx;

import java.io.Serializable;
import java.util.Arrays;

public class Solucion implements Serializable, Cloneable {
    private int[][] distribucion;
    private double calidad;
    private int numPasos;
    private int filas;
    private int columnas;

    /**
     * Constructor de la clase Solucion
     */
    public Solucion() {
        this.calidad = 0;
        this.numPasos = 0;
    }

    /**
     * Constructor de la clase Solucion
     * @param files Número de filas de la solución
     * @param columnes Número de columnas de la solución
     */
    public Solucion(int files, int columnes) {
        this.calidad = 0;
        this.numPasos = 0;
        this.introducir_numero_columnas_i_filas(files, columnes);
    }

    /**
     * Establece el número de filas y columnas de la solución
     * @param files Número de filas
     * @param columnes Número de columnas
     */
    public void introducir_numero_columnas_i_filas(int files, int columnes) {
        this.distribucion = new int[files][columnes];
        this.filas = files;
        this.columnas = columnes;
    }

    /**
     * Imprime la distribución de la solución en la salida estándar
     */
    public void imprimir_distribucion() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(distribucion[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Intercambia dos productos de la solución
     * @param fila_p1 Fila del primer producto
     * @param columna_p1 Columna del primer producto
     * @param fila_p2 Fila del segundo producto
     * @param columna_p2 Columna del segundo producto
     * @return true si se ha podido intercambiar los productos, false en caso contrario
     */
    public boolean intercambiar_productos(int fila_p1, int columna_p1, int fila_p2, int columna_p2) {
        if (0 <= fila_p1 && fila_p1 < filas && 0 <= columna_p1 && columna_p1 < columnas &&
            0 <= fila_p2 && fila_p2 < filas && 0 <= columna_p2 && columna_p2 < columnas) {
            int aux = distribucion[fila_p1][columna_p1];
            distribucion[fila_p1][columna_p1] = distribucion[fila_p2][columna_p2];
            distribucion[fila_p2][columna_p2] = aux;
            return true;
        }
        return false;
    }

    /**
     * Devuelve la distribución de la solución
     * @return Matriz de enteros con la distribución de la solución
     */
    public int[][] getDistribucion() {
        return distribucion;
    }

    /**
     * Establece la distribución de la solución
     * @param distribucion Matriz de enteros con la distribución de la solución
     */
    public void setDistribucion(int[][] distribucion) {
        this.distribucion = distribucion;
    }

    /**
     * Devuelve la calidad de la solución
     * @return Calidad de la solución
     */
    public double getCalidad() {
        return calidad;
    }

    /**
     * Establece la calidad de la solución
     * @param calidad Calidad de la solución
     */
    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }

    /**
     * Devuelve el número de pasos de la solución
     * @return Número de pasos de la solución
     */
    public int getNumPasos() {
        return numPasos;
    }

    /**
     * Establece el número de pasos de la solución
     * @param numPasos Número de pasos de la solución
     */
    public void setNumPasos(int numPasos) {
        this.numPasos = numPasos;
    }

    @Override
    protected Solucion clone() throws CloneNotSupportedException {
        Solucion solucion = (Solucion) super.clone();
        // implement clone function
        solucion.introducir_numero_columnas_i_filas(filas, columnas);
        solucion.setCalidad(calidad);
        solucion.setNumPasos(numPasos);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                solucion.distribucion[i][j] = distribucion[i][j];
            }
        }
        return solucion;
    }

    @Override
    public String toString() {
        return "Solucion{" +
                "distribucion=" + Arrays.toString(distribucion) +
                ", calidad=" + calidad +
                ", num_pasos=" + numPasos +
                '}';
    }
}
