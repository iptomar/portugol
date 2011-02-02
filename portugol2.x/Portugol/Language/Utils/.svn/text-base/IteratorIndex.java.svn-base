/*
 * IteratorIndex.java
 *
 * Created on 21 de Setembro de 2006, 22:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Portugol.Language.Utils;

/**
 *
 * @author arm
 */
public class IteratorIndex extends IteratorString{
    
    /** Creates a new instance of IteratorIndex */
    public IteratorIndex(String msg) {
        SEPARATOR = " \t";
        this.str = msg;
        next();
    }
    private void PassarIndex(){
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
            //se encontrar o ]
            if(end < str.length() && str.charAt(end) == ']')
                end++; //passar o ]
            // senão é um ERRO       
    }
    
    private void PassarString(){
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
            // senão é um ERRO - String não terminada
    }
    
    /**
     * tem de passar por cima das virgulas das funções com parametros
     */
    public void next(){
        begin= end+1;
        //passar po cima dos separadores
        while( begin  < str.length() &&  SEPARATOR.indexOf(str.charAt(begin))>=0 )
            begin++;
        
        //-------------------------- INDEX ----------------------------------
        if(begin  < str.length() && str.charAt(begin)== '['){
           PassarIndex();
        }
       //--------------------------------- strings -----------------------------
        else  if(begin  < str.length() && str.charAt(begin)== STR){            
            PassarString();            
        } 
        //------------------------ NORMAL ---------------------------------------
        else{
            end = begin;
            while( end  < str.length() &&  SEPARATOR.indexOf(str.charAt(end))==-1 ) // str.charAt(end)!= ' ')
                end++;
        }
    }
}
