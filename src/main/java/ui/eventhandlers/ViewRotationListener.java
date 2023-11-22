package ui.eventhandlers;

import ui.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static utilities.Constants.SCREEN_HEIGHT;
import static utilities.Constants.SCREEN_WIDTH;

public class ViewRotationListener implements MouseMotionListener  {
    private int nullPointX = SCREEN_WIDTH / 2;
    private int nullPointY = SCREEN_HEIGHT / 2;

    private final Window window;

    public ViewRotationListener(Window window) {
        this.window = window;
        recalculateNullpoints();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() - nullPointX;
        int y = e.getY() - nullPointY;

        if (x > 10.00) {
            window.rotateRight();
            recalculateNullpoints();
        } else if (x < -10.00) {
            window.rotateLeft();
            recalculateNullpoints();
        } else if (y > 10.00) {
            window.rotateDown();
            recalculateNullpoints();
        } else if (y < -10.00) {
            window.rotateUp();
            recalculateNullpoints();
        }
    }

    private void recalculateNullpoints() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        nullPointX = location.x;
        nullPointY = location.y;
    }
}
