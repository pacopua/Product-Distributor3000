//Class by Adria Cebrian Ruiz

//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx.domain;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AlgoritmoRapido extends Algoritmo {
    private int dist_files = 0;
    private int dist_columnes = 0;

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
    public Solucion ejecutar(Solucion s, int intentos) { //, int semilla) {
        dist_files = s.getDistribucion().length;
        dist_columnes = s.getDistribucion()[0].length;

        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Future<Solucion>> futures = new ArrayList<>();
        //Random first_random = new Random(semilla);
        for (int t = 0; t < 8; ++t) {
            futures.add(executor.submit(() -> {
                //Random random = new Random(first_random.nextInt());
                Random random = new Random();
                Solucion best_solution = copiar_solucion(s);
                Solucion pruebas = copiar_solucion(s);
                for (int indice_intentos = 0; indice_intentos < intentos; ++indice_intentos) {
                    //System.out.println("Saludos");
                    for (int i = 0; i < dist_files; ++i) {
                        for (int j = 0; j < dist_columnes; ++j) {
                            pruebas.getDistribucion()[i][j] = -1;
                        }
                    }

                    for (int i = 0; i < matrizAdyacencia.getMatriz().length; ++i) {
                        int aux_i = random.nextInt(0, dist_files);
                        int aux_j = random.nextInt(0, dist_columnes);
                        while (pruebas.getDistribucion()[aux_i][aux_j] < i && pruebas.getDistribucion()[aux_i][aux_j] != -1) {
                            ++aux_j;
                            if (aux_j >= dist_columnes) {
                                aux_j = 0;
                                ++aux_i;
                            }
                            if (aux_i == dist_files) aux_i = 0;
                        }
                        pruebas.getDistribucion()[aux_i][aux_j] = i;
                    }

                    pruebas.setCalidad(calcular_todas(pruebas));
                    //System.out.println("Estoy aqui");
                    Solucion solucion_a_probar = hillClimbing(pruebas);
                    if (solucion_a_probar.getCalidad() >= best_solution.getCalidad()) {
                        if (solucion_a_probar.getCalidad() > best_solution.getCalidad()
                                || solucion_a_probar.getNumPasos() < best_solution.getNumPasos())
                            best_solution = solucion_a_probar;
                    }
                }
                //System.out.println("acabe");
                return best_solution;
            }));
        }
        Solucion best_solution = s;
        best_solution.setCalidad(-1);
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
        System.out.println("He acabado");
        best_solution.imprimir_distribucion();
        return best_solution;


    }

    /**
     * Algoritmo de aproximacion de una solución de hill climbing
     * @param currentSolution la solución que queremos mejorar
     * @return la mejor solución encontrada, con posibles minimos locales
     */
    private Solucion hillClimbing(Solucion currentSolution) {
        Solucion bestSolution = copiar_solucion(currentSolution);
        boolean improved = true;

        while (improved) {
            //System.out.println("Saludos desde el thread" + Thread.currentThread().getId());
            improved = false;

            for(int i = 0; i < dist_files; ++i) {
                for(int j = 0; j < dist_columnes; ++j) {
                    for(int y = i; y < dist_files; ++y) {
                        for(int x = j; x < dist_columnes; ++x) {
                            if(i == y && j == x) continue;
                            Solucion neighbor = copiar_solucion(currentSolution);
                            neighbor.intercambiar_productos(i, j, y, x);
                            neighbor.setCalidad(calcular_todas(neighbor));
                            neighbor.setNumPasos(neighbor.getNumPasos() + 1);

                            if(neighbor.getCalidad() >= bestSolution.getCalidad()) {
                                if(neighbor.getCalidad() > bestSolution.getCalidad()
                                        || neighbor.getNumPasos() < bestSolution.getNumPasos()) {
                                    bestSolution = neighbor;
                                    improved = true;
                                }
                            }
                        }
                    }
                }
            }
            currentSolution = bestSolution;
            System.out.println("Calidad: " + currentSolution.getCalidad());
            currentSolution.imprimir_distribucion();
        }


        return bestSolution;
    }
}