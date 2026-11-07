package gui.prestamos.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DevolverPrestamoFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCodigoPrestamo;

    JButton btnDevolver;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public DevolverPrestamoFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Devolver Préstamo");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigoPrestamo = new JTextField();

        btnDevolver = new JButton("Devolver");
        btnDevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                devolverPrestamo();
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

        panel.add(new JLabel("Código de préstamo:"));
        panel.add(txtCodigoPrestamo);

        panel.add(btnVolver);
        panel.add(btnDevolver);

        add(panel);        
    }
    // METODOS
    public void devolverPrestamo() {
        String codigoPrestamo = txtCodigoPrestamo.getText().trim();

        if(!codigoPrestamo.isEmpty()) {
            if(sistema.devolverPrestamo(codigoPrestamo)) {
                JOptionPane.showMessageDialog(this, "Préstamo devuelto correctamente");
                sistema.registrarBitacora("DEVOLVER PRESTAMO", "ADMIN/OPERADOR", "GESTION PRESTAMOS");
                txtCodigoPrestamo.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo devolver el préstamo. Verifique el código");
                sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN/OPERADOR", "GESTION PRESTAMOS");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código del préstamo");
        }
    }
}