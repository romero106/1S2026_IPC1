package model;

public class Estudiante {

    /* ATRIBUTOS */
    private String nombreCompleto;
    private String carnet;
    private String correo;
    private String seccion;
    private String semestre;
    private String descripcionProyecto;

    /* CONSTRUCTOR */
    public Estudiante(String nombreCompleto, String carnet, String correo, String seccion, String semestre, String descripcionProyecto) {
        this.nombreCompleto = nombreCompleto;
        this.carnet = carnet;
        this.correo = correo;
        this.seccion = seccion;
        this.semestre = semestre;
        this.descripcionProyecto = descripcionProyecto;
    }

    /* GETTERS */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getCorreo() {
        return correo;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }
}