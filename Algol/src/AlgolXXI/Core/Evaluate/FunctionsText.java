/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * FunctionsText.java
 *
 * Created on 7 de Maio de 2006, 10:16
 *
 *
 */

package AlgolXXI.Core.Evaluate;

import AlgolXXI.Core.Utils.Values;
import java.util.Vector;

/**
 * Fun��es de Texto
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


public class FunctionsText extends AbstractCalculus{
    
    private static String functions1 =" COMPRIMENTO ";
    private static String functions2 =" LETRA ";
    /**
     * veririfica se o par�metro � um elemento de calculo
     * @param str nome do elemento
     * @return veririfica se o par�metro � um elemento de calculo
     */
    public  boolean IsValid( String str){
        return functions1.indexOf(" " + str.toUpperCase() + " ") != -1 ||
                functions2.indexOf(" " + str.toUpperCase() + " ") != -1;
        
    }
    /**
     * Retorn o numero de parametros do elemento
     * @return numero de par�metros do elemento
     * @param oper elemento
     * @throws java.lang.Exception erro
     */
    public int GetNumParameters(String oper)throws Exception{
        if(functions1.indexOf(" " + oper.toUpperCase() + " ") != -1 ) return 1;
        if(functions2.indexOf(" " + oper.toUpperCase() + " ") != -1 ) return 2;
        throw new Exception("ERRO nos parametros das fun�oes ["+oper+"]");                
    }
    /**
     * calcula a prioridade do elemento
     * @return prioridade do elemento
     * @param oper elemento
     * @throws java.lang.Exception erro
     */
    public int GetPriority(String oper)throws Exception{
        if( IsValid(oper)) return AbstractCalculus.FUNCTION_PRIORITY;
        throw new Exception("ERRO na Prioridade das fun��es ["+oper+"]");
        
        
    }
    /**
     * Executa o calculo
     * @return valor do calculo
     * @param oper elemento de calculo
     * @param params parametros do calculo
     * @throws java.lang.Exception erro
     */
    public String Calculate( String oper , Vector params)throws Exception{
        if( params.size() == 1 )
            return Calculate1(oper,(String)params.get(0));
        if( params.size() == 2 )
            return Calculate2(oper,(String)params.get(0),(String)params.get(1));
        
        throw new Exception("ERRO fun��o parametros errados ["+oper+"] " + params.toString() );        
    }
    
    private  String Calculate1( String oper, String param )throws Exception{
        double val=0;
        // param = "texto" => comprimento -2 porque " n�o conta
        if( oper.equalsIgnoreCase("COMPRIMENTO") ) 
            val = param.length() - 2;
        else 
            throw new Exception("ERRO fun��o Desconhecida 2 ["+oper+"]");
        return Values.IntegerToString(val);
    }
    
    private  String Calculate2( String oper, String param1, String param2 )throws Exception{                
        if( oper.equalsIgnoreCase("LETRA") ){
            int pos = Values.StringToInteger(param2) + 1; // +1 porque "
            char ch = param1.charAt(pos);
            return Values.StringToText(""+ch);
        }
        else 
            throw new Exception("ERRO fun��o Desconhecida 2 ["+oper+"]");
           }
    
    //------------------------------------------------------------------------
    // resultado seguro destes operadores
    // util para fazer o parse
    //------------------------------------------------------------------------
    public String CalculateSafe( String oper , Vector params)throws Exception{
        return "1";
    }
}
