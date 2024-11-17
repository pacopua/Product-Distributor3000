package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SolucionTest {
    private Solucion sol;

    @Before
    public void setUp() {
        sol = new Solucion(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }

    /**
     * Tests de la funcion de intercambio de productos
     */
    @Test
    public void testInterccambio() {
        // Limites
        assertFalse(sol.intercambiar_productos(3, 0 , 0, 0));
        assertFalse(sol.intercambiar_productos(0, 3 , 0, 0));
        assertFalse(sol.intercambiar_productos(-1, 0 , 0, 0));
        assertFalse(sol.intercambiar_productos(0, -1 , 0, 0));
        assertFalse(sol.intercambiar_productos(0, 0 , 3, 0));
        assertFalse(sol.intercambiar_productos(0, 0 , 0, 3));
        assertFalse(sol.intercambiar_productos(0, 0 , -1, 0));
        assertFalse(sol.intercambiar_productos(0, 0 , 0, -1));

        // Caso en que los productos son iguales
        assertFalse(sol.intercambiar_productos(0, 0, 0, 0));

        // Nada deberia cambiar
        assertEquals(1, sol.getDistribucion()[0][0]);
        assertEquals(2, sol.getDistribucion()[0][1]);
        assertEquals(3, sol.getDistribucion()[0][2]);
        assertEquals(4, sol.getDistribucion()[1][0]);
        assertEquals(5, sol.getDistribucion()[1][1]);
        assertEquals(6, sol.getDistribucion()[1][2]);
        assertEquals(7, sol.getDistribucion()[2][0]);
        assertEquals(8, sol.getDistribucion()[2][1]);
        assertEquals(9, sol.getDistribucion()[2][2]);

        // Casos simples
        assertTrue(sol.intercambiar_productos(0, 0, 0, 1));
        assertEquals(2, sol.getDistribucion()[0][0]);
        assertEquals(1, sol.getDistribucion()[0][1]);
        assertTrue(sol.intercambiar_productos(0, 0, 0, 1));
        assertEquals(1, sol.getDistribucion()[0][0]);
        assertEquals(2, sol.getDistribucion()[0][1]);
        assertTrue(sol.intercambiar_productos(0, 0, 2, 2));
        assertEquals(9, sol.getDistribucion()[0][0]);
        assertEquals(1, sol.getDistribucion()[2][2]);
    }

    @Test
    public void testEquals() throws CloneNotSupportedException {
        Solucion aux = sol.clone();
        assertEquals(sol, aux);
        aux.setDistribucion(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 8}});
        assertNotEquals(sol, aux);

        aux = sol.clone();
        assertEquals(sol, aux);
        aux.setCalidad(10);
        assertNotEquals(sol, aux);
        aux.setCalidad(sol.getCalidad());
        aux.setNumPasos(1);
        assertNotEquals(sol, aux);
    }
}
