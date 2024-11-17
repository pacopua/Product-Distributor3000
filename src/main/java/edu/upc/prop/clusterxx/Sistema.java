package edu.upc.prop.clusterxx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;

public class Sistema {
    private static ListaProductos listaProductos = new ListaProductos();
    private static MatrizAdyacencia matrizAdyacencia = new MatrizAdyacencia(0);
    private static Solucion solucion = new Solucion();
    public static ObservableList<Producto> observableProducts = FXCollections.observableArrayList();
    public static ObservableList<Pair<Producto, Producto>> observableProductPairs = FXCollections.observableArrayList();
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
    public static void importarEstado(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Estado estado = (Estado) objectIn.readObject();
        solucion = estado.getSolucion();
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
        System.out.println(listaProductos);
        listaProductos = (ListaProductos) objectIn.readObject();
        System.out.println(listaProductos);
        // TODO: expandir matriz para que quepan todos los productos? encogerla si hay menos?
        actualizarDatos();
    }
    public static void exportarLista(File f) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(listaProductos);
    }
    public static void importarMatriz(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        matrizAdyacencia = (MatrizAdyacencia) objectIn.readObject();
        // TODO: expandir matriz para que quepan todos los productos? encogerla si hay menos?
        actualizarDatos();
    }
    public static void exportarMatriz(File f) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(matrizAdyacencia);
    }
    public static void importarSolucion(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        solucion = (Solucion) objectIn.readObject();
        // TODO: que hacemos si la solución no es compatible con el nuevo número de productos?
        actualizarDatos();
    }
    public static void exportarSolucion(File f) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(solucion);
    }
    public static void salir() {
        Platform.exit();
        System.exit(0);
    }

    public static void nuevaSolucion(int filas, int columnas) {
        solucion = new Solucion(filas, columnas);
        // TODO: como comprobamos si es compatible? que hacemos si no lo es?
    }

    public static void actualizarDatos() {
        observableProducts.setAll(listaProductos.getListaProductos());
        ArrayList<Pair<Producto, Producto>> productPairs = new ArrayList<>();
        for (Producto p1 : listaProductos.getListaProductos())
            for (Producto p2 : listaProductos.getListaProductos())
                if(listaProductos.getListaProductos().indexOf(p1) < listaProductos.getListaProductos().indexOf(p2)) productPairs.add(new Pair(p1, p2));
        observableProductPairs.setAll(productPairs);
        // TODO: actualizar solución? o descartarla?
    }
}
