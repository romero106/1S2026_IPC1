package gui;

import javax.swing.*;
import java.awt.*;

import charts.*;
import stats.*;
import threads.*;
import utils.*;

public class MainFrame extends JFrame {

    /* ATRIBUTOS */
    private ControlPanel controlPanel;
    private VisualizationPanel visualizationPanel;
    private ComparisonPanel comparisonPanel;
    private ChartManager chartManager;
    private SortingThread hilo;
    private OperationStats statsActual;
    private JTabbedPane pestanias;

    private JButton btnAleatorio;
    private JButton btnIniciar;
    private JButton btnDetener;
    private JButton btnReiniciar;

    /* CONSTRUCTOR */
    public MainFrame() {
        setTitle("Visualizador de Ordenamiento");
        setLayout(new BorderLayout(10, 10));
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controlPanel = new ControlPanel();
        visualizationPanel = new VisualizationPanel();
        comparisonPanel = new ComparisonPanel();
        pestanias = new JTabbedPane();
        pestanias.addTab("Modo Normal", visualizationPanel);
        pestanias.addTab("Modo Comparacion", comparisonPanel);

        add(controlPanel, BorderLayout.WEST);
        add(pestanias, BorderLayout.CENTER);
        chartManager = visualizationPanel.getChartManager();

        controlPanel.getBoxAlgoritmos().addActionListener(e -> actualizarBoxVariante());     
        actualizarBoxVariante();

        /* Accion de btnAleatorio */
        btnAleatorio = controlPanel.getBtnAleatorio();
        btnAleatorio.addActionListener(e -> {
            int[] arreglo = Arrays.arregloAleatorio(10, 1, 95);
            controlPanel.getInputArreglo().setText(Arrays.arregloTexto(arreglo));
            chartManager.actualizar(arreglo);
        });

        /* Accion de btnIniciar */
        btnIniciar = controlPanel.getBtnIniciar();
        btnIniciar.addActionListener(e -> {
            try {
                String arregloTexto = controlPanel.getInputArreglo().getText();
                int[] arreglo = Arrays.textoArreglo(arregloTexto);

                String algoritmo = (String) controlPanel.getBoxAlgoritmos().getSelectedItem();
                String variante = (String) controlPanel.getBoxVariante().getSelectedItem();
                int velocidad = controlPanel.getVelocidad();
                String orden = (String) controlPanel.getBoxOrden().getSelectedItem();

                if (pestanias.getSelectedComponent() == comparisonPanel) {
                    comparisonPanel.iniciarComparacion(arreglo, velocidad, orden);
                } else {
                    statsActual = new OperationStats();
                    OperationLogger loggerActual = new OperationLogger(visualizationPanel);
                    visualizationPanel.limpiarEstadisticas();
                    visualizationPanel.limpiarLog();

                    chartManager.actualizar(arreglo);
                    
                    hilo = new SortingThread(arreglo, algoritmo, variante, velocidad, orden, statsActual,
                        loggerActual, chartManager,
                        () -> visualizationPanel.actualizarEstadisticas(statsActual));
                    hilo.start();
                }

            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /* Accion de btnDetener */
        btnDetener = controlPanel.getBtnDetener();
        btnDetener.addActionListener(e -> {
            if (pestanias.getSelectedComponent() == comparisonPanel) {
                comparisonPanel.detenerComparacion();
            } else {
                if (hilo != null && hilo.corriendo()) {
                    hilo.detener();
                }
            }
        });

        /* Accion de btnReiniciar */
        btnReiniciar = controlPanel.getBtnReiniciar();
        btnReiniciar.addActionListener(e -> {
            try {
                String arreglo = controlPanel.getInputArreglo().getText();
                int[] arregloActual = Arrays.textoArreglo(arreglo);

                if (pestanias.getSelectedComponent() == comparisonPanel) {
                    comparisonPanel.reiniciarComparacion(arregloActual);
                } else {
                    chartManager.actualizar(arregloActual);
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /* METODOS */
    private void actualizarBoxVariante() {
        String algoritmo = (String) controlPanel.getBoxAlgoritmos().getSelectedItem();
        JComboBox<String> boxVariante = controlPanel.getBoxVariante();

        boxVariante.removeAllItems();

        if (algoritmo.equals("BubbleSort")) {
            boxVariante.addItem("Iterativo");
            boxVariante.addItem("Recursivo");
            boxVariante.setEnabled(true);
        } else if (algoritmo.equals("QuickSort")) {
            boxVariante.addItem("Recursivo");
            boxVariante.setEnabled(false);
        } else if (algoritmo.equals("ShellSort")) {
            boxVariante.addItem("Iterativo");
            boxVariante.setEnabled(false);
        }
    }
}
