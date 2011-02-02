/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * ExpandFluxogram.java
 *
 * Created on 14 de Junho de 2006, 17:37
 * 
 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.IteratorCodeParams;
import Portugol.Language.Utils.IteratorExpression;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import java.util.Stack;
import java.util.Vector;

/**
 * expande os ciclos de um fluxograma
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
public class ExpandFluxogram {
    
   //--------------------------------------------------------------------
//--------------------------------------------------------------------
//----          EXPAND NODES                                     -----
//--------------------------------------------------------------------
//--------------------------------------------------------------------
    /**
     * Expande os nos que são estruturas SE ENQUANTO PARA REPETE , etc.
     * @param start no de inicio do fluxograma
     * @param memory vector de memoria
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static void ExpandNodes(NodeInstruction start , Vector memory) throws LanguageException{
        
        NodeInstruction pt = start;
        Stack stack = new Stack();
        while( pt != null){
            //fazer as variaveis            
            if( pt.GetType() ==  Keyword.DEFINIR) {
                pt = ExpandDefinirSimbol.ExpandVariable(pt,stack.size(),memory);
            }
            //verificar o calculo
            else if( pt.GetType() ==  Keyword.CALCULAR) {
                VerifyCalculate(pt,memory);
            }
            
            //fazer a leitura das  variaveis
            else if( pt.GetType() ==  Keyword.LER) {
                pt = ExpandLer.ExpandRead(pt,stack.size(),memory);
            }
            //verificar a escrita
            else if( pt.GetType() ==  Keyword.ESCREVER) {
                VerifyWRITE(pt,memory);
            }
            
            
            // nós dos blocos vão para a pilha
            else if( pt.GetType() ==  Keyword.SE ||
                    pt.GetType() ==  Keyword.PARA ||
                    pt.GetType() ==  Keyword.ENQUANTO ||
                    pt.GetType() ==  Keyword.FAZ ||
                    pt.GetType() ==  Keyword.ESCOLHE ||
                    pt.GetType() ==  Keyword.REPETE ){
                stack.push(pt);
            }
             //limpar variaveis detro do escolhe
            // o senao está ao mesmo nivel das variaveis
            else if(pt.GetType() ==  Keyword.SENAO ||pt.GetType() ==  Keyword.CASO || pt.GetType() ==  Keyword.DEFEITO) {
                Fluxogram.cleanMemory( stack.size() ,memory);
            }
            // --------------------- Fazer os IFS ----------------------------
            else if(pt.GetType() == Keyword.FIMSE){
                if( stack.isEmpty())
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "Encontrei o FIMSE sem haver um  SE aberto ",
                            "coloque um SE antes desta linha");
                // retirar o IF da pilha
                NodeInstruction n = (NodeInstruction) stack.pop();
                //ERRO
                if(n.GetType() != Keyword.SE)
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "o ciclo :\"" +n.GetText()+"\" está aberto",
                            " escreva o fim do ciclo e não   \"" + pt.GetText()+"\"");
                
                Fluxogram.cleanMemory( stack.size()+1,memory);
                ExpandSe.ExpandIF(n, stack.size(), memory);
            }
            // --------------------- Fazer os WHILE ----------------------------
            else if(pt.GetType() == Keyword.FIMENQUANTO){
                if( stack.isEmpty())
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "Encontrei o FIMENQUANTO sem haver um ciclo ENQUANTO aberto ",
                            "coloque um ENQUANTO antes desta linha");
                
                NodeInstruction n = (NodeInstruction) stack.pop();
                if(n.GetType() != Keyword.ENQUANTO)
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "o ciclo :\"" +n.GetText()+"\" está aberto",
                            " escreva o fim do ciclo e não   \"" + pt.GetText()+"\"");
                
                
                ExpandEnquanto.ExpandWHILE(n, stack.size(),memory);
                
            }
            //------------------------- fazer os FAZ ENQUANTO-------------------------
            else if(pt.GetType() == Keyword.FAZENQUANTO){
                if( stack.isEmpty())
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "Encontrei o ENQUANTO sem haver um ciclo FAZ aberto ",
                            "coloque um FAZ antes desta linha");
                //-------------------- retirar o FAZ da pilha  --------------
                NodeInstruction n = (NodeInstruction) stack.pop();
                if(n.GetType() != Keyword.FAZ)
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "o ciclo :\"" +n.GetText()+"\" está aberto",
                            " escreva o fim do ciclo e não   \"" + pt.GetText()+"\"");
                
               
                ExpandFazEnquanto.ExpandDoWhile(n,pt, stack.size(), memory);
                Fluxogram.cleanMemory( stack.size() +1 ,memory);
            }
            //------------------------- fazer os REPETE -------------------------
            else if(pt.GetType() == Keyword.ATE){
                if( stack.isEmpty())
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "Encontrei o ATE sem haver um ciclo REPETE aberto ",
                            "coloque um REPETE antes desta linha");
                //-------------------- retirar o Repete da pilha  --------------
                NodeInstruction n = (NodeInstruction) stack.pop();
                if(n.GetType() != Keyword.REPETE)
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "o ciclo :\"" +n.GetText()+"\" está aberto",
                            " escreva o fim do ciclo e não   \"" + pt.GetText()+"\"");
                
               
                ExpandRepeteAte.ExpandRepeat(n,pt, stack.size(), memory);
                Fluxogram.cleanMemory( stack.size() +1 ,memory);
            }
             //--------------------------  Fazer os FOR -----------------------
            else if(pt.GetType() == Keyword.PROXIMO){
                if( stack.isEmpty())
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "Encontrei o PROXIMO sem haver um ciclo PARA aberto ",
                            "coloque um PARA antes desta linha");
                
                //-------retirar o PARA da pilha ---------
                NodeInstruction n = (NodeInstruction) stack.pop();
                if(n.GetType() != Keyword.PARA)
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "o ciclo :\"" +n.GetText()+"\" está aberto",
                            " escreva o fim do ciclo e não   \"" + pt.GetText()+"\"");
                
                
                ExpandPara.ExpandFOR(n, stack.size(),memory);
                Fluxogram.cleanMemory( stack.size() +1 ,memory);
            }
               // --------------------- Fazer os CASOS ----------------------------
            else if(pt.GetType() == Keyword.FIMESCOLHE){
                if( stack.isEmpty())
                    throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "Encontrei o FIM ESCOLHE sem haver um  ESCOLHE aberto ",
                            "coloque um FAZ antes desta linha");
                // retirar o IF da pilha
                NodeInstruction n = (NodeInstruction) stack.pop();
                //ERRO
                if(n.GetType() != Keyword.ESCOLHE)
                        throw new LanguageException(
                            pt.GetCharNum(), pt.GetText(),
                            "o ciclo :\"" +n.GetText()+"\" está aberto",
                            " escreva o fim do ciclo e não   \"" + pt.GetText()+"\"");
                //FAZER O ESCOLHE                
                pt = ExpandEscolhe.ExpandSWITCH(n, stack.size(), memory); 
                Fluxogram.cleanMemory( stack.size() +1 ,memory);
            }
            
            pt=pt.GetNext();
            
        }// pt != null
        //  - - -- ciclos não fechados  . . . .
        if(! stack.isEmpty()){
            NodeInstruction n = (NodeInstruction) stack.pop();
            throw new LanguageException(
                    n.GetCharNum(), n.GetText(),
                    "Este ciclo nao esta fechado",
                    "Feche este ciclo");
        }
    }
    
 //-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------         E S C R E V E R                     ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------    
    /**
     * verifica se a instrução de escrita está bem contruida
     * @param node nodo da instrução
     * @param memory vector de memoria
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    protected static void VerifyWRITE(NodeInstruction node, Vector memory) throws LanguageException{
        String ESCREVER = Keyword.GetTextKey( Keyword.ESCREVER);
        String instrucao = node.GetText().trim();
        //verificar se a primeira palavra é "escrever
        String escr = instrucao.substring(0, ESCREVER.length());
        if( ! escr.equalsIgnoreCase(ESCREVER)){
            throw new LanguageException(
                    node.GetCharNum(), node.GetText(),
                    " Esperava um " + ESCREVER ,
                    " VERIFICAR a Classificação do NO");
        }
        instrucao = instrucao.substring(ESCREVER.length() ).trim();
        //dividir as variaveis
        IteratorCodeParams tok = new IteratorCodeParams(instrucao,",");
        while( tok.hasMoreElements()){
            String elem = tok.current();
            tok.next();
            
            if( ! Values.IsString(elem) && !Expression.IsExpression(elem , memory) )
                throw  new LanguageException(
                        node.GetCharNum(),
                        node.GetText(),
                        "o elemento \"" + elem +"\" não é uma string nem uma expressão" ,
                        " coloque aspas no elemento ou verifique a expressão");
        }
    }
      //-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------        CALCULATE                            ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------    
    /**
     * verifica se a instrução de escrita está bem contruida
     * @param node nodo da instrução
     * @param memory vector de memoria
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static void VerifyCalculate(NodeInstruction node, Vector memory) throws LanguageException{
        String str = node.GetText();
        int pos = str.indexOf(Keyword.ATRIBUI);
        String name = str.substring(0, pos).trim();
        String elem = str.substring(pos + Keyword.ATRIBUI.length() ).trim();        
        Symbol var = Variable.getVariable(name,memory);
        if( var == null)
          throw new LanguageException(
                    node.GetCharNum(), str,
                    " o símbolo \"" + name + "\" não está definido na memória",
                    " defina o símbolo ante de o usar");
         elem = NormalizeMinus(elem,memory);
        String value = Expression.EvaluateByDefaults(elem,memory);
        
        //if ( !Symbol.IsCompatible( var.getType(), value))
        if ( !Values.IsNumber(value))
          throw new LanguageException(
                    node.GetCharNum(), str,
                    " o valor da expressão :" + elem,
                    " não é compativel com tipo da variavel:" + var.getStringType());
        node.SetText(name + " " + Keyword.ATRIBUI + " " + elem );
    }
    
    public static String NormalizeMinus(String str, Vector memory){
        StringBuffer newExpr = new StringBuffer();
        IteratorExpression tok = new IteratorExpression(str);
        Symbol var;
        String elem;
        //se começar pelo sinal menos
        if( tok.current().equals("-(")){
            newExpr.append("-1 * (");
            tok.next();                    
        }
            
        while( tok.hasMoreElements()){
            elem = tok.current(); tok.next();
            newExpr.append(GetSafeElement(elem , memory) );
        }
        return newExpr.toString().trim();        
    }
    
    public static String GetSafeElement(String elem, Vector memory ){
        Symbol var;
        //------------------------- sinal -  -------------
        if( elem.charAt(0) == '-'){
            //--operador - 
            if( elem.length() == 1 ) return elem + " ";
            //---resto
            String resto = elem.substring(1);
            var = Variable.getVariable(resto,memory);
            //valor negativo
            if( var == null )
                return elem + " ";
            // se for uma variavel com sinal -
            else
                return " ( " +  resto + " * -1 ) ";
        }
                return elem + " ";            
    }
}
