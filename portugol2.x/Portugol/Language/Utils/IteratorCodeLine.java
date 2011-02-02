/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * IteratorCodeLine.java
 *
 * Created on 2 de Junho de 2006, 20:39
 *
 *
 */

package Portugol.Language.Utils;


/**
 * Itera uma string pelos espaços
 * - preserva as strings
 *-  preserva as variaveis indexadas []
 *- preserva os parentesis
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
public class IteratorCodeLine extends IteratorString{
    
    /**
     * Creates a new instance of IteratorCodeLine
     * @param msg string a iterar
     */
    
    public IteratorCodeLine(String msg) {
        
        SEPARATOR = " \t";
        str = msg;
        next();
    }
    
    /**
     * contrutor
     * @param msg string a iterar
     * @param separ caracteres separadores
     */
    public IteratorCodeLine(String msg, String separ) {
        super(msg);
        SEPARATOR = separ;
        str = msg;
        next();
    }
    /**
     * tem de passar por cima das virgulas das funções com parametros
     */
    public void next(){
        begin= end+1;
        
        while( begin  < str.length() &&  SEPARATOR.indexOf(str.charAt(begin))>=0 ) // str.charAt(begin)== ' ' )
            begin++;
        
        //INDEX
        if(begin  < str.length() && str.charAt(begin)== '['){
            end = begin;
            int rect =0;
            while( end  < str.length() ){
                // contar os []
                if( str.charAt(end) =='[')  rect++;
                if( str.charAt(end) ==']')   rect--;
                if( rect == 0 )
                    break;
                end++;
            }
            //passar o ]
            end+=2;
        }
        //strings
        else if(begin  < str.length() && str.charAt(begin)== '"'){
            end = begin+1;
            while( end  < str.length() ){
                //quebrar o ciclo
                if( str.charAt(end) =='\"' && (end==0 || end>0 && str.charAt(end-1) != '\\'))
                    break;
                
                end++;
            }
            //se encontrar o \"
            if(end < str.length() && str.charAt(end) =='\"')
                end++; //passar o "
            // senão é um ERRO - String não terminada
            
            
        } else{
            end = begin;
            //passar os espaços entre os perentesis
            int parentesis = 0;
            while( end  < str.length()   ){
                if( str.charAt(end) == '(')
                    parentesis++;
                if( str.charAt(end) == ')')
                    parentesis--;
                if( SEPARATOR.indexOf(str.charAt(end))!= -1 && parentesis%2 ==0)
                    break;
                
                end++;
            }
        }
    }
    
    
    public static void main(String args[]){
        System.out.println("IteratorCodeLine");
        //String str = " escrever i [  j[20] + 10 ]  + 20 , 50";
        String str = "  a [1] <- a[3]  + a [4]";
        IteratorCodeLine it = new IteratorCodeLine(str);
        while( it.hasMoreElements()){
            System.out.println(it.current());
            it.next();
        }
        
    }
    
}
