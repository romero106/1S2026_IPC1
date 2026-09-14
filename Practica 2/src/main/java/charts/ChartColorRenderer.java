package charts;

import org.jfree.chart.renderer.category.BarRenderer;

import java.awt.*;

public class ChartColorRenderer extends BarRenderer {

    /* ATRIBUTOS */
    private int indice1 = -1;
    private int indice2 = -1;
    private int pivote = -1;
    private String estado = "normal";

    /* METODOS */
    @Override
    public Paint getItemPaint(int row, int column) {
        if (column == pivote) {
            return new Color(200, 0, 200); // MORADO
        }

        if (column == indice1 || column == indice2) {
            if ("intercambiando".equals(estado)) {
                return new Color(245, 35, 35); // ROJO
            }
            return new Color(255, 190, 60); // AMARILLO
        }

        return new Color(100, 160, 240); // AZUL
    }

    public void limpiarEstados() {
        this.indice1 = -1;
        this.indice2 = -1;
        this.pivote = -1;
        this.estado = "normal";
    }

    /* SETTERS */
    public void setEstado(int indice1, int indice2, String estado) {
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.estado = estado;
    }

    public void setPivote(int pivote) {
        this.pivote = pivote;
        this.indice1 = -1;
        this.indice2 = -1;
        this.estado = "pivote";
    }
}
