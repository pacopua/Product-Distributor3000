package edu.upc.prop.clusterxx.domain;
import edu.upc.prop.clusterxx.PropApp;

import java.io.FileWriter;
import java.io.IOException;



public class AlgoritmoSAestudio {

    static String[] productos = {"Jamón", "Queso", "Pan", "Tomate", "Lechuga", "Mayonesa", "Ketchup", "Mostaza", "Detergente",
            "Suavizante", "Lejía", "Cerveza", "Vino", "Refresco", "Agua", "Café", "Té", "Azúcar", "Sal", "Pimienta",
            "Aceite", "Vinagre", "Pasta", "Arroz", "Patatas", "Cebollas", "Ajos", "Zanahorias", "Pimientos", "Calabacines",};
    final static int SAMPLE_AMOUNT = 100;


    public static void main(String[] args) {

        ListaProductos listaProductos = new ListaProductos(); // Initialize as needed
        for (String producto : productos) {
            Producto p = new Producto(producto, 1.0);
            listaProductos.addProducto(p);
        }

        double[][] sinergiasEntreProductos = new double[productos.length][productos.length];

        for (int i = 0; i < productos.length; i++) {
            for (int j = 0; j < productos.length; j++) {
                sinergiasEntreProductos[i][j] = Math.abs(i - j) * 3;
            }
        }

        MatrizAdyacencia matriz = new MatrizAdyacencia(sinergiasEntreProductos); // Initialize as needed
        Solucion solucion = new Solucion(listaProductos, 6, 6); // Initialize as needed

        int steps = 25000;
        // 100: 4797, 1000: 5120, 10000: 5150, 25000: 5154, 50000: 5148 -> 25000 steps

        int stiter = 10;
        // 1: 5176, 2: 5180, 5: 5178, 10: 5181, 100: 5173, 250: 5162, 2500: 5158, 5000: 5152, 12500: 5146, 25000: 5150 -> 10 stiter

        double[] TRYk = {1, 5, 25, 100};
        double[] TRYlambda = {0.001, 0.01, 0.1, 0.5};
        /*
        K: 1.0 LAMBDA: 0.001 AVG QUALITY: 5145.240000000001 AVG TIME: 102.0
        K: 1.0 LAMBDA: 0.01 AVG QUALITY: 5145.3 AVG TIME: 100.0
        K: 1.0 LAMBDA: 0.1 AVG QUALITY: 5152.680000000001 AVG TIME: 100.0
        K: 1.0 LAMBDA: 0.5 AVG QUALITY: 5137.26 AVG TIME: 100.0
        K: 5.0 LAMBDA: 0.001 AVG QUALITY: 5183.880000000001 AVG TIME: 100.0
        K: 5.0 LAMBDA: 0.01 AVG QUALITY: 5178.3600000000015 AVG TIME: 100.0
        K: 5.0 LAMBDA: 0.1 AVG QUALITY: 5176.620000000002 AVG TIME: 100.0
        K: 5.0 LAMBDA: 0.5 AVG QUALITY: 5174.880000000001 AVG TIME: 100.0
        K: 25.0 LAMBDA: 0.001 AVG QUALITY: 5168.1 AVG TIME: 100.0
        K: 25.0 LAMBDA: 0.01 AVG QUALITY: 5178.300000000001 AVG TIME: 100.0
        K: 25.0 LAMBDA: 0.1 AVG QUALITY: 5179.320000000001 AVG TIME: 100.0
        K: 25.0 LAMBDA: 0.5 AVG QUALITY: 5175.719999999998 AVG TIME: 100.0
        K: 100.0 LAMBDA: 0.001 AVG QUALITY: 4895.52 AVG TIME: 0.0
        K: 100.0 LAMBDA: 0.01 AVG QUALITY: 5111.2800000000025 AVG TIME: 0.0
        K: 100.0 LAMBDA: 0.1 AVG QUALITY: 5136.299999999995 AVG TIME: 0.0
        K: 100.0 LAMBDA: 0.5 AVG QUALITY: 5150.1 AVG TIME: 0.0
        -> K: 5.0 LAMBDA: 0.001
         */
        double avgQuality = 0;
        double avgTime = 0;
        try {
            for (double k : TRYk) {
                for (double lambda : TRYlambda) {
                    FileWriter writer = new FileWriter("/home2/users/alumnes/1289647/Desktop/datos_sa/KandL/results_" + k + "_K_" + lambda + "_LAMBDA.csv");
                    writer.write("QUALITY TIME\n");
                    for (int i = 0; i < SAMPLE_AMOUNT; i++) {
                        AlgoritmoSA sa = new AlgoritmoSA(matriz, steps, stiter, k, lambda);
                        long startTime = System.currentTimeMillis();
                        Solucion result = sa.ejecutar(solucion, 1);
                        long endTime = System.currentTimeMillis();
                        long duration = endTime - startTime;
                        writer.write(result.getCalidad() + " " + duration + "\n");
                        avgQuality += result.getCalidad() / SAMPLE_AMOUNT;
                        avgTime += (double) duration / SAMPLE_AMOUNT;
                    }
                    System.out.println("K: " + k + " LAMBDA: " + lambda + " AVG QUALITY: " + avgQuality + " AVG TIME: " + avgTime);
                    writer.write("AVG " + avgQuality + " " + avgTime + "\n");
                    avgTime = 0;
                    avgQuality = 0;
                    writer.close();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}