package maths.geometry.cuberotator;

import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.coordinate.vector.rotator.VectorRotator;
import maths.geometry.Cube;
import maths.geometry.CubeSides;
import maths.geometry.RandomDirectionCubeSides;
import maths.geometry.Square;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnimatedCubeRotator extends CubeRotator {

    private final VectorRotator vectorRotator;

    public AnimatedCubeRotator(Collection<Cube> cubes, UnitVector3D axis, double angle) {
        super(cubes, axis, angle);
        vectorRotator = VectorRotator.of(axis);
        revertAngleIfNegativeDirection();
    }

    private void revertAngleIfNegativeDirection() {
        if (axis.x() + axis.y() + axis.z() < 0) {
            angle *= -1;
        }
    }

    @Override
    protected Cube rotateCube(Cube cube) {
        Vector3D midPoint = cube.getMidPoint().rotateAround(vectorRotator, angle);
        Cube nextCube = new Cube(midPoint);
        nextCube.setSides(rotateSquares(cube, nextCube));
        return nextCube;
    }

    private CubeSides rotateSquares(Cube cube, Cube nextCube) {
        List<Square> squares = cube.sides().squares().stream()
                .map(s -> rotateSquare(s))
                .collect(toList());

        return new RandomDirectionCubeSides(nextCube, squares);
    }

    private Square rotateSquare(Square square) {
        Vector3D normalDirection = square.getNormalDirection().rotateAround(vectorRotator, angle);
        Vector3D[] corners = recalculateCornerPoints(square);
        Square s = new Square(square.getColor());
        s.setNormalDirection(normalDirection);
        s.setCorners(corners);
        return s;
    }

    private Vector3D[] recalculateCornerPoints(Square square) {
        Vector3D[] corners = square.getCorners();
        return Arrays.stream(corners)
                .map(corner -> corner.rotateAround(vectorRotator, angle))
                .collect(toList()).toArray(new Vector3D[corners.length]);
    }
}
