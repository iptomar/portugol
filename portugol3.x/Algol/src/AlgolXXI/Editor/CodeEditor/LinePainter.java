package AlgolXXI.Editor.CodeEditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Classe que permite uma linha da tab de edição de código.
 * @author Apocas
 */
public class LinePainter implements Highlighter.HighlightPainter, CaretListener, MouseListener, MouseMotionListener {

    private JTextComponent component;
    private Color color;
    private int lastLine;

    /**
     * Constuctor para a classe LinePainter
     * @param component LinePainter
     * @param color Color
     */
    public LinePainter(JTextComponent component, Color color) {
        this.component = component;
        this.color = color;

        component.addCaretListener(this);
        component.addMouseListener(this);
        component.addMouseMotionListener(this);

        try {
            component.getHighlighter().addHighlight(0, 0, this);
        } catch (BadLocationException ble) {
        }
    }

    /**
     * Método que preenche e define a cor de linha.
     * @param g Graphics
     * @param p0 int
     * @param p1 int
     * @param bounds Shape
     * @param c JTextComponent
     */
    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        try {
            Rectangle r = c.modelToView(c.getCaretPosition());
            g.setColor(getColor());
            g.fillRect(0, r.y, c.getWidth(), r.height);

        } catch (BadLocationException ble) {
        }
    }

    /**
     * Método que limpa todo o syntax Highlighting.
     */
    private void resetHighlight() {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    Element root = component.getDocument().getDefaultRootElement();
                    int offset = component.getCaretPosition();
                    int currentLine = root.getElementIndex(offset);

                    if (lastLine != currentLine) {
                        Element lineElement = root.getElement(lastLine);

                        if (lineElement != null) {
                            int start = lineElement.getStartOffset();
                            Rectangle r = component.modelToView(start);
                            if (r == null) {
                                r = new Rectangle(3, 3, 0, 17);
                            }
                            component.repaint(0, r.y, component.getWidth(), r.height);
                        }

                        lastLine = currentLine;
                    }
                } catch (BadLocationException ble) {
                }
            }
        });
    }

    /**
     * Método que associado a um evento CaretEvent limpa todo o syntax Highlighting.
     * @param e CaretEvent
     */
    public void caretUpdate(CaretEvent e) {
        resetHighlight();
    }

    /**
     * Método que associado a um evento MouseEvent limpa todo o syntax Highlighting.
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        resetHighlight();
    }

    /**
     * Método associado a um evento MouseEvent. Não está implementado.
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Método associado a um evento MouseEvent. Não está implementado.
     * @param e MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Método associado a um evento MouseEvent. Não está implementado.
     * @param e MouseEvent
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Método associado a um evento MouseEvent. Não está implementado.
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Método que associado a um evento MouseEvent limpa todo o syntax Highlighting.
     * @param e MouseEvent
     */
    public void mouseDragged(MouseEvent e) {
        resetHighlight();
    }

    /**
     * Método associado a um evento MouseEvent. Não está implementado.
     * @param e MouseEvente
     */
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Selector para a variável Color <color>.
     * @return <color> Color
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
}

