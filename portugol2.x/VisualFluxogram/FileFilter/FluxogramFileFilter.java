/*
 * FluxogramFileFilter.java
 *
 * Created on 7 de Julho de 2006, 18:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.FileFilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Eduardo
 */
public class FluxogramFileFilter extends FileFilter{
    
    /** Creates a new instance of FluxogramFileFilter */
    
    public final static String EXTENSION = "flux";
    
    public FluxogramFileFilter() {
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
            if (extension.equals("flux"))  
                type = "flux";            
        }
        return type;
    }

    //The description of this filter
    public String getDescription() {
        return "Projecto em Fluxogramas (*.flux)";
    }
    
}
