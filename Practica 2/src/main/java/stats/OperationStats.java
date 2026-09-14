package stats;

public class OperationStats {
    
    /* ATRIBUTOS */
    private int comparaciones;
    private int intercambios;
    private int llamadasRecursivas;

    private long tiempoInicio;
    private long tiempoFin;

    /* METODOS */
    public void reiniciarStats() {
        comparaciones = 0;
        intercambios = 0;
        llamadasRecursivas = 0;
        tiempoInicio = 0;
        tiempoFin = 0;
    }

    public void incrementarComparaciones() {
        comparaciones++;
    }

    public void incrementarIntercambios() {
        intercambios++;
    }

    public void incrementarLlamadasRecursivas() {
        llamadasRecursivas++;
    }

    public void iniciarTiempo() {
        tiempoInicio = System.currentTimeMillis();
    }

    public void finalizarTiempo() {
        tiempoFin = System.currentTimeMillis();
    }

    /* GETTERS */
    public int getComparaciones() {
        return comparaciones;
    }

    public int getIntercambios() {
        return intercambios;
    }

    public int getLlamadasRecursivas() {
        return llamadasRecursivas;
    }

    public long getTiempoTotal() {
        return tiempoFin - tiempoInicio;
    }
}
