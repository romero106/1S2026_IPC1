package service;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.*;
import util.*;

public class ReporteService {

    /* ATRIBUTOS */
    private String rutaReportes;
    private ListaSimple<Juego> catalogo;
    private ListaSimple<Compra> historialCompras;
    private MatrizCartas album;
    private ListaSimple<Torneo> torneos;
    private ListaSimple<TicketVenta> ticketsVendidos;
    private RecompensaService recompensaService;

    /* CONSTRUCTOR */
    public ReporteService(String rutaReportes, ListaSimple<Juego> catalogo, ListaSimple<Compra> historialCompras, MatrizCartas album, ListaSimple<Torneo> torneos, ListaSimple<TicketVenta> ticketsVendidos, RecompensaService recompensaService) {
        this.rutaReportes = rutaReportes;
        this.catalogo = catalogo;
        this.historialCompras = historialCompras;
        this.album = album;
        this.torneos = torneos;
        this.ticketsVendidos = ticketsVendidos;
        this.recompensaService = recompensaService;
    }

    /* MÉTODOS */
    // Genera un reporte del inventario de la tienda
    public String generarReporteInventario() {
        StringBuilder html = new StringBuilder();
        html.append(crearInicioReporte("Reporte de Inventario de Tienda"));
        html.append("<table><tr><th>Codigo</th><th>Nombre</th><th>Genero</th><th>Plataforma</th><th>Precio</th><th>Stock</th></tr>");

        NodoSimple<Juego> actual = catalogo.getCabeza();
        while (actual != null) {
            Juego juego = actual.dato;
            html.append("<tr>")
                    .append("<td>").append(escapeHtml(juego.getCodigo())).append("</td>")
                    .append("<td>").append(escapeHtml(juego.getNombre())).append("</td>")
                    .append("<td>").append(escapeHtml(juego.getGenero())).append("</td>")
                    .append("<td>").append(escapeHtml(juego.getPlataforma())).append("</td>")
                    .append("<td>Q").append(recompensaService.formatearMonto(juego.getPrecio())).append("</td>")
                    .append("<td>").append(juego.getStock()).append("</td>")
                    .append("</tr>");
            actual = actual.siguiente;
        }

        html.append("</table></body></html>");
        return escribirYAbrirReporte(getTimestampArchivo() + "_Inventario.html", html.toString());
    }

    // Genera un reporte de las ventas realizadas
    public String generarReporteVentas() {
        StringBuilder html = new StringBuilder();
        html.append(crearInicioReporte("Reporte de Ventas"));
        html.append("<table><tr><th>Fecha</th><th>Detalle</th><th>Total</th></tr>");

        NodoSimple<Compra> actual = historialCompras.getCabeza();
        while (actual != null) {
            Compra compra = actual.dato;
            html.append("<tr>")
                    .append("<td>").append(escapeHtml(compra.getFechaHora())).append("</td>")
                    .append("<td>").append(escapeHtml(compra.getDetalle())).append("</td>")
                    .append("<td>Q").append(recompensaService.formatearMonto(compra.getTotal())).append("</td>")
                    .append("</tr>");
            actual = actual.siguiente;
        }

        html.append("</table></body></html>");
        return escribirYAbrirReporte(getTimestampArchivo() + "_Ventas.html", html.toString());
    }

    // Genera un reporte del estado actual del álbum de cartas
    public String generarReporteAlbum() {
        StringBuilder html = new StringBuilder();
        html.append(crearInicioReporte("Reporte del Album"));
        html.append("<table>");

        NodoMatriz filaActual = album.getInicio();
        while (filaActual != null) {
            html.append("<tr>");
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                if (columnaActual.getCarta() == null) {
                    html.append("<td class='vacia'>Vacia</td>");
                } else {
                    Carta carta = columnaActual.getCarta();
                    String clase = carta.getRareza().equalsIgnoreCase("Legendaria") ? "legendaria" : "";
                    html.append("<td class='").append(clase).append("'>")
                            .append("<strong>").append(escapeHtml(carta.getNombre())).append("</strong><br>")
                            .append("Tipo: ").append(escapeHtml(carta.getTipo())).append("<br>")
                            .append("Rareza: ").append(escapeHtml(carta.getRareza()))
                            .append("</td>");
                }

                columnaActual = columnaActual.getDerecha();
            }

            html.append("</tr>");
            filaActual = filaActual.getAbajo();
        }

        html.append("</table></body></html>");
        return escribirYAbrirReporte(getTimestampArchivo() + "_Album.html", html.toString());
    }

    // Genera un reporte de los torneos disponibles y los tickets vendidos
    public String generarReporteTorneos() {
        StringBuilder html = new StringBuilder();
        html.append(crearInicioReporte("Reporte de Torneos"));
        html.append("<h2>Torneos</h2>");
        html.append("<table><tr><th>ID</th><th>Nombre</th><th>Juego</th><th>Fecha</th><th>Hora</th><th>Precio</th><th>Tickets Restantes</th></tr>");

        NodoSimple<Torneo> torneoActual = torneos.getCabeza();
        while (torneoActual != null) {
            Torneo torneo = torneoActual.dato;
            html.append("<tr>")
                    .append("<td>").append(escapeHtml(torneo.getId())).append("</td>")
                    .append("<td>").append(escapeHtml(torneo.getNombre())).append("</td>")
                    .append("<td>").append(escapeHtml(torneo.getJuego())).append("</td>")
                    .append("<td>").append(escapeHtml(torneo.getFecha())).append("</td>")
                    .append("<td>").append(escapeHtml(torneo.getHora())).append("</td>")
                    .append("<td>Q").append(recompensaService.formatearMonto(torneo.getPrecioTicket())).append("</td>")
                    .append("<td>").append(torneo.getTicketsDisponibles()).append("</td>")
                    .append("</tr>");
            torneoActual = torneoActual.siguiente;
        }

        html.append("</table>");
        html.append("<h2>Tickets Vendidos</h2>");
        html.append("<table><tr><th>Fecha</th><th>Torneo</th><th>Comprador</th><th>Precio</th><th>Taquilla</th></tr>");

        NodoSimple<TicketVenta> ticketActual = ticketsVendidos.getCabeza();
        while (ticketActual != null) {
            TicketVenta ticket = ticketActual.dato;
            html.append("<tr>")
                    .append("<td>").append(escapeHtml(ticket.getFechaHora())).append("</td>")
                    .append("<td>").append(escapeHtml(ticket.getTorneoNombre())).append("</td>")
                    .append("<td>").append(escapeHtml(ticket.getComprador())).append("</td>")
                    .append("<td>Q").append(recompensaService.formatearMonto(ticket.getPrecio())).append("</td>")
                    .append("<td>").append(escapeHtml(ticket.getTaquilla())).append("</td>")
                    .append("</tr>");
            ticketActual = ticketActual.siguiente;
        }

        html.append("</table></body></html>");
        return escribirYAbrirReporte(getTimestampArchivo() + "_Torneos.html", html.toString());
    }

    // Crea la estructura inicial del reporte HTML con un título y estilos básicos
    private String crearInicioReporte(String titulo) {
        return "<html><head><meta charset='UTF-8'><title>" + titulo + "</title><style>"
                + "body{font-family:Arial,sans-serif;background:#f4f7fb;color:#1f2937;padding:30px;}"
                + "h1,h2{color:#0f172a;}"
                + "table{width:100%;border-collapse:collapse;background:white;margin-top:20px;}"
                + "th,td{border:1px solid #d1d5db;padding:10px;text-align:left;}"
                + "th{background:#1d4ed8;color:white;}"
                + ".vacia{background:#d1d5db;color:#374151;text-align:center;font-weight:bold;}"
                + ".legendaria{background:#fbbf24;}"
                + "</style></head><body><h1>" + titulo + "</h1>";
    }

    // Escribe el contenido HTML en un archivo y lo abre automáticamente en el navegador
    private String escribirYAbrirReporte(String nombreArchivo, String contenido) {
        File archivo = new File(rutaReportes + File.separator + nombreArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(contenido);
            writer.close();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }

            return archivo.getAbsolutePath();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible generar el reporte HTML.", error);
        }
    }

    // Escapa caracteres especiales para evitar problemas de formato en el HTML
    private String escapeHtml(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /* GETTERS */
    // Genera un timestamp para el nombre del archivo del reporte
    private String getTimestampArchivo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        return LocalDateTime.now().format(formato);
    }
}