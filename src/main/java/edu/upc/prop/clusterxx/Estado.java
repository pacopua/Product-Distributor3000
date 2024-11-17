package edu.upc.prop.clusterxx;

import java.io.Serializable;

public class Estado implements Serializable {
    private Solucion solucion;
    private ListaProductos listaProductos;
    private MatrizAdyacencia matrizAdyacencia;
    Estado(Solucion solucion, ListaProductos listaProductos, MatrizAdyacencia matrizAdyacencia) {
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
