//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;


import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Test para Lista Productos (un test por metodo)
 */
public class ListaProductosTest {
    private ListaProductos listaProductos;
    private static final String TEST_JSON_FILE = "test_products.json";
    private static final String TEST_CSV_FILE = "test_products.csv";
    private static final String TEST_TXT_FILE = "test_products.txt";

    @Before
    public void setUp() {
        listaProductos = new ListaProductos();
        listaProductos.addProducto(new Producto( "Test Product", 9.99));
    }

    @After
    public void tearDown() {
        new File(TEST_JSON_FILE).delete();
        new File(TEST_CSV_FILE).delete();
        new File(TEST_TXT_FILE).delete();
    }

    @Test
    public void testAddProducto() {
        Producto producto = new Producto("New Product", 19.99);
        listaProductos.addProducto(producto);
        assertEquals(2, listaProductos.getCantidadProductos());
    }

    @Test
    public void testGetProducto() {
        Optional<Producto> producto = listaProductos.getProducto(0);
        assertTrue(producto.isPresent());
        assertEquals("Test Product", producto.get().getNombre());
        assertEquals(9.99, producto.get().getPrecio(), 0.001);
    }

    @Test
    public void testEliminarProducto() {
        assertTrue(listaProductos.eliminarProducto(0));
        assertEquals(0, listaProductos.getCantidadProductos());
    }

    @Test
    public void testExportarEImportarJSON() throws IOException {
        listaProductos.exportarAJSON(TEST_JSON_FILE);

        ListaProductos nuevaLista = new ListaProductos();
        nuevaLista.ImportarLista(TEST_JSON_FILE);

        assertEquals(listaProductos.getCantidadProductos(), nuevaLista.getCantidadProductos());
        List<Producto> productos = nuevaLista.getListaProductos();
        assertEquals(1, productos.size());
        assertEquals("Test Product", productos.getFirst().getNombre());
    }

    @Test
    public void testImportarCSV() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_FILE)) {
            writer.write("1,Test CSV Product,29.99\n");
        }

        ListaProductos nuevaLista = new ListaProductos();
        nuevaLista.ImportarLista(TEST_CSV_FILE);

        List<Producto> productos = nuevaLista.getListaProductos();
        assertEquals(1, productos.size());
        assertEquals("Test CSV Product", productos.getFirst().getNombre());
    }

    @Test
    public void testImportarTXT() throws IOException {
        // delimitador |
        try (FileWriter writer = new FileWriter(TEST_TXT_FILE)) {
            writer.write("1|Test TXT Product|39.99\n");
        }

        ListaProductos nuevaLista = new ListaProductos();
        nuevaLista.ImportarLista(TEST_TXT_FILE);

        List<Producto> productos = nuevaLista.getListaProductos();
        assertEquals(1, productos.size());
        assertEquals("Test TXT Product", productos.getFirst().getNombre());
    }

    @Test
    public void testGetListaProductos() {
        List<Producto> productos = listaProductos.getListaProductos();
        assertEquals(1, productos.size());

        // comprobar que hay compia (encapsulacion o algo así)
        productos.clear();
        assertEquals(1, listaProductos.getCantidadProductos());
    }

    @Test
    public void testToJSON() {
        String json = listaProductos.toJSON();
        assertNotNull(json);
        assertTrue(json.contains("Test Product"));
        assertTrue(json.contains("9.99"));
    }

    @Test
    public void testExportarAJSONConManejodeErrores() {
        assertTrue(listaProductos.exportarAJSONConManejodeErrores(TEST_JSON_FILE));
        // probar path inválido (ns que más errores podrían haber)
        assertFalse(listaProductos.exportarAJSONConManejodeErrores("/invalid/path/test.json"));
    }
}