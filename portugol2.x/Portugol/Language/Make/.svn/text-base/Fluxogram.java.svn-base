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
/*
/*
 * Fluxogram.java
 *
 * Created on 4 de Outubro de 2005, 16:17
 *
 */

package Portugol.Language.Make;

import Editor.Utils.FileManager;
import Portugol.Language.Parse.Expression;
import Portugol.Language.Execute.ConsoleIO;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.SymbolArray;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.CodeLine;
import Portugol.Language.Utils.IteratorCodeParams;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import java.util.StringTokenizer;
import java.util.Vector;



/**
 * Classe que representa um fluxograma
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
/*
 * Histórico
 *  17-11 -2005
 *      corigi o bug do repete dentro do para
 *  19-11-2005
 *      implementei o  FAZ enquanto
 *      implementei o  Ler para varias variaveis
 *      Corrigi o WRITE
 *           - escrevia texto sem ser dentro de aspas
 *           - não verifica variaveis
 *
 *  20 -11 -2005
 *      - implementei o ESCOLHE CASO
 *  22 - 11 - 2005
 *      - corrigi o bug do numero de  inicios e do fins
 *
 */
public class Fluxogram {
    /**
     * Versão do fluxograma
     */
    public static String VERSION = "ver:2.0\tdata:22-06-2006 \t(c)António Manso";
        
    /**
     * apontador para o inicio do fluxograma
     */
    protected NodeInstruction start;
    /**
     * apontador para a nó que está a ser executado
     */
    protected NodeInstruction nodeExecute;
    /**
     * indicador se o texto pertence a um comentário do programa
     */
    protected boolean isComented=false;
    /**
     * vector das variaveis em memória
     */
    public Vector memory;
    
    /**
     * Constroi um fluxograma
     * @param code programa fonte
     * @throws Portugol.Language.Utils.LanguageException excepção
     */
    public Fluxogram(String code)throws LanguageException {
        Build(code);
    }
    
     
    /**
     * constroi um fluxograma com um ficheiro de programa
     * @param filename no do ficheiro
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public void ReadFile(String filename)throws LanguageException{
          FileManager f = new FileManager();          
          Build( f.ReadFile(filename));        
    }
    
    /**
     * retorna o inicio do fluxograma
     * @return nó onde começa o fluxograma
     */
    public NodeInstruction getStart(){
        return start;
    }
    
    /**
     * Controi o fluxograma
     * @param program Texto do programa
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    protected void Build(String program)throws LanguageException{
        int charNum = 0;
        int positionY =0;
        memory = new Vector();
        String instruction;
        // as intruções terminam com enter
        StringTokenizer st = new StringTokenizer(program,"\n\r");
        //no anterior (para fazer a ligação)
        NodeInstruction previousNode=null;
        // novo no
        NodeInstruction newNode=null;
        start = null;
        //fazer a lista seguida
        while (st.hasMoreTokens()) {
            //retirar os espaços
            instruction = st.nextToken();
            //contar o numero de caracteres
            charNum += instruction.length() + 1; // terminador
            //retirar espaços e comentarios
            instruction = RemoveComentarios(instruction);
            
            //se for uma linha vazia
            if(instruction.length()==0)
                continue;
            
            //normalizar os operadores e sinais
            instruction = CodeLine.GetNormalized(instruction);
            //construir o no da fluxograma
            newNode = new NodeInstruction(instruction, charNum-1, 0);
            newNode.SetPositionY(++positionY);
            if( newNode.GetType() == Keyword.DESCONHECIDO)
                throw new LanguageException(
                        newNode.GetCharNum(), newNode.GetText(),
                        " Instrução não reconhecida",
                        " Consulte a ajuda da linguagem" );
            //inicio do fluxograma
            if( start == null){
                // se não for um no de inicio
                if( newNode.GetType() != Keyword.INICIO)
                    throw new LanguageException(
                            newNode.GetCharNum(), newNode.GetText(),
                            "Os programas começam por INICIO ",
                            "Coloque uma intrução INICIO no principio do programa" );
                
                start = newNode;
                previousNode = start;
            } else{
                previousNode.SetNext(newNode);
                previousNode = newNode;
            }
        }//fim da lista seguida
        
        // se não for um no de FIM
        if( newNode.GetType() != Keyword.FIM)
            throw new LanguageException(
                    newNode.GetCharNum(), newNode.GetText(),
                    "Os programas terminam por FIM ",
                    "Coloque uma intrução FIM no fim do programa" );
        //expande cada uma das linhas
        ExpandFluxogram.ExpandNodes( start , memory);
        //calcular as posicoes do fluxograma
        FluxogramVisual.CalculateVisual(start);
    }
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                              ---------------------------
//------------  E X E C U Ç Ã O   D E   I N S T R U Ç Ã O   ---------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Executa uma linha de código - normalmente mais que um nó do fluxograma
     * @return Nó seguinte
     * @param node nó a ser executado
     * @param console consola para leitura e escrita
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    
    public NodeInstruction  ExecuteLine(NodeInstruction node,ConsoleIO console) throws LanguageException{
        int line = node.GetCharNum();
        while( line == node.GetCharNum())
            node = Execute(node,console);
        return node;
    }
    
    /**
     * Executa um um nó do fluxograma
     * @return Nó seguinte
     * @param node nó a ser executado
     * @param console consola para leitura e escrita
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    
    public NodeInstruction  Execute(NodeInstruction node,ConsoleIO console) throws LanguageException{
        switch(node.GetType() ){
            
            case Keyword.INICIO :
                cleanMemory( node.GetLevel(),memory);
                return node.GetNext();
                
            case Keyword.FIM:
                cleanMemory( node.GetLevel(),memory);
                return start;
                
            case Keyword.DEFINIR:                
                Variable.defineVAR(node,memory);
                return node.GetNext();
                
            case Keyword.CALCULAR :
                executeCalculate(node.GetText());
                return node.GetNext();
                
            case Keyword.LER:
                executeREAD(node.GetText(),console);
                return  node.GetNext();
                
            case Keyword.ESCREVER:
                executeWRITE(node.GetText(), console);
                return node.GetNext();
                
            case Keyword.CONECTOR:
            case Keyword.FAZ:
            case Keyword.REPETE:
            case Keyword.ESCOLHE :
                return node.GetNext();
                
                // verifica o passo do for e modifica a condição se necessário
            case Keyword.PASSO:
                String value = Expression.Evaluate(node.GetText(),memory);
                //avalia o passo
                double valor =Values.StringToDouble(value);
                //passo nulo
                if(valor == 0.0)
                    throw new LanguageException("ERRO - O PASSO do CICLO PARA é ZERO","Corrija o passo");
                //passa para o no da condição
                NodeInstruction forNode = node.GetNext();
                String instruction = forNode.GetText();
                // se o passo for negativo a condição
                if( valor > 0 )
                    forNode.SetText( instruction.replaceFirst(">=" , "<=") );
                else
                    forNode.SetText( instruction.replaceFirst("<=" , ">=") );
                return  node.GetNext();
            
            case Keyword.ATE:
            case Keyword.FAZENQUANTO:
            case Keyword.ENQUANTO:    
            case Keyword.SE:    
            case Keyword.PARA :
                String compare = Expression.Evaluate(node.GetText(),memory);
                //variaveis defenidas dentro do bloco
                cleanMemory( node.GetLevel()+1,memory);
                if(compare.equalsIgnoreCase( Values.VERDADEIRO))
                    return  node.GetIfTrue();
                else
                    return  node.GetIfFalse();
                                
        }       
        throw new LanguageException("ERRRO - NODO DESCONHECIDO " , node.toString());
        
    }
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------         C A L C U L A R                     ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Executa uma instrução de atribuição
     * @param str linha de código com a atribuição
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    
    protected void executeCalculate(String str)  throws LanguageException{
        int pos = str.indexOf(Keyword.ATRIBUI);
        String var = str.substring(0, pos).trim();
        String values = str.substring(pos + Keyword.ATRIBUI.length() ).trim();
        
        String newValue = Expression.Evaluate(values,memory);
        Variable.replaceVariableValue(var, newValue,memory);
    }
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * limpa a memoria,  os niveis de memória. <br>
     * Utiliza-se no ciclo para limpar as variaveis <br>
     * locais ao ciclo.
     * @param level nivel a partir do qual vai limpar
     */
    
    protected static void cleanMemory(int level, Vector memory){
        for( int index = memory.size()-1 ; index >=0 ; index--){
            Symbol v = (Symbol) memory.get(index);
            //elimina as variaveis superiores ou iguais ao nivel
            if (v.getLevel()>= level )
                memory.remove(index);
            else
                break;
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
     * executa a instrução de escrever
     * @param str string com a intrução
     * @param console consola para escrever
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    protected void executeWRITE(String str,ConsoleIO console) throws LanguageException{
        str = str.substring( Keyword.GetTextKey( Keyword.ESCREVER ).length()); 
        IteratorCodeParams tok = new IteratorCodeParams(str.trim());
        //WriteTokenizer tok = new  WriteTokenizer(str);
        StringBuffer line = new StringBuffer();
        String elemLine ;
        //parametros
        while(tok.hasMoreElements()){
            String elem = tok.current();
            tok.next();
            
            if( !Values.IsString(elem) )
                elemLine = Expression.Evaluate(elem,memory);
            else
                elemLine = elem;
            line.append( Values.getStringValue(elemLine));
        }
        console.write(line.toString());
    }
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------         L E R                               ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Executa a instrução de leitura
     * @param str linha de código
     * @param console consola de leitura
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    
    protected void executeREAD(String str,ConsoleIO console) throws LanguageException{
        //ler = 3 caracteres
        String varName = str.substring(3).trim();
        /*
        if( MakeArrays.isArray(varName))
            varName = MakeArrays.replaceIndexToValues(varName, memory);
         */
        Symbol var = Variable.getVariable(varName,memory);
        // fazer o set ao index do array
        if( var instanceof SymbolArray)
            ((SymbolArray) var).SetIndex(varName,memory);
        
        String value = console.read(var);
        var.setValue(value);
    }
    
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------                                             ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Normaliza uma linha de código
     * retira-lhe os comentários e os espaços
     * @param str linha de código
     * @return linha de código normalizada
     */
    public   String RemoveComentarios(String str){
        StringBuffer newStr = new StringBuffer();
        for(int index=0 ; index< str.length() ; index++ ) {
            switch (str.charAt(index)) {
                case '/':
                    // comentario "//"
                    if(index +1 < str.length() && str.charAt(index+1)=='/')
                        return newStr.toString().trim();
                    //inicio de um comentário /*
                    if(index +1 < str.length() && str.charAt(index+1)=='*')
                        isComented=true;
                    else
                        //fim do comentario */
                        if( index >0 && str.charAt(index-1)=='*')
                            isComented=false;
                    //introduz caracter /
                        else
                            newStr.append(str.charAt(index));
                    break;
                default:
                    // se não for comentario
                    if( !isComented)
                        newStr.append(str.charAt(index));
            }
        }
        return newStr.toString().trim();
    }
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------                                             ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * string
     * @return string
     */
    public String toString(){
        StringBuffer str = new StringBuffer();
        NodeInstruction pt = start;
        while( pt != null){
            str.append( GetCode(pt));
            pt = pt.next;
        }
        return str.toString();
    }
    
    /**
     * calcula o texto de um nodo
     * @param node nodo de origem
     * @return o texto de um nodo
     */
   public static String GetCode(NodeInstruction node){
        
        if( node.GetType() == Keyword.CONECTOR )    return "";
        if( node.GetType() == Keyword.SE )    return ExpandSe.toString(node);
        if( node.GetType() == Keyword.ENQUANTO ) return ExpandEnquanto.toString(node);
        return node.toString()+ "\n";
    }
    
}

