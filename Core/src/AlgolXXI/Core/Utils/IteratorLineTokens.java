/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 25/Mai/2008 - 4:35:40
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Token;
import java.util.Iterator;
import java.util.Vector;

public class IteratorLineTokens implements Iterator {

    int pos;
    Vector<Token> elem;

    public IteratorLineTokens(LineTokens l) {
        elem = l.getElements();
        pos = 0;
    }

    public boolean hasNext() {
        return pos < elem.size() && pos >= 0;
    }

    public Token next() {
        return elem.get(pos++);
    }

    public Token peek() {
        return elem.get(pos);
    }

    public void remove() {
        elem.remove(pos);
    }

    public void insert(Token t) {
        elem.add(pos, t);
    }
    public void goBack(){
        pos--;
    }

    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (Token t : elem) {
            txt.append(t.getValue() + " ");
        }
        return txt.toString().trim();
    }
}
