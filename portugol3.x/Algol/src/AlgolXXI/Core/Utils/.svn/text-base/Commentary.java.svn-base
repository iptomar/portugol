/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * @author Antonio M@nso
 */

package AlgolXXI.Core.Utils;

public class Commentary {
    private boolean isComented = false;
    public Commentary( )
    {
        //constructor 
    }
      /**
     * Normaliza uma linha de código
     * retira-lhe os comentários e os espaços
     * @param str linha de código
     * @return linha de código normalizada
     */
    public   String RemoveCommentary(String str){
        StringBuffer newStr = new StringBuffer();
        for(int index=0 ; index< str.length() ; index++ ) {
            switch (str.charAt(index)) {
                case '/':
                    // comentario "//"
                    if(index +1 < str.length() && str.charAt(index+1)=='/')
                        return newStr.toString().trim();
                    //inicio de um comentário /*
                    if(index +1 < str.length() && str.charAt(index+1)=='*')
                        isComented=true;
                    else
                        //fim do comentario */
                        if( index >0 && str.charAt(index-1)=='*')
                            isComented=false;
                    //introduz caracter /
                        else
                            newStr.append(str.charAt(index));
                    break;
                default:
                    // se não for comentario
                    if( !isComented)
                        newStr.append(str.charAt(index));
            }
        }
        return newStr.toString().trim();
    }

}
