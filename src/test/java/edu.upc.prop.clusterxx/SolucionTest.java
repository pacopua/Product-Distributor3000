package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

public class SolucionTest {
    private Solucion sol;

    @Before
    public void setUp() {
        sol = new Solucion(3, 3);
        sol.setDistribucion(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }

    @Test
    public void testDistribucion() {
        assert sol.getDistribucion()[0][0] == 1;
        assert sol.getDistribucion()[0][1] == 2;
        assert sol.getDistribucion()[0][2] == 3;
        assert sol.getDistribucion()[1][0] == 4;
        assert sol.getDistribucion()[1][1] == 5;
        assert sol.getDistribucion()[1][2] == 6;
        assert sol.getDistribucion()[2][0] == 7;
        assert sol.getDistribucion()[2][1] == 8;
        assert sol.getDistribucion()[2][2] == 9;
    }

    @Test
    public void testInterccambio() {
        sol.intercambiar_productos(0, 0, 2, 2);
        assert sol.getDistribucion()[0][0] == 9;
        assert sol.getDistribucion()[2][2] == 1;
    }

    @Test
    public void testSetters() {
        sol.setCalidad(1.0);
        assert sol.getCalidad() == 1.0;
        sol.setNumPasos(2);
        assert sol.getNumPasos() == 2;
    }

    @Test
    public void testEquals() {
        Solucion aux = new Solucion();
        aux.introducir_numero_columnas_i_filas(3, 3);
        aux.setDistribucion(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assert sol.equals(aux);
    }
}
