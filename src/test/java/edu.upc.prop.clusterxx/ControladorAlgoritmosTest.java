//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ControladorAlgoritmosTest {

    private ControladorAlgoritmos algoritmo;
    private MatrizAdyacencia matrizAdyacencia;

    @Before
    public void setUp() {
        algoritmo = new ControladorAlgoritmos();
        inicializarMatrizDePrueba();
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
        // añadir más si hace falta
    }

    // TODO: biel creo que los tests de sinergias deberían ser de matriz de adyacencia 😔

    @Test
    public void testCalcularSinergias() {
        // solución 2*2
        Solucion solucion = new Solucion(2, 2);
        int[][] distribucion = {
                {0, 1},
                {2, 3}
        };
        solucion.setDistribucion(distribucion);
        double synergy = algoritmo.calcular_sinergias(solucion, 0, 0);
        // Esperado: derecha (1) + abajo (2) + izquierda (dando la vuelta pq es una locura esto) (3)
        assertEquals(6, synergy, 0.001);
    }

    @Test
    public void testCalcularTodas() {
        Solucion solucion = new Solucion(2, 2);
        int[][] distribucion = {
                {0, 1},
                {2, 3}
        };
        solucion.setDistribucion(distribucion);

        double totalSynergy = algoritmo.calcular_todas(solucion);
        // todas las direcciones para todos los productos
        double expected =  6 * 4; // todos están conectados
        assertEquals(expected, totalSynergy, 0.001);
    }
}
