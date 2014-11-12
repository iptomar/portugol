/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * ExceptionCompileError.java
 *
 * Created on 12 de Outubro de 2005, 22:32
 *
 */

package AlgolXXI.Core.Utils;
/**
 * Exce��es da linguagem
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
public class LanguageException extends Exception {
    
    /**
     * numero da linha
     */
    public int line ; // numero da linha
    /**
     * texto da linha
     */
    public String codeLine;  //Linha de c�digo
    /**
     * causa da excep��o
     */
    public String error;
    /**
     * solu��o da excep��o
     */
    public String solution;
    
  
    /**
     * Contrutor
     * @param l n�mero da linha
     * @param code texto da linha de c�digo
     * @param e texto do erro
     * @param s texto da solu��o
     */
    public LanguageException(int l, String code, String e, String s) {
        super(e);
        line = l;
        codeLine = code;
        error = e;
        solution = s;
    }
    
    /**
     * Contrutor
     * @param e texto do erro
     * @param s Texto da excep��o
     */
    public LanguageException(String e, String s) {
        super(e);
        line = 0;
        codeLine = "";
        error = e;
        solution = s;
    }
     /**
      * Excepções baseadas no codigo de erro
      * @param line - linha onde ocorreu o erro para poder destacada
      * @param CODEID - Código do ERRO
      */
     public LanguageException( int line , int ERROR_ID)
     {
         super();
         this.line = line;
         // esta string é lida do ficheiro XML como o erro ERROR_ID
         error = " NECESSARIO UM FICHEIRO XML COM ERROS";
         // esta string é lida do ficheiro XML como o erro ERROR_ID
         solution = " PARA IR BUSCAR AS EXCEPÇOES";
     }
    /**
     * Mostra a excep��o
     */
    public void Show(){
       System.out.println(GetError());
    }
    
    /**
     * devolve uma string com a excep��o
     * @return texto da excep��o
     */
    public String GetError(){
        StringBuffer str = new StringBuffer();
        str.append("INSTRU��O:\t" +codeLine  + "\n");
        str.append("ERRO:\t" + error  + "\n");
        str.append("SOLU��O:\t" +solution +"\n");
        str.append("Linha:\t" +line );
        return str.toString();
    }
    
    /**
     * return object String
     * @return excep��o
     */
    public String toString(){
       return GetError();
    }
    
}
