package gui.usuarios.operadores;

import javax.swing.*;

import gui.usuarios.operadores.gestion.*;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionOperadoresFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JButton btnMostrarOperadores;
    JButton btnRegistrarOperador;
    JButton btnEliminarOperador;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public GestionOperadoresFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Gestión de Operadores");
        setSize(500, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnMostrarOperadores = new JButton("Mostrar operadores");
        btnMostrarOperadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTexto("Operadores", sistema.mostrarOperadores());
            }
        });

        btnRegistrarOperador = new JButton("Registrar operador");
        btnRegistrarOperador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegistrarOperadorFrame(sistema).setVisible(true);
            }
        });

        btnEliminarOperador = new JButton("Eliminar operador");
        btnEliminarOperador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EliminarOperadorFrame(sistema).setVisible(true);
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

        panel.add(btnMostrarOperadores);
        panel.add(btnRegistrarOperador);
        panel.add(btnEliminarOperador);
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