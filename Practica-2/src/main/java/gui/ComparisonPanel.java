package gui;

import javax.swing.*;
import java.awt.*;

import stats.*;
import threads.*;
import utils.Arrays;

public class ComparisonPanel extends JPanel {

    /* ATRIBUTOS */
    private VisualizationPanel panelIterativo;
    private VisualizationPanel panelRecursivo;

    private SortingThread hiloIterativo;
    private SortingThread hiloRecursivo;

    private OperationStats statsIterativo;
    private OperationStats statsRecursivo;

    private JLabel lblComparacionesIterativo;
    private JLabel lblComparacionesRecursivo;
    private JLabel lblIntercambiosIterativo;
    private JLabel lblIntercambiosRecursivo;
    private JLabel lblLlamadasIterativo;
    private JLabel lblLlamadasRecursivo;
    private JLabel lblTiempoIterativo;
    private JLabel lblTiempoRecursivo;

    private JButton btnDetenerIterativo;
    private JButton btnDetenerRecursivo;

    /* CONSTRUCTOR */
    public ComparisonPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelIterativo = new VisualizationPanel();
        panelRecursivo = new VisualizationPanel();

        JPanel panelVisualizaciones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelVisualizaciones.add(crearPanelAlgoritmo("BubbleSort Iterativo", panelIterativo, true));
        panelVisualizaciones.add(crearPanelAlgoritmo("BubbleSort Recursivo", panelRecursivo, false));

        add(panelVisualizaciones, BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.SOUTH);
    }

    /* METODOS */
    private JPanel crearPanelAlgoritmo(String titulo, VisualizationPanel panel, boolean iterativo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createTitledBorder(titulo));
        contenedor.add(panel, BorderLayout.CENTER);

        JButton btnDetener = new JButton("Detener");
        btnDetener.addActionListener(e -> {
            if (iterativo) {
                if (hiloIterativo != null && hiloIterativo.corriendo()) {
                    hiloIterativo.detener();
                }
            } else {
                if (hiloRecursivo != null && hiloRecursivo.corriendo()) {
                    hiloRecursivo.detener();
                }
            }
        });

        if (iterativo) {
            btnDetenerIterativo = btnDetener;
        } else {
            btnDetenerRecursivo = btnDetener;
        }

        contenedor.add(btnDetener, BorderLayout.NORTH);
        return contenedor;
    }

    private JPanel crearPanelResumen() {
        JPanel panelResumen = new JPanel(new GridLayout(5, 3, 10, 5));
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen comparativo"));

        lblComparacionesIterativo = new JLabel("0");
        lblComparacionesRecursivo = new JLabel("0");
        lblIntercambiosIterativo = new JLabel("0");
        lblIntercambiosRecursivo = new JLabel("0");
        lblLlamadasIterativo = new JLabel("0");
        lblLlamadasRecursivo = new JLabel("0");
        lblTiempoIterativo = new JLabel("0 ms");
        lblTiempoRecursivo = new JLabel("0 ms");

        panelResumen.add(new JLabel(""));
        panelResumen.add(new JLabel("Iterativo"));
        panelResumen.add(new JLabel("Recursivo"));

        panelResumen.add(new JLabel("Comparaciones:"));
        panelResumen.add(lblComparacionesIterativo);
        panelResumen.add(lblComparacionesRecursivo);

        panelResumen.add(new JLabel("Intercambios:"));
        panelResumen.add(lblIntercambiosIterativo);
        panelResumen.add(lblIntercambiosRecursivo);

        panelResumen.add(new JLabel("Llamadas recursivas:"));
        panelResumen.add(lblLlamadasIterativo);
        panelResumen.add(lblLlamadasRecursivo);

        panelResumen.add(new JLabel("Tiempo:"));
        panelResumen.add(lblTiempoIterativo);
        panelResumen.add(lblTiempoRecursivo);

        return panelResumen;
    }

    public void iniciarComparacion(int[] arregloOriginal, int velocidad, String orden) {
        detenerComparacion();

        int[] arregloIterativo = Arrays.copiarArreglo(arregloOriginal);
        int[] arregloRecursivo = Arrays.copiarArreglo(arregloOriginal);

        statsIterativo = new OperationStats();
        statsRecursivo = new OperationStats();

        panelIterativo.limpiarEstadisticas();
        panelIterativo.limpiarLog();
        panelRecursivo.limpiarEstadisticas();
        panelRecursivo.limpiarLog();

        panelIterativo.getChartManager().actualizar(arregloIterativo);
        panelRecursivo.getChartManager().actualizar(arregloRecursivo);

        OperationLogger loggerIterativo = new OperationLogger(panelIterativo);
        OperationLogger loggerRecursivo = new OperationLogger(panelRecursivo);

        actualizarResumen();

        hiloIterativo = new SortingThread(arregloIterativo, "BubbleSort", "Iterativo", velocidad, orden,
                statsIterativo, loggerIterativo, panelIterativo.getChartManager(),
                () -> {
                    panelIterativo.actualizarEstadisticas(statsIterativo);
                    actualizarResumen();
                });

        hiloRecursivo = new SortingThread(arregloRecursivo, "BubbleSort", "Recursivo", velocidad, orden,
                statsRecursivo, loggerRecursivo, panelRecursivo.getChartManager(),
                () -> {
                    panelRecursivo.actualizarEstadisticas(statsRecursivo);
                    actualizarResumen();
                });

        hiloIterativo.start();
        hiloRecursivo.start();
    }

    public void detenerComparacion() {
        if (hiloIterativo != null && hiloIterativo.corriendo()) {
            hiloIterativo.detener();
        }

        if (hiloRecursivo != null && hiloRecursivo.corriendo()) {
            hiloRecursivo.detener();
        }
    }

    public void reiniciarComparacion(int[] arregloOriginal) {
        detenerComparacion();

        int[] arregloIterativo = Arrays.copiarArreglo(arregloOriginal);
        int[] arregloRecursivo = Arrays.copiarArreglo(arregloOriginal);

        statsIterativo = new OperationStats();
        statsRecursivo = new OperationStats();

        panelIterativo.limpiarEstadisticas();
        panelIterativo.limpiarLog();
        panelRecursivo.limpiarEstadisticas();
        panelRecursivo.limpiarLog();

        panelIterativo.getChartManager().actualizar(arregloIterativo);
        panelRecursivo.getChartManager().actualizar(arregloRecursivo);

        actualizarResumen();
    }

    private void actualizarResumen() {
        if (statsIterativo == null) {
            lblComparacionesIterativo.setText("0");
            lblIntercambiosIterativo.setText("0");
            lblLlamadasIterativo.setText("0");
            lblTiempoIterativo.setText("0 ms");
        } else {
            lblComparacionesIterativo.setText(String.valueOf(statsIterativo.getComparaciones()));
            lblIntercambiosIterativo.setText(String.valueOf(statsIterativo.getIntercambios()));
            lblLlamadasIterativo.setText(String.valueOf(statsIterativo.getLlamadasRecursivas()));
            lblTiempoIterativo.setText(statsIterativo.getTiempoTotal() + " ms");
        }

        if (statsRecursivo == null) {
            lblComparacionesRecursivo.setText("0");
            lblIntercambiosRecursivo.setText("0");
            lblLlamadasRecursivo.setText("0");
            lblTiempoRecursivo.setText("0 ms");
        } else {
            lblComparacionesRecursivo.setText(String.valueOf(statsRecursivo.getComparaciones()));
            lblIntercambiosRecursivo.setText(String.valueOf(statsRecursivo.getIntercambios()));
            lblLlamadasRecursivo.setText(String.valueOf(statsRecursivo.getLlamadasRecursivas()));
            lblTiempoRecursivo.setText(statsRecursivo.getTiempoTotal() + " ms");
        }
    }
}
