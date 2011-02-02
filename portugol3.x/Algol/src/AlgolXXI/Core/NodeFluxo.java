/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 1/Jun/2008 - 8:27:06
 * @author Antonio M@nso
 */
package AlgolXXI.Core;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.Token;

public class NodeFluxo {

    /**
     * numero do caracter no programa 
     */
    protected int charNum;
    /**
     * nivel do nodo no fluxograma
     */
    private int level;
    /**
     * numero de vezes que o nodo foi executado
     */
    protected int numExecutions;
    private LineTokens instruction;
    /**
     * tipo
     */
    protected int type;
    /**
     * ponteiro par o pr�ximo nodo
     */
    protected NodeFluxo next;
    /**
     * ponteiro par o nodo se verdadeiro
     */
    protected NodeFluxo ifTrue;
    /**
     * ponteiro par o nodo se falso
     */
    protected NodeFluxo ifFalse;
    /**
     * Identificador do bloco (fluxograma) a que pertence o Nodo
     */
    private int blockID;
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    /**
     * construtor
     * @param instruction linha de c�digo
     * @param charNum n� do caracter da primeira letra
     * @param level n�vel do c�digo
     */
    public NodeFluxo(LineTokens line, int level, int ID) {
        instruction = line;
        this.level = level;
        this.type = line.getTypeIntruction();
        this.numExecutions = 0;
        this.next = null;
        this.ifTrue = null;
        this.ifFalse = null;
        this.charNum = line.getLineCode();
        this.blockID = ID;
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    /**
     * construtor c�pia
     * @param node no original
     */
    public NodeFluxo(NodeFluxo node, int ID) {
        this.instruction = node.instruction;
        this.type = node.type;
        this.level = node.level;
        this.next = node.next;
        this.numExecutions = node.numExecutions;
        this.ifTrue = node.ifTrue;
        this.ifFalse = node.ifFalse;
        this.charNum = node.charNum;
        this.blockID = ID;
    }

    public String getName() {
        return instruction.getElements().get(0).getValue();
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (Token t : getInstruction().getElements()) {
            txt.append(t.getValue() + " ");
        }
        return txt.toString();

    }
    //-------------------------------------------------------------------------
    /**
     * altera o ponteiro para o proximo nodo
     * @param n novo no
     */
    public void setNext(NodeFluxo n) {
        next = n;
    }
    //-------------------------------------------------------------------------
    public NodeFluxo getNext() {
        return next;
    }
    //-------------------------------------------------------------------------
    public int getType() {
        return type;
    }

    public int getBlockID() {
        return blockID;
    }

    public LineTokens getInstruction() {
        return instruction;
    }

    public String getText() {
        return instruction.toString();
    }

    public int getLevel() {
        return level;
    }

    public int getCharNum() {
        return charNum;
    }
}
