package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.Token;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/**
 * ver 0.3
 * 9/04/08
 * @author 
 *         Luís Vasco
 *         Diogo Silva
 *         Cláudio Antunes
 *         Telmo Silva
 *         Luís Mendes
 *         Manso 
 */
public class IteratorExpressionTokens implements Iterator {

    private LinkedList<Token> tokens = new LinkedList();
    private int currentPos = 0;

    /**
     * construtor da classe
     */
    public IteratorExpressionTokens(Vector<Token> equ) throws Exception {
        tokens.addAll(equ);
    }

    


    /**
     * Verifica se existem mais elementos na lista
     * @return
     */
    public boolean hasNext() {
        return (currentPos < tokens.size());
    }

    /**
     * Retorna o próximo elemento da lista
     * @return
     */
    public Token next() {
        return tokens.get(currentPos++);
    }

    /**
     * não foi usado, mas teve que ser implementado porque a classe assim o pedia
     */
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * retorna o Token actual
     * @return
     */
    public Token currentToken() {
        return this.tokens.get(currentPos);
    }

    /**
     * Coloca o ponteiro no inicio da lista
     */
    public void resetPos() {
        currentPos = 0;
    }

    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (Token t : tokens) {
            txt.append("«" + Keyword.getFastKeyword(t.getTtype()) + " " + t.getValue() + "»");
        }
        return txt.toString();
    }
}
