/*
 * AlgoCodeBlank.java
 *
 * Created on 22 de Setembro de 2006, 9:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Editor.GUI.CodeDocument;

import Editor.GUI.Dialogs.Message;
import java.awt.Color;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


/**
 *
 * @author arm
 */
public class AlgoCodeBlank extends AlgoSyntaxHighlight {
    
     private SimpleAttributeSet normal;   // normal
     private Color backGround = defaultBackGround;
    /** Creates a new instance of AlgoCodeBlank */
    public AlgoCodeBlank() {
        //set the attributes for normal
        normal = new SimpleAttributeSet();
        StyleConstants.setBold( normal,false );
        StyleConstants.setForeground( normal, Color.black );
        StyleConstants.setFontFamily( normal, "monospaced" );
    }
    
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
    
    private void printSelectLine(int numChar){
        if (numChar < 0 ) return;
        try {
            Element element = this.getParagraphElement( numChar );
            int start = element.getStartOffset();
            int end = element.getEndOffset();
            String str = this.getText(start,end - start  );
            this.remove( start, str.length() );
            StyleConstants.setBackground(normal, backGround );            
            super.insertString( start, str, normal );            
        } catch ( Exception ex ) {
            Message.Error("PRINT SELECT LINE \n" + ex.getMessage());
        }
    }
    
    
    
    public void selectErrorLine(int numChar){
        this.backGround = new Color(255,230,230);
        printSelectLine(numChar);
        this.backGround = defaultBackGround;
    }
    
    public void selectCodeLine(int numChar){
        this.backGround = new Color(255,255,120);
        printSelectLine(numChar);
        this.backGround = defaultBackGround;
        
    }
    public void deSelectCodeLine(int numChar) {
        this.backGround = defaultBackGround;
        printSelectLine(numChar);
    }
    
    
    public void clearTextBackground(){        
        try {
            int index =0 ;
            while(index < this.getLength()) {
                
                Element element = this.getParagraphElement(index);
                String old = this.getText( element.getStartOffset(),
                        element.getEndOffset() - element.getStartOffset() );
                
                printSelectLine(index);
                //fazer o skip dos \n
                if(old.length() < 2)
                    index+=2;
                else
                    index+=old.length();
            }
        } catch ( Exception ex ) {
            Message.Error("CLEAR TEXT BACKGROUND \n" + ex.getMessage());
        }
    }
    
}
