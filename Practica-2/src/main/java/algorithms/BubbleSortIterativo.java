package algorithms;

import charts.*;
import stats.*;
import threads.*;

public class BubbleSortIterativo {

    public static void ordenar(int[] arreglo, String orden, OperationStats stats, OperationLogger logger,
            ChartUpdater callback, SortingThread hilo) {
        int n = arreglo.length;
        boolean ascendente;

        if (orden.equals("Ascendente")) {
            ascendente = true;
        } else {
            ascendente = false;
        }

        for (int i = 0; i < n - 1 && hilo.corriendo(); i++) {
            for (int j = 0; j < n - i - 1 && hilo.corriendo(); j++) {
                callback.actualizarConEstados(arreglo, j, j + 1, "comparando");
                hilo.pausar();
                
                stats.incrementarComparaciones();
                logger.logComparacion(j, j + 1);
                
                if (ascendente) {
                    if (arreglo[j] > arreglo[j + 1]) {
                        callback.actualizarConEstados(arreglo, j, j + 1, "intercambiando");
                        hilo.pausar();

                        int aux = arreglo[j];
                        arreglo[j] = arreglo[j + 1];
                        arreglo[j + 1] = aux;

                        callback.actualizarConEstados(arreglo, j, j + 1, "intercambiando");
                        hilo.pausar();

                        stats.incrementarIntercambios();
                        logger.logIntercambio(j, j + 1);
                    }
                } else {
                    if (arreglo[j] < arreglo[j + 1]) {
                        callback.actualizarConEstados(arreglo, j, j + 1, "intercambiando");
                        hilo.pausar();

                        int aux = arreglo[j];
                        arreglo[j] = arreglo[j + 1];
                        arreglo[j + 1] = aux;

                        callback.actualizarConEstados(arreglo, j, j + 1, "intercambiando");
                        hilo.pausar();

                        stats.incrementarIntercambios();
                        logger.logIntercambio(j, j + 1);
                    }
                }
            }

            callback.actualizar(arreglo);
        }
    }
}
