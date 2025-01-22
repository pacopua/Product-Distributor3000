//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlgoritmoTest {

    private Algoritmo algoritmo;
    private MatrizAdyacencia matrizAdyacencia;

    @Before
    public void setUp() {
        inicializarMatrizDePrueba();
        algoritmo = new Algoritmo(matrizAdyacencia);
    }

    private void inicializarMatrizDePrueba() {
        matrizAdyacencia = new MatrizAdyacencia(4);
        //matriz de sinergias donde cada sinergia = id1 + id2 (menos la diagonal que es 0)
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if (i == j) matrizAdyacencia.modificar_sinergias(i, j, 0);
                else matrizAdyacencia.modificar_sinergias(i, j, i+j);
            }
        }
    }


    @Test
    public void testCalcularSinergias() {
        // solución 2*2
        ListaProductos l = new ListaProductos();
        for (int i = 0; i < 4; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        Solucion solucion = new Solucion(l, 2, 2);
        int[][] distribucion = {
                {0, 1},
                {2, 3}
        };
        solucion.setDistribucion(distribucion);
        double synergy = algoritmo.calcular_sinergias(solucion, 0, 0);
        // Esperado: derecha (1) + abajo (2) + izquierda (3)
        assertEquals(6, synergy, 0.001);
    }

    @Test
    public void testCalcularTodas() {
        ListaProductos l = new ListaProductos();
        for (int i = 0; i < 4; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        Solucion solucion = new Solucion(l, 2, 2);
        int[][] distribucion = {
                {0, 1},
                {2, 3}
        };
        solucion.setDistribucion(distribucion);

        double totalSynergy = algoritmo.calcular_todas(solucion);
        // todas las direcciones para todos los productos
        double expected =  38; // todos están conectados
        assertEquals(expected, totalSynergy, 0.001);
    }

}
