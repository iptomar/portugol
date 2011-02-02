/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
 

package Portugol.Language.Parse;

import Portugol.Language.Evaluate.*;
import Portugol.Language.Utils.IteratorCodeLine;
import Portugol.Language.Utils.IteratorElements;
import Portugol.Language.Utils.IteratorExpression;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Parentesis;
import Portugol.Language.Utils.Values;
import java.util.Vector;


/**
 * Trata de expressões
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
public class Expression {
  
    
      
    /**
     * verifica se uma string é uma expressão
     * @param infix string com a expressãp
     * @throws java.lang.Exception mensagem de erro caso não seja uma expressão
     */
    public static void Verify( String infix)throws Exception{
        if( ! IsGood(infix))
            throw new Exception("ERRO no PARSER :"+ GetError(infix));
    }
    
    /**
     * Verifica se uma string é uma expressão
     * @param infix string com a expressão 
     * @return verdadeiro se for uma expressão 
     */
    public static boolean IsGood( String infix){
        //verificar parentesis
        if( ! Parentesis.Verify(infix))
            return false;
        //verificar valores operadores e funções
        IteratorExpression it = new IteratorExpression(infix);
        CalculusElement calc = new CalculusElement();
        while( it.hasMoreElements()){
            String elem = it.current();
            it.next();
            if( !calc.IsCalculus(elem) && !Values.IsValue(elem) && !Parentesis.IsParentesis(elem))
                return false;
        }
        return true;
    }
    
    
    /**
     * Calcula o erro de uma expressão
     * @param infix string com a expressão
     * @return string com o erro
     */
    public static String GetError( String infix){
        if( ! Parentesis.Verify(infix))
            return Parentesis.GetError(infix);
        
        IteratorExpression it = new IteratorExpression(infix);
        CalculusElement calc = new CalculusElement();
        while( it.hasMoreElements()){
            String elem = it.current();
            it.next();
            if( !calc.IsCalculus(elem) && !Values.IsValue(elem) && !Parentesis.IsParentesis(elem) )
                return  " ERRO : simbolo ["  + elem + "] desconhecido ";
        }
        return "OK";
    }
    
    /**
     * substitui as variaveis de uma expressão pelos seus valores em memória.
     * @param expr expressão
     * @param memory memória
     * @return expressão com os valores
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public static String ReplaceVariablesToValues(String expr, Vector memory)throws LanguageException{
        
        StringBuffer newExpr = new StringBuffer();
        // iterador de elementos do codigo
        //IteratorExpression tok = new IteratorExpression(expr);
        IteratorElements tok = new IteratorElements(expr);
        Symbol var;
        String elem;
        while( tok.hasMoreElements()){
            elem = tok.current(); tok.next();
            newExpr.append(GetValueElement(elem , memory) );
        }
        return newExpr.toString().trim();
    }
   
    public static String GetValueElement(String elem, Vector memory ){
        Symbol var;
        //------------------------- sinal -  -------------
        if( elem.charAt(0) == '-'){
            //--operador - 
            if( elem.length() == 1 ) return elem;
            //---resto
            String resto = elem.substring(1);
            var = Variable.getVariable(resto,memory);
            //valor negativo
            if( var == null )
                return elem + " ";
            // se for uma variavel vai selecionar o valor            
            else if(var  instanceof SymbolArray ){
                SymbolArray a = (SymbolArray) var;
                a.SetIndex(elem,memory);
                return " ( " +  a.getValue() + " * -1 ) ";                
            }
            else   
                return " ( " +  var.getValue() + " * -1 ) ";
        }
        //-------------------------------------------------------------
        //  variavel sem valor negativo 
        //------------------------------------------------------------
          var = Variable.getVariable(elem,memory);            
            if( var == null )
                return elem + " ";            
            else if(var  instanceof SymbolArray ){
                SymbolArray a = (SymbolArray) var;
                a.SetIndex(elem,memory);
                return   a.getValue()+ " ";                
            }
            else   
                return var.getValue() + " " ;

    }
   //--------------------------------------------------------------------------- 
   //--------------------------------------------------------------------------- 
   
 
   //--------------------------------------------------------------------------- 
   //--------------------------------------------------------------------------- 
 
    /**
     * substitui as variaveis de uma expressão pelos seus valores por defeito.
     * @param expr expressão
     * @param memory memória
     * @return expressão com os valores
     */
    public static String ReplaceVariablesToDefaults(String expr, Vector memory){        
        StringBuffer newExpr = new StringBuffer();
        // iterador de elementos do codigo
        //IteratorExpression tok = new IteratorExpression(expr);
        IteratorElements tok = new IteratorElements(expr);
        Symbol var;
        String elem;
        while( tok.hasMoreElements()){
            elem = tok.current(); tok.next();
            newExpr.append(GetSafeValueElement(elem , memory) );
        }
        return newExpr.toString().trim();
    }
    
    public static String GetSafeValueElement(String elem, Vector memory ){
        Symbol var;
        //------------------------- sinal -  -------------
        if( elem.charAt(0) == '-'){
            //--operador - 
            if( elem.length() == 1 ) return elem;
            //---resto
            String resto = elem.substring(1);
            var = Variable.getVariable(resto,memory);
            //valor negativo
            if( var == null )
                return elem + " ";
            // se for uma variavel vai selecionar o valor            
            else
                return " ( " +  var.getSafeDefaultValue() + " * -1 ) ";
        }
        //-------------------------------------------------------------
        //  variavel sem valor negativo 
        //------------------------------------------------------------
          var = Variable.getVariable(elem,memory);            
            if( var == null )
                return elem + " ";            
            else
                return  var.getSafeDefaultValue() + " " ;        
    }
   //--------------------------------------------------------------------------- 
   //--------------------------------------------------------------------------- 
    
     public static String ReplaceVariablesToDefaults(String expr, Vector memory, int tryUnknow){
        
        StringBuffer newExpr = new StringBuffer();
        // iterador de elementos do codigo
        IteratorExpression tok = new IteratorExpression(expr);
        Symbol var;
        String elem;
        while( tok.hasMoreElements()){
            elem = tok.current(); tok.next();
            var = Variable.getVariable(elem,memory);
            // se for uma variavel vai selecionar o valor
            if( var != null){
                if (var.getType()!=var.DESCONHECIDO)
                    newExpr.append( var.getDefaultValue() + " ");
                else
                    newExpr.append( Symbol.getDefaultValue(tryUnknow) + " ");
            }
            //senao mete o elmento
            else
                newExpr.append(elem + " ");
        }
        return newExpr.toString().trim();
    }
    /**
     * verifica se uma expressão é aceitavel de acordo com o vectro de memória
     * @param express expressão
     * @param memory vector de memória
     * @return verdadeiro se for uma expressão aceitável
     */
    public static boolean IsExpression(String express,Vector memory){
        String exp = ReplaceVariablesToDefaults(express,memory);
        IteratorExpression it = new IteratorExpression(exp);
        while( it.hasMoreElements()){
            String elem = it.current();
            if( !elem.equals("(") && !elem.equals(")") &&
                    !elem.equals(",") &&
                    ! Calculator.IsCalculus( elem ) &&
                    ! Values.IsValue( elem ) )
                return false;
            it.next();
        }
        return true;
    }
    
    /**
     * calcula qual o elemento de uma expressão que dá erro
     * @param elem expressão
     * @param memory vector de memória
     * @return o elemento que dá erro
     */
    public static String ErrorExpression(String elem,Vector memory){
        String exp = ReplaceVariablesToDefaults(elem,memory);
        IteratorExpression it = new IteratorExpression(exp);
        while( it.hasMoreElements()){
            if( ! Calculator.IsCalculus(it.current()) )
                return it.current();
            it.next();
        }
        return "NO ERROR";
    }
    
    
    /**
     * calcula o tipo de dados de retorno de uma expressão (inteiro, real, lógico, . . .)
     * @param elem expressão
     * @param memory vector de memória
     * @return tipo de dados
     */
    public static int TypeExpression(String elem,Vector memory){
        try{
            String exp = ReplaceVariablesToDefaults(elem,memory);
            String result = Calculator.CalulateValue(exp);
            if(Values.IsBoolean(result) )   return Symbol.LOGICO;
            if(Values.IsInteger(result) )   return Symbol.INTEIRO;
            if(Values.IsCharacter(result))  return Symbol.CARACTER;
            if(Values.IsString(result) )    return Symbol.TEXTO;
            if(Values.IsReal(result) )      return Symbol.REAL;
            return Symbol.DESCONHECIDO;
        }catch( Exception e){
            return Symbol.DESCONHECIDO;
        }
        
    }
    
    /**
     * Avalia uma expressão
     * @param expression expressão
     * @param memory vector de memória
     * @return valor calculado
     */
    public static String Evaluate(String expression,Vector memory){
        try{
            String exp = ReplaceVariablesToValues(expression,memory);
            return Calculator.CalulateValue(exp);
        }catch( Exception e){
            return "ERROR CALCULATE";
        }
        
    }
    /**
     * Avalia uma expressão com os valores por defeito das variáveis
     * @param expression expressao
     * @param memory vector de memória
     * @return valor calculado
     */
     public static String EvaluateByDefaults(String expression,Vector memory){
        try{
            String exp = ReplaceVariablesToDefaults(expression,memory);            
            return Calculator.CalulateValue(exp);
        }catch( Exception e){
            return "ERROR DEFAULT CALCULATE";
        }
        
    }
        
}
