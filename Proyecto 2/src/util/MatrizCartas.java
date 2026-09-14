package util;

import model.Carta;

public class MatrizCartas {

    /* ATRIBUTOS */
    private int filas;
    private int columnas;
    private NodoMatriz inicio;

    /* CONSTRUCTOR */
    public MatrizCartas(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        construirMatriz();
    }

    /* MÉTODOS */
    private void construirMatriz() {
        NodoMatriz filaAnterior = null;
        NodoMatriz primerNodoFilaActual = null;

        for (int fila = 0; fila < filas; fila++) {
            NodoMatriz actual = null;
            NodoMatriz anterior = null;
            NodoMatriz arriba = filaAnterior;

            for (int columna = 0; columna < columnas; columna++) {
                actual = new NodoMatriz(fila, columna);

                if (inicio == null) {
                    inicio = actual;
                }

                if (anterior == null) {
                    primerNodoFilaActual = actual;
                } else {
                    anterior.setDerecha(actual);
                    actual.setIzquierda(anterior);
                }

                if (arriba != null) {
                    arriba.setAbajo(actual);
                    actual.setArriba(arriba);
                    arriba = arriba.getDerecha();
                }

                anterior = actual;
            }

            filaAnterior = primerNodoFilaActual;
        }
    }

    public NodoMatriz getNodo(int fila, int columna) {
        if (fila < 0 || columna < 0 || fila >= filas || columna >= columnas) {
            return null;
        }

        NodoMatriz actualFila = inicio;
        int filaActual = 0;

        while (actualFila != null && filaActual < fila) {
            actualFila = actualFila.getAbajo();
            filaActual++;
        }

        NodoMatriz actualColumna = actualFila;
        int columnaActual = 0;

        while (actualColumna != null && columnaActual < columna) {
            actualColumna = actualColumna.getDerecha();
            columnaActual++;
        }

        return actualColumna;
    }

    public NodoMatriz getInicio() {
        return inicio;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public NodoMatriz buscarPrimeraVacia() {
        NodoMatriz filaActual = inicio;

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                if (columnaActual.getCarta() == null) {
                    return columnaActual;
                }
                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        return null;
    }

    public boolean agregarCarta(Carta carta) {
        NodoMatriz vacio = buscarPrimeraVacia();

        if (vacio == null) {
            return false;
        }

        vacio.setCarta(carta);
        return true;
    }

    public void intercambiar(int filaUno, int columnaUno, int filaDos, int columnaDos) {
        NodoMatriz primero = getNodo(filaUno, columnaUno);
        NodoMatriz segundo = getNodo(filaDos, columnaDos);

        if (primero == null || segundo == null) {
            return;
        }

        Carta temporal = primero.getCarta();
        primero.setCarta(segundo.getCarta());
        segundo.setCarta(temporal);
    }

    public int contarCartasEnFila(int fila) {
        NodoMatriz actual = getNodo(fila, 0);
        int total = 0;

        while (actual != null) {
            if (actual.getCarta() != null) {
                total++;
            }
            actual = actual.getDerecha();
        }

        return total;
    }
}