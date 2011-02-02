/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
/**
 * Classe que permite definir as acções do botão Abrir
 * @author Apocas
 */
public final class Abrir extends CallableSystemAction {

   /**
    *  Método que define a acção do ícone.
    */
    public void performAction() {
        try {
            ProgramaTopComponent tc = new ProgramaTopComponent();
            if (tc.abrir() == -1) {
                tc.close();
            } else {
                tc.open();
                tc.requestActive();
            }
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    /**
     * Método que permite retornar o nome do ícone consoante a língua seleccionada.
     * @return String NbBundle message
     */
    public String getName() {
        return NbBundle.getMessage(Abrir.class, "CTL_Abrir");
    }

    /**
     * Método que retorna o path do ícone.
     * @return String path
     */
    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/Computer_File_064.gif";
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
