package maths.geometry.cube.sides;

import maths.coordinate.vector.Vector3D;
import maths.geometry.cube.Cube;

import java.util.Collection;

public class RandomDirectionCubeSides extends CubeSides {

    public RandomDirectionCubeSides(Cube cube, Collection<Square> squares) {
        super(cube, squares);
    }

    @Override
    protected Vector3D[] calculateCornerPoints(Square s) {
        return s.getCorners();
    }
}
