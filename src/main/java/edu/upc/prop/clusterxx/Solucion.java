package edu.upc.prop.clusterxx;

import java.util.Arrays;

public class Solucion {
    private int[][] distribucion;
    private double calidad;
    private int numPasos;
    private int filas;
    private int columnas;

    /**
     * Constructor de la clase Solucion
     * @param files Número de filas de la solución
     * @param columnes Número de columnas de la solución
     */
    public Solucion(int files, int columnes) {
        this.calidad = 0;
        this.numPasos = 0;
        this.establecerFilasColumnas(files, columnes);
    }

    /**
     * Constructor de la clase Solucion
     */
    public Solucion() {
        this.calidad = 0;
        this.numPasos = 0;
    }

    /**
     * Establece el número de filas y columnas de la solución
     * @param files Número de filas
     * @param columnes Número de columnas
     */
    public void establecerFilasColumnas(int files, int columnes) {
        this.distribucion = new int[files][columnes];
        this.filas = files;
        this.columnas = columnes;
    }

    /**
     * Imprime la distribución de la solución en la salida estándar
     */
    public void imprimirDistribucio () {}

    /**
     * Intercambia dos productos de la solución
     * @param x_p1 Fila del primer producto
     * @param y_p1 Columna del primer producto
     * @param x_p2 Fila del segundo producto
     * @param y_p2 Columna del segundo producto
     * @return true si se ha podido intercambiar los productos, false en caso contrario
     */
    public boolean intercambiarProductos(int x_p1, int y_p1, int x_p2, int y_p2) {
        if (0 <= x_p1 && x_p1 < filas && 0 <= y_p1 && y_p1 < columnas &&
            0 <= x_p2 && x_p2 < filas && 0 <= y_p2 && y_p2 < columnas) {
            int aux = distribucion[x_p1][y_p1];
            distribucion[x_p1][y_p1] = distribucion[x_p2][y_p2];
            distribucion[x_p2][y_p2] = aux;
            return true;
        }
        return false;
    }

    public boolean importarSolucion(String path) {
        // TODO
        return false;
    }

    public boolean exportarSolucion(String path) {
        // TODO
        return false;
    }

    public void calcular_solucion_optima() {
        // TODO
    }

    public void calcular_solucion_rapida() {
        // TODO
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
    public String toString() {
        return "Solucion{" +
                "distribucion=" + Arrays.toString(distribucion) +
                ", calidad=" + calidad +
                ", num_pasos=" + numPasos +
                '}';
    }
}
