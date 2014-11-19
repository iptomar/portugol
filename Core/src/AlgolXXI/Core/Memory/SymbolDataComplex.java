/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 19/Mai/2008 - 15:27:42
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorElemTokens;
import AlgolXXI.Core.Utils.IteratorLine;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.ArrayList;
import java.util.Vector;

public abstract class SymbolDataComplex extends SymbolData {

    protected ArrayList<SymbolData> data;

    public SymbolDataComplex(int type, boolean ref) throws LanguageException {
        //contrutor de symbolos
        super(type, ref);
    }

    public SymbolData getComplexSymbol(String varName) {
        //verificar se é um dos membros
        for (SymbolData sym : data) {
            if (sym.name.equalsIgnoreCase(varName)) {
                return sym;
            }
            //se o nome começar por varname e for uma estrututa
            if (sym instanceof SymbolDataComplex //se for uma estrutura ou array
                    && (varName.startsWith(sym.name + ".") ||
                    varName.startsWith(sym.name + "["))) {
                return ((SymbolDataComplex) sym).getComplexSymbol(varName);
            }
        }
        return null;
    }

    public ArrayList<SymbolData> getData() {
        return data;
    }

//    public void setValue(LineTokens val) throws LanguageException {
//        //limpar os { e }
//        if (val.getElements().size() > 0 && val.getFirst().ttype == Keyword.LKEYWAY) {
//            val.removeFirst();
//        }
//        if (val.getElements().size() > 0 && val.getLast().ttype == Keyword.RKEYWAY) {
//            val.removeLast();
//        }
//
//        IteratorElemTokens it = new IteratorElemTokens(val);
//        for (int i = 0; i < data.size(); i++) {
//            LineTokens values = new LineTokens();
//            //valores
//            if (it.hasNext()) {
//                values = it.next();
//            }
//            //virgulas - consumir a virgula
//            if (it.hasNext() && it.peek().getFirst().ttype == Keyword.COMMA) {
//                it.next();            
//            }
//            //symbolos complexos
//            if (data.get(i) instanceof SymbolDataComplex) {
//                SymbolDataComplex complex = (SymbolDataComplex) data.get(i);
//                complex.setValue(values);
//            } //simbolos simples
//            else if (values.getElements().isEmpty()) //valor por defeito
//            {
//                data.get(i).setValue("");
//            } else //valor de atribuição
//            {
//                data.get(i).setValue(values.getElement(0).getValue());
//            }
//        }
//    }

    @Override
    public void setValue(String val) throws LanguageException {
        //retirar os espaços
        val = val.trim();
        //retirar as chavetas no inicio e no fim
        //  { 1 ,2 ,3 }
        if (val.startsWith("{")) {
            val = val.substring(1);
        }
        if (val.endsWith("}")) {
            val = val.substring(0, val.length() - 1);
        }
        //iterador pelas virgulas
        IteratorLine itVal = new IteratorLine(val, ",");
        for (SymbolData symb : getData()) {
            if (itVal.hasNext()) {
                symb.setValue(itVal.next());
            } else {
                symb.setValue("");      //valor por defeito 
            }
        }
        if (itVal.hasNext()) {
            throw new LanguageException("Excesso de valores para a inicializacao do array",
                    "Verifique o numero de valores");
        }
    }

    @Override
    public String getValue() {
        return getValue(this, name);
    }

    public static String getValue(SymbolDataComplex symbol, String varName) {
        StringBuffer txt = new StringBuffer();
        txt.append("{");
        for (int i = 0; i < symbol.data.size(); i++) {
            SymbolData sym = symbol.data.get(i);
            //se for um array
            if (sym instanceof SymbolArray) {
                txt.append(getValue((SymbolArray) symbol.data.get(i), varName));
            } //se for uma variavel simples
            else {
                txt.append(sym.getValue());
            }
            //colocar a virgula entre os elementos
            if (i < symbol.data.size() - 1) {
                txt.append(",");
            }
        }
        txt.append("}");
        return txt.toString();
    }

    public int getSize() {
        return data.size();
    }

    @Override
    public void setLevel(int l) {
        level = l;
        for (SymbolData s : data) {
            s.setLevel(l);
        }
    }
    
     public SymbolData getClone(String newName) throws LanguageException {
        //SymbolStructure obj = new SymbolStructure(this,"",0);
        return this;
    }
}
