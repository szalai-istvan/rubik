package maths.geometry;

import maths.coordinate.vector.Vector3D;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RandomDirectionCubeSides extends CubeSides {

    public RandomDirectionCubeSides(Cube cube, Collection<Square> squares) {
        super(cube, squares);
    }

    @Override
    protected Vector3D[] calculateCornerPoints(Square s) {
        return s.getCorners();
    }
}
