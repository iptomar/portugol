/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Execute;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Pedro
 */
public class ConsoleReader implements DropTargetListener, Runnable {

    /**
     * Atributos da Classe
     */
    boolean reading = false;
    ConsoleIO consoleIO;
    StringBuffer readed;
    int initialCaretPosition = 0;
    DropTarget dropTarget;
    Thread thread;

    /**
     * Cria um console Reader
     * @param console Consola em que é executado
     * @param read True se estiver a ler; False cao contrário
     */
    public ConsoleReader(ConsoleIO console, boolean read) {

        reading = read;
        consoleIO = console;
        readed = new StringBuffer("");                              // stringBuffer que irá ter todos os dados lidos do teclado        
        initialCaretPosition = consoleIO.getText().length();        // CaretPosition inicial no final do texto        
        consoleIO.setCaretPosition(consoleIO.getText().length());   // CaretPosition no final do texto                      
        consoleIO.setCaretColor(Color.YELLOW);                        // Cor do Caret                      

        // adicionar keylistener
        addKeyListenerToConsole();

        dropTarget = new DropTarget(consoleIO, this);

        consoleIO.setDropMode(DropMode.INSERT);
        consoleIO.setDragEnabled(true);

        // thread que controla a leitura
        thread = new Thread(this);

        //colocar focuslistener
        addFocusListenerToConsole();

    }

    /**
     * Set do valor que indica se está ou não a existir leitura do teclado
     * @param read True se estiver a ler; False caso contrário
     */
    public void SetReading(boolean read) {
        reading = read;
    }

    /**
     * Retorna o que foi lido pelo teclado
     */
    public StringBuffer GetReaded() {
        return readed;
    }

    /**
     * Coloca focusListener na consola de forma a mostrar o Caret
     * quando a consola tem focus... e não mostrar o Caret quando perde o focus
     */
    private void addFocusListenerToConsole() {
        consoleIO.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if (reading) {
                    consoleIO.getCaret().setVisible(true);
                }
            }

            public void focusLost(FocusEvent e) {
                consoleIO.getCaret().setVisible(false);
            }
        });
    }

    /**
     * Atribuir key listener à consola, para poder ler
     * dados quando pressionada uma tecla na consola!
     */
    private void addKeyListenerToConsole() {

        // KeyListeners
        consoleIO.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                // Reading                
                if (reading) {
                    // caracter inserido
                    char typed = e.getKeyChar();
                    // texto... para verificar se foi premido o CTRL
                    String modifiersText = e.getKeyModifiersText(e.getModifiers());

                    // Verificar a posicao de escrita
                    VerifyCaretPosition();

                    // inserir caracter                    
                    System.out.println("typed:" + KeyEvent.VK_DELETE);
                    if (!(typed + "").equals('\u001b' + "") && // não é a tecla Escape
                            !(typed + "").equals('\b' + "") && // não é BackSpace
                            !modifiersText.contains("Ctrl") && // não é uma combinação de Ctrl + ... que gera carácters desconhecidos                                                        
                            !wasDeleteButton(e)) {
                        int cp = consoleIO.getCaretPosition();
                        int position = cp - initialCaretPosition;
                        System.out.println("CaretPosition Antes:" + consoleIO.getCaretPosition());
                        readed.insert(position, e.getKeyChar() + "");
                        consoleIO.writeFromReader(e.getKeyChar() + "");
                        System.out.println("CaretPosition Depois:" + consoleIO.getCaretPosition() + " Texto:" + readed);
                    }
                }
            }

            public void keyPressed(KeyEvent e) {
                // Reading
                if (reading) {
                    try {
                        // ENTER Pressed
                        int keyCode = e.getKeyCode();
                        switch (keyCode) {
                            case KeyEvent.VK_ENTER:
                                reading = false;
                                consoleIO.SetReading(false);
                                consoleIO.setCaretPosition(consoleIO.getText().length());
                                consoleIO.transferFocus();          // retirar o focus da Consola
                                System.out.println("Readed:" + readed);

                                break;
                            case KeyEvent.VK_BACK_SPACE:
                                //Saber quantos caracteres são para apagar (1 ou +)
                                int initCh = consoleIO.getSelectionStart();
                                int endCh = consoleIO.getSelectionEnd();
                                if (endCh - initCh == 0) {
                                    // Apagar apenas um caracter... nao existe texto seleccionado                                    
                                    // garantir que com o backspace a consola não apaga texto que nao deve                               
                                    if (consoleIO.getCaretPosition() > initialCaretPosition) {
                                        // colocar o cursor na posicao anterior
                                        consoleIO.setCaretPosition(consoleIO.getCaretPosition() - 1); // decrementar a posição do caracter
                                        // remover um caracter
                                        consoleIO.getDocument().remove(consoleIO.getCaretPosition(), 1);
                                        // Para apagar um caracter da String que foi lida é necessário
                                        // saber a posicao, dada por: Posicao Na String = caretPosition - initialCaretPosition
                                        int pos = consoleIO.getCaretPosition() - initialCaretPosition;
                                        readed.deleteCharAt(pos);
                                        System.out.println("" + readed);
                                        System.out.println("CaretPosition:" + consoleIO.getCaretPosition());
                                    }
                                } else {
                                    // Existe texto selecionado... apagar todos os caracteres
                                    if (initCh < initialCaretPosition) {
                                        initCh = initialCaretPosition;
                                    }
                                    // apagar o texto da consola
                                    consoleIO.getDocument().remove(initCh, endCh - initCh);
                                    // apagar o texto da StringBuffer
                                    readed.delete(initCh - initialCaretPosition, endCh - initialCaretPosition);
                                    System.out.println("Readed = " + readed);
                                }

                                break;
                            case KeyEvent.VK_LEFT:
                                if (consoleIO.getCaretPosition() < initialCaretPosition) {
                                    consoleIO.setCaretPosition(initialCaretPosition);
                                }
                                System.out.println("Caret:" + consoleIO.getCaret());
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (consoleIO.getCaretPosition() > consoleIO.getText().length()) {
                                    consoleIO.setCaretPosition(consoleIO.getText().length());
                                }
                                break;

                            case KeyEvent.VK_INSERT:
                                // verificar a posicao onde colocar o texto
                                VerifyCaretPosition();

                                // posicao na string = console.getCharetPos - initCharetPos
                                int pos = consoleIO.getCaretPosition() - initialCaretPosition;
                                int caretPosBeforePaste = consoleIO.getCaretPosition();

                                // colar texto na consola... para isso é necessário 'abrir' a consola
                                // synchronized evita que seja possivel alterar conteudo da consola enquanto está editavel
                                synchronized (this) {
                                    consoleIO.setEditable(true);        // tornar a consola editável                                    
                                    // Obter os dados do Clipboard e retirar a primeira linha da string
                                    Transferable tr = consoleIO.getToolkit().getDefaultToolkit().getSystemClipboard().getContents(consoleIO);
                                    if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                                        String s = (String) tr.getTransferData(DataFlavor.stringFlavor);
                                        // Escrever a primeira linha da string copiada
                                        int index = s.indexOf('\n');    // se index = -1 é porque foi copiada apenas uma linha
                                        if (index == -1) {
                                            consoleIO.write(s);
                                        } else {
                                            consoleIO.write(s.substring(0, index));
                                        }
                                    }
                                    //consoleIO.paste();
                                    consoleIO.setEditable(false);       // "fechar" a consola                                                                
                                }

                                // colocar o texto na string 'readed'
                                readed.insert(pos, consoleIO.getDocument().getText(caretPosBeforePaste, consoleIO.getCaretPosition() - caretPosBeforePaste));
                                System.out.println("Readed:" + readed);

                                break;
                            case KeyEvent.VK_DELETE:
                                //Saber quantos caracteres são para apagar (1 ou +)
                                int initChar = consoleIO.getSelectionStart();
                                int endChar = consoleIO.getSelectionEnd();
                                if (endChar - initChar == 0) {
                                    // Apagar apenas um caracter... nao existe texto seleccionado
                                    // garantir que com o delete a consola não apaga texto que nao deve                               
                                    int carPos = consoleIO.getCaretPosition();
                                    if (carPos >= initialCaretPosition && carPos < consoleIO.getText().length()) {
                                        // remover um caracter
                                        consoleIO.getDocument().remove(consoleIO.getCaretPosition(), 1);
                                        // Para apagar um caracter da String que foi lida é necessário
                                        // saber a posicao, dada por: Posicao Na String = caretPosition - initialCaretPosition
                                        int posInStr = consoleIO.getCaretPosition() - initialCaretPosition;
                                        readed.deleteCharAt(posInStr);
                                        System.out.println("" + readed);
                                        System.out.println("CaretPosition:" + consoleIO.getCaretPosition());
                                    }
                                } else {
                                    // Existe texto selecionado... apagar todos os caracteres
                                    if (initChar < initialCaretPosition) {
                                        initChar = initialCaretPosition;
                                    }
                                    // apagar o texto da consola
                                    consoleIO.getDocument().remove(initChar, endChar - initChar);
                                    // apagar o texto da StringBuffer
                                    readed.delete(initChar - initialCaretPosition, endChar - initialCaretPosition);
                                    System.out.println("Readed = " + readed);
                                }
                                break;
                            default:
                        }
                    } catch (UnsupportedFlavorException ex) {
                        Logger.getLogger(ConsoleReader.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ConsoleReader.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BadLocationException ex) {
                        // Apanhada a Excepção no caso de tentar apagar fora dos limites
                        Logger.getLogger(ConsoleReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

    }

    /** Verificar se foi precionada a tecla Delete */
    private boolean wasDeleteButton(KeyEvent e) {
        StringBuffer buf = new StringBuffer(e.toString());
        // verificar se foi a tecla Del
        return buf.indexOf("keyChar=Delete") > 0;
    }

    /**
     * Posição inicial do Caret na String... Atribuido quando é iniciada a leitura
     * Permite garantir que o utilizador não apaga dados que não pode...
     * @param pos Posicao initial do Caret quando se começa a ler
     */
    public void SetInitialCaretPos(int pos) {
        initialCaretPosition = pos;
    }

    //
    // Métodos a implementar da interface DropTargetListener
    // para permitir fazer o drop de texto na consola
    //
    public void dragEnter(DropTargetDragEvent dtde) {

    }

    public void dragOver(DropTargetDragEvent dtde) {

    }

    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    public void dragExit(DropTargetEvent dte) {

    }

    /**
     * Evento que ocorre ao fazer o Drop de texto na consola!
     * @param dtde Contém informaçoes daquilo o qual é feito o drop
     * na consola
     */
    public void drop(DropTargetDropEvent dtde) {

        // Se estiver a ler
        if (reading) {
            // objecto transferivel 
            Transferable tr = dtde.getTransferable();

            // retorna o valor dos metadados que fornecem do Drop
            if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                // Se é texto
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    // String Transferida
                    String s = (String) tr.getTransferData(DataFlavor.stringFlavor);

                    // Drop do texto apenas da primeira linha
                    String str;
                    int index = s.indexOf('\n');
                    if (index == -1) {
                        str = s;
                    } else {
                        str = s.substring(0, index);
                    }

                    VerifyCaretPosition();

                    int positionDropedText = consoleIO.getCaretPosition();
                    // Escrever a String na consola
                    consoleIO.writeFromReader(str);
                    // Actualizar a string lida
                    readed.insert(positionDropedText - initialCaretPosition, str);
                    System.out.println("new String:" + readed);
                    // DROP Completo
                    dtde.dropComplete(true);
                } catch (UnsupportedFlavorException ex) {
                } catch (IOException ex) {
                }
            } else {
                // mostrar mensagem!
                JOptionPane.showMessageDialog(consoleIO, "A Consola só permite Texto!");
                // rejeitar o drop
                dtde.rejectDrop();
            }
        }
    }

    /**
     * Verifica e altera se necessário a CaretPosition da consola,
     * de forma a garantir que não é colocado texto em local proibido
     */
    private void VerifyCaretPosition() {
        // garantir que o utilizador nao escreve em local protegido!                   
        // caso pretenda colocar texto antes do texto já existente na consola, 
        // o texto é colocado na primeira posicao possivel.
        // caso o texto fosse para inserir em posicao invalida no fim... o texto
        // é colocado na ultima posicao...
        if (consoleIO.getCaretPosition() < initialCaretPosition) {
            consoleIO.setCaretPosition(initialCaretPosition);
        } else if (consoleIO.getCaretPosition() > consoleIO.getText().length()) {
            consoleIO.setCaretPosition(consoleIO.getText().length());
        }
    }

    public void run() {
        while (reading) {
        }
    }
}
