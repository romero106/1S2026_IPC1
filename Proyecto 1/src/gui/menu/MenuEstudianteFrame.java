package gui.menu;

import javax.swing.*;

import gui.auth.LoginFrame;
import gui.prestamos.gestion.*;
import model.Usuario;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEstudianteFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;
    Usuario usuario;

    JLabel lblMensaje;

    JButton btnMostrarLibros;
    JButton btnSolicitarPrestamo;
    JButton btnMiHistorial;
    JButton btnLogout;

    JPanel panel;
    // CONSTRUCTOR
    public MenuEstudianteFrame(SistemaBiblioteca sistema, Usuario usuario) {
        this.sistema = sistema;
        this.usuario = usuario;

        setTitle("Sistema Biblioteca");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        lblMensaje = new JLabel(
            "<html>Bienvenido, " + usuario.getNombre() +
                "<br>Carné: " + usuario.getCarne() +
            "<br>Rol: " + usuario.getRol() +
            "</html>"
        );

        btnMostrarLibros = new JButton("Ver libros");
        btnMostrarLibros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTexto("Libros disponibles", sistema.mostrarLibros());
            }
        });

        btnSolicitarPrestamo = new JButton("Solicitar préstamo");
        btnSolicitarPrestamo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SolicitarPrestamoEstudianteFrame solicitarFrame = new SolicitarPrestamoEstudianteFrame(sistema, usuario);
                solicitarFrame.setVisible(true);
            }
        });

        btnMiHistorial = new JButton("Mi historial");
        btnMiHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTexto("Historial de préstamos", sistema.mostrarPrestamosPorUsuario(usuario.getCarne()));
            }
        });

        btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sistema.registrarBitacora("CERRAR SESION", usuario.getCarne(), "MENU");
                LoginFrame loginFrame = new LoginFrame(sistema);
                loginFrame.setVisible(true);
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(lblMensaje);
        panel.add(btnMostrarLibros);
        panel.add(btnSolicitarPrestamo);
        panel.add(btnMiHistorial);
        panel.add(btnLogout);

        add(panel);
    }
    // METODOS
    private void mostrarTexto(String titulo, String contenido) {
        if (!contenido.isEmpty()) {
            JTextArea area = new JTextArea(contenido);
            area.setEditable(false);

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(600, 200));

            JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTextArea area = new JTextArea("Sin registros");
            area.setEditable(false);

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(600, 200));

            JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}