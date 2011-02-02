/*
 * UnknowExpression.java
 *
 * Created on 30 de Junho de 2006, 11:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Parser;

import Portugol.Language.Evaluate.Calculator;
import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.IteratorExpression;
import Portugol.Language.Utils.Values;
import java.util.Vector;

/**
 *
 * @author Zeus
 */
public class UnknowExpression extends Expression{
    
    public static void ReplaceUnknowVariables(String expr, Vector memory, int know){
        IteratorExpression tok = new IteratorExpression(expr);
        Symbol var;
        while( tok.hasMoreElements()){ 
            var = Variable.getVariable(tok.current(),memory);
            // se for uma variavel vai guardar o tipo
            if( var != null && var.getType() == var.DESCONHECIDO )
                var.setType(know);
            tok.next();
        }
    }
    
    public static String ReplaceVariablesToAlternative(String expr, Vector memory, int tryUnknow){
        
        int alternate = 1;
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
                if ( var.getType() != var.DESCONHECIDO )
                    if ( var.getType() == Symbol.INTEIRO || var.getType() == Symbol.REAL )                    
                        newExpr.append( ( var.getDefaultValue()+alternate++ ) + " ");
                    else
                        newExpr.append( var.getDefaultValue() + " ");
                else
                    if ( tryUnknow == Symbol.INTEIRO || tryUnknow == Symbol.REAL )
                        newExpr.append( ( Symbol.getDefaultValue(tryUnknow)+alternate++ ) + " ");
                    else
                        newExpr.append( Symbol.getDefaultValue(tryUnknow) + " ");
            }
            //senao mete o elmento
            else
                newExpr.append(elem + " ");
        }
        return newExpr.toString().trim();
    }
    
    public static int TypeExpression(String elem) throws Exception{        
        String result = Calculator.CalulateValue(elem);
        if(Values.IsBoolean(result) )   return Symbol.LOGICO;
        if(Values.IsInteger(result) )   return Symbol.INTEIRO;
        if(Values.IsCharacter(result) ) return Symbol.CARACTER;
        if(Values.IsString(result) )    return Symbol.TEXTO;
        if(Values.IsReal(result) )      return Symbol.REAL;
        return Symbol.DESCONHECIDO;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public static int FortuneType(String elem,Vector memory, int Try){
        
        String exp = ReplaceVariablesToDefaults(elem, memory, Try);
        int trytype = Symbol.DESCONHECIDO;
        
        try {
            trytype = TypeExpression(exp);
        } catch (Exception ex) {
            exp = ReplaceVariablesToAlternative(elem, memory, Try);
            try {
                trytype = TypeExpression(exp);
            } catch (Exception e) {
                trytype = Symbol.DESCONHECIDO;
            }
        }
        
        if ( trytype != Symbol.DESCONHECIDO )
            ReplaceUnknowVariables(elem, memory, Try);
        
        return trytype;
    }
     
    
    public static int FortuneType_NumberPreference(String elem,Vector memory) throws Exception{        
        
        int type = FortuneType(elem, memory, Symbol.INTEIRO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.REAL);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.TEXTO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.LOGICO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
                
        Calculator.CalulateValue( ReplaceVariablesToAlternative(elem, memory, Symbol.INTEIRO) );
        return Symbol.DESCONHECIDO;
    }
    
    public static int FortuneType_TextPreference(String elem,Vector memory) throws Exception{        
        
        int type = FortuneType(elem, memory, Symbol.TEXTO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.REAL);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.LOGICO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
                
        Calculator.CalulateValue( ReplaceVariablesToDefaults(elem, memory, Symbol.TEXTO) );
        return Symbol.DESCONHECIDO;
    }
    
    public static int FortuneType_LogicPreference(String elem,Vector memory) throws Exception{
         
        int type = FortuneType(elem, memory, Symbol.LOGICO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.INTEIRO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.REAL);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
        
        type = FortuneType(elem, memory, Symbol.TEXTO);
        if ( type !=  Symbol.DESCONHECIDO )
            return type;
                
        Calculator.CalulateValue( ReplaceVariablesToAlternative(elem, memory, Symbol.REAL) );
        return Symbol.DESCONHECIDO;
    }
    

    
}
