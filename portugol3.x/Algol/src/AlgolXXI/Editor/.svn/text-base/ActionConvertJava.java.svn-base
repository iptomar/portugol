/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class ActionConvertJava extends CallableSystemAction {

    public void performAction() {
        ProgramaTopComponent ptc = AlgolXXI.Editor.Utils.EditorUtils.getTopComponent();
        if (ptc != null) {
            ptc.convert2Java();
        }
    }

    public String getName() {
        return NbBundle.getMessage(ActionConvertJava.class, "CTL_ActionConvertJava");
    }

    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/java.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
