/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgolXXI.Core.Parser;

/**
 *
 * @author Saso
 */
public class AlgolXXIKeyword {
    
    private int id;
    private String word;
    private String category;

    public AlgolXXIKeyword(int id, String word, String category) {
        this.id = id;
        this.word = word;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }
}
