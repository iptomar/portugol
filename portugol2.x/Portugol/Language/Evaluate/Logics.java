/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Logics.java
 *
 * Created on 2 de Maio de 2006, 12:41
 *
 *
 */

package Portugol.Language.Evaluate;

import Portugol.Language.Utils.Values;
import java.util.Vector;

/**
 * Calculos lógicos<br>
 * <B>Operadores Lógicos definidos :</B> E OU NAO <br>
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
public class Logics  extends AbstractCalculus{
    
    private static String logics1 =" NAO ";
    private static String logics2 =" E OU XOU ";
    
    /**
     * retorna os simbolos da arimtética
     * @return operadores logicos separados por espaço
     */
    public String GetSymbols(){
        return logics1 + logics2;
    }
       
    /**
     * verifica se é um operador lógico
     * @param str nome do operador
     * @return operador válido
     */
    public boolean IsValid( String str){
        return logics1.indexOf(" " + str.toUpperCase() + " ") != -1 ||
                logics2.indexOf(" " + str.toUpperCase() + " ") != -1;
    }
    /**
     * calcula o número de parâmetros
     * @return numero de parâmetos
     * @param str nome do operador
     * @throws java.lang.Exception Erro
     */
    public  int GetNumParameters(String str)throws Exception{
        if(logics1.indexOf(" " + str.toUpperCase() + " ") != -1 ) return 1;
        if(logics2.indexOf(" " + str.toUpperCase() + " ") != -1 ) return 2;
        throw new Exception("ERRO nos parametros do Logico ["+str+"]");
    }
    
    //---------------------------------------------------------------------------
    /**
     * prioridade do operador
     * @return valor da prioridade
     * @param oper nome do operador
     * @throws java.lang.Exception Erro
     */
    public  int GetPriority(String oper)throws Exception{
        if( oper.equalsIgnoreCase("OU"))    return AbstractCalculus.LOGIC_PRIORITY + 1;
        if( oper.equalsIgnoreCase("XOU" ))    return AbstractCalculus.LOGIC_PRIORITY + 1;
        if( oper.equalsIgnoreCase("E" ))    return AbstractCalculus.LOGIC_PRIORITY + 2;
        if( oper.equalsIgnoreCase("NAO"))   return AbstractCalculus.LOGIC_PRIORITY + 3;
        
        throw new Exception("ERRO na prioridade do Logico ["+oper+"]");
        
    }
    
    /**
     * Executa o calculo
     * @return valor do calculo
     * @param oper nome do operador
     * @param params parametros
     * @throws java.lang.Exception erro
     */
    public  String Calculate( String oper , Vector params)throws Exception{
        if( params.size() == 1 )
            return CalculateLogic1(oper,(String)params.get(0));
        if( params.size() == 2 )
            return CalculateLogic2((String)params.get(0),oper,(String)params.get(1));
        
        throw new Exception("ERRO  parametros logicos errados ["+oper+"] " + params.toString() );
        
    }
    
    private  String CalculateLogic2( String str1, String oper , String str2)throws Exception{
        boolean n1 = Values.StringToBoolean(str1);
        boolean n2 = Values.StringToBoolean(str2);
        boolean val=false;
        if( oper.equalsIgnoreCase("E") ) val = n1 && n2;
        else if( oper.equalsIgnoreCase("OU") ) val =  n1 || n2;
        else if( oper.equalsIgnoreCase("XOU") ) val =  n1 != n2;
        else
            throw new Exception("ERROR - Operador Logico binário desconhecido ["+ oper+"]");
        return Values.BooleanToString(val);
    }
//---------------------------------------------------------------------------
    private String CalculateLogic1(String oper , String str1)throws Exception{
        boolean n1 = Values.StringToBoolean(str1);
        boolean val=false;
        if( oper.equalsIgnoreCase("NAO") ) val = !n1;
        else
            throw new Exception("ERROR - Operador Logico unário desconhecido ["+ oper+"]");
        return Values.BooleanToString(val);
        
    }
}
