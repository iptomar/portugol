/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.CodeEditor;

import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import org.openide.awt.UndoRedo;

/**
 * Classe que permite adicionar estilos as tab de edição de código.
 * @author Saso
 */
public class AlgolXXIEditorKit extends StyledEditorKit {

    AlgolXXISyntax syntax;

    /**
     * Constructor por parâmetros para a classe AlgolXXIEditorKit
     * @param path String, path para o ficheiro XML com as propriedades 
     * @param manager UndoRedo.Manager, para ser possível passar o objecto UndoRedo.Manager 
     * que será usado na classe AlgolXXISyntax
     */
    public AlgolXXIEditorKit(UndoRedo.Manager manager) {
        syntax = new AlgolXXISyntax(manager);
    }

    @Override
    public Document createDefaultDocument() {
        return syntax;
    }

    public void newSettings(PortugolStyle style) {
        syntax.newSettings(style);
    }
}
