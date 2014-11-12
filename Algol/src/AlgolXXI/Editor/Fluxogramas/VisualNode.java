/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Fluxogramas;

import AlgolXXI.Core.NodeFluxo;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author Apocas
 */
public class VisualNode extends DefaultGraphCell {

    private NodeFluxo nodeinstruction = null;
    private VisualNode next_node = null;

    VisualNode(String name) {
        super(name);
    }

    void setNextNode(VisualNode node_aux) {
        next_node = node_aux;
    }

    VisualNode getNext() {
        return next_node;

    }

    void setNodeinstruction(NodeFluxo nodef) {
        nodeinstruction = nodef;
    }

    NodeFluxo getNodeinstruction() {
        return nodeinstruction;
    }
}
