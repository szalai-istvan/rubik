package ui.renderer;

import maths.coordinate.plane.ViewPlane;
import rubik.RubiksCube;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static java.awt.Color.*;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static utilities.Constants.SCREEN_HEIGHT;
import static utilities.Constants.SCREEN_WIDTH;

public class RubiksCubeRenderer {
    private JComponent target;
    private ViewPlane viewPlane;
    private RubiksCube rubiksCube;
    private List<RenderingTask> renderingTasks;

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
        BufferedImage image = image();
        Graphics graphics = image.getGraphics();
        prepare(graphics);

        renderingTasks = rubiksCube.getRenderingTasks(viewPlane);
        renderingTasks
                .stream()
                .map(task -> task.withViewPlane(viewPlane))
                .forEach(task -> task.render(graphics));

        Graphics targetGraphics = target.getGraphics();
        targetGraphics.drawImage(image, 0, 0, null);

        graphics.dispose();
        targetGraphics.dispose();
    }

    private void prepare(Graphics graphics) {
        fillWithBlack(graphics);
        drawWhiteCircleInCenter(graphics);
    }

    private void fillWithBlack(Graphics graphics) {
        graphics.setColor(BLACK);
        graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void drawWhiteCircleInCenter(Graphics graphics) {
        int shade = 0;
        int dOffset = 0;
        while (shade < 192) {
            drawCenteredCircle(
                    graphics,
                    600 + dOffset,
                    new Color(shade, shade, shade));
            shade += 16;
            dOffset -= 10;
        }
    }

    private void drawCenteredCircle(Graphics graphics, int diameter, Color color) {
        int x = SCREEN_WIDTH / 2 - diameter / 2;
        int y = SCREEN_HEIGHT / 2 - diameter / 2;
        graphics.setColor(color);
        graphics.fillOval(x, y, diameter, diameter);
    }

    private BufferedImage image() {
        return new BufferedImage(target.getWidth(), target.getHeight(), TYPE_3BYTE_BGR);
    }
}
