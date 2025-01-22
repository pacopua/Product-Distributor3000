package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.DomainProductoController;
import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import edu.upc.prop.clusterxx.domain.IOController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolucionControllerTest {
    IOController ioController = IOController.getInstance();
    DomainProductoController productoController = DomainProductoController.getInstance();
    GestorPesistencia gestorPesistencia = GestorPesistencia.getInstance();
    DomainSolucionController solucionController = DomainSolucionController.getInstance();

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
        solucionController.calcularDistribucionUltraRapida(2, 2);
    }

    @Test
    public void testIntercambiarProductos() {
        int[] poseProd1 = gestorPesistencia.getSolucion().buscar_producto(0);
        int[] poseProd2 = gestorPesistencia.getSolucion().buscar_producto(1);
        int[] poseProd3 = gestorPesistencia.getSolucion().buscar_producto(2);
        int[] poseProd4 = gestorPesistencia.getSolucion().buscar_producto(3);
        solucionController.intercambiarProductos("Test #1", "Test #2");
        assertArrayEquals(poseProd1, gestorPesistencia.getSolucion().buscar_producto(1));
        assertArrayEquals(poseProd2, gestorPesistencia.getSolucion().buscar_producto(0));
        assertArrayEquals(poseProd3, gestorPesistencia.getSolucion().buscar_producto(2));
        assertArrayEquals(poseProd4, gestorPesistencia.getSolucion().buscar_producto(3));
        solucionController.intercambiarProductos("Test #1", "Test #2");
        solucionController.intercambiarProductos("Test #2", "Test #3");
        assertArrayEquals(poseProd1, gestorPesistencia.getSolucion().buscar_producto(0));
        assertArrayEquals(poseProd2, gestorPesistencia.getSolucion().buscar_producto(2));
        assertArrayEquals(poseProd3, gestorPesistencia.getSolucion().buscar_producto(1));
        assertArrayEquals(poseProd4, gestorPesistencia.getSolucion().buscar_producto(3));
        solucionController.intercambiarProductos("Test #0", "");
        assertArrayEquals(poseProd1, gestorPesistencia.getSolucion().buscar_producto(0));
        assertArrayEquals(poseProd2, gestorPesistencia.getSolucion().buscar_producto(2));
        assertArrayEquals(poseProd3, gestorPesistencia.getSolucion().buscar_producto(1));
        assertArrayEquals(poseProd4, gestorPesistencia.getSolucion().buscar_producto(3));
    }
}
