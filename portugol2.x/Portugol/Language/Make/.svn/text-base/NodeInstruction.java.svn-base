/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

/*
/*
 * NodeInstruction.java
 *
 * Created on 4 de Outubro de 2005, 16:10
 *
 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Keyword;
import Portugol.Language.Utils.CodeLine;
import VisualFluxogram.Patterns.Forma;

/**
 * Representa um nodo do fluxograma
 * @author António M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Informática<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Politécnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
public class NodeInstruction {
    //posição no editor texto
    /**
     * numero do caracter no programa (isto ´necessário para fazer a correspondencia com o editor de texto)
     */
    protected   int     charNum;
    /**
     * nível do nodo no fluxograma
     */
    protected   int     level;
    /**
     * posição x do simbolo
     */
    protected   double  positionX;
    /**
     * posição y do simbolo
     */
    protected   double  positionY;
    /**
     * representação gráfica do simbolo
     */
    protected   Forma   visual;
    /**
     * texto
     */
    protected   String  text;
    /**
     * tipo
     */
    protected   int     type;
    
    
    /**
     * ponteiro par o próximo nodo
     */
    protected   NodeInstruction     next;
    /**
     * ponteiro par o nodo se verdadeiro
     */
    protected   NodeInstruction     ifTrue;
    /**
     * ponteiro par o nodo se falso
     */
    protected   NodeInstruction     ifFalse;
    
    /**
     * construtor
     * @param instruction linha de código
     * @param charNum nº do caracter da primeira letra
     * @param level nível do código
     */
    public NodeInstruction(String instruction, int charNum , int level) {
        this.text =  CodeLine.GetNormalized(instruction);
        this.type = Keyword.GetKey(instruction);
        this.level = level;
        this.positionX = 0;
        this.positionY = 0;
        this.next =null;
        this.ifTrue =null;
        this.ifFalse =null;
        this.charNum = charNum;
        this.visual = null;
    }
    
    
    /**
     * construtor cópia
     * @param node no original
     */
    public NodeInstruction(NodeInstruction node) {
        this.text =  node.text;
        this.type = node.type;
        this.level = node.level;
        this.next = node.next;
        this.positionX = node.positionX;
        this.positionY = node.positionY;
        this.ifTrue = node.ifTrue;
        this.ifFalse = node.ifFalse;
        this.charNum = node.charNum;
        visual = node.visual;
    }
    
    /**
     * atribui uma forma ao nodo
     * @param f forma visual do nodo
     */
    public void SetForm(Forma f){
        visual=f;
    }
    /**
     * devolve a forma do nodo
     * @return forma visual
     */
    public Forma GetForm(){
        return visual;
    }
    
    /**
     * retorna o tipo de nodo
     * @return tipo
     */
    public int GetType(){
        return type;
    }
    
    /**
     * altera o tipo do nodo
     * @param newType novo tipo
     */
    public void SetType(int newType){
        type = newType;
    }
    
    /**
     * retorna o texto
     * @return texto
     */
    public String GetText(){
        return text;
    }
    
    /**
     * retorna o texto
     * @return texto
     */
    public String GetIntruction(){
        StringBuffer str = new StringBuffer();
        str.append(Keyword.GetTextKey(GetType()) );
        str.append("\t");
        str.append(text);
        return str.toString();
    }
    
    /**
     * altera o texto
     * @param newText novo texto
     */
    public void SetText(String newText){
        text = CodeLine.GetNormalized(newText);
    }
    
    /**
     * altera o ponteiro para o proximo nodo
     * @param n novo no
     */
    public void SetNext(NodeInstruction n){
        next = n;
    }
    
    /**
     * altera o ponteiro para o nodo verdadeiro
     * @param n no
     */
    public void SetIfTrue(NodeInstruction n){
        ifTrue = n;
    }
    
    /**
     * altera o ponteiro para o nodo falso
     * @param n no
     */
    public void SetIfFalse(NodeInstruction n){
        ifFalse = n;
    }
    
    
    /**
     * retorna o proximo nodo
     * @return retorna o proximo nodo
     */
    public NodeInstruction GetNext(){
        return next;
    }
    
    
    
    /**
     * retorna o nodo se verdadeiro
     * @return retorna o nodo se verdadeiro
     */
    public NodeInstruction GetIfTrue(){
        return ifTrue;
    }
    
    /**
     * retorna o nodo se falso
     * @return retorna o nodo se falso
     */
    public NodeInstruction GetIfFalse(){
        return ifFalse;
    }
    
    /**
     * retorna o nivel do nodo
     * @return nivel
     */
    public int GetLevel(){
        return level;
    }
    /**
     * altera o nivel
     * @param lv novo nivel
     */
    public void  SetLevel(int lv){
        level=lv;
    }
    /**
     * incrementa o nivel do nodo
     */
    public void  IncrementLevel(){
        level++;
    }
    
    /**
     * decrementa o nivel do nodo
     */
    public void  DecrementLevel(){
        level--;
    }
    
    /**
     * retorna a posicao x
     * @return retorna a posicao x
     */
    public double GetPositionX(){
        return positionX;
    }
    /**
     * Atera a posicao x
     * @param p novo x
     */
    public void  SetPositionX(double p){
        positionX = p;
    }
    /**
     * retorna a posicao y
     * @return retorna a posicao x
     */
    public double GetPositionY(){
        return positionY;
    }
    /**
     * Atera a posicao y
     * @param p posicao y
     */
    public void  SetPositionY(double p){
        positionY = p;
    }
    
    
    /**
     * numero do caracter
     * @return numero do caracter
     */
    public int GetCharNum(){
        return charNum;
    }
    /**
     * altera o numero do caracter
     * @param cn novo numero do caracter
     */
    public void  SetCharNum(int cn){
        charNum=cn;
    }
    
    /**
     * se é um nodo que abre um ciclo
     * @return se for um no que abre um ciclo
     */
    public boolean IsNodeOpen(){
        return
                type == Keyword.INICIO ||
                type == Keyword.SE     ||
                type == Keyword.PARA   ||
                type == Keyword.ENQUANTO ||
                type == Keyword.FAZ    ||
                type == Keyword.ESCOLHE ||
                type == Keyword.REPETE ;
    }
    
    /**
     * se for um no que fecha um ciclo
     * @return se for um no que fecha um ciclo
     */
    public boolean IsNodeClose(){
        return
                type == Keyword.FIM ||
                type == Keyword.FIMSE ||
                type == Keyword.PROXIMO ||
                type == Keyword.FAZENQUANTO ||
                type == Keyword.FIMENQUANTO ||
                type == Keyword.FIMESCOLHE ||
                type == Keyword.ATE ;
        
    }
    
    /**
     * texto
     * @return texto
     */
    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(level + "\t");
        str.append("[" + positionY + "," + positionX + "]\t");
        str.append(Keyword.GetTextKey(GetType()) );
        while( str.length() < 35)
            str.append(" ");
        str.append("\t");
        for(int i=0; i< level ; i++)
            str.append("\t");
        str.append(text);
        
        return str.toString();
    }
    
}
