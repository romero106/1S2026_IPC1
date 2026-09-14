package service;

import model.*;
import util.*;

public class AlbumService {

    /* ATRIBUTOS */
    private MatrizCartas album;
    private UsuarioActual usuarioActual;
    private RecompensaService recompensaService;

    /* CONSTRUCTOR */
    public AlbumService(MatrizCartas album, UsuarioActual usuarioActual, RecompensaService recompensaService) {
        this.album = album;
        this.usuarioActual = usuarioActual;
        this.recompensaService = recompensaService;
    }

    /* MÉTODOS */
    // Agrega una carta al álbum y actualiza las estadísticas del usuario
    public String agregarCartaAlAlbum(Carta carta) {
        NodoMatriz nodoVacio = album.buscarPrimeraVacia();

        if (nodoVacio == null) {
            return "El album ya esta lleno.";
        }

        nodoVacio.setCarta(carta);
        usuarioActual.setCartasAgregadas(usuarioActual.getCartasAgregadas() + 1);

        if (carta.getRareza().equalsIgnoreCase("Legendaria")) {
            usuarioActual.setCartasLegendarias(usuarioActual.getCartasLegendarias() + 1);
            recompensaService.agregarExperiencia(200);
        }

        int fila = nodoVacio.getFila();
        if (album.contarCartasEnFila(fila) == album.getColumnas()) {
            usuarioActual.setFilasCompletas(usuarioActual.getFilasCompletas() + 1);
            recompensaService.agregarExperiencia(100);
        }

        recompensaService.verificarLogros(true);
        return "Carta agregada en la posicion F" + (fila + 1) + " C" + (nodoVacio.getColumna() + 1) + ".";
    }
    
    // Intercambia dos cartas en el álbum
    public void intercambiarCartas(int filaUno, int columnaUno, int filaDos, int columnaDos) {
        album.intercambiar(filaUno, columnaUno, filaDos, columnaDos);
    }

    // Verifica si una carta coincide con el texto de búsqueda
    public boolean cartaCoincideBusqueda(Carta carta, String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }

        String criterio = texto.trim().toLowerCase();
        return carta.getNombre().toLowerCase().contains(criterio)
                || carta.getTipo().toLowerCase().contains(criterio)
                || carta.getRareza().toLowerCase().contains(criterio);
    }
}