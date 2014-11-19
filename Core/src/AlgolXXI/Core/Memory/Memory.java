/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Function;
import AlgolXXI.Core.Make.MakeSymbol;
import AlgolXXI.Core.Parser.*;
import AlgolXXI.Core.Utils.CodeLine;
import AlgolXXI.Core.Utils.IteratorLine;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Constroi um novo simbolo na memoria
 * @author manso
 */
public class Memory {

    /**
     * vector os simbolos definidos no programa
     */
    //private Vector<ProgramBlock> funcs;
    /**
     * vector os simbolos definidos no programa
     */
    private Vector<Function> defFuncs;
    /**
     * vector os simbolos definidos no programa
     */
    private Vector<SymbolStructure> struct;
    /**
     * vector com os simbolos constantes e variaveis
     */
    private ArrayList<SymbolData> memory;

    public Memory() {
        struct = new Vector<SymbolStructure>();
        memory = new ArrayList<SymbolData>();
        //funcs = new Vector<ProgramBlock>();
        defFuncs = new Vector<Function>();
    }

    public Memory(Memory clone) {
        struct = new Vector<SymbolStructure>();
        memory = new ArrayList<SymbolData>();
        //funcs = new Vector<ProgramBlock>();
        defFuncs = new Vector<Function>();

        struct.addAll(clone.struct);
        memory.addAll(clone.memory);
        //funcs.addAll(clone.funcs);
        defFuncs.addAll(clone.defFuncs);
    }
    //----------------------------------------------------------------
//    public void addFunction(ProgramBlock func) {
//        funcs.add(func);
//    }
    //----------------------------------------------------------------
    public void addDefFunction(Function func) {
        defFuncs.add(func);
    }

    public Function getDefFunction(String name) {
        for (Function p : defFuncs) {
            if (name.equalsIgnoreCase(p.getName())) {
                return p;
            }
        }
        return null;
    }

//    public ProgramBlock getFunction(String name) {
//        for (ProgramBlock p : funcs) {
//            if (name.equalsIgnoreCase(p.getName())) {
//                return p;
//            }
//        }
//        return null;
//    }
//
//    public Vector<ProgramBlock> getFunctions() {
//        return funcs;
//    }

//    public boolean isDefinedFunction(String name) {
//        //partir por um espaço ou (
//        IteratorLine it = new IteratorLine(name, " (");
//        return getFunction(it.next()) != null;
//    }
    //----------------------------------------------------------------
    public void addStructure(SymbolStructure newStruct) {
        struct.add(newStruct);
    }

    public Vector<SymbolStructure> getStructures() {
        return struct;
    }

    public SymbolStructure getStructure(String name) {
        for (SymbolStructure symb : struct) {
            if (name.equalsIgnoreCase(symb.getName())) {
                return symb;
            }
        }
        return null;
    }

    public boolean isDefinedStructure(String name) {
        //partir por um espaço
        IteratorLine it = new IteratorLine(name, " ");
        return getStructure(it.next()) != null;
    }
    //----------------------------------------------------------------
    public SymbolData getSymbol(String name) {
        for (SymbolData s : memory) {

            if (name.equalsIgnoreCase(s.name)) {
                return s;
            //se o nome começar por varname e for uma estrututa
            }
            if (s instanceof SymbolDataComplex //se for uma estrutura ou array
                    && (name.startsWith(s.name + ".") ||
                    name.startsWith(s.name + "["))) {
                return ((SymbolDataComplex) s).getComplexSymbol(name);
            }
        }
        return null;
    }

    ArrayList<SymbolData> getSymbols() {
        return memory;
    }

    Vector<SymbolData> getSymbols(int level) {
        Vector<SymbolData> tmp = new Vector<SymbolData>();
        for (SymbolData s : memory) {
            if (s.level == level) {
                tmp.add(s);
            }
        }
        return tmp;
    }

    public boolean isDefined(String name) {
        return getSymbol(name) != null;
    }

    /**
     * Adiciona uma REFERENCIA para um simbolo na memoria
     * Isto funciona como a passagem de parametros por referencia
     * na linguagem C++
     * @param s
     */
    public void addSymbol(SymbolData s) {
        getMemory().add(s);
    }

    /**
     * Introduz na memorias todos os simbolos que estão definidos 
     * na linha do paramtro
     * preparado par definir varios simbolos numa linha
     * @param line - linha de código com a definição
     * @param mem  - memoria de destino dos simbolos
     * @param level - nivel do simbolo
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public static void defineSymbolLine(String line, Memory mem, int level)
            throws LanguageException {
        SymbolData newSymbol = null;

        String alter,
                type;
        String CONSTANTE = Keyword.GetTextKey(Keyword.CONSTANTE);
        String VARIAVEL = Keyword.GetTextKey(Keyword.VARIAVEL);
        line =
                CodeLine.removeLineNumber(line);

        IteratorLine it = new IteratorLine(line);
        alter =
                it.next().toUpperCase();

        //verificar se é constante ou variavel
        if (alter.equalsIgnoreCase(CONSTANTE)) {
            type = it.next().toUpperCase();

        } else if (alter.equals(VARIAVEL)) {
            type = it.next().toUpperCase();
        } else {
            type = alter;
            alter =
                    VARIAVEL;
        }
//----definicao de  variáveis e valores --------------------
//---------------------------------------------------------
// as variaveis estão separadas por virgulas
//--------------------------------------------------------
        String vars = it.GetUnprocessedLine();
        IteratorLine itv = new IteratorLine(vars, ",");
        while (itv.hasNext()) {
            String var = itv.next();
            //sem nome e sem valores
            SymbolData model;
//contruir um modelo
            if (mem.isDefinedStructure(type)) {
                model = mem.getStructure(type);
            } else {
                model = SymbolManager.getDataSymbol(alter, type, var, "", mem, level);
            }

            if (SymbolArray.isArray(var)) {
                newSymbol = SymbolManager.getDataArray(model, var, level);
            } else if (model instanceof SymbolStructure) {
                newSymbol = SymbolManager.getCloneStructure((SymbolStructure) model, var, level);
            } else {
                newSymbol = model;
            }

            newSymbol.normalizeName();
            mem.addSymbol(newSymbol);
        }

    }

    /**
     * Faz um simbolo com os parametros
     * @param alter - variavel / constante
     * @param type tipos de dados
     * @param var - codigo com a definição da variavel com a possibilidade
     * de ter o valor de inicialização
     * @param level - nivel do simbolo
     * @return - Simbolo 
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public static SymbolData makeNewSymbol(
            String line, Memory mem, int level)
            throws LanguageException {
        //se for um array
        if (SymbolArray.isArray(line)) {
            return new SymbolArray(line, mem, level);
        }

        String alter, type;
        String CONSTANTE = Keyword.GetTextKey(Keyword.CONSTANTE);
        String VARIAVEL = Keyword.GetTextKey(Keyword.VARIAVEL);
        line =
                CodeLine.removeLineNumber(line);

        IteratorLine it = new IteratorLine(line);
        alter =
                it.next().toUpperCase();
        //verificar se é constante ou variavel
        if (alter.equalsIgnoreCase(CONSTANTE)) {
            type = it.next().toUpperCase();

        } else if (alter.equals(VARIAVEL)) {
            type = it.next().toUpperCase();
        } else {
            type = alter;
            alter =
                    VARIAVEL;
        }

        String var = it.next();
        SymbolStructure s = mem.getStructure(type);
        SymbolData newVar;
//se for uma estrutura
        if (s != null) {
            newVar = SymbolManager.getCloneStructure(s, var, level);
        // newVar = new SymbolStructure(s,var,level);
        } else {
            newVar = makeNewDataSymbol(alter, type, var, level);
        }

        return newVar;
    }

    /**
     * Faz um simbolo com os parametros
     * @param alter - variavel / constante
     * @param type tipos de dados
     * @param var - codigo com a definição da variavel com a possibilidade
     * de ter o valor de inicialização
     * @param level - nivel do simbolo
     * @return - Simbolo 
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public static SymbolData makeNewDataSymbol(
            String alter, String type, String var, int level)
            throws LanguageException {
        switch (Symbol.getType(type)) {
//            case Keyword.INTEIRO:
//                //return new SymbolInteger(alter, type, var, "", level);
//                return new SymbolInteger(alter, type, var, "", level, null);
//            case Keyword.REAL:
//                //return new SymbolReal(alter, type, var, "", level);
//                return new SymbolReal(alter, type, var, "", level, null);
//            case Keyword.LOGICO:
//                //return new SymbolLogic(alter, type, var, "", level);
//                return new SymbolLogic(alter, type, var, "", level, null);
//            case Keyword.TEXTO:
//                //return new SymbolText(alter, type, var, "", level);
//                return new SymbolText(alter, type, var, "", level, null);
//            case Keyword.CARACTER:
//                //return new SymbolCharacter(alter, type, var, "", level);
//                return new SymbolCharacter(alter, type, var, "", level, null);
            default:
                return null;
        }

    }

    public static SymbolData cloneDataSymbol(
            SymbolData symb, String newName, int level)
            throws LanguageException {
        String _mod = symb.getModify();
        String _type = symb.getTypeName();
        String _name = newName;
        String _value = symb.getValue();

        SymbolData tmp = MakeSymbol.clone(symb, newName);


        return tmp;
    }

    @Override
    public String toString() {
        StringBuffer tmp = new StringBuffer();
        tmp.append("STRUCTS\n");
        for (SymbolData s : struct) {
            tmp.append(s.toString() + "\n");
        }

        tmp.append("MEMORY\n");
        for (SymbolData s : getMemory()) {
            tmp.append(s.toString() + "\n");
        }

        tmp.append("FUNCTIONS\n");
        for (Function f : defFuncs) {
            tmp.append(f.toString() + "\n");
        }

        return tmp.toString().trim();
    }

    public ArrayList<SymbolData> getMemory() {
        return memory;
    }

    public static Memory MakeMemoryClone(
            Memory model) {
        Memory tmp = new Memory();
        for (SymbolData s : model.memory) {
            tmp.addSymbol(s);
        }
        for (SymbolStructure s : model.struct) {
            tmp.addStructure(s);
        }
//        for (ProgramBlock s : model.funcs) {
//            tmp.addFunction(s);
//        }
        return tmp;
    }

    public Vector<Function> getDefFuncs() {
        return defFuncs;
    }
}
