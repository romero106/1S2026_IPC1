package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import app.GameZoneProManager;
import model.*;
import util.*;

public class AlbumPanel extends JPanel {
    private GameZoneProManager manager;
    private Runnable refrescadorGeneral;
    private Runnable volverMenu;
    private JPanel panelMatriz;
    private JTextArea areaDetalle;
    private JTextField campoBusqueda;
    private NodoMatriz primeraSeleccion;
    private NodoMatriz segundaSeleccion;

    public AlbumPanel(GameZoneProManager manager, Runnable refrescadorGeneral, Runnable volverMenu) {
        this.manager = manager;
        this.refrescadorGeneral = refrescadorGeneral;
        this.volverMenu = volverMenu;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        JButton botonMenu = new JButton("Volver al menu");
        botonMenu.addActionListener(e -> volverMenu.run());
        panelSuperior.add(botonMenu, BorderLayout.WEST);

        JPanel controles = new JPanel(new GridLayout(1, 3, 10, 10));
        campoBusqueda = new JTextField();
        JButton botonAgregar = new JButton("Agregar Carta");
        JButton botonIntercambiar = new JButton("Intercambiar Seleccionadas");

        botonAgregar.addActionListener(e -> mostrarDialogoAgregarCarta());
        botonIntercambiar.addActionListener(e -> intercambiarCartasSeleccionadas());

        controles.add(campoBusqueda);
        controles.add(botonAgregar);
        controles.add(botonIntercambiar);
        panelSuperior.add(new JLabel("Buscar por nombre, tipo o rareza"), BorderLayout.CENTER);
        panelSuperior.add(controles, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        panelMatriz = new JPanel(new GridLayout(manager.getAlbum().getFilas(), manager.getAlbum().getColumnas(), 8, 8));
        JScrollPane scrollMatriz = new JScrollPane(panelMatriz);
        scrollMatriz.setPreferredSize(new Dimension(940, 620));

        JPanel lateral = new JPanel(new BorderLayout());
        lateral.add(new JLabel("Detalle de la Carta"), BorderLayout.NORTH);
        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setLineWrap(true);
        areaDetalle.setWrapStyleWord(true);
        lateral.add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        add(scrollMatriz, BorderLayout.CENTER);
        add(lateral, BorderLayout.EAST);

        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refrescarDatos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refrescarDatos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refrescarDatos();
            }
        });
    }

    public void refrescarDatos() {
        panelMatriz.removeAll();
        NodoMatriz filaActual = manager.getAlbum().getInicio();

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                panelMatriz.add(crearCelda(columnaActual));
                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        panelMatriz.revalidate();
        panelMatriz.repaint();
    }

    private JPanel crearCelda(NodoMatriz nodo) {
        JPanel celda = new JPanel(new BorderLayout());
        celda.setPreferredSize(new Dimension(140, 120));
        celda.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184), 2));

        if (nodo == primeraSeleccion || nodo == segundaSeleccion) {
            celda.setBorder(BorderFactory.createLineBorder(new Color(37, 99, 235), 4));
        }

        if (nodo.getCarta() == null) {
            celda.setBackground(new Color(226, 232, 240));
            JLabel vacia = new JLabel("Vacia", JLabel.CENTER);
            vacia.setFont(new Font("SansSerif", Font.BOLD, 16));
            celda.add(vacia, BorderLayout.CENTER);
        } else {
            Carta carta = nodo.getCarta();
            celda.setBackground(obtenerColorPorTipo(carta.getTipo()));

            String texto = "<html><center><strong>" + carta.getNombre()
                    + "</strong><br>" + carta.getTipo()
                    + "<br>" + carta.getRareza()
                    + "</center></html>";
            JLabel etiqueta = new JLabel(texto, JLabel.CENTER);
            celda.add(etiqueta, BorderLayout.CENTER);

            if (manager.cartaCoincideBusqueda(carta, campoBusqueda.getText())) {
                celda.setBorder(BorderFactory.createLineBorder(new Color(250, 204, 21), 4));
            }
        }

        JButton botonSeleccionar = new JButton("Seleccionar");
        botonSeleccionar.addActionListener(e -> seleccionarNodo(nodo));
        celda.add(botonSeleccionar, BorderLayout.SOUTH);
        return celda;
    }

    private Color obtenerColorPorTipo(String tipo) {
        if (tipo.equalsIgnoreCase("Fuego")) {
            return new Color(254, 202, 202);
        }
        if (tipo.equalsIgnoreCase("Agua")) {
            return new Color(191, 219, 254);
        }
        if (tipo.equalsIgnoreCase("Planta")) {
            return new Color(187, 247, 208);
        }
        if (tipo.equalsIgnoreCase("Electrico")) {
            return new Color(254, 240, 138);
        }
        if (tipo.equalsIgnoreCase("Psiquico")) {
            return new Color(233, 213, 255);
        }
        if (tipo.equalsIgnoreCase("Oscuro")) {
            return new Color(203, 213, 225);
        }
        if (tipo.equalsIgnoreCase("Acero")) {
            return new Color(226, 232, 240);
        }
        return new Color(243, 244, 246);
    }

    private void seleccionarNodo(NodoMatriz nodo) {
        if (primeraSeleccion == null) {
            primeraSeleccion = nodo;
        } else if (segundaSeleccion == null) {
            segundaSeleccion = nodo;
        } else {
            primeraSeleccion = nodo;
            segundaSeleccion = null;
        }

        if (nodo.getCarta() == null) {
            areaDetalle.setText("Celda seleccionada: Fila " + (nodo.getFila() + 1) + " Columna " + (nodo.getColumna() + 1) + "\nEsta posicion esta vacia.");
        } else {
            Carta carta = nodo.getCarta();
            areaDetalle.setText("Codigo: " + carta.getCodigo()
                    + "\nNombre: " + carta.getNombre()
                    + "\nTipo: " + carta.getTipo()
                    + "\nRareza: " + carta.getRareza()
                    + "\nAtaque: " + carta.getAtaque()
                    + "\nDefensa: " + carta.getDefensa()
                    + "\nPS: " + carta.getPuntosSalud()
                    + "\nImagen: " + carta.getImagen()
                    + "\nPosicion: Fila " + (nodo.getFila() + 1) + ", Columna " + (nodo.getColumna() + 1));
        }

        refrescarDatos();
    }

    private void mostrarDialogoAgregarCarta() {
        JTextField campoCodigo = new JTextField();
        JTextField campoNombre = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<String>(new String[]{"Fuego", "Agua", "Planta", "Electrico", "Psiquico", "Normal", "Oscuro", "Acero"});
        JComboBox<String> comboRareza = new JComboBox<String>(new String[]{"Comun", "Poco Comun", "Rara", "Ultra Rara", "Legendaria"});
        JTextField campoAtaque = new JTextField();
        JTextField campoDefensa = new JTextField();
        JTextField campoPs = new JTextField();
        JTextField campoImagen = new JTextField("sin_imagen.png");

        JPanel formulario = new JPanel(new GridLayout(8, 2, 8, 8));
        formulario.add(new JLabel("Codigo"));
        formulario.add(campoCodigo);
        formulario.add(new JLabel("Nombre"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Tipo"));
        formulario.add(comboTipo);
        formulario.add(new JLabel("Rareza"));
        formulario.add(comboRareza);
        formulario.add(new JLabel("Ataque"));
        formulario.add(campoAtaque);
        formulario.add(new JLabel("Defensa"));
        formulario.add(campoDefensa);
        formulario.add(new JLabel("PS"));
        formulario.add(campoPs);
        formulario.add(new JLabel("Imagen"));
        formulario.add(campoImagen);

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Agregar Carta", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String codigo = campoCodigo.getText().trim();
                String nombre = campoNombre.getText().trim();
                int ataque = Integer.parseInt(campoAtaque.getText().trim());
                int defensa = Integer.parseInt(campoDefensa.getText().trim());
                int ps = Integer.parseInt(campoPs.getText().trim());

                if (codigo.isEmpty() || nombre.isEmpty() || ataque <= 0 || defensa <= 0 || ps <= 0) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben tener valores validos.");
                    return;
                }

                Carta carta = new Carta(
                        codigo,
                        nombre,
                        (String) comboTipo.getSelectedItem(),
                        (String) comboRareza.getSelectedItem(),
                        ataque,
                        defensa,
                        ps,
                        campoImagen.getText().trim()
                );

                JOptionPane.showMessageDialog(this, manager.agregarCartaAlAlbum(carta));
                refrescadorGeneral.run();
                mostrarMensajesPendientes();
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(this, "Ataque, defensa y PS deben ser numeros positivos.");
            }
        }
    }

    private void intercambiarCartasSeleccionadas() {
        if (primeraSeleccion == null || segundaSeleccion == null) {
            JOptionPane.showMessageDialog(this, "Selecciona dos celdas para intercambiar.");
            return;
        }

        manager.intercambiarCartas(
                primeraSeleccion.getFila(),
                primeraSeleccion.getColumna(),
                segundaSeleccion.getFila(),
                segundaSeleccion.getColumna()
        );

        primeraSeleccion = null;
        segundaSeleccion = null;
        areaDetalle.setText("Las cartas seleccionadas fueron intercambiadas.");
        refrescadorGeneral.run();
    }

    private void mostrarMensajesPendientes() {
        String mensajes = manager.consumirMensajesPendientes();
        if (!mensajes.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensajes, "Recompensas", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}