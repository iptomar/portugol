/*
 * EditorPTG.java
 *
 * Created on 10 de Setembro de 2006, 16:39
 */

package Editor.GUI;

import Editor.GUI.About.AboutThis;
import Editor.GUI.About.PortugolInfo;
import Editor.GUI.CodeDocument.AlgoCodeBlank;
import Editor.GUI.CodeDocument.AlgoCodeStyle;
import Editor.GUI.CodeDocument.AlgoSyntaxHighlight;
import Editor.GUI.CodeDocument.BeautifyCode;
import Editor.GUI.Dialogs.Message;
import Editor.Utils.EditorProperties;
import Editor.Utils.FileManager;
import Editor.Utils.FontChooser;
import Editor.Utils.MyUndoRedoManager;

import Editor.help.HelpFileName;
import Editor.help.WWWHelpText;
import Portugol.Language.Execute.ConsoleIO;
import Portugol.Language.Make.Fluxogram;
import Portugol.Language.Make.NodeInstruction;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Utils.LanguageException;
import VisualFluxogram.GUI.FluxogramaPTG;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author  _zeus
 */
public class EditorPTG extends javax.swing.JFrame implements Runnable{
    public static String TITLE = "Portugol IDE 2.0 ";
    public static String DATE = "10-09-2006";
    public static String VERSION = "ver:2.0 \t(c)António M@nso";
    //texto modificado
    private boolean textChanged = false;
    // consola de execução do programa
    public ConsoleIO console;
    private ConsoleIO info;
    // gestor de ficheiros
    private FileManager fileManager;
    // propriedades do editor
    private EditorProperties editorProperties;
    //formatação dos caracteres do editor
    private AlgoSyntaxHighlight txtCode ;
    //ajuda da linguagem
    private WWWHelpText helpLang;
    // undo manager
    private MyUndoRedoManager urManager;
    
    
    //---------------------------- EXecução do programa ---------
    private Thread autoExecute=null;
    private NodeInstruction instruction;
    private Fluxogram prog;
    //-----------------------------------------------------------
    
    public EditorPTG() {
        initComponents();
        initMyComponents();
        // ler o ficheiro por defeito
        this.LerFicheiro( fileManager.getFileName());
        
    }
    
    public EditorPTG(String fileName) {
        initComponents();
        initMyComponents();
        // ler o ficheiro por defeito
        this.LerFicheiro( fileName);
        
    }
    
    private void initMyComponents(){
        //maximizar a janela
        this.setExtendedState(MAXIMIZED_BOTH);
        //---------------- consola ------------------------
        console = new ConsoleIO();
        scrollMonitor.add(console);
        scrollMonitor.setViewportView(console);
        //--------------------------------------------------
        fileManager = new FileManager();
        
        //----------- Propriedades do Editor --------------------
        SetEditorProperties();
        //---------------- HELP Linguagem -----------------------------------
        helpLang = new WWWHelpText("Editor/help/index.html");
        this.spLinguagem.add(helpLang);
        this.spLinguagem.setViewportView(helpLang);
        //------------------------  UNDO MANAGER  ---------
        urManager = new MyUndoRedoManager();
        TextPaneCode.getDocument().addUndoableEditListener( urManager );
        //----------------------------------------
        info.write( PortugolInfo.getInformation());
        
    }
    public void SetEditorProperties(){
        editorProperties = new EditorProperties();
        //-----------------------------MENU FICHEIRO --------------------
        MenuFicheiroAberto1.setText( editorProperties.GetProperty("file1"));
        MenuFicheiroAberto2.setText( editorProperties.GetProperty("file2"));
        MenuFicheiroAberto3.setText( editorProperties.GetProperty("file3"));        
        //----------------------------FONTE ----------------------------------
        String fontName = editorProperties.GetProperty("fontName");
        int size = Integer.parseInt(editorProperties.GetProperty("fontSize"));
        int style =0;
        if ( editorProperties.GetProperty("fontItalic").equalsIgnoreCase("true") )
            style += Font.ITALIC;
        if ( editorProperties.GetProperty("fontBold").equalsIgnoreCase("true") )
            style += Font.BOLD;
        TextPaneCode.setFont( new Font( fontName,style, size ) );
        
       //------------------------------------COR ------------------------------- 
        int R = Integer.parseInt(editorProperties.GetProperty("backColorR"));
        int G = Integer.parseInt(editorProperties.GetProperty("backColorG"));
        int B = Integer.parseInt(editorProperties.GetProperty("backColorB"));
        txtCode.defaultBackGround = new Color(R,G,B);
        TextPaneCode.setBackground( new Color(R,G,B));
        
        //------------------------  SISTEMA DE COLORAÇÃO DO EDITOR ---------
        MenuEditorCheckSyntax.setState(editorProperties.GetProperty("sintax").equalsIgnoreCase("true") );
        MenuEditorCheckSyntaxActionPerformed(null);                    
    }
    
    public void SelectErrorLine(int numChar){
        txtCode.selectErrorLine(numChar);
        TextPaneCode.setCaretPosition(numChar);
    }
    public void SelectCodeLine(int numChar){
        txtCode.selectCodeLine(numChar);
        TextPaneCode.setCaretPosition(numChar);
    }
    
    
    public void DeSelectLine(int numChar){
        txtCode.deSelectCodeLine(numChar);
    }
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
    public void run(){
        try{
            instruction = prog.getStart();
            while(autoExecute != null )  {
                instruction = prog.ExecuteLine(instruction, console);
                autoExecute.sleep(10);
                if( instruction.GetType() == Keyword.FIM){
//                    this.btRun.setEnabled(true);
                    autoExecute =null;
                    break;
                }
            }
            info.write("\nO programa terminou com sucesso :-) ");
            info.write("\n\t\t\t\t\t"+ (new Date()).toString() );
//            this.btRun.setEnabled(true);
        }catch(Exception e){
            //  autoExecute =null;
            SelectErrorLine(instruction.GetCharNum());
            LanguageException ex = new LanguageException(
                    instruction.GetCharNum(),
                    instruction.GetText(),
                    e.getMessage(), "ERRO de EXECUÇÃO");
            Message.ExecutionError("ERRO de EXECUÇAO", ex);
            DeSelectLine(instruction.GetCharNum());
//            this.btRun.setEnabled(true);
        }
        autoExecute =null;
//      this.btRun.setEnabled(true);
    }
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
    public void SelectTabUnderEditor(int index){
        this.tpUnderCodeEditor.setSelectedIndex(index);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        ToolBarPrincipal = new javax.swing.JToolBar();
        ToolBarFicheiro = new javax.swing.JToolBar();
        ButtonFicheiroNovo = new javax.swing.JButton();
        ButtonFicheiroAbrir = new javax.swing.JButton();
        ButtonFicheiroGuardar = new javax.swing.JButton();
        TooBarEditar = new javax.swing.JToolBar();
        ButtonEditarReformatar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ButtonEditarRecuar = new javax.swing.JButton();
        ButtonEditarAvancar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ButtonEditarCopiar = new javax.swing.JButton();
        ButtonEditarColar = new javax.swing.JButton();
        ButtonEditarCortar = new javax.swing.JButton();
        ToolBarPrograma = new javax.swing.JToolBar();
        ButtonProgramaVerificar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        ButtonProgramaCorrer = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ButtonProgramaCorrerPassoIniciar = new javax.swing.JButton();
        ButtonProgramaCorrerPassoContinuar = new javax.swing.JButton();
        ButtonProgramaParar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        ButtonProgramaCorrerMonitorar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        ButtonProgramaFluxograma = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        scrollCodeEditor = new javax.swing.JScrollPane();
        TextPaneCode = new javax.swing.JTextPane();
        tpUnderCodeEditor = new javax.swing.JTabbedPane();
        spOutput = new javax.swing.JScrollPane();
        scrollMonitor = new javax.swing.JScrollPane();
        spInfo = new javax.swing.JScrollPane();
        scrollInfo = new javax.swing.JScrollPane();
        spLinguagem = new javax.swing.JScrollPane();
        jMenuBar2 = new javax.swing.JMenuBar();
        MenuFicheiro1 = new javax.swing.JMenu();
        MenuFicheiroNovo1 = new javax.swing.JMenuItem();
        MenuFicheiroAbrir1 = new javax.swing.JMenuItem();
        MenuFicheiroGuardar = new javax.swing.JMenuItem();
        MenuFicheiroGuardarComo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        MenuFicheiroAberto1 = new javax.swing.JMenuItem();
        MenuFicheiroAberto2 = new javax.swing.JMenuItem();
        MenuFicheiroAberto3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        MenuFicheiroSair1 = new javax.swing.JMenuItem();
        MenuEditar1 = new javax.swing.JMenu();
        MenuEditarUndo = new javax.swing.JMenuItem();
        MenuEditarRedo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        MenuEditarCopiar = new javax.swing.JMenuItem();
        MenuEditarColar = new javax.swing.JMenuItem();
        MenuEditarCortar = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        MenuEditarReformatar = new javax.swing.JMenuItem();
        MenuAlgoritmo = new javax.swing.JMenu();
        MenuAlgoritmoVerificar = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        MenuAlgoritmoCorrer = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        MenuAlgoritmoPassoIniciar = new javax.swing.JMenuItem();
        MenuAlgoritmoPassoContinuar = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        MenuAlgoritmoMonitorar = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        MenuProgramaParar = new javax.swing.JMenuItem();
        MenuEditor = new javax.swing.JMenu();
        MenuEditorCheckSyntax = new javax.swing.JCheckBoxMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        MenuEditorFonte = new javax.swing.JMenuItem();
        MenuAjuda = new javax.swing.JMenu();
        MenuAjudaAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editor");
        ButtonFicheiroNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_new.png")));
        ButtonFicheiroNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonFicheiroNovoActionPerformed(evt);
            }
        });

        ToolBarFicheiro.add(ButtonFicheiroNovo);

        ButtonFicheiroAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_open.png")));
        ButtonFicheiroAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonFicheiroAbrirActionPerformed(evt);
            }
        });

        ToolBarFicheiro.add(ButtonFicheiroAbrir);

        ButtonFicheiroGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/file_save.png")));
        ButtonFicheiroGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonFicheiroGuardarActionPerformed(evt);
            }
        });

        ToolBarFicheiro.add(ButtonFicheiroGuardar);

        ToolBarPrincipal.add(ToolBarFicheiro);

        ButtonEditarReformatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_reformat.png")));
        ButtonEditarReformatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarReformatarActionPerformed(evt);
            }
        });

        TooBarEditar.add(ButtonEditarReformatar);

        jLabel1.setText("    ");
        TooBarEditar.add(jLabel1);

        ButtonEditarRecuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_undo.png")));
        ButtonEditarRecuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarRecuarActionPerformed(evt);
            }
        });

        TooBarEditar.add(ButtonEditarRecuar);

        ButtonEditarAvancar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_redo.png")));
        ButtonEditarAvancar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarAvancarActionPerformed(evt);
            }
        });

        TooBarEditar.add(ButtonEditarAvancar);

        jLabel2.setText("    ");
        TooBarEditar.add(jLabel2);

        ButtonEditarCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_copy.png")));
        ButtonEditarCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarCopiarActionPerformed(evt);
            }
        });

        TooBarEditar.add(ButtonEditarCopiar);

        ButtonEditarColar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_past.png")));
        ButtonEditarColar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarColarActionPerformed(evt);
            }
        });

        TooBarEditar.add(ButtonEditarColar);

        ButtonEditarCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/edit_cut.png")));
        ButtonEditarCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarCortarActionPerformed(evt);
            }
        });

        TooBarEditar.add(ButtonEditarCortar);

        ToolBarPrincipal.add(TooBarEditar);

        ButtonProgramaVerificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/programa_verify.png")));
        ButtonProgramaVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaVerificarActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaVerificar);

        jLabel3.setText("            ");
        ToolBarPrograma.add(jLabel3);

        ButtonProgramaCorrer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_run.png")));
        ButtonProgramaCorrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaCorrerActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaCorrer);

        jLabel4.setText("            ");
        ToolBarPrograma.add(jLabel4);

        ButtonProgramaCorrerPassoIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_run_step.png")));
        ButtonProgramaCorrerPassoIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaCorrerPassoIniciarActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaCorrerPassoIniciar);

        ButtonProgramaCorrerPassoContinuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_next_step.png")));
        ButtonProgramaCorrerPassoContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaCorrerPassoContinuarActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaCorrerPassoContinuar);

        ButtonProgramaParar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_stop.png")));
        ButtonProgramaParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaPararActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaParar);

        jLabel5.setText("            ");
        ToolBarPrograma.add(jLabel5);

        ButtonProgramaCorrerMonitorar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/program_run_debug.png")));
        ButtonProgramaCorrerMonitorar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaCorrerMonitorarActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaCorrerMonitorar);

        jLabel6.setText("            ");
        ToolBarPrograma.add(jLabel6);

        ButtonProgramaFluxograma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/toolbar/run_Fluxogram.png")));
        ButtonProgramaFluxograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonProgramaFluxogramaActionPerformed(evt);
            }
        });

        ToolBarPrograma.add(ButtonProgramaFluxograma);

        ToolBarPrincipal.add(ToolBarPrograma);

        getContentPane().add(ToolBarPrincipal, java.awt.BorderLayout.NORTH);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        scrollCodeEditor.setMinimumSize(new java.awt.Dimension(400, 300));
        scrollCodeEditor.setPreferredSize(new java.awt.Dimension(400, 600));
        TextPaneCode.setFont(new java.awt.Font("Courier New", 0, 18));
        TextPaneCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextPaneCodeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextPaneCodeKeyTyped(evt);
            }
        });

        scrollCodeEditor.setViewportView(TextPaneCode);

        jSplitPane2.setLeftComponent(scrollCodeEditor);

        tpUnderCodeEditor.setAutoscrolls(true);
        spOutput.setAutoscrolls(true);
        spOutput.setPreferredSize(new java.awt.Dimension(400, 200));
        scrollMonitor.setBackground(new java.awt.Color(0, 0, 0));
        scrollMonitor.setForeground(new java.awt.Color(255, 255, 255));
        scrollMonitor.setToolTipText("Program Console");
        scrollMonitor.setFont(new java.awt.Font("Courier New", 0, 12));
        spOutput.setViewportView(scrollMonitor);

        tpUnderCodeEditor.addTab("ecran", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/console.png")), spOutput, "Esta \"tab\" apresenta a saida por defeito do interpretador.");

        spInfo.setAutoscrolls(true);
        spInfo.setPreferredSize(new java.awt.Dimension(400, 200));
        scrollInfo.setBackground(new java.awt.Color(0, 0, 0));
        scrollInfo.setForeground(new java.awt.Color(255, 255, 255));
        scrollInfo.setToolTipText("Program Console");
        scrollInfo.setFont(new java.awt.Font("Courier New", 0, 12));
        info = new ConsoleIO();
        info.setColor(Color.WHITE,Color.BLACK);
        scrollInfo.add(info);
        scrollInfo.setViewportView(info);
        spInfo.setViewportView(scrollInfo);

        tpUnderCodeEditor.addTab("Informa\u00e7\u00f5es", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/info.png")), spInfo, "Esta \"tab\" apresenta a saida por defeito do interpretador.");

        tpUnderCodeEditor.addTab("Ajuda da Linguagem", new javax.swing.ImageIcon(getClass().getResource("/Icons/tabs/help.png")), spLinguagem);

        tpUnderCodeEditor.setSelectedIndex(1);

        jSplitPane2.setBottomComponent(tpUnderCodeEditor);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.CENTER);

        MenuFicheiro1.setText("Ficheiro");
        MenuFicheiroNovo1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        MenuFicheiroNovo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_new.png")));
        MenuFicheiroNovo1.setText("Novo");
        MenuFicheiroNovo1.setToolTipText("novo Algoritmo");
        MenuFicheiroNovo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroNovo1ActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroNovo1);

        MenuFicheiroAbrir1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        MenuFicheiroAbrir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_open.png")));
        MenuFicheiroAbrir1.setText("Abrir");
        MenuFicheiroAbrir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroAbrir1ActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroAbrir1);

        MenuFicheiroGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        MenuFicheiroGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_save.png")));
        MenuFicheiroGuardar.setText("Guardar");
        MenuFicheiroGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroGuardarActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroGuardar);

        MenuFicheiroGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_save_as.png")));
        MenuFicheiroGuardarComo.setText("Guardar Como . . .");
        MenuFicheiroGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroGuardarComoActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroGuardarComo);

        MenuFicheiro1.add(jSeparator2);

        MenuFicheiroAberto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_document.png")));
        MenuFicheiroAberto1.setText("ficheiro_sem_nome");
        MenuFicheiroAberto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroAberto1ActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroAberto1);

        MenuFicheiroAberto2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_document.png")));
        MenuFicheiroAberto2.setText("ficheiro_sem_nome");
        MenuFicheiroAberto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroAberto2ActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroAberto2);

        MenuFicheiroAberto3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_document.png")));
        MenuFicheiroAberto3.setText("ficheiro_sem_nome");
        MenuFicheiroAberto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroAberto3ActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroAberto3);

        MenuFicheiro1.add(jSeparator1);

        MenuFicheiroSair1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        MenuFicheiroSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/file_exit.png")));
        MenuFicheiroSair1.setText("Sair");
        MenuFicheiroSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFicheiroSair1ActionPerformed(evt);
            }
        });

        MenuFicheiro1.add(MenuFicheiroSair1);

        jMenuBar2.add(MenuFicheiro1);

        MenuEditar1.setText("Editar");
        MenuEditarUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        MenuEditarUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_undo.png")));
        MenuEditarUndo.setText("Recuar");
        MenuEditarUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditarUndoActionPerformed(evt);
            }
        });

        MenuEditar1.add(MenuEditarUndo);

        MenuEditarRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        MenuEditarRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_redo.png")));
        MenuEditarRedo.setText("Avan\u00e7ar");
        MenuEditarRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditarRedoActionPerformed(evt);
            }
        });

        MenuEditar1.add(MenuEditarRedo);

        MenuEditar1.add(jSeparator3);

        MenuEditarCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        MenuEditarCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_copy.png")));
        MenuEditarCopiar.setText("Copiar");
        MenuEditarCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditarCopiarActionPerformed(evt);
            }
        });

        MenuEditar1.add(MenuEditarCopiar);

        MenuEditarColar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        MenuEditarColar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_past.png")));
        MenuEditarColar.setText("Colar");
        MenuEditarColar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditarColarActionPerformed(evt);
            }
        });

        MenuEditar1.add(MenuEditarColar);

        MenuEditarCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        MenuEditarCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_cut.png")));
        MenuEditarCortar.setText("Colar");
        MenuEditarCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditarCortarActionPerformed(evt);
            }
        });

        MenuEditar1.add(MenuEditarCortar);

        MenuEditar1.add(jSeparator4);

        MenuEditarReformatar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        MenuEditarReformatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/edit_reformat.png")));
        MenuEditarReformatar.setText("Formatar autom\u00e1tico");
        MenuEditarReformatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditarReformatarActionPerformed(evt);
            }
        });

        MenuEditar1.add(MenuEditarReformatar);

        jMenuBar2.add(MenuEditar1);

        MenuAlgoritmo.setText("Algoritmo");
        MenuAlgoritmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAlgoritmoActionPerformed(evt);
            }
        });

        MenuAlgoritmoVerificar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        MenuAlgoritmoVerificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/programa_verify.png")));
        MenuAlgoritmoVerificar.setText("Verificar");
        MenuAlgoritmoVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAlgoritmoVerificarActionPerformed(evt);
            }
        });

        MenuAlgoritmo.add(MenuAlgoritmoVerificar);

        MenuAlgoritmo.add(jSeparator5);

        MenuAlgoritmoCorrer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        MenuAlgoritmoCorrer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_run.png")));
        MenuAlgoritmoCorrer.setText("Executar");
        MenuAlgoritmoCorrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAlgoritmoCorrerActionPerformed(evt);
            }
        });

        MenuAlgoritmo.add(MenuAlgoritmoCorrer);

        MenuAlgoritmo.add(jSeparator7);

        MenuAlgoritmoPassoIniciar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        MenuAlgoritmoPassoIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_run_step.png")));
        MenuAlgoritmoPassoIniciar.setText("Iniciar Passo a Passo");
        MenuAlgoritmoPassoIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAlgoritmoPassoIniciarActionPerformed(evt);
            }
        });

        MenuAlgoritmo.add(MenuAlgoritmoPassoIniciar);

        MenuAlgoritmoPassoContinuar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        MenuAlgoritmoPassoContinuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_next_step.png")));
        MenuAlgoritmoPassoContinuar.setText("Pr\u00f3ximo Passo");
        MenuAlgoritmoPassoContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAlgoritmoPassoContinuarActionPerformed(evt);
            }
        });

        MenuAlgoritmo.add(MenuAlgoritmoPassoContinuar);

        MenuAlgoritmo.add(jSeparator9);

        MenuAlgoritmoMonitorar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        MenuAlgoritmoMonitorar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_run_debug.png")));
        MenuAlgoritmoMonitorar.setText("Executar e Monitorar");
        MenuAlgoritmoMonitorar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAlgoritmoMonitorarActionPerformed(evt);
            }
        });

        MenuAlgoritmo.add(MenuAlgoritmoMonitorar);

        MenuAlgoritmo.add(jSeparator6);

        MenuProgramaParar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/program_stop.png")));
        MenuProgramaParar.setText("Parar Execu\u00e7\u00e3o");
        MenuProgramaParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuProgramaPararActionPerformed(evt);
            }
        });

        MenuAlgoritmo.add(MenuProgramaParar);

        jMenuBar2.add(MenuAlgoritmo);

        MenuEditor.setText("Editor");
        MenuEditorCheckSyntax.setText("Colorir Programa");
        MenuEditorCheckSyntax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditorCheckSyntaxActionPerformed(evt);
            }
        });

        MenuEditor.add(MenuEditorCheckSyntax);

        MenuEditor.add(jSeparator10);

        MenuEditorFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu/editor_font.png")));
        MenuEditorFonte.setText("Fonte");
        MenuEditorFonte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEditorFonteActionPerformed(evt);
            }
        });

        MenuEditor.add(MenuEditorFonte);

        jMenuBar2.add(MenuEditor);

        MenuAjuda.setText("Ajuda");
        MenuAjudaAcerca.setText("Informa\u00e7oes sobre . . .");
        MenuAjudaAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAjudaAcercaActionPerformed(evt);
            }
        });

        MenuAjuda.add(MenuAjudaAcerca);

        jMenuBar2.add(MenuAjuda);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonProgramaFluxogramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaFluxogramaActionPerformed
        this.fileManager.saveTextFile("portugol.tmp",TextPaneCode.getText()) ;        
        
        (new FluxogramaPTG("portugol.tmp")).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ButtonProgramaFluxogramaActionPerformed
    
    private void MenuEditorFonteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditorFonteActionPerformed
        FontChooser f = new FontChooser(this, TextPaneCode.getFont(), TextPaneCode.getBackground());
        f.setVisible(true);
        if( f.fontSelected ){
            Font fnt = f.getNewFont();
            System.out.println("FONTE   :" + fnt.getFontName());
            System.out.println("tamanho :" + fnt.getSize());
            TextPaneCode.setFont(f.getNewFont());
            TextPaneCode.setBackground( f.getNewColor());
            txtCode.defaultBackGround = f.getNewColor();
            
            editorProperties.SetProperty("fontName",fnt.getName());
            editorProperties.SetProperty("fontSize",fnt.getSize() + "");            
            editorProperties.SetProperty("fontBold",fnt.isBold() + "");
            editorProperties.SetProperty("fontItalic",fnt.isItalic()+"");             
            editorProperties.SetProperty("backColorR", "" + f.getNewColor().getRed());
            editorProperties.SetProperty("backColorG", "" + f.getNewColor().getGreen());
            editorProperties.SetProperty("backColorB", "" + f.getNewColor().getBlue());            
            //recolorir
            this.MenuEditorCheckSyntaxActionPerformed(evt);            
        }
        
    }//GEN-LAST:event_MenuEditorFonteActionPerformed
    
    private void MenuEditorCheckSyntaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditorCheckSyntaxActionPerformed
        String txt = TextPaneCode.getText();
        if(MenuEditorCheckSyntax.isSelected() ){
            txtCode = new AlgoCodeStyle();
        } else{
            txtCode = new AlgoCodeBlank();
        }
       editorProperties.SetProperty("sintax",MenuEditorCheckSyntax.isSelected() + "");
        TextPaneCode.setStyledDocument(txtCode); // colorir sintaxe
        TextPaneCode.setText(txt);
        txtCode.clearTextBackground();
         
    }//GEN-LAST:event_MenuEditorCheckSyntaxActionPerformed
    
    private void MenuAjudaAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAjudaAcercaActionPerformed
        tpUnderCodeEditor.setSelectedIndex(1);
        info.Clear();
        info.write( PortugolInfo.getInformation());
        AboutThis about = new AboutThis();
        about.setAlwaysOnTop(true);
        about.setVisible(true);
        about.requestFocus();
    }//GEN-LAST:event_MenuAjudaAcercaActionPerformed
    
    private void MenuAlgoritmoMonitorarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAlgoritmoMonitorarActionPerformed
        //   info.write("Guardar Programa . . .\n" +  fileManager.getFileName());
        //   this.MenuFicheiroGuardarActionPerformed(evt);
        if(TextPaneCode.getText().length() > 0){
            try{
                prog = new Fluxogram(TextPaneCode.getText());
                ConsoleMonitorStep c = new ConsoleMonitorStep(this,prog);
                c.setVisible(true);
            }catch (LanguageException e){
                SelectErrorLine(e.line);
                Message.CompileError(e);
            }
        }
    }//GEN-LAST:event_MenuAlgoritmoMonitorarActionPerformed
    
    private void MenuAlgoritmoPassoContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAlgoritmoPassoContinuarActionPerformed
        try{
            //se não houver instrução
            if( prog == null || instruction == null)
                return;
            
            autoExecute = null;
            this.tpUnderCodeEditor.setSelectedIndex(0);
            
            // se for o fim
            if( instruction.GetType() == Keyword.FIM){
                DeSelectLine(instruction.GetCharNum());
                instruction = null;
            }
            
            DeSelectLine(instruction.GetCharNum());
            instruction = prog.ExecuteLine(instruction, console);
            SelectCodeLine(instruction.GetCharNum());
            
            
        }catch(Exception e){
            if( instruction == null)
                return;
            
            SelectErrorLine(instruction.GetCharNum());
            LanguageException ex = new LanguageException(
                    instruction.GetCharNum(),
                    instruction.GetText(),
                    e.getMessage(), "ERRO de EXECUÇÃO");
            Message.ExecutionError("ERRO de EXECUÇAO", ex);
            DeSelectLine(instruction.GetCharNum());
            
        }
    }//GEN-LAST:event_MenuAlgoritmoPassoContinuarActionPerformed
    
    private void ButtonProgramaCorrerPassoContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaCorrerPassoContinuarActionPerformed
        MenuAlgoritmoPassoContinuarActionPerformed(evt);
    }//GEN-LAST:event_ButtonProgramaCorrerPassoContinuarActionPerformed
    
    private void ButtonProgramaCorrerPassoIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaCorrerPassoIniciarActionPerformed
        MenuAlgoritmoPassoIniciarActionPerformed(evt);
    }//GEN-LAST:event_ButtonProgramaCorrerPassoIniciarActionPerformed
    
    private void MenuAlgoritmoPassoIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAlgoritmoPassoIniciarActionPerformed
        //parar a thread
        autoExecute =null;
        if( instruction != null)
            DeSelectLine(instruction.GetCharNum());
        try{
            //verificar o programa e construir o fluxograma
            MenuAlgoritmoVerificarActionPerformed(null);
            prog = new Fluxogram(TextPaneCode.getText());
            instruction= prog.getStart();
            SelectCodeLine(instruction.GetCharNum());
            this.tpUnderCodeEditor.setSelectedIndex(0);
            console.Clear();
        }catch (Exception e){
            SelectErrorLine(instruction.GetCharNum());
            Message.Error("Erro de Execução\n" +
                    "na linha " + instruction.GetText() +
                    "\n ERRO : " + e.getMessage() );
            DeSelectLine(instruction.GetCharNum());
            return;
        }
        
    }//GEN-LAST:event_MenuAlgoritmoPassoIniciarActionPerformed
    
    private void MenuProgramaPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuProgramaPararActionPerformed
        if( prog == null) return;
        prog= null;
        autoExecute = null;
        if(instruction != null)
            DeSelectLine(instruction.GetCharNum());
        this.tpUnderCodeEditor.setSelectedIndex(1);
        info.write("\nAlgoritmo parado pelo utilizador");
    }//GEN-LAST:event_MenuProgramaPararActionPerformed
    
    private void ButtonProgramaPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaPararActionPerformed
        MenuProgramaPararActionPerformed(evt);
    }//GEN-LAST:event_ButtonProgramaPararActionPerformed
    
    private void ButtonProgramaCorrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaCorrerActionPerformed
        MenuAlgoritmoCorrerActionPerformed(evt);
    }//GEN-LAST:event_ButtonProgramaCorrerActionPerformed
    
    private void MenuAlgoritmoCorrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAlgoritmoCorrerActionPerformed
        
        NodeInstruction instruction = null;
        //se estiver a ser executado não faz nada
        if ( autoExecute != null)
            return ;
        
        try{
            //verificar o programa e construir o fluxograma
            MenuAlgoritmoVerificarActionPerformed(null);
            prog = new Fluxogram(TextPaneCode.getText());
            instruction= prog.getStart();
        }catch (Exception e){
            if( instruction != null){
                SelectErrorLine(instruction.GetCharNum());
                Message.Error("Erro de Execução\n" +
                        "na linha " + instruction.GetText() +
                        "\n ERRO : " + e.getMessage() );
                DeSelectLine(instruction.GetCharNum());
            }
            return;
        }
        info.write("\nGuardar Algoritmo :" +  fileManager.getFileName());
        // this.MenuFicheiroGuardarActionPerformed(null);
        
        console.Clear();
        //mostrar a consola
        tpUnderCodeEditor.setSelectedIndex(0);
        
        //------------------- fazer uma thread -------------------------
        try{
            info.write("\nA executar o Algoritmo : . . . ");
            info.write("\n\t\t\t\t\t"+ (new Date()).toString() );
            this.autoExecute = new Thread(this);
            this.autoExecute.start();
            //    this.btRun.setEnabled(false);
        }catch(Exception e){
            Message.Error("ERRO de EXECUÇAO:\n" + instruction.GetText()+
                    "\n" +e.getMessage());
            this.tpUnderCodeEditor.setSelectedIndex(1);
            info.write("\n\nO algoritmo foi abortado :-( ");
            //   this.btRun.setEnabled(true);
        }
    }//GEN-LAST:event_MenuAlgoritmoCorrerActionPerformed
    
    private void ButtonProgramaVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaVerificarActionPerformed
        this.MenuAlgoritmoVerificarActionPerformed(null);
    }//GEN-LAST:event_ButtonProgramaVerificarActionPerformed
    
    private void MenuAlgoritmoVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAlgoritmoVerificarActionPerformed
        try{
            this.tpUnderCodeEditor.setSelectedIndex(1);
            info.Clear();
            info.write("\n\t\t\t\t\t"+ (new Date()).toString() );
            info.write("\nAlgoritmo :" +  fileManager.getFileName());
            info.write("\nA Verificar o Algoritmo . . .");
            //   btClearBackGroundActionPerformed(null);
            prog = new Fluxogram(TextPaneCode.getText());
            info.write("\nAlgoritmo Ok.\n");
        }catch (LanguageException e){
            SelectErrorLine(e.line);
            Message.CompileError(e);
        }
    }//GEN-LAST:event_MenuAlgoritmoVerificarActionPerformed
    
    private void ButtonProgramaCorrerMonitorarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonProgramaCorrerMonitorarActionPerformed
        this.MenuAlgoritmoMonitorarActionPerformed(evt);
    }//GEN-LAST:event_ButtonProgramaCorrerMonitorarActionPerformed
    
    private void MenuAlgoritmoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAlgoritmoActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_MenuAlgoritmoActionPerformed
    
    private void ButtonEditarReformatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarReformatarActionPerformed
        MenuEditarReformatarActionPerformed(null);
    }//GEN-LAST:event_ButtonEditarReformatarActionPerformed
    
    private void MenuEditarReformatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditarReformatarActionPerformed
        int initPosCaret = TextPaneCode.getCaretPosition();
        String newCode = BeautifyCode.IndentCode(TextPaneCode.getText());
        TextPaneCode.setText(newCode);
        txtCode.clearTextBackground();
        TextPaneCode.setCaretPosition(initPosCaret);
    }//GEN-LAST:event_MenuEditarReformatarActionPerformed
    
    private void ButtonEditarCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarCortarActionPerformed
        this.MenuEditarCortarActionPerformed(null);
    }//GEN-LAST:event_ButtonEditarCortarActionPerformed
    
    private void ButtonEditarColarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarColarActionPerformed
        this.MenuEditarColarActionPerformed(null);
    }//GEN-LAST:event_ButtonEditarColarActionPerformed
    
    private void ButtonEditarCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarCopiarActionPerformed
        this.MenuEditarCopiarActionPerformed(null);
    }//GEN-LAST:event_ButtonEditarCopiarActionPerformed
    
    private void ButtonEditarAvancarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarAvancarActionPerformed
        urManager.redoAction();
    }//GEN-LAST:event_ButtonEditarAvancarActionPerformed
    
    private void ButtonEditarRecuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarRecuarActionPerformed
        urManager.undoAction();
    }//GEN-LAST:event_ButtonEditarRecuarActionPerformed
    
    private void MenuEditarRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditarRedoActionPerformed
        urManager.redo();
    }//GEN-LAST:event_MenuEditarRedoActionPerformed
    
    private void MenuEditarUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditarUndoActionPerformed
        urManager.undo();
    }//GEN-LAST:event_MenuEditarUndoActionPerformed
    
    private void MenuEditarCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditarCortarActionPerformed
        TextPaneCode.cut();
    }//GEN-LAST:event_MenuEditarCortarActionPerformed
    
    private void MenuEditarColarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditarColarActionPerformed
        TextPaneCode.paste();
    }//GEN-LAST:event_MenuEditarColarActionPerformed
    
    private void MenuEditarCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEditarCopiarActionPerformed
        TextPaneCode.copy();
    }//GEN-LAST:event_MenuEditarCopiarActionPerformed
    
    private void TextPaneCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextPaneCodeKeyPressed
        try {
            if( evt.VK_F1 == evt.getKeyCode() ){
                tpUnderCodeEditor.setSelectedIndex(2);
                int caretPosition = TextPaneCode.getCaretPosition();
                int startIndex = javax.swing.text.Utilities.getWordStart(TextPaneCode, caretPosition );
                int endIndex   = javax.swing.text.Utilities.getWordEnd(TextPaneCode, caretPosition );
                String keyword = TextPaneCode.getDocument().getText( startIndex, endIndex-startIndex ).trim();
                helpLang.setPage( HelpFileName.getHelpFile(keyword) );
            }
            //------------ Identação automatica-------------------------------
            if( evt.VK_TAB == evt.getKeyCode() ){
                // introduzir a TAB
                int caretPosition = TextPaneCode.getCaretPosition();
                txtCode.insertString(caretPosition, BeautifyCode.TAB_SPACES, new SimpleAttributeSet());
                // consumir o enter
                evt.consume();
                //------------ Identação automatica-------------------------------
            } else if( evt.VK_ENTER == evt.getKeyCode() ){
                // ir buscar o paragrafo actual
                int caretPosition = TextPaneCode.getCaretPosition();
                Element element = txtCode.getParagraphElement( caretPosition );
                int start = element.getStartOffset();
                int end = element.getEndOffset();
                String old = txtCode.getText(start,end - start  );
                // contruir uma string com o enter e espaços
                int spaces=0;
                String tab="\n";
                while( old.charAt(spaces) == ' '){
                    spaces++;
                    tab += " ";
                }
                // introduzir a nova string
                txtCode.insertString(caretPosition, tab, new SimpleAttributeSet());
                // consumir o enter
                evt.consume();
            }
        }catch(Exception e){}
    }//GEN-LAST:event_TextPaneCodeKeyPressed
    
    private void ButtonFicheiroGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonFicheiroGuardarActionPerformed
        this.MenuFicheiroGuardarActionPerformed(null);
    }//GEN-LAST:event_ButtonFicheiroGuardarActionPerformed
    
    private void ButtonFicheiroAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonFicheiroAbrirActionPerformed
        this.MenuFicheiroAbrir1ActionPerformed(null);
    }//GEN-LAST:event_ButtonFicheiroAbrirActionPerformed
    
    private void ButtonFicheiroNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonFicheiroNovoActionPerformed
        this.MenuFicheiroNovo1ActionPerformed(null);
    }//GEN-LAST:event_ButtonFicheiroNovoActionPerformed
    
    private void MenuFicheiroGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroGuardarComoActionPerformed
        GuardarFicheiroComo( fileManager.getFileName());
    }//GEN-LAST:event_MenuFicheiroGuardarComoActionPerformed
    
    private void MenuFicheiroGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroGuardarActionPerformed
        
        if( ! fileManager.isFileOpened() )
            GuardarFicheiroComo( fileManager.getFileName());
        else
            GuardarFicheiro( fileManager.getFileName());
    }//GEN-LAST:event_MenuFicheiroGuardarActionPerformed
    
    private void MenuFicheiroAberto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroAberto3ActionPerformed
        LerFicheiro(MenuFicheiroAberto3.getText());
    }//GEN-LAST:event_MenuFicheiroAberto3ActionPerformed
    
    private void MenuFicheiroAberto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroAberto2ActionPerformed
        LerFicheiro(MenuFicheiroAberto2.getText());
    }//GEN-LAST:event_MenuFicheiroAberto2ActionPerformed
    
    private void MenuFicheiroAberto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroAberto1ActionPerformed
        LerFicheiro(MenuFicheiroAberto1.getText());
    }//GEN-LAST:event_MenuFicheiroAberto1ActionPerformed
    
    private void TextPaneCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextPaneCodeKeyTyped
        if( ! this.getTextChanged()){
            setTextChanged(true);
        }
    }//GEN-LAST:event_TextPaneCodeKeyTyped
    
    private void MenuFicheiroNovo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroNovo1ActionPerformed
        if( getTextChanged() ) // o texto foi alterado se sim
        {
            int action = Message.Confirm("Deseja salvar o algoritmo Actual\n" + fileManager.getFileName());
            if( action == JOptionPane.CANCEL_OPTION )
                return;
            if( action == JOptionPane.YES_OPTION )
                this.MenuFicheiroGuardarActionPerformed(null);
        }
        NovoFicheiro();
    }//GEN-LAST:event_MenuFicheiroNovo1ActionPerformed
    
    private void MenuFicheiroSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroSair1ActionPerformed
        //guardar as propriedades
        editorProperties.Save();
        //libertar os recutrsos
        this.dispose();
    }//GEN-LAST:event_MenuFicheiroSair1ActionPerformed
    
    private void MenuFicheiroAbrir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFicheiroAbrir1ActionPerformed
        if( fileManager.openFileWindow(this) != null ){
            LerFicheiro(fileManager.getFileName());
        }
    }//GEN-LAST:event_MenuFicheiroAbrir1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorPTG().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonEditarAvancar;
    private javax.swing.JButton ButtonEditarColar;
    private javax.swing.JButton ButtonEditarCopiar;
    private javax.swing.JButton ButtonEditarCortar;
    private javax.swing.JButton ButtonEditarRecuar;
    private javax.swing.JButton ButtonEditarReformatar;
    private javax.swing.JButton ButtonFicheiroAbrir;
    private javax.swing.JButton ButtonFicheiroGuardar;
    private javax.swing.JButton ButtonFicheiroNovo;
    private javax.swing.JButton ButtonProgramaCorrer;
    private javax.swing.JButton ButtonProgramaCorrerMonitorar;
    private javax.swing.JButton ButtonProgramaCorrerPassoContinuar;
    private javax.swing.JButton ButtonProgramaCorrerPassoIniciar;
    private javax.swing.JButton ButtonProgramaFluxograma;
    private javax.swing.JButton ButtonProgramaParar;
    private javax.swing.JButton ButtonProgramaVerificar;
    private javax.swing.JMenu MenuAjuda;
    private javax.swing.JMenuItem MenuAjudaAcerca;
    private javax.swing.JMenu MenuAlgoritmo;
    private javax.swing.JMenuItem MenuAlgoritmoCorrer;
    private javax.swing.JMenuItem MenuAlgoritmoMonitorar;
    private javax.swing.JMenuItem MenuAlgoritmoPassoContinuar;
    private javax.swing.JMenuItem MenuAlgoritmoPassoIniciar;
    private javax.swing.JMenuItem MenuAlgoritmoVerificar;
    private javax.swing.JMenu MenuEditar1;
    private javax.swing.JMenuItem MenuEditarColar;
    private javax.swing.JMenuItem MenuEditarCopiar;
    private javax.swing.JMenuItem MenuEditarCortar;
    private javax.swing.JMenuItem MenuEditarRedo;
    private javax.swing.JMenuItem MenuEditarReformatar;
    private javax.swing.JMenuItem MenuEditarUndo;
    private javax.swing.JMenu MenuEditor;
    private javax.swing.JCheckBoxMenuItem MenuEditorCheckSyntax;
    private javax.swing.JMenuItem MenuEditorFonte;
    private javax.swing.JMenu MenuFicheiro1;
    private javax.swing.JMenuItem MenuFicheiroAberto1;
    private javax.swing.JMenuItem MenuFicheiroAberto2;
    private javax.swing.JMenuItem MenuFicheiroAberto3;
    private javax.swing.JMenuItem MenuFicheiroAbrir1;
    private javax.swing.JMenuItem MenuFicheiroGuardar;
    private javax.swing.JMenuItem MenuFicheiroGuardarComo;
    private javax.swing.JMenuItem MenuFicheiroNovo1;
    private javax.swing.JMenuItem MenuFicheiroSair1;
    private javax.swing.JMenuItem MenuProgramaParar;
    private javax.swing.JTextPane TextPaneCode;
    private javax.swing.JToolBar TooBarEditar;
    private javax.swing.JToolBar ToolBarFicheiro;
    private javax.swing.JToolBar ToolBarPrincipal;
    private javax.swing.JToolBar ToolBarPrograma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JScrollPane scrollCodeEditor;
    private javax.swing.JScrollPane scrollInfo;
    private javax.swing.JScrollPane scrollMonitor;
    private javax.swing.JScrollPane spInfo;
    private javax.swing.JScrollPane spLinguagem;
    private javax.swing.JScrollPane spOutput;
    private javax.swing.JTabbedPane tpUnderCodeEditor;
    // End of variables declaration//GEN-END:variables
    
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
    private void setTextChanged( boolean flag){
        textChanged = flag;
        if( flag)
            this.setTitle( this.getTitle() + "*");
    }
    
    private boolean getTextChanged(){
        return textChanged;
    }
    private void ActualizarMenuFicheiro(String newFile){
        this.editorProperties.SetLoadFileName(newFile);
        MenuFicheiroAberto1.setText( editorProperties.GetProperty("file1"));
        MenuFicheiroAberto2.setText( editorProperties.GetProperty("file2"));
        MenuFicheiroAberto3.setText( editorProperties.GetProperty("file3"));
        editorProperties.Save();
    }
    
    private void NovoFicheiro(){
        fileManager.clearFileName();
        textChanged=false;
        TextPaneCode.setText("");
        this.setTitle( this.TITLE + " - " + fileManager.getFileName());
    }
    //---------------------------------------------------------------------------
    public void LerFicheiro(String fileName) {
        if(fileManager.FileExists(fileName)){
            TextPaneCode.setText( fileManager.ReadFile(fileName));
            //colocar o cursor no inicio do texto
            TextPaneCode.setCaretPosition(1);
            // actualizar o menu
            ActualizarMenuFicheiro(fileName);
            textChanged = false;
            this.setTitle( this.TITLE + " - " + fileManager.getFileName());
        }
    }
    //---------------------------------------------------------------------------
    public void GuardarFicheiro(String fileName) {
        //se conseguir salvar
        if( fileManager.saveFileUpdate(TextPaneCode.getText()) ){
            textChanged = false;
            this.setTitle( this.TITLE + " - " + fileManager.getFileName());
        }
    }
    //---------------------------------------------------------------------------
    public void GuardarFicheiroComo(String fileName) {
        //se conseguir salvar
        if(fileManager.saveFile(this,fileManager.getFileName(), TextPaneCode.getText())){
            textChanged = false;
            this.setTitle( this.TITLE + " - " + fileManager.getFileName());
            ActualizarMenuFicheiro(fileName);
        }
    }
    
}
