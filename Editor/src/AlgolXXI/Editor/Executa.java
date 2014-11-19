/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

/**
 * Classe que permite definir as acções do botão Executa
 * @author Apocas
 */
public final class Executa extends CallableSystemAction {

    /**
     * Método que define a acção do ícone.
     */
    public void performAction() {
        ProgramaTopComponent ptc = AlgolXXI.Editor.Utils.EditorUtils.getTopComponent();
        if (ptc != null) {
            ptc.executa();
        }
    }

    /**
     * Método que permite retornar o nome do ícone consoante a língua seleccionada.
     * @return String NbBundle message
     */
    public String getName() {
        return NbBundle.getMessage(Executa.class, "CTL_Executa");
    }

    /**
     * Método que retorna o path do ícone.
     * @return String path
     */
    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/pencil_go.png";
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
}
