package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.*;
import util.*;

public class TiendaService {

    /* ATRIBUTOS */
    private ListaSimple<Juego> catalogo;
    private ListaSimple<CarritoItem> carrito;
    private ListaSimple<Compra> historialCompras;
    private UsuarioActual usuarioActual;
    private RecompensaService recompensaService;

    /* CONSTRUCTOR */
    public TiendaService(ListaSimple<Juego> catalogo, ListaSimple<CarritoItem> carrito, ListaSimple<Compra> historialCompras, UsuarioActual usuarioActual, RecompensaService recompensaService) {
        this.catalogo = catalogo;
        this.carrito = carrito;
        this.historialCompras = historialCompras;
        this.usuarioActual = usuarioActual;
        this.recompensaService = recompensaService;
    }

    /* MÉTODOS */
    // Filtra los juegos del catálogo según el texto, género y plataforma seleccionados
    public boolean juegoCoincideFiltro(Juego juego, String texto, String genero, String plataforma) {
        String textoLimpio = texto == null ? "" : texto.trim().toLowerCase();
        boolean coincideTexto = textoLimpio.isEmpty()
                || juego.getNombre().toLowerCase().contains(textoLimpio)
                || juego.getCodigo().toLowerCase().contains(textoLimpio);

        boolean coincideGenero = genero == null || genero.equals("Todos") || juego.getGenero().equalsIgnoreCase(genero);
        boolean coincidePlataforma = plataforma == null || plataforma.equals("Todas") || juego.getPlataforma().equalsIgnoreCase(plataforma);

        return coincideTexto && coincideGenero && coincidePlataforma;
    }

    // Busca un juego en el catálogo por su código
    public Juego buscarJuegoPorCodigo(String codigo) {
        NodoSimple<Juego> actual = catalogo.getCabeza();

        while (actual != null) {
            if (actual.dato.getCodigo().equalsIgnoreCase(codigo)) {
                return actual.dato;
            }

            actual = actual.siguiente;
        }

        return null;
    }

    // Agrega un juego al carrito, verificando stock y evitando duplicados
    public String agregarAlCarrito(String codigo) {
        Juego juego = buscarJuegoPorCodigo(codigo);

        if (juego == null) {
            return "No se encontro el juego seleccionado.";
        }

        if (juego.getStock() <= 0) {
            return "No hay stock disponible para " + juego.getNombre() + ".";
        }

        NodoSimple<CarritoItem> actual = carrito.getCabeza();

        while (actual != null) {
            if (actual.dato.getJuego().getCodigo().equalsIgnoreCase(codigo)) {
                if (actual.dato.getCantidad() + 1 > juego.getStock()) {
                    return "No puedes agregar mas unidades de las disponibles.";
                }

                actual.dato.setCantidad(actual.dato.getCantidad() + 1);
                return "Juego agregado al carrito.";
            }

            actual = actual.siguiente;
        }

        carrito.insertarAlFinal(new CarritoItem(juego, 1));
        return "Juego agregado al carrito.";
    }

    // Actualiza la cantidad de un juego en el carrito o lo elimina si la cantidad es cero o negativa
    public String actualizarCantidadCarrito(String codigo, int cantidad) {
        if (cantidad <= 0) {
            eliminarDelCarrito(codigo);
            return "El producto fue eliminado del carrito.";
        }

        NodoSimple<CarritoItem> actual = carrito.getCabeza();

        while (actual != null) {
            if (actual.dato.getJuego().getCodigo().equalsIgnoreCase(codigo)) {
                if (cantidad > actual.dato.getJuego().getStock()) {
                    return "La cantidad supera el stock disponible.";
                }

                actual.dato.setCantidad(cantidad);
                return "Cantidad actualizada.";
            }

            actual = actual.siguiente;
        }

        return "No se encontro el producto en el carrito.";
    }

    // Elimina un juego del carrito por su código
    public void eliminarDelCarrito(String codigo) {
        NodoSimple<CarritoItem> actual = carrito.getCabeza();
        int indice = 0;

        while (actual != null) {
            if (actual.dato.getJuego().getCodigo().equalsIgnoreCase(codigo)) {
                carrito.eliminarPorIndice(indice);
                return;
            }

            actual = actual.siguiente;
            indice++;
        }
    }

    // Calcula el total del carrito sumando los subtotales de cada item
    public double calcularTotalCarrito() {
        double total = 0;
        NodoSimple<CarritoItem> actual = carrito.getCabeza();

        while (actual != null) {
            total += actual.dato.getSubtotal();
            actual = actual.siguiente;
        }

        return total;
    }

    // Confirma la compra, verificando stock, actualizando inventario, historial y recompensas
    public String confirmarCompra() {
        if (carrito.estaVacia()) {
            return "El carrito esta vacio.";
        }

        String faltantes = "";
        NodoSimple<CarritoItem> actual = carrito.getCabeza();

        while (actual != null) {
            if (actual.dato.getCantidad() > actual.dato.getJuego().getStock()) {
                if (!faltantes.isEmpty()) {
                    faltantes += ", ";
                }

                faltantes += actual.dato.getJuego().getNombre();
            }

            actual = actual.siguiente;
        }

        if (!faltantes.isEmpty()) {
            return "Stock insuficiente para: " + faltantes;
        }

        double total = 0;
        int cantidadJuegosComprados = 0;
        String detalle = "";
        actual = carrito.getCabeza();

        while (actual != null) {
            Juego juego = actual.dato.getJuego();
            juego.setStock(juego.getStock() - actual.dato.getCantidad());
            total += actual.dato.getSubtotal();
            cantidadJuegosComprados += actual.dato.getCantidad();

            if (!detalle.isEmpty()) {
                detalle += ", ";
            }

            detalle += juego.getNombre() + " x" + actual.dato.getCantidad();
            actual = actual.siguiente;
        }

        historialCompras.insertarAlInicio(new Compra(getTimestampBonito(), detalle, total));
        carrito.limpiar();
        usuarioActual.setComprasRealizadas(usuarioActual.getComprasRealizadas() + 1);
        usuarioActual.setGastoTotal(usuarioActual.getGastoTotal() + total);
        recompensaService.agregarExperiencia(cantidadJuegosComprados * 50);
        recompensaService.verificarLogros(true);

        return "Compra realizada con exito. Total: Q" + recompensaService.formatearMonto(total);
    }

    /* GETTERS */
    // Genera un timestamp para el nombre del archivo del reporte
    private String getTimestampBonito() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formato);
    }
}