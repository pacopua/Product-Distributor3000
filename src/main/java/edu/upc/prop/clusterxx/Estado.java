package edu.upc.prop.clusterxx;

import java.io.Serializable;

public class Estado implements Serializable {
    private Solucion solucion;
    private ListaProductos listaProductos;
    // matriz
    Estado(Solucion solucion, ListaProductos listaProductos) {
        this.solucion = solucion;
        this.listaProductos = listaProductos;
    }
    public Solucion getSolucion() {
        return solucion;
    }
    public ListaProductos getListaProductos() {
        return listaProductos;
    }
}
