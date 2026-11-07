package threads;

import javax.swing.SwingUtilities;

import algorithms.*;
import charts.*;
import stats.*;

public class SortingThread extends Thread {
    
    /* ATRIBUTOS */
    private int[] arreglo;
    private String algoritmo;
    private String variante;
    private int velocidad;
    private String orden;
    private OperationStats stats;
    private OperationLogger logger;
    private ChartUpdater actualizador;
    private volatile boolean corriendo = true;
    private Runnable alTerminar;

    /* CONSTRUCTOR */
    public SortingThread(int[] arreglo, String algoritmo, String variante, int velocidad, String orden,
            OperationStats stats, OperationLogger logger, ChartUpdater actualizador, Runnable alTerminar) {
        this.arreglo = arreglo;
        this.algoritmo = algoritmo;
        this.variante = variante;
        this.velocidad = velocidad;
        this.orden = orden;
        this.stats = stats;
        this.logger = logger;
        this.actualizador = actualizador;
        this.alTerminar = alTerminar;
    }

    /* METODOS */
    // Metodo para ejecutar el algoritmo de ordenamiento
    @Override
    public void run() {
        stats.iniciarTiempo();
        switch (algoritmo) {
            case "BubbleSort":
                if(variante.equals("Iterativo")) {
                    BubbleSortIterativo.ordenar(arreglo, orden, stats, logger, actualizador, this);
                } else if (variante.equals("Recursivo")) {
                    BubbleSortRecursivo.ordenar(arreglo, arreglo.length, orden, stats, logger, actualizador, this);
                }
                break;
            case "QuickSort":
                QuickSortRecursivo.ordenar(arreglo, 0, arreglo.length - 1, orden, stats, logger, actualizador, this);
                break;

            case "ShellSort":
                ShellSortIterativo.ordenar(arreglo, orden, stats, logger, actualizador, this);
                break;
        }
        stats.finalizarTiempo();
        actualizador.actualizar(arreglo);

        if (alTerminar != null) {
            SwingUtilities.invokeLater(alTerminar);
        }
    }

    // Metodo para pausar el algoritmo de ordenamiento
    public void pausar() {
        try {
            Thread.sleep(velocidad);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Metodo para detener el algoritmo de ordenamiento
    public void detener() {
        corriendo = false;
        interrupt();
    }

    // Metodo para saber si el hilo esta corriendo
    public boolean corriendo() {
        return corriendo;
    }

    /* GETTERS */
    public int getVelocidad() {
        return velocidad;
    }
}
