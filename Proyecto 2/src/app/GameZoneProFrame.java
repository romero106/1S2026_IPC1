package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ui.*;

public class GameZoneProFrame extends JFrame {

    /* ATRIBUTOS */
    private GameZoneProManager manager;
    private CardLayout cardLayout;
    private JPanel panelCentral;
    private TiendaPanel tiendaPanel;
    private AlbumPanel albumPanel;
    private TorneosPanel torneosPanel;
    private RecompensasPanel recompensasPanel;
    private ReportesPanel reportesPanel;
    private DatosEstudiantePanel datosEstudiantePanel;

    /* CONSTRUCTOR */
    public GameZoneProFrame(String rutaBase) {
        this.manager = new GameZoneProManager(rutaBase);
        manager.inicializar();

        setTitle("GameZone Pro");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        tiendaPanel = new TiendaPanel(manager, this::refrescarTodo, this::mostrarMenu);
        albumPanel = new AlbumPanel(manager, this::refrescarTodo, this::mostrarMenu);
        torneosPanel = new TorneosPanel(manager, this::refrescarTodo, this::mostrarMenu);
        recompensasPanel = new RecompensasPanel(manager, this::mostrarMenu);
        reportesPanel = new ReportesPanel(manager, this::mostrarMenu);
        datosEstudiantePanel = new DatosEstudiantePanel(manager, this::mostrarMenu);

        panelCentral.add(crearPanelInicio(), "INICIO");
        panelCentral.add(tiendaPanel, "TIENDA");
        panelCentral.add(albumPanel, "ALBUM");
        panelCentral.add(torneosPanel, "TORNEOS");
        panelCentral.add(recompensasPanel, "RECOMPENSAS");
        panelCentral.add(reportesPanel, "REPORTES");
        panelCentral.add(datosEstudiantePanel, "ESTUDIANTE");

        add(crearPanelMenuLateral(), BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarAplicacion();
            }
        });

        refrescarTodo();
        mostrarMenu();
    }

    /* MÉTODOS*/
    // Muestra la tarjeta de inicio
    private void mostrarMenu() {
        mostrarTarjeta("INICIO");
    }

    // Crea el panel de inicio con tarjetas para cada sección
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Plataforma Gamer de Escritorio");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(2, 3, 20, 20));
        centro.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        centro.add(crearTarjetaInicio("Tienda de Videojuegos",
                "Explora el catalogo, administra tu carrito y confirma compras.", "TIENDA"));
        centro.add(crearTarjetaInicio("Album de Cartas", "Guarda cartas en una matriz ortogonal y revisa sus detalles.",
                "ALBUM"));
        centro.add(crearTarjetaInicio("Eventos Especiales", "Forma una cola de jugadores y vende tickets con hilos.",
                "TORNEOS"));
        centro.add(crearTarjetaInicio("Recompensas", "Consulta XP, nivel, logros y leaderboard.", "RECOMPENSAS"));
        centro.add(crearTarjetaInicio("Reportes HTML", "Genera y abre reportes visuales en el navegador.", "REPORTES"));
        centro.add(crearTarjetaInicio("Datos del Estudiante", "Muestra la informacion del estudiante del proyecto.",
                "ESTUDIANTE"));
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // Crea el panel lateral con el menú de navegación
    private JPanel crearPanelMenuLateral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBackground(new Color(15, 23, 42));

        JLabel titulo = new JLabel("GameZone Pro");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(8, 1, 10, 10));
        botones.setOpaque(false);
        botones.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        botones.add(crearBotonMenu("Menu Principal", "INICIO"));
        botones.add(crearBotonMenu("Tienda", "TIENDA"));
        botones.add(crearBotonMenu("Album", "ALBUM"));
        botones.add(crearBotonMenu("Torneos", "TORNEOS"));
        botones.add(crearBotonMenu("Recompensas", "RECOMPENSAS"));
        botones.add(crearBotonMenu("Reportes", "REPORTES"));
        botones.add(crearBotonMenu("Datos del Estudiante", "ESTUDIANTE"));

        JButton salir = new JButton("Salir");
        salir.addActionListener(e -> cerrarAplicacion());
        botones.add(salir);

        panel.add(botones, BorderLayout.CENTER);
        return panel;
    }

    // Crea un botón para el menú lateral que muestra la tarjeta correspondiente al hacer clic
    private JButton crearBotonMenu(String texto, String tarjeta) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> mostrarTarjeta(tarjeta));
        return boton;
    }

    // Crea una tarjeta para el panel de inicio con un título, descripción y botón para abrir la sección correspondiente
    private JPanel crearTarjetaInicio(String titulo, String descripcion, String tarjeta) {
        JPanel tarjetaPanel = new JPanel(new BorderLayout());
        tarjetaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        tarjetaPanel.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblDescripcion = new JLabel("<html><body style='width:250px'>" + descripcion + "</body></html>");
        tarjetaPanel.add(lblDescripcion, BorderLayout.CENTER);

        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.addActionListener(e -> mostrarTarjeta(tarjeta));
        tarjetaPanel.add(btnAbrir, BorderLayout.SOUTH);

        return tarjetaPanel;
    }

    // Muestra la tarjeta correspondiente al nombre dado y refresca los datos de todas las secciones
    private void mostrarTarjeta(String nombreTarjeta) {
        refrescarTodo();
        cardLayout.show(panelCentral, nombreTarjeta);
    }

    // Refresca los datos de todas las secciones para asegurar que la información esté actualizada al navegar entre ellas
    private void refrescarTodo() {
        tiendaPanel.refrescarDatos();
        albumPanel.refrescarDatos();
        torneosPanel.refrescarDatos();
        recompensasPanel.refrescarDatos();
        datosEstudiantePanel.refrescarDatos();
    }

    // Muestra un cuadro de diálogo de confirmación al intentar cerrar la aplicación, guardando los datos antes de salir si el usuario confirma
    private void cerrarAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(this, "Se guardaran los datos antes de salir. Deseas continuar?",
                "Salir", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            manager.guardarTodo();
            dispose();
        }
    }
}