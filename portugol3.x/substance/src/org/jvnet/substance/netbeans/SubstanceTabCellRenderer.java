package org.jvnet.substance.netbeans;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.SubstanceConstants.Side;
import org.jvnet.substance.painter.border.SubstanceBorderPainter;
import org.jvnet.substance.painter.gradient.SubstanceGradientPainter;
import org.jvnet.substance.shaper.BaseButtonShaper;
import org.jvnet.substance.shaper.ClassicButtonShaper;
import org.jvnet.substance.utils.LazyResettableHashMap;
import org.jvnet.substance.utils.MemoryAnalyzer;
import org.jvnet.substance.utils.SubstanceCoreUtilities;
import org.jvnet.substance.utils.SubstanceOutlineUtilities;
import org.jvnet.substance.utils.SubstanceSizeUtils;
import org.jvnet.substance.utils.SubstanceThemeUtilities;
import org.netbeans.swing.tabcontrol.plaf.AbstractTabCellRenderer;
import org.netbeans.swing.tabcontrol.plaf.TabPainter;

/**
 * Cell renderer for a single tab in
 * {@link org.netbeans.swing.tabcontrol.TabbedContainer}. This class is based
 * on code from
 * <code>org.netbeans.swing.tabcontrol.plaf.MetalEditorTabCellRenderer</code>
 * from NetBeans source base.
 *
 * @author Kirill Grouchnikov
 */
public class SubstanceTabCellRenderer extends AbstractTabCellRenderer {

    private static final SubstanceTabPainter metalborder =
            new SubstanceTabPainter();
    private static final SubstanceRightClippedTabPainter rightBorder =
            new SubstanceRightClippedTabPainter();
    private static final SubstanceLeftClippedTabPainter leftBorder =
            new SubstanceLeftClippedTabPainter();
    protected static LazyResettableHashMap<BufferedImage> backgroundCache =
            new LazyResettableHashMap<BufferedImage>("SubstanceTabCellRenderer");

    /**
     * Creates a new instance of MetalEditorTabCellRenderer
     */
    public SubstanceTabCellRenderer() {
        super(leftBorder, metalborder, rightBorder, new Dimension(34, 29));
        setBorder(metalborder);
    }

    private static synchronized BufferedImage getBackgroundImage(
            Component c, SubstanceGradientPainter gradientPainter,
            SubstanceBorderPainter borderPainter, int width, int height,
            int radius, SubstanceColorScheme scheme, float cyclePos) {

        String key =
                SubstanceCoreUtilities.getHashKey(
                gradientPainter.getDisplayName(),
                borderPainter.getDisplayName(), width, height,
                scheme.getDisplayName(), cyclePos);
        MemoryAnalyzer.enqueueUsage("Checking " + key);
        BufferedImage result = backgroundCache.get(key);
        if (result == null) {
            Set<Side> bottom =
                    new HashSet<Side>();
            bottom.add(Side.BOTTOM);

            Shape contour =
                    SubstanceOutlineUtilities.getBaseOutline(width, height, radius,
                    bottom);
            result =
                    SubstanceCoreUtilities.getBlankImage(width, height);
            Graphics2D g2d = result.createGraphics();
            gradientPainter.paintContourBackground(g2d, width, height, contour,
                    false, scheme, scheme, cyclePos, true, false);
            borderPainter.paintBorder(g2d, c, width, height,
                    contour, null, scheme, scheme, cyclePos, false);
            MemoryAnalyzer.enqueueUsage("Computed background image for " + key);
            g2d.dispose();
            backgroundCache.put(key, result);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.netbeans.swing.tabcontrol.plaf.AbstractTabCellRenderer#getCaptionYAdjustment()
     */
    protected int getCaptionYAdjustment() {
        return 0;
    }

//    public Dimension getPadding() {
//        Dimension d = super.getPadding();
//        d.width = isShowCloseButton() && !Boolean.getBoolean("nb.tabs.suppressCloseButton") ? 32 : 16;
//        return d;
//    }
    /**
     * Painter for regular tab.
     *
     * @author Kirill Grouchnikov
     */
    private static class SubstanceTabPainter implements TabPainter {

        public Insets getBorderInsets(Component c) {
            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;
            return new Insets(mtr.isSelected() ? 1 : 3,
                    mtr.isSelected() ? 10 : 9, 0, 0); // XXX

        }

        public boolean supportsCloseButton(JComponent renderer) {
            return ((AbstractTabCellRenderer) renderer).isShowCloseButton();
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width,
                int height) {
        }

        public Polygon getInteriorPolygon(Component c) {
            Polygon p = new Polygon();

            int width = c.getWidth() - 2;
            int height = c.getHeight() + 1;

            p.addPoint(0, 0);
            p.addPoint(width, 0);
            p.addPoint(width, height);
            p.addPoint(0, height);
            return p;
        }

        public void paintInterior(Graphics g, Component c) {
            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;

            Graphics2D graphics = (Graphics2D) g.create();
            int width = c.getWidth() - 2;
            int height = c.getHeight() + 1;

            SubstanceColorScheme colorScheme =
                    (mtr.isSelected() | mtr.inCloseButton())
                    ? SubstanceCoreUtilities.getSkin(c).getMainActiveColorScheme()
                    : SubstanceCoreUtilities.getSkin(c).getMainDefaultColorScheme();
            int cyclePos = mtr.isPressed() ? 5 : 0;

            SubstanceGradientPainter painter =
                    SubstanceCoreUtilities.getGradientPainter(c);
            SubstanceBorderPainter borderPainter =
                    SubstanceCoreUtilities.getBorderPainter(c);
            int radius =
                    (SubstanceCoreUtilities.getButtonShaper(c) instanceof ClassicButtonShaper)
                    ? 2 : height / 3;
            BufferedImage bi =
                    getBackgroundImage(c, painter, borderPainter, width - 2,
                    height + 2, radius, colorScheme, cyclePos);

            graphics.drawImage(bi, 2, 0, null);

            Rectangle r = new Rectangle();
            getCloseButtonRectangle(mtr, r,
                    new Rectangle(0, 0, mtr.getWidth(), mtr.getHeight()));
            r.x += 2;
            r.y += 2;
            r.width -= 4;
            r.height -= 4;

            if ((r.width > 0) && (r.height > 0)) {
                SubstanceColorScheme scheme = 
                    SubstanceThemeUtilities.getColorScheme(c, ComponentState.DEFAULT);
                Icon closeIcon =
                        SubstanceImageCreator.getCloseIcon(r.width,
                        scheme);
                closeIcon.paintIcon(c, g, r.x, r.y);
                if (mtr.inCloseButton()) {
                    SubstanceImageCreator.paintBorder(null, graphics, r.x - 2,
                            r.y - 2, r.width + 4, r.height + 4, 0.0f,
                            scheme, scheme, 0);
                }
            }
        }

        public void getCloseButtonRectangle(JComponent jc, final Rectangle rect,
                Rectangle bounds) {
            if (!((AbstractTabCellRenderer) jc).isShowCloseButton()) {
                rect.x = -100;
                rect.y = -100;
                rect.width = 0;
                rect.height = 0;
                return;
            }
            Insets ins = getBorderInsets(jc);

            rect.y = bounds.y + ins.top;

            rect.height = bounds.height - rect.y;
            rect.x = bounds.x + bounds.width - 18;

            int closeButtonSize = SubstanceSizeUtils.getTabCloseIconSize(
                    SubstanceSizeUtils.getComponentFontSize(jc));
            rect.width = closeButtonSize + 4;

            rect.height = closeButtonSize + 4;
            rect.y += (rect.height / 2) - 4;

            // Issue nnn
            rect.x -= 2;
            rect.y -= 2;
        }
    }

    /**
     * Painter for left-clipped tab.
     *
     * @author Kirill Grouchnikov
     */
    private static class SubstanceLeftClippedTabPainter implements TabPainter {

        public Insets getBorderInsets(Component c) {
            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;
            return new Insets(mtr.isSelected() ? 1 : 3,
                    mtr.isSelected() ? 10 : 9, 0, 0); // XXX

        }

        public boolean isBorderOpaque() {
            return true;
        }

        public Polygon getInteriorPolygon(Component c) {
            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;

            Polygon p = new Polygon();

            int width = c.getWidth();
            int height =
                    1 + (mtr.isSelected() ? c.getHeight() + 3 : c.getHeight());

            p.addPoint(-1, 0);
            p.addPoint(-1 + width, 0);
            p.addPoint(-1 + width, height);
            p.addPoint(-1, height);

            return p;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width,
                int height) {
        }

        public void paintInterior(Graphics g, Component c) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;
            int width = c.getWidth() - 2;
            int height = c.getHeight() + 1;


            
            SubstanceColorScheme colorScheme =
                    (mtr.isSelected() | mtr.inCloseButton())
                    ? SubstanceCoreUtilities.getSkin(c).getMainActiveColorScheme()
                    : SubstanceCoreUtilities.getSkin(c).getMainDefaultColorScheme();
            int cyclePos = mtr.isPressed() ? 5 : 0;

            SubstanceGradientPainter painter =
                    SubstanceCoreUtilities.getGradientPainter(c);
            SubstanceBorderPainter borderPainter =
                    SubstanceCoreUtilities.getBorderPainter(c);
            int radius =
                    (SubstanceCoreUtilities.getButtonShaper(c) instanceof ClassicButtonShaper)
                    ? 2 : height / 3;

            BufferedImage bi =
                    getBackgroundImage(c, painter, borderPainter, 2 * width - 2,
                    height + 2, radius, colorScheme, cyclePos);

            GeneralPath clip = new GeneralPath();
            int zzCount = 4;
            int zzHeight = height / zzCount;
            clip.moveTo(2, 0);
            for (int i = 0; i < zzCount;
                    i++) {
                clip.lineTo(2, i * zzHeight);
                clip.lineTo(2 + zzHeight / 2, i * zzHeight + zzHeight / 2);
            }
            clip.lineTo(2, 2 + height);

            clip.lineTo(width + 2, 2 + height);
            clip.lineTo(width + 2, 0);
            clip.lineTo(2, 0);
            graphics.setClip(clip);

            graphics.drawImage(bi, 2 - width, 0, null);

            GeneralPath zz = new GeneralPath();
            zz.moveTo(2, 0);
            for (int i = 0; i < zzCount;
                    i++) {
                zz.lineTo(2, i * zzHeight);
                zz.lineTo(2 + zzHeight / 2, i * zzHeight + zzHeight / 2);
            }
            zz.lineTo(2, 2 + height);
            graphics.setColor(Color.black);
            graphics.setClip(null);
            graphics.draw(zz);

            graphics.dispose();
        }

        public void getCloseButtonRectangle(JComponent jc, Rectangle rect,
                Rectangle bounds) {
            bounds.setBounds(-20, -20, 0, 0);
        }

        public boolean supportsCloseButton(JComponent renderer) {
            return false;
        }
    }

    /**
     * Painter for right-clipped tab.
     *
     * @author Kirill Grouchnikov
     */
    private static class SubstanceRightClippedTabPainter implements TabPainter {

        public Insets getBorderInsets(Component c) {
            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;
            return new Insets(mtr.isSelected() ? 1 : 3,
                    mtr.isSelected() ? 10 : 9, 0, 0); // XXX

        }

        public boolean isBorderOpaque() {
            return true;
        }

        public Polygon getInteriorPolygon(Component c) {
            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;

            Polygon p = new Polygon();

            int width = c.getWidth() + 2;
            int height =
                    1 + (mtr.isSelected() ? c.getHeight() + 3 : c.getHeight());

            p.addPoint(0, 0);
            p.addPoint(width, 0);
            p.addPoint(width, height);
            p.addPoint(0, height);
            return p;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width,
                int height) {
        }

        public void paintInterior(Graphics g, Component c) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            SubstanceTabCellRenderer mtr = (SubstanceTabCellRenderer) c;
            int width = c.getWidth() - 2;
            int height = c.getHeight() + 1;

            SubstanceColorScheme colorScheme =
                    (mtr.isSelected() | mtr.inCloseButton())
                    ? SubstanceCoreUtilities.getSkin(c).getMainActiveColorScheme()
                    : SubstanceCoreUtilities.getSkin(c).getMainDefaultColorScheme();
            int cyclePos = mtr.isPressed() ? 5 : 0;

            SubstanceGradientPainter painter =
                    SubstanceCoreUtilities.getGradientPainter(c);
            SubstanceBorderPainter borderPainter =
                    SubstanceCoreUtilities.getBorderPainter(c);
            int radius =
                    (SubstanceCoreUtilities.getButtonShaper(c) instanceof ClassicButtonShaper)
                    ? 2 : height / 3;
            BufferedImage bi =
                    getBackgroundImage(c, painter, borderPainter, 2 * width - 2,
                    height + 2, radius, colorScheme, cyclePos);

            GeneralPath clip = new GeneralPath();
            int zzCount = 4;
            int zzHeight = height / zzCount;
            clip.moveTo(width, 0);
            for (int i = 0; i < zzCount;
                    i++) {
                clip.lineTo(width, 3 + i * zzHeight);
                clip.lineTo(width - zzHeight / 2,
                        3 + i * zzHeight + zzHeight / 2);
            }
            clip.lineTo(width, 2 + height);

            clip.lineTo(2, 2 + height);
            clip.lineTo(2, 0);
            clip.lineTo(width, 0);
            graphics.setClip(clip);

            graphics.drawImage(bi, 2, 0, null);

            GeneralPath zz = new GeneralPath();
            zz.moveTo(width, 0);
            for (int i = 0; i < zzCount;
                    i++) {
                zz.lineTo(width, i * zzHeight);
                zz.lineTo(width - zzHeight / 2, i * zzHeight + zzHeight / 2);
            }
            zz.lineTo(width, 2 + height);
            graphics.setColor(Color.black);
            graphics.setClip(null);
            graphics.draw(zz);

            graphics.dispose();
        }

        public void getCloseButtonRectangle(JComponent jc, Rectangle rect,
                Rectangle bounds) {
            bounds.setBounds(-20, -20, 0, 0);
        }

        public boolean supportsCloseButton(JComponent renderer) {
            return false;
        }
    }
}
