/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Utils;

import java.util.Iterator;

/**
 *  Este iterador itera uma linha de código através de:
 *    - espaços
 *    - tabulações
 *    - virgulas
 *  respeitando:
 *    - os parentesis rectos
 *    - os parentesis curvos
 *    - as strings
 * 
 * O iterador elimina os caracteres de iteração
 * isto é : retira os espaços as virgulas e as tabs 
 * @author manso
 */
public class IteratorLine implements Iterator {

    /**
     * string a iterar
     */
    protected String str;
    /**
     * ponteiro para o inicio do elemento
     */
    protected int begin;
    /**
     * ponteiro para o fim do elemento
     */
    protected int end;
    /**
     * string com os caracters separadores
     */
    protected String SEPARATOR = " ,\t\n\r";
    protected char STR = '\"';

    /**
     * Controi um iterador com SEPARATOR = " ,\t";
     * @param line - linha de codigo a iterar
     */
    public IteratorLine(String line) {
        begin = 0;
        end = -1;
        //limpar o inicio e o fim
        str = TrimSeparators(line);
        GoNext();
    }

    /**
     * Controi um iterador especificando os separadores
     * @param line - linha de codigo a iterar
     */
    public IteratorLine(String line, String separ) {
        begin = 0;
        end = -1;
        SEPARATOR = separ;
        str = TrimSeparators(line).trim();
        GoNext();
    }

    public boolean hasNext() {
        return str != null && !str.isEmpty() && begin < str.length();
    }

    public String next() {
        String tmp = GetCurrent();
        GoNext();
        return tmp.trim();
    }

    public void remove() {
    }

    /**
     * retorna o elemento corrente
     * @return retorna o elemento corrente
     */
    private String GetCurrent() {
        String tmp = "";
        for (int i = begin; i <= end; i++) {
            tmp += str.charAt(i);
        }
        //remover no inicio
        int index2 = tmp.length() - 1;
        while (index2 > 0 && SEPARATOR.indexOf(tmp.charAt(index2)) >= 0) {
            index2--;
        //remover o fim
        }
        int index1 = 0;
        while (index1 < index2 && SEPARATOR.indexOf(tmp.charAt(index1)) >= 0) {
            index1++;
        }
        return tmp.substring(index1, index2 + 1);

    }

    /**
     * avan�a para o pr�ximo elemento
     */
    private void GoNext() {

        //avançar com o begin
        begin = end + 1;
        //passar os separadores
        while (begin < str.length() &&
                SEPARATOR.indexOf(str.charAt(begin)) >= 0) {
            begin++;
        //actualizar o end
        }
        end = begin;

        while (end < str.length() &&
                SEPARATOR.indexOf(str.charAt(end)) == -1) {
            //passar Matrizes [ ....qualquer coisa.... ]
            if (end < str.length() && str.charAt(end) == '[') {
                int rect = 0;
                while (end < str.length()) {
                    // contar os []
                    if (str.charAt(end) == '[') {
                        rect++;
                    }
                    if (str.charAt(end) == ']') {
                        rect--;
                    }
                    if (rect == 0) {
                        break;
                    }
                    end++;
                }


            } //passar parentesis ( ....qualquer coisa.... )
            else if (end < str.length() && str.charAt(end) == '(') {
                int rect = 0;
                while (end < str.length()) {
                    // contar os []
                    if (str.charAt(end) == '(') {
                        rect++;
                    }
                    if (str.charAt(end) == ')') {
                        rect--;
                    }
                    if (rect == 0) {
                        break;
                    }
                    end++;
                }

            } //----------------------------------------------------------------- 
            //passar as chavetas { ....qualquer coisa.... }
            else if (end < str.length() && str.charAt(end) == '{') {
                int rect = 0;
                while (end < str.length()) {
                    // contar os []
                    if (str.charAt(end) == '{') {
                        rect++;
                    }
                    if (str.charAt(end) == '}') {
                        rect--;
                    }
                    if (rect == 0) {
                        break;
                    }
                    end++;
                }
            } //----------------------------------------------------------------- 
            //passar as strings
            else if (end < str.length() && str.charAt(end) == STR) {
                //passar por cima da aspa "
                end++;
                while (end < str.length()) {
                    //quebrar o ciclo
                    if (str.charAt(end) == STR && (end == 0 || end > 0 && str.charAt(end - 1) != '\\')) {
                        break;
                    }
                    end++;
                }
                //passar por cima da aspa "
                end++;
            } //Instruções normais
            else {
                end++;
            }
        }

        //por causa do ultimo -  GetCurrente vai de [begin ... end]
        if (end == str.length()) {
            end--;
        }
    }

    /**
     * Devolve a string que ainda nao foi processada
     * @return
     */
    public String GetUnprocessedLine() {
        String tmp = "";
        for (int i = begin; i < str.length(); i++) {
            tmp += str.charAt(i);
        }
        return TrimSeparators(tmp);
    }

    /**
     * Elimina os separadores no inicio e no fim da string
     * @param str - String com separadores
     * @return string sem separadores no inicio e no fim
     */
    public String TrimSeparators(String str) {
        str = str.trim();
        //remover no inicio
        int index2 = str.length() - 1;
        while (index2 > 0 && SEPARATOR.indexOf(str.charAt(index2)) >= 0) {
            index2--;
        //remover o fim
        }
        int index1 = 0;
        while (index1 < index2 && SEPARATOR.indexOf(str.charAt(index1)) >= 0) {
            index1++;
        }
        return str.substring(index1, index2 + 1);
    }
}
