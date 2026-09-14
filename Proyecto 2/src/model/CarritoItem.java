package model;

public class CarritoItem {

    /* ATRIBUTOS */
    private Juego juego;
    private int cantidad;

    /* CONSTRUCTOR */
    public CarritoItem(Juego juego, int cantidad) {
        this.juego = juego;
        this.cantidad = cantidad;
    }

    /* GETTERS Y SETTERS */
    public Juego getJuego() {
        return juego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return juego.getPrecio() * cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}