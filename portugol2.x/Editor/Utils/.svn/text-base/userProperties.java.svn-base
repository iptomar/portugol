/*
 * userProperties.java
 *
 * Created on 26 de Março de 2005, 12:46
 */

package Editor.Utils;

import java.util.*;
import java.io.*;

/**
 *
 * @author  lmmendes@gmail.com
 *revised : António Manso 06/2006
 */
public class userProperties {
    
    private String propertiesFileName;
    //private FileInputStream fis; // não existe necessidade de estar aqui
    private Properties uProperties;
    private boolean loaded;
    
    /** Creates a new instance of userProperties */
    public userProperties() {
        propertiesFileName = "/Editor.defs"; // default properties filename
        loaded = false;
        initProperties(); // init class
    }
    
    public userProperties(String pFileName) {
        this.propertiesFileName = pFileName; // paremeter ser properties filename
        loaded = false;
        initProperties(); //init class
    }
    
    public String getPropertiesFileName(){
        return( propertiesFileName );
    }
    
    public void setPropertiesFileName(String pFileName)
    {
        this.propertiesFileName = pFileName;
    }
    
    public boolean isLoaded()
    {
        return( this.loaded );
    }
    
    
    private void initProperties()
    {
        uProperties = new Properties();
        try{
            // algo para mais tarde recordar, a carregar o ficheiro como um recurso
            // a partir da class path
            //java.net.URL url = ClassLoader.getSystemResource( propertiesFileName );
            //uProperties.load( url.openStream()  ); 
            String path = getClass().getProtectionDomain().getCodeSource().
                    getLocation().toString().substring(6);
            path = path.replaceAll("%20", " ");
            //System.out.println("Relative path: " + path  +  propertiesFileName );
            FileInputStream fis = new FileInputStream( path  +  propertiesFileName );
            uProperties.load( fis  ); 
            loaded = true;
            fis.close();
        }catch(FileNotFoundException e){ System.out.println("File Not Found: " + propertiesFileName + "\n" + e.getMessage()); }
        catch(IOException e){ System.out.println("Exception: Error while trying to load properties (or close inputStream).\n" + e.getMessage() );}
    }
    
    
    public String getPropertie(String key)
    {
        if( !isLoaded() || key==null ) return( null );
        return( uProperties.getProperty( key ) );
    }
    
    public boolean containsValue(Object value)
    {
        return( uProperties.containsValue( value ) );
    }
    
    public void setPropertie( String key, String value )
    {
        uProperties.setProperty( key, value );
        //uProperties.put(key, value );
       // uProperties.list( System.out );
        
    }
    
    public boolean saveProperties()
    {
        try{
            
            String path = getClass().getProtectionDomain().getCodeSource().
                    getLocation().toString().substring(6);
            path = path.replaceAll("%20", " ");
            System.out.println("[SAVE] Relative path: " + path + this.propertiesFileName  );
            FileOutputStream out = new FileOutputStream( path + this.propertiesFileName  );
            uProperties.store( out, null );
            out.close();
            return( true );
        }catch(FileNotFoundException e){ System.out.println("File not found: "  + this.propertiesFileName + "\n" + e.getMessage() );}
        catch(IOException e) {System.out.println("Exception: saveProperties()\n FileOutputStream Exception");}
        return( false );
    }
    
    public int size()
    {
        return( uProperties.size() );
    }
    
    public Enumeration getKeys()
    {    
        return( uProperties.keys() );
    }
    
    
    
}
