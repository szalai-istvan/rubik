package maths.geometry;

import maths.coordinate.Vector3D;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Color.BLACK;
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
        for (int x_ : x) {
            for (int y_ : y) {
                for (int z_ : z) {
                    corners[index++] = vector(
                            cube.midPoint.x() + x_ * HALF_LENGTH,
                            cube.midPoint.y() + y_ * HALF_LENGTH,
                            cube.midPoint.z() + z_ * HALF_LENGTH
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
