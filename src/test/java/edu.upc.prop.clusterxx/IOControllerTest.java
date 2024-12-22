package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.DomainProductoController;
import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import edu.upc.prop.clusterxx.domain.IOController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IOControllerTest {
    static final String TEST_FILE = "test_file.data";
    IOController ioController = IOController.getInstance();
    DomainProductoController productoController = DomainProductoController.getInstance();
    GestorPesistencia gestorPesistencia = GestorPesistencia.getInstance();
    DomainSolucionController solucionController = DomainSolucionController.getInstance();

    @Before
    public void setUp() {
        limpiarProductos();
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

    @After
    public void limpiarProductos() {
        productoController.eliminarProductoPorId(0);
        productoController.eliminarProductoPorId(0);
        productoController.eliminarProductoPorId(0);
        productoController.eliminarProductoPorId(0);
    }

    @AfterClass
    public static void limpiarArchivos() {
        if (new java.io.File(TEST_FILE).delete() && new java.io.File(TEST_FILE + "2").delete()) {
            System.out.println("Archivos eliminados");
        } else {
            System.err.println("No se pudo eliminar algun archivo");
        }
    }

    @Test
    public void testExportarEstado() {
        try {
            ioController.exportarEstado(TEST_FILE);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion");
        }
        assertTrue(new java.io.File(TEST_FILE).exists());
    }

    @Test
    public void testImportarEstado() {
        testExportarEstado();
        int numPasos = gestorPesistencia.getSolucion().getNumPasos();
        solucionController.calcularDistribucionUltraRapida(3, 3);
        int numPasos2 = gestorPesistencia.getSolucion().getNumPasos();
        assertNotEquals(numPasos2, numPasos);
        limpiarProductos();
        assertEquals(0, gestorPesistencia.getListaProductos().getCantidadProductos());
        try {
            ioController.importarEstado(TEST_FILE);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion");
        }
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertNotEquals(gestorPesistencia.getSolucion().getNumPasos(), numPasos2);
        assertEquals(gestorPesistencia.getSolucion().getNumPasos(), numPasos);
    }

    @Test
    public void testExportarListaProductos() {
        try {
            ioController.exportarListaProductos(TEST_FILE + "2");
        } catch (Exception e) {
            fail("No deberia lanzar excepcion");
        }
        assertTrue(new java.io.File(TEST_FILE + "2").exists());
    }

    @Test
    public void testImportarListaProductos() {
        testExportarListaProductos();
        int numPasos = gestorPesistencia.getSolucion().getNumPasos();
        solucionController.calcularDistribucionUltraRapida(3, 3);
        int numPasos2 = gestorPesistencia.getSolucion().getNumPasos();
        assertNotEquals(numPasos2, numPasos);
        limpiarProductos();
        assertEquals(0, gestorPesistencia.getListaProductos().getCantidadProductos());
        try {
            ioController.importarListaProductos(TEST_FILE + "2");
        } catch (Exception e) {
            fail("No deberia lanzar excepcion");
        }
        assertEquals(4, gestorPesistencia.getListaProductos().getCantidadProductos());
        assertEquals(gestorPesistencia.getSolucion().getNumPasos(), numPasos2);
        assertNotEquals(gestorPesistencia.getSolucion().getNumPasos(), numPasos);
    }
}
