package ui.eventhandlers;

import ui.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.function.Consumer;

public class ShiftListener extends KeyAdapter {

    private static final Map<Character, Consumer<Window>> OP = Map.of(
            'A', Window::shiftSelectedFaceLeft,
            'S', Window::shiftSelectedFaceDown,
            'D', Window::shiftSelectedFaceRight,
            'W', Window::shiftSelectedFaceUp,
            'E', Window::rotateSelectedFaceRight,
            'Q', Window::rotateSelectedFaceLeft

    );

    private final Window window;

    public ShiftListener(Window w) {
        this.window = w;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        key = Character.toUpperCase(key);
        OP.getOrDefault(key, window -> {}).accept(window);
    }
}
