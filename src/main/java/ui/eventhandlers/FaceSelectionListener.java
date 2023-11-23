package ui.eventhandlers;

import maths.coordinate.Coordinates;
import maths.coordinate.vector.UnitVector3D;
import maths.coordinate.vector.Vector3D;
import maths.geometry.CornerCalculator;
import rubik.RubiksCube;
import ui.Window;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static utilities.Constants.CUBE_LENGTH;
import static utilities.Constants.HALF_LENGTH;
import static utilities.UnitVectors.UNIT_VECTORS;

public class FaceSelectionListener extends MouseAdapter {
    private static final int LEFT_CLICK = 1;
    private static final int FIGURE_SIZE = CUBE_LENGTH + HALF_LENGTH;
    private final Window window;

    public FaceSelectionListener(Window window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != LEFT_CLICK) {
            return;
        }
        Point locationOnScreen = e.getLocationOnScreen();
        Vector3D viewDirection = window.getView().getNormalVector();
        UNIT_VECTORS.stream()
                .filter(vector -> vector.scalarMultiply(viewDirection) > 0.00)
                .filter(vector -> createPolygon(vector).contains(locationOnScreen))
                .findFirst()
                .ifPresent(window::selectFace);
    }

    private Polygon createPolygon(UnitVector3D vector) {
        Vector3D[] corners = new CornerCalculator<>(identity())
                .figuresize(FIGURE_SIZE)
                .calculateCornerPoints(vector);
        List<Coordinates> projected = Arrays.stream(corners)
                .map(c -> window.getView().projectPointToPlane(c))
                .collect(Collectors.toList());
        int[] x = getX(projected);
        int[] y = getY(projected);
        int n = x.length;
        return new Polygon(x, y, n);
    }

    private int[] getX(List<Coordinates> projected) {
        return mapCoordinates(projected, c -> (int) c.x);

    }

    private int[] getY(List<Coordinates> projected) {
        return mapCoordinates(projected, c -> (int) c.y);
    }

    private int[] mapCoordinates(List<Coordinates> coordinates, Function<Coordinates, Integer> mapper) {
        int[] x = new int[coordinates.size()];
        int index = 0;
        for (Coordinates c : coordinates) {
            x[index++] = (int) mapper.apply(c);
        }
        return x;
    }
}
