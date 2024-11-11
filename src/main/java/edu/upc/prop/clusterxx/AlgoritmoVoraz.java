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

    public void change_positions(Solucion s, int i, int j, int y, int x) {
        if(i != 0 || i != (s.distribucion.length - 1)) {

            if (j != 0 || i != (s.distribucion[0].length - 1)) {
                s.calidad -= s.distribucion[i][j] * s.distribucion[i-1][j] + s.distribucion[i][j] * s.distribucion[i][j-1] + s.distribucion[i][j] * s.distribucion[i+1][j] + s.distribucion[i][j] * s.distribucion[i][j+1];
                s.calidad += s.distribucion[y][x] * s.distribucion[i-1][j] + s.distribucion[y][x] * s.distribucion[i][j-1] + s.distribucion[y][x] * s.distribucion[i+1][j] + s.distribucion[y][x] * s.distribucion[i][j+1];
            }
            else {
                if(i % 2 == 0);
            }
        }
        if(y != 0 || y != (s.distribucion.length - 1)) {
            if (x != 0 || x != (s.distribucion[0].length - 1)) {
                s.calidad -= s.distribucion[y][x] * s.distribucion[y-1][x] + s.distribucion[y][x] * s.distribucion[y][x-1] + s.distribucion[y][x] * s.distribucion[y+1][x] + s.distribucion[y][x] * s.distribucion[y][x+1];
                s.calidad += s.distribucion[i][j] * s.distribucion[y-1][x] + s.distribucion[i][j] * s.distribucion[y][x-1] + s.distribucion[i][j] * s.distribucion[y+1][x] + s.distribucion[i][j] * s.distribucion[y][x+1];
            }
        }

    }

    public Solucion recursive_calcular(Solucion s, int y, int x) {
        Solucion best_solution = copiar_solucion(s);
        if(y == s.distribucion.length-1 && x == s.distribucion[0].length-1) return best_solution;
        for(int i = 0; i < s.distribucion.length; ++i) {
            for(int j = 0; j < s.distribucion[0].length; ++j) {
                Solucion aux = recursive_calcular(s, )
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
