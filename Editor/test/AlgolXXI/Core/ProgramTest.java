/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Algol.Core;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author manso
 */
public class ProgramTest {
  String program = "" +
                "inteiro i \n" +
                "vazio escreveOla() \n" +
                "inicio \n" +
                "escreve \"ola mundo\" \n" +
                "fim\n" +
                "\n" +
                "inicio \n" +
                "escreveOla() \n" +
                "escreveOla() \n" +
                "fim \n"
                
                ;
    public ProgramTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of BuildBlocks method, of class Program.
     */
    @Test
    public void testBuildBlocks() {
        System.out.println("BuildBlocks");

        System.out.println(program);
    }

    /**
     * Test of getNextBlocks method, of class Program.
     */
    @Test
    public void testGetNextBlocks() {
      System.out.println("GetNextBlocks()");
      Program p = new Program(program);
      String prog = p.getNextBlocks(program);
        System.out.println(prog); 
    }

}