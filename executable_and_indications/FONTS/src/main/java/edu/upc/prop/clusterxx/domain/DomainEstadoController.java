package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.data.GestorPesistencia;

/**
 * Clase DomainEstadoController
 * Controlador de estado del dominio
 */
public class DomainEstadoController {
    /**
     * Instancia única de DomainEstadoController
     */
    private static DomainEstadoController instance;

    /**
     * Constructor privado de la clase DomainEstadoController
     */
    private DomainEstadoController() {
    }

    /**
     * Metodo que devuelve la instancia unica de la clase DomainEstadoController
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
