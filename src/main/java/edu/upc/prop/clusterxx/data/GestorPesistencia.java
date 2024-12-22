package edu.upc.prop.clusterxx.data;

import edu.upc.prop.clusterxx.domain.*;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Clase GestorPesistencia
 * Gestiona la persistencia de los datos
 */
public class GestorPesistencia {
    /**
     * Instancia única de GestorPesistencia
     */
    private static GestorPesistencia instance;
    /**
     * Lista de productos
     */
    private ListaProductos listaProductos = new ListaProductos();
    /**
     * Matriz de adyacencia
     */
    private MatrizAdyacencia matrizAdyacencia = new MatrizAdyacencia(0);
    /**
     * Solución
     */
    private Solucion solucion = new Solucion(listaProductos);
    /**
     * Historial de estados
     */
    private final ArrayList<Estado> historial = new ArrayList<>();

    /**
     * Constructor privado de la clase GestorPesistencia
     */
    private GestorPesistencia() {
    }

    /**
     * Metodo que devuelve la instancia única de GestorPesistencia
     * @return instancia única de GestorPesistencia
     */
    public static GestorPesistencia getInstance() {
        if (instance == null) {
            instance = new GestorPesistencia();
        }

        return instance;
    }

    /**
     * Devuelve la lista de productos
     * @return lista de productos
     */
    public ListaProductos getListaProductos() {
        return listaProductos;
    }

    /**
     * Devuelve la matriz de adyacencia
     * @return matriz de adyacencia
     */
    public MatrizAdyacencia getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    /**
     * Devuelve la solución
     * @return solución
     */
    public Solucion getSolucion() {
        return solucion;
    }

    /**
     * Establece la lista matriz de adyacencia
     * @param nuevaMatrizAdyacencia nueva matriz de adyacencia
     */
    public void setMatrizAdyacencia(MatrizAdyacencia nuevaMatrizAdyacencia) { // temporal?
        matrizAdyacencia = nuevaMatrizAdyacencia;
    }

    /**
     * Establece la solución
     * @param s nueva solución
     */
    public void setSolucion(Solucion s) {
        solucion = s;
    }

    /**
     * Importa el estado de un archivo
     * @param f archivo a importar
     */
    public void importarEstado(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Estado estado = (Estado) objectIn.readObject();
        listaProductos = estado.getListaProductos();
        matrizAdyacencia = estado.getMatrizAdyacencia();
        solucion = estado.getSolucion();
    }

    /**
     * Exporta el estado actual a un archivo
     * @param f archivo a exportar
     */
    public void exportarEstado(File f) throws IOException {
        Estado estado = new Estado(solucion, listaProductos, matrizAdyacencia);
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(estado);
    }

    /**
     * Importa una lista de productos de un archivo
     * @param f archivo a importar
     */
    public void importarLista(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Estado estado = (Estado) objectIn.readObject();
        listaProductos = estado.getListaProductos();
        matrizAdyacencia = estado.getMatrizAdyacencia();
    }

    /**
     * Exporta la lista de productos actual a un archivo
     * @param f archivo a exportar
     */
    public void exportarLista(File f) throws IOException {
        Estado estado = new Estado(new Solucion(listaProductos, 0, 0), listaProductos, matrizAdyacencia);
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(estado);
    }

    /**
     * Importa una matriz de adyacencia de un archivo
     */
    public void salir() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Crea una nueva solucion de tamanyo filas x columnas
     * @param filas Número de filas
     * @param columnas Número de columnas
     * @return Nueva solución
     */
    public Solucion nuevaSolucion(int filas, int columnas) throws IllegalArgumentException {
        return new Solucion(listaProductos.clone(), filas, columnas);
    }

    /**
     * Devuelve el estado actual
     * @return Estado actual
     */
    private Estado getEstadoActual()  {
        return new Estado(solucion.clone(), listaProductos.clone(), matrizAdyacencia.clone());
    }

    /**
     * Guarda el estado actual en el historial
     */
    public void guardarEstadoEnHistorial() {
        Estado estado = getEstadoActual();
        Estado last;
        try {
            last = historial.getLast();
        } catch (NoSuchElementException e) {
            last = null;
        }
        if (last == null || !compararEstados(estado, last)) {
            historial.add(estado);
        }
    }

    /**
     * Deshace el último cambio
     */
    public void deshacerCambio() {
        if (!historial.isEmpty()) {
            Estado estado;
            do {
                estado = historial.getLast();
                historial.removeLast();
            } while (!historial.isEmpty() && compararEstados(estado, getEstadoActual()));
            listaProductos = estado.getListaProductos();
            matrizAdyacencia = estado.getMatrizAdyacencia();
            solucion = estado.getSolucion();
            if (historial.isEmpty()) {
                guardarEstadoEnHistorial();
            }
        }
    }

    /**
     * Compara dos estados
     * @param e1 Estado 1
     * @param e2 Estado 2
     * @return true si son iguales, false si no
     */
    public boolean compararEstados(Estado e1, Estado e2) {
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

    /**
     * Compara dos listas de productos
     * @param lp1 Lista de productos 1
     * @param lp2 Lista de productos 2
     * @return true si son iguales, false si no
     */
    private boolean compararListaProductos(ListaProductos lp1, ListaProductos lp2) {
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
}
