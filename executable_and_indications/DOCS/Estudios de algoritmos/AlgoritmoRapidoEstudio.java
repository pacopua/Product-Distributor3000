package edu.upc.prop.clusterxx.domain;

import java.io.FileWriter;
import java.io.IOException;

public class AlgoritmoRapidoEstudio {
    public static void main(String [] args) throws IOException {
        int[] semillas = {
                68893704,
                1282654127,
                -1932541438,
                1808963067,
                1639702225,
                1966800074,
                -295385852,
                236654658,
                -515245099,
                -503429392,
                1870262154,
                -707809822,
                1225847947,
                -421946927,
                -1908643658,
                -1150482868,
                1585225140,
                -277149196,
                90269793,
                212614995,
                -1847139192,
                -871443497,
                -814546572,
                795650797,
                1216222001,
                -1128613543,
                -1334919819,
                2121465146,
                -404330372,
                -2052632228,
                -1099309724,
                198663878,
                1378940621,
                610020774,
                173560776,
                583786250,
                2079129172,
                -1944192248,
                1032899588,
                -1795617992,
                -2135411074,
                1481375681,
                -939961618,
                1061596472,
                403028653,
                -1951622109,
                -569543519,
                180549357,
                -2095411670,
                7254391,
                -30940270,
                -793076292,
                700050266,
                144708381,
                835124449,
                -1992775091,
                -1142238673,
                943154679,
                -1700479503,
                -1588789101,
                669223393,
                1523716616,
                -1226834118,
                518317066,
                1254033886,
                103623255,
                -587331491,
                -1292829633,
                1953201663,
                157970201,
                -848768648,
                742956437,
                -1837082117,
                -2036281842,
                2088732091,
                242375237,
                -1113739232,
                -766900950,
                -611050693,
                -881451098,
                -1864515777,
                689261432,
                -242609147,
                928867246,
                -302191641,
                -222223301,
                -495469450,
                -213244232,
                -630482730,
                236765270,
                166492874,
                2102775174,
                -322923860,
                -1720479251,
                1251214322,
                1173690250,
                909771490,
                -911477173,
                -1994946135,
                598652257
        };
        double[][] matrizAdyacencia = {
                {0.0, 26.282936, 86.47317, 17.923319, 11.520058, 94.38012, 28.280317, 68.740204, 13.2031555, 34.742844, 83.965996, 88.19951, 85.21542, 77.85251, 70.64724, 26.554537, 93.79761, 9.256399, 54.241627, 8.806288, 78.35916, 27.568823, 64.172646, 98.28305, 98.16223, 79.17277, 73.87799, 63.74786, 3.1977296, 66.58403},
                {26.282936, 0.0, 87.851944, 4.0151415, 27.561724, 40.71702, 76.08126, 67.40639, 46.23979, 10.950554, 25.384718, 54.96646, 89.21379, 99.23386, 39.12324, 87.77957, 22.659391, 91.611206, 68.35942, 41.8611, 52.20909, 1.4037311, 31.721664, 46.165977, 83.37943, 9.607059, 12.441945, 7.7647448, 58.93283, 29.39484},
                {86.47317, 87.851944, 0.0, 70.322296, 76.028305, 40.85409, 0.3236413, 86.34179, 7.8743696, 88.52424, 34.09032, 57.4024, 17.221588, 16.385359, 45.486885, 69.05189, 22.539402, 69.78916, 30.330717, 12.1997595, 80.85561, 1.7500818, 25.233768, 44.705997, 69.18404, 44.984745, 63.537495, 75.681885, 44.7677, 70.98047},
                {17.923319, 4.0151415, 70.322296, 0.0, 46.820663, 16.370302, 53.819664, 93.02421, 84.1343, 70.10524, 29.914087, 74.33231, 21.395784, 50.183178, 13.481098, 30.903698, 33.85833, 20.17424, 63.945152, 95.79177, 85.75055, 49.48024, 27.189774, 25.531816, 56.35506, 97.59511, 59.811802, 57.91255, 82.07759, 64.88894},
                {11.520058, 27.561724, 76.028305, 46.820663, 0.0, 14.9414835, 64.64668, 93.375175, 33.72983, 24.457317, 13.342369, 15.501857, 30.849976, 1.7360449, 55.134003, 17.44361, 56.09058, 65.48105, 71.08825, 6.514162, 81.6203, 96.81045, 39.813793, 82.04611, 43.57496, 82.117256, 67.38732, 43.425076, 40.53523, 27.00689},
                {94.38012, 40.71702, 40.85409, 16.370302, 14.9414835, 0.0, 49.641983, 41.179703, 62.627975, 10.954618, 5.9906425, 18.570244, 95.15469, 67.60146, 37.277412, 1.539129, 41.926395, 46.528805, 60.73421, 1.2363672, 67.89149, 33.93885, 70.09328, 22.662098, 66.71651, 72.25258, 54.46288, 60.140682, 66.81692, 32.86279},
                {28.280317, 76.08126, 0.3236413, 53.819664, 64.64668, 49.641983, 0.0, 95.96628, 73.032135, 25.596476, 18.104935, 2.6888847, 98.78467, 54.561924, 24.201918, 33.555443, 83.43826, 22.319485, 8.082867, 37.80861, 8.116346, 4.579467, 9.4647045, 80.28656, 12.81693, 73.10423, 72.626274, 10.267109, 72.67354, 23.506546},
                {68.740204, 67.40639, 86.34179, 93.02421, 93.375175, 41.179703, 95.96628, 0.0, 47.41708, 75.88713, 56.35209, 45.974308, 79.058655, 96.7518, 88.74392, 5.9619308, 94.360695, 7.0704994, 80.78522, 62.808247, 76.54314, 32.31272, 66.06588, 53.838295, 80.69933, 89.846954, 67.687836, 77.06713, 61.135715, 23.248404},
                {13.2031555, 46.23979, 7.8743696, 84.1343, 33.72983, 62.627975, 73.032135, 47.41708, 0.0, 88.20756, 35.995144, 64.77559, 19.943237, 81.87489, 60.576916, 41.224052, 90.66286, 0.99960566, 86.2316, 49.979294, 38.940983, 59.188194, 81.27759, 23.972929, 40.79032, 83.260635, 37.515594, 11.773896, 23.99854, 25.554592},
                {34.742844, 10.950554, 88.52424, 70.10524, 24.457317, 10.954618, 25.596476, 75.88713, 88.20756, 0.0, 30.511648, 49.353893, 57.501, 29.542469, 68.66906, 57.55644, 72.399315, 17.524893, 63.209373, 69.9189, 64.842575, 40.73567, 96.05582, 34.161274, 18.673962, 73.42472, 47.238667, 11.6849, 13.038278, 75.1141},
                {83.965996, 25.384718, 34.09032, 29.914087, 13.342369, 5.9906425, 18.104935, 56.35209, 35.995144, 30.511648, 0.0, 77.292, 5.3372264, 91.67245, 3.7997186, 79.2222, 54.32804, 0.054097176, 59.760483, 6.5963564, 44.67251, 91.71604, 30.013079, 13.285053, 37.343544, 52.663906, 0.3033638, 72.12022, 28.21108, 43.838074},
                {88.19951, 54.96646, 57.4024, 74.33231, 15.501857, 18.570244, 2.6888847, 45.974308, 64.77559, 49.353893, 77.292, 0.0, 0.6542206, 66.48083, 32.909042, 55.882782, 55.508865, 14.444912, 65.39887, 62.12632, 56.843185, 10.054672, 25.896406, 37.705105, 62.509323, 72.40688, 77.017525, 42.308826, 19.56619, 29.236729},
                {85.21542, 89.21379, 17.221588, 21.395784, 30.849976, 95.15469, 98.78467, 79.058655, 19.943237, 57.501, 5.3372264, 0.6542206, 0.0, 6.8089724, 87.27555, 8.313751, 27.508324, 14.4082365, 84.978584, 14.853406, 35.9195, 14.378256, 8.937377, 43.733692, 61.394085, 15.557432, 40.231808, 35.911007, 48.358078, 61.136097},
                {77.85251, 99.23386, 16.385359, 50.183178, 1.7360449, 67.60146, 54.561924, 96.7518, 81.87489, 29.542469, 91.67245, 66.48083, 6.8089724, 0.0, 92.04393, 26.97106, 22.222996, 10.510808, 55.998154, 65.24507, 9.942121, 37.48541, 6.9855747, 65.853065, 55.23452, 21.10163, 6.9702387, 44.36602, 5.123639, 17.624546},
                {70.64724, 39.12324, 45.486885, 13.481098, 55.134003, 37.277412, 24.201918, 88.74392, 60.576916, 68.66906, 3.7997186, 32.909042, 87.27555, 92.04393, 0.0, 21.57579, 0.06235242, 90.40937, 48.123837, 78.793724, 83.83766, 44.74495, 84.13748, 68.756485, 27.699041, 95.84517, 2.8499365, 83.05813, 52.23608, 35.296165},
                {26.554537, 87.77957, 69.05189, 30.903698, 17.44361, 1.539129, 33.555443, 5.9619308, 41.224052, 57.55644, 79.2222, 55.882782, 8.313751, 26.97106, 21.57579, 0.0, 47.14393, 86.52396, 34.124397, 99.316154, 19.188272, 82.632416, 69.00916, 17.041767, 58.610653, 94.043564, 25.274288, 52.458427, 77.79375, 14.018387},
                {93.79761, 22.659391, 22.539402, 33.85833, 56.09058, 41.926395, 83.43826, 94.360695, 90.66286, 72.399315, 54.32804, 55.508865, 27.508324, 22.222996, 0.06235242, 47.14393, 0.0, 53.278305, 54.491035, 12.757081, 64.11218, 2.4938107, 62.119823, 84.09058, 3.6486745, 48.29983, 89.34725, 79.445656, 1.4443815, 41.432755},
                {9.256399, 91.611206, 69.78916, 20.17424, 65.48105, 46.528805, 22.319485, 7.0704994, 0.99960566, 17.524893, 0.054097176, 14.444912, 14.4082365, 10.510808, 90.40937, 86.52396, 53.278305, 0.0, 41.636425, 68.43534, 88.43517, 49.604256, 20.855623, 39.029003, 38.699226, 53.65485, 41.14506, 18.542803, 67.37764, 13.75733},
                {54.241627, 68.35942, 30.330717, 63.945152, 71.08825, 60.73421, 8.082867, 80.78522, 86.2316, 63.209373, 59.760483, 65.39887, 84.978584, 55.998154, 48.123837, 34.124397, 54.491035, 41.636425, 0.0, 61.380173, 74.333466, 17.731434, 16.352076, 0.5140364, 11.816978, 30.670935, 14.237672, 26.075071, 28.290146, 32.24159},
                {8.806288, 41.8611, 12.1997595, 95.79177, 6.514162, 1.2363672, 37.80861, 62.808247, 49.979294, 69.9189, 6.5963564, 62.12632, 14.853406, 65.24507, 78.793724, 99.316154, 12.757081, 68.43534, 61.380173, 0.0, 52.918488, 31.07183, 88.37396, 35.50358, 41.10151, 54.675682, 54.18198, 69.81696, 35.126392, 73.03633},
                {78.35916, 52.20909, 80.85561, 85.75055, 81.6203, 67.89149, 8.116346, 76.54314, 38.940983, 64.842575, 44.67251, 56.843185, 35.9195, 9.942121, 83.83766, 19.188272, 64.11218, 88.43517, 74.333466, 52.918488, 0.0, 94.31293, 23.618877, 11.905784, 30.720186, 69.557205, 6.2955437, 56.260967, 94.394585, 29.186016},
                {27.568823, 1.4037311, 1.7500818, 49.48024, 96.81045, 33.93885, 4.579467, 32.31272, 59.188194, 40.73567, 91.71604, 10.054672, 14.378256, 37.48541, 44.74495, 82.632416, 2.4938107, 49.604256, 17.731434, 31.07183, 94.31293, 0.0, 4.714048, 72.786835, 58.922585, 74.56665, 73.452415, 93.80762, 93.956, 51.637573},
                {64.172646, 31.721664, 25.233768, 27.189774, 39.813793, 70.09328, 9.4647045, 66.06588, 81.27759, 96.05582, 30.013079, 25.896406, 8.937377, 6.9855747, 84.13748, 69.00916, 62.119823, 20.855623, 16.352076, 88.37396, 23.618877, 4.714048, 0.0, 61.8815, 20.642399, 84.95291, 10.814434, 42.199158, 40.149837, 46.399315},
                {98.28305, 46.165977, 44.705997, 25.531816, 82.04611, 22.662098, 80.28656, 53.838295, 23.972929, 34.161274, 13.285053, 37.705105, 43.733692, 65.853065, 68.756485, 17.041767, 84.09058, 39.029003, 0.5140364, 35.50358, 11.905784, 72.786835, 61.8815, 0.0, 32.82741, 70.550415, 70.0407, 93.541046, 17.768347, 37.803085},
                {98.16223, 83.37943, 69.18404, 56.35506, 43.57496, 66.71651, 12.81693, 80.69933, 40.79032, 18.673962, 37.343544, 62.509323, 61.394085, 55.23452, 27.699041, 58.610653, 3.6486745, 38.699226, 11.816978, 41.10151, 30.720186, 58.922585, 20.642399, 32.82741, 0.0, 30.223322, 80.35645, 98.52274, 60.765713, 21.780962},
                {79.17277, 9.607059, 44.984745, 97.59511, 82.117256, 72.25258, 73.10423, 89.846954, 83.260635, 73.42472, 52.663906, 72.40688, 15.557432, 21.10163, 95.84517, 94.043564, 48.29983, 53.65485, 30.670935, 54.675682, 69.557205, 74.56665, 84.95291, 70.550415, 30.223322, 0.0, 0.6051481, 90.35878, 31.074793, 36.185722},
                {73.87799, 12.441945, 63.537495, 59.811802, 67.38732, 54.46288, 72.626274, 67.687836, 37.515594, 47.238667, 0.3033638, 77.017525, 40.231808, 6.9702387, 2.8499365, 25.274288, 89.34725, 41.14506, 14.237672, 54.18198, 6.2955437, 73.452415, 10.814434, 70.0407, 80.35645, 0.6051481, 0.0, 11.924076, 67.63741, 94.14528},
                {63.74786, 7.7647448, 75.681885, 57.91255, 43.425076, 60.140682, 10.267109, 77.06713, 11.773896, 11.6849, 72.12022, 42.308826, 35.911007, 44.36602, 83.05813, 52.458427, 79.445656, 18.542803, 26.075071, 69.81696, 56.260967, 93.80762, 42.199158, 93.541046, 98.52274, 90.35878, 11.924076, 0.0, 63.955666, 63.016384},
                {3.1977296, 58.93283, 44.7677, 82.07759, 40.53523, 66.81692, 72.67354, 61.135715, 23.99854, 13.038278, 28.21108, 19.56619, 48.358078, 5.123639, 52.23608, 77.79375, 1.4443815, 67.37764, 28.290146, 35.126392, 94.394585, 93.956, 40.149837, 17.768347, 60.765713, 31.074793, 67.63741, 63.955666, 0.0, 53.601448},
                {66.58403, 29.39484, 70.98047, 64.88894, 27.00689, 32.86279, 23.506546, 23.248404, 25.554592, 75.1141, 43.838074, 29.236729, 61.136097, 17.624546, 35.296165, 14.018387, 41.432755, 13.75733, 32.24159, 73.03633, 29.186016, 51.637573, 46.399315, 37.803085, 21.780962, 36.185722, 94.14528, 63.016384, 53.601448, 0.0}
        };

        MatrizAdyacencia m = new MatrizAdyacencia(matrizAdyacencia);
        AlgoritmoRapido ar = new AlgoritmoRapido(m);
        ListaProductos l = new ListaProductos();
        for (int i = 0; i < 30; i++) l.addProducto(new Producto(Integer.toString(i), 0.));
        Solucion s = new Solucion(l, 5, 6);
        //creamos un documento csv que escriba los resultados en una direccion especifica

        FileWriter writer = new FileWriter("ResultadosParalelizado.csv");
        FileWriter writer2 = new FileWriter("ResultadosParalelizadoTotal.csv");
        writer.append("Semilla, calidad, tiempo(ms) \n");
        writer2.append("calida_media, tiempo_medio(ms) \n");
        double tiempoIniTotal = System.currentTimeMillis();
        double suma_calidades = 0;
        for(int i = 0; i < 100; i++) {
            double tiempoIni = System.currentTimeMillis();
            s = ar.ejecutar(s, 100);//, semillas[i]);
            double tiempoFin = System.currentTimeMillis();
            suma_calidades += s.getCalidad();
            writer.append(semillas[i] + ", " + s.getCalidad() + ", " + (tiempoFin-tiempoIni) + "\n");
            System.out.println(i);
        }
        double tiempoFinTotal = System.currentTimeMillis();
        //writer2.append("calida_media, tiempo_medio(ms) \n");
        System.out.println("Toy aqui");
        writer2.append(suma_calidades/100 + ", " + (tiempoFinTotal-tiempoIniTotal) + "\n");
    }
}
