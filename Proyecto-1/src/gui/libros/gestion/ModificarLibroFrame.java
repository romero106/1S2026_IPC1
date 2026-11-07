package gui.libros.gestion;

import javax.swing.*;

import service.SistemaBiblioteca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarLibroFrame extends JFrame {
    // ATRIBUTOS
    SistemaBiblioteca sistema;

    JTextField txtCodigo;
    JTextField txtTitulo;
    JTextField txtAutor;
    JTextField txtAnio;
    JTextField txtCantidad;

    JButton btnModificar;
    JButton btnVolver;

    JPanel panel;
    // CONSTRUCTOR
    public ModificarLibroFrame(SistemaBiblioteca sistema) {
        this.sistema = sistema;

        setTitle("Modificar Libro");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigo = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtAnio = new JTextField();
        txtCantidad = new JTextField();

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarLibro();
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

        panel.add(new JLabel("Código del libro:"));
        panel.add(txtCodigo);

        panel.add(new JLabel("Nuevo título:"));
        panel.add(txtTitulo);

        panel.add(new JLabel("Nuevo autor:"));
        panel.add(txtAutor);

        panel.add(new JLabel("Nuevo año:"));
        panel.add(txtAnio);

        panel.add(new JLabel("Nueva cantidad:"));
        panel.add(txtCantidad);

        panel.add(btnVolver);
        panel.add(btnModificar);

        add(panel);
    }
    // METODOS
    public void modificarLibro() {
        String codigo = txtCodigo.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String anioTexto = txtAnio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        int anio;
        int cantidad;

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

            String mensajeValidacion = sistema.validarModificarLibro(codigo, cantidad);

            if(!mensajeValidacion.equals("OK")) {
                JOptionPane.showMessageDialog(this, mensajeValidacion);
                return;
            }

            if(sistema.modificarLibro(codigo, titulo, autor, anio, cantidad)) {
                JOptionPane.showMessageDialog(this, "Libro modificado correctamente");
                sistema.registrarBitacora("MODIFICAR LIBRO", "ADMIN/OPERADOR", "GESTION LIBROS");
                txtCodigo.setText("");
                txtTitulo.setText("");
                txtAutor.setText("");
                txtAnio.setText("");
                txtCantidad.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el libro");
                sistema.registrarBitacora("OPERACION ERRONEA", "ADMIN/OPERADOR", "GESTION LIBROS");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
        }
    }
}