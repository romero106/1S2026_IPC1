package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.*;
import repository.*;
import service.*;
import util.*;

public class GameZoneProManager {

    /* ATRIBUTOS */
    private String rutaBase;
    private String rutaData;
    private String rutaReportes;
    private ListaSimple<Juego> catalogo;
    private ListaSimple<CarritoItem> carrito;
    private ListaSimple<Compra> historialCompras;
    private MatrizCartas album;
    private ListaSimple<Torneo> torneos;
    private ListaSimple<TicketVenta> ticketsVendidos;
    private ListaSimple<Logro> logros;
    private UsuarioActual usuarioActual;
    private Estudiante estudiante;
    private UsuarioXP[] leaderboardBase;
    private int totalUsuariosLeaderboard;
    private CatalogoRepository catalogoRepository;
    private HistorialRepository historialRepository;
    private AlbumRepository albumRepository;
    private TorneoRepository torneoRepository;
    private TicketRepository ticketRepository;
    private UsuarioRepository usuarioRepository;
    private LeaderboardRepository leaderboardRepository;
    private EstudianteRepository estudianteRepository;
    private RecompensaService recompensaService;
    private TiendaService tiendaService;
    private AlbumService albumService;
    private TorneoService torneoService;
    private ReporteService reporteService;

    /* CONSTRUCTOR */
    public GameZoneProManager(String rutaBase) {
        this.rutaBase = rutaBase;
        this.rutaData = rutaBase + File.separator + "data";
        this.rutaReportes = rutaBase + File.separator + "reports";
        this.catalogoRepository = new CatalogoRepository();
        this.historialRepository = new HistorialRepository();
        this.albumRepository = new AlbumRepository();
        this.torneoRepository = new TorneoRepository();
        this.ticketRepository = new TicketRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.leaderboardRepository = new LeaderboardRepository();
        this.estudianteRepository = new EstudianteRepository();
    }

    /* MÉTODOS */
    // Inicializa el sistema, creando archivos base si no existen y cargando los datos en memoria
    public void inicializar() {
        crearArchivosBase();
        catalogo = catalogoRepository.cargarCatalogo(rutaData + File.separator + "catalogo.txt");
        carrito = new ListaSimple<CarritoItem>();
        historialCompras = historialRepository.cargarHistorial(rutaData + File.separator + "historial.txt");
        album = albumRepository.cargarAlbum(rutaData + File.separator + "album.txt", 4, 6);
        torneos = torneoRepository.cargarTorneos(rutaData + File.separator + "torneos.txt");
        ticketsVendidos = ticketRepository.cargarTickets(rutaData + File.separator + "tickets_vendidos.txt");
        usuarioActual = usuarioRepository.cargarUsuario(rutaData + File.separator + "usuario.txt");
        leaderboardBase = leaderboardRepository.cargarLeaderboard(rutaData + File.separator + "leaderboard.txt", 50);
        totalUsuariosLeaderboard = contarUsuariosLeaderboard(leaderboardBase);
        estudiante = estudianteRepository.cargarEstudiante(rutaData + File.separator + "estudiante.txt");
        logros = new ListaSimple<Logro>();

        configurarServicios();

        recompensaService.crearLogrosBase();
        recompensaService.verificarLogros(false);
        recompensaService.agregarExperiencia(10);
        recompensaService.verificarLogros(false);
        recompensaService.consumirMensajesPendientes();
    }

    // Crea los archivos base con contenido inicial solo si no existen, para asegurar que siempre haya datos para cargar
    private void crearArchivosBase() {
        try {
            new File(rutaData).mkdirs();
            new File(rutaReportes).mkdirs();

            crearArchivoConContenidoSiNoExiste("catalogo.txt",
                    "G009|The Witcher 3: Wild Hunt|RPG|299.99|PC|9|Mundo abierto con decisiones morales y misiones secundarias profundas.\n"
                            + "G010|Red Dead Redemption 2|Accion|499.99|PlayStation|10|Western cinematografico sobre lealtad y el fin de una era.\n"
                            + "G011|God of War (2018)|Accion|299.99|PlayStation|9|Kratos y su hijo en una historia emotiva sobre la paternidad.\n"
                            + "G012|The Last of Us Part II|Accion|399.99|PlayStation|8|Supervivencia cruda con narrativa intensa y emocional.\n"
                            + "G013|Disco Elysium|RPG|299.99|PC|10|Detective RPG donde los dialogos son los combates principales.\n"
                            + "G014|Elden Ring|RPG|499.99|PC|10|Mundo abierto desafiante con historia fragmentada y profunda.\n"
                            + "G015|Detroit: Become Human|Aventura|299.99|PlayStation|9|Tus decisiones cambian el destino de tres androides.\n"
                            + "G016|Life is Strange|Aventura|149.99|PC|8|Historia emotiva sobre amistad y viajes en el tiempo.\n");

            crearArchivoConContenidoSiNoExiste("historial.txt", "");
            crearArchivoConContenidoSiNoExiste("album.txt", "");
            crearArchivoConContenidoSiNoExiste("tickets_vendidos.txt", "");
            crearArchivoConContenidoSiNoExiste("torneos.txt",
                    "T001|Copa Blade|The Last Blade|2026-05-02|10:00|75.00|10\n"
                            + "T002|Mystic Masters|Mystic Valley|2026-05-04|15:30|90.00|8\n"
                            + "T003|Pixel Cup|Pixel Kart|2026-05-06|11:15|60.00|12\n"
                            + "T004|Cyber Arena|Cyber Raid|2026-05-08|18:00|110.00|6\n");

            crearArchivoConContenidoSiNoExiste("leaderboard.txt",
                    "Jeffrey|3200\n"
                            + "Luis|2450\n"
                            + "Fernando|1800\n"
                            + "Galicia|1500\n"
                            + "Diego|900\n");

            crearArchivoConContenidoSiNoExiste("usuario.txt",
                    "Jugador Actual|0|0|0|0|0|0|0.0||\n");

            crearArchivoConContenidoSiNoExiste("estudiante.txt",
                    "Patricio Manuel Romero Castellanos|202504020|3058919280301@ingenieria.usac.edu.gt|E|Primer semestre 2026|GameZone Pro es una plataforma de escritorio en Java Swing que integra tienda, album, torneos, recompensas y reportes HTML.\n");
        } catch (IOException error) {
            throw new RuntimeException("No fue posible crear los archivos base del proyecto.", error);
        }
    }

    // Crea un archivo con contenido inicial solo si no existe, para asegurar que siempre haya datos base para cargar
    private void crearArchivoConContenidoSiNoExiste(String nombreArchivo, String contenido) throws IOException {
        File archivo = new File(rutaData + File.separator + nombreArchivo);

        if (!archivo.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(contenido);
            writer.close();
        }
    }

    // Configura los servicios, inyectando las dependencias necesarias para su funcionamiento
    private void configurarServicios() {
        recompensaService = new RecompensaService(usuarioActual, logros);
        tiendaService = new TiendaService(catalogo, carrito, historialCompras, usuarioActual, recompensaService);
        albumService = new AlbumService(album, usuarioActual, recompensaService);
        torneoService = new TorneoService(torneos, ticketsVendidos, usuarioActual, recompensaService);
        reporteService = new ReporteService(rutaReportes, catalogo, historialCompras, album, torneos, ticketsVendidos,
                recompensaService);
    }

    // Cuenta cuántos usuarios hay en el leaderboard para mostrar solo los necesarios en la interfaz
    private int contarUsuariosLeaderboard(UsuarioXP[] usuarios) {
        int total = 0;

        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i] != null) {
                total++;
            }
        }

        return total;
    }

    // Guarda todos los datos en sus respectivos archivos, delegando en los repositorios para manejar la persistencia
    public void guardarTodo() {
        catalogoRepository.guardarCatalogo(rutaData + File.separator + "catalogo.txt", catalogo);
        historialRepository.guardarHistorial(rutaData + File.separator + "historial.txt", historialCompras);
        albumRepository.guardarAlbum(rutaData + File.separator + "album.txt", album);
        torneoRepository.guardarTorneos(rutaData + File.separator + "torneos.txt", torneos);
        ticketRepository.guardarTickets(rutaData + File.separator + "tickets_vendidos.txt", ticketsVendidos);
        usuarioRepository.guardarUsuario(rutaData + File.separator + "usuario.txt", usuarioActual);
        leaderboardRepository.guardarLeaderboard(rutaData + File.separator + "leaderboard.txt",
                getLeaderboardOrdenado());
    }

    // Estos métodos permiten a la interfaz interactuar con la lógica
    public boolean juegoCoincideFiltro(Juego juego, String texto, String genero, String plataforma) {
        return tiendaService.juegoCoincideFiltro(juego, texto, genero, plataforma);
    }

    public Juego buscarJuegoPorCodigo(String codigo) {
        return tiendaService.buscarJuegoPorCodigo(codigo);
    }

    public String agregarAlCarrito(String codigo) {
        return tiendaService.agregarAlCarrito(codigo);
    }

    public String actualizarCantidadCarrito(String codigo, int cantidad) {
        return tiendaService.actualizarCantidadCarrito(codigo, cantidad);
    }

    public void eliminarDelCarrito(String codigo) {
        tiendaService.eliminarDelCarrito(codigo);
    }

    public double calcularTotalCarrito() {
        return tiendaService.calcularTotalCarrito();
    }

    public String confirmarCompra() {
        return tiendaService.confirmarCompra();
    }

    public String agregarCartaAlAlbum(Carta carta) {
        return albumService.agregarCartaAlAlbum(carta);
    }

    public void intercambiarCartas(int filaUno, int columnaUno, int filaDos, int columnaDos) {
        albumService.intercambiarCartas(filaUno, columnaUno, filaDos, columnaDos);
    }

    public boolean cartaCoincideBusqueda(Carta carta, String texto) {
        return albumService.cartaCoincideBusqueda(carta, texto);
    }

    public String inscribirEnTorneo(Torneo torneo, String nombreJugador) {
        return torneoService.inscribirEnTorneo(torneo, nombreJugador);
    }

    public TicketVenta procesarVentaTicket(Torneo torneo, String comprador, String taquilla) {
        return torneoService.procesarVentaTicket(torneo, comprador, taquilla);
    }

    public int calcularNivel(int experiencia) {
        return recompensaService.calcularNivel(experiencia);
    }

    public String formatearMonto(double monto) {
        return recompensaService.formatearMonto(monto);
    }

    public String consumirMensajesPendientes() {
        return recompensaService.consumirMensajesPendientes();
    }

    public String generarReporteInventario() {
        return reporteService.generarReporteInventario();
    }

    public String generarReporteVentas() {
        return reporteService.generarReporteVentas();
    }

    public String generarReporteAlbum() {
        return reporteService.generarReporteAlbum();
    }

    public String generarReporteTorneos() {
        return reporteService.generarReporteTorneos();
    }

    /* GETTERS */
    // Estos métodos permiten a la interfaz acceder a los datos y servicios necesarios para mostrar información y responder a las acciones del usuario
    public ListaSimple<Juego> getCatalogo() {
        return catalogo;
    }

    public ListaSimple<CarritoItem> getCarrito() {
        return carrito;
    }

    public ListaSimple<Compra> getHistorialCompras() {
        return historialCompras;
    }

    public MatrizCartas getAlbum() {
        return album;
    }

    public ListaSimple<Torneo> getTorneos() {
        return torneos;
    }

    public ListaSimple<TicketVenta> getTicketsVendidos() {
        return ticketsVendidos;
    }

    public ListaSimple<Logro> getLogros() {
        return logros;
    }

    public UsuarioActual getUsuarioActual() {
        return usuarioActual;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public String[] getTorneosParaCombo() {
        return torneoService.getTorneosParaCombo();
    }

    public Torneo getTorneoPorIndice(int indice) {
        return torneoService.getTorneoPorIndice(indice);
    }

    public String getTextoCola(Torneo torneo) {
        return torneoService.getTextoCola(torneo);
    }

    public String getNombreNivel(int nivel) {
        return recompensaService.getNombreNivel(nivel);
    }

    public int getInicioNivel(int nivel) {
        return recompensaService.getInicioNivel(nivel);
    }

    public int getFinNivel(int nivel) {
        return recompensaService.getFinNivel(nivel);
    }

    public int getValorProgressBar() {
        return recompensaService.getValorProgressBar();
    }

    public UsuarioXP[] getLeaderboardOrdenado() {
        return recompensaService.getLeaderboardOrdenado(leaderboardBase, totalUsuariosLeaderboard);
    }
}