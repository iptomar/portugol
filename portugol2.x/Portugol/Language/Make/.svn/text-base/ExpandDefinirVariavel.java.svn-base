/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*/*
 * ExpandDefinirVariavel.java
 *
 * Created on 15 de Setembro de 2006, 18:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.LanguageException;
import java.util.Vector;

/**
 *
 * @author to
 */
public class ExpandDefinirVariavel {
  
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    public static NodeInstruction Define(String alter , String type , String name , String value, NodeInstruction node, Vector memory)
    throws LanguageException{
    
        //verificar se é um nome aceitavel
        if( !Variable.isNameAcceptable(name))
            throw new LanguageException(
                    node.GetCharNum(), node.GetText() ,
                    " simbolo " + name + " inválido :" + Variable.getErrorName(name),
                    "altere o nome do símbolo " + name);
    
        //verificar se a variavel ja esta definida
        Symbol tmpVar = Variable.getVariable(name,memory);
        if( tmpVar != null && tmpVar.getLevel() == node.GetLevel())
            throw new  LanguageException(
                    node.GetCharNum(), node.GetText() ,
                    "O simbolo <" + name + "> já está definido na memoria",
                    " Mude o o nome do simbolo <" + name + "> .");
        
        //verificar a expressão
        if ( ! Expression.IsExpression(value, memory))
            throw new  LanguageException(
                    node.GetCharNum(), node.GetText() ,
                    "O Simbolo <"+ Expression.ErrorExpression(value,memory) + "> não está definido",
                    "defina o simbolo  antes de o usar");
        
        //Avaliar a expressão
        // se não for possivel avaliar provoca erro
        String memValue;
        try {
            memValue = Expression.EvaluateByDefaults(value,memory);
        } catch( Exception e){
            throw new  LanguageException(
                    node.GetCharNum(), node.GetText() ,
                    e.toString(),
                    "Verifique a expressão <" +  value + ">");
        }
        
        //verificar se o resultado da expressão e compativel com a variavel
        if( !Symbol.IsCompatible( Symbol.getType(type), memValue ) )
            throw new  LanguageException(
                    "O valor <" + value + "> não é permitido para a variavel " + type ,
                    " verifique a expressão :" + value);
        
        //normalizar o texto da expressão
        String text = alter +" "+type +" "+ name + " <- "+ value;
        // fazer um novo no
        NodeInstruction  newNode = new NodeInstruction(node);
        newNode.SetText(text);
        Variable.defineVAR(newNode,memory);
        return newNode;
    }
    
}
