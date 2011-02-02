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
public class ValuesTest {

    public ValuesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getDefault method, of class Values.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        String type = "";
        String expResult = "";
        String result = Values.getDefault(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IntegerToString method, of class Values.
     */
    @Test
    public void testIntegerToString() {
        System.out.println("IntegerToString");
        double val = 0.0;
        String expResult = "";
        String result = Values.IntegerToString(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of StringToInteger method, of class Values.
     */
    @Test
    public void testStringToInteger() {
        System.out.println("StringToInteger");
        String val = "";
        int expResult = 0;
        int result = Values.StringToInteger(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of DoubleToString method, of class Values.
     */
    @Test
    public void testDoubleToString() {
        System.out.println("DoubleToString");
        double val = 0.0;
        String expResult = "";
        String result = Values.DoubleToString(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of StringToDouble method, of class Values.
     */
    @Test
    public void testStringToDouble() {
        System.out.println("StringToDouble");
        String val = "";
        double expResult = 0.0;
        double result = Values.StringToDouble(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of BooleanToString method, of class Values.
     */
    @Test
    public void testBooleanToString() {
        System.out.println("BooleanToString");
        boolean val = false;
        String expResult = "";
        String result = Values.BooleanToString(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of StringToBoolean method, of class Values.
     */
    @Test
    public void testStringToBoolean() {
        System.out.println("StringToBoolean");
        String val = "";
        boolean expResult = false;
        boolean result = Values.StringToBoolean(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsNumber method, of class Values.
     */
    @Test
    public void testIsNumber() {
        System.out.println("IsNumber");
        String n = "";
        boolean expResult = false;
        boolean result = Values.IsNumber(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsInteger method, of class Values.
     */
    @Test
    public void testIsInteger() {
        System.out.println("IsInteger");
        String n = "";
        boolean expResult = false;
        boolean result = Values.IsInteger(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsReal method, of class Values.
     */
    @Test
    public void testIsReal() {
        System.out.println("IsReal");
        String n = "";
        boolean expResult = false;
        boolean result = Values.IsReal(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsBoolean method, of class Values.
     */
    @Test
    public void testIsBoolean() {
        System.out.println("IsBoolean");
        String val = "";
        boolean expResult = false;
        boolean result = Values.IsBoolean(val);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsString method, of class Values.
     */
    @Test
    public void testIsString() {
        System.out.println("IsString");
        String expr = "";
        boolean expResult = false;
        boolean result = Values.IsString(expr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsCharacter method, of class Values.
     */
    @Test
    public void testIsCharacter() {
        System.out.println("IsCharacter");
        String expr = "";
        boolean expResult = false;
        boolean result = Values.IsCharacter(expr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of TextToString method, of class Values.
     */
    @Test
    public void testTextToString() {
        System.out.println("TextToString");
        String str = "";
        String expResult = "";
        String result = Values.TextToString(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of StringToText method, of class Values.
     */
    @Test
    public void testStringToText() {
        System.out.println("StringToText");
        String str = "";
        String expResult = "";
        String result = Values.StringToText(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsValue method, of class Values.
     */
    @Test
    public void testIsValue() {
        System.out.println("IsValue");
        String str = "";
        boolean expResult = false;
        boolean result = Values.IsValue(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeStringComas method, of class Values.
     */
    @Test
    public void testRemoveStringComas() {
        System.out.println("removeStringComas");
        String orig = "";
        String expResult = "";
        String result = Values.removeStringComas(orig);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringValue method, of class Values.
     */
    @Test
    public void testGetStringValue() {
        System.out.println("getStringValue");
        String orig = "";
        String expResult = "";
        String result = Values.getStringValue(orig);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTypeOfValue method, of class Values.
     */
    @Test
    public void testGetTypeOfValue() {
        System.out.println("getTypeOfValue");
        String value = "";
        int expResult = 0;
        int result = Values.getTypeOfValue(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}