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
 * Expande o ciclo PARA
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
public class ExpandPara {
    
    //-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------    I N S T R U Ç Ã O    P A R A            ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * expande o ciclo
     * @param forNode nodo de inicio do ciclo
     * @param level nivel
     * @param memory vector de memória
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static void ExpandFOR(NodeInstruction forNode, int level,Vector memory)throws LanguageException{
        String PARA = Keyword.GetTextKey( Keyword.PARA);
        String DE = " " + Keyword.GetTextKey( Keyword.DE) + " ";
        String ATE = " " + Keyword.GetTextKey( Keyword.ATE) + " ";
        String PASSO = " " + Keyword.GetTextKey( Keyword.PASSO) + " ";
        
        String exp = forNode.GetText().toUpperCase().trim();
        //--------------------------------------------------------------------------------
        // -------------------- extrair a variavel----------------------------------
        //--------------------------------------------------------------------------------
        int endVar = exp.indexOf(DE);
        if(endVar < 0)
            throw  new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "ciclo PARA sem DE" ,
                    "escreva PARA variável <DE> inicio ATE fim PASSO p");
        
        String  variable= forNode.GetText().substring(PARA.length(),endVar).trim();
        //extrair a variavel da memória
        Symbol var = Variable.getVariable(variable, memory);
        if( var == null )
            throw new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "\"" + variable + "\" não está definida" ,
                    "defina a variavel numérica \"" + variable + "\" antes deste ciclo ");
        if(var.isConstant() )
            throw new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "\"" + variable + "\" é constante " ,
                    "Altere o tipo de \"" + variable + "\" para variavel ");
       
        if(!var.isNumber() )
            throw new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "\"" + variable + "\" não é numérica" ,
                    "Altere o tipo de \"" + variable + "\" para real ou inteiro ");
        //--------------------------------------------------------------------------------
        // -------------------- extrair o valor de inicialização --------------------------
        //--------------------------------------------------------------------------------
        int endValue = exp.indexOf(ATE);
        if(endValue < 0)
            throw  new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "ciclo PARA sem ATE" ,
                    "escreva PARA variável DE inicio <ATE> fim PASSO p");
        
        String  value= forNode.GetText().substring( endVar + DE.length() , endValue).trim();
        //--------------------------------------------------------------------------------
        // -------------------- extrair o Passo e LIMITE --------------------------
        //--------------------------------------------------------------------------------
        int begPasso = exp.indexOf(PASSO);
        String  limite;
        String passo = new String("1");
        //se não houver passo leio apenas o limite
        if(begPasso == -1){
            limite= forNode.GetText().substring(endValue + ATE.length() ).trim();
        }
        // ler limite e passo
        else{
            limite = forNode.GetText().substring(endValue + ATE.length() , begPasso).trim();
            passo = forNode.GetText().substring(begPasso + PASSO.length()).trim();
        }
        //--------------------------------------------------------------------------------
        //--------------------------- VERIFICAR se são expressões numéricas ---------------
        //--------------------------------------------------------------------------------
        int type = Expression.TypeExpression(value,memory);
        if( type != Symbol.INTEIRO && type != Symbol.REAL  )
            throw new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "\"" + value + "\" não é uma expressão numérica" ,
                    "verifique se a expressão está bem escrita");
        
        type = Expression.TypeExpression(passo,memory);
        if( type != Symbol.INTEIRO && type != Symbol.REAL  )
            throw new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "\"" + passo + "\" não é uma expressão numérica" ,
                    "verifique se a expressão está bem escrita");
        
        type = Expression.TypeExpression(limite,memory);
        if( type != Symbol.INTEIRO && type != Symbol.REAL  )
            throw new LanguageException(
                    forNode.GetCharNum(), forNode.GetText(),
                    "\"" + limite + "\" não é uma expressão numérica" ,
                    "verifique se a expressão está bem escrita");
        //--------------------------------------------------------------------------------
        //------------------ fazer do for o nó de inicializaçaõ --------------------------
        //--------------------------------------------------------------------------------
        forNode.SetText(variable + " " +  Keyword.ATRIBUI + " " + value);
        forNode.SetType( Keyword.CALCULAR);
        forNode.SetLevel(level);
        //--------------------------------------------------------------------------------
        //------------------ fazer o nó do actualizaçao da variavel ----------------------
        //--------------------------------------------------------------------------------
        NodeInstruction passNode = new NodeInstruction(passo,forNode.GetCharNum(), level);
        passNode.SetType(Keyword.PASSO);
        passNode.SetLevel(level);
        //--------------------------------------------------------------------------------
        //------------------ fazer o nó da condicao                 ----------------------
        //--------------------------------------------------------------------------------
        NodeInstruction condic = new NodeInstruction(variable + " <= " + limite  ,  forNode.GetCharNum(), level);
        condic.SetType(Keyword.ENQUANTO);
        condic.SetLevel(level);
        //--------------------------------------------------------------------------------
        //------------------ fazer as ligações                     ----------------------
        //--------------------------------------------------------------------------------        
        NodeInstruction tmp = forNode.GetNext();        
        //---------------- ligar o init e a condic -------------
        forNode.SetNext(passNode);
        passNode.SetNext(condic);
        //-------- ligar a condic e o bloco ------
        condic.SetIfTrue(tmp);
        while(tmp.GetNext().GetType() != Keyword.PROXIMO){
            tmp.SetLevel(level+1);
            tmp = tmp.GetNext();
        }
        //------- no antes do proximo ------
        tmp.SetLevel(level+1);
        //-------- fazer o no de actualização da var  a var -------
        NodeInstruction actualize = new NodeInstruction( variable + " " + Keyword.ATRIBUI  + "  " + variable + " + " + passo , forNode.GetCharNum(), level+1);
        actualize.SetLevel(level+1);
        actualize.SetType(Keyword.CALCULAR);
        //---------fim do for-------------------
        NodeInstruction endFor = tmp.GetNext();
        // ligar actualize a condic
        actualize.SetNext(condic);
        //--------------- ligar o corpo do ciclo a actualize -------
        //no caso de ser um enquanto o terminar ligar o falso
        if(tmp.GetIfFalse()!= null  && tmp.GetIfFalse().GetType() == Keyword.PROXIMO)
           tmp.SetIfFalse(actualize);        
        //no caso de ser um Repeat o terminar ligar o verdadeiro
        if(tmp.GetIfTrue()!= null  && tmp.GetIfTrue().GetType() == Keyword.PROXIMO)
           tmp.SetIfTrue(actualize);
        // ligar o next do tmp
        tmp.SetNext( actualize);
        //ligar o true de condic
        condic.SetIfFalse(endFor);
        //modificar o endFor para JOIN
        endFor.SetType(Keyword.CONECTOR);
        //nivel do endfor
        endFor.SetLevel(level);
        //instrução seguinte ao ciclo
        condic.SetNext(endFor);              
        //-------------------------
    }
 
    
}
