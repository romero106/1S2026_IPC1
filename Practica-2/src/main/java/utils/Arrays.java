package utils;

import java.util.Random;

public class Arrays {

    /* METODOS */
    // Metodo para convertir una cadena de texto a un arreglo de enteros
    public static int[] textoArreglo(String arregloTexto) {
        if (arregloTexto == null || arregloTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("Entrada vacia");
        }

        String[] partes = arregloTexto.split(",");
        int[] arreglo = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {
            String parte = partes[i].trim();

            if (parte.isEmpty()) {
                throw new IllegalArgumentException("Hay valores vacios en la entrada");
            }

            try {
                arreglo[i] = Integer.parseInt(partes[i].trim());
            } catch (NumberFormatException err) {
                throw new IllegalArgumentException("Entrada invalida");
            }
        }
        
        return arreglo;
    }

    // Metodo para convertir un arreglo de enteros a una cadena de texto
    public static String arregloTexto(int[] arreglo) {
        StringBuilder arregloTexto = new StringBuilder();

        for (int i = 0; i < arreglo.length; i++) {
            arregloTexto.append(arreglo[i]);
            if (i < arreglo.length - 1) {
                arregloTexto.append(", ");
            }
        }

        return arregloTexto.toString();
    }

    // Metodo para generar un arreglo aleatorio
    public static int[] arregloAleatorio(int cantidad, int min, int max) {
        Random random = new Random();
        int[] arreglo = new int[cantidad];

        for (int i = 0; i < cantidad; i++) {
            arreglo[i] = random.nextInt(max - min + 1) + min;
        }

        return arreglo;
    }

    // Metodo para realizar una copia de un arreglo
    public static int[] copiarArreglo(int[] arreglo) {
        int[] arregloCopia = new int[arreglo.length];
        for (int i = 0; i < arreglo.length; i++) {
            arregloCopia[i] = arreglo[i]; 
        }

        return arregloCopia;
    }
    
    // Metodo para realizar una copia del arreglo original
    /*
     * public static int[] arregloOriginal(int[] arreglo) {
     * int[] arregloOriginal = new int[arreglo.length];
     * for (int i = 0; i < arreglo.length; i++) {
     * arregloOriginal[i] = arreglo[i];
     * }
     * 
     * return arregloOriginal;
     * }
     */
}
