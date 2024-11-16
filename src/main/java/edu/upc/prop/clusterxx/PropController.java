package edu.upc.prop.clusterxx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class PropController {
    @FXML
    private TabPane pane;
    @FXML
    private ListView productosView;

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
        ObservableList<Producto> productosList = FXCollections.observableArrayList();
        productosView.setItems(productosList);
    }

    protected boolean confirmarImportar() {
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
    @FXML
    protected void onImportarEstado() throws IOException, ClassNotFoundException {
        if (!confirmarImportar()) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de origen");
        fileChooser.setInitialFileName("data.state");
        fileChooser.getExtensionFilters().add(stateFilter);
        fileChooser.setSelectedExtensionFilter(stateFilter);
        File in = fileChooser.showOpenDialog(pane.getScene().getWindow());
        Sistema.importarEstado(in);
    }
    @FXML
    protected void onExportarEstado() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de destino");
        fileChooser.setInitialFileName("data.state");
        fileChooser.getExtensionFilters().add(stateFilter);
        fileChooser.setSelectedExtensionFilter(stateFilter);
        File out = fileChooser.showSaveDialog(pane.getScene().getWindow());
        Sistema.exportarEstado(out);
    }
    @FXML
    protected void onImportarLista() {
        /*
        if (!confirmarImportar()) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de origen");
        fileChooser.setInitialFileName("products.list");
        fileChooser.getExtensionFilters().add(listFilter);
        fileChooser.setSelectedExtensionFilter(listFilter);
        File in = fileChooser.showOpenDialog(pane.getScene().getWindow());
        Sistema.importarLista(in);
        */
    }
    @FXML
    protected void onExportarLista() {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de destino");
        fileChooser.setInitialFileName("products.list");
        fileChooser.getExtensionFilters().add(listFilter);
        fileChooser.setSelectedExtensionFilter(listFilter);
        File out = fileChooser.showSaveDialog(pane.getScene().getWindow());
        Sistema.exportarLista(out);
        */
    }
    @FXML
    protected void onImportarRelaciones() {
        /*
        if (!confirmarImportar()) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de origen");
        fileChooser.setInitialFileName("relations.matrix");
        fileChooser.getExtensionFilters().add(relacionesFilter);
        fileChooser.setSelectedExtensionFilter(relacionesFilter);
        File in = fileChooser.showOpenDialog(pane.getScene().getWindow());
        Sistema.importarRelaciones(in);
        */
    }
    @FXML
    protected void onExportarRelaciones() {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de destino");
        fileChooser.setInitialFileName("relations.matrix");
        fileChooser.getExtensionFilters().add(relacionesFilter);
        fileChooser.setSelectedExtensionFilter(relacionesFilter);
        File out = fileChooser.showSaveDialog(pane.getScene().getWindow());
        Sistema.exportarRelaciones(out);
        */
    }
    @FXML
    protected void onImportarSolucion() {
        /*
        if (!confirmarImportar()) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de origen");
        fileChooser.setInitialFileName("result.solution");
        fileChooser.getExtensionFilters().add(solucionFilter);
        fileChooser.setSelectedExtensionFilter(solucionFilter);
        File in = fileChooser.showOpenDialog(pane.getScene().getWindow());
        Sistema.importarSolucion(in);
        */
    }
    @FXML
    protected void onExportarSolucion() {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo de destino");
        fileChooser.setInitialFileName("result.solution");
        fileChooser.getExtensionFilters().add(solucionFilter);
        fileChooser.setSelectedExtensionFilter(solucionFilter);
        File out = fileChooser.showSaveDialog(pane.getScene().getWindow());
        Sistema.exportarSolucion(out);
        */
    }
    protected void close(WindowEvent event) {
        event.consume();
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
            try {
                onExportarEstado();
                Sistema.salir();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
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
                Sistema.salir();
                return true;
            }
        }
        return false;
    }

    @FXML
    protected void onNuevaSolucion() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PropApp.class.getResource("nueva-solucion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(pane.getScene().getWindow());
        stage.setTitle("Nueva Solución");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void onNuevoProducto() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PropApp.class.getResource("nuevo-producto-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(pane.getScene().getWindow());
        stage.setTitle("Nuevo Producto");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}