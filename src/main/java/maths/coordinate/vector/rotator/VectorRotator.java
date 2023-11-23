package maths.coordinate.vector.rotator;

import maths.coordinate.vector.Vector3D;

import static java.lang.Math.toRadians;

public interface VectorRotator {
    default Vector3D rotate(Vector3D v, double degrees) {
        double radians = toRadians(degrees);
        return performRotation(v, radians);
    }

    Vector3D performRotation(Vector3D v, double radians);
}
