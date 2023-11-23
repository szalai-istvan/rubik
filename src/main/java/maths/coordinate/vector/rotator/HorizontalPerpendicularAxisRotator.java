package maths.coordinate.vector.rotator;

import maths.coordinate.vector.Vector3D;

import static java.lang.Math.*;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.Z_POSITIVE;

public class HorizontalPerpendicularAxisRotator implements VectorRotator {
    @Override
    public Vector3D performRotation(Vector3D v, double radians) {
        return vector(
                v.x(),
                v.y(),
                z(v, radians)
        );
    }

    private double z(Vector3D v, double radians) {
        double cosAlpha = v.cosAlpha(Z_POSITIVE);
        double alpha = acos(cosAlpha);
        double degrees = toDegrees(alpha);

        if (shouldMove(degrees, radians)) {
            return v.z() / cosAlpha * cos(alpha + radians);
        } else {
            return v.z();
        }
    }

    private boolean shouldMove(double degrees, double radians) {
        return degrees > 20.00 && radians < 0.00 ||
                degrees < 160.00 && radians > 0.00;
    }


}
