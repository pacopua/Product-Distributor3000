package edu.upc.prop.clusterxx;

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

        Producto[][] distribucion = sol.getDistribucionProductos();
        // Nada deberia cambiar
        assertEquals(productos.getProducto(0), distribucion[0][0]);
        assertEquals(productos.getProducto(1), distribucion[0][1]);
        assertEquals(productos.getProducto(2), distribucion[0][2]);
        assertEquals(productos.getProducto(3), distribucion[1][0]);
        assertEquals(productos.getProducto(4), distribucion[1][1]);
        assertEquals(productos.getProducto(5), distribucion[1][2]);
        assertEquals(productos.getProducto(6), distribucion[2][0]);
        assertEquals(productos.getProducto(7), distribucion[2][1]);
        assertEquals(productos.getProducto(8), distribucion[2][2]);

        // Casos simples
        assertTrue(sol.intercambiar_productos(0, 0, 0, 1));
        assertEquals(productos.getProducto(1), distribucion[0][0]);
        assertEquals(productos.getProducto(0), distribucion[0][1]);
        assertTrue(sol.intercambiar_productos(0, 0, 0, 1));
        assertEquals(productos.getProducto(0), distribucion[0][0]);
        assertEquals(productos.getProducto(1), distribucion[0][1]);
        assertTrue(sol.intercambiar_productos(0, 0, 2, 2));
        assertEquals(productos.getProducto(8), distribucion[0][0]);
        assertEquals(productos.getProducto(0), distribucion[2][2]);
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
