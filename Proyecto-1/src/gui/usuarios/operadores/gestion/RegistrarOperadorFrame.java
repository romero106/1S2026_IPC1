package gui.usuarios.operadores.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarOperadorFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCarne;
    JTextField txtNombre;
    JPasswordField txtContrasena;

    JButton btnRegistrar;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public RegistrarOperadorFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Registrar Operador");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCarne = new JTextField();
        txtNombre = new JTextField();
        txtContrasena = new JPasswordField();

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarOperador();
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

        panel.add(new JLabel("Carné:"));
        panel.add(txtCarne);

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);

        panel.add(btnVolver);
        panel.add(btnRegistrar);

        add(panel);
    }
    // METODOS
    public void registrarOperador() {
        String carne = txtCarne.getText().trim();
        String nombre = txtNombre.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if(!carne.isEmpty() && !nombre.isEmpty() && !contrasena.isEmpty()) {
            boolean registrado = sistema.registrarUsuario(carne, nombre, "N/A", contrasena, "Operador");
            if(registrado) {
                JOptionPane.showMessageDialog(this, "Operador registrado correctamente");
                sistema.registrarBitacora("REGISTRAR OPERADOR", "ADMIN", "GESTION OPERADORES");
                txtCarne.setText("");
                txtNombre.setText("");
                txtContrasena.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el operador. Carné duplicado o límite alcanzado");
                sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN", "GESTION OPERADORES");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
        }
    }
}