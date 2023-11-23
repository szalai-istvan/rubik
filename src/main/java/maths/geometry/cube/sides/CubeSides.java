package maths.geometry.cube.sides;

import maths.coordinate.vector.Vector3D;
import maths.coordinate.plane.ViewPlane;
import maths.geometry.CornerCalculator;
import maths.geometry.cube.Cube;
import ui.renderer.RenderingTask;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.Color.BLACK;
import static java.util.stream.Collectors.toList;
import static utilities.UnitVectors.*;

public class CubeSides {
    protected Map<Vector3D, Square> sides;
    protected final Cube cube;
    private final CornerCalculator<Square> cornerCalculator;

    public CubeSides(Cube cube, Collection<Square> squares) {
        this.cube = cube;
        cornerCalculator = new CornerCalculator<>(Square::getNormalDirection)
                .midpoint(cube.getMidPoint());
        sides = new HashMap<>();
        for (Square s : squares) {
            addSide(s);
        }
    }

    public CubeSides(Cube cube) {
        this.cube = cube;
        cornerCalculator = new CornerCalculator<>(Square::getNormalDirection)
                .midpoint(cube.getMidPoint());
        sides = new HashMap<>();
        init();
    }

    private void init() {
        this
                .setXPositive(BLACK)
                .setXNegative(BLACK)
                .setYPositive(BLACK)
                .setYNegative(BLACK)
                .setZPositive(BLACK)
                .setZNegative(BLACK);
    }

    public CubeSides setXPositive(Color color) {
        return setSide(X_POSITIVE, color);
    }

    public CubeSides setXNegative(Color color) {
        return setSide(X_NEGATIVE, color);
    }

    public CubeSides setYPositive(Color color) {
        return setSide(Y_POSITIVE, color);
    }

    public CubeSides setYNegative(Color color) {
        return setSide(Y_NEGATIVE, color);
    }

    public CubeSides setZPositive(Color color) {
        return setSide(Z_POSITIVE, color);
    }

    public CubeSides setZNegative(Color color) {
        return setSide(Z_NEGATIVE, color);
    }

    public List<Square> squares() {
        return new ArrayList<>(sides.values());
    }

    public List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
        return sides.values().stream()
                .filter(square -> square.isLookingAt(viewPlane))
                .flatMap(square -> square.getRenderingTasks(viewPlane).stream())
                .collect(toList());
    }

    public CubeSides setSide(Vector3D vector, Color color) {
        Square square = new Square(color);
        square.setNormalDirection(vector);
        Vector3D[] corners = calculateCornerPoints(square);
        square.setCorners(corners);
        sides.put(vector, square);
        return this;
    }

    private CubeSides addSide(Square s) {
        sides.put(s.getNormalDirection(), s);
        return this;
    }

    protected Vector3D[] calculateCornerPoints(Square square) {
        return cornerCalculator.calculateCornerPoints(square);
    }
}
