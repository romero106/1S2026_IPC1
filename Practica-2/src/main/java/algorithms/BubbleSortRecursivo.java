package algorithms;

import charts.*;
import stats.*;
import threads.*;

public class BubbleSortRecursivo {

    public static void ordenar(int[] arreglo, int n, String orden, OperationStats stats, OperationLogger logger,
            ChartUpdater callback, SortingThread hilo) {
        boolean ascendente;

        if (orden.equals("Ascendente")) {
            ascendente = true;
        } else {
            ascendente = false;
        }

        stats.incrementarLlamadasRecursivas();
        logger.logBubbleRecursion(n);

        if (!hilo.corriendo()) {
            return;
        }

        if (n == 1) {
            return;
        }
        
        for (int i = 0; i < n - 1 && hilo.corriendo(); i++) {
            callback.actualizarConEstados(arreglo, i, i + 1, "comparando");
            hilo.pausar();

            stats.incrementarComparaciones();
            logger.logComparacion(i, i + 1);

            if (ascendente) {
                if (arreglo[i] > arreglo[i + 1]) {
                    callback.actualizarConEstados(arreglo, i, i + 1, "intercambiando");
                    hilo.pausar();

                    int aux = arreglo[i];
                    arreglo[i] = arreglo[i + 1];
                    arreglo[i + 1] = aux;

                    callback.actualizar(arreglo);
                    hilo.pausar();

                    stats.incrementarIntercambios();
                    logger.logIntercambio(i, i + 1);
                }
            } else {
                if (arreglo[i] < arreglo[i + 1]) {
                    callback.actualizarConEstados(arreglo, i, i + 1, "intercambiando");
                    hilo.pausar();

                    int aux = arreglo[i];
                    arreglo[i] = arreglo[i + 1];
                    arreglo[i + 1] = aux;

                    callback.actualizar(arreglo);
                    hilo.pausar();

                    stats.incrementarIntercambios();
                    logger.logIntercambio(i, i + 1);
                }
            }
        }

        if (!hilo.corriendo()) {
            return;
        }

        ordenar(arreglo, n - 1, orden, stats, logger, callback, hilo);
    }
}
