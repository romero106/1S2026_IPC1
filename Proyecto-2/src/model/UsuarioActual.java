package model;

public class UsuarioActual {

    /* ATRIBUTOS */
    private String nombre;
    private int experiencia;
    private int comprasRealizadas;
    private int cartasAgregadas;
    private int filasCompletas;
    private int cartasLegendarias;
    private int torneosComprados;
    private double gastoTotal;
    private String torneosDistintos;

    /* CONSTRUCTOR */
    public UsuarioActual(String nombre) {
        this.nombre = nombre;
        this.torneosDistintos = "|";
    }

    /* GETTERS Y SETTERS */
    public String getNombre() {
        return nombre;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void setComprasRealizadas(int comprasRealizadas) {
        this.comprasRealizadas = comprasRealizadas;
    }

    public int getCartasAgregadas() {
        return cartasAgregadas;
    }

    public void setCartasAgregadas(int cartasAgregadas) {
        this.cartasAgregadas = cartasAgregadas;
    }

    public int getFilasCompletas() {
        return filasCompletas;
    }

    public void setFilasCompletas(int filasCompletas) {
        this.filasCompletas = filasCompletas;
    }

    public int getCartasLegendarias() {
        return cartasLegendarias;
    }

    public void setCartasLegendarias(int cartasLegendarias) {
        this.cartasLegendarias = cartasLegendarias;
    }

    public int getTorneosComprados() {
        return torneosComprados;
    }

    public void setTorneosComprados(int torneosComprados) {
        this.torneosComprados = torneosComprados;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(double gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public String getTorneosDistintos() {
        return torneosDistintos;
    }

    public void setTorneosDistintos(String torneosDistintos) {
        this.torneosDistintos = torneosDistintos;
    }

    public void sumarExperiencia(int puntos) {
        experiencia += puntos;
    }

    public boolean tieneTorneoRegistrado(String torneoId) {
        return torneosDistintos.contains("|" + torneoId + "|");
    }

    public void registrarTorneo(String torneoId) {
        if (!tieneTorneoRegistrado(torneoId)) {
            torneosDistintos += torneoId + "|";
        }
    }
}