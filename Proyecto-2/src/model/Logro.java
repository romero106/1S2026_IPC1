package model;

public class Logro {

    /* ATRIBUTOS */
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean desbloqueado;

    /* CONSTRUCTOR */
    public Logro(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /* GETTERS Y SETTERS */
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }
}