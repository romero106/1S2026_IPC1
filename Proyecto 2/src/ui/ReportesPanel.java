package ui;

import java.awt.*;
import javax.swing.*;

import app.GameZoneProManager;

public class ReportesPanel extends JPanel {
    private GameZoneProManager manager;
    private Runnable volverMenu;
    private JLabel lblUltimoReporte;

    public ReportesPanel(GameZoneProManager manager, Runnable volverMenu) {
        this.manager = manager;
        this.volverMenu = volverMenu;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton botonMenu = new JButton("Volver al menu");
        botonMenu.addActionListener(e -> volverMenu.run());
        add(botonMenu, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(2, 2, 15, 15));
        botones.add(crearBotonReporte("Reporte Inventario", () -> manager.generarReporteInventario()));
        botones.add(crearBotonReporte("Reporte Ventas", () -> manager.generarReporteVentas()));
        botones.add(crearBotonReporte("Reporte Album", () -> manager.generarReporteAlbum()));
        botones.add(crearBotonReporte("Reporte Torneos", () -> manager.generarReporteTorneos()));
        add(botones, BorderLayout.CENTER);

        lblUltimoReporte = new JLabel("Aun no se ha generado ningun reporte.");
        add(lblUltimoReporte, BorderLayout.SOUTH);
    }

    private JButton crearBotonReporte(String texto, GeneradorReporte generador) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> {
            try {
                String ruta = generador.generar();
                lblUltimoReporte.setText("Ultimo reporte generado: " + ruta);
                JOptionPane.showMessageDialog(this, "Reporte generado correctamente.");
            } catch (RuntimeException error) {
                JOptionPane.showMessageDialog(this, error.getMessage());
            }
        });
        return boton;
    }

    private interface GeneradorReporte {
        String generar();
    }
}