/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
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
package AlgolXXI.Editor.Help;

/**
 *
 * @author Ant�nio Manuel Rodrigues MAnso
 */
public class HelpFileName {

    public static String getHelpFile(String word) {
        String keyword = "index";
        if (word.equalsIgnoreCase("se")) {
            keyword = "se";
        } else if (word.equalsIgnoreCase("entao")) {
            keyword = "se";
        } else if (word.equalsIgnoreCase("senao")) {
            keyword = "se";
        } else if (word.equalsIgnoreCase("fimse")) {
            keyword = "se";
        } else if (word.equalsIgnoreCase("escolhe")) {
            keyword = "escolhe";
        } else if (word.equalsIgnoreCase("caso")) {
            keyword = "escolhe";
        } else if (word.equalsIgnoreCase("defeito")) {
            keyword = "escolhe";
        } else if (word.equalsIgnoreCase("fimescolhe")) {
            keyword = "escolhe";
        } else if (word.equalsIgnoreCase("enquanto")) {
            keyword = "enquanto";
        } else if (word.equalsIgnoreCase("fimenquanto")) {
            keyword = "enquanto";
        } else if (word.equalsIgnoreCase("repete")) {
            keyword = "repete";
        } else if (word.equalsIgnoreCase("faz")) {
            keyword = "faz";
        } else if (word.equalsIgnoreCase("para")) {
            keyword = "para";
        } else if (word.equalsIgnoreCase("passo")) {
            keyword = "para";
        } else if (word.equalsIgnoreCase("ler")) {
            keyword = "ler";
        } else if (word.equalsIgnoreCase("escrever")) {
            keyword = "escrever";
        } else if (word.equalsIgnoreCase("INTEIRO")) {
            keyword = "tipo_dados_basicos";
        } else if (word.equalsIgnoreCase("REAL")) {
            keyword = "tipo_dados_basicos";
        } else if (word.equalsIgnoreCase("CARACTER")) {
            keyword = "tipo_dados_basicos";
        } else if (word.equalsIgnoreCase("LOGICO")) {
            keyword = "tipo_dados_basicos";
        } else if (word.equalsIgnoreCase("TEXTO")) {
            keyword = "tipo_dados_basicos";
        } else if (word.equalsIgnoreCase("CONSTANTE")) {
            keyword = "tipo_dados_constantes";
        } else if (word.equalsIgnoreCase("E")) {
            keyword = "operadores_logicos";
        } else if (word.equalsIgnoreCase("OU")) {
            keyword = "operadores_logicos";
        } else if (word.equalsIgnoreCase("NAO")) {
            keyword = "operadores_logicos";
        } else if (word.equalsIgnoreCase("VERDADEIRO")) {
            keyword = "operadores_logicos";
        } else if (word.equalsIgnoreCase("FALSO")) {
            keyword = "operadores_logicos";
        // else if( CalculusElement.IsFunction(word) ) keyword = "funcoes_biblioteca";
        } else if (word.equalsIgnoreCase("arrays")) {
            keyword = "tipo_dados_estruturados";
        } else if (word.equalsIgnoreCase("aleatorio")) {
            keyword = "Func_aleatorio";
        } else if (word.equalsIgnoreCase("sen")) {
            keyword = "Func_Seno";
        } else if (word.equalsIgnoreCase("cos")) {
            keyword = "Func_Coseno";
        } else if (word.equalsIgnoreCase("tan")) {
            keyword = "Func_tangente";
        } else if (word.equalsIgnoreCase("ctg")) {
            keyword = "Func_Cotangente";
        } else if (word.equalsIgnoreCase("senh")) {
            keyword = "Func_Senoh";
        } else if (word.equalsIgnoreCase("cosh")) {
            keyword = "Func_Cosenoh";
        } else if (word.equalsIgnoreCase("tanh")) {
            keyword = "Func_Tanh";
        } else if (word.equalsIgnoreCase("ctgh")) {
            keyword = "Func_Ctgh";
        } else if (word.equalsIgnoreCase("atan")) {
            keyword = "Func_ArcoTangente";
        } else if (word.equalsIgnoreCase("asen")) {
            keyword = "Func_ArcoSeno";
        } else if (word.equalsIgnoreCase("acos")) {
            keyword = "Func_ArcoCoseno";
        } else if (word.equalsIgnoreCase("actg")) {
            keyword = "Func_ArcoCotangente";
        } else if (word.equalsIgnoreCase("exp")) {
            keyword = "Func_exponencial";
        } else if (word.equalsIgnoreCase("abs")) {
            keyword = "Func_absoluto";
        } else if (word.equalsIgnoreCase("raiz")) {
            keyword = "Func_raiz";
        } else if (word.equalsIgnoreCase("log")) {
            keyword = "Func_logaritmo";
        } else if (word.equalsIgnoreCase("ln")) {
            keyword = "Func_logaritmo_E";
        } else if (word.equalsIgnoreCase("int")) {
            keyword = "Func_Int";
        } else if (word.equalsIgnoreCase("frac")) {
            keyword = "Func_frac";
        } else if (word.equalsIgnoreCase("arred")) {
            keyword = "Func_arred";
        } else if (word.equalsIgnoreCase("potencia")) {
            keyword = "Func_potencias";
        } else if (word.equalsIgnoreCase("vazio")) {
            keyword = "procedimentos";
        } else if (word.equalsIgnoreCase("funcao")) {
            keyword = "funcoes";
        } else if (word.equalsIgnoreCase("estrutura")) {
            keyword = "estrutura";
        }
        else {
            String opAritm = "+-*/%^";
            String opRelac = "<>=";
            if (opAritm.indexOf(word) != -1) {
                keyword = "operadores_aritmeticos";
            } else if (opRelac.indexOf(word) != -1) {
                keyword = "operadores_relacionais";
            }
        }

        return "AlgolXXI/Editor/Help/Contents/" + keyword + ".html";
    }
}
