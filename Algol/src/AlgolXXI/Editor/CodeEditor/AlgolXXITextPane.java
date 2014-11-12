/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.CodeEditor;

import java.awt.Component;
import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;

/**
 * Classe que impossibilita o warp text e cria um scrollbar
 * @author Saso
 */
public class AlgolXXITextPane extends JTextPane {

    @Override
    public boolean getScrollableTracksViewportWidth() {
        Component parent = getParent();
        ComponentUI uiComponent = getUI();
        
        return parent != null ? (uiComponent.getPreferredSize(this).width <= parent.getSize().width) : true;
    }
}
