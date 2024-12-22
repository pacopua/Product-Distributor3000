package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HistorialTest {
    DomainEstadoController estadoController = DomainEstadoController.getInstance();
    DomainSolucionController solucionController = DomainSolucionController.getInstance();
    DomainProductoController productoController = DomainProductoController.getInstance();
    GestorPesistencia gestorPesistencia = GestorPesistencia.getInstance();


    @Before
    public void setUp() {
        productoController.anyadirProducto("Test#1", 1);
        productoController.anyadirProducto("Test#2", 2);
        productoController.anyadirProducto("Test#3", 3);
        productoController.anyadirProducto("Test#4", 4);
        productoController.setSinergias(0, 1, 1);
        productoController.setSinergias(0, 2, 1.5);
        productoController.setSinergias(0, 3, 2);
        productoController.setSinergias(1, 2, 3);
        productoController.setSinergias(1, 3, 4);
        productoController.setSinergias(2, 3, 6);
    }

    @Test
    public void testAnadirProducto() {
        Estado estadoMas1, estadoMas2, estadoBase = getEstado();
        productoController.anyadirProducto("Test#5", 5);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        estadoMas1 = getEstado();
        productoController.anyadirProducto("Test#6", 6);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas1, getEstado()));
        estadoMas2 = getEstado();
        assertEquals(6, gestorPesistencia.getListaProductos().getCantidadProductos());

        estadoController.deshacer();
        assertEquals(5, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertTrue(gestorPesistencia.compararEstados(estadoMas1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas2, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));

        estadoController.deshacer();
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas2, getEstado()));
    }

    @Test
    public void testElminiarProducto() {
        Estado estadoMas1, estadoMas2, estadoBase = getEstado();
        productoController.eliminarProductoPorId(3);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(3, gestorPesistencia.getListaProductos().getCantidadProductos());
        estadoMas1 = getEstado();
        productoController.eliminarProductoPorId(2);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas1, getEstado()));
        assertEquals(2, gestorPesistencia.getListaProductos().getCantidadProductos());
        estadoMas2 = getEstado();

        estadoController.deshacer();
        assertEquals(3, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertTrue(gestorPesistencia.compararEstados(estadoMas1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas2, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));

        estadoController.deshacer();
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoMas2, getEstado()));
    }

    @Test
    public void testModificarPrecioProducto() {
        Estado estadoModPrecio, estadoBase = getEstado();
        productoController.setPrecioProductoPorId(2, 10);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(10, gestorPesistencia.getListaProductos().getListaProductos().get(2).getPrecio(), 0);
        estadoModPrecio = getEstado();
        productoController.setPrecioProductoPorId(2, 2);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModPrecio, getEstado()));
        assertEquals(2, gestorPesistencia.getListaProductos().getListaProductos().get(2).getPrecio(), 0);
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoModPrecio, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(10, gestorPesistencia.getListaProductos().getListaProductos().get(2).getPrecio(), 0);
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModPrecio, getEstado()));
        assertEquals(3, gestorPesistencia.getListaProductos().getListaProductos().get(2).getPrecio(), 0);

        productoController.setPrecioProductoPorId(2, -2);
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(3, gestorPesistencia.getListaProductos().getListaProductos().get(2).getPrecio(), 0);
        assertNotEquals(-2, gestorPesistencia.getListaProductos().getListaProductos().get(2).getPrecio(), 0);
    }

    @Test
    public void testModificarNombreProducto() {
        Estado estadoModNombre, estadoBase = getEstado();
        productoController.cambiarNombreProducto(2, "Test#10");
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals("Test#10", gestorPesistencia.getListaProductos().getListaProductos().get(2).getNombre());
        estadoModNombre = getEstado();
        productoController.cambiarNombreProducto(2, "Test#3");
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModNombre, getEstado()));
        assertEquals("Test#3", gestorPesistencia.getListaProductos().getListaProductos().get(2).getNombre());
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoModNombre, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals("Test#10", gestorPesistencia.getListaProductos().getListaProductos().get(2).getNombre());
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModNombre, getEstado()));
        assertEquals("Test#3", gestorPesistencia.getListaProductos().getListaProductos().get(2).getNombre());
    }

    @Test
    public void testModificarSinergia() {
        Estado estadoBase = getEstado(), estadoModSingergia1, estadoModSingergia2;
        productoController.setSinergias(0, 3, 10);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(10, productoController.getSinergias(0, 3), 0);
        estadoModSingergia1 = getEstado();
        productoController.setSinergias(1, 2, 100);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia1, getEstado()));
        assertEquals(100, productoController.getSinergias(1, 2), 0);
        estadoModSingergia2 = getEstado();
        productoController.setSinergias(0, 3, -5);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia2, getEstado()));
        assertEquals(-5, productoController.getSinergias(0, 3), 0);
        estadoController.deshacer();
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia1, getEstado()));
        assertTrue(gestorPesistencia.compararEstados(estadoModSingergia2, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(100, productoController.getSinergias(1, 2), 0);
        estadoController.deshacer();
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertTrue(gestorPesistencia.compararEstados(estadoModSingergia1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia2, getEstado()));
        assertEquals(10, productoController.getSinergias(0, 3), 0);
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoModSingergia2, getEstado()));
        assertEquals(2, productoController.getSinergias(0, 3), 0);
    }

    @Test
    public void testCalcularSolucion() {
        Estado calc1, estadoBase = getEstado();
        solucionController.calcularDistribucionUltraRapida(2, 2);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        calc1 = getEstado();
        Solucion s1 = gestorPesistencia.getSolucion().clone();
        solucionController.calcularDistribucionUltraRapida(3, 3);
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(calc1, getEstado()));
        assertNotEquals(s1, gestorPesistencia.getSolucion());
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(calc1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertEquals(s1, gestorPesistencia.getSolucion());
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(calc1, getEstado()));
        assertNotEquals(s1, gestorPesistencia.getSolucion());
    }

    @Test
    public void testIntercambiarProductos() {
        Estado estadoBase = getEstado(), estadoIntercambio1, estadoIntercambio2;
        solucionController.calcularDistribucionUltraRapida(2, 2);
        estadoBase = getEstado();
        solucionController.intercambiarProductos("Test#1", "Test#2");
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        estadoIntercambio1 = getEstado();
        solucionController.intercambiarProductos("Test#1", "Test#3");
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio1, getEstado()));
        estadoIntercambio2 = getEstado();
        solucionController.intercambiarProductos("Test#2", "Test#4");
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio2, getEstado()));
        estadoController.deshacer();
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio1, getEstado()));
        assertTrue(gestorPesistencia.compararEstados(estadoIntercambio2, getEstado()));
        estadoController.deshacer();
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertTrue(gestorPesistencia.compararEstados(estadoIntercambio1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio2, getEstado()));
        estadoController.deshacer();
        assertTrue(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio2, getEstado()));

        // No se ha calculado la solucion
        estadoController.deshacer();
        assertFalse(gestorPesistencia.compararEstados(estadoBase, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio1, getEstado()));
        assertFalse(gestorPesistencia.compararEstados(estadoIntercambio2, getEstado()));

    }

    private Estado getEstado() {
        return new Estado(gestorPesistencia.getSolucion().clone(), gestorPesistencia.getListaProductos().clone(), gestorPesistencia.getMatrizAdyacencia().clone());
    }
}
