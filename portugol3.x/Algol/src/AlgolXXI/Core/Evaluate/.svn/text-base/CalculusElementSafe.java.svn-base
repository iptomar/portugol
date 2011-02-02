/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/* CalculusElementSafe.java
 *
 * Created on 15 de Outubro de 2006, 10:56
 *
 */

package AlgolXXI.Core.Evaluate;

import java.util.Vector;

/**
 *
 * @author: Ant�nio M@nuel Rodrigues M@nso
 */
public class CalculusElementSafe extends CalculusElement{
    
    /** Creates a new instance of CalculusElementSafe */
    public CalculusElementSafe() {
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
                return ((AbstractCalculus) elemCalc.get(index) ).CalculateSafe(str,params);
        }
        throw new Exception("ERRO NO CALCULO DE OPERADOR DESCONHECIDO [" + str +"]" + params.toString());
    }        
    
}
