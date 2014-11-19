/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Fluxogramas;

import AlgolXXI.Core.Execute.ExecuteProgram;
import AlgolXXI.Core.Function;
import AlgolXXI.Core.NodeFluxo;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Editor.ProgramaTopComponent;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.BorderFactory;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Apocas
 */
public class Fluxogram extends JGraph {

    private int nodes = 0;
    private int blockid = -1;
    private VisualNode oldnode = null;
    private int x_pos = 0;
    private ArrayList<Integer> programas_desenhados;
    private ExecuteProgram executor;
    private ProgramaTopComponent topcomponent;
    private VisualNode inexecution;
    private DefaultGraphModel model;
    private int blockid_main;

    public Fluxogram(DefaultGraphModel model, ProgramaTopComponent tpc) {
        super(model);
        this.model = model;
        programas_desenhados = new ArrayList<Integer>();
        topcomponent = tpc;
        executor = topcomponent.getExecutor();
        setEditable(false);
        getGraphLayoutCache().setFactory(new ViewFactory());

        getGraphLayoutCache().setAutoSizeOnValueChange(true);
        getGraphLayoutCache().setSelectsLocalInsertedCells(false);
        getGraphLayoutCache().setSelectsAllInsertedCells(false);
    }

    public void updateFluxogram() {
        for (Function programa_bloco : executor.getBlocks()) {
            if (!isDrawn(programa_bloco)) {
                x_pos = x_pos + 250;
                programas_desenhados.add(programa_bloco.getID());
                adicionaAlgolNode(programa_bloco.getFlux().getBegin());
            }
        }
    }

    public boolean isDrawn(Function pb) {
        for (Integer id : programas_desenhados) {
            if (pb.getID() == id) {
                return true;
            }
        }
        return false;
    }

    private int numNodesBlock(int id) {
        int contador = 0;
        for (Object node_obj : getDescendants(getRoots())) {
            if (node_obj instanceof VisualNode) {
                VisualNode node = (VisualNode) node_obj;
                if (node.getNodeinstruction().getBlockID() == id) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public VisualNode highlightNode(NodeFluxo nodef) {

        VisualNode node_aux = null;
        for (int i = 0; i < getDescendants(getRoots()).length; i++) {
            if (getDescendants(getRoots())[i] instanceof VisualNode) {
                node_aux = (VisualNode) getDescendants(getRoots())[i];
                if (node_aux.getNodeinstruction().equals(nodef)) {
                    this.setHighlight(node_aux, Color.GREEN);
                    inexecution = node_aux;

                    VisualEdge edge_aux = findEdge(null, node_aux);
                    if (edge_aux != null) {
                        this.setHighlight(edge_aux, Color.GREEN);
                    }
                    break;
                }
            }
        }

        if (nodef.getType() == Keyword.FIM && node_aux != null && node_aux.getNodeinstruction().getBlockID() != blockid_main) {
            removeBlock(node_aux.getNodeinstruction().getBlockID());
            return node_aux.getNext();
        }

        return node_aux;
    }

    private void adicionaAlgolNode(NodeFluxo nodef) {
        if (nodef == null) {
            return;
        }

        nodes++;
        int y = 0;

        if (nodef.getType() == Keyword.INICIO) {
            if (blockid == -1) {
                blockid_main = nodef.getBlockID();
            }
            blockid = nodef.getBlockID();
            y = 10;
        } else {
            y = (numNodesBlock(nodef.getBlockID())) * 90;
        }

        VisualNode node_aux = toAlgolNode(nodef, x_pos, y);
        addNode(node_aux);

        if (nodef.getType() == Keyword.INICIO && inexecution != null) {
            addEdge(inexecution, node_aux);
        } else {
            if (oldnode != null && node_aux != null) {
                addEdge(oldnode, node_aux);
                oldnode.setNextNode(node_aux);
            }
        }

        if (nodef.getType() == Keyword.FIM && inexecution != null) {
            addEdge(node_aux, inexecution.getNext());
            node_aux.setNextNode(inexecution.getNext());
        }

        oldnode = node_aux;
        if (nodef.getNext() == null || nodef.getNext().getBlockID() != blockid) {
            oldnode = null;
            return;
        } else {
            adicionaAlgolNode(nodef.getNext());
        }
    }

    private void addNode(VisualNode nodef) {
        this.getGraphLayoutCache().insert(nodef);
    }

    private void addEdge(VisualNode origem, VisualNode destino) {
        VisualEdge edge = new VisualEdge();

        ViewFactory.setViewClass(edge.getAttributes(), "AlgolXXI.Editor.Fluxogramas.DefeitoEdgeView");

        edge.addPort();
        edge.addPort();

        edge.setDestino(destino);
        edge.setOrigem(origem);

        if (origem.getNodeinstruction().getBlockID() < destino.getNodeinstruction().getBlockID()) {
            edge.setSource(origem.getChildAt(1));
            edge.setTarget(destino.getChildAt(3));
        } else if (origem.getNodeinstruction().getBlockID() > destino.getNodeinstruction().getBlockID()) {
            edge.setSource(origem.getChildAt(3));
            edge.setTarget(destino.getChildAt(1));
        } else if (origem.getNodeinstruction().getBlockID() == destino.getNodeinstruction().getBlockID()) {
            edge.setSource(origem.getChildAt(2));
            edge.setTarget(destino.getChildAt(0));
        }

        GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
        GraphConstants.setEndFill(edge.getAttributes(), true);

        this.getGraphLayoutCache().insert(edge);
    }

    private void removeBlock(int blockID) {
        ArrayList<VisualNode> nodes_remover = new ArrayList<VisualNode>();
        ArrayList<DefaultGraphCell> remover = new ArrayList<DefaultGraphCell>();

        for (Object node_obj : getDescendants(getRoots())) {
            if (node_obj instanceof VisualNode) {
                VisualNode node = (VisualNode) node_obj;
                if (node.getNodeinstruction().getBlockID() == blockID) {
                    nodes_remover.add(node);
                    remover.add(node);
                }

            }
            if (node_obj instanceof VisualEdge) {
                VisualEdge edge = (VisualEdge) node_obj;
                if (edge.getDestino().getNodeinstruction().getBlockID() == blockID || edge.getOrigem().getNodeinstruction().getBlockID() == blockID) {
                    remover.add(edge);
                }

            }
        }

        getModel().remove(remover.toArray());

        x_pos = x_pos - 250;
    }

    private void setHighlight(DefaultGraphCell node_aux, Color cor) {
        Map nested = new Hashtable();
        Map attr1 = new Hashtable();
        GraphConstants.setGradientColor(attr1, cor);
        nested.put(node_aux, attr1);
        getGraphLayoutCache().edit(nested, null, null, null);
    }

    private VisualNode toAlgolNode(NodeFluxo nodef, int x, int y) {
        VisualNode node = null;
        String label = "";

        if (nodef.getText().toLowerCase().startsWith("escrever") || nodef.getText().toLowerCase().startsWith("ler")) {
            if (nodef.getText().toLowerCase().startsWith("escrever")) {
                label = nodef.getText().toLowerCase().replaceFirst("escrever", "").trim();
            }

            if (nodef.getText().toLowerCase().startsWith("ler")) {
                label = nodef.getText().toLowerCase().replaceFirst("ler", "").trim();
            }

        } else {
            label = nodef.getText().toLowerCase();
        }

        switch (nodef.getType()) {
            case Keyword.INICIO:
                node = createVertex(label, x, y, 150, 40, Color.RED, true, "AlgolXXI.Editor.Fluxogramas.InicioFimNodeView");
                break;

            case Keyword.FIM:
                node = createVertex(label, x, y, 150, 40, Color.RED, true, "AlgolXXI.Editor.Fluxogramas.InicioFimNodeView");
                break;

            case Keyword.LER:
                node = createVertex(label, x, y, 150, 40, Color.BLUE, true, "AlgolXXI.Editor.Fluxogramas.LerNodeView");
                break;

            case Keyword.ESCREVER:
                node = createVertex(label, x, y, 150, 40, Color.YELLOW, true, "AlgolXXI.Editor.Fluxogramas.EscreverNodeView");
                break;

            case Keyword.DEFINIR:
                node = createVertex(label, x, y, 150, 40, Color.PINK, true, "AlgolXXI.Editor.Fluxogramas.DefeitoNodeView");
                break;

            default:
                node = createVertex(label, x, y, 150, 40, Color.GRAY, true, "AlgolXXI.Editor.Fluxogramas.DefeitoNodeView");
                break;

        }

        node.setNodeinstruction(nodef);
        return node;
    }

    public static VisualNode createVertex(String name, double x, double y, double w, double h, Color bg, boolean raised, String viewClass) {

        VisualNode cell = new VisualNode(name);

        GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));

        if (viewClass != null) {
            ViewFactory.setViewClass(cell.getAttributes(), viewClass);
        }

        if (bg != null) {
            GraphConstants.setGradientColor(cell.getAttributes(), bg);
            GraphConstants.setOpaque(cell.getAttributes(), true);
        }

        if (raised) {
            GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createRaisedBevelBorder());
        } else {
            GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        }

        cell.addPort(new Point(GraphConstants.PERMILLE / 2, 0));
        cell.addPort(new Point(GraphConstants.PERMILLE, GraphConstants.PERMILLE / 2));
        cell.addPort(new Point(GraphConstants.PERMILLE / 2, GraphConstants.PERMILLE));
        cell.addPort(new Point(0, GraphConstants.PERMILLE / 2));

        return cell;
    }

    private VisualEdge findEdge(VisualNode origem, VisualNode destino) {
        for (Object node_obj : getDescendants(getRoots())) {
            if (node_obj instanceof VisualEdge) {
                VisualEdge edge = (VisualEdge) node_obj;
                if (origem != null && destino != null) {
                    if (edge.getDestino().equals(destino) && edge.getOrigem().equals(origem)) {
                        return edge;
                    }
                } else if (origem != null && destino == null) {
                    if (edge.getOrigem().equals(origem)) {
                        return edge;
                    }
                } else if (origem == null && destino != null) {
                    if (edge.getDestino().equals(destino)) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }
}
