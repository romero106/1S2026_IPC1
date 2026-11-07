package gui;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    
    /* ATRIBUTOS */
    private JTextField inputArreglo;

    private JComboBox<String> boxAlgoritmos;
    private JComboBox<String> boxVariante;
    private JComboBox<String> boxVelocidad;
    private JComboBox<String> boxOrden;

    private JButton btnAleatorio;
    private JButton btnIniciar;
    private JButton btnDetener;
    private JButton btnReiniciar;

    /* CONSTRUCTOR */
    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(375, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        /* Campo de texto para el Arreglo */
        inputArreglo = new JTextField();
        inputArreglo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        inputArreglo.setBorder(BorderFactory.createTitledBorder("Datos del arreglo:"));
        add(inputArreglo);
        add(Box.createVerticalStrut(20));

        /* ComboBox Algoritmo */
        add(new JLabel("Algoritmo:"));
        add(Box.createVerticalStrut(20));
        String[] algoritmos = { "BubbleSort", "QuickSort", "ShellSort" };
        boxAlgoritmos = new JComboBox<>(algoritmos);
        boxAlgoritmos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        boxAlgoritmos.setAlignmentX(LEFT_ALIGNMENT);
        add(boxAlgoritmos);
        add(Box.createVerticalStrut(20));

        /* ComboBox variante */
        add(new JLabel("Variante:"));
        add(Box.createVerticalStrut(20));
        boxVariante = new JComboBox<>();
        boxVariante.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        boxVariante.setAlignmentX(LEFT_ALIGNMENT);
        add(boxVariante);
        add(Box.createVerticalStrut(20));

        add(new JLabel("Orden:"));
        add(Box.createVerticalStrut(10));
        String[] ordenamiento = { "Ascendente", "Descendente" };
        boxOrden = new JComboBox<>(ordenamiento);
        boxOrden.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        boxOrden.setAlignmentX(LEFT_ALIGNMENT);
        add(boxOrden);
        add(Box.createVerticalStrut(20));

        /* ComboBox Velocidad */
        add(new JLabel("Velocidad:"));
        add(Box.createVerticalStrut(20));
        String[] velocidad = { "Lento (500 ms)", "Medio (100 ms)", "Rapido (20 ms)" };
        boxVelocidad = new JComboBox<>(velocidad);
        boxVelocidad.setSelectedIndex(1);
        boxVelocidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        boxVelocidad.setAlignmentX(LEFT_ALIGNMENT);
        add(boxVelocidad);
        add(Box.createVerticalStrut(60));

        /* Boton aleatorio */
        btnAleatorio = new JButton("Generar aleatorio");
        btnAleatorio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        add(btnAleatorio);
        add(Box.createVerticalStrut(10));

        /* Boton iniciar */
        btnIniciar = new JButton("Iniciar");
        btnIniciar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        add(btnIniciar);
        add(Box.createVerticalStrut(10));

        /* Boton detener */
        btnDetener = new JButton("Detener");
        btnDetener.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        add(btnDetener);
        add(Box.createVerticalStrut(10));

        /* Boton reiniciar */
        btnReiniciar = new JButton("Reiniciar vista");
        btnReiniciar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        add(btnReiniciar);
    }

    /* METODOS */
    
    /* GETTTERS */
    public JTextField getInputArreglo() {
        return inputArreglo;
    }

    public JButton getBtnAleatorio() {
        return btnAleatorio;
    }

    public JComboBox<String> getBoxAlgoritmos() {
        return boxAlgoritmos;
    }

    public JComboBox<String> getBoxVariante() {
        return boxVariante;
    }

    public int getVelocidad() {
        String velocidad = (String) boxVelocidad.getSelectedItem();
        if (velocidad.equals("Lento (500 ms)")) {
            return 500;
        } else if (velocidad.equals("Medio (100 ms)")) {
            return 100;
        } else {
        return 20;
        }
    }

    public JComboBox<String> getBoxOrden() {
        return boxOrden;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public JButton getBtnDetener() {
        return btnDetener;
    }

    public JButton getBtnReiniciar() {
        return btnReiniciar;
    }
    
}