package org.jvnet.substance.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jvnet.lafwidget.animation.FadeConfigurationManager;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.netbeans.ui.SkinSelector;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.openide.ErrorManager;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

/**
 * Runs on NetBeans startup to set the look and feel.
 *
 * @author Timothy Boudreau
 * @author Kirill Grouchnikov
 */
public class SubstanceModule extends ModuleInstall {

    public void restored() {
        try {
            SubstanceLookAndFeel slf =
                    new SubstanceBusinessBlackSteelLookAndFeel();
            UIManager.setLookAndFeel(slf);
            new NetbeansPlugin().initialize();

            // check if need to restore parameters or prefer VM / file /
            // other Substance command parameters
            SkinSelector.restore();
            SkinSelector.updateUIs();

            FadeConfigurationManager.getInstance().
                    allowFades(SubstanceLookAndFeel.TREE_DECORATIONS_ANIMATION_KIND);
//            FadeConfigurationManager.getInstance().
//                    allowFades(SubstanceLookAndFeel.TREE_SMART_SCROLL_ANIMATION_KIND);
            updateLookAndFeel();

        } catch (UnsupportedLookAndFeelException ulafe) {
            ErrorManager.getDefault().notify(ulafe);
            throw new RuntimeException(ulafe);
        }
    }

    private static void updateLookAndFeel() {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        updateLookAndFeel();
                    }
                });
            } catch (Exception ex) {
                /**
                 * includes InvocationTargetException and InterruptedException
                 */
                ErrorManager.getDefault().notify(ex);
            }
            return;
        }

        try {
            LookAndFeel laf = UIManager.getLookAndFeel();
            if (laf.getSupportsWindowDecorations()) {
                ErrorManager.getDefault().
                        log(ErrorManager.WARNING,
                        "Look and Feel supports window decorations!");
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                // set main IDE window to be decorated, too
                JFrame frame =
                        (JFrame) WindowManager.getDefault().getMainWindow();
                boolean needToDispose = frame.isDisplayable();
                boolean isVisible = frame.isVisible();
                if (needToDispose) {
                    frame.dispose();
                }
                frame.setUndecorated(true);
                frame.getRootPane().
                        setWindowDecorationStyle(JRootPane.FRAME);
                frame.getRootPane().updateUI();
                if (needToDispose) {
                    frame.pack();
                    frame.setVisible(isVisible);
                }
            } else {
                JFrame.setDefaultLookAndFeelDecorated(false);
                JDialog.setDefaultLookAndFeelDecorated(false);
            }
        } catch (Exception ex) {
            ErrorManager.getDefault().notify(ex);
        }
    }
}
