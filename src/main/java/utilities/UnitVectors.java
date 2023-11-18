package utilities;

import maths.coordinate.Vector3D;

import static maths.coordinate.Vector3D.vector;

public class UnitVectors {
    public static final Vector3D X_POSITIVE = vector(1, 0, 0);
    public static final Vector3D X_NEGATIVE = vector(-1, 0, 0);
    public static final Vector3D Y_POSITIVE = vector(0, 1, 0);
    public static final Vector3D Y_NEGATIVE = vector(0, -1, 0);
    public static final Vector3D Z_POSITIVE = vector(0, 0, 1);
    public static final Vector3D Z_NEGATIVE = vector(0, 0, -1);
}
