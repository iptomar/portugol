/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Converters;

import AlgolXXI.Core.Execute.ExecuteProgram;
import AlgolXXI.Core.Function;
import AlgolXXI.Core.Make.MakeProgram;
import AlgolXXI.Core.NodeFluxo;

/**
 *
 * @author Apocas
 */
public abstract class Converter {

    public ExecuteProgram executor = null;
    public String codigo = "";
    public MakeProgram programa = null;
    public String inicio = "";
    public String fim = "";

    public String convert() throws Exception {
        /*
        String codigo_final = inicio + codigo;
        for (Function bloco : programa.getBlock()) {
        codigo = "";
        convert(bloco.getFlux().getStart());
        codigo_final = codigo_final + codigo;
        }
        return codigo_final + fim;
         * */
        return "";
    }

    public void addString(String str) {
        codigo = codigo + str;
    }

    abstract void convert(NodeFluxo node) throws Exception;

    abstract String getConvertedType(String retorno);
}
