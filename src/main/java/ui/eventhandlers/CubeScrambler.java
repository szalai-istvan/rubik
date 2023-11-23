package ui.eventhandlers;

import ui.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.function.Consumer;

public class CubeScrambler extends KeyAdapter {
    private static final Map<Character, Consumer<Window>> OP = Map.of(
            'X', window -> window.scramble()
    );

    private final Window window;

    public CubeScrambler(Window window) {
        this.window = window;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        key = Character.toUpperCase(key);
        OP.getOrDefault(key, window -> {}).accept(window);
    }
}
