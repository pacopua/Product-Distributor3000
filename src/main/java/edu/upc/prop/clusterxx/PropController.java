package edu.upc.prop.clusterxx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private TableView solucionView;
    @FXML
    private Label calidad;
    @FXML
    private Label pasos;
    @FXML
    private Button intercambiar;

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
        productosView.setItems(Sistema.observableProducts);
        relacionesView.setCellFactory(productPair -> new RelacionCell());
        relacionesView.setItems(Sistema.observableProductPairs);
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
    protected void onImportarEstado() {
        if (!ventanaConfirmar()) return;
        File in = abrirFileChooser(true,"data.state", stateFilter);
        if (in == null) return;
        try {
            Sistema.importarEstado(in);
        } catch (IOException e) {
            ventanaErrorArchivo();
        } catch (ClassNotFoundException e) {
            ventanaErrorInternoArchivo();
        }
    }
    @FXML
    protected void onExportarEstado() {
        File out = abrirFileChooser(false,"data.state", stateFilter);
        if (out == null) return;
        try {
            Sistema.exportarEstado(out);
        } catch (IOException e) {
            ventanaErrorExportar();
        }
    }
    @FXML
    protected void onImportarLista() {
        if (!ventanaConfirmar()) return;
        File in = abrirFileChooser(true,"products.list", listFilter);
        if (in == null) return;
        try {
            Sistema.importarLista(in);
        } catch (IOException e) {
            ventanaErrorArchivo();
        } catch (ClassNotFoundException e) {
            ventanaErrorInternoArchivo();
        }
    }
    @FXML
    protected void onExportarLista() {
        File out = abrirFileChooser(false,"products.list", listFilter);
        if (out == null) return;
        try {
            Sistema.exportarLista(out);
        } catch (IOException e) {
            ventanaErrorExportar();
        }
    }
    @FXML
    protected void onImportarSolucion() {
        if (!ventanaConfirmar()) return;
        File in = abrirFileChooser(true,"result.solution", solucionFilter);
        if (in == null) return;
        try {
            Sistema.importarSolucion(in);
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
    @FXML
    protected boolean onSalir() {
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
        if (eleccion.get() == si) {
            onExportarEstado();
            Sistema.salir();
            return true;
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
                Sistema.salir();
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
        calidad.setText(Double.toString(Sistema.getSolucion().getCalidad()));
        pasos.setText(Integer.toString(Sistema.getSolucion().getNumPasos()));
    }

    @FXML
    protected void onNuevoProducto() throws IOException {
        abrirVentana("Nueva Producto", "nuevo-producto-view.fxml");
    }

    @FXML
    protected void onIntercambiarProductos() throws IOException {
        abrirVentana("Intercambiar Productos", "intercambiar-productos-view.fxml");
    }
}