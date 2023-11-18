package ui.renderer;

import java.awt.*;

public abstract class RenderingTask<TASK_TYPE extends Shape> {
    protected final Color color;
    protected final TASK_TYPE shape;

    public RenderingTask(Color color, TASK_TYPE shape) {
        this.shape = shape;
        this.color = color;
    }

    public final void render(Graphics g) {
        beforeRender(g);
        performRendering(g);
        afterRender(g);
    }

    protected void beforeRender(Graphics g) {
        g.setColor(color);
    }

    protected abstract void performRendering(Graphics g);

    protected void afterRender(Graphics g) {
    }

}
