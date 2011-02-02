/*
 * URManager.java
 *
 * Created on 18 de Abril de 2005, 22:27
 */

package Editor.Utils;

import javax.swing.undo.*;

/**
 *
 * @author Administrator
 */
public class undoRedoManager {
    
    protected UndoManager undoManager;
    protected MyUndoableEditListener myUndoRedo;
            
    /** Creates a new instance of URManager */
    public undoRedoManager() {
        undoManager = new UndoManager();
        myUndoRedo = new MyUndoableEditListener();
    }
    
    
    public boolean undoAction()
    {
    if( undoManager.canUndo() )
       {
            try
            {
                undoManager.undo();
                return( true );
            }catch(CannotUndoException e){
                return( false );
            }
       }else return( false );
    }
    
    public boolean redoAction()
    {
       if( undoManager.canRedo() )
       {
            try
            {
                undoManager.redo();
                return( true );
            }catch(CannotUndoException e){
                return( false );
            }
       }else return( false );
    }
    
    public MyUndoableEditListener getMyUndoableEditListener()
    {
        return( this.myUndoRedo );
    }
    
    //---------------------------------------------------------------------------------------
    protected class MyUndoableEditListener  implements javax.swing.event.UndoableEditListener
    {
        public void undoableEditHappened(javax.swing.event.UndoableEditEvent e) {
            //Remember the edit and update the menus
            undoManager.addEdit(e.getEdit());
            //undoAction.updateUndoState();
            //redoAction.updateRedoState();
        }
    } 
    
   
}
