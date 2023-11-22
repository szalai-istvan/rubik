package maths.geometry;

import maths.coordinate.vector.Vector3D;

import java.util.function.Function;

import static maths.coordinate.vector.Vector3D.vector;
import static utilities.Constants.*;

public class CornerCalculator<T> {

    private final Function<T, Vector3D> vectorFactory;
    private int figureSize = HALF_LENGTH;
    private Vector3D midpoint = vector(0, 0, 0);

    public CornerCalculator(Function<T, Vector3D> vectorFactory) {
        this.vectorFactory = vectorFactory;
    }

    public CornerCalculator<T> midpoint(Vector3D midpoint) {
        this.midpoint = midpoint;
        return this;
    }

    public CornerCalculator<T> figuresize(int figureSize) {
        this.figureSize = figureSize;
        return this;
    }


    public Vector3D[] calculateCornerPoints(T t) {
        Vector3D directionVector = vectorFactory.apply(t);
        int[] x = toStepArray(directionVector.x());
        int[] y = toStepArray(directionVector.y());
        int[] z = toStepArray(directionVector.z());

        Vector3D[] corners = new Vector3D[x.length * y.length * z.length];
        int index = 0;
        for (int ix = 0; ix < x.length; ix++) {
            for (int iy = 0; iy < y.length; iy++) {
                for (int iz = 0; iz < z.length; iz++) {
                    corners[INDEX_ORDER[index++]] = vector(
                            midpoint.x() + x[ix] * figureSize,
                            midpoint.y() + y[iy] * figureSize,
                            midpoint.z() + z[iz] * figureSize
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
