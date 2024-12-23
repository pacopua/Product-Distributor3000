package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DomainSolucionController
 * Controlador de soluciones del dominio
 */
public class DomainSolucionController {
    /**
     * Instancia única de DomainSolucionController
     */
    private static DomainSolucionController instance;
    /**
     * Algoritmo rápido
     */
    private static AlgoritmoRapido algoRapido;
    /**
     * Algoritmo óptimo
     */
    private static AlgoritmoOptimo algoOptimo;
    /**
     * Algoritmo SA
     */
    private static AlgoritmoUltraRapido algoSA;
    /**
     * Observable para la barra de progreso
     */
    private static DoubleProperty progreso = new SimpleDoubleProperty(0);

    /**
     * Constructor privado de la clase DomainSolucionController
     */
    private DomainSolucionController() {
    }

    /**
     * Metodo que devuelve la instancia unica de la clase IOController
     * @return instancia única de DomainSolucionController
     */
    public static DomainSolucionController getInstance() {
        if (instance == null) {
            instance = new DomainSolucionController();
        }
        return instance;
    }

    /**
     * Metodo para obtener la lista de productos con su id
     * @return ArrayList de Pair<String, Integer> donde String es el nombre del producto y Integer es el id del producto
     */
    public ArrayList<Pair<String, Integer>> getProductosConID(){
        return GestorPesistencia.getInstance().getSolucion().getProductosConId();
    }

    /**
     * Metodo para obtener la calidad de una solucion
     * @return double con la calidad de la solucion
     */
    public double getCalidadSolucion() {
        return GestorPesistencia.getInstance().getSolucion().getCalidad();
    }

    /**
     * Metodo para obtener el numero de pasos de una solucion
     * @return int con el numero de pasos de la solucion
     */
    public int getPasosSolucion() {
        return GestorPesistencia.getInstance().getSolucion().getNumPasos();
    }

    /**
     * Metodo para obtener el ancho de la distribucion de una solucion
     * @return int con el ancho de la distribucion de la solucion
     */
    public int getDistLenght() {
        try {
            return GestorPesistencia.getInstance().getSolucion().getDistribucion()[0].length;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    /**
     * Metodo para obtener el valor de la distribucion en una posicion
     * @param i fila
     * @param j columna
     * @return int con el valor de la posicion i,j en la distribucion
     */
    public int getDistValue(int i, int j) {
        return GestorPesistencia.getInstance().getSolucion().getDistribucion()[i][j];
    }

    /**
     * Metodo para obtener la altura de la distribucion de una solucion
     * @return int con la altura de la distribucion de la solucion
     */
    public int getDistHeight() {
        return GestorPesistencia.getInstance().getSolucion().getDistribucion().length;
    }

    /**
     * Metodo para saber si la solucion esta completada
     * @return true si la solucion esta completada, false en caso contrario
     */
    public boolean is_complete() {
        return GestorPesistencia.getInstance().getSolucion().estaCompletado();
    }

    /**
     * Metodo para detener el algoritmo
     */
    public void parar_algoritmo() {
        if (algoRapido != null) algoRapido.stopExecution();
        if (algoOptimo != null) algoOptimo.stopExecution();
        if (algoSA != null) algoSA.stopExecution();
    }

    /**
     * Metodo para calcular la distribucion de una solucion de forma rapida
     * @param filas numero de filas
     * @param columnas numero de columnas
     */
    public void calcularDistribucionRapida(int filas, int columnas) {
        DomainEstadoController.getInstance().actualizarHistorial();
        GestorPesistencia gp = GestorPesistencia.getInstance();
        Solucion s = gp.nuevaSolucion(filas, columnas);
        algoRapido = new AlgoritmoRapido(gp.getMatrizAdyacencia());
        //lanzamos excepcion si no se puede hacer la distribucion
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        gp.setSolucion(algoRapido.ejecutar(s, 1));
    }

    /**
     * Metodo para calcular la distribucion de una solucion de forma optima
     * @param filas numero de filas
     * @param columnas numero de columnas
     */
    public void calcularDistribucionOptima(int filas, int columnas) {
        DomainEstadoController.getInstance().actualizarHistorial();
        GestorPesistencia gp = GestorPesistencia.getInstance();
        Solucion s = gp.nuevaSolucion(filas, columnas);
        algoOptimo = new AlgoritmoOptimo(gp.getMatrizAdyacencia());
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            //System.out.println("AAAAAAAAA");
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        gp.setSolucion(algoOptimo.ejecutar(s));
    }

    /**
     * Metodo para calcular la distribucion de una solucion de forma ultra rapida
     * @param filas numero de filas
     * @param columnas numero de columnas
     */
    public void calcularDistribucionUltraRapida(int filas, int columnas) {
        DomainEstadoController.getInstance().actualizarHistorial();
        GestorPesistencia gp = GestorPesistencia.getInstance();
        Solucion s = gp.nuevaSolucion(filas, columnas);
        algoSA = new AlgoritmoUltraRapido(gp.getMatrizAdyacencia(), 10000,1, 25, 0.001 );
        if(filas*columnas < s.getListaProductos().getCantidadProductos()) {
            throw new IllegalArgumentException("No se puede hacer la distribución");
        }
        gp.setSolucion(algoSA.ejecutar(s, 1));
    }

    /**
     * Metodo para obtener la lista de nombres de los productos
     * @return lista de nombres de los productos
     */
    public List<String> getListaNombresProductos() {
        return GestorPesistencia.getInstance().getSolucion().getListaProductos().getListaProductos().stream()
                .map(Producto::getNombre)
                .toList();
    }

    /**
     * Metodo para intercambiar dos productos
     * @param nombreP1 nombre del primer producto
     * @param nombreP2 nombre del segundo producto
     */
    public void intercambiarProductos(String nombreP1, String nombreP2) {
        Solucion solucion = GestorPesistencia.getInstance().getSolucion();

        int p1 = -1, p2 = -1;
        List<Producto> lp = solucion.getListaProductos().getListaProductos();
        for (int i = 0; i < lp.size(); ++i) {
            String _nombre = lp.get(i).getNombre();
            if (_nombre.equals(nombreP1)) p1 = i;
            if (_nombre.equals(nombreP2)) p2 = i;
        }

        DomainEstadoController.getInstance().actualizarHistorial();
        int[] pos_p1 = solucion.buscar_producto(p1);
        int[] pos_p2 = solucion.buscar_producto(p2);
        solucion.intercambiar_productos(pos_p1[0], pos_p1[1], pos_p2[0], pos_p2[1]);
    }

    /**
     * Establece un bind entre una progress bar y el observable de progreso
     * @param barra progress bar a la que bindear el observable
     */
    public void bindProgreso(ProgressBar barra) {
        progreso.setValue(0);
        barra.progressProperty().bind(progreso);
    }

    /**
     * Actualiza el valor del observable de progreso
     * @param v Nuevo valor, [0, 1]
     */
    public void setProgreso(double v) {
        progreso.setValue(v);
    }
}
