/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 25/Mai/2008 - 4:15:50
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Make;

import AlgolXXI.Core.Evaluate.Expression;
import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Memory.SymbolArray;

import AlgolXXI.Core.Memory.SymbolData;
import AlgolXXI.Core.Memory.SymbolDataComplex;
import AlgolXXI.Core.Memory.SymbolDataSimple;
import AlgolXXI.Core.Memory.SymbolStructure;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.LineTokens;
import AlgolXXI.Core.Parser.ProgramTokens;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorElemTokens;
import AlgolXXI.Core.Utils.IteratorLineTokens;
import AlgolXXI.Core.Utils.LanguageException;

public class MakeSymbol {

    public MakeSymbol() {
        //constructor 
    }
    private static final int DATA = 1;
    private static final int ARRAY = 2;
    private static final int STRUCT = 3;

    public static void Make(ProgramTokens prog, Memory mem, int level)
            throws Exception {
        //espero apenas uma linha
        if (prog.getLines().size() != 1) {
            throw new LanguageException("Erro no bloco de declaração de variaveis",
                    " esperava apenas uma declaração");
        }
        Make(prog.getLines().get(0), mem, level);
    }
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
    public static void Make(LineTokens line, Memory mem, int level)
            throws Exception {
        IteratorElemTokens it = new IteratorElemTokens(line);
        //token temp
        Token tmp = null;
        //tipo de dados
        Token dataType;
        Token dataAlter = it.next().getElement(0);
        // tipo de varivel array
        if (dataAlter.kw == Keyword.VARIAVEL || dataAlter.kw == Keyword.CONSTANTE) {
            dataType = it.next().getElement(0);
        } else {
            dataType = dataAlter;
            dataAlter = new Token(Keyword.VARIAVEL);
        }
        //------------ nomes e valores ----------------------
        while (it.hasNext()) {
            //introduzir o tipo e o tipo de dados
            LineTokens def = new LineTokens();
            def.addTokens(dataAlter);
            def.addTokens(dataType);
            //introduzir o nome e os valores de inicializaçao
            while (it.hasNext() && it.peek().getElement(0).ttype != Keyword.COMMA) {
                def.addLine(it.next());
            //eliminar a virgula
            }
            if (it.hasNext() && it.peek().getElement(0).ttype == Keyword.COMMA) {
                it.next();
            //fazer a variavel
            }
            SymbolData var = NewSymbol(def, mem, level);
            //adiciona-la á memoria
            mem.addSymbol(var);
        }


    }
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
    public static SymbolData NewSymbol(LineTokens line, Memory mem, int level)
            throws Exception {
        SymbolData var; //variavel de retorno
        IteratorElemTokens it = new IteratorElemTokens(line);
        //token temp
        Token tmp = null;
        //tipo de dados
        Token dataType;
        Token dataAlter = it.next().getElement(0);
        // tipo de varivel array
        if (dataAlter.kw == Keyword.VARIAVEL || dataAlter.kw == Keyword.CONSTANTE) {
            dataType = it.next().getElement(0);
        } else {
            dataType = dataAlter;
            dataAlter = new Token(Keyword.VARIAVEL);
        }
        //nome da variavel
        Token dataName = it.next().getElement(0);
        //nome da variavel
        int typeVar = DATA;
        SymbolStructure s = mem.getStructure(dataType.sval);
        //Dimensões dos arrays
        LineTokens dimsArray = new LineTokens();
        if (it.hasNext() && it.peek().getElement(0).ttype == Keyword.LBRACKET) {
            typeVar = ARRAY;
            //--------------------outras Dimensões---------------------------------            
            while (it.hasNext() && it.peek().getElement(0).ttype != Keyword.ASSIGN && it.peek().getElement(0).ttype != Keyword.COMMA) {
                dimsArray.addLine(it.next());
            }
        } else if (s != null) {
            typeVar = STRUCT;
        }
        //valor de inicialização
        LineTokens values = new LineTokens();
        if (it.hasNext() && it.peek().getElement(0).ttype == Keyword.ASSIGN) {
            //eliminar o assign
            it.next();
            values.addLine(it.next());
        }

        if (typeVar == ARRAY) {
            LineTokens varArray = new LineTokens();
            varArray.addTokens(dataAlter);      // constante / var
            varArray.addTokens(dataType);       // tipo de dados
            varArray.addTokens(dataName);        // nome            
            varArray.addLine(dimsArray);        // dimensões
            var = new SymbolArray(varArray, mem, level);
            if( !values.getElements().isEmpty())
               var.setValue(values);
        } //se for uma estrutura
        else if (typeVar == STRUCT) {
            var = new SymbolStructure(s, dataName.sval, level);
            if( !values.getElements().isEmpty())
               var.setValue(values);
        } else {
            //introduzir um token vazio
            if( values.getElements().isEmpty())
               values.addTokens(new Token(""));
            var = makeNewDataSymbol(dataAlter, dataType, dataName, values.getElement(0), level);
        }

        return var;
    }
//------------------------------------------------------------------------
    public static SymbolData clone(SymbolData s , String newName) throws LanguageException{
        if( s instanceof SymbolDataSimple)
            return s.getClone(newName);
        return null;
    }
//------------------------------------------------------------------------
    public static Token getValue(Memory mem, IteratorLineTokens it) throws Exception {
        Expression exp = getExpression(mem, it);
        exp =
                replaceVarsToValues(mem, exp);
        return exp.evaluate(mem);
    }

    public static Expression replaceVarsToValues(
            Memory mem, Expression exp) {
        Expression tmp = new Expression();
        for (Token token : exp.getTokens()) {
            if (token.ttype == Keyword.ID) {
                SymbolData var = mem.getSymbol(token.sval);
                if (var != null) {
                    token = new Token(token.ttype, var.getValue());
                }

            }
            tmp.add(token);
        }

        return tmp;
    }
//------------------------------------------------------------------------
    public static Expression getExpression(
            Memory mem, IteratorLineTokens it) throws Exception {
        Expression exp = new Expression();
        Token token;

        int PC = 0, PB = 0;
        while (it.hasNext()) {
            token = it.next();
            // fim de um valor
            if ((PB <= 0 && PC <= 0 && token.ttype == Keyword.COMMA) || token.ttype == Keyword.ENDL) {
                it.goBack();
                return exp;
            }

            if (token.ttype == Keyword.LBRACKET) {
                PB++;
            }

            if (token.ttype == Keyword.RBRACKET) {
                PB--;
                //final da expressão
                if (PB < 0) {
                    it.goBack();
                    return exp;
                }

            }
            if (token.ttype == Keyword.LPARENT) {
                PC++;
            }

            if (token.ttype == Keyword.RPARENT) {
                PC--;
                //final da expressão
                if (PC < 0) {
                    it.goBack();
                    return exp;
                }

            }
//            if (token.ttype == Keyword.ID) {
//                SymbolData var = mem.getSymbol(token.sval);
//                if (var != null) {
//                    token = new Token(token.ttype, var.getValue());
//                }
//
//            }
            exp.add(token);
        }

        return exp;
    }
//------------------------------------------------------------------------
    public static String getComplexValues(
            Memory mem, IteratorLineTokens it) throws Exception {
        StringBuffer txt = new StringBuffer();
        Expression exp = new Expression();

        int PC = 0, PB = 0, PK = 0;
        Token token = it.next();
        if (token.ttype == Keyword.LKEYWAY) {
            PK++;
            txt.append(token.getValue() + " ");
        } else {
            throw new Exception(" falta a chaveta de inicializaçao");
        }

        while (it.hasNext() && PK > 0) {
            token = it.next();
            if (token.ttype == Keyword.LKEYWAY) {
                PK++;
                txt.append(token.getValue() + " ");
                continue;

            }
            //calcular o valor

            if (token.ttype == Keyword.RKEYWAY) {
                PK--;
                if (!exp.isEmpty()) {
                    Token val = exp.evaluate(mem);
                    exp =
                            new Expression();
                    txt.append(val.getValue() + " ");
                }

                txt.append(token.getValue() + " ");
                if (PK > 0 && it.peek().ttype == Keyword.COMMA) {
                    token = it.next(); //consumir a virgula
                    txt.append(token.getValue() + " ");
                }

                continue;
            }

// fim de um valor
            if ((PB == 0 && PC == 0 && token.ttype == Keyword.COMMA) || token.ttype == Keyword.ENDL) {
                Token val = exp.evaluate(mem);
                exp =
                        new Expression();
                txt.append(val.getValue() + " , ");
                continue;

            }


            if (token.ttype == Keyword.LBRACKET) {
                PB++;
            }

            if (token.ttype == Keyword.RBRACKET) {
                PB--;
            }

            if (token.ttype == Keyword.LPARENT) {
                PC++;
            }

            if (token.ttype == Keyword.RPARENT) {
                PC--;
            }

            if (token.ttype == Keyword.ID) {
                SymbolData var = mem.getSymbol(token.sval);
                if (var != null) {
                    token = new Token(token.ttype, var.getValue());
                }

            }
            exp.add(token);
        }

        return txt.toString();
    }
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//retorna uma string com as dimensões
    public static String getDimsArray(
            Memory mem, IteratorLineTokens it) throws Exception {
        StringBuffer txt = new StringBuffer();
        Expression oneDim = new Expression();

        boolean isdone = false;
        Token tok;

        while (!isdone) {
            tok = it.next();
            // fim de uma dimensao
            if (tok.ttype == Keyword.RBRACKET) {
                Token val = oneDim.evaluate(mem);
                txt.append(" [ " + val.getValue() + " ]");
                tok =
                        it.next();
                isdone =
                        true;
            }
//inicio de outra
            if (tok.ttype == Keyword.LBRACKET) {
                oneDim = new Expression();
                isdone =
                        false;
            } else if (tok.ttype == Keyword.ENDL || tok.ttype == Keyword.ENDF) {
                isdone = true;
            } else {
                oneDim.add(tok);
            }

        }
        //tirar o ultimo
        it.goBack();
        return txt.toString();
    }

//NOTA:
// a implementar para suportar indexes com arrays
    public static String getArrayDimension(
            IteratorLineTokens it) {
        StringBuffer txt = new StringBuffer();
        // implementar para [  x[4] + Y [  v[5] ] ]
        return txt.toString();
    }
//------------------------------------------------------------------------
    /**
     * Faz um simbolo com os parametros
     * @param alter - variavel / constante
     * @param type tipos de dados
     * @param var - codigo com a definição da variavel com a possibilidade
     * de ter o valor de inicialização
     * @param level - nivel do simbolo
     * @return - Simbolo 
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public static SymbolData makeNewDataSymbol(
            String alter, String type, String var, String value, int level)
            throws LanguageException {
        return new SymbolDataSimple(alter, type, var, value, level);
    }
    //------------------------------------------------------------------------
    /**
     * Faz um simbolo com os parametros
     * @param alter - variavel / constante
     * @param type tipos de dados
     * @param var - codigo com a definição da variavel com a possibilidade
     * de ter o valor de inicialização
     * @param level - nivel do simbolo
     * @return - Simbolo 
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public static SymbolData makeNewDataSymbol(
            Token alter, Token type, Token var, Token value, int level)
            throws LanguageException {
        String alt = Keyword.getFastKeyword(alter.kw);
        String tp = Keyword.getFastKeyword(type.kw);
        String val;
        //extrair o valor de inicialização
        if (value.nval == 0.0) {
            val = value.sval;
        } else {
            val = value.getValue();
        }
        return new SymbolDataSimple(alt, tp, var.sval, val, level);
    }
}
