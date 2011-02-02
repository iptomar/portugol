/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * FunctionsMath.java
 *
 * Created on 2 de Maio de 2006, 11:59
 *
 *
 */

package AlgolXXI.Core.Evaluate;


import AlgolXXI.Core.Utils.Values;
import java.util.Vector;

/**
 * Calculo com funcoes<br>
 * <B>FUN�OES DEFINIDAS </B><br>
 * <B>Sem par�metros</B><br>
 * ALEATORIO <br>
 * <B>Um par�metro</B><br>
 * SEN COS TAN CTG ASEN ACOS ATAN ACTG EXP ABS RAIZ LOG LN <br>
 * <B>Dois par�metro</B><br>
 * POTENCIA <br>
 * @author Ant�nio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 *<br>
 */
public class FunctionsMath extends AbstractCalculus{
    
    private static String functions0 =" ALEATORIO ";
    private static String functions1 =" SEN COS TAN CTG ASEN ACOS ATAN ACTG " +
                                      " SENH COSH TANH CTGH " +
                                      " EXP ABS RAIZ LOG LN " +
                                      " INT FRAC ARRED "      ;
    private static String functions2 =" POTENCIA ";
    
    
    /**
     * verifica se � uma fun��o definida<br>
     * @param str nome da fun��o
     * @return fun��o v�lida
     */
    public  boolean IsValid( String str){
        return functions0.indexOf(" " + str.toUpperCase() + " ") != -1 ||
                functions1.indexOf(" " + str.toUpperCase() + " ") != -1 ||
                functions2.indexOf(" " + str.toUpperCase() + " ") != -1;
    }
    
    /**
     * calcula o numero de par�metros
     * @return numero de parametros
     * @param str nome da fun��o
     * @throws java.lang.Exception erro
     */
    public  int GetNumParameters(String str)throws Exception{
        if(functions0.indexOf(" " + str.toUpperCase() + " ") != -1 ) return 0;
        if(functions1.indexOf(" " + str.toUpperCase() + " ") != -1 ) return 1;
        if(functions2.indexOf(" " + str.toUpperCase() + " ") != -1 ) return 2;
        throw new Exception("ERRO nos parametros das fun�oes ["+str+"]");
    }
    //---------------------------------------------------------------------------
    /**
     * prioridade da fun��o
     * @return prioridade
     * @param oper nome da fun��o
     * @throws java.lang.Exception erro
     */
    public  int GetPriority(String oper)throws Exception{
        if( IsValid(oper)) return AbstractCalculus.FUNCTION_PRIORITY;
        throw new Exception("ERRO na Prioridade das fun��es ["+oper+"]");        
    }
    
    /**
     * Executa o c�lculo da fun��o
     * @return valor do c�lculo
     * @param oper nome da fun��o
     * @param params parametros da fun��o
     * @throws java.lang.Exception erro
     */
    public  String Calculate( String oper , Vector params)throws Exception{
        if( params.size() == 0 )
            return Calculate0(oper);
        if( params.size() == 1 )
            return Calculate1(oper,(String)params.get(0));
        if( params.size() == 2 )
            return Calculate2(oper,(String)params.get(0),(String)params.get(1));
        
        throw new Exception("ERRO fun��o parametros errados ["+oper+"] " + params.toString() );        
    }
    //---------------------------------------------------------------------------
    private  String Calculate0( String oper )throws Exception{
        double val=0;
        if( oper.equalsIgnoreCase("ALEATORIO") ) val = java.lang.Math.random();
        else throw new Exception("ERRO fun��o Desconhecida 2 ["+oper+"]");
        return Values.DoubleToString(val);
    }
    //---------------------------------------------------------------------------
    private  String Calculate1( String oper , String str1)throws Exception{
        double n1 = Values.StringToDouble(str1);
        double val=0;
        
        if( oper.equalsIgnoreCase("SEN") ) val = java.lang.Math.sin(n1);
        else if( oper.equalsIgnoreCase("COS") ) val = java.lang.Math.cos(n1);
        else if( oper.equalsIgnoreCase("TAN") ) val =  java.lang.Math.tan(n1);
        else if( oper.equalsIgnoreCase("CTG") ) val =  1.0 / java.lang.Math.tan(n1);
        
        else if( oper.equalsIgnoreCase("ASEN") ) val = java.lang.Math.asin(n1);
        else if( oper.equalsIgnoreCase("ACOS") ) val = java.lang.Math.acos(n1);
        else if( oper.equalsIgnoreCase("ATAN") ) val =  java.lang.Math.atan(n1);
        else if( oper.equalsIgnoreCase("ACTG") ) val =  1.0 / java.lang.Math.atan(n1);

        else if( oper.equalsIgnoreCase("SENH") ) val = java.lang.Math.sinh(n1);
        else if( oper.equalsIgnoreCase("COSH") ) val = java.lang.Math.cosh(n1);
        else if( oper.equalsIgnoreCase("TANH") ) val =  java.lang.Math.tanh(n1);
        else if( oper.equalsIgnoreCase("CTGH") ) val =  1.0 / java.lang.Math.tanh(n1);
    
        else if( oper.equalsIgnoreCase("EXP") ) val = java.lang.Math.exp(n1);
        //valor absoluto de inteiros s�o inteiros
        else if( oper.equalsIgnoreCase("ABS") ){
            val = java.lang.Math.abs(n1);
            if( Values.IsInteger(str1))
                return Values.IntegerToString(val);
        }
        else if( oper.equalsIgnoreCase("RAIZ") ) val =  java.lang.Math.sqrt(n1);
        else if( oper.equalsIgnoreCase("LOG") ) val =  java.lang.Math.log10(n1);
        else  if( oper.equalsIgnoreCase("LN") ) val =  java.lang.Math.log(n1);
       // parte inteira do numeros
        else  if( oper.equalsIgnoreCase("INT") ){
            return Values.IntegerToString(n1);
        }
        //parte real
        else  if( oper.equalsIgnoreCase("FRAC") ){
            String num= Values.DoubleToString(n1);
            return num.substring( num.indexOf('.')+1);
        }    
        
         //parte real
        else  if( oper.equalsIgnoreCase("ARRED") ){
            double  vm = java.lang.Math.ceil(n1);            
            if( n1 - vm >= 0.5)
                return Values.IntegerToString((int) n1+1);
           return Values.IntegerToString((int) n1); 
        }    
        
        else throw new Exception("ERRO fun��o Desconhecida 1 ["+oper+"]");
        return Values.DoubleToString(val);
    }
    //---------------------------------------------------------------------------
    private  String Calculate2( String oper , String str1, String str2)throws Exception{
        double n1 = Values.StringToDouble(str1);
        double n2 = Values.StringToDouble(str2);
        System.out.println(n1 + " + " + n2);
        double val=0;
        // potencia de inteiros s�o inteiros
        if( oper.equalsIgnoreCase("POTENCIA") ) {
            val = java.lang.Math.pow(n1 , n2 );
            if( Values.IsInteger(str1) && Values.IsInteger(str2) )
                return Values.IntegerToString(val);
        }
        
        else throw new Exception("ERRO fun��o Desconhecida 2 ["+oper+"]");
        return Values.DoubleToString(val);
    }
    
    //------------------------------------------------------------------------
    // resultado seguro destes operadores
    // util para fazer o parse
    //------------------------------------------------------------------------
    public String CalculateSafe( String oper , Vector params)throws Exception{
        return Values.StringToText("");
    }    
}
