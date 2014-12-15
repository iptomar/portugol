/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.CodeEditor;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openide.util.Exceptions;

/**
 *
 * @author André Teixeira & João Gameiro
 */
public class PortugolStyle {

    //Code
    private String fontFamily;
    private int fontSize;
    private AlgolXXIStyle normalStyle;
    private AlgolXXIStyle commentStyle;
    private AlgolXXIStyle stringStyle;
    private AlgolXXIStyle blockStyle;
    private AlgolXXIStyle typeStyle;
    private AlgolXXIStyle lexiconStyle;
    private AlgolXXIStyle valueStyle;
    private AlgolXXIStyle operatorStyle;
    private Color codeBackground; //added by André dos Santos
    //Console
    private String consfontFamily;
    private int consfontSize;
    private String[] consColor;
    private boolean consBold;
    private boolean consItalic;
    private Color consBackgroundColor;//added by André dos Santos
    private Color consTextColor;//added by André dos Santos
    //Fluxogram
    private String fluxfontFamily;
    private int fluxfontSize;
    private String[] fluxColor;
    private boolean fluxBold;
    private boolean fluxItalic;

    public PortugolStyle() {
    }

    /**
     * Selector para a variável String <fontFamily>.
     *
     * @return fontFamily, String
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * Modificador para a variável String <fontFamily>.
     *
     * @param fontFamily String
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * Selector para a variável int <fontSize>.
     *
     * @return fontSize, int
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Modificador para a variável int <fontSize>.
     *
     * @param fontSize int
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Modificador para a variável int <fontSize>.
     *
     * @param fontSize String
     */
    public void setFontSize(String fontSize) {
        this.fontSize = Integer.parseInt(fontSize);
    }

    /**
     * Método que permite adicionar um estilo a um tipo de keywords, operadores,
     * comentários ou código normal.
     *
     * @param style AlgolXXIStyle, para o estilo
     * @param cat String, com o tipo de keywords, operadores, comentários ou
     * código normal
     */
    public void addStyle(AlgolXXIStyle style, String cat) {
        if (cat.equals("NORMAL")) {
            this.setNormalStyle(style);
        } else if (cat.equals("COMMENT")) {
            this.setCommentStyle(style);
        } else if (cat.equals("STRING")) {
            this.setStringStyle(style);
        } else if (cat.equals("BLOCK")) {
            this.setBlockStyle(style);
        } else if (cat.equals("TYPE")) {
            this.setTypeStyle(style);
        } else if (cat.equals("LEXICON")) {
            this.setLexiconStyle(style);
        } else if (cat.equals("VALUE")) {
            this.setValueStyle(style);
        } else if (cat.equals("OPERATOR")) {
            this.setOperatorStyle(style);
        }
    }

    /**
     * Selector para a variável AlgolXXIStyle <normalStyle>.
     *
     * @return normalStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getNormalStyle() {
        return normalStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <normalStyle>.
     *
     * @param normalStyle AlgolXXIStyle
     */
    public void setNormalStyle(AlgolXXIStyle normalStyle) {
        this.normalStyle = normalStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <commentStyle>.
     *
     * @return commentStyle AlgolXXIStyle
     */
    public AlgolXXIStyle getCommentStyle() {
        return commentStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <commentStyle>.
     *
     * @param commentStyle AlgolXXIStyle
     */
    public void setCommentStyle(AlgolXXIStyle commentStyle) {
        this.commentStyle = commentStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <stringStyle>.
     *
     * @return stringStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getStringStyle() {
        return stringStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <stringStyle>.
     *
     * @param stringStyle AlgolXXIStyle
     */
    public void setStringStyle(AlgolXXIStyle stringStyle) {
        this.stringStyle = stringStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <blockStyle>.
     *
     * @return blockStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getBlockStyle() {
        return blockStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <blockStyle>.
     *
     * @param blockStyle AlgolXXIStyle
     */
    public void setBlockStyle(AlgolXXIStyle blockStyle) {
        this.blockStyle = blockStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <typeStyle>.
     *
     * @return typeStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getTypeStyle() {
        return typeStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <typeStyle>.
     *
     * @param typeStyle AlgolXXIStyle
     */
    public void setTypeStyle(AlgolXXIStyle typeStyle) {
        this.typeStyle = typeStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <lexiconStyle>.
     *
     * @return lexiconStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getLexiconStyle() {
        return lexiconStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <lexiconStyle>.
     *
     * @param lexiconStyle AlgolXXIStyle
     */
    public void setLexiconStyle(AlgolXXIStyle lexiconStyle) {
        this.lexiconStyle = lexiconStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <valueStyle>
     *
     * @return valueStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getValueStyle() {
        return valueStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <valueStyle>
     *
     * @param valueStyle AlgolXXIStyle
     */
    public void setValueStyle(AlgolXXIStyle valueStyle) {
        this.valueStyle = valueStyle;
    }

    /**
     * Selector para a variável AlgolXXIStyle <operatorStyle>
     *
     * @return operatorStyle, AlgolXXIStyle
     */
    public AlgolXXIStyle getOperatorStyle() {
        return operatorStyle;
    }

    /**
     * Modificador para a variável AlgolXXIStyle <operatorStyle>
     *
     * @param operatorStyle AlgolXXIStyle
     */
    public void setOperatorStyle(AlgolXXIStyle operatorStyle) {
        this.operatorStyle = operatorStyle;
    }

    /**
     * Selector para a variável String <consfontFamily>.
     *
     * @return consfontFamily, String
     */
    public String getConsfontFamily() {
        return consfontFamily;
    }

    /**
     * Modificador para a variável String <consfontFamily>.
     *
     * @param consfontFamily String
     */
    public void setConsfontFamily(String fluxfontFamily) {
        this.consfontFamily = fluxfontFamily;
    }

    /**
     * Selector para a variável int <consfontSize>.
     *
     * @return consfontSize, int
     */
    public int getConsfontSize() {
        return consfontSize;
    }

    /**
     * Modificador para a variável int <consfontSize>.
     *
     * @param consfontSize int
     */
    public void setConsfontSize(int fluxfontSize) {
        this.consfontSize = fluxfontSize;
    }

    /**
     * Modificador para a variável int <consfontSize>.
     *
     * @param consfontSize String
     */
    public void setConsfontSize(String fluxfontSize) {
        this.consfontSize = Integer.parseInt(fluxfontSize);
    }

    /**
     * Selector para a variável String[] <consColor>.
     *
     * @return consColor, String[]
     */
    public String[] getConsColor() {
        return consColor;
    }

    /**
     * Modificador para a variável String[] <consColor>.
     *
     * @param r,g,b String[]
     */
    public void setConsColor(String r, String g, String b) {
        String[] cor = {r, g, b};
        this.consColor = cor;
    }

    /**
     * Modificador para variável String[] <consColor>.
     *
     * @author - André dos Santos
     * @param caretColor
     */
    public void setConsColor(Color caretColor) {
        String[] cor = {"" + caretColor.getRed(), "" + caretColor.getGreen(), "" + caretColor.getBlue()};
        this.consColor = cor;
    }

    public Color getConsBackgroundColor() {
        return consBackgroundColor;
    }

    public void setConsBackgroundColor(Color consBackgroundColor) {
        this.consBackgroundColor = consBackgroundColor;
    }

    public Color getConsTextColor() {
        return consTextColor;
    }

    public void setConsTextColor(Color consTextColor) {
        this.consTextColor = consTextColor;
    }
    
    public Color getCodeBackground() {
        return codeBackground;
    }

    public void setCodeBackground(Color codeBackground) {
        this.codeBackground = codeBackground;
    }
    

    /**
     *
     * @param path
     */
    public void writeToFile(String path) {

        String tab = "    ";
        File file = null;
        FileWriter out = null;
        try {
            file = new File(path);
            out = new FileWriter(file);
        } catch (IOException ex) {
            String[] aux = path.split("/");
            file = new File(aux[aux.length - 1]);
            try {
                out = new FileWriter(file);
            } catch (IOException ex1) {
                System.out.println("IOException: AlgolXXI.Editor.CodeStyle.Style");
                Exceptions.printStackTrace(ex1);
            }
        }

        try {
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<style>\n");
            out.write(tab + "<code>\n");

            out.write(tab + tab + "<font>\n");
            out.write(tab + tab + tab + "<family>" + this.fontFamily + "</family>\n");
            out.write(tab + tab + tab + "<size>" + Integer.toString(this.fontSize) + "</size>\n");
            out.write(tab + tab + "</font>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(normalStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(normalStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(normalStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(normalStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(normalStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>NORMAL</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(commentStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(commentStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(commentStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(commentStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(commentStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>COMMENT</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(stringStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(stringStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(stringStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(stringStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(stringStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>STRING</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(blockStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(blockStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(blockStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(blockStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(blockStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>BLOCK</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(typeStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(typeStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(typeStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(typeStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(typeStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>TYPE</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(lexiconStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(lexiconStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(lexiconStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(lexiconStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(lexiconStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>LEXICON</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(valueStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(valueStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(valueStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(valueStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(valueStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>VALUE</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + tab + "<set>\n");
            out.write(tab + tab + tab + "<r>" + Integer.toString(operatorStyle.getColor().getRed()) + "</r>\n");
            out.write(tab + tab + tab + "<g>" + Integer.toString(operatorStyle.getColor().getGreen()) + "</g>\n");
            out.write(tab + tab + tab + "<b>" + Integer.toString(operatorStyle.getColor().getBlue()) + "</b>\n");
            out.write(tab + tab + tab + "<bold>" + new Boolean(operatorStyle.isBold()).toString() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + new Boolean(operatorStyle.isItalic()).toString() + "</italic>\n");
            out.write(tab + tab + tab + "<category>OPERATOR</category>\n");
            out.write(tab + tab + "</set>\n");

            out.write(tab + "</code>\n");
            out.write(tab + "<flux>\n");

            out.write(tab + tab + "<font>\n");
            out.write(tab + tab + tab + "<family>" + this.fluxfontFamily + "</family>\n");
            out.write(tab + tab + tab + "<size>" + Integer.toString(this.fluxfontSize) + "</size>\n");
            out.write(tab + tab + tab + "<bold>" + this.isFluxBold() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + this.isFluxItalic() + "</italic>\n");
            out.write(tab + tab + "</font>\n");

            out.write(tab + tab + "<color>\n");
            if (this.fluxColor != null) {
                out.write(tab + tab + tab + "<r>" + this.fluxColor[0] + "</r>\n");
                out.write(tab + tab + tab + "<g>" + this.fluxColor[1] + "</g>\n");
                out.write(tab + tab + tab + "<b>" + this.fluxColor[2] + "</b>\n");
            } else {
                out.write(tab + tab + tab + "<r>" + 0 + "</r>\n");
                out.write(tab + tab + tab + "<g>" + 0 + "</g>\n");
                out.write(tab + tab + tab + "<b>" + 0 + "</b>\n");
            }
            out.write(tab + tab + "</color>\n");

            out.write(tab + "</flux>\n");
            out.write(tab + "<cons>\n");

            out.write(tab + tab + "<font>\n");
            out.write(tab + tab + tab + "<family>" + this.consfontFamily + "</family>\n");
            out.write(tab + tab + tab + "<size>" + Integer.toString(this.consfontSize) + "</size>\n");
            out.write(tab + tab + tab + "<bold>" + this.isConsBold() + "</bold>\n");
            out.write(tab + tab + tab + "<italic>" + this.isConsItalic() + "</italic>\n");
            out.write(tab + tab + tab + "<color>\n");
            if (this.consColor != null) {
                out.write(tab + tab + tab + tab + "<r>" + this.consTextColor.getRed() + "</r>\n");
                out.write(tab + tab + tab + tab + "<g>" + this.consTextColor.getGreen() + "</g>\n");
                out.write(tab + tab + tab + tab + "<b>" + this.consTextColor.getBlue() + "</b>\n");
            } else {
                out.write(tab + tab + tab + tab + "<r>" + 255 + "</r>\n");
                out.write(tab + tab + tab + tab + "<g>" + 255 + "</g>\n");
                out.write(tab + tab + tab + tab + "<b>" + 255 + "</b>\n");
            }
            out.write(tab + tab + tab + "</color>\n");
            out.write(tab + tab + "</font>\n");

            out.write(tab + tab + "<color>\n");
            if (this.consColor != null) {
                out.write(tab + tab + tab + "<r>" + this.consColor[0] + "</r>\n");
                out.write(tab + tab + tab + "<g>" + this.consColor[1] + "</g>\n");
                out.write(tab + tab + tab + "<b>" + this.consColor[2] + "</b>\n");
            } else {
                out.write(tab + tab + tab + "<r>" + 0 + "</r>\n");
                out.write(tab + tab + tab + "<g>" + 0 + "</g>\n");
                out.write(tab + tab + tab + "<b>" + 0 + "</b>\n");
            }
            out.write(tab + tab + "</color>\n");

            out.write(tab + "</cons>\n");
            out.write("</style>\n");

            out.flush();
            out.close();

        } catch (IOException ex1) {
            System.out.println("IOException: AlgolXXI.Editor.CodeStyle.Style");
            Exceptions.printStackTrace(ex1);
        }

    }

    public boolean isConsBold() {
        return consBold;
    }

    public void setConsBold(String bold) {
        if (bold.equals("true")) {
            this.consBold = true;
        } else {
            this.consBold = false;
        }
    }

    public boolean isConsItalic() {
        return consItalic;
    }

    public void setConsItalic(String italic) {
        if (italic.equals("true")) {
            this.consItalic = true;
        } else {
            this.consItalic = false;
        }
    }

    public String getFluxfontFamily() {
        return fluxfontFamily;
    }

    public void setFluxfontFamily(String fluxfontFamily) {
        this.fluxfontFamily = fluxfontFamily;
    }

    public int getFluxfontSize() {
        return fluxfontSize;
    }

    public void setFluxfontSize(int fluxfontSize) {
        this.fluxfontSize = fluxfontSize;
    }

    public void setFluxfontSize(String fluxfontSize) {
        this.fluxfontSize = Integer.parseInt(fluxfontSize);
    }

    public String[] getFluxColor() {
        return fluxColor;
    }

    public void setFluxColor(String r, String g, String b) {
        String[] cor = {r, g, b};
        this.fluxColor = cor;
    }

    public boolean isFluxBold() {
        return fluxBold;
    }

    public void setFluxBold(String fluxBold) {
        if (fluxBold.equals("true")) {
            this.consBold = true;
        } else {
            this.consBold = false;
        }
    }

    public boolean isFluxItalic() {
        return fluxItalic;
    }

    public void setFluxItalic(String fluxItalic) {
        if (fluxItalic.equals("true")) {
            this.consBold = true;
        } else {
            this.consBold = false;
        }
    }

}
