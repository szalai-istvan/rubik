package utilities;

import maths.coordinate.UnitVector3D;
import maths.coordinate.Vector3D;

import static maths.coordinate.UnitVector3D.unitVector;
import static maths.coordinate.Vector3D.vector;

public class UnitVectors {
    public static final UnitVector3D X_POSITIVE = unitVector(1, 0, 0);
    public static final UnitVector3D X_NEGATIVE = unitVector(-1, 0, 0);
    public static final UnitVector3D Y_POSITIVE = unitVector(0, 1, 0);
    public static final UnitVector3D Y_NEGATIVE = unitVector(0, -1, 0);
    public static final UnitVector3D Z_POSITIVE = unitVector(0, 0, 1);
    public static final UnitVector3D Z_NEGATIVE = unitVector(0, 0, -1);
}
