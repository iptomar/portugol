/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 25/Mai/2008 - 6:15:57
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Evaluate;

import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Memory.SymbolData;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorLineTokens;
import java.util.Vector;

public class Expression {

    private Vector<Token> tokens;

    public Expression() {
        tokens = new Vector<Token>();
    }
    
    public boolean isEmpty(){
        return getTokens().size() == 0;
    }

    public static Token evaluate(Memory mem, IteratorLineTokens it) throws Exception {
        Expression line = new Expression();
        Token token;
        int PC = 0, PB = 0;
        while (it.hasNext()) {
            token = it.next();
            //passar os parentesis curvos e rectos
            if ((PB == 0 && PC == 0 && token.ttype == Keyword.COMMA) || token.ttype == Keyword.ENDL) {
                break;
            }
            if (token.ttype == Keyword.LBRACKET) {
                PB++;
            }
            if (token.ttype == Keyword.RBRACKET) {
                PB--;
            }
            if (token.ttype == Keyword.LPARENT) {
                PC++;
            }
            if (token.ttype == Keyword.RPARENT) {
                PC--;
            }
            //substituir a variavel pelo valor
            if (token.ttype == Keyword.ID) {
                SymbolData var = mem.getSymbol(token.sval);
                if (var != null) {
                    token = new Token(token.ttype, var.getValue());
                }
            }
            line.add(token);
        }
        return Calculator.evaluate(line);
    }
    //avalia uma expressão
    public Token evaluate(Memory mem) throws Exception {
        Expression line = new Expression();
        for (Token token : getTokens()) {
            if (token.ttype == Keyword.ID) {
                SymbolData var = mem.getSymbol(token.sval);
                if (var != null) {
                    token = new Token(token.ttype, var.getValue());
                }
            }
            line.add(token);
        }
        return Calculator.evaluate(line);
    }

    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (Token t : getTokens()) {
            txt.append(t.getValue() + " ");
        }
        return txt.toString().trim();
    }

    public void add(Token t) {
        if (t.ttype != Keyword.ENDL) {
            getTokens().add(t);
        }
    }

    /**
     * normaliza a equação, ou seja, passa de vector para lista e normaliza os sinais unários
     */
    public void normalizeExpression() throws Exception {
        normUnaryMinus();
        normUnaryPlus();
    }

    /**
     * Verifica se o sinal indicado na posição index é binário
     *
     * @param index
     * @return
     */
    private boolean isBinary(int index) {
        if (index > 0) {
            Token t = getTokens().get(index - 1);
            // se for o final de uma funcao , expressão  array ou numero
            return (t.ttype == Keyword.RPARENT ||
                    t.ttype == Keyword.RBRACKET ||
                    t.ttype == Keyword.REAL ||
                    t.ttype == Keyword.INTEIRO ||
                    t.ttype == Keyword.TEXTO ||
                    t.ttype == Keyword.CARACTER ||
                    t.ttype == Keyword.TEXTO ||
                    t.ttype == Keyword.ID);

        } else {
            return false;
        }
    }

    /**
     * Verifica quantos sinais de menos unários existem e simplifica 
     * a expressão recorrendo a uma potência de base -1 e pelo expoente 
     * correspondente ao número de menos
     */
    private void normUnaryMinus() {
        int cont = 0;
        int pot = 0;
        for (int i = 0; i < getTokens().size(); i++) {
            //normalizar o sinal -  
            if (getTokens().get(i).ttype == Keyword.OP_MINUS && !isBinary(i)) {
                cont++;
                getTokens().remove(i);
                while (i < getTokens().size() && getTokens().get(i).ttype == Keyword.OP_MINUS) {
                    cont++;
                    getTokens().remove(i);
                }
                pot = (int) Math.pow(-1, cont);
                if (pot < 0) {
                    getTokens().add(i + 1, new Token(Keyword.RPARENT, ")"));
                    getTokens().add(i, new Token(Keyword.KEYWORD, "*"));
                    getTokens().add(i, new Token(Keyword.INTEIRO, pot));
                    getTokens().add(i, new Token(Keyword.LPARENT, "("));
                    cont = 0;
                }
            }
        }
    }

    /**
     * Verifica quantos sinais de menos unários existem e simplifica 
     * a expressão recorrendo a uma potência de base -1 e pelo expoente 
     * correspondente ao número de menos
     */
    private void normUnaryPlus() {
        int cont = 0;
        int pot = 0;
        for (int i = 0; i < getTokens().size(); i++) {
            //normalizar o sinal +  
            if (getTokens().get(i).ttype == Keyword.OP_PLUS && !isBinary(i)) {
                cont++;
                getTokens().remove(i);
                while (i < getTokens().size() && getTokens().get(i).ttype == Keyword.OP_PLUS) {
                    cont++;
                    getTokens().remove(i);
                }
                pot = (int) Math.pow(-1, cont);
                if (pot < 0) {
                    getTokens().add(i, new Token(Keyword.KEYWORD, "+"));
                    cont = 0;
                }
            }
        }
    }

    public

    Vector<Token> getTokens() {
        return tokens;
    }
}
