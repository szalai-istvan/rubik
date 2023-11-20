package ui.eventhandlers;

import ui.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static utilities.Constants.SCREEN_HEIGHT;
import static utilities.Constants.SCREEN_WIDTH;

public class RotationListener implements MouseMotionListener  {
    private static final int CENTER_X = SCREEN_WIDTH / 2;
    private static final int CENTER_Y = SCREEN_HEIGHT / 2;

    private final Window window;

    public RotationListener(Window window) {
        this.window = window;
        recenterMouse();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() - CENTER_X;
        int y = e.getY() - CENTER_Y;

        if (x > 10.00) {
            window.rotateRight();
            recenterMouse();
        } else if (x < -10.00) {
            window.rotateLeft();
            recenterMouse();
        } else if (y > 10.00) {
            window.rotateDown();
            recenterMouse();
        } else if (y < -10.00) {
            window.rotateUp();
            recenterMouse();
        }
    }

    private void recenterMouse() {
        try {
            new Robot().mouseMove(CENTER_X, CENTER_Y);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
