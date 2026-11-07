package repository;

import java.io.*;

import model.*;
import util.*;

public class TicketRepository {

    /* MÉTODOS */
    // Carga los tickets vendidos desde un archivo de texto
    public ListaSimple<TicketVenta> cargarTickets(String rutaArchivo) {
        ListaSimple<TicketVenta> tickets = new ListaSimple<TicketVenta>();
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\|", -1);
                if (partes.length >= 6) {
                    tickets.insertarAlFinal(new TicketVenta(
                            partes[0],
                            partes[1],
                            partes[2],
                            partes[3],
                            Double.parseDouble(partes[4]),
                            partes[5]
                    ));
                }
            }

            reader.close();
            return tickets;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar los tickets vendidos.", error);
        }
    }

    // Guarda los tickets vendidos en un archivo de texto
    public void guardarTickets(String rutaArchivo, ListaSimple<TicketVenta> tickets) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            NodoSimple<TicketVenta> actual = tickets.getCabeza();

            while (actual != null) {
                TicketVenta ticket = actual.dato;
                writer.write(ticket.getTorneoId() + "|" + ticket.getTorneoNombre() + "|" + ticket.getComprador() + "|" + ticket.getFechaHora() + "|" + ticket.getPrecio() + "|" + ticket.getTaquilla());
                writer.newLine();
                actual = actual.siguiente;
            }

            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar los tickets.", error);
        }
    }
}