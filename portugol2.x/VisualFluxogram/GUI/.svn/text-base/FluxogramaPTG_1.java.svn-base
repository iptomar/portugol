/*
 * mainFluxograma.java
 *
 * Created on 30 de Junho de 2006, 13:59
 */

package VisualFluxogram.GUI; 

import Editor.GUI.Dialogs.Message;
import Editor.GUI.EditorPTG;
import Editor.Utils.FileManager;
import Editor.help.WWWHelpText;
import Portugol.Language.Evaluate.Calculator;
import Portugol.Language.Execute.ConsoleIO;
import Portugol.Language.Make.Fluxogram;
import Portugol.Language.Make.NodeInstruction;
import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.IteratorCodeParams;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import VisualFluxogram.FileFilter.BmpFileFilter;
import VisualFluxogram.FileFilter.FluxogramFileFilter;
import VisualFluxogram.FileFilter.JpegFileFilter;
import VisualFluxogram.FileFilter.PngFileFilter;
import VisualFluxogram.Make.MakeCode;
import VisualFluxogram.Make.MakeFlux;
import VisualFluxogram.Patterns.Conector;
import VisualFluxogram.Patterns.Conexao;
import VisualFluxogram.Patterns.Decisao;
import VisualFluxogram.Patterns.Escrita;
import VisualFluxogram.Patterns.Forma;
import VisualFluxogram.Patterns.Leitura;
import VisualFluxogram.Patterns.Processo;
import VisualFluxogram.Patterns.Terminador;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author  Zeus
 */
public class FluxogramaPTG_1 extends javax.swing.JFrame implements Runnable{
    
    public static String TITLE = "FLUXOGRAMA IDE 1.0 ";
    public static String DATE = "2-07-2006";
    public static String VERSION = "ver:1.0\tdata:02-07-2006 \t(c) Eduardo Gomes & António Manso";
    
    
    //public static Color Background=new Color(244,225,147);//new Color(176,254,143);//new Color(244,225,147);//new Color(50,68,44);//new Color(39,75,107);
    public static int Action=0;
    private int []ActionSelector;
    public static Vector AllForms=new Vector();
    public static Vector SelectedForms=new Vector();
    public static int SelectForm=-1;
    private Graphics2D g;
    
    private Forma forma=null;
    private Forma dest=null;
    public static Forma Execute=null;
    
    
    private double ScaleFactor=1.0;
    
    private Vector ConectedForms = new Vector();
    public static Vector ErrForms=new Vector();
    private Point TranslatePt=new Point();
    private Point orig=new Point();
    Vector ClipBoard= new Vector();
    
    Conector ConectorInCalc = null;
    
    
    Vector memory=new Vector();
    
    Color TerminadorColor = new Color(187,224,227);
    Color LeituraColor = new Color(187,224,227);
    Color EscritaColor = new Color(187,224,227);
    Color DecisaoColor = new Color(187,224,227);
    Color ConexaoColor = new Color(187,224,227);
    Color ProcessoColor = new Color(187,224,227);
    int typeconector=0;
    int EspHoriz=50;
    int EspVert=30;
    Preferencias preferences = new Preferencias();
    
    boolean isCalcConector=false;
    long InitialPress=0;
    boolean isIfTrueInCalc=true;
    private FileManager code;
    
    String ProjectPath = null;
    boolean ProjectChanged = false;
    
    
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    private ConsoleIO console;       // consola de execuçao
    private Thread autoExecute=null; //  thread
    private NodeInstruction instruction; // no do fluxograma
    private Fluxogram prog; // fluxograma completo
    private WWWHelpText helpFluxo; // ajuda
    //----------------------------------------------------------------
    
    public FluxogramaPTG_1(String prog) {
        initComponents();
        initMyComponents();
        
        
        
        
        this.setExtendedState(MAXIMIZED_BOTH);
        
        scrollPane1.setWheelScrollingEnabled(true);
        
        scrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        
        // Get all font family names
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String fontNames[] = ge.getAvailableFontFamilyNames();
        
        // Iterate the font family names
        for (int i=0; i<fontNames.length; i++) {
            this.FontFamilyName.addItem(fontNames[i]);
        }
        FontFamilyName.setSelectedItem("Papyrus");
        FontStyle.setSelectedItem("Negrito");
        FontSize.setText(""+20);
        
        EixoHoriz.setText(""+EspHoriz);
        EixoVert.setText(""+EspVert);
        
        txtCodePortugol.setTabSize(2);
        
        
        
        g=(Graphics2D)DrawArea.getGraphics();
        ErrTextField.setEditable(false);
        DrawArea.setPreferredSize(new Dimension( 1500, 1500 ));
        DrawArea.setBackground(new Color(244,225,147));
        scrollPane1.doLayout();
        
        // Get the size of the default screen
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        
        //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        Rectangle MaximumWindow = ge.getMaximumWindowBounds();
        this.setSize(MaximumWindow.width, MaximumWindow.height);
        
        tpUnderFluxogramEditor.setSelectedIndex(4);
        jSplitPane1.setDividerSize(6);
        jSplitPane1.setResizeWeight(1);
        
        jSplitPane1.setDividerLocation(this.getHeight()-40-this.toolBarFile.getHeight()-this.jMenuBar1.getHeight()-tpUnderFluxogramEditor.getMinimumSize().height);
        tpUnderFluxogramEditor.setMinimumSize(new Dimension(0,3));
        scrollPane1.setMinimumSize(new Dimension(0,200));
        
        ////////////////////////////////////////////////7
        
        if (prog != null){
            try{
                
                ErrForms.clear();
                ((ScrollArea)DrawArea).setSelected(null);
                MakeFlux flux = new MakeFlux(AllForms);
                FileManager f = new FileManager();
                flux.CalcFlowChart( f.ReadFile(prog) );
                
                
                EspHoriz=50;
                EspVert=30;
                ((ScrollArea)DrawArea).setGridSize(50, 30);
                
                FitFlowChart(true);
                DrawArea.repaint();
                ProjectChanged = false;
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            
            
            try {
                ObjectInputStream fi = new ObjectInputStream(new FileInputStream("defeito.flux"));
                this.AllForms = (Vector) fi.readObject();
                fi.close();
                ProjectPath = "defeito.flux";
                this.setTitle("FLUXOGRAMA IDE 1.0 - "+ProjectPath);
                FitFlowChart(true);
            } catch (FileNotFoundException ex) {
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        //////////////////////////////////////////////
        
        try{
            ObjectInputStream fi = new ObjectInputStream(new FileInputStream("fluxogramas.cfg"));
            preferences = (Preferencias) fi.readObject();
            fi.close();
            LoadPreferences();
            
        } catch (FileNotFoundException ex) {
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        
        //////////////////////////////////////////
        
        SelectedForms.clear();
        ErrForms.clear();
        ((ScrollArea)DrawArea).setSelected(null);
        ((ScrollArea)DrawArea).setPatterns(AllForms, SelectedForms);
        DrawArea.repaint();
        
        
        
    }
    private void initMyComponents(){
        code = new FileManager();
        ScrollArea s = (ScrollArea) this.DrawArea;
        s.setPatterns(AllForms, SelectedForms);
        //---------------- consola ------------------------
        console = new ConsoleIO();
        scrollMonitor.add(console);
        scrollMonitor.setViewportView(console);
        //----------------------------------------
        txtInfoFluxogram.append( FluxogramaPTG.TITLE  + "\n\r");
        txtInfoFluxogram.append( FluxogramaPTG.VERSION  + "\n\r");
        //---------------- HELP Linguagem -----------------------------------
        helpFluxo = new WWWHelpText("VisualFluxogram/help/index.html");
        this.spHelp.add(helpFluxo);
        this.spHelp.setViewportView(helpFluxo);
    }
    
    public void run(){
        
        long tmp_inicial = System.currentTimeMillis();/*tempo de inicio da corrida*/
        
        while (Execute != null){
            if (Execute instanceof Leitura)
                Execute=ExecuteRead(Execute);
            else if (Execute instanceof Escrita)
                Execute=ExecuteWrite(Execute);
            else if (Execute instanceof Processo)
                Execute=ExecuteProcess(Execute);
            else if (Execute instanceof Decisao)
                Execute=ExecuteDecision(Execute);
            else
                Execute=ExecuteOther(Execute);
            this.DisplayMemory();
        }
        
        autoExecute = null;
        Execute = null;
        console.write("\n-------------------------------------");
        //tempo
        long msec=System.currentTimeMillis()-tmp_inicial;
        int sec=0,min=0;
        
        while (msec>=1000){
            msec-=1000;
            sec++;
            if(sec==60){
                sec=0;
                min++;
            }
        }
        
        console.write("\nO programa terminou com sucesso :-) \n(tempo total: "+
                ( min!=0 ? ""+min+"min " : "" ) +
                ( sec>9 ? ""+sec : "0"+sec ) +"sec "+
                ( msec/10>9 ? ""+msec/10 : "0"+msec/10 )+"ms)" );
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    
    public JPanel getDrawArea(){
        return DrawArea;
    }
    
    public ConsoleIO getConsole(){
        return console;
    }
    
    public JScrollPane getScrollPanel(){
        return scrollPane1;
    }
    
    public void LoadPreferences(){
        
        DrawArea.setPreferredSize( new Dimension(preferences.largura, preferences.altura) );
        DrawArea.setSize( preferences.largura, preferences.altura );
        scrollPane1.doLayout();
        
        ////////////////////////////////////////////////////////////////////////
        
        TerminadorColor = preferences.TerminadorColor;
        LeituraColor = preferences.LeituraColor;
        EscritaColor = preferences.EscritaColor;
        DecisaoColor = preferences.DecisaoColor;
        ConexaoColor = preferences.ConexaoColor;
        ProcessoColor = preferences.ProcessoColor;
        DrawArea.setBackground( preferences.BackgroundColor );
        
        ////////////////////////////////////////////////////////////////////////
        
        ((ScrollArea)DrawArea).setAlphaComposite(preferences.slider);
        
        ////////////////////////////////////////////////////////////////////////
        
        jToggleButton1.setSelected(preferences.espacamento);
        ((ScrollArea)DrawArea).setGridEnabled(preferences.grelha);
        EspHoriz=preferences.EspHoriz;
        EspVert=preferences.EspVert;
        ((ScrollArea)DrawArea).setGridSize(EspHoriz, EspVert);
        
        ////////////////////////////////////////////////////////////////////////
        
        ManualConector.setSelected(false);
        AutoConector.setSelected(false);
        ClosestConector.setSelected(false);
        
        if (preferences.typeconector==0)
            ManualConector.setSelected(true);
        if (preferences.typeconector==1)
            AutoConector.setSelected(true);
        if (preferences.typeconector==2)
            ClosestConector.setSelected(true);
        
        ////////////////////////////////////////////////////////////////////////
        
        ((ScrollArea)DrawArea).setFontFamily( preferences.family );
        
        int style;
        if (preferences.style==0)
            style=Font.PLAIN;
        else if (preferences.style==1)
            style=Font.BOLD;
        else
            style=Font.ITALIC;
        ((ScrollArea)DrawArea).setFontStyle(style);
        
        ((ScrollArea)DrawArea).setFontSize( preferences.size );
        
        ((ScrollArea)DrawArea).setFontColor( preferences.FontColor );
        
        ////////////////////////////////////////////////////////////////////////
        //======================================================================
        
        
        Largura.setText( ""+DrawArea.getWidth() );
        Altura.setText( ""+DrawArea.getHeight() );
        
        ////////////////////////////////////////////////////////////////////////
        
        TerminadorColorBtn.setBackground( TerminadorColor );
        LeituraColorBtn.setBackground( LeituraColor );
        EscritaColorBtn.setBackground( EscritaColor );
        DecisaoColorBtn.setBackground( DecisaoColor );
        ConexaoColorBtn.setBackground( ConexaoColor );
        ProcessoColorBtn.setBackground( ProcessoColor );
        BackgroundColorBtn.setBackground( DrawArea.getBackground() );
        
        ////////////////////////////////////////////////////////////////////////
        
        int trans_value = (int)( 1.0f - ((ScrollArea)DrawArea).getAlphaComposite() ) * 100;
        TransparenciaField.setText(""+trans_value+"%");
        TransparenciaSlider.setValue(trans_value);
        
        ////////////////////////////////////////////////////////////////////////
        
        Espacamento.setSelected( jToggleButton1.isSelected() );
        Grelha.setSelected( ((ScrollArea)DrawArea).isGridEnabled() );
        EixoHoriz.setText( ""+EspHoriz );
        EixoVert.setText( ""+EspVert );
        EspacamentoActionPerformed(null);
        
        ////////////////////////////////////////////////////////////////////////
        
        if (typeconector==0)
            ManualConector.setSelected(true);
        if (typeconector==1)
            AutoConector.setSelected(true);
        if (typeconector==2)
            ClosestConector.setSelected(true);
        
        ////////////////////////////////////////////////////////////////////////
        
        FontFamilyName.setSelectedItem( preferences.family );
        
        if ( preferences.style==Font.PLAIN )
            FontStyle.setSelectedItem(0);
        if ( preferences.style==Font.BOLD )
            FontStyle.setSelectedItem(1);
        if ( preferences.style==Font.ITALIC )
            FontStyle.setSelectedItem(2);
        
        FontSize.setText( ""+preferences.size );
        
        FontColor.setBackground( ((ScrollArea)DrawArea).getFontColor() );
        
        this.Options.setVisible(false);
        DrawArea.repaint();
        
    }
    
    public void SavePreferences(){
        
        preferences.largura = DrawArea.getWidth();
        preferences.altura = DrawArea.getHeight();
        
        ////////////////////////////////////////////////////////////////////////
        
        preferences.TerminadorColor = TerminadorColor;
        preferences.LeituraColor = LeituraColor;
        preferences.EscritaColor = EscritaColor;
        preferences.DecisaoColor = DecisaoColor;
        preferences.ConexaoColor = ConexaoColor;
        preferences.ProcessoColor = ProcessoColor;
        preferences.BackgroundColor = DrawArea.getBackground();
        
        ////////////////////////////////////////////////////////////////////////
        
        preferences.slider = ((ScrollArea)DrawArea).getAlphaComposite();
        
        ////////////////////////////////////////////////////////////////////////
        
        preferences.espacamento = jToggleButton1.isSelected();
        preferences.grelha = ((ScrollArea)DrawArea).isGridEnabled();
        preferences.EspHoriz = EspHoriz;
        preferences.EspVert = EspVert;
        
        ////////////////////////////////////////////////////////////////////////
        
        if ( ManualConector.isSelected() )
            preferences.typeconector=0;
        if ( AutoConector.isSelected() )
            preferences.typeconector=1;
        if ( ClosestConector.isSelected() )
            preferences.typeconector=2;
        
        ////////////////////////////////////////////////////////////////////////
        Font font = ((ScrollArea)DrawArea).getFont();
        
        preferences.family = new String(font.getFamily());
        preferences.style = font.getStyle();
        preferences.size = font.getSize();
        preferences.FontColor = ((ScrollArea)DrawArea).getFontColor();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        DrawAreaPopupMenu = new javax.swing.JPopupMenu();
        MenuItemCut = new javax.swing.JMenuItem();
        MenuItemCopy = new javax.swing.JMenuItem();
        MenuItemPaste = new javax.swing.JMenuItem();
        MenuItemDelete = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        MenuItemSetText = new javax.swing.JMenuItem();
        MenuItemSetColor = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        MenuOrder = new javax.swing.JMenu();
        MenuItemTfrente = new javax.swing.JMenuItem();
        MenuItemTtras = new javax.swing.JMenuItem();
        MenuItemT1frente = new javax.swing.JMenuItem();
        MenuItemT1tras = new javax.swing.JMenuItem();
        MenuItemResize = new javax.swing.JMenuItem();
        MenuItemPreferences = new javax.swing.JMenuItem();
        Options = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ProcessoColorBtn = new javax.swing.JButton();
        EscritaColorBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        LeituraColorBtn = new javax.swing.JButton();
        DecisaoColorBtn = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        ConexaoColorBtn = new javax.swing.JButton();
        TerminadorColorBtn = new javax.swing.JButton();
        TransparenciaSlider = new javax.swing.JSlider();
        TransparenciaField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ManualConector = new javax.swing.JRadioButton();
        AutoConector = new javax.swing.JRadioButton();
        ClosestConector = new javax.swing.JRadioButton();
        FontFamilyName = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Largura = new javax.swing.JTextField();
        Altura = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        BackgroundColorBtn = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        FontSize = new javax.swing.JTextField();
        FontStyle = new javax.swing.JComboBox();
        FontColor = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        EixoVert = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        EixoHoriz = new javax.swing.JTextField();
        Espacamento = new javax.swing.JCheckBox();
        Grelha = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        Find = new javax.swing.JFrame();
        FindText = new javax.swing.JTextField();
        ReplaceText = new javax.swing.JTextField();
        MatchCase = new javax.swing.JCheckBox();
        WholeWord = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        FindAll = new javax.swing.JButton();
        ReplaceAll = new javax.swing.JButton();
        CancelFind = new javax.swing.JButton();
        toolBarFile = new javax.swing.JToolBar();
        toolBarFiles = new javax.swing.JToolBar();
        btFileNewAlgorithm = new javax.swing.JButton();
        btFileOpen = new javax.swing.JButton();
        btFileOpen1 = new javax.swing.JButton();
        btFileSave = new javax.swing.JButton();
        toolBarEdit = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        btEditCopy = new javax.swing.JButton();
        btEditCut = new javax.swing.JButton();
        btEditPast = new javax.swing.JButton();
        toolBarRun = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        btCompile = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btRun = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        btRunStep = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        btStop = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btRunPortugol = new javax.swing.JButton();
        jToolPatterns = new javax.swing.JToolBar();
        TerminadorBtn = new javax.swing.JToggleButton();
        LeituraBtn = new javax.swing.JToggleButton();
        ProcessoBtn = new javax.swing.JToggleButton();
        EscritaBtn = new javax.swing.JToggleButton();
        DecisaoBtn = new javax.swing.JToggleButton();
        ConexaoBtn = new javax.swing.JToggleButton();
        ConectorBtn = new javax.swing.JToggleButton();
        SaidaDecisao = new javax.swing.JButton();
        ScaleFactorField = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        scrollPane1 = new javax.swing.JScrollPane();
        DrawArea = new ScrollArea();
        tpUnderFluxogramEditor = new javax.swing.JTabbedPane();
        spOutput = new javax.swing.JScrollPane();
        splMonitor = new javax.swing.JScrollPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        scrollMonitor = new javax.swing.JScrollPane();
        scrollMemory = new javax.swing.JScrollPane();
        panelMemory = new javax.swing.JPanel();
        spErrorLis = new javax.swing.JScrollPane();
        ErrList = new java.awt.List();
        spPortugol = new javax.swing.JScrollPane();
        txtCodePortugol = new javax.swing.JTextArea();
        spHelp = new javax.swing.JScrollPane();
        spAbout = new javax.swing.JScrollPane();
        txtInfoFluxogram = new javax.swing.JTextArea();
        ErrTextField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuFicheiro = new javax.swing.JMenu();
        jMenuItemNewFile = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        MenuItemAbrirProjecto = new javax.swing.JMenuItem();
        MenuItemAbrirCodigo = new javax.swing.JMenuItem();
        MenuItemGuardarProjecto = new javax.swing.JMenuItem();
        MenuItemGuardarProjectoComo = new javax.swing.JMenuItem();
        MenuItemGuardarCodigo = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        MenuItemPropriedades = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        MenuItemSair = new javax.swing.JMenuItem();
        MenuEditar = new javax.swing.JMenu();
        MenuItemCortar = new javax.swing.JMenuItem();
        MenuItemCopiar = new javax.swing.JMenuItem();
        MenuItemColar = new javax.swing.JMenuItem();
        MenuItemApagar = new javax.swing.JMenuItem();
        MenuItemSelecionarTudo = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        MenuInterpretador = new javax.swing.JMenu();
        MenuItemCompilar = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        MenuItemExecutar = new javax.swing.JMenuItem();
        MenuItemExecutarPasso = new javax.swing.JMenuItem();
        MenuItemPararExecucao = new javax.swing.JMenuItem();
        MenuImagem = new javax.swing.JMenu();
        MenuItemAjustarArea = new javax.swing.JMenuItem();
        MenuItemEmbelezar = new javax.swing.JMenuItem();
        MenuItemCaptureSelected = new javax.swing.JMenuItem();
        MenuItemCaptureAll = new javax.swing.JMenuItem();
        MenuAjuda = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        MenuItemCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/cut.png")));
        MenuItemCut.setText("Cortar");
        MenuItemCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCutActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemCut);

        MenuItemCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/copy.png")));
        MenuItemCopy.setText("Copiar");
        MenuItemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCopyActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemCopy);

        MenuItemPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/paste.png")));
        MenuItemPaste.setText("Colar");
        MenuItemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPasteActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemPaste);

        MenuItemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/del.PNG")));
        MenuItemDelete.setText("Apagar");
        MenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemDeleteActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemDelete);

        DrawAreaPopupMenu.add(jSeparator3);

        MenuItemSetText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/text.PNG")));
        MenuItemSetText.setText("Colocar Texto");
        MenuItemSetText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemSetTextActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemSetText);

        MenuItemSetColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/color.png")));
        MenuItemSetColor.setText("Escolher Cor");
        MenuItemSetColor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MenuItemSetColorItemStateChanged(evt);
            }
        });
        MenuItemSetColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemSetColorActionPerformed(evt);
            }
        });
        MenuItemSetColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuItemSetColorKeyPressed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemSetColor);

        DrawAreaPopupMenu.add(jSeparator5);

        MenuOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuOrder.setText("Ordem");
        MenuOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuOrderActionPerformed(evt);
            }
        });

        MenuItemTfrente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/Tfrente.png")));
        MenuItemTfrente.setText("Trazer para a frente");
        MenuItemTfrente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemTfrenteActionPerformed(evt);
            }
        });

        MenuOrder.add(MenuItemTfrente);

        MenuItemTtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/Ttras.png")));
        MenuItemTtras.setText("Enviar para traz");
        MenuItemTtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemTtrasActionPerformed(evt);
            }
        });

        MenuOrder.add(MenuItemTtras);

        MenuItemT1frente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/T1frente.png")));
        MenuItemT1frente.setText("Trazer para diante");
        MenuItemT1frente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemT1frenteActionPerformed(evt);
            }
        });

        MenuOrder.add(MenuItemT1frente);

        MenuItemT1tras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/T1tras.png")));
        MenuItemT1tras.setText("enviar atras");
        MenuItemT1tras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemT1trasActionPerformed(evt);
            }
        });

        MenuOrder.add(MenuItemT1tras);

        DrawAreaPopupMenu.add(MenuOrder);

        MenuItemResize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemResize.setText("Redimensionar");
        MenuItemResize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemResizeActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemResize);

        MenuItemPreferences.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemPreferences.setText("Propriedades");
        MenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPreferencesActionPerformed(evt);
            }
        });

        DrawAreaPopupMenu.add(MenuItemPreferences);

        Options.setTitle("Preferencias");
        Options.setAlwaysOnTop(true);
        Options.setResizable(false);
        Options.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                OptionsWindowClosing(evt);
            }
        });

        jLabel1.setText("Cores por Defeito:");

        jLabel10.setText("Conexao:");

        jLabel2.setText("Terminador:");

        ProcessoColorBtn.setBackground(new java.awt.Color(187, 224, 227));
        ProcessoColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProcessoColorBtnActionPerformed(evt);
            }
        });

        EscritaColorBtn.setBackground(new java.awt.Color(187, 224, 227));
        EscritaColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EscritaColorBtnActionPerformed(evt);
            }
        });

        jLabel11.setText("Leitura:");

        jLabel21.setText("Escrita:");

        LeituraColorBtn.setBackground(new java.awt.Color(187, 224, 227));
        LeituraColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeituraColorBtnActionPerformed(evt);
            }
        });

        DecisaoColorBtn.setBackground(new java.awt.Color(187, 224, 227));
        DecisaoColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DecisaoColorBtnActionPerformed(evt);
            }
        });

        jLabel22.setText("Processo:");

        jLabel23.setText("Decisao:");

        ConexaoColorBtn.setBackground(new java.awt.Color(187, 224, 227));
        ConexaoColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConexaoColorBtnActionPerformed(evt);
            }
        });

        TerminadorColorBtn.setBackground(new java.awt.Color(187, 224, 227));
        TerminadorColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminadorColorBtnActionPerformed(evt);
            }
        });

        TransparenciaSlider.setValue(0);
        TransparenciaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TransparenciaSliderStateChanged(evt);
            }
        });

        TransparenciaField.setText("0%");
        TransparenciaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransparenciaFieldActionPerformed(evt);
            }
        });

        jLabel9.setText("Transpar\u00eancia:");

        jLabel12.setText("Modo de Calculo dos Conectores:");

        ManualConector.setSelected(true);
        ManualConector.setText("Manual");
        ManualConector.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ManualConector.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ManualConector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManualConectorActionPerformed(evt);
            }
        });
        ManualConector.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ManualConectorStateChanged(evt);
            }
        });

        AutoConector.setText("Automatico");
        AutoConector.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        AutoConector.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AutoConector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutoConectorActionPerformed(evt);
            }
        });
        AutoConector.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AutoConectorStateChanged(evt);
            }
        });

        ClosestConector.setText("Mais Proximo");
        ClosestConector.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ClosestConector.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ClosestConector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClosestConectorActionPerformed(evt);
            }
        });
        ClosestConector.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ClosestConectorStateChanged(evt);
            }
        });

        FontFamilyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FontFamilyNameActionPerformed(evt);
            }
        });

        jLabel13.setText("Dimens\u00e3o da Area de Desenho:");

        jLabel14.setText("Largura:");

        jLabel15.setText("Altura:");

        Largura.setText("1000");

        Altura.setText("1000");

        jLabel16.setText("Cor de Fundo:");

        BackgroundColorBtn.setBackground(new java.awt.Color(244, 225, 147));
        BackgroundColorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackgroundColorBtnActionPerformed(evt);
            }
        });

        FontSize.setText("20");
        FontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FontSizeActionPerformed(evt);
            }
        });

        FontStyle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Negrito", "It\u00e1lico" }));
        FontStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FontStyleActionPerformed(evt);
            }
        });

        FontColor.setBackground(new java.awt.Color(0, 0, 0));
        FontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FontColorActionPerformed(evt);
            }
        });

        jLabel17.setText("Tipo de Letra:");

        jLabel18.setText("Tamanho:");

        jLabel19.setText("Estilo:");

        jLabel20.setText("Cor:");

        EixoVert.setText("60");
        EixoVert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EixoVertActionPerformed(evt);
            }
        });

        jLabel25.setText("Eixo Horizontal:");

        jLabel26.setText("Eixo Vertical:");

        EixoHoriz.setText("100");
        EixoHoriz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EixoHorizActionPerformed(evt);
            }
        });

        Espacamento.setSelected(true);
        Espacamento.setText("Espacamento entre figuras");
        Espacamento.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Espacamento.setMargin(new java.awt.Insets(0, 0, 0, 0));
        Espacamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EspacamentoActionPerformed(evt);
            }
        });

        Grelha.setText("Grelha");
        Grelha.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Grelha.setMargin(new java.awt.Insets(0, 0, 0, 0));
        Grelha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrelhaActionPerformed(evt);
            }
        });

        jButton1.setText("Alterar Cores");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Pre-Visualizar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout OptionsLayout = new org.jdesktop.layout.GroupLayout(Options.getContentPane());
        Options.getContentPane().setLayout(OptionsLayout);
        OptionsLayout.setHorizontalGroup(
            OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(OptionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(OptionsLayout.createSequentialGroup()
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel13)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, OptionsLayout.createSequentialGroup()
                                .add(37, 37, 37)
                                .add(jLabel14)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(Largura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(22, 22, 22)
                        .add(jLabel15)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(Altura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(OptionsLayout.createSequentialGroup()
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(OptionsLayout.createSequentialGroup()
                                    .add(jLabel25)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(EixoHoriz, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jLabel26))
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(Espacamento))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(EixoVert)
                            .add(Grelha, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(OptionsLayout.createSequentialGroup()
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(OptionsLayout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(ManualConector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(30, 30, 30)
                                .add(AutoConector))
                            .add(OptionsLayout.createSequentialGroup()
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel17)
                                    .add(FontFamilyName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 22, Short.MAX_VALUE)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(FontStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel19))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(OptionsLayout.createSequentialGroup()
                                .add(17, 17, 17)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel18)
                                    .add(FontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(14, 14, 14)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(FontColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel20)))
                            .add(ClosestConector))
                        .add(31, 31, 31))
                    .add(OptionsLayout.createSequentialGroup()
                        .add(30, 30, 30)
                        .add(TransparenciaSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(TransparenciaField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(OptionsLayout.createSequentialGroup()
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(OptionsLayout.createSequentialGroup()
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(TerminadorColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(ConexaoColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(14, 14, 14)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel21))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(LeituraColorBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(EscritaColorBtn)))
                            .add(OptionsLayout.createSequentialGroup()
                                .add(jLabel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(BackgroundColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(9, 9, 9)
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(OptionsLayout.createSequentialGroup()
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel22))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(DecisaoColorBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(ProcessoColorBtn)))
                            .add(jButton1)))
                    .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator7)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                        .add(OptionsLayout.createSequentialGroup()
                            .add(jButton4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jButton5)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jButton6))))
                .add(416, 416, 416))
        );
        OptionsLayout.setVerticalGroup(
            OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(OptionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel13)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel15)
                    .add(jLabel14)
                    .add(Altura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(Largura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(15, 15, 15)
                .add(jSeparator8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(TerminadorColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(LeituraColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(DecisaoColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ConexaoColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(EscritaColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ProcessoColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(OptionsLayout.createSequentialGroup()
                        .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(BackgroundColorBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jButton1))
                        .add(12, 12, 12)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(TransparenciaSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(TransparenciaField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jSeparator7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(Espacamento)
                    .add(Grelha))
                .add(14, 14, 14)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel25)
                    .add(jLabel26)
                    .add(EixoHoriz, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(EixoVert, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(17, 17, 17)
                .add(jLabel12)
                .add(15, 15, 15)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ManualConector)
                    .add(ClosestConector)
                    .add(AutoConector))
                .add(15, 15, 15)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel17)
                    .add(jLabel20)
                    .add(jLabel18)
                    .add(jLabel19))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(FontColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(FontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(FontStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(FontFamilyName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(OptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton6)
                    .add(jButton5)
                    .add(jButton4))
                .addContainerGap(382, Short.MAX_VALUE))
        );
        Find.setTitle("Localizar/Substituir");
        Find.setAlwaysOnTop(true);
        Find.setName("");
        Find.setResizable(false);
        FindText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindTextActionPerformed(evt);
            }
        });

        ReplaceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReplaceTextActionPerformed(evt);
            }
        });

        MatchCase.setText("Mai\u00fascula/min\u00faculas");
        MatchCase.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        MatchCase.setMargin(new java.awt.Insets(0, 0, 0, 0));

        WholeWord.setText("Apenas palavras inteiras");
        WholeWord.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        WholeWord.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel6.setText("Encontrar:");

        jLabel24.setText("Substituir por:");

        FindAll.setText("Pesquisar");
        FindAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindAllActionPerformed(evt);
            }
        });

        ReplaceAll.setText("Substituir");
        ReplaceAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReplaceAllActionPerformed(evt);
            }
        });

        CancelFind.setText("Cancelar");
        CancelFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelFindActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout FindLayout = new org.jdesktop.layout.GroupLayout(Find.getContentPane());
        Find.getContentPane().setLayout(FindLayout);
        FindLayout.setHorizontalGroup(
            FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(FindLayout.createSequentialGroup()
                .addContainerGap()
                .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(MatchCase)
                    .add(FindLayout.createSequentialGroup()
                        .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel24)
                            .add(jLabel6))
                        .add(22, 22, 22)
                        .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(ReplaceText)
                            .add(FindText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)))
                    .add(WholeWord))
                .add(24, 24, 24)
                .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(FindAll)
                    .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(CancelFind)
                        .add(ReplaceAll)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FindLayout.setVerticalGroup(
            FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(FindLayout.createSequentialGroup()
                .addContainerGap()
                .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(FindText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(FindAll))
                .add(18, 18, 18)
                .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel24)
                    .add(ReplaceText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ReplaceAll))
                .add(24, 24, 24)
                .add(FindLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(FindLayout.createSequentialGroup()
                        .add(MatchCase)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(WholeWord))
                    .add(CancelFind))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("FLUXOGRAMA IDE 1.0");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        toolBarFile.setFloatable(false);
        toolBarFile.setAutoscrolls(true);
        toolBarFiles.setFloatable(false);
        toolBarFiles.setAutoscrolls(true);
        btFileNewAlgorithm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_new.png")));
        btFileNewAlgorithm.setBorder(null);
        btFileNewAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileNewAlgorithmActionPerformed(evt);
            }
        });

        toolBarFiles.add(btFileNewAlgorithm);

        btFileOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_open_fluxo.png")));
        btFileOpen.setBorder(null);
        btFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileOpenActionPerformed(evt);
            }
        });

        toolBarFiles.add(btFileOpen);

        btFileOpen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_open_code.png")));
        btFileOpen1.setBorder(null);
        btFileOpen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileOpen1ActionPerformed(evt);
            }
        });

        toolBarFiles.add(btFileOpen1);

        btFileSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_save.png")));
        btFileSave.setBorder(null);
        btFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileSaveActionPerformed(evt);
            }
        });

        toolBarFiles.add(btFileSave);

        toolBarFile.add(toolBarFiles);

        toolBarEdit.setFloatable(false);
        toolBarEdit.setToolTipText("Procurar");
        toolBarEdit.setAutoscrolls(true);
        jLabel4.setText("              ");
        toolBarEdit.add(jLabel4);

        btEditCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_copy.png")));
        btEditCopy.setBorder(null);
        btEditCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditCopyActionPerformed(evt);
            }
        });

        toolBarEdit.add(btEditCopy);

        btEditCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_cut.png")));
        btEditCut.setBorder(null);
        btEditCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditCutActionPerformed(evt);
            }
        });

        toolBarEdit.add(btEditCut);

        btEditPast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_past.png")));
        btEditPast.setBorder(null);
        btEditPast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditPastActionPerformed(evt);
            }
        });

        toolBarEdit.add(btEditPast);

        toolBarFile.add(toolBarEdit);

        toolBarRun.setToolTipText("Parar programa");
        toolBarRun.setAutoscrolls(true);
        jLabel3.setText("        ");
        toolBarRun.add(jLabel3);

        btCompile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/programa_verify.png")));
        btCompile.setToolTipText("Verificar fluxograma");
        btCompile.setBorder(null);
        btCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCompileActionPerformed(evt);
            }
        });

        toolBarRun.add(btCompile);

        jLabel5.setText("     ");
        toolBarRun.add(jLabel5);

        btRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_run.png")));
        btRun.setToolTipText("Executar Fluxograma");
        btRun.setBorder(null);
        btRun.setDoubleBuffered(true);
        btRun.setFocusCycleRoot(true);
        btRun.setFocusPainted(false);
        btRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRunActionPerformed(evt);
            }
        });

        toolBarRun.add(btRun);

        jLabel27.setText("     ");
        toolBarRun.add(jLabel27);

        btRunStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_pause_step.png")));
        btRunStep.setToolTipText("Correr Passo a Passo");
        btRunStep.setBorder(null);
        btRunStep.setDoubleBuffered(true);
        btRunStep.setFocusCycleRoot(true);
        btRunStep.setFocusPainted(false);
        btRunStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRunStepActionPerformed(evt);
            }
        });

        toolBarRun.add(btRunStep);

        jLabel28.setText("           ");
        toolBarRun.add(jLabel28);

        btStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_stop.png")));
        btStop.setToolTipText("Parar Execu\u00e7\u00e3o");
        btStop.setBorder(null);
        btStop.setDoubleBuffered(true);
        btStop.setFocusCycleRoot(true);
        btStop.setFocusPainted(false);
        btStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStopActionPerformed(evt);
            }
        });

        toolBarRun.add(btStop);

        jLabel7.setText("             ");
        toolBarRun.add(jLabel7);

        jLabel8.setText("             ");
        toolBarRun.add(jLabel8);

        btRunPortugol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/run_Algoritm.png")));
        btRunPortugol.setToolTipText("Executaro  Portugol");
        btRunPortugol.setBorder(null);
        btRunPortugol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRunPortugolActionPerformed(evt);
            }
        });

        toolBarRun.add(btRunPortugol);

        toolBarFile.add(toolBarRun);

        jToolPatterns.setMaximumSize(new java.awt.Dimension(410, 33));
        jToolPatterns.setMinimumSize(new java.awt.Dimension(410, 33));
        jToolPatterns.setPreferredSize(new java.awt.Dimension(410, 33));
        jToolPatterns.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jToolPatternsComponentMoved(evt);
            }
        });

        TerminadorBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/terminador.PNG")));
        TerminadorBtn.setToolTipText("terminador");
        TerminadorBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        TerminadorBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        TerminadorBtn.setPreferredSize(new java.awt.Dimension(24, 24));
        TerminadorBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TerminadorBtnItemStateChanged(evt);
            }
        });

        jToolPatterns.add(TerminadorBtn);

        LeituraBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/leitura.PNG")));
        LeituraBtn.setToolTipText("leitura");
        LeituraBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        LeituraBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        LeituraBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LeituraBtnItemStateChanged(evt);
            }
        });

        jToolPatterns.add(LeituraBtn);

        ProcessoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/processo.PNG")));
        ProcessoBtn.setToolTipText("processo");
        ProcessoBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        ProcessoBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        ProcessoBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProcessoBtnItemStateChanged(evt);
            }
        });

        jToolPatterns.add(ProcessoBtn);

        EscritaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/escrita.PNG")));
        EscritaBtn.setToolTipText("escrita");
        EscritaBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        EscritaBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        EscritaBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EscritaBtnItemStateChanged(evt);
            }
        });

        jToolPatterns.add(EscritaBtn);

        DecisaoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/decisao.PNG")));
        DecisaoBtn.setToolTipText("decisao");
        DecisaoBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        DecisaoBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        DecisaoBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DecisaoBtnItemStateChanged(evt);
            }
        });
        DecisaoBtn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DecisaoBtnPropertyChange(evt);
            }
        });
        DecisaoBtn.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                DecisaoBtnVetoableChange(evt);
            }
        });

        jToolPatterns.add(DecisaoBtn);

        ConexaoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/conexao.PNG")));
        ConexaoBtn.setToolTipText("conexao");
        ConexaoBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        ConexaoBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        ConexaoBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ConexaoBtnItemStateChanged(evt);
            }
        });

        jToolPatterns.add(ConexaoBtn);

        ConectorBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/conector.PNG")));
        ConectorBtn.setToolTipText("conector");
        ConectorBtn.setMaximumSize(new java.awt.Dimension(31, 31));
        ConectorBtn.setMinimumSize(new java.awt.Dimension(31, 31));
        ConectorBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ConectorBtnItemStateChanged(evt);
            }
        });

        jToolPatterns.add(ConectorBtn);

        SaidaDecisao.setText("SIM");
        SaidaDecisao.setMaximumSize(new java.awt.Dimension(40, 31));
        SaidaDecisao.setMinimumSize(new java.awt.Dimension(40, 31));
        SaidaDecisao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaidaDecisaoActionPerformed(evt);
            }
        });

        jToolPatterns.add(SaidaDecisao);

        ScaleFactorField.setText("100%");
        ScaleFactorField.setMaximumSize(new java.awt.Dimension(40, 31));
        ScaleFactorField.setMinimumSize(new java.awt.Dimension(40, 31));
        ScaleFactorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScaleFactorFieldActionPerformed(evt);
            }
        });
        ScaleFactorField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ScaleFactorFieldFocusLost(evt);
            }
        });

        jToolPatterns.add(ScaleFactorField);

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/grid.png")));
        jToggleButton1.setSelected(true);
        jToggleButton1.setMaximumSize(new java.awt.Dimension(31, 31));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(31, 31));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToolPatterns.add(jToggleButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/up.png")));
        jButton2.setMaximumSize(new java.awt.Dimension(31, 31));
        jButton2.setMinimumSize(new java.awt.Dimension(31, 31));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jToolPatterns.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/down.png")));
        jButton3.setMaximumSize(new java.awt.Dimension(31, 31));
        jButton3.setMinimumSize(new java.awt.Dimension(31, 31));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jToolPatterns.add(jButton3);

        toolBarFile.add(jToolPatterns);

        getContentPane().add(toolBarFile, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 0));
        jSplitPane1.setDividerSize(2);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setAutoscrolls(true);
        scrollPane1.setMinimumSize(new java.awt.Dimension(400, 300));
        scrollPane1.setPreferredSize(new java.awt.Dimension(400, 800));
        DrawArea.setComponentPopupMenu(DrawAreaPopupMenu);
        DrawArea.setPreferredSize(new java.awt.Dimension(800, 1500));
        DrawArea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                DrawAreaMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                DrawAreaMouseMoved(evt);
            }
        });
        DrawArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DrawAreaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DrawAreaMouseReleased(evt);
            }
        });

        scrollPane1.setViewportView(DrawArea);

        jSplitPane1.setLeftComponent(scrollPane1);

        tpUnderFluxogramEditor.setAutoscrolls(true);
        tpUnderFluxogramEditor.setMinimumSize(new java.awt.Dimension(2, 125));
        tpUnderFluxogramEditor.setPreferredSize(new java.awt.Dimension(117, 230));
        spOutput.setAutoscrolls(true);
        spOutput.setPreferredSize(new java.awt.Dimension(400, 200));
        splMonitor.setBackground(new java.awt.Color(0, 0, 0));
        splMonitor.setForeground(new java.awt.Color(255, 255, 255));
        splMonitor.setToolTipText("Program Console");
        splMonitor.setFont(new java.awt.Font("Courier New", 0, 12));
        jSplitPane2.setDividerLocation(700);
        jSplitPane2.setDividerSize(20);
        jSplitPane2.setLeftComponent(scrollMonitor);

        panelMemory.setLayout(new javax.swing.BoxLayout(panelMemory, javax.swing.BoxLayout.Y_AXIS));

        scrollMemory.setViewportView(panelMemory);

        jSplitPane2.setRightComponent(scrollMemory);

        splMonitor.setViewportView(jSplitPane2);

        spOutput.setViewportView(splMonitor);

        tpUnderFluxogramEditor.addTab("Output", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/console.png")), spOutput, "Esta \"tab\" apresenta a saida por defeito do interpretador.");

        ErrList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ErrListItemStateChanged(evt);
            }
        });
        ErrList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ErrListActionPerformed(evt);
            }
        });
        ErrList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ErrListMouseClicked(evt);
            }
        });

        spErrorLis.setViewportView(ErrList);

        tpUnderFluxogramEditor.addTab("Erros", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/errors.png")), spErrorLis);

        txtCodePortugol.setColumns(20);
        txtCodePortugol.setFont(new java.awt.Font("Courier New", 0, 12));
        txtCodePortugol.setRows(5);
        txtCodePortugol.setText("Portugol");
        spPortugol.setViewportView(txtCodePortugol);

        tpUnderFluxogramEditor.addTab("Portugol", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/portugol.png")), spPortugol);

        tpUnderFluxogramEditor.addTab("Ajuda", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/help.png")), spHelp);

        txtInfoFluxogram.setColumns(20);
        txtInfoFluxogram.setFont(new java.awt.Font("Courier New", 0, 12));
        txtInfoFluxogram.setRows(5);
        spAbout.setViewportView(txtInfoFluxogram);

        tpUnderFluxogramEditor.addTab("Informa\u00e7\u00f5es . . .", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/about.png")), spAbout, "Informa\u00e7\u00f5es do compilador");

        jSplitPane1.setBottomComponent(tpUnderFluxogramEditor);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(ErrTextField, java.awt.BorderLayout.SOUTH);

        MenuFicheiro.setText("Ficheiro");
        jMenuItemNewFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNewFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_new.png")));
        jMenuItemNewFile.setText("Novo");
        jMenuItemNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewFileActionPerformed(evt);
            }
        });

        MenuFicheiro.add(jMenuItemNewFile);

        MenuFicheiro.add(jSeparator2);

        MenuItemAbrirProjecto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemAbrirProjecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_open_Fluxo.png")));
        MenuItemAbrirProjecto.setText("Abrir Projecto");
        MenuItemAbrirProjecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemAbrirProjectoActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemAbrirProjecto);

        MenuItemAbrirCodigo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK));
        MenuItemAbrirCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_open_code.png")));
        MenuItemAbrirCodigo.setText("Abrir Codigo");
        MenuItemAbrirCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemAbrirCodigoActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemAbrirCodigo);

        MenuItemGuardarProjecto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemGuardarProjecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_save.png")));
        MenuItemGuardarProjecto.setText("Guardar Projecto");
        MenuItemGuardarProjecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemGuardarProjectoActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemGuardarProjecto);

        MenuItemGuardarProjectoComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_save_as.png")));
        MenuItemGuardarProjectoComo.setText("Guardar Projecto Como...");
        MenuItemGuardarProjectoComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemGuardarProjectoComoActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemGuardarProjectoComo);

        MenuItemGuardarCodigo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK));
        MenuItemGuardarCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_save_code.png")));
        MenuItemGuardarCodigo.setText("Guardar Codigo");
        MenuItemGuardarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemGuardarCodigoActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemGuardarCodigo);

        MenuFicheiro.add(jSeparator9);

        MenuItemPropriedades.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.ALT_MASK));
        MenuItemPropriedades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemPropriedades.setText("Propriedades");
        MenuItemPropriedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPropriedadesActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemPropriedades);

        MenuFicheiro.add(jSeparator1);

        MenuItemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_exit.png")));
        MenuItemSair.setText("Sair");
        MenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemSairActionPerformed(evt);
            }
        });

        MenuFicheiro.add(MenuItemSair);

        jMenuBar1.add(MenuFicheiro);

        MenuEditar.setText("Editar");
        MenuItemCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_cut.png")));
        MenuItemCortar.setText("Cortar");
        MenuItemCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCortarActionPerformed(evt);
            }
        });

        MenuEditar.add(MenuItemCortar);

        MenuItemCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_copy.png")));
        MenuItemCopiar.setText("Copiar");
        MenuItemCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCopiarActionPerformed(evt);
            }
        });

        MenuEditar.add(MenuItemCopiar);

        MenuItemColar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemColar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_past.png")));
        MenuItemColar.setText("Colar");
        MenuItemColar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemColarActionPerformed(evt);
            }
        });

        MenuEditar.add(MenuItemColar);

        MenuItemApagar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        MenuItemApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemApagar.setText("Apagar");
        MenuItemApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemApagarActionPerformed(evt);
            }
        });

        MenuEditar.add(MenuItemApagar);

        MenuItemSelecionarTudo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemSelecionarTudo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemSelecionarTudo.setText("Selecionar Tudo");
        MenuItemSelecionarTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemSelecionarTudoActionPerformed(evt);
            }
        });

        MenuEditar.add(MenuItemSelecionarTudo);

        MenuEditar.add(jSeparator4);

        jMenuBar1.add(MenuEditar);

        MenuInterpretador.setText("Interpretador");
        MenuItemCompilar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        MenuItemCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/programa_verify.png")));
        MenuItemCompilar.setText("Compilar programa");
        MenuItemCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCompilarActionPerformed(evt);
            }
        });

        MenuInterpretador.add(MenuItemCompilar);

        MenuInterpretador.add(jSeparator6);

        MenuItemExecutar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        MenuItemExecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_run.png")));
        MenuItemExecutar.setMnemonic('E');
        MenuItemExecutar.setText("Executar");
        MenuItemExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemExecutarActionPerformed(evt);
            }
        });

        MenuInterpretador.add(MenuItemExecutar);

        MenuItemExecutarPasso.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        MenuItemExecutarPasso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_pause_step.png")));
        MenuItemExecutarPasso.setMnemonic('E');
        MenuItemExecutarPasso.setText("Executar passo a passo");
        MenuItemExecutarPasso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemExecutarPassoActionPerformed(evt);
            }
        });

        MenuInterpretador.add(MenuItemExecutarPasso);

        MenuItemPararExecucao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        MenuItemPararExecucao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_stop.png")));
        MenuItemPararExecucao.setText("Parar Execucao");
        MenuItemPararExecucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPararExecucaoActionPerformed(evt);
            }
        });

        MenuInterpretador.add(MenuItemPararExecucao);

        jMenuBar1.add(MenuInterpretador);

        MenuImagem.setText("Imagem");
        MenuItemAjustarArea.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemAjustarArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemAjustarArea.setText("Ajustar Area de Desenho");
        MenuItemAjustarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemAjustarAreawe11ActionPerformed(evt);
            }
        });

        MenuImagem.add(MenuItemAjustarArea);

        MenuItemEmbelezar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemEmbelezar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemEmbelezar.setText("Embelezar");
        MenuItemEmbelezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemEmbelezarwe11ActionPerformed(evt);
            }
        });

        MenuImagem.add(MenuItemEmbelezar);

        MenuItemCaptureSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemCaptureSelected.setText("Capturar Figuras Selecionadas");
        MenuItemCaptureSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCaptureSelectedActionPerformed(evt);
            }
        });

        MenuImagem.add(MenuItemCaptureSelected);

        MenuItemCaptureAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        MenuItemCaptureAll.setText("Capturar Area de Desenho");
        MenuItemCaptureAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemCaptureAllActionPerformed(evt);
            }
        });

        MenuImagem.add(MenuItemCaptureAll);

        jMenuBar1.add(MenuImagem);

        MenuAjuda.setText("Ajuda");
        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/help.png")));
        jMenuItem4.setText("Ajuda Fluxogramas");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });

        MenuAjuda.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VisualFluxogram/Icons/empty.png")));
        jMenuItem5.setText("Sobre");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });

        MenuAjuda.add(jMenuItem5);

        jMenuBar1.add(MenuAjuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btFileOpen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileOpen1ActionPerformed
        MenuItemAbrirCodigoActionPerformed(null);
    }//GEN-LAST:event_btFileOpen1ActionPerformed
    
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
// TODO add your handling code here:
        About about = new About();
        about.setVisible(true);
        about.requestFocus();
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
        jButton6ActionPerformed(null);
        this.Options.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
// TODO add your handling code here:
        LoadPreferences();/*
                           
        Largura.setText( ""+DrawArea.getWidth() );
        Altura.setText( ""+DrawArea.getHeight() );
                           
        ////////////////////////////////////////////////////////////////////////
                           
        TerminadorColorBtn.setBackground( TerminadorColor );
        LeituraColorBtn.setBackground( LeituraColor );
        EscritaColorBtn.setBackground( EscritaColor );
        DecisaoColorBtn.setBackground( DecisaoColor );
        ConexaoColorBtn.setBackground( ConexaoColor );
        ProcessoColorBtn.setBackground( ProcessoColor );
        BackgroundColorBtn.setBackground( DrawArea.getBackground() );
                           
        ////////////////////////////////////////////////////////////////////////
                           
        int trans_value = (int)( 1.0f - ((ScrollArea)DrawArea).getAlphaComposite() ) * 100;
            TransparenciaField.setText(""+trans_value+"%");
            TransparenciaSlider.setValue(trans_value);
                           
        ////////////////////////////////////////////////////////////////////////
                           
        Espacamento.setSelected( jToggleButton1.isSelected() );
        Grelha.setSelected( ((ScrollArea)DrawArea).isGridEnabled() );
        EixoHoriz.setText( ""+EspHoriz );
        EixoVert.setText( ""+EspVert );
                           
        ////////////////////////////////////////////////////////////////////////
                           
        if (typeconector==0)
            ManualConector.setSelected(true);
        if (typeconector==1)
            AutoConector.setSelected(true);
        if (typeconector==2)
            ClosestConector.setSelected(true);
                           
        ////////////////////////////////////////////////////////////////////////
                           
        Font font = ((ScrollArea)DrawArea).getFont();
                           
        FontFamilyName.setSelectedItem( font.getFamily() );
                           
        if ( font.getStyle()==Font.PLAIN )
            FontStyle.setSelectedItem(0);
        if ( font.getStyle()==Font.BOLD )
            FontStyle.setSelectedItem(1);
        if ( font.getStyle()==Font.ITALIC )
            FontStyle.setSelectedItem(2);
                           
        FontSize.setText( ""+((ScrollArea)DrawArea).getFontSize() );
                           
        FontColor.setBackground( ((ScrollArea)DrawArea).getFontColor() );
                           
        this.Options.setVisible(false);*/
        
    }//GEN-LAST:event_jButton5ActionPerformed
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
// TODO add your handling code here:
        boolean error = false;
        
        int largura = DrawArea.getWidth();
        int altura = DrawArea.getHeight();
        try{
            largura = Integer.parseInt( Largura.getText() );
            if (largura<100)
                largura=100;
        }catch(NumberFormatException e){
            Largura.setText( ""+DrawArea.getWidth() );
            error = true;
        }
        try{
            altura = Integer.parseInt( Altura.getText() );
            if (altura<100)
                altura=100;
        }catch(NumberFormatException e){
            Altura.setText( ""+DrawArea.getHeight() );
            error = true;
        }
        DrawArea.setPreferredSize(new Dimension(largura, altura));
        DrawArea.setSize(largura, altura);
        scrollPane1.doLayout();
        
        ////////////////////////////////////////////////////////////////////////
        
        TerminadorColor = TerminadorColorBtn.getBackground();
        LeituraColor = LeituraColorBtn.getBackground();
        EscritaColor = EscritaColorBtn.getBackground();
        DecisaoColor = DecisaoColorBtn.getBackground();
        ConexaoColor = ConexaoColorBtn.getBackground();
        ProcessoColor = ProcessoColorBtn.getBackground();
        DrawArea.setBackground( BackgroundColorBtn.getBackground() );
        
        ////////////////////////////////////////////////////////////////////////
        
        String value=TransparenciaField.getText();
        if (value.endsWith("%"))
            value=value.substring(0,value.length()-1);
        try{
            int SliderValue=Integer.parseInt(value);
            if (SliderValue<0 || SliderValue>100)
                Integer.parseInt("erro");
            
            TransparenciaField.setText(value+"%");
            TransparenciaSlider.setValue(SliderValue);
            ((ScrollArea)DrawArea).setAlphaComposite(1.0f-SliderValue/100.0f);
            
        }catch(NumberFormatException e){
            int trans_value = (int)( 1.0f - ((ScrollArea)DrawArea).getAlphaComposite() ) * 100;
            TransparenciaField.setText(""+trans_value+"%");
            TransparenciaSlider.setValue(trans_value);
            error = true;
        }
        
        ////////////////////////////////////////////////////////////////////////
        if (Espacamento.isSelected())
            jToggleButton1.setSelected(true);
        else
            jToggleButton1.setSelected(false);
        
        if (Grelha.isSelected())
            ((ScrollArea)DrawArea).setGridEnabled(true);
        else
            ((ScrollArea)DrawArea).setGridEnabled(false);
        
        try{
            int horiz=Integer.parseInt( EixoHoriz.getText() );
            if (horiz<=0)
                Integer.parseInt("erro");
            
            EspHoriz=horiz;
            
        }catch(NumberFormatException e){
            EixoHoriz.setText( ""+EspHoriz );
            error = true;
        }
        
        try{
            int vert=Integer.parseInt( EixoVert.getText() );
            if (vert<=0)
                Integer.parseInt("erro");
            
            EspVert=vert;
            
        }catch(NumberFormatException e){
            EixoVert.setText( ""+EspVert );
            error = true;
        }
        ((ScrollArea)DrawArea).setGridSize(EspHoriz, EspVert);
        
        ////////////////////////////////////////////////////////////////////////
        
        ((ScrollArea)DrawArea).setFontFamily( (String)FontFamilyName.getSelectedItem() );
        
        int style;
        if (FontStyle.getSelectedIndex()==0)
            style=Font.PLAIN;
        else if (FontStyle.getSelectedIndex()==1)
            style=Font.BOLD;
        else
            style=Font.ITALIC;
        ((ScrollArea)DrawArea).setFontStyle(style);
        
        try{
            ((ScrollArea)DrawArea).setFontSize( Integer.parseInt(FontSize.getText()) );
        }catch(NumberFormatException e){
            FontSize.setText( ""+((ScrollArea)DrawArea).getFontSize() );
            error = true;
        }
        
        ((ScrollArea)DrawArea).setFontColor( FontColor.getBackground() );
        
        
        if (error)
            Toolkit.getDefaultToolkit().beep();
        DrawArea.repaint();
        
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void MenuItemPropriedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPropriedadesActionPerformed
// TODO add your handling code here:
        if (ManualConector.isSelected())
            typeconector=0;
        if (AutoConector.isSelected())
            typeconector=1;
        if (ClosestConector.isSelected())
            typeconector=2;
        
        Largura.setText( ""+DrawArea.getWidth() );
        Altura.setText( ""+DrawArea.getHeight() );
        EixoHoriz.setText( ""+EspHoriz );
        EixoVert.setText( ""+EspVert );
        FontSize.setText( ""+((ScrollArea)DrawArea).getFontSize() );
        SavePreferences();
        
        this.Options.setSize(380, 570);
        this.Options.setVisible(true);
    }//GEN-LAST:event_MenuItemPropriedadesActionPerformed
    
    private void jMenuItemNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewFileActionPerformed
        if( ProjectChanged && !AllForms.isEmpty() ){
            int option = JOptionPane.showConfirmDialog(this, "Deseja guardar o projecto aberto?");
            if (option == JOptionPane.YES_OPTION)
                MenuItemGuardarProjectoActionPerformed(evt);
            if (option == JOptionPane.CANCEL_OPTION)
                return;
        }
        
        this.setTitle("FLUXOGRAMA IDE 1.0");
        ProjectPath = null;
        ProjectChanged = false;
        SelectedForms.clear();
        ErrForms.clear();
        AllForms.clear();
        ((ScrollArea)DrawArea).setSelected(null);
        DrawArea.repaint();
    }//GEN-LAST:event_jMenuItemNewFileActionPerformed
    
    private void MenuItemAbrirProjectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemAbrirProjectoActionPerformed
// TODO add your handling code here:
        if( ProjectChanged && !AllForms.isEmpty() ){
            int option = JOptionPane.showConfirmDialog(this, "Deseja guardar o projecto aberto?");
            if (option == JOptionPane.YES_OPTION)
                MenuItemGuardarProjectoActionPerformed(evt);
            if (option == JOptionPane.CANCEL_OPTION)
                return;
        }
        
        try{
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setFileFilter(new FluxogramFileFilter());
            // Show dialog; this method does not return until dialog is closed
            int returnValue = chooser.showOpenDialog(this);
            if( (returnValue == chooser.ERROR_OPTION ) || (returnValue==chooser.CANCEL_OPTION)  )
                return;
            
            ProjectPath = chooser.getSelectedFile().getPath();
            
            ObjectInputStream fi = new ObjectInputStream(new FileInputStream(ProjectPath));
            this.AllForms = (Vector) fi.readObject();
            fi.close();
            this.setTitle("FLUXOGRAMA IDE 1.0 - "+ProjectPath);
            
            SelectedForms.clear();
            ErrForms.clear();
            ((ScrollArea)DrawArea).setSelected(null);
            ((ScrollArea)DrawArea).setPatterns(AllForms, SelectedForms);
            FitFlowChart(true);
            DrawArea.repaint();
            
            ProjectChanged = false;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_MenuItemAbrirProjectoActionPerformed
    
    private void MenuItemAbrirCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemAbrirCodigoActionPerformed
// TODO add your handling code here:
        
        if( ProjectChanged && !AllForms.isEmpty() ){
            int option = JOptionPane.showConfirmDialog(this, "Deseja guardar o projecto aberto?");
            if (option == JOptionPane.YES_OPTION)
                MenuItemGuardarProjectoActionPerformed(evt);
            if (option == JOptionPane.CANCEL_OPTION)
                return;
        }
        
        
        try{
            String prog = code.openFileWindow(this);
            if ( prog==null )
                return;
            // Show dialog; this method does not return until dialog is closed
            
            SelectedForms.clear();
            ErrForms.clear();
            ((ScrollArea)DrawArea).setSelected(null);
            MakeFlux flux = new MakeFlux(AllForms);
            flux.CalcFlowChart( prog );
            
            EspHoriz=50;
            EspVert=30;
            ((ScrollArea)DrawArea).setGridSize(50, 30);
            
            FitFlowChart(true);
            DrawArea.repaint();
            ProjectChanged = false;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_MenuItemAbrirCodigoActionPerformed
    
    private void MenuItemGuardarProjectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemGuardarProjectoActionPerformed
// TODO add your handling code here:
        if (ProjectPath == null ){
            
            try{
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                chooser.setFileFilter(new FluxogramFileFilter());
                // Show dialog; this method does not return until dialog is closed
                int returnValue = chooser.showSaveDialog(this);
                if( (returnValue == chooser.ERROR_OPTION ) || (returnValue==chooser.CANCEL_OPTION)  )
                    return;
                
                ProjectPath = chooser.getSelectedFile().getPath();
                //introduzir a extensão caso não exista
                if( ! ProjectPath.endsWith(".flux"))
                    ProjectPath += ".flux";
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        try {
            ObjectOutputStream fo = new ObjectOutputStream(new FileOutputStream(ProjectPath, false));
            fo.writeObject(AllForms);
            fo.flush();
            fo.close();
            ProjectChanged = false;
            this.setTitle("FLUXOGRAMA IDE 1.0 - "+ProjectPath);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_MenuItemGuardarProjectoActionPerformed
    
    private void MenuItemGuardarProjectoComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemGuardarProjectoComoActionPerformed
// TODO add your handling code here:
        String auxProjectPath=ProjectPath;
        ProjectPath = null;
        MenuItemGuardarProjectoActionPerformed(evt);
        if (ProjectPath == null)
            ProjectPath = auxProjectPath;
    }//GEN-LAST:event_MenuItemGuardarProjectoComoActionPerformed
    
    private void MenuItemGuardarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemGuardarCodigoActionPerformed
// TODO add your handling code here:
        MenuItemCompilarActionPerformed(evt);
        if ( forma == null )
            return;
        
        try{
            code.saveFile(this, null, txtCodePortugol.getText() );
        }catch(Exception e){
            Message.Error("MenuItemSalvarActionPerformed\n");
        } // end of catch statement
    }//GEN-LAST:event_MenuItemGuardarCodigoActionPerformed
    
    private void MenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemSairActionPerformed
        formComponentHidden(null);
    }//GEN-LAST:event_MenuItemSairActionPerformed
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
// TODO add your handling code here:
        if( ProjectChanged && !AllForms.isEmpty() ){
            int option = JOptionPane.showConfirmDialog(this, "Deseja guardar o projecto aberto?");
            if (option == JOptionPane.CANCEL_OPTION){
                this.setVisible(true);
                return;
            }
            if (option == JOptionPane.YES_OPTION)
                MenuItemGuardarProjectoActionPerformed(null);
        }
        if ( autoExecute != null && autoExecute.isAlive() )
            autoExecute.stop();
        
        try {
            SavePreferences();
            ObjectOutputStream fo = new ObjectOutputStream(new FileOutputStream("fluxogramas.cfg", false));
            fo.writeObject( preferences );
            fo.flush();
            fo.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        
        System.exit(0);
    }//GEN-LAST:event_formComponentHidden
    
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
// TODO add your handling code here:
        
        if (jToggleButton1.isSelected()){
            Espacamento.setSelected(true);
            if (Grelha.isSelected())
                ((ScrollArea)DrawArea).setGridEnabled(true);
        }else{
            Espacamento.setSelected(false);
            ((ScrollArea)DrawArea).setGridEnabled(false);
        }
        EspacamentoActionPerformed(null);
        DrawArea.repaint();
    }//GEN-LAST:event_jToggleButton1ActionPerformed
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
        ((ScrollArea)DrawArea).setFontSize( ((ScrollArea)DrawArea).getFontSize()-1 );
        DrawArea.repaint();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        ((ScrollArea)DrawArea).setFontSize( ((ScrollArea)DrawArea).getFontSize()+1 );
        DrawArea.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void MenuItemAjustarAreawe11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemAjustarAreawe11ActionPerformed
// TODO add your handling code here:
        FitFlowChart(true);
        SelectedForms.clear();
        ConectedForms.clear();
        DrawArea.repaint();
    }//GEN-LAST:event_MenuItemAjustarAreawe11ActionPerformed
    
    private void MenuItemEmbelezarwe11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemEmbelezarwe11ActionPerformed
// TODO add your handling code here:
        MenuItemCompilarActionPerformed(evt);
        if (forma == null)
            return;
        forma = null;
        Vector AuxForms = AllForms;
        try{
            
            SelectedForms.clear();
            ErrForms.clear();
            ((ScrollArea)DrawArea).setSelected(null);
            
            MakeFlux flux = new MakeFlux(AllForms);
            flux.CalcFlowChart( txtCodePortugol.getText() );
            
            /*int largura = flux.GetHorizSize();
            int altura = flux.GetVertSize();
            DrawArea.setPreferredSize(new Dimension(largura, altura));
            DrawArea.setSize(largura, altura);
            scrollPane1.doLayout();*/
            
            EspHoriz=50;
            EspVert=30;
            ((ScrollArea)DrawArea).setGridSize(50, 30);
            
            FitFlowChart(true);
            DrawArea.repaint();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            AllForms = AuxForms;
        }
    }//GEN-LAST:event_MenuItemEmbelezarwe11ActionPerformed
    
    private void MenuItemPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPreferencesActionPerformed
// TODO add your handling code here:
        MenuItemPropriedadesActionPerformed(null);
    }//GEN-LAST:event_MenuItemPreferencesActionPerformed
    
    private void ReplaceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReplaceTextActionPerformed
// TODO add your handling code here:
        ReplaceAllActionPerformed(null);
    }//GEN-LAST:event_ReplaceTextActionPerformed
    
    private void ReplaceAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReplaceAllActionPerformed
// TODO add your handling code here:
        ErrList.removeAll();
        ErrForms.clear();
        
        String text = this.FindText.getText();
        if (text==null || text.length()==0){
            ErrTextField.setText("Erro: nao exite palavra para pesquisa");
            return;
        }
        
        String replace = this.ReplaceText.getText();
        if (replace==null || replace.length()==0){
            ErrTextField.setText("Erro: nao exite palavra para substituicao");
            return;
        }
        
        for (int i=0;i<AllForms.size();i++)
            if ( ((Forma)AllForms.get(i)).Text.contains( text ) ){
            
            if ( WholeWord.isSelected() ){
                int index = ((Forma)AllForms.get(i)).Text.indexOf( text );
                if ( index>0 && ((Forma)AllForms.get(i)).Text.charAt(index-1)!=' ' )
                    continue;
                
                index += text.length();
                if ( index<((Forma)AllForms.get(i)).Text.length() && ((Forma)AllForms.get(i)).Text.charAt(index)!=' ' )
                    continue;
            }
            
            ((Forma)AllForms.get(i)).Text = ((Forma)AllForms.get(i)).Text.replaceAll(text, replace);
            }
        
        
    }//GEN-LAST:event_ReplaceAllActionPerformed
    
    private void CancelFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelFindActionPerformed
// TODO add your handling code here:
        this.Find.setVisible(false);
    }//GEN-LAST:event_CancelFindActionPerformed
    
    private void FindTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindTextActionPerformed
// TODO add your handling code here:
        FindAllActionPerformed(null);
    }//GEN-LAST:event_FindTextActionPerformed
    
    private void ErrListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ErrListActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_ErrListActionPerformed
    
    private void FindAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindAllActionPerformed
// TODO add your handling code here:
        
        ErrList.removeAll();
        ErrForms.clear();
        ((ScrollArea)DrawArea).setSelected(null);
        ((ScrollArea)DrawArea).setSelectedColor( Color.YELLOW );
        
        tpUnderFluxogramEditor.setSelectedIndex(1);
        jSplitPane1.setDividerLocation(.7);
        
        String text = this.FindText.getText();
        if (text==null || text.length()==0){
            ErrTextField.setText("Erro: nao exite palavra para pesquisa");
            return;
        }
        
        
        for (int i=0;i<AllForms.size();i++)
            if ( ( MatchCase.isSelected() && ((Forma)AllForms.get(i)).Text.contains( text ) ) ||
                ( ( !MatchCase.isSelected() &&
                ((Forma)AllForms.get(i)).Text.toLowerCase().contains( text.toLowerCase() ) ) ) ){
            
            if ( WholeWord.isSelected() ){
                
                int index = ((Forma)AllForms.get(i)).Text.toLowerCase().indexOf( text.toLowerCase() );
                if ( index>0 && ((Forma)AllForms.get(i)).Text.charAt(index-1)!=' ' )
                    continue;
                
                index += text.length();
                if ( index<((Forma)AllForms.get(i)).Text.length() && ((Forma)AllForms.get(i)).Text.charAt(index)!=' ' )
                    continue;
            }
            
            ErrList.add( "Posicao: "+ ( i>9 ? "" : "0" ) +  i +
                    "           Tipo: "+ ((Forma)AllForms.get(i)).getClass().getSimpleName() +
                    "           Texto: "+((Forma)AllForms.get(i)).Text );
            ErrForms.add( AllForms.get(i) );
            }
        //this.Find.setVisible(false);
    }//GEN-LAST:event_FindAllActionPerformed
    
    private void ErrListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ErrListMouseClicked
// TODO add your handling code here:
        if ( evt.getClickCount() == 2 )
            MenuItemSetTextActionPerformed(null);
        
        
    }//GEN-LAST:event_ErrListMouseClicked
    
    private void MenuItemCaptureSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCaptureSelectedActionPerformed
// TODO add your handling code here:
        if (SelectedForms.isEmpty()){
            JOptionPane.showMessageDialog(this, "Selecione primeiro as figuras a capturar", "INFORMAÇÃO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //////////////////////////////////
        int MaxHoriz=-999999999;
        int MinHoriz=999999999;
        int MaxVert=-999999999;
        int MinVert=999999999;
        
        for(int i=0; i<SelectedForms.size(); i++){
            
            if ( ((Forma)SelectedForms.get(i)).BottonRight.x > MaxHoriz )
                MaxHoriz = (int)((Forma)SelectedForms.get(i)).BottonRight.x;
            if ( ((Forma)SelectedForms.get(i)).BottonRight.y > MaxVert )
                MaxVert = (int)((Forma)SelectedForms.get(i)).BottonRight.y;
            
            if ( ((Forma)SelectedForms.get(i)).TopLeft.x < MinHoriz )
                MinHoriz = (int)((Forma)SelectedForms.get(i)).TopLeft.x;
            if ( ((Forma)SelectedForms.get(i)).TopLeft.y < MinVert )
                MinVert = (int)((Forma)SelectedForms.get(i)).TopLeft.y;
        }
        
        int largura= MaxHoriz-MinHoriz+100;
        int altura = MaxVert-MinVert+50;
        /////////////////////////
        
        BufferedImage image = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setClip( 0, 0, largura, altura );
        g2d.setColor(DrawArea.getBackground());
        g2d.fillRect(0, 0, largura, altura );
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((ScrollArea)DrawArea).getAlphaComposite() ) );
        g2d.setFont( ((ScrollArea)DrawArea).getFont() );
        TranslateSelectedForms( 50-MinHoriz , 25-MinVert);
        
        for(int i=0; i<SelectedForms.size(); i++){
            ((Forma)(SelectedForms.get(i))).Draw(g2d);
            try {
                if (  SelectedForms.contains( ((Forma)(SelectedForms.get(i))).GetNext() ) )
                    ((Forma)(SelectedForms.get(i))).NextConector().DrawConector(g2d,true);
                if (  SelectedForms.contains( ((Forma)(SelectedForms.get(i))).GetIfTrue() ) )
                    ((Forma)(SelectedForms.get(i))).IfTrueConector().DrawConector(g2d,true);
                if (  SelectedForms.contains( ((Forma)(SelectedForms.get(i))).GetIfFalse() ) )
                    ((Forma)(SelectedForms.get(i))).IfFalseConector().DrawConector(g2d,true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            g2d.setColor( ((ScrollArea)DrawArea).getFontColor() );
            ((Forma)(SelectedForms.get(i))).DrawSimpleText(g2d);
        }
        TranslateSelectedForms( MinHoriz-50 , MinVert-25);
        
        g2d.dispose();
        
        
        try{
            
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            
            FileFilter JPG = new JpegFileFilter();
            FileFilter PNG = new PngFileFilter();
            FileFilter BMP = new BmpFileFilter();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(PNG);
            chooser.addChoosableFileFilter(JPG);
            chooser.addChoosableFileFilter(BMP);
            chooser.setFileFilter(PNG);
            
            // Show dialog; this method does not return until dialog is closed
            int returnValue = chooser.showSaveDialog(this);
            if( (returnValue == chooser.ERROR_OPTION ) || (returnValue==chooser.CANCEL_OPTION)  )
                return;
            
            String ImagePath = chooser.getSelectedFile().getPath();
            if (ImagePath == null) return;
            
            if ( ! chooser.getFileFilter().accept( chooser.getSelectedFile() ) ){
                
                if ( chooser.getFileFilter() instanceof PngFileFilter )
                    ImagePath += ".png";
                if ( chooser.getFileFilter() instanceof JpegFileFilter )
                    ImagePath += ".jpg";
                if ( chooser.getFileFilter() instanceof BmpFileFilter )
                    ImagePath += ".bmp";
                
            }
            
            ImageIO.write(image, chooser.getFileFilter().toString(), new File( ImagePath ));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        DrawArea.repaint();
        
        
    }//GEN-LAST:event_MenuItemCaptureSelectedActionPerformed
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
// TODO add your handling code here:
        
        if (SelectedForms.size()==1){
            scrollPane1.getHorizontalScrollBar().setValue( (int)(((Forma)SelectedForms.get(0)).Center.x-(scrollPane1.getWidth()/2)) );
            scrollPane1.getVerticalScrollBar().setValue( (int)(((Forma)SelectedForms.get(0)).Center.y-(scrollPane1.getHeight()/2)) );
            helpFluxo = new WWWHelpText("VisualFluxogram/help/"+((Forma)SelectedForms.get(0)).getClass().getSimpleName()+".html");
        }else
            helpFluxo = new WWWHelpText("VisualFluxogram/help/index.html");
        tpUnderFluxogramEditor.setSelectedIndex(3);
        jSplitPane1.setDividerLocation(.3);
        this.spHelp.add(helpFluxo);
        this.spHelp.setViewportView(helpFluxo);
        
    }//GEN-LAST:event_jMenuItem4ActionPerformed
    
    private void MenuItemPararExecucaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPararExecucaoActionPerformed
// TODO add your handling code here:
        if ( Execute != null ){
            
            console.write("\n-------------------------------------");
            console.write("\nprograma abortado pelo utilizador");
            if ( autoExecute != null && autoExecute.isAlive() ){
                autoExecute.stop();
                autoExecute = null;
            }
            Execute = null;
            ((ScrollArea)DrawArea).setSelected(Execute);
            DrawArea.repaint();
        }
    }//GEN-LAST:event_MenuItemPararExecucaoActionPerformed
        
    private void btRunStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRunStepActionPerformed
// TODO add your handling code here:
        MenuItemExecutarPassoActionPerformed(evt);
    }//GEN-LAST:event_btRunStepActionPerformed
    
    private void ErrListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ErrListItemStateChanged
// TODO add your handling code here:
        SelectedForms.clear();
        if (ErrList.getSelectedIndex()!=-1 && ErrForms.size()>ErrList.getSelectedIndex()){
            Forma SelectedError=(Forma)ErrForms.get(ErrList.getSelectedIndex());
            SelectForm = AllForms.indexOf(SelectedError);
            
            ((ScrollArea)DrawArea).setSelected( SelectedError );
            scrollPane1.getHorizontalScrollBar().setValue( (int)(SelectedError.Center.x-(scrollPane1.getWidth()/2)) );
            scrollPane1.getVerticalScrollBar().setValue( (int)(SelectedError.Center.y-(scrollPane1.getHeight()/2)) );
            DrawArea.repaint();
        }
    }//GEN-LAST:event_ErrListItemStateChanged
    
    private void MenuItemCaptureAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCaptureAllActionPerformed
// TODO add your handling code here:
        
        boolean opaqueValue = DrawArea.isOpaque();
        DrawArea.setOpaque( true );
        BufferedImage image = new BufferedImage(DrawArea.getWidth(), DrawArea.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setClip( 0, 0, DrawArea.getWidth(), DrawArea.getHeight() );
        g2d.setColor(DrawArea.getBackground());
        g2d.fillRect(0, 0, DrawArea.getWidth(), DrawArea.getHeight() );
        
        DrawArea.paint( g2d );
        g2d.dispose();
        DrawArea.setOpaque( opaqueValue );
        
        
        try{
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            
            FileFilter JPG = new JpegFileFilter();
            FileFilter PNG = new PngFileFilter();
            FileFilter BMP = new BmpFileFilter();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(PNG);
            chooser.addChoosableFileFilter(JPG);
            chooser.addChoosableFileFilter(BMP);
            chooser.setFileFilter(PNG);
            //chooser.setFileFilter(new FluxogramFileFilter());
            // Show dialog; this method does not return until dialog is closed
            int returnValue = chooser.showSaveDialog(this);
            if( (returnValue == chooser.ERROR_OPTION ) || (returnValue==chooser.CANCEL_OPTION)  )
                return;
            
            String ImagePath = chooser.getSelectedFile().getPath();
            if (ImagePath == null) return;
            
            if ( ! chooser.getFileFilter().accept( chooser.getSelectedFile() ) ){
                
                if ( chooser.getFileFilter() instanceof PngFileFilter )
                    ImagePath += ".png";
                if ( chooser.getFileFilter() instanceof JpegFileFilter )
                    ImagePath += ".jpg";
                if ( chooser.getFileFilter() instanceof BmpFileFilter )
                    ImagePath += ".bmp";
                
            }
            
            ImageIO.write(image, chooser.getFileFilter().toString(), new File( ImagePath ));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_MenuItemCaptureAllActionPerformed
    
    private void MenuItemT1trasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemT1trasActionPerformed
// TODO add your handling code here:
        //colocar uma posicao acima no vector
        int index;
        for (int j=0;j<SelectedForms.size();j++){
            index=AllForms.indexOf(SelectedForms.get(j));
            if (index>0){
                AllForms.setElementAt(AllForms.get(index-1), index);
                AllForms.setElementAt(SelectedForms.get(j), index-1);
            }
        }
        DrawArea.repaint();
    }//GEN-LAST:event_MenuItemT1trasActionPerformed
    
    private void MenuItemT1frenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemT1frenteActionPerformed
// TODO add your handling code here:
        //colocar uma posicao abaixo no vector
        int index;
        for (int j=0;j<SelectedForms.size();j++){
            index=AllForms.indexOf(SelectedForms.get(j));
            if (index<AllForms.size()-1){
                AllForms.setElementAt(AllForms.get(index+1), index);
                AllForms.setElementAt(SelectedForms.get(j), index+1);
            }
        }
        DrawArea.repaint();
    }//GEN-LAST:event_MenuItemT1frenteActionPerformed
    
    private void MenuItemTtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemTtrasActionPerformed
// TODO add your handling code here:
        for (int j=0;j<SelectedForms.size();j++){
            AllForms.remove(SelectedForms.get(j));
            AllForms.add(0, SelectedForms.get(j));
        }
        DrawArea.repaint();
    }//GEN-LAST:event_MenuItemTtrasActionPerformed
    
    private void MenuItemTfrenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemTfrenteActionPerformed
// TODO add your handling code here:
        //colocar no final do vector
        for (int j=0;j<SelectedForms.size();j++){
            AllForms.remove(SelectedForms.get(j));
            AllForms.add(SelectedForms.get(j));
        }
        DrawArea.repaint();
    }//GEN-LAST:event_MenuItemTfrenteActionPerformed
    
    private void MenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemDeleteActionPerformed
// TODO add your handling code here:
        MenuItemApagarActionPerformed(evt);
    }//GEN-LAST:event_MenuItemDeleteActionPerformed
    
    private void MenuItemPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPasteActionPerformed
// TODO add your handling code here:
        MenuItemColarActionPerformed(evt);
    }//GEN-LAST:event_MenuItemPasteActionPerformed
    
    private void MenuItemCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCopyActionPerformed
// TODO add your handling code here:
        MenuItemCopiarActionPerformed(evt);
    }//GEN-LAST:event_MenuItemCopyActionPerformed
    
    private void MenuItemCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCutActionPerformed
// TODO add your handling code here:
        MenuItemCortarActionPerformed(evt);
    }//GEN-LAST:event_MenuItemCutActionPerformed
    
    private void MenuOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuOrderActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_MenuOrderActionPerformed
    
    private void MenuItemCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCompilarActionPerformed
// TODO add your handling code here:
        SelectedForms.clear();
        DrawArea.repaint();
        ((ScrollArea)DrawArea).setSelected(null);
        txtCodePortugol.setText("");
        Execute = null;
        forma = null;
        try {
            MakeCode code = new MakeCode(AllForms);
            forma = code.WriteCodeWithParse(ErrList, ErrForms);
            txtCodePortugol.setText( code.getCode() );
            memory = code.getMemory();
        } catch (Exception e) {
            txtCodePortugol.setText( "Foram encontrados erros no fluxograma\n" );
            txtCodePortugol.append("ERRO: "+e.getMessage());
            ErrTextField.setText( "ERRO: "+e.getMessage() );
        }
        
        ((ScrollArea)DrawArea).setSelectedColor( new Color(221,0,0) );
        if (forma==null){
            txtCodePortugol.setText( "Foram encontrados erros no fluxograma" );
            tpUnderFluxogramEditor.setSelectedIndex(1);
        }else
            tpUnderFluxogramEditor.setSelectedIndex(2);
        jSplitPane1.setDividerLocation(.7);
        
    }//GEN-LAST:event_MenuItemCompilarActionPerformed
    
    private void MenuItemSelecionarTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemSelecionarTudoActionPerformed
// TODO add your handling code here:
        SelectedForms.clear();
        for (int i=0;i<AllForms.size();i++)
            SelectedForms.add(AllForms.get(i));
        DrawArea.repaint();
    }//GEN-LAST:event_MenuItemSelecionarTudoActionPerformed
    
    private void MenuItemColarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemColarActionPerformed
// TODO add your handling code here:
        SelectedForms.clear();
        for (int i=0;i<ClipBoard.size();i++){
            ((Forma)ClipBoard.get(i)).Translate(15, 15);
            ((Forma)ClipBoard.get(i)).TranslateConectors(15, 15);
            AllForms.add(((Forma)ClipBoard.get(i)));
            SelectedForms.add(AllForms.lastElement());
        }
        
        MenuItemCopiarActionPerformed(evt);
        DrawArea.repaint();
        
    }//GEN-LAST:event_MenuItemColarActionPerformed
    
    private void MenuItemApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemApagarActionPerformed
// TODO add your handling code here:
        
        ErrList.removeAll();
        ErrForms.clear();
        ((ScrollArea)DrawArea).setSelected(null);
        //eliminar conectores ligados ás AllForms selecionadas
        for(int i=0; i<AllForms.size(); i++)
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)AllForms.get(i);
            if ( f.GetIfTrue()==SelectedForms.get(j) )
                f.DeleteIfTrueConector();
            if ( f.GetIfFalse() == SelectedForms.get(j) )
                f.DeleteIfFalseConector();
            if ( f.GetNext() == SelectedForms.get(j) )
                f.DeleteNextConector();
            }
        
        //libertar posicoes bloqueadas nas outras figuras
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null )
                f.DeleteNextConector();
            if ( f.IfTrueConector() != null )
                f.DeleteIfTrueConector();
            if ( f.IfFalseConector() != null )
                f.DeleteIfFalseConector();
        }
        
        //apagar figuras
        for (int j=0;j<SelectedForms.size();j++)
            AllForms.remove(SelectedForms.get(j));
        
        SelectedForms.clear();
        forma=null;
        SelectForm=-1;
        
        if (evt != null)
            DrawArea.repaint();
    }//GEN-LAST:event_MenuItemApagarActionPerformed
    
    private void MenuItemCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCopiarActionPerformed
// TODO add your handling code here:
        ClipBoard.clear();
        for (int i=0;i<SelectedForms.size();i++)
            ClipBoard.add(((Forma)SelectedForms.get(i)).clone());
        
        int index = -1;
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            
            index = SelectedForms.indexOf( f.GetNext() );
            if ( index!=-1 )
                ((Forma)ClipBoard.get(i)).CreateNextConector( (Forma)ClipBoard.get(index),
                        f.NextConector().InitialPos(), f.NextConector().FinalPos() );
            
            index = SelectedForms.indexOf( f.GetIfTrue() );
            if ( index!=-1 )
                ((Forma)ClipBoard.get(i)).CreateIfTrueConector( (Forma)ClipBoard.get(index),
                        f.IfTrueConector().InitialPos(), f.IfTrueConector().FinalPos() );
            
            index = SelectedForms.indexOf( f.GetIfFalse() );
            if ( index!=-1 )
                ((Forma)ClipBoard.get(i)).CreateIfFalseConector( (Forma)ClipBoard.get(index),
                        f.IfFalseConector().InitialPos(), f.IfFalseConector().FinalPos() );
            
        }
        
    }//GEN-LAST:event_MenuItemCopiarActionPerformed
    
    private void MenuItemCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemCortarActionPerformed
// TODO add your handling code here:
        MenuItemCopiarActionPerformed(evt);
        MenuItemApagarActionPerformed(evt);
        
    }//GEN-LAST:event_MenuItemCortarActionPerformed
    
    private void FontStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FontStyleActionPerformed
// TODO add your handling code here:
        
    }//GEN-LAST:event_FontStyleActionPerformed
    
    private void FontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FontSizeActionPerformed
// TODO add your handling code here:
        
    }//GEN-LAST:event_FontSizeActionPerformed
    
    private void FontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FontColorActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor do Texto",
                FontColor.getBackground());
        if (bgColor != null)
            FontColor.setBackground(bgColor);
    }//GEN-LAST:event_FontColorActionPerformed
    
    private void SaidaDecisaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaidaDecisaoActionPerformed
// TODO add your handling code here:
        if (SaidaDecisao.getText().equals("SIM"))
            SaidaDecisao.setText("NAO");
        else
            SaidaDecisao.setText("SIM");
    }//GEN-LAST:event_SaidaDecisaoActionPerformed
    
    private void ClosestConectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClosestConectorActionPerformed
// TODO add your handling code here:
        if (ClosestConector.isSelected()){
            ManualConector.setSelected(false);
            AutoConector.setSelected(false);
        }else
            ClosestConector.setSelected(true);
    }//GEN-LAST:event_ClosestConectorActionPerformed
    
    private void ManualConectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManualConectorActionPerformed
// TODO add your handling code here:
        if (ManualConector.isSelected()){
            AutoConector.setSelected(false);
            ClosestConector.setSelected(false);
        }else
            ManualConector.setSelected(true);
    }//GEN-LAST:event_ManualConectorActionPerformed
    
    private void ClosestConectorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ClosestConectorStateChanged
// TODO add your handling code here:
        
    }//GEN-LAST:event_ClosestConectorStateChanged
    
    private void AutoConectorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AutoConectorStateChanged
// TODO add your handling code here:
    }//GEN-LAST:event_AutoConectorStateChanged
    
    private void ManualConectorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ManualConectorStateChanged
// TODO add your handling code here:
        
    }//GEN-LAST:event_ManualConectorStateChanged
    
    private void EixoVertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EixoVertActionPerformed
// TODO add your handling code here:
        EixoHorizActionPerformed(evt);
    }//GEN-LAST:event_EixoVertActionPerformed
    
    private void EixoHorizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EixoHorizActionPerformed
// TODO add your handling code here:
        
        
    }//GEN-LAST:event_EixoHorizActionPerformed
    
    private void GrelhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrelhaActionPerformed
// TODO add your handling code here:
        
    }//GEN-LAST:event_GrelhaActionPerformed
    
    private void TransparenciaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransparenciaFieldActionPerformed
// TODO add your handling code here:
        
    }//GEN-LAST:event_TransparenciaFieldActionPerformed
    
    private void TransparenciaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TransparenciaSliderStateChanged
// TODO add your handling code here:
        TransparenciaField.setText(""+TransparenciaSlider.getValue()+"%");
    }//GEN-LAST:event_TransparenciaSliderStateChanged
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        for(int i=0;i<AllForms.size();i++){
            if ( AllForms.get(i) instanceof Terminador)
                ((Forma)AllForms.get(i)).FormColor=TerminadorColorBtn.getBackground();
            else if ( AllForms.get(i) instanceof Conexao)
                ((Forma)AllForms.get(i)).FormColor=ConexaoColorBtn.getBackground();
            else if ( AllForms.get(i) instanceof Leitura)
                ((Forma)AllForms.get(i)).FormColor=LeituraColorBtn.getBackground();
            else if ( AllForms.get(i) instanceof Escrita)
                ((Forma)AllForms.get(i)).FormColor=EscritaColorBtn.getBackground();
            else if ( AllForms.get(i) instanceof Processo)
                ((Forma)AllForms.get(i)).FormColor=ProcessoColorBtn.getBackground();
            else if ( AllForms.get(i) instanceof Decisao)
                ((Forma)AllForms.get(i)).FormColor=DecisaoColorBtn.getBackground();
        }
        DrawArea.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void BackgroundColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackgroundColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor de fundo",
                DrawArea.getBackground());
        if (bgColor != null)
            BackgroundColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_BackgroundColorBtnActionPerformed
    
    private void ProcessoColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProcessoColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor da figura Processo",
                ProcessoColorBtn.getBackground());
        if (bgColor != null)
            ProcessoColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_ProcessoColorBtnActionPerformed
    
    private void DecisaoColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DecisaoColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor da figura Decisao",
                DecisaoColorBtn.getBackground());
        if (bgColor != null)
            DecisaoColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_DecisaoColorBtnActionPerformed
    
    private void EscritaColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EscritaColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor da figura Escrita",
                EscritaColorBtn.getBackground());
        if (bgColor != null)
            EscritaColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_EscritaColorBtnActionPerformed
    
    private void LeituraColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeituraColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor da figura Leitura",
                LeituraColorBtn.getBackground());
        if (bgColor != null)
            LeituraColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_LeituraColorBtnActionPerformed
    
    private void ConexaoColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConexaoColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor da figura Conexao",
                ConexaoColorBtn.getBackground());
        if (bgColor != null)
            ConexaoColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_ConexaoColorBtnActionPerformed
    
    private void TerminadorColorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminadorColorBtnActionPerformed
// TODO add your handling code here:
        Color bgColor
                = JColorChooser.showDialog(this,
                "Escolher cor da figura Terminacao",
                TerminadorColorBtn.getBackground());
        if (bgColor != null)
            TerminadorColorBtn.setBackground(bgColor);
    }//GEN-LAST:event_TerminadorColorBtnActionPerformed
    
    private void OptionsWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OptionsWindowClosing
// TODO add your handling code here:
        try{
            int largura = Integer.parseInt( Largura.getText() );
            int altura = Integer.parseInt( Altura.getText() );
            
            if (largura<scrollPane1.getWidth())
                largura=scrollPane1.getWidth()-3;
            if (altura<scrollPane1.getHeight())
                altura=scrollPane1.getHeight()-3;
            
            DrawArea.setPreferredSize(new Dimension(largura, altura));
            DrawArea.setSize(largura, altura);
            scrollPane1.doLayout();
            
        }catch(NumberFormatException e){
        }
        
        EixoHorizActionPerformed(null);
        FontFamilyNameActionPerformed(null);
        
        /*
        if ( Largura.getText()!=DrawArea.getPreferredSize().width || Altura!=DrawArea.getPreferredSize().height ){
            DrawArea.getPreferredSize().width=Largura;
            DrawArea.getPreferredSize().height=Altura;
            this.scrollPane1.doLayout();
        }*/
        //DrawArea.setPreferredSize(new Dime)
        //if (Largura)
    }//GEN-LAST:event_OptionsWindowClosing
    
    private void EspacamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EspacamentoActionPerformed
// TODO add your handling code here:
        if (Espacamento.isSelected()){
            Grelha.setEnabled(true);
            EixoHoriz.setEditable(true);
            EixoVert.setEditable(true);
            jLabel25.setEnabled(true);
            jLabel26.setEnabled(true);
        }else{
            Grelha.setEnabled(false);
            EixoHoriz.setEditable(false);
            EixoVert.setEditable(false);
            jLabel25.setEnabled(false);
            jLabel26.setEnabled(false);
        }
    }//GEN-LAST:event_EspacamentoActionPerformed
    
    private void FontFamilyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FontFamilyNameActionPerformed
// TODO add your handling code here:
        
    }//GEN-LAST:event_FontFamilyNameActionPerformed
    
    private void AutoConectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutoConectorActionPerformed
// TODO add your handling code here:
        if (AutoConector.isSelected()){
            ManualConector.setSelected(false);
            ClosestConector.setSelected(false);
        }else
            AutoConector.setSelected(true);
    }//GEN-LAST:event_AutoConectorActionPerformed
    
    private void MenuItemSetTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemSetTextActionPerformed
// TODO add your handling code here:
        if (SelectForm!=-1){
            if (AllForms.get(SelectForm) instanceof Terminador){
                if ( ((Forma)AllForms.get(SelectForm)).Text.equalsIgnoreCase("Inicio"))
                    ((Forma)AllForms.get(SelectForm)).Text="Fim";
                else
                    ((Forma)AllForms.get(SelectForm)).Text="Inicio";
            }else if ( !(AllForms.get(SelectForm) instanceof Conexao) ){
                String text=null;
                text=JOptionPane.showInputDialog(this, "Texto:", ((Forma)AllForms.get(SelectForm)).Text);
                if (text!=null)
                    ((Forma)AllForms.get(SelectForm)).Text=text;
            }
            DrawArea.repaint();
        }
    }//GEN-LAST:event_MenuItemSetTextActionPerformed
    
    private void MenuItemResizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemResizeActionPerformed
// TODO add your handling code here:
        
        for (int i=0;i<SelectedForms.size();i++){
            if ( !((Forma)SelectedForms.get(i)).Text.equals("") )
                ((Forma)SelectedForms.get(i)).ResizeFromText(g, ((ScrollArea)DrawArea).getFont());
            DrawArea.repaint();
        }
        
        GetLinksToSelectedForms();
        //desenhar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null )
                f.NextConector().CalcConector();
            if ( f.IfTrueConector() != null )
                f.IfTrueConector().CalcConector();
            if ( f.IfFalseConector() != null )
                f.IfFalseConector().CalcConector();
        }
        
        //desenhar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) )
                f.NextConector().CalcConector();
            if ( f.GetIfTrue() == SelectedForms.get(j) )
                f.IfTrueConector().CalcConector();
            if ( f.GetIfFalse() == SelectedForms.get(j) )
                f.IfFalseConector().CalcConector();
            }
        
        if (Espacamento.isSelected())
            CalcEspacamento();
        
    }//GEN-LAST:event_MenuItemResizeActionPerformed
    
    private void MenuItemSetColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemSetColorActionPerformed
// TODO add your handling code here:
        if (!SelectedForms.isEmpty()){
            
            Color bgColor
                    = JColorChooser.showDialog(this, "Escolher cor da figura",
                    SelectedForms.size()==1? ((Forma)SelectedForms.get(0)).FormColor : null);
            
            if (bgColor != null)
                for (int i=0;i<SelectedForms.size();i++)
                    ((Forma)SelectedForms.get(i)).FormColor=bgColor;
            DrawArea.repaint();
        }
    }//GEN-LAST:event_MenuItemSetColorActionPerformed
    
    private void MenuItemSetColorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MenuItemSetColorItemStateChanged
// TODO add your handling code here:
        System.out.println("ok");
        Color bgColor
                = JColorChooser.showDialog(this,
                "Choose Background Color",
                getBackground());
        if (bgColor != null)
            DrawArea.setBackground(bgColor);
    }//GEN-LAST:event_MenuItemSetColorItemStateChanged
    
    private void MenuItemSetColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuItemSetColorKeyPressed
// TODO add your handling code here:
        System.out.println("ok");
        Color bgColor
                = JColorChooser.showDialog(this,
                "Choose Background Color",
                getBackground());
        if (bgColor != null)
            DrawArea.setBackground(bgColor);
    }//GEN-LAST:event_MenuItemSetColorKeyPressed
    
    private void DrawAreaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DrawAreaMouseMoved
// TODO add your handling code here:
        if (Action==-1)
            DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        else if (Action==-2){
            
            if (!isCalcConector){
                //======================================================================
                //criar conector--------------------------------------------------------
                DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                int aux=-1;
                for(int i=0; i<AllForms.size(); i++){
                    Forma f=(Forma)AllForms.get(i);
                    if ( (f.Contains(evt.getPoint()) || f.ContainsExtremes(evt.getPoint())) &&
                            f.ValidExtremes() )
                        
                        if ( f instanceof Decisao ){
                        if ( f.IfTrueConector()==null || f.IfFalseConector()==null )
                            aux=i;
                        }else if ( f.NextConector()==null )
                            if ( !(f instanceof Terminador) || (f.Text.equals("Inicio")) )
                                aux=i;
                    
                }
                
                g=(Graphics2D)DrawArea.getGraphics();
                //Mudar modo de desenho para XORMode
                g.setXORMode(Color.white);
                g.setColor(Color.black);
                
                if (aux!=SelectForm && aux!=-1){
                    if (SelectForm!=-1)
                        forma.DrawExtremes(g);
                    if (aux!=-1){
                        forma=((Forma)(AllForms.get(aux)));
                        forma.DrawExtremes(g);
                    }else
                        forma=null;
                    SelectForm=aux;
                }
                
            }else{
                
                DrawAreaMouseDragged(evt);
                return;
            }
            
            
        }else if(Action==0){
            
            DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            SelectForm=-1;
            for(int i=0; i<AllForms.size(); i++){
                if (((Forma)(AllForms.get(i))).Contains(evt.getPoint())){
                    DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    SelectForm=i;
                }
            }
            
            for (int i=0;i<SelectedForms.size();i++){
                forma=(Forma)SelectedForms.get(i);
                if (evt.getPoint().distance(forma.Top())<5 || evt.getPoint().distance(forma.Botton())<5){
                    DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    return;
                }else if (evt.getPoint().distance(forma.Left())<5 || evt.getPoint().distance(forma.Right())<5){
                    DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    return;
                }else if (evt.getPoint().distance(forma.TopLeft())<5 || evt.getPoint().distance(forma.BottonRight())<5){
                    DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                    return;
                }else if (evt.getPoint().distance(forma.TopRight())<5 || evt.getPoint().distance(forma.BottonLeft())<5){
                    DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                    return;
                }else
                    forma=null;
            }
        }
    }//GEN-LAST:event_DrawAreaMouseMoved
    
    private void DrawAreaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DrawAreaMouseDragged
// TODO add your handling code here:
        
        //verificar se foi o botao esquerdo do rato que foi pressionado
        if ( (evt.getModifiers() & InputEvent.BUTTON1_MASK) == 0 && !isCalcConector)
            return;
        
        if (evt.getClickCount()==2 || Action==-3)
            return;
        
        //======================================================================
        //desenhar caixa de seleccao--------------------------------------------
        if (Action==0){
            //apagar caixa desenahda anteriormente
            g.drawRect(Math.min(orig.x, TranslatePt.x), Math.min(orig.y, TranslatePt.y),
                    Math.abs(TranslatePt.x-orig.x), Math.abs(TranslatePt.y-orig.y));
            //desenhar nova caixa
            g.drawRect(Math.min(orig.x, evt.getPoint().x), Math.min(orig.y, evt.getPoint().y),
                    Math.abs(evt.getPoint().x-orig.x), Math.abs(evt.getPoint().y-orig.y));
            //guardar nova posicao usada
            TranslatePt=evt.getPoint();
            return;
        }
        
        //======================================================================
        //criar conector--------------------------------------------------------
        if (Action==-2){
            //apagar conector
            if (!isCalcConector){
                
                ConectorInCalc.DrawConector(g);
                
                //apagar extremos da fig selecionada
                dest.DrawExtremes(g);
                
            }else
                DrawArea.repaint();
            
            
            //procurar fig selecionada
            for(int i=0; i<AllForms.size(); i++)
                if (( ((Forma)(AllForms.get(i))).Contains(evt.getPoint()) ||
                    ((Forma)(AllForms.get(i))).ContainsExtremes(evt.getPoint()) ) &&
                    ((Forma)(AllForms.get(i))).ValidExtremes())
                    dest=(Forma)AllForms.get(i);
            
            //desenhar extremos da fig selecionada
            if (!isCalcConector)
                dest.DrawExtremes(g);
            
            
            //calcular ponto + proximo ,conector e desenhar
            ConectorInCalc.SetPointer(dest);
            if (AutoConector.isSelected())
                ConectorInCalc.AutoSetFinalExtreme();
            else if (ClosestConector.isSelected())
                ConectorInCalc.SetClosestExtremes();
            else
                ConectorInCalc.SetFinalClosestExtreme(evt.getPoint());
            
            if (!isCalcConector)
                ConectorInCalc.CalcDrawConector(g);
            else
                ConectorInCalc.CalcConector();
            
            
        }else{
            
            if (Action==1){
                //==================================================================
                //transladar--------------------------------------------------------
                
                //apagar conectores
                if (ClosestConector.isSelected())
                    ClearClosestConectors();
                else
                    ClearConectors();
                
                //transladar AllForms selecionadas
                for (int i=0;i<SelectedForms.size();i++)
                    ((Forma)SelectedForms.get(i)).Translate(evt.getPoint().x-TranslatePt.x, evt.getPoint().y-TranslatePt.y);
                
                
                
                //calcular e desenhar conectores
                if (ClosestConector.isSelected())
                    DrawCalcClosestConectors();
                else
                    DrawCalcConectors();
                
                TranslatePt=evt.getPoint();
                
            }else{
                //==================================================================
                //resize------------------------------------------------------------
                
                //apagar figura
                if (Action==-1)
                    Action=9;
                else
                    //apagar conectores
                    if (ClosestConector.isSelected())
                        ClearClosestConectors();
                    else
                        ClearConectors();
                
                
                int dy=0;
                int dx=0;
                //calcular deformacoes a usar em relacao a figura inicial
                if (Action==2 || Action==6 || Action==7)
                    dy=evt.getPoint().y-forma.TopLeft().y;
                if (Action==3 || Action==8 || Action==9)
                    dy=evt.getPoint().y-forma.BottonRight().y;
                if (Action==4 || Action==6 || Action==8)
                    dx=evt.getPoint().x-forma.TopLeft().x;
                if (Action==5 || Action==7 || Action==9)
                    dx=evt.getPoint().x-forma.BottonRight().x;
                
                //efectuar accao na figuras
                for (int i=0;i<SelectedForms.size();i++){
                    
                    if (ActionSelector[i]==2)
                        ((Forma)SelectedForms.get(i)).ExtendTop(dy);
                    else if (ActionSelector[i]==3)
                        ((Forma)SelectedForms.get(i)).ExtendBotton(dy);
                    else if (ActionSelector[i]==4)
                        ((Forma)SelectedForms.get(i)).ExtendLeft(dx);
                    else if (ActionSelector[i]==5)
                        ((Forma)SelectedForms.get(i)).ExtendRight(dx);
                    
                    else if (ActionSelector[i]==6)
                        ((Forma)SelectedForms.get(i)).ExtendTopLeft(dx, dy);
                    else if (ActionSelector[i]==7)
                        ((Forma)SelectedForms.get(i)).ExtendTopRight(dx, dy);
                    else if (ActionSelector[i]==8)
                        ((Forma)SelectedForms.get(i)).ExtendBottonLeft(dx, dy);
                    else if (ActionSelector[i]==9)
                        ((Forma)SelectedForms.get(i)).ExtendBottonRight(dx, dy);
                    
                    //colocar a figura com posicoes correctas e guardar accao da figura
                    ActionSelector[i]=CalcAction_RightPosition((Forma)SelectedForms.get(i), ActionSelector[i]);
                    if (SelectedForms.get(i)==forma)
                        Action=ActionSelector[i];
                }
                
                //calcular e desenhar conectores
                if (ClosestConector.isSelected())
                    DrawCalcClosestConectors();
                else
                    DrawCalcConectors();
                
                
            }
            
        }
    }//GEN-LAST:event_DrawAreaMouseDragged
    
    private void DrawAreaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DrawAreaMouseReleased
// TODO add your handling code here:
        scrollPane1.setWheelScrollingEnabled(true);
        
        if (evt.getButton()!=evt.BUTTON1 && !isCalcConector)
            return;
        
        if (evt.getClickCount()==2){
            DrawArea.repaint();
            return;
        }
        
        //======================================================================
        //criar conector--------------------------------------------------------
        if ( (Action==-2 && System.currentTimeMillis()-InitialPress>100) || isCalcConector ){
            
            boolean err=false;
            
            if (forma instanceof Decisao){
                if ( forma.GetIfFalse()==forma.GetIfTrue() ){
                    ErrTextField.setText("Erro: conectores de falso e verdadeiro nao podem apontar para a mesma figura");
                    err=true;
                }/*else if ( !isIfTrueInCalc && forma.GetIfTrue()==dest ){
                    ErrTextField.setText("Erro: conectores de falso e verdadeiro nao podem apontar para a mesma figura");
                    err=true;
                }*/
            }
            
            if (dest instanceof Terminador && dest.Text.equals("Inicio")){
                ErrTextField.setText("Erro: nao podem existir apontadores para a figura de inicio");
                err=true;
            }
            
            if (dest==forma){
                ErrTextField.setText("Aviso: conector apagado");
                err=true;
            }
            
            if (!(dest instanceof Conexao) && !(dest instanceof Decisao))
                for(int i=0; i<AllForms.size(); i++)
                    if ( ((Forma)(AllForms.get(i)))!=forma )
                        if ( ((Forma)(AllForms.get(i))).GetNext()==dest  ||
                    ((Forma)(AllForms.get(i))).GetIfTrue()==dest  ||
                    ((Forma)(AllForms.get(i))).GetIfFalse()==dest  ){
                ErrTextField.setText("Erro: nao é posivel ter mais de um conector a apontar para a figura");
                err=true;
                        }
            
            
            if (forma instanceof Decisao)
                if ( isIfTrueInCalc ){
                if (!err)
                    forma.IfTrueConector().BlockConector();
                else
                    forma.DeleteIfTrueConector();
                }else{
                if (!err)
                    forma.IfFalseConector().BlockConector();
                else
                    forma.DeleteIfFalseConector();
                } else
                    if (!err)
                        forma.NextConector().BlockConector();
                    else
                        forma.DeleteNextConector();
            
            SaidaDecisao.setText("SIM");
            isCalcConector=false;
            ConectorInCalc = null;
            
            //ConectorBtnItemStateChanged(null);
            //return;
            
        }else if (Action==-2){
            isCalcConector=true;
            return;
            
        }else if ( Action!=-2 && System.currentTimeMillis()-InitialPress<200 )
            if (forma != null){
            forma.ExtendBottonRight( (int)(orig.x-forma.BottonRight.x), (int)(orig.y-forma.BottonRight.y) );
            forma.CalcRightPosition();
            //TranslatePt=forma.BottonRight.toPoint();
            
            
            
            forma.Translate((int)(forma.TopLeft.x-forma.Center.x), (int)(forma.TopLeft.y-forma.Center.y));
            }
        
        
        //-----------------------------------------
        
        if (Action==0){
            //verificar figura contidas na caixa e adicionar as figuras selecionadas
            if (!evt.isControlDown())
                SelectedForms.clear();
            Rectangle area=new Rectangle(Math.min(orig.x, evt.getPoint().x), Math.min(orig.y, evt.getPoint().y),
                    Math.abs(evt.getPoint().x-orig.x), Math.abs(evt.getPoint().y-orig.y));
            for(int i=0; i<AllForms.size();i++)
                if (area.contains(((Forma)AllForms.get(i)).GetBounds()))
                    if (!SelectedForms.contains(AllForms.get(i)))
                        SelectedForms.add(AllForms.get(i));
                    else
                        SelectedForms.remove(AllForms.get(i));
        }
        
        if (Espacamento.isSelected())
            CalcEspacamento();
        
        
        //desactivar botoes
        DecisaoBtn.setSelected(false);
        ConectorBtn.setSelected(false);
        TerminadorBtn.setSelected(false);
        LeituraBtn.setSelected(false);
        ProcessoBtn.setSelected(false);
        EscritaBtn.setSelected(false);
        ConexaoBtn.setSelected(false);
        
        //predefenir as variaveis
        g.setPaintMode();
        Action=0;
        ConectedForms.clear();
        forma=null;
        DrawArea.repaint();
        DrawAreaMouseMoved(evt);
        
    }//GEN-LAST:event_DrawAreaMouseReleased
    
    private void DrawAreaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DrawAreaMousePressed
// TODO add your handling code here:
        scrollPane1.setWheelScrollingEnabled(false);
        
        if (isCalcConector)
            return;
        
        if (evt.getButton() != evt.BUTTON1){
            if (SelectForm==-1){
                SelectedForms.clear();
                DrawArea.repaint();
            }else{
                if (!SelectedForms.contains(AllForms.get(SelectForm))){
                    SelectedForms.clear();
                    SelectedForms.add(AllForms.get(SelectForm));
                    DrawArea.repaint();
                }
            }
            return;
        }
        
        
        g=(Graphics2D)DrawArea.getGraphics();
        //Mudar modo de desenho para XORMode
        g.setXORMode(Color.white);
        g.setColor(Color.black);
        
        if (evt.getClickCount()==2){
            ProjectChanged=true;
            MenuItemSetTextActionPerformed(null);
            return;
        }
        
        if (Action==-1){
            //======================================================================
            //criar figura---------------------------------------------------------
            if (DecisaoBtn.isSelected())
                forma=new Decisao(evt.getPoint(), "", DecisaoColor);
            
            if (TerminadorBtn.isSelected()){
                forma=new Terminador(evt.getPoint(), true, TerminadorColor);
                for (int i=0;i<AllForms.size();i++)
                    if(AllForms.get(i) instanceof Terminador && ((Forma)AllForms.get(i)).Text.equals("Inicio"))
                        forma.Text="Fim";
            }
            if (LeituraBtn.isSelected())
                forma=new Leitura(evt.getPoint(), "", LeituraColor);
            
            if (ProcessoBtn.isSelected())
                forma=new Processo(evt.getPoint(), "", ProcessoColor);
            
            if (EscritaBtn.isSelected())
                forma=new Escrita(evt.getPoint(), "", EscritaColor);
            
            if (ConexaoBtn.isSelected())
                forma=new Conexao(evt.getPoint(), ConexaoColor);
            
            InitialPress=System.currentTimeMillis();
            //adicionar figura criada ao vector
            
            orig=forma.BottonRight.toPoint();
            AllForms.add(forma);
            
            //selecionar a figura criada
            SelectedForms.clear();
            SelectedForms.add(forma);
            ActionSelector=new int[1];
            ActionSelector[0]=9;
            
        }else if (Action==-2){
            
            if (!isCalcConector)
                InitialPress=System.currentTimeMillis();
            //======================================================================
            //criar conector--------------------------------------------------------
            
            //remover figuras selecionadas
            SelectedForms.clear();
            //verificar se existe uma figura de partida para fazer o conector
            if (forma==null){
                Action=-3;
                //se nao existir nao faz mais nada
                return;
            }
            
            
            if ( SaidaDecisao.getText().equals("SIM") )
                isIfTrueInCalc=true;
            else
                isIfTrueInCalc=false;
            
            //varificar se existe um conector no lado verdadeiro
            if ( isIfTrueInCalc && forma.IfTrueConector() != null ){
                SaidaDecisao.setText("NAO");
                isIfTrueInCalc=false;
                //se existir entao cria o conector do lado falso
            }else if ( !isIfTrueInCalc && forma.IfFalseConector() != null ){
                SaidaDecisao.setText("SIM");
                isIfTrueInCalc=true;
            }
            
            if (forma instanceof Decisao)
                if ( isIfTrueInCalc ){
                forma.SetIfTrue(forma);
                ConectorInCalc = forma.IfTrueConector();
                }else{
                forma.SetIfFalse(forma);
                ConectorInCalc = forma.IfFalseConector();
                } else{
                forma.SetNext(forma);
                ConectorInCalc = forma.NextConector();
                }
            
            //gravar ponto de partida do conector
            if (AutoConector.isSelected())
                ConectorInCalc.AutoSetInitialExtreme();
            else
                ConectorInCalc.SetInitialClosestExtreme( evt.getPoint() );
            
            ConectorInCalc.CalcDrawConector(g);
            
            
            //figura de destino vair ser igual a de inicio
            dest=forma;
            
        }else{
            //verificar se existe alguma figura para fazer o resize
            if (forma!=null){
                //======================================================================
                //resize figura---------------------------------------------------------
                
                //verificar tipo de resize a efectuar
                if (evt.getPoint().distance(forma.Top())<5)
                    Action=2;
                if (evt.getPoint().distance(forma.Botton())<5)
                    Action=3;
                if (evt.getPoint().distance(forma.Left())<5)
                    Action=4;
                if (evt.getPoint().distance(forma.Right())<5)
                    Action=5;
                if (evt.getPoint().distance(forma.TopLeft())<5)
                    Action=6;
                if (evt.getPoint().distance(forma.TopRight())<5)
                    Action=7;
                if (evt.getPoint().distance(forma.BottonLeft())<5)
                    Action=8;
                if (evt.getPoint().distance(forma.BottonRight())<5)
                    Action=9;
                
                if (Action!=0){
                    //guardar todas a figuras que apontam para as figuras selecionadas
                    GetLinksToSelectedForms();
                    //criar tipos de accoes que cada figura vai efectuar
                    ActionSelector=new int[SelectedForms.size()];
                    for (int i=0;i<SelectedForms.size();i++)
                        ActionSelector[i]=Action;
                    //desenhar figuras usando XORMode para da proxima vez
                    //que forem desenhadas estas serem apagadas
                    ClearConectors();
                }
            }
            if (Action==0)
                //verificar se o cursor esta sobre alguma figura
                if (SelectForm!=-1){
                //==============================================================
                //translate figura----------------------------------------------
                
                //verificar se foi adicionada alguma figura as figuras selecionadas
                if (!SelectedForms.contains(AllForms.get(SelectForm))){
                    if (!evt.isControlDown())
                        SelectedForms.clear();
                    SelectedForms.add(AllForms.get(SelectForm));
                }else if (evt.isControlDown())
                    //se ja existia a figura entao remove-a
                    SelectedForms.remove(AllForms.get(SelectForm));
                
                //paint();//-->((Forma)SelectedForms.get(i)).DrawComponents(g);
                
                //accao para transladar figura
                Action=1;
                //guardar ponto de transladacao
                TranslatePt=evt.getPoint();
                
                //guardar todas a figuras que apontam para as figuras selecionadas
                GetLinksToSelectedForms();
                //desenhar figuras usando XORMode para da proxima vez
                //que forem desenhadas estas serem apagadas
                ClearConectors();
                
                
                }else{
                //==============================================================
                //translate conector--------------------------------------------
                
                ConectorInCalc = null;
                for(int i=0; i<AllForms.size(); i++){
                    forma=(Forma)AllForms.get(i);
                    //se o rato estiver a selecionar 1 conector
                    
                    if ( forma.NextConector() != null && forma.NextConector().Contains(evt.getPoint()) )
                        ConectorInCalc = forma.NextConector();
                    else if ( forma.IfTrueConector() != null && forma.IfTrueConector().Contains(evt.getPoint()) ){
                        ConectorInCalc = forma.IfTrueConector();
                        isIfTrueInCalc = true;
                    } else if ( forma.IfFalseConector() != null && forma.IfFalseConector().Contains(evt.getPoint()) ){
                        ConectorInCalc = forma.IfFalseConector();
                        isIfTrueInCalc = false;
                    }
                    
                    if ( ConectorInCalc != null ){
                        DrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        dest = ConectorInCalc.Pointer();
                        dest.DrawExtremes(g);
                        Action=-2;
                        forma.DrawShadow(g);
                        
                        SelectedForms.clear();
                        SelectedForms.add(forma);
                        
                        ConectorInCalc.DrawConector(g);
                        ConectorInCalc.UnBlockConector();
                        return;
                    }
                }
                ConectorInCalc = null;
                
                }
            
            
            //======================================================================
            //desenhar caixa de seleccao--------------------------------------------
            if (Action==0){
                //guardar posicao inicial da caixa
                TranslatePt=orig=evt.getPoint();
                //g.setXORMode(Color.white);
                //g.setColor(Color.black);
                //desenhar caixa em XORMode
                g.drawRect(Math.min(orig.x, TranslatePt.x), Math.min(orig.y, TranslatePt.y),
                        Math.abs(TranslatePt.x-orig.x), Math.abs(TranslatePt.y-orig.y));
                
            }
        }
        if (!ProjectChanged && Action!=0 && Action!=-3)
            ProjectChanged=true;
    }//GEN-LAST:event_DrawAreaMousePressed
    
    private void jToolPatternsComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jToolPatternsComponentMoved
// TODO add your handling code here:
        //paint();
    }//GEN-LAST:event_jToolPatternsComponentMoved
    
    private void ScaleFactorFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ScaleFactorFieldFocusLost
// TODO add your handling code here:
        ScaleFactorField.setText(Integer.valueOf((int)(ScaleFactor*100.0))+"%");
    }//GEN-LAST:event_ScaleFactorFieldFocusLost
    
    private void ScaleFactorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScaleFactorFieldActionPerformed
// TODO add your handling code here:
        
        double PreviousScale=1.0/ScaleFactor;
        
        String value=ScaleFactorField.getText();
        if (value.endsWith("%"))
            value=value.substring(0,value.length()-1);
        try{
            ScaleFactor=Double.parseDouble(value)/100.0;
            if (ScaleFactor<.15){
                ScaleFactor = .15;
                value = "15";
            }
            ScaleFactorField.setText(value+"%");
        }catch(NumberFormatException e){
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        
        Espacamento.setSelected(false);
        jToggleButton1.setSelected(false);
        EspacamentoActionPerformed(null);
        Forma.ScaleDefaultDis(ScaleFactor);
        
        ((ScrollArea)DrawArea).ScaleFontSize(ScaleFactor);
        
        
        //escalar figuras
        for(int i=0; i<AllForms.size(); i++)
            ((Forma)(AllForms.get(i))).Scale(PreviousScale*ScaleFactor, PreviousScale*ScaleFactor);
        
        
        //calcular conectores que saem das figuras selecionadas
        for (int i=0;i<AllForms.size();i++){
            Forma f = (Forma)AllForms.get(i);
            if ( f.NextConector() != null )
                f.NextConector().CalcConector();
            if ( f.IfTrueConector() != null )
                f.IfTrueConector().CalcConector();
            if ( f.IfFalseConector() != null )
                f.IfFalseConector().CalcConector();
        }
        
        DrawArea.repaint();
        DrawArea.requestFocus();
    }//GEN-LAST:event_ScaleFactorFieldActionPerformed
    
    private void ConectorBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ConectorBtnItemStateChanged
// TODO add your handling code here:
        if (isCalcConector){
            JOptionPane.showMessageDialog(this, "Termine o conector actual", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DrawArea.repaint();
        if (ConectorBtn.isSelected()){
            DecisaoBtn.setSelected(false);
            TerminadorBtn.setSelected(false);
            LeituraBtn.setSelected(false);
            ProcessoBtn.setSelected(false);
            EscritaBtn.setSelected(false);
            ConexaoBtn.setSelected(false);
            forma=null;
            //Mudar modo de desenho para XORMode
            g.setXORMode(Color.white);
            g.setColor(Color.black);
            SelectForm=-1;
            Action=-2;
        }else
            Action=0;
    }//GEN-LAST:event_ConectorBtnItemStateChanged
    
    private void ConexaoBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ConexaoBtnItemStateChanged
// TODO add your handling code here:
        
        DrawArea.repaint();
        if (ConexaoBtn.isSelected()){
            ConectorBtn.setSelected(false);
            TerminadorBtn.setSelected(false);
            LeituraBtn.setSelected(false);
            ProcessoBtn.setSelected(false);
            EscritaBtn.setSelected(false);
            DecisaoBtn.setSelected(false);
            Action=-1;
        }else
            Action=0;
    }//GEN-LAST:event_ConexaoBtnItemStateChanged
    
    private void DecisaoBtnVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_DecisaoBtnVetoableChange
// TODO add your handling code here:
    }//GEN-LAST:event_DecisaoBtnVetoableChange
    
    private void DecisaoBtnPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DecisaoBtnPropertyChange
// TODO add your handling code here:
    }//GEN-LAST:event_DecisaoBtnPropertyChange
    
    private void DecisaoBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DecisaoBtnItemStateChanged
// TODO add your handling code here:
        DrawArea.repaint();
        if (DecisaoBtn.isSelected()){
            ConectorBtn.setSelected(false);
            TerminadorBtn.setSelected(false);
            LeituraBtn.setSelected(false);
            ProcessoBtn.setSelected(false);
            EscritaBtn.setSelected(false);
            ConexaoBtn.setSelected(false);
            Action=-1;
        }else
            Action=0;
    }//GEN-LAST:event_DecisaoBtnItemStateChanged
    
    private void EscritaBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EscritaBtnItemStateChanged
// TODO add your handling code here:
        DrawArea.repaint();
        if (EscritaBtn.isSelected()){
            ConectorBtn.setSelected(false);
            TerminadorBtn.setSelected(false);
            LeituraBtn.setSelected(false);
            ProcessoBtn.setSelected(false);
            DecisaoBtn.setSelected(false);
            ConexaoBtn.setSelected(false);
            Action=-1;
        }else
            Action=0;
    }//GEN-LAST:event_EscritaBtnItemStateChanged
    
    private void ProcessoBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProcessoBtnItemStateChanged
// TODO add your handling code here:
        DrawArea.repaint();
        if (ProcessoBtn.isSelected()){
            ConectorBtn.setSelected(false);
            TerminadorBtn.setSelected(false);
            LeituraBtn.setSelected(false);
            DecisaoBtn.setSelected(false);
            EscritaBtn.setSelected(false);
            ConexaoBtn.setSelected(false);
            Action=-1;
        }else
            Action=0;
    }//GEN-LAST:event_ProcessoBtnItemStateChanged
    
    private void LeituraBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LeituraBtnItemStateChanged
// TODO add your handling code here:
        DrawArea.repaint();
        if (LeituraBtn.isSelected()){
            ConectorBtn.setSelected(false);
            TerminadorBtn.setSelected(false);
            DecisaoBtn.setSelected(false);
            ProcessoBtn.setSelected(false);
            EscritaBtn.setSelected(false);
            ConexaoBtn.setSelected(false);
            Action=-1;
        }else
            Action=0;
    }//GEN-LAST:event_LeituraBtnItemStateChanged
    
    private void TerminadorBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TerminadorBtnItemStateChanged
// TODO add your handling code here:
        DrawArea.repaint();
        if (TerminadorBtn.isSelected()){
            ConectorBtn.setSelected(false);
            DecisaoBtn.setSelected(false);
            LeituraBtn.setSelected(false);
            ProcessoBtn.setSelected(false);
            EscritaBtn.setSelected(false);
            ConexaoBtn.setSelected(false);
            Action=-1;
        }else
            Action=0;
    }//GEN-LAST:event_TerminadorBtnItemStateChanged
    
    private void btFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileSaveActionPerformed
        MenuItemGuardarProjectoActionPerformed(null);
    }//GEN-LAST:event_btFileSaveActionPerformed
    
    private void btFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileOpenActionPerformed
        MenuItemAbrirProjectoActionPerformed(null);
    }//GEN-LAST:event_btFileOpenActionPerformed
    
    private void btFileNewAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileNewAlgorithmActionPerformed
        jMenuItemNewFileActionPerformed(null);
    }//GEN-LAST:event_btFileNewAlgorithmActionPerformed
    
    private void btRunPortugolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRunPortugolActionPerformed
// TODO add your handling code here:
        MenuItemCompilarActionPerformed(evt);
        if ( forma==null )
            return;
        
        FileManager manager = new FileManager();
        manager.saveTextFile("fluxogramas.tmp",txtCodePortugol.getText());
        
        (new EditorPTG("fluxogramas.tmp")).setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btRunPortugolActionPerformed
    
    private void btStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStopActionPerformed
        MenuItemPararExecucaoActionPerformed(null);
    }//GEN-LAST:event_btStopActionPerformed
    
    private void btRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRunActionPerformed
        MenuItemExecutarActionPerformed(evt);
    }//GEN-LAST:event_btRunActionPerformed
    
    private void btCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCompileActionPerformed
        MenuItemCompilarActionPerformed(evt);
    }//GEN-LAST:event_btCompileActionPerformed
    
    private void btEditPastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditPastActionPerformed
        MenuItemColarActionPerformed(null);
    }//GEN-LAST:event_btEditPastActionPerformed
    
    private void btEditCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditCutActionPerformed
        MenuItemColarActionPerformed(null);
    }//GEN-LAST:event_btEditCutActionPerformed
    
    private void btEditCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditCopyActionPerformed
        MenuItemCopiarActionPerformed(null);
    }//GEN-LAST:event_btEditCopyActionPerformed
    
    private void MenuItemExecutarPassoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExecutarPassoActionPerformed
        
        if ( autoExecute!=null )
            return;
        
        if ( Execute == null ){
            SelectedForms.clear();
            ErrForms.clear();
            DrawArea.repaint();
            memory.clear();
            ErrList.removeAll();
            try {
                MenuItemCompilarActionPerformed(null);
                //limpar os valores das variaveis
                CleanMemoryValues(memory);
                
                if (forma == null)
                    return;
                
                Execute = forma;
                forma = null;
                ////////
                ((ScrollArea)DrawArea).setSelectedColor(Color.GREEN);
                ((ScrollArea)DrawArea).setSelected(Execute);
                scrollPane1.getHorizontalScrollBar().setValue( (int)(Execute.Center.x-(scrollPane1.getWidth()/2)) );
                scrollPane1.getVerticalScrollBar().setValue( (int)(Execute.Center.y-(scrollPane1.getHeight()/2)) );
                ////////
                console.Clear();
                tpUnderFluxogramEditor.setSelectedIndex(0);
                jSplitPane1.setDividerLocation(.7);
                console.write("\nA executar o programa : . . . ");
                console.write("\n-------------------------------------\n");
                DrawArea.repaint();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }
        }else{
            
            if (Execute instanceof Leitura)
                Execute=ExecuteRead(Execute);
            else if (Execute instanceof Escrita)
                Execute=ExecuteWrite(Execute);
            else if (Execute instanceof Processo)
                Execute=ExecuteProcess(Execute);
            else if (Execute instanceof Decisao)
                Execute=ExecuteDecision(Execute);
            else
                Execute=ExecuteOther(Execute);
            
            if (Execute!=null){
                
                if (!(Execute instanceof Decisao))
                    ((ScrollArea)DrawArea).setSelectedColor(Color.GREEN);
                else
                    try {if ( !Calculator.CalulateValue(Expression.ReplaceVariablesToValues(Execute.Text, memory)).equalsIgnoreCase("VERDADEIRO") )
                        ((ScrollArea)DrawArea).setSelectedColor(new Color(221,0,0));
                    } catch (Exception ex) {}
                
                
                scrollPane1.getHorizontalScrollBar().setValue( (int)(Execute.Center.x-(scrollPane1.getWidth()/2)) );
                scrollPane1.getVerticalScrollBar().setValue( (int)(Execute.Center.y-(scrollPane1.getHeight()/2)) );
            }else{
                console.write("\n-------------------------------------");
                console.write("\nO programa terminou com sucesso :-) \n");
            }
            
            ((ScrollArea)DrawArea).setSelected(Execute);
            this.DisplayMemory();
            DrawArea.repaint();
        }
        
    }//GEN-LAST:event_MenuItemExecutarPassoActionPerformed
    //   LIMPAR os valores da memória MEMORIA
    //   os valores são calculados com no decorrer do programa
    // (c) manso 22/09/2006
    
    private void CleanMemoryValues( Vector memory){
        for(int i=0; i< memory.size(); i++){
            Symbol s = (Symbol) memory.get(i);
            s.setHardValue("ERRO");
        }
    }
    private void MenuItemExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExecutarActionPerformed
        
        if ( Execute!=null)
            return ;
        
        SelectedForms.clear();
        DrawArea.repaint();
        memory.clear();
        ErrList.removeAll();
        
        //fazer uma thread
        try {
            MenuItemCompilarActionPerformed(null);
            if (forma == null)
                return;
            
            console.Clear();
            CleanMemoryValues(memory);
            tpUnderFluxogramEditor.setSelectedIndex(0);
            jSplitPane1.setDividerLocation(.7);
            console.write("\nA executar o programa : . . . ");
            console.write("\n-------------------------------------\n");
            Execute = forma;
            forma = null;
            this.autoExecute = new Thread(this);
            this.autoExecute.start();
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            console.write("\n\nO programa foi abortado :-( ");
            Execute = null;
            return;
        }
        
    }//GEN-LAST:event_MenuItemExecutarActionPerformed
    private void ActualizeMenuOpenItem( String item){
        /*MenuItemFile2.setText(MenuItemFile1.getText());
        MenuItemFile2.setEnabled(MenuItemFile1.isEnabled());
        MenuItemFile1.setText(MenuItemFile0.getText());
        MenuItemFile1.setEnabled(MenuItemFile0.isEnabled());
        MenuItemFile0.setText(item);
        MenuItemFile0.setEnabled(true);*/
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FluxogramaPTG(null).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Altura;
    private javax.swing.JRadioButton AutoConector;
    private javax.swing.JButton BackgroundColorBtn;
    private javax.swing.JButton CancelFind;
    private javax.swing.JRadioButton ClosestConector;
    public static javax.swing.JToggleButton ConectorBtn;
    public static javax.swing.JToggleButton ConexaoBtn;
    private javax.swing.JButton ConexaoColorBtn;
    public static javax.swing.JToggleButton DecisaoBtn;
    private javax.swing.JButton DecisaoColorBtn;
    private javax.swing.JPanel DrawArea;
    private javax.swing.JPopupMenu DrawAreaPopupMenu;
    private javax.swing.JTextField EixoHoriz;
    private javax.swing.JTextField EixoVert;
    private java.awt.List ErrList;
    private javax.swing.JTextField ErrTextField;
    public static javax.swing.JToggleButton EscritaBtn;
    private javax.swing.JButton EscritaColorBtn;
    private javax.swing.JCheckBox Espacamento;
    private javax.swing.JFrame Find;
    private javax.swing.JButton FindAll;
    private javax.swing.JTextField FindText;
    private javax.swing.JButton FontColor;
    private javax.swing.JComboBox FontFamilyName;
    private javax.swing.JTextField FontSize;
    private javax.swing.JComboBox FontStyle;
    private javax.swing.JCheckBox Grelha;
    private javax.swing.JTextField Largura;
    public static javax.swing.JToggleButton LeituraBtn;
    private javax.swing.JButton LeituraColorBtn;
    private javax.swing.JRadioButton ManualConector;
    private javax.swing.JCheckBox MatchCase;
    private javax.swing.JMenu MenuAjuda;
    private javax.swing.JMenu MenuEditar;
    private javax.swing.JMenu MenuFicheiro;
    private javax.swing.JMenu MenuImagem;
    private javax.swing.JMenu MenuInterpretador;
    private javax.swing.JMenuItem MenuItemAbrirCodigo;
    private javax.swing.JMenuItem MenuItemAbrirProjecto;
    private javax.swing.JMenuItem MenuItemAjustarArea;
    private javax.swing.JMenuItem MenuItemApagar;
    private javax.swing.JMenuItem MenuItemCaptureAll;
    private javax.swing.JMenuItem MenuItemCaptureSelected;
    private javax.swing.JMenuItem MenuItemColar;
    private javax.swing.JMenuItem MenuItemCompilar;
    private javax.swing.JMenuItem MenuItemCopiar;
    private javax.swing.JMenuItem MenuItemCopy;
    private javax.swing.JMenuItem MenuItemCortar;
    private javax.swing.JMenuItem MenuItemCut;
    private javax.swing.JMenuItem MenuItemDelete;
    private javax.swing.JMenuItem MenuItemEmbelezar;
    private javax.swing.JMenuItem MenuItemExecutar;
    private javax.swing.JMenuItem MenuItemExecutarPasso;
    private javax.swing.JMenuItem MenuItemGuardarCodigo;
    private javax.swing.JMenuItem MenuItemGuardarProjecto;
    private javax.swing.JMenuItem MenuItemGuardarProjectoComo;
    private javax.swing.JMenuItem MenuItemPararExecucao;
    private javax.swing.JMenuItem MenuItemPaste;
    private javax.swing.JMenuItem MenuItemPreferences;
    private javax.swing.JMenuItem MenuItemPropriedades;
    private javax.swing.JMenuItem MenuItemResize;
    private javax.swing.JMenuItem MenuItemSair;
    private javax.swing.JMenuItem MenuItemSelecionarTudo;
    private javax.swing.JMenuItem MenuItemSetColor;
    private javax.swing.JMenuItem MenuItemSetText;
    private javax.swing.JMenuItem MenuItemT1frente;
    private javax.swing.JMenuItem MenuItemT1tras;
    private javax.swing.JMenuItem MenuItemTfrente;
    private javax.swing.JMenuItem MenuItemTtras;
    private javax.swing.JMenu MenuOrder;
    private javax.swing.JFrame Options;
    public static javax.swing.JToggleButton ProcessoBtn;
    private javax.swing.JButton ProcessoColorBtn;
    private javax.swing.JButton ReplaceAll;
    private javax.swing.JTextField ReplaceText;
    private javax.swing.JButton SaidaDecisao;
    private javax.swing.JTextField ScaleFactorField;
    public static javax.swing.JToggleButton TerminadorBtn;
    private javax.swing.JButton TerminadorColorBtn;
    private javax.swing.JTextField TransparenciaField;
    private javax.swing.JSlider TransparenciaSlider;
    private javax.swing.JCheckBox WholeWord;
    private javax.swing.JButton btCompile;
    private javax.swing.JButton btEditCopy;
    private javax.swing.JButton btEditCut;
    private javax.swing.JButton btEditPast;
    private javax.swing.JButton btFileNewAlgorithm;
    private javax.swing.JButton btFileOpen;
    private javax.swing.JButton btFileOpen1;
    private javax.swing.JButton btFileSave;
    private javax.swing.JButton btRun;
    private javax.swing.JButton btRunPortugol;
    private javax.swing.JButton btRunStep;
    private javax.swing.JButton btStop;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItemNewFile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolPatterns;
    private javax.swing.JPanel panelMemory;
    private javax.swing.JScrollPane scrollMemory;
    private javax.swing.JScrollPane scrollMonitor;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane spAbout;
    private javax.swing.JScrollPane spErrorLis;
    private javax.swing.JScrollPane spHelp;
    private javax.swing.JScrollPane spOutput;
    private javax.swing.JScrollPane spPortugol;
    private javax.swing.JScrollPane splMonitor;
    private javax.swing.JToolBar toolBarEdit;
    private javax.swing.JToolBar toolBarFile;
    private javax.swing.JToolBar toolBarFiles;
    private javax.swing.JToolBar toolBarRun;
    private javax.swing.JTabbedPane tpUnderFluxogramEditor;
    private javax.swing.JTextArea txtCodePortugol;
    private javax.swing.JTextArea txtInfoFluxogram;
    // End of variables declaration//GEN-END:variables
    
    private void GetLinksToSelectedForms(){
        //vector com todas a AllForms que apontam para figuras selecionadas
        ConectedForms.clear();
        
        //percorrer todas as AllForms
        for(int i=0; i<AllForms.size(); i++)
            //AllForms a verificar se exitem algum ponteiro
            for (int j=0;j<SelectedForms.size();j++)
                //verificar se existe algum ponteiro para figuras selecionadas
                if ( ((Forma)(AllForms.get(i))).GetNext()==SelectedForms.get(j)  ||
                ((Forma)(AllForms.get(i))).GetIfTrue()==SelectedForms.get(j)  ||
                ((Forma)(AllForms.get(i))).GetIfFalse()==SelectedForms.get(j)  ){
            //verificar se esta ja tinha sido adicionada e se é uma figura selecionada
            if (!ConectedForms.contains(AllForms.get(i)) && !SelectedForms.contains(AllForms.get(i)))
                ConectedForms.add(AllForms.get(i));
                }
    }
    
    private void ClearConectors(){
        //apagar figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++)
            ((Forma)SelectedForms.get(i)).DrawShadow(g);
        
        //apagar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null )
                f.NextConector().DrawConector(g);
            if ( f.IfTrueConector() != null )
                f.IfTrueConector().DrawConector(g);
            if ( f.IfFalseConector() != null )
                f.IfFalseConector().DrawConector(g);
        }
        
        //apagar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) )
                f.NextConector().DrawConector(g);
            if ( f.GetIfTrue() == SelectedForms.get(j) )
                f.IfTrueConector().DrawConector(g);
            if ( f.GetIfFalse() == SelectedForms.get(j) )
                f.IfFalseConector().DrawConector(g);
            }
    }
    
    private void DrawCalcConectors(){
        //desenhar figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++)
            ((Forma)SelectedForms.get(i)).DrawShadow(g);
        
        //desenhar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null )
                f.NextConector().CalcDrawConector(g);
            if ( f.IfTrueConector() != null )
                f.IfTrueConector().CalcDrawConector(g);
            if ( f.IfFalseConector() != null )
                f.IfFalseConector().CalcDrawConector(g);
        }
        
        //desenhar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) )
                f.NextConector().CalcDrawConector(g);
            if ( f.GetIfTrue() == SelectedForms.get(j) )
                f.IfTrueConector().CalcDrawConector(g);
            if ( f.GetIfFalse() == SelectedForms.get(j) )
                f.IfFalseConector().CalcDrawConector(g);
            }
    }
    
    private void ClearClosestConectors(){
        //apagar figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++)
            ((Forma)SelectedForms.get(i)).DrawShadow(g);
        
        //apagar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null ){
                //desenhar o conector
                f.NextConector().DrawConector(g);
                //libertar extremos que estavam a ser usados pelo conector
                f.NextConector().UnBlockConector();
            }
            if ( f.IfTrueConector() != null ){
                f.IfTrueConector().DrawConector(g);
                f.IfTrueConector().UnBlockConector();
            }
            if ( f.IfFalseConector() != null ){
                f.IfFalseConector().DrawConector(g);
                f.IfFalseConector().UnBlockConector();
            }
            
        }
        
        //apagar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) ){
                f.NextConector().DrawConector(g);
                f.NextConector().UnBlockConector();
            }
            if ( f.GetIfTrue() == SelectedForms.get(j) ){
                f.IfTrueConector().DrawConector(g);
                f.IfTrueConector().UnBlockConector();
            }
            if ( f.GetIfFalse() == SelectedForms.get(j) ){
                f.IfFalseConector().DrawConector(g);
                f.IfFalseConector().UnBlockConector();
            }
            }
    }
    
    private void DrawCalcClosestConectors(){
        //desenhar figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++)
            ((Forma)SelectedForms.get(i)).DrawShadow(g);
        
        //desenhar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null ){
                //calcular automaticamente o conector
                f.NextConector().SetClosestExtremes();
                //bloquear extremos usados pelo conector criado
                f.NextConector().BlockConector();
                //desenhar o conector
                f.NextConector().DrawConector(g);
            }
            if ( f.IfTrueConector() != null ){
                f.IfTrueConector().SetClosestExtremes();
                f.IfTrueConector().BlockConector();
                f.IfTrueConector().DrawConector(g);
            }
            if ( f.IfFalseConector() != null ){
                f.IfFalseConector().SetClosestExtremes();
                f.IfFalseConector().BlockConector();
                f.IfFalseConector().DrawConector(g);
            }
        }
        
        //desenhar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) ){
                f.NextConector().SetClosestExtremes();
                f.NextConector().BlockConector();
                f.NextConector().DrawConector(g);
            }
            if ( f.GetIfTrue() == SelectedForms.get(j) ){
                f.IfTrueConector().SetClosestExtremes();
                f.IfTrueConector().BlockConector();
                f.IfTrueConector().DrawConector(g);
            }
            if ( f.GetIfFalse() == SelectedForms.get(j) ){
                f.IfFalseConector().SetClosestExtremes();
                f.IfFalseConector().BlockConector();
                f.IfFalseConector().DrawConector(g);
            }
            }
    }
    
    //colocar forma direita--------------------------------------------------
    private int CalcAction_RightPosition(Forma f, int Actioni){
        
        if (Actioni==5 && f.Right().x<f.Left().x)
            Actioni=4;
        else if (Actioni==4 && f.Right().x<f.Left().x)
            Actioni=5;
        
        if (Actioni==2 && f.Botton().y<f.Top().y)
            Actioni=3;
        else if (Actioni==3 && f.Botton().y<f.Top().y)
            Actioni=2;
        
        
        if (Actioni==6){
            if (f.Botton().y<f.Top().y && f.Right().x<f.Left().x)
                Actioni=9;
            else if (f.Right().x<f.Left().x)
                Actioni=7;
            else if (f.Botton().y<f.Top().y)
                Actioni=8;
            
        }else if (Actioni==7){
            if (f.Botton().y<f.Top().y && f.Right().x<f.Left().x)
                Actioni=8;
            else if (f.Right().x<f.Left().x)
                Actioni=6;
            else if (f.Botton().y<f.Top().y)
                Actioni=9;
            
        }else if (Actioni==8){
            if (f.Botton().y<f.Top().y && f.Right().x<f.Left().x)
                Actioni=7;
            else if (f.Right().x<f.Left().x)
                Actioni=9;
            else if (f.Botton().y<f.Top().y)
                Actioni=6;
            
        }else if (Actioni==9){
            if (f.Botton().y<f.Top().y && f.Right().x<f.Left().x)
                Actioni=6;
            else if (f.Right().x<f.Left().x)
                Actioni=8;
            else if (f.Botton().y<f.Top().y)
                Actioni=7;
        }
        
        
        f.CalcRightPosition();
        return Actioni;
    }
    
    public void CalcEspacamento(){
        
        //espacamento
        for(int i=0;i<SelectedForms.size();i++){
            
            //retirar o multiplos do espacamento ficando o resto na parte fraccionaria
            double razao=((Forma)SelectedForms.get(i)).Center.x/EspHoriz;
            
            //retirar a parte a transladar
            int dx=(int)( ((Forma)SelectedForms.get(i)).Center.x-((int)razao*EspHoriz) );
            
            //verificar para que lado deve transladar
            if (dx<EspHoriz/2)
                dx=-dx;
            else
                dx=EspHoriz-dx;
            
            //retirar o multiplos do espacamento ficando o resto na parte fraccionaria
            razao=((Forma)SelectedForms.get(i)).TopLeft.y/EspVert;
            
            //retirar a parte a transladar
            int dy=(int)( ((Forma)SelectedForms.get(i)).TopLeft.y-((int)razao*EspVert) );
            
            //verificar para que lado deve transladar
            if (dy<EspVert/2)
                dy=-dy;
            else
                dy=EspVert-dy;
            
            ((Forma)SelectedForms.get(i)).Translate(dx ,dy);
        }
        
        
        //desenhar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null )
                f.NextConector().CalcConector();
            if ( f.IfTrueConector() != null )
                f.IfTrueConector().CalcConector();
            if ( f.IfFalseConector() != null )
                f.IfFalseConector().CalcConector();
        }
        
        //desenhar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) )
                f.NextConector().CalcConector();
            else if ( f.GetIfTrue() == SelectedForms.get(j) )
                f.IfTrueConector().CalcConector();
            else if ( f.GetIfFalse() == SelectedForms.get(j) )
                f.IfFalseConector().CalcConector();
            }
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public Forma ExecuteForm(Forma ExecuteForma){
        
        
        if (ExecuteForma instanceof Leitura)
            ExecuteForma=ExecuteRead(ExecuteForma);
        else if (ExecuteForma instanceof Escrita)
            ExecuteForma=ExecuteWrite(ExecuteForma);
        else if (ExecuteForma instanceof Processo)
            ExecuteForma=ExecuteProcess(ExecuteForma);
        else if (ExecuteForma instanceof Decisao)
            ExecuteForma=ExecuteDecision(ExecuteForma);
        else
            ExecuteForma=ExecuteOther(ExecuteForma);
        
        if (ExecuteForma!=null){
            
            if (!(ExecuteForma instanceof Decisao))
                ((ScrollArea)DrawArea).setSelectedColor(Color.BLUE);
            else{
                String result = "";
                try {
                    result = Calculator.CalulateValue(Expression.ReplaceVariablesToValues(ExecuteForma.Text, memory));
                } catch (Exception ex) {}
                
                if (result.equalsIgnoreCase("VERDADEIRO"))
                    ((ScrollArea)DrawArea).setSelectedColor(Color.GREEN);
                else
                    ((ScrollArea)DrawArea).setSelectedColor(new Color(221,0,0));
                
            }
            
            
            
            scrollPane1.getHorizontalScrollBar().setValue( (int)(ExecuteForma.Center.x-(scrollPane1.getWidth()/2)) );
            scrollPane1.getVerticalScrollBar().setValue( (int)(ExecuteForma.Center.y-(scrollPane1.getHeight()/2)) );
        }else{
            console.write("\n-------------------------------------");
            console.write("\nO programa terminou com sucesso :-) \n");
        }
        
        ((ScrollArea)DrawArea).setSelected(ExecuteForma);
        DrawArea.repaint();
        
        return ExecuteForma;
    }
    
    
    
    /*private void UpdateVars(){
        String str="";
        for (int i=0;i<memory.size();i++){
            Symbol var=(Symbol)memory.get(i);
            str+=var.getStringType().toLowerCase()+" "+var.getName()+" "+var.getValue()+"\n";
        }
        txtCodePortugol.setText(str);
    }*/
    
    //--------------------------------------------------------------------
    // executa a leitura de um valor
    // se a variavel nao estiver definida
    // le uma variavel do tipo texto, detecta o tipo e define a variavel
    // (c) manso 22/09/2006
    //--------------------------------------------------------------------
    private Forma ExecuteRead(Forma f){
        // vou ler uma variavel do tipo texto
        
        Symbol tmp;
        String varName = f.Text.trim();
        Symbol var = Variable.getVariable(varName,memory);
        //verificar se está definida
        boolean isdefined=true;
        String value;
        try {
            if ( var.getValue().equalsIgnoreCase("ERRO")){
                tmp = new Symbol("variavel","texto",varName,"",0);
                isdefined=false;
            } else
                tmp = var;
            
            
            value = console.read(var);
            // se não estiver definido definir o tipo
            if(!isdefined)
                // como é variavel to tipo texto o valor vem com "___" por isso é preciso removelas
                //para determinar o tipo
                var.setType( Values.getTypeOfValue( Values.removeStringComas(value) ));
            //atribuir um valor
            var.setValue(value);
            
        } catch (Exception ex) {
            if ( autoExecute == null )
                return f;
            else{
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        
        return f.GetNext();
    }
    
    private Forma ExecuteWrite(Forma f){
        
        String str = f.Text;
        IteratorCodeParams tok = new IteratorCodeParams(str.trim());
        //WriteTokenizer tok = new  WriteTokenizer(str);
        StringBuffer line = new StringBuffer();
        String elemLine ;
        //parametros
        while(tok.hasMoreElements()){
            String elem = tok.current();
            tok.next();
            
            if( !Values.IsString(elem) )
                elemLine = Expression.Evaluate(elem,memory);
            else
                elemLine = elem;
            line.append( Values.getStringValue(elemLine));
        }
        console.write(line.toString());
        
        return f.GetNext();
    }
    
    //--------------------------------------------------------------------
    // executa a atribuição de um valor a uma variavel
    // se a variavel nao estiver definida
    // determina o tipo com base no resultado na expressão e
    // define a variavel correctamente
    // (c) manso 22/09/2006
    //--------------------------------------------------------------------
    private Forma ExecuteProcess(Forma f){
        
        String varName = f.Text.substring(0, f.Text.indexOf("<-")).trim();
        Symbol var = Variable.getVariable(varName,memory);
        
        String expression=f.Text.substring(f.Text.indexOf("<-")+2);
        String value;
        boolean isdefined=true;
        try {
            if ( var.getValue().equalsIgnoreCase("ERRO"))
                isdefined=false;
            
            value = Calculator.CalulateValue( Expression.ReplaceVariablesToValues(expression, memory) );
            // se não estiver definido definir o tipo
            if(!isdefined)
                var.setType( Values.getTypeOfValue(value));
            
            var.setValue(value);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return f.GetNext();
    }
    
    private Forma ExecuteDecision(Forma f){
        
        String result;
        try {
            result=Calculator.CalulateValue(Expression.ReplaceVariablesToValues(f.Text, memory));
            if ( !Values.IsBoolean(result) ){
                JOptionPane.showMessageDialog(this, "expressao mal construida", "ERRO", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        if (result.equalsIgnoreCase("VERDADEIRO"))
            return f.GetIfTrue();
        else
            return f.GetIfFalse();
    }
    
    private Forma ExecuteOther(Forma f){
        return f.GetNext();
    }
    
    
    
    private void TranslateSelectedForms(int dx, int dy){
        
        //guardar todas a figuras que apontam para as figuras selecionadas
        GetLinksToSelectedForms();
        
        //transladar AllForms selecionadas
        for (int i=0;i<SelectedForms.size();i++)
            ((Forma)SelectedForms.get(i)).Translate(dx, dy);
        
        
        //desenhar conectores que saem das figuras selecionadas
        for (int i=0;i<SelectedForms.size();i++){
            Forma f = (Forma)SelectedForms.get(i);
            if ( f.NextConector() != null )
                f.NextConector().CalcConector();
            if ( f.IfTrueConector() != null )
                f.IfTrueConector().CalcConector();
            if ( f.IfFalseConector() != null )
                f.IfFalseConector().CalcConector();
        }
        
        //desenhar conectores que ligam as figuras selecionadas
        for(int i=0; i<ConectedForms.size(); i++)
            //verificar se alguma das figuras aponta para uma figura selecionada
            for (int j=0;j<SelectedForms.size();j++){
            Forma f = (Forma)ConectedForms.get(i);
            if ( f.GetNext() == SelectedForms.get(j) )
                f.NextConector().CalcConector();
            if ( f.GetIfTrue() == SelectedForms.get(j) )
                f.IfTrueConector().CalcConector();
            if ( f.GetIfFalse() == SelectedForms.get(j) )
                f.IfFalseConector().CalcConector();
            }
        
        DrawArea.repaint();
    }
    
    
    public void FitFlowChart(boolean esp){
        
        int MaxHoriz=-999999999;
        int MinHoriz=999999999;
        int MaxVert=-999999999;
        int MinVert=999999999;
        
        for (int i=0; i<AllForms.size(); i++){
            
            if ( ((Forma)AllForms.get(i)).BottonRight.x > MaxHoriz )
                MaxHoriz = (int)((Forma)AllForms.get(i)).BottonRight.x;
            if ( ((Forma)AllForms.get(i)).BottonRight.y > MaxVert )
                MaxVert = (int)((Forma)AllForms.get(i)).BottonRight.y;
            
            if ( ((Forma)AllForms.get(i)).TopLeft.x < MinHoriz )
                MinHoriz = (int)((Forma)AllForms.get(i)).TopLeft.x;
            if ( ((Forma)AllForms.get(i)).TopLeft.y < MinVert )
                MinVert = (int)((Forma)AllForms.get(i)).TopLeft.y;
            
        }
        
        
        
        
        if (MinHoriz==999999999 || MaxHoriz==-999999999
                || MaxVert==-999999999 || MinVert==999999999)return;
        
        
        int largura= MaxHoriz-MinHoriz+150;
        int altura = MaxVert-MinVert+50;
        
        
        /*
        if ( largura<scrollPane1.getWidth() )
            MinHoriz = scrollPane1.getWidth()/2-largura/2;
        if ( altura<scrollPane1.getHeight() )
            MinVert = scrollPane1.getWidth()/2-altura/2;
         */
        MenuItemSelecionarTudoActionPerformed(null);
        TranslateSelectedForms( 75-MinHoriz , 25-MinVert);
        
        if (esp && Espacamento.isSelected())
            CalcEspacamento();
        
        SelectedForms.clear();
        ConectedForms.clear();
        
        
        DrawArea.setPreferredSize( new Dimension(largura, altura) );
        DrawArea.setSize(largura, altura);
        scrollPane1.doLayout();
        
    }
    
    private void DisplayMemory(){
        panelMemory.setVisible(false);
        panelMemory.removeAll();
        for( int index = 0 ; index< memory.size() ; index++){
            Symbol var = (Symbol) memory.get(index);
            panelMemory.add(new MemoryVariable(var ));
        }
        panelMemory.setVisible(true);
    }
    
    
    
}
