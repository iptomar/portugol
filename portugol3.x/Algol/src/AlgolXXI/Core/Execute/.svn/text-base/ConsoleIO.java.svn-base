/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************//*
 * ConsoleIO.java
 *
 * Created on 19 de Outubro de 2005, 10:40
 *
 */

package AlgolXXI.Core.Execute;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * representa  a consola
 * @author António M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
//History
// 11-11 - Line Wrap - this.setLineWrap(true);
public class ConsoleIO extends JTextArea implements Runnable {

    /**
     * Atributos
     */
    boolean reading = false;
    ConsoleReader consoleReader;
    Thread thread = null;

    /**
     * Contrutor da Consola
     */
    public ConsoleIO() {
        setBackground(new Color(0, 0, 0));
        setFont(new Font("Courier New", Font.BOLD, 15));
        setForeground(new Color(255, 255, 255));
        this.setLineWrap(true);
        this.setText("");
        this.setEditable(false);
        this.setCursor(new Cursor(1));
        this.setAutoscrolls(true);
        // Objecto que faz a leitura dos dados do teclado
        consoleReader = new ConsoleReader(this, reading);
    }

    /**
     * Limpa o texto da consola
     */
    public void Clear() {
        this.setText("");
    }

    public String GetReadedValue() {
        writeLn("");
        return this.consoleReader.readed.toString();
    }

    /**
     * Escreve uma string na consola e coloca o cursor no final...
     * Este metodo é utilizado para escrever, nao devendo ser utilizado
     * pela classe ConsoleReader para escrever os careacteres lidos do teclado
     * @param str string a escrever
     */
    public void write(String str) {
        // Colocar o cursor no fim do texto
        this.setCaretPosition(this.getText().length());
        // colocar o texto na posição do cursor
        this.insert(str, this.getCaretPosition());
    }

    /**
     * Utilizado pelo objecto do tipo ConsoleReader para escrever
     * quando lê do teclado!!
     * @param str String a escrever na consola...
     */
    public void writeFromReader(String str) {
        // colocar o texto na posição do cursor
        this.insert(str, this.getCaretPosition());
    }

    /**
     * Escreve uma string na consola
     * @param str string a escrever
     */
    public void writeLn(String str) {
        write("\n" + str + "\n");
    }

    /**
     * Ler uma linha do teclado e retorna-a
     * @param type Tipo de valor a ser lido
     */
    public void read() throws IOException {
        //colocar o focus na consola        
        reading = true;
        consoleReader.SetReading(true);   // permitir a leitura     

        consoleReader.GetReaded().delete(0, consoleReader.GetReaded().length()); // limpar o String Buffer

        consoleReader.SetInitialCaretPos(this.getText().length()); // posicao inicial do texto é todo o texto existente... que por consequência não poderá ser apagado        

        this.setCaretPosition(this.getText().length());

        //colocar o focus na consola
        this.requestFocusInWindow();

        // definir a thread        
        thread = new Thread(this);
        // iniciar a thread
        thread.start();
    }

    /**
     * Atribuir cores à consola
     * @param backGround Cor de fundo
     * @param text Cor do texto
     */
    public void setColor(Color backGround, Color text) {
        setBackground(backGround);
        setForeground(text);
    }

    /**
     * Atribuir fonte a utilizar ao escrever na consola
     * @param f fonte a utilizar para escrever na consola
     */
    public void setNewFont(Font f) {
        setFont(f);
    }

    /**
     * Atribuir valor boolean se está ou não a ser lido da consola
     * @param read true indica a ler da consola; false caso contrário
     */
    public void SetReading(boolean read) {
        reading = read;
    }

    /**
     * Verifica se está a ser lido valores da consola
     * @return true se está a ser lido da consola; false caso contrário.
     */
    public boolean GetReading() {
        return reading;
    }

    // Eduardo Dias - experimental, parar leitura de dados.
    public void StopReading() {
        try {
            consoleReader.SetReading(false);
            reading = false;
            if (this.thread != null) {
                this.thread.join();
            }
            Clear();
        //this.setCaretPosition(this.getText().length());
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (reading) {
            try {
                thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        thread = null;
    }

    public Thread GetThread() {
        return thread;
    }
}
