package edu.upc.prop.clusterxx;

import javafx.application.Platform;

import java.io.*;

public class Sistema {
    private static Solucion solucion;
    public static Solucion getSolucion() {
        return solucion;
    }
    public static void importarEstado(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Estado estado = (Estado) objectIn.readObject();
        solucion = estado.getSolucion();
    }
    public static void exportarEstado(File f) throws IOException {
        Estado estado = new Estado(solucion);
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(estado);
    }
    public static void salir() {
        Platform.exit();
        System.exit(0);
    }

    public static void nuevaSolucion(int filas, int columnas) {
        solucion = new Solucion(filas, columnas);
    }
}
