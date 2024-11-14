package edu.upc.prop.clusterxx;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Solucion {
    private int[][] distribucion;
    private double calidad;
    private int numPasos;
    private int filas;
    private int columnas;

    /**
     * Constructor de la clase Solucion
     * @param files Número de filas de la solución
     * @param columnes Número de columnas de la solución
     */
    public Solucion(int files, int columnes) {
        this.calidad = 0;
        this.numPasos = 0;
        this.introducir_numero_columnas_i_filas(files, columnes);
    }

    /**
     * Constructor de la clase Solucion
     */
    public Solucion() {
        this.calidad = 0;
        this.numPasos = 0;
    }

    /**
     * Establece el número de filas y columnas de la solución
     * @param files Número de filas
     * @param columnes Número de columnas
     */
    public void introducir_numero_columnas_i_filas(int files, int columnes) {
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
                System.out.print(distribucion[i][j] + " ");
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
            0 <= fila_p2 && fila_p2 < filas && 0 <= columna_p2 && columna_p2 < columnas) {
            int aux = distribucion[fila_p1][columna_p1];
            distribucion[fila_p1][columna_p1] = distribucion[fila_p2][columna_p2];
            distribucion[fila_p2][columna_p2] = aux;
            return true;
        }
        return false;
    }

    /**
     * Importa una solución desde un fichero
     * @param path Ruta del fichero
     * @throws IOException Si el fichero no existe o está vacío
     */
    public void importar_solucion(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        if (lines.isEmpty()) {
            throw new IOException("Empty file");
        }

        int num_filas = lines.size();
        int num_columnas = lines.getFirst().split(",").length;
        if (num_filas != filas || num_columnas != columnas) {
            introducir_numero_columnas_i_filas(lines.size(), lines.getFirst().length());
        }

        for (int i = 0; i < num_filas; i++) {
            String[] values = lines.get(i).split(",");
            for (int j = 0; j < num_columnas; j++) {
                distribucion[i][j] = Integer.parseInt(values[j]);
            }
        }
    }

    /**
     * Exporta la solución a un fichero
     * @param path Ruta del fichero
     * @throws IOException Si no se puede escribir en el fichero
     */
    public void exportar_solucion(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new PrintWriter(path))) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    line.append(distribucion[i][j]);
                    if (j < columnas - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }

        }
    }

    /**
     * Devuelve la distribución de la solución
     * @return Matriz de enteros con la distribución de la solución
     */
    public int[][] getDistribucion() {
        return distribucion;
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

    @Override
    public String toString() {
        return "Solucion{" +
                "distribucion=" + Arrays.toString(distribucion) +
                ", calidad=" + calidad +
                ", num_pasos=" + numPasos +
                '}';
    }
}
