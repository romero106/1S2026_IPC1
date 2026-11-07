package stats;

import gui.LogUpdater;

public class OperationLogger {

    private LogUpdater salida;

    public OperationLogger(LogUpdater salida) {
        this.salida = salida;
    }

    public void logComparacion(int i, int j) {
        salida.agregarLog("Comparando posiciones " + i + " y " + j);
    }

    public void logIntercambio(int i, int j) {
        salida.agregarLog("Intercambio entre posiciones " + i + " y " + j);
    }

    public void logBubbleRecursion(int n) {
        salida.agregarLog("Llamada recursiva BubbleSort con n = " + n);
    }

    public void logQuickSortRango(int low, int high) {
        salida.agregarLog("QuickSort en rango [" + low + ", " + high + "]");
    }

    public void logPivote(int posicion, int valor) {
        salida.agregarLog("Pivote seleccionado en posicion " + posicion + " con valor " + valor);
    }

    public void logComparacionConPivote(int posicion) {
        salida.agregarLog("Comparando posicion " + posicion + " con pivote");
    }

    public void logPivoteFinal(int posicion) {
        salida.agregarLog("Pivote colocado en posicion " + posicion);
    }

    public void logGap(int gap) {
        salida.agregarLog("Nuevo gap: " + gap);
    }

    public void logMovimiento(int posicion) {
        salida.agregarLog("Movimiento hacia posicion " + posicion);
    }

    public void logInsercion(int posicion) {
        salida.agregarLog("Elemento insertado en posicion " + posicion);
    }
}
