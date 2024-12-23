package edu.upc.prop.clusterxx.visual;

import edu.upc.prop.clusterxx.PropApp;
//import edu.upc.prop.clusterxx.data.GestorPesistencia;
import edu.upc.prop.clusterxx.domain.IOController;
import edu.upc.prop.clusterxx.domain.DomainProductoController;
import edu.upc.prop.clusterxx.domain.DomainSolucionController;
import edu.upc.prop.clusterxx.domain.DomainEstadoController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
    private TableView<Pair<String, Integer>[]> solucionView;
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
    private static IOController ioController = IOController.getInstance();
    private static DomainProductoController domainProductoController = DomainProductoController.getInstance();
    private static DomainSolucionController domainSolucionController = DomainSolucionController.getInstance();

    private static ObservableList<Integer> observableProducts = FXCollections.observableArrayList();
    private static ObservableList<Pair<Integer, Integer>> observableProductPairs = FXCollections.observableArrayList();
    private static ObservableList<Pair<String, Integer>[]> observableSolutionProducts = FXCollections.observableArrayList();

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
        productosView.setCellFactory(producto -> new ProductoCell(this));
        productosView.setItems(observableProducts);
        relacionesView.setCellFactory(productPair -> new RelacionCell());
        relacionesView.setItems(observableProductPairs);
        solucionView.setItems(observableSolutionProducts);
        DomainEstadoController.getInstance().actualizarHistorial();
    }

    public boolean ventanaConfirmar(String mensaje, String titulo) {
        // pedimos confirmación
        Alert alerta = new Alert(
                Alert.AlertType.WARNING,
                mensaje,
                si,
                no
        );
        alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
        alerta.setTitle(titulo);
        Optional<ButtonType> eleccion = alerta.showAndWait();
        return eleccion.get() == si;
    }
    private void ventanaErrorExportar() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "No se han podido exportar los datos.\n" +
                        "Los datos son incorrectos o no se tiene acceso de lectura."
        );
        alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
        alerta.setTitle("Error de exportación");
        alerta.showAndWait();
    }
    private void ventanaErrorArchivo() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "No se han podido importar los datos.\n" +
                        "El archivo está corrupto o no se tiene acceso de lectura."
        );
        alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
        alerta.setTitle("Archivo incorrecto");
        alerta.showAndWait();
    }
    private void ventanaErrorInternoArchivo() {
        Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                "No se han podido importa1r los datos.\n" +
                        "El sistema ha encontrado un error irrecuperable."
        );
        alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
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
        if (!ventanaConfirmar("Esta acción reemplazará los datos.\n¿Desea continuar?", "Importar estado")) return false;
        File in = abrirFileChooser(true,"data.state", stateFilter);
        if (in == null) return false;
        try {
            DomainEstadoController.getInstance().actualizarHistorial();
            ioController.importarEstado(in.getAbsolutePath());
            actualizarDatos();
            //actualizarSolucion();

            ordenarProductosView();
            ordenarRelacionesView();
            // Retrieve the list of products

            if(domainSolucionController.is_complete()) {
                calidadBox.setDisable(false);
                pasosBox.setDisable(false);
                intercambiar.setDisable(false);
                calidad.setText(Double.toString(domainSolucionController.getCalidadSolucion()));
                pasos.setText(Integer.toString(domainSolucionController.getPasosSolucion()));
            }
            /*
            ArrayList<Pair<String, Integer>> productosConID = domainSolucionController.getProductosConID();
            //Print the arraylist
            // Clear the current observableSolutionProducts
            observableSolutionProducts.clear();

            // Separate the products into rows and add them to observableSolutionProducts
            int rowLength = domainSolucionController.getDistLenght();
            for (int i = 0; i < productosConID.size(); i += rowLength) {
                Pair<String, Integer>[] row = new Pair[rowLength];
                for (int j = 0; j < rowLength && (i + j) < productosConID.size(); j++) {
                    row[j] = productosConID.get(i + j);
                }
                observableSolutionProducts.add(row);
            }

            solucionView.getColumns().clear();

            // Add new columns
            for (int i = 0; i < rowLength; i++) {
                TableColumn<Pair<String, Integer>[], String> col = new TableColumn<>(Integer.toString(i + 1));
                int index = i;
                col.setCellValueFactory(param -> {
                    Pair<String, Integer> prodPair = param.getValue()[index];
                    if (prodPair == null) return new SimpleStringProperty("");
                    else return new SimpleStringProperty(prodPair.getKey());
                });
                solucionView.getColumns().add(col);
            }

            solucionView.setItems(observableSolutionProducts);
            solucionView.refresh();

             */
            ArrayList<Pair<String, Integer>> productosConID = domainSolucionController.getProductosConID();
            observableSolutionProducts.clear();

            int rowLength = domainSolucionController.getDistLenght();
            for (int i = 0; i < productosConID.size(); i += rowLength) {
                Pair<String, Integer>[] row = new Pair[rowLength];
                for (int j = 0; j < rowLength && (i + j) < productosConID.size(); j++) {
                    row[j] = productosConID.get(i + j);
                }
                observableSolutionProducts.add(row);
            }

            solucionView.getColumns().clear();

            // Add the first column with # and row numbers
            TableColumn<Pair<String, Integer>[], String> colNumeros = new TableColumn<>("#");
            colNumeros.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(observableSolutionProducts.indexOf(param.getValue()) + 1)));
            solucionView.getColumns().add(colNumeros);

            for (int i = 0; i < rowLength; i++) {
                TableColumn<Pair<String, Integer>[], String> col = new TableColumn<>(Integer.toString(i + 1));
                int index = i;
                col.setCellValueFactory(param -> {
                    Pair<String, Integer> prodPair = param.getValue()[index];
                    if (prodPair == null) return new SimpleStringProperty("");
                    else return new SimpleStringProperty(prodPair.getKey());
                });
                solucionView.getColumns().add(col);
            }
            solucionView.setItems(observableSolutionProducts);
            solucionView.refresh();
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
        if (!ventanaConfirmar("Esta acción reemplazará los productos.\n¿Desea continuar?", "Importar Productos")) return false;;
        File in = abrirFileChooser(true,"products.list", listFilter);
        if (in == null) return false;;
        try {
            DomainEstadoController.getInstance().actualizarHistorial();
            ioController.importarListaProductos(in.getAbsolutePath());
            actualizarDatos();
            ordenarProductosView();
            ordenarRelacionesView();
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
        alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
        alerta.setTitle("Guardar y salir");
        Optional<ButtonType> eleccion = alerta.showAndWait();
        // guardamos y salimos
        //if(!alerta.isShowing()) return false;
         IOController ioController = IOController.getInstance();
        if (eleccion.get() == si) {
            if(onExportarEstado()) {
                ioController.salir();
                return true;
            }
        }
        // preguntamos si de verdad quiere salir sin guardar
        else if (eleccion.get() == no) {
            alerta = new Alert(
                    Alert.AlertType.WARNING,
                    "¿Seguro que desea salir sin guardar?",
                    si,
                    no
            );
            alerta.getDialogPane().getStylesheets().add(PropApp.class.getResource("/edu/upc/prop/clusterxx/styles.css").toExternalForm());
            alerta.setTitle("Salir sin guardar");
            eleccion = alerta.showAndWait();
            // si confirma, cerramos la aplicación
            if (eleccion.get() == si) {
                ioController.salir();
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
        if(domainSolucionController.is_complete())
            if (!ventanaConfirmar("Esta acción reemplazará la solución.\n¿Desea continuar?", "Reemplazar solución")) return;
        abrirVentana("Nueva Solución", "nueva-solucion-view.fxml");
        actualizarSolucion();
    }

    private static ArrayList<Integer>getProductosIds() {
        return domainProductoController.getProductsIds();
    }

    public static void actualizarDatos() {
        //System.out.println("actualizando_datos...");
        observableProducts.setAll(getProductosIds());

        ArrayList<Pair<Integer, Integer>> productPairs = domainProductoController.lista_sinergias();
        /*
        for (Producto p1 : GestorPesistencia.getListaProductos().getListaProductos())
            for (Producto p2 : GestorPesistencia.getListaProductos().getListaProductos())
                if(GestorPesistencia.getListaProductos().getListaProductos().indexOf(p1) < GestorPesistencia.getListaProductos().getListaProductos().indexOf(p2)) productPairs.add(new Pair(p1, p2));
        */
        observableProductPairs.setAll(productPairs);

        //productosView.refresh();
    }

    public ObservableList<Pair<String, Integer>[]> getObservableSolutionProducts() {
        return observableSolutionProducts;
    }
    public static ObservableList<String> getProductsIntercambio() {
        ObservableList<String> productNames = FXCollections.observableArrayList();
        for (Pair<String, Integer>[] row : observableSolutionProducts) {
            boolean primera = true;
            for (Pair<String, Integer> pair : row) {
                if (!primera && pair != null && pair.getKey() != null) {
                    productNames.add(pair.getKey());
                }
                primera = false;
            }
        }
        return productNames;
    }

    private void actualizarSolucion() {
        calidadBox.setDisable(true);
        pasosBox.setDisable(true);
        intercambiar.setDisable(true);
        solucionView.getColumns().clear();

        if (domainSolucionController.is_complete()) {
            calidadBox.setDisable(false);
            pasosBox.setDisable(false);
            intercambiar.setDisable(false);
            calidad.setText(Double.toString(domainSolucionController.getCalidadSolucion()));
            pasos.setText(Integer.toString(domainSolucionController.getPasosSolucion()));

            TableColumn<Pair<String, Integer>[], String> colNumeros = new TableColumn<>("#");
            colNumeros.setCellValueFactory(param -> {
                Pair<String, Integer> prodPair = param.getValue()[0];
                return new SimpleStringProperty(Integer.toString(prodPair.getValue()));
            });
            solucionView.getColumns().add(colNumeros);

            for (int i = 0; i < domainSolucionController.getDistLenght(); i++) {
                TableColumn<Pair<String, Integer>[], String> col = new TableColumn<>(Integer.toString(i + 1));
                int index = i + 1;
                col.setCellValueFactory(param -> {
                    Pair<String, Integer> prodPair = param.getValue()[index];
                    if (prodPair == null) return new SimpleStringProperty("");
                    else return new SimpleStringProperty(prodPair.getKey());
                });
                solucionView.getColumns().add(col);
            }

            observableSolutionProducts = FXCollections.observableArrayList();
            for (int i = 0; i < domainSolucionController.getDistHeight(); i++) {
                Pair<String, Integer>[] row = new Pair[domainSolucionController.getDistLenght() + 1];
                row[0] = new Pair<>("", i + 1);
                for (int j = 0; j < domainSolucionController.getDistLenght(); j++) {
                    Integer prodID = domainSolucionController.getDistValue(i, j);
                    String prodName = prodID == null ? "" : domainProductoController.getNombreProductoPorId(prodID);
                    row[j + 1] = new Pair<>(prodName, prodID);
                }
                observableSolutionProducts.add(row);
            }
            solucionView.setItems(observableSolutionProducts);
        }
    }

    public void actualizarAfterSwap(String nombreP1,String nombreP2) {
        //change the products in the observable list
        Pair<String, Integer> pos1 = null;
        Pair<String, Integer> pos2 = null;
        int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

        // Find the positions of the products
        for (int i = 0; i < observableSolutionProducts.size(); i++) {
            Pair<String, Integer>[] row = observableSolutionProducts.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] != null && row[j].getKey() != null && row[j].getKey().equals(nombreP1)) {
                    pos1 = row[j];
                    row1 = i;
                    col1 = j;
                } else if (row[j] != null && row[j].getKey() != null && row[j].getKey().equals(nombreP2)) {
                    pos2 = row[j];
                    row2 = i;
                    col2 = j;
                }
            }
        }

        // Swap the products if both are found
        if (pos1 != null && pos2 != null) {
            observableSolutionProducts.get(row1)[col1] = pos2;
            observableSolutionProducts.get(row2)[col2] = pos1;
        }
        //solucionView.setItems(observableSolutionProducts);
    }
    /*
    private void actualizarSolucion() {
        calidadBox.setDisable(true);
        pasosBox.setDisable(true);
        intercambiar.setDisable(true);
        solucionView.getColumns().clear();

        if (domainSolucionController.is_complete()) {
            calidadBox.setDisable(false);
            pasosBox.setDisable(false);
            intercambiar.setDisable(false);
            calidad.setText(Double.toString(domainSolucionController.getCalidadSolucion()));
            pasos.setText(Integer.toString(domainSolucionController.getPasosSolucion()));

            for (int i = 0; i < domainSolucionController.getDistLenght(); i++) {
                TableColumn<Integer[], String> col = new TableColumn<>(Integer.toString(i + 1));
                int index = i;
                col.setCellValueFactory(param -> {
                            Integer prodID = param.getValue()[index];
                            if (prodID == null) return new SimpleStringProperty("");
                            else return new SimpleStringProperty(domainProductoController.getNombreProductoPorId(prodID));
                        }
                );
                solucionView.getColumns().add(col);
            }

            observableSolutionProducts = FXCollections.observableArrayList();
            for (int i = 0; i < domainSolucionController.getDistHeight(); i++) {
                Integer[] row = new Integer[domainSolucionController.getDistLenght()];
                for (int j = 0; j < domainSolucionController.getDistLenght(); j++)
                    row[j] = domainSolucionController.getDistValue(i, j);
                            //GestorPesistencia.getSolucion().getDistribucion()[i][j];
                observableSolutionProducts.add(row);
            }
                //observableSolutionProducts.add(GestorPesistencia.getSolucion().getDistribucionProductos()[i]);
            solucionView.setItems(observableSolutionProducts);
        }
    }

     */

    @FXML
    protected void onNuevoProducto() throws IOException {
        abrirVentana("Nuevo Producto", "nuevo-producto-view.fxml");
        actualizarDatos();
        //ArrayList<String> s = new ArrayList<String>();
        //int espacio = observableProducts.size();
        //for(int i = 0; i < espacio; i++) {
            //lo añadimos  ala lista s
            //s.add(domainProductoController.getNombreProductoPorId(observableProducts.get(i)));
        //}
        //SortedList<String> sortedData = new SortedList<String>();
        ordenarProductosView();
        ordenarRelacionesView();
    }

    private void ordenarRelacionesView() {
        SortedList<Pair<Integer, Integer>> sortedData = new SortedList<>(observableProductPairs);
        sortedData.setComparator((p1, p2) -> {
            String name1First = domainProductoController.getNombreProductoPorId(p1.getKey()) + domainProductoController.getNombreProductoPorId(p1.getValue());
            String name2First = domainProductoController.getNombreProductoPorId(p2.getKey()) + domainProductoController.getNombreProductoPorId(p2.getValue());
            int comparacion = name1First.compareToIgnoreCase(name2First);
            if(comparacion == 0) {
                String name1Second = domainProductoController.getNombreProductoPorId(p1.getValue()) + domainProductoController.getNombreProductoPorId(p1.getKey());
                String name2Second = domainProductoController.getNombreProductoPorId(p2.getValue()) + domainProductoController.getNombreProductoPorId(p2.getKey());
                comparacion = name1Second.compareToIgnoreCase(name2Second);
            }
            return comparacion;
        });
        relacionesView.setItems(sortedData);
    }
    public void ordenarProductosView() {
        SortedList<Integer> sortedData = new SortedList<>(observableProducts);
        sortedData.setComparator((id1, id2) -> {
            String name1 = domainProductoController.getNombreProductoPorId(id1);
            String name2 = domainProductoController.getNombreProductoPorId(id2);
            return name1.compareToIgnoreCase(name2);
        });
        productosView.setItems(sortedData);
    }
    @FXML
    protected void onIntercambiarProductos() throws IOException {
        abrirVentana("Intercambiar Productos", "intercambiar-productos-view.fxml");
        //actualizarSolucion();
        solucionView.refresh();
    }

    public static boolean existeProductoConDiferenteID(int id, String nombre) {
        return domainProductoController.existeProductoConDiferenteID(id, nombre);
    }

    public static void eliminarProducto(int id) {
        domainProductoController.eliminarProductoPorId(id);
    }

    public static void cambiarNombreProducto(int id, String newName) {
        domainProductoController.cambiarNombreProducto(id, newName);
    }

    public static double getSinergia(int id1, int id2) {
        return domainProductoController.getSinergias(id1, id2);
    }

    public static void setSinergias(int id1, int id2, double sinergia) {
        domainProductoController.setSinergias(id1, id2, sinergia);
    }

    public static String getNombreProducto(int id) {
        return domainProductoController.getNombreProductoPorId(id);
    }

    public static double getPrecioProducto(int id) {
        return domainProductoController.getPrecioProductoPorId(id);
    }

    public static void setPrecioProducto(int id, double precio) {
        domainProductoController.setPrecioProductoPorId(id, precio);
    }

    public static ObservableList<Pair<String, Integer>[]> getSolutionProducts() {
        return observableSolutionProducts;
    }

    public static void setSolutionProducts(ObservableList<Pair<String, Integer>[]> p) {
        observableSolutionProducts = p;
    }

    public void onDeshacer() {
        DomainEstadoController.getInstance().deshacer();
        actualizarDatos();
        actualizarSolucion();
    }
}