package repository;

import java.io.*;

import model.*;
import util.*;

public class AlbumRepository {

    /* MÉTODOS */
    // Carga el álbum desde un archivo de texto
    public MatrizCartas cargarAlbum(String rutaArchivo, int filas, int columnas) {
        MatrizCartas album = new MatrizCartas(filas, columnas);
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\|", -1);
                if (partes.length >= 10) {
                    int fila = Integer.parseInt(partes[0]);
                    int columna = Integer.parseInt(partes[1]);
                    NodoMatriz nodo = album.getNodo(fila, columna);

                    if (nodo != null) {
                        nodo.setCarta(new Carta(
                                partes[2],
                                partes[3],
                                partes[4],
                                partes[5],
                                Integer.parseInt(partes[6]),
                                Integer.parseInt(partes[7]),
                                Integer.parseInt(partes[8]),
                                partes[9]
                        ));
                    }
                }
            }

            reader.close();
            return album;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar el album.", error);
        }
    }

    // Guarda el álbum en un archivo, escribiendo cada carta con su posición y atributos
    public void guardarAlbum(String rutaArchivo, MatrizCartas album) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            NodoMatriz filaActual = album.getInicio();

            while (filaActual != null) {
                NodoMatriz columnaActual = filaActual;

                while (columnaActual != null) {
                    if (columnaActual.getCarta() != null) {
                        Carta carta = columnaActual.getCarta();
                        writer.write(columnaActual.getFila() + "|" + columnaActual.getColumna() + "|" + carta.getCodigo() + "|" + carta.getNombre() + "|" + carta.getTipo() + "|" + carta.getRareza() + "|" + carta.getAtaque() + "|" + carta.getDefensa() + "|" + carta.getPuntosSalud() + "|" + carta.getImagen());
                        writer.newLine();
                    }
                    columnaActual = columnaActual.getDerecha();
                }

                filaActual = filaActual.getAbajo();
            }

            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar el album.", error);
        }
    }
}