/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Memory;

import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Utils.CodeLine;
import AlgolXXI.Core.Utils.IteratorLine;
import java.util.Vector;

/**
 *
 * @author manso
 */
public class Symbol {

    static final int UNKNOW = 0;
    static final int DATA = 1;
    static final int STRUCTURE = 2;
    static final int ARRAY = 3;
    static final int FUNCTION = 4;
    protected String name;     //nome da variavel
    protected int symbolType;

    public Symbol(int type) {
        this.name = "SEM NOME";
        symbolType = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        name = newName;
    } 
    public int getSymbolType() {
        return symbolType;
    }

    public static int getType(String type) {
        if (type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.INTEIRO))) {
            return Keyword.INTEIRO;
        }
        if (type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.REAL))) {
            return Keyword.REAL;
        }
        if (type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.LOGICO))) {
            return Keyword.LOGICO;
        }
        if (type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.CARACTER))) {
            return Keyword.CARACTER;
        }
        if (type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.TEXTO))) {
            return Keyword.TEXTO;
        }
        if (type.equalsIgnoreCase(Keyword.GetTextKey(Keyword.PONTEIRO))) {
            return Keyword.PONTEIRO;
        }
        return Keyword.DESCONHECIDO;
    }

    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------   
    public static Vector<String> getDefinitionTokens(String line) {
        String CONSTANTE = Keyword.GetTextKey(Keyword.CONSTANTE);
        String VARIAVEL = Keyword.GetTextKey(Keyword.VARIAVEL);
        line = CodeLine.removeLineNumber(line);
        IteratorLine it = new IteratorLine(line);
        String alter = it.next().toUpperCase();
        String type;
        //verificar se é constante ou variavel
        if (alter.equalsIgnoreCase(CONSTANTE)) {
            type = it.next().toUpperCase();

        } else if (alter.equals(VARIAVEL)) {
            type = it.next().toUpperCase();
        } else {
            type = alter;
            alter = VARIAVEL;
        }
        String name = it.next();
        String value = "";
        //valores
        if (it.hasNext()) {
            String attrib = it.next();
            value = it.next();
        }
        Vector<String> v = new Vector<String>();
        v.add(alter);
        v.add(type);
        v.add(name);
        v.add(value);
        return v;
    }
    //-----------------------------------------------------------------
    //NOTA: por causa dos arrays
    public static String iniArray ="\uc794";
    public static String finArray = "\uc894";
    public void normalizeName()
    {
        name = normalizeName(name);
    }
    //NOTA: por causa dos arrays
    public String normalizeName(String orig)
    {
        orig = orig.replaceAll(iniArray, "[");
        orig = orig.replaceAll(finArray, "]");
        return orig;
    }
    //-----------------------------------------------------------------

    public static String getDefAlter(String line) {
        return getDefinitionTokens(line).get(0);
    }
    //-----------------------------------------------------------------

    public static String getDefType(String line) {
        return getDefinitionTokens(line).get(1);
    }
    //-----------------------------------------------------------------

    public static String getDefName(String line) {
        return getDefinitionTokens(line).get(2);
    }
    
    public static String getDefValue(String line) {
        return getDefinitionTokens(line).get(3);
    }
    
      public static String getDefArrayName(String line) {
          // inteiro j[14]«12»[16]«20»[50]
          
//        String name = getDefinitionTokens(line).get(2);
//        String previous="";
//        StringTokenizer it = new StringTokenizer(name,"«»",true);
//        while( it.hasMoreTokens()){
//            String elem=it.nextToken();
//            if( elem)
//        }
           

        return "";//txt.toString();
    }
    //-----------------------------------------------------------------

}
