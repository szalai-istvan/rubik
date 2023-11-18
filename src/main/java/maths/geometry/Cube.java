package maths.geometry;

import maths.coordinate.Vector3D;

public class Cube {


    private final CubeSides sides;
    Vector3D midPoint;

    public Cube(Vector3D midPoint) {
        this.midPoint = midPoint;
        sides = new CubeSides(this);
    }

    public CubeSides sides() {
        return sides;
    }

    public Vector3D getMidPoint() {
        return midPoint;
    }

}
