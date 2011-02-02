/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 2/Jun/2008 - 18:43:22
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Utils.LanguageException;

public class DataObject {

    /**
     * objecto que contem o valor
     */
    Object valueObject;
    /**
     * tipo de valor (INTEIRO ,REAL . . .
     */
    int typeValue;

    /**
     * Construot
     * @param typeValue - tipo de valor
     * @param value - valor de inicializaçao - se for vazio controi por defeito
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public DataObject(int typeValue, String value) throws LanguageException {
        this.typeValue = typeValue;
        valueObject = createObject(value);
    }
     /**
     * Construot
     * @param typeValue - tipo de valor
     * @param value - objecto de referencia
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public DataObject(int typeValue, Object value) throws LanguageException {
        this.typeValue = typeValue;
        valueObject = value;
    }
 
    
    /**
     * cria um novo objecto de valor com o valor do parametro
     * @param value -valor do objecto
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public void setValue(String value) throws LanguageException {
        valueObject = createObject(value);
    }

    /**
     * retorna o objecto de valor
     * @return
     */
    public Object getObject() {
        return valueObject;
    }

    /**
     * altera o objecto de valor
     * @param value
     */
    public void setObject(Object value) {
        valueObject = value;
    }
    //--------------------------------------------------------------------------
    //------------------                                     -------------------
    //------------------                                     -------------------
    //--------------------------------------------------------------------------
    /**
     * retorna o valor do objecto
     * @return valor do objecto em string
     */
    public String getValue() {
        switch (typeValue) {
            case Keyword.LOGICO:
                return getStringLogic(((Boolean) valueObject).booleanValue());
            default:
                return valueObject.toString();
        }
    }
    //--------------------------------------------------------------------------
    //------------------                                     -------------------
    //------------------                                     -------------------
    //--------------------------------------------------------------------------
    private Object createObject(String value) throws LanguageException {
        if (value.isEmpty()) {
            return createDefaultObject();
        }
        try {
            switch (typeValue) {
                case Keyword.INTEIRO:
                    return new Integer(value);
                case Keyword.REAL:
                    return new Double(value);
                case Keyword.TEXTO:
                    return new String(value);
                case Keyword.CARACTER:
                    value = getEscapedString(value);
                    return new Character(value.charAt(0));
                case Keyword.LOGICO:
                    return new Boolean(getLogicString(value));
            }
        } catch (Exception e) {
        }
        throw new LanguageException("O valor " + value + "não e valido",
                "verifique o valor");

    }
    //--------------------------------------------------------------------------
    //------------------                                     -------------------
    //--------------------------------------------------------------------------
    private Object createDefaultObject() {
        switch (typeValue) {

            case Keyword.INTEIRO:
                return new Integer(0);
            case Keyword.REAL:
                return new Double(0.0);
            case Keyword.TEXTO:
                return new String("");

            case Keyword.CARACTER:
                return new Character(' ');
            case Keyword.LOGICO:
                return new Boolean(false);
            default:
                return "ERROR";
        }

    }

    public String tostString() {
        return getValue();
    }
    //--------------------------------------------------------------------------
    //------------------        s t r i n g   l o g i c      -------------------
    //--------------------------------------------------------------------------
    public static String getStringLogic(boolean logic) {
        if (logic) {
            return Keyword.GetTextKey(Keyword.VERDADEIRO);
        } else {
            return Keyword.GetTextKey(Keyword.FALSO);
        }
    }

    public static boolean getLogicString(String logic) throws LanguageException {
        if (getStringLogic(true).equalsIgnoreCase(logic)) {
            return true;
        }
        if (getStringLogic(false).equalsIgnoreCase(logic)) {
            return false;
        }
        throw new LanguageException(logic + " nao e um valor logico",
                "verifique o valor");
    }
    //--------------------------------------------------------------------------
    //------------------        s t r i n g   l o g i c      -------------------
    //--------------------------------------------------------------------------
    public static boolean isEscapedChar(String str) {
        if (str.length() < 2 || str.charAt(0) != '\\') {
            return false;
        }
        String escape = "untrf\\\"";
        return escape.contains("" + str.charAt(1));
    }

    public static String getEscapedString(String str) {
        StringBuffer txt = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\\' && i < str.length() - 1) {
                i++;// ir para o proximo caracter

                char ch = str.charAt(i);
                switch (ch) {
                    case 'n':
                    case 'N':
                        txt.append("\n");
                        break;
                    case 't':
                    case 'T':
                        txt.append("\t");
                        break;
                    case 'r':
                    case 'R':
                        txt.append("\r");
                        break;
                    case 'f':
                    case 'F':
                        txt.append("\n");
                        break;
                    case '"':
                        txt.append("\"");
                        break;
                    case '\\':
                        txt.append("\\");
                        break;
                    case 'u':
                        txt.append(getUnicodeString(str.substring(2)));
                        i += 4;
                    default:
                        txt.append("\\" + ch);
                }
            } else {
                txt.append(str.charAt(i));
            }
        }
        return txt.toString();
    }

    public static String getUnicodeString(String code) {
        return getUnicodeString(Integer.valueOf(code));
    }

    public static String getUnicodeString(int code) {
        // NOTA:
        // refazer este codigo
        return String.format("%c", code);
    }
}
