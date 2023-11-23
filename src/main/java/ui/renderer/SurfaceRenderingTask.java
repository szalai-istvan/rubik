package ui.renderer;

import maths.geometry.cube.sides.Square;

import java.awt.*;

public class SurfaceRenderingTask extends RenderingTask<Polygon> {

    protected final Square square;

    public SurfaceRenderingTask(Color color, Polygon shape) {
        super(color, shape);
        this.square = null;
    }

    public SurfaceRenderingTask(Color color, Polygon shape, Square square) {
        super(color, shape);
        this.square = square;
    }

    @Override
    protected void beforeRender(Graphics g) {
        g.setColor(getShadedColor());
    }

    protected Color getShadedColor() {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        double cosAlpha = square.cosAlpha(viewPlane);
        double multiplier = 1 - (1 - cosAlpha) / 1.5;
        return new Color(
                (int) (red * multiplier),
                (int) (green * multiplier),
                (int) (blue * multiplier)
        );
    }

    @Override
    protected void performRendering(Graphics g) {
        g.fillPolygon(shape);
    }
}
