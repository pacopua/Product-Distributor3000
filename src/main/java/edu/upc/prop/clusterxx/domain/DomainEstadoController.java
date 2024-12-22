package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

public class DomainEstadoController {

    private static DomainEstadoController instance;

    private DomainEstadoController() {
    }

    public static DomainEstadoController getInstance() {
        if (instance == null) {
            instance = new DomainEstadoController();
        }
        return instance;
    }

    public void deshacer() {
        GestorPesistencia.getInstance().deshacerCambio();
    }

    public void actualizarHistorial() {
        GestorPesistencia.getInstance().guardarEstadoEnHistorial();
    }
}
