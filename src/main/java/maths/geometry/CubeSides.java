package maths.geometry;

import maths.coordinate.vector.Vector3D;
import maths.coordinate.plane.ViewPlane;
import ui.renderer.RenderingTask;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.Color.BLACK;
import static java.util.stream.Collectors.toList;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.Constants.*;
import static utilities.UnitVectors.*;

public class CubeSides {
    protected Map<Vector3D, Square> sides;

    protected final Cube cube;

    public CubeSides(Cube cube, Collection<Square> squares) {
        this.cube = cube;
        sides = new HashMap<>();
        for (Square s : squares) {
            addSide(s);
        }
    }

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

    public List<Square> squares() {
        return new ArrayList<>(sides.values());
    }

    List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
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
        Vector3D v = square.getNormalDirection();
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
