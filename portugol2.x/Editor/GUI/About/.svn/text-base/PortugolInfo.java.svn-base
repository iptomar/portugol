package Editor.GUI.About;

import Editor.GUI.CodeDocument.AlgoCodeStyle;
import Editor.GUI.EditorPTG;
import Editor.help.WWWHelpText;
import Portugol.Language.Evaluate.Calculator;
import Portugol.Language.Execute.ConsoleIO;
import Portugol.Language.Make.Fluxogram;
import VisualFluxogram.GUI.FluxogramaPTG;


/**
 *
 * @author _arm
 */
public class PortugolInfo {
    
    /**
     * Creates a new instance of PortugolInfo
     */
    public PortugolInfo() {
    }
    public static String getInformation(){
        StringBuffer str = new StringBuffer();
        
            str.append("\n\tTITULO              : " + EditorPTG.TITLE);
            str.append("\n\tData de compilação  : " + EditorPTG.DATE );            
            str.append("\n\n\tEditor            : " + EditorPTG.VERSION);            
            str.append("\n\tParser              : " + Fluxogram.VERSION);
            str.append("\n\tCalculo             : " + Calculator.VERSION);
            str.append("\n\tFluxograma          : " + FluxogramaPTG.VERSION);
            str.append("\n\tSintax              : " + AlgoCodeStyle.VERSION);
            str.append("\n\tConsola             : " + ConsoleIO.VERSION);
            str.append("\n\tAjuda               : " + WWWHelpText.VERSION);
            str.append("\n");
            str.append("\n\tUtilizador          : " + System.getProperty("user.name"));
            str.append("\n\tArquitectura Comp.  : " + System.getProperty("os.arch"));
            str.append("\n\tSistema Operativo   : " + System.getProperty("os.name") +  " versão " + System.getProperty("os.version"));
            str.append("\n\tLocal de Execução   : " + System.getProperty("user.dir"));
            str.append("\n\tLocal do Utilizador : " + System.getProperty("user.home"));            
            
            return str.toString();
    }
    
}
