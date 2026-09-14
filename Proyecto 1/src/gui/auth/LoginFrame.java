package gui.auth;

import javax.swing.*;

import gui.menu.MenuAdminFrame;
import gui.menu.MenuEstudianteFrame;
import gui.menu.MenuOperadorFrame;
import model.Usuario;
import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtUsuario;
    JPasswordField txtContrasena;

    JButton btnLogin;
    JButton btnRegister;

    JPanel panel;
    // CONSTRUCTOR
    public LoginFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Sistema Biblioteca");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();

        btnLogin = new JButton("Iniciar sesion");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        btnRegister = new JButton("Crear cuenta");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SigninFrame Signin = new SigninFrame(sistema);
                Signin.setVisible(true);
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);

        panel.add(new JLabel());
        panel.add(btnLogin);

        panel.add(new JLabel());
        panel.add(btnRegister);
        
        add(panel);

    }
    // METODOS
    public void iniciarSesion() {
        String carneIngresado = txtUsuario.getText();
        String contrasenaIngresada = new String(txtContrasena.getPassword());

        Usuario usuario = sistema.iniciarSesion(carneIngresado, contrasenaIngresada);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Inicio de sesion exitoso");
            sistema.registrarBitacora("LOGIN EXITOSO", usuario.getCarne(), "AUTENTICACION");
            String rol = usuario.getRol();

            if (rol.equals("Administrador")) {
                MenuAdminFrame MenuAdmin = new MenuAdminFrame(sistema, usuario);
                MenuAdmin.setVisible(true);
                dispose();
            } else if(rol.equals("Estudiante")) {
                MenuEstudianteFrame MenuEstudiante = new MenuEstudianteFrame(sistema, usuario);
                MenuEstudiante.setVisible(true);
                dispose();
            } else if (rol.equals("Operador")) {
                MenuOperadorFrame MenuOp = new MenuOperadorFrame(sistema, usuario);
                MenuOp.setVisible(true);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecto(s)");
            sistema.registrarBitacora("LOGIN FALLIDO", carneIngresado, "AUTENTICACION");
        }
    }
}