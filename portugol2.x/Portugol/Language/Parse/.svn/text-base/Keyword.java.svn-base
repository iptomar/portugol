/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

/*
 * Keyword.java
 *
 * Created on 2 de Junho de 2006, 14:30
 *
 *
 */

package Portugol.Language.Parse;

import java.util.Hashtable;

/**
 * Palavras reservadas da linguagem
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
public class Keyword {
    
    public static final int DESCONHECIDO  = 0;
    public static final int INICIO        = 1;
    public static final int FIM           = 2;
    public static final int LER           = 3;
    public static final int ESCREVER      = 4;
    public static final int CALCULAR      = 5;
    public static final int DEFINIR       = 6;
    public static final int CONECTOR      = 7;
    public static final int SE            = 8;
    public static final int SENAO         = 9;
    public static final int FIMSE         = 10;
    
    public static final int PARA          = 11;
    public static final int PROXIMO       = 12;
    public static final int PASSO         = 13;
    
    public static final int ENQUANTO      = 14;
    public static final int FIMENQUANTO   = 15;
    
    public static final int REPETE        = 16;
    public static final int ATE           = 17;
    
    public static final int FAZ           = 18;
    public static final int FAZENQUANTO   = 19;
    
    public static final int ESCOLHE       = 20;
    public static final int CASO          = 21;
    public static final int DEFEITO       = 22;
    public static final int FIMESCOLHE    = 23;
    public static final int CONSTANTE     = 24;
    public static final int VARIAVEL      = 25;
    
    public static final int ENTAO         = 26;
    public static final int DE            = 27;
    
    
    public static final int VAZIO           = 28;
    public static final int LOGICO          = 29;
    public static final int TEXTO           = 30;
    public static final int INTEIRO         = 31;
    public static final int REAL            = 32;
    public static final int CARACTER        = 33;
    
    
    
    /**
     * sinal de atribuição
     */
    public static final String ATRIBUI = "<-";
    
    static private final String txt[] ={
        "DESCONHECIDO",
        "INICIO",
        "FIM",
        "LER",
        "ESCREVER",
        "CALCULAR",
        "DEFINIR",
        "CONECTOR",
        "SE",
        "SENAO",
        "FIMSE",
        "PARA",
        "PROXIMO",
        "PASSO",
        "ENQUANTO",
        "FIMENQUANTO",
        "REPETE",
        "ATE",
        "FAZ",
        "ENQUANTO", //faz . . . .enquanto
        "ESCOLHE",
        "CASO",
        "DEFEITO",
        "FIMESCOLHE",
        "CONSTANTE",
        "VARIAVEL",
        "ENTAO",
        "DE",
        
        //tipos de variaveis
        "VAZIO",
        "LOGICO",
        "TEXTO",
        "INTEIRO",
        "REAL",
        "CARACTER"
    };
    
    /**
     * retorna o texto de uma palavra chave
     * @param key palavra chave
     * @return texto
     */
    public static String GetTextKey(int key){
        return txt[key];
    }
    
    /**
     * calcula o tipo de key através da string
     * @param instr instrução
     * @return chave da instrução
     */
    public static final int GetKey(String instr){
        //String instruction = instr.toUpperCase();
        String instruction = Normalize(instr);
        if( instruction.startsWith("LER "))         return  LER;
        if( instruction.startsWith("ESCREVER "))    return  ESCREVER;
        if( instruction.equals("FAZ"))              return  FAZ;
        // se for um enquanto sem o faz
        if( instruction.startsWith("ENQUANTO") &&
                ! instruction.endsWith("FAZ")    )      return  FAZENQUANTO;
        
        if( instruction.equals("SENAO"))            return  SENAO;
        if( instruction.startsWith("SE "))          return  SE;
        if( instruction.equals("FIMSE"))            return  FIMSE;
        if( instruction.equals("FIM SE"))           return  FIMSE;
        
        if( instruction.startsWith("PARA "))        return  PARA;
        if( instruction.equals("PROXIMO"))          return  PROXIMO;
        
        if( instruction.startsWith("ENQUANTO "))    return  ENQUANTO;
        if( instruction.equals("FIMENQUANTO"))      return  FIMENQUANTO;
        if( instruction.equals("FIM ENQUANTO"))     return  FIMENQUANTO;
        
        if( instruction.equals("REPETE"))           return  REPETE;
        if( instruction.startsWith("ATE "))         return  ATE;
        
        
        if( instruction.startsWith("INTEIRO "))     return  DEFINIR;
        if( instruction.startsWith("REAL "))        return  DEFINIR;
        if( instruction.startsWith("CARACTER "))    return  DEFINIR;
        if( instruction.startsWith("LOGICO "))      return  DEFINIR;
        if( instruction.startsWith("TEXTO "))       return  DEFINIR;
        if( instruction.startsWith("CONSTANTE "))   return  DEFINIR;
        if( instruction.startsWith("VARIAVEL "))    return  DEFINIR;
        
        if( instruction.startsWith("ESCOLHE "))    return   ESCOLHE;
        if( instruction.startsWith("CASO "))       return   CASO;
        if( instruction.startsWith("DEFEITO"))     return   DEFEITO;
        if( instruction.equals("FIMESCOLHE"))     return    FIMESCOLHE;
        if( instruction.equals("FIM ESCOLHE"))    return    FIMESCOLHE;
        
        if( instruction.equals("INICIO"))           return  INICIO;
        if( instruction.equals("FIM"))              return  FIM;
        
        if( instruction.indexOf( ATRIBUI ) != -1)        return  CALCULAR;
        
        else return  DESCONHECIDO;
    }
    
    /**
     * verifica se uma string é uma palavra reservada
     * @param key palavra
     * @return verdadeiro se for uma palavra reservada
     */
    public static boolean IsKeyword(String key){
        String tmp = Normalize(key);
        for(int i=0; i< txt.length ; i++){
            if( tmp.equalsIgnoreCase( txt[i]))
                return true;
        }
        return false;
    }
    
       
    static private String from = "ãõáéíóúàèìòùâêîôûÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛçÇ";
     static private String to   = "AOAEIOUAEIOUAEIOUAEIOUAEIOUAOAEIOUCC";

    private static String Normalize(String str){
        StringBuffer tmp = new StringBuffer();
        int index;
        for(int i=0; i< str.length();i++ ){
            index = from.indexOf(str.charAt(i));
            if( index == -1 )
                tmp.append(str.charAt(i));
            else
                tmp.append(to.charAt(index));
        }
        return tmp.toString().trim().toUpperCase();
    }
}
