import maths.coordinate.plane.ViewPlane;
import org.junit.Test;

import static maths.coordinate.vector.UnitVector3D.unitVector;
import static maths.coordinate.vector.Vector3D.vector;

public class ProjectionTest {
    @Test
    public void project() {
        ViewPlane viewPlane = new ViewPlane(
                unitVector(1, 1, 0),
                500);

        // center: 432, 768
        viewPlane.projectPointToPlane(vector(0, 0, 0));
    }
}