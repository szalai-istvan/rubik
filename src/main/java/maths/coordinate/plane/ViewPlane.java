package maths.coordinate.plane;

import maths.coordinate.Coordinates;
import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.coordinate.vector.rotator.VectorRotator;
import maths.geometry.cube.Cube;
import rubik.RubiksCubeShifter;

import static java.lang.Math.abs;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.Constants.*;

public class ViewPlane {
    protected final UnitVector3D normalVector;
    protected final Vector3D centerPoint;
    private final InPlaneVectors inPlaneVectors;
    private final Vector3D leftCorner;

    public ViewPlane(UnitVector3D normalVector, Vector3D centerPoint) {
        this.normalVector = normalVector;
        this.centerPoint = centerPoint;
        inPlaneVectors = InPlaneVectors.of(this);
        leftCorner = calculateLeftCorner();
    }

    public ViewPlane(UnitVector3D normalVector, double viewRadius) {
        this(normalVector, normalVector.multiply(viewRadius));
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
        double x;
        if (abs(h.x()) < 1E-10) {
            x = (p.y() - y * v.y() - l.y()) / h.y();
        } else {
            x = (p.x() - y * v.x() - l.x()) / h.x();
        }

        return toCoordinates(x, y);
    }

    public ViewPlane rotateAround(VectorRotator rotator, double degrees) {
        return new ViewPlane(
                normalVector.rotateAround(rotator, degrees).toUnitVector(),
                centerPoint.rotateAround(rotator, degrees)
        );
    }

    public double distanceFrom(Cube cube) {
        Vector3D point = cube.getMidPoint();
        Vector3D projected = calculateProjectedPoint(point);
        return projected.distanceFrom(point);
    }

    public String toString() {
        return "[v=" + normalVector + ", p=" + centerPoint + "]";
    }

    public ViewPlane flip() {
        return new FlippedViewPlane(
                vector(normalVector.x(), normalVector.y(), normalVector.z() * -1.00).toUnitVector(),
                vector(centerPoint.x(), centerPoint.y(), centerPoint.z() * -1.00)
        );
    }

    public RubiksCubeShifter rubiksCubeShifter() {
        return new RubiksCubeShifter();
    }

    protected Coordinates toCoordinates(double x, double y) {
        return new Coordinates(x, y);
    }

    private Vector3D calculateProjectedPoint(Vector3D point) {
        Vector3D vectorPointingToPoint = point.subtract(centerPoint);
        double distance = abs(vectorPointingToPoint.scalarMultiply(normalVector));
        return point.plus(normalVector.multiply(distance));
    }

    private Vector3D calculateLeftCorner() {
        double offsetX = SCREEN_SIZE.getWidth() / 2;
        double offsetY = SCREEN_SIZE.getHeight() / 2;

        UnitVector3D horizontalUnit = horizontalUnitVector();
        UnitVector3D verticalUnit = verticalUnitVector();

        return vector(
                centerPoint.x() - offsetX * horizontalUnit.x() - offsetY * verticalUnit.x(),
                centerPoint.y() - offsetX * horizontalUnit.y() + offsetY * verticalUnit.y(),
                centerPoint.z() - offsetX * horizontalUnit.z() - offsetY * verticalUnit.z()
        );
    }
}
