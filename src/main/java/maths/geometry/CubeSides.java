package maths.geometry;

import maths.coordinate.Vector3D;
import maths.coordinate.ViewPlane;
import ui.renderer.RenderingTask;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.awt.Color.BLACK;
import static java.util.stream.Collectors.toList;
import static maths.coordinate.Vector3D.vector;
import static utilities.Constants.*;
import static utilities.UnitVectors.*;

public class CubeSides {
    final Map<Vector3D, Square> sides;

    private final Cube cube;

    public CubeSides(Cube cube) {
        this.cube = cube;
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

    List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
        return sides.values().stream()
                .filter(square -> square.isLookingAt(viewPlane))
                .flatMap(square -> square.getRenderingTasks(viewPlane).stream())
                .collect(toList());
    }

    private CubeSides setSide(Vector3D vector, Color color) {
        Square square = new Square(color);
        square.setNormalDirection(vector);
        Vector3D[] corners = calculateCornerPoints(vector);
        square.setCorners(corners);
        sides.put(vector, square);
        return this;
    }

    private Vector3D[] calculateCornerPoints(Vector3D v) {
        int[] x = toStepArray(v.x());
        int[] y = toStepArray(v.y());
        int[] z = toStepArray(v.z());

        Vector3D[] corners = new Vector3D[x.length * y.length * z.length];
        int index = 0;
        for (int ix = 0; ix < x.length; ix++) {
            for (int iy = 0; iy < y.length; iy++) {
                for (int iz = 0; iz < z.length; iz++) {
                    corners[INDEX_ORDER[index++]] = vector(
                            cube.midPoint.x() + x[ix] * HALF_LENGTH,
                            cube.midPoint.y() + y[iy] * HALF_LENGTH,
                            cube.midPoint.z() + z[iz] * HALF_LENGTH
                    );
                }
            }
        }

        return corners;
    }

    private int[] toStepArray(double x) {
        if (x == 1.00) {
            return POSITIVE;
        } else if (x == 0.00) {
            return POSITIVE_NEGATIVE;
        } else if (x == -1.00) {
            return NEGATIVE;
        }
        throw new IllegalArgumentException();
    }
}
