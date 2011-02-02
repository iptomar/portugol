/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 1/Jun/2008 - 5:03:45
 * @author Antonio M@nso
 */

package AlgolXXI.Core.Make;

import AlgolXXI.Core.Function;
import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Parser.ProgramTokens;
import AlgolXXI.Core.Utils.LanguageException;


public class MakeFunction {
    public static void Make(ProgramTokens prog, Memory mem, int level) throws LanguageException{
        Function func = new Function(prog, mem, level+1);
        mem.addDefFunction(func);
        
    }
  
  
   
}
