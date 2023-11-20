package ui.eventhandlers;

import ui.Window;
import ui.WindowSetupTool;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.function.Consumer;

public class ShiftListener extends KeyAdapter {

    private static final Map<Character, Consumer<Window>> OP = Map.of(
            'A', window -> window.shiftLeft(),
            'S', window -> window.shiftDown(),
            'D', window -> window.shiftRight(),
            'W', window -> window.shiftUp(),
            'E', window -> window.rotateSelectedFaceRightWithAnimation(),
            'Q', window -> window.rotateSelectedFaceLeftWithAnimation()

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
