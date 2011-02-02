/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*/*
/*
 * ExpandDefinirArray.java
 *
 * Created on 15 de Setembro de 2006, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.IteratorArray;
import Portugol.Language.Utils.IteratorCodeParams;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import java.util.Vector;

/**
 *
 * @author to
 */
public class ExpandDefinirArray {
    
    public static NodeInstruction Define(String alter , String type , String name , String value, NodeInstruction node, Vector memory)
    throws LanguageException{
        int pCut = name.indexOf("[");
        String indexes = name.substring(pCut).trim();
        name = name.substring(0,pCut-1).trim();
        
         
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
        
        
        VerifyIndex(indexes, node,memory);
        VerifyValues(type, value, node,memory);
        
        //normalizar o texto da expressão
        String text = alter +" "+type +" "+ name + " " + indexes + "  <- "+ value;
        // fazer um novo no
        NodeInstruction  newNode = new NodeInstruction(node);
        newNode.SetText(text);
        Variable.defineVAR(newNode,memory);
        return newNode;
        
    }
    
    private static void VerifyIndex(String  indexDefs,NodeInstruction node, Vector memory) throws LanguageException {
        IteratorArray iter = new IteratorArray(indexDefs);
        while( iter.hasMoreElements()){
            String value = iter.getNext();
            
            //Avaliar a expressão
            // se não for possivel avaliar provoca erro
            String result;
            try {
                result = Expression.Evaluate(value,memory);
            } catch( Exception e){
                throw new  LanguageException(
                        node.GetCharNum(), node.GetText() ,
                        e.toString(),
                        "Verifique a expressão <" +  value + ">");
            }
            if( !Values.IsInteger( result) )
                throw new LanguageException(
                        node.GetCharNum(), node.GetText() ,
                        value + " = " + result + " não é uma variavel inteira",
                        "redefina o valor do index");
            
            if( Integer.parseInt(result) <= 0 )
                  throw new LanguageException(
                        node.GetCharNum(), node.GetText() ,
                        value + " = " + result + " não é um index válido",
                        "redefina o valor do index");
        }
        
    }
    
    private static void VerifyValues(String type, String  values,NodeInstruction node, Vector memory) throws LanguageException {
        IteratorCodeParams iter = new IteratorCodeParams(values , ",{}");
        int index =0;
        while( iter.hasMoreElements()){
            String value = iter.current();
            //Avaliar a expressão
            // se não for possivel avaliar provoca erro
            String result;
            try {
                result = Expression.EvaluateByDefaults(value,memory);
            } catch( Exception e){
                throw new  LanguageException(
                        node.GetCharNum(), node.GetText() ,
                        e.toString(),
                        "Verifique a expressão <" +  value + ">");
            }
            
            //verificar se o resultado da expressão e compativel com a variavel
            if( !Symbol.IsCompatible( Symbol.getType(type), result) )
                throw new  LanguageException(
                        "O valor <" + result + "> não é permitido para a variavel " + type ,
                        " verifique a expressão :" + value);
            
            iter.next();
        }
    }
    
}
