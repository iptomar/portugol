/*
 * PngFileFilter.java
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
public class PngFileFilter extends FileFilter{
    
    /**
     * Creates a new instance of PngFileFilter
     */
    
    public PngFileFilter() {
    }
    
    public boolean accept(File f) {
        
        if( AcceptableExtension(f) || f.isDirectory()==true)
            return true;
        return false;        
    }
   
    public String getExtension(File f) {
        
        String ext = null;
        String name = f.getName();
        int offset = name.lastIndexOf('.');

        if (offset > 0 &&  offset < name.length() - 1) {
            ext = name.substring(offset+1).toLowerCase();
        }
        return ext;
    }
    
    public boolean AcceptableExtension(File f) {
       
        String extension = getExtension(f);
        if ( extension != null )
            if ( extension.equals("bmp") || extension.equals("dib") )
                return true;
        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Portable Network Graphics (*.png)";
    }
    
    public String toString(){
        return "png";
    }
    
}
