/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Fluxogramas;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import org.jgraph.graph.EdgeRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;

/**
 *
 * @author Apocas
 */
public class DefeitoEdgeView extends EdgeView {

    public static transient DefeitoEdgeRenderer renderer = new DefeitoEdgeRenderer();

    public DefeitoEdgeView() {
        super();
    }

    public EdgeRenderer getRenderer() {
        return renderer;
    }

    public static class DefeitoEdgeRenderer extends EdgeRenderer {

        private float tension = (float) 1.0;

        public Rectangle getPaintBounds(EdgeView view) {
            Shape teste = createShape();
            if (teste != null) {
                return teste.getBounds();
            }
            return new Rectangle();
        }

        public void paint(Graphics g) {
            super.paint(g);
        }

        protected Shape createLineEnd(int size, int style, Point2D src, Point2D dst) {
            return super.createLineEnd(size, style, new Point2D.Double(dst.getX(), src.getY()), dst);
        }

        protected Shape createShape() {
            int n = view.getPointCount();
            if (n > 1) {
                EdgeView tmp = view;
                Point2D[] p = new Point2D[n];
                for (int i = 0; i < n; i++) {
                    if (tmp != null && tmp.getPoint(i) != null) {
                        p[i] = tmp.getAttributes().createPoint(tmp.getPoint(i));
                    }
                }

                if (view != tmp) {
                    view = tmp;
                    installAttributes(view);
                }

                if (view.sharedPath == null) {
                    view.sharedPath = new GeneralPath(GeneralPath.WIND_NON_ZERO);
                } else {
                    view.sharedPath.reset();
                }

                if (beginDeco != GraphConstants.ARROW_NONE) {
                    view.beginShape = createLineEnd(beginSize, beginDeco, p[1], p[0]);
                }
                if (endDeco != GraphConstants.ARROW_NONE) {
                    view.endShape = createLineEnd(endSize, endDeco, p[n - 2], p[n - 1]);
                }

                Point2D startPoint = p[0];
                view.sharedPath.moveTo((float) startPoint.getX(), (float) startPoint.getY());

                for (int i = 1; i < p.length; i++) {
                    float startX = (float) p[i - 1].getX();
                    float startY = (float) p[i - 1].getY();
                    float endX = (float) p[i].getX();
                    float endY = (float) p[i].getY();
                    float rowHeight = (endY - startY) * tension;
                    view.sharedPath.curveTo(startX, startY + rowHeight, endX, endY - rowHeight, endX, endY);
                }

                view.sharedPath.moveTo((float) p[n - 1].getX(), (float) p[n - 1].getY());
                view.lineShape = (GeneralPath) view.sharedPath.clone();
                if (view.endShape != null) {
                    view.sharedPath.append(view.endShape, true);
                }
                if (view.beginShape != null) {
                    view.sharedPath.append(view.beginShape, true);
                }
                return view.sharedPath;
            }

            return null;

        }
    }
}
