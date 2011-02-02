/*
 * FileManager.java
 *
 * Created on 12 de Abril de 2005, 0:05
 */

package Editor.Utils;

import java.io.*;
import java.awt.*;
import javax.swing.*;



/**
 *
 * @author  Administrator
 */

public class FileManager {
    
    
    private String fileName;
    private String path = System.getProperty("user.dir");
    private String noName = "Sem Nome";
    private final static String EXTENSION = "alg";
    
    /** Creates a new instance of FileManager */
    public FileManager() {
        fileName = noName + "." + EXTENSION ;
    }
//-------------------------------------------------------------------------------
    public void clearFileName(){
        fileName = noName +"." + EXTENSION;
    }
    
//-------------------------------------------------------------------------------
    public String getFileName(){
        return fileName;
    }
    
//-------------------------------------------------------------------------------
    public String ReadFile(String file){
        fileName = file;
        StringBuffer text = new StringBuffer();
        String tmp="";
        try{            
            BufferedReader fi = new BufferedReader(new FileReader(fileName));
            while(true) {
                if(!fi.ready())
                    break;
                tmp = new String(fi.readLine());
                text.append(tmp +"\n");
            }
            fi.close();
        } catch(IOException e){
            text.append(" ERROR FileManager: readFile\n" +  e.getMessage());
        }
        return text.toString();
    }
//-------------------------------------------------------------------------------
    public String openFileWindow(Component component ) {
        int returnValue = 0;
        try{
            JFileChooser jfc = new JFileChooser(path);
            jfc.setFileFilter(new AlgorithmFileFilter());
            returnValue = jfc.showOpenDialog( component );
            if( (returnValue == jfc.ERROR_OPTION ) || (returnValue==jfc.CANCEL_OPTION)  )
                return null;
            path = jfc.getSelectedFile().toString() ;
        }catch(java.awt.HeadlessException e){
           return "ERROR  FileManager: openFileWindow\n" + e.getMessage();
        }
        return ReadFile(path);
    }
    
//-------------------------------------------------------------------------------
    public void saveTextFile(String file, String txtCode){
        try{
            fileName = file;
            FileOutputStream fo = new FileOutputStream(fileName, false);
            fo.write( txtCode.getBytes() );
            fo.flush();
            fo.close();
        } catch(IOException e){
            System.out.println("FileManager: saveTextFile\n" + e.getMessage());
        }
    }
//-------------------------------------------------------------------------------
    public boolean saveFile(Component component, String FileName, String textCode ) {
        String output ="";
        int returnValue = 0;
        
        try{
            
            JFileChooser jfc = new JFileChooser(path);
            jfc.setFileFilter(new AlgorithmFileFilter());
            returnValue = jfc.showSaveDialog( component );
            if( (returnValue != jfc.ERROR_OPTION ) || (returnValue!=jfc.CANCEL_OPTION)  ) {
                
                path = jfc.getSelectedFile().toString();
                //introduzir a extensão caso não exista
                if( ! path.endsWith("." + EXTENSION))
                    path += "." + EXTENSION;
               
                saveTextFile(path, textCode);
                return true;
            }
        }catch(java.awt.HeadlessException e){
            System.out.println("FileManager: saveFile" + e.getMessage());
        }
        return false;        
    }
    
    
    public boolean saveFileUpdate(String codeText){
        
        try{
            FileOutputStream fo = new FileOutputStream( getFileName() , false);
            fo.write( codeText.getBytes() );
            fo.flush();
            fo.close();
        } catch(IOException e){
            System.out.println("FileManager:SaveFileUpdate\n" + e.getMessage() );
            return(false);
        }
        return( true );
    }
    
    
    public boolean isFileOpened() {
        return !fileName.equals(noName+"."+EXTENSION);
    }
       
   
    public boolean FileExists(String filename) {
        return( new File(filename).exists() );
    }
 
    public String getSimpleFileName() {
        try{
            if( fileName!=null )
                return( new File(this.fileName).getName() );
        }catch(Exception e){ System.err.println("--- error ---"); }
        return( null );
    }
    
}
