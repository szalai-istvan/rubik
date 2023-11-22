package ui.eventhandlers;

import ui.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ViewRotationToggle extends MouseAdapter {
    private static int RIGHT_CLICK = 3;
    private final Window window;
    private ViewRotationListener viewRotationListener;
    private int clickCount = 0;

    public ViewRotationToggle(Window window) {
        this.window = window;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == RIGHT_CLICK) {
            if (clickCount++ % 2 == 0) {
                window.addMouseMotionListener(viewRotationListener());
            } else {
                window.removeMouseMotionListener(viewRotationListener);
            }
        }
    }

    private ViewRotationListener viewRotationListener() {
        viewRotationListener = new ViewRotationListener(window);
        return viewRotationListener;
    }
}
