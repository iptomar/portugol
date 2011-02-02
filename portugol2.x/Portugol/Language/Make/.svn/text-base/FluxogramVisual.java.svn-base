/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
 
/*
 * MakeVisual.java
 *
 * Created on 14 de Junho de 2006, 17:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Portugol.Language.Make;

import Portugol.Language.Parse.Keyword;

/**
 * Cálculo das posições visusuais do fluxograma
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
public class FluxogramVisual {
    
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//------------                                             ----------------------------
//------------       CALCULATE POSITIONS                    ----------------------------
//------------                                              ---------------------------
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Calcular as posições X e Y dos nodos
     * @param start inicio do fluxograma
     */
    public static void CalculateVisual(NodeInstruction start ){        
        NodeInstruction pt = start;
        double positionY=1;
        while( pt != null){
            ProcessNodePosition(pt,positionY,0);
            positionY = pt.GetPositionY() + 1;
            if( pt.GetType() == Keyword.FAZ || pt.GetType() == Keyword.REPETE)
                pt = pt.GetIfTrue();
            else
                pt = pt.GetNext();
        }
    }
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    /**
     * Processa um nodo
     * @param node nodo a processar
     * @param Y Y do nodo
     * @param X X do nodo
     */
    public static void ProcessNodePosition(NodeInstruction node ,double Y, double X ){
        switch( node.GetType() ){
            case Keyword.SE:
                ExpandSe.CalculatePositions(node,Y,X);
                break;
                
            case Keyword.ENQUANTO:
                ExpandEnquanto.CalculatePositions(node,Y,X);
                break;
                
            case Keyword.FAZ:
                ExpandFazEnquanto.CalculatePositions(node,Y,X);
                break;
            case Keyword.REPETE:
                ExpandRepeteAte.CalculatePositions(node,Y,X);
                break;
            
                // o conector já foi processado
            case Keyword.CONECTOR:
                break;
                
            default:
                node.SetPositionX(X);
                node.SetPositionY(Y);
                break;
                
        }//switch
        
    }
}
