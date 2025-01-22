package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.domain.ListaProductos;
import edu.upc.prop.clusterxx.domain.Producto;
import edu.upc.prop.clusterxx.domain.Solucion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SolucionTest {
    private Solucion sol;
    private ListaProductos productos ;

    @Before
    public void setUp() {
        productos = new ListaProductos();
        productos.addProducto(new Producto("Producto 1", 1));
        productos.addProducto(new Producto("Producto 2", 2));
        productos.addProducto(new Producto("Producto 3", 3));
        productos.addProducto(new Producto("Producto 4", 4));
        productos.addProducto(new Producto("Producto 5", 5));
        productos.addProducto(new Producto("Producto 6", 6));
        productos.addProducto(new Producto("Producto 7", 7));
        productos.addProducto(new Producto("Producto 8", 8));
        productos.addProducto(new Producto("Producto 9", 9));
        productos.addProducto(new Producto("Producto 10", 10));
        sol = new Solucion(productos, new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
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

        int[][] distribucion = sol.getDistribucion();
        // Nada deberia cambiar
        assertEquals(1, distribucion[0][0]);
        assertEquals(2, distribucion[0][1]);
        assertEquals(3, distribucion[0][2]);
        assertEquals(4, distribucion[1][0]);
        assertEquals(5, distribucion[1][1]);
        assertEquals(6, distribucion[1][2]);
        assertEquals(7, distribucion[2][0]);
        assertEquals(8, distribucion[2][1]);
        assertEquals(9, distribucion[2][2]);

        // Casos simples
        assertTrue(sol.intercambiar_productos(0, 0, 0, 1));
        distribucion = sol.getDistribucion();
        assertEquals(2, distribucion[0][0]);
        assertEquals(1, distribucion[0][1]);
        assertTrue(sol.intercambiar_productos(0, 0, 0, 1));
        distribucion = sol.getDistribucion();
        assertEquals(1, distribucion[0][0]);
        assertEquals(2, distribucion[0][1]);
        assertTrue(sol.intercambiar_productos(0, 0, 2, 2));
        distribucion = sol.getDistribucion();
        assertEquals(9, distribucion[0][0]);
        assertEquals(1, distribucion[2][2]);
    }

    /**
     * Test de la funcion equals
     */
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

    /**
     * Test de la funcion clone
     */
    @Test
    public void testClone() throws CloneNotSupportedException {
        Solucion aux = sol.clone();
        assertEquals(sol, aux);
        assertNotSame(sol, aux);
        assertNotSame(sol.getDistribucionProductos(), aux.getDistribucionProductos());
    }
}
