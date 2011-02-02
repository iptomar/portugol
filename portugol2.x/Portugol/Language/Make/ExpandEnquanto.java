/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/


package Portugol.Language.Make;

import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Utils.LanguageException;
import java.util.Vector;



/**
 * Expande o ciclo Enquanto
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
public class ExpandEnquanto {
    
    /**
     * Creates a new instance of ExpandEnquanto
     */
    public ExpandEnquanto() {
    }
    
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------    I N S T R U Ç Ã O    E N Q U A N T O     ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * expand o enquanto
     * @param whileNode nodo de inicio do ciclo
     * @param level nível
     * @param memory vector de memória
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static void ExpandWHILE(NodeInstruction whileNode, int level, Vector memory) throws LanguageException{
        String ENQUANTO = Keyword.GetTextKey( Keyword.ENQUANTO);
        String FIMENQUANTO = Keyword.GetTextKey( Keyword.FIMENQUANTO);
        
        String exp = whileNode.GetText().toUpperCase().trim();
        int endCondic = exp.indexOf("FAZ");
        if(endCondic < 0)
            throw  new LanguageException(
                    whileNode.GetCharNum(),
                    whileNode.GetText(),
                    "ciclo ENQUANTO sem FAZ" ,
                    "escreva ENQUANTO condição <FAZ>");
        
        //enquanto = 8 caracteres
        String  condic= whileNode.GetText().substring(ENQUANTO.length(),endCondic).trim();
        
        if( Expression.TypeExpression(condic,memory)!= Symbol.LOGICO)
            throw new LanguageException(
                    whileNode.GetCharNum(), whileNode.GetText(),
                    "\"" + endCondic + "\" não é uma condição válida" ,
                    "verifique se a condição está bem escrita");
        
        
        //fazer do for node a inicializacao
        whileNode.SetText(condic);
        whileNode.SetType(Keyword.ENQUANTO);
        whileNode.SetLevel(level);
        NodeInstruction tmp = whileNode.GetNext();
        // ligar a condic e o bloco
        whileNode.SetIfTrue(tmp);
        
        while(tmp.GetNext().GetType() != Keyword.FIMENQUANTO){
            tmp.SetLevel(level+1);
            tmp = tmp.GetNext();
        }
        //o último antes do fim-enquanto
        tmp.SetLevel(level+1);
        //fim do while
        NodeInstruction endWhile = tmp.GetNext();
        //fazer o ciclo
        tmp.SetNext(whileNode);
        //ligar o false de condic
        whileNode.SetIfFalse(endWhile);
        //modificar o endWhile para JOIN
        endWhile.SetType(Keyword.CONECTOR);
        //instrução seguinte ao ciclo
        endWhile.SetLevel(level);
        whileNode.SetNext(endWhile);
    }
    
    /**
     * texto com as intruções do ciclo
     * @param begin inicio do ciclo
     * @return texto com as intruções do ciclo
     */
    public static  String toString(NodeInstruction begin){
        StringBuffer str = new StringBuffer();
        str.append(begin.toString()+"\n");
        NodeInstruction tmp = begin.GetIfTrue();
        while(tmp !=null && tmp.GetType() != Keyword.ENQUANTO){
            str.append(Fluxogram.GetCode(tmp));
            tmp = tmp.GetNext();
        }
        tmp = begin.GetIfFalse();
        str.append(tmp.toString()+"\n");
        
        
        return str.toString();
    }
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------    
    /**
     * calcular as posições do fluxograma visual
     * @param begin nodo de inicio
     * @param Y posição Y do inicio
     * @param X posição X do inicio
     */
    public static void CalculatePositions( NodeInstruction begin ,double Y, double X ){
        double PY ,  PX ;
        PX = 0.5/(begin.level + 1.0);
        
        begin.SetPositionY(Y);
        begin.SetPositionX(X);
        NodeInstruction tmp = begin.GetIfTrue();
        //fazer o  if
        PY = Y+1;
        NodeInstruction end = begin;
        while(tmp != end){
            FluxogramVisual.ProcessNodePosition(tmp, PY , X + PX);
            PY = tmp.GetPositionY() + 1;
            tmp = tmp.GetNext();
        }
        //processar o fim do enquanto
        tmp = begin.GetIfFalse();
        tmp.SetPositionY(PY);
        tmp.SetPositionX(X);
        
    }
    
}

