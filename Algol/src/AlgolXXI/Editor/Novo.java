/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Classe que permite definir as acções do botão Novo
 * @author Apocas
 */
public final class Novo extends CallableSystemAction {

    int contador = 2;
    private ResourceBundle bundle;
    private Locale currentLocale;

    /**
     * Método que define a acção do ícone.
     */
    public void performAction() {
        currentLocale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("AlgolXXI/Editor/Bundle", currentLocale);

        try {
            ProgramaTopComponent tc = new ProgramaTopComponent();
            tc.setDisplayName(bundle.getString("Programa") + contador + ".alg");
            tc.open();
            tc.requestActive();
            contador++;
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Método que permite retornar o nome do ícone consoante a língua seleccionada.
     * @return String NbBundle message
     */
    public String getName() {
        return NbBundle.getMessage(Novo.class, "CTL_Novo");
    }

    /**
     * Método que retorna o path do ícone.
     * @return String path
     */
    @Override
    protected String iconResource() {
        return "AlgolXXI/Editor/Images/new.png";
    }

    /**
     * 
     */
    @Override
    protected void initialize() {
        super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    //putValue("noIconInMenu", Boolean.TRUE);
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
