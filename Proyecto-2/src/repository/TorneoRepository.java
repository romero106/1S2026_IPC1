package repository;

import java.io.*;

import model.*;
import util.*;

public class TorneoRepository {

    /* MÉTODOS */
    // Carga los torneos desde un archivo de texto
    public ListaSimple<Torneo> cargarTorneos(String rutaArchivo) {
        ListaSimple<Torneo> torneos = new ListaSimple<Torneo>();
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
                    torneos.insertarAlFinal(new Torneo(
                            partes[0],
                            partes[1],
                            partes[2],
                            partes[3],
                            partes[4],
                            Double.parseDouble(partes[5]),
                            Integer.parseInt(partes[6])
                    ));
                }
            }

            reader.close();
            return torneos;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar los torneos.", error);
        }
    }

    // Guarda los torneos en un archivo de texto
    public void guardarTorneos(String rutaArchivo, ListaSimple<Torneo> torneos) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            NodoSimple<Torneo> actual = torneos.getCabeza();

            while (actual != null) {
                Torneo torneo = actual.dato;
                writer.write(torneo.getId() + "|" + torneo.getNombre() + "|" + torneo.getJuego() + "|" + torneo.getFecha() + "|" + torneo.getHora() + "|" + torneo.getPrecioTicket() + "|" + torneo.getTicketsDisponibles());
                writer.newLine();
                actual = actual.siguiente;
            }

            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar los torneos.", error);
        }
    }
}