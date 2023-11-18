package ui.renderer;

import java.awt.*;
import java.awt.geom.Line2D;

import static java.awt.Color.*;

public class LineRenderingTask extends RenderingTask<Line2D> {

    public LineRenderingTask(Color color, Line2D shape) {
        super(color, shape);
    }

    public LineRenderingTask(Line2D shape) {
        super(GRAY, shape);
    }

    @Override
    protected void beforeRender(Graphics g) {
        super.beforeRender(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
    }

    @Override
    public void performRendering(Graphics g) {
        g.drawLine(
                (int) shape.getX1(),
                (int) shape.getY1(),
                (int) shape.getX2(),
                (int) shape.getY2()
        );
    }
}
