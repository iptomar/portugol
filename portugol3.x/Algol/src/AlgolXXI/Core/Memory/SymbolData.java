/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

/*
 * SymbolData.java
 *
 * Created on 11 de Outubro de 2005, 12:42
 *
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Utils.IteratorElemTokens;
import AlgolXXI.Core.Utils.LanguageException;

/**
 * Simbolos da linguagem (variaveis e constantes)
 * @author Ant�nio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
public abstract class SymbolData extends Symbol {

    public abstract SymbolData getClone(String newName) throws LanguageException;
    protected boolean isConst;
    /**
     * tipo de dados
     */
    protected int type;
    /**
     * nivel do simbolo
     * o valor 0 (ZERO) significa que é memoria principal
     */
    protected int level;
    /** 
     * Endereço da variavel - contem um identifacidor único
     * vai funcionar para os ponteiros poderem apontar~
     * para um simbolo
     */
    protected int endereco;
    /**
     * Numero sequencial para os endereços
     * O endereço 0 vai funcionar como NULL
     */
    protected static int NUMADRESS = 1;
    /**
     * Objecto que suporta os valores do simbolo
     */
    protected Object objectValue;
    /**
     * tipo de valor
     * VALOR
     * REFERENCIA
     */
    private boolean reference;
    //para as classes derivadas
    protected SymbolData(int typeData, boolean reference) throws LanguageException {
        //contrutor de symbolos
        super(typeData);
        this.reference = reference;

    }

    /**
     * tipo da variavel
     * @param t texto com o tipo
     * @return tipo
     */
    public static int getType(String t) {
        return Keyword.GetKey(t);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLevel(int l) {
        level = l;
    }
//   /**
//     * devolve os valores por defeito de cada tipo
//     * @param varType tipo
//     * @return valor
//     */
//    public static String getDefaultValue(int type){
//        return getDefaultValue(Keyword.GetTextKey(type));
//    }
//    /**
//     * devolve os valores por defeito de cada tipo
//     * @param varType tipo
//     * @return valor
//     */
//    public static String getDefaultValue(String type){
//       if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.REAL)) ) 
//            return  SymbolReal.defaultValue;
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.INTEIRO)) ) 
//            return  SymbolInteger.defaultValue;
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.LOGICO)) ) 
//            return SymbolLogic.defaultValue;
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CARACTER)) )  
//            return SymbolCharacter.defaultValue;
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.TEXTO)) ) 
//            return SymbolText.defaultValue;        
//        return "ERROR DEFAULT VALUE";
//    }
    public String getStringType() {
        return Keyword.GetTextKey(type);
    }

    /**
     * tipo da variavel
     * @return tipo
     */
    public int getType() {
        return type;
    }

    /**
     * n�vel
     * @return n�vel da varivel
     */
    public int getLevel() {
        return level;
    }

    /**
     * verifica se � constante
     * @return � constante
     */
    public boolean isConstant() {
        return this.isConst;
    }

    public void setValueType(int symbolType) {
        this.symbolType = symbolType;
    }

    /**
     * return object String
     * @return informa�ao  da variavel
     */
    public String toString() {
        StringBuffer str = new StringBuffer();
        if (isConstant()) {
            str.append("\tCONSTANTE ");
        } else {
            str.append("\tVARIAVEL \t");
        }
        str.append(getStringType() + " \t");
        str.append(getName() + " = ");
        str.append("«" + getValue() + "»\t");
        str.append("nivel\t:" + getLevel());
        return str.toString();
    }

    public String getModify() {
        if (isConst) {
            return Keyword.GetTextKey(Keyword.CONSTANTE);
        } else {
            return Keyword.GetTextKey(Keyword.VARIAVEL);
        }
    }

    public String getTypeName() {
        return Keyword.GetTextKey(this.type);
    }

    public Object getObjectValue() {
        return objectValue;
    }
    
    public void setObjectValue(Object obj) {
       objectValue = obj; 
    }

    public void setValue(LineTokens val) throws LanguageException {
        //limpar os { e }
        if (val.getElements().size() > 0 && val.getFirst().ttype == Keyword.LKEYWAY) {
            val.removeFirst();
        }
        if (val.getElements().size() > 0 && val.getLast().ttype == Keyword.RKEYWAY) {
            val.removeLast();
        }

        IteratorElemTokens it = new IteratorElemTokens(val);
        if (this instanceof SymbolDataComplex) {
            SymbolDataComplex obj = (SymbolDataComplex) this;

            for (int i = 0; i < obj.data.size(); i++) {
                LineTokens values = new LineTokens();
                //valores
                if (it.hasNext()) {
                    values = it.next();
                }
                //virgulas - consumir a virgula
                if (it.hasNext() && it.peek().getFirst().ttype == Keyword.COMMA) {
                    it.next();
                }
                //symbolos complexos
                if (obj.data.get(i) instanceof SymbolDataComplex) {
                    SymbolDataComplex complex = (SymbolDataComplex) obj.data.get(i);
                    complex.setValue(values);
                } //simbolos simples
                else if (values.getElements().isEmpty()) //valor por defeito
                {
                    obj.data.get(i).setValue("");
                } else //valor de atribuição
                {
                    obj.data.get(i).setValue(values.getElement(0).getValue());
                }
            }
        } else { // simbolo simples
            if (val.getElements().isEmpty()) //valor por defeito
            {
                setValue("");
            } else //valor de atribuição
            {
                setValue(val.getElement(0).getValue());
            }
        }

    }

    public void setValue(String value) throws LanguageException {
        if (this.isConst) {
            throw new LanguageException("O simbolo " + this.getName() + " e CONSTANTE, e por isso nao pode recer valores",
                    " Altere o simbolo para variavel");
        }
        if (this instanceof SymbolDataComplex) {
            ((SymbolDataComplex) this).setValue(value);
        } else {
            ((DataObject) objectValue).setValue(value);
        }
    }

    public String getValue() {
        if (this instanceof SymbolDataComplex) {
            return ((SymbolDataComplex) this).getValue();
        }
        return ((DataObject) objectValue).getValue();
    }

    public boolean isReference() {
        return reference;
    }
}
