package org.jvnet.substance.netbeans;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.painter.border.SubstanceBorderPainter;
import org.jvnet.substance.painter.gradient.SubstanceGradientPainter;
import org.jvnet.substance.shaper.SubstanceButtonShaper;
import org.jvnet.substance.utils.LazyResettableHashMap;
import org.jvnet.substance.utils.SubstanceCoreUtilities;
import org.jvnet.substance.utils.SubstanceThemeUtilities;
import org.netbeans.swing.tabcontrol.SlideBarDataModel;
import org.netbeans.swing.tabcontrol.SlidingButton;

/**
 * Delegate class for painting backgrounds of buttons in <b>NetBeans Substance</b>
 * plugin.
 *
 * @author Kirill Grouchnikov
 */
class NetbeansButtonBackgroundDelegate {

    /**
     * Cache for background images with regular slightly-round corners. Each
     * time {@link #getBackground(AbstractButton, int, int, boolean)} is called
     * with <code>isRoundCorners</code> equal to <code>false</code>, it
     * checks <code>this</code> map to see if it already contains such
     * background. If so, the background from the map is returned.
     */
    private static LazyResettableHashMap<BufferedImage> regularBackgrounds =
            new LazyResettableHashMap<BufferedImage>("NetbeansButtonBackgroundDelegate");

    /**
     * Retrieves background image for the specified button.
     *
     * @param button
     *            Button.
     * @param width
     *            Button width.
     * @param height
     *            Button height.
     * @param isRoundCorners
     *            If <code>true</code>, the corners of the resulting button
     *            will be completely rounded.
     * @return Button background image.
     */
    private static synchronized BufferedImage getBackground(AbstractButton button,
            GeneralPath contour, int width, int height) {
        ComponentState state =
                ComponentState.getState(button.getModel(), button);
        int cyclePos = state.getCycleCount();

        SubstanceColorScheme colorScheme =
                SubstanceThemeUtilities.getColorScheme(button, state);
        SubstanceColorScheme borderScheme =
                SubstanceThemeUtilities.getBorderColorScheme(button, state);

        SlidingButton slider =
                (SlidingButton) button;
        int orientation = slider.getOrientation();
        int rotation = 0;
        switch (orientation) {
            case SlideBarDataModel.EAST:
                rotation = 3;
                break;
            case SlideBarDataModel.WEST:
                rotation = 1;
                break;
        }

        String key =
                SubstanceCoreUtilities.getHashKey(width, 
                height, state.name(), cyclePos,
                colorScheme, borderScheme, rotation,
                button.getClass().getName());
        if (!regularBackgrounds.containsKey(key)) {
            SubstanceGradientPainter painter =
                    SubstanceCoreUtilities.getGradientPainter(button);
            SubstanceBorderPainter borderPainter =
                    SubstanceCoreUtilities.getBorderPainter(button);
            BufferedImage contourImage =
                    SubstanceCoreUtilities.getBlankImage(width, height);
            Graphics2D g2d = contourImage.createGraphics();
            painter.paintContourBackground(g2d, width, height, contour, false,
                    colorScheme, colorScheme, cyclePos, true, false);
            borderPainter.paintBorder(contourImage.getGraphics(), button,
                    width, height,
                    contour, null, borderScheme, borderScheme, cyclePos, false);
            if (rotation != 0) {
                contourImage =
                        SubstanceImageCreator.getRotated(contourImage, 2);

                contourImage =
                        SubstanceImageCreator.getRotated(contourImage,
                        rotation - 1);
            }

            g2d.dispose();
            regularBackgrounds.put(key, contourImage);
        }
        return regularBackgrounds.get(key);
    }

    /**
     * Simple constructor.
     */
    public NetbeansButtonBackgroundDelegate() {
        super();
    }

    /**
     * Updates background of the specified button.
     *
     * @param g
     *            Graphic context.
     * @param button
     *            Button to update.
     * @param cycleCount
     *            Cycle count for transition effects.
     */
    public void updateBackground(Graphics g, AbstractButton button,
            long cycleCount) {
        button.setOpaque(false);
        Graphics2D graphics = (Graphics2D) g;

        int width = button.getWidth();
        int height = button.getHeight();

        if ((width <= 0) || (height <= 0)) {
            return;
        }
        SubstanceButtonShaper shaper =
                SubstanceCoreUtilities.getButtonShaper(button);
        GeneralPath contour = shaper.getButtonOutline(button);
        graphics.drawImage(getBackground(button, contour, width, height), 0, 0,
                null);
    }

    /**
     * Returns <code>true</code> if the specified <i>x,y</i> location is
     * contained within the look and feel's defined shape of the specified
     * component. <code>x</code> and <code>y</code> are defined to be
     * relative to the coordinate system of the specified component.
     *
     * @param button
     *            the component where the <i>x,y</i> location is being queried;
     * @param x
     *            the <i>x</i> coordinate of the point
     * @param y
     *            the <i>y</i> coordinate of the point
     */
    public static boolean contains(AbstractButton button, int x, int y) {
        int width = button.getWidth();
        int height = button.getHeight();

        // rough estimation - outside the rectangle.
        if ((x < 0) || (y < 0) || (x > width) || (y > height)) {
            return false;
        }
        int radius = 2;

        // mirror
        if (x >= (width / 2)) {
            x = width - x;
        }
        if (y >= (height / 2)) {
            y = height - y;
        }

        if ((x < radius) && (y < radius)) {
            int dx = radius - x;
            int dy = radius - y;
            int diff = dx * dx + dy * dy - radius * radius;
            return diff <= 0;
        } else {
            return true;
        }
    }
}
