package gui.prestamos.gestion;

import javax.swing.*;

import model.Usuario;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolicitarPrestamoEstudianteFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;
    Usuario usuario;

    JTextField txtCodigoPrestamo;
    JTextField txtCodigoLibro;
    JTextField txtFechaPrestamo;

    JButton btnRegistrar;
    JButton btnVolver;

    JPanel panel;

    // CONSTRUCTOR
    public SolicitarPrestamoEstudianteFrame(SistemaBiblioteca sistema, Usuario usuario) {
        this.sistema = sistema;
        this.usuario = usuario;

        setTitle("Solicitar Préstamo");
        setSize(500, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigoPrestamo = new JTextField();
        txtCodigoLibro = new JTextField();
        txtFechaPrestamo = new JTextField();

        btnRegistrar = new JButton("Solicitar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solicitarPrestamo();
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Código de préstamo:"));
        panel.add(txtCodigoPrestamo);

        panel.add(new JLabel("Código del libro:"));
        panel.add(txtCodigoLibro);

        panel.add(new JLabel("Fecha:"));
        panel.add(txtFechaPrestamo);

        panel.add(btnVolver);
        panel.add(btnRegistrar);

        add(panel);
    }
    // METODOS
    public void solicitarPrestamo() {
        String codigoPrestamo = txtCodigoPrestamo.getText().trim();
        String carneUsuario = usuario.getCarne();
        String codigoLibro = txtCodigoLibro.getText().trim();
        String fechaPrestamo = txtFechaPrestamo.getText().trim();

        if (!codigoPrestamo.isEmpty() && !carneUsuario.isEmpty() && !codigoLibro.isEmpty() && !fechaPrestamo.isEmpty()) {
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
                sistema.registrarBitacora("SOLICITAR PRESTAMO", usuario.getCarne(), "GESTION PRESTAMOS");
                txtCodigoPrestamo.setText("");
                txtCodigoLibro.setText("");
                txtFechaPrestamo.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el préstamo.");
                sistema.registrarBitacora("OPERACION ERRONEA", usuario.getCarne(), "GESTION PRESTAMOS");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
        }
    }
}