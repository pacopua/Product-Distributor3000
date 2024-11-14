package src.main.java.edu.upc.prop.clusterxx;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");



    Solucion s = new Solucion(2, 2);
    MatrizAdyacencia m = new MatrizAdyacencia(4);
    double [][] matrix = {{0, 10, 15, 20},{10, 0, 35, 25},{15, 35, 0, 30},{20, 25, 30, 0}};
    MatrizAdyacencia.matriz = matrix;
    AlgoritmoVoraz a = new AlgoritmoVoraz();
    Solucion x = a.ejecutar(s);
    x.imprimirDistribucio();
    System.out.println("calidad" + x.getCalidad());



    new Gson();
    Main divisioner = new Main();
    System.out.println("Dividing 10 by 2 is " + divisioner.division(10,2));

  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}