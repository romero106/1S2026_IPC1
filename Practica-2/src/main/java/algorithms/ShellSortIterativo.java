package algorithms;

import charts.*;
import stats.*;
import threads.*;

public class ShellSortIterativo {

    public static void ordenar(int[] arreglo, String orden, OperationStats stats, OperationLogger logger,
            ChartUpdater callback, SortingThread hilo) {
        int n = arreglo.length;
        boolean ascendente;

        if (orden.equals("Ascendente")) {
            ascendente = true;
        } else {
            ascendente = false;
        }

        for (int gap = n / 2; gap > 0 && hilo.corriendo(); gap /= 2) {
            logger.logGap(gap);
            for (int i = gap; i < n && hilo.corriendo(); i++) {
                int aux = arreglo[i];
                int j = i;

                while (j >= gap && hilo.corriendo()) {
                    callback.actualizarConEstados(arreglo, j, j - gap, "comparando");
                    hilo.pausar();

                    stats.incrementarComparaciones();
                    logger.logComparacion(j - gap, j);

                    if (ascendente) {
                        if (arreglo[j - gap] > aux) {
                            arreglo[j] = arreglo[j - gap];

                            callback.actualizarConEstados(arreglo, j, j - gap, "intercambiando");
                            hilo.pausar();

                            stats.incrementarIntercambios();
                            logger.logMovimiento(j);

                            j -= gap;
                        } else {
                            break;
                        }
                    } else {
                        if (arreglo[j - gap] < aux) {
                            arreglo[j] = arreglo[j - gap];

                            callback.actualizarConEstados(arreglo, j, j - gap, "intercambiando");
                            hilo.pausar();

                            stats.incrementarIntercambios();
                            logger.logMovimiento(j);

                            j -= gap;
                        } else {
                            break;
                        }
                    }
                }
                arreglo[j] = aux;
                callback.actualizar(arreglo);
                hilo.pausar();
                logger.logInsercion(j);
            }
        }
        callback.actualizar(arreglo);
    }
}
