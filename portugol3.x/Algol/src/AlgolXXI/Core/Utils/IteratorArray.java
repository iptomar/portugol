/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * ArrayTokenizer.java
 *
 * Created on 2 de Novembro de 2005, 0:30
 *
 */

package AlgolXXI.Core.Utils;

import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author Ant�nio Manuel Rodrigues MAnso
 */
public class IteratorArray {
    
    /**
     * Creates a new instance of ArrayTokenizer
     */
    Vector elements;
    //tudo aquilo que est� para al�m do ]
    String tail="";
    public IteratorArray(String array){
        elements = new Vector();
        BuildElements(array);
    }
    
    public boolean hasMoreElements(){
        return !elements.isEmpty();
    }
    public Vector getDimensions(){
        return elements;
    }
    public String getNext(){
        return (String) elements.remove(0);
    }
    void BuildElements(String array){
        Stack s= new Stack();
        String elem="";
        for(int index=0 ; index < array.length() ; index++){
            char ch = array.charAt(index);
            if(ch=='['){
                // nome do array
                if(s.empty() && elem.trim().length()>0){
                    elements.add(elem.trim());
                    elem="";
                }
                //retirar o primeiro [
                if(!s.isEmpty())
                    elem +="[";
                
                s.add(""+ch);
                
                
            } else if(ch==']'){
                s.pop();
                if(s.empty()){
                    elements.add(elem.trim());
                    elem="";
                }
                //retirar o ultimo ]
                if(!s.isEmpty())
                    elem +="]";
                
            } else
                elem += ch;
        }
        
        tail = elem.trim();
        
    }
    public String getTail(){
        return tail;
    }
    
     public int getNumElements(){
       return elements.size();
    }  
     
     public String getUnprocessed(){
         StringBuffer txt =new StringBuffer();
         for( int i=0; i < elements.size(); i++){
            txt.append("[" + elements.get(i).toString()+"]");
         }
         txt.append(tail);
         return txt.toString();
     }
   
}
