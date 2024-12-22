package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import javafx.beans.property.DoubleProperty;

import java.util.List;

public class DomainSolucionController {

    public double getCalidadSolucion() {
        return GestorPesistencia.getSolucion().getCalidad();
    }

    public int getPasosSolucion() {
        return GestorPesistencia.getSolucion().getNumPasos();
    }

    public int getDistLenght() {
        return GestorPesistencia.getSolucion().getDistribucion()[0].length;
    }

    public int getDistValue(int i, int j) {
        return GestorPesistencia.getSolucion().getDistribucion()[i][j];
    }

    public int getDistHeight() {
        return GestorPesistencia.getSolucion().getDistribucion().length;
    }

    public boolean is_complete() {
        return GestorPesistencia.getSolucion().estaCompletado();
    }

    public void calcularDistribucionRapida(int filas, int columnas, DoubleProperty progreso) {
        Solucion s = GestorPesistencia.nuevaSolucion(filas, columnas);
        AlgoritmoRapido algo = new AlgoritmoRapido(GestorPesistencia.getMatrizAdyacencia(), progreso);
        //lanzamos excepcion si no se puede hacer la distribucion
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        GestorPesistencia.setSolucion(algo.ejecutar(s, 1));
    }

    public void calcularDistribucionOptima(int filas, int columnas, DoubleProperty progreso) {
        Solucion s = GestorPesistencia.nuevaSolucion(filas, columnas);
        AlgoritmoOptimo algo = new AlgoritmoOptimo(GestorPesistencia.getMatrizAdyacencia(), progreso);
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            //System.out.println("AAAAAAAAA");
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        GestorPesistencia.setSolucion(algo.ejecutar(s));
    }

    public List<String> getListaNombresProductos() {
        return GestorPesistencia.getSolucion().getListaProductos().getListaProductos().stream()
                .map(Producto::getNombre)
                .toList();
    }

    public void intercambiarProductos(String nombreP1, String nombreP2) {
        Solucion solucion = GestorPesistencia.getSolucion();

        int p1 = -1, p2 = -1;
        List<Producto> lp = solucion.getListaProductos().getListaProductos();
        for (int i = 0; i < lp.size(); ++i) {
            String _nombre = lp.get(i).getNombre();
            if (_nombre.equals(nombreP1)) p1 = i;
            if (_nombre.equals(nombreP2)) p2 = i;
        }

        int[] pos_p1 = solucion.buscar_producto(p1);
        int[] pos_p2 = solucion.buscar_producto(p2);
        solucion.intercambiar_productos(pos_p1[0], pos_p1[1], pos_p2[0], pos_p2[1]);
    }
}
