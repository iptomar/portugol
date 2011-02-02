/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Relationals.java
 *
 * Created on 2 de Maio de 2006, 11:27
 *
 *
 */

package Portugol.Language.Evaluate;

import Portugol.Language.Utils.Values;
import java.util.Vector;

/**
 * Calculo com operadores relacionais<br>
 *<B>Operadores Relacionais definidos :</B> = =/= > >= < <= <br>
 *@author António M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Informática<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Politécnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
public class Relationals extends AbstractCalculus {
    
    private static String relationals =" > >= < <= ";
    private static String equalsOper = " = =/= ";
    
   
    /**
     * verifica se é um operador relacional
     * @param str nome do operador
     * @return operador valido
     */
    public  boolean IsValid( String str){
        return relationals.indexOf(" " + str.toUpperCase() + " ") != -1 ||
                equalsOper.indexOf(" " + str.toUpperCase() + " ") != -1;
    }
    
    /**
     * calcula o numero de parâmetros do operador
     * @return numero de parâmetros
     * @param oper nome do operador
     * @throws java.lang.Exception ERRO
     */
    public  int GetNumParameters(String oper) throws Exception{
        if ( IsValid(oper) )
            return 2;
        throw new Exception("ERRO nos parametros Operador Relacional ["+oper+"]");
    }
    //---------------------------------------------------------------------------
    /**
     * retorna a prioridade do operador
     * @return prioridade do operdor
     * @param oper nome do operador
     * @throws java.lang.Exception ERRO
     */
    public  int GetPriority(String oper) throws Exception{
        if( oper.equalsIgnoreCase(">") )     return  AbstractCalculus.RELATIONAL_PRIORITY;
        if( oper.equalsIgnoreCase("<") )     return  AbstractCalculus.RELATIONAL_PRIORITY;
        if( oper.equalsIgnoreCase(">=") )    return AbstractCalculus.RELATIONAL_PRIORITY;
        if( oper.equalsIgnoreCase("<=") )    return AbstractCalculus.RELATIONAL_PRIORITY;
        if( oper.equalsIgnoreCase("=") )     return  AbstractCalculus.RELATIONAL_PRIORITY;
        if( oper.equalsIgnoreCase("=/=") )   return AbstractCalculus.RELATIONAL_PRIORITY;
        throw new Exception("ERRO na prioridade do Operador Relacional ["+oper+"]");
    }
    /**
     * Executa o calculo
     * @return valor do calculo
     * @param oper nome do operador
     * @param params parametros
     * @throws java.lang.Exception ERRO
     */
    public  String Calculate( String oper , Vector params)throws Exception{
        if( params.size() != 2 ){
            throw new Exception("ERRO RELACIONAIS - relacionais com dois parametros");
        }
        return Calculate((String)params.get(0),oper,(String)params.get(1));
    }
    //---------------------------------------------------------------------------
    private  String Calculate( String str1, String oper , String str2) throws Exception{
        //aritmetica de numeros intereiros
        if( Values.IsNumber(str1) && Values.IsNumber(str2))
            return CalculateValueRelational(str1,oper,str2);
        //aritmetica de numeros intereiros
        if( Values.IsString(str1) && Values.IsString(str2))
            return CalculateTextRelational(str1,oper,str2);
        else if( Values.IsBoolean(str1) && Values.IsBoolean(str2) )
            return CalculateLogicRelational(str1,oper,str2);
        else
            throw new Exception("ERRO no tipo de parametros do operador :" + oper );
    }
    
    //---------------------------------------------------------------------------
    private  String CalculateLogicRelational( String str1, String oper , String str2)throws Exception{
        boolean n1 = Values.StringToBoolean(str1);
        boolean n2 = Values.StringToBoolean(str2);
        boolean val=false;
        if( oper.equalsIgnoreCase("=") ) val =  n1 == n2;
        else if( oper.equalsIgnoreCase("=/=") ) val =  n1 != n2;
        else
            throw new Exception("ERRO no calculo booleano do Operador Relacional ["+oper+"]");
        return Values.BooleanToString(val);
    }
    //---------------------------------------------------------------------------
    private  String CalculateValueRelational( String str1, String oper , String str2)throws Exception{
        double n1 = Values.StringToDouble(str1);
        double n2 = Values.StringToDouble(str2);
        boolean val= false;
        if( oper.equalsIgnoreCase(">") ) val = n1 > n2;
        else if( oper.equalsIgnoreCase(">=") ) val = n1 >= n2;
        else if( oper.equalsIgnoreCase("<") ) val =  n1 < n2;
        else if( oper.equalsIgnoreCase("<=") ) val =  n1 <= n2;
        else if( oper.equalsIgnoreCase("=") ) val =  n1 == n2;
        else if( oper.equalsIgnoreCase("=/=") ) val =  n1 != n2;
        else
            throw new Exception("ERRO no calculo do Operador Relacional ["+oper+"]");
        return Values.BooleanToString(val);
    }
    //---------------------------------------------------------------------------
    private  String CalculateTextRelational( String str1, String oper , String str2)throws Exception{
        String n1 = Values.TextToString(str1);
        String n2 = Values.TextToString(str2);
        boolean val= false;
        if( oper.equalsIgnoreCase(">") ) val = n1.compareTo(n2)>0;
        else if( oper.equalsIgnoreCase(">=") ) val = n1.compareTo(n2)>=0;
        else if( oper.equalsIgnoreCase("<") ) val =  n1.compareTo(n2)<0;
        else if( oper.equalsIgnoreCase("<=") ) val =  n1.compareTo(n2)<=0;
        else if( oper.equalsIgnoreCase("=") ) val =  n1.compareTo(n2)==0;
        else if( oper.equalsIgnoreCase("=/=") ) val =  n1.compareTo(n2)!=0;
        else
            throw new Exception("ERRO no calculo do Operador Relacional ["+oper+"]");
        return Values.BooleanToString(val);
    }
    
    
}
