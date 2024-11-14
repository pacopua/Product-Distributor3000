package edu.upc.prop.clusterxx;


import java.io.*;
import java.util.ArrayList;

public class MatrizAdyacencia {
    private double[][] matriz;
    private int numProductos;

    /**
     * Constructor de la clase MatrizAdyacencia
     * @param n Número de productos
     */
    public MatrizAdyacencia(int n) {
        matriz = new double[n][n];
        numProductos = n;
    }

    /**
     * Modifica la sinergia entre dos productos
     * @param p1 Producto 1
     * @param p2 Producto 2
     * @param nueva_sinergia Nueva sinergia entre los productos
     * @return true si se ha podido modificar la sinergia, false en caso contrario
     */
    public boolean modificar_singergias(int p1, int p2, double nueva_sinergia) {
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            matriz[p1][p2] = nueva_sinergia;
            matriz[p2][p1] = nueva_sinergia;
            return true;
        }
        return false;
    }

    /**
     * Exporta la matriz de adyacencia a un fichero
     * @param path Ruta del fichero
     * @throws IOException Si no existe el fichero, no se puede leer el fichero o este está vacío
     */
    public void importar_matriz_relaciones(String path) throws IOException {
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

        int n = lines.size();
        if (n != numProductos) {
            matriz = new double[n][n];
            numProductos = n;
        }

        for (int i = 0; i < n; i++) {
            String[] values = lines.get(i).split(",");
            for (int j = 0; j < n; j++) {
                matriz[i][j] = Double.parseDouble(values[j]);
            }
        }
    }

    /**
     * Exporta la matriz de adyacencia a un fichero
     * @param path Ruta del fichero
     * @throws IOException Si no se puede escribir en el fichero
     */
    public void exportar_matriz_relaciones(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new PrintWriter(path))) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < numProductos; i++) {
                for (int j = 0; j < numProductos; j++) {
                    line.append(matriz[i][j]);
                    if (j < numProductos - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }

        }
    }

    /**
     * Obtiene la matriz de adyacencia
     * @return Matriz de adyacencia
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * Obtiene el número de productos
     * @return Número de productos
     */
    public int getNumProductos() {
        return numProductos;
    }
}
