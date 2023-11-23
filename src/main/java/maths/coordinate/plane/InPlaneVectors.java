package maths.coordinate.plane;

import maths.coordinate.vector.UnitVector3D;

import static utilities.UnitVectors.Z_POSITIVE;

public class InPlaneVectors {
    private final UnitVector3D horizontalUnit;
    private final UnitVector3D verticalUnit;

    public InPlaneVectors(UnitVector3D horizontalUnit, UnitVector3D verticalUnit) {
        this.horizontalUnit = horizontalUnit;
        this.verticalUnit = verticalUnit;
    }

    public static InPlaneVectors of(ViewPlane viewPlane) {
        UnitVector3D horizontal = Z_POSITIVE.multiply(viewPlane.getNormalVector()).toUnitVector();
        UnitVector3D vertical = horizontal.multiply(viewPlane.getNormalVector()).toUnitVector();
        return new InPlaneVectors(horizontal, vertical);
    }

    public UnitVector3D getHorizontalUnit() {
        return horizontalUnit;
    }

    public UnitVector3D getVerticalUnit() {
        return verticalUnit;
    }
}
