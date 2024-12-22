package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

import java.util.List;

public class DomainSolucionController {
    private static AlgoritmoRapido algoRapido;
    private static AlgoritmoOptimo algoOptimo;
    private static AlgoritmoSA algoSA;
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

    public void parar_algoritmo() {
        if (algoRapido != null) algoRapido.stopExecution();
        if (algoOptimo != null) algoOptimo.stopExecution();
        //no lo hago para el SA pq al tener un step predeterminado no tiene sentido
        //tardara muy poquito en acabar
    }

    public void calcularDistribucionRapida(int filas, int columnas) {
        Solucion s = GestorPesistencia.nuevaSolucion(filas, columnas);
        algoRapido = new AlgoritmoRapido(GestorPesistencia.getMatrizAdyacencia());
        //lanzamos excepcion si no se puede hacer la distribucion
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        GestorPesistencia.setSolucion(algoRapido.ejecutar(s, 1));
    }

    public void calcularDistribucionOptima(int filas, int columnas) {
        Solucion s = GestorPesistencia.nuevaSolucion(filas, columnas);
        algoOptimo = new AlgoritmoOptimo(GestorPesistencia.getMatrizAdyacencia());
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            //System.out.println("AAAAAAAAA");
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        GestorPesistencia.setSolucion(algoOptimo.ejecutar(s));
    }

    public void calcularDistribucionUltraRapida(int filas, int columnas) {
        Solucion s = GestorPesistencia.nuevaSolucion(filas, columnas);
        algoSA = new AlgoritmoSA(GestorPesistencia.getMatrizAdyacencia(), 10000,1, 25, 0.001 );
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        GestorPesistencia.setSolucion(algoSA.ejecutar(s, 1));
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
