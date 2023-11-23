package ui;

import maths.coordinate.plane.ViewPlane;
import maths.coordinate.vector.Vector3D;
import maths.coordinate.vector.rotator.HorizontalPerpendicularAxisRotator;
import rubik.RubiksCube;
import rubik.TempCube;
import ui.renderer.RubiksCubeRenderer;

import javax.swing.*;

import java.util.List;

import static java.util.stream.IntStream.range;
import static maths.coordinate.vector.UnitVector3D.unitVector;
import static maths.coordinate.vector.rotator.BasicVectorRotator.Z_AXIS;
import static utilities.Constants.ROTATION_UNIT;
import static utilities.UnitVectors.randomUnitVector;

public class Window extends JFrame {
    RubiksCube rubiksCube = new RubiksCube();
    private ViewPlane view = new ViewPlane(unitVector(1, 1, 1), 400L);
    final JPanel canvas = new JPanel();

    public Window() {
        WindowSetupTool.of(this).setup();
        render();
    }

    public ViewPlane getView() {
        return view;
    }

    public void flip() {
        view = view.flip();
        render();
    }

    public void rotateViewLeft() {
        view = view.rotateAround(Z_AXIS, ROTATION_UNIT);
        render();
    }

    public void rotateViewRight() {
        view = view.rotateAround(Z_AXIS, -ROTATION_UNIT);
        render();
    }

    public void rotateViewUp() {
        view = view.rotateAround(new HorizontalPerpendicularAxisRotator(), ROTATION_UNIT);
        render();
    }

    public void rotateViewDown() {
        view = view.rotateAround(new HorizontalPerpendicularAxisRotator(), -ROTATION_UNIT);
        render();
    }

    public void selectFace(Vector3D vector) {
        rubiksCube.select(vector);
        render();
    }

    public void shiftSelectedFaceLeft() {
        view.rubiksCubeShifter().shiftSelectedFaceLeft(rubiksCube);
        render();
    }

    public void shiftSelectedFaceRight() {
        view.rubiksCubeShifter().shiftSelectedFaceRight(rubiksCube);
        render();
    }

    public void shiftSelectedFaceUp() {
        view.rubiksCubeShifter().shiftSelectedFaceUp(rubiksCube);
        render();
    }

    public void shiftSelectedFaceDown() {
        view.rubiksCubeShifter().shiftSelectedFaceDown(rubiksCube);
        render();
    }

    public void rotateSelectedFaceLeft() {
        List<TempCube> animationSteps = rubiksCube.animateSelectedLeft();
        renderAnimationSteps(animationSteps);
        rubiksCube.rotateSelectedLeft();
    }

    public void rotateSelectedFaceRight() {
        List<TempCube> animationSteps = rubiksCube.animateSelectedRight();
        renderAnimationSteps(animationSteps);
        rubiksCube.rotateSelectedRight();
    }

    public void scramble() {
        range(0, 40).forEach(i -> {
            rubiksCube.select(randomUnitVector());
            rubiksCube.rotateSelectedRight();
        });
        render();
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
        new Thread(this::performRendering).start();
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
}
