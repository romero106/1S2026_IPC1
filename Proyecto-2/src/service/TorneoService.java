package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.*;
import util.*;

public class TorneoService {

    /* ATRIBUTOS */
    private ListaSimple<Torneo> torneos;
    private ListaSimple<TicketVenta> ticketsVendidos;
    private UsuarioActual usuarioActual;
    private RecompensaService recompensaService;

    /* CONSTRUCTOR */
    public TorneoService(ListaSimple<Torneo> torneos, ListaSimple<TicketVenta> ticketsVendidos, UsuarioActual usuarioActual, RecompensaService recompensaService) {
        this.torneos = torneos;
        this.ticketsVendidos = ticketsVendidos;
        this.usuarioActual = usuarioActual;
        this.recompensaService = recompensaService;
    }

    /* MÉTODOS */
    // Inscribe a un jugador en la cola de espera de un torneo, verificando condiciones básicas
    public String inscribirEnTorneo(Torneo torneo, String nombreJugador) {
        if (torneo == null) {
            return "Selecciona un torneo.";
        }

        if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
            return "Debes escribir un nombre valido.";
        }

        if (torneo.getTicketsDisponibles() <= 0) {
            return "Ya no hay tickets disponibles para este torneo.";
        }

        torneo.getColaEspera().encolar(nombreJugador.trim());
        return "Jugador agregado a la cola del torneo.";
    }

    // Procesa la venta de un ticket para un torneo, actualizando inventario, historial y recompensas
    public synchronized TicketVenta procesarVentaTicket(Torneo torneo, String comprador, String taquilla) {
        if (torneo == null || comprador == null) {
            return null;
        }

        if (!torneo.venderUno()) {
            return null;
        }

        TicketVenta ticket = new TicketVenta(
                torneo.getId(),
                torneo.getNombre(),
                comprador,
                getTimestampBonito(),
                torneo.getPrecioTicket(),
                taquilla
        );

        ticketsVendidos.insertarAlInicio(ticket);
        usuarioActual.setTorneosComprados(usuarioActual.getTorneosComprados() + 1);
        usuarioActual.registrarTorneo(torneo.getId());
        recompensaService.agregarExperiencia(150);
        recompensaService.verificarLogros(true);
        return ticket;
    }

    /* GETTERS */
    // Obtiene un arreglo de nombres de torneos para mostrar en un combo box
    public String[] getTorneosParaCombo() {
        String[] nombres = new String[torneos.tamanio()];
        NodoSimple<Torneo> actual = torneos.getCabeza();
        int indice = 0;

        while (actual != null) {
            nombres[indice] = actual.dato.getNombre() + " - " + actual.dato.getJuego();
            actual = actual.siguiente;
            indice++;
        }

        return nombres;
    }

    // Obtiene un torneo por su índice en la lista
    public Torneo getTorneoPorIndice(int indice) {
        return torneos.obtener(indice);
    }

    // Obtiene el texto de la cola de espera de un torneo para mostrar en un área de texto
    public String getTextoCola(Torneo torneo) {
        if (torneo == null) {
            return "Sin torneo seleccionado.";
        }

        if (torneo.getColaEspera().estaVacia()) {
            return "La cola esta vacia.";
        }

        String texto = "";
        NodoCola<String> actual = torneo.getColaEspera().getFrente();
        int posicion = 1;

        while (actual != null) {
            texto += posicion + ". " + actual.dato + "\n";
            actual = actual.siguiente;
            posicion++;
        }

        return texto;
    }

    /* GETTERS */
    // Genera un timestamp para el nombre del archivo del reporte
    private String getTimestampBonito() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formato);
    }
}