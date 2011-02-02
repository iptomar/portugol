/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************//*
 * ConsoleIO.java
 *
 * Created on 19 de Outubro de 2005, 10:40
 *
                                                                                
 */

package Portugol.Language.Execute;

import Portugol.Language.Parse.Symbol;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


/**
 * representa  a consola
 * @author António M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Informática<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Politécnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
//History
// 11-11 - Line Wrap - this.setLineWrap(true);

public class ConsoleIO extends JTextArea {
    /**
     * versão do teclado
     */
    public static String VERSION = "ver:1.1\tdata:06-11-2005 \t(c)António Manso & Paulo Santos";
    
    /**
     * contrutor
     */
    public ConsoleIO() {
        setBackground(new Color(0, 0, 0));
        setFont(new Font("Courier New", 0, 12));
        setForeground(new Color(255, 255, 255));
        this.setLineWrap(true);
        this.setText("");
        this.setEditable(false);
    }
    /**
     * limpa a conola
     */
    public void Clear(){
        this.setText("");
    }
    
    
    /**
     * Ler uma símbolo
     * @param s simbolo a ser lido
     * @return valor lido da consola
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public String read(Symbol s) throws LanguageException{
        try{
            String input;
            do{
                input = JOptionPane.showInputDialog(this,"    "+s.getName(),"CONSOLE",JOptionPane.QUESTION_MESSAGE);
            }while(input.length() <=0);
            write(input+"\n");
            if( s.getType() == s.TEXTO || s.getType() == s.CARACTER)
                return Values.StringToText(input);
            else
                return input;
        }catch (Exception e){
            throw new LanguageException("Leitura abortada pelo utilizador","");
        }
    }
    
    /**
     * Escreve uma string na consola
     * @param str string a escrever
     */
    public void write(String str){
        this.append(str);
        //por o cursor no final i.e. deslizar o texto
        this.setCaretPosition( this.getText().length());
    }
    
    public void setColor(Color backGround, Color text){
        setBackground(backGround);
        setForeground(text);
    }
    
    public void setNewFont( Font f) {
        setFont(f);
    }
}
