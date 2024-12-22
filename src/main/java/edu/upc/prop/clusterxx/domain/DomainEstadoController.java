package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

public class DomainEstadoController {
    private static DomainEstadoController instance;

    /**
     * Constructor privado
     */
    private DomainEstadoController() {
    }

    /**
     * Singleton
     * @return instancia única de DomainEstadoController
     */
    public static DomainEstadoController getInstance() {
        if (instance == null) {
            instance = new DomainEstadoController();
        }
        return instance;
    }

    /**
     * Deshace el último cambio
     */
    public void deshacer() {
        GestorPesistencia.getInstance().deshacerCambio();
    }

    /**
     * Actualiza el historial de cambios
     */
    public void actualizarHistorial() {
        GestorPesistencia.getInstance().guardarEstadoEnHistorial();
    }
}
