package edu.upc.prop.clusterxx.domain;

import java.io.Serializable;

/**
 * Clase que representa un estado del programa
 */
public class Estado implements Serializable {
    /**
     * Solucion
     */
    private Solucion solucion;
    /**
     * Lista de productos
     */
    private ListaProductos listaProductos;
    /**
     * Matriz de adyacencia
     */
    private MatrizAdyacencia matrizAdyacencia;

    /**
     * Constructor de la clase Estado
     * @param solucion Solucion
     * @param listaProductos Lista de productos
     * @param matrizAdyacencia Matriz de adyacencia
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
