package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.PropApp;
import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.DomainEstadoController;
import edu.upc.prop.clusterxx.domain.IOController;
import edu.upc.prop.clusterxx.domain.Producto;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class PropController {
    @FXML
    private TabPane pane;
    @FXML
    private ListView productosView;
    @FXML
    private ListView relacionesView;
    @FXML
    private TableView<Producto[]> solucionView;
    @FXML
    private Label calidad;
    @FXML
    private Label pasos;
    @FXML
    private Button intercambiar;
    @FXML
    HBox calidadBox;
    @FXML
    HBox pasosBox;
    private static IOController ioController = new IOController();
    private static ObservableList<Producto> observableProducts = FXCollections.observableArrayList();
    private static ObservableList<Pair<Producto, Producto>> observableProductPairs = FXCollections.observableArrayList();
    private static ObservableList<Producto[]> observableSolutionProducts = FXCollections.observableArrayList();

    ButtonType si = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    FileChooser.ExtensionFilter stateFilter = new FileChooser.ExtensionFilter(
            "Archivo de estado",
            Arrays.asList("*.state")
        );
    FileChooser.ExtensionFilter listFilter = new FileChooser.ExtensionFilter(
            "Archivo de productos",
            Arrays.asList("*.list")
    );
    FileChooser.ExtensionFilter relacionesFilter = new FileChooser.ExtensionFilter(
            "Archivo de relaciones",
            Arrays.asList("*.matrix")
    );
    FileChooser.ExtensionFilter solucionFilter = new FileChooser.ExtensionFilter(
            "Archivo de solución",
            Arrays.asList("*.solution")
    );

    @FXML
    private void initialize() {
        productosView.setCellFactory(producto -> new ProductoCell());
        productosView.setItems(observableProducts);
        relacionesView.setCellFactory(productPair -> new RelacionCell());
        relacionesView.setItems(observableProductPairs);
        solucionView.setItems(observableSolutionProducts);
    }

    private boolean ventanaConfirmar() {
        // pedimos confirmación
        Alert alerta = new Alert(
                Alert.AlertType.WARNING,
                "Esta acción reemplazará los datos.\n¿Desea continuar?",
                si,
                no
        );
        alerta.setTitle("Importar datos");
        Optional<ButtonType> eleccion = alerta.showAndWait();
        return eleccion.get() == si;
    }
    private void ventanaErrorExportar() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "No se han podido exportar los datos.\n" +
                        "Los datos son incorrectos o no se tiene acceso de lectura."
        );
        alerta.setTitle("Error de exportación");
        alerta.showAndWait();
    }
    private void ventanaErrorArchivo() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "No se han podido importar los datos.\n" +
                        "El archivo está corrupto o no se tiene acceso de lectura."
        );
        alerta.setTitle("Archivo incorrecto");
        alerta.showAndWait();
    }
    private void ventanaErrorInternoArchivo() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "No se han podido importar los datos.\n" +
                        "El sistema ha encontrado un error irrecuperable."
        );
        alerta.setTitle("Error interno al importar");
        alerta.showAndWait();
    }
    private File abrirFileChooser(boolean in, String archivo, FileChooser.ExtensionFilter filtro) {
        FileChooser fileChooser = new FileChooser();
        if (in) fileChooser.setTitle("Escoja el archivo de origen");
        else fileChooser.setTitle("Escoja el archivo de destino");
        fileChooser.setInitialFileName(archivo);
        fileChooser.getExtensionFilters().add(filtro);
        fileChooser.setSelectedExtensionFilter(filtro);
        if (in) return fileChooser.showOpenDialog(pane.getScene().getWindow());
        else return fileChooser.showSaveDialog(pane.getScene().getWindow());

    }
    @FXML
    protected boolean onImportarEstado() {
        if (!ventanaConfirmar()) return false;
        File in = abrirFileChooser(true,"data.state", stateFilter);
        if (in == null) return false;
        try {
            ioController.importarEstado(in.getAbsolutePath());
            actualizarDatos();
            actualizarSolucion();
        } catch (IOException e) {
            ventanaErrorArchivo();
            return false;
        } catch (ClassNotFoundException e) {
            ventanaErrorInternoArchivo();
            return false;
        }
        return true;
    }
    @FXML
    protected boolean onExportarEstado() {
        File out = abrirFileChooser(false,"data.state", stateFilter);
        if (out == null) return false;
        try {
            ioController.exportarEstado(out.getAbsolutePath());
        } catch (IOException e) {
            ventanaErrorExportar();
            return false;
        }
        return true;
    }
    @FXML
    protected boolean onImportarLista() {
        if (!ventanaConfirmar()) return false;;
        File in = abrirFileChooser(true,"products.list", listFilter);
        if (in == null) return false;;
        try {
            ioController.importarListaProductos(in.getAbsolutePath());
            actualizarDatos();
        } catch (IOException e) {
            ventanaErrorArchivo();
            return false;
        } catch (ClassNotFoundException e) {
            ventanaErrorInternoArchivo();
            return false;
        }
        return true;
    }
    @FXML
    protected boolean onExportarLista() {
        File out = abrirFileChooser(false,"products.list", listFilter);
        if (out == null) return false;
        try {
            ioController.exportarListaProductos(out.getAbsolutePath());
        } catch (IOException e) {
            ventanaErrorExportar();
            return false;
        }
        return true;
    }
    /*
    @FXML
    protected void onImportarSolucion() {
        if (!ventanaConfirmar()) return;
        File in = abrirFileChooser(true,"result.solution", solucionFilter);
        if (in == null) return;
        try {
            Sistema.importarSolucion(in);
            actualizarSolucion();
        } catch (IOException e) {
            ventanaErrorArchivo();
        } catch (ClassNotFoundException e) {
            ventanaErrorInternoArchivo();
        }
    }

    @FXML
    protected void onExportarSolucion() {
        File out = abrirFileChooser(false,"result.solution", solucionFilter);
        if (out == null) return;
        try {
            Sistema.exportarSolucion(out);
        } catch (IOException e) {
            ventanaErrorExportar();
        }
    }
    */
    @FXML
    public boolean onSalir() {
        // preguntamos si quiere guardar antes de salir
        Alert alerta = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Guardar estado antes de salir?",
                si,
                no
        );
        alerta.setTitle("Guardar y salir");
        Optional<ButtonType> eleccion = alerta.showAndWait();
        // guardamos y salimos
        DomainEstadoController domainEstadoController = new DomainEstadoController();
        if (eleccion.get() == si) {
            if(onExportarEstado()) {
                domainEstadoController.salir();
                return true;
            }
        }
        // preguntamos si de verdad quiere salir sin guardar
        else {
            alerta = new Alert(
                    Alert.AlertType.WARNING,
                    "¿Seguro que desea salir sin guardar?",
                    si,
                    no
            );
            alerta.setTitle("Salir sin guardar");
            eleccion = alerta.showAndWait();
            // si confirma, cerramos la aplicación
            if (eleccion.get() == si) {
                domainEstadoController.salir();
                return true;
            }
        }
        return false;
    }

    private void abrirVentana(String nombre, String controlador) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PropApp.class.getResource(controlador));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(pane.getScene().getWindow());
        stage.setTitle(nombre);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    protected void onNuevaSolucion() throws IOException {
        if (!ventanaConfirmar()) return;
        abrirVentana("Nueva Solución", "nueva-solucion-view.fxml");
        actualizarSolucion();
    }

    public static void actualizarDatos() {
        observableProducts.setAll(GestorPesistencia.getListaProductos().getListaProductos());
        ArrayList<Pair<Producto, Producto>> productPairs = new ArrayList<>();
        for (Producto p1 : GestorPesistencia.getListaProductos().getListaProductos())
            for (Producto p2 : GestorPesistencia.getListaProductos().getListaProductos())
                if(GestorPesistencia.getListaProductos().getListaProductos().indexOf(p1) < GestorPesistencia.getListaProductos().getListaProductos().indexOf(p2)) productPairs.add(new Pair(p1, p2));
        observableProductPairs.setAll(productPairs);
    }

    private void actualizarSolucion() {
        calidadBox.setDisable(true);
        pasosBox.setDisable(true);
        intercambiar.setDisable(true);
        solucionView.getColumns().clear();

        if (GestorPesistencia.getSolucion().estaCompletado()) {
            calidadBox.setDisable(false);
            pasosBox.setDisable(false);
            intercambiar.setDisable(false);
            calidad.setText(Double.toString(GestorPesistencia.getSolucion().getCalidad()));
            pasos.setText(Integer.toString(GestorPesistencia.getSolucion().getNumPasos()));

            for (int i = 0; i < GestorPesistencia.getSolucion().getDistribucion()[0].length; i++) {
                TableColumn<Producto[], String> col = new TableColumn<>(Integer.toString(i + 1));
                int index = i;
                col.setCellValueFactory(param -> {
                            Producto prod = param.getValue()[index];
                            if (prod == null) return new SimpleStringProperty("");
                            else return new SimpleStringProperty(prod.getNombre());
                        }
                );
                solucionView.getColumns().add(col);
            }

            observableSolutionProducts = FXCollections.observableArrayList();
            for (int i = 0; i < GestorPesistencia.getSolucion().getDistribucion().length; i++)
                observableSolutionProducts.add(GestorPesistencia.getSolucion().getDistribucionProductos()[i]);
            solucionView.setItems(observableSolutionProducts);
        }
    }

    @FXML
    protected void onNuevoProducto() throws IOException {
        abrirVentana("Nuevo Producto", "nuevo-producto-view.fxml");
        actualizarDatos();
    }

    @FXML
    protected void onIntercambiarProductos() throws IOException {
        abrirVentana("Intercambiar Productos", "intercambiar-productos-view.fxml");
        actualizarSolucion();
    }
}