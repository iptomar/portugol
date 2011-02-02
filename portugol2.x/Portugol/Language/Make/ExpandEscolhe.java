/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * ExpandEscolhe.java
 *
 * Created on 14 de Junho de 2006, 16:49

 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Utils.LanguageException;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * Expande a instrução escolhe
 * 
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
public class ExpandEscolhe {
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Expand o ciclo escolhe
     * @param switchNode nodo do escolhe
     * @param level nivel
     * @param memory vector de memória
     * @throws Portugol.Language.Utils.LanguageException erro
     * @return o último nodo do ciclo
     */
    public static NodeInstruction ExpandSWITCH(NodeInstruction switchNode, int level,Vector memory)
    throws LanguageException{
        //-----------------------------------------------------ESCOLHE --------
        String line = switchNode.GetText().trim();
        String ESCOLHE = Keyword.GetTextKey( Keyword.ESCOLHE);
        //palavras ESCOLHE
        String escolhe = line.substring(0, ESCOLHE.length());
        // expressão de controle
        String expressaoEscolhe = line.substring(ESCOLHE.length()).trim();
        if( ! escolhe.equalsIgnoreCase(ESCOLHE)){
            throw new LanguageException(
                    switchNode.GetCharNum(), switchNode.GetText(),
                    " Esperava um " + ESCOLHE,
                    " VERIFICAR a Classificação do NO");
        }
        
        if( ! Expression.IsExpression(expressaoEscolhe,memory) )
            throw new LanguageException(
                    switchNode.GetCharNum(), switchNode.GetText(),
                    expressaoEscolhe + " não é uma varivel nem uma expressão ",
                    " Verifique a expressão do " + ESCOLHE);
        
        //procurar o ultimo no do escolhe
        NodeInstruction end = switchNode.GetNext();
        while(end != null && end.GetType() != Keyword.FIMESCOLHE)
            end = end.GetNext();
        //se não houver fim do ciclo
        if( end== null )
            throw new LanguageException(
                    switchNode.GetCharNum(), switchNode.GetText(),
                    " não existe o FIM ESCOLHE que fecha esta estrutura ",
                    " Escreva um FIM ESCOLHE depois deste BLOCO");
        //----------------------------------- CASOS ----
        NodeInstruction tmp2,tmp1,oldDecision = null,  nodeAnt=null;
        tmp1 = switchNode.GetNext();
        //fazer todos os casos
         while(tmp1.GetType() != Keyword.FIMESCOLHE  && tmp1.GetType() != Keyword.DEFEITO ){
            // se for um caso expandir
            if(tmp1.GetType() == Keyword.CASO){
                //---------------------------------------Ligar o ultimo ao END
                if( nodeAnt != null ) //se não fo o primeiro caso
                {
                    // anterior aponta para fim
                    nodeAnt.SetNext(end);
                    //se o falso apontar par o no TMP1, passa a apontar para fim
                    if(nodeAnt.GetIfFalse()!= null && nodeAnt.GetIfFalse() == tmp1)
                        nodeAnt.SetIfFalse(end);
                    //se o verdadeiro apontar par o no TMP1, passa a apontar para fim
                    if(nodeAnt.GetIfTrue()!= null && nodeAnt.GetIfTrue() == tmp1)
                        nodeAnt.SetIfTrue(end);
                    
                }
                tmp2 = ExpandCase(tmp1,level,memory,expressaoEscolhe ,end);
                //actualizar o ponteiro da condição anterior
                if( oldDecision == null)
                    switchNode.SetNext(tmp1);
                else
                    oldDecision.SetIfFalse(tmp1);
                //actualizar a condição anterior
                oldDecision = tmp1;
                // colocar o tmp1 no next
                tmp1 = tmp2;
            }
            //senão actualizar o nivel
            else{
                tmp1.IncrementLevel();
            }
            nodeAnt = tmp1;
            //passar para a frente
            tmp1 = tmp1.GetNext();
        }
        if( oldDecision == null)
            throw new LanguageException(
                    switchNode.GetCharNum(), switchNode.GetText(),
                    " não existe nenhum CASO nesta estrutura ",
                    " Escreva um CASO depois do Escolhe");
        
        //--------------------------- actualizar os ponteiro do ultimo caso        
            // anterior aponta para fim
            nodeAnt.SetNext(end);
            //se o falso apontar par o no TMP1, passa a apontar para fim
            if(nodeAnt.GetIfFalse()!= null && nodeAnt.GetIfFalse() == tmp1)
                nodeAnt.SetIfFalse(end);
            //se o verdadeiro apontar par o no TMP1, passa a apontar para fim
            if(nodeAnt.GetIfTrue()!= null && nodeAnt.GetIfTrue() == tmp1)
                nodeAnt.SetIfTrue(end);
            
        
        //------------------------------------------------------Fazer o default
        if( tmp1.GetType() == Keyword.DEFEITO){
            tmp1 = ExpandDefaultCase(tmp1,level,memory,expressaoEscolhe ,end);
            oldDecision.SetIfFalse(tmp1);
            while(tmp1.GetType() != Keyword.FIMESCOLHE){
                tmp1.IncrementLevel();
                tmp1 = tmp1.GetNext();
            }
        }
        //fazer um no de junção
        tmp1.SetType(Keyword.CONECTOR);
        //fim do escolhe
        return tmp1;
    } 
    
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    private static NodeInstruction ExpandDefaultCase(NodeInstruction defaultNode, int level,Vector memory,
            String expressaoEscolhe,NodeInstruction end)  throws LanguageException{
        String line = defaultNode.GetText().trim();
        //defeito tem 7 letras
        String strCaso = line.substring(0, 7);
        if( ! strCaso.equalsIgnoreCase("DEFEITO")){
            throw new LanguageException(
                    defaultNode.GetCharNum(), defaultNode.GetText(),
                    " Esperava um DEFEITO : intruções",
                    " ???? ");
        }
        int indexPontos = line.indexOf(':');
        if( indexPontos < 0 ){
            throw new LanguageException(
                    defaultNode.GetCharNum(), defaultNode.GetText(),
                    " Esperava um DEFEITO  \":\" intruções",
                    "coloque os dois pontos : ao DEFEITO");
        }
        //----------------------------------------------------FAZER A PRIMEIRA INSTRUÇAO
        //import buscar a intrução
        String instrucao = line.substring(indexPontos+1).trim();
        // no da instrução
        NodeInstruction nodeInstr;
        //fazer um novo no
        if(instrucao.length() >0 ){
            nodeInstr = new NodeInstruction(instrucao, defaultNode.GetCharNum(), level+1 );
        } else{
            nodeInstr = defaultNode.GetNext();
        }
        return nodeInstr;
    }
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------
    private static NodeInstruction ExpandCase(NodeInstruction caseNode, int level,Vector memory,
            String expressaoEscolhe,NodeInstruction end)  throws LanguageException{
        String line = caseNode.GetText().trim();
        
        //caso tem 4 letras
        String strCaso = line.substring(0, 4);
        if( ! strCaso.equalsIgnoreCase("CASO")){
            throw new LanguageException(
                    caseNode.GetCharNum(), caseNode.GetText(),
                    " Esperava um CASO [expressao] : intruções",
                    " a seguir a um ESCOLHE vem um CASO ");
        }
        int indexPontos = line.indexOf(':');
        if( indexPontos < 0 ){
            throw new LanguageException(
                    caseNode.GetCharNum(), caseNode.GetText(),
                    " Esperava um CASO [expressao] \":\" intruções",
                    "coloque os dois pontos : a seguir à expressão");
        }
        //----------------------------------------------------------------
        //-------    condiçoes multiplas no CASO exp, exp, exp : --------
        //----------------------------------------------------------------
        // expressão da condição (multipla)
        StringBuffer condicaoCaso = new StringBuffer();
        // expressão simples do caso
        String expressaoCaso ="";
        // expressão do caso
        String todasCaso = line.substring(4, indexPontos).trim();
        //as condições no caso estão separadas por virgulas
        StringTokenizer tok = new StringTokenizer(todasCaso,",");
        while( tok.hasMoreElements()){
            expressaoCaso = tok.nextToken().trim();

            //verificar se não uma variavel ou expressao            
            if( ! Expression.IsExpression(expressaoCaso,memory) )
                throw new LanguageException(
                        caseNode.GetCharNum(), caseNode.GetText(),
                        expressaoCaso + " não é uma varivel nem uma expressão ",
                        " Verifique a expressão do ESCOLHE");
            //adicionar uma nova condição
            condicaoCaso.append(expressaoEscolhe + " = " + expressaoCaso);
            // adicionar a condição OU caso haja mais elementos
            if( tok.hasMoreElements() )
                condicaoCaso.append(" OU ");
        }
        //----------------------------------------------------------------
        //--------------- FAZER A PRIMEIRA INSTRUÇAO ------------------------
        //----------------------------------------------------------------
        //import buscar a intrução
        String instrucao = line.substring(indexPontos+1).trim();
        // no da instrução
        NodeInstruction nodeInstr;
        //fazer um novo no
        if(instrucao.length() >0 ){
            nodeInstr = new NodeInstruction(instrucao, caseNode.GetCharNum(), level+1 );
            nodeInstr.SetNext(caseNode.GetNext());
        } else{
            nodeInstr = caseNode.GetNext();
            nodeInstr.IncrementLevel();
        }
        
        //-------------------------------------------------------ALTERAR O CASO
        //alterar o no para ser um IF
        caseNode.SetLevel(level);
        // retirar o CASO e o :
        caseNode.SetText(condicaoCaso.toString());
        // defenir o tipo
        caseNode.SetType(Keyword.SE);
        // actualizar os ponteiros next e false  para end;
        caseNode.SetNext(end);
        caseNode.SetIfFalse(end);
        //ligar o verdadeiro a intrução
        caseNode.SetIfTrue(nodeInstr);
        // retorna um o com a intrução seguinte
        return nodeInstr;
    }
}
