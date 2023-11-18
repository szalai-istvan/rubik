import maths.coordinate.UnitVector3D;
import maths.coordinate.ViewPlane;
import org.junit.Test;

import static maths.coordinate.UnitVector3D.unitVector;
import static maths.coordinate.Vector3D.vector;

public class ProjectionTest {
    @Test
    public void project() {
        ViewPlane viewPlane = new ViewPlane(
                unitVector(1, 1, 1),
                100);

        // center: 432, 768
        viewPlane.projectPointToPlane(vector(1, 1, 1));
    }
}