//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import org.junit.Test;
//import edu.upc.prop.clusterxx.MatrizAdyacencia;  <- para qué -Marcel

import java.util.Arrays;

public class TestMatrizAdyacencia {
    @Test
    public void testMatrizAdyacencia() {
        MatrizAdyacencia ma = new MatrizAdyacencia(3);
        ma.modificar_singergias(0, 1, 1.0);
        ma.modificar_singergias(0, 2, 2.0);
        ma.modificar_singergias(1, 2, 3.0);
        assert ma.getSinergia(0, 1) == 1.0;
        assert ma.getSinergia(1, 0) == 1.0;
        assert ma.getSinergia(2, 0) == 2.0;
        assert Arrays.deepEquals(ma.getMatriz(), new double[][]{{0.0, 1.0, 2.0}, {1.0, 0.0, 3.0}, {2.0, 3.0, 0.0}});
    }
}
