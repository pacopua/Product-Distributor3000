package edu.upc.prop.clusterxx;

import java.io.Serializable;

public class Estado implements Serializable {
    private Solucion solucion;
    // lista prod
    // matriz
    Estado(Solucion solucion) {
        this.solucion = solucion;
    }

    public Solucion getSolucion() {
        return solucion;
    }
}
