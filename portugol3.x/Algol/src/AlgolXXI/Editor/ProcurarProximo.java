/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

/**
 * Classe que permite definir as acções do botão ProcurarProximo
 * @author Apocas
 */
public final class ProcurarProximo extends CallableSystemAction {

    /**
     * Método que define a acção do ícone.
     */
    public void performAction() {
        if (!AlgolXXI.Editor.Utils.EditorUtils.isDialogOpened()) {
            ProgramaTopComponent ptc = AlgolXXI.Editor.Utils.EditorUtils.getTopComponent();
            if (ptc != null) {
                ptc.find();
            }
        }
    }

    /**
     * Método que permite retornar o nome do ícone consoante a língua seleccionada.
     * @return String NbBundle message
     */
    public String getName() {
        return NbBundle.getMessage(ProcurarProximo.class, "CTL_ProcurarProximo");
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    /**
     * Método que providência a ajuda para objecto.
     * @return Default help page
     */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/find_next.png";
    }
}
