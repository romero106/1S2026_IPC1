package repository;

import java.io.*;

import model.*;
import util.*;

public class HistorialRepository {

    /* MÉTODOS */
    // Carga el historial de compras desde un archivo de texto
    public ListaSimple<Compra> cargarHistorial(String rutaArchivo) {
        ListaSimple<Compra> historial = new ListaSimple<Compra>();
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\|", -1);
                if (partes.length >= 3) {
                    historial.insertarAlFinal(new Compra(partes[0], partes[1], Double.parseDouble(partes[2])));
                }
            }

            reader.close();
            return historial;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar el historial de compras.", error);
        }
    }

    // Guarda el historial de compras en un archivo, escribiendo cada compra con su fecha, detalle y total separados por '|'
    public void guardarHistorial(String rutaArchivo, ListaSimple<Compra> historial) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            NodoSimple<Compra> actual = historial.getCabeza();

            while (actual != null) {
                Compra compra = actual.dato;
                writer.write(compra.getFechaHora() + "|" + compra.getDetalle() + "|" + compra.getTotal());
                writer.newLine();
                actual = actual.siguiente;
            }

            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar el historial.", error);
        }
    }
}