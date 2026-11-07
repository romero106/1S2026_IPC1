package util;

public class NodoCola<T> {

    /* ATRIBUTOS */
    public T dato;
    public NodoCola<T> siguiente;

    /* CONSTRUCTOR */
    public NodoCola(T dato) {
        this.dato = dato;
    }
}