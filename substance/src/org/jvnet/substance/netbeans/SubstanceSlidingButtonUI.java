package org.jvnet.substance.netbeans;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import org.jvnet.lafwidget.utils.RenderingUtils;

import org.jvnet.substance.utils.MemoryAnalyzer;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.shaper.ClassicButtonShaper;
import org.netbeans.swing.tabcontrol.SlidingButton;
import org.netbeans.swing.tabcontrol.SlidingButtonUI;

public class SubstanceSlidingButtonUI extends SlidingButtonUI {
    // Has the shared instance defaults been initialized?
    private boolean defaults_initialized = false;
    
    protected JToggleButton hiddenToggle;
    
    protected NetbeansButtonBackgroundDelegate backgroundDelegate;
    
    private static final SubstanceSlidingButtonUI INSTANCE = new SubstanceSlidingButtonUI();
    
    private SubstanceSlidingButtonUI() {
        this.backgroundDelegate = new NetbeansButtonBackgroundDelegate();
    }
    
    public static ComponentUI createUI(JComponent c) {
        SlidingButton button = (SlidingButton) c;
        button.putClientProperty(SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY,
                ClassicButtonShaper.class.getName());
        if (MemoryAnalyzer.isRunning())
            MemoryAnalyzer.enqueueUsage("Sliding " + button.getText());
        return INSTANCE;
    }
    
    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        if (!defaults_initialized) {
            hiddenToggle = new JToggleButton();
            hiddenToggle.setText("");
            JToolBar bar = new JToolBar();
            bar.setRollover(true);
            bar.add(hiddenToggle);
            defaults_initialized = true;
        }
        b.setFont(SubstanceLookAndFeel.getFontPolicy().getFontSet("Substance", null).getControlFont());
    }
    
    protected void uninstallDefaults(AbstractButton b) {
        super.uninstallDefaults(b);
        defaults_initialized = false;
    }
    
    protected void paintBackground(Graphics2D g, AbstractButton button) {
        this.backgroundDelegate.updateBackground(g, button, 0);
        hiddenToggle.setBackground(button.getBackground());
        hiddenToggle.paint(g);
    }
    
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        this.backgroundDelegate.updateBackground(g, b, 5);
        hiddenToggle.setBackground(b.getBackground());
        hiddenToggle.paint(g);
    }
    
    @Override
    public void update(Graphics graphics, JComponent jComponent) {
        Graphics2D g2 = (Graphics2D)graphics.create();
        RenderingUtils.installDesktopHints(g2);
        super.update(g2, jComponent);
        g2.dispose();
    }
}
