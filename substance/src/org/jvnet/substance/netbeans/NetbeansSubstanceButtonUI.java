package org.jvnet.substance.netbeans;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import org.jvnet.substance.SubstanceButtonUI;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.gradient.ClassicGradientPainter;
import org.jvnet.substance.shaper.ClassicButtonShaper;
import org.jvnet.substance.shaper.SubstanceButtonShaper;
import org.jvnet.substance.utils.SubstanceCoreUtilities;

public class NetbeansSubstanceButtonUI extends SubstanceButtonUI {
    protected ClassicButtonShaper classicShaper = new ClassicButtonShaper();
    
    public static ComponentUI createUI(JComponent b) {
        ((AbstractButton) b).setRolloverEnabled(true);
        return new NetbeansSubstanceButtonUI();
    }
    
    protected boolean isInMatissePalette(AbstractButton button) {
        Component c = button;
        while (c != null) {
            if (c.getClass().getSimpleName().equals("PalettePanel"))
                return true;
            c = c.getParent();
        }
        return false;
    }
    
    public Dimension getPreferredSize(JComponent c) {
        AbstractButton button = (AbstractButton) c;
        SubstanceButtonShaper shaper =
                SubstanceCoreUtilities.getButtonShaper(button);
        if (isInMatissePalette(button)) {
            shaper = classicShaper;
            Object currentShaper = button.getClientProperty(SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY);
            if (!(currentShaper instanceof ClassicButtonShaper)) {
                button.putClientProperty(SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY,
                        new ClassicButtonShaper());
            }
            button.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
            button.setBorder(shaper.getButtonBorder(button));
        }
        
        return shaper.getPreferredSize(button,
                BasicGraphicsUtils.getPreferredButtonSize(button, button.getIconTextGap()));
    }
    
    public void update(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        if (isInMatissePalette(button)) {
            Object currentShaper = button.getClientProperty(SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY);
            if (!(currentShaper instanceof ClassicButtonShaper)) {
                button.putClientProperty(SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY,
                        new ClassicButtonShaper());
                button.setBorder(((ClassicButtonShaper)currentShaper).getButtonBorder(button));
            }
            button.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        }
        super.update(g, c);
    }
    
}
