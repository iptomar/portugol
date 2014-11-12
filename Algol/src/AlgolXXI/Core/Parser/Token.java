/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Parser;

import AlgolXXI.Core.Utils.Values;

/**
 * 
 * @author 
 * Luis Talento
 * Eduardo Dias
 * Joao Fernandes
 * Daniel Metelo
 * Lindomar Santos
 * Carlos Andrade
 * Osvaldo Spencer
 * Bruno Louro
 */
public class Token {

    public int ttype = 0;
    public String sval = "";
//    public int ival = 0; //deprecated

//    public double dval = 0.0; //deprecated

    //number value for int or double types..
    public double nval = 0.0;
    public int kw = 0;

    /**
     * Método que verifica se um caracter é um dígito
     * @param c Objecto do tipo char que contém o caracter a analisar
     * @return verdadeiro ou falso
     */
    public static boolean isDigit(char c) {
        if (c >= 48 && c <= 57) {
            return true;
        }
        return false;
    }

    /**
     * Método que verifica se um caracter é uma letra
     * @param c Objecto do tipo char que contém o caracter a analisar
     * @return verdadeiro ou falso
     */
    public static boolean isLetter(char c) {
        if ((c >= 64 && c <= 90) || c == '_' || (c >= 97 && c <= 122)) {
            return true;
        }
        return false;
    }

    public Token() {
        this.ttype = Keyword.DESCONHECIDO;
        this.sval = "";
        this.nval = 0;
        this.kw = 0;
    }

    public Token(int ttype, String sval) {
        this.ttype = ttype;
        this.sval = sval;
    }

    public Token(int ttype, double nval) {
        this.ttype = ttype;
        this.nval = nval;
    }

    public Token(int kw) {
        this.ttype = Keyword.KEYWORD;
        this.kw = kw;
    }

    public Token(String value) {
        this.ttype = Keyword.getFastKey(value);
        if (Values.IsBoolean(value)) {
            this.ttype = Keyword.LOGICO;
        } else if (Values.IsInteger(value)) {
            this.ttype = Keyword.INTEIRO;
        } else if (Values.IsReal(value)) {
            this.ttype = Keyword.REAL;
        } else if (Values.IsCharacter(value)) {
            this.ttype = Keyword.CARACTER;
        } else if (Values.IsString(value)) {
            this.ttype = Keyword.TEXTO;
        }
        if (Values.IsNumber(value)) {
            nval = Values.StringToDouble(value);
        } else if (Values.IsString(value)) {
            sval = Values.removeStringComas(value);
        } else {
            sval = value;
        }
        this.kw = this.ttype;
    }

    public String getValue() {
        if (sval.isEmpty()) {
            if (ttype == Keyword.INTEIRO) {
                return (int) nval + "";
            } else {
                return nval + "";
            }
        }
        if (ttype == Keyword.TEXTO || ttype == Keyword.CARACTER) {
            return "\"" + sval + "\"";
        }
        return sval;
    }
    //Realizado por David Jardim && Edou Suilen
    /**
     * Selector para o tipo de token
     * @return Valor do tipo de token
     */
    public int getTtype() {
        return ttype;
    }

    /**
     * Adicionado por Hugo Lobo, 17/05/08
     * Método que verifica se o token é uma operação aritmética
     * @return verdadeiro se for uma operação§Ã£o aritmética©tica, falso caso contrário
     */
    public boolean IsArithmeticOperator() {
        if (this.ttype == Keyword.OP_DIV || this.ttype == Keyword.OP_MINUS ||
                this.ttype == Keyword.OP_MUL || this.ttype == Keyword.OP_PLUS) {
            return true;
        }
        return false;
    }

    /**
     * Adicionado por António Manso 
     * @return
     */
    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
//        txt.append(Keyword.getFastKeyword(kw) + " ");
//        txt.append(Keyword.getFastKeyword(ttype) + " [");
        txt.append(this.getValue());
//        txt.append("] ");
        return txt.toString();

    }

    public boolean isType() {
        return kw == Keyword.LOGICO ||
                kw == Keyword.INTEIRO ||
                kw == Keyword.REAL ||
                kw == Keyword.CARACTER ||
                kw == Keyword.TEXTO ||
                kw == Keyword.VAZIO;
    }
}
