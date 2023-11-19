package maths.geometry;

import maths.coordinate.vector.Vector3D;
import maths.coordinate.plane.ViewPlane;
import ui.renderer.RenderingTask;

import java.util.List;

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

    public List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
        return sides.getRenderingTasks(viewPlane);
    }


}
