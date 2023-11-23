package maths.coordinate.vector;

import maths.coordinate.vector.rotator.VectorRotator;

public class Vector3D {
    private final double x;
    private final double y;
    private final double z;

    Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public static Vector3D vector(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    public Vector3D plus(Vector3D v) {
        return new Vector3D(
                x + v.x,
                y + v.y,
                z + v.z
        );
    }

    public Vector3D subtract(Vector3D v) {
        return new Vector3D(
                x - v.x,
                y - v.y,
                z - v.z
        );
    }

    public Vector3D multiply(Vector3D v) {
        return new Vector3D(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    public Vector3D multiply(double a) {
        return new Vector3D(
                a * x,
                a * y,
                a * z
        );
    }

    public double scalarMultiply(Vector3D v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public double abs() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public double cosAlpha(Vector3D v) {
        double nominator = scalarMultiply(v);
        double denominator = abs() * v.abs();
        return nominator / denominator;
    }

    public UnitVector3D toUnitVector() {
        Vector3D divide = divide(abs());
        return new UnitVector3D(divide.x, divide.y, divide.z);
    }

    public Vector3D rotateAround(VectorRotator rotator, double degrees) {
        return rotator.rotate(this, degrees);
    }

    public double distanceFrom(Vector3D point) {
        return Math.sqrt((point.x - x) * (point.x - x) +
                (point.y - y) * (point.y - y) +
                (point.z - z) * (point.z - z));
    }

    public boolean isParallelTo(Vector3D v) {
        return this.multiply(v).abs() < 1E-10;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode *= x;
        hashCode += 1;
        hashCode *= y;
        hashCode += 1;
        hashCode *= z;
        hashCode += 1;
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Vector3D this_;
        if (obj instanceof UnitVector3D) {
            this_ = toUnitVector();
        } else {
            this_ = this;
        }

        if (obj instanceof Vector3D) {
            Vector3D v = (Vector3D) obj;
            return this_.x == v.x && this_.y == v.y && this_.z == v.z;
        }

        return false;
    }

    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    private Vector3D divide(double a) {
        return multiply(1 / a);
    }
}
