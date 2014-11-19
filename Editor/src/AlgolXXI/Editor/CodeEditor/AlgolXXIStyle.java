/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgolXXI.Editor.CodeEditor;

import java.awt.Color;

/**
 *Classe que define os objectos AlgolXXIStyle.
 * @author Saso
 */
public class AlgolXXIStyle {
    private Color color;
    private boolean bold;
    private boolean italic;


    /**
     * Constructor por defeito da classe AlgolXXIStyle.
     */
    public AlgolXXIStyle() {
    }
    
    /**
     * Constructor por parâmetros da classe AlgolXXIStyle.
     * @param r int 
     * @param g int 
     * @param b int
     * @param bold boolean
     * @param italic boolean
     */
    public AlgolXXIStyle(int r, int g, int b, boolean bold, boolean italic) {
        this.color = new Color(r,g,b);
        this.bold = bold;
        this.italic = italic;
    }
    
    /**
     * Constructor por parâmetros da classe AlgolXXIStyle.
     * @param r String 
     * @param g String 
     * @param b String 
     * @param bold String
     * @param italic String
     */
    public AlgolXXIStyle(String r, String g, String b, String bold, String italic) {
        this.color = new Color(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b));
        if(bold.equals("true"))
            this.bold = true;
        else
            this.bold = false;
        if(italic.equals("true"))
            this.italic = true;
        else
            this.italic = false;
    }

    /**
     * Selector para a variável Color <color>
     * @return color, Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Modificador para a variável Color <color>
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Selector para a variável boolean <bold>
     * @return bold, boolean
     */
    public boolean isBold() {
        return bold;
    }

    /**
     * Modificador para a variável boolean <bold>
     * @param bold boolean
     */
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    /**
     * Selector para a variável <italic>
     * @return italic, boolean
     */
    public boolean isItalic() {
        return italic;
    }

    /**
     * Modificador para a variável <italic>
     * @param italic boolean
     */
    public void setItalic(boolean italic) {
        this.italic = italic;
    }
}
