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
public class IteratorLineTest {

    public IteratorLineTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testHasNext() {
        String test[] = {
            "       [123]        [456]",
            "[123]    [456]",
            "[123]    [45 + v[345] + 6]",
            "\"era uma vez\" \"era outra vez\"",
            "  \"era uma vez\"     \"era outra vez\" ", //dá problemas
            " (1 ) ( 55) ( seno(3(4)4) )",
            "escrever 123 , 456 , seno(123) , \"er UMA vez\" ",
            " x <- seno ( 1,2,3,4 + x [123][456]) * 4",
            " ler a,b, c , d"
                    
                    
        };
        
        for(String s:test){
            System.out.println("\n-----------");
            System.out.println(s);
            IteratorLine it = new IteratorLine(s);
            while( it.hasNext()){
                System.out.print("«" + it.next() + "»\n");
            }
        }
    }

    @Test
    public void testNext() {
    }

    @Test
    public void testRemove() {
    }

}