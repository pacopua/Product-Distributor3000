//Class by Marcel Alabart Benoit

//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx.domain;

import java.util.Random;

public class AlgoritmoSA extends Algoritmo {
    public AlgoritmoSA(MatrizAdyacencia m) {
        super(m);
    }

    /**
     * Esta función ejecuta el algoritmo Simulated Annealing
     * @param s instancia de solución que queremos ordenar
     * @param intentos numero de veces que se repite el algorismo ya que es un algoritmo estocástico
     *                 y puede caer en máximos locales aunque esté diseñado para evitarlos
     * @return una aproximación a una buena solución
     */
    public Solucion ejecutar(Solucion s, int intentos) {
        Solucion bestSolution = s;
        System.out.println("ejecutando_algoritmo_simulated_annealing...");
        for (int attempt = 0; attempt < intentos; ++attempt) {
            initializeSolution(s);
            s.setCalidad(calcular_todas(s));
            Solucion candidateSolution = simulatedAnnealing(s);
            if (isBetterSolution(candidateSolution, bestSolution)) {
                bestSolution = candidateSolution;
            }
        }
        bestSolution.setCompletado(true);
        return bestSolution;
    }

    /**
     * Inicializa una solución de manera aleatoria
     * @param s la solución a inicializar
     */
    private void initializeSolution(Solucion s) {
        Random random = new Random();
        resetDistribution(s);
        for (int i = 0; i < matrizAdyacencia.getMatriz().length; ++i) {
            int row, col;
            do {
                row = random.nextInt(s.getDistribucion().length);
                col = random.nextInt(s.getDistribucion()[0].length);
            } while (s.getDistribucion()[row][col] != -1);
            s.getDistribucion()[row][col] = i;
        }
    }

    /**
     * Reinicia la distribución de la solución
     * @param s la solución a reiniciar
     */
    private void resetDistribution(Solucion s) {
        for (int i = 0; i < s.getDistribucion().length; ++i) {
            for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                s.getDistribucion()[i][j] = -1;
            }
        }
    }

    /**
     * Comprueba si una solución es mejor que otra
     * @param candidate la solución candidata
     * @param best la mejor solución
     * @return true si la solución candidata es mejor que la mejor solución
     */
    private boolean isBetterSolution(Solucion candidate, Solucion best) {
        return candidate.getCalidad() > best.getCalidad() ||
                (candidate.getCalidad() == best.getCalidad() && candidate.getNumPasos() < best.getNumPasos());
    }

    /**
     * Algoritmo de Simulated Annealing
     * @param currentSolution la solución actual
     * @return la mejor solución encontrada
     */
    private Solucion simulatedAnnealing(Solucion currentSolution) {
        Solucion bestSolution = copiar_solucion(currentSolution);
        Solucion current = copiar_solucion(currentSolution);
        double temperature = 1000.0;
        double coolingRate = 0.003;
        Random random = new Random();

        while (temperature > 1) {
            Solucion neighbor = copiar_solucion(current);
            int i1 = random.nextInt(current.getDistribucion().length);
            int j1 = random.nextInt(current.getDistribucion()[0].length);
            int i2 = random.nextInt(current.getDistribucion().length);
            int j2 = random.nextInt(current.getDistribucion()[0].length);
            neighbor.intercambiar_productos(i1, j1, i2, j2);
            neighbor.setCalidad(calcular_todas(neighbor));
            neighbor.setNumPasos(neighbor.getNumPasos() + 1);

            double currentEnergy = current.getCalidad();
            double neighborEnergy = neighbor.getCalidad();

            if (acceptanceProbability(currentEnergy, neighborEnergy, temperature) > random.nextDouble()) {
                current = neighbor;
            }

            if (current.getCalidad() > bestSolution.getCalidad()) {
                bestSolution = current;
            }

            temperature *= 1 - coolingRate;
        }

        return bestSolution;
    }

    /**
     * Calcula la probabilidad de aceptación para el Simulated Annealing
     * @param currentEnergy la calidad de la solución actual
     * @param neighborEnergy la calidad de la solución vecina
     * @param temperature la temperatura actual
     * @return la probabilidad de aceptación
     */
    private double acceptanceProbability(double currentEnergy, double neighborEnergy, double temperature) {
        if (neighborEnergy > currentEnergy) {
            return 1.0;
        }
        return Math.exp((neighborEnergy - currentEnergy) / temperature);
    }
}