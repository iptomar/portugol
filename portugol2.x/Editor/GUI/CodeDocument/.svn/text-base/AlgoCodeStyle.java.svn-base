/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * AlgoCodeStyle.java
 *
 * Created on 10 de Setembro de 2006, 20:27
 */

package Editor.GUI.CodeDocument;

import Editor.GUI.Dialogs.Message;
import Portugol.Language.Evaluate.CalculusElement;
import Portugol.Language.Parse.Keyword;
import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;



/**
 *
 * @author _zeus
 */



public class AlgoCodeStyle extends AlgoSyntaxHighlight {
    public static String VERSION = "ver:1.5\tdata:09-09-2006 \t(c)António Manso";
    
    public final static int STRING_MODE = 10;
    public final static int TEXT_MODE = 11;
    public final static int NUMBER_MODE = 12;
    public final static int BLOCK_COMMENT_MODE = 13;
    public final static int SINGLE_COMMENT_MODE = 14;
    
    public final static int FUNCTION_MODE = 15;
    public final static int KEYWORD_MODE = 16;
    public final static int STATEMENT_MODE = 17;
    public final static int OPERATOR_MODE = 18;
    public final static int ATTRIB_MODE = 19;
    
    private int mode = TEXT_MODE;
    private int currentPos = 0;
    
    private Color backGround = defaultBackGround;
    
    private SimpleAttributeSet comments; //comentarios
    private SimpleAttributeSet normal;   // normal
    private SimpleAttributeSet string;   // strings
    private SimpleAttributeSet number;   // numeros
    private SimpleAttributeSet function; // funcoes
    
    private SimpleAttributeSet keyword;  // palavras reservadas
    private SimpleAttributeSet operator; // operadores
    private SimpleAttributeSet attrib;   // sinal de atribuição
    
    
    public AlgoCodeStyle(){
        //set the attributes for string
        string = new SimpleAttributeSet();
        StyleConstants.setForeground( string, new Color(210,14,162));
        StyleConstants.setFontFamily( string, "monospaced" );
        
        //set the attributes for number
        number = new SimpleAttributeSet();
        StyleConstants.setForeground( number, Color.red );
        StyleConstants.setFontFamily( number, "monospaced" );
        
        //set the attributes for comments
        comments = new SimpleAttributeSet();
        StyleConstants.setForeground( comments, new Color( 150, 150, 150 ) );
        StyleConstants.setItalic( comments, true );
        StyleConstants.setFontFamily( comments, "monospaced" );
        
        //set the attributes for normal
        normal = new SimpleAttributeSet();
        StyleConstants.setBold( normal,false );
        StyleConstants.setForeground( normal, Color.black );
        StyleConstants.setFontFamily( normal, "monospaced" );
        
        
        
        //set the attributes for Functions
        function = new SimpleAttributeSet();
        StyleConstants.setBold(function, true );
        StyleConstants.setForeground(function, Color.RED);
        StyleConstants.setFontFamily( function, "monospaced" );
        
        //set the attributes for KeyWords
        keyword = new SimpleAttributeSet();
        StyleConstants.setBold(keyword, true );
        StyleConstants.setForeground(keyword, Color.BLACK);
        StyleConstants.setFontFamily( keyword, "monospaced" );
        
        //set the attributes for operators
        operator = new SimpleAttributeSet();
        StyleConstants.setBold(operator, true );
        StyleConstants.setForeground( operator, new Color(30,180,30)); //green
        StyleConstants.setFontFamily( operator, "monospaced" );
        
        //set the attributes for operators
        attrib = new SimpleAttributeSet();
        StyleConstants.setBold(attrib, true );
        StyleConstants.setForeground( attrib, Color.BLUE); //green
        StyleConstants.setFontFamily( attrib, "monospaced" );
    }
    
    
    
    
//-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // verifica se é atribuição
    //-------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    private boolean checkForAttrib(int offs , String elementText) {
        if ( ( mode == this.BLOCK_COMMENT_MODE ) || ( mode == this.SINGLE_COMMENT_MODE ) ) {
            return false;
        }
        int strLen = elementText.length();
        int i = 0;
        if ( ( offs >= 1 ) && ( offs <= strLen - 1 ) ) {
            i = offs;
            char commentStartChar1 = elementText.charAt( i - 1 );
            char commentStartChar2 = elementText.charAt( i );
            if ( commentStartChar1 == '<' && commentStartChar2 == '-' ) {
                mode = this.ATTRIB_MODE;
                insertHighlight( "<-", currentPos - 1 );
                return true;
            }
        }
        return false;
        
    }
//-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // verifica se é um comentário
    //-------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    private boolean checkForComment(int offs , String elementText) {
        int strLen = elementText.length();
        int i = 0;
        if ( ( offs >= 1 ) && ( offs <= strLen - 1 ) ) {
            i = offs;
            char commentStartChar1 = elementText.charAt( i - 1 );
            char commentStartChar2 = elementText.charAt( i );
            if ( commentStartChar1 == '/' && commentStartChar2 == '*' ) {
                mode = this.BLOCK_COMMENT_MODE;
                insertHighlight( "/*", currentPos - 1 );
                return true;
            } else if ( commentStartChar1 == '*' && commentStartChar2 == '/' ) {
                insertHighlight( "*/", currentPos - 1 );
                mode = this.TEXT_MODE;
                return true;
            } else if ( commentStartChar1 == '/' && commentStartChar2 == '/' ) {
                this.mode = this.SINGLE_COMMENT_MODE;
                insertHighlight( "//", currentPos - 1 );
                return true;
            }
        }
        return false;
    }
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // verifica se é uma string
    //-------------------------------------------------------------------------
    private boolean checkForString(int offs , String elementText) {
        if ( ( mode == this.BLOCK_COMMENT_MODE ) || ( mode == this.SINGLE_COMMENT_MODE ) ) {
            return false;
        }
        
        int i = 0;
        int quoteCount = 0;
        int strLen = elementText.length();
        if ( ( offs >= 0 ) && ( offs <= strLen - 1 ) ) {
            i = offs;
            while ( i > 0 ) {
                //contar os ""
                if (elementText.charAt( i ) == '"' ) {
                    // se estiver precedido de \ não conta
                    if( i==0 || ( i>0  && elementText.charAt( i-1) !='\\') )
                        quoteCount++;
                }
                i--;
            }
            // insere O " em STRING_MODE
            mode = this.STRING_MODE;
            insertHighlight( "\"", currentPos);
            
            // se o numero for impar é string
            int rem = quoteCount % 2;
            mode = ( rem == 0 ) ? this.TEXT_MODE : this.STRING_MODE;
            return true;
        }
        
        return false;
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //verifica se é numero
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    private void checkForNumber(int offs , String elementText) {
        //se for comentario ou
        if ( ( mode == this.BLOCK_COMMENT_MODE ) || ( mode == this.SINGLE_COMMENT_MODE )|| ( mode == this.STRING_MODE ) ) {
            return;
        }
        int i = 0;
        mode = this.TEXT_MODE;
        int strLen = elementText.length();
        if ( ( offs >= 0 ) && ( offs <= strLen - 1 ) ) {
            i = offs;
            while ( i > 0 ) {
                //the while loop walks back until we hit a delimiter
                char charAt = elementText.charAt( i );
                if ( ( charAt == ' ' ) | ( i == 0 ) | ( charAt == '(' ) | ( charAt == ')' ) |
                        ( charAt == '{' ) | ( charAt == '}' ) /*|*/ ) { //if i == 0 then we're at the begininng
                    if ( i != 0 ) {
                        i++;
                    }
                    mode = this.NUMBER_MODE;
                    break;
                } else if ( ! ( charAt >= '0' & charAt <= '9' | charAt == '.'
                        | charAt == '+' | charAt == '-'
                        | charAt == '/' | charAt == '*' | charAt == '%' | charAt == '=' ) ) {
                    mode = this.TEXT_MODE;
                    break;
                }
                i--;
            }
        }
    }
    
    private boolean checkForKeyword(int offs , String elementText) {
        if ( ( mode == this.BLOCK_COMMENT_MODE ) || ( mode == this.SINGLE_COMMENT_MODE )|| ( mode == this.STRING_MODE ) ) {
            return false;
        }
        int i;
        int strLen = elementText.length();
        if ( ( offs >= 0 ) && ( offs <= strLen - 1 ) ) {
            i = offs;
            while ( i > 0 ) {
                //the while loop walks back until we hit a delimiter
                i--;
                char charAt = elementText.charAt( i );
                if ( ( charAt == ' ' ) | ( i == 0 ) | ( charAt == '(' ) | ( charAt == ')' ) |
                        ( charAt == '{' ) | ( charAt == '}' ) ) { //if i == 0 then we're at the begininng
                    if ( i != 0 ) {
                        i++;
                    }
                    return ClassifyWord(elementText.substring( i, offs ) ); //skip the period
                }
            }
        }
        return false;
    }
    
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    private boolean ClassifyWord( String word){
        if ( ( mode == this.BLOCK_COMMENT_MODE ) || ( mode == this.SINGLE_COMMENT_MODE )|| ( mode == this.STRING_MODE ) ) {
            return false;
        }
        
        if( CalculusElement.IsFunction(word )){
            mode = this.FUNCTION_MODE;
            insertHighlight( word, currentPos - word.length());
            return true;
        }
        if( Keyword.IsKeyword( word )){
            mode = this.KEYWORD_MODE;
            insertHighlight( word, currentPos - word.length());
            return true;
            //operadores aritmeticos relacionais e logicos
        } else if( CalculusElement.IsElemCalculus( word )){
            mode = this.OPERATOR_MODE;
            insertHighlight( word, currentPos - word.length());
            return true;
        }
        return false;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    public void insertString( int offs, String str, AttributeSet a ) throws BadLocationException {
        super.insertString( offs, str, normal );
        int strLen = str.length();
        int endpos = offs + strLen;
        int strpos;
        for ( int i = offs; i < endpos; i++ ) {
            currentPos = i;
            strpos = i - offs;
            processChar( str.charAt( strpos ) );
        }
        currentPos = offs;
    }
    //-------------------------------------------------------------------------
    private void processChar( char strChar) {
        //-------------------------------  extrair o elemento ---------------
        int offs = this.currentPos;
        Element element = this.getParagraphElement( offs );
        String elementText = "";
        try {
            //this gets our chuck of current text for the element we're on
            elementText = this.getText( element.getStartOffset(),
                    element.getEndOffset() - element.getStartOffset() );
        } catch ( Exception ex ) {
            //whoops!
            Message.Error("PORTUGOLCODESTYLE.checkForString\n" + ex.getMessage());
        }
        if ( elementText.length() == 0 ) {
            return ;
        }
        if ( element.getStartOffset() > 0 ) {
            //translates backward if neccessary
            offs = offs - element.getStartOffset();
        }
        //-------------------------------  processar o caracter ---------------
        //por defeito é modo normal, excepto se . . .
        if ( this.mode != this.BLOCK_COMMENT_MODE
                && this.mode != this.SINGLE_COMMENT_MODE
                && this.mode != this.STRING_MODE)
            this.mode = TEXT_MODE;
        
        switch ( strChar ) {
            case ( '\n' ):
               if ( checkForKeyword(offs, elementText) )                    
                    insertHighlight( strChar +"", this.currentPos );                               
                
                if(this.mode != this.BLOCK_COMMENT_MODE )
                    this.mode = this.TEXT_MODE;
                return;
                
                
                
            case ( ' ' ):
            case ( '(' ):
            case ( ')' ):
                if ( checkForKeyword(offs, elementText) ){
                    //imprimir este caracter
                    insertHighlight( strChar +"", this.currentPos );
                    return;
                }
                break;
                
            case ( '*' ):
            case ( '/' ):
                if( checkForComment(offs, elementText) ) return;
                break;
                
            case ( '"' ):
                if (checkForString(offs, elementText) ) return;
                break;
                
            case ( '-' ):
                if (checkForAttrib(offs, elementText) ) return;
                break;
                
            case ( '0' ):
            case ( '1' ):
            case ( '2' ):
            case ( '3' ):
            case ( '4' ):
            case ( '5' ):
            case ( '6' ):
            case ( '7' ):
            case ( '8' ):
            case ( '9' ):
            case ( '.' ):
                checkForNumber(offs, elementText);
                break;
                
        }
        insertHighlight( strChar +"", this.currentPos );
    }
    
    //----------------------------------------------------------------------------
    private void insertHighlight( String str, int pos ) {
        try {
            SimpleAttributeSet attr;
            //remove the old word and formatting
            this.remove( pos, str.length() );
            switch( this.mode){
                case STRING_MODE:
                    attr = string;
                    break;
                    
                case NUMBER_MODE:
                    attr = number;
                    break;
                    
                case FUNCTION_MODE:
                    attr = function;
                    break;
                    
                    
                    
                case KEYWORD_MODE:
                    attr = keyword;
                    break;
                    
                case OPERATOR_MODE:
                    attr = operator;
                    break;
                    
                case ATTRIB_MODE:
                    attr = attrib;
                    break;
                    
                case BLOCK_COMMENT_MODE:
                case SINGLE_COMMENT_MODE:
                    attr = comments;
                    break;
                default:
                    attr = normal;
            }
            
            StyleConstants.setBackground( attr, backGround );
            
            super.insertString( pos, str, attr );
        } catch ( Exception ex ) {
            Message.Error("PORTUGOLCODESTYLE.insertCommentString\n" + ex.getMessage());
        }
    }
    
 ////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
    
    private void printSelectLine(int numChar){
        if (numChar < 0 ) return;
        try {
            Element element = this.getParagraphElement( numChar );
            int start = element.getStartOffset();
            int end = element.getEndOffset();
            String old = this.getText(start,end - start  );
            currentPos = numChar;
            for(int i = start ; i< end ; i++){
                currentPos = i;
                this.processChar( old.charAt(i-start));
            }
            
        } catch ( Exception ex ) {
            Message.Error("PRINT SELECT LINE \n" + ex.getMessage());
        }
    }
    
    
    
    public void selectErrorLine(int numChar){
        this.backGround = new Color(255,230,230);
        printSelectLine(numChar);
        this.backGround = defaultBackGround;
    }
    
    public void selectCodeLine(int numChar){
        this.backGround = new Color(255,255,120);
        printSelectLine(numChar);
        this.backGround = defaultBackGround;
        
    }
    public void deSelectCodeLine(int numChar) {
        this.backGround = defaultBackGround;
        printSelectLine(numChar);
    }
    
    
    public void clearTextBackground(){
        this.mode = this.TEXT_MODE;
        try {
            int index =0 ;
            while(index < this.getLength()) {
                
                Element element = this.getParagraphElement(index);
                String old = this.getText( element.getStartOffset(),
                        element.getEndOffset() - element.getStartOffset() );
                
                printSelectLine(index);
                //fazer o skip dos \n
                if(old.length() < 2)
                    index+=2;
                else
                    index+=old.length();
            }
        } catch ( Exception ex ) {
            Message.Error("CLEAR TEXT BACKGROUND \n" + ex.getMessage());
        }
    }
}