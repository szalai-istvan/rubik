package rubik;

import maths.geometry.Cube;

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
}
