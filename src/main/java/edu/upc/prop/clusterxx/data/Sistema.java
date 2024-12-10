package edu.upc.prop.clusterxx.data;

import edu.upc.prop.clusterxx.domain.Estado;
import edu.upc.prop.clusterxx.domain.ListaProductos;
import edu.upc.prop.clusterxx.domain.MatrizAdyacencia;
import edu.upc.prop.clusterxx.domain.Solucion;
import javafx.application.Platform;

import java.io.*;

public class Sistema {
    private static ListaProductos listaProductos = new ListaProductos();
    private static MatrizAdyacencia matrizAdyacencia = new MatrizAdyacencia(0);
    private static Solucion solucion = new Solucion(listaProductos);
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
        Solucion s = new Solucion(listaProductos.clone(), filas, columnas);
        return s;
    }
}
