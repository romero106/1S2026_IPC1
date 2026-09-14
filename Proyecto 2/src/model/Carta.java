package model;

public class Carta {

    /* ATRIBUTOS */
    private String codigo;
    private String nombre;
    private String tipo;
    private String rareza;
    private int ataque;
    private int defensa;
    private int puntosSalud;
    private String imagen;

    /* CONSTRUCTOR */
    public Carta(String codigo, String nombre, String tipo, String rareza, int ataque, int defensa, int puntosSalud,
            String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.ataque = ataque;
        this.defensa = defensa;
        this.puntosSalud = puntosSalud;
        this.imagen = imagen;
    }

    /* GETTERS */
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRareza() {
        return rareza;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getPuntosSalud() {
        return puntosSalud;
    }

    public String getImagen() {
        return imagen;
    }
}