/*
 * IteratorElements.java
 *
 * Created on 20 de Setembro de 2006, 20:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Portugol.Language.Utils;

import Portugol.Language.Evaluate.CalculusElement;

/**
 *
 * @author arm
 */
public class IteratorElements {
    
    
    String element;
    IteratorIndex it;
    
    //itera uma string  atraves dos operadores e destes simbolos
    private  static String OTHERSEPARATORS = "(),{}";
    
    public IteratorElements(String code) {
        it = new IteratorIndex(CodeLine.GetNormalized(code));
        next();
    }
    
    //-------------------------------------------------------------------
    public boolean hasMoreElements(){
        return element.length() > 0;
    }
    
    private boolean isSeparator(String str){
        return OTHERSEPARATORS.indexOf(str) != -1 ||
                CalculusElement.IsOperator(str);
    }
//-------------------------------------------------------------------
    public void next() {
        element = it.current();
        it.next();
        
        if(! isSeparator(element) ) {
        //adicionar até ao proximo separador
            while(  it.hasMoreElements() && ! isSeparator(it.current()) ){
                element += " " + it.current();
                it.next();
            }
        }
    }
    
    
    /**
     * retorna o elemento corrente
     * @return retorna o elemento corrente
     */
    public String current(){
        return element;
    }
    
    
    public static void main(String args[]){
        System.out.println("ITERATOR");
        //String str = " , -i <= i [ j* 2 + 5]    [6 * k[78]]  - -3 * seno ( 23 * 45 , potencia (2,4) ) * 5 , 5";
        //String str = " escrever seno( i[k*2+4] ) ";
        String str = "((-2 + -3)*-5/valor[123*345][23*2],23*5)";
        //String str = "3--5 ";
        str = CodeLine.GetNormalized(str);
        System.out.println("STR:<" + str + ">");
        IteratorElements it = new IteratorElements(str);
        
        while( it.hasMoreElements()){
            System.out.println(it.current());
            it.next();
        }
    }
}
