package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class ChartManager implements ChartUpdater {

    /* ATRIBUTOS */
    private DefaultCategoryDataset dataset;
    private JFreeChart grafico;
    private ChartPanel graficoPanel;
    private ChartColorRenderer renderer;

    /* CONSTRUCTOR */
    public ChartManager() {
        dataset = new DefaultCategoryDataset();
        
        grafico = ChartFactory.createBarChart(
            "Visualización",
            "Posición",
            "Valor",
            dataset
        );
        grafico.removeLegend();

        renderer = new ChartColorRenderer();
        renderer.setShadowVisible(false);

        CategoryPlot plot = grafico.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setOutlineVisible(false);

        graficoPanel = new ChartPanel(grafico);
    }

    /* METODOS */
    public void mostrarGrafico(int[] arreglo) {
        dataset.clear();

        for (int i = 0; i < arreglo.length; i++) {
            dataset.addValue(arreglo[i], "Valor", String.valueOf(i));
        }
    }

    @Override
    public void actualizar(int[] arreglo) {
        SwingUtilities.invokeLater(() -> {
            renderer.limpiarEstados();
            mostrarGrafico(arreglo);
        });
    }

    @Override
    public void actualizarConEstados(int[] arreglo, int indice1, int indice2, String estado) {
        SwingUtilities.invokeLater(() -> {
            renderer.setEstado(indice1, indice2, estado);
            mostrarGrafico(arreglo);
        });
    }

    @Override
    public void actualizarConPivote(int[] arreglo, int pivote) {
        SwingUtilities.invokeLater(() -> {
            renderer.setPivote(pivote);
            mostrarGrafico(arreglo);
        });
    }

    /* GETTERS */
    public ChartPanel getChartPanel() {
        return graficoPanel;
    }
}
