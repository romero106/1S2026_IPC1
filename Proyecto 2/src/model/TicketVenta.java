package model;

public class TicketVenta {

    /* ATRIBUTOS */
    private String torneoId;
    private String torneoNombre;
    private String comprador;
    private String fechaHora;
    private double precio;
    private String taquilla;

    /* CONSTRUCTOR */
    public TicketVenta(String torneoId, String torneoNombre, String comprador, String fechaHora, double precio, String taquilla) {
        this.torneoId = torneoId;
        this.torneoNombre = torneoNombre;
        this.comprador = comprador;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.taquilla = taquilla;
    }

    /* GETTERS */
    public String getTorneoId() {
        return torneoId;
    }

    public String getTorneoNombre() {
        return torneoNombre;
    }

    public String getComprador() {
        return comprador;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTaquilla() {
        return taquilla;
    }
}