package org.jvnet.substance.netbeans;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import org.jvnet.lafwidget.utils.RenderingUtils;
import org.jvnet.substance.*;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.utils.MemoryAnalyzer;
import org.jvnet.substance.utils.SubstanceCoreUtilities;
import org.jvnet.substance.utils.SubstanceThemeUtilities;
import org.netbeans.swing.tabcontrol.TabDisplayer;
import org.netbeans.swing.tabcontrol.plaf.*;

/**
 * UI delegate for tab displayer. This class is based on code from
 * <code>org.netbeans.swing.tabcontrol.plaf.MetalEditorTabDisplayerUI</code>
 * from NetBeans source base.
 *
 * @author Kirill Grouchnikov
 */
public class SubstanceEditorTabDisplayerUI extends BasicScrollingTabDisplayerUI {

    private Rectangle scratch = new Rectangle();

    /**
     * Simple constructor.
     */
    public SubstanceEditorTabDisplayerUI(TabDisplayer displayer) {
        super(displayer);
        if (MemoryAnalyzer.isRunning()) {
            MemoryAnalyzer.enqueueUsage("SubstanceEditorTabDisplayerUI initialized");
        }
    }

    /* (non-Javadoc)
     * @see org.netbeans.swing.tabcontrol.plaf.BasicTabDisplayerUI#createDefaultRenderer()
     */
    protected TabCellRenderer createDefaultRenderer() {
        return new SubstanceTabCellRenderer();
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
        return new SubstanceEditorTabDisplayerUI((TabDisplayer) c);
    }

    @Override
    protected Font createFont() {
        return SubstanceLookAndFeel.getFontPolicy().getFontSet("Substance", null).
                getControlFont();
    }


    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
     */
    public Dimension getMinimumSize(JComponent c) {
        return new Dimension(80, 28);
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
     */
    public Dimension getPreferredSize(JComponent c) {
        int prefHeight = 28;
        Graphics g = BasicScrollingTabDisplayerUI.getOffscreenGraphics();
        if (g != null) {
            FontMetrics fm = g.getFontMetrics(displayer.getFont());
            Insets ins = getTabAreaInsets();
            prefHeight = fm.getHeight() + ins.top + ins.bottom + 6;
        }
        return new Dimension(displayer.getWidth(), prefHeight);
    }

    /* (non-Javadoc)
     * @see org.netbeans.swing.tabcontrol.plaf.BasicTabDisplayerUI#createRepaintPolicy()
     */
    protected int createRepaintPolicy() {
        return TabState.REPAINT_ALL_TABS_ON_ACTIVATION_CHANGE |
                TabState.REPAINT_ALL_TABS_ON_SELECTION_CHANGE |
                TabState.REPAINT_ON_MOUSE_ENTER_CLOSE_BUTTON;
    }

    /* (non-Javadoc)
     * @see org.netbeans.swing.tabcontrol.plaf.BasicTabDisplayerUI#getTabAreaInsets()
     */
    public Insets getTabAreaInsets() {
        return new Insets(0, 0, 2, 70);
    }

    @Override
    public void update(Graphics graphics, JComponent jComponent) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        RenderingUtils.installDesktopHints(g2);
//        displayer.setFont(SubstanceLookAndFeel.getFontPolicy().
//                getFontSet("Substance", null).getControlFont());
        super.update(g2, jComponent);
        g2.dispose();
    }

    /* (non-Javadoc)
     * @see org.netbeans.swing.tabcontrol.plaf.AbstractTabDisplayerUI#install()
     */
    public void install() {
        super.install();
        displayer.setBackground(UIManager.getColor("control")); // NOI18N

        displayer.setFont(SubstanceLookAndFeel.getFontPolicy().
                getFontSet("Substance", null).getControlFont());
    }

    protected void paintAfterTabs(Graphics g) {
        Rectangle r = new Rectangle();
        getTabsVisibleArea(r);
        r.width = displayer.getWidth();
        g.setColor(SubstanceCoreUtilities.getSkin(this.displayer).
                getMainDefaultColorScheme().getMidColor());
        g.drawLine(0, displayer.getHeight() - 1, displayer.getWidth(),
                displayer.getHeight() - 1);
    }

    /* (non-Javadoc)
     * @see org.netbeans.swing.tabcontrol.plaf.BasicScrollingTabDisplayerUI#createLayout()
     */
    protected LayoutManager createLayout() {
        return new SubstanceTabLayout();
    }

    public Rectangle getTabRect(int idx, Rectangle rect) {
        Rectangle retValue;

        retValue = super.getTabRect(idx, rect);
        //                if (selectionModel.getSelectedIndex() == idx) {
        //                    retValue.height+=4;
        //                    retValue.y -= 2;
        //                }
        retValue.width += 4;
        return retValue;
    }

    @Override
    protected Component getControlButtons() {
        Component result = super.getControlButtons();
        //   this.configureButtons(result);
        return result;
    }

    private void configureButtons(Component comp) {
        if (comp instanceof JButton) {
            configureControlButton((JButton) comp);
        }
        if (comp instanceof Container) {
            Container cont = (Container) comp;
            for (int i = 0; i < cont.getComponentCount();
                    i++) {
                configureButtons(cont.getComponent(i));
            }
        }
    }

    private void configureControlButton(JButton button) {
        button.setContentAreaFilled(true);
        //        button.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY,
        //                Boolean.TRUE);
        button.setBorderPainted(true);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    @Override
    public Icon getButtonIcon(int buttonId, int buttonState) {
        SubstanceColorScheme scheme = 
                SubstanceThemeUtilities.getColorScheme(displayer, 
                ComponentState.DEFAULT);
        Icon result = SubstanceNetbeansImageCreator.getTabIcon(scheme, buttonId);
        if (result == null) {
            return super.getButtonIcon(buttonId, buttonState);
        }
        return result;
    }

    /**
     * Layout manager for the tab component.
     *
     * @author Kirill Grouchnikov
     */
    private class SubstanceTabLayout implements LayoutManager {

        /* (non-Javadoc)
         * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
         */
        public void addLayoutComponent(String name, java.awt.Component comp) {
        }

        /* (non-Javadoc)
         * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
         */
        public void layoutContainer(java.awt.Container parent) {
            Insets in = getTabAreaInsets();
            Component[] c = parent.getComponents();
            int x = parent.getWidth() - in.right + 3;
            int y = 0;
            Dimension psize;
            for (int i = 0; i < c.length;
                    i++) {
                psize = c[i].getPreferredSize();
                y = in.top + 3; // hardcoded to spec

                int w = Math.min(psize.width, parent.getWidth() - x);
                c[i].setBounds(x, y, w,
                        Math.min(psize.height, parent.getHeight()));
                x += psize.width;
                if (i == 1) {
                    x += 3;
                }
            }
        }

        /* (non-Javadoc)
         * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
         */
        public Dimension minimumLayoutSize(java.awt.Container parent) {
            return getPreferredSize((JComponent) parent);
        }

        /* (non-Javadoc)
         * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
         */
        public Dimension preferredLayoutSize(java.awt.Container parent) {
            return getPreferredSize((JComponent) parent);
        }

        /* (non-Javadoc)
         * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
         */
        public void removeLayoutComponent(java.awt.Component comp) {
        }
    }
}
