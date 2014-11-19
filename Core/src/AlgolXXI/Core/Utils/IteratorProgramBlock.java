/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 15/Mai/2008 - 5:17:29
 * @author Antonio M@nso
 * 
 * Iterador de blocos de programa
 * 
 * este iterador mete um numero no inicio da linha
 * que representa o numero do caracter de inicio
 * que é necessario para selecionar a linha
 * 
 */
package AlgolXXI.Core.Utils;

import AlgolXXI.Core.Parser.Keyword;
import java.util.Iterator;
import java.util.StringTokenizer;


public class IteratorProgramBlock implements Iterator {

    StringTokenizer tok;
    private int charPosition;

    public IteratorProgramBlock(String program) {
        tok = new StringTokenizer(program, "\n\r");
        charPosition = 0;
    }

    public boolean hasNext() {
        return tok.hasMoreElements();
    }

    public String next() {
        //passar os comentarios
        //--------------------------------------------
        Commentary comment = new Commentary();
        String line, code;
        do {
            line = tok.nextToken();
            charPosition += line.length()+1;// terminador
            //retirar os comentarios da linha

            code = comment.RemoveCommentary(line);
            if (code.length() != 0) {
                break;
            }

        } while (hasNext());
        //---------------------------------------------
        int key = Keyword.GetKey(code);
        //se for a definição de uma função
        if (CodeLine.IsFuncion(code) || CodeLine.IsStructure(code)) {
            String codeline = charPosition + "\t" + code;
            return getFunction(codeline);
        } //se for a definição de uma variavel
        else if (key == Keyword.DEFINIR) {
            String codeline = charPosition + "\t" + code;
            return codeline;
        } else // Erro de codificação
        {
            charPosition += line.length() + 1;
            return "ERROR : " + line;
        }

    }

    public void remove() {
    }

    /**
     * Devolve um bloco de intruções sem os comentarios
     * @param start
     * @return
     */
    public String getFunction(String start) {
        String line = "", code = "";
        Commentary comment = new Commentary();
        StringBuffer elem = new StringBuffer();
        elem.append(start + "\n");
        while (tok.hasMoreElements()) {
            //introduzir o numero da linha
            line = tok.nextToken();
            //actualizar a posicao do caracter
            charPosition += line.length()+1;
            code = comment.RemoveCommentary(line);
            //retirar as linhas em branco
            if (code.isEmpty()) {
                continue;
            }
            elem.append(charPosition + "\t" + code + "\n");

            //Bloco termina por fim . . . .
            if (Keyword.GetKey(code) == Keyword.FIM) {
                break;
            }
        }

        return elem.toString();
    }

    /**
     * retorna a posição inicial do último bloco que foi retornado
     * NOTA: primeiro faz-se o next() e só depois o getCharPosition()
     * @return
     */
    public int getCharPosition() {
        return charPosition + 1;
    }
}
