/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * MakeVAR.java
 *
 * Created on 29 de Outubro de 2005, 19:10
 *
 
 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Keyword;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.SymbolArray;
import Portugol.Language.Utils.IteratorCodeLine;
import Portugol.Language.Utils.IteratorCodeParams;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import java.util.Vector;


/**
 * Trata de variveis
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
public class ExpandDefinirSimbol {
    private static int SIMPLES     = 0;
    private static int ARRAY        = 1;
    
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------    V A R I A V E I S                        ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Expande variveis. uma linha de código pode
     * definir várias variáveis
     *
     * retorna o último nó definido para se poder continuar a
     * expandir o fluxograma
     * @return último nodo definido
     * @param node nó da linha de código
     * @param level nivel
     * @param memory vector da memória
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static NodeInstruction ExpandVariable(NodeInstruction node , int level, Vector memory)
    throws LanguageException{
        int tipoVar = SIMPLES ; // tipo simples ou array
        boolean isConst; // é constante
        node.SetLevel(level);
        String alter , type , name, value="",memValue="";
        String CONSTANTE = Keyword.GetTextKey( Keyword.CONSTANTE);
        String VARIAVEL = Keyword.GetTextKey( Keyword.VARIAVEL);
        
        
        
        //lugar para onde aponta node
        NodeInstruction originalNextNode = node.GetNext();
        // uma instrução pode desgenerar em muitos nós
        // com a definição de várias variaveis ao mesmo tempo
        NodeInstruction prevNode = null;            // nó anterior
        
        String instruction = node.GetText();
        IteratorCodeLine tok = new IteratorCodeLine(instruction);
        alter = tok.current().toUpperCase();
        tok.next();
        //verificar se é constante ou variavel
        if (alter.equalsIgnoreCase(CONSTANTE)){
            isConst = true;
            type = tok.current().toUpperCase(); tok.next();
            
        }else if (alter.equals(VARIAVEL)){
            isConst = false;
            type = tok.current().toUpperCase(); tok.next();
        } else{
            isConst = false;
            type = alter;
            alter = VARIAVEL;
        }
        
        if( Symbol.getType(type) == Symbol.DESCONHECIDO  ){
            throw new LanguageException(
                    node.GetCharNum(), node.GetText() ,
                    type + " não é um tipo de simbolo válido ",
                    " verifique o tipo de constante ou variável");
        }
        
        //----definicao de  variáveis e valores --------------------
        //---------------------------------------------------------
        // as variaveis estão separadas por virgulas
        //--------------------------------------------------------
        String vars = tok.getUnprocessedString().trim();
        if(vars.length() == 0 )
            throw new LanguageException(
                    node.GetCharNum(), node.GetText() ,
                    " não existe o nome da simbolo",
                    " introduza um nome para o simbolo ");
        
        // iterador por virgulas
        IteratorCodeParams params = new IteratorCodeParams(vars);
        while(params.hasMoreElements()){
            // definir as variaveis
            NodeInstruction newNode = DefineSymbol( alter, type, params.current(),node, memory);
            params.getNext();
            //----------------- ligar os diversos nodos --------------------
            if( prevNode == null){
                node.SetText( newNode.GetText());
                prevNode = node;
            } else{
                newNode.SetPositionY( prevNode.GetPositionY() + 1 );
                prevNode.SetNext(newNode);
                prevNode = newNode;
            }
        }
        
        //-------------------------------------------------------
        //fazer a ligação com o no da intrução seguinte
        prevNode.SetNext(originalNextNode);
        //retornar o ultimo
        return prevNode;
//------------------------------------------------------------------------------
    }
    
    private static NodeInstruction DefineSymbol(String alter , String type , String code , NodeInstruction node , Vector memory)
    throws LanguageException{
        
        String value;
        String name;
        int pos_atr = code.indexOf( Keyword.ATRIBUI);
        if( pos_atr == -1) { // não tem valores
            name = code.trim();
            //se for constante tem de ser inicializada por isso provoca excepção
            if (alter.equalsIgnoreCase(Keyword.GetTextKey( Keyword.CONSTANTE)))
                throw new LanguageException(
                        node.GetCharNum(), node.GetText() ,
                       "<" + name + "> é constante e tem de ser inicializada na definição",
                        " introduza um valor para o simbolo <" + name + ">");
            
                value = Values.getDefault(type);
            } else{
                name = code.substring(0,pos_atr).trim();
                value = code.substring(pos_atr + Keyword.ATRIBUI.length() );
            }
            
          
            if ( SymbolArray.isArray(name))
                return ExpandDefinirArray.Define(alter,type,name,value,node,memory);
            else
                return ExpandDefinirVariavel.Define(alter,type,name,value,node,memory);
            
        }
        //---------------------------------------------------------------------------------------------------
        
        public static void main(String args[]){
            System.out.println("EXPAND SIMBOL");
            Vector memory = new Vector();
            try{
                NodeInstruction node = new NodeInstruction("variavel inteiro xx[ 10][20][5-6 --6] <- { 1,2,3,4,5} , j<-10, seno , l", 0 , 0);
                ExpandVariable( node,0,memory);
                
            } catch( Exception e){
                System.out.println(e.toString());
            }
        }
        
    }
