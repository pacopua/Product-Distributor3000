//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlgoritmoOptimoTest {
    private ListaProductos l;
    private Solucion s;
    private MatrizAdyacencia ma4x4;
    private MatrizAdyacencia ma9x9;
    private MatrizAdyacencia ma16x16;
    private AlgoritmoOptimo av;


    @Test
    public void testAlgoritmoOptimoDist2x2() {
        ma4x4 = new MatrizAdyacencia(
                new double[][]{
                        {0, 10, 15, 20},
                        {10, 0, 35, 25},
                        {15, 35, 0, 30},
                        {20, 25, 30, 0}
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
    public void testAlgoritmoOptimoDist3x3() {
        ma9x9 = new MatrizAdyacencia(
                new double[][]{
                        {0, 29, 82, 46, 68, 52, 72, 42, 51},
                        {29, 0, 55, 46, 42, 43, 43, 23, 23},
                        {82, 55, 0, 68, 46, 55, 23, 43, 41},
                        {46, 46, 68, 0, 82, 15, 72, 31, 62},
                        {68, 42, 46, 82, 0, 74, 23, 52, 21},
                        {52, 43, 55, 15, 74, 0, 61, 23, 55},
                        {72, 43, 23, 72, 23, 61, 0, 42, 23},
                        {42, 23, 43, 31, 52, 23, 42, 0, 33},
                        {51, 23, 41, 62, 21, 55, 23, 33, 0}
                }
        );
        l = new ListaProductos();
        for (int i = 0; i < 9; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        av = new AlgoritmoOptimo(ma9x9);
        s = new Solucion(l, 3, 3);

        s = av.ejecutar(s);

        assertTrue(s.getCalidad() >= 246);
    }

    @Test
    public void testStopExecution() {
        ma9x9 = new MatrizAdyacencia(
                new double[][]{
                        {0, 29, 82, 46, 68, 52, 72, 42, 51},
                        {29, 0, 55, 46, 42, 43, 43, 23, 23},
                        {82, 55, 0, 68, 46, 55, 23, 43, 41},
                        {46, 46, 68, 0, 82, 15, 72, 31, 62},
                        {68, 42, 46, 82, 0, 74, 23, 52, 21},
                        {52, 43, 55, 15, 74, 0, 61, 23, 55},
                        {72, 43, 23, 72, 23, 61, 0, 42, 23},
                        {42, 23, 43, 31, 52, 23, 42, 0, 33},
                        {51, 23, 41, 62, 21, 55, 23, 33, 0}
                }
        );
        av = new AlgoritmoOptimo(ma9x9);
        l = new ListaProductos();
        for (int i = 0; i < 9; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        s = new Solucion(l, 3, 3);
        long current = System.nanoTime();
        s = av.ejecutar(s);
        av.stopExecution();
        long time = System.nanoTime() - current;
        long current2 = System.nanoTime();
        s = av.ejecutar(s);
        long time2 = System.nanoTime() - current2;
        assertTrue(time2 < time);
    }

    @Test
    public void testStopExecutionParallel() {
        ma9x9 = new MatrizAdyacencia(
                new double[][]{
                        {0, 29, 82, 46, 68, 52, 72, 42, 51},
                        {29, 0, 55, 46, 42, 43, 43, 23, 23},
                        {82, 55, 0, 68, 46, 55, 23, 43, 41},
                        {46, 46, 68, 0, 82, 15, 72, 31, 62},
                        {68, 42, 46, 82, 0, 74, 23, 52, 21},
                        {52, 43, 55, 15, 74, 0, 61, 23, 55},
                        {72, 43, 23, 72, 23, 61, 0, 42, 23},
                        {42, 23, 43, 31, 52, 23, 42, 0, 33},
                        {51, 23, 41, 62, 21, 55, 23, 33, 0}
                }
        );
        av = new AlgoritmoOptimo(ma9x9);
        AlgoritmoOptimo avNoStop = new AlgoritmoOptimo(ma9x9);
        l = new ListaProductos();
        for (int i = 0; i < 9; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        s = new Solucion(l, 3, 3);
        long current = System.nanoTime();
        new Thread(() -> {
            s = av.ejecutar(s);
        }).start();
        av.stopExecution();
        long time = System.nanoTime() - current;
        long current2 = System.nanoTime();
        s = avNoStop.ejecutar(s);
        long time2 = System.nanoTime() - current2;
        assertTrue(time < time2);
    }

    @Test
    public void testGetNumIters() {
        ma4x4 = new MatrizAdyacencia(
                new double[][]{
                        {0, 10, 15, 20},
                        {10, 0, 35, 25},
                        {15, 35, 0, 30},
                        {20, 25, 30, 0}
                }
        );
        av = new AlgoritmoOptimo(ma4x4);
        l = new ListaProductos();
        for (int i = 0; i < 4; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        s = new Solucion(l, 2, 2);
        av.ejecutar(s);
        System.out.println("getNumIters: " + av.getNumIters() + " solution steps: " + s.getNumPasos());
        assertTrue(av.getNumIters() > s.getNumPasos());
    }

    @Test
    public void testCopiarSolucion() {
        ma4x4 = new MatrizAdyacencia(
                new double[][]{
                        {0, 10, 15, 20},
                        {10, 0, 35, 25},
                        {15, 35, 0, 30},
                        {20, 25, 30, 0}
                }
        );
        av = new AlgoritmoOptimo(ma4x4);
        l = new ListaProductos();
        for (int i = 0; i < 4; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        s = new Solucion(l, 2, 2);
        s = av.ejecutar(s);
        Solucion s2 = av.copiar_solucion(s);
        assertEquals(s.getCalidad(), s2.getCalidad(), 0.001);
        assertEquals(s.getNumPasos(), s2.getNumPasos());
        assertEquals(s.getDistribucion().length, s2.getDistribucion().length);
        assertEquals(s.getDistribucion()[0].length, s2.getDistribucion()[0].length);
        for (int i = 0; i < s.getDistribucion().length; i++) {
            for (int j = 0; j < s.getDistribucion()[0].length; j++) {
                assertEquals(s.getDistribucion()[i][j], s2.getDistribucion()[i][j]);
            }
        }
    }
}
