package utilities;

import static java.lang.Math.PI;

public class Angles {
    public static double toRadians(double degrees) {
        return PI * degrees / 180;
    }

    public static double toDegrees(double radians) {
        return radians * 180 / PI;
    }
}
