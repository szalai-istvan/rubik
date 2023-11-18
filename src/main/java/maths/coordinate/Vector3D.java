package maths.coordinate;

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
        return z;
    }

    public double z() {
        return z;
    }

    public static Vector3D vector(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    public Vector3D add(Vector3D v) {
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

    public Vector3D divide(double a) {
        return multiply(1 / a);
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

        if (obj instanceof UnitVector3D) {
            return toUnitVector().equals(obj);
        }

        if (obj instanceof Vector3D) {
            Vector3D v = (Vector3D) obj;
            return x == v.x && y == v.y && z == v.z;
        }

        return false;
    }
}
