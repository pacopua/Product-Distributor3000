//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

/*
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
*/

import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");



    Solucion s = new Solucion(9, 9);
    MatrizAdyacencia m = new MatrizAdyacencia(4);
    double [][] matrix = {
            { 0, 1, 1, 1 },
            { 1, 0, 1, 1 },
            { 1, 1, 0, 1 },
            { 1, 1, 1, 0 }
    };
    //MatrizAdyacencia.matriz = matrix;   <- no entiendo esto, entonces para qué está "m"?

    /* TODO: creo que estaría bien que biel implemente una función que copie una matriz de doubles a una instancia
        de matriz de adyacencia, así no habría que modificar cada sinergia una por una*/
    for(int i = 0; i < matrix.length; i++) {
      for(int j = 0; j < matrix[i].length; j++) {
        m.modificar_sinergias(i, j, matrix[i][j]);
      }
    }

    //Solucion adecuada = new Solucion();
    //for(int i = 0; i < 10; ++i) {
      AlgoritmoRapido a = new AlgoritmoRapido(m);
      Solucion x = a.ejecutar(s, 30);
      System.out.println("solución final: ");
      x.imprimir_distribucion();
      System.out.println("calidad: " + x.getCalidad());
      System.out.println("numPasos: " + x.getNumPasos());
      //if(x.getCalidad() > adecuada.getCalidad()) adecuada = x;
    //}

    //System.out.println("solución definitiva: ");
    //adecuada.imprimir_distribucion();
    //System.out.println("calidad: " + adecuada.getCalidad());



    new Gson();
    Main divisioner = new Main();
    System.out.println("Dividing 10 by 2 is " + divisioner.division(10,2));

  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}