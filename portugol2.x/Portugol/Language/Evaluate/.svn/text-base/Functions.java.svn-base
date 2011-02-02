/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Functions.java
 *
 * Created on 7 de Maio de 2006, 10:54
 *
 *
 */

package Portugol.Language.Evaluate;

import java.util.Vector;

/**
 * Funções pré definidas na linguagem
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
public class Functions  extends AbstractCalculus{
     
    private FunctionsMath math = new FunctionsMath();
    private FunctionsText txt = new FunctionsText();
    
    /**
     * veririfica se o parâmetro é um elemento de calculo
     * @param str nome do elemento
     * @return veririfica se o parâmetro é um elemento de calculo
     */
    public boolean IsValid( String str){
        return math.IsValid(str ) || txt.IsValid(str);
    }
    /**
     * Retorn o numero de parametros do elemento
     * @return numero de parâmetros do elemento
     * @param oper elemento
     * @throws java.lang.Exception erro
     */
    public  int GetNumParameters(String oper)throws Exception{
        if( math.IsValid(oper) ) return math.GetNumParameters(oper);
        if( txt.IsValid(oper) ) return txt.GetNumParameters(oper);
        throw new Exception("ERRO : operador de funçoes desconhecido :" + oper );        
    }
    /**
     * calcula a prioridade do elemento
     * @return prioridade do elemento
     * @param oper elemento
     * @throws java.lang.Exception erro
     */
    public  int GetPriority(String oper)throws Exception{
        if( math.IsValid(oper) ) return math.GetPriority(oper);
        if( txt.IsValid(oper) ) return txt.GetPriority(oper);
        throw new Exception("ERRO : operador de funçoes desconhecido :" + oper );
    }
    /**
     * Executa o calculo
     * @return valor do calculo
     * @param oper elemento de calculo
     * @param params parametros do calculo
     * @throws java.lang.Exception erro
     */
    public String Calculate( String oper , Vector params)throws Exception{
        if( math.IsValid(oper) ) return math.Calculate(oper,params);
        if( txt.IsValid(oper) ) return txt.Calculate(oper,params);
        throw new Exception("ERRO : operador de funçoes desconhecido :" + oper );        
    } 
     
 }
