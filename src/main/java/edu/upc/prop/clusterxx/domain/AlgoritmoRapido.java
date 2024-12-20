//Class by Adria Cebrian Ruiz

//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx.domain;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// no haría falta el extends si los cálculos de sinergias estuviesen en matriz de adyacencias
public class AlgoritmoRapido extends Algoritmo {
    public AlgoritmoRapido(MatrizAdyacencia m) {
        super(m);
    }

    /**
     * Esta función inicializa de manera aleatoria una solución y luego llama a un algoritmo de ordenación aproximado
     * @param s instancia de solución que queremos ordenar
     * @param intentos numero de veces que se repite el algorismo (util para intentar no encontrar maximos locales)
     * @return una aproximación a una buena solución
     */
    //@Override
    public Solucion ejecutar(Solucion s, int intentos) {
        //System.out.println("ejecutando_algoritmo_hill_climbing...");

        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Future<Solucion>> futures = new ArrayList<>();

        for (int t = 0; t < 8; ++t) {
            futures.add(executor.submit(() -> {
                Random random = new Random();
                Solucion best_solution = copiar_solucion(s);
                for (int indice_intentos = 0; indice_intentos < intentos; ++indice_intentos) {

                    for (int i = 0; i < s.getDistribucion().length; ++i) {
                        for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                            s.getDistribucion()[i][j] = -1;
                        }
                    }

                    //int x = 0;
                    //boolean b[] = new boolean[MatrizAdyacencia.getMatriz().length];
                    for (int i = 0; i < matrizAdyacencia.getMatriz().length; ++i) {
                        int aux_i = random.nextInt(0, s.getDistribucion().length);
                        int aux_j = random.nextInt(0, s.getDistribucion()[0].length);
                        while (s.getDistribucion()[aux_i][aux_j] < i && s.getDistribucion()[aux_i][aux_j] != -1) {
                            //aux_i = random.nextInt(0, s.getDistribucion().length - 1);
                            //aux_j = random.nextInt(0, s.getDistribucion()[0].length - 1);
                            ++aux_j;
                            if (aux_j >= s.getDistribucion()[0].length) {
                                aux_j = 0;
                                ++aux_i;
                                if (aux_i == s.getDistribucion().length) aux_i = 0;
                            }
                        }
                        s.getDistribucion()[aux_i][aux_j] = i;
                    }

                    s.setCalidad(calcular_todas(s));
                    //System.out.println("Calidad inicial: " + s.getCalidad());

                    Solucion solucion_a_probar = hillClimbing(s);
                    if (solucion_a_probar.getCalidad() >= best_solution.getCalidad()) {
                        if (solucion_a_probar.getCalidad() > best_solution.getCalidad()
                                || solucion_a_probar.getNumPasos() < best_solution.getNumPasos())
                            best_solution = solucion_a_probar;
                    }
                }
                System.out.println("una calidad: " + best_solution.getCalidad());
                return best_solution;
            }));
        }
        //System.out.println("mejor calidad: " + best_solution.getCalidad());
        Solucion best_solution = s;
        for (Future<Solucion> future : futures) {
            try {
                Solucion result = future.get();
                if (result.getCalidad() > best_solution.getCalidad()) {
                    best_solution = result;
                } else if (result.getCalidad() == best_solution.getCalidad()) {
                    if (result.getNumPasos() < best_solution.getNumPasos()) {
                        best_solution = result;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        best_solution.setCompletado(true);
        return best_solution;


    }

    /**
     * Algoritmo de aproximacion de una solución de hill climbing
     * @param currentSolution la solución que queremos mejorar
     * @return la mejor solución encontrada, con posibles minimos locales
     */
    private Solucion hillClimbing(Solucion currentSolution) {
        Solucion bestSolution = copiar_solucion(currentSolution);
        //int iterations = 0;
        //int attemptsWithoutImprovement = 0;
        boolean improved = true;

        while (improved) {//iterations < MAX_ITERATIONS && attemptsWithoutImprovement < MAX_ATTEMPTS) {
            improved = false;
            //++iterations;
            //Solucion base_solution = copiar_solucion(currentSolution);
            for(int i = 0; i < currentSolution.getDistribucion().length; ++i) {
                for(int j = 0; j < currentSolution.getDistribucion()[0].length; ++j) {
                    for(int y = 0; y < currentSolution.getDistribucion().length; ++y) {
                        for(int x = 0; x < currentSolution.getDistribucion()[0].length; ++x) {
                            if(i == y && j == x) continue; //Saltar posición

                            Solucion neighbor = copiar_solucion(currentSolution);
                            neighbor.intercambiar_productos(i, j, y, x);
                            neighbor.setCalidad(calcular_todas(neighbor));
                            neighbor.setNumPasos(neighbor.getNumPasos() + 1);
                            //System.out.println(neighbor.getNumPasos());
                            if(neighbor.getCalidad() >= bestSolution.getCalidad()) {
                                if(neighbor.getCalidad() > bestSolution.getCalidad()
                                        || neighbor.getNumPasos() < bestSolution.getNumPasos()) {
                                    bestSolution = neighbor;
                                    improved = true;
                                    //attemptsWithoutImprovement = 0;
                                    //System.out.println("Mejora encontrada! Nueva calidad: " + neighbor.getCalidad());
                                }
                            }
                        }
                    }
                }
            }
            currentSolution = bestSolution;
            /*
            if(!improved) {
                ++attemptsWithoutImprovement;
            }

             */

            //System.out.println("Iteración " + iterations +
                    //", Calidad actual: " + bestSolution.getCalidad() +
                    //", Intentos sin mejora: " + attemptsWithoutImprovement);
        }

        //System.out.println("Hill Climbing terminado después de " + iterations + " iteraciones");
        //System.out.println("Calidad final: " + bestSolution.getCalidad());

        return bestSolution;
    }
}