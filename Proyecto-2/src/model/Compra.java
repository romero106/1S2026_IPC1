package model;

public class Compra {
    
    /* ATRIBUTOS */
    private String fechaHora;
    private String detalle;
    private double total;

    /* CONSTRUCTOR */
    public Compra(String fechaHora, String detalle, double total) {
        this.fechaHora = fechaHora;
        this.detalle = detalle;
        this.total = total;
    }

    /* GETTERS */
    public String getFechaHora() {
        return fechaHora;
    }

    public String getDetalle() {
        return detalle;
    }

    public double getTotal() {
        return total;
    }
}