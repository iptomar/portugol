package org.jvnet.substance.netbeans;

import java.awt.Color;
import javax.swing.UIManager;

import org.jvnet.lafplugin.LafComponentPlugin;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.baseline.SubstanceBaseline;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.utils.SubstanceColorUtilities;
import org.netbeans.swing.plaf.Startup;

/**
 * <b>Substance</b> plugin for NetBeans components.
 *
 * @author Kirill Grouchnikov
 */
public class NetbeansPlugin implements LafComponentPlugin {

    public NetbeansPlugin() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jvnet.substance.plugin.SubstancePlugin#getDefaults(org.jvnet.substance.color.ColorSchemeEnum)
     */
    public Object[] getDefaults(Object mSkin) {
        SubstanceSkin skin = (SubstanceSkin) mSkin;
        SubstanceColorScheme mainDefaultScheme = skin.getMainDefaultColorScheme();
        Color foregroundColor = SubstanceColorUtilities.getForegroundColor(mainDefaultScheme);

        Object[] defaults = new Object[]{
            "EditorTabDisplayerUI",
            "org.jvnet.substance.netbeans.SubstanceEditorTabDisplayerUI",
            "ViewerTabDisplayerUI",
            "org.jvnet.substance.netbeans.SubstanceViewTabDisplayerUI",
            "SlidingButtonUI",
            "org.jvnet.substance.netbeans.SubstanceSlidingButtonUI",
            "ButtonUI",
            "org.jvnet.substance.netbeans.NetbeansSubstanceButtonUI",
            "ToggleButtonUI",
            "org.jvnet.substance.netbeans.NetbeansSubstanceToggleButtonUI",
            "TabRenderer.selectedActivatedForeground", foregroundColor,
            "TabRenderer.selectedForeground", foregroundColor,
            "Nb.SubstanceLFCustoms", new SubstanceLFCustoms(),
            "Nb.SubstanceXPLFCustoms", new SubstanceLFCustoms(),
            "Baseline.instance", new SubstanceBaseline(),
        };

        return defaults;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jvnet.substance.plugin.SubstancePlugin#initialize()
     */
    public void initialize() {
        Startup.run(SubstanceBusinessBlackSteelLookAndFeel.class, 0, null);
        //SubstanceLookAndFeel.getCurrentSkin().addCustomEntriesToTable(UIManager.getDefaults());
        SubstanceLFCustoms customs = new SubstanceLFCustoms();
        UIManager.getDefaults().putDefaults(customs.createApplicationSpecificKeysAndValues());
        UIManager.getDefaults().putDefaults(customs.createGuaranteedKeysAndValues());
        UIManager.getDefaults().putDefaults(customs.createLookAndFeelCustomizationKeysAndValues());
    }

    public void uninitialize() {
    }
}
