package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class MatrizAdyacenciaTest {
    private MatrizAdyacencia mat;
    // en vdd la matriz es estática ns por qué estamos creando instancias tod0 el rato

    @Before
    public void initMatriz() {
        mat = new MatrizAdyacencia(3);
        mat.modificar_sinergias(0, 1, 1.0);
        mat.modificar_sinergias(0, 2, 2.0);
        mat.modificar_sinergias(1, 2, 3.0);
    }

    @Test
    public void sinergiasCorrectas() {
        assert mat.getSinergia(0, 1) == 1.0;
        assert mat.getSinergia(1, 0) == 1.0;
        assert mat.getSinergia(2, 0) == 2.0;
        assert Arrays.deepEquals(mat.getMatriz(), new double[][]{{0.0, 1.0, 2.0}, {1.0, 0.0, 3.0}, {2.0, 3.0, 0.0}});
    }
}
