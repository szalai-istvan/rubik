package maths.coordinate.vector.rotator;

import maths.coordinate.vector.Vector3D;

import static java.lang.Math.*;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.Z_POSITIVE;

public enum Rotator {
    X_AXIS {
        @Override
        protected Vector3D performRotation(Vector3D v, double radians) {
            return vector(
                    v.x(),
                    cos(radians) * v.y() - sin(radians) * v.z(),
                    sin(radians) * v.y() + cos(radians) * v.z()
            );
        }
    },
    Y_AXIS {
        @Override
        protected Vector3D performRotation(Vector3D v, double radians) {
            return vector(
                    cos(radians) * v.x() + sin(radians) * v.z(),
                    v.y(),
                    -sin(radians) * v.x() + cos(radians) * v.z()
            );
        }
    },
    Z_AXIS {
        @Override
        protected Vector3D performRotation(Vector3D v, double radians) {
            return vector(
                    cos(radians) * v.x() - sin(radians) * v.y(),
                    sin(radians) * v.x() + cos(radians) * v.y(),
                    v.z()
            );
        }
    },
    HORIZONTAL_PERPENDICULAR_AXIS {
        @Override
        protected Vector3D performRotation(Vector3D v, double radians) {
            double cosAlpha = v.cosAlpha(Z_POSITIVE);
            double alpha = acos(cosAlpha);
            double degrees = toDegrees(alpha);
            double z;

            boolean shouldMove = toDegrees(alpha) > 20.00 && radians < 0.00 ||
                    toDegrees(alpha) < 160.00 && radians > 0.00;

            if (shouldMove) {
                z = v.z() / cosAlpha * cos(alpha + radians);
            } else {
                z = v.z();
            }

            return vector(
                    v.x(),
                    v.y(),
                    z
            );
        }
    };

    public Vector3D rotate(Vector3D v, double degrees) {
        double radians = toRadians(degrees);
        return performRotation(v, radians);
    }

    protected abstract Vector3D performRotation(Vector3D v, double radians);
}
