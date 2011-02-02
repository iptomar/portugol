/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Fluxogramas;

import AlgolXXI.Editor.ProgramaTopComponent;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jgraph.graph.DefaultGraphModel;

/**
 *
 * @author Apocas
 */
public class FluxogramaPanel extends JPanel {

    private Fluxogram grafo = null;
    private JScrollPane scroll_pane;

    public FluxogramaPanel(ProgramaTopComponent tpc) {
        setLayout(new BorderLayout());

        grafo = new Fluxogram(new DefaultGraphModel(), tpc);

        grafo.setJumpToDefaultPort(true);

        scroll_pane = new JScrollPane();
        add(scroll_pane);
        scroll_pane.setViewportView(grafo);
    }

    public FluxogramaPanel(ProgramaTopComponent tpc, DefaultGraphModel model) {
        setLayout(new BorderLayout());

        grafo = new Fluxogram(model, tpc);

        grafo.setJumpToDefaultPort(true);

        scroll_pane = new JScrollPane();
        add(scroll_pane);
        scroll_pane.setViewportView(grafo);
    }

    public Fluxogram getFluxograma() {
        return grafo;
    }

    public void setFontFluxograma(Font fontf) {
        grafo.setFont(fontf);
    }

    public Font getFontFluxograma() {
        return grafo.getFont();
    }

    public double centrar() {
        double altura_fluxo = 0.0;
        double largura_fluxo = 0.0;
        double altura_visivel = getScrollPane().getBounds().height;
        double largura_visivel = getScrollPane().getBounds().width;
        double factor_altura = 1.0;
        double factor_largura = 1.0;

        if (getScrollPane().getHorizontalScrollBar().isShowing()) {
            largura_fluxo = getScrollPane().getHorizontalScrollBar().getMaximum();
            factor_largura = (largura_visivel * grafo.getScale()) / largura_fluxo;
        }
        if (getScrollPane().getVerticalScrollBar().isShowing()) {
            altura_fluxo = getScrollPane().getVerticalScrollBar().getMaximum();
            factor_altura = (altura_visivel * grafo.getScale()) / altura_fluxo;
        }

        if (factor_altura < factor_largura && factor_altura != 1.0) {
            grafo.setScale(factor_altura);
            return factor_altura;
        } else if (factor_largura < factor_altura && factor_largura != 1.0) {
            grafo.setScale(factor_largura);
            return factor_largura;
        }

        return 1.0;
    }

    public JScrollPane getScrollPane() {
        return scroll_pane;
    }
}

