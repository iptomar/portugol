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
 
/**
 *
 * @author _arm
 */
// Histórico
// 10 - 11 - suporte para operadores unários  ( # equivalente a MENOS )


package Portugol.Language.Make;

import Portugol.Language.Parse.Keyword;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.LanguageException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Expande a instrução de leitura
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
public class ExpandLer {
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------    V A R I A V E I S                        ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Expande  a leitura de  variveis. uma linha de código pode
     * ler várias variáveis
     * 
     * retorna o último nó definido para se poder continuar a
     * expandir o fluxograma
     * @return último nodo definido
     * @param node nó da linha de código
     * @param level nivel
     * @param memory vector da memória
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static NodeInstruction ExpandRead(NodeInstruction node , int level, Vector memory)
    throws LanguageException{
        String LER = Keyword.GetTextKey( Keyword.LER);
         // uma instrução pode desgenerar em muitos nós
        // com a leitura de várias variaveis ao mesmo tempo
        NodeInstruction prevNode = null;                   // nó anterior
        //lugar para onde aponta node
        NodeInstruction originalNextNode = node.GetNext();
        //intrução
        String instruction = node.GetText().trim();
        //verificar se a primeira palavra é ler
        String ler = instruction.substring(0, LER.length());
        if( ! ler.equalsIgnoreCase(LER)){
            throw new LanguageException(
                            node.GetCharNum(), node.GetText(),
                            " Esperava um LER",
                            " VERIFICAR a Classificação do NO");
        }
        instruction = instruction.substring(LER.length()).trim();
        //dividir as variaveis
        StringTokenizer tok = new StringTokenizer(instruction,",");
        while( tok.hasMoreElements()){
          String variavel = ((String)tok.nextElement()).trim();
          Symbol v = Variable.getVariable(variavel,memory);
        if( v== null)
            throw  new LanguageException(
                    node.GetCharNum(),
                    node.GetText(),
                    "a variável \"" + variavel +"\" não está definida" ,
                    "defina a variavel \"" + variavel + "\" antes de a ler");

          if(v.isConstant())
              throw  new LanguageException(
                    node.GetCharNum(),
                    node.GetText(),
                     variavel +" é uma constante e não pode alterar o seu valor" ,
                    "defina  \"" + variavel + "\" como variavel ");
           //texto da definição da variável
          String text = "LER " + variavel;              
          if( prevNode == null){
                node.SetText(text);
                prevNode = node;
            } else{
                NodeInstruction newNode = new NodeInstruction(node);
                newNode.SetText(text);
                newNode.SetPositionY(node.GetPositionY()+1);
                prevNode.SetNext(newNode);
                prevNode = newNode;
            }          
        }        
        //fazer a ligação com o no da intrução seguinte
        originalNextNode.SetPositionY(prevNode.GetPositionY()+1);
        prevNode.SetNext(originalNextNode);
        //retornar o ultimo
        return prevNode;
//------------------------------------------------------------------------------
    }    
}
