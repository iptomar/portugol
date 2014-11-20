/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

/*
 * Keyword.java
 *
 * Created on 2 de Junho de 2006, 14:30
 *
 *
 */
package AlgolXXI.Core.Parser;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Palavras reservadas da linguagem
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
public class Keyword {

    public static final int DESCONHECIDO = 0;
    public static final int INICIO = 1;
    public static final int FIM = 2;
    public static final int LER = 3;
    public static final int ESCREVER = 4;
    public static final int CALCULAR = 5;
    public static final int DEFINIR = 6;
    public static final int CONECTOR = 7;
    public static final int SE = 8;
    public static final int SENAO = 9;
    public static final int FIMSE = 10;
    public static final int PARA = 11;
    public static final int PROXIMO = 12;
    public static final int PASSO = 13;
    public static final int ENQUANTO = 14;
    public static final int FIMENQUANTO = 15;
    public static final int REPETE = 16;
    public static final int ATE = 17;
    public static final int FAZ = 18;
    public static final int FAZENQUANTO = 19;
    public static final int ESCOLHE = 20;
    public static final int CASO = 21;
    public static final int DEFEITO = 22;
    public static final int FIMESCOLHE = 23;
    public static final int CONSTANTE = 24;
    public static final int VARIAVEL = 25;
    public static final int ENTAO = 26;
    public static final int DE = 27;
    public static final int VAZIO = 28;
    public static final int LOGICO = 29;
    public static final int TEXTO = 30;
    public static final int INTEIRO = 31;
    public static final int REAL = 32;
    public static final int CARACTER = 33;
    public static final int ESTRUTURA = 34;
    public static final int PONTEIRO = 35;
    public static final int PROGRAMA = 36;
    public static final int FALSO = 37;
    public static final int VERDADEIRO = 38;
    public static final int FUNCAODEFINIDA = 39;
    public static final int DEFINIRFUNCAO = 40;
    public static final int FIMDEFINIRFUNCAO = 41;
    public static final int FIMDEESTRUTURA = 42;
    public static final int FUNCAOLINGUAGEM = 43;
    public static final int RETORNAR = 44;
    public static final int REFERENCIA = 45;
    public static final int VALOR = 46;
    //---------------------------------------------
    // PARSER
    //----------------------------------------------
    public static final int ID = 57;
    public static final int KEYWORD = 58;
    public static final int ASSIGN = 59; //<-
    public static final int OP_G = 60; //>
    public static final int OP_L = 61; //<
    public static final int OP_GE = 62; //>=
    public static final int OP_LE = 63; //<=
    public static final int OP_E = 64; //<=
    public static final int OP_PLUS = 65; //+
    public static final int OP_MINUS = 66; //-
    public static final int OP_MUL = 67; //*
    public static final int OP_DIV = 68; // /
    public static final int LPARENT = 69; // (
    public static final int RPARENT = 70; // )
    public static final int COMMA = 71; // ,
    public static final int LBRACKET = 72; //[ 
    public static final int RBRACKET = 73; //]
    public static final int OP_MOD = 74; //%    
    public static final int LKEYWAY = 75; //{
    public static final int RKEYWAY = 76; //}
    public static final int ENDL = 80;
    public static final int ENDF = 81;
    /**
     * sinal de atribuição
     */
    public static final String ATRIBUI = "<-";
    /*
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
    "CARACTER",
    "ESTRUTURA",
    "PONTEIRO",
    "PROGRAMA",
    
    "VERDADEIRO",
    "FALSO",
    
    "FUNCAODEFINIDA"
    }; 
     */

    /**
     * retorna o texto de uma palavra chave
     * @param key palavra chave
     * @return texto
     */
    public static String GetTextKey(int key) {
        //return txt[key];
        return KeywordsDatabase.getKeyword(key);
    }

    public static String getFastKeyword(int key) {
        return KeywordsDatabase.getFastKeyword(key);
    }

    public static int getFastKey(String word) {
        return KeywordsDatabase.getFastID(word);
    }

    /**
     * calcula o tipo de key através da string
     * @param instr instrução
     * @return chave da instrução
     */
    public static final int GetKey(String instr) {
        //String instruction = instr.toUpperCase();
        String instruction = Normalize(instr);
        /*
        if( instruction.startsWith("LER"))         return  LER;
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
        
        
        if( instruction.startsWith("INTEIRO"))     return  DEFINIR;
        if( instruction.startsWith("REAL"))        return  DEFINIR;
        if( instruction.startsWith("CARACTER"))    return  DEFINIR;
        if( instruction.startsWith("LOGICO"))      return  DEFINIR;
        if( instruction.startsWith("TEXTO"))       return  DEFINIR;
        if( instruction.startsWith("CONSTANTE"))   return  DEFINIR;
        if( instruction.startsWith("VARIAVEL"))    return  DEFINIR;
        if( instruction.startsWith("VAZIO"))       return  DEFINIR;
        
        if( instruction.startsWith("PONTEIRO"))   return  DEFINIR;
        
        if( instruction.startsWith("ESCOLHE "))    return   ESCOLHE;
        if( instruction.startsWith("CASO "))       return   CASO;
        if( instruction.startsWith("DEFEITO"))     return   DEFEITO;
        if( instruction.equals("FIMESCOLHE"))     return    FIMESCOLHE;
        if( instruction.equals("FIM ESCOLHE"))    return    FIMESCOLHE;
        
        if( instruction.startsWith("INICIO"))           return  INICIO;
        if( instruction.startsWith("FIM"))              return  FIM;
         */
        ArrayList<AlgolXXIKeyword> k = KeywordsDatabase.getNormalKeywords();
        String doStatement = KeywordsDatabase.getDoStatement();

        for (int i = 0; i < k.size(); i++) {
            int id = k.get(i).getId();
            String key = k.get(i).getWord();

            if (id == LER && instruction.startsWith(key)) {
                return id;
            }
            if (id == ESCREVER && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == FAZ && instruction.equals(key)) {
                return id;
            }
            if (id == ENQUANTO && instruction.startsWith(key) &&
                    !instruction.endsWith(doStatement)) {
                return id;
            }
            if (id == SENAO && instruction.equals(key)) {
                return id;
            }
            if (id == SE && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == FIMSE && instruction.equals(key)) {
                return id;
            }
            if (id == PARA && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == PROXIMO && instruction.equals(key)) {
                return id;
            }
            if (id == ENQUANTO && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == FIMENQUANTO && instruction.equals(key)) {
                return id;
            }
            if (id == REPETE && instruction.equals(key)) {
                return id;
            }
            if (id == ATE && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == INTEIRO && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == REAL && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == CARACTER && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == LOGICO && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == TEXTO && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == CONSTANTE && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == VARIAVEL && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == VAZIO && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == ESTRUTURA && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == PONTEIRO && instruction.startsWith(key)) {
                return DEFINIR;
            }
            if (id == ESCOLHE && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == CASO && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == DEFEITO && instruction.startsWith(key)) {
                return id;
            }
            if (id == FIMESCOLHE && instruction.equals(key)) {
                return id;
            }
            if (id == INICIO && instruction.startsWith(key)) {
                return id;
            }
            if (id == FIM && instruction.startsWith(key)) {
                return id;
            }
        }

        if (instruction.indexOf(ATRIBUI) != -1) {
            return CALCULAR;
        } else {
            return DESCONHECIDO;
        }
    }

    /**
     * calcula o tipo de key através da string
     * @param instr instrução
     * @return chave da instrução
     */
    public static final int GetKeyType(String instr) {
        //String instruction = instr.toUpperCase();
        String instruction = Normalize(instr);

        ArrayList<AlgolXXIKeyword> k = KeywordsDatabase.getNormalKeywords();
        String doStatement = KeywordsDatabase.getDoStatement();

        for (int i = 0; i < k.size(); i++) {
            int id = k.get(i).getId();
            String key = k.get(i).getWord();

            if (id == LER && instruction.startsWith(key)) {
                return id;
            }
            if (id == ESCREVER && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == FAZ && instruction.equals(key)) {
                return id;
            }
            if (id == ENQUANTO && instruction.startsWith(key) &&
                    !instruction.endsWith(doStatement)) {
                return id;
            }
            if (id == SENAO && instruction.equals(key)) {
                return id;
            }
            if (id == SE && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == FIMSE && instruction.equals(key)) {
                return id;
            }
            if (id == PARA && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == PROXIMO && instruction.equals(key)) {
                return id;
            }
            if (id == ENQUANTO && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == FIMENQUANTO && instruction.equals(key)) {
                return id;
            }
            if (id == REPETE && instruction.equals(key)) {
                return id;
            }
            if (id == ATE && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == INTEIRO && instruction.startsWith(key)) {
                return id;
            }
            if (id == REAL && instruction.startsWith(key)) {
                return id;
            }
            if (id == CARACTER && instruction.startsWith(key)) {
                return id;
            }
            if (id == LOGICO && instruction.startsWith(key)) {
                return id;
            }
            if (id == TEXTO && instruction.startsWith(key)) {
                return id;
            }
            if (id == CONSTANTE && instruction.startsWith(key)) {
                return id;
            }
            if (id == VARIAVEL && instruction.startsWith(key)) {
                return id;
            }
            if (id == VAZIO && instruction.startsWith(key)) {
                return id;
            }
            if (id == ESTRUTURA && instruction.startsWith(key)) {
                return id;
            }
            if (id == PONTEIRO && instruction.startsWith(key)) {
                return id;
            }
            if (id == ESCOLHE && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == CASO && instruction.startsWith(key + " ")) {
                return id;
            }
            if (id == DEFEITO && instruction.startsWith(key)) {
                return id;
            }
            if (id == FIMESCOLHE && instruction.equals(key)) {
                return id;
            }
            if (id == INICIO && instruction.startsWith(key)) {
                return id;
            }
            if (id == FIM && instruction.startsWith(key)) {
                return id;
            }
        }

        if (instruction.indexOf(ATRIBUI) != -1) {
            return CALCULAR;
        } else {
            return DESCONHECIDO;
        }
    }

    /**
     * calcula o tipo de key através da string
     * @param instr instrução
     * @return chave da instrução
     */
    public static final int GetKeySymbol(String type) {

        /*if( type.equalsIgnoreCase("INTEIRO"))     return  INTEIRO;
        if( type.equalsIgnoreCase("REAL"))        return  REAL;
        if( type.equalsIgnoreCase("CARACTER"))    return  CARACTER;
        if( type.equalsIgnoreCase("LOGICO"))      return  LOGICO;
        if( type.equalsIgnoreCase("TEXTO"))       return  TEXTO;
        if( type.equalsIgnoreCase("VAZIO"))       return  VAZIO;
        if( type.equalsIgnoreCase("ESTRUTURA"))   return  ESTRUTURA;
        if( type.equalsIgnoreCase("PONTEIRO"))   return  PONTEIRO;
        else return  DESCONHECIDO;
         */

        ArrayList<AlgolXXIKeyword> k = KeywordsDatabase.getTypeKeywords();
        for (int i = 0; i < k.size(); i++) {
            if (k.get(i).getWord().equals(type)) {
                return k.get(i).getId();
            }
        }
        return DESCONHECIDO;
    }

    /**
     * verifica se uma string é uma palavra reservada
     * @param key palavra
     * @return verdadeiro se for uma palavra reservada
     */
    public static boolean IsKeyword(String key) {
        String tmp = Normalize(key);
        ArrayList<AlgolXXIKeyword> k = KeywordsDatabase.getNormalKeywords();
        for (int i = 0; i < k.size(); i++) {
            if (tmp.equalsIgnoreCase(k.get(i).getWord())) {
                return true;
            }
        }
        return false;
    }
    static private String from = "ãõáéíóúàèìòùâêîôûÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛçÇ";
    static private String to = "AOAEIOUAEIOUAEIOUAEIOUAEIOUAOAEIOUCC";

    private static String Normalize(String str) {
        StringBuffer tmp = new StringBuffer();
        int index;
        for (int i = 0; i < str.length(); i++) {
            index = from.indexOf(str.charAt(i));
            if (index == -1) {
                tmp.append(str.charAt(i));
            } else {
                tmp.append(to.charAt(index));
            }
        }
        return tmp.toString().trim().toUpperCase();
    }
}
