package utilities;

import maths.coordinate.vector.UnitVector3D;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Collections.unmodifiableList;
import static maths.coordinate.vector.UnitVector3D.unitVector;

public class UnitVectors {
    public static final UnitVector3D X_POSITIVE = unitVector(1, 0, 0);
    public static final UnitVector3D X_NEGATIVE = unitVector(-1, 0, 0);
    public static final UnitVector3D Y_POSITIVE = unitVector(0, 1, 0);
    public static final UnitVector3D Y_NEGATIVE = unitVector(0, -1, 0);
    public static final UnitVector3D Z_POSITIVE = unitVector(0, 0, 1);
    public static final UnitVector3D Z_NEGATIVE = unitVector(0, 0, -1);

    private static final List<UnitVector3D> UNIT_VECTORS = List.of(X_POSITIVE, X_NEGATIVE, Y_POSITIVE, Y_NEGATIVE, Z_POSITIVE, Z_NEGATIVE);
    private static final Random RANDOM = new Random();

    public static UnitVector3D randomUnitVector() {
        return UNIT_VECTORS.get(RANDOM.nextInt(UNIT_VECTORS.size()));
    }
}
