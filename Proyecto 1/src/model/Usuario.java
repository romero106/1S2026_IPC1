package model;

public class Usuario {
    // ATRIBUTOS
    String nombre;
    String carne;
    String carrera;
    String contrasena;
    String rol;
    // CONSTRUCTOR
    public Usuario(String carne, String nombre, String carrera, String contrasena, String rol) {
        this.carne = carne;
        this.nombre = nombre;
        this.carrera = carrera; 
        this.contrasena = contrasena;
        this.rol = rol;
    }
    // METODOS
    public void Informacion() {
        System.out.println("Rol: " + rol);        
        System.out.println("Carné: " + carne);
        System.out.println("Nombre: " + nombre);
        System.out.println("Carrera: " + carrera);
    }

    public boolean validarContrasena(String contrasenaIngresada) {
        if(contrasena.equals(contrasenaIngresada)) {
            return true;
        } else {
            return false;
        }
    }
    // GETTERS
    public String getRol() {
        return rol;
    }
    public String getCarne() {
        return carne;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getInformacion() {
        return 
        "Carné: " + carne + 
        "\nNombre: " + nombre + 
        "\nCarrera: " + carrera +
        "\n";
    }
}