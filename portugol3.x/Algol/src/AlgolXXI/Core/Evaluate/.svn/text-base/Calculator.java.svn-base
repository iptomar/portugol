/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Calculator.java
 *
 * Created on 2 de Maio de 2006, 16:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package AlgolXXI.Core.Evaluate;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorExpressionTokens;
import java.util.Stack;
import java.util.Vector;

/**
 * Calculador de express�es
 * @author Ant�nio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
/*
 *24/06/2006 - corrigi o bug das virgulas nos parametros das fun��es
 *
 */
public class Calculator {

    public static String VERSION = "ver:3.0\tdata:25-05-2004 \t(c)Antonio M@nso";
    private static CalculusElement calculatorValues = new CalculusElement();
    private static CalculusElementSafe calculatorSafe = new CalculusElementSafe();
    //------------------------------------------------------------------------------
    public static Token evaluate(Expression expr) throws Exception {
        return calulateExpressionValue(expr, calculatorValues);
    }

    public static Token evaluateSafe(Expression expr) throws Exception {
        return calulateExpressionValue(expr, calculatorSafe);
    }

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//---------------------------------------------------------------------------
    /**
     * met�do est�tico va converte infixa em posfixa
     * @return express�o posfixa
     * @param infix express�o infixa
     * @throws java.lang.Exception erro
     */
    public static Expression calculatePosFix(Expression infix, CalculusElement calc) throws Exception {
        IteratorExpressionTokens _it = new IteratorExpressionTokens(infix.getTokens());
        Stack<Token> s = new Stack<Token>();
        Token tok;
        Expression posFix = new Expression();
        while (_it.hasNext()) {
            tok = _it.next();
            
            //eliminar as virgulas
            if( tok.ttype == Keyword.COMMA){
                //esvaziar a pilha ate encontrar 
                while(!s.empty() && s.peek().ttype != Keyword.LPARENT )
                    posFix.add(s.pop());
                
                continue;
            }
                
            
            //introduzir os parentesis na pilha
            if (tok.ttype == Keyword.LBRACKET || tok.ttype == Keyword.LPARENT) {
                s.push(tok);
            } //parentesis a fechar
            //retirar da pilha os elementos ate encontrar o parentesis
            else if (tok.ttype == Keyword.RBRACKET || tok.ttype == Keyword.RPARENT)
            {
                while (!s.empty()) {
                    Token toks = s.pop();
                    //encontrou o parentesis a abrir
                    if (matchParentesis(toks, tok)) {
                        break;
                    } 
                        posFix.add(toks);
                }
            } //operadores
            else 
            {
                if (calc.IsCalculus(tok.getValue())) {
                    int prio = calc.GetPriority(tok.getValue());
                    while (!s.empty() && calc.GetPriority(s.peek().getValue()) >= prio) {
                        posFix.add(s.pop());
                    }
                    s.push(tok);
                } //valores
                else {
                    posFix.add(tok);
                }
            }
        }
        while (!s.empty()) {
            posFix.add(s.pop());
        }

        return posFix;
    }

    public static boolean matchParentesis(Token open, Token close) {
        if (open.ttype == Keyword.LBRACKET && close.ttype == Keyword.RBRACKET) {
            return true;
        }
        if (open.ttype == Keyword.LPARENT && close.ttype == Keyword.RPARENT) {
            return true;
        }
        return false;
    }
    //---------------------------------------------------------------------------
    /**
     * Executa o c�lculo
     * @return valor da express�o
     * @param expr express�o infixa
     * @throws java.lang.Exception erro
     */
    public static Token calulateExpressionValue(Expression expr, CalculusElement calculator) throws Exception {
        expr.normalizeExpression();
        Expression posFix = calculatePosFix(expr, calculator);
        IteratorExpressionTokens it = new IteratorExpressionTokens(posFix.getTokens());
        Token elem;
        Stack<Token> result = new Stack<Token>();
        Vector params = new Vector();
        while (it.hasNext()) {
            elem = it.next();
            // se for um calculo
            if (calculator.IsCalculus(elem.getValue())) {
                // retirar os parametros do calculo
                params.clear();
                for (int index = 0; index < calculator.GetNumParameters(elem.getValue()); index++) {
                    //verificar se existem parametros
                    if (result.empty()) {
                        throw new Exception(" ERRO no numero de parametros do simbolo :" + elem);
                    //adicionar no inicio
                    }
                    params.add(0, result.pop().getValue());
                }
                //adicionar o resultado
                String calcResult = calculator.Calculate(elem.getValue(), params);
                result.push(new Token(calcResult));
            } else // se for um valor   
            {
                result.push(elem);
            }
        }

        if (result.size() == 1) {
            return result.pop();
        } else {
            throw new Exception(" A Expressao [" + expr.toString() + "] esta  mal construida");
        }
    }
}
