

package Editor.Utils;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;


public class AlgorithmFileFilter extends FileFilter{
    
    /**
     * Creates a new instance of AlgorithmFileFilter 
     */
    
    public final static String EXTENSION = "alg";
    
    public AlgorithmFileFilter() {
    }
    
    public boolean accept(File f) {
        if(getTypeDescription(f)== EXTENSION || f.isDirectory()==true)
            return true;
        return false;
        
    }
   
    public String getExtension(File f) 
   {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
   public String getTypeDescription(File f) 
    {
        String extension = getExtension(f);
        String type = null;
        if (extension != null) 
        {
            if (extension.equals("alg"))  
                type = "alg";            
        }
        return type;
    }

    //The description of this filter
    public String getDescription() {
        return "Algoritmo em Portugol (*.alg)";
    }
    
    
}
