package rubik;

import maths.geometry.Cube;

import java.util.HashSet;
import java.util.Set;

public class TempCube extends RubiksCube {
    public TempCube(Set<Cube> selected, Set<Cube> notSelected) {
        this.selectedCubes = new HashSet<>(selected);
        this.cubes = new HashSet<>(notSelected);
        this.cubes.addAll(selected);
    }
}
