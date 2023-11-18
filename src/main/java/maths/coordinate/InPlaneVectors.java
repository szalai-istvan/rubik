package maths.coordinate;

public class InPlaneVectors {
    private final UnitVector3D horizontalUnit;
    private final UnitVector3D verticalUnit;

    public InPlaneVectors(UnitVector3D horizontalUnit, UnitVector3D verticalUnit) {
        this.horizontalUnit = horizontalUnit;
        this.verticalUnit = verticalUnit;
    }

    public UnitVector3D getHorizontalUnit() {
        return horizontalUnit;
    }

    public UnitVector3D getVerticalUnit() {
        return verticalUnit;
    }
}
