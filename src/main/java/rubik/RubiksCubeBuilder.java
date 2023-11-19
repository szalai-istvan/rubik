package rubik;

import maths.coordinate.vector.Vector3D;
import maths.geometry.Cube;
import maths.geometry.CubeSides;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import static java.awt.Color.*;
import static maths.coordinate.vector.Vector3D.vector;
import static utilities.Constants.CUBE_LENGTH;
import static utilities.Constants.STEP;
import static utilities.UnitVectors.*;

public class RubiksCubeBuilder {
    Map<Color, Vector3D> COLOR_DIRECTIONS = Map.of(
            RED, X_POSITIVE,
            GREEN, Y_POSITIVE,
            WHITE, Z_POSITIVE,
            ORANGE, X_NEGATIVE,
            BLUE, Y_NEGATIVE,
            YELLOW, Z_NEGATIVE
    );

    private final RubiksCube rubiksCube;

    private RubiksCubeBuilder(RubiksCube rubiksCube) {
        this.rubiksCube = rubiksCube;
        init();
    }

    public static RubiksCubeBuilder of(RubiksCube rubiksCube) {
        return new RubiksCubeBuilder(rubiksCube);
    }

    RubiksCubeBuilder init() {
        int i = 0;
        for (int x : STEP) {
            for (int y : STEP) {
                for (int z : STEP) {
                    rubiksCube.cubes[i++] = new Cube(
                            vector(x, y, z).multiply(CUBE_LENGTH)
                    );
                }
            }
        }
        return this;
    }

    RubiksCube build() {
        return this
                .buildRedSide()
                .buildWhiteSide()
                .buildGreenSide()
                .buildOrangeSide()
                .buildYellowSide()
                .buildBlueSide()
                .rubiksCube;
    }

    RubiksCubeBuilder buildRedSide() {
        return buildSide(RED, sides -> sides.setXPositive(RED));
    }

    RubiksCubeBuilder buildOrangeSide() {
        return buildSide(ORANGE, sides -> sides.setXNegative(ORANGE));
    }

    RubiksCubeBuilder buildWhiteSide() {
        return buildSide(WHITE, sides -> sides.setZPositive(WHITE));
    }

    RubiksCubeBuilder buildYellowSide() {
        return buildSide(YELLOW, sides -> sides.setZNegative(YELLOW));
    }

    RubiksCubeBuilder buildGreenSide() {
        return buildSide(GREEN, sides -> sides.setYPositive(GREEN));
    }

    RubiksCubeBuilder buildBlueSide() {
        return buildSide(BLUE, sides -> sides.setYNegative(BLUE));
    }

    private RubiksCubeBuilder buildSide(Color color, Consumer<CubeSides> task) {
        Vector3D direction = COLOR_DIRECTIONS.get(color).multiply(CUBE_LENGTH);
        Arrays.stream(rubiksCube.cubes)
                .filter(cube -> cube.getMidPoint().scalarMultiply(direction) > 0.001)
                .map(Cube::sides)
                .forEach(task);
        return this;
    }
}
