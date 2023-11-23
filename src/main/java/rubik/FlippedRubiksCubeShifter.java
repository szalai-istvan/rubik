package rubik;

public class FlippedRubiksCubeShifter extends RubiksCubeShifter {
    @Override
    public void shiftSelectedFaceLeft(RubiksCube rubiksCube) {
        rubiksCube.shiftRight();
    }

    @Override
    public void shiftSelectedFaceRight(RubiksCube rubiksCube) {
        rubiksCube.shiftLeft();
    }

    @Override
    public void shiftSelectedFaceUp(RubiksCube rubiksCube) {
        rubiksCube.shiftDown();
    }

    @Override
    public void shiftSelectedFaceDown(RubiksCube rubiksCube) {
        rubiksCube.shiftUp();
    }
}
