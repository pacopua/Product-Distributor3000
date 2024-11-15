//Class by Adria Cebrian Ruiz

//Class by Adria Cebrian Ruiz
package src.main.java.edu.upc.prop.clusterxx;

import java.util.Random;

public class AlgoritmoRapido implements Algoritmo {
    private int contador = 0;
    private final int MAX_ITERATIONS = Integer.MAX_VALUE;
    private final int MAX_ATTEMPTS = 10;

    public Solucion calcular_solucion(Solucion s) {return s;}

    @Override
    public Solucion ejecutar(Solucion s) {
        System.out.println("ejecutando_algoritmo_hill_climbing...");
        Random random = new Random();

        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                s.getDistribucion()[i][j] = -1;
            }
        }

        //int x = 0;
        //boolean b[] = new boolean[MatrizAdyacencia.getMatriz().length];
        for(int i = 0; i < MatrizAdyacencia.getMatriz().length; ++i) {
            int aux_i = random.nextInt(0,s.getDistribucion().length-1);
            int aux_j = random.nextInt(0, s.getDistribucion()[0].length-1);
            while(s.getDistribucion()[aux_i][aux_j] < i && s.getDistribucion()[aux_i][aux_j] != -1) {
                aux_i = random.nextInt(0, s.getDistribucion().length - 1);
                aux_j = random.nextInt(0, s.getDistribucion()[0].length - 1);
            }
            s.getDistribucion()[aux_i][aux_j] = i;
        }

        s.setCalidad(calcular_todas(s));
        System.out.println("Calidad inicial: " + s.getCalidad());

        return hillClimbing(s);
    }

    private Solucion hillClimbing(Solucion currentSolution) {
        Solucion bestSolution = copiar_solucion(currentSolution);
        int iterations = 0;
        int attemptsWithoutImprovement = 0;

        while (iterations < MAX_ITERATIONS && attemptsWithoutImprovement < MAX_ATTEMPTS) {
            ++iterations;
            boolean improved = false;

            for(int i = 0; i < currentSolution.getDistribucion().length && !improved; ++i) {
                for(int j = 0; j < currentSolution.getDistribucion()[0].length && !improved; ++j) {
                    for(int y = 0; y < currentSolution.getDistribucion().length && !improved; ++y) {
                        for(int x = 0; x < currentSolution.getDistribucion()[0].length && !improved; ++x) {
                            if(i == y && j == x) continue; // Skip same position

                            Solucion neighbor = copiar_solucion(currentSolution);
                            neighbor.intercambiar_productos(i, j, y, x);
                            neighbor.setCalidad(calcular_todas(neighbor));

                            if(neighbor.getCalidad() > bestSolution.getCalidad()) {
                                bestSolution = neighbor;
                                currentSolution = neighbor;
                                improved = true;
                                attemptsWithoutImprovement = 0;
                                System.out.println("Mejora encontrada! Nueva calidad: " + neighbor.getCalidad());
                            }
                        }
                    }
                }
            }

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

    public double calcular_todas(Solucion s) {
        double suma = 0;
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                suma += calcular_sinergias(s, i, j);
            }
        }
        return suma;
    }

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



/*package src.main.java.edu.upc.prop.clusterxx;

import aima.search.framework.*;
import java.util.*;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
public class AlgoritmoRapido implements Algoritmo{
    public Solucion ejecutar(Solucion s) {
        return s;
    }
    public Solucion calcular_solucion(Solucion s) {return s;}

    public Solucion copiar_solucion(Solucion s) {
        Solucion best_solution = new Solucion();
        best_solution.setDistribucion(new int[s.getDistribucion().length][s.getDistribucion()[0].length]);
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                best_solution.getDistribucion()[i][j] = s.getDistribucion()[i][j];
            }
        }
        best_solution.setCalidad(s.getCalidad());;
        best_solution.setNumPasos(s.getNumPasos());
        return best_solution;
    }

    public class goal_test implements GoalTest{
        public boolean isGoalState(Object state){
            return false;
        }
    }

    public class HeuristicFunctionDist implements HeuristicFunction {
        public double getHeuristicValue(Object o) {
            Solucion s = (Solucion) o;
            return s.getCalidad();
        }
    }

    public class sucesor implements SuccessorFunction {
        public List<Successor> getSuccessors(Object a) {
            ArrayList<Successor> retVal = new ArrayList();
            Solucion sol_actual = (Solucion)a;
            for(int i = 0; i < sol_actual.getDistribucion().length; ++i) {
                for(int j = 0; i < sol_actual.getDistribucion()[0].length; ++j) {
                    for(int x = 0; x < sol_actual.getDistribucion().length; ++x) {
                        for(int y = 0; y < sol_actual.getDistribucion()[0].length; ++y) {
                            Solucion newstate = copiar_solucion(sol_actual);
                            if(newstate.intercambiar_productos(i,j, x,y));
                            String S = "intercambiados producto: " + sol_actual.getDistribucion()[i][j] + " por producto: "
                                    + sol_actual.getDistribucion()[x][y];
                            retVal.add(new Successor(S, newstate));
                        }
                    }
                }
            }
            return retVal;
        }
    }
}
 */


