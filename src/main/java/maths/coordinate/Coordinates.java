package maths.coordinate;

public class Coordinates {
    public final double x;
    public final double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "("+x+", "+y+")";
    }
}
