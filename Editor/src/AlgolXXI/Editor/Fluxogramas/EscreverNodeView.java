/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Fluxogramas;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Apocas
 */
public class EscreverNodeView extends VertexView {

    /**
     */
    public static transient EscreverRenderer renderer = new EscreverRenderer();

    public static int getArcSize(int width, int height) {
        int arcSize;

        // The arc width of a activity rectangle is 1/5th of the larger
        // of the two of the dimensions passed in, but at most 1/2
        // of the smaller of the two. 1/5 because it looks nice and 1/2
        // so the arc can complete in the given dimension

        if (width <= height) {
            arcSize = height / 5;
            if (arcSize > (width / 2)) {
                arcSize = width / 2;
            }
        } else {
            arcSize = width / 5;
            if (arcSize > (height / 2)) {
                arcSize = height / 2;
            }
        }

        return arcSize;
    }

    /**
     */
    public EscreverNodeView() {
        super();
    }

    /**
     */
    public EscreverNodeView(Object cell) {
        super(cell);
    }

    /**
     * Returns the intersection of the bounding rectangle and the
     * straight line between the source and the specified point p.
     * The specified point is expected not to intersect the bounds.
     */
    public Point2D getPerimeterPoint(EdgeView edge, Point2D source, Point2D p) {
        Rectangle2D r = getBounds();

        double x = r.getX();
        double y = r.getY();
        double a = (r.getWidth() + 1) / 2;
        double b = (r.getHeight() + 1) / 2;

        // x0,y0 - center of ellipse
        double x0 = x + a;
        double y0 = y + b;

        // x1, y1 - point
        double x1 = p.getX();
        double y1 = p.getY();

        // calculate straight line equation through point and ellipse center
        // y = d * x + h
        double dx = x1 - x0;
        double dy = y1 - y0;

        if (dx == 0) {
            return new Point((int) x0, (int) (y0 + b * dy / Math.abs(dy)));
        }
        double d = dy / dx;
        double h = y0 - d * x0;

        // calculate intersection
        double e = a * a * d * d + b * b;
        double f = -2 * x0 * e;
        double g = a * a * d * d * x0 * x0 + b * b * x0 * x0 - a * a * b * b;

        double det = Math.sqrt(f * f - 4 * e * g);

        // two solutions (perimeter points)
        double xout1 = (-f + det) / (2 * e);
        double xout2 = (-f - det) / (2 * e);
        double yout1 = d * xout1 + h;
        double yout2 = d * xout2 + h;

        double dist1Squared = Math.pow((xout1 - x1), 2) + Math.pow((yout1 - y1), 2);
        double dist2Squared = Math.pow((xout2 - x1), 2) + Math.pow((yout2 - y1), 2);

        // correct solution
        double xout, yout;

        if (dist1Squared < dist2Squared) {
            xout = xout1;
            yout = yout1;
        } else {
            xout = xout2;
            yout = yout2;
        }

        return getAttributes().createPoint(xout, yout);
    }

    /**
     */
    public CellViewRenderer getRenderer() {
        return renderer;
    }

    /**
     */
    public static class EscreverRenderer extends VertexRenderer {

        /**
         * Return a slightly larger preferred size than for a rectangle.
         */
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            d.width += d.height / 5;
            return d;
        }

        public void paint(Graphics g) {
            int b = borderWidth;
            Graphics2D g2 = (Graphics2D) g;
            Dimension d = getSize();
            boolean tmp = selected;

            int[] xCoord = new int[5];
            int[] yCoord = new int[5];
            xCoord[0] = b / 2 + ((int) (getWidth() * 0.25));
            yCoord[0] = b / 2;
            xCoord[1] = b / 2 + getWidth() - 2;
            yCoord[1] = b / 2;
            xCoord[2] = b / 2 + getWidth() - 2;
            yCoord[2] = b / 2 + getHeight() - 2;
            xCoord[3] = b / 2;
            yCoord[3] = b / 2 + getHeight() - 2;
            xCoord[4] = b / 2;
            yCoord[4] = b / 2 + ((int) (getHeight() * 0.25));

            if (super.isOpaque()) {
                g.setColor(super.getBackground());
                if (gradientColor != null && !preview) {
                    setOpaque(false);
                    g2.setPaint(new GradientPaint(0, 0, getBackground(),
                            getWidth(), getHeight(), gradientColor, true));
                }

                // g.fillRoundRect(b / 2, b / 2, d.width - (int) (b * 1.5), d.height - (int) (b * 1.5), roundRectArc, roundRectArc);

                g.fillPolygon(xCoord, yCoord, 5);
            }
            try {
                setBorder(null);
                setOpaque(false);
                selected = false;
                super.paint(g);
            } finally {
                selected = tmp;
            }
            if (bordercolor != null) {
                g.setColor(bordercolor);
                g2.setStroke(new BasicStroke(b));
                //g.drawRoundRect(b / 2, b / 2, d.width - (int) (b * 1.5), d.height - (int) (b * 1.5), roundRectArc, roundRectArc);
                g.drawPolygon(xCoord, yCoord, 5);
            }
            if (selected) {
                g2.setStroke(GraphConstants.SELECTION_STROKE);
                g.setColor(highlightColor);
                //g.drawRoundRect(b / 2, b / 2, d.width - (int) (b * 1.5),d.height - (int) (b * 1.5), roundRectArc, roundRectArc);
                g.drawPolygon(xCoord, yCoord, 5);
            }
        }
    }
}
