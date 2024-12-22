package edu.upc.prop.clusterxx.domain;

import java.io.Serializable;

/**
 * Clase que representa un estado del programa
 */
public class Estado implements Serializable {
    /**
     * Solucion del estado
     */
    private Solucion solucion;
    /**
     * Lista de productos del estado
     */
    private ListaProductos listaProductos;
    /**
     * Matriz de adyacencia del estado
     */
    private MatrizAdyacencia matrizAdyacencia;

    /**
     * Constructor de la clase Estado
     * @param solucion Solucion del estado
     * @param listaProductos Lista de productos del estado
     * @param matrizAdyacencia Matriz de adyacencia del estado
     */
    public Estado(Solucion solucion, ListaProductos listaProductos, MatrizAdyacencia matrizAdyacencia) {
        this.solucion = solucion;
        this.listaProductos = listaProductos;
        this.matrizAdyacencia = matrizAdyacencia;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public ListaProductos getListaProductos() {
        return listaProductos;
    }

    public MatrizAdyacencia getMatrizAdyacencia() {
        return matrizAdyacencia;
    }
}
