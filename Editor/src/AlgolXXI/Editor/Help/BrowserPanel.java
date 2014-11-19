package AlgolXXI.Editor.Help;

import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.openide.util.Exceptions;

public class BrowserPanel extends javax.swing.JPanel implements HyperlinkListener {

    private javax.swing.JButton btHome;
    private javax.swing.JButton btPrevious;
    private javax.swing.JButton btNext;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JEditorPane txtHelp;
    private URL home;
    private URL current;
    private Stack<URL> urlNext;
    private Stack<URL> urlPrevious;

    public BrowserPanel(String path) {
        super();
        initComponents();
        urlNext = new Stack<URL>();
        urlPrevious = new Stack<URL>();
        setPage(path);
        home = getClass().getClassLoader().getResource(path);
    }

    public void setPage(String path) {
        try {
            txtHelp.setPage(getClass().getClassLoader().getResource(path));
            current = getClass().getClassLoader().getResource(path);
        } catch (IOException ioe) {
            txtHelp.setText("ERROR: " + ioe);
        }
    }

    public void setURLPage(URL url) {
        try {
            txtHelp.setPage(url);
            current = url;
        } catch (IOException ioe) {
            txtHelp.setText("ERROR: " + ioe);
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (!event.getURL().toString().contains("http://") && !event.getURL().toString().contains("www.")) {
                urlPrevious.push(current);
                setURLPage(event.getURL());
            }
        }
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        btHome = new javax.swing.JButton();
        btPrevious = new javax.swing.JButton();
        btNext = new javax.swing.JButton();
        txtHelp = new javax.swing.JEditorPane();
        txtHelp.setEditable(false);
        txtHelp.addHyperlinkListener(this);
        JScrollPane scrollPane = new JScrollPane(txtHelp);
        add(scrollPane);
        setLayout(new java.awt.BorderLayout());
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        btPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Help/Contents/previous.png")));
        btPrevious.setPreferredSize(new java.awt.Dimension(25, 24));
        btPrevious.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPreviousAction(evt);
            }
        });
        jPanel1.add(btPrevious);
        //-----------------------------------------------------------------------
        btHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Help/Contents/home.png")));
        btHome.setPreferredSize(new java.awt.Dimension(65, 24));
        btHome.setText("Inicio");
        btHome.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHomeAction(evt);
            }
        });
        jPanel1.add(btHome);
        //-----------------------------------------------------------------------
        btNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Help/Contents/next.png")));
        btNext.setPreferredSize(new java.awt.Dimension(25, 24));
        btNext.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextAction(evt);
            }
        });
        jPanel1.add(btNext);
        //-----------------------------------------------------------------------
        add(jPanel1, java.awt.BorderLayout.NORTH);
        add(scrollPane, java.awt.BorderLayout.CENTER);
        btHome.setBorder(BorderFactory.createEmptyBorder());
        btHome.setContentAreaFilled(false);
        btPrevious.setBorder(BorderFactory.createEmptyBorder());
        btPrevious.setContentAreaFilled(false);
        btNext.setBorder(BorderFactory.createEmptyBorder());
        btNext.setContentAreaFilled(false);
    }

    private void btHomeAction(java.awt.event.ActionEvent evt) {
        try {
            urlPrevious.push(current);
            txtHelp.setPage(home);
            current = home;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void btNextAction(java.awt.event.ActionEvent evt) {
        if (!urlNext.empty()) {
            urlPrevious.push(current);
            setURLPage(urlNext.pop());
        }

    }

    private void btPreviousAction(java.awt.event.ActionEvent evt) {
        if (!urlPrevious.empty()) {
            urlNext.push(current);
            setURLPage(urlPrevious.pop());
        }
    }
}
