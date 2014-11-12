package org.jvnet.substance.netbeans;

import org.jvnet.lafwidget.utils.RenderingUtils;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.netbeans.swing.tabcontrol.TabDisplayer;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceConstants.Side;
import org.jvnet.substance.painter.border.SubstanceBorderPainter;
import org.jvnet.substance.painter.gradient.SubstanceGradientPainter;
import org.jvnet.substance.shaper.BaseButtonShaper;
import org.jvnet.substance.utils.SubstanceCoreUtilities;
import org.jvnet.substance.utils.SubstanceOutlineUtilities;
import org.netbeans.swing.tabcontrol.plaf.AbstractViewTabDisplayerUI;
import org.openide.awt.HtmlRenderer;

public class SubstanceViewTabDisplayerUI extends AbstractViewTabDisplayerUI {

    /**
     * *********** constants ******************
     */
    private static final int TXT_X_PAD = 5;

    private static final int ICON_X_LEFT_PAD = 5;
    private static final int ICON_X_RIGHT_PAD = 2;

    private static final int BUMP_X_PAD = 5;
    private static final int BUMP_Y_PAD = 4;

    /**
     * ****** static fields **********
     */

    private static Color inactBgColor;
    private static Color actBgColor;
    private static Color borderHighlight;
    private static Color borderShadow;

    private FontMetrics fm;

    private Font txtFont;
    /**
     * ******* instance fields *********
     */

    private Dimension prefSize;

    /**
     * Reusable Rectangle to optimize rectangle creation/garbage collection
     * during paints
     */
    private Rectangle tempRect = new Rectangle();

    protected SubstanceViewTabDisplayerUI(TabDisplayer displayer) {
        super(displayer);
        prefSize = new Dimension(100, 19);
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        displayer.setFont(SubstanceLookAndFeel.getFontPolicy().
                getFontSet("Substance", null).getControlFont());
    }

    /**
     * Should be constructed only from createUI method.
     */

    public static ComponentUI createUI(JComponent c) {
        return new SubstanceViewTabDisplayerUI((TabDisplayer) c);
    }

//    @Override
//    public Icon getButtonIcon(int buttonId, int buttonState) {
//        SubstanceTheme theme =
//                SubstanceLookAndFeel.getTheme();
//        Icon result =
//                SubstanceNetbeansImageCreator.getTabIcon(theme, buttonId);
//        if (result == null) {
//            return super.getButtonIcon(buttonId, buttonState);
//        }
//        return result;
//    }

    public Dimension getPreferredSize(JComponent c) {
        FontMetrics fm = getTxtFontMetrics();
        int height = fm == null ? 21 : fm.getAscent() + 2 * fm.getDescent() + 4;
        Insets insets = c.getInsets();
        prefSize.height = height + insets.bottom + insets.top;
        return prefSize;
    }

    /**
     * Overrides basic paint mathod, adds painting of overall
     * bottom area, depending on activation status value
     */
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        paintBottomBorder(g, c);
    }

    @Override
    public void update(Graphics graphics, JComponent jComponent) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        RenderingUtils.installDesktopHints(g2);
        super.update(g2, jComponent);
        g2.dispose();
    }

    /**
     * Paints bottom "activation" line
     */
    private void paintBottomBorder(Graphics g, JComponent c) {
        Rectangle bounds = c.getBounds();
        g.setColor(SubstanceCoreUtilities.getSkin(c).
                getMainDefaultColorScheme().getMidColor());
        g.drawLine(1, bounds.height - 1, bounds.width - 1, bounds.height - 1);
    }

    protected void paintTabContent(Graphics _g, int index, String text, int x,
            int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) _g.create();
        RenderingUtils.installDesktopHints(g2);
        FontMetrics fm = getTxtFontMetrics();
        // setting font already here to compute string width correctly
        g2.setFont(getTxtFont());
        int txtWidth = width;

        if (isSelected(index)) {
            Component buttons = getControlButtons();
            if (null != buttons) {
                Dimension buttonsSize = buttons.getPreferredSize();
                txtWidth =
                        width -
                        (buttonsSize.width + ICON_X_LEFT_PAD + ICON_X_RIGHT_PAD +
                        2 * TXT_X_PAD);
                buttons.setLocation(x + txtWidth + 2 * TXT_X_PAD,
                        y + (height - buttonsSize.height) / 2 + 1);
            }
            txtWidth =
                    (int) HtmlRenderer.renderString(text, g2, x + TXT_X_PAD, height -
                    fm.getDescent() - 4, txtWidth, height, getTxtFont(),
                    UIManager.getColor("textText"),
                    HtmlRenderer.STYLE_TRUNCATE, true);
            int bumpWidth = width - (TXT_X_PAD + txtWidth + BUMP_X_PAD);
            if (bumpWidth > 0) {
                paintBump(index, g2, x + TXT_X_PAD + txtWidth + BUMP_X_PAD,
                        y + BUMP_Y_PAD, bumpWidth, height - 2 * BUMP_Y_PAD);
            }
        } else {
            txtWidth = width - 2 * TXT_X_PAD;
            HtmlRenderer.renderString(text, g2, x + TXT_X_PAD,
                    height - fm.getDescent() - 4, txtWidth, height, getTxtFont(),
                    UIManager.getColor("textText"),
                    HtmlRenderer.STYLE_TRUNCATE, true);
        }
        g2.dispose();
    }

    protected void paintTabBorder(Graphics g, int index, int x, int y, int width,
            int height) {
    }

    protected void paintTabBackground(Graphics g, int index, int x, int y,
            int width, int height) {
        boolean selected = isSelected(index);
        boolean highlighted = selected && isActive();
        boolean attention = isAttention(index);
        SubstanceColorScheme colorScheme =
                (selected | highlighted | attention)
                    ? SubstanceCoreUtilities.getSkin(this.displayer).getMainActiveColorScheme()
                    : SubstanceCoreUtilities.getSkin(this.displayer).getMainDefaultColorScheme();
        int cyclePos = selected ? 5 : 0;

        Set<Side> bottom =
                new HashSet<Side>();
        bottom.add(Side.BOTTOM);
        
        SubstanceGradientPainter painter =
                    SubstanceCoreUtilities.getGradientPainter(this.displayer);
        SubstanceBorderPainter borderPainter =
                    SubstanceCoreUtilities.getBorderPainter(this.displayer);

        Shape contour =
                SubstanceOutlineUtilities.getBaseOutline(width, height + 2, 0, bottom);
        
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.translate(x, y);
        painter.paintContourBackground(g2d, width, height + 2, contour, false,
                colorScheme, colorScheme, cyclePos, true, false);
        borderPainter.paintBorder(g2d, this.displayer, width, height + 2, contour,
                null, colorScheme, colorScheme, cyclePos, false);
        g2d.dispose();
    }

    private void paintBump(int index, Graphics g, int x, int y, int width,
            int height) {
    }

    static Color getInactBgColor() {
        return SubstanceCoreUtilities.getSkin(null).
                getMainDefaultColorScheme().getMidColor();
    }

    static Color getActBgColor() {
        return  SubstanceCoreUtilities.getSkin(null).
                getMainActiveColorScheme().getMidColor();
    }

    private Color getBorderHighlight() {
        if (borderHighlight == null) {
            borderHighlight = getInactBgColor().brighter();
        }
        return borderHighlight;
    }

    private Color getBorderShadow() {
        if (borderShadow == null) {
            borderShadow = getInactBgColor().darker();
        }
        return borderShadow;
    }

    /**
     * Specifies font to use for text and font metrics. Subclasses may override
     * to specify their own text font
     */
    protected Font getTxtFont() {
        if (txtFont == null) {
            txtFont =
                    SubstanceLookAndFeel.getFontPolicy().
                    getFontSet("Substance", null).getControlFont();
            if (txtFont == null) {
                txtFont = new Font("Dialog", Font.PLAIN, 11);
            } else if (txtFont.isBold()) {
                // don't use deriveFont() - see #49973 for details
                txtFont =
                        new Font(txtFont.getName(), Font.PLAIN,
                        txtFont.getSize());
            }
        }
        return txtFont;
    }
}
