package gui.libros.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarLibroFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCodigo;

    JPanel panel;
    // CONSTRUCTOR
    public EliminarLibroFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Eliminar Libro");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigo = new JTextField();

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarLibro();
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código del libro:"));
        panel.add(txtCodigo);

        panel.add(btnVolver);
        panel.add(btnEliminar);

        add(panel);
    }
    // METODOS
    public void eliminarLibro() {
        String codigo = txtCodigo.getText().trim();

        if(!codigo.isEmpty()) {
            String mensajeValidacion = sistema.validarEliminarLibro(codigo);

            if(!mensajeValidacion.equals("OK")) {
                JOptionPane.showMessageDialog(this, mensajeValidacion);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el libro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if(confirmacion == JOptionPane.YES_OPTION) {
                if(sistema.eliminarLibro(codigo)) {
                    JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
                    sistema.registrarBitacora("ELIMINAR LIBRO", "ADMIN/OPERADOR", "GESTION LIBROS");
                    txtCodigo.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el libro.");
                    sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN/OPERADOR", "GESTION LIBROS");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un código.");
        }
    }
}