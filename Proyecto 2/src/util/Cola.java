package util;

public class Cola<T> {

    /* ATRIBUTOS */
    private NodoCola<T> frente;
    private NodoCola<T> fin;
    private int tamanio;

    /* MÉTODOS */
    public void encolar(T dato) {
        NodoCola<T> nuevo = new NodoCola<T>(dato);

        if (frente == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }

        tamanio++;
    }

    public synchronized T desencolar() {
        if (frente == null) {
            return null;
        }

        T dato = frente.dato;
        frente = frente.siguiente;

        if (frente == null) {
            fin = null;
        }

        tamanio--;
        return dato;
    }

    public T peek() {
        if (frente == null) {
            return null;
        }
        return frente.dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanio() {
        return tamanio;
    }

    public NodoCola<T> getFrente() {
        return frente;
    }
}