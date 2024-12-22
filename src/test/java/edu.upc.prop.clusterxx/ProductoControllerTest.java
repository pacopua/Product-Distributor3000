package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.DomainProductoController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoControllerTest {
    DomainProductoController productoController = DomainProductoController.getInstance();
    GestorPesistencia gestorPesistencia = GestorPesistencia.getInstance();

    @Before
    public void setUp() {
        productoController.anyadirProducto("Test #1", 9.99);
        productoController.anyadirProducto("Test #2", 19.99);
        productoController.anyadirProducto("Test #3", 29.99);
        productoController.anyadirProducto("Test #4", 0.99);
        productoController.setSinergias(0, 1, 2);
        productoController.setSinergias(0, 2, 3);
        productoController.setSinergias(0, 3, 4);
        productoController.setSinergias(1, 2, 6);
        productoController.setSinergias(1, 3, 8);
        productoController.setSinergias(2, 3, 12);
    }

    @After
    public void tearDown() {
        productoController.eliminarProductoPorId(0);
        productoController.eliminarProductoPorId(0);
        productoController.eliminarProductoPorId(0);
        productoController.eliminarProductoPorId(0);
    }

    @Test
    public void testEditarSinergias() {
        assertEquals(2, productoController.getSinergias(0, 1), 0);
        assertEquals(3, productoController.getSinergias(0, 2), 0);
        assertEquals(4, productoController.getSinergias(0, 3), 0);
        assertEquals(6, productoController.getSinergias(1, 2), 0);
        assertEquals(8, productoController.getSinergias(1, 3), 0);
        assertEquals(12, productoController.getSinergias(2, 3), 0);
        productoController.setSinergias(0, 1, 5);
        productoController.setSinergias(0, 2, 7);
        productoController.setSinergias(0, 3, 9);
        productoController.setSinergias(1, 2, 11);
        productoController.setSinergias(1, 3, 13);
        productoController.setSinergias(2, 3, 15);
        assertEquals(5, productoController.getSinergias(0, 1), 0);
        assertEquals(7, productoController.getSinergias(0, 2), 0);
        assertEquals(9, productoController.getSinergias(0, 3), 0);
        assertEquals(11, productoController.getSinergias(1, 2), 0);
        assertEquals(13, productoController.getSinergias(1, 3), 0);
        assertEquals(15, productoController.getSinergias(2, 3), 0);
    }

    @Test
    public void testCambiarNombre() {
        assertEquals("Test #1", productoController.getNombreProductoPorId(0));
        assertEquals("Test #2", productoController.getNombreProductoPorId(1));
        assertEquals("Test #3", productoController.getNombreProductoPorId(2));
        assertEquals("Test #4", productoController.getNombreProductoPorId(3));
        productoController.cambiarNombreProducto(0, "Test #5");
        productoController.cambiarNombreProducto(1, "Test #6");
        productoController.cambiarNombreProducto(2, "Test #7");
        productoController.cambiarNombreProducto(3, "Test #8");
        assertEquals("Test #5", productoController.getNombreProductoPorId(0));
        assertEquals("Test #6", productoController.getNombreProductoPorId(1));
        assertEquals("Test #7", productoController.getNombreProductoPorId(2));
        assertEquals("Test #8", productoController.getNombreProductoPorId(3));
        productoController.cambiarNombreProducto(0, "Test #1");
        productoController.cambiarNombreProducto(1, "Test #1");
        assertEquals("Test #1", productoController.getNombreProductoPorId(0));
        assertNotEquals("Test #1", productoController.getNombreProductoPorId(1));
    }

    @Test
    public void testEliminarProducto() {
        productoController.eliminarProductoPorId(-1);
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        productoController.eliminarProductoPorId(5);
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        productoController.eliminarProductoPorId(3);
        assertEquals(3, gestorPesistencia.getListaProductos().getCantidadProductos());
        productoController.eliminarProductoPorId(2);
        assertEquals(2, gestorPesistencia.getListaProductos().getCantidadProductos());
        productoController.eliminarProductoPorId(1);
        assertEquals(1, gestorPesistencia.getListaProductos().getCantidadProductos());
        productoController.eliminarProductoPorId(0);
        assertEquals(0, gestorPesistencia.getListaProductos().getCantidadProductos());
    }

    @Test
    public void testCambiarPrecio() {
        assertEquals(9.99, productoController.getPrecioProductoPorId(0), 0);
        assertEquals(19.99, productoController.getPrecioProductoPorId(1), 0);
        assertEquals(29.99, productoController.getPrecioProductoPorId(2), 0);
        assertEquals(0.99, productoController.getPrecioProductoPorId(3), 0);
        productoController.setPrecioProductoPorId(0, 5.99);
        productoController.setPrecioProductoPorId(1, 15.99);
        productoController.setPrecioProductoPorId(2, 25.99);
        productoController.setPrecioProductoPorId(3, 35.99);
        assertEquals(5.99, productoController.getPrecioProductoPorId(0), 0);
        assertEquals(15.99, productoController.getPrecioProductoPorId(1), 0);
        assertEquals(25.99, productoController.getPrecioProductoPorId(2), 0);
        assertEquals(35.99, productoController.getPrecioProductoPorId(3), 0);
        productoController.setPrecioProductoPorId(-1, 5.99);
        productoController.setPrecioProductoPorId(5, 4.99);
        productoController.setPrecioProductoPorId(0, -4.99);
        productoController.setPrecioProductoPorId(1, 4.99);
        assertEquals(5.99, productoController.getPrecioProductoPorId(0), 0);
        assertEquals(4.99, productoController.getPrecioProductoPorId(1), 0);
        assertEquals(25.99, productoController.getPrecioProductoPorId(2), 0);
        assertEquals(35.99, productoController.getPrecioProductoPorId(3), 0);
    }

    @Test
    public void testAnyadirProducto() {
        assertTrue(productoController.anyadirProducto("Test #3", 29.99));
        assertFalse(productoController.anyadirProducto("Test #5", 29.99));
        assertTrue(productoController.anyadirProducto("Test #5", 29.99));
        assertEquals(5, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertEquals(29.99, productoController.getPrecioProductoPorId(4), 0);
        assertEquals("Test #5", productoController.getNombreProductoPorId(4));
        productoController.anyadirProducto("Test #6", -0.99);
        assertEquals(5, gestorPesistencia.getListaProductos().getCantidadProductos());
    }

    @Test
    public void testConsistencia() {
        tearDown();
        setUp();
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertEquals(4, gestorPesistencia.getMatrizAdyacencia().getMatriz().length);
        assertEquals(4, gestorPesistencia.getMatrizAdyacencia().getMatriz()[0].length);
        assertEquals(4, gestorPesistencia.getMatrizAdyacencia().getMatriz()[1].length);
        assertEquals(4, gestorPesistencia.getMatrizAdyacencia().getMatriz()[2].length);
        assertEquals(4, gestorPesistencia.getMatrizAdyacencia().getMatriz()[3].length);
        assertEquals(2, gestorPesistencia.getMatrizAdyacencia().getMatriz()[0][1], 0);
        assertEquals(2, gestorPesistencia.getMatrizAdyacencia().getMatriz()[1][0], 0);
        assertEquals(3, gestorPesistencia.getMatrizAdyacencia().getMatriz()[2][0], 0);
        assertEquals(4, gestorPesistencia.getMatrizAdyacencia().getMatriz()[3][0], 0);
        assertEquals(6, gestorPesistencia.getMatrizAdyacencia().getMatriz()[2][1], 0);
        assertEquals(8, gestorPesistencia.getMatrizAdyacencia().getMatriz()[3][1], 0);
        assertEquals(12, gestorPesistencia.getMatrizAdyacencia().getMatriz()[3][2], 0);
    }
}

