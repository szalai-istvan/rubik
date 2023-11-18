package ui.renderer;

import maths.geometry.Square;

import java.awt.*;

public class SurfaceRenderingTask extends RenderingTask<Polygon> {

    public SurfaceRenderingTask(Color color, Polygon shape) {
        super(color, shape);
    }

    @Override
    protected void performRendering(Graphics g) {
        g.drawPolygon(shape);
    }
}
