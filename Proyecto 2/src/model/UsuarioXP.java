package model;

public class UsuarioXP {

    /* ATRIBUTOS */
    private String nombre;
    private int experiencia;

    /* CONSTRUCTOR */
    public UsuarioXP(String nombre, int experiencia) {
        this.nombre = nombre;
        this.experiencia = experiencia;
    }

    /* GETTERS*/
    public String getNombre() {
        return nombre;
    }

    public int getExperiencia() {
        return experiencia;
    }
}