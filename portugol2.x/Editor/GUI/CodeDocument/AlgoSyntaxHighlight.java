/*
 * AlgoSyntaxHighlight.java
 *
 * Created on 22 de Setembro de 2006, 9:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Editor.GUI.CodeDocument;

import java.awt.Color;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author arm
 */
public class AlgoSyntaxHighlight extends DefaultStyledDocument{
    
    public static Color defaultBackGround = Color.WHITE;
    /** Creates a new instance of AlgoSyntaxHighlight */
    public AlgoSyntaxHighlight() {}
    
    private void printSelectLine(int numChar){}
    
    
     public void selectErrorLine(int numChar){}
    
    public void selectCodeLine(int numChar){}
    public void deSelectCodeLine(int numChar) {}
    
    
    public void clearTextBackground(){ }
    
}
