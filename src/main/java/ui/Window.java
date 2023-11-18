package ui;

import maths.coordinate.ViewPlane;
import rubik.RubiksCube;
import ui.renderer.RubiksCubeRenderer;

import javax.swing.*;

import java.awt.*;

import static java.awt.Color.BLACK;
import static maths.coordinate.UnitVector3D.unitVector;
import static utilities.Constants.SCREEN_SIZE;

public class Window extends JFrame {
    private RubiksCube rubiksCube = new RubiksCube();
    private ViewPlane view = new ViewPlane(unitVector(1, 1, 1), 500);
    private final JPanel canvas = new JPanel();
    public Window() {
        setSize(SCREEN_SIZE);
        setLayout(null);
        setBackground(BLACK);
        add(canvas);
        canvas.setSize(SCREEN_SIZE);
        setVisible(true);
        render();
    }

    private void render() {
        RubiksCubeRenderer
                .draw(rubiksCube)
                .on(view)
                .useTarget(canvas)
                .render();

    }
}
