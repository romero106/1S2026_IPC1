package util;

public class ListaSimple<T> {

    /* ATRIBUTOS */
    private NodoSimple<T> cabeza;
    private NodoSimple<T> cola;
    private int tamanio;

    /* MÉTODOS */
    public void insertarAlInicio(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<T>(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;

        if (cola == null) {
            cola = nuevo;
        }

        tamanio++;
    }

    public void insertarAlFinal(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<T>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }

        tamanio++;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }

        NodoSimple<T> actual = cabeza;
        int posicion = 0;

        while (actual != null) {
            if (posicion == indice) {
                return actual.dato;
            }
            actual = actual.siguiente;
            posicion++;
        }

        return null;
    }

    public boolean eliminarPorIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return false;
        }

        if (indice == 0) {
            cabeza = cabeza.siguiente;
            if (cabeza == null) {
                cola = null;
            }
            tamanio--;
            return true;
        }

        NodoSimple<T> anterior = cabeza;
        int posicion = 0;

        while (anterior != null && posicion < indice - 1) {
            anterior = anterior.siguiente;
            posicion++;
        }

        if (anterior == null || anterior.siguiente == null) {
            return false;
        }

        if (anterior.siguiente == cola) {
            cola = anterior;
        }

        anterior.siguiente = anterior.siguiente.siguiente;
        tamanio--;
        return true;
    }

    public void limpiar() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int tamanio() {
        return tamanio;
    }

    public NodoSimple<T> getCabeza() {
        return cabeza;
    }
}