package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

public class MatrizAdyacenciaTest {
    private MatrizAdyacencia mat;

    /**
     * Inicializa la matriz de adyacencia
     */
    @Before
    public void initMatriz() {
        mat = new MatrizAdyacencia(3);
        mat.modificar_sinergias(0, 1, 1.0);
        mat.modificar_sinergias(0, 2, 2.0);
        mat.modificar_sinergias(1, 2, 3.0);
    }

    /**
     * Tests de la función de modificar sinergias
     */
    @Test
    public void sinergiasCorrectas() {
        // Casos normales
        assertEquals(1.0, mat.getSinergia(0, 1), 0.0);
        assertEquals(1.0, mat.getSinergia(1, 0), 0.0);
        assertEquals(2.0, mat.getSinergia(0, 2), 0.0);
        assertEquals(2.0, mat.getSinergia(2, 0), 0.0);
        assertEquals(3.0, mat.getSinergia(1, 2), 0.0);
        assertEquals(3.0, mat.getSinergia(2, 1), 0.0);

        // Mismo producto
        assertEquals(-1, mat.getSinergia(0, 0), 0.0);
        assertEquals(-1, mat.getSinergia(1, 1), 0.0);
        assertEquals(-1, mat.getSinergia(2, 2), 0.0);

        // Limites
        assertEquals(-1, mat.getSinergia(-1, 0), 0.0);
        assertEquals(-1, mat.getSinergia(0, -1), 0.0);
        assertEquals(-1, mat.getSinergia(3, 0), 0.0);
        assertEquals(-1, mat.getSinergia(0,3), 0.0);
    }
}
