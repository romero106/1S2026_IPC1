package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Prestamo {
    // ATRIBUTOS
    private String codigoPrestamo;
    private Usuario user;
    private Libro libro;
    private String fechaPrestamo;
    private String fechaLimite;
    private String fechaDevolucion;
    private String estado;
    
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato de fecha
    // CONSTRUCTOR
    public Prestamo(String codigoPrestamo, Usuario user, Libro libro, String fechaPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
        this.user = user;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = calcularFechaLimite(fechaPrestamo);
        this.fechaDevolucion = "";
        this.estado = "ACTIVO";
    }
    // METODOS
    public void Informacion() {
        System.out.println("Código: " + codigoPrestamo);
        System.out.println("Usuario: " + user.getCarne());
        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Fecha préstamo: " + fechaPrestamo);
        System.out.println("Fecha límite: " + fechaLimite);
        System.out.println("Fecha devolución: " + fechaDevolucion);
        System.out.println("Estado: " + estado);
    }

    private String calcularFechaLimite(String fechaPrestamo) {
        try {
            LocalDate fecha = LocalDate.parse(fechaPrestamo, FORMATO);
            LocalDate limite = fecha.plusDays(15);
            return limite.format(FORMATO);
        } catch(Exception e) {
            return "Fecha inválida";
        }
    }

    public boolean registrarDevolucion(String fechaDevolucion) {
        if(estado.equals("ACTIVO")) {
            libro.devolverLibro();
            this.fechaDevolucion = fechaDevolucion;
            this.estado = "DEVUELTO";
            return true;
        }
        return false;
    }

    public boolean estaActivo() {
        return estado.equals("ACTIVO");
    }

    public boolean estaVencido() {
        if(!estado.equals("ACTIVO")) {
            return false;
        }

        try {
            LocalDate hoy = LocalDate.now();
            LocalDate limite = LocalDate.parse(fechaLimite, FORMATO);
            return hoy.isAfter(limite);
        } catch(Exception e) {
            return false;
        }
    }
    // GETTERS
    public String getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public String getCarneUsuario() {
        return user.getCarne();
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return user;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public String getInformacion() {
        return "Código: " + codigoPrestamo +
                "\nCarné: " + user.getCarne() +
                "\nNombre: " + user.getNombre() +
                "\nLibro: " + libro.getTitulo() +
                "\nFecha préstamo: " + fechaPrestamo +
                "\nFecha límite: " + fechaLimite +
                "\nFecha devolución: " + (fechaDevolucion.equals("") ? "Pendiente" : fechaDevolucion) +
                "\nEstado: " + estado +
                "\nVencido: " + (estaVencido() ? "Sí" : "No") +
                "\n";
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}