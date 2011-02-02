/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Parser;

import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Utils.LanguageException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author 
 * Luis Talento
 * Eduardo Dias
 * Joao Fernandes
 * Daniel Metelo
 * Lindomar Santos
 * Carlos Andrade
 * Osvaldo Spencer
 * Bruno Louro
 * António Manso
 */
public class Parser {

    /**
     * Vector que contém todos os tokens
     */
    public Vector<Token> tokens = new Vector<Token>();
    public Vector functions = new Vector();
    public Memory mem = new Memory();
    /**
     * programa com as linhas divididas em tokens
     */
    private ProgramTokens program = new ProgramTokens();
    /**
     * Linha do programa
     */
    LineTokens line = new LineTokens();
    /**
     * Vector que contém erros lexicos e sintáticos
     */
    Vector<LanguageException> errors;
    //Keyword kw = new Keyword(); //load xml
    int BLOCKNO = 0;

    /**
     * Recebe uma string contendo código, e no caso de encontrar algum erro, adiciona esse erro a um vector de erros
     * @param code Objecto do tipo String que contém o código
     * @param kDic Objecto do tipo KeywordDic que contém o dicionário escolhido
     */
    public Parser(String code) {

        try {
            errors = new Vector<LanguageException>();
            verifyLexical(code);
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException ex) {
        //Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        //errors.add(new AlgolException(0,"", "erro lexico: ", ex.toString(), "Categoria"));
        }
    }

    /**
     * Selector que retorna um vector de erros
     * @return Vector de erros
     */
    public Vector getErrors() {
        return errors;
    }

    /**
     * Método que verifica se uma determinada expressão está correcta
     * @return verdadeiro ou falso
     */
    public boolean isOk() {
        return errors.size() == 0;
    }

    /**
     * Modificador da sintaxe
     * @param st Objecto do tipo StreamTokenizer
     */
    private void SetSyntax(OurStreamTokenizer st) {
        //st.parseNumbers(); //default
        //a-z        
        //st.wordChars(97,122)
        //A-Z
        //st.wordChars(64,90)
        st.wordChars('_', '_');

        // If whitespace is not to be discarded, make this call
        //st.ordinaryChars(0, ' ');

        //operators
        //st.ordinaryChar('+');
        st.ordinaryChar('-');
        ////st.ordinaryChar('*');
        st.ordinaryChar('/'); //-- recognizes the division operator '/'
        //st.wordChars('+','+');
        //st.wordChars('-','-');
        //st.wordChars('*','*');
        //st.wordChars('/', '/');


        //st.ordinaryChars('0','9');//-- let your C or java compiler take care of numerical constants...   
        //st.wordChars('0','9'); //-- let your C or java compiler take care of numerical constants...   

        //st.ordinaryChars(low, hi)
        //st.commentChar()
        //st.ordinaryChar(ch)

        //st.quoteChar('"');   
        st.eolIsSignificant(true);
        //st.whitespaceChars(',', ','); //ignore comma ','


        //comments
        st.slashStarComments(true); //-- recognizes /*   

        st.slashSlashComments(true); //-- recognizes // 
    // st.quoteChar('"');

    }

    //http://www.mcmanis.com/chuck/java/javaworld/index.html
    //http://www.guj.com.br/posts/list/69651.java
    //http://forum.java.sun.com/thread.jspa?threadID=755293
    //http://www.exampledepot.com/egs/java.io/ParseJava.html
    /**
     * Método que verifica o léxico da string
     * @param code Objecto do tipo String que contém o código
     * @param dic Objecto do tipo KeywordDic que contém o dicionário escolhido
     * @throws java.io.IOException
     */
    public void verifyLexical(String code) throws IOException, Exception {
        OurStreamTokenizer st = new OurStreamTokenizer(new StringReader(code));
        //st.resetSyntax();
        SetSyntax(st);
        //TreeMap tokens = new             
        tokens.clear();
        while (st.nextToken() != OurStreamTokenizer.TT_EOF) {
            Token t = new Token();
            switch (st.ttype) {
                case OurStreamTokenizer.TT_EOF:
                    t.ttype = Keyword.ENDF;
                    break;
                case OurStreamTokenizer.TT_EOL:
                    t.ttype = Keyword.ENDL;
                    break;
                //==============================================================
                // Alterado por David Jardim e Edou Suilen, Token Inteiro
                case OurStreamTokenizer.TT_INT:
                    t.ttype = Keyword.INTEIRO;
                    t.nval = st.ival;
//                    System.out.println("INT");
                    break;
                // Alterado por David Jardim e Edou Suilen, Token Double
                case OurStreamTokenizer.TT_DOUBLE:
                    t.ttype = Keyword.REAL;
                    t.nval = st.nval;
//                    System.out.println("DOUBLE");
                    break;
                //==============================================================
                case OurStreamTokenizer.TT_WORD:   //identificador ou keyword                 
                    // Alterado por David Jardim e Edou Suilen

                    if (Keyword.getFastKey(st.sval.toUpperCase()) != Keyword.DESCONHECIDO) {
                        t.ttype = Keyword.KEYWORD;
                        t.sval = st.sval;
                        // Alterado por David Jardim e Edou Suilen
                        // comentado por manso
//                        t.ival = Keyword.getFastKey(st.sval.toUpperCase());
                        //
                        t.kw = Keyword.getFastKey(st.sval.toUpperCase());
                    } else {
                        t.ttype = Keyword.ID;
                        t.sval = st.sval;
                    }
                    break;
                case '"':
                    //string
                    t.ttype = Keyword.TEXTO;
                    t.sval = st.sval;
                    break;
                case '\'':
                    //caracter 'c'
                    t.ttype = Keyword.CARACTER;
                    // Eduardo Dias - alterei esta linha para aceitar o primeiro
                    // caracter que aparece entre pelicas '
                    t.sval = String.valueOf(st.sval.charAt(0));
                    break;
                case '<':
                    st.nextToken();
                    if (st.ttype == '-') {
                        t.ttype = Keyword.ASSIGN;
                        t.sval = "<-";
                    } else if (st.ttype == '=') {
                        t.ttype = Keyword.OP_LE;
                        t.sval = "<=";
                    } else {
                        st.pushBack();
                        t.ttype = Keyword.OP_L;
                        t.sval = "<";
                    }
                    break;
                case '>':
                    st.nextToken();
                    if (st.ttype == '=') {
                        t.ttype = Keyword.OP_GE;
                        t.sval = ">=";
                    } else {
                        st.pushBack();
                        t.ttype = Keyword.OP_G;
                        t.sval = ">";
                    }
                    break;
                case '=':
                    t.ttype = Keyword.OP_E;
                    t.sval = "=";
                    break;
                case '+':
                    t.ttype = Keyword.OP_PLUS;
                    t.sval = "+";
                    break;
                case '-':
                    t.ttype = Keyword.OP_MINUS;
                    t.sval = "-";
                    break;
                case '*':
                    t.ttype = Keyword.OP_MUL;
                    t.sval = "*";
                    break;
                case '/':
                    t.ttype = Keyword.OP_DIV;
                    t.sval = "/";
                    break;
                case '(':
                    t.ttype = Keyword.LPARENT;
                    t.sval = "(";
                    break;
                case ')':
                    t.ttype = Keyword.RPARENT;
                    t.sval = ")";
                    break;
                case ',':
                    t.ttype = Keyword.COMMA;
                    t.sval = ",";
                    break;
                case '[':
                    t.ttype = Keyword.LBRACKET;
                    t.sval = "[";
                    break;
                case ']':
                    t.ttype = Keyword.RBRACKET;
                    t.sval = "]";
                    break;
                case '%':
                    t.ttype = Keyword.OP_MOD;
                    t.sval = "%";
                    break;
                case '{':
                    t.ttype = Keyword.LKEYWAY;
                    t.sval = "{";
                    break;
                case '}':
                    t.ttype = Keyword.RKEYWAY;
                    t.sval = "}";
                    break;
                case '&':
                    t.ttype = Keyword.REFERENCIA;
                    t.sval = "&";
                    break;
                default:
                    errors.add(new LanguageException(0, "", "erro lexico: " + "token nao reconhecido", "Categoria"));
                    t.ttype = Keyword.DESCONHECIDO;
                    t.sval = "wtf?? " + (char) st.ttype;
                    break;
            }
            tokens.add(t);
            line.addTokens(t);
            if (t.ttype == Keyword.ENDF || t.ttype == Keyword.ENDL) {
                //adicionar a linha ao programa
                getProgram().addLine(line);
                line = new LineTokens();
                line.setLineCode(st.getCharNumber());
            }
        }

     //   verifySintaxe();
        getProgram().classifyLines();
    }

    /**
     * faz o debug dos tokens
     */
    void debug_tokens() {
        //debug info
        for (int i = 0; i < tokens.size(); i++) {
            Token j = tokens.get(i);
            switch (j.getTtype()) {
                case Keyword.ASSIGN:
                    System.out.print(" ASSIGN " + j.sval);
                    break;
                case Keyword.LOGICO:
                    System.out.print(" BOOL" + j.sval);
                    break;
                case Keyword.CARACTER:
                    System.out.print(" CHAR " + j.sval);
                    break;
                case Keyword.TEXTO:
                    System.out.print(" STRING " + j.sval);
                    break;
                case Keyword.ENDF:
                    System.out.println(" ENDF ");
                    break;
                case Keyword.ENDL:
                    System.out.println(" ENDL ");
                    break;
                case Keyword.ID:
                    System.out.print(" ID " + j.sval);
                    break;
                case Keyword.INTEIRO:
                    System.out.print(" INT " + ((int) j.nval));
                    break;
                case Keyword.REAL:
                    System.out.print(" REAL " + j.nval);
                    break;
                case Keyword.KEYWORD:
                    System.out.print("KEYWORD " + j.sval);
                    break;
                case Keyword.LPARENT:
                    System.out.print(" LPAREMT " + j.sval);
                    break;
                case Keyword.RPARENT:
                    System.out.print(" RPAREMT " + j.sval);
                    break;
                case Keyword.COMMA:
                    System.out.print(" COMMA " + j.sval);
                    break;
                case Keyword.LBRACKET:
                    System.out.print(" LBRACKET " + j.sval);
                    break;
                case Keyword.RBRACKET:
                    System.out.print(" RBRACKET " + j.sval);
                    break;
                case Keyword.OP_PLUS:
                case Keyword.OP_MINUS:
                case Keyword.OP_MUL:
                case Keyword.OP_DIV:
                case Keyword.OP_L:
                case Keyword.OP_LE:
                case Keyword.OP_G:
                case Keyword.OP_GE:
                case Keyword.OP_E:
                case Keyword.OP_MOD:
                    System.out.print(" OP " + j.sval);
                    break;
                default:
                    System.out.print("NONE " + j.sval);
                    break;
            }
        }
    }
//            texto global <- "teste"
//    
//        vazio escreve(texto str)
//        escrever golbal, " - ", str, "\n"
//        fim escreve
//    
//        inicio programa
//        texto x
//        escreve("ola")
//        escreve("Algol")
//        fim programa    
    int tokenIdx = -1;
    Token tok;

    //Recursive Descent Parser
    /**
     * 
     * @throws Este método verifica a sintaxe do programa de forma recursiva descendente
     * caso haja erro adiciona-o ao vector errors
     */
    public void verifySintaxe() throws Exception {
        //int level = 0;
        tokenIdx = -1;
        Advance();
        while (tokenIdx < tokens.size() - 1) {
            if (tok.ttype == Keyword.ENDL) {
                Advance();
                continue;
            } else if (tok.ttype == Keyword.KEYWORD) {
                switch (tok.kw) {
                    case Keyword.CONSTANTE:
                        ExpectKW(Keyword.CONSTANTE);
                        ConstDeclaration();
                        ExpectToken(Keyword.ENDL); //or end of file

                        break;
                    case Keyword.VARIAVEL:
                        ExpectKW(Keyword.VARIAVEL);
                        VarDeclaration();
                        ExpectToken(Keyword.ENDL); //or end of file

                        break;
                    case Keyword.VAZIO:
                    case Keyword.INTEIRO:
                    case Keyword.REAL:
                    case Keyword.TEXTO:
                    case Keyword.CARACTER:
                    case Keyword.PONTEIRO:
                    case Keyword.LOGICO:
                        Advance();
                        ExpectToken(Keyword.ID);
                        if (tok.ttype == Keyword.LPARENT) {
                            GoBack();
                            GoBack();
                            tok.kw = Keyword.FUNCAODEFINIDA;
                            FuncDeclaration();
                            Statements();
                            tok.kw = Keyword.FIMDEFINIRFUNCAO;
                            ExpectKW(Keyword.FIM);
                            ExpectToken(Keyword.ID);
                            ExpectToken(Keyword.ENDL);//or end of file

                            System.out.println("fim function");
                        } else {
                            GoBack();
                            GoBack();
                            VarDeclaration();
                            ExpectToken(Keyword.ENDL); //or end of file

                        }
                        break;
                    case Keyword.INICIO:
                        ExpectKW(Keyword.INICIO);
                        if (ExpectKW(Keyword.PROGRAMA)) {
                            System.out.println("inicio programa");
                            ExpectToken(Keyword.ENDL);
                            Statements();
                            ExpectKW(Keyword.FIM);
                            ExpectKW(Keyword.PROGRAMA);
                            ExpectToken(Keyword.ENDL); // or end of file

                            System.out.println("fim programa");
                        } else {
                            errors.add(new LanguageException(0, "", "erro syntax: " + "erro esperado \"Programa\"", "Categoria"));
                            System.out.println("erro esperado \"Programa\"");
                            Advance();
                        }
                        break;
                    //MANSO
                    case Keyword.ESTRUTURA:
                        ExpectKW(Keyword.ESTRUTURA);
                        ExpectToken(Keyword.ID);
                        StructDeclaration();
                        ExpectKW(Keyword.FIM);
                        ExpectToken(Keyword.ID);


                        break;
                    default:
                        errors.add(new LanguageException(0, "", "erro syntax: " + "erro esperado inicio de programa ou funçao/decaraçao de variaveis", "Categoria"));
                        System.out.println("erro esperado inicio de programa ou funçao/decaraçao de variaveis" + tok.toString());
                        Advance();
                        break;
                }
            } else {
                errors.add(new LanguageException(0, "", "erro syntax: " + "erro esperado inicio de programa ou funçao/decaraçao de variaveis", "Categoria"));
                System.out.println("erro esperado inicio de programa ou funçao/decaraçao de variaveis" + tok.toString());
                Advance();
            }
        }
    }
    int var_q = Keyword.VARIAVEL;
    int var_s = Keyword.VAZIO;
    String var_name = "";
    Vector expression = new Vector();

    void ConstDeclaration() throws Exception {
        System.out.println("const declaration");

        if (tok.ttype == Keyword.KEYWORD) {
            switch (tok.kw) {
                case Keyword.VAZIO:
                case Keyword.INTEIRO:
                case Keyword.REAL:
                case Keyword.TEXTO:
                case Keyword.CARACTER:
                case Keyword.PONTEIRO:
                case Keyword.LOGICO:
                    var_q = Keyword.CONSTANTE;
                    var_s = tok.kw;
                    ExpectKW(tok.kw);
                    do {
                        var_name = tok.sval;
                        System.out.println("ID " + tok.sval);
                        ExpectToken(Keyword.ID); //erro nome de varialvel esperada

                        ExpectToken(Keyword.ASSIGN);//senao erro, constantes precisam de ser inicializadas

                        expression.clear();
                        Expression();
                        System.out.println("");
                    // NOTA  : Calculator cal = new Calculator(expression, kDic, mem);
                    //  mem.addSymbol(var_name, cal.CalulateValue() ,var_s, var_q);   
                    } while (AcceptToken(Keyword.COMMA));
                    break;
                default:
                    errors.add(new LanguageException(0, "", "erro syntax: " + "error esperado tipo de variavel..", "Categoria"));
                    System.out.println("error esperado tipo de variavel..");
                    Advance();
                    break;
            }
        } else {
            errors.add(new LanguageException(0, "", "erro syntax: " + "error esperado tipo de variavel..", "Categoria"));
            System.out.println("error esperado tipo de variavel..");
            Advance();
        }
    }

    void StructDeclaration() throws Exception {
        System.out.println("Struct declaration");
        do {
            VarDeclaration();
        } while (tok.kw != Keyword.FIM);

    }

    void VarDeclaration() throws Exception {
        System.out.println("var declaration");
        switch (tok.kw) {
            case Keyword.VAZIO:
            case Keyword.INTEIRO:
            case Keyword.REAL:
            case Keyword.TEXTO:
            case Keyword.CARACTER:
            case Keyword.PONTEIRO:
            case Keyword.LOGICO:
                var_q = Keyword.VARIAVEL;
                var_s = tok.kw;
                ExpectKW(tok.kw);
                do {
                    var_name = tok.sval;
                    tok.kw = Keyword.DEFINIR;
                    System.out.println("ID " + tok.sval);
                    ExpectToken(Keyword.ID); //errors.add(new AlgolException(0,"", "erro syntax: ", "erro nome de varialvel esperada..", "Categoria"));                         

                    if (tok.ttype == Keyword.ASSIGN) {
                        ExpectToken(Keyword.ASSIGN);
                        expression.clear();
                        Expression();
                        System.out.println("");
//NOTA                        Calculator cal = new Calculator(expression, kDic, mem);
                    //mem.addSymbol(var_name, cal.CalulateValue() ,var_s, var_q);                        
                    } else {
//NOTA                        mem.addSymbol(var_name, var_q, var_q);
                    }
                } while (AcceptToken(Keyword.COMMA));
                break;
            default:
                errors.add(new LanguageException(0, "", "erro syntax: " + "error esperado tipo de variavel..", "Categoria"));
                System.out.println("error esperado tipo de variavel..");
                Advance();
                break;
        }
    }

    /**
     * Este método adiciona tokens ao vector expression e avança para proximo enquanto ñ encontrar tokens ENDL, COMMA e ENDF
     */
    void Expression() {

        System.out.print("Expression: ");
        while (tok.ttype != Keyword.ENDL &&
                tok.ttype != Keyword.ENDF) {
            expression.add(tok);
            Advance();
        }
        System.out.println("\nEXPRESSIOn" + expression.toString());


//    Advance();
//    switch(tok.ttype){
//        case Token.INT:
//        case Token.REAL:            
//        case Token.LPARENT:
//        case Token.RPARENT:
//        case Token.STRING:
//        case Token.PONTIER:    
//        case Token.OP_DIV:
//        case Token.OP_E:    
//        case Token.OP_G:    
//        case Token.OP_GE:    
//        case Token.OP_L:    
//        case Token.OP_LE:    
//        case Token.OP_MINUS:    
//        case Token.OP_MUL:    
//        case Token.OP_PLUS:
//        case Token.CHAR:
//            expression.add(tok);
//            System.out.print(tok.sval+tok.nval);
//            Expression();
//            break;
//        case Token.KEYWORD:    
//            if(tok.kw==Keyword.TRUE
//                    || tok.kw==Keyword.FALSE) {
//                expression.add(tok);
//                System.out.print(tok.sval+tok.nval);
//                Expression();  
//                break;
//             }      
//        case Token.COMMA:
//        case Token.ENDL:
//        case Token.ENDF:
//            return;
//        default:            
//            System.out.println("\nerro na expresao..");
//            break;
//    }  

    }

    /**
     * Verifica se é um tipo especifico
     * @return devolve true se for verdadeiro
     */
    boolean isTypeSpec() {
        if (tok.ttype == Keyword.KEYWORD) {
            switch (tok.kw) {
                case Keyword.VAZIO:
                case Keyword.INTEIRO:
                case Keyword.REAL:
                case Keyword.TEXTO:
                case Keyword.CARACTER:
//                case Keyword.POINTER:
                case Keyword.LOGICO:
                case Keyword.ESCREVER:
                case Keyword.LER:
                    return true;
            }
        }
        return false;
    }

    boolean isStatement() {
        switch (tok.ttype) {
            case Keyword.ID:
            case Keyword.ENDL:
                return true;
            case Keyword.KEYWORD:
                switch (tok.kw) {
                    case Keyword.CONSTANTE:
                    case Keyword.VARIAVEL:
                    case Keyword.VAZIO:
                    case Keyword.INTEIRO:
                    case Keyword.REAL:
                    case Keyword.TEXTO:
                    case Keyword.CARACTER:
//                case Keyword.POINTER:
                    case Keyword.LOGICO:
                    case Keyword.ESCREVER:
                    case Keyword.LER:
                        return true;
                }
        }
        return false;
    }

    void Statements() throws Exception {
        System.out.println("Statements:");

        while (isStatement()) {

            switch (tok.ttype) {
                case Keyword.ENDL:
                    ExpectToken(Keyword.ENDL);
                    break;
                case Keyword.ID:

                    Advance();
                    if (tok.ttype == Keyword.ASSIGN) {
                        System.out.println("ID ASSIGN");
                        expression.clear();
                        Expression();
                        //Calculator cal = new Calculator(expression, kDic, mem);
                        ExpectToken(Keyword.ENDL);
                    } else if (tok.ttype == Keyword.LPARENT) {
                        GoBack();
                        FuncCall();
                        ExpectToken(Keyword.ENDL);
                    } else {
                        errors.add(new LanguageException(0, "", "erro syntax: " + "erro nao é um id assiment ou func call..", "Categoria"));
                        Advance();
                    }
                    break;
                case Keyword.KEYWORD:
                    switch (tok.kw) {
                        case Keyword.CONSTANTE:
                            ExpectKW(Keyword.CONSTANTE);
                            ConstDeclaration();
                            ExpectToken(Keyword.ENDL);
                            break;
                        case Keyword.VARIAVEL:
                            ExpectKW(Keyword.VARIAVEL);
                        case Keyword.VAZIO:
                        case Keyword.INTEIRO:
                        case Keyword.REAL:
                        case Keyword.TEXTO:
                        case Keyword.CARACTER:
                        case Keyword.PONTEIRO:
                        case Keyword.LOGICO:
                            VarDeclaration();
                            ExpectToken(Keyword.ENDL);
                            break;
                        case Keyword.ESCREVER:
                            Write();
                            ExpectToken(Keyword.ENDL);
                            break;
                        case Keyword.LER:
                            Read();
                            ExpectToken(Keyword.ENDL);
                            break;
                        default:
                            errors.add(new LanguageException(0, "", "erro syntax: " + "erro esperado statement", "Categoria"));
                            Advance();
                    }
                    break;
                default:
                    errors.add(new LanguageException(0, "", "erro syntax: " + "erro esperado statement", "Categoria"));
                    Advance();

            }
        }
    }

    void FuncDeclaration() {
        Advance();//funct return type
        tok.kw = Keyword.DEFINIRFUNCAO;
        System.out.println("function declarator " + tok.sval);
        ExpectToken(Keyword.ID);
        ExpectToken(Keyword.LPARENT);
        if (tok.ttype != Keyword.RPARENT) {
            //list of parameters..
            do {
                if (isTypeSpec()) {
                    Advance();//type of parameter

                    System.out.println("function parameter " + tok.sval);
                    ExpectToken(Keyword.ID);
                } else {
                    System.out.println("erro tipo de parametero experado");
                    errors.add(new LanguageException(0, "", "erro" + "erro tipo de parametero experado", "Categoria"));
                }
            } while (AcceptToken(Keyword.COMMA));
        }
        ExpectToken(Keyword.RPARENT);
        ExpectToken(Keyword.ENDL);

    }

    void FuncCall() {
        System.out.println("function call " + tok.sval);
        //procedimento
        tok.kw = Keyword.FUNCAODEFINIDA;
        ExpectToken(Keyword.ID);
        ExpectToken(Keyword.LPARENT);
        if (tok.ttype != Keyword.RPARENT) {
            do {
                expression.clear();
                Expression();
                System.out.println("");
            } while (AcceptToken(Keyword.COMMA));
        }
        ExpectToken(Keyword.RPARENT);

    }

    /**
     * Método que escreve tokens, limpa o vector expression 
     */
    void Write() {
        System.out.println("write ");
        ExpectKW(Keyword.ESCREVER);
        do {
            expression.clear();
            Expression();
            System.out.println("");
        } while (AcceptToken(Keyword.COMMA));
    }

    /**
     * Método que lê tokens enquanto forem válidos 
     */
    void Read() {
        System.out.println("read ");

        ExpectKW(Keyword.LER);
        do {
            System.out.println("ID " + tok.sval);
            ExpectToken(Keyword.ID);
        } while (AcceptToken(Keyword.COMMA));
    }

    /**
     * 
     * @param t - parâmetro deste método que é passado para verificação, caso t for igual tipo token avança caso contrário devolve falso
     * @return true caso o t for um tipo token
     */
    boolean AcceptToken(int t) {
        if (tok.ttype == t) {
            Advance();
        } else {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param t - parâmetro que é passado para verificação, caso seja o token esperado avança, caso contrário adiciona-o ao vector de erros e devolve falso
     * @return true caso t seja o token esperado e falso caso contrário
     */
    boolean ExpectToken(int t) {
        if (tok.ttype == t) {
            Advance();
        } else {
            errors.add(new LanguageException(0, "", "erro" + "esperado token: " + t, "Categoria"));
            return false;
        }
        return true;
    }

    /**
     * 
     * @param kw parâmetro a ser verificado se é igual ao token keyword, caso seja avança se ñ for adiciona-o ao vector de erros
     * @return true caso seja verdadeiro
     */
    boolean ExpectKW(int kw) {

        if (tok.ttype == Keyword.KEYWORD && kw == tok.kw) {
            Advance();
        } else {
            errors.add(new LanguageException(0, "", "erro" + "esperado: " + Keyword.GetTextKey(kw), "Categoria"));
            return false;
        }
        return true;
    }

    /**
     * Este método passa para o token seguinte
     */
    void Advance() {
        tok = GetToken();
    }

    /**
     * Método que devolve um token
     * @return
     */
    Token GetToken() {
        if (tokenIdx < tokens.size() - 1) {
            return tokens.get(++tokenIdx);
        }
//        throw new Exception( "");
        Token ret = new Token();
        ret.ttype = Keyword.DESCONHECIDO;
        return ret;
    }

    //bool LookHead(){    }
    void GoBack() {
        if (tokenIdx > 0) {
            tok = tokens.get(--tokenIdx);
        }
    }

    public ProgramTokens getProgram() {
        return program;
    }
}
