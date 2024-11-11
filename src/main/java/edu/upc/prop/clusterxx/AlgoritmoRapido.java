//Class by Adria Cebrian Ruiz
package src.main.java.edu.upc.prop.clusterxx;

import aima.search.framework.*;
import java.util.*;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
public class AlgoritmoRapido implements Algoritmo{
    public void ejecutar() {

    }
    public Solucion calcular_solucion(Solucion s) {return s;}

    public Solucion copiar_solucion(Solucion s) {
        Solucion best_solution = new Solucion();
        best_solution.distribucion = new int[s.distribucion.length][s.distribucion[0].length];
        for(int i = 0; i < s.distribucion.length; ++i) {
            for(int j = 0; j < s.distribucion[0].length; ++j) {
                best_solution.distribucion[i][j] = s.distribucion[i][j];
            }
        }
        best_solution.calidad = s.calidad;
        best_solution.num_pasos = s.num_pasos;
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
            for(int i = 0; i < sol_actual.distribucion.length; ++i) {
                for(int j = 0; i < sol_actual.distribucion[0].length; ++j) {
                    for(int x = 0; x < sol_actual.distribucion.length; ++x) {
                        for(int y = 0; y < sol_actual.distribucion[0].length; ++y) {
                            Solucion newstate = copiar_solucion(sol_actual);
                            if(newstate.intercambiarProductos(i,j, x,y));
                            String S = "intercambiados producto: " + sol_actual.distribucion[i][j] + " por producto: "
                                    + sol_actual.distribucion[x][y];
                            retVal.add(new Successor(S, newstate));
                        }
                    }
                }
            }
            return retVal;
        }
    }
}


