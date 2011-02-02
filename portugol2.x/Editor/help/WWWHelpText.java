/*
 * WWWHelpText.java
 *
 * Created on 18 de Novembro de 2005, 11:13
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Editor.help;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


/**
 *
 * @author arm
 */
public class WWWHelpText extends javax.swing.JPanel implements HyperlinkListener{
    public static String VERSION = "ver:1.5\tdata:20-11-2005 \t(c)António Manso";  
    // Variables declaration - do not modify                     
    private javax.swing.JButton btHome;
    private javax.swing.JButton btPrevious;
    private javax.swing.JButton btNext;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JEditorPane txtHelp;
    private javax.swing.JLabel txtUrl;
    private String home;
    Vector urlNext; // vector com links
    Vector urlPrevious;// vector com links
    
    
    public WWWHelpText(String file) {
        super();
        initComponents();
        urlNext = new Vector();
        urlPrevious = new Vector();
        setPage(file);
        home = file;
        
    }
    
    public void setPage(String file){
        try {            
            txtUrl.setText(ClassLoader.getSystemResource(file).toString());
            txtHelp.setPage(ClassLoader.getSystemResource(file));
        } catch(IOException ioe) {
            txtHelp.setText("ERROR: " + ioe );
        }
    }
     public void setURLPage(String file){
        try {            
                txtHelp.setPage( new URL(file));
                txtUrl.setText(file);
        } catch(IOException ioe) {
            txtHelp.setText("ERROR: " + ioe );
        }
    }
    
    public void hyperlinkUpdate(HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                urlPrevious.add(txtUrl.getText());
                setURLPage(event.getURL().toString());         
        }
    }
    
    
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        btHome = new javax.swing.JButton();
        btPrevious = new javax.swing.JButton();
        btNext = new javax.swing.JButton();
        txtUrl = new javax.swing.JLabel();
        txtHelp = new javax.swing.JEditorPane();
        
        txtHelp = new javax.swing.JEditorPane();
        txtHelp.setEditable(false);
        txtHelp.addHyperlinkListener(this);
        JScrollPane scrollPane = new JScrollPane(txtHelp);
        add(scrollPane);
        
        setLayout(new java.awt.BorderLayout());
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        btPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/www/previous.png")));
        btPrevious.setPreferredSize(new java.awt.Dimension(50, 24));
       // btPrevious.setText("Anterior");
        btPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPreviousAction(evt);
            }
        });        
        jPanel1.add(btPrevious);
        //-----------------------------------------------------------------------
         
       //-----------------------------------------------------------------------
        btHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/www/home.png")));
        btHome.setPreferredSize(new java.awt.Dimension(100, 24));
        btHome.setText("Inicio");
        btHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHomeAction(evt);
            }
        });        
        jPanel1.add(btHome);
        //-----------------------------------------------------------------------
        btNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/www/next.png")));
        btNext.setPreferredSize(new java.awt.Dimension(50, 24));
        //btNext.setText("seguinte");
        btNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextAction(evt);
            }
        });        
        jPanel1.add(btNext);
        //-----------------------------------------------------------------------
        
        jPanel1.add(txtUrl);
        add(jPanel1, java.awt.BorderLayout.NORTH);
        //para ocupar todo o ecra
        add(txtHelp, java.awt.BorderLayout.CENTER);
    }
    private void btHomeAction(java.awt.event.ActionEvent evt) {                                       
      setPage(home);
    }
    
    private void btNextAction(java.awt.event.ActionEvent evt) {                                       
        if( ! urlNext.isEmpty()){            
            //adicionar ao previous que está na string URL
            urlPrevious.add(txtUrl.getText());
            //import buscar o último ao next
            String link = (String) urlNext.remove(urlNext.size()-1);                        
            //actualizar a pagina
            setURLPage(link);
       }
 
    }
    
    private void btPreviousAction(java.awt.event.ActionEvent evt) {                                       
        if( ! urlPrevious.isEmpty()){            
            //System.out.println("\nPREV : " + urlPrevious.toString());
            urlNext.add(txtUrl.getText());
            String link = (String) urlPrevious.remove(urlPrevious.size()-1);                                    
            setURLPage(link);
       }
    }
        
}
