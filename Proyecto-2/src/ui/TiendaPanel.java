package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import app.GameZoneProManager;
import model.*;
import util.*;

public class TiendaPanel extends JPanel {
    private GameZoneProManager manager;
    private Runnable refrescadorGeneral;
    private Runnable volverMenu;
    private JTextField campoBusqueda;
    private JComboBox<String> boxGenero;
    private JComboBox<String> boxPlataforma;
    private JPanel panelCatalogo;
    private JPanel panelCarrito;
    private JTextArea areaHistorial;
    private JLabel lblTotal;

    public TiendaPanel(GameZoneProManager manager, Runnable refrescadorGeneral, Runnable volverMenu) {
        this.manager = manager;
        this.refrescadorGeneral = refrescadorGeneral;
        this.volverMenu = volverMenu;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        JButton botonMenu = new JButton("Volver al menu");
        botonMenu.addActionListener(e -> volverMenu.run());
        panelSuperior.add(botonMenu, BorderLayout.WEST);

        JPanel filtros = new JPanel(new GridLayout(1, 3, 10, 10));
        campoBusqueda = new JTextField();
        boxGenero = new JComboBox<String>(
                new String[] { "Todos", "Accion", "RPG", "Estrategia", "Deportes", "Terror", "Aventura" });
        boxPlataforma = new JComboBox<String>(
                new String[] { "Todas", "PC", "PlayStation", "Xbox", "Nintendo Switch" });
        filtros.add(crearCampoConTitulo("Buscar por codigo o nombre", campoBusqueda));
        filtros.add(crearCampoConTitulo("Genero", boxGenero));
        filtros.add(crearCampoConTitulo("Plataforma", boxPlataforma));
        panelSuperior.add(filtros, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        panelCatalogo = new JPanel(new GridLayout(0, 3, 12, 12));
        JScrollPane scrollCatalogo = new JScrollPane(panelCatalogo);
        scrollCatalogo.setPreferredSize(new Dimension(820, 600));

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelCarrito = new JPanel();
        panelCarrito.setLayout(new BoxLayout(panelCarrito, BoxLayout.Y_AXIS));
        JScrollPane scrollCarrito = new JScrollPane(panelCarrito);
        scrollCarrito.setPreferredSize(new Dimension(380, 340));

        JPanel panelHistorial = new JPanel(new BorderLayout());
        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setLineWrap(true);
        areaHistorial.setWrapStyleWord(true);
        panelHistorial.add(new JLabel("Historial de Compras"), BorderLayout.NORTH);
        panelHistorial.add(new JScrollPane(areaHistorial), BorderLayout.CENTER);

        lblTotal = new JLabel("Total: Q0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        JButton botonConfirmar = new JButton("Confirmar Compra");
        botonConfirmar.addActionListener(e -> confirmarCompra());

        JPanel panelInferiorCarrito = new JPanel(new BorderLayout());
        panelInferiorCarrito.add(lblTotal, BorderLayout.WEST);
        panelInferiorCarrito.add(botonConfirmar, BorderLayout.EAST);

        JPanel bloqueCarrito = new JPanel(new BorderLayout(10, 10));
        bloqueCarrito.add(new JLabel("Carrito"), BorderLayout.NORTH);
        bloqueCarrito.add(scrollCarrito, BorderLayout.CENTER);
        bloqueCarrito.add(panelInferiorCarrito, BorderLayout.SOUTH);

        panelDerecho.add(bloqueCarrito, BorderLayout.NORTH);
        panelDerecho.add(panelHistorial, BorderLayout.CENTER);

        JPanel cuerpo = new JPanel(new BorderLayout(15, 15));
        cuerpo.add(scrollCatalogo, BorderLayout.CENTER);
        cuerpo.add(panelDerecho, BorderLayout.EAST);
        add(cuerpo, BorderLayout.CENTER);

        DocumentListener escucha = new DocumentListener() {
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
        };

        campoBusqueda.getDocument().addDocumentListener(escucha);
        boxGenero.addActionListener(e -> refrescarDatos());
        boxPlataforma.addActionListener(e -> refrescarDatos());
    }

    private JPanel crearCampoConTitulo(String titulo, JComponent componente) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel(titulo), BorderLayout.NORTH);
        panel.add(componente, BorderLayout.CENTER);
        return panel;
    }

    public void refrescarDatos() {
        renderizarCatalogo();
        renderizarCarrito();
        renderizarHistorial();
    }

    private void renderizarCatalogo() {
        panelCatalogo.removeAll();

        NodoSimple<Juego> actual = manager.getCatalogo().getCabeza();
        while (actual != null) {
            Juego juego = actual.dato;

            if (manager.juegoCoincideFiltro(juego, campoBusqueda.getText(), (String) boxGenero.getSelectedItem(),
                    (String) boxPlataforma.getSelectedItem())) {
                panelCatalogo.add(crearTarjetaJuego(juego));
            }

            actual = actual.siguiente;
        }

        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }

    private JPanel crearTarjetaJuego(Juego juego) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254), 2),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel titulo = new JLabel(juego.getNombre());
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        tarjeta.add(titulo, BorderLayout.NORTH);

        JTextArea descripcion = new JTextArea(
                "Codigo: " + juego.getCodigo()
                        + "\nGenero: " + juego.getGenero()
                        + "\nPlataforma: " + juego.getPlataforma()
                        + "\nPrecio: Q" + manager.formatearMonto(juego.getPrecio())
                        + "\nStock: " + juego.getStock()
                        + "\n\n" + juego.getDescripcion());
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setBackground(tarjeta.getBackground());
        tarjeta.add(descripcion, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonDetalle = new JButton("Detalles");
        botonDetalle.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Codigo: " + juego.getCodigo()
                        + "\nNombre: " + juego.getNombre()
                        + "\nGenero: " + juego.getGenero()
                        + "\nPlataforma: " + juego.getPlataforma()
                        + "\nPrecio: Q" + manager.formatearMonto(juego.getPrecio())
                        + "\nStock: " + juego.getStock()
                        + "\nDescripcion: " + juego.getDescripcion(),
                "Detalle del Juego",
                JOptionPane.INFORMATION_MESSAGE));

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, manager.agregarAlCarrito(juego.getCodigo()));
            refrescadorGeneral.run();
            mostrarMensajesPendientes();
        });

        acciones.add(botonDetalle);
        acciones.add(botonAgregar);
        tarjeta.add(acciones, BorderLayout.SOUTH);

        return tarjeta;
    }

    private void renderizarCarrito() {
        panelCarrito.removeAll();

        if (manager.getCarrito().estaVacia()) {
            panelCarrito.add(new JLabel("Tu carrito esta vacio."));
        } else {
            NodoSimple<CarritoItem> actual = manager.getCarrito().getCabeza();

            while (actual != null) {
                panelCarrito.add(crearFilaCarrito(actual.dato));
                actual = actual.siguiente;
            }
        }

        lblTotal.setText("Total: Q" + manager.formatearMonto(manager.calcularTotalCarrito()));
        panelCarrito.revalidate();
        panelCarrito.repaint();
    }

    private JPanel crearFilaCarrito(CarritoItem item) {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
        fila.setMaximumSize(new Dimension(360, 90));
        fila.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));

        JLabel lbl = new JLabel("<html><strong>" + item.getJuego().getNombre() + "</strong><br>Cantidad: "
                + item.getCantidad() + " | Subtotal: Q" + manager.formatearMonto(item.getSubtotal()) + "</html>");
        fila.add(lbl, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new GridLayout(1, 3, 5, 5));
        JTextField campoCantidad = new JTextField(String.valueOf(item.getCantidad()));

        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.addActionListener(e -> {
            try {
                int cantidad = Integer.parseInt(campoCantidad.getText().trim());
                JOptionPane.showMessageDialog(this,
                        manager.actualizarCantidadCarrito(item.getJuego().getCodigo(), cantidad));
                refrescadorGeneral.run();
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser numerica.");
            }
        });

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> {
            manager.eliminarDelCarrito(item.getJuego().getCodigo());
            refrescadorGeneral.run();
        });

        acciones.add(campoCantidad);
        acciones.add(botonActualizar);
        acciones.add(botonEliminar);
        fila.add(acciones, BorderLayout.SOUTH);

        return fila;
    }

    private void renderizarHistorial() {
        String texto = "";
        NodoSimple<Compra> actual = manager.getHistorialCompras().getCabeza();

        if (actual == null) {
            texto = "Aun no hay compras registradas.";
        } else {
            while (actual != null) {
                texto += actual.dato.getFechaHora() + " | " + actual.dato.getDetalle() + " | Total Q"
                        + manager.formatearMonto(actual.dato.getTotal()) + "\n";
                actual = actual.siguiente;
            }
        }

        areaHistorial.setText(texto);
    }

    private void confirmarCompra() {
        String mensaje = manager.confirmarCompra();
        JOptionPane.showMessageDialog(this, mensaje);
        refrescadorGeneral.run();
        mostrarMensajesPendientes();
    }

    private void mostrarMensajesPendientes() {
        String mensajes = manager.consumirMensajesPendientes();
        if (!mensajes.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensajes, "Recompensas", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}