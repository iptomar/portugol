/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Memory;

import AlgolXXI.Core.Memory.SymbolData;
import AlgolXXI.Core.Memory.SymbolDataComplex;
import AlgolXXI.Core.Memory.SymbolStructure;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Apocas
 */
public class MemoryDisplay extends JPanel {

    private ArrayList<SymbolData> symbols;
    private int blockid;
    private DynamicTree treePanel;
    private boolean flag = false;

    public MemoryDisplay(int id) {
        symbols = new ArrayList<SymbolData>();
        treePanel = new DynamicTree();
        //populateTree(treePanel);

        this.blockid = id;
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(100, 100));

        treePanel.setPreferredSize(new Dimension(100, 100));
        add(treePanel, BorderLayout.CENTER);
    }

    public void populateTree(DynamicTree treePanel) {
        String p1Name = new String("Parent 1");
        String p2Name = new String("Parent 2");
        String c1Name = new String("Child 1");
        String c2Name = new String("Child 2");

        DefaultMutableTreeNode p1, p2;

        p1 = treePanel.addObject(null, p1Name);
        p2 = treePanel.addObject(null, p2Name);

        treePanel.addObject(p1, c1Name);
        treePanel.addObject(p1, c2Name);

        treePanel.addObject(p2, c1Name);
        treePanel.addObject(p2, c2Name);
    }

    public void clear() {
        treePanel.clear();
    }

    public void update(ArrayList<SymbolData> symbolsf) {
        for (int i = 0; i < symbolsf.size(); i++) {
            if (isMember(symbolsf.get(i))) {
                TreePath node = findByName(treePanel.tree, new String[]{"Memoria", symbolsf.get(i).getName()});
                treePanel.tree.setSelectionPath(node);
                treePanel.removeCurrentNode();
            } else {
                treePanel.tree.setSelectionPath(new TreePath((TreeNode) treePanel.tree.getModel().getRoot()));
                symbols.add(symbolsf.get(i));
            }

            DefaultMutableTreeNode parent = treePanel.addObject(symbolsf.get(i).getName());

            if (!(symbolsf.get(i) instanceof SymbolDataComplex)) {
                treePanel.addObject(parent, symbolsf.get(i).getValue(), true);
            } else {
                adicionaComplexo(parent, symbolsf.get(i));
            }
        }
    }

    SymbolData existsStruct(ArrayList<SymbolData> data) {
        for (SymbolData sd : data) {
            if (sd instanceof SymbolStructure) {
                return sd;
            }
        }
        return null;
    }

    private void adicionaComplexo(DefaultMutableTreeNode parent, SymbolData symbolf) {
        ArrayList<SymbolData> dataf = ((SymbolDataComplex) symbolf).getData();
        for (SymbolData sd1 : dataf) {
            DefaultMutableTreeNode parent_aux = treePanel.addObject(parent, sd1.getName(), true);

            if (!(sd1 instanceof SymbolDataComplex)) {
                treePanel.addObject(parent_aux, sd1.getValue(), true);
            } else {
                adicionaComplexo(parent_aux, sd1);
            }

        }

    }

    private boolean isMember(SymbolData s) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getName().equals(s.getName())) {
                return true;
            }
        }
        return false;
    }

    public int getBlockid() {
        return blockid;
    }

    public TreePath findByName(JTree tree, String[] names) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        return find(tree, new TreePath(root), names, 0, true);
    }

    private TreePath find(JTree tree, TreePath parent, Object[] nodes, int depth, boolean byName) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        Object o = node;

        if (byName) {
            o = o.toString();
        }

        if (o.equals(nodes[depth])) {
            if (depth == nodes.length - 1) {
                return parent;
            }

            if (node.getChildCount() >= 0) {
                for (Enumeration e = node.children(); e.hasMoreElements();) {
                    TreeNode n = (TreeNode) e.nextElement();
                    TreePath path = parent.pathByAddingChild(n);
                    TreePath result = find(tree, path, nodes, depth + 1, byName);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public DefaultTreeModel getModel() {
        return treePanel.getTreeModel();
    }
}
