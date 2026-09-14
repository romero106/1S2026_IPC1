package algorithms;

import charts.*;
import stats.*;
import threads.*;

public class QuickSortRecursivo {

    public static void ordenar(int[] arreglo, int low, int high, String orden, OperationStats stats,
            OperationLogger logger, ChartUpdater callback, SortingThread hilo) {
        stats.incrementarLlamadasRecursivas();

        if (!hilo.corriendo()) {
            return;
        }
        if (low < high) {
            logger.logQuickSortRango(low, high);
            int indicePivote = partition(arreglo, low, high, orden, stats, logger, callback, hilo);

            if (!hilo.corriendo()) {
                return;
            }

            ordenar(arreglo, low, indicePivote - 1, orden, stats, logger, callback, hilo);
            ordenar(arreglo, indicePivote + 1, high, orden, stats, logger, callback, hilo);
        }
    }

    public static int partition(int[] arreglo, int low, int high, String orden, OperationStats stats,
            OperationLogger logger, ChartUpdater callback, SortingThread hilo) {
        int pivote = arreglo[high];
        int i = low - 1;
        boolean ascendente;

        if (orden.equals("Ascendente")) {
            ascendente = true;
        } else {
            ascendente = false;
        }

        callback.actualizarConPivote(arreglo, high);
        hilo.pausar();
        logger.logPivote(high, pivote);

        
        for (int j = low; j < high && hilo.corriendo(); j++) {
            callback.actualizarConEstados(arreglo, j, -1, "comparando");
            hilo.pausar();

            stats.incrementarComparaciones();
            logger.logComparacionConPivote(j);

            if (ascendente) {
                if (arreglo[j] < pivote) {
                    i++;

                    callback.actualizarConEstados(arreglo, i, j, "intercambiando");
                    hilo.pausar();

                    int aux = arreglo[i];
                    arreglo[i] = arreglo[j];
                    arreglo[j] = aux;

                    callback.actualizarConEstados(arreglo, i, j, "intercambiando");
                    hilo.pausar();

                    stats.incrementarIntercambios();
                    logger.logIntercambio(i, j);
                }
            } else {
                if (arreglo[j] > pivote) {
                    i++;

                    callback.actualizarConEstados(arreglo, i, j, "intercambiando");
                    hilo.pausar();

                    int aux = arreglo[i];
                    arreglo[i] = arreglo[j];
                    arreglo[j] = aux;

                    callback.actualizarConEstados(arreglo, i, j, "intercambiando");
                    hilo.pausar();

                    stats.incrementarIntercambios();
                    logger.logIntercambio(i, j);
                }
            }
        }

        if (!hilo.corriendo()) {
            return i + 1;
        }

        callback.actualizarConEstados(arreglo, i + 1, high, "intercambiando");
        hilo.pausar();

        int aux = arreglo[i + 1];
        arreglo[i + 1] = arreglo[high];
        arreglo[high] = aux;

        callback.actualizarConEstados(arreglo, i + 1, -1, "pivote_fijo");
        hilo.pausar();

        stats.incrementarIntercambios();
        logger.logPivoteFinal(i + 1);

        return i + 1;
    }   
}
