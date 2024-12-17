package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.Sistema;

public class DomainSolucionController {
    public void calcularDistribucionRapida(int filas, int columnas) {
        Solucion s = Sistema.nuevaSolucion(filas, columnas);
        AlgoritmoRapido algo = new AlgoritmoRapido(Sistema.getMatrizAdyacencia());

        Sistema.setSolucion(algo.ejecutar(s, 10));
    }

    public void calcularDistribucionOptima(int filas, int columnas) {
        Solucion s = Sistema.nuevaSolucion(filas, columnas);
        AlgoritmoOptimo algo = new AlgoritmoOptimo(Sistema.getMatrizAdyacencia());
        Sistema.setSolucion(algo.ejecutar(s));
    }
}
