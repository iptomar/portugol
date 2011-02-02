/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * HelpFileName.java
 *
 * Created on 13 de Outubro de 2005, 1:19
 *
 */

package Editor.help;

import Portugol.Language.Evaluate.CalculusElement;



/**
 *
 * @author António Manuel Rodrigues MAnso
 */
public class HelpFileName {
    public static String getHelpFile(String word){
        String keyword = "index";
        if(word.equalsIgnoreCase("se") ) keyword = "se";
        else if(word.equalsIgnoreCase("entao") ) keyword = "se";
        else if(word.equalsIgnoreCase("senao") ) keyword = "se";
        else if(word.equalsIgnoreCase("fimse") ) keyword = "se";
        
        
        else if(word.equalsIgnoreCase("escolhe") ) keyword = "escolhe";
        else if(word.equalsIgnoreCase("caso") ) keyword = "escolhe";
        else if(word.equalsIgnoreCase("defeito") ) keyword = "escolhe";
        else if(word.equalsIgnoreCase("fimescolhe") ) keyword = "escolhe";
        
        else if(word.equalsIgnoreCase("enquanto") ) keyword = "enquanto";
        else if(word.equalsIgnoreCase("fimenquanto") ) keyword = "enquanto";
        
        else if(word.equalsIgnoreCase("repete") ) keyword = "repete";
        
        else if(word.equalsIgnoreCase("faz") ) keyword = "faz";
        
        else if(word.equalsIgnoreCase("para") ) keyword = "para";
        else if(word.equalsIgnoreCase("passo") ) keyword = "para";
        
        else if(word.equalsIgnoreCase("ler") ) keyword = "ler";
        else if(word.equalsIgnoreCase("escrever") ) keyword = "escrever";
        
        
        else if(word.equalsIgnoreCase("INTEIRO")) keyword = "tipo_dados_basicos";
        else if(word.equalsIgnoreCase("REAL")) keyword = "tipo_dados_basicos";
        else if(word.equalsIgnoreCase("CARACTER")) keyword = "tipo_dados_basicos";
        else if(word.equalsIgnoreCase("LOGICO")) keyword = "tipo_dados_basicos";
        else if(word.equalsIgnoreCase("TEXTO")) keyword = "tipo_dados_basicos";
        
        else if(word.equalsIgnoreCase("CONSTANTE")) keyword = "tipo_dados_constantes";
        else if(word.equalsIgnoreCase("E")) keyword = "operadores_logicos";
        else if(word.equalsIgnoreCase("OU")) keyword = "operadores_logicos";
        else if(word.equalsIgnoreCase("NAO")) keyword = "operadores_logicos";
        else if(word.equalsIgnoreCase("VERDADEIRO")) keyword = "operadores_logicos";
        else if(word.equalsIgnoreCase("FALSO")) keyword = "operadores_logicos";
        else if( CalculusElement.IsFunction(word) ) keyword = "funcoes_biblioteca";
        else{
            String opAritm ="+-*/%^";
            String opRelac ="<>=";
            if( opAritm.indexOf(word) != -1) keyword = "operadores_aritmeticos";
            else if( opRelac.indexOf(word) != -1) keyword = "operadores_relacionais";
        }
        return "Editor/help/" + keyword + ".html";
    }
    
}
