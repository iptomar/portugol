/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 23/Mai/2008 - 12:56:04
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Parser;

import java.util.Vector;

public class LineTokens {

    private int lineCode;
    private int blockNO;
    private int typeIntruction;
    private Vector<Token> elements;

    public LineTokens() {
        lineCode = 0;
        typeIntruction = Keyword.DESCONHECIDO;
        elements = new Vector<Token>();
    }

    public int getLineCode() {
        return lineCode;
    }

    public void setLineCode(int lineCode) {
        this.lineCode = lineCode;
    }

    public int getTypeIntruction() {
        return getType();
    }

    public Vector<Token> getElements() {
        return elements;
    }

    public Token getElement(int index) {
        return elements.get(index);
    }

    public Token getFirst() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(0);
    }
      public Token getLast() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(elements.size()-1);
    }

    public void addTokens(Token t) {
        elements.add(t);
    }

    public void addLine(LineTokens l) {
        elements.addAll(l.elements);
    }

    public void calculateInstructionType() {
        this.setTypeIntruction(getType());

    }

    private int getType() {
        //terceiro elemento
        if (elements.size() > 2) {
            if (elements.get(0).isType() &&
                    elements.get(1).ttype == Keyword.ID &&
                    elements.get(2).ttype == Keyword.LPARENT) {
                return Keyword.DEFINIRFUNCAO;
            }
        }
        //segundo elemento
        if (elements.size() > 1) {
            if (elements.get(0).ttype == Keyword.CONSTANTE ||
                    elements.get(0).ttype == Keyword.VARIAVEL) {
                return Keyword.DEFINIR;
            }

            if (elements.get(0).kw == Keyword.FIM &&
                    elements.get(1).kw == Keyword.PROGRAMA) {
                return Keyword.FIMDEFINIRFUNCAO;
            }
            //funçoes
            if (elements.get(0).kw == Keyword.FIM &&
                    elements.get(1).ttype == Keyword.ID) {
                return Keyword.FIMDEFINIRFUNCAO;
            }

            if (elements.get(1).ttype == Keyword.ASSIGN) {
                return Keyword.CALCULAR;
            }
            if (elements.get(0).kw == Keyword.FIM &&
                    elements.get(1).kw == Keyword.ESTRUTURA) {
                return Keyword.FIMDEESTRUTURA;
            }

            if (elements.get(0).ttype == Keyword.ID &&
                    elements.get(1).ttype == Keyword.LPARENT) {
                return Keyword.FUNCAODEFINIDA;
            }
            //estruturas  data  d  => ID ID
            //muitas duvidas - testar
            if (elements.get(0).ttype == Keyword.ID &&
                    elements.get(1).ttype == Keyword.ID) {
                return Keyword.DEFINIR;
            }
        }
        //primeiro elemento
        if (elements.get(0).kw == Keyword.PROGRAMA) {
            return Keyword.DEFINIRFUNCAO;
        }
        if (elements.get(0).kw == Keyword.VARIAVEL) {
            return Keyword.DEFINIR;
        }
        if (elements.get(0).kw == Keyword.CONSTANTE) {
            return Keyword.DEFINIR;
        }
        if (Keyword.getFastKey(elements.get(0).getValue()) == Keyword.INTEIRO) {
            return Keyword.DEFINIR;
        }
        if (Keyword.getFastKey(elements.get(0).getValue()) == Keyword.REAL) {
            return Keyword.DEFINIR;
        }
        if (Keyword.getFastKey(elements.get(0).getValue()) == Keyword.TEXTO) {
            return Keyword.DEFINIR;
        }
        if (Keyword.getFastKey(elements.get(0).getValue()) == Keyword.CARACTER) {
            return Keyword.DEFINIR;
        }
        if (Keyword.getFastKey(elements.get(0).getValue()) == Keyword.LOGICO) {
            return Keyword.DEFINIR;
        }


        return elements.get(0).kw;
    }

    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();

//        txt.append(Keyword.getFastKeyword(getTypeIntruction()) + " »»\t");
//        txt.append("[" + lineCode + "]\t");
        for (Token t : elements) {
            txt.append(t.toString() + " ");
        }
        return txt.toString();
    }

    public int getBlockNO() {
        return blockNO;
    }

    public void setBlockNO(int blockNO) {
        this.blockNO = blockNO;
    }

    public void setTypeIntruction(int typeIntruction) {
        this.typeIntruction = typeIntruction;
    }

    public void removeFirst() {
        if (elements.size() > 0) {
            elements.remove(0);
        }
    }

    public void removeLast() {
        if (elements.size() > 0) {
            elements.remove(elements.size() - 1);
        }
    }
}
