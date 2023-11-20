package ui;

import maths.coordinate.plane.ViewPlane;
import rubik.RubiksCube;
import rubik.TempCube;
import ui.renderer.RubiksCubeRenderer;

import javax.swing.*;

import java.util.List;

import static java.util.stream.IntStream.range;
import static maths.coordinate.vector.UnitVector3D.unitVector;
import static maths.coordinate.vector.rotator.VectorRotator.HORIZONTAL_PERPENDICULAR_AXIS;
import static maths.coordinate.vector.rotator.VectorRotator.Z_AXIS;
import static utilities.Constants.ROTATION_UNIT;
import static utilities.UnitVectors.randomUnitVector;

public class Window extends JFrame {
    private RubiksCube rubiksCube = new RubiksCube();
    private ViewPlane view = new ViewPlane(unitVector(1, 1, 1), 400L);
    final JPanel canvas = new JPanel();

    public Window() {
        WindowSetupTool.of(this).setup();
        render();
    }

    public void flip() {
        view = view.flip();
        render();
    }

    public void rotateLeft() {
        view = view.rotateAround(Z_AXIS, ROTATION_UNIT);
        render();
    }

    public void rotateRight() {
        view = view.rotateAround(Z_AXIS, -ROTATION_UNIT);
        render();
    }

    public void rotateUp() {
        view = view.rotateAround(HORIZONTAL_PERPENDICULAR_AXIS, ROTATION_UNIT);
        render();
    }

    public void rotateDown() {
        view = view.rotateAround(HORIZONTAL_PERPENDICULAR_AXIS, -ROTATION_UNIT);
        render();
    }

    public void shiftLeft() {
        rubiksCube.shiftLeft();
        render();
    }

    public void shiftRight() {
        rubiksCube.shiftRight();
        render();
    }

    public void shiftUp() {
        rubiksCube.shiftUp();
        render();
    }

    public void shiftDown() {
        rubiksCube.shiftDown();
        render();
    }

    public void rotateSelectedFaceLeft() {
        rubiksCube.stepSelectedLeft();
        render();
    }

    public void rotateSelectedFaceRight() {
        rubiksCube.stepSelectedRight();
        render();
    }

    public void rotateSelectedFaceLeftWithAnimation() {
        List<TempCube> animationSteps = rubiksCube.animateSelectedLeft();
        renderAnimationSteps(animationSteps);
        rubiksCube.stepSelectedLeft();
    }

    public void rotateSelectedFaceRightWithAnimation() {
        List<TempCube> animationSteps = rubiksCube.animateSelectedRight();
        renderAnimationSteps(animationSteps);
        rubiksCube.stepSelectedRight();
    }

    private void renderAnimationSteps(List<TempCube> animationSteps) {
        Thread thread = new Thread(() -> {
            for (TempCube t : animationSteps) {
                performRendering(t);
            }
        });
        thread.start();
    }

    private void render() {
        new Thread(() -> performRendering()).start();
    }

    private void performRendering() {
        performRendering(rubiksCube);
    }

    private void performRendering(RubiksCube rubiksCube) {
        RubiksCubeRenderer
                .draw(rubiksCube)
                .on(view)
                .useTarget(canvas)
                .render();
    }

    public void scramble() {
        range(0, 40)
                .forEach(i -> {
                    rubiksCube.select(randomUnitVector());
                    rubiksCube.stepSelectedRight();
                });
        render();
    }
}
