//Class by Adria Cebrian Ruiz

//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero
package edu.upc.prop.clusterxx;

// no haría falta el extends si los cálculos de sinergias estuviesen en matriz de adyacencias
public class AlgoritmoOptimo extends Algoritmo {

    //public int contador = 0;
    //public double best_value = Double.NEGATIVE_INFINITY;

    public AlgoritmoOptimo(MatrizAdyacencia m) {
        super(m);
    }

    /**
     * configura una solución inicial para luego llamar un algoritmo recursivo que comprueba
     * todas las soluciones y se queda la mejor
     * @param s la solución de la que queremos encontrar la mejor solución
     * @return devuelve la mejor configuración
     */
    //@Override
    public Solucion ejecutar(Solucion s) {
        System.out.println("ejecutando_algoritmo...");
        int x = 0;
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                s.getDistribucion()[i][j] = -1;
            }
        }

        for (int i = 0; i < s.getDistribucion().length; ++i) {
            for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                if(x < matrizAdyacencia.getMatriz().length) {
                    s.getDistribucion()[i][j] = x;
                    ++x;
                }
            }
        }
        s.setCalidad(calcular_todas(s));
        s.imprimir_distribucion();
        Solucion resultado = recursive_calcular(s, 0, 0);
        calcular_todas(resultado);
        resultado.setCompletado(true);
        return resultado;
    }
    /**
     * calcula recursivamente todas las combinaciones partiendo de la solución y los parametros elegidos
     * @param s solución de la que se parte
     * @param y fila de la que se parte
     * @param x columna de la que se parte
     * @return la mejor combinacion partiendo de los valores seleccionados
     */
    public Solucion recursive_calcular(Solucion s, int y, int x) {
        s.setNumPasos(s.getNumPasos() + 1);
        //++contador;
        //System.out.println("Contador = " + contador);
        Solucion best_solution = s;
        if(y >= s.getDistribucion().length-1 && x >= s.getDistribucion()[0].length-1) return best_solution;
        else if(x == s.getDistribucion()[0].length-1) {
            x = 0;
            ++y;
        }
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                //System.out.println("Aquitoy");
                Solucion aux = copiar_solucion(s);

                //change_positions(aux, i, j, y, x);
                //aux.imprimir_distribucion();

                aux.intercambiar_productos(i, j, y, x);
                aux.setCalidad(calcular_todas(aux));
                //System.out.println("la calidad es:" + aux.getCalidad());
                //System.out.println("alternativa calidad es: " + calcular_todas(aux));
                //System.out.println("numero de pasos: " + aux.getNumPasos());
                //System.out.println("x: " + x + " y: " + y);
                aux = recursive_calcular(aux, y, x+1);
                if (aux.getCalidad() > best_solution.getCalidad()) best_solution = aux;
                else if(aux.getCalidad() == best_solution.getCalidad()) {
                    if(aux.getNumPasos() < best_solution.getNumPasos()) best_solution = aux;
                }
            }
        }
        return best_solution;
    }
}
