package service;

import model.*;
import util.*;

public class RecompensaService {

    /* ATRIBUTOS */
    private UsuarioActual usuarioActual;
    private ListaSimple<Logro> logros;
    private String mensajesPendientes;

    /* CONSTRUCTOR */
    public RecompensaService(UsuarioActual usuarioActual, ListaSimple<Logro> logros) {
        this.usuarioActual = usuarioActual;
        this.logros = logros;
        this.mensajesPendientes = "";
    }

    /* MÉTODOS */
    // Crea los logros base del sistema
    public void crearLogrosBase() {
        logros.limpiar();
        logros.insertarAlFinal(new Logro("PRIMERA_COMPRA", "Primera Compra", "Realiza tu primera compra en la tienda."));
        logros.insertarAlFinal(new Logro("COLECCIONISTA_NOVATO", "Coleccionista Novato", "Agrega 10 cartas al album."));
        logros.insertarAlFinal(new Logro("COLECCIONISTA_EXPERTO", "Coleccionista Experto", "Completa una fila completa del album."));
        logros.insertarAlFinal(new Logro("TAQUILLERO", "Taquillero", "Compra tickets para 3 torneos distintos."));
        logros.insertarAlFinal(new Logro("ALTA_RAREZA", "Alta Rareza", "Consigue una carta legendaria."));
        logros.insertarAlFinal(new Logro("GAMER_DEDICADO", "Gamer Dedicado", "Acumula 1000 XP."));
        logros.insertarAlFinal(new Logro("LEYENDA_VIVIENTE", "Leyenda Viviente", "Alcanza el Nivel 5."));
        logros.insertarAlFinal(new Logro("GRAN_GASTADOR", "Gran Gastador", "Gasta mas de Q2000 en la tienda."));
    }

    // Verifica si se han cumplido los requisitos para desbloquear logros
    public void verificarLogros(boolean avisar) {
        revisarLogro("PRIMERA_COMPRA", usuarioActual.getComprasRealizadas() >= 1, avisar);
        revisarLogro("COLECCIONISTA_NOVATO", usuarioActual.getCartasAgregadas() >= 10, avisar);
        revisarLogro("COLECCIONISTA_EXPERTO", usuarioActual.getFilasCompletas() >= 1, avisar);
        revisarLogro("TAQUILLERO", contarTorneosDistintos() >= 3, avisar);
        revisarLogro("ALTA_RAREZA", usuarioActual.getCartasLegendarias() >= 1, avisar);
        revisarLogro("GAMER_DEDICADO", usuarioActual.getExperiencia() >= 1000, avisar);
        revisarLogro("LEYENDA_VIVIENTE", calcularNivel(usuarioActual.getExperiencia()) >= 5, avisar);
        revisarLogro("GRAN_GASTADOR", usuarioActual.getGastoTotal() > 2000, avisar);
    }

    // Método auxiliar para revisar y desbloquear logros
    private void revisarLogro(String codigo, boolean cumple, boolean avisar) {
        NodoSimple<Logro> actual = logros.getCabeza();

        while (actual != null) {
            if (actual.dato.getCodigo().equals(codigo) && cumple && !actual.dato.isDesbloqueado()) {
                actual.dato.setDesbloqueado(true);

                if (avisar) {
                    agregarMensaje("Logro desbloqueado: " + actual.dato.getNombre());
                }
            }

            actual = actual.siguiente;
        }
    }

    // Agrega un mensaje a la lista de mensajes pendientes
    private void agregarMensaje(String mensaje) {
        if (!mensajesPendientes.isEmpty()) {
            mensajesPendientes += "\n";
        }

        mensajesPendientes += mensaje;
    }

    // Agrega experiencia al usuario y verifica si ha subido de nivel
    public void agregarExperiencia(int puntos) {
        int nivelAntes = calcularNivel(usuarioActual.getExperiencia());
        usuarioActual.sumarExperiencia(puntos);
        int nivelDespues = calcularNivel(usuarioActual.getExperiencia());

        if (nivelDespues > nivelAntes) {
            agregarMensaje("Subiste al Nivel " + nivelDespues + " - " + getNombreNivel(nivelDespues));
        }
    }

    // Devuelve los mensajes pendientes y los borra de la memoria
    public String consumirMensajesPendientes() {
        String respuesta = mensajesPendientes;
        mensajesPendientes = "";
        return respuesta;
    }

    // Calcula el nivel del usuario basado en su experiencia
    public int calcularNivel(int experiencia) {
        if (experiencia >= 7000) {
            return 5;
        }
        if (experiencia >= 3500) {
            return 4;
        }
        if (experiencia >= 1500) {
            return 3;
        }
        if (experiencia >= 500) {
            return 2;
        }
        return 1;
    }

    // Cuenta la cantidad de torneos distintos a los que el usuario ha asistido
    private int contarTorneosDistintos() {
        String texto = usuarioActual.getTorneosDistintos();
        int total = 0;

        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '|') {
                total++;
            }
        }

        return total <= 1 ? 0 : total - 1;
    }

    public String formatearMonto(double monto) {
        return String.format("%.2f", monto);
    }

    /* GETTERS */
    // Devuelve el nombre del nivel basado en su número
    public String getNombreNivel(int nivel) {
        if (nivel == 5) {
            return "Leyenda";
        }
        if (nivel == 4) {
            return "Maestro";
        }
        if (nivel == 3) {
            return "Veterano";
        }
        if (nivel == 2) {
            return "Jugador";
        }
        return "Aprendiz";
    }

    // Devuelve la cantidad de experiencia necesaria para alcanzar el inicio de un nivel específico
    public int getInicioNivel(int nivel) {
        if (nivel == 5) {
            return 7000;
        }
        if (nivel == 4) {
            return 3500;
        }
        if (nivel == 3) {
            return 1500;
        }
        if (nivel == 2) {
            return 500;
        }
        return 0;
    }

    // Devuelve la cantidad de experiencia necesaria para alcanzar el siguiente nivel
    public int getFinNivel(int nivel) {
        if (nivel == 5) {
            return 7000;
        }
        if (nivel == 4) {
            return 6999;
        }
        if (nivel == 3) {
            return 3499;
        }
        if (nivel == 2) {
            return 1499;
        }
        return 499;
    }

    // Devuelve el porcentaje de avance hacia el siguiente nivel para la barra de progreso
    public int getValorProgressBar() {
        int nivel = calcularNivel(usuarioActual.getExperiencia());

        if (nivel == 5) {
            return 100;
        }

        int inicio = getInicioNivel(nivel);
        int fin = getFinNivel(nivel) + 1;
        int rango = fin - inicio;
        int avance = usuarioActual.getExperiencia() - inicio;
        return (avance * 100) / rango;
    }

    // Devuelve un leaderboard ordenado por experiencia, incluyendo al usuario actual
    public UsuarioXP[] getLeaderboardOrdenado(UsuarioXP[] leaderboardBase, int totalUsuariosLeaderboard) {
        UsuarioXP[] copia = new UsuarioXP[leaderboardBase.length + 1];
        int total = 0;
        boolean usuarioYaExiste = false;

        for (int i = 0; i < totalUsuariosLeaderboard; i++) {
            if (leaderboardBase[i] != null) {
                if (leaderboardBase[i].getNombre().equalsIgnoreCase(usuarioActual.getNombre())) {
                    copia[total] = new UsuarioXP(usuarioActual.getNombre(), usuarioActual.getExperiencia());
                    usuarioYaExiste = true;
                } else {
                    copia[total] = new UsuarioXP(leaderboardBase[i].getNombre(), leaderboardBase[i].getExperiencia());
                }
                total++;
            }
        }

        if (!usuarioYaExiste) {
            copia[total] = new UsuarioXP(usuarioActual.getNombre(), usuarioActual.getExperiencia());
            total++;
        }

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {
                if (copia[j].getExperiencia() < copia[j + 1].getExperiencia()) {
                    UsuarioXP temporal = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temporal;
                }
            }
        }

        return copia;
    }
}