package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

import java.io.File;
import java.io.IOException;

public class IOController {
    public void salir() {
        GestorPesistencia.getInstance().salir();
    }

    public void importarEstado(String ruta_archivo) throws IOException, ClassNotFoundException {
        GestorPesistencia.getInstance().importarEstado(new File(ruta_archivo));
    }

    public void exportarEstado(String ruta_archivo) throws IOException {
        GestorPesistencia.getInstance().exportarEstado(new File(ruta_archivo));
    }

    public void importarListaProductos(String ruta_archivo) throws IOException, ClassNotFoundException {
        GestorPesistencia.getInstance().importarLista(new File(ruta_archivo));
    }

    public void exportarListaProductos(String ruta_archivo) throws IOException {
        GestorPesistencia.getInstance().exportarLista(new File(ruta_archivo));
    }
}
