package maths.geometry.cuberotator;

import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.geometry.Cube;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static maths.coordinate.vector.Vector3D.vector;
import static utilities.UnitVectors.*;

public abstract class CubeRotator {
    protected final Collection<Cube> cubes;
    protected final UnitVector3D axis;
    protected double angle;

    CubeRotator(Collection<Cube> cubes, UnitVector3D axis, double angle) {
        this.cubes = cubes;
        this.axis = axis;
        this.angle = angle;
    }

    public Set<Cube> rotateCubes() {
        return cubes.stream()
                .map(this::rotateCube)
                .collect(Collectors.toSet());
    }

    protected abstract Cube rotateCube(Cube cube);

    protected Vector3D filterVector(Vector3D v) {
        if (axis.isParallelTo(X_POSITIVE)) {
            return vector(0, v.y(), v.z());
        } else if (axis.isParallelTo(Y_NEGATIVE)) {
            return vector(v.x(), 0, v.z());
        } else if (axis.isParallelTo(Z_POSITIVE)) {
            return vector(v.x(), v.y(), 0);
        }
        return v;
    }
}
