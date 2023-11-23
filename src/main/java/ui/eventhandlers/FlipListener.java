package ui.eventhandlers;

import ui.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlipListener extends KeyAdapter {
    private static final int SPACE_BAR = 32;
    private final Window window;

    public FlipListener(Window window) {
        this.window = window;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == SPACE_BAR) {
            window.flip();
        }
    }
}
