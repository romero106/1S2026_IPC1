package model;

public class Juego {

    /* ATRIBUTOS */
    private String codigo;
    private String nombre;
    private String genero;
    private double precio;
    private String plataforma;
    private int stock;
    private String descripcion;

    /* CONSTRUCTOR */
    public Juego(String codigo, String nombre, String genero, double precio, String plataforma, int stock, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
        this.plataforma = plataforma;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    /* GETTERS Y SETTERS */
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecio() {
        return precio;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public int getStock() {
        return stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}