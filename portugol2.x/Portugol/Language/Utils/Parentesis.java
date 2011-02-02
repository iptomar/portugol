/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Parentesis.java
 *
 * Created on 8 de Maio de 2006, 0:06
 *
 *
 */

package Portugol.Language.Utils;

import java.util.Stack;

/**
 * verifica a posição dos parentesis numa expressão
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
public class Parentesis {
    /**
     * parentesis que abrem
     */
    protected static String parOpen     ="([{";
    /**
     * parentesis que fecham
     */
    protected static String parClose    =")]}";
    
    
    /**
     * verifica se é parentesis
     * @param ch caracter a verificar
     * @return verdadeiro se for um paratesis
     */
    public static boolean IsParentesis(char ch){
        return parOpen.indexOf(ch)!= -1 || parClose.indexOf(ch) != -1;
    }
    
    /**
     * verifica se é parentesis
     * @param ch string a verificar
     * @return verdadeiro se for um parentesis
     */
    public static boolean IsParentesis(String ch){
        return parOpen.indexOf(ch)!= -1 || parClose.indexOf(ch) != -1;
    }

    
    /**
     * verifica se dois parentesis casam
     * @param open parentesis a abrir
     * @param close parentesis a fechar
     * @return verdadeiro se forem complementares
     */
    protected static boolean Match(char open, char close){
        return parOpen.indexOf(open)== parClose.indexOf(close);
    }
    
    /**
     * verifica se uma string tem os parentesis na ordem correcta
     * @param expr string a verificar
     * @return verdadeiro se os parentesis estiverem correctos
     */
    public static boolean Verify(String expr){
        Stack s = new Stack();
        for(int i=0; i< expr.length(); i++){
            char ch = expr.charAt(i);
            if(parOpen.indexOf(ch) != -1)
                       s.push(""+ch);
            else if(parClose.indexOf(ch) != -1){
                if( s.empty()) 
                    return  false;
                char ch2 = ((String)s.pop()).charAt(0);
                if( ! Match( ch , ch2))
                    return  false;
            }            
        }
        if( ! s.empty())
            return false;
        return true;                    
    }
    
    /**
     * calcula o erro de parentesis numa expressão
     * @param expr expressão a verificar
     * @return descriçãio do erro
     */
    public static String GetError(String expr){
        Stack s = new Stack();
        for(int i=0; i< expr.length(); i++){
            char ch = expr.charAt(i);
            if(parOpen.indexOf(ch) != -1)
                s.push(""+ch);
            else if(parClose.indexOf(ch) != -1){
                if( s.empty())
                    return  " parentesis " + ch  + "nao esta aberto";
                char ch2 = ((String)s.pop()).charAt(0);
                if( ! Match( ch , ch2))
                    return  " parentesis " + ch  + " e " + ch2 + "nao combinam";
            }            
        }
        if( ! s.empty())
            return "falta fechar os parentesis: " + s.toString();        
        return "OK";                    
    }
}
