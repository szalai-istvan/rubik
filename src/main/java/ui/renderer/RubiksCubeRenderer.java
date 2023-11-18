package ui.renderer;

import maths.coordinate.ViewPlane;
import rubik.RubiksCube;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class RubiksCubeRenderer {
    private JComponent target;
    private ViewPlane viewPlane;
    private RubiksCube rubiksCube;

    RubiksCubeRenderer(RubiksCube rubiksCube) {
        this.rubiksCube = rubiksCube;
    }

    public static RubiksCubeRenderer draw(RubiksCube rubiksCube) {
        return new RubiksCubeRenderer(rubiksCube);
    }

    public RubiksCubeRenderer on(ViewPlane viewPlane) {
        this.viewPlane = viewPlane;
        return this;
    }

    public RubiksCubeRenderer useTarget(JComponent target) {
        this.target = target;
        return this;
    }

    public void render() {
        List<RenderingTask> renderingTasks = rubiksCube.getRenderingTasks(viewPlane);
        Graphics graphics = target.getGraphics();
        renderingTasks
                .stream()
                .filter(task -> task instanceof SurfaceRenderingTask)
                .forEach(task -> task.render(graphics));
    }

    private BufferedImage image() {
        return new BufferedImage(target.getWidth(), target.getHeight(), TYPE_3BYTE_BGR);
    }
}
