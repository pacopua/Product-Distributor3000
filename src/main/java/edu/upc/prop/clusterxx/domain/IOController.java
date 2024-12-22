package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

import java.io.File;
import java.io.IOException;

/**
 * Clase IOController
 * Controlador de entrada y salida
 */
public class IOController {
    /**
     * Instancia unica de la clase IOController
     */
    private static IOController instance;

    /**
     * Constructor privado de la clase IOController
     */
    private IOController() {
    }

    /**
     * Metodo que devuelve la instancia unica de la clase IOController
     * @return instancia unica de la clase IOController
     */
    public static IOController getInstance() {
        if (instance == null) {
            instance = new IOController();
        }
        return instance;
    }

    /**
     * Metodo para cerra el programa
     */
    public void salir() {
        GestorPesistencia.getInstance().salir();
    }

    /**
     * Metodo para importar el estado del programa
     * @param ruta_archivo ruta del archivo a importar
     */
    public void importarEstado(String ruta_archivo) throws IOException, ClassNotFoundException {
        GestorPesistencia.getInstance().importarEstado(new File(ruta_archivo));
    }

    /**
     * Metodo para exportar el estado del programa
     * @param ruta_archivo ruta del archivo a exportar
     */
    public void exportarEstado(String ruta_archivo) throws IOException {
        GestorPesistencia.getInstance().exportarEstado(new File(ruta_archivo));
    }

    /**
     * Metodo para importar la lista de productos
     * @param ruta_archivo ruta del archivo a importar
     */
    public void importarListaProductos(String ruta_archivo) throws IOException, ClassNotFoundException {
        GestorPesistencia.getInstance().importarLista(new File(ruta_archivo));
    }

    /**
     * Metodo para exportar la lista de productos
     * @param ruta_archivo ruta del archivo a exportar
     */
    public void exportarListaProductos(String ruta_archivo) throws IOException {
        GestorPesistencia.getInstance().exportarLista(new File(ruta_archivo));
    }
}
