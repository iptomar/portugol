/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 1/Jun/2008 - 8:24:14
 * @author Antonio M@nso
 */
package AlgolXXI.Core;

import AlgolXXI.Core.Make.MakeSymbol;
import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Memory.SymbolData;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.ProgramTokens;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorLineTokens;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.Vector;

public class FunctionFluxo {

    /**
     * Memoria do fluxo
     */
    protected Memory memory;
    /**
     * Parametros do fluxo 
     */
    private Vector<SymbolData> params;
    /**
     * retorno do fluxo
     */
    protected SymbolData rets;
    /**
     * nome do fluxo
     */
    protected String fluxName;
    /**
     * apontador para o inicio do fluxograma
     */
    private NodeFluxo begin;
    /**
     * no final do fluxograma
     */
    private NodeFluxo end;

    public FunctionFluxo(ProgramTokens prog, Memory mem, int level, int ID) throws LanguageException {
        this.memory = mem;
        params = new Vector<SymbolData>();
        getDefinition(prog.getLine(0), level);
        begin = null;
        end = null;

        for (int i = 0; i < prog.getLines().size(); i++) {
            LineTokens line = prog.getLine(i);
            NodeFluxo node = makeNode(line, level, ID);
            if (begin == null) {
                begin = node;
                end = node;
            }
            end.setNext(node);
            end = node;
        }

        ExpandFluxogram();
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------  
    public FunctionFluxo(FunctionFluxo flux, NodeFluxo nextEndNode, int ID)
            throws LanguageException {
        //memoria do bloco
        this.memory = flux.memory;
        //----------------------------------------------------------------
        //fazer o start
        this.begin = null;
        //para percorrer o fluxo
        NodeFluxo tmp = flux.begin;
        //no anterior
        NodeFluxo previous = null;
        //copiar o fluxograma
        // NOTA:
        // rafazer este ciclo quando houver desvio de fluxo
        while (tmp != null) {
            NodeFluxo newNode = new NodeFluxo(tmp, ID);
            if (begin == null) {
                begin = newNode;
                previous = newNode;
            } else {
                //se for um no terminal ligar para o nextEndNode
                if (newNode.getNext() == null) {
                    newNode.setNext(nextEndNode);
                }
                previous.setNext(newNode);
                previous = newNode;
            }
            //passar ao proximo
            tmp = tmp.getNext();
        }
        
        end = nextEndNode;
        begin.type = Keyword.INICIO;
        previous.type =  Keyword.FIM;
        previous.setNext(end);
        
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    private void ExpandFluxogram() {
        NodeFluxo tmp = getBegin();
        while (tmp != end) {
            switch (tmp.getType()) {
//                 //
//                 case Keyword.RETORNAR:
//                     tmp.setNext(end);
//                     break;
            }

            tmp = tmp.getNext();
        }
    }

    private NodeFluxo makeNode(LineTokens line, int level, int ID) {
        IteratorLineTokens it = new IteratorLineTokens(line);
        switch (line.getTypeIntruction()) {
            case Keyword.DEFINIR:
                if (it.next().kw != Keyword.CONSTANTE && it.next().kw != Keyword.VARIAVEL) {
                    Token tok = new Token(Keyword.GetTextKey(Keyword.VARIAVEL));
                    line.getElements().add(0, tok);
                }
                break;
        }
        //remover o ultimo \n
        line.getElements().remove(line.getElements().size() - 1);
        return new NodeFluxo(line, level,ID);
    }

    private void getDefinition(LineTokens line, int level) throws LanguageException {
        IteratorLineTokens it = new IteratorLineTokens(line);
        Token tok = it.next();
        fluxName = tok.getValue();
        if (tok.kw == Keyword.PROGRAMA) {
            return;
        // contruir o retorno
        }
        rets = MakeSymbol.makeNewDataSymbol(Keyword.GetTextKey(Keyword.VARIAVEL),
                tok.getValue(), "_returnValue", "", level);
        //nome do fluxo
        tok = it.next();
        fluxName = tok.getValue();
        //parentesis
        tok = it.next(); //parentesis
        LineTokens param = new LineTokens();
        while (true) {
            tok = it.next();
            if (tok.ttype == Keyword.RPARENT) {
                defineParam(param, level);
                break;
            }
            if (tok.ttype == Keyword.COMMA) {
                defineParam(param, level);
                param = new LineTokens();
            } else {
                param.addTokens(tok);
            }
        }
    }

    public void defineParam(LineTokens l, int level) throws LanguageException {
        IteratorLineTokens it = new IteratorLineTokens(l);
        int symbolType = Keyword.VALOR;
        Token type = it.next();
        Token name = it.next();
        Token value = new Token();
        if (name.ttype == Keyword.REFERENCIA) {
            symbolType = Keyword.REFERENCIA;
            name = it.next();
        }

        // contruir o parametro
        SymbolData param = MakeSymbol.makeNewDataSymbol(Keyword.GetTextKey(Keyword.VARIAVEL),
                type.getValue(), name.getValue(), "", level);
        param.setValueType(symbolType);
        getParams().add(param);
    }

    public String toString() {
        StringBuffer txt = new StringBuffer();
        NodeFluxo tmp = getBegin();
        while (tmp != end) {
            txt.append(tmp.toString() + "\n");
            tmp = tmp.getNext();
        }
        txt.append(end.toString());
        return txt.toString().trim();
    }

    public Vector<SymbolData> getParams() {
        return params;
    }

    public NodeFluxo getBegin() {
        return begin;
    }
}
