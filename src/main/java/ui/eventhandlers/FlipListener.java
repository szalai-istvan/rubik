package ui.eventhandlers;

import ui.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlipListener extends KeyAdapter {
    private final Window window;

    public FlipListener(Window window) {
        this.window = window;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            window.flip();
        }
    }
}
