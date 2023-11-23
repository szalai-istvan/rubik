package ui.eventhandlers;

import ui.Window;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ScrollRotateListener implements MouseWheelListener {
    private final Window window;

    public ScrollRotateListener(Window window) {
        this.window = window;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getUnitsToScroll();
        if (scrollAmount > 0) {
            window.rotateSelectedFaceRight();
        } else {
            window.rotateSelectedFaceLeft();
        }
    }
}
