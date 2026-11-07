package gui.libros;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import gui.libros.gestion.*;
import model.Libro;
import model.Usuario;
import service.SistemaBiblioteca;

import java.awt.*;

public class GestionLibrosFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;
    Usuario usuario;

    JTable tabla;
    DefaultTableModel modeloTabla;

    // CONSTRUCTOR
    public GestionLibrosFrame(SistemaBiblioteca sistema, Usuario usuario) {
        this.sistema = sistema;

        setTitle("Sistema Biblioteca");
        setSize(1280,720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        /* ============================================================= */
        /*                      PANEL BOTONES */
        /* ============================================================= */
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> {
            cargarDatos();
        });

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.addActionListener(e -> {
                new RegistrarLibroFrame(sistema).setVisible(true);
        });

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> {
                new ModificarLibroFrame(sistema).setVisible(true);
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> {
                new EliminarLibroFrame(sistema).setVisible(true);
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnNuevo);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        /* ============================================================= */
        /*                      PANEL BUSQUEDA */
        /* ============================================================= */
        JLabel lblBusqueda = new JLabel("Busqueda:");
        JTextField txtBusqueda = new JTextField();

        JPanel panelBusqueda = new JPanel(new BorderLayout(5,0));
        panelBusqueda.add(lblBusqueda, BorderLayout.WEST);
        panelBusqueda.add(txtBusqueda, BorderLayout.CENTER);

        /* ============================================================= */
        /*                      PANEL SUPERIOR */
        /* ============================================================= */
        JPanel panelSuperior = new JPanel(new BorderLayout(10,0));

        panelSuperior.add(panelBotones, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);

        /* ============================================================= */
        /*                          TABLA */
        /* ============================================================= */
        String[] columns = {"Código","Título","Autor","Año","Disponibles"};
        modeloTabla = new DefaultTableModel(columns, 0);

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);

        cargarDatos();

        JScrollPane tablaLibros = new JScrollPane(tabla);

        /* ============================================================= */
        /*                      PANEL PRINCIPAL */
        /* ============================================================= */
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(panelPrincipal);

        JPanel encabezado = new JPanel(new BorderLayout(0, 5));
        encabezado.add(new JLabel(
                "<html>Bienvenido, " + usuario.getNombre() +
                "<br>Rol: " + usuario.getRol() + "</html>"
            ), BorderLayout.NORTH);
        encabezado.add(panelSuperior, BorderLayout.CENTER);

        panelPrincipal.add(encabezado, BorderLayout.NORTH);
        panelPrincipal.add(tablaLibros, BorderLayout.CENTER);
    }
    // METODOS
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        Libro[] libros = sistema.getLibros();

        for (int i = 0; i < libros.length; i++) {
            if (libros[i] != null) {
                modeloTabla.addRow(new Object[] {
                        libros[i].getCodigo(),
                        libros[i].getTitulo(),
                        libros[i].getAutor(),
                        libros[i].getAnio(),
                        libros[i].getCantidad()
                });
            }
        }
    }
}