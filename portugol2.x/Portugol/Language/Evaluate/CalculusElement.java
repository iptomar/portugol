/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * CalculusElement.java
 *
 * Created on 2 de Maio de 2006, 13:02
 *
 *
 */

package Portugol.Language.Evaluate;

import java.util.Vector;

/**
 * Executa o calculo de um elemento
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
public class CalculusElement {
    
   static Vector elemCalc = new Vector();
   
   static{
        elemCalc.add( new Functions());
        elemCalc.add( new Aritmetics());
        elemCalc.add( new Logics());
        elemCalc.add( new Relationals());      
   }
    
    /**
     * verifica se é uma função
     * @param str texto
     * @return verdadeiro se é uma função
     */
   public static boolean IsFunction( String str){
       return ((AbstractCalculus) elemCalc.get(0) ).IsValid(str);
   }
   
    /**
     * verifica se é um operador aritmético
     * @param str texto
     * @return verdadeiro se é um operador aritmético
     */
   public static boolean IsAritmetic( String str){
       return ((AbstractCalculus) elemCalc.get(1) ).IsValid(str);
   }
   
    /**
     * verifica se é um operador lógico
     * @param str texto
     * @return verdadeiro se é um operador lógico
     */
   public static boolean IsLogic( String str){
       return ((AbstractCalculus) elemCalc.get(2) ).IsValid(str);
   }
   
    /**
     * verifica se é um operador relacional
     * @param str texto
     * @return verdadeiro se é um operador relacional
     */
   public static boolean IsRelational( String str){
       return ((AbstractCalculus) elemCalc.get(3) ).IsValid(str);
   }
   
   
    /**
     * verifica se é um operador relacional logico ou aritmético
     * @param str texto
     * @return verdadeiro se é um operador 
     */
   public static boolean IsOperator( String str){
       return IsRelational(str) || IsLogic(str) || IsAritmetic(str);               
   }
   
    /**
     * verifica se o texto é um elemento de calculo (função ou operador)
     * @param str texto
     * @return verdadeiro se for função ou operador
     */
   public static boolean IsElemCalculus( String str){
       return  IsFunction(str) ||
               IsAritmetic(str) ||
               IsLogic(str) ||
               IsRelational(str);
   }
    
    /**
     * verifica se é um elemento de cálculo valido (função ou operador valor, etc.)
     * @param str nome do elemento
     * @return elemento de calculo válido
     */
    public boolean IsCalculus( String str){
        if( str.equals("(") || str.equals(")") )
            return true;
        
        for( int index=0; index< elemCalc.size() ; index++){
            if ( ((AbstractCalculus) elemCalc.get(index) ).IsValid(str) )
                return true;
        }
        return false;
    }
    
    /**
     * retorna o número de parametros do elemento
     * @return numero de parametros
     * @param str nome do elemento
     * @throws java.lang.Exception erro
     */
    public  int GetNumParameters(String str)throws Exception{
        for( int index=0; index< elemCalc.size() ; index++){
            if ( ((AbstractCalculus) elemCalc.get(index) ).IsValid(str) )
                return ((AbstractCalculus) elemCalc.get(index) ).GetNumParameters(str);
        }
        throw new Exception("ERRO Parâmetros DE OPERADOR DESCONHECIDO [" + str +"]");
    }
    
    //---------------------------------------------------------------------------
    /**
     * prioridade do elemento
     * @return prioridade
     * @param str nome do elemento
     * @throws java.lang.Exception erro
     */
    public int GetPriority(String str)throws Exception{
        if( str.equalsIgnoreCase("(") )     return 0;
        if( str.equalsIgnoreCase(")") )     return 0;
        for( int index=0; index< elemCalc.size() ; index++){
            if ( ((AbstractCalculus) elemCalc.get(index) ).IsValid(str) )
                return ((AbstractCalculus) elemCalc.get(index) ).GetPriority(str);
        }
        
        throw new Exception("ERRO na Prioridade de ["+str+"]");
    }
    
    
    /**
     * Executa o calculo do elemento
     * @return valor do calculo
     * @param str nome do elemento
     * @param params parametros
     * @throws java.lang.Exception erro
     */
    public String Calculate( String str , Vector params)throws Exception{
        for( int index=0; index< elemCalc.size() ; index++){
            if ( ((AbstractCalculus) elemCalc.get(index) ).IsValid(str) )
                return ((AbstractCalculus) elemCalc.get(index) ).Calculate(str,params);
        }
        throw new Exception("ERRO NO CALCULO DE OPERADOR DESCONHECIDO [" + str +"]" + params.toString());
    }        
}
