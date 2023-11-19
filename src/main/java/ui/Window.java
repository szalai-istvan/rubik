package ui;

import maths.coordinate.plane.ViewPlane;
import rubik.RubiksCube;
import ui.renderer.RubiksCubeRenderer;

import javax.swing.*;

import static java.awt.Color.BLACK;
import static maths.coordinate.vector.UnitVector3D.unitVector;
import static maths.coordinate.vector.rotator.Rotator.HORIZONTAL_PERPENDICULAR_AXIS;
import static maths.coordinate.vector.rotator.Rotator.Z_AXIS;
import static utilities.Constants.ROTATION_UNIT;
import static utilities.Constants.SCREEN_SIZE;

public class Window extends JFrame {
    private RubiksCube rubiksCube = new RubiksCube();
    private ViewPlane view = new ViewPlane(unitVector(1, 1, 1), 400L);
    final JPanel canvas = new JPanel();
    public Window() {
        WindowSetupTool.of(this).setup();
        render();
    }

    private void keepRotating() {
        while (true) {
            try {
                Thread.sleep(200L);
            rotateRight();
            } catch (InterruptedException e) {

            }
        }
    }

    private void render() {
        new Thread(() -> performRendering()).start();
    }

    private void performRendering() {
        RubiksCubeRenderer
                .draw(rubiksCube)
                .on(view)
                .useTarget(canvas)
                .render();
    }

    public void rotateLeft() {
        view = view.rotateAround(Z_AXIS, ROTATION_UNIT);
        render();
    }

    public void rotateRight() {
        view = view.rotateAround(Z_AXIS, - ROTATION_UNIT);
        render();
    }

    public void rotateUp() {
        view = view.rotateAround(HORIZONTAL_PERPENDICULAR_AXIS, ROTATION_UNIT);
        render();
    }

    public void rotateDown() {
        view = view.rotateAround(HORIZONTAL_PERPENDICULAR_AXIS, - ROTATION_UNIT);
        render();
    }
}
