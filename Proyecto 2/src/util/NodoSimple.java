package util;

public class NodoSimple<T> {

    /* ATRIBUTOS */
    public T dato;
    public NodoSimple<T> siguiente;

    /* CONSTRUCTOR */
    public NodoSimple(T dato) {
        this.dato = dato;
    }
}