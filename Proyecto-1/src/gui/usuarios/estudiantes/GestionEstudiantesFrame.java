package gui.usuarios.estudiantes;

import javax.swing.*;

import gui.usuarios.estudiantes.gestion.*;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionEstudiantesFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JButton btnMostrarEstudiantes;
    JButton btnConsultarEstudiante;
    JButton btnEliminarEstudiante;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public GestionEstudiantesFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Gestión de Estudiantes");
        setSize(500, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnMostrarEstudiantes = new JButton("Mostrar estudiantes");
        btnMostrarEstudiantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTexto("Estudiantes", sistema.mostrarEstudiantes());
            }
        });

        btnConsultarEstudiante = new JButton("Consultar estudiante");
        btnConsultarEstudiante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConsultarEstudianteFrame(sistema).setVisible(true);
            }
        });

        btnEliminarEstudiante = new JButton("Eliminar estudiante");
        btnEliminarEstudiante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EliminarEstudianteFrame(sistema).setVisible(true);
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

        panel.add(btnMostrarEstudiantes);
        panel.add(btnConsultarEstudiante);
        panel.add(btnEliminarEstudiante);
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