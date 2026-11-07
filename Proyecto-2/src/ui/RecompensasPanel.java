package ui;

import java.awt.*;
import javax.swing.*;

import app.GameZoneProManager;
import model.*;
import util.*;

public class RecompensasPanel extends JPanel {
    private GameZoneProManager manager;
    private Runnable volverMenu;
    private JLabel lblXp;
    private JLabel lblNivel;
    private JLabel lblRango;
    private JLabel lblGasto;
    private JProgressBar barraNivel;
    private JTextArea areaLogros;
    private JTextArea areaLeaderboard;
    private JLabel lblPodioUno;
    private JLabel lblPodioDos;
    private JLabel lblPodioTres;

    public RecompensasPanel(GameZoneProManager manager, Runnable volverMenu) {
        this.manager = manager;
        this.volverMenu = volverMenu;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        JButton botonMenu = new JButton("Volver al menu");
        botonMenu.addActionListener(e -> volverMenu.run());
        panelSuperior.add(botonMenu, BorderLayout.WEST);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelResumen = new JPanel(new GridLayout(5, 1, 8, 8));
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen del Usuario"));
        lblXp = new JLabel();
        lblNivel = new JLabel();
        lblRango = new JLabel();
        lblGasto = new JLabel();
        barraNivel = new JProgressBar(0, 100);
        barraNivel.setStringPainted(true);
        panelResumen.add(lblXp);
        panelResumen.add(lblNivel);
        panelResumen.add(lblRango);
        panelResumen.add(lblGasto);
        panelResumen.add(barraNivel);

        JPanel panelPodio = new JPanel(new GridLayout(1, 3, 10, 10));
        lblPodioUno = crearEtiquetaPodio(new Color(250, 204, 21));
        lblPodioDos = crearEtiquetaPodio(new Color(203, 213, 225));
        lblPodioTres = crearEtiquetaPodio(new Color(251, 146, 60));
        panelPodio.add(lblPodioUno);
        panelPodio.add(lblPodioDos);
        panelPodio.add(lblPodioTres);

        areaLogros = new JTextArea();
        areaLogros.setEditable(false);
        areaLeaderboard = new JTextArea();
        areaLeaderboard.setEditable(false);

        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 12, 12));
        panelInferior.add(new JScrollPane(areaLogros));
        panelInferior.add(new JScrollPane(areaLeaderboard));

        JPanel centro = new JPanel(new BorderLayout(12, 12));
        centro.add(panelResumen, BorderLayout.NORTH);
        centro.add(panelPodio, BorderLayout.CENTER);
        centro.add(panelInferior, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);
    }

    private JLabel crearEtiquetaPodio(Color color) {
        JLabel lbl = new JLabel("", JLabel.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(color);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        return lbl;
    }

    public void refrescarDatos() {
        int xp = manager.getUsuarioActual().getExperiencia();
        int nivel = manager.calcularNivel(xp);

        lblXp.setText("XP actual: " + xp);
        lblNivel.setText("Nivel: " + nivel);
        lblRango.setText("Rango: " + manager.getNombreNivel(nivel));
        lblGasto.setText("Gasto total en tienda: Q" + manager.formatearMonto(manager.getUsuarioActual().getGastoTotal()));
        barraNivel.setValue(manager.getValorProgressBar());
        barraNivel.setString(manager.getValorProgressBar() + "% del nivel actual");

        areaLogros.setText(construirTextoLogros());
        areaLeaderboard.setText(construirTextoLeaderboard());
        actualizarPodio();
    }

    private String construirTextoLogros() {
        String texto = "Logros\n\n";
        NodoSimple<Logro> actual = manager.getLogros().getCabeza();

        while (actual != null) {
            texto += (actual.dato.isDesbloqueado() ? "[DESBLOQUEADO] " : "[BLOQUEADO] ")
                    + actual.dato.getNombre()
                    + "\n"
                    + actual.dato.getDescripcion()
                    + "\n\n";
            actual = actual.siguiente;
        }

        return texto;
    }

    private String construirTextoLeaderboard() {
        String texto = "Leaderboard\n\n";
        UsuarioXP[] usuarios = manager.getLeaderboardOrdenado();
        int posicion = 1;

        for (int i = 0; i < usuarios.length && usuarios[i] != null && posicion <= 10; i++) {
            String marca = usuarios[i].getNombre().equalsIgnoreCase(manager.getUsuarioActual().getNombre()) ? " <= Tu Usuario" : "";
            texto += posicion + ". " + usuarios[i].getNombre() + " - " + usuarios[i].getExperiencia() + " XP" + marca + "\n";
            posicion++;
        }

        return texto;
    }

    private void actualizarPodio() {
        UsuarioXP[] usuarios = manager.getLeaderboardOrdenado();
        lblPodioUno.setText(usuarios[0] != null ? "<html><center>1. " + usuarios[0].getNombre() + "<br>" + usuarios[0].getExperiencia() + " XP</center></html>" : "1. Sin dato");
        lblPodioDos.setText(usuarios[1] != null ? "<html><center>2. " + usuarios[1].getNombre() + "<br>" + usuarios[1].getExperiencia() + " XP</center></html>" : "2. Sin dato");
        lblPodioTres.setText(usuarios[2] != null ? "<html><center>3. " + usuarios[2].getNombre() + "<br>" + usuarios[2].getExperiencia() + " XP</center></html>" : "3. Sin dato");
    }
}