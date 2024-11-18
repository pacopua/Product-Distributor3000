//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AlgoritmoOptimoTest {
    private ListaProductos l;
    private Solucion s;
    private MatrizAdyacencia ma4x4;
    private MatrizAdyacencia ma9x9;
    private AlgoritmoOptimo av;

    // TODO: faltan productos de prueba para la solución
    @Before
    public void initSolucion() {

    }

    @Test
    public void testAlgoritmoVoraz4x4() {
        ma4x4 = new MatrizAdyacencia(
                new double[][] {
                        { 0, 10,  15,  20 },
                        { 10, 0,  35,  25 },
                        { 15, 35,  0,  30 },
                        { 20, 25, 30,   0 }
                }
        );
        l = new ListaProductos();
        for (int i = 0; i < 4; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        s = new Solucion(l, 2, 2);
        av = new AlgoritmoOptimo(ma4x4);
        s = av.ejecutar(s);
        assertTrue(s.getCalidad() >= 80);
    }

    @Test
    public void testAlgoritmoVoraz9x9() {
        ma9x9 = new MatrizAdyacencia(
                new double[][] {
                        {  0,29,82,46,68,52,72,42,51},
                        { 29, 0,55,46,42,43,43,23,23},
                        { 82,55, 0,68,46,55,23,43,41},
                        { 46,46,68, 0,82,15,72,31,62},
                        { 68,42,46,82, 0,74,23,52,21},
                        { 52,43,55,15,74, 0,61,23,55},
                        { 72,43,23,72,23,61, 0,42,23},
                        { 42,23,43,31,52,23,42, 0,33},
                        { 51,23,41,62,21,55,23,33, 0}
                }
        );
        l = new ListaProductos();
        for (int i = 0; i < 9; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        av = new AlgoritmoOptimo(ma9x9);
        s = new Solucion(l, 3, 3);

        s = av.ejecutar(s);

        assertTrue(s.getCalidad() >= 246);
    }
}
