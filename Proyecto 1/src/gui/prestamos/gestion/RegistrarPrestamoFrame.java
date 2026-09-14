package gui.prestamos.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarPrestamoFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCodigoPrestamo;
    JTextField txtCarneUsuario;
    JTextField txtCodigoLibro;
    JTextField txtFechaPrestamo;

    JButton btnRegistrar;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public RegistrarPrestamoFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Registrar Préstamo");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigoPrestamo = new JTextField();
        txtCarneUsuario = new JTextField();
        txtCodigoLibro = new JTextField();
        txtFechaPrestamo = new JTextField();

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarPrestamo();
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Código de préstamo:"));
        panel.add(txtCodigoPrestamo);

        panel.add(new JLabel("Carné del usuario:"));
        panel.add(txtCarneUsuario);

        panel.add(new JLabel("Código del libro:"));
        panel.add(txtCodigoLibro);

        panel.add(new JLabel("Fecha:"));
        panel.add(txtFechaPrestamo);
        
        panel.add(btnVolver);
        panel.add(btnRegistrar);

        add(panel);
    }
    // METODOS
    public void registrarPrestamo() {
        String codigoPrestamo = txtCodigoPrestamo.getText().trim();
        String carneUsuario = txtCarneUsuario.getText().trim();
        String codigoLibro = txtCodigoLibro.getText().trim();
        String fechaPrestamo = txtFechaPrestamo.getText().trim();

        if(!codigoPrestamo.isEmpty() && !carneUsuario.isEmpty() && !codigoLibro.isEmpty() && !fechaPrestamo.isEmpty()) {
            try {
                java.time.LocalDate.parse(fechaPrestamo, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "La fecha debe tener formato dd/MM/yyyy");
                return;
            }

            String mensajeValidacion = sistema.validarPrestamo(carneUsuario, codigoLibro);

            if(!mensajeValidacion.equals("OK")) {
                JOptionPane.showMessageDialog(null, mensajeValidacion);
                return;
            }

            boolean exitoso = sistema.registrarPrestamo(codigoPrestamo, carneUsuario, codigoLibro, fechaPrestamo);

            if(exitoso) {
                JOptionPane.showMessageDialog(null, "Préstamo registrado correctamente.");
                sistema.registrarBitacora("REGISTRAR PRESTAMO", carneUsuario, "GESTION PRESTAMOS");
                txtCodigoPrestamo.setText("");
                txtCarneUsuario.setText("");
                txtCodigoLibro.setText("");
                txtFechaPrestamo.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el préstamo.");
                sistema.registrarBitacora("OPERACION ERRONEA", carneUsuario, "GESTION PRESTAMOS");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
        }
    }
}