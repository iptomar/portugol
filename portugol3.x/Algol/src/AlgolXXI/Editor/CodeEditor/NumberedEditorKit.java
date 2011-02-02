package AlgolXXI.Editor.CodeEditor;

import AlgolXXI.Editor.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * 
 * @author Apocas
 */
class NumberedEditorKit extends StyledEditorKit {

    @Override
    public ViewFactory getViewFactory() {
        return new NumberedViewFactory();
    }
}

class NumberedViewFactory implements ViewFactory {

    /**
     * 
     * @param elem
     * @return
     */
    public View create(Element elem) {
        String kind = elem.getName();
        if (kind != null) {
            if (kind.equals(AbstractDocument.ContentElementName)) {
                return new LabelView(elem);
            } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
//              return new ParagraphView(elem);
                return new NumberedParagraphView(elem);
            } else if (kind.equals(AbstractDocument.SectionElementName)) {
                return new BoxView(elem, View.Y_AXIS);
            } else if (kind.equals(StyleConstants.ComponentElementName)) {
                return new ComponentView(elem);
            } else if (kind.equals(StyleConstants.IconElementName)) {
                return new IconView(elem);
            }
        }
        // default to text display
        return new LabelView(elem);
    }
}

class NumberedParagraphView extends ParagraphView {

    public static short NUMBERS_WIDTH = 30;

    /**
     * 
     * @param e
     */
    public NumberedParagraphView(Element e) {
        super(e);
        short top = 0;
        short left = 0;
        short bottom = 0;
        short right = 0;
        this.setInsets(top, left, bottom, right);
    }

    /**
     * 
     * @param top
     * @param left
     * @param bottom
     * @param right
     */
    @Override
    protected void setInsets(short top, short left, short bottom,
            short right) {
        super.setInsets(top, (short) (left + NUMBERS_WIDTH),
                bottom, right);
    }

    /**
     * 
     * @param g
     * @param r
     * @param n
     */
    @Override
    public void paintChild(Graphics g, Rectangle r, int n) {
        super.paintChild(g, r, n);
        int previousLineCount = getPreviousLineCount();
        int numberX = 0;// r.x - getLeftInset();
        int numberY = r.y + r.height - 5;

        String texto = Integer.toString(previousLineCount + n + 1);
        int fw = (g.getFontMetrics()).stringWidth(texto);

        int w = NUMBERS_WIDTH - 2;
        int h = r.height;
        int d = (g.getFontMetrics()).getDescent();


        for (int i = 0; i < h; i++) {
            g.setColor(new Color(235, 235, 235));
            g.drawLine(numberX, numberY + d - i, numberX + w, numberY + d - i);
            g.setColor(new Color(192, 192, 192));
            g.drawLine(numberX + w, numberY + d - i, numberX + w + 1, numberY + d - i);
        }
        g.setColor(Color.BLACK);
        g.drawString(texto, NUMBERS_WIDTH - fw - 5, numberY);
    }

    /**
     * 
     * @param p
     * @param distance
     * @return
     */
    int ShiftNorth(int p, int distance) {
        return (p - distance);
    }

    /**
     * 
     * @param p
     * @param distance
     * @return
     */
    int ShiftSouth(int p, int distance) {
        return (p + distance);
    }

    /**
     * 
     * @param p
     * @param distance
     * @return
     */
    int ShiftEast(int p, int distance) {
        return (p + distance);
    }

    /**
     * 
     * @param p
     * @param distance
     * @return
     */
    int ShiftWest(int p, int distance) {
        return (p - distance);
    }

    /**
     * 
     * @return
     */
    public int getPreviousLineCount() {
        int lineCount = 0;
        View parent = this.getParent();
        int count = parent.getViewCount();
        for (int i = 0; i < count; i++) {
            if (parent.getView(i) == this) {
                break;
            } else {
                lineCount += parent.getView(i).getViewCount();
            }
        }
        return lineCount;
    }
}
