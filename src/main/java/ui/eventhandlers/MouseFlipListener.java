package ui.eventhandlers;

import ui.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseFlipListener extends MouseAdapter {
    private static final int MIDDLE_BUTTON = 2;

    private final Window window;

    public MouseFlipListener(Window w) {
        window = w;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MIDDLE_BUTTON) {
            window.flip();
        }
    }
}
