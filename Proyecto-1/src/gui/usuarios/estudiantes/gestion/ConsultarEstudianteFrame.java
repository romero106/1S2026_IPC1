package gui.usuarios.estudiantes.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsultarEstudianteFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCarne;
    JTextArea txtResultado;

    JButton btnConsultar;
    JButton btnVolver;

    JPanel panelSuperior;
    JPanel panelBotones;
    // CONSTRUCTOR
    public ConsultarEstudianteFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Consultar Estudiante");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        txtCarne = new JTextField();
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);

        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sistema.registrarBitacora("CONSULTAR ESTUDIANTE", "ADMIN/OPERADOR", "GESTION ESTUDIANTES");
                consultarEstudiante();
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelSuperior.add(new JLabel("Carné del estudiante:"));
        panelSuperior.add(txtCarne);

        panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnVolver);
        panelBotones.add(btnConsultar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    // METODOS
    public void consultarEstudiante() {
        String carne = txtCarne.getText().trim();

        if(!carne.isEmpty()) {
            String info = sistema.consultarEstudiante(carne);
            txtResultado.setText(info);
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un carné.");
        }
    }
}