/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 1/Jun/2008 - 5:10:51
 * @author Antonio M@nso
 */
package AlgolXXI.Core;

import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Memory.SymbolData;
import AlgolXXI.Core.Memory.SymbolStructure;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.ProgramTokens;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.ArrayList;

public class Function {

    /**
     * memoria local
     */
    private Memory localMemory;
    /**
     * fluxograma que manipula a memoria
     */
    private FunctionFluxo flux;
    private String nameFlux;
    private int ID;
    //para dar um ID a cada objecto do tipo bloco
    private static int IDFUNCTIONCOUNTER = 1;

    public Function(ProgramTokens prog, Memory mem, int level) throws LanguageException {
        localMemory = new Memory();
        addMemory(mem);
        ID = 0;
        flux = new FunctionFluxo(prog, mem, level,IDFUNCTIONCOUNTER++);
        nameFlux = flux.fluxName;
    }
    /**
     * construtor cópia
     * Isto serve para fazer cópias do bloco
     * Cada vez que se chama uma função faz-se um objecto novo
     * com este construtor
     * isto é necessário para a recursividade
     * @param other  b - modelo para copiar 
     * @param nextNode - proximo no executavel
     */
    public Function(Function other, ArrayList<SymbolData> param,
            NodeFluxo nextNode) throws LanguageException {
        //contruir uma nova memoria
        localMemory = new Memory();
        //copiar a memoria glogal
        addMemory(other.localMemory);
        //adicionar os parametros a memoria local
        addParameters(other.flux.getParams(),param); 
        ID = IDFUNCTIONCOUNTER++;
        
        //Copiar o codigo para actualizar o Fim do fluxo
        flux = new FunctionFluxo(other.flux, nextNode, getID());
        nameFlux = other.nameFlux;
        
    }
     // extrai os parametros da definição
    public void addParameters(ArrayList<SymbolData> def,ArrayList<SymbolData> param)
            throws LanguageException {
        if( def == null) return;
        for(int i=0; i < def.size() ; i++){
            //simbolo da definicao
            SymbolData d = def.get(i);
            //simbolo parametro
            SymbolData p = param.get(i);
            //novo simbolo            
            SymbolData n = Memory.cloneDataSymbol(p,d.getName(), p.getLevel());            
            //se for uma referencia
            if( d.getSymbolType() == Keyword.REFERENCIA)
               n.setObjectValue(p.getObjectValue());
            else
            //atribuir o valor
            n.setValue(p.getValue());
            //adicionar a memoria
            localMemory.addSymbol(n);
        }
    }

    /**
     * Adiciona a memoria principal á memoria local
     * fazendo referencias
     * @param mainMemory - memoria principal
     */
    public void addMemory(Memory mainMemory) throws LanguageException {
        for (SymbolStructure s : mainMemory.getStructures()) {
            localMemory.addStructure(s);
        }
        for (SymbolData s : mainMemory.getMemory()) {
            localMemory.addSymbol(s);
        }
        for (Function f : mainMemory.getDefFuncs()) {
            localMemory.addDefFunction(f);
        }
    }

    public FunctionFluxo getFlux() {
        return flux;
    }

    public Memory getMemory() {
        return localMemory;
    }

    public String getName() {
        return nameFlux;
    }
    
  public NodeFluxo getBegin(){
      return flux.getBegin();
  }
    @Override
    public String toString() {
        StringBuffer tmp = new StringBuffer();
        tmp.append("_______________________________________________\n");
        tmp.append("************* «" + nameFlux + " " + ID + "» **************\n");
        tmp.append("---------------------------------------------\n");
        tmp.append(localMemory.toString() + "\n");
        tmp.append("---------------------------------------------\n");
        tmp.append(flux.toString());
        tmp.append("\n_______________________________________________");
        return tmp.toString().trim();
    }

    public int getID() {
        return ID;
    }
}
