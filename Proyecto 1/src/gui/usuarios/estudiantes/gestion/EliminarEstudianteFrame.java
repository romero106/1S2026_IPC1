package gui.usuarios.estudiantes.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarEstudianteFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCarne;

    JButton btnEliminar;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public EliminarEstudianteFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Eliminar Estudiante");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCarne = new JTextField();

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarEstudiante();
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Carné del estudiante:"));
        panel.add(txtCarne);

        panel.add(btnVolver);
        panel.add(btnEliminar);

        add(panel);
    }
    // METODOS
    public void eliminarEstudiante() {
        String carne = txtCarne.getText().trim();

        if(!carne.isEmpty()) {
            String mensajeValidacion = sistema.validarEliminarEstudiante(carne);
            
            if(!mensajeValidacion.equals("OK")) {
                JOptionPane.showMessageDialog(this, mensajeValidacion);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al estudiante?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if(confirmacion == JOptionPane.YES_OPTION) {
                if(sistema.eliminarEstudiante(carne)) {
                    JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente.");
                    sistema.registrarBitacora("ELIMINAR ESTUDIANTE", "ADMIN/OPERADOR", "GESTION ESTUDIANTES");
                    txtCarne.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el estudiante.");
                    sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN/OPERADOR", "GESTION ESTUDIANTES");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un carné.");
        }
    }
}