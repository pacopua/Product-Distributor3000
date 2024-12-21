package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

public class DomainEstadoController {
    public void salir() {
        GestorPesistencia.salir();
    }

    public static void deshacer() {
        GestorPesistencia.deshacerCambio();
    }

    public static void actualizarHistorial() {
        GestorPesistencia.guardarEstadoEnHistorial();
    }

    public static void borrarUltimoEstadoHistorial() {
        GestorPesistencia.borrarUltimoEstadoHistorial();
    }
}
