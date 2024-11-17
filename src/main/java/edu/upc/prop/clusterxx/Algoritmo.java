//Class by Adria Cebrian Ruiz
package src.main.java.edu.upc.prop.clusterxx;

public class Algoritmo {

    //Solucion calcular_solucion(Solucion s);
    /**
     * Calcula la suma total de las sinergias en la solución dada usando la funcion "calcular_sinergias"
     * @param s la distribución cuyas sinergias queremos calcular
     * @return devuelve la suma total de las sinergias para la solución dada
     */
    public double calcular_todas(Solucion s) {
        double suma = 0;
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                suma += calcular_sinergias(s, i, j);
            }
        }
        return suma;
    }

    /**
     * Calcula, las sinergias que contribuye una posición deternimanda de la distribución
     * @param s la distribución en la que está ubicada
     * @param i la fila en que se encuentra el producto
     * @param j la columna en que se encuentra el producto
     * @return la calidad que suma el producto dada los que hay a su alrededor
     */
    public double calcular_sinergias(Solucion s, int i, int j) {
        double suma = 0;
        if(i > 0) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i-1][j]);
        }
        if(i < s.getDistribucion().length - 1) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i+1][j]);
            if(j == 0) {
                if (i % 2 != 0)
                    suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i + 1][j]);
                else if (i != 0)
                    suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i - 1][j]);
            }
            if(j == s.getDistribucion()[0].length - 1) {
                if (i % 2 == 0)
                    suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i + 1][j]);
                else if (i != 0)
                    suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i - 1][j]);
            }
        }
        if(i == 0 && j == 0) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[s.getDistribucion().length-1][s.getDistribucion()[0].length-1]);
        }
        else if(i == s.getDistribucion().length-1 && j == s.getDistribucion()[0].length-1) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[0][0]);
            if (i % 2 == 0)
                suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i - 1][j]);
        }
        if(j < s.getDistribucion()[0].length - 1) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i][j+1]);
        }
        if (j > 0) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i][j-1]);
        }
        return suma;
    }

    //Solucion recursive_calcular(Solucion s);

    /**
     * hace una hard copy de una solución
     * @param s la solución que queremos copiar
     * @return una solución con los mismos valores que la solución a copiar
     */
    public Solucion copiar_solucion(Solucion s) {
        Solucion best_solution = new Solucion(s.getDistribucion().length, s.getDistribucion()[0].length);
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                best_solution.getDistribucion()[i][j] = s.getDistribucion()[i][j];
            }
        }
        best_solution.setCalidad(s.getCalidad());
        best_solution.setNumPasos(s.getNumPasos());
        return best_solution;
    }
}


