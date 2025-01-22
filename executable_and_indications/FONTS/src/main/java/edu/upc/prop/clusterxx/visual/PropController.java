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

/**
 * Clase PropController
 * Controlador principal de la capa de presentación
 */
public class PropController {

    /**
     * Panel de pestañas
     */
    @FXML
    private TabPane pane;
    /**
     * Lista de productos
     */
    @FXML
    private ListView productosView;
    /**
     * Lista de relaciones
     */
    @FXML
    private ListView relacionesView;
    /**
     * Lista de solución
     */
    @FXML
    private TableView<Pair<String, Integer>[]> solucionView;
    /**
     * Etiqueta de la calidad de la solución
     */
    @FXML
    private Label calidad;
    /**
     * Etiqueta de los pasos de la solución
     */
    @FXML
    private Label pasos;
    /**
     * Botón para abrir el menú de intercambiar productos
     */
    @FXML
    private Button intercambiar;
    /**
     * Contenedor de la calidad de la solución
     */
    @FXML
    HBox calidadBox;
    /**
     * Contenedor de los pasos de la solución
     */
    @FXML
    HBox pasosBox;
    /**
     * Controlador de entrada/salida
     */
    private static IOController ioController = IOController.getInstance();
    /**
     * Controlador de productos
     */
    private static DomainProductoController domainProductoController = DomainProductoController.getInstance();
    /**
     * Controlador de soluciones
     */
    private static DomainSolucionController domainSolucionController = DomainSolucionController.getInstance();

    /**
     * Lista observable de productos
     */
    private static ObservableList<Integer> observableProducts = FXCollections.observableArrayList();
    /**
     * Lista observable de relaciones
     */
    private static ObservableList<Pair<Integer, Integer>> observableProductPairs = FXCollections.observableArrayList();
    /**
     * Lista observable de solución
     */
    private static ObservableList<Pair<String, Integer>[]> observableSolutionProducts = FXCollections.observableArrayList();

    /**
     * Botón de confirmación
     */
    ButtonType si = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
    /**
     * Botón de cancelación
     */
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    /**
     * Filtro de archivos de estado
     */
    FileChooser.ExtensionFilter stateFilter = new FileChooser.ExtensionFilter(
            "Archivo de estado",
            Arrays.asList("*.state")
    );
    /**
     * Filtro de archivos de productos
     */
    FileChooser.ExtensionFilter listFilter = new FileChooser.ExtensionFilter(
            "Archivo de productos",
            Arrays.asList("*.list")
    );

    /**
     * Método de inicialización
     */
    @FXML
    private void initialize() {
        productosView.setCellFactory(producto -> new ProductoCell(this));
        productosView.setItems(observableProducts);
        relacionesView.setCellFactory(productPair -> new RelacionCell());
        relacionesView.setItems(observableProductPairs);
        solucionView.setItems(observableSolutionProducts);
        DomainEstadoController.getInstance().actualizarHistorial();
    }

    /**
     * Método para mostrar una ventana de confirmación
     *
     * @param mensaje mensaje de la ventana
     * @param titulo  título de la ventana
     * @return true si se confirma, false si se cancela
     */
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

    /**
     * Método para actualizar la vista de la solución
     */
    private void ActualizarSolucionProductosVista() {
        if (domainSolucionController.is_complete()) {
            calidadBox.setDisable(false);
            pasosBox.setDisable(false);
            intercambiar.setDisable(false);
            calidad.setText(Double.toString(domainSolucionController.getCalidadSolucion()));
            pasos.setText(Integer.toString(domainSolucionController.getPasosSolucion()));
        }
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

        solucionView.getColumns().forEach(tableColumn -> {
            tableColumn.setSortable(false);
            tableColumn.setReorderable(false);
        });
        solucionView.setItems(observableSolutionProducts);
        solucionView.refresh();
    }

    /**
     * Método para mostrar una ventana de error de exportación
     */
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

    /**
     * Método para mostrar una ventana de error de archivo
     */
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

    /**
     * Método para mostrar una ventana de error interno de archivo
     */
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

    /**
     * Método para abrir un FileChooser
     * @param in true si es para abrir un archivo de entrada, false si es para abrir un archivo de salida
     * @param archivo nombre del archivo
     * @param filtro filtro del archivo
     * @return archivo seleccionado
     */
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

    /**
     * Método para intercambiar productos
     * @return true si se ha intercambiado, false si no
     */
    @FXML
    protected boolean onImportarEstado() {
        if (!ventanaConfirmar("Esta acción reemplazará los datos.\n¿Desea continuar?", "Importar estado")) return false;
        File in = abrirFileChooser(true, "data.state", stateFilter);
        if (in == null) return false;
        try {
            DomainEstadoController.getInstance().actualizarHistorial();
            ioController.importarEstado(in.getAbsolutePath());
            actualizarDatos();
            //actualizarSolucion();

            ordenarProductosView();
            ordenarRelacionesView();
            // Retrieve the list of products

            ActualizarSolucionProductosVista();
        } catch (IOException e) {
            ventanaErrorArchivo();
            return false;
        } catch (ClassNotFoundException e) {
            ventanaErrorInternoArchivo();
            return false;
        }
        return true;
    }

    /**
     * Método para exportar el estado
     * @return true si se ha exportado, false si no
     */
    @FXML
    protected boolean onExportarEstado() {
        File out = abrirFileChooser(false, "data.state", stateFilter);
        if (out == null) return false;
        try {
            ioController.exportarEstado(out.getAbsolutePath());
        } catch (IOException e) {
            ventanaErrorExportar();
            return false;
        }
        return true;
    }

    /**
     * Método para ordenar la vista de productos
     * @return true si se ha ordenado, false si no
     */
    @FXML
    protected boolean onImportarLista() {
        if (!ventanaConfirmar("Esta acción reemplazará los productos.\n¿Desea continuar?", "Importar Productos"))
            return false;
        ;
        File in = abrirFileChooser(true, "products.list", listFilter);
        if (in == null) return false;
        ;
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

    /**
     * Método para ordenar la vista de productos
     * @return true si se ha ordenado, false si no
     */
    @FXML
    protected boolean onExportarLista() {
        File out = abrirFileChooser(false, "products.list", listFilter);
        if (out == null) return false;
        try {
            ioController.exportarListaProductos(out.getAbsolutePath());
        } catch (IOException e) {
            ventanaErrorExportar();
            return false;
        }
        return true;
    }

    /**
     * Método para salir
     * @return true si se ha salido, false si no
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
            if (onExportarEstado()) {
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

    /**
     * Método para abrir una ventana
     * @param nombre nombre de la ventana
     * @param controlador controlador de la ventana
     */
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

    /**
     * Método para abrir una ventana para generar una solución
     */
    @FXML
    protected void onNuevaSolucion() throws IOException {
        if (domainSolucionController.is_complete())
            if (!ventanaConfirmar("Esta acción reemplazará la solución.\n¿Desea continuar?", "Reemplazar solución"))
                return;
        abrirVentana("Nueva Solución", "nueva-solucion-view.fxml");
        actualizarSolucion();
    }

    /**
     * Método para obtener un listado con los ids de los productos
     * @return ArrayList con los ids de los productos
     */
    private static ArrayList<Integer> getProductosIds() {
        return domainProductoController.getProductsIds();
    }

    /**
     * Método para actualizar los datos de la interficie gráfica
     */
    public static void actualizarDatos() {
        observableProducts.setAll(getProductosIds());
        ArrayList<Pair<Integer, Integer>> productPairs = domainProductoController.lista_sinergias();
        observableProductPairs.setAll(productPairs);
    }

    /**
     * Método para obtener la lista observable de productos
     * @return lista observable de productos
     */
    public ObservableList<Pair<String, Integer>[]> getObservableSolutionProducts() {
        return observableSolutionProducts;
    }

    /**
     * Método para obtener la lista observable de productos
     * @return lista observable de productos
     */
    public static ObservableList<String> getProductsIntercambio() {
        ObservableList<String> productNames = FXCollections.observableArrayList();
        for (Pair<String, Integer>[] row : observableSolutionProducts) {
            for (Pair<String, Integer> pair : row) {
                if (pair != null && pair.getKey() != null && !pair.getKey().isEmpty()) {
                    productNames.add(pair.getKey());
                }
            }
        }
        return productNames;
    }

    /**
     * Método para actualizar la solución
     */
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

            solucionView.getColumns().forEach(tableColumn -> {
                tableColumn.setSortable(false);
                tableColumn.setReorderable(false);
            });
            solucionView.setItems(observableSolutionProducts);
        }
    }

    /**
     * Método para actualizar la solución después de un intercambio
     * @param nombreP1 nombre del primer producto
     * @param nombreP2 nombre del segundo producto
     */
    public void actualizarAfterSwap(String nombreP1, String nombreP2) {
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

    /**
     * Método que se ejecuta al creaer un nuevo producto
     */
    @FXML
    protected void onNuevoProducto() throws IOException {
        abrirVentana("Nuevo Producto", "nuevo-producto-view.fxml");
        actualizarDatos();
        ordenarProductosView();
        ordenarRelacionesView();
    }

    /**
     * Método para ordenar la vista de relaciones
     */
    private void ordenarRelacionesView() {
        SortedList<Pair<Integer, Integer>> sortedData = new SortedList<>(observableProductPairs);
        sortedData.setComparator((p1, p2) -> {
            String name1First = domainProductoController.getNombreProductoPorId(p1.getKey()) + domainProductoController.getNombreProductoPorId(p1.getValue());
            String name2First = domainProductoController.getNombreProductoPorId(p2.getKey()) + domainProductoController.getNombreProductoPorId(p2.getValue());
            int comparacion = name1First.compareToIgnoreCase(name2First);
            if (comparacion == 0) {
                String name1Second = domainProductoController.getNombreProductoPorId(p1.getValue()) + domainProductoController.getNombreProductoPorId(p1.getKey());
                String name2Second = domainProductoController.getNombreProductoPorId(p2.getValue()) + domainProductoController.getNombreProductoPorId(p2.getKey());
                comparacion = name1Second.compareToIgnoreCase(name2Second);
            }
            return comparacion;
        });
        relacionesView.setItems(sortedData);
    }

    /**
     * Método para ordenar la vista de productos
     */
    public void ordenarProductosView() {
        SortedList<Integer> sortedData = new SortedList<>(observableProducts);
        sortedData.setComparator((id1, id2) -> {
            String name1 = domainProductoController.getNombreProductoPorId(id1);
            String name2 = domainProductoController.getNombreProductoPorId(id2);
            return name1.compareToIgnoreCase(name2);
        });
        productosView.setItems(sortedData);
    }

    /**
     * Método para abrir la ventana de intercambio de un producto
     */
    @FXML
    protected void onIntercambiarProductos() throws IOException {
        abrirVentana("Intercambiar Productos", "intercambiar-productos-view.fxml");
        solucionView.refresh();
    }

    /**
     * Método para saber si existe un producto con el mismo nombre y diferente id
     * @param id id del producto
     * @param nombre nombre del producto
     * @return true si existe, false si no
     */
    public static boolean existeProductoConDiferenteID(int id, String nombre) {
        return domainProductoController.existeProductoConDiferenteID(id, nombre);
    }

    /**
     * Método para eliminar un producto
     * @param id id del producto
     */
    public static void eliminarProducto(int id) {
        domainProductoController.eliminarProductoPorId(id);
    }

    /**
     * Método para cambiar el nombre de un producto
     * @param id id del producto
     * @param newName nuevo nombre del producto
     */
    public static void cambiarNombreProducto(int id, String newName) {
        domainProductoController.cambiarNombreProducto(id, newName);
    }

    /**
     * Método para obtener la sinergia entre dos productos
     * @param id1 id del producto
     * @param id2 id del producto
     * @return sinergia entre los productos
     */
    public static double getSinergia(int id1, int id2) {
        return domainProductoController.getSinergias(id1, id2);
    }

    /**
     * Método para establecer la sinergia entre dos productos
     * @param id1 id del producto 1
     * @param id2 id del producto 2
     * @param sinergia sinergia entre los productos
     */
    public static void setSinergias(int id1, int id2, double sinergia) {
        domainProductoController.setSinergias(id1, id2, sinergia);
    }

    /**
     * Método para obtener el nombre de un producto
     * @param id id del producto
     * @return nombre del producto
     */
    public static String getNombreProducto(int id) {
        return domainProductoController.getNombreProductoPorId(id);
    }

    /**
     * Método para obtener el precio de un producto
     * @param id id del producto
     * @return precio del producto
     */
    public static double getPrecioProducto(int id) {
        return domainProductoController.getPrecioProductoPorId(id);
    }

    /**
     * Método para establecer el precio de un producto
     * @param id id del producto
     * @param precio precio del producto
     */
    public static void setPrecioProducto(int id, double precio) {
        domainProductoController.setPrecioProductoPorId(id, precio);
    }

    /**
     * Método para obtener la lista de productos con su id
     * @return lista de productos con su id
     */
    public static ObservableList<Pair<String, Integer>[]> getSolutionProducts() {
        return observableSolutionProducts;
    }

    /**
     * Método para establecer la lista de productos de la solución
     * @param p lista de productos de la solución
     */
    public static void setSolutionProducts(ObservableList<Pair<String, Integer>[]> p) {
        observableSolutionProducts = p;
    }

    /**
     * Método para deshacer un cambio
     */
    public void onDeshacer() {
        DomainEstadoController.getInstance().deshacer();
        actualizarDatos();
        ActualizarSolucionProductosVista();
    }
}