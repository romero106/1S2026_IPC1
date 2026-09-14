package gui.prestamos;

import javax.swing.*;

import gui.prestamos.gestion.*;
import model.Usuario;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionPrestamosFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;
    Usuario usuario;

    JButton btnMostrarPrestamos;
    JButton btnRegistrarPrestamo;
    JButton btnDevolverPrestamo;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public GestionPrestamosFrame(SistemaBiblioteca sistema, Usuario usuario) {
        this.sistema = sistema;
        this.usuario = usuario;

        setTitle("Gestión de Préstamos");
        setSize(500, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnMostrarPrestamos = new JButton("Mostrar préstamos");
        btnMostrarPrestamos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTexto("Préstamos", sistema.mostrarPrestamos());
            }
        });

        btnRegistrarPrestamo = new JButton("Registrar préstamo");
        btnRegistrarPrestamo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegistrarPrestamoFrame(sistema).setVisible(true);
            }
        });

        btnDevolverPrestamo = new JButton("Devolver préstamo");
        btnDevolverPrestamo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DevolverPrestamoFrame(sistema).setVisible(true);
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(btnMostrarPrestamos);
        panel.add(btnRegistrarPrestamo);
        panel.add(btnDevolverPrestamo);
        panel.add(btnVolver);

        add(panel);
    }
    // METODOS
    private void mostrarTexto(String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 200));

        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}