package org.jvnet.substance.netbeans.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import org.jvnet.substance.skin.SkinInfo;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.openide.util.NbBundle;
import org.openide.util.WeakSet;
import org.openide.util.actions.Presenter;

/**
 * Adds a submenu to the View menu to let the user select themes.
 *
 * @author Timothy Boudreau
 */
public class SkinSelector extends AbstractAction implements Presenter.Menu, Presenter.Popup {

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException();
    }

    public JMenuItem getPopupPresenter() {
        return getMenuPresenter();
    }

    public JMenuItem getMenuPresenter() {
        LookAndFeel lf = UIManager.getLookAndFeel();
        JMenu result = new JMenu(NbBundle.getMessage(getClass(),
                "LBL_Skins")); //NOI18N

        if (lf instanceof SubstanceLookAndFeel) {
            SubstanceLookAndFeel slf = (SubstanceLookAndFeel) lf;
            Map<String, SkinInfo> skins = slf.getAllSkins();
            for (SkinInfo skinInfo : skins.values()) {
                JMenuItem item = new JMenuItem(new ChangeSkinAction(skinInfo.getDisplayName()));
                result.add(item);
            }
        } else {
            result.setText(NbBundle.getMessage(getClass(), "LBL_NotRunning")); //NOI18N

            result.setEnabled(false);
        }
        return result;
    }
    private static final WeakSet allActions = new WeakSet();

    private static void updateActions() {
        for (Iterator i = allActions.iterator(); i.hasNext();) {
            Object skinAction = i.next();
            if (skinAction instanceof ChangeSkinAction) {
                ChangeSkinAction a = (ChangeSkinAction) skinAction;
                if (a != null) {
                    a.update();
                }
            }
        }
    }

    private static final class ChangeSkinAction extends AbstractAction {

        private final String skin;

        public ChangeSkinAction(String skin) {
            this.skin = skin;
            allActions.add(this);
            update();
        }

        private void update() {
            String nm = skin;
            putValue(Action.NAME, skin);
        }

        public void actionPerformed(ActionEvent e) {
            SkinInfo info = (SkinInfo) SubstanceLookAndFeel.getAllSkins().get(skin);

            assert info != null;
            String skinClass = info.getClassName();
            setSkin(skinClass);
            updateUIs();
            persist(skinClass);
            updateActions();
        }
    }

    public static final void updateUIs() {
        Frame[] f = Frame.getFrames();
        for (int i = 0; i < f.length; i++) {
            SwingUtilities.updateComponentTreeUI(f[i]);
        }
    }

    static void setSkin(String s) {
        boolean status = SubstanceLookAndFeel.setSkin(s);
    }

    static void persist(String value) {
        NbPrefs p = new NbPrefs();
        p.put("skinClassName", value); //NOI18N

    }

    public static boolean restore() {
        NbPrefs p = new NbPrefs();
        String preferred = p.get("skinClassName", null); //NOI18N

        if (preferred != null) {
            setSkin(preferred);
        }
        return preferred != null;
    }
}
