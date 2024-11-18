package edu.upc.prop.clusterxx.driver;

import edu.upc.prop.clusterxx.*;

import java.io.File;
import java.util.Objects;

public class Driver {
    private static final inout io = new inout();

    public static void main(String[] args) {
        int opcion;
        while (true) {
            opcion = menu();
            if (opcion == 5) {
                break;
            }
        }
    }

    /**
     * Muestra el menu principal
     * @return la opcion seleccionada
     */
    private static int menu() {
        try {
            io.write("Bienvenido al sistema de gestion de productos!\n");
            io.write("Por favor, seleccione una opcion:\n");
            io.write("1. Solucion\n");
            io.write("2. Lista de productos\n");
            io.write("3. Importar estado\n");
            io.write("4. Guardar estado\n");
            io.write("5. Salir\n");
            io.write("Opcion: ");
            int opcion = io.readint();
            io.writeln();
            switch (opcion) {
                case 1:
                    sistemaSolucion();
                    break;
                case 2:
                    sistemaListaProductos();
                    break;
                case 3:
                    importarEstado();
                    break;
                case 4:
                    guardarEstado();
                    break;
                case 5:
                    io.write("Saliendo del sistema...");
                    break;
                default:
                    io.write("Opcion no valida");
                    break;
            }
            return opcion;
        } catch (Exception e) {
            System.err.println("Error al escribir en consola!");
        }
        return -1;
    }

    /**
     * Muestra el menu de la solucion
     */
    private static void sistemaSolucion() {
        while (true) {
            try {
                io.write("Por favor, seleccione una opcion:\n");
                io.write("1. Calcular distribucion rapida\n");
                io.write("2. Calcular distribucion optima\n");
                io.write("3. Intercambiar productos de la solucion\n");
                io.write("4. Consultar posicion de un producto en la solucion\n");
                io.write("5. Volver\n");
                io.write("Opcion: ");
                int opcion = io.readint();
                io.writeln();
                int num_productos = Sistema.getListaProductos().getListaProductos().size();
                Solucion s;
                switch (opcion) {
                    case 1:
                        calcularDistribucionRapida();
                        break;
                    case 2:
                        calcularDistribucionOptima();
                        break;
                    case 3:
                        intercambiarProductosSolucion(num_productos);
                        break;
                    case 4:
                        consultarPosicionProducto(num_productos);
                        break;
                    case 5:
                        return;
                    default:
                        io.writeln("Opcion no valida");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error solucion - " + e.getMessage());
            }
        }
    }

    /**
     * Calcula la distribucion rapida
     * @throws Exception si hay un error al imprimir en consola
     */
    private static void calcularDistribucionRapida() throws Exception {
        io.write("Introduce el numero de filas de la solución: ");
        int filas = io.readint();
        io.writeln();
        io.write("Introduce el numero de columnas de la solución: ");
        int columnas = io.readint();
        Sistema.nuevaSolucion(filas, columnas);
        Solucion s = Sistema.getSolucion();
        AlgoritmoRapido ar = new AlgoritmoRapido(Sistema.getMatrizAdyacencia());
        ar.ejecutar(s, 30);
        io.writeln("Algoritmo rapido ejecutado con exito!");
        io.writeln("Ha tardado " + s.getNumPasos() + " pasos en ejecutarse");
        io.writeln("La calidad de la solucion es: " + s.getCalidad());
    }

    /**
     * Calcula la distribucion optima
     * @throws Exception si hay un error al imprimir en consola
     */
    private static void calcularDistribucionOptima() throws Exception {
        io.write("Introduce el numero de filas de la solución: ");
        int filas = io.readint();
        io.writeln();
        io.write("Introduce el numero de columnas de la solución: ");
        int columnas = io.readint();
        Sistema.nuevaSolucion(filas, columnas);
        Solucion s = Sistema.getSolucion();
        AlgoritmoVoraz av = new AlgoritmoVoraz(Sistema.getMatrizAdyacencia());
        av.ejecutar(s);
        io.writeln("Algoritmo voraz ejecutado con exito!");
        io.writeln("Ha tardado " + s.getNumPasos() + " pasos en ejecutarse");
        io.writeln("La calidad de la solucion es: " + s.getCalidad());
    }

    /**
     * Intercambia dos productos de la solucion
     * @param num_productos el numero total de productos
     * @throws Exception si hay un error al imprimir en consola
     */
    private static void intercambiarProductosSolucion(int num_productos) throws Exception {
        imprimirListaProductos();
        io.writeln("Introduce el numero de 2 productos a intercambiar: ");
        int p1, p2;
        boolean bp1 = false, bp2 = false;
        do {
            p1 = io.readint();
            bp1 = p1 < 0 || p1 > num_productos;
            if (bp1) {
                io.writeln("Producto no valido!");
            }
        } while (bp1);
        do {
            p2 = io.readint();
            bp2 = p2 < 0 || p2 > num_productos || p1 == p2;
            if (bp2) {
                io.writeln(p1 == p2 ? "Los productos no pueden ser iguales" : "Producto no valido!");
            }
        } while (bp2);
        int[] pos1 = Sistema.getSolucion().buscar_producto(p1);
        int[] pos2 = Sistema.getSolucion().buscar_producto(p2);
        Sistema.getSolucion().intercambiar_productos(pos1[0], pos1[1], pos2[0], pos2[1]);
    }

    /**
     * Consulta la posicion de un producto en la solucion
     * @param num_productos el numero total de productos
     * @throws Exception si hay un error al imprimir en consola
     */
    private static void consultarPosicionProducto(int num_productos) throws Exception {
        imprimirListaProductos();
        io.writeln("Introduce el numero del producto: ");
        int id;
        Producto p;
        while (true) {
            id = io.readint();
            if (id < 0 || id > num_productos) {
                io.writeln("Producto no valido!");
            } else {
                p = Sistema.getListaProductos().getProducto(id).orElse(null);
                if (p == null) {
                    throw new Exception("Producto no encontrado!");
                } else {
                    break;
                }
            }
        }
        int[] pos = Sistema.getSolucion().buscar_producto(id);
        io.writeln("El producto " + "(" + id + ") se encuentra en la fila " + pos[0] + " y columna " + pos[1]);
    }

    /**
     * Imprime la lista de productos
     * @throws Exception si hay un error al imprimir en consola
     */
    private static void imprimirListaProductos() throws Exception {
        int i = 0;
        for (Producto p : Sistema.getListaProductos().getListaProductos()) {
            System.out.printf("ID %d - \t%s - %f€\n", i, p.getNombre(), p.getPrecio());
            i++;
        }
        io.writeln();
    }

    /**
     * Muestra el menu de la lista de productos
     * @throws Exception
     */
    private static void sistemaListaProductos() throws Exception {
        while (true) {
            try {
                io.write("Por favor, seleccione una opcion:\n");
                io.write("1. Importar lista de productos\n");
                io.write("2. Exportar lista de productos\n");
                io.write("3. Anyadir producto\n");
                io.write("4. Eliminar producto\n");
                io.write("5. Modificar sinergia entre dos productos\n");
                io.write("6. Consultar lista de productos\n");
                io.write("7. Consultar un producto\n");
                io.write("8. Volver\n");
                io.write("Opcion: ");
                int opcion = io.readint();
                io.writeln();
                int num_productos = Sistema.getListaProductos().getCantidadProductos();
                switch (opcion) {
                    case 1:
                        importarListaProductos();
                        break;
                    case 2:
                        exportarListaProductos();
                        break;
                    case 3:
                        crearProducto();
                        num_productos++;
                        break;
                    case 4:
                        eliminarProducto();
                        io.writeln("Lista de productos actualizada: ");
                        num_productos--;
                        imprimirListaProductos();
                        break;
                    case 5:
                        modificarSinergia(num_productos);
                        break;
                    case 6:
                        imprimirListaProductos();
                        break;
                    case 7:
                        imprimirListaProductos();
                        io.write("Introduce el numero del producto a consultar: ");
                        int id = io.readint();
                        Producto producto = Sistema.getListaProductos().getProducto(id).orElse(null);
                        if (producto != null) {
                            io.writeln("Nombre del producto: " + producto.getNombre());
                            io.writeln("Precio del producto: " + producto.getPrecio());
                        } else {
                            io.writeln("Producto no encontrado!");
                        }
                        break;
                    case 8:
                        return;
                    default:
                        io.write("Opcion no valida");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error lista de productos - " + e.getMessage());
            }
        }
    }

    /**
     * Crea un nuevo producto
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void crearProducto() throws Exception {
        io.write("Introduce el nombre del nuevo producto: ");
        io.readnext();
        String nombre_producto = io.readline();
        io.write("Introduce el precio del nuevo producto: ");
        double precio = io.readdouble();
        Producto p = new Producto(nombre_producto, precio);
        Sistema.getListaProductos().addProducto(p);
    }

    /**
     * Elimina un producto de la lista
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void eliminarProducto() throws Exception {
        imprimirListaProductos();
        io.write("Introduce el numero del producto a eliminar: ");
        int num_producto = io.readint();
        Sistema.getListaProductos().eliminarProducto(num_producto);
    }

    /**
     * Modifica la sinergia entre dos productos
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void modificarSinergia(int num_productos) throws Exception{
        imprimirListaProductos();
        io.write("Introduce el numero del primer producto: ");
        int p1, p2;
        while (true) {
            p1 = io.readint();
            if (p1 < 0 || p1 > num_productos) {
                io.write("Producto no valido!\n");
            } else {
                break;
            }
        }
        io.write("Introduce el numero del segundo producto: ");
        while (true) {
            p2 = io.readint();
            if (p2 < 0 || p2 > num_productos) {
                io.writeln("Producto no valido!");
            } else if (p1 == p2) {
                io.writeln("Los productos no pueden ser iguales!");
            } else {
                break;
            }
        }
        io.write("Introduce la nueva sinergia entre los productos: ");
        double sinergia = io.readdouble();
        Sistema.getMatrizAdyacencia().modificar_sinergias(p1, p2, sinergia);
    }

    /**
     * Importa la lista de productos
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void importarListaProductos() throws Exception {
        io.write("Introduce la ruta del archivo (Introduce 'volver' para volver al menu principal): \n");
        while (true) {
            io.readnext();
            String path = io.readline();
            if (Objects.equals(path, "volver")) {
                break;
            }
            File file = new File(path);
            if (!file.exists()) {
                io.write("Archivo no encontrado!\n");
            } else if (file.isDirectory()) {
                io.write("El archivo es un directorio!\n");
            } else {
                Sistema.importarLista(file);
                break;
            }
        }
    }

    /**
     * Exporta la lista de productos
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void exportarListaProductos() throws Exception {
        io.write("Introduce la ruta del archivo donde guardar la lista de productos (Introduce 'volver' para volver al menu principal): \n");
        while (true) {
            io.readnext();
            String path = io.readline();
            if (path.equalsIgnoreCase("volver")) {
                break;
            }
            File file = new File(path);
            if (file.exists()) {
                io.write("Archivo ya existente!\n");
            } else if (file.isDirectory()) {
                io.write("El archivo es un directorio!\n");
            } else {
                Sistema.exportarLista(file);
                break;
            }
        }
    }

    /**
     * Importa el estado del sistema
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void importarEstado() throws Exception {
        io.write("Introduce la ruta del archivo (Introduce 'volver' para volver al menu principal): \n");
        while (true) {
            io.readnext();
            String path = io.readline();
            if (Objects.equals(path, "volver")) {
                break;
            }
            File file = new File(path);
            if (!file.exists()) {
                io.write("Archivo no encontrado!\n");
            } else if (file.isDirectory()) {
                io.write("El archivo es un directorio!\n");
            } else {
                Sistema.importarEstado(file);
                break;
            }
        }
    }

    /**
     * Guarda el estado del sistema
     * @throws Exception si hay un error al leer la entrada o al escribir en consola
     */
    private static void guardarEstado() throws Exception {
        io.write("Introduce la ruta del archivo donde guardar el estado (Introduce 'volver' para volver al menu principal): \n");
        while (true) {
            io.readnext();
            String path = io.readline();
            if (path.equalsIgnoreCase("volver")) {
                break;
            }
            File file = new File(path);
            if (file.exists()) {
                io.write("Archivo ya existente!\n");
            } else if (file.isDirectory()) {
                io.write("El archivo es un directorio!\n");
            } else {
                Sistema.exportarEstado(file);
                break;
            }
        }
    }
}
