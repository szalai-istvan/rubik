package rubik;

public class RubiksCubeShifter {
    public void shiftSelectedFaceLeft(RubiksCube rubiksCube) {
        rubiksCube.shiftLeft();
    }

    public void shiftSelectedFaceRight(RubiksCube rubiksCube) {
        rubiksCube.shiftRight();
    }

    public void shiftSelectedFaceUp(RubiksCube rubiksCube) {
        rubiksCube.shiftUp();
    }

    public void shiftSelectedFaceDown(RubiksCube rubiksCube) {
        rubiksCube.shiftDown();
    }
}
