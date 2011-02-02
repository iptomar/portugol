/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.openide.util.Exceptions;


public class FileManager {

    /**
     * Este método retorna o conteudo que está no ficheiro
     * que e passado por parametro e que encontra na
     * package SourceCodePortugol
     * @param fileName - nome do ficheiro sem a path
     * @return     - string com o conteudo do ficheiro
     */
    public static String getSource(String fileName) throws IOException {
        InputStream is = FileManager.class.getClassLoader().getResourceAsStream("SourceCodePortugol/" + fileName);

        String line = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();

        while ((line = in.readLine()) != null) {
            buffer.append(line + "\n");
        }

        return buffer.toString();
    }

    /**
     * 
     * Este método grava o programa prog no ficheiro file
     * na  package SourceCodePortugol 
     * @param prog - String com o programa
     * @param fileName - nome do ficheiro sem a path
     */
    public static void saveSource(String prog, String fileName) {
    }
}
