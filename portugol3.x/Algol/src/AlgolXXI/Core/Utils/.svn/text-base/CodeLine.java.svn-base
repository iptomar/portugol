/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Parser.Keyword;
import java.util.StringTokenizer;

/**
 *
 * @author manso
 */
public class CodeLine {

    /**
     * Verifica se é a definição de uma estrutura
     * @param line - linha de código
     * @return verdadeiro se for a definição de uma estrutura
     */
    public static boolean IsStructure(String line) {
        line = removeLineNumber(line);
        //funcao principal
        if (line.toUpperCase().startsWith(Keyword.GetTextKey(Keyword.ESTRUTURA))) {
            return true;
        }
        return false;
    }

    /**
     * verifica se é uma função
     * este metodo precisa de ser melhorado
     * neste momento verifica se o parentesis aparece antes da atribuicao
     * @param line
     * @return
     */
    public static boolean IsFuncion(String program) {
        program = removeLineNumber(program);
        //primeira linha
        StringTokenizer t = new StringTokenizer(program, "\n\r");
        //--------------------------------------------------
        String line = t.nextToken();
        //funcao principal
        if (Keyword.GetKey(line) == Keyword.INICIO) {
            return true;
        //se nao for uma definicao
        }
        if (Keyword.GetKey(line) != Keyword.DEFINIR) {
            return false;
        }
        int index1 = line.indexOf("(");
        int index2 = line.indexOf(Keyword.ATRIBUI);
        //se nao existir parentesis
        if (index1 < 0) {
            return false;
        //não existe atribuição mas existe um parentesis
        }
        if (index1 > 0 && index2 < 0) {
            return true;
        //o parentesis aparece depois da atribuição
        }
        if (index2 < index1) {
            return false;
        }
        return false;
    }
    
    public static String removeLineNumber(String str) {
        str = str.trim();
        int index = 0;
        while (index < str.length()) {
            if (str.charAt(index) >= '0' && str.charAt(index) <= '9') {
                index++;
            } else {
                break;
            }
        }
        return str.substring(index).trim();
                
    }
    public static int getLineNumber(String str) {
        str = str.trim();
        int index = 0;
        while (index < str.length()) {
            if (str.charAt(index) >= '0' && str.charAt(index) <= '9') {
                index++;
            } else {
                break;
            }
        }
        return Integer.valueOf(str.substring(0,index));
                
    }
}
