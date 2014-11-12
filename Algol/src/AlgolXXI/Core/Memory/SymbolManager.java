/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 18/Mai/2008 - 13:48:54
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Utils.IteratorArray;
import AlgolXXI.Core.Utils.LanguageException;

public class SymbolManager {
    //retorna um array de estruturas
    public static SymbolArray getStructArray(SymbolStructure model, String varName, int level)
            throws LanguageException {
        IteratorArray it = new IteratorArray(varName);
        String name = it.getNext(); //tirar as 

        int dimension = Integer.valueOf(it.getNext());
        SymbolArray varStruct = new SymbolArray(name, dimension);
        varStruct.level = level;
        SymbolData newVar;

        for (int i = 0; i < dimension; i++) {
            //esconder a dimensao
            String nameNewVar = name + Symbol.iniArray + i + Symbol.finArray;
            if (!it.hasMoreElements()) {
                //newVar = new SymbolStructure(model, nameNewVar, level);
                newVar = new SymbolStructure(model, nameNewVar, level);
            } else {
                newVar = getStructArray(model, nameNewVar + it.getUnprocessed(), level);
            //colocar o nome correcto  
            }
            newVar.normalizeName();
            varStruct.getData().add(newVar);
        }
//        varStruct.setObjectValue(varStruct.getData());
        return varStruct;
    }

     //O nome da variavel 
    public static SymbolArray getCloneArray(SymbolArray model, String varName, int level)
            throws LanguageException {

        String name = varName; 

        int dimension = model.getSize();
        SymbolArray varStruct = new SymbolArray(name, dimension);
        SymbolData newVar;

        for (SymbolData sym : model.getData()) {
             if( sym instanceof SymbolArray){
                newVar = getCloneArray((SymbolArray)sym,varName, level);
            } else {
                newVar = getDataSymbol(sym,varName, level);
            //colocar o nome correcto              
            }
            newVar.name = varName +"."+ sym.name;
            newVar.normalizeName();
            varStruct.getData().add(newVar);
        }
        varStruct.type = Symbol.ARRAY;
//        varStruct.setObjectValue(varStruct.getData());
        return varStruct;
    }

    
    //O nome da variavel 
    public static SymbolArray getDataArray(SymbolData model, String varName, int level)
            throws LanguageException {
        //um array de estruturas
        if( model instanceof SymbolStructure)
            return getStructArray((SymbolStructure)model, varName, level);
        
        IteratorArray it = new IteratorArray(varName);
        String name = it.getNext(); //tirar as 

        int dimension = Integer.valueOf(it.getNext());
        SymbolArray varStruct = new SymbolArray(name, dimension);
        SymbolData newVar;

        for (int i = 0; i < dimension; i++) {
            //esconder a dimensao
            String nameNewVar = name + Symbol.iniArray + i + Symbol.finArray;
            if (!it.hasMoreElements()) {
                newVar = getDataSymbol(model, nameNewVar, level);
            } else {
                newVar = getDataArray(model, nameNewVar + it.getUnprocessed(), level);
            //colocar o nome correcto              
            }
         
            newVar.name = nameNewVar;
            newVar.normalizeName();
            varStruct.getData().add(newVar);
        }
    
        varStruct.type = Symbol.ARRAY;
//        varStruct.setObjectValue(varStruct.getData());
        return varStruct;
    }

    public static SymbolData getDataSymbol(SymbolData symb, String newName, int level)
            throws LanguageException {
        String _mod = symb.getModify();
        String _type = symb.getTypeName();
        String _name = newName;
        String _value = symb.getValue();

        if (symb instanceof SymbolStructure) {
            //return new SymbolStructure((SymbolStructure) symb, newName, level);
            return SymbolManager.getCloneStructure((SymbolStructure) symb, newName, level);
        }
        return new SymbolDataSimple(_mod,_type,_name,_value,level);
    }
    
    public static SymbolData getValueSymbol(String value, String name, int level)
            throws LanguageException {

        String _mod = Keyword.GetTextKey(Keyword.VARIAVEL);
       
//        if (Values.IsInteger(value)) {
//            //return new SymbolInteger(_mod, Keyword.GetTextKey(Keyword.INTEIRO),name, value, level);
//            return new SymbolInteger(_mod, Keyword.GetTextKey(Keyword.INTEIRO),name, value, level, null);
//        }
//        if (Values.IsReal(value)) {
//            //return new SymbolReal(_mod, Keyword.GetTextKey(Keyword.REAL),name, value, level);
//            return new SymbolReal(_mod, Keyword.GetTextKey(Keyword.REAL),name, value, level, null);
//        }
//        if (Values.IsBoolean(value)) {
//            //return new SymbolLogic(_mod, Keyword.GetTextKey(Keyword.LOGICO),name, value, level);
//            return new SymbolLogic(_mod, Keyword.GetTextKey(Keyword.LOGICO),name, value, level, null);
//        }
//        if (Values.IsString(value)) {
//            //return new SymbolText(_mod, Keyword.GetTextKey(Keyword.TEXTO),name, value, level);
//            return new SymbolText(_mod, Keyword.GetTextKey(Keyword.TEXTO),name, value, level, null);
//        }
//        if (Values.IsCharacter(value)) {
//            //return new SymbolCharacter(_mod, Keyword.GetTextKey(Keyword.CARACTER),name, value, level);
//            return new SymbolCharacter(_mod, Keyword.GetTextKey(Keyword.CARACTER),name, value, level, null);
//        }
        return null;
    }
    
    

    public static SymbolData getDataSymbol(String _mod, String _type, String _name, String _value, Memory mem, int level)
            throws LanguageException {
        //retirar os [][][][]
        //os arrays são feitos por outro método com este modelo
        int iarray = _name.indexOf("[");
        if( iarray> 0)
          _name = _name.substring(0,iarray);
       
        switch (Keyword.GetKeySymbol(_type)) {
//            case Keyword.INTEIRO:
//                //return new SymbolInteger(_mod, _type, _name, _value, level);
//                return new SymbolInteger(_mod, _type, _name, _value, level, null);
//            case Keyword.REAL:
//                //return new SymbolReal(_mod, _type, _name, _value, level);
//                return new SymbolReal(_mod, _type, _name, _value, level, null);
//            case Keyword.LOGICO:
//                //return new SymbolLogic(_mod, _type, _name, _value, level);
//                return new SymbolLogic(_mod, _type, _name, _value, level, null);
//            case Keyword.TEXTO:
//                //return new SymbolText(_mod, _type, _name, _value, level);
//                return new SymbolText(_mod, _type, _name, _value, level, null);
//            case Keyword.CARACTER:
//                //return new SymbolCharacter(_mod, _type, _name, _value, level);
//                return new SymbolCharacter(_mod, _type, _name, _value, level, null);
            default:
                return null;
        }
    }
    
    //-----------------------------------------------------------
//-----------------------------------------------------------
    public static SymbolData getCloneStructure( SymbolStructure model, String name, int level)
    throws LanguageException {
        //retornar uma array de estruturas
        if( SymbolArray.isArray(name))
            return getStructArray(model, name, level);
        
        
        SymbolStructure struct = new SymbolStructure(model);
        struct.name = name;
        struct.type = Symbol.STRUCTURE;
        struct.level = level;
        SymbolData field;
        for (SymbolData sym : model.data) {
            //se for uma estrutura
            if( sym instanceof SymbolStructure ){
               field = getCloneStructure((SymbolStructure)sym,name,level);
            }
            //se for um array
            else if(sym instanceof SymbolArray) {
                field = getCloneArray((SymbolArray)sym, name, level);
            }
            //se for uma variavel simples
            else {
                field = SymbolManager.getDataSymbol(sym,sym.name, level);
            }
            field.name = name + "." + sym.name;
            field.normalizeName();
            struct.data.add(field);
        }
//        struct.setObjectValue(struct.data);                
        
        return struct;
    }
    
     
}
