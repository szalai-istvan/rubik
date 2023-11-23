package maths.geometry.cuberotator;

import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.geometry.cube.Cube;
import maths.geometry.cube.sides.CubeSides;

import java.util.Collection;

import static java.lang.Math.signum;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.*;

public class NinetyDegreesCubeRotator extends CubeRotator {

    public NinetyDegreesCubeRotator(Collection<Cube> cubes, UnitVector3D axis, double angle) {
        super(cubes, axis, angle);
    }

    @Override
    protected Cube rotateCube(Cube cube) {
        Vector3D rotatedMidpoint = rotateMidPoint(cube);
        Cube rotatedCube = new Cube(rotatedMidpoint);
        CubeSides sides = recalculateSquares(cube, rotatedCube);
        rotatedCube.setSides(sides);
        return rotatedCube;
    }

    private CubeSides recalculateSquares(Cube cube, Cube rotatedCube) {
        CubeSides newSides = new CubeSides(rotatedCube);
        cube.sides().squares().forEach(square -> newSides.setSide(
                rotatePoint(square.getNormalDirection()),
                square.getColor()
        ));
        return newSides;
    }

    private Vector3D rotateMidPoint(Cube cube) {
        Vector3D filteredPoint = filterVector(cube.getMidPoint());
        return rotatePoint(filteredPoint)
                .plus(cube.getMidPoint())
                .subtract(filteredPoint);
    }

    private Vector3D filterVector(Vector3D v) {
        if (axis.isParallelTo(X_POSITIVE)) {
            return vector(0, v.y(), v.z());
        } else if (axis.isParallelTo(Y_NEGATIVE)) {
            return vector(v.x(), 0, v.z());
        } else if (axis.isParallelTo(Z_POSITIVE)) {
            return vector(v.x(), v.y(), 0);
        }
        return v;
    }

    private Vector3D rotatePoint(Vector3D point) {
        if (point.isParallelTo(axis)) {
            return point;
        }
        return point
                .multiply(axis)
                .multiply(signum(angle));
    }
}
