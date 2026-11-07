package repository;

import java.io.*;

import model.*;
import util.*;

public class CatalogoRepository {

    /* MÉTODOS */
    // Carga el catálogo desde un archivo de texto
    public ListaSimple<Juego> cargarCatalogo(String rutaArchivo) {
        ListaSimple<Juego> catalogo = new ListaSimple<Juego>();
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\|", -1);
                if (partes.length >= 7) {
                    catalogo.insertarAlFinal(new Juego(
                            partes[0],
                            partes[1],
                            partes[2],
                            Double.parseDouble(partes[3]),
                            partes[4],
                            Integer.parseInt(partes[5]),
                            partes[6]
                    ));
                }
            }

            reader.close();
            return catalogo;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar el catalogo.", error);
        }
    }

    // Guarda el catálogo en un archivo, escribiendo cada juego con sus atributos separados por '|'
    public void guardarCatalogo(String rutaArchivo, ListaSimple<Juego> catalogo) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            NodoSimple<Juego> actual = catalogo.getCabeza();

            while (actual != null) {
                Juego juego = actual.dato;
                writer.write(juego.getCodigo() + "|" + juego.getNombre() + "|" + juego.getGenero() + "|" + juego.getPrecio() + "|" + juego.getPlataforma() + "|" + juego.getStock() + "|" + juego.getDescripcion());
                writer.newLine();
                actual = actual.siguiente;
            }

            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar el catalogo.", error);
        }
    }
}