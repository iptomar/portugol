package AlgolXXI.Editor.CodeEditor;

import AlgolXXI.Editor.CodeEditor.*;
import AlgolXXI.Editor.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import org.openide.util.ImageUtilities;

import org.openide.util.Utilities;

/**
 * Classe que define as propriedades e accções do PopupMenu de conversão de código.
 * @author Apocas
 */
public class PopupMenu implements ActionListener, ItemListener {

    private JPopupMenu popup;
    private JMenuItem menuItem;
    private ProgramaTopComponent editor;
    private ResourceBundle bundle;
    private Locale currentLocale;

    /**
     * Constructor da classe que cria o PopupMenu que permite converter a linguagem para outras línguas
     * @param algolf  ProgramaTopComponent
     * @param codigo JEditorPane 
     */
    public PopupMenu(ProgramaTopComponent algolf, JEditorPane codigo) {
        editor = algolf;
        currentLocale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("AlgolXXI/Editor/Bundle", currentLocale);
        popup = new JPopupMenu();

        menuItem = new JMenuItem("Copiar");
        menuItem.setIcon(new ImageIcon(ImageUtilities.loadImage("AlgolXXI/Editor/Images/copy.png", true)));
        menuItem.addActionListener(this);
        popup.add(menuItem);

        menuItem = new JMenuItem("Colar");
        menuItem.setIcon(new ImageIcon(ImageUtilities.loadImage("AlgolXXI/Editor/Images/paste.png", true)));
        menuItem.addActionListener(this);
        popup.add(menuItem);

        menuItem = new JMenuItem("-");
        popup.add(menuItem);

        menuItem = new JMenuItem(bundle.getString("Converter_para_C++"));
        menuItem.setIcon(new ImageIcon(ImageUtilities.loadImage("AlgolXXI/Editor/Images/text_uppercase.png", true)));
        menuItem.addActionListener(this);
        popup.add(menuItem);

        menuItem = new JMenuItem(bundle.getString("Converter_para_Java"));
        menuItem.setIcon(new ImageIcon(ImageUtilities.loadImage("AlgolXXI/Editor/Images/text_uppercase.png", true)));
        menuItem.addActionListener(this);
        popup.add(menuItem);

        MouseListener popupListener = new PopupListener(popup, editor);

        codigo.addMouseListener(popupListener);
    }

    /**
     * Método que permite converter o código consoante a opção escolhida no menu
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());

        if (source.getText().equals("Copiar")) {
            editor.call_copiar();
        }
        if (source.getText().equals("Colar")) {
            editor.call_colar();
        }
        if (source.getText().equals(bundle.getString("Converter_para_C++"))) {
            //editor.convert2C();
        }
        if (source.getText().equals(bundle.getString("Converter_para_Java"))) {
            editor.convert2Java();
        }
    }

    /**
     * Método que retorna o index seleccionado. Compara o texto do PopupMenu com o texto do JMenuItem seleccionado 
     * @param source JMenuItem
     * @return index se a condição for verdadeira, -1 se falsa
     */
    private int getIndex(JMenuItem source) {
        for (int i = 0; i < popup.getSubElements().length; i++) {
            if (((JMenuItem) (popup.getSubElements()[i])).getText().equals(source.getText())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método que verifica a alteração do estado do item.
     * @param e ItemEvent
     */
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método que retorna o nome da classe de um objecto
     * @param o Object
     * @return string com o nome da classe
     */
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }
}

class PopupListener extends MouseAdapter {

    JPopupMenu popup = null;
    ProgramaTopComponent editor = null;

    /**
     * Constructor da classe PopupListener
     * @param popupMenu JPopupMenu
     */
    PopupListener(JPopupMenu popupMenu, ProgramaTopComponent algolf) {
        popup = popupMenu;
        editor = algolf;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * Método que permite mostrar o JPopupMenu aquando um envento do rato
     * @param e MouseEvent
     */
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger() && !editor.isRunning()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
