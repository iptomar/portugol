/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 25/Mai/2008 - 2:14:33
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Parser;
import AlgolXXI.Core.Parser.ProgramTokens;
import java.util.Iterator;
import java.util.Vector;

public class IteratorProgramTokens implements Iterator {

    private Vector<LineTokens> codeLines;
    int line;

    public IteratorProgramTokens(String prog) {
        Parser p = new Parser(prog);
        codeLines = p.getProgram().getLines();
        line = 0;
        System.out.println(p.getProgram().toString());

    }

    public boolean hasNext() {
        return line < codeLines.size();
    }

    public ProgramTokens next() {
        ProgramTokens block = new ProgramTokens();
        LineTokens l = codeLines.get(line);
        switch (l.getTypeIntruction()) {
            case Keyword.DEFINIR:
                block.addLine(l);
                line++;
                break;
            case Keyword.ESTRUTURA:
                while (l.getTypeIntruction() != Keyword.FIMDEESTRUTURA) {
                    block.addLine(l);
                    line++;
                    l = codeLines.get(line);
                }
                block.addLine(l);
                line++;
                break;
            case Keyword.DEFINIRFUNCAO:
                while (l.getTypeIntruction() != Keyword.FIMDEFINIRFUNCAO) {
                    block.addLine(l);
                    line++;
                    l = codeLines.get(line);
                }
                block.addLine(l);
                line++;
                break;

            case Keyword.INICIO:
                while (l.getTypeIntruction() != Keyword.FIM) {
                    block.addLine(l);
                    line++;
                    l = codeLines.get(line);
                }
                block.addLine(l);
                line++;
                break;
            default:
               l.setTypeIntruction(Keyword.DESCONHECIDO);
               block.addLine(l);
        }
        return block;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
