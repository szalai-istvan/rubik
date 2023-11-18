package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Constants {
    public static final int[] POSITIVE = new int[]{1};
    public static final int[] NEGATIVE = new int[]{-1};
    public static final int[] POSITIVE_NEGATIVE = new int[]{-1, 1};
    public static final int[] STEP = new int[]{-1, 0, 1};

    public static final int CUBE_LENGTH = 100;
    public static final int HALF_LENGTH = CUBE_LENGTH / 2;

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
}
