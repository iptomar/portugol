package org.jvnet.substance.netbeans;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.baseline.SubstanceBaseline;
import org.netbeans.swing.plaf.LFCustoms;
import org.openide.util.Utilities;

/**
 * LAF custom values for NetBeans components. This class is based on code from
 * <code>org.netbeans.swing.plaf.metal.MetalLFCustoms</code> from NetBeans
 * source base.
 *
 * @author Kirill Grouchnikov
 */
public class SubstanceLFCustoms extends LFCustoms {

    /*
     * (non-Javadoc)
     *
     * @see org.netbeans.swing.plaf.LFCustoms#createLookAndFeelCustomizationKeysAndValues()
     */
    public Object[] createLookAndFeelCustomizationKeysAndValues() {
        Object[] result = {};
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.netbeans.swing.plaf.LFCustoms#createApplicationSpecificKeysAndValues()
     */
    public Object[] createApplicationSpecificKeysAndValues() {
        Color unfocusedSelBg = UIManager.getColor("controlShadow");
        if (!Color.WHITE.equals(unfocusedSelBg.brighter())) { // #57145
            unfocusedSelBg = unfocusedSelBg.brighter();
        }

        Border empty = new BorderUIResource(BorderFactory.createEmptyBorder());
        Border line = SubstanceLookAndFeel.isCurrentLookAndFeel() ? 
            new BorderUIResource.LineBorderUIResource(
                SubstanceLookAndFeel.getCurrentSkin().
                getMainActiveColorScheme().getUltraLightColor()):
            new BorderUIResource.LineBorderUIResource(Color.gray);
        Object[] result = {
            DESKTOP_BORDER,
            new EmptyBorder(1, 1, 1, 1),
            SCROLLPANE_BORDER,
            empty,
            EXPLORER_STATUS_BORDER,
            line,
            EDITOR_STATUS_LEFT_BORDER,
            line,
            EDITOR_STATUS_RIGHT_BORDER,
            line,
            EDITOR_STATUS_INNER_BORDER,
            line,
            EDITOR_STATUS_ONLYONEBORDER,
            line,
            EDITOR_TOOLBAR_BORDER,
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            // UI Delegates for the tab control
            EDITOR_TAB_DISPLAYER_UI,
            "org.jvnet.substance.netbeans.SubstanceEditorTabDisplayerUI",
            VIEW_TAB_DISPLAYER_UI,
            "org.jvnet.substance.netbeans.SubstanceViewTabDisplayerUI",
            SLIDING_BUTTON_UI,
            "org.jvnet.substance.netbeans.SubstanceSlidingButtonUI",
            "ButtonUI",
            "org.jvnet.substance.netbeans.NetbeansSubstanceButtonUI",
            "ToggleButtonUI",
            "org.jvnet.substance.netbeans.NetbeansSubstanceToggleButtonUI",
            EDITOR_TAB_OUTER_BORDER,
            empty,
            VIEW_TAB_OUTER_BORDER,
            empty,
            // #48951 invisible unfocused selection background in Metal L&F
            "nb.explorer.unfocusedSelBg",
            unfocusedSelBg,
            PROGRESS_CANCEL_BUTTON_ICON,
            Utilities.loadImage("org/netbeans/swing/plaf/resources/cancel_task_win_linux_mac.png"),
            // progress component related
            "nbProgressBar.Foreground", new Color(49, 106, 197),
            "nbProgressBar.Background", Color.WHITE,
            "nbProgressBar.popupDynaText.foreground",
            new Color(141, 136, 122), "nbProgressBar.popupText.background",
            new Color(249, 249, 249), "nbProgressBar.popupText.foreground",
            UIManager.getColor("TextField.foreground"),
            "nbProgressBar.popupText.selectBackground",
            UIManager.getColor("List.selectionBackground"),
            "nbProgressBar.popupText.selectForeground",
            UIManager.getColor("List.selectionForeground"),
            "Baseline.instance", new SubstanceBaseline(),
        }; // NOI18N

        return result;
    }
}
