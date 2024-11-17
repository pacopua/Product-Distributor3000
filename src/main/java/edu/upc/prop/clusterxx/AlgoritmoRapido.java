//Class by Adria Cebrian Ruiz

//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import java.util.Random;

public class AlgoritmoRapido extends Algoritmo {
    private int contador = 0;
    private final int MAX_ITERATIONS = Integer.MAX_VALUE;
    private final int MAX_ATTEMPTS = 10;

    /**
     * Esta función inicializa de manera aleatoria una solución y luego llama a un algoritmo de ordenación aproximado
     * @param s instancia de solución que queremos ordenar
     * @param intentos numero de veces que se repite el algorismo (util para intentar no encontrar maximos locales)
     * @return una aproximación a una buena solución
     */
    //@Override
    public Solucion ejecutar(Solucion s, int intentos) {
        System.out.println("ejecutando_algoritmo_hill_climbing...");
        Random random = new Random();
        Solucion solucion_a_probar;
        Solucion best_solution = s;
        for (int indice_intentos = 0; indice_intentos < intentos; ++indice_intentos) {

            for (int i = 0; i < s.getDistribucion().length; ++i) {
                for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                    s.getDistribucion()[i][j] = -1;
                }
            }

            //int x = 0;
            //boolean b[] = new boolean[MatrizAdyacencia.getMatriz().length];
            for (int i = 0; i < MatrizAdyacencia.getMatriz().length; ++i) {
                int aux_i = random.nextInt(0, s.getDistribucion().length - 1);
                int aux_j = random.nextInt(0, s.getDistribucion()[0].length - 1);
                while (s.getDistribucion()[aux_i][aux_j] < i && s.getDistribucion()[aux_i][aux_j] != -1) {
                    aux_i = random.nextInt(0, s.getDistribucion().length - 1);
                    aux_j = random.nextInt(0, s.getDistribucion()[0].length - 1);
                }
                s.getDistribucion()[aux_i][aux_j] = i;
            }

            s.setCalidad(calcular_todas(s));
            System.out.println("Calidad inicial: " + s.getCalidad());

            solucion_a_probar = hillClimbing(s);
            if(solucion_a_probar.getCalidad() >= best_solution.getCalidad()) {
                if(solucion_a_probar.getCalidad() > best_solution.getCalidad()
                        || solucion_a_probar.getNumPasos() < best_solution.getNumPasos())
                    best_solution = solucion_a_probar;
            }
        }
        return best_solution;
    }

    /**
     * Algoritmo de aproximacion de una solución de hill climbing
     * @param currentSolution la solución que queremos mejorar
     * @return la mejor solución encontrada, con posibles minimos locales
     */
    private Solucion hillClimbing(Solucion currentSolution) {
        Solucion bestSolution = copiar_solucion(currentSolution);
        int iterations = 0;
        int attemptsWithoutImprovement = 0;
        boolean improved = true;

        while (improved) {//iterations < MAX_ITERATIONS && attemptsWithoutImprovement < MAX_ATTEMPTS) {
            improved = false;
            ++iterations;
            //Solucion base_solution = copiar_solucion(currentSolution);
            for(int i = 0; i < currentSolution.getDistribucion().length; ++i) {
                for(int j = 0; j < currentSolution.getDistribucion()[0].length; ++j) {
                    for(int y = 0; y < currentSolution.getDistribucion().length; ++y) {
                        for(int x = 0; x < currentSolution.getDistribucion()[0].length; ++x) {
                            if(i == y && j == x) continue; //Saltar posición

                            Solucion neighbor = copiar_solucion(currentSolution);
                            neighbor.intercambiar_productos(i, j, y, x);
                            neighbor.setCalidad(calcular_todas(neighbor));

                            if(neighbor.getCalidad() > bestSolution.getCalidad()) {
                                bestSolution = neighbor;
                                //currentSolution = neighbor;
                                improved = true;
                                //attemptsWithoutImprovement = 0;
                                //System.out.println("Mejora encontrada! Nueva calidad: " + neighbor.getCalidad());
                            }
                        }
                    }
                }
            }
            currentSolution = bestSolution;

            if(!improved) {
                ++attemptsWithoutImprovement;
            }

            System.out.println("Iteración " + iterations +
                    ", Calidad actual: " + bestSolution.getCalidad() +
                    ", Intentos sin mejora: " + attemptsWithoutImprovement);
        }

        System.out.println("Hill Climbing terminado después de " + iterations + " iteraciones");
        System.out.println("Calidad final: " + bestSolution.getCalidad());

        return bestSolution;
    }
}