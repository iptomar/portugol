package AlgolXXI.Editor;

import AlgolXXI.Core.Converters.JavaConverter;
import AlgolXXI.Editor.CodeEditor.PopupMenu;
import AlgolXXI.Editor.CodeEditor.TextTransfer;
import AlgolXXI.Editor.CodeEditor.LinePainter;
import AlgolXXI.Editor.CodeEditor.AlgolXXITextPane;
import AlgolXXI.Editor.Fluxogramas.FluxogramaPanel;
import AlgolXXI.Core.Execute.ConsoleIO;
import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Editor.CodeEditor.AlgolXXIEditorKit;
import AlgolXXI.Editor.Help.BrowserPanel;
import AlgolXXI.Editor.Memory.MemoryDisplay;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.awt.UndoRedo;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import AlgolXXI.Core.Converters.Converter;
import AlgolXXI.Editor.CodeEditor.PortugolStyle;
import AlgolXXI.Editor.Fluxogramas.Fluxogram;
import AlgolXXI.Editor.Fluxogramas.VisualNode;
import java.io.PrintWriter;
import AlgolXXI.Core.Make.MakeProgram;
import AlgolXXI.Core.Execute.ExecuteProgram;
import AlgolXXI.Core.NodeFluxo;
import AlgolXXI.Core.Parser.Keyword;
import java.awt.BorderLayout;
import javax.swing.tree.DefaultTreeModel;
import org.jgraph.graph.DefaultGraphModel;

/**
 * Topcomponent
 * @author Apocas
 */
public class ProgramaTopComponent extends TopComponent implements Runnable {

    static final String ICON_PATH = "AlgolXXI/Editor/Images/Computer_File_053.gif";
    private ConsoleIO console;
    private ConsoleIO console_info;
    private static ProgramaTopComponent instance;
    private static final String PREFERRED_ID = "ProgramaTopComponent";
    private UndoRedo.Manager manager = new UndoRedo.Manager();
    private JFileChooser chooser;
    //---
    private ResourceBundle bundle;
    private Locale currentLocale;
    private AlgolXXITextPane codigo;
    private Boolean executing = false;
    private Boolean thread_pause = true;
    private Thread threadprincipal;
    private int timer = 0;
    private int contador_geral = 0;
    private int last_tab = 0;
    private AlgolXXIEditorKit editorKit;
    private ExecuteProgram executor_editor = null;
    private MakeProgram programa = null;
    private boolean flag_fluxograma = false;
    private boolean dialog_opened = false;
    private boolean saved = false;
    private String save_location = null;
    private BrowserPanel ajuda_panel;
    private Font font = new Font("Helvetica", Font.BOLD, 13);
    private Converter converter = null;
    private FluxogramaPanel fluxograma_visual;
    private double scale = 1.0;
    private boolean stepbystep;
    private NodeFluxo runningnode;

    /**
     * Constructor do topcomponent 
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public ProgramaTopComponent() throws ParserConfigurationException {
        currentLocale = Locale.getDefault();
        System.out.println("LINGUA EDITOR: " + currentLocale.toString());

        bundle = ResourceBundle.getBundle("AlgolXXI/Editor/Bundle", currentLocale);

        initComponents();
        initMyComponents();
        setName(NbBundle.getMessage(ProgramaTopComponent.class, "CTL_ProgramaTopComponent"));
        setToolTipText(NbBundle.getMessage(ProgramaTopComponent.class, "HINT_ProgramaTopComponent"));
        setIcon(Utilities.loadImage(ICON_PATH, true));

        //codigo.getDocument().addUndoableEditListener(manager);

        new PopupMenu(this, codigo);

        this.requestFocusInWindow(true);
        this.requestActive();

        initMyComponentsAfter();
    }

    public ExecuteProgram getExecutor() {
        return executor_editor;
    }

    /**
     * Primeiro inicializador
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public void initMyComponents() throws ParserConfigurationException {
        console = new ConsoleIO();
        console_info = new ConsoleIO();

        codigo = new AlgolXXITextPane();
        scroll_codigo.setViewportView(codigo);
        scroll_consola.setViewportView(getConsole());
        scroll_info.setViewportView(getConsole_info());

        new LinePainter(codigo, new Color(214, 223, 237));

        getConsole().write("Portugol v3");
        getConsole_info().write("Portugol v3");
        getConsole_info().write("\n\nArquitectura Comp.: " + System.getProperty("os.arch"));
        getConsole_info().write("\nUtilizador          : " + System.getProperty("user.name"));
        getConsole_info().write("\nSistema Operativo   : " + System.getProperty("os.name") + " versão " + System.getProperty("os.version"));
        getConsole_info().write("\nLocal de Execução   : " + System.getProperty("user.dir"));
        getConsole_info().write("\nLocal do Utilizador : " + System.getProperty("user.home"));
        getConsole_info().write("\nVersão do Java      : " + System.getProperty("java.version") + " : " + System.getProperty("java.home") + "\n");

        ajuda_panel = new BrowserPanel("AlgolXXI/Editor/Help/Contents/index.html");
        panel_ajuda.add(ajuda_panel);
    }

    @Override
    public UndoRedo getUndoRedo() {
        return manager;
    }

    public void convert2Java() {
        try {
            converter = new JavaConverter(codigo.getText());
            if (tabpane.indexOfTab("Java") == -1) {
                TextoPanel panel1 = new TextoPanel(this, AlgolXXI.Editor.Utils.EditorUtils.JAVA);
                String java_code = converter.convert();
                panel1.setTexto(java_code);
                tabpane.addTab("Java", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/text_uppercase.png")), panel1, "Java");
            } else {
                TextoPanel panel1 = (TextoPanel) tabpane.getComponentAt(tabpane.indexOfTab("Java"));
                panel1.setCompiled(false);
                panel1.setTexto(converter.convert());
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        if (tabpane.getSelectedIndex() != tabpane.indexOfTab("Java")) {
            tabpane.setSelectedIndex(tabpane.indexOfTab("Java"));
        }
    }

    /**
     * Segundo inicializador, chamado no final do primeiro
     */
    private void initMyComponentsAfter() {
        String lang = currentLocale.toString();
        if (lang.length() > 2) {
            lang = lang.substring(0, 2).toUpperCase();
        }

        editorKit = new AlgolXXIEditorKit(manager);
        codigo.setEditorKit(editorKit);

        try {
            codigo.setText(AlgolXXI.Core.Utils.FileManager.getSource("SemNome.alg"));
        } catch (Exception ex) {
            codigo.setText("inicio programa\n"
                    + "escrever \"ola mundo\"\n"
                    + "fim programa\n");
        }

        manager.discardAllEdits();

        separador_secundario1.setDividerLocation(0.0);
        separador_principal.setDividerLocation(0.65);

        under_tabpane.setSelectedIndex(under_tabpane.indexOfTab("Informações"));
    }

    /**
     * Método que permite copiar texto para o clipboard.
     */
    public void call_copiar() {
        if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Fluxograma"))) {
            //getSelectedGraph().copy2Clipboard();
        } else if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Codigo"))) {
            new TextTransfer().setClipboardContents(codigo.getSelectedText());
        }
    }

    /**
     * Método que permite colar texto vindo do clipboard.
     */
    public void call_colar() {
        if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Fluxograma"))) {
            //past no fluxograma
        } else if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Codigo"))) {
            try {
                codigo.getDocument().insertString(codigo.getCaretPosition(), new TextTransfer().getClipboardContents(), codigo.getDocument().getDefaultRootElement().getAttributes().copyAttributes());
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    public void setFontFluxogramas(Font fontf) {
        for (Component fluxo : fluxos.getComponents()) {
            if (fluxo instanceof FluxogramaPanel) {
                ((FluxogramaPanel) fluxo).setFontFluxograma(fontf);
            }
        }
        this.font = fontf;
    }

    public Font getFontFluxogramas() {
        return this.font;
    }

    private Fluxogram getSelectedGraph() {
        if (fluxos.getSelectedComponent() instanceof FluxogramaPanel) {
            return ((FluxogramaPanel) fluxos.getSelectedComponent()).getFluxograma();
        }
        return null;
    }

    private FluxogramaPanel getSelectedGraphPane() {
        if (fluxos.getSelectedComponent() instanceof FluxogramaPanel) {
            return (FluxogramaPanel) fluxos.getSelectedComponent();
        }
        return null;
    }

    private void rebuildFluxo() {
        scale = getSelectedGraph().getScale();
        getSelectedGraph().setScale(scale + 0.1);
        getSelectedGraph().setScale(scale);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    separador_principal = new javax.swing.JSplitPane();
    underpanel = new javax.swing.JPanel();
    under_tabpane = new javax.swing.JTabbedPane();
    scroll_info = new javax.swing.JScrollPane();
    scroll_consola = new javax.swing.JScrollPane();
    panel_ajuda = new javax.swing.JPanel();
    label_file = new javax.swing.JLabel();
    label_status = new javax.swing.JLabel();
    upperpanel = new javax.swing.JPanel();
    separador_secundario1 = new javax.swing.JSplitPane();
    left_panel = new javax.swing.JPanel();
    output_tabpane = new javax.swing.JTabbedPane();
    output_panel = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    execucao = new javax.swing.JTextArea();
    panel_memoria = new javax.swing.JPanel();
    memorias = new javax.swing.JTabbedPane();
    right_panel = new javax.swing.JPanel();
    tabpane = new javax.swing.JTabbedPane();
    codigoPanel = new javax.swing.JPanel();
    scroll_codigo = new javax.swing.JScrollPane();
    fluxogramaPanel = new javax.swing.JPanel();
    fluxos = new javax.swing.JTabbedPane();
    panel_botoes = new javax.swing.JPanel();
    botao_zoom_menos = new javax.swing.JButton();
    botao_zoom_mais = new javax.swing.JButton();
    botao_zoom_centro = new javax.swing.JButton();
    botao_rebuild = new javax.swing.JButton();
    botao_export = new javax.swing.JButton();

    setDisplayName(bundle.getString("Programa") + "1.alg");
    setName("Programa"); // NOI18N
    setLayout(new java.awt.BorderLayout());

    separador_principal.setDividerLocation(300);
    separador_principal.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

    under_tabpane.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        under_tabpaneStateChanged(evt);
      }
    });

    scroll_info.setAutoscrolls(true);
    under_tabpane.addTab("Informações", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/informacoes.png")), scroll_info, "Informações"); // NOI18N

    scroll_consola.setAutoscrolls(true);
    under_tabpane.addTab("Consola", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/console.png")), scroll_consola, "Consola"); // NOI18N

    panel_ajuda.setLayout(new java.awt.BorderLayout());
    under_tabpane.addTab("Ajuda", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/help.png")), panel_ajuda, "Ajuda"); // NOI18N

    label_file.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    org.openide.awt.Mnemonics.setLocalizedText(label_file, "  Ficheiro sem gravacao. ");

    label_status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    org.openide.awt.Mnemonics.setLocalizedText(label_status, "Estado: Parado ");
    label_status.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

    org.jdesktop.layout.GroupLayout underpanelLayout = new org.jdesktop.layout.GroupLayout(underpanel);
    underpanel.setLayout(underpanelLayout);
    underpanelLayout.setHorizontalGroup(
      underpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(underpanelLayout.createSequentialGroup()
        .add(label_file, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 637, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(label_status, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
      .add(under_tabpane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
    );
    underpanelLayout.setVerticalGroup(
      underpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(underpanelLayout.createSequentialGroup()
        .add(underpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
          .add(label_status)
          .add(label_file, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(under_tabpane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
    );

    separador_principal.setBottomComponent(underpanel);

    org.jdesktop.layout.GroupLayout upperpanelLayout = new org.jdesktop.layout.GroupLayout(upperpanel);
    upperpanel.setLayout(upperpanelLayout);
    upperpanelLayout.setHorizontalGroup(
      upperpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 0, Short.MAX_VALUE)
    );
    upperpanelLayout.setVerticalGroup(
      upperpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 0, Short.MAX_VALUE)
    );

    separador_principal.setTopComponent(upperpanel);

    separador_secundario1.setDividerLocation(200);
    separador_secundario1.setDividerSize(7);
    separador_secundario1.setOneTouchExpandable(true);

    output_tabpane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
    output_tabpane.setAutoscrolls(true);

    execucao.setEditable(false);
    execucao.setBackground(javax.swing.UIManager.getDefaults().getColor("EditorPane.disabledBackground"));
    execucao.setColumns(20);
    execucao.setRows(5);
    jScrollPane3.setViewportView(execucao);

    org.jdesktop.layout.GroupLayout output_panelLayout = new org.jdesktop.layout.GroupLayout(output_panel);
    output_panel.setLayout(output_panelLayout);
    output_panelLayout.setHorizontalGroup(
      output_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 194, Short.MAX_VALUE)
      .add(output_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
    );
    output_panelLayout.setVerticalGroup(
      output_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 267, Short.MAX_VALUE)
      .add(output_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
    );

    output_tabpane.addTab("Execução", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/execucao.png")), output_panel, "Execucao"); // NOI18N

    org.jdesktop.layout.GroupLayout panel_memoriaLayout = new org.jdesktop.layout.GroupLayout(panel_memoria);
    panel_memoria.setLayout(panel_memoriaLayout);
    panel_memoriaLayout.setHorizontalGroup(
      panel_memoriaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(memorias, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
    );
    panel_memoriaLayout.setVerticalGroup(
      panel_memoriaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(memorias, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
    );

    output_tabpane.addTab("Memoria", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/memoria.gif")), panel_memoria, "Memoria"); // NOI18N

    org.jdesktop.layout.GroupLayout left_panelLayout = new org.jdesktop.layout.GroupLayout(left_panel);
    left_panel.setLayout(left_panelLayout);
    left_panelLayout.setHorizontalGroup(
      left_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 199, Short.MAX_VALUE)
      .add(left_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(output_tabpane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
    );
    left_panelLayout.setVerticalGroup(
      left_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 297, Short.MAX_VALUE)
      .add(left_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(output_tabpane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
    );

    separador_secundario1.setLeftComponent(left_panel);

    right_panel.setLayout(new java.awt.BorderLayout());

    tabpane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
    tabpane.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        tabpaneStateChanged(evt);
      }
    });

    codigoPanel.setLayout(new java.awt.BorderLayout());
    codigoPanel.add(scroll_codigo, java.awt.BorderLayout.CENTER);

    tabpane.addTab(bundle.getString("Codigo"), new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/codigo.png")), codigoPanel, "Codigo"); // NOI18N

    fluxogramaPanel.setLayout(new java.awt.BorderLayout());
    fluxogramaPanel.add(fluxos, java.awt.BorderLayout.CENTER);

    panel_botoes.setPreferredSize(new java.awt.Dimension(40, 100));

    botao_zoom_menos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/minus.png"))); // NOI18N
    botao_zoom_menos.setToolTipText("Zoom Out");
    botao_zoom_menos.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botao_zoom_menosActionPerformed(evt);
      }
    });

    botao_zoom_mais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/add.png"))); // NOI18N
    botao_zoom_mais.setToolTipText("Zoom In");
    botao_zoom_mais.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botao_zoom_maisActionPerformed(evt);
      }
    });

    botao_zoom_centro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/center.png"))); // NOI18N
    botao_zoom_centro.setToolTipText("Centrar Fluxograma");
    botao_zoom_centro.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botao_zoom_centroActionPerformed(evt);
      }
    });

    botao_rebuild.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/rebuild.png"))); // NOI18N
    botao_rebuild.setToolTipText("Rebuild");
    botao_rebuild.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botao_rebuildActionPerformed(evt);
      }
    });

    botao_export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/gravar_fluxo.png"))); // NOI18N
    botao_export.setToolTipText("Exportar para JPEG");
    botao_export.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botao_exportActionPerformed(evt);
      }
    });

    org.jdesktop.layout.GroupLayout panel_botoesLayout = new org.jdesktop.layout.GroupLayout(panel_botoes);
    panel_botoes.setLayout(panel_botoesLayout);
    panel_botoesLayout.setHorizontalGroup(
      panel_botoesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(panel_botoesLayout.createSequentialGroup()
        .add(panel_botoesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER, false)
          .add(botao_zoom_mais, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(botao_zoom_menos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(botao_zoom_centro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(botao_rebuild, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(botao_export, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    panel_botoesLayout.linkSize(new java.awt.Component[] {botao_export, botao_rebuild, botao_zoom_centro, botao_zoom_mais, botao_zoom_menos}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

    panel_botoesLayout.setVerticalGroup(
      panel_botoesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(panel_botoesLayout.createSequentialGroup()
        .add(29, 29, 29)
        .add(botao_zoom_mais)
        .add(5, 5, 5)
        .add(botao_zoom_menos)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(botao_zoom_centro)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(botao_rebuild)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(botao_export)
        .addContainerGap(90, Short.MAX_VALUE))
    );

    panel_botoesLayout.linkSize(new java.awt.Component[] {botao_export, botao_rebuild, botao_zoom_centro, botao_zoom_mais, botao_zoom_menos}, org.jdesktop.layout.GroupLayout.VERTICAL);

    fluxogramaPanel.add(panel_botoes, java.awt.BorderLayout.EAST);

    tabpane.addTab(bundle.getString("Fluxograma"), new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/fluxograma.png")), fluxogramaPanel, "Fluxograma"); // NOI18N

    right_panel.add(tabpane, java.awt.BorderLayout.CENTER);

    separador_secundario1.setRightComponent(right_panel);

    separador_principal.setTopComponent(separador_secundario1);

    add(separador_principal, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents
    private void tabpaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabpaneStateChanged
        if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Codigo"))) {
            if (last_tab == tabpane.indexOfTab(bundle.getString("Fluxograma"))) {
                //codigo.requestFocus();
                //codigo.updateUI();
            }
        } else if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Fluxograma"))) {
            if (fluxos.getComponents().length < 1 && isRunning() == false) {
                buildFluxograms();
                updateFluxos();
            }
        } else if (tabpane.getSelectedIndex() == tabpane.indexOfTab("Java")) {
            this.convert2Java();
        } else if (tabpane.getSelectedIndex() == tabpane.indexOfTab("C++")) {
            //this.convert2C();
        }
        last_tab = tabpane.getSelectedIndex();
    }//GEN-LAST:event_tabpaneStateChanged

    /**
     * Zoom Out
     * @param evt
     */
private void botao_zoom_menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_zoom_menosActionPerformed
    getSelectedGraph().setScale(getSelectedGraph().getScale() / 1.1);
    scale = getSelectedGraph().getScale() / 1.1;
}//GEN-LAST:event_botao_zoom_menosActionPerformed

    /**
     * Zoom In
     * @param evt
     */
private void botao_zoom_maisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_zoom_maisActionPerformed
    getSelectedGraph().setScale(getSelectedGraph().getScale() * 1.1);
    scale = getSelectedGraph().getScale() * 1.1;
}//GEN-LAST:event_botao_zoom_maisActionPerformed

    /**
     * Zoom para o fluxograma caber na janela de visualizacao.
     * @param evt
     */
private void botao_zoom_centroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_zoom_centroActionPerformed
    scale = getSelectedGraphPane().centrar();
}//GEN-LAST:event_botao_zoom_centroActionPerformed

private void botao_rebuildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_rebuildActionPerformed
    rebuildFluxo();
}//GEN-LAST:event_botao_rebuildActionPerformed

private void botao_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_exportActionPerformed
    String pathf = getSaveLocation("jpg");
    if (pathf != null && !pathf.equals("")) {
        //getSelectedGraph().save2JPG(pathf);
    }
}//GEN-LAST:event_botao_exportActionPerformed

private void under_tabpaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_under_tabpaneStateChanged
    if (under_tabpane.getSelectedIndex() == under_tabpane.indexOfTab("Ajuda")) {
        separador_principal.setDividerLocation(0.4);
    } else {
        separador_principal.setDividerLocation(0.65);
    }
}//GEN-LAST:event_under_tabpaneStateChanged
    /**
     * Apaga a widget selecionada
     */
    public void apaga() {
        DefaultTreeModel tree_model = ((MemoryDisplay) memorias.getSelectedComponent()).getModel();

        DefaultGraphModel graph_model = new DefaultGraphModel();

        FluxogramaPanel memoria_fluxograma_visual = new FluxogramaPanel(this, graph_model);
        fluxos.addTab("Memoria", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/fluxos.png")), memoria_fluxograma_visual);
    }

    private String getSaveLocation(String terminacao) {
        File ficheiro = null;

        AlgolFileFilter filter = new AlgolFileFilter();
        filter.addExtension(terminacao);
        filter.setDescription("AlgolXXI");
        chooser = new JFileChooser();
        chooser.setFileFilter(filter);


        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ficheiro = chooser.getSelectedFile();

            while (AlgolXXI.Editor.Utils.EditorUtils.containsChars(ficheiro.getName(), new char[]{'\\', '/', '*', '?', '<', '>', '|', '.'}) && ficheiro.getName().length() > 0) {
                JOptionPane.showMessageDialog(null, bundle.getString("Caracter_invalido"));
                filter = new AlgolFileFilter();
                filter.addExtension(terminacao);
                filter.setDescription("AlgolXXI");
                chooser = new JFileChooser();
                chooser.setFileFilter(filter);
                returnVal = chooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    ficheiro = chooser.getSelectedFile();
                } else {
                    ficheiro = null;
                    break;
                }
            }
        }

        if (ficheiro != null) {
            String str_ficheiro = "";
            if (!ficheiro.toString().contains(".alg") && !ficheiro.toString().contains(".cpp") && !ficheiro.toString().contains(".java") && !ficheiro.toString().contains(".jpg")) {
                str_ficheiro = ficheiro.toString() + "." + terminacao;
            } else {
                str_ficheiro = ficheiro.toString();
            }
            if (terminacao.equals("alg")) {
                if (!ficheiro.toString().contains(".alg")) {
                    this.setDisplayName(ficheiro.getName() + ".alg");
                } else {
                    this.setDisplayName(ficheiro.getName());
                }
            }
            return str_ficheiro;
        }
        return null;
    }

    public void guardar() {
        if (!saved) {
            save_location = getSaveLocation("alg");
            if (save_location == null || save_location.equals("")) {
                return;
            }
        }
        if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Codigo"))) {
            try {
                FileWriter fstream = new FileWriter(save_location);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(codigo.getText());
                out.close();
                saved = true;
                label_file.setText("  Ficheiro: " + save_location);

                getConsole_info().writeLn("Ficheiro Guardado -> " + save_location);
            } catch (Exception e) {
                System.err.println("Erro Guardar: " + e.getMessage());
            }
        }
    }

    public void guardarcomo() {

        save_location = getSaveLocation("alg");
        if (save_location == null || save_location.equals("")) {
            return;
        }

        if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Codigo"))) {
            try {
                FileWriter fstream = new FileWriter(save_location);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(codigo.getText());
                out.close();
                saved = true;
                label_file.setText("  Ficheiro: " + save_location);
                getConsole_info().writeLn("Ficheiro Guardado -> " + save_location);
            } catch (Exception e) {
                System.err.println("Erro Guardar: " + e.getMessage());
            }
        }
    }

    /**
     * Abre e carrega o conteudo dos ficheiros .alg
     * @return -1 se o ficheiro for nulo, -1 se a extensão do ficheiro for diferente de .alg, 1 se as condições anteriores não se verificarem.
     */
    public int abrir() {
        String line = null;
        File ficheiro = null;

        AlgolFileFilter filter = new AlgolFileFilter();
        filter.addExtension("alg");
        filter.setDescription("AlgolXXI");
        chooser = new JFileChooser();
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ficheiro = chooser.getSelectedFile();
        }

        if (ficheiro != null) {
            if (ficheiro.toString().contains(".alg")) {
                try {
                    FileReader fstream = new FileReader(ficheiro);
                    BufferedReader in = new BufferedReader(fstream);
                    codigo.setText("");
                    line = in.readLine();
                    while (line != null) {
                        codigo.setText(codigo.getText() + line + "\n");
                        line = in.readLine();
                    }

                    this.setDisplayName(ficheiro.getName());
                    manager.discardAllEdits();
                    saved = true;
                    save_location = ficheiro.toString();
                    label_file.setText("  Ficheiro: " + save_location);
                } catch (Exception e) {
                    System.err.println("Erro Abrir: " + e.getMessage());
                }

            } else {
                return -1;
            }

        } else {
            return -1;
        }

        return 1;
    }

    public int abrir(String ficheiro_path) throws Exception {
        String line = null;
        File ficheiro = null;

        ficheiro =
                new File(ficheiro_path);

        if (ficheiro != null) {
            if (ficheiro.toString().contains(".alg")) {
                FileReader fstream = new FileReader(ficheiro);
                BufferedReader in = new BufferedReader(fstream);
                codigo.setText("");
                line =
                        in.readLine();
                while (line != null) {
                    codigo.setText(codigo.getText() + line + "\n");
                    line =
                            in.readLine();
                }

                this.setDisplayName(ficheiro.getName());
                manager.discardAllEdits();
            } else {
                return -1;
            }

        } else {
            return -1;
        }

        return 1;
    }

    /**
     * Inicializa o executor e programa
     */
    void buildFluxograms() {
        try {
            programa = new MakeProgram(codigo.getText());
            executor_editor = new ExecuteProgram(programa);


            fluxograma_visual = new FluxogramaPanel(this);
            fluxograma_visual.getFluxograma().setScale(scale);
            fluxos.addTab("Fluxograma", new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/fluxos.png")), fluxograma_visual);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    private void updateFluxos() {
        fluxograma_visual.getFluxograma().updateFluxogram();
    }

    /**
     * inicializa as execucoes
     */
    private void initializeExecutions() {

        fluxos.removeAll();

        under_tabpane.setSelectedIndex(under_tabpane.indexOfTab("Consola"));
        buildFluxograms();
        updateFluxos();
        separador_secundario1.setDividerLocation(200);
        execucao.setText("");

        getConsole().Clear();
        removeMemoryDisplays();

        if (tabpane.getSelectedIndex() == tabpane.indexOfTab(bundle.getString("Fluxograma"))) {
            flag_fluxograma = true;
            tabpane.setSelectedIndex(tabpane.indexOfTab(bundle.getString("Codigo")));
        }

        runningnode = executor_editor.getStart();

        codigo.setEditable(false);

        label_status.setText("Estado: A correr ");
        getConsole().writeLn("- INICIO da Execução! -");
        executing = true;
    }

    /**
     * Call do botao passo-a-passo
     */
    public void call_executa_passo() {
        if (!executing) {
            initializeExecutions();
            if (flag_fluxograma == true) {
                tabpane.setSelectedIndex(tabpane.indexOfTab(bundle.getString("Fluxograma")));
                flag_fluxograma = false;
            }
        }


        this.executa_passo();

    }

    /**
     * Call do botao passo-a-passo por tempo
     */
    public void call_executa_tempo(int t) {
        timer = t;
        stepbystep = false;
//        if (!isThreadRunning()) {
        if (!executing) {
            initializeExecutions();
        }
        if (flag_fluxograma == true) {
            tabpane.setSelectedIndex(tabpane.indexOfTab(bundle.getString("Fluxograma")));
            flag_fluxograma = false;
        }

        threadprincipal = new Thread((Runnable) this);
        threadprincipal.start();

//        }
    }

    /**
     * Método que permite parar a execução do código.
     */
    public void para() {
        this.thread_stop();
        endExecutions();
        contador_geral = 0;
        separador_secundario1.setDividerLocation(0);

        getConsole().StopReading();
        if (executor_editor != null) {
            executor_editor.stopExecution();
        }
        getConsole().Clear();
        label_status.setText("Estado: Parado ");
        getConsole().writeLn("- Stopped! -");
        codigo.requestFocus();
        runningnode = null;
    }

    /**
     * Método que permite efectuar uma pausa na thread.
     */
    public void pause() {
        this.thread_pause();
    }

    /**
     * Método que permite efectuar uma pausa na thread do TopComponent.
     */
    private void thread_pause() {
        timer = 0;
        thread_pause = true;
    }

    /**
     * Call do botao executar.
     */
    public void executa() {
        stepbystep = false;
        timer = -1;
        call_executa_tempo(timer);
    }

    /**
     * Método que executa passo a passo o debug do código implementado.
     */
    public void executa_passo() {
        timer = 0;
        stepbystep = true;

        execute_node(runningnode);
    }

    /**
     * Método que executa um node do fluxograma.
     * É recursivo.
     * @param nodef NodeInstruction
     */
    @SuppressWarnings("static-access")
    private void execute_node(NodeFluxo nodef) {
        try {
            execucao.setText(execucao.getText() + contador_geral + ": " + nodef.getText() + "\n");

            codigo.setCaretPosition(nodef.getCharNum());


            try {
                if (fluxograma_visual != null) {

                    VisualNode visual_node = fluxograma_visual.getFluxograma().highlightNode(nodef);

                    rebuildFluxo();

                    if (visual_node != null) {
                        if (fluxograma_visual.getScrollPane().getVerticalScrollBar().isShowing()) {
                            if ((int) (fluxograma_visual.getFluxograma().getCellBounds(visual_node).getY()) > fluxograma_visual.getScrollPane().getVerticalScrollBar().getValue() + fluxograma_visual.getScrollPane().getVerticalScrollBar().getVisibleAmount() || (int) (fluxograma_visual.getFluxograma().getCellBounds(visual_node).getY()) < fluxograma_visual.getScrollPane().getVerticalScrollBar().getValue()) {
                                fluxograma_visual.getScrollPane().getVerticalScrollBar().setValue((int) (fluxograma_visual.getFluxograma().getCellBounds(visual_node).getY()));
                                this.setFontFluxogramas(font);
                            }
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Erro no execute_node no highlight do node!");
            }

            NodeFluxo next = executor_editor.ExecuteNode(nodef, getConsole());
            updateFluxos();

            //rebuildFluxo();

            if (nodef.getType() != Keyword.FIM) {
                MemoryDisplay display_memoria = findMemoryDisplay(nodef.getBlockID());
                Memory mem_temp = executor_editor.getMemory(nodef.getBlockID());
                if (display_memoria == null) {
                    display_memoria = new MemoryDisplay(nodef.getBlockID());
                    memorias.addTab(executor_editor.getBlock(nodef.getBlockID()).getName(), new javax.swing.ImageIcon(getClass().getResource("/AlgolXXI/Editor/Images/memoria.gif")), display_memoria);
                } else {
                    //if (output_tabpane.getSelectedIndex() == output_tabpane.indexOfTab("Memoria")) {
                    memorias.setSelectedComponent(display_memoria);
                    //}
                    display_memoria.update(mem_temp.getMemory());
                }
            }

            contador_geral++;

            if (timer != -1) {
                if (timer > 0) {
                    threadprincipal.sleep(timer);

                    if (timer <= 0 && thread_pause == true) {
                        runningnode = next;
                        return;
                    }
                }

            }

            if (next == null || stepbystep == true) {
                runningnode = next;
                if (next == null) {
                    endExecutions();
                }
                return;
            } else {
                runningnode = next;
                return;

                //execute_node(next);
            }

        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    private MemoryDisplay findMemoryDisplay(int id) {
        for (Component comp : memorias.getComponents()) {
            if (comp instanceof MemoryDisplay) {
                if (((MemoryDisplay) comp).getBlockid() == id) {
                    return (MemoryDisplay) comp;
                }
            }
        }
        return null;
    }

    private void removeMemoryDisplays() {
        for (Component comp : memorias.getComponents()) {
            if (comp instanceof MemoryDisplay) {
                memorias.remove(comp);
            }
        }
    }

//    /**
//     * Adormece a thread ate a flag mudar.
//     */
//    private int thread_wait() {
//        while (thread_pause) {
//            if (isThreadRunning() == false) {
//                return 1;
//            }
//        }
//        thread_pause = true;
//        return 0;
//    }
    /**
     * Método que sublinha uma determinada linha
     * @param linha int, número da linha que se deseja sublinhar.
     */
    void selectLine(int linha) {
        for (int i = 0; i
                < codigo.getText().length(); i++) {
            int linha_aux = codigo.getDocument().getDefaultRootElement().getElementIndex(i);
            if (linha_aux == linha) {
                codigo.setCaretPosition(i);
                break;

            }
        }
    }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton botao_export;
  private javax.swing.JButton botao_rebuild;
  private javax.swing.JButton botao_zoom_centro;
  private javax.swing.JButton botao_zoom_mais;
  private javax.swing.JButton botao_zoom_menos;
  private javax.swing.JPanel codigoPanel;
  private javax.swing.JTextArea execucao;
  private javax.swing.JPanel fluxogramaPanel;
  private javax.swing.JTabbedPane fluxos;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JLabel label_file;
  private javax.swing.JLabel label_status;
  private javax.swing.JPanel left_panel;
  private javax.swing.JTabbedPane memorias;
  private javax.swing.JPanel output_panel;
  private javax.swing.JTabbedPane output_tabpane;
  private javax.swing.JPanel panel_ajuda;
  private javax.swing.JPanel panel_botoes;
  private javax.swing.JPanel panel_memoria;
  private javax.swing.JPanel right_panel;
  private javax.swing.JScrollPane scroll_codigo;
  private javax.swing.JScrollPane scroll_consola;
  private javax.swing.JScrollPane scroll_info;
  private javax.swing.JSplitPane separador_principal;
  private javax.swing.JSplitPane separador_secundario1;
  private javax.swing.JTabbedPane tabpane;
  private javax.swing.JTabbedPane under_tabpane;
  private javax.swing.JPanel underpanel;
  private javax.swing.JPanel upperpanel;
  // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized ProgramaTopComponent getDefault() throws ParserConfigurationException {
        if (instance == null) {
            instance = new ProgramaTopComponent();
        }

        return instance;
    }

    /**
     * Obtain the ProgramaTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized ProgramaTopComponent findInstance() throws ParserConfigurationException {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ProgramaTopComponent.class.getName()).warning("Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");

            return getDefault();
        }
        if (win instanceof ProgramaTopComponent) {
            return (ProgramaTopComponent) win;
        }

        Logger.getLogger(ProgramaTopComponent.class.getName()).warning("There seem to be multiple components with the '" + PREFERRED_ID + "' ID. That is a potential source of errors and unexpected behavior.");


        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        separador_secundario1.setDividerLocation(0);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    public Boolean isRunning() {
        return executing;
    }

    public boolean isDialogOpened() {
        return dialog_opened;
    }

    public void setDialogOpened(boolean dialog_opened) {
        this.dialog_opened = dialog_opened;
    }

    public ConsoleIO getConsole() {
        under_tabpane.setSelectedIndex(under_tabpane.indexOfTab("Consola"));
        return console;
    }

    public ConsoleIO getConsole_info() {
        under_tabpane.setSelectedIndex(under_tabpane.indexOfTab("Informações"));
        return console_info;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() throws ParserConfigurationException {
            return ProgramaTopComponent.getDefault();
        }
    }

    /**
     * Método que para a variável <thread_running>.
     */
    public void thread_stop() {
        executing = false;
        codigo.setEditable(true);
        timer = 0;
    }

    /**
     * Método run para a executar o código implementado. 
     * Enquanto decorre a execução do código é impossível alterar o mesmo.
     */
    public void run() {
        while (runningnode != null) {
            execute_node(runningnode);
        }
    }

    public void endExecutions() {
        label_status.setText("Estado: Parado ");
        getConsole().writeLn("- FIM da Execução! -");

        System.out.println("Acabou Execucao");
        executing = false;
        codigo.setEditable(true);
        timer = 0;
        contador_geral = 0;
    }

    /**
     * Módificador para variável int <timer>
     * @param tempo int
     */
    public void setTimer(int tempo) {
        this.timer = tempo;
    }

    /**
     * Método que permite efectuar uma pesquisa no código implementado.
     */
    public void find() {
        if (codigo.getSelectedText() != null) {
            int start = codigo.getText().toLowerCase().indexOf(codigo.getSelectedText().toLowerCase(), codigo.getSelectionEnd());
            if (start != -1) {
                int end = codigo.getSelectionEnd() - codigo.getSelectionStart() + start;
                codigo.select(start, end);
            } else {
                start = codigo.getText().toLowerCase().indexOf(codigo.getSelectedText().toLowerCase());
                if (start != -1) {
                    int end = codigo.getSelectionEnd() - codigo.getSelectionStart() + start;
                    codigo.select(start, end);
                }

            }
        }
    }

    public void ajuda() {
        if (codigo.getSelectedText() != null) {
            this.displayHelp(codigo.getSelectedText());
        } else {
            try {
                int caretPosition = codigo.getCaretPosition();
                int startIndex = javax.swing.text.Utilities.getWordStart(codigo, caretPosition);
                int endIndex = javax.swing.text.Utilities.getWordEnd(codigo, caretPosition);
                String keyword = codigo.getDocument().getText(startIndex, endIndex - startIndex).trim();
                this.displayHelp(keyword);
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private void displayHelp(String word) {
        ajuda_panel.setPage(AlgolXXI.Editor.Help.HelpFileName.getHelpFile(word));
        under_tabpane.setSelectedIndex(under_tabpane.indexOfTab("Ajuda"));
        separador_principal.setDividerLocation(0.3);
    }

    /**
     * Método que permite efectuar uma pesquisa consoante o texto desejado.
     * @param txt String
     */
    public void find(String txt) {
        int start = codigo.getText().toLowerCase().indexOf(txt, codigo.getCaretPosition());
        if (start != -1) {
            int end = start + txt.length();
            codigo.select(start, end);
        } else {
            start = codigo.getText().toLowerCase().indexOf(txt);
            if (start != -1) {
                int end = start + txt.length();
                codigo.select(start, end);
            }
        }
    }

    public void newColoringSettings(PortugolStyle style) {
        editorKit.newSettings(style);
    }

    public void replaceAll(String replace, String by) {
        codigo.setText(codigo.getText().replaceAll(replace, by));
    }

    public int compilaJava(String[] args) {
        PrintWriter pen = null;
        try {
            pen = new java.io.PrintWriter("compilador.debug");
            pen.println("Gerado no Portugol");
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        //com.sun.tools.javac.main.Main compiler = new com.sun.tools.javac.main.Main("javac");
        pen.close();
        //return compiler.compile(args);
        return 1;
    }

    public void setFontConsola(Font fontf) {
        if (fontf != null) {
            this.getConsole().setFont(fontf);
            this.getConsole_info().setFont(fontf);
        }
    }

    public void replaceNext(String replace, String by) {
        if (codigo.getSelectionStart() != codigo.getSelectionEnd()) {
            if (codigo.getSelectedText().toLowerCase().equals(replace.toLowerCase())) {
                String start = codigo.getText().substring(0, codigo.getSelectionStart());
                String end = codigo.getText().substring(codigo.getSelectionEnd(), codigo.getText().length());
                codigo.setText(start + by + end);
                codigo.setSelectionStart(start.length());
                codigo.setSelectionEnd(start.length() + by.length());
            }
        }
    }
}
