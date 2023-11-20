package maths.geometry.cuberotator;

import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.geometry.Cube;
import maths.geometry.CubeSides;

import java.util.Collection;

import static java.lang.Math.signum;

public class CubeStepper extends CubeRotator {

    public CubeStepper(Collection<Cube> cubes, UnitVector3D axis, double angle) {
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

    private Vector3D rotatePoint(Vector3D point) {
        if (point.isParallelTo(axis)) {
            return point;
        }
        return point
                .multiply(axis)
                .multiply(signum(angle));
    }
}
