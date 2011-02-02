/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Fluxogramas;

import org.jgraph.graph.DefaultEdge;

/**
 *
 * @author Apocas
 */
public class VisualEdge extends DefaultEdge {

    private VisualNode origem = null;
    private VisualNode destino = null;

    VisualEdge() {
        super();
    }

    void setDestino(VisualNode node) {
        destino = node;
    }

    void setOrigem(VisualNode node) {
        origem = node;
    }

    VisualNode getDestino() {
        return destino;
    }

    VisualNode getOrigem() {
        return origem;
    }
}
