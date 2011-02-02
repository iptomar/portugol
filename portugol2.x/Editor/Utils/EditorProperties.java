/*
 * EditorProperties.java
 *
 * Created on 8 de Setembro de 2006, 11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Editor.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author _zeus
 */

public class EditorProperties  {
    Properties props = new Properties();
    /** Creates a new instance of EditorProperties */
    private String fileName = "editor.cfg";
    public EditorProperties() {
        Init();
        Load();
    }
    
    public String GetProperty( String key){
        return props.getProperty(key);
    }
    
    public void SetProperty( String key, String value){
        props.setProperty(key, value);
        Save();
    }
    
    public void SetLoadFileName( String file){
        if( !file.equalsIgnoreCase(props.getProperty("file1")) &&
                !file.equalsIgnoreCase(props.getProperty("file2")) &&
                !file.equalsIgnoreCase(props.getProperty("file3")) ) 
        {
            props.setProperty("file3", props.getProperty("file2"));
            props.setProperty("file2", props.getProperty("file1"));
            props.setProperty("file1",file);
        }
    }
    
    void Init(){
        props.setProperty("file1","sem nome.alg");
        props.setProperty("file2","sem nome.alg");
        props.setProperty("file3","sem nome.alg");
        props.setProperty("sintax","sim");
        props.setProperty("fontName","Monospaced");
        props.setProperty("fontSize","20");
        props.setProperty("fontItalic","nao");
        props.setProperty("fontBold","nao");
        props.setProperty("backColorR","255");
        props.setProperty("backColorG","255");
        props.setProperty("backColorB","255");
        
    }
    
    public void Load(){
        try{
            FileInputStream in = new FileInputStream(fileName);
            props.load(in);
            in.close();
        }catch(Exception e){            
        }
    }
    
    public void Save(){
        try{
            FileOutputStream out = new FileOutputStream(fileName);
            props.store(out,"sem comentarios");
            out.close();
        }catch(Exception e){
            
        }
    }
    
}
