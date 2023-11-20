package ui;

import ui.eventhandlers.CubeScrambler;
import ui.eventhandlers.FlipListener;
import ui.eventhandlers.RotationListener;
import ui.eventhandlers.ShiftListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static utilities.Constants.SCREEN_SIZE;

public class WindowSetupTool {
    private final Window window;

    private WindowSetupTool(Window window) {
        this.window = window;
    }

    public static WindowSetupTool of(Window window) {
        return new WindowSetupTool(window);
    }

    public void setup() {
        Arrays.stream(WindowSetupTool.class.getDeclaredMethods())
                .filter(method -> isSetupMethod(method))
                .forEach(method -> invoke(method));
    }

    @SetupMethod
    public void initWindow() {
        window.setSize(SCREEN_SIZE);
        window.setUndecorated(true);
        window.setVisible(true);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @SetupMethod
    public void addCanvas() {
        window.setLayout(null);
        window.add(window.canvas);
        window.canvas.setSize(SCREEN_SIZE);
    }

    @SetupMethod
    public void addRotation() {
        window.addMouseMotionListener(new RotationListener(window));
    }

    @SetupMethod
    public void addShiftListener() {
        window.addKeyListener(new ShiftListener(window));
    }

    @SetupMethod
    public void addCubeScrambler() {
        window.addKeyListener(new CubeScrambler(window));
    }

    @SetupMethod
    public void addFlipListener() {
        window.addKeyListener(new FlipListener(window));
    }

    private boolean isSetupMethod(Method method) {
        return method.isAnnotationPresent(SetupMethod.class);
    }

    private void invoke(Method method) {
        try {
            method.invoke(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}

@Retention(RUNTIME)
@Target(METHOD)
@interface SetupMethod {

}