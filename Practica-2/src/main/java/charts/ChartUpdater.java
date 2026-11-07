package charts;

public interface ChartUpdater {
    void actualizar(int[] arreglo);
    void actualizarConEstados(int[] arreglo, int indice1, int indice2, String estado);
    void actualizarConPivote(int[] arreglo, int pivote);
}