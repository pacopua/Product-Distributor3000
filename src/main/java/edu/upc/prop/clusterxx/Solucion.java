package edu.upc.prop.clusterxx;

import java.io.Serializable;
import java.util.Arrays;

public class Solucion implements Serializable, Cloneable {
    private final ListaProductos productos;
    private int[][] distribucion;
    private double calidad;
    private int numPasos;
    private int filas;
    private int columnas;
    private boolean completado;

    /**
     * Constructor de la clase Solucion
     */
    public Solucion(ListaProductos productos) {
        this.calidad = 0;
        this.numPasos = 0;
        this.productos = productos.clone();
        completado = false;
    }

    /**
     * Constructor de la clase Solucion
     * @param files Número de filas de la solución
     * @param columnes Número de columnas de la solución
     */
    public Solucion(ListaProductos productos, int files, int columnes) throws IllegalArgumentException {
        this.calidad = 0;
        this.numPasos = 0;
        this.productos = productos.clone();
        this.introducir_numero_columnas_i_filas(files, columnes);
        completado = false;
    }

    /**
     * Constructor de la clase Solucion
     * @param matriz Matriz de enteros con la distribución de la solución
     */
    public Solucion(ListaProductos productos, int[][] matriz) throws IllegalArgumentException {
        if (matriz.length == 0) {
            throw new IllegalArgumentException("La matriz no puede estar vacía");
        }
        this.productos = productos.clone();
        this.calidad = 0;
        this.numPasos = 0;
        this.introducir_numero_columnas_i_filas(matriz.length, matriz[0].length);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                distribucion[i][j] = matriz[i][j];
            }
        }
        completado = false;
    }

    /**
     * Establece el número de filas y columnas de la solución
     * @param files Número de filas
     * @param columnes Número de columnas
     */
    public void introducir_numero_columnas_i_filas(int files, int columnes) throws IllegalArgumentException {
        if (productos.getCantidadProductos() > files * columnes) {
            throw new IllegalArgumentException("No hay suficientes productos para llenar la matriz");
        }
        this.distribucion = new int[files][columnes];
        this.filas = files;
        this.columnas = columnes;
    }

    /**
     * Imprime la distribución de la solución en la salida estándar
     */
    public void imprimir_distribucion() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(productos.getProducto(distribucion[i][j]) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Intercambia dos productos de la solución
     * @param fila_p1 Fila del primer producto
     * @param columna_p1 Columna del primer producto
     * @param fila_p2 Fila del segundo producto
     * @param columna_p2 Columna del segundo producto
     * @return true si se ha podido intercambiar los productos, false en caso contrario
     */
    public boolean intercambiar_productos(int fila_p1, int columna_p1, int fila_p2, int columna_p2) {
        if (0 <= fila_p1 && fila_p1 < filas && 0 <= columna_p1 && columna_p1 < columnas &&
            0 <= fila_p2 && fila_p2 < filas && 0 <= columna_p2 && columna_p2 < columnas &&
            distribucion[fila_p1][columna_p1] != distribucion[fila_p2][columna_p2]) {
            int aux = distribucion[fila_p1][columna_p1];
            distribucion[fila_p1][columna_p1] = distribucion[fila_p2][columna_p2];
            distribucion[fila_p2][columna_p2] = aux;
            return true;
        }
        return false;
    }

    /**
     * Busca la posicion de un producto en la solución
     * @param id ID del producto
     * @return Vector de tamaño 2 con la fila del producto en la posición 0 y la columna del producto en la posición 1
     */
    public int[] buscar_producto(int id) {
        int[] pos = new int[2];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (distribucion[i][j] == id) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return new int[] {-1, -1};
    }

    /**
     * Devuelve la distribución de la solución
     * @return Matriz de enteros con la distribución de la solución
     */
    public int[][] getDistribucion() {
        return distribucion;
    }

    /**
     * Devuelve la distribución de la solución con los IDs de los productos
     * @return Matriz de enteros con la distribución de la solución
     */
    public Producto[][] getDistribucionProductos() {
        Producto[][] distribucionProductos = new Producto[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                distribucionProductos[i][j] = productos.getProducto(distribucion[i][j]).orElse(null);
            }
        }
        return distribucionProductos;
    }

    /**
     * Establece la distribución de la solución
     * @param distribucion Matriz de enteros con la distribución de la solución
     */
    public void setDistribucion(int[][] distribucion) {
        this.distribucion = distribucion;
    }

    /**
     * Devuelve la calidad de la solución
     * @return Calidad de la solución
     */
    public double getCalidad() {
        return calidad;
    }

    /**
     * Establece la calidad de la solución
     * @param calidad Calidad de la solución
     */
    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }

    /**
     * Devuelve el número de pasos de la solución
     * @return Número de pasos de la solución
     */
    public int getNumPasos() {
        return numPasos;
    }

    /**
     * Establece el número de pasos de la solución
     * @param numPasos Número de pasos de la solución
     */
    public void setNumPasos(int numPasos) {
        this.numPasos = numPasos;
    }

    /**
     * Devuelve si la solución está completada
     * @return true si la solución está completada, false en caso contrario
     */
    public boolean estaCompletado() {
        return completado;
    }

    /**
     * Establece si la solución está completada
     * @param completado true si la solución está completada, false en caso contrario
     */
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    /**
     * Devuelve la lista de productos
     * @return Lista de productos
     */
    public ListaProductos getListaProductos() {
        return productos;
    }

    /**
     * Devuelve la copia de la solución
     * @return Copia de la solución
     * @throws CloneNotSupportedException Excepción lanzada si no se puede clonar la solución
     */
    @Override
    protected Solucion clone() throws CloneNotSupportedException {
        Solucion solucion = (Solucion) super.clone();
        // implement clone function
        solucion.introducir_numero_columnas_i_filas(filas, columnas);
        solucion.setCalidad(calidad);
        solucion.setNumPasos(numPasos);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                solucion.distribucion[i][j] = distribucion[i][j];
            }
        }
        return solucion;
    }

    /**
     * Compara dos objetos
     * @param obj Objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Solucion sol) {
            if (this.filas != sol.filas
                    || this.columnas != sol.columnas
                    || this.calidad != sol.calidad
                    || this.numPasos != sol.numPasos) {
                return false;
            }
            return Arrays.deepEquals(this.distribucion, sol.getDistribucion());
        }
        return super.equals(obj);
    }

    /**
     * Devuelve una cadena de caracteres con la información de la solución
     * @return Cadena de caracteres con la información de la solución
     */
    @Override
    public String toString() {
        return "Solucion{" +
                "distribucion=" + Arrays.toString(distribucion) +
                ", calidad=" + calidad +
                ", num_pasos=" + numPasos +
                '}';
    }
}
