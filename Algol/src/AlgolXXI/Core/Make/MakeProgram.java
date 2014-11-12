/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 25/Mai/2008 - 4:05:40
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Make;

import AlgolXXI.Core.Function;
import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Memory.SymbolStructure;
import AlgolXXI.Core.NodeFluxo;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.ProgramTokens;
import AlgolXXI.Core.Utils.IteratorProgramTokens;

public class MakeProgram {

    /**
     * memória principal do programa
     * comtém as variaveis e as definiçoes das estruturas
     */
    private Memory mainMemory = new Memory();
    Function main = null;

    public MakeProgram(String program) throws Exception {
        IteratorProgramTokens it = new IteratorProgramTokens(program);
        int MEMORYLEVEL = 0;
        while (it.hasNext()) {
            ProgramTokens prog = it.next();
            switch (prog.getProgramType()) {
                case Keyword.DEFINIR:
                    MakeSymbol.Make(prog, mainMemory, MEMORYLEVEL);
                    break;
                case Keyword.ESTRUTURA:
                    SymbolStructure s = new SymbolStructure(prog, mainMemory, MEMORYLEVEL);
                    mainMemory.addStructure(s);
                    break;
                case Keyword.DEFINIRFUNCAO:
                    Function func = new Function(prog, mainMemory, MEMORYLEVEL + 1);
                    mainMemory.addDefFunction(func);
                    if (func.getName().equalsIgnoreCase(Keyword.GetTextKey(Keyword.PROGRAMA))) {
                        main = func;
                    }
                    break;
            }
        }
    }

    public Memory getMainMemory() {
        return mainMemory;
    }

    public Function getFunction(NodeFluxo node) {
        for (Function b : mainMemory.getDefFuncs()) {
            if (b.getName().equalsIgnoreCase(node.getName())) {
                return b;
            }
        }
        return null;
    }

    public Memory getMemoryFunction(NodeFluxo node) {
        for (Function b : mainMemory.getDefFuncs()) {
            if (b.getName().equalsIgnoreCase(node.getName())) {
                return b.getMemory();
            }
        }
        return null;
    }

    public Function getMainFunction() {
        return main;
    }

    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        txt.append(mainMemory.toString());
        return txt.toString();
    }
}
