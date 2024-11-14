//Class by Adria Cebrian Ruiz
package src.main.java.edu.upc.prop.clusterxx;

public class AlgoritmoVoraz implements Algoritmo {

    @Override
    public void ejecutar() {

    }

    public Solucion calcular_solucion(Solucion s) {
        Solucion best_solution = copiar_solucion(s);
        for (int i = 0; i < s.distribucion.length; ++i) {
            for (int j = 0; j < s.distribucion[0].length; ++j) {
                best_solution = recursive_calcular(best_solution, i, j);
            }
        }
        return best_solution;
    }

    public double calcular_sinergias(Solucion s, int i, int j) {
        double suma = 0;
        if(i > 0) {
            suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[i-1][j]);
        }
        if(i < s.distribucion.length - 1) {
            suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[i+1][j]);
            if(j == 0)
                if(i%2 != 0) suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[i+1][j]);
            if(j == s.distribucion[0].length - 1)
                if(i%2 == 0) suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[i+1][j]);
        }
        if(i == 0 && j == 0) {
            suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[s.distribucion.length-1][s.distribucion[0].length-1]);
        }
        else if(i == s.distribucion.length-1 && j == s.distribucion[0].length-1) {
            suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[0][0]);
        }
        if(j < s.distribucion[0].length - 1) {
            suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[i][j+1]);
        }
        if (j > 0) {
            suma += MatrizAdyacencia.getSinergia(s.distribucion[i][j], s.distribucion[i][j-1]);
        }
        return suma;
    }

    public void change_positions(Solucion s, int i, int j, int y, int x) {
        s.calidad -= calcular_sinergias(s, i, j);
        s.calidad -= calcular_sinergias(s, y, x);
        s.intercambiarProductos()
    }

    public Solucion recursive_calcular(Solucion s, int y, int x) {
        Solucion best_solution = copiar_solucion(s);
        if(y == s.distribucion.length-1 && x == s.distribucion[0].length-1) return best_solution;
        else if(x == s.distribucion[0].length-1) {
            x = 0;
            ++y;
        }
        for(int i = 0; i < s.distribucion.length; ++i) {
            for(int j = 0; j < s.distribucion[0].length; ++j) {
                Solucion aux = copiar_solucion(s);
                change_positions(s, y, x, i, j);
                recursive_calcular(aux, y, x+1);
                if (aux.calidad > best_solution.calidad) best_solution = aux;
            }
        }
        return best_solution;
    }

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


}
