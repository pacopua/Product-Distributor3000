//package src.main.java.edu.upc.prop.clusterxx;   <- marcad src como root para no poner el path entero -Marcel
package edu.upc.prop.clusterxx;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
//import src.main.java.edu.upc.prop.clusterxx.Main; <- para qué? -Marcel

public class TestDivisionShould {
  @Test
  public void divideGettingCorrectResult() {
    Main divisionMachine = new Main();

    float actualResult = divisionMachine.division(10,2);
    assertEquals(5.0, actualResult, 0.0001);
  }

  @Test(expected = ArithmeticException.class)
  public void divideByZeroThrowsException() {
    Main divisionMachine = new Main();

    float actualResult = divisionMachine.division(10,0);

  }
}