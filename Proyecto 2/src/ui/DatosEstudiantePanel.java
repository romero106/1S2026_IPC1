package ui;

import java.awt.*;
import javax.swing.*;

import app.GameZoneProManager;
import model.*;

public class DatosEstudiantePanel extends JPanel {
    private GameZoneProManager manager;
    private Runnable volverMenu;
    private JLabel lblNombre;
    private JLabel lblCarnet;
    private JLabel lblCorreo;
    private JLabel lblSeccion;
    private JLabel lblSemestre;
    private JTextArea areaDescripcion;

    public DatosEstudiantePanel(GameZoneProManager manager, Runnable volverMenu) {
        this.manager = manager;
        this.volverMenu = volverMenu;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton botonMenu = new JButton("Volver al menu");
        botonMenu.addActionListener(e -> volverMenu.run());
        add(botonMenu, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridLayout(5, 1, 10, 10));
        datos.setBorder(BorderFactory.createTitledBorder("Informacion del Estudiante"));
        lblNombre = new JLabel();
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblCarnet = new JLabel();
        lblCorreo = new JLabel();
        lblSeccion = new JLabel();
        lblSemestre = new JLabel();
        datos.add(lblNombre);
        datos.add(lblCarnet);
        datos.add(lblCorreo);
        datos.add(lblSeccion);
        datos.add(lblSemestre);

        areaDescripcion = new JTextArea();
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setBorder(BorderFactory.createTitledBorder("Acerca de GameZone Pro"));

        add(datos, BorderLayout.NORTH);
        add(areaDescripcion, BorderLayout.CENTER);
    }

    public void refrescarDatos() {
        Estudiante datos = manager.getEstudiante();
        lblNombre.setText("Nombre: " + datos.getNombreCompleto());
        lblCarnet.setText("Carnet: " + datos.getCarnet());
        lblCorreo.setText("Correo: " + datos.getCorreo());
        lblSeccion.setText("Seccion: " + datos.getSeccion());
        lblSemestre.setText("Semestre: " + datos.getSemestre());
        areaDescripcion.setText(datos.getDescripcionProyecto());
    }
}