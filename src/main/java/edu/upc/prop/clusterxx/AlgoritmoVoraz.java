//Class by Adria Cebrian Ruiz
package src.main.java.edu.upc.prop.clusterxx;



public class AlgoritmoVoraz implements Algoritmo {

    public int contador = 0;
    public double best_value = Double.NEGATIVE_INFINITY;

    @Override
    public Solucion ejecutar(Solucion s) {
        System.out.println("ejecutando_algoritmo...");
        int x = 0;
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                s.getDistribucion()[i][j] = -1;
            }
        }

        for (int i = 0; i < s.getDistribucion().length; ++i) {
            for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                if(x < MatrizAdyacencia.getMatriz().length) {
                    s.getDistribucion()[i][j] = x;
                    s.setCalidad(s.getCalidad() + calcular_sinergias(s, i, j));
                    ++x;
                }
            }
        }
        s.imprimir_distribucion();
        Solucion xD = recursive_calcular(s, 0, 0);
        recalcular(xD);
        return xD;
    }

    public void recalcular(Solucion s) {
        double calidad = 0;
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                calidad += calcular_sinergias(s, i, j);
            }
        }
        s.setCalidad(calidad);
    }

    public Solucion calcular_solucion(Solucion s) {
        Solucion best_solution = copiar_solucion(s);
        for (int i = 0; i < s.getDistribucion().length; ++i) {
            for (int j = 0; j < s.getDistribucion()[0].length; ++j) {
                best_solution = recursive_calcular(best_solution, i, j);
            }
        }
        return best_solution;
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
            //suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i-1][j]);
        }
        if(j < s.getDistribucion()[0].length - 1) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i][j+1]);
        }
        if (j > 0) {
            suma += MatrizAdyacencia.getSinergia(s.getDistribucion()[i][j], s.getDistribucion()[i][j-1]);
        }
        return suma;
    }

    public void change_positions(Solucion s, int i, int j, int y, int x) {
        //s.setCalidad(s.getCalidad()-calcular_sinergias(s, i, j));
        //s.setCalidad(s.getCalidad()-calcular_sinergias(s, y, x));
        s.intercambiar_productos(i, j, y, x);
        //s.setCalidad(s.getCalidad()+calcular_sinergias(s, i, j));
        //s.setCalidad(s.getCalidad()+calcular_sinergias(s, y, x));
    }

    public Solucion recursive_calcular(Solucion s, int y, int x) {
        ++contador;
        System.out.println("Contador = " + contador);
        Solucion best_solution = s;
        if(y >= s.getDistribucion().length-1 && x >= s.getDistribucion()[0].length-1) return best_solution;
        else if(x == s.getDistribucion()[0].length-1) {
            x = 0;
            ++y;
        }
        for(int i = 0; i < s.getDistribucion().length; ++i) {
            for(int j = 0; j < s.getDistribucion()[0].length; ++j) {
                //System.out.println("Aquitoy");
                Solucion aux = copiar_solucion(s);
                aux.intercambiar_productos(i, j, y, x);
                //aux.imprimir_distribucion();
                aux.setCalidad(calcular_todas(aux));
                //System.out.println("la calidad es:" + aux.getCalidad());
                System.out.println("alternativa calidad es: " + calcular_todas(aux));
                System.out.println("x: " + x + " y: " + y);
                aux = recursive_calcular(aux, y, x+1);
                if (aux.getCalidad() > best_solution.getCalidad()) best_solution = aux;
            }
        }
        return best_solution;
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
