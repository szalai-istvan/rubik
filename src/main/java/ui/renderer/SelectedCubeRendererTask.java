package ui.renderer;

import maths.coordinate.plane.ViewPlane;
import maths.geometry.Cube;
import maths.geometry.Square;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SelectedCubeRendererTask extends SurfaceRenderingTask {
    public SelectedCubeRendererTask(Color color, Polygon shape) {
        super(color, shape);
    }

    public SelectedCubeRendererTask(Color color, Polygon shape, Square square) {
        super(color, shape, square);
    }

    public static List<RenderingTask> of(Cube cube, ViewPlane viewPlane) {
        return cube.getRenderingTasks(viewPlane).stream()
                .map(task -> {
                    if (task instanceof LineRenderingTask) {
                        return task;
                    } else if (task instanceof SurfaceRenderingTask) {
                        SurfaceRenderingTask task_ = (SurfaceRenderingTask) task;
                        return SelectedCubeRendererTask.from(task_);
                    }
                    return task;
                })
                .collect(Collectors.toList());
    }

    public static SelectedCubeRendererTask from(SurfaceRenderingTask task) {
        return new SelectedCubeRendererTask(task.color, task.shape, task.square);
    }

    @Override
    protected void beforeRender(Graphics g) {
        Color original = getShadedColor();
        g.setColor(original.brighter());
    }
}
