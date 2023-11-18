package rubik;

import maths.coordinate.ViewPlane;
import maths.geometry.Cube;
import ui.renderer.RenderingTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RubiksCube {
    Cube[] cubes = new Cube[27];

    public RubiksCube() {
        RubiksCubeBuilder.of(this)
                .buildRedSide()
                .buildWhiteSide()
                .buildGreenSide()
                .buildOrangeSide()
                .buildYellowSide()
                .buildBlueSide();
    }

    public List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
        return Arrays.stream(cubes)
                .flatMap(cube -> cube.getRenderingTasks(viewPlane).stream())
                .collect(Collectors.toList());
    }
}
