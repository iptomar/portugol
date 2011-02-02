/*****************************************************************************/
/****     Copyright (C) 2006                                              ****/
/****     Ant�nio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Polit�cnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*
 * Calculator.java
 *
 * Created on 2 de Maio de 2006, 16:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package AlgolXXI.Core.Evaluate;


import AlgolXXI.Core.Utils.IteratorCodeParams;
import AlgolXXI.Core.Utils.IteratorExpression;
import AlgolXXI.Core.Utils.IteratorString;
import AlgolXXI.Core.Utils.Parentesis;
import java.util.Stack;
import java.util.Vector;

/**
 * Calculador de express�es
 * @author Ant�nio M@nuel Rodrigues M@nso<br>
 * Departamento de Engenharia Inform�tica<br>
 * Escola Superior de Tecnologia de Tomar<br>
 * Intituto Polit�cnico de Tomar<br>
 * Estrada da Serra<br>
 * 2350 - Tomar - Portugal<br>
 * <br>
 * Web site:  http://orion.ipt.pt/~manso/<br>
 * Email:     manso@ipt.pt <br>
 */
/*
 *24/06/2006 - corrigi o bug das virgulas nos parametros das fun��es
 *
 */
public class CalculatorTokens {
    public static String VERSION = "ver:2.0\tdata:22-06-2006 \t(c)Ant�nio Manso";
    /**
     * Creates a new instance of Calculator
     */
    String inFix;
    String posFix;
    private static CalculusElement      calculatorValues = new CalculusElement();
    private static CalculusElementSafe  calculatorSafe = new CalculusElementSafe();
    
    /**
     * constroi um calculador com uma express�o na forma infixa
     * @param exp express�o nao forma infixa
     */
    public CalculatorTokens(String exp) {
        inFix = exp;
        try{
        posFix = CalulatePosFix(inFix,calculatorSafe);
        } catch(Exception e){
            posFix = e.getMessage();
        }
    }
    
    /**
     * verifica se a string � um elemento de c�lculo v�lido
     * @param str elemento
     * @return verdadeiro se for elemento de c�lculo
     */
    public static boolean IsCalculus(String str){  
        //CalculusElement calculator = new CalculusElement();
        //return calculator.IsCalculus(str);
        return calculatorValues.IsCalculus(str);
    }
    /**
     * retorna a express�o infixa normalizada
     * @return express�o infixa
     */
    public String GetInfix(){
        return inFix;
    }
    /**
     * retorna a Express�o posfixa
     * @return express�o posfixa
     */
    public String GetPosfix(){
        return posFix;
    }
    /**
     * faz o calculo da express�o
     * @return valor do calculo
     * @throws java.lang.Exception erro
     */
    public String GetResult() throws Exception {
        return CalulateValue(inFix);
    }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//---------------------------------------------------------------------------
    /**
     * met�do est�tico va converte infixa em posfixa
     * @return express�o posfixa
     * @param infix express�o infixa
     * @throws java.lang.Exception erro
     */
    public static String CalulatePosFix(String infix, CalculusElement calculator) throws Exception{
       if( ! Parentesis.Verify(infix))
            throw new Exception(Parentesis.GetError(infix));
       
        IteratorExpression it = new IteratorExpression(infix);
        Stack s = new Stack();
        String strPosFix ="";
        
        while(it.hasMoreElements() ){
            String elem = it.current();
            it.next();
            
            // parametros das fun��es
            if(elem.equals(",")){
               //retirar todos elementos at� ao parentesis
                while( !s.empty() &&  !((String)s.peek()).equals("(") ){
                    elem = (String) s.pop();
                    strPosFix += elem + " ";
                }      
                continue;
            }
            // introduzir directamente na pilha
            if( elem.equalsIgnoreCase("(") )
                s.push(elem);
            //retirar da pilha todos operadores ate encontrar o (
            else if( elem.equalsIgnoreCase(")") ){
                while( !s.empty() ){
                    elem = (String) s.pop();
                    if(elem.equalsIgnoreCase("(")  )
                        break;
                    strPosFix += elem + " ";
                }               
            }
            // se for um operador
            else if( calculator.IsCalculus( elem ) ){
                int prio = calculator.GetPriority(elem);
                //retirar todos operadores com maior prioridade
                while( !s.empty() && calculator.GetPriority( (String) s.peek() ) >= prio){
                    strPosFix += (String) s.pop() + " ";
                }
                s.push(elem);
            } else
                strPosFix += elem + " ";
        }// fim do iterador
        
        while( ! s.empty()){            
            strPosFix += (String) s.pop() + " ";
        } 

        
        
        
        return strPosFix.trim();        
    }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
   public  static String CalulateExpressionValues(String expr) throws Exception{
      String init ="";
      String fin ="";
      StringBuffer newExpr = new StringBuffer();
      int pi =0;
      int pf = expr.length()-1;
      
       if( pf >0 && expr.charAt(pi)=='{' && expr.charAt(pf)=='}' ) {
          init = expr.substring(pi,pi+1);
          fin = expr.substring(pf,pf+1);
          expr = expr.substring(pi+1, pf-1);
       }
      
      newExpr.append(init + " ");
       IteratorCodeParams it = new IteratorCodeParams(expr);
         while( it.hasMoreElements())    {
            String value = CalulateValue(it.current());
            newExpr.append( value + " ");
            it.next();
            if(it.hasMoreElements())
                newExpr.append( ", ");
         }
          newExpr.append(fin);
         return newExpr.toString().trim();
         
       
   }
         // se a express�o for vazia
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
   public  static String CalulateExpressionSafeValues(String expr) throws Exception{
      String init ="";
      String fin ="";
      StringBuffer newExpr = new StringBuffer();
      int pi =0;
      int pf = expr.length()-1;
      
       if( pf >0 && expr.charAt(pi)=='{' && expr.charAt(pf)=='}' ) {
          init = expr.substring(pi,pi+1);
          fin = expr.substring(pf,pf+1);
          expr = expr.substring(pi+1, pf-1);
       }
      
      newExpr.append(init + " ");
       IteratorCodeParams it = new IteratorCodeParams(expr);
         while( it.hasMoreElements())    {
            String value = CalulateSafeValue(it.current());
         //   System.out.println("VALUE <" + value + ">");
            newExpr.append( value + " ");
         //   System.out.println("VALUE <" + newExpr.toString() + ">");
            
            it.next();
            if(it.hasMoreElements())
                newExpr.append( ", ");
         }
          newExpr.append(fin);
         return newExpr.toString().trim();
         
       
   }
         // se a express�o for vazia   

//------------------------------------------------------------------------------
//---------------------------------------------------------------------------
    /**
     * Executa o c�lculo
     * @return valor da express�o
     * @param expr express�o infixa
     * @throws java.lang.Exception erro
     */
   public  static String CalulateValue(String expr) throws Exception{
       return CalulateValue(expr,calculatorValues);
   }
//------------------------------------------------------------------------------
//---------------------------------------------------------------------------
    /**
     * Executa o c�lculo
     * @return valor da express�o
     * @param expr express�o infixa
     * @throws java.lang.Exception erro
     */
   public  static String CalulateSafeValue(String expr) throws Exception{
       return CalulateValue(expr,calculatorSafe);
   }

//------------------------------------------------------------------------------
//---------------------------------------------------------------------------
    /**
     * Executa o c�lculo
     * @return valor da express�o
     * @param expr express�o infixa
     * @throws java.lang.Exception erro
     */
   public  static String CalulateValue(String expr, CalculusElement calculator) throws Exception{
         String str = CalulatePosFix(expr,calculator);
         // se a express�o for vazia
         // if( str.length() == 0) return expr;
        IteratorString it = new IteratorString(str);
        Stack result = new Stack();
        Vector params = new Vector();
        String elem;
        while( it.hasMoreElements() ){
            elem = it.current();
            it.next();            
            // se for um calculo
            if( calculator.IsCalculus(elem) ){
               // retirar os parametros do calculo
                params.clear();
                for( int index=0; index < calculator.GetNumParameters(elem) ; index++){
                    
                    //verificar se existem parametros
                    if( result.empty())
                         throw new Exception(" ERRO no n�mero de parametros do simbolo :" + elem );
                    //adicionar no inicio
                    params.add(0,result.pop());
                }
                //adicionar o resultado
                result.push( calculator.Calculate( elem , params) );
            } else
             // se for um valor   
                result.push(elem);
            
        }
        
        if( result.size() == 1){
            return (String) result.pop();        
        }   
        else 
            throw new Exception(" A Express�o ["+ expr + "] est�  mal construida");
            
    }
    
}
