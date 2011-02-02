/*
 * SubstanceNetbeansImageCreator.java
 *
 * Created on Aug 11, 2007, 10:07:48 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jvnet.substance.netbeans;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.utils.SubstanceColorUtilities;
import org.jvnet.substance.utils.SubstanceCoreUtilities;
import org.jvnet.substance.utils.SubstanceSizeUtils;
import org.netbeans.swing.tabcontrol.plaf.TabControlButton;

/**
 * Image utilities for the module.
 *
 * @author Kirill Grouchnikov
 */
public class SubstanceNetbeansImageCreator {

    /**
     * Returns <code>pin down</code> icon.
     *
     * @param theme
     *            Theme for the icon.
     * @return <code>Pin down</code> icon.
     */
    public static Icon getPinDownIcon(SubstanceColorScheme scheme) {
        int iSize =
                SubstanceSizeUtils.getTitlePaneIconSize();
        BufferedImage image =
                SubstanceCoreUtilities.getBlankImage(iSize, iSize);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        int start = iSize / 4;
        int end = (3 * iSize / 4) - 1;
        int center = (start + end) / 2;
        Color color = scheme.getForegroundColor();
        graphics.setColor(color);
        graphics.drawRect(start, start, end - start, end - start);
        graphics.fillRect(center, center, 2, 2);
        return new ImageIcon(SubstanceImageCreator.overlayEcho(image,
                SubstanceColorUtilities.getColorStrength(color), 1, 1));
    }

    /**
     * Returns <code>pin down</code> icon.
     *
     * @param theme
     *            Theme for the icon.
     * @return <code>Pin down</code> icon.
     */
    public static Icon getPinUpIcon(SubstanceColorScheme scheme) {
        int iSize =
                SubstanceSizeUtils.getTitlePaneIconSize();
        BufferedImage image =
                SubstanceCoreUtilities.getBlankImage(iSize, iSize);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        int start = (iSize / 4) - 1;
        int end = 3 * iSize / 4;
        int center = (start + end) / 2;
        Color color = scheme.getForegroundColor();
        graphics.setColor(color);

        graphics.drawLine(start, start, center - 1, start);
        graphics.drawLine(center + 2, start, end, start);
        graphics.drawLine(end, start, end, center - 1);
        graphics.drawLine(end, center + 2, end, end);
        graphics.drawLine(end, end, center + 2, end);
        graphics.drawLine(center - 1, end, start, end);
        graphics.drawLine(start, end, start, center + 2);
        graphics.drawLine(start, center - 1, start, start);
        return new ImageIcon(SubstanceImageCreator.overlayEcho(image,
                SubstanceColorUtilities.getColorStrength(color), 1, 1));
    }
    protected static Map<String, Map<Integer, Icon>> iconMap =
            new HashMap<String, Map<Integer, Icon>>();

    private static synchronized void computeTabIcons(SubstanceColorScheme scheme) {
        String themeId = scheme.getDisplayName();
        // MemoryAnalyzer.enqueueUsage("Computing icons for " + themeId);
        Map<Integer, Icon> themeIcons = iconMap.get(themeId);
        if (themeIcons == null) {
            //      MemoryAnalyzer.enqueueUsage("Creating map for " + themeId);
            themeIcons =
                    new HashMap<Integer, Icon>();
            iconMap.put(themeId, themeIcons);
            themeIcons.put(TabControlButton.ID_RESTORE_BUTTON,
                    new WrapperIcon(SubstanceImageCreator.getRestoreIcon(scheme)));
            themeIcons.put(TabControlButton.ID_CLOSE_BUTTON,
                    new WrapperIcon(SubstanceImageCreator.getCloseIcon(SubstanceSizeUtils.getTabCloseIconSize(SubstanceSizeUtils.getComponentFontSize(null)),
                    scheme)));

            themeIcons.put(TabControlButton.ID_MAXIMIZE_BUTTON,
                    new WrapperIcon(SubstanceImageCreator.getMaximizeIcon(SubstanceSizeUtils.getTabCloseIconSize(SubstanceSizeUtils.getComponentFontSize(null)),
                    scheme)));

            themeIcons.put(TabControlButton.ID_DROP_DOWN_BUTTON,
                    new WrapperIcon(SubstanceImageCreator.getArrowIcon(SubstanceSizeUtils.getArrowIconWidth(SubstanceSizeUtils.getComponentFontSize(null)),
                    SubstanceSizeUtils.getArrowIconHeight(SubstanceSizeUtils.getComponentFontSize(null)),
                    SubstanceSizeUtils.getArrowStrokeWidth(SubstanceSizeUtils.getComponentFontSize(null)),
                    SwingConstants.SOUTH, scheme)));

            themeIcons.put(TabControlButton.ID_SCROLL_LEFT_BUTTON,
                    new WrapperIcon(SubstanceImageCreator.getArrowIcon(SubstanceSizeUtils.getArrowIconWidth(SubstanceSizeUtils.getComponentFontSize(null)),
                    SubstanceSizeUtils.getArrowIconHeight(SubstanceSizeUtils.getComponentFontSize(null)),
                    SubstanceSizeUtils.getArrowStrokeWidth(SubstanceSizeUtils.getComponentFontSize(null)),
                    SwingConstants.WEST, scheme)));

            themeIcons.put(TabControlButton.ID_SCROLL_RIGHT_BUTTON,
                    new WrapperIcon(SubstanceImageCreator.getArrowIcon(SubstanceSizeUtils.getArrowIconWidth(SubstanceSizeUtils.getComponentFontSize(null)),
                    SubstanceSizeUtils.getArrowIconHeight(SubstanceSizeUtils.getComponentFontSize(null)),
                    SubstanceSizeUtils.getArrowStrokeWidth(SubstanceSizeUtils.getComponentFontSize(null)),
                    SwingConstants.EAST, scheme)));
        }
    }

    public static Icon getTabIcon(SubstanceColorScheme scheme, int buttonId) {
        computeTabIcons(scheme);
        return iconMap.get(scheme.getDisplayName()).get(buttonId);
    }

    private static class WrapperIcon implements Icon {

        private Icon delegate;
        private int dx;
        private int dy;
        private static final int sizeW = 8;
        private static final int sizeH = 8;

        public WrapperIcon(Icon delegate) {
            this.delegate = delegate;
            this.dx = (sizeW - this.delegate.getIconWidth()) / 2;
            this.dy = (sizeH - this.delegate.getIconHeight()) / 2;
        }

        public int getIconHeight() {
            return sizeH;
        }

        public int getIconWidth() {
            return sizeW;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            this.delegate.paintIcon(c, g, x + this.dx, y + this.dy);
        }
    }
}
