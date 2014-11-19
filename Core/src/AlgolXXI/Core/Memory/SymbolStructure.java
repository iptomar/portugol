/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Make.MakeSymbol;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.ProgramTokens;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.ArrayList;

/**
 *ESta classe vai representar as estruturas
 * @author manso
 */
public class SymbolStructure extends SymbolDataComplex {

    SymbolStructure ref;
    String typeName;

    // faz a definicao de um objecto do tipo estrutura
    // e atribui-lhe os valores por defeito
    public SymbolStructure(ProgramTokens prog, Memory mem, int level)
            throws Exception {
        super(Symbol.STRUCTURE, false);
        this.level = level;
        data = new ArrayList<SymbolData>();
        //primeira linha tem o nome da estrutura // estrutura data
        LineTokens l = prog.getLines().get(0);
        name = l.getElements().get(1).getValue();
        //as outras têm as definições
        for (int i = 1; i < prog.getLines().size() - 1; i++) {
            SymbolData var = MakeSymbol.NewSymbol(prog.getLines().get(i), mem, level);
            var.normalizeName();
            data.add(var);
        }
    }

    public SymbolStructure(SymbolStructure model, String newName, int level) throws LanguageException {
        super(Symbol.STRUCTURE, true);
        this.level = level;
        data = new ArrayList<SymbolData>();
        name = newName;
        typeName = model.typeName;
        // normalizeName( );
        SymbolData field;
        for (SymbolData sym : model.data) {

            if (sym instanceof SymbolArray) {
                //o array mete o nome dos elementos
                field = SymbolManager.getCloneArray((SymbolArray) sym, newName, level);
                field.name = field.name + "." + sym.name;
            } else if (sym instanceof SymbolStructure) {
                //fazer um clone da estrutura
                field = SymbolManager.getCloneStructure((SymbolStructure) sym, newName, level);
                field.name = field.name + "." + sym.name;
            } else {
                //o data nao mete o nome dos elementos
                field = SymbolManager.getDataSymbol(sym, newName + "." + sym.name, level);
            }

            field.normalizeName();
            data.add(field);
        }
//        objectValue = data;

    }

    public SymbolStructure(SymbolStructure model) throws LanguageException {
        super(Symbol.STRUCTURE, true);
        this.level = model.level;
        data = new ArrayList<SymbolData>();
        name = model.name;
        typeName = model.typeName;
        objectValue = data;
    }
//    private String getStructName(String line) {
//        IteratorLine it = new IteratorLine(line, " ");
//        it.next();     // estrutura
//
//        return it.next(); // nome
//
//    }

//    public String insertStructName(String line, String struct) {
//        IteratorLine it = new IteratorLine(line);
//        String _modify = it.next();
//        String _type;
//        if (!_modify.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CONSTANTE)) &&
//                !_modify.equalsIgnoreCase(Keyword.GetTextKey(Keyword.VARIAVEL))) {
//            _type = _modify;
//            _modify = Keyword.GetTextKey(Keyword.VARIAVEL);
//        } else {
//            _type = it.next();
//        }
//        String _name = it.next();
//        String _tail = it.GetUnprocessedLine();
//
//        return _modify + " " + _type + " " + struct + "." + _name + " " + _tail;
//    }
//
//    public String getValue() {
//        StringBuffer txt = new StringBuffer();
//        txt.append("{");
//        for (int i = 0; i < data.size(); i++) {
//            txt.append(data.get(i).getValue());
//            if (i < data.size() - 1) {
//                txt.append(",");
//            }
//        }
//        txt.append("}");
//        return txt.toString();
//
//    }
//
//    public void setValue(String val) throws LanguageException {
//        //retirar os espaços
//        val = val.trim();
//        //retirar as chavetas no inicio e no fim
//        //  { 1 ,2 ,3 }
//        if (val.startsWith("{")) {
//            val = val.substring(1);
//        }
//        if (val.endsWith("}")) {
//            val = val.substring(0, val.length() - 1);
//        }
//        String value;
//        //iterador pelas virgulas
//        IteratorLine itVal = new IteratorLine(val, ",");
//        for (SymbolData symb : this.data) {
//            if (itVal.hasNext()) {
//                value = itVal.next(); //valor
//
//            } else {
//                value = "";        //valor por defeito 
//            //atribuir o valor
//            // se for um array o value continua a ser um valor
//
//            }
//            symb.setValue(value); //atribuir o valor
//
//        }
//        if (itVal.hasNext()) {
//            throw new LanguageException("Excesso de valores para a inicializacao da estrutura",
//                    "Verifique o numero de valores");
//        }
//    }
    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        txt.append(this.name);
        for (SymbolData s : data) {
            txt.append(s.toString() + "\n");
        }
        return txt.toString();
    }

    public static boolean isStructure(String def) {
        return def.toUpperCase().startsWith(
                Keyword.GetTextKey(Keyword.ESTRUTURA) + " ");
    }

    @Override
    public String getTypeName() {
        return typeName;
    }
}
