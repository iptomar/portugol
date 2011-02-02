/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import javax.swing.JOptionPane;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

/**
 * Classe que permite definir as acções do botão Procurar
 * @author Apocas
 */
public final class Procurar extends CallableSystemAction {

    /**
     * Método que define a acção do ícone.
     */
    public void performAction() {
        ProgramaTopComponent ptc = AlgolXXI.Editor.Utils.EditorUtils.getTopComponent();
        if (!AlgolXXI.Editor.Utils.EditorUtils.isDialogOpened() && ptc != null) {
            String response = JOptionPane.showInputDialog(null, "Texto a procurar:", "Procurar", JOptionPane.QUESTION_MESSAGE);
            if (response != null) {
                ptc.find(response.toLowerCase());
            }
        }
    }

    /**
     * Método que permite retornar o nome do ícone consoante a língua seleccionada.
     * @return String NbBundle message
     */
    public String getName() {
        return NbBundle.getMessage(Procurar.class, "CTL_Procurar");
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
        return "AlgolXXI/Editor/Images/find.png";
    }
}
