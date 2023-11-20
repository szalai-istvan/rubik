package rubik;

import maths.coordinate.vector.UnitVector3D;
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
    Map<Vector3D, Color> COLOR_DIRECTIONS = Map.of(
            X_POSITIVE, RED,
            Y_POSITIVE, GREEN,
            Z_POSITIVE, WHITE,
            X_NEGATIVE, new Color(242, 140, 40),
            Y_NEGATIVE, BLUE,
            Z_NEGATIVE, YELLOW
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
        for (int x : STEP) {
            for (int y : STEP) {
                for (int z : STEP) {
                    rubiksCube.cubes.add(new Cube(
                            vector(x, y, z).multiply(CUBE_LENGTH)
                    ));
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
        UnitVector3D unitVector = X_POSITIVE;
        Color color = COLOR_DIRECTIONS.get(unitVector);
        return buildSide(unitVector, sides -> sides.setXPositive(color));
    }

    RubiksCubeBuilder buildOrangeSide() {
        UnitVector3D unitVector = X_NEGATIVE;
        Color color = COLOR_DIRECTIONS.get(unitVector);
        return buildSide(unitVector, sides -> sides.setXNegative(color));
    }

    RubiksCubeBuilder buildWhiteSide() {
        UnitVector3D unitVector = Z_POSITIVE;
        Color color = COLOR_DIRECTIONS.get(unitVector);
        return buildSide(unitVector, sides -> sides.setZPositive(color));
    }

    RubiksCubeBuilder buildYellowSide() {
        UnitVector3D unitVector = Z_NEGATIVE;
        Color color = COLOR_DIRECTIONS.get(unitVector);
        return buildSide(unitVector, sides -> sides.setZNegative(color));
    }

    RubiksCubeBuilder buildGreenSide() {
        UnitVector3D unitVector = Y_POSITIVE;
        Color color = COLOR_DIRECTIONS.get(unitVector);
        return buildSide(unitVector, sides -> sides.setYPositive(color));
    }

    RubiksCubeBuilder buildBlueSide() {
        UnitVector3D unitVector = Y_NEGATIVE;
        Color color = COLOR_DIRECTIONS.get(unitVector);
        return buildSide(unitVector, sides -> sides.setYNegative(color));
    }

    private RubiksCubeBuilder buildSide(Vector3D v, Consumer<CubeSides> task) {
        Vector3D direction = v.multiply(CUBE_LENGTH);
        rubiksCube.cubes.stream()
                .filter(cube -> cube.getMidPoint().scalarMultiply(direction) > 0.001)
                .map(Cube::sides)
                .forEach(task);
        return this;
    }
}
