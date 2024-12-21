package edu.upc.prop.clusterxx.data;

import edu.upc.prop.clusterxx.domain.*;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestorPesistencia {
    private static ListaProductos listaProductos = new ListaProductos();
    private static MatrizAdyacencia matrizAdyacencia = new MatrizAdyacencia(0);
    private static Solucion solucion = new Solucion(listaProductos);
    private static final ArrayList<Estado> historial = new ArrayList<>();

    public static ListaProductos getListaProductos() {
        return listaProductos;
    }

    public static MatrizAdyacencia getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    public static Solucion getSolucion() {
        return solucion;
    }

    public static void setMatrizAdyacencia(MatrizAdyacencia nuevaMatrizAdyacencia) { // temporal?
        matrizAdyacencia = nuevaMatrizAdyacencia;
    }

    public static void setSolucion(Solucion s) {
        solucion = s;
    }

    public static void importarEstado(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Estado estado = (Estado) objectIn.readObject();
        listaProductos = estado.getListaProductos();
        matrizAdyacencia = estado.getMatrizAdyacencia();
        solucion = estado.getSolucion();
    }

    public static void exportarEstado(File f) throws IOException {
        Estado estado = new Estado(solucion, listaProductos, matrizAdyacencia);
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(estado);
    }

    public static void importarLista(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Estado estado = (Estado) objectIn.readObject();
        listaProductos = estado.getListaProductos();
        matrizAdyacencia = estado.getMatrizAdyacencia();
    }

    public static void exportarLista(File f) throws IOException {
        Estado estado = new Estado(new Solucion(listaProductos, 0, 0), listaProductos, matrizAdyacencia);
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(estado);
    }

    /*
    public static void importarSolucion(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        solucion = (Solucion) objectIn.readObject();
    }
    public static void exportarSolucion(File f) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(solucion);
    }
    */
    public static void salir() {
        Platform.exit();
        System.exit(0);
    }

    public static Solucion nuevaSolucion(int filas, int columnas) throws IllegalArgumentException {
        return new Solucion(listaProductos.clone(), filas, columnas);
    }

    public static void guardarEstadoEnHistorial() {
        Estado estado;
        try {
            estado = new Estado(solucion.clone(), listaProductos.clone(), matrizAdyacencia.clone());
        } catch (CloneNotSupportedException e) {
            return;
        }
        if (historial.isEmpty() || !compararEstados(estado, historial.getLast())) {
            historial.add(estado);
        }
    }

    public static void deshacerCambio() {
        if (!historial.isEmpty()) {
            Estado estado;
            do {
                estado = historial.getLast();
                historial.removeLast();
            } while (compararEstados(estado, new Estado(solucion, listaProductos, matrizAdyacencia)));
            listaProductos = estado.getListaProductos();
            matrizAdyacencia = estado.getMatrizAdyacencia();
            solucion = estado.getSolucion();
            if (historial.isEmpty()) guardarEstadoEnHistorial(); // Guardamos el estado actual
        }
    }

    private static boolean compararEstados(Estado e1, Estado e2) {
        ListaProductos lp1, lp2;
        MatrizAdyacencia ma1, ma2;
        Solucion s1, s2;
        lp1 = e1.getListaProductos();
        lp2 = e2.getListaProductos();

        if (!compararListaProductos(lp1, lp2)) {
            return false;
        }

        ma1 = e1.getMatrizAdyacencia();
        ma2 = e2.getMatrizAdyacencia();
        if (!Arrays.deepEquals(ma1.getMatriz(), ma2.getMatriz())) {
            return false;
        }

        s1 = e1.getSolucion();
        s2 = e2.getSolucion();
        return s1.equals(s2);
    }


    private static boolean compararListaProductos(ListaProductos lp1, ListaProductos lp2) {
        if (lp2.getCantidadProductos() != lp1.getCantidadProductos())
            return false;

        for (int i = 0; i < lp1.getCantidadProductos(); i++) {
            Producto p1 = lp1.getListaProductos().get(i);
            Producto p2 = lp2.getListaProductos().get(i);
            if (!p1.getNombre().equals(p2.getNombre()) || p1.getPrecio() != p2.getPrecio()) {
                return false;
            }
        }
        return true;
    }

    public static void reiniciarHistorial() {
        historial.clear();
    }

    public static void borrarUltimoEstadoHistorial() {
        historial.removeLast();
    }
}
