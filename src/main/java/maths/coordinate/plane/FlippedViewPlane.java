package maths.coordinate.plane;


import maths.coordinate.Coordinates;
import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.coordinate.vector.rotator.VectorRotator;
import rubik.FlippedRubiksCubeShifter;
import rubik.RubiksCubeShifter;

import static maths.coordinate.vector.Vector3D.vector;
import static utilities.Constants.SCREEN_HEIGHT;
import static utilities.Constants.SCREEN_WIDTH;

public class FlippedViewPlane extends ViewPlane {
    public FlippedViewPlane(UnitVector3D normalVector, Vector3D centerPoint) {
        super(normalVector, centerPoint);
    }

    @Override
    public Coordinates toCoordinates(double x, double y) {
        return new Coordinates(
                SCREEN_WIDTH - x,
                SCREEN_HEIGHT - y
        );
    }

    @Override
    public ViewPlane rotateAround(VectorRotator rotator, double degrees) {
        return new FlippedViewPlane(
                normalVector.rotateAround(rotator, -1.00 * degrees).toUnitVector(),
                centerPoint.rotateAround(rotator, -1.00 * degrees)
        );
    }

    @Override
    public ViewPlane flip() {
        return new ViewPlane(
                vector(normalVector.x(), normalVector.y(), normalVector.z() * -1.00).toUnitVector(),
                vector(centerPoint.x(), centerPoint.y(), centerPoint.z() * -1.00)
        );
    }

    @Override
    public RubiksCubeShifter rubiksCubeShifter() {
        return new FlippedRubiksCubeShifter();
    }
}
