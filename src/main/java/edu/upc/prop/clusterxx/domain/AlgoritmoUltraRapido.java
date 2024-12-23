//Class by Marcel Alabart Benoit

//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx.domain;

import java.util.Random;

/**
 * Clase que implementa el algoritmo Simulated Annealing
 */
public class AlgoritmoUltraRapido extends Algoritmo {
    /**
     * Numero de pasos
     */
    int numPasos;
    /**
     * Numero de iteraciones para bajar la temperatura
     */
    int stiter;
    /**
     * Coeficiente lineal
     */
    double k;
    /**
     * Coeficiente exponencial
     */
    double lambda;

    /**
     * Variable para indicar si se ha solicitado detener la ejecución
     */
    private volatile boolean stopRequested = false;

    /**
     * Metodo para detener la ejecución del algoritmo
     */
    public void stopExecution() {
        stopRequested = true;
    }


    /**
     * Temperatura inicial
     */
    final float T0 = 1000;

    /**
     * Tasa de enfriamiento
     */
    final float coolingRate = 0.003f;



    /**
     * Constructor de la clase AlgoritmoSA
     * @param m matriz de adyacencia
     * @param numPasos número de pasos
     * @param stiter número de iteraciones para bajar la temperatura
     * @param k coeficiente lineal
     * @param lambda coeficiente exponencial
     */
    public AlgoritmoUltraRapido(MatrizAdyacencia m, int numPasos, int stiter, double k, double lambda) {
        super(m);
        this.numPasos = numPasos;
        this.stiter = stiter;
        this.k = k;
        this.lambda = lambda;
    }

    /**
     * Constructor de la clase AlgoritmoSA con parámetros por defecto
     * @param m matriz de adyacencia
     */
    public AlgoritmoUltraRapido(MatrizAdyacencia m) {
        this(m, 10000, 1, 25, 0.001);
        // parámetros por defecto obtenidos de la experimentación previa
    }

    /**
     * Esta función ejecuta el algoritmo Simulated Annealing con una semilla aleatoria
     * @param s instancia de solución que queremos ordenar
     * @param intentos numero de veces que se repite el algorismo ya que es un algoritmo estocástico
     *                 y puede caer en máximos locales aunque esté diseñado para evitarlos
     * @return una aproximación a una buena solución
     */
    public Solucion ejecutar(Solucion s, int intentos) {
        Random r = new Random();
        return ejecutar(s, intentos, r.nextLong());
    }

    /**
     * Esta función ejecuta el algoritmo Simulated Annealing con una semilla determinada
     * @param s instancia de solución que queremos ordenar
     * @param intentos numero de veces que se repite el algorismo ya que es un algoritmo estocástico
     *                 y puede caer en máximos locales aunque esté diseñado para evitarlos
     * @return una aproximación a una buena solución
     */
    public Solucion ejecutar(Solucion s, int intentos, long seed) {
        Solucion bestSolution = s;
        for (int attempt = 0; attempt < intentos; ++attempt) {
            if(stopRequested) return bestSolution;
            initializeSolution(s);
            s.setCalidad(calcular_todas(s));
            Solucion candidateSolution = simulatedAnnealing(s, seed);
            if (isBetterSolution(candidateSolution, bestSolution)) {
                bestSolution = candidateSolution;
            }
        }
        if(!stopRequested)bestSolution.setCompletado(true);
        else {
            System.out.println("Algoritmo detenido");
        }

        bestSolution.setNumPasos(numPasos);
        return bestSolution;
    }

    /**
     * Inicializa una solución de manera aleatoria
     * @param s la solución a inicializar
     */
    private void initializeSolution(Solucion s) {
        Random random = new Random();
        for (int i = 0; i < s.getDistribucion().length; ++i) {
            for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                s.getDistribucion()[i][j] = -1;
            }
        }
        for (int i = 0; i < matrizAdyacencia.getMatriz().length; ++i) {
            if(stopRequested) return;
            int aux_i = random.nextInt(0, s.getDistribucion().length);
            int aux_j = random.nextInt(0, s.getDistribucion()[0].length);
            while (s.getDistribucion()[aux_i][aux_j] < i && s.getDistribucion()[aux_i][aux_j] != -1) {
                ++aux_j;
                if(aux_j >= s.getDistribucion()[0].length) {
                    aux_j = 0;
                    ++aux_i;
                    if(aux_i == s.getDistribucion().length) aux_i = 0;
                }
            }
            s.getDistribucion()[aux_i][aux_j] = i;
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
    private Solucion simulatedAnnealing(Solucion currentSolution, long seed) {
        Solucion bestSolution = copiar_solucion(currentSolution);
        Solucion current = copiar_solucion(currentSolution);
        double temperature = T0;
        Random random = new Random(seed);

        while (current.getNumPasos() < numPasos) {
            if(stopRequested) return bestSolution;
            Solucion neighbor = copiar_solucion(current);
            int i1 = random.nextInt(current.getDistribucion().length);
            int j1 = random.nextInt(current.getDistribucion()[0].length);
            int i2 = random.nextInt(current.getDistribucion().length);
            int j2 = random.nextInt(current.getDistribucion()[0].length);
            neighbor.intercambiar_productos(i1, j1, i2, j2);
            neighbor.setCalidad(calcular_todas(neighbor));
            neighbor.setNumPasos(neighbor.getNumPasos() + 1);
            actualizarProgreso(neighbor.getNumPasos());

            double currentEnergy = current.getCalidad();
            double neighborEnergy = neighbor.getCalidad();

            if (acceptanceProbability(currentEnergy, neighborEnergy, temperature) > random.nextDouble()) {
                current = neighbor;
            }

            if (current.getCalidad() > bestSolution.getCalidad()) {
                bestSolution = current;
            }

            if (current.getNumPasos() % stiter == 0) temperature *= 1 - coolingRate;
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
        double F = k * Math.exp(-lambda * temperature);
        return Math.exp((neighborEnergy - currentEnergy) / F);
    }

    private void actualizarProgreso(double progreso) {
        actualizarProgreso(progreso, numPasos);
    }
}