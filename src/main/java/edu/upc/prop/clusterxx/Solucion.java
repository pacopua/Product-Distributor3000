package edu.upc.prop.clusterxx;

import java.util.Arrays;

public class Solucion {
    private int[][] distribucion;
    private double calidad;
    private int num_pasos;

    public void imprimirDistribucio () {}

    public boolean intercambiarProductos(int p1, int p2) {
        return false;
    }

    public boolean importarSolucion(String path) {
        return false;
    }

    public boolean exportarSolucion(String path) {
        return false;
    }

    public void calcular_solucion_optima() {}
    public void calcular_solucion_rapida() {}

    public int[][] getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(int[][] distribucion) {
        this.distribucion = distribucion;
    }

    public double getCalidad() {
        return calidad;
    }

    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }

    public int getNum_pasos() {
        return num_pasos;
    }

    public void setNum_pasos(int num_pasos) {
        this.num_pasos = num_pasos;
    }

    @Override
    public String toString() {
        return "Solucion{" +
                "distribucion=" + Arrays.toString(distribucion) +
                ", calidad=" + calidad +
                ", num_pasos=" + num_pasos +
                '}';
    }
}
