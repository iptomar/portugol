/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows vala component.
 */
public class ProgramaAction extends AbstractAction {

    /**
     * Constructor por defeito da classe ProgramaAction
     */
    public ProgramaAction() {
        super(NbBundle.getMessage(ProgramaAction.class, "CTL_ProgramaAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(valaTopComponent.ICON_PATH, true)));
    }

    /**
     * Método que associado a um ActionEvent torna o TopComponent activo.
     * @param evt ActionEvent
     */
    public void actionPerformed(ActionEvent evt) {
        try {
            TopComponent win = ProgramaTopComponent.findInstance();
            win.open();
            win.requestActive();
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
