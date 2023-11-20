package rubik;

import maths.coordinate.plane.ViewPlane;
import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.geometry.Cube;
import maths.geometry.cuberotator.CubeStepper;
import ui.renderer.RenderingTask;
import ui.renderer.SelectedCubeRendererTask;

import java.util.*;
import java.util.stream.Collectors;

import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.Z_NEGATIVE;
import static utilities.UnitVectors.Z_POSITIVE;

public class RubiksCube {
    Set<Cube> cubes = new HashSet<>();
    private UnitVector3D selectedDirection;

    private Set<Cube> selectedCubes = new HashSet<>();
    private Vector3D verticalShiftBackupVector;

    public RubiksCube() {
        RubiksCubeBuilder.of(this)
                .buildRedSide()
                .buildWhiteSide()
                .buildGreenSide()
                .buildOrangeSide()
                .buildYellowSide()
                .buildBlueSide();

        select(vector(1, 0, 0));
    }

    public List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
        return cubes.stream()
                .sorted(Comparator.comparing(cube -> -1 * viewPlane.distanceFrom(cube)))
                .flatMap(cube -> {
                    if (selectedCubes.contains(cube)) {
                        return SelectedCubeRendererTask.of(cube, viewPlane).stream();
                    }
                    return cube.getRenderingTasks(viewPlane).stream();
                })
                .collect(Collectors.toList());

    }

    public void select(Vector3D v) {
        selectedDirection = v.toUnitVector();
        selectedCubes = cubes.stream()
                .filter(cube -> cube.getMidPoint().scalarMultiply(v) > 1E-10)
                .collect(Collectors.toSet());
    }

    public void shiftRight() {
        if (selectedDirection.isParallelTo(Z_POSITIVE)) {
            return;
        }
        select(Z_POSITIVE.multiply(selectedDirection));
    }

    public void shiftLeft() {
        if (selectedDirection.isParallelTo(Z_POSITIVE)) {
            return;
        }
        select(selectedDirection.multiply(Z_POSITIVE));
    }

    public void shiftUp() {
        if (selectedDirection.isParallelTo(Z_POSITIVE)) {
            select(verticalShiftBackupVector);
        } else {
            verticalShiftBackupVector = selectedDirection;
            select(Z_POSITIVE);
        }
    }

    public void shiftDown() {
        if (selectedDirection.isParallelTo(Z_POSITIVE)) {
            select(verticalShiftBackupVector);
        } else {
            verticalShiftBackupVector = selectedDirection;
            select(Z_NEGATIVE);
        }
    }

    public void rotateSelectedLeft() {
        rotateSelected(-1.00);
    }

    public void rotateSelectedRight() {
        rotateSelected(1.00);
    }

    private void rotateSelected(double angle) {
        Set<Cube> rotatedCubes = new CubeStepper(selectedCubes, selectedDirection, angle)
                .rotateCubes();
        replaceCubesWith(rotatedCubes);
        selectedCubes = rotatedCubes;
    }

    private void replaceCubesWith(Set<Cube> rotatedCubes) {
        cubes = cubes.stream()
                .filter(cube -> !selectedCubes.contains(cube))
                .collect(Collectors.toSet());
        cubes.addAll(rotatedCubes);
    }
}
