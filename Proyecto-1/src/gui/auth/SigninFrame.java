package gui.auth;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SigninFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCarne;
    JTextField txtNombre;
    JPasswordField txtContrasena;

    JComboBox<String> boxCarrera;

    JButton btnRegister;
    JButton btnLogin;

    JPanel panel;
    // CONSTRUCTOR
    public SigninFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;
        
        setTitle("Sistema Biblioteca");
        setSize(400,250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCarne = new JTextField();
        txtNombre = new JTextField();
        txtContrasena = new JPasswordField();
        
        String[] carreras = {"","Ciencias y Sistemas", "Civíl", "Electrónica", "Eléctrica", "Industrial" , "Mecánica" , "Quimica"};
        boxCarrera = new JComboBox<>(carreras);
        
        btnRegister = new JButton("Registrarse");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarSesion();
            }
        });
        
        btnLogin = new JButton("Volver");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame Login = new LoginFrame(sistema);
                Login.setVisible(true);
                dispose();
            }
        });
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(6,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Carne:"));
        panel.add(txtCarne);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Carrera:"));
        panel.add(boxCarrera);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);
        panel.add(new JLabel());
        panel.add(btnRegister);
        panel.add(new JLabel());
        panel.add(btnLogin);
        add(panel);
    }
    // METODOS
    public void registrarSesion() {
        String carneIngresado = txtCarne.getText().trim();
        String nombreIngresado = txtNombre.getText().trim();
        String carreraIngresada = (String) boxCarrera.getSelectedItem();
        String contrasenaIngresada = new String(txtContrasena.getPassword()).trim();  

        if(!carneIngresado.isEmpty() && !nombreIngresado.isEmpty() && !carreraIngresada.isEmpty() && !contrasenaIngresada.isEmpty()) {
            if(sistema.registrarUsuario(carneIngresado, nombreIngresado, carreraIngresada, contrasenaIngresada, "Estudiante")) {
                JOptionPane.showMessageDialog(null, "Registro exitoso");
                
                LoginFrame Login = new LoginFrame(sistema);
                Login.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario. Carné duplicado o límite alcanzado");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
        }
    }
    
}
