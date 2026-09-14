package gui.usuarios.operadores.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarOperadorFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCarne;

    JButton btnEliminar;
    JButton btnVolver;

    JPanel panel;

    public EliminarOperadorFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Eliminar Operador");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCarne = new JTextField();

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarOperador();
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

        panel.add(new JLabel("Carné del operador:"));
        panel.add(txtCarne);

        panel.add(btnVolver);
        panel.add(btnEliminar);
        
        add(panel);    
    }

    public void eliminarOperador() {
        String carne = txtCarne.getText().trim();

        if(!carne.isEmpty()) {
            String mensajeValidacion = sistema.validarEliminarOperador(carne);

            if(!mensajeValidacion.equals("OK")) {
                JOptionPane.showMessageDialog(this, mensajeValidacion);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el operador?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if(confirmacion == JOptionPane.YES_OPTION) {
                if(sistema.eliminarOperador(carne)) {
                    JOptionPane.showMessageDialog(this, "Operador eliminado correctamente.");
                    sistema.registrarBitacora("ELIMINAR OPERADOR", "ADMIN", "GESTION OPERADORES");
                    txtCarne.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el operador.");
                    sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN", "GESTION OPERADORES");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un carné.");
        }
    }
}