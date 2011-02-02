/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class Colar extends CallableSystemAction {

    /**
     * Método que define a acção do ícone.
     */
    public void performAction() {
        ProgramaTopComponent ptc = AlgolXXI.Editor.Utils.EditorUtils.getTopComponent();
        if (ptc != null) {
            ptc.call_colar();
        }
    }

    public String getName() {
        return NbBundle.getMessage(Colar.class, "CTL_Colar");
    }

    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/paste.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
