/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
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
 * BeautifyCode.java
 *
 * Created on 31 de Outubro de 2005, 4:04
 *
 */

package Editor.GUI.CodeDocument;

import Portugol.Language.Make.NodeInstruction;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Utils.CodeLine;
import java.util.StringTokenizer;



/**
 *
 * @author António Manuel Rodrigues MAnso
 */
public class BeautifyCode {
    
    
    protected static  boolean isComented=false;
    public static  String TAB_SPACES = "    ";
    protected static  String comments="";
    
    
    public static String IndentCode(String program){
        isComented=false;
        StringTokenizer st = new StringTokenizer(program,"\n\r");
        NodeInstruction node=null;
        String instruction;
        String line="";
        StringBuffer newCode = new StringBuffer();
        int level=0;
        
        while (st.hasMoreTokens()) {
            line = st.nextToken();
            instruction = NormalizeString(line);
            //instruction = CodeLine.GetNormalized(line);
            node = new NodeInstruction(instruction,0,0);                        
            if(node.IsNodeClose() )    level--;
            //o case tem identação 2
            if(node.GetType() == Keyword.FIMESCOLHE) level--; 
            //o else vem para tras
            if(node.GetType() == Keyword.SENAO) level--;            
            //os casos tem as intruções à frente
            if(node.GetType() == Keyword.CASO) level--;             
            if(node.GetType() == Keyword.DEFEITO) level--;             
            
            line = IdentLine(node.GetText() + comments, level);
            
            
            newCode.append(line+"\n");
            //regressar a posicao boa
            if(node.GetType() == Keyword.SENAO) level++;              
            
            //os casos tem as intruções à frente
            if(node.GetType() == Keyword.CASO) level++;             
            if(node.GetType() == Keyword.DEFEITO) level++;             
            
            //nos que abrem um ciclo
            if(node.IsNodeOpen() )
                level ++;
            //o case tem identação 2
            if(node.GetType() == Keyword.ESCOLHE ) level++; 
        }
        newCode.append("\n");
        return newCode.toString();
        
    }
    
    private static String IdentLine(String line, int level){
        StringBuffer tmp = new StringBuffer();
        String tab = "";
        for(int i=0; i< level ; i++)
            tmp.append(TAB_SPACES);
        tmp.append(line.trim());
        return tmp.toString();
    }
    /**
     * Normaliza uma linha de código
     * retira-lhe os comentários e os espaços
     * @param str linha de código
     * @return linha de código normalizada
     */
    public static  String NormalizeString(String str){
        comments="";
        StringBuffer newStr = new StringBuffer();
        for(int index=0 ; index< str.length() ; index++ ) {
            switch (str.charAt(index)) {
                case '/':
                    // comentario "//"
                    if(index +1 < str.length() && str.charAt(index+1)=='/'){
                        comments = str.substring(index);
                        return newStr.toString().trim();
                    }
                    //inicio de um comentário /*
                    if(index +1 < str.length() && str.charAt(index+1)=='*'){
                        comments += "/";
                        isComented=true;
                    } else if( index >0 && str.charAt(index-1)=='*'){
                        comments += "/";
                        isComented=false;
                    }else
                        newStr.append(str.charAt(index));
                    break;
                default:
                    // se não for comentario
                    if( !isComented)
                        newStr.append(str.charAt(index));
                    else
                        comments += str.charAt(index);
            }
        }
        return newStr.toString().trim();
    }
}
