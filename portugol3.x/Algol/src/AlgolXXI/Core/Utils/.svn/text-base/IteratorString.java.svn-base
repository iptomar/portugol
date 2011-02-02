
// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 

// NOTA : ALTERAR ESTE ITERADOR 


/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * IteratorString.java
 *
 * Created on 2 de Maio de 2006, 9:35
 *
 *
 */
package AlgolXXI.Core.Utils;

/**
 * Itera uma string atrav�s dos espa�os
 * 
 * @author Ant�nio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
public class IteratorString {
    
    /**
     * string a iterar
     */
    protected String str;
    /**
     * ponteiro para o inicio do elemento
     */
    protected int begin;
    /**
     * ponteiro para o fim do elemento
     */
    protected int end;
    /**
     * string com os caracters separadores
     */
    protected static String SEPARATOR = " ,\t";
    protected char STR = '\"';
    
    /**
     * construtor
     */
    protected IteratorString(){
        begin=0;
        end=-1;
    }
    /**
     * constroi o iterador
     * @param msg texto a iterar
     */
    public IteratorString(String msg) {
        begin=0;
        end=-1;
        str=msg;
        next();
    }      
    
    /**
     * verifica se existem mais elementos
     * @return verifica se existem mais elementos
     */
    public boolean hasMoreElements(){
        return  str!= null && begin < str.length();
    }
    
    /**
     * retorna o elemento corrente
     * @return retorna o elemento corrente
     */
    public String current(){
        String tmp="";
        for(int i=begin; i< end; i++)
            tmp += str.charAt(i);
        
        return tmp.trim();
    }
    
    /**
     * avan�a para o pr�ximo elemento
     */
    public void next(){
        begin= end+1;
        //passar po cima dos separadores
        while( begin  < str.length() &&  SEPARATOR.indexOf(str.charAt(begin))>=0 ) 
            begin++;        
        
        //strings
        if(begin  < str.length() && str.charAt(begin)== STR){
            end = begin+1;
            while( end  < str.length() ){
                //quebrar o ciclo 
                if( str.charAt(end) == STR && str.charAt(end-1) != '\\') 
                   break;
                 
                 end++;
            }
            //se encontrar o \"
            if(end < str.length() && str.charAt(end) ==STR)
               end++; //passar o "
            // sen�o � um ERRO - String n�o terminada
            
            
        } else{
            end = begin;
            while( end  < str.length() &&  SEPARATOR.indexOf(str.charAt(end))==-1 ) // str.charAt(end)!= ' ')
                end++;
        }
    }
    
    
   public String getProcessedString(){
        String tmp="";
        for(int i=0; i<begin; i++)
            tmp += str.charAt(i);
        
        return tmp.trim();
    }

    public String getUnprocessedString(){
        String tmp="";
        for(int i=begin; i< str.length(); i++)
            tmp += str.charAt(i);
        
        return tmp.trim();
    }
    
}
