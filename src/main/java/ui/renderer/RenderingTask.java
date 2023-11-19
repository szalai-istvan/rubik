package ui.renderer;

import maths.coordinate.plane.ViewPlane;

import java.awt.*;

public abstract class RenderingTask<TASK_TYPE extends Shape> {
    protected final Color color;
    protected final TASK_TYPE shape;
    protected ViewPlane viewPlane;

    public RenderingTask(Color color, TASK_TYPE shape) {
        this.shape = shape;
        this.color = color;
    }

    public final RenderingTask render(Graphics g) {
        beforeRender(g);
        performRendering(g);
        afterRender(g);
        return this;
    }

    public RenderingTask withViewPlane(ViewPlane viewPlane) {
        this.viewPlane = viewPlane;
        return this;
    }

    protected void beforeRender(Graphics g) {
        g.setColor(color);
    }

    protected abstract void performRendering(Graphics g);

    protected void afterRender(Graphics g) {
    }

}
