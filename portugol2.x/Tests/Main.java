package Tests;
import Editor.GUI.EditorPTG;
import Portugol.Language.Evaluate.Aritmetics;
import Portugol.Language.Evaluate.Calculator;
import Portugol.Language.Evaluate.CalculusElement;
import Portugol.Language.Evaluate.Functions;
import Portugol.Language.Evaluate.Relationals;
import Portugol.Language.Make.Fluxogram;
import Portugol.Language.Utils.Parentesis;
import Portugol.Language.Utils.IteratorString;
import Portugol.Language.Utils.IteratorExpression;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import java.util.Vector;

/*
 * Main.java
 *
 * Created on 2 de Maio de 2006, 9:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Zeus_God
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(args.length);
        for(int i=0; i< args.length ; i++)
            System.out.println(args[i]);
        
        EditorPTG frmain;
        if( args.length > 0 )
            frmain = new EditorPTG(args[0]);
        else
            frmain = new EditorPTG();
        frmain.setVisible(true); 
        
       
        
    }
// --------------------------------------------
    public static void TestParentesis()throws Exception{
        String str= " (2 + (3) *)) 5";
        System.out.println("STR: " + str);
        if( Parentesis.Verify(str))
            System.out.println("OK");
        else
            System.out.println(Parentesis.GetError(str));
    }
//----------------------------------------------------
    public static void TestStrings()throws Exception{
        String msg = "OLA MUNDO";
        String txt = Values.StringToText(msg);
        String str = Values.TextToString(txt);
        System.out.println("MSG\t:"  + msg);
        System.out.println("TXT\t:"  + txt);
        System.out.println("STR\t:"  + str);
        Functions fstr = new Functions();
        Vector v = new Vector();
        v.clear() ; v.add(txt);
        String comp= fstr.Calculate("comprimento",v );
        int max= Values.StringToInteger(comp);
        
        for(int i=0 ; i< max ; i++){
            v.clear() ; v.add(txt) ; v.add(Values.IntegerToString(i));
            System.out.println(fstr.Calculate("caracter" ,v) );
        }
        
        
    }
    //----------------------------------------------------
//----------------------------------------------------
    public static void TestPosFix()throws Exception{
        //String str= " 2+ 3 * 5";
        // String str= " -2+ -3 * -5";
        String str= " -2+ -30.0 / -5 + comprimento(\"Ola mundo novo\")";
        
        //String str= " 2 / 3.0  ";
        
        Calculator exp = new Calculator(str);
        System.out.println("INFIX  :" + exp.GetInfix());
        System.out.println("POSFIX :" + exp.GetPosfix());
        //  System.out.println("RESULT :" + exp.GetResult());
    }
    
    public static void TestStringCalculus()throws Exception{
        Vector v = new Vector();
        v.clear() ; v.add("\"Ola\""); v.add("\"Mundo\"");
        Calculate("+" , v);
        v.clear() ; v.add("\"Ola\"");
        Calculate("comprimento" , v);
    }
//----------------------------------------------------
    public static void TestCalculus()throws Exception{
        Vector v = new Vector();
        v.clear() ; v.add("100"); v.add("2");
        Calculate("+" , v);
        Calculate("-" , v);
        Calculate("^" , v);
        Calculate(">" , v);
        Calculate("=/=" , v);
        Calculate("=" , v);
        v.clear() ; v.add("verdadeiro"); v.add("falso");
        Calculate("=/=" , v);
        Calculate("=" , v);
        Calculate("E" , v);
        Calculate("OU" , v);
        v.clear() ; v.add("verdadeiro"); v.add("verdadeiro");
        Calculate("=/=" , v);
        Calculate("=" , v);
        Calculate("E" , v);
        Calculate("OU" , v);
        v.clear() ; v.add("falso"); v.add("falso");
        Calculate("=/=" , v);
        Calculate("=" , v);
        Calculate("E" , v);
        Calculate("OU" , v);
        
        v.clear() ; v.add("falso");
        Calculate("NAO" , v);
        v.clear() ; v.add("verdadeiro");
        Calculate("NAO" , v);
        
        v.clear() ; v.add("25");
        Calculate("SEN" , v);
        Calculate("RAIZ" , v);
        
        v.clear() ; v.add("5") ; v.add("2");
        Calculate("POTENCIA" , v);
        
        v.clear() ;
        Calculate("Aleatorio" , v);
        
        
    }
    public static void Calculate(String oper, Vector params)throws Exception{
        CalculusElement calc = new CalculusElement();
        System.out.println( oper + " <" + params.toString() + "> =" + calc.Calculate(oper,params));
    }
    
//----------------------------------------------------
    public static void testRelational()throws Exception{
        testAllRelational("1","1");
        testAllRelational("10","1");
        testAllRelational("9.895","200.52");
        testAllRelational("verdadeiro","falso");
        testAllRelational("\"Ola\"","\"Ola\"");
        testAllRelational("\"Ola\"","\"Mundo\"");
        testAllRelational("\"Mundo\"","\"Ola\"");
    }
    public static void testAllRelational(String v1, String v2)throws Exception{
        Relationals relat = new Relationals();
        Vector v = new Vector();
        v.add(v1); v.add(v2);
        System.out.println("---------------------------------------");
        String oper = "=";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + relat.Calculate(oper,v) );
        oper = "=/=";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + relat.Calculate(oper,v) );
        oper = ">";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + relat.Calculate(oper,v) );
        oper = ">=";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + relat.Calculate(oper,v) );
        oper = "<";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + relat.Calculate(oper,v) );
        oper = "<=";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + relat.Calculate(oper,v) );
        
    }
//----------------------------------------------------
    public static void testAritmetic()throws Exception{
        testAllAritmetic("10","5");
        testAllAritmetic("5","10");
        testAllAritmetic("10","3.0");
        testAllAritmetic("\"OLA\"","\"MUNDO\"");
    }
    
    public static void testAllAritmetic(String v1, String v2)throws Exception{
        Aritmetics aritm = new Aritmetics();
        Vector v = new Vector();
        v.add(v1); v.add(v2);
        System.out.println("---------------------------------------");
        String oper = "+";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + aritm.Calculate(oper,v) );
        oper = "-";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + aritm.Calculate(oper,v) );
        oper = "*";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + aritm.Calculate(oper,v) );
        oper = "/";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + aritm.Calculate(oper,v) );
        oper = "%";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + aritm.Calculate(oper,v) );
        oper = "^";
        System.out.println(v1 + " " + oper + " " + v2 + " ->\t " + aritm.Calculate(oper,v) );
    }
    
    //----------------------------------------------------
    public static void testExpressionIterator()throws Exception{
        // String str ="\"começo\" +  \"água \\\"lírimão \" + sen(20) ";
        //String str= " -2+ -3 * -5";
        String str = "potencia(2,10)%23";
        IteratorExpression it = new IteratorExpression(str);
        System.out.println("EXPRESSION\t:" + str);
        System.out.println("NORMALIZED\t:" + it.getExpression());
        System.out.println("ELEMS\n");
        while( it.hasMoreElements()){
            System.out.println("[" + it.current() + "]");
            it.next();
        }
    }
    
    //----------------------------------------------------
    public static void testStringIterator()throws Exception{
        String str =" 1234 + 2344 * sen ( 3 * 10 )";
        IteratorString it = new IteratorString(str);
        while( it.hasMoreElements()){
            System.out.println("[" + it.current() + "]");
            it.next();
        }
    }
    
}
