/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 2/Jun/2008 - 23:16:46
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Token;
import java.util.Iterator;
import java.util.Vector;

public class IteratorElemTokens implements Iterator {

    int pos;
    Vector<LineTokens> elements = new Vector<LineTokens>();
    String start = "{[(";
    String stop = "}])";

    public IteratorElemTokens(LineTokens l) {
        BuildIterator(l);
        pos = 0;
    }

    public boolean hasNext() {
        return pos < elements.size() && pos >= 0;
    }

    public LineTokens next() {
        return elements.get(pos++);
    }
    //devolve o que ainda nao foi processado
    public LineTokens getUnprocessed() {
        LineTokens line = new LineTokens();
        for (int i = pos; i < elements.size(); i++) {
            LineTokens l = elements.get(i);
            for (Token t : l.getElements()) {
                line.addTokens(t);
            }
        }
        return line;
    }

    public LineTokens peek() {
        return elements.get(pos);
    }

    public void goBack() {
        pos--;
    }

    private void BuildIterator(LineTokens l) {
        IteratorLineTokens it = new IteratorLineTokens(l);
        LineTokens elem = new LineTokens();
        int P = 0;
        while (it.hasNext()) {
            Token token = it.next();
            if (!token.sval.isEmpty() && start.contains(token.sval)) {
                P++;
            } else if (!token.sval.isEmpty() && stop.contains(token.sval)) {
                P--;
            }
            elem.addTokens(token);

            if (P == 0) {
                elements.add(elem);
                elem = new LineTokens();
            }


        }


    }

    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (LineTokens t : elements) {
            txt.append(t.toString() + "\n");
        }
        return txt.toString().trim();
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
