/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Make.MakeSymbol;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorArray;
import AlgolXXI.Core.Utils.IteratorElemTokens;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *Estrutura de dados dos arrays
 * @author manso
 */
public class SymbolArray extends SymbolDataComplex {

    public SymbolArray(LineTokens line, Memory mem, int level) throws Exception {
        super(Symbol.ARRAY, true);
        IteratorElemTokens it = new IteratorElemTokens(line);
        //tipo de dados
        Token dataType;
        Token dataAlter = it.next().getElement(0);
        // tipo de varivel array
        if (dataAlter.kw == Keyword.VARIAVEL || dataAlter.kw == Keyword.CONSTANTE) {
            dataType = it.next().getElement(0);
        } else {
            dataType = dataAlter;
            dataAlter = new Token(Keyword.VARIAVEL);
        }
        //nome do array
        Token dataName = it.next().getElement(0);
        //elimiar o [
        LineTokens firstDim = it.next();
        firstDim.removeFirst();
        firstDim.removeLast();
        //alterar isto para suportar o calculo
        int dimension = (int) firstDim.getElement(0).nval;
        //eliminar os ]
        //--------------------outras Dimensões---------------------------------
        LineTokens dims = new LineTokens();
        while (it.hasNext() && it.peek().getElement(0).ttype!= Keyword.ASSIGN && it.peek().getElement(0).ttype != Keyword.COMMA) {
            dims.addLine(it.next());
        //--------------valores--------------------------------------
        }
        LineTokens values = new LineTokens();
        if (it.hasNext() && it.peek().getElement(0).ttype == Keyword.ASSIGN) {
            //eliminar o assign
            it.next();
            values.addLine(it.next());
        }
        //--------outras variaveis---------------------------------
        LineTokens newVars = new LineTokens();
        if (it.hasNext() && it.peek().getElement(0).ttype == Keyword.COMMA) {
            //eliminar a virgula
            it.next();
            newVars.addLine(it.getUnprocessed());
        }

        data = new Vector<SymbolData>(dimension);
        //copia da memoria
//        Memory tmpMem = new Memory(mem);
        for (int i = 0; i < dimension; i++) {
            LineTokens varArray = new LineTokens();
            varArray.addTokens(dataAlter);
            varArray.addTokens(dataType);
            Token nameVar = new Token(dataName.sval + Symbol.iniArray + i + Symbol.finArray);
            varArray.addTokens(nameVar);
            varArray.addLine(dims);
            SymbolData var = MakeSymbol.NewSymbol(varArray, mem, level);
            var.normalizeName();
           data.add(var);
        }
        this.name = dataName.sval;
        this.level = level;
        this.setValue(values);

    }

    public SymbolArray(String name, int dimension)
            throws LanguageException {
        super(Symbol.ARRAY, true);
        this.name = name;
        data = new Vector<SymbolData>(dimension);  // construir um vector

    }
    //um Array de estruturas
    public SymbolArray(SymbolStructure model, String varName, int level)
            throws LanguageException {
        super(Symbol.ARRAY, true);
        this.level = level;
        IteratorArray it = new IteratorArray(varName);
        name = it.getNext(); //tirar as 

        int dimension = Integer.valueOf(it.getNext());
        data = new Vector<SymbolData>(dimension);  // construir um vector

        for (int i = 0; i < dimension; i++) {
            //SymbolStructure s = new SymbolStructure(model,name+ "[" + i + "]", level);
            SymbolData s = SymbolManager.getCloneStructure(model, name + "[" + i + "]", level);
            data.add(s);
        }
    //objectValue = data; 
    }

    //O nome da variavel 
    public SymbolArray(SymbolArray model, String varName, int level)
            throws LanguageException {
        super(Symbol.ARRAY, true);
        this.level = level;
        IteratorArray it = new IteratorArray(varName);
        name = varName;
        int dimension = model.getSize();
        data = new Vector<SymbolData>(dimension);  // construir um vector

        for (int i = 0; i < dimension; i++) {
            SymbolData var = Memory.cloneDataSymbol(model.data.get(i),
                    name + Symbol.iniArray + i + Symbol.finArray, level);
            var.normalizeName();
            data.add(var);
        }
    //objectValue = data;
    }

    /**
     * 
     * @param line  - linha com a definicao
     * @param level  - nivel do symbolo
     */
    public SymbolArray(String line, String values, Memory mem, int level)
            throws LanguageException {
        super(Symbol.ARRAY, true);
        this.level = level;
        IteratorArray it = new IteratorArray(line);
        String mod = Symbol.getDefAlter(line);
        String type = Symbol.getDefType(line);
        String nameVar = Symbol.getDefName(it.getNext());

        //nome da variavel
        name = nameVar + getOriginalDims(line);

        int dimension = Integer.valueOf(it.getNext()); //numero de dimensoes

        data = new Vector<SymbolData>(dimension);


        nameVar = nameVar + it.getUnprocessed();


        nameVar = mod + " " + type + " " + nameVar;
        for (int i = 0; i < dimension; i++) {
            SymbolData var = Memory.makeNewSymbol(nameVar + Symbol.iniArray + i + Symbol.finArray, mem, level);
            var.name = name + "[" + i + "]";
            var.normalizeName();
            data.add(var);
        }

//        objectValue = data;
        this.setValue(values);

    //constructor 
    }

    /**
     * 
     * @param line  - linha com a definicao
     * @param level  - nivel do symbolo
     */
    public SymbolArray(String line, Memory mem, int level)
            throws LanguageException {
        super(Symbol.ARRAY, true);
        this.level = level;
        IteratorArray it = new IteratorArray(line);
        String mod = Symbol.getDefAlter(line);
        String type = Symbol.getDefType(line);
        String nameVar = Symbol.getDefName(it.getNext());

        //nome da variavel
        name = nameVar + getOriginalDims(line);

        int dimension = Integer.valueOf(it.getNext()); //numero de dimensoes

        data = new Vector<SymbolData>(dimension);


        nameVar = nameVar + it.getUnprocessed();


        nameVar = mod + " " + type + " " + nameVar;
        for (int i = 0; i < dimension; i++) {
            SymbolData var = Memory.makeNewSymbol(nameVar + Symbol.iniArray + i + Symbol.finArray, mem, level);
            var.normalizeName();
            data.add(var);
        }

//        objectValue = data;


    //constructor 
    }


//    @Override
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
//        for (SymbolData symb : getData()) {
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
//            throw new LanguageException("Excesso de valores para a inicializacao do array",
//                    "Verifique o numero de valores");
//        }
//    }
//
//    @Override
//    public String getValue() {
//        return getValue(this, name);
//    }

//    public static String getValue(SymbolArray array, String varName) {
//
//        StringBuffer txt = new StringBuffer();
//        txt.append("{");
//        for (int i=0 ; i< array.data.size() ; i++) {
//            SymbolData sym = array.data.get(i);
//            //se for um array
//            if (sym instanceof SymbolArray) {
//                txt.append(getValue((SymbolArray) array.data.get(i), varName));
//            } //se for uma variavel simples
//            else {
//                txt.append(sym.getValue());
//            }
//            //colocar a virgula entre os elementos
//            if( i < array.data.size() - 1)
//                txt.append(",");
//        }
//        txt.append("}");
//        return txt.toString();
//    }

//    @Override
//    public String getName() {
//        int index = name.indexOf("[");
//        if (index > 0) {
//            return name.substring(0, index);
//        }
//
//        return name;
//    }
    /**
     * verifica de o parametro é a definição de um array
     * @param def - linha de codigo tipo inteiro v[15][3][10]
     * @return
     */
    public static boolean isArray(String line) {
        //descartar os valores de inicializacao
        // inteiro v[0].name <- 10
        // inteiro v <- a[0]
        int index = line.indexOf(Keyword.ATRIBUI);
        if (index >= 0) {
            line = line.substring(0, index);
        //contruir um iterador para o array
        }

        index = line.length() - 1;
        while (index >= 0) {
            //se for uma estrutura
            if (line.charAt(index) == '.') {
                return false;
            }

            if (line.charAt(index) == ']') {
                return true;
            }

            index--;
        }

        return false;

//       IteratorArray it = new IteratorArray(line);
//        //o primeiro elemento é a definição e o nome
//        return it.getNumElements() > 1;
    }

    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        txt.append("\n NAME: " + name);
        txt.append("\n TYPE: " + this.type);
        for (SymbolData s : getData()) {
            txt.append("\n|" + s.toString() + "|\t");
        }

        return txt.toString();
    }

// converte os «» para dimensoes
    public static String getOriginalDims(
            String original) {
        StringTokenizer it = new StringTokenizer(original, Symbol.iniArray + Symbol.finArray, true);
        String previous = "";
        String current = "";
        StringBuffer txt =
                new StringBuffer();
        while (it.hasMoreTokens()) {

            current = it.nextToken();
            if (previous.equalsIgnoreCase(Symbol.iniArray)) {
                txt.append("[" + current + "]");
            }
            previous = current;

        }


        return txt.toString();
    }//// converte os «» para dimensoes
//    public static String getFullDims(
//            String original) {
//        StringTokenizer it = new StringTokenizer(original, "[]«»", true);
//        String previous = "";
//        String current = "";
//        StringBuffer txt =
//                new StringBuffer();
//        while (it.hasMoreTokens()) {
//
//            current = it.nextToken();
//            if (previous.equalsIgnoreCase("[")) {
//                txt.append("[" + current + "]");
//            }
//            previous = current;
//
//        }
//
//
//        return txt.toString();
//    }
}
