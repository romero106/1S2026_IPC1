package model;

public class Libro {
    // ATRIBUTOS
    private String codigo;
    private String titulo;
    private String autor;
    private int anio;
    private int cantidad;
    // CONSTRUCTOR
    public Libro(String codigo, String titulo, String autor, int anio, int cantidad) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.cantidad = cantidad;
    }
    // METODOS
    public void Informacion() {
        System.out.println("Código: " + codigo);
        System.out.println("Titulo: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Año: " + anio);
        System.out.println("Ejemplares disponibles: " + cantidad);
    }

    public boolean prestarLibro() {
        if(cantidad > 0) {
            cantidad--;
            return true;
        } else {
            return false;
        }
    }

    public void devolverLibro() {
        cantidad++;
    }

    // GETTERS Y SETTERS
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getInformacion() {
        return "Codigo: " + codigo +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor +
                "\nAño: " + anio +
                "\nEjemplares disponibles: " + cantidad +
                "\n";
    }
}