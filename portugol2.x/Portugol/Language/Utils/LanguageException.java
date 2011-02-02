/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * ExceptionCompileError.java
 *
 * Created on 12 de Outubro de 2005, 22:32
 *
 */

package Portugol.Language.Utils;

import java.util.Hashtable;
/**
 * Exceções da linguagem
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
public class LanguageException extends Exception {
    
    /**
     * numero da linha
     */
    public int line ; // numero da linha
    /**
     * texto da linha
     */
    public String codeLine;  //Linha de código
    /**
     * causa da excepção
     */
    public String error;
    /**
     * solução da excepção
     */
    public String solution;
    
  
    /**
     * Contrutor
     * @param l número da linha
     * @param code texto da linha de código
     * @param e texto do erro
     * @param s texto da solução
     */
    public LanguageException(int l, String code, String e, String s) {
        super(e);
        line = l;
        codeLine = code;
        error = e;
        solution = s;
    }
    
    /**
     * Contrutor
     * @param e texto do erro
     * @param s Texto da excepção
     */
    public LanguageException(String e, String s) {
        super(e);
        line = 0;
        codeLine = "";
        error = e;
        solution = s;
    }
    /**
     * Mostra a excepção
     */
    public void Show(){
       System.out.println(GetError());
    }
    
    /**
     * devolve uma string com a excepção
     * @return texto da excepção
     */
    public String GetError(){
        StringBuffer str = new StringBuffer();
        str.append("INSTRUÇÃO:\t" +codeLine  + "\n");
        str.append("ERRO:\t" + error  + "\n");
        str.append("SOLUÇÃO:\t" +solution +"\n");
        str.append("Linha:\t" +line );
        return str.toString();
    }
    
    /**
     * return object String
     * @return excepção
     */
    public String toString(){
       return GetError();
    }
    
}
