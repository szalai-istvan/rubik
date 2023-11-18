package maths.coordinate;

import static utilities.Constants.SCREEN_SIZE;
import static utilities.UnitVectors.Z_POSITIVE;

public class ViewPlane {
    private final UnitVector3D normalVector;
    private final Vector3D centerPoint;
    private final InPlaneVectors inPlaneVectors;
    private final Vector3D leftCorner;

    public ViewPlane(UnitVector3D normalVector, Vector3D centerPoint) {
        this.normalVector = normalVector;
        this.centerPoint = centerPoint;
        inPlaneVectors = calculateInPlaneVectors();
        leftCorner = calculateLeftCorner();
    }

    public ViewPlane(UnitVector3D normalVector, double viewRadius) {
        this(normalVector, normalVector.multiply(viewRadius));
    }

    private InPlaneVectors calculateInPlaneVectors() {
        UnitVector3D horizontal = Z_POSITIVE.multiply(normalVector).toUnitVector();
        UnitVector3D vertical = horizontal.multiply(normalVector).toUnitVector();
        return new InPlaneVectors(horizontal, vertical);
    }

    public Vector3D getNormalVector() {
        return normalVector;
    }

    public UnitVector3D horizontalUnitVector() {
        return inPlaneVectors.getHorizontalUnit();
    }

    public UnitVector3D verticalUnitVector() {
        return inPlaneVectors.getVerticalUnit();
    }

    public Coordinates projectPointToPlane(Vector3D point) {
        Vector3D p = calculateProjectedPoint(point);
        Vector3D h = horizontalUnitVector();
        Vector3D v = verticalUnitVector();
        Vector3D l = leftCorner;

        double y = (p.z() - l.z()) / v.z();
        double x; // TODO cover h.x() == 0.00 case
        if (h.x() == 0.00) {
            x = (p.y() - y * v.y() - l.y()) / h.y();
        } else {
            x = (p.x() - y * v.x() - l.x()) / h.x();
        }
        System.out.println(toString() + " projected point " + point + " -> " + new Coordinates(x, y));
        return new Coordinates(x, y);
    }

    private Vector3D calculateProjectedPoint(Vector3D point) {
        Vector3D vectorPointingToPoint = point.subtract(centerPoint);
        double distance = Math.abs(vectorPointingToPoint.scalarMultiply(normalVector));
        return point.plus(normalVector.multiply(distance));
    }

    private Vector3D calculateLeftCorner() {
        double offsetX = SCREEN_SIZE.getWidth() / 2;
        double offsetY = SCREEN_SIZE.getHeight() / 2;

        UnitVector3D horizontalUnit = horizontalUnitVector();
        UnitVector3D verticalUnit = verticalUnitVector();

        return new Vector3D(
                centerPoint.x() - offsetX * horizontalUnit.x() - offsetY * verticalUnit.x(),
                centerPoint.y() - offsetX * horizontalUnit.y() + offsetY * verticalUnit.y(),
                centerPoint.z() - offsetX * horizontalUnit.z() - offsetY * verticalUnit.z()
        );
    }

    public String toString() {
        return "[v="+normalVector + ", p=" + centerPoint+"]";
    }
}
