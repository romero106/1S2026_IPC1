package model;

import util.Cola;

public class Torneo {

    /* ATRIBUTOS */
    private String id;
    private String nombre;
    private String juego;
    private String fecha;
    private String hora;
    private double precioTicket;
    private int ticketsDisponibles;
    private Cola<String> colaEspera;

    /* CONSTRUCTOR */
    public Torneo(String id, String nombre, String juego, String fecha, String hora, double precioTicket, int ticketsDisponibles) {
        this.id = id;
        this.nombre = nombre;
        this.juego = juego;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTicket = precioTicket;
        this.ticketsDisponibles = ticketsDisponibles;
        this.colaEspera = new Cola<String>();
    }

    /* GETTERS Y SETTERS */
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getJuego() {
        return juego;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public double getPrecioTicket() {
        return precioTicket;
    }

    public int getTicketsDisponibles() {
        return ticketsDisponibles;
    }

    public void setTicketsDisponibles(int ticketsDisponibles) {
        this.ticketsDisponibles = ticketsDisponibles;
    }

    public Cola<String> getColaEspera() {
        return colaEspera;
    }

    public synchronized boolean venderUno() {
        if (ticketsDisponibles <= 0) {
            return false;
        }

        ticketsDisponibles--;
        return true;
    }
}
