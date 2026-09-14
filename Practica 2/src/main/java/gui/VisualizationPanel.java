package gui;

import javax.swing.*;
import java.awt.*;

import charts.*;
import stats.OperationStats;

public class VisualizationPanel extends JPanel implements LogUpdater {

    /* ATRIBUTOS */
    private ChartManager chartManager;

    private JLabel lblComparaciones;
    private JLabel lblIntercambios;
    private JLabel lblLlamadasRecursivas;
    private JLabel lblTiempo;

    private JTextArea areaLog;

    /* CONSTRUCTOR */
    public VisualizationPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chartManager = new ChartManager();
        add(chartManager.getChartPanel(), BorderLayout.CENTER);
        
        // ESTADISTICAS //
        lblComparaciones = new JLabel("0");
        lblIntercambios = new JLabel("0");
        lblLlamadasRecursivas = new JLabel("0");
        lblTiempo = new JLabel("0 ms");

        // LOG DE OPERACIONES //
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);

        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de operaciones"));
        scrollLog.setPreferredSize(new Dimension(0, 120));
        

        JPanel panelStats = new JPanel(new GridLayout(4, 2, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        panelStats.add(new JLabel("Comparaciones:"));
        panelStats.add(lblComparaciones);
        panelStats.add(new JLabel("Intercambios:"));
        panelStats.add(lblIntercambios);
        panelStats.add(new JLabel("Llamadas recursivas:"));
        panelStats.add(lblLlamadasRecursivas);
        panelStats.add(new JLabel("Tiempo:"));
        panelStats.add(lblTiempo);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.add(panelStats);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(scrollLog);

        add(panelInferior, BorderLayout.SOUTH);
    }

    /* METODOS */
    public void actualizarEstadisticas(OperationStats stats) {
        lblComparaciones.setText(String.valueOf(stats.getComparaciones()));
        lblIntercambios.setText(String.valueOf(stats.getIntercambios()));
        lblLlamadasRecursivas.setText(String.valueOf(stats.getLlamadasRecursivas()));
        lblTiempo.setText(stats.getTiempoTotal() + " ms");
    }

    public void limpiarEstadisticas() {
        lblComparaciones.setText("0");
        lblIntercambios.setText("0");
        lblLlamadasRecursivas.setText("0");
        lblTiempo.setText("0 ms");

    }

    @Override
    public void agregarLog(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            areaLog.append(mensaje + "\n");
            areaLog.setCaretPosition(areaLog.getDocument().getLength());
        });
    }

    public void limpiarLog() {
        SwingUtilities.invokeLater(() -> {
            areaLog.setText("");
        });
    }
    
    /* GETTERS */
    public ChartManager getChartManager() {
        return chartManager;
    }
}
