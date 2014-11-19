/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class Substituir extends CallableSystemAction {

    public void performAction() {
        ProgramaTopComponent ptc = AlgolXXI.Editor.Utils.EditorUtils.getTopComponent();
        if (!AlgolXXI.Editor.Utils.EditorUtils.isDialogOpened() && ptc != null) {
            new ReplaceForm().setVisible(true);
            ptc.setDialogOpened(true);
        }
    }

    public String getName() {
        return NbBundle.getMessage(Substituir.class, "CTL_Substituir");
    }

    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/replace.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
