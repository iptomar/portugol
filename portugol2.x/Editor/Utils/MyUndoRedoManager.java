/*
 * URManager.java
 *
 * Created on 18 de Abril de 2005, 22:27
 */

package Editor.Utils;

import javax.swing.event.UndoableEditEvent;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.*;

/**
 *
 * @author Administrator
 */
public class MyUndoRedoManager extends UndoManager implements javax.swing.event.UndoableEditListener {
    
    public void undoableEditHappened(UndoableEditEvent e) {
              super.addEdit(e.getEdit());
    }
    
    public boolean undoAction() {
   
        if( canUndo() ){
            undo();           
        }
           return( true );
        
    }
    
    public boolean redoAction() {
           if( canRedo() ){
            redo();
         }
           return( true );
        
    }
    
}
