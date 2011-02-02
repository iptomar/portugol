/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 2/Jun/2008 - 20:45:32
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Utils.LanguageException;

public class SymbolDataSimple extends SymbolData {

    public SymbolDataSimple(String modify, String type, String name, String value, int level)
            throws LanguageException {
        super(Symbol.DATA, false);
        this.isConst = modify.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CONSTANTE));
        this.type = Keyword.getFastKey(type);
        if (this.type == Keyword.DESCONHECIDO) {
            throw new LanguageException(" Tipo desconhecido :" + type,
                    "verifique os tipos de dados");
        }
        this.name = name.trim();
        if (name.isEmpty()) {
            throw new LanguageException(" O nome da variavel não pode ser nulo " ,
                    " de um nome á variavel");
        }
        this.level = level;
        endereco = NUMADRESS++;
        objectValue = new DataObject(this.type,value);
    }
    //NOTA: isto faz uma referencia do symbolo
    public SymbolDataSimple(SymbolDataSimple other,String newName) throws LanguageException {
        //contrutor de symbolos
        super(Symbol.DATA, true);
        this.type = other.type;
        this.isConst = other.isConst;
        this.type = other.type;
        this.name = newName;
        this.level = other.level;
        endereco = NUMADRESS++;
        objectValue = other.objectValue;
    }
    //hard copy do symbolo
    public SymbolDataSimple getClone(String newName) throws LanguageException {
        return new SymbolDataSimple(
                this.getModify(), this.getStringType(),
                newName, this.getValue(), this.level);
    }
}
