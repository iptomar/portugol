/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Algol.Core.Utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manso
 */
public class CodeTest {

    public CodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of IsFuncion method, of class Code.
     */
    @Test
    public void testIsFuncion() {
        System.out.println("IsFuncion");
        String line[] = {
            "inteiro soma( inteiro i , inteiro j)",
            "inteiro j",
            "string k <- \"ol\"",
            "vazio escrever()",
            "inteiro i , j <- (4 * 2)",
            "escrever k",
            "inteiro factorial( inteiro k)"
        };
        boolean result[] = { 
            true,
            false,
            false,
            true,
            false,
            false,
            true
        };
        for( int i=0 ; i < line.length ; i++){
            System.out.println(result[i] + " \t " + line[i] );
            if( result[i])
               assertTrue(CodeLine.IsFuncion(line[i]));
            else
                assertFalse(CodeLine.IsFuncion(line[i]));
        }   
        
       
    }

    /**
     * Test of IsStructure method, of class Code.
     */
    @Test
    public void testIsStructure() {
        System.out.println("IsStructure");
        String line = "";
        boolean expResult = false;
        boolean result = CodeLine.IsStructure(line);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsFuncion method, of class Code.
     */
    @Test
    public void testIsFuncion_String() {
        System.out.println("IsFuncion");
        String line = "";
        boolean expResult = false;
        boolean result = CodeLine.IsFuncion(line);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}