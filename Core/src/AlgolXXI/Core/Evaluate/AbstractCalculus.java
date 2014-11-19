/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * AbstractCalculus.java
 *
 * Created on 4 de Maio de 2006, 9:48
 *
 *
 */

package AlgolXXI.Core.Evaluate;

import java.util.Vector;

/**
 * Representa um elemento de calculo abstractro
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
public abstract class AbstractCalculus {
    /**
     * Prioridade dos operadores L�gicos
     */
    public static int LOGIC_PRIORITY = 100;
    /**
     * Prioridade dos operadores Relacionais
     */
    public static int RELATIONAL_PRIORITY = 200;
    /**
     * Prioridade dos operadores Aritm�ticos
     */
    public static int ARITMETIC_PRIORITY = 300;
    /**
     * Prioridade das Fun��es
     */
    public static int FUNCTION_PRIORITY = 400;
    
    /**
     * veririfica se o par�metro � um elemento de calculo
     * @param str nome do elemento
     * @return veririfica se o par�metro � um elemento de calculo
     */
    public abstract boolean IsValid( String str);
    /**
     * Retorn o numero de parametros do elemento
     * @return numero de par�metros do elemento
     * @param oper elemento
     * @throws java.lang.Exception erro
     */
    public abstract int GetNumParameters(String oper)throws Exception;
    /**
     * calcula a prioridade do elemento
     * @return prioridade do elemento
     * @param oper elemento
     * @throws java.lang.Exception erro
     */
    public abstract int GetPriority(String oper)throws Exception;
    /**
     * Executa o calculo
     * @return valor do calculo
     * @param oper elemento de calculo
     * @param params parametros do calculo
     * @throws java.lang.Exception erro
     */
    public abstract String Calculate( String oper , Vector params)throws Exception;   
    /**
     * Executa o calculo e devolve sem pre os valores por defeito
     * �til para fazer o parse
     * @return valor do calculo
     * @param oper elemento de calculo
     * @param params parametros do calculo
     * @throws java.lang.Exception erro
     */
    public abstract String CalculateSafe( String oper , Vector params)throws Exception;   
}
