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

package Portugol.Language.Make;

import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Utils.LanguageException;
import java.util.Vector;

/**
 * expande o ciclo repete
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
public class ExpandRepeteAte {
    
   
    
//------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                                    ---------------------
//------------    I N S T R U Ç Ã O    F A Z  E N Q U A N T O     ---------------------
//------------                                                     --------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * expande o ciclo
     * @param repeatNode nodo de inicio
     * @param level nivel
     * @param memory vector de memoria
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static void ExpandRepeat(NodeInstruction repeatNode,NodeInstruction endNode,int level, Vector memory)throws LanguageException{
        String REPETE = Keyword.GetTextKey( Keyword.REPETE);
        String ATE = Keyword.GetTextKey( Keyword.ATE);
        //marcar o no como FAZ
        repeatNode.SetType(Keyword.REPETE);
        repeatNode.SetLevel(level);
        
        NodeInstruction tmp = repeatNode.GetNext();
        //procurar o fim do ciclo
        // while(tmp.GetType() != Keyword.ATE){
        while(!tmp.equals(endNode)){
            tmp.SetLevel(level+1);
            tmp = tmp.GetNext();
        }
        //fim do REPETE
        NodeInstruction endRepeatNode = tmp;
        //fazer o no do ATE
        String  condic = (endRepeatNode.GetText().trim()).substring(ATE.length()).trim();
        //verificar a condição               
        if( Expression.TypeExpression(condic,memory)!= Symbol.LOGICO)
            throw new LanguageException(
                    endRepeatNode.GetCharNum(), endRepeatNode.GetText(),
                    "\"" + condic + "\" não é uma condição válida" ,
                    "verifique se a condição está bem escrita");
                
        //alterar o texto para a condição
        endRepeatNode.SetText(condic);
        //tipo de no do tipo ATE
        endRepeatNode.SetType(Keyword.ATE);
        //nivel do no
        endRepeatNode.SetLevel(level);
        //se for false volta para cima
        endRepeatNode.SetIfFalse(repeatNode);
        //se for verdadeiro continua
        endRepeatNode.SetIfTrue(endRepeatNode.GetNext());
        
        //ligar o REPETE á Condição
        // utilizo esta ligação para escrever (toString)
        repeatNode.SetIfTrue(endRepeatNode.GetNext());
        repeatNode.SetIfFalse(endRepeatNode.GetNext());
        
    }
    
    /**
     * calcula as posições visuais
     * @param begin nodo de inicio do ciclo
     * @param Y Y do primeiro nodo
     * @param X X do primeiro nodo
     */
     public static void CalculatePositions( NodeInstruction begin ,double Y, double X ){
        double PY ,  PX ;
        PX = 0.5/(begin.level + 1.0);
        
        begin.SetPositionY(Y);
        begin.SetPositionX(X);
        NodeInstruction tmp = begin.GetNext();
        //fazer o  if
        PY = Y+1;        
        while(tmp.GetIfFalse() != begin){
            FluxogramVisual.ProcessNodePosition(tmp, PY , X - PX);
            PY = tmp.GetPositionY() + 1;
            tmp = tmp.GetNext();
        } 
        //colocar a decisão ao mesmo nível do faz
        tmp.SetPositionY(PY);
        tmp.SetPositionX(X);

        
    }
     
    /**
     * texto do ciclo
     * @param begin inicio do ciclo
     * @return texto do ciclo
     */
     public static  String toString(NodeInstruction begin){
        StringBuffer str = new StringBuffer();
        str.append(begin.toString()+"\n");
        NodeInstruction tmp = begin.GetNext();
         while(tmp.GetIfTrue() != begin){
             str.append(Fluxogram.GetCode(tmp));
            tmp = tmp.GetNext();
        }                    
        return str.toString();
    }
    
}
