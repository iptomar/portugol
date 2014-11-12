/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Aritmetics.java
 *
 * Created on 2 de Maio de 2006, 10:53
 *
 *
 */

package AlgolXXI.Core.Evaluate;


import AlgolXXI.Core.Utils.Values;
import java.util.Vector;
/**
 * Executa Calculos aritm�ticos<br>
 * <B>OPERADORES DEFINIDsS </B>
 * + - * / ^ % <br>
 *<br>
 *@author Ant�nio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
public class Aritmetics extends AbstractCalculus{
    
    private static String operBin =" + - * / % ^ ";
    
    /**
     * retorna os simbolos da arimt�tica
     * @return operadores aritm�ticos separados por espa�o
     */
    public String GetSymbols(){
        return operBin;
    }
    /**
     * verifica se � um operador aritm�tico ( + - * / % ^ )
     * @param str simbolo
     * @return verifica se � um operador
     */
    public  boolean IsValid( String str){
        return operBin.indexOf(" " + str + " ") != -1;
    }
    
    /**
     * calcula o numero de par�metros
     * @return numero de par�metros
     * @param oper operador
     * @throws java.lang.Exception erro
     */
    public  int GetNumParameters(String oper)throws Exception{
        if (operBin.indexOf(" " + oper + " ") != -1)
            return 2;
        throw new Exception("ERRO no Operador aritm�tico ["+oper+"]");
    }
    //---------------------------------------------------------------------------
    /**
     * prioridade do operador
     * @return prioridade
     * @param oper operador
     * @throws java.lang.Exception erro
     */
    public  int GetPriority(String oper)throws Exception{
        if( oper.equalsIgnoreCase("+") )     return AbstractCalculus.ARITMETIC_PRIORITY + 1;
        if( oper.equalsIgnoreCase("-") )     return AbstractCalculus.ARITMETIC_PRIORITY + 1;
        if( oper.equalsIgnoreCase("*") )     return AbstractCalculus.ARITMETIC_PRIORITY + 2;
        if( oper.equalsIgnoreCase("/") )     return AbstractCalculus.ARITMETIC_PRIORITY + 2;
        if( oper.equalsIgnoreCase("%") )     return AbstractCalculus.ARITMETIC_PRIORITY + 2;
        if( oper.equalsIgnoreCase("^") )     return AbstractCalculus.ARITMETIC_PRIORITY + 3;
        throw new Exception("ERRO no Operador aritm�tico ["+oper+"]");
    }
    
    /**
     * executa o calculo
     * @return valor do calculo
     * @param oper operador
     * @param params parametros
     * @throws java.lang.Exception erro
     */
    public  String Calculate( String oper , Vector params)throws Exception{
        if( params.size() != 2 ){
            throw new Exception("ERROR Aritmetic - aritmeticos com dois parametros");
        }
        return Calculate2((String)params.get(0),oper,(String)params.get(1));
    }
    
    //---------------------------------------------------------------------------
    /**
     * calculo com dois parametros
     * @return valor do calculo
     * @param str1 primeiro valor
     * @param oper operador
     * @param str2 segundo valor
     * @throws java.lang.Exception erro
     */
    public  String Calculate2( String str1, String oper , String str2)throws Exception{
        //aritmetica de numeros intereiros
        if( Values.IsInteger(str1) && Values.IsInteger(str2))
            return CalculateInteger(str1,oper,str2);
        else if( Values.IsString(str1) ||  Values.IsString(str2))
            return CalculateText(str1,oper,str2);
        if( Values.IsNumber(str1) && Values.IsNumber(str2))
            return CalculateReal(str1,oper,str2);
        throw new Exception("ERRO OPERADOR [" + oper + "] N�O DEFINIDO");
    }
    //---------------------------------------------------------------------------
    private  String CalculateReal( String str1, String oper , String str2)throws Exception{
        double n1 = Values.StringToDouble(str1);
        double n2 = Values.StringToDouble(str2);
        double val=0;
        if( oper.equalsIgnoreCase("+") ) val = n1 + n2;
        else if( oper.equalsIgnoreCase("-") ) val = n1 - n2;
        else if( oper.equalsIgnoreCase("*") ) val =  n1 * n2;
        else if( oper.equalsIgnoreCase("/") ){
            if( n2 == 0)
                throw new Exception(" DIVISAO POR ZERO");
            val =  n1 / n2;
        } else if( oper.equalsIgnoreCase("^") ) val =  java.lang.Math.pow(n1,n2);
        else throw new Exception("ERRO OPERADOR [" + oper + "] N�O DEFINIDO");
        return Values.DoubleToString(val);
    }
    
    //---------------------------------------------------------------------------
    private  String CalculateInteger( String str1, String oper , String str2)throws Exception{
        int n1 = Values.StringToInteger(str1);
        int n2 = Values.StringToInteger(str2);
        int val=0;
        if( oper.equalsIgnoreCase("+") ) val = n1 + n2;
        else if( oper.equalsIgnoreCase("-") ) val = n1 - n2;
        else if( oper.equalsIgnoreCase("*") ) val =  n1 * n2;
        else if( oper.equalsIgnoreCase("/") ){
            if( n2 == 0)
                throw new Exception(" DIVISAO POR ZERO");
            val =  n1 / n2;
        } 
        else if( oper.equalsIgnoreCase("%") ) val =  n1 % n2;
        else if( oper.equalsIgnoreCase("^") ) val = (int) java.lang.Math.pow(n1,n2);
        else throw new Exception("ERRO OPERADOR [" + oper + "] N�O DEFINIDO");
        return Values.IntegerToString(val);
    }
    
    //---------------------------------------------------------------------------
    private  String CalculateText( String str1, String oper , String str2)throws Exception{
        String n1 = Values.TextToString(str1);
        String n2 = Values.TextToString(str2);
        String val= "";
        if( oper.equalsIgnoreCase("+") ) val = n1 + n2;
        else throw new Exception("ERRO OPERADOR [" + oper + "] N�O DEFINIDO");
        return Values.StringToText(val);
    }
    //------------------------------------------------------------------------
    // resultado seguro destes operadores
    // util para fazer o parse
    //------------------------------------------------------------------------
    public String CalculateSafe( String oper , Vector params)throws Exception{
       String str1 = (String)params.get(0);
          if( Values.IsInteger(str1) )
            return "1";
        else if( Values.IsString(str1))
            return "\"\"";
       else if( Values.IsCharacter(str1))
            return "\" \"";
       else if( Values.IsBoolean(str1))
            return "VERDADEIRO";
       else if( Values.IsReal(str1))
            return "1.0";
       else 
            return "ERRO";
       
       
       
    }
}





