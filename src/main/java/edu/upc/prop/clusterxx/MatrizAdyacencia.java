package src.main.java.edu.upc.prop.clusterxx;

public class MatrizAdyacencia {
    private static double[][] matriz;
    private static int numProductos;

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
     * Obtiene la sinergia entre dos productos
     * @param p1 Producto 1
     * @param p2 Producto 2
     * @return Sinergia entre los productos, -1 si no se ha podido obtener
     */
    public static double getSinergia(int p1, int p2) {
        if (0 <= p1 && p1 < numProductos && 0 <= p2 && p2 < numProductos) {
            return matriz[p1][p2];
        }
        return -1;
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
