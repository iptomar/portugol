/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.CodeEditor;

import AlgolXXI.Core.Parser.AlgolXXIKeyword;
import AlgolXXI.Core.Parser.KeywordsDatabase;
import AlgolXXI.Core.Parser.KeywordsReader;
import javax.swing.text.*;
import java.util.*;
import java.util.regex.*;
import org.openide.awt.UndoRedo;
import org.openide.util.Exceptions;

/**
 * Classe que pemite fazer o coloring.
 * @author Saso
 */
public class AlgolXXISyntax extends DefaultStyledDocument {

    private Element rootElement;
    private ArrayList<String> block;
    private ArrayList<String> type;
    private ArrayList<String> lexicon;
    private ArrayList<String> value;
    private ArrayList<String> operator;
    private String[] mathOperators = {"+", "-", "*", "/", "<", ">", "="};
    //private String codeStyleFilePath = "Algol/src/AlgolXXI/Editor/CodeEditor/StyleCOST.xml";
    //private String codeStyleFilePath = "StyleCOST.xml";
  
    private MutableAttributeSet style;
    private Pattern lineComment = Pattern.compile("//");
    private Pattern multiLineCommentStart = Pattern.compile("/\\*");
    private Pattern multiLineCommentEnd = Pattern.compile("\\*/");
    private char quoteDelimiter = '"';
    private char newLineDelimiter = '\n';
    private UndoRedo.Manager manager;
    private PortugolStyle codestyle;

    /**
     * Constructor por parâmetros para a classe AlgolXXISyntax.
     * @param path String, path para o ficheiro XML
     * @param managerf UndoRedo.Manager, para activar ou desactivar o UndoRedo na altura
     * de fazer adicionar as propriedas de cores ao código
     */
    public AlgolXXISyntax(UndoRedo.Manager managerf) {
        manager = managerf;
        rootElement = getDefaultRootElement();
        style = new SimpleAttributeSet();

        addUndoableEditListener(manager);
        addKeywords();

        createStyle();
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        super.insertString(offset, str, attr);
        removeUndoableEditListener(manager);
        processChangedLines();
        addUndoableEditListener(manager);
    }

    @Override
    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
        removeUndoableEditListener(manager);
        processChangedLines();
        addUndoableEditListener(manager);
    }

    /**
     * Modificador que permite seleccionar a língua utilizada na linguagem de programação Algol.
     * @param path
     */
    public void newSettings(PortugolStyle style_) {
        this.codestyle = style_;
        StyleConstants.setFontFamily(style, codestyle.getFontFamily());
        StyleConstants.setFontSize(style, codestyle.getFontSize());
        try {
            processChangedLines();
        } catch (BadLocationException ex) {
            System.out.println("BadLocationException: AlgolXXI.Editor.CodeStyle.AlgolXXISyntax");
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Método que consoante a língua escolhida define as keywords para syntax highlighting.
     */
    private void addKeywords() {

        block = new ArrayList<String>();
        type = new ArrayList<String>();
        lexicon = new ArrayList<String>();
        value = new ArrayList<String>();
        operator = new ArrayList<String>();

        ArrayList<AlgolXXIKeyword> keywords;
        keywords = KeywordsDatabase.getAllKeywords();

        for (int i = 0; i < keywords.size(); i++) {
            String auxCat = keywords.get(i).getCategory();

            if (auxCat.equals("BLOCK")) {
                block.add(keywords.get(i).getWord());
            } else if (auxCat.equals("TYPE")) {
                type.add(keywords.get(i).getWord());
            } else if (auxCat.equals("LEXICON") || auxCat.equals("MATHFUNC") || auxCat.equals("TEXTFUNC")) {
                lexicon.add(keywords.get(i).getWord());
            } else if (auxCat.equals("VALUE")) {
                value.add(keywords.get(i).getWord());
            } else if (auxCat.equals("LOGICFUNC")) {
                operator.add(keywords.get(i).getWord());
            }
        }
    }

    private void createStyle() {

        codestyle = PortugolStyleReader.getStyle();

        StyleConstants.setFontFamily(style, codestyle.getFontFamily());
        StyleConstants.setFontSize(style, codestyle.getFontSize());

    }

    /**
     * Método que processa a modificação das linhas de código implementado na tab de código.
     * @throws javax.swing.text.BadLocationException
     */
    private void processChangedLines() throws BadLocationException {
        String text = getText(0, getLength());
        formatText(0, getLength(), true, codestyle.getNormalStyle());

        findBlockKeywords(text);
        findTypeKeywords(text);
        findLexiconKeywords(text);
        findValueKeywords(text);
        findOperatorKeywords(text);
        findMathOperatorKeywords(text);

        findQuotedText(text);
        findMultiLineComments(text);
        findSingleLineComments(text);
    }

    /**
     * Método que define o formato do texto apresentado na tab código.
     * @param col Color, cor do código
     * @param begin int, ínicio do código
     * @param length int tamanho
     * @param replaceAtrbs boolean
     * @param bold boolean, para negrito do código
     * @param italic boolean, para itálico do código
     */
    private void formatText(int begin, int length, boolean replaceAtrbs, AlgolXXIStyle s) {
        StyleConstants.setForeground(style, s.getColor());
        StyleConstants.setBold(style, s.isBold());
        StyleConstants.setItalic(style, s.isItalic());

        setCharacterAttributes(begin, length, style, replaceAtrbs);

    }

    /**
     * Método de pesquisa para blocos de código implementado. Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findBlockKeywords(String text) {
        for (int i = 0; i < block.size(); i++) {

            String keyword = block.get(i);
            Pattern p = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);

            while (m.find()) {
                formatText(m.start(), keyword.length(), true, codestyle.getBlockStyle());
            }
        }
    }

    /**
     * Método de pesquisa para keywords de léxico no código implementado.
     * Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findLexiconKeywords(String text) {
        for (int i = 0; i < lexicon.size(); i++) {

            String keyword = lexicon.get(i);
            Pattern p = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);

            while (m.find()) {
                formatText(m.start(), keyword.length(), true, codestyle.getLexiconStyle());
            }
        }
    }

    /**
     * Método de pesquisa para operadores no código implementado. 
     * Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findOperatorKeywords(String text) {
        for (int i = 0; i < operator.size(); i++) {

            String keyword = operator.get(i);
            Pattern p = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);

            while (m.find()) {
                formatText(m.start(), keyword.length(), true, codestyle.getOperatorStyle());
            }
        }
    }

    /**
     * Método de pesquisa para operadores matemáticos no código implementado. 
     * Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findMathOperatorKeywords(String text) {
        for (int i = 0; i < mathOperators.length; i++) {
            int index = text.indexOf(mathOperators[i]);
            while (index != -1) {
                formatText(index, mathOperators[i].length(), true, codestyle.getOperatorStyle());
                index = text.indexOf(mathOperators[i], index + mathOperators[i].length());
            }
        }
    }

    /**
     * Método de pesquisa para keywords de definição de variáveis no código implementado. Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findTypeKeywords(String text) {
        for (int i = 0; i < type.size(); i++) {

            String keyword = type.get(i);
            Pattern p = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);

            while (m.find()) {
                formatText(m.start(), keyword.length(), true, codestyle.getTypeStyle());
            }
        }
    }

    /**
     * Método de pesquisa para keywords de valor no código implementado. Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findValueKeywords(String text) {
        for (int i = 0; i < value.size(); i++) {

            String keyword = value.get(i);
            Pattern p = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);

            while (m.find()) {
                formatText(m.start(), keyword.length(), true, codestyle.getValueStyle());
            }
        }
    }

    /**
     * Método de pesquisa para Quote no código implementado. Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findQuotedText(String text) {
        int quote1 = text.indexOf(String.valueOf(quoteDelimiter));
        while (quote1 != -1) {
            if (quote1 == text.length()) {
                formatText(quote1, 1, true, codestyle.getStringStyle());
                break;
            } else {
                int quote2 = text.indexOf(String.valueOf(quoteDelimiter), quote1 + 1);
                int newLine = text.indexOf(String.valueOf(newLineDelimiter), quote1 + 1);
                if (quote2 == -1 || (quote2 > newLine && newLine != -1)) {
                    quote2 = newLine;
                }
                if (quote2 != -1) {
                    formatText(quote1, quote2 - quote1 + 1, true, codestyle.getStringStyle());
                    quote1 = text.indexOf(String.valueOf(quoteDelimiter), quote2 + 1);
                } else {
                    formatText(quote1, text.length() - quote1, true, codestyle.getStringStyle());
                    break;
                }
            }
        }
    }

    /**
     * Método de pesquisa para um bloco de comentários no código implementado. Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findMultiLineComments(String text) {
        Matcher commentStart = multiLineCommentStart.matcher(text);
        Matcher commentEnd = multiLineCommentEnd.matcher(text);

        while (commentStart.find()) {
            if (commentEnd.find(commentStart.end())) {
                formatText(commentStart.start(), (commentEnd.end() - commentStart.start()), true, codestyle.getCommentStyle());
            } else {
                formatText(commentStart.start(), text.length() - commentStart.start(), true, codestyle.getCommentStyle());
            }
        }
    }

    /**
     * Método de pesquisa para linha de comentários no código implementado. Define o formato do código pesquisado.
     * @param text String, string com o código implementado
     */
    private void findSingleLineComments(String text) {
        Matcher comment = lineComment.matcher(text);

        while (comment.find()) {
            int line = rootElement.getElementIndex(comment.start());
            int endOffset = rootElement.getElement(line).getEndOffset() - 1;

            formatText(comment.start(), (endOffset - comment.start()), true, codestyle.getCommentStyle());
        }
    }
}
