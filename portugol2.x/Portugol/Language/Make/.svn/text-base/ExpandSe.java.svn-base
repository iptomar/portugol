/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
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
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.LanguageException;
import java.util.Vector;

/**
 * Expande o ciclo se
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
public class ExpandSe {
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------    I N S T R U Ç Ã O    S E                ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    
    /**
     * expande o ciclo
     * @param begin nodo de inicip
     * @param level nivel
     * @param memory vector de memoria
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static void ExpandIF(NodeInstruction begin , int level , Vector memory)
    throws LanguageException{
    //string das instruções
        String SE = Keyword.GetTextKey( Keyword.SE);
        String ENTAO = Keyword.GetTextKey( Keyword.ENTAO);
        String SENAO = Keyword.GetTextKey( Keyword.SENAO);
        
        String exp = begin.GetText().toUpperCase();
        int endExp = exp.indexOf(ENTAO);
        if(endExp < 0)
            throw new LanguageException(
                    begin.GetCharNum(), begin.GetText(),
                    "SE sem ENTAO" ,
                    "coloque o ENTAO no final da instrução");
        
        // SE ocupa dois caracteres
        String  condicao = begin.GetText().substring(SE.length(),endExp).trim();
        // se nao for uma variavel lógica
        Symbol var = Variable.getVariable(condicao, memory);
        if( (var == null || var.getType() != Symbol.LOGICO ) &&
                // uma expressao logica
                Expression.TypeExpression(condicao,memory)!= Symbol.LOGICO)
            throw new LanguageException(
                    begin.GetCharNum(), begin.GetText(),
                    "\"" + condicao + "\" não é uma condição válida" ,
                    "verifique se a condição está bem escrita");
        
        begin.SetLevel(level);
        // retirar o SE e o ENTAO i faca apenas a condiçao
        begin.SetText(condicao);
        // defenir o tipo
        begin.SetType(Keyword.SE);
        
        NodeInstruction tmp = begin.GetNext();
        NodeInstruction endIF= tmp ; // nodo onde termina o if
        // incrementar o nivel
        tmp.SetLevel(level+1);
        //ligar o ponteiro do verdadeiro
        begin.SetIfTrue(tmp);        
        //-----------------------------------------------------
        //ponteiro para o final do if verdade
        endIF = tmp;
        while(tmp.GetType() != Keyword.FIMSE ) {
            //local onde termina o if (para ligar ao conector de fluxo)
            endIF = tmp;
            tmp.SetLevel(level+1);
            if(tmp.GetNext().GetType()== Keyword.SENAO){
                //senão
                tmp = tmp.GetNext();            
                break;
            }

            tmp = tmp.GetNext();
        }
        //se naõ tiver else
        if(tmp.GetType() ==  Keyword.FIMSE){
            begin.SetIfFalse(tmp);
        } else{
            tmp = tmp.GetNext();
            //ligar o ponteiro de falso
            begin.SetIfFalse(tmp);
            while(tmp.GetType() != Keyword.FIMSE){
                tmp.SetLevel(level+1);
                tmp = tmp.GetNext();
            }
        }
        // ligar o if
        endIF.SetNext(tmp);       
        //------------------------------------------------
        tmp.SetLevel(level);
        tmp.SetType(Keyword.CONECTOR);
                       
        //ligação directa do se ao fimse
        begin.SetNext(tmp);
        
        /*
        System.out.println("--------------------------------------");
        System.out.println(toString(begin));
        System.out.println("--------------------------------------");        
         */
    }
//-------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------- 
    /**
     * texto do ciclo
     * @param begin nodo de inicio
     * @return texto do ciclo
     */
    public static  String toString(NodeInstruction begin){
        StringBuffer str = new StringBuffer();
        str.append(begin.toString()+"\n");
        NodeInstruction tmp = begin.GetIfTrue();
        NodeInstruction end = begin.GetNext();
        
        while( tmp != end ){
            str.append(Fluxogram.GetCode(tmp));
            tmp = tmp.GetNext();
        }
        str.append("\n");
        tmp = begin.GetIfFalse();
        while(tmp != end){
            str.append(Fluxogram.GetCode(tmp));
            tmp = tmp.GetNext();
        }
       
         str.append(end.toString()+ "\n");     
         return str.toString();
    }
 //-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
   
    /**
     * calcula as posicoes visuais
     * @param begin nodo de inicio
     * @param Y Y do primeiro nodo
     * @param X X do primeiro nodo
     */
    public static void CalculatePositions( NodeInstruction begin ,double Y, double X ){
        double PY ,  PX ;
        PX = 0.5/(begin.level + 1.0);       
        begin.SetPositionY(Y);
        begin.SetPositionX(X);
        NodeInstruction tmp = begin.GetIfTrue();
        //fazer o  if
        PY = Y+1;
        NodeInstruction end = begin.GetNext();
        while(tmp != end){            
            FluxogramVisual.ProcessNodePosition(tmp, PY , X + PX);
            PY = tmp.GetPositionY() + 1;
            tmp = tmp.GetNext();
        }
        // posicao Y do conector
         end.SetPositionY(PY); 
        // fazer o else
        tmp = begin.GetIfFalse();
        PY = Y+1;
        while(tmp != end){
            FluxogramVisual.ProcessNodePosition(tmp,PY , X - PX);
            PY = tmp.GetPositionY() + 1;
            tmp = tmp.GetNext();
        }
        //conector ( calcular o máximo dos ys do if e do else
        if( end.GetPositionY() < PY)
            end.SetPositionY(PY);
        //posicao X do conector
        end.SetPositionX(X);
                
    }
    
}
