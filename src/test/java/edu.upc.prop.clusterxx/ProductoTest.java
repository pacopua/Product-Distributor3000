//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test para Lista Productos (un test por metodo)
 */
public class ProductoTest {
    private Producto producto;
    private static final String TEST_NOMBRE = "Test Product";
    private static final double TEST_PRECIO = 9.99;

    @Before
    public void setUp() {
        producto = new Producto(TEST_NOMBRE, TEST_PRECIO);
    }

    @Test
    public void testConstructor() {
        assertNotNull(producto);
        assertEquals(TEST_NOMBRE, producto.getNombre());
        assertEquals(TEST_PRECIO, producto.getPrecio(), 0.001);
    }

    @Test
    public void testSetNombre() {
        String newNombre = "Updated Product";
        producto.setNombre(newNombre);
        assertEquals(newNombre, producto.getNombre());
    }

    @Test
    public void testSetPrecio() {
        double newPrecio = 19.99;
        producto.setPrecio(newPrecio);
        assertEquals(newPrecio, producto.getPrecio(), 0.001);
    }

    @Test
    public void testToString() {
        String expectedString = producto.toString();
        assertTrue(expectedString.contains(TEST_NOMBRE));
        assertTrue(expectedString.contains(String.valueOf(TEST_PRECIO)));
    }
}