package maths.geometry;

import maths.coordinate.vector.Vector3D;
import maths.coordinate.plane.ViewPlane;
import ui.renderer.RenderingTask;

import java.util.List;

public class Cube {
    private CubeSides sides;
    Vector3D midPoint;

    public Cube(Vector3D midPoint) {
        this.midPoint = midPoint;
        sides = new CubeSides(this);
    }

    public Cube(Vector3D midPoint, CubeSides sides) {
        this.midPoint = midPoint;
        this.sides = sides;
    }

    public void setSides(CubeSides cubeSides) {
        this.sides = cubeSides;
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

    public boolean equals(Object o) {
        if (o == null || !(o instanceof Cube)) {
            return false;
        } else if (o == this) {
            return true;
        }

        Cube c = (Cube) o;

        return c.midPoint.equals(midPoint);
    }

    public int hashCode() {
        return midPoint.hashCode();
    }

}
