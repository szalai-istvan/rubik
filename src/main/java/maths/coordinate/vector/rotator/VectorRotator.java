package maths.coordinate.vector.rotator;

import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.Math.*;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.*;

public enum VectorRotator {
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


    private static final Map<UnitVector3D, VectorRotator> ROTATOR_MAP = Map.of(
            X_POSITIVE, X_AXIS,
            X_NEGATIVE, X_AXIS,
            Y_POSITIVE, Y_AXIS,
            Y_NEGATIVE, Y_AXIS,
            Z_POSITIVE, Z_AXIS,
            Z_NEGATIVE, Z_AXIS
    );

    public static VectorRotator of(UnitVector3D axis) {
        VectorRotator mapResult = ROTATOR_MAP.get(axis);
        if (mapResult != null) {
            return mapResult;
        }

        return ROTATOR_MAP.keySet().stream()
                .reduce((v1, v2) -> v1.subtract(axis).abs() > v2.subtract(axis).abs() ? v2 : v1)
                .map(closestVector -> ROTATOR_MAP.get(closestVector))
                .orElseThrow(NoSuchElementException::new);
    }

    public Vector3D rotate(Vector3D v, double degrees) {
        double radians = toRadians(degrees);
        return performRotation(v, radians);
    }

    protected abstract Vector3D performRotation(Vector3D v, double radians);
}
