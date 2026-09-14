package gui.libros.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarLibroFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCodigo;
    JTextField txtTitulo;
    JTextField txtAutor;
    JTextField txtAnio;
    JTextField txtCantidad;

    JButton btnRegistrar;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public RegistrarLibroFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Registrar Libro");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigo = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtAnio = new JTextField();
        txtCantidad = new JTextField();

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarLibro();
            }
        });
        
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);

        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);

        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);

        panel.add(new JLabel("Año:"));
        panel.add(txtAnio);

        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);

        panel.add(btnVolver);
        panel.add(btnRegistrar);

        add(panel);
    }
    // METODOS
    public void registrarLibro() {
        int anio;
        int cantidad;

        String codigo = txtCodigo.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String anioTexto = txtAnio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        if(!codigo.isEmpty() && !titulo.isEmpty() && !autor.isEmpty() && !anioTexto.isEmpty() && !cantidadTexto.isEmpty()) {
            try {
                anio = Integer.parseInt(anioTexto);
                cantidad = Integer.parseInt(cantidadTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Año y cantidad deben ser números válidos");
                return;
            }

            if(anio <= 0 || cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "Año y cantidad deben ser mayores que 0");
            return;
            }

            if(sistema.registrarLibro(codigo, titulo, autor, anio, cantidad)) {
                JOptionPane.showMessageDialog(this, "Libro registrado correctamente");
                sistema.registrarBitacora("REGISTRAR LIBRO", "ADMIN/OPERADOR", "GESTION LIBROS");
                txtCodigo.setText("");
                txtTitulo.setText("");
                txtAutor.setText("");
                txtAnio.setText("");
                txtCantidad.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el libro. Código duplicado o límite alcanzado");
                sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN/OPERADOR", "GESTION LIBROS");
                return;
            }        
        } else {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
        }
    }
}