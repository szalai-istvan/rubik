package rubik;

import maths.coordinate.plane.ViewPlane;
import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.geometry.cube.Cube;
import maths.geometry.cuberotator.AnimatedCubeRotator;
import maths.geometry.cuberotator.NinetyDegreesCubeRotator;
import ui.renderer.RenderingTask;
import ui.renderer.SelectedCubeRendererTask;

import java.util.*;
import java.util.stream.Collectors;

import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.Z_NEGATIVE;
import static utilities.UnitVectors.Z_POSITIVE;

public class RubiksCube {
    protected Set<Cube> cubes = new HashSet<>();

    private UnitVector3D selectedDirection;
    private Vector3D backupSelectedVector;
    protected Set<Cube> selectedCubes = new HashSet<>();
    private Set<Cube> notSelectedCubes = new HashSet<>();

    public RubiksCube() {
        RubiksCubeBuilder.of(this).build();

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
        backupSelectedVector = selectedDirection;
        selectedDirection = v.toUnitVector();
        selectedCubes = cubes.stream()
                .filter(cube -> cube.getMidPoint().scalarMultiply(v) > 1E-10)
                .collect(Collectors.toSet());
        notSelectedCubes = cubes.stream()
                .filter(cube -> !selectedCubes.contains(cube))
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
            if (selectedDirection.scalarMultiply(Z_POSITIVE) > 0.00) {
                select(backupSelectedVector.multiply(-1.00));
            } else {
                select(backupSelectedVector);
            }
        } else {
            select(Z_POSITIVE);
        }
    }

    public void shiftDown() {
        if (selectedDirection.isParallelTo(Z_POSITIVE)) {
            if (selectedDirection.scalarMultiply(Z_NEGATIVE) > 0.00) {
                select(backupSelectedVector.multiply(-1.00));
            } else {
                select(backupSelectedVector);
            }
        } else {
            select(Z_NEGATIVE);
        }
    }

    public void rotateSelectedLeft() {
        rotateSelected(-1.00);
    }

    public void rotateSelectedRight() {
        rotateSelected(1.00);
    }

    public List<TempCube> animateSelectedLeft() {
        return createAnimationSteps(15);
    }

    public List<TempCube> animateSelectedRight() {
        return createAnimationSteps(-15);
    }

    private List<TempCube> createAnimationSteps(int stepSize) {
        List<TempCube> animationSteps = new ArrayList<>();
        RubiksCube previous = this;
        int absoluteStep = Math.abs(stepSize);
        for (int angle = 0; angle < 90; angle += absoluteStep) {
            Set<Cube> rotatedCubes = new AnimatedCubeRotator(previous.selectedCubes, selectedDirection, stepSize)
                    .rotateCubes();
            TempCube tempCube = new TempCube(rotatedCubes, notSelectedCubes);
            tempCube.select(selectedDirection);
            previous = tempCube;
            animationSteps.add(tempCube);
        }
        return animationSteps;
    }

    private void rotateSelected(double angle) {
        Set<Cube> rotatedCubes = new NinetyDegreesCubeRotator(selectedCubes, selectedDirection, angle)
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
