/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     AntÛnio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto PolitÈcnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/

package Portugol.Language.Utils;

import java.util.StringTokenizer;



/**
 * ManipulaÁ„o de uma linha de cÛdigo
 * @author AntÛnio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform·tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto PolitÈcnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
public class CodeLine {
    
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    /**
     * transforma os caracteres acentuados em n„o acentuados
     * @param str linha de cÛdigo
     * @return linha de cÛdigo sem acentos
     */
    public static String ToPortuguese(String str){    
        StringBuffer newStr = new StringBuffer();
        boolean isString = false; 
        for(int i=0 ; i< str.length() ; i++){
            char ch = str.charAt(i);
            if( ch == '\"' ){
                if( i==0 || (i >0 && str.charAt(i-1) != '\\'))
                    isString = ! isString;
            }
            if(isString)    
                newStr.append(ch);
            else  if(port.indexOf(ch) !=-1)
                newStr.append( noc.charAt(port.indexOf(ch) ));
            else
                newStr.append( (new String(ch+"")).toLowerCase());
        }
        return newStr.toString();
    }
    
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    /**
     * Normaliza uma linha de cÛdigo colocando espaÁoes onde s„o necess·rios e retirando os desnecess·rios
     * @param str linha de cÛdigo
     * @return linha de cÛdigo normalizada
     */
    public static String GetNormalized( String str){
        
        String oper ="+-*/^(),%[]";        
        String relat ="><=/";
        boolean isString= false;
        
        str = ToPortuguese(str);
        String tmp= "";
        for(int i=0; i< str.length(); i++){
            
            char ch = str.charAt(i);            
            if( ch =='\"' && i>0 && str.charAt(i-1) != '\\')
                isString = !isString;
            
            //sinal de atribuiÁ„o
            if( !isString &&  ch=='<' && i+1 < str.length() && str.charAt(i+1)== '-'  ){
                tmp+= " ";
                tmp+= "<-";
                tmp +=" ";
                //passar por cima do '-'
                i++;
            }
            //acrescenter espaÁos aos operadores
            else if( !isString &&  oper.indexOf( ch ) != -1 ){
                tmp+= " ";
                tmp+= ch;
                tmp +=" ";
            }
            // relacional com um simbolo
            else if( !isString && i < str.length() -1 && relat.indexOf( ch ) != -1 &&
                    relat.indexOf( str.charAt(i+1) ) == -1){
                tmp+= " ";
                tmp+= ch;
                tmp +=" ";
            }
            // relacional com dois simbolos
            else if( !isString && i < str.length()-2 && relat.indexOf( ch ) != -1 &&
                    str.charAt(i+1) == '=' ){
                tmp+= " ";
                tmp+= ch;
                tmp +="= ";
                i++;
            }
            // relacional com tres simbolos
            else if(!isString && i < str.length()-2 && str.charAt(i) == '=' && str.charAt(i+1) == '/' && str.charAt(i+2) == '='){
                tmp += " =/= ";
                i += 2;
            }
            // outros simbolos
            else                   
                tmp += str.charAt(i);
            
        }
        
        return  normalizeMinus(tmp);
        
    }
//------------------------------------------------------------------------------
   static String normalizeMinus( String str){              
       // retirei o ) por n„o contarem como operadores
       String oper = " E OU NAO + - * / ^ (  , <- >= <= < > = =/= ";
        String tmp="";
        String elem;
        boolean prevOperator = true;
        StringTokenizer it = new StringTokenizer( str," , \t", true);
        while( it.hasMoreElements() ){
            elem = ((String)it.nextElement()).trim();
            if(elem.length() ==0) continue;           
            if( ! elem.equalsIgnoreCase("-") )
                tmp += elem + " ";
            else{
                // se o anterior for um operador
                if( prevOperator )
                    tmp+= "-";
                else
                    // retirei um espaÁo 24/09/2006
                    //tmp+= " - ";
                    tmp+= "- ";
            }
            if( oper.indexOf(" " + elem + " ") != -1 )
                prevOperator = true;
            else
                prevOperator = false;            
        }
        return tmp.trim();
    }
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
    private static String port  = "·ÈÌÛ˙¡…Õ”⁄‡ËÏÚ˘¿»Ã“Ÿ„ı√’‚ÍÓÙ˚¬ Œ‘€Á«";
    private static String noc  =  "aeiouAEIOUaeiouAEIOUaoAOaeiouAEIOUcC";
    private static String lower = "aeiouaeiouaeiouaeiouaoaoaeiouaeiuocc";
    private static String upper = "AEIOUAEIOUAEIOUAEIOUAOAOAEIOUAEIOUCC";
        
}
