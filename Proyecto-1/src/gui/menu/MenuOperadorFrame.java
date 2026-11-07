package gui.menu;

import javax.swing.*;

import gui.auth.LoginFrame;
import gui.libros.GestionLibrosFrame;
import gui.prestamos.GestionPrestamosFrame;
import gui.usuarios.estudiantes.GestionEstudiantesFrame;
import model.Usuario;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuOperadorFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JLabel lblMensaje;

    JButton btnGestionarLibros;
    JButton btnGestionarEstudiantes;
    JButton btnGestionarPrestamos;
    JButton btnReporteBitacora;
    JButton btnLogout;

    JPanel panel;
    // CONSTRUCTOR
    public MenuOperadorFrame(SistemaBiblioteca sistema, Usuario usuario) {
        this.sistema = sistema;

        setTitle("Menú Operador");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        lblMensaje = new JLabel(
                "<html>Bienvenido, " + usuario.getNombre() +
                        "<br>Rol: " + usuario.getRol() +
                        "</html>");

        btnGestionarLibros = new JButton("Gestionar libros");
        btnGestionarLibros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GestionLibrosFrame(sistema, usuario).setVisible(true);
            }
        });

        btnGestionarEstudiantes = new JButton("Gestionar estudiantes");
        btnGestionarEstudiantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GestionEstudiantesFrame(sistema).setVisible(true);
            }
        });

        btnGestionarPrestamos = new JButton("Gestionar préstamos");
        btnGestionarPrestamos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GestionPrestamosFrame(sistema, usuario).setVisible(true);
            }
        });

        btnReporteBitacora = new JButton("Reporte bitácora");
        btnReporteBitacora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean generado = sistema.generarReporteBitacoraHTML();

                if (generado) {
                    JOptionPane.showMessageDialog(null,
                            "Reporte de bitácora generado correctamente en Proyecto1/reportes");
                    sistema.registrarBitacora("GENERAR REPORTE", usuario.getCarne(), "REPORTES");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo generar el reporte de bitácora.");
                }
            }
        });

        btnLogout = new JButton("Cerrar sesión");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sistema.registrarBitacora("CERRAR SESION", usuario.getCarne(), "MENU");
                LoginFrame loginFrame = new LoginFrame(sistema);
                loginFrame.setVisible(true);
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(lblMensaje);
        panel.add(btnGestionarLibros);
        panel.add(btnGestionarEstudiantes);
        panel.add(btnGestionarPrestamos);
        panel.add(btnReporteBitacora);
        panel.add(btnLogout);

        add(panel);
    }
}