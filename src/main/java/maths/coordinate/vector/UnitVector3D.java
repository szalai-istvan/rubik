package maths.coordinate.vector;

public class UnitVector3D extends Vector3D {
    UnitVector3D(double x, double y, double z) {
        super(x, y, z);
    }

    public static UnitVector3D unitVector(double x, double y, double z) {
        return vector(x, y, z).toUnitVector();
    }
}
