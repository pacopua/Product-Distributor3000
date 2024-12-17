package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.Sistema;

import java.util.List;

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

    public List<String> getListaNombresProductos() {
        return Sistema.getSolucion().getListaProductos().getListaProductos().stream()
                .map(Producto::getNombre)
                .toList();
    }

    public void intercambiarProductos(String nombreP1, String nombreP2) {
        Solucion solucion = Sistema.getSolucion();
        Producto p1 = null;
        for (Producto p : Sistema.getSolucion().getListaProductos().getListaProductos()) {
            if (nombreP1.equals(p.getNombre())) p1 = p;
        }

        Producto p2 = null;
        for (Producto p : Sistema.getSolucion().getListaProductos().getListaProductos()) {
            if (nombreP2.equals(p.getNombre())) p2 = p;
        }

        Producto[][] distribucionProductos = solucion.getDistribucionProductos();
        int[][] distribucion = solucion.getDistribucion();
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        for (int i = 0; i < distribucionProductos.length; i++) {
            for (int j = 0; j < distribucionProductos[0].length; j++) {
                if (p1 == distribucionProductos[i][j]) {
                    x1 = i;
                    y1 = j;
                }
                if (p2 == distribucionProductos[i][j]) {
                    x2 = i;
                    y2 = j;
                }
            }
        }

        distribucionProductos[x1][y1] = p2;
        distribucionProductos[x2][y2] = p1;
        distribucion[x1][y1] = solucion.getListaProductos().getListaProductos().indexOf(p2);
        distribucion[x2][y2] = solucion.getListaProductos().getListaProductos().indexOf(p1);
    }
}
