/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

/*
 * Values.java
 *
 * Created on 2 de Maio de 2006, 11:00
 *
 *
 */

package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Parser.Keyword;
/**
 * M�todos est�ticos para tratamento de strings e Valores
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
public class Values {
    //---------------------------------------------------------------------------
    /**
     * Retorna os valores por defeito dos tipo
     * @param type nome do tipo
     * @return string com o valor por defeito
     */
    //necessario a keyword para ler o texto dos simbolos
    public static Object getDefaultObject(String type){
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.REAL)) ) 
            return  new Double(0.0);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.INTEIRO)) ) 
            return  new Integer(0);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.LOGICO)) ) 
            return new Boolean(false);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CARACTER)) )  
            return new Character(' ');
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.TEXTO)) ) 
            return "";
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.PONTEIRO)) )  
            return new Integer(0);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.VAZIO)) )  
            return "";
        
        return "ERROR DEFAULT VALUE";
    }
    //---------------------------------------------------------------------------
    /**
     * Retorna os valores por defeito dos tipo
     * @param type nome do tipo
     * @return string com o valor por defeito
     */
    //necessario a keyword para ler o texto dos simbolos
    public static String getDefaultValue(String type){
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.REAL)) ) 
//            return  SymbolReal.defaultValue;
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.INTEIRO)) ) 
//            return  SymbolReal.defaultValue;
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.LOGICO)) ) 
//            return Keyword.GetTextKey(Keyword.FALSO);
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CARACTER)) )  
//            return " ";
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.TEXTO)) ) 
//            return "";
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.PONTEIRO)) )  
//            return "0";
//        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.VAZIO)) )  
//            return "";
        
        return "ERROR DEFAULT VALUE";
    }

    //---------------------------------------------------------------------------
    /**
     * Retorna os valores por defeito dos tipo
     * @param type nome do tipo
     * @return string com o valor por defeito
     */
    //necessario a keyword para ler o texto dos simbolos
    public static Object getSafeDefault(String type){
       if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.REAL)) ) 
            return  new Double(1.0);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.INTEIRO)) ) 
            return  new Integer(1);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.LOGICO)) ) 
            return new Boolean(false);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CARACTER)) )  
            return new Character(' ');
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.TEXTO)) ) 
            return "";
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.PONTEIRO)) )  
            return new Integer(0);
        if( type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.VAZIO)) )  
            return "";
        
        return "ERROR DEFAULT VALUE";

    }
    
     //---------------------------------------------------------------------------
    /**
     * Converte um numero para uma string com o inteiro
     * @param val valor do numero
     * @return string com o inteiro
     */
    public static String IntegerToString(double val){
        String num =  Double.toString(val);
        return num.substring(0, num.indexOf('.'));
    }
 //---------------------------------------------------------------------------
    /**
     * converte um string para inteiro
     * @param val string com o inteiro
     * @return valor inteiro
     */
    public static int StringToInteger(String val){
        Double d = new Double(val);
        return d.intValue();
    }
//---------------------------------------------------------------------------
    
//---------------------------------------------------------------------------
    /**
     * converte um numero para uma string
     * @param val valor
     * @return string
     */
    public static String DoubleToString(double val){
        String num= Double.toString(val);
        if( num.indexOf('.')==-1) return num + ".0";
        else return num;
    }
//---------------------------------------------------------------------------
    /**
     * converte uma string para numero
     * @param val texto
     * @return valor
     */
    public static double StringToDouble(String val){
        return  Double.valueOf(val);
    }
//---------------------------------------------------------------------------
    /**
     * converte um valor logico para texto
     * @param val valor logico
     * @return texto
     */
    public static String BooleanToString(boolean val){
        if( val ) return Keyword.GetTextKey(Keyword.VERDADEIRO);
        return Keyword.GetTextKey(Keyword.FALSO);
    }
//---------------------------------------------------------------------------
    /**
     * converte texto para lógico
     * @param val texto com o valor
     * @return valor lógico
     */
    public static boolean StringToBoolean(String val){
        if( val.equalsIgnoreCase(Keyword.GetTextKey(Keyword.VERDADEIRO)) )
            return true;
        if( val.equalsIgnoreCase(Keyword.GetTextKey(Keyword.FALSO)) ) 
            return false;
        System.out.println("ERRO StringToBoolean ["+ val + "] não é um boleano");
        return false;
    }
//------------------------------------------------------------------------------
    /**
     * verifica se é um numero
     * @param n texto
     * @return verica se é um inteiro ou um real
     */
    public static  boolean IsNumber(String n) {
        try{
            double v = Double.parseDouble(n);
            return true;
        } catch ( Exception e ){
            return false;
        }
    }
    //------------------------------------------------------------------------------
    /**
     * verifica se é um numero inteiro
     * @param n text
     * @return verifica se é um numero inteiro
     */
    public static boolean IsInteger(String n){
        if( ! IsNumber(n))
            return false;
        return n.indexOf(".") == -1;
    }
    //------------------------------------------------------------------------------
    /**
     * verifica se é um numero inteiro
     * @param n text
     * @return verifica se é um numero inteiro
     */
    public static boolean IsReal(String n){
        if( ! IsNumber(n))
            return false;
        return n.indexOf(".") != -1;
    }
    //------------------------------------------------------------------------------
    /**
     * verifica se é um valor lógico
     * @param val texto
     * @return verifica se é um valor lógico
     */
    public static boolean IsBoolean(String val){
        if( val.equalsIgnoreCase(Keyword.GetTextKey(Keyword.VERDADEIRO)) ||
                val.equalsIgnoreCase(Keyword.GetTextKey(Keyword.FALSO)) )  
            
            return true;
        
        return false;
    }
    /**
     * verifica se é uma string
     * @return verifica se é uma string
     * @param expr expressão
     */
    public static boolean IsString(String expr){
        if( !expr.startsWith("\"") ||  !expr.endsWith("\""))
            return false;
        //contar os "
        for(int index= 1 ; index < expr.length()-1 ; index++){
            if(expr.charAt(index)=='"' && expr.charAt(index-1)!='\\' )
                return false;
        }
        return true;
    }
    /**
     * verifica se é uma string
     * @return verifica se é uma string
     * @param expr expressão
     */
    public static boolean IsCharacter(String expr){
        return IsString(expr) && expr.length()==3;
    }
    
    //---------------------------------------------------------------------------
    /**
     * converte um valor Texto para String (retira as aspas)
     * @param str string com aspas
     * @return "conjunto de caracteres"
     */
    public static String TextToString(String str){
        if(str.startsWith("\"") && str.endsWith("\""))
            return str.substring(1, str.length() -1);
        return str;
    }
//---------------------------------------------------------------------------
    /**
     * converte uma string para texto (adiciona-lhe aspas)
     * @return conjunto de caracteres
     * @param str string sem aspas
     */
    public static String StringToText(String str){
        return "\""+ str + "\"";
    }
    //-------------------------------------
    /**
     * Verifica se é um número um lógico ou uma string
     * @param str expressão
     * @return se é um valor
     */
    public static boolean IsValue(String str){
        return IsString(str) || IsBoolean(str) || IsNumber(str);
    }
    
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
    /**
     * remove os carecteres "" da string
     * @param orig string com ""
     * @return string sem ""
     */
    public static String removeStringComas(String orig){
        String tmp;
        if(orig.startsWith("\"") )
            tmp = orig.substring(1);
        else
            tmp = orig;
        if(tmp.endsWith("\"") )
            return tmp.substring(0, tmp.length()-1);
        else
            return tmp;
    }
    /**
     * retira as comas á string e introduz os caracteres especiais (\n \r, etc.)
     * @param orig string de origem
     * @return valor da string
     */
    public static String getStringValue(String orig){
        StringBuffer tmp = new StringBuffer();
        // remover as aspas se existirem
        orig = removeStringComas(orig);
        // substituir os caracteres de escape        
        for(int i= 0; i< orig.length(); i++){
            switch(orig.charAt(i)){
                case '\\':
                    switch(orig.charAt(i+1)){
                       case 'n':
                                tmp.append("\n");
                                break;
                        case 'r':
                                tmp.append("\r");
                                break;
                        case 't':
                                tmp.append("\t");
                                break;
                        default:
                                tmp.append(orig.charAt(i+1));
                                break;                                                                
                    }// switch i+1
                    //passar á frente
                    i++;
                    break;
                    
                default:
                    tmp.append( orig.charAt(i));
            }
        }
        return tmp.toString();
    }
    
    public static int getTypeOfValue(String value){
        if( Values.IsReal(value)) return Keyword.REAL;
        if( Values.IsInteger(value)) return Keyword.INTEIRO;
        if( Values.IsBoolean(value)) return Keyword.LOGICO;
        return Keyword.TEXTO;
    }
 
}
