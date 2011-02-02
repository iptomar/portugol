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
public class IteratorBlockTest {

    public IteratorBlockTest() {
    }
   
    /**
     * Test of hasNext method, of class BlockIterator.
     */
    @Test
    public void testHasNext() {
         String program2 = "" +
                "inteiro i \n" +
                
                "real k \n"+
                
                "vazio escreveOla() \n" +
                "inicio \n" +
                "escreve \"ola mundo\" \n" +
                "texto ole\n" +
                "fim\n" +
                
                
                "inicio \n" +
                "escreveOla() \n" +
                "logico x\n"+
                "escreveOla() \n" +
                "fim ";
        System.out.println("\n-----------PROGRAM 2 ------\n");
        IteratorBlock it = new IteratorBlock(program2);
        int blocks=0;
        while( it.hasNext()){
            System.out.println("--------------");
            System.out.println(it.next());
            blocks++;
        }
        //4 blocos
        assertEquals(blocks, 4);
     }
 
     
     
         
         

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of next method, of class BlockIterator.
     */
    @Test
    public void testNext() {
        String program = "" +
                "inteiro i \n" +
                "vazio escreveOla() \n" +
                "inicio \n" +
                "escreve \"ola mundo\" \n" +
                "fim\n" +
                "inicio \n" +
                "escreveOla() \n" +
                "escreveOla() \n" +
                "fim ";
        System.out.println("\n-----------PROGRAM ------\n");
         IteratorBlock it = new IteratorBlock(program);
        int blocks=0;
        while( it.hasNext()){
            System.out.println("--------------");
            System.out.println(it.next());
            blocks++;
        }
        //3 blocos
        assertEquals(blocks, 3);
    }

    /**
     * Test of remove method, of class BlockIterator.
     */
    @Test
    public void testRemove() {
         String program3 = "" +
                  "inteiro i\n" +
                  "estrutura data\n" +
                  "inicio\n" +
                  "inteiro d\n" +
                  "inteiro m\n" +
                  "fim\n" +
                  "vazio escreveTeste()\n" +
                  "inicio" +
                  "escrever \"isto e um teste\"\n" +
                  "fim\n" +
                  "inicio\n" +
                  "escreveTeste()\n" +
                  "data d\n" +
                  "fim\n" ;
        System.out.println("\n-----------PROGRAM 3 ------\n");
        IteratorBlock it = new IteratorBlock(program3);
        int blocks=0;
        while( it.hasNext()){
            System.out.println("--------------");
            System.out.println(it.next());
            blocks++;
        }
        //3 blocos
        assertEquals(blocks, 4);
      }

    /**
     * Test of GetFunction method, of class BlockIterator.
     */
    @Test
    public void testGetFunction() {
     }

}