/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

/*
 * Symbol.java
 *
 * Created on 11 de Outubro de 2005, 12:42
 *
 */

package Portugol.Language.Parse;

import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;


/**
 * Simbolos da linguagem (variaveis e constantes)
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
public class Symbol {
    /**
     * tipo Vazio
     */
    public final static int VAZIO     = 0;
    /**
     * tipo Logico
     */
    public final static int LOGICO     = 1;
    /**
     * tipo Real
     */
    public final static int REAL      = 2;
    /**
     * tipo Inteiro
     */
    public final static int INTEIRO   = 3;
    /**
     * tipo caracter (símbolo da tabela ASCII )
     */
    public final static int CARACTER = 4;
    /**
     * tipo Texto (strings)
     */
    public final static int TEXTO      = 5;
    /**
     * tipo Desconhecido
     */
    public final static int DESCONHECIDO    = -1;
    /**
     * se é constante ou variavel
     */
    protected boolean isConst;
    /**
     * tipo de dados
     */
    protected int type;
    /**
     * nome do simbolo
     */
    protected String name;
            
    /**
     * valor
     */
    protected String value;
    /**
     * nivel do simbolo
     */
    protected int level;
    /**
     * Cria um novo símbolo
     * @param modify VARIAVEL ou CONSTANTE
     * @param type tipo de DADOS (logico, real, etc.)
     * @param name nome da variavel
     * @param value valor da variavel
     * @param level nivel
     * @throws Portugol.Language.Utils.LanguageException erro nos parametros do construtor
     */
    public Symbol(String modify,String type,String name, String value, int level)
    throws LanguageException {
        if(modify.equalsIgnoreCase(Keyword.GetTextKey( Keyword.CONSTANTE)))
            this.isConst = true;
        else
            this.isConst =false;
        
        this.type = getType(type);
        
        this.name = name.trim();
        
        if(value.length()==0)
            this.value = this.getDefaultValue(this.type);
        else
            this.value = getNormalizedValue(value);
        
        this.level = level;
    }
    
    public Symbol(int type, String name){
        this.isConst =false;
        
        this.type = type;
        
        this.name = name.trim();
        
        this.value = this.getDefaultValue(this.type);
        
        this.level = 0;
    }
    
    protected Symbol(){
        // somente para as classes derivadas (ex. array)
    }
    
    public boolean IsCompatible(int type){
        if( this.type == type) return true;
        if( this.type == Symbol.REAL && type==Symbol.INTEIRO) return true;
        if( this.type == Symbol.TEXTO && type==Symbol.CARACTER) return true;
        return false;
    }
    /**
     * tipo da variavel
     * @param t texto com o tipo
     * @return tipo
     */
    public static  int getType(String t){
        if(t.equalsIgnoreCase(Keyword.GetTextKey(Keyword.LOGICO ))) return LOGICO;
        if(t.equalsIgnoreCase(Keyword.GetTextKey(Keyword.TEXTO ))) return TEXTO;
        if(t.equalsIgnoreCase(Keyword.GetTextKey(Keyword.INTEIRO ))) return INTEIRO;
        if(t.equalsIgnoreCase(Keyword.GetTextKey(Keyword.REAL ))) return REAL;
        if(t.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CARACTER ))) return CARACTER;
        return DESCONHECIDO;
    }
    
    public void setType(int type){
        this.type=type;
    }
    
    public void setLevel(int l){
        level=l;
    }
    
    /**
     * devolve os valores por defeito de cada tipo
     * @param varType tipo
     * @return valor
     */
    public static  String getDefaultValue(int varType){
        if(varType == LOGICO) return Values.FALSO;
        if(varType == TEXTO ) return new String("\"\"");
        if(varType == INTEIRO ) return "0";
        if(varType == REAL ) return "0.0";
        if(varType == CARACTER) return new String("\"_\"");
        return "ERRO";
    }
    
    /**
     * devolve os valores por defeito de cada tipo e 1 para os numericos
     * @param varType tipo
     * @return valor
     */
    public static  String getSafeDefaultValue(int varType){
        if(varType == LOGICO) return Values.FALSO;
        if(varType == TEXTO ) return new String("\"\"");
        if(varType == INTEIRO ) return "1";
        if(varType == REAL ) return "1.0";
        if(varType == CARACTER) return new String("\" \" ");
        return "ERRO";
    }
    /**
     * devolve os valores por defeito de cada tipo
     * @param varType tipo
     * @return valor
     */
    public static  String getDefaultValue(String varType){
        return getDefaultValue(getType(varType));
    }
    
    /**
     * verifica se é inteiro ou real
     * @return variavel inteira ou real
     */
    public  boolean isNumber(){
        if( type == REAL) return true;
        if(type == INTEIRO) return true;
        return false;
    }
    
    /**
     *
     * verifica se a variavel é do tipo caracter ou Texto
     * @return tipo caracter ou Texto
     */
    public  boolean isString(){
        if( type == TEXTO) return true;
        if(type == CARACTER) return true;
        return false;
    }
    
    /**
     * Atribui um novo valor à variável
     * @param val novo valor
     * @throws portugol.Language.Evaluate.LanguageException Erros
     */
    protected void setCanonicalValue(String val) throws LanguageException{
        if(this.isString())
            value = getNormalizedValue("\"" + val + "\"");
        else
            value = getNormalizedValue(val);
    }
    
    /**
     * Atribui um novo valor à variável sem qualquer verificação
     * @param val novo valor
     */
    public void setHardValue(String val){
        value = val;
    }

    /**
     * Atribui um novo valor à variável
     * @param val novo valor
     * @throws Portugol.Language.Utils.LanguageException erro
     */
    public void setValue(String val) throws LanguageException{
        if( this.isConst)
            throw new LanguageException
                    ("O símbolo " + this.name + " é CONSTANTE, e por isso não pode recer valores",
                    " Altere o simbolo para variavel");
        value = getNormalizedValue(val);
    }
    //
    protected  String getNormalizedValue(String val)throws LanguageException{
        val = val.trim();
        
        if( type == TEXTO){
            if(val.length() <2  || !Values.IsString(val))
                throw new LanguageException(
                        0, "", name +" é uma variavel TEXTO",  "\""+val + "\" não é um Texto");
            return val;
        } else if( type == CARACTER ) {
            
            if( !Values.IsString(val) || val.length() != 3)
                throw new LanguageException(
                        0, "", name +" é uma variavel CARACTER",  "\""+val + "\" não é um caracter");
            return  val;
        } else if( type == LOGICO ) {
            if( !Values.IsBoolean(val))
                throw new LanguageException(
                        0, "", name +" é uma variavel LOGICO", "\""+val + "\"  não é um valor lógico válido");
             return val.toUpperCase();
        } else if( type == REAL ) {
            if(!Values.IsNumber(val))
                throw new LanguageException(
                        0, "", name +" é uma variavel REAL", "\""+val + "\"  não é um número válido");
            
            double d = Values.StringToDouble(val);
            return Values.DoubleToString(d);
        } else if( type == INTEIRO ) {
            if(!Values.IsValue(val))
                throw new LanguageException(
                        0, "", name +" é uma variavel INTEIRO", "\""+val + "\" não é um número válido");
            int i = Values.StringToInteger(val);
            return  "" + i;
        }
        return "ERRO TIPO DE VARIAVEL DESCONHECIDO";
    }
    
    
    /**
     * retorna o valor
     * @return valor
     */
    public String getValue(){
        return value;
    }
    /**
     * devolve os valores por defeito de cada tipo
     * @return valor por defeito
     */
    public String getDefaultValue(){
        return getDefaultValue( this.type);
    }
    
    
      /**
     * devolve os valores por defeito de cada tipo e os numericos a 1 por causa das divisoes
     * @return valor por defeito
     */
    public String getSafeDefaultValue(){
        return getSafeDefaultValue( this.type);
    }
    
     public static  String getStringType(int t){
        if(t == LOGICO)return "LOGICO";
        if(t == TEXTO) return "TEXTO";
        if(t == CARACTER) return "CARACTER";
        if(t == REAL) return "REAL";
        if(t == INTEIRO) return "INTEIRO";
        return "DESCONHECIDO";
    }
    public String getStringType(){
        if(type == LOGICO)return "LOGICO";
        if(type == TEXTO) return "TEXTO";
        if(type == CARACTER) return "CARACTER";
        if(type == REAL) return "REAL";
        if(type == INTEIRO) return "INTEIRO";
        return "DESCONHECIDO";
    }
    
    /**
     * tipo da variavel
     * @return tipo
     */
    public int getType(){
        return type;
    }
    
    /**
     * gets nome
     * @return nome
     */
    public String getName(){
        return name;
    }
    
    /**
     * nível
     * @return nível da varivel
     */
    public int getLevel(){
        return level;
    }
    
    /**
     * verifica se é constante
     * @return é constante
     */
    public boolean isConstant(){
        return this.isConst;
    }
    
    /**
     * return object String
     * @return informaçao  da variavel
     */
    public String toString(){
        StringBuffer str = new StringBuffer();
        if( isConstant())
            str.append("\tCONSTANTE\n");
        else
            str.append("\tVARIAVEL\n");
        str.append("nome\t:" + getName() + "\n");
        str.append("valor\t:" +getValue() + "\n");
        str.append("tipo\t:" + getStringType()+ "\n");
        str.append("nivel\t:" +getLevel()+ "\n");
        return str.toString();
    }
    
    /**
     * nome =
     * @param var varivel a comparar
     * @return nome = paramentro
     */
    public boolean nameEqual(String var){
        return var.equalsIgnoreCase(name);
    }
    
    /**
     * verifica se o valor e o tipo são compativeis
     * @param type1 type de dados
     * @param value valor
     * @return compatibilidade
     */
    public static boolean IsCompatible(int type1, String value){
        if(type1 == Symbol.REAL && Values.IsNumber(value)) return true;
        if(type1 == Symbol.INTEIRO && Values.IsInteger(value)) return true;
        if(type1 == Symbol.LOGICO && Values.IsBoolean(value)) return true;
        if(type1 == Symbol.CARACTER && Values.IsCharacter(value)) return true;
        if(type1 == Symbol.TEXTO && Values.IsString(value)) return true;
        return false;
    }
    
    /**
     * igual
     * @param other segundo argumento
     * @return são iguais?
     */
    public boolean equal(Symbol other){
        if( !nameEqual(other.name)) return false;
        return other.level == level;
    }
}
