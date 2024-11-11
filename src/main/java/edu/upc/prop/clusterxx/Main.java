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
    new Gson();

    Main divisioner = new Main();
    System.out.println("Dividing 10 by 2 is " + divisioner.division(10,2));
  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}