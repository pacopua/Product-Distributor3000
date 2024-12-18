package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

public class DomainEstadoController {
    public void salir() {
        GestorPesistencia gestorPesistencia = new GestorPesistencia();
        gestorPesistencia.salir();
    }
}
