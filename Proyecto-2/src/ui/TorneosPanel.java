package ui;

import java.awt.*;
import javax.swing.*;
import java.util.Random;

import app.GameZoneProManager;
import model.*;

public class TorneosPanel extends JPanel {
    private GameZoneProManager manager;
    private Runnable refrescadorGeneral;
    private Runnable volverMenu;
    private JComboBox<String> boxTorneos;
    private JTextArea areaDetalle;
    private JTextArea areaCola;
    private JTextArea areaLog;
    private JLabel lblTaquillaUno;
    private JLabel lblTaquillaDos;
    private JTextField campoNombre;
    private boolean ventaEnProceso;
    private int taquillasActivas;

    public TorneosPanel(GameZoneProManager manager, Runnable refrescadorGeneral, Runnable volverMenu) {
        this.manager = manager;
        this.refrescadorGeneral = refrescadorGeneral;
        this.volverMenu = volverMenu;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        JButton botonMenu = new JButton("Volver al menu");
        botonMenu.addActionListener(e -> volverMenu.run());
        panelSuperior.add(botonMenu, BorderLayout.WEST);

        boxTorneos = new JComboBox<String>();
        boxTorneos.addActionListener(e -> refrescarDatos());
        panelSuperior.add(boxTorneos, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setLineWrap(true);
        areaDetalle.setWrapStyleWord(true);
        panelIzquierdo.add(new JLabel("Detalle del Torneo"), BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        JPanel inscripcion = new JPanel(new GridLayout(2, 2, 8, 8));
        campoNombre = new JTextField();
        JButton botonInscribir = new JButton("Inscribirse");
        JButton botonVenta = new JButton("Iniciar Venta");
        botonInscribir.addActionListener(e -> inscribirJugador());
        botonVenta.addActionListener(e -> iniciarVenta());
        inscripcion.add(new JLabel("Nombre del jugador"));
        inscripcion.add(campoNombre);
        inscripcion.add(botonInscribir);
        inscripcion.add(botonVenta);
        panelIzquierdo.add(inscripcion, BorderLayout.SOUTH);

        JPanel panelDerecho = new JPanel(new GridLayout(1, 2, 12, 12));

        JPanel colaPanel = new JPanel(new BorderLayout());
        areaCola = new JTextArea();
        areaCola.setEditable(false);
        colaPanel.add(new JLabel("Cola Restante"), BorderLayout.NORTH);
        colaPanel.add(new JScrollPane(areaCola), BorderLayout.CENTER);

        JPanel logPanel = new JPanel(new BorderLayout(10, 10));
        JPanel estadoTaquillas = new JPanel(new GridLayout(2, 1, 8, 8));
        lblTaquillaUno = new JLabel("Taquilla 1: Libre");
        lblTaquillaDos = new JLabel("Taquilla 2: Libre");
        estadoTaquillas.add(lblTaquillaUno);
        estadoTaquillas.add(lblTaquillaDos);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);
        logPanel.add(estadoTaquillas, BorderLayout.NORTH);
        logPanel.add(new JScrollPane(areaLog), BorderLayout.CENTER);

        panelDerecho.add(colaPanel);
        panelDerecho.add(logPanel);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        panelIzquierdo.setPreferredSize(new Dimension(360, 0));
    }

    public void refrescarDatos() {
        String[] torneos = manager.getTorneosParaCombo();

        if (boxTorneos.getItemCount() != torneos.length) {
            boxTorneos.removeAllItems();
            for (int i = 0; i < torneos.length; i++) {
                boxTorneos.addItem(torneos[i]);
            }
        }

        Torneo torneo = obtenerTorneoSeleccionado();

        if (torneo == null) {
            areaDetalle.setText("No hay torneos disponibles.");
            areaCola.setText("");
            return;
        }

        areaDetalle.setText(
                "ID: " + torneo.getId()
                + "\nNombre: " + torneo.getNombre()
                + "\nJuego: " + torneo.getJuego()
                + "\nFecha: " + torneo.getFecha()
                + "\nHora: " + torneo.getHora()
                + "\nPrecio: Q" + manager.formatearMonto(torneo.getPrecioTicket())
                + "\nTickets Disponibles: " + torneo.getTicketsDisponibles()
        );

        areaCola.setText(manager.getTextoCola(torneo));
    }

    private Torneo obtenerTorneoSeleccionado() {
        int indice = boxTorneos.getSelectedIndex();
        if (indice < 0) {
            return null;
        }
        return manager.getTorneoPorIndice(indice);
    }

    private void inscribirJugador() {
        String mensaje = manager.inscribirEnTorneo(obtenerTorneoSeleccionado(), campoNombre.getText());
        JOptionPane.showMessageDialog(this, mensaje);
        campoNombre.setText("");
        refrescadorGeneral.run();
    }

    private void iniciarVenta() {
        Torneo torneo = obtenerTorneoSeleccionado();

        if (torneo == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un torneo primero.");
            return;
        }

        if (ventaEnProceso) {
            JOptionPane.showMessageDialog(this, "Ya hay una venta en proceso.");
            return;
        }

        if (torneo.getColaEspera().estaVacia()) {
            JOptionPane.showMessageDialog(this, "No hay jugadores en la cola.");
            return;
        }

        ventaEnProceso = true;
        taquillasActivas = 2;
        areaLog.append("Inicio de venta para " + torneo.getNombre() + "\n");

        new TaquillaWorker("Taquilla 1", torneo, lblTaquillaUno).start();
        new TaquillaWorker("Taquilla 2", torneo, lblTaquillaDos).start();
    }

    private class TaquillaWorker extends Thread {
        private  Torneo torneo;
        private  JLabel lblEstado;
        private  Random random;

        public TaquillaWorker(String nombre, Torneo torneo, JLabel lblEstado) {
            super(nombre);
            this.torneo = torneo;
            this.lblEstado = lblEstado;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (true) {
                String comprador = torneo.getColaEspera().desencolar();

                if (comprador == null) {
                    break;
                }

                actualizarEstado("Procesando a: " + comprador);

                try {
                    Thread.sleep(800 + random.nextInt(1201));
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                }

                TicketVenta ticket = manager.procesarVentaTicket(torneo, comprador, getName());

                if (ticket == null) {
                    agregarAlLog(getName() + ": no fue posible vender ticket a " + comprador + " porque ya no hay disponibilidad.");
                } else {
                    agregarAlLog(getName() + ": ticket vendido a " + ticket.getComprador() + " para " + ticket.getTorneoNombre() + ".");
                    mostrarMensajesPendientes();
                }

                SwingUtilities.invokeLater(() -> refrescadorGeneral.run());

                if (torneo.getTicketsDisponibles() <= 0) {
                    break;
                }
            }

            actualizarEstado("Libre");
            notificarFinTaquilla();
        }

        private void actualizarEstado(String estado) {
            SwingUtilities.invokeLater(() -> lblEstado.setText(getName() + ": " + estado));
        }

        private void agregarAlLog(String texto) {
            SwingUtilities.invokeLater(() -> areaLog.append(texto + "\n"));
        }
    }

    private synchronized void notificarFinTaquilla() {
        taquillasActivas--;

        if (taquillasActivas <= 0) {
            ventaEnProceso = false;
            SwingUtilities.invokeLater(() -> {
                refrescadorGeneral.run();
                areaLog.append("La venta del torneo seleccionado ha izado.\n");
                JOptionPane.showMessageDialog(this, "Las taquillas izaron el procesamiento de la cola.");
            });
        }
    }

    private void mostrarMensajesPendientes() {
        SwingUtilities.invokeLater(() -> {
            String mensajes = manager.consumirMensajesPendientes();
            if (!mensajes.isEmpty()) {
                JOptionPane.showMessageDialog(this, mensajes, "Recompensas", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}