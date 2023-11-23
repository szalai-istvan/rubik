package maths.geometry.cube.sides;

import maths.coordinate.Coordinates;
import maths.coordinate.vector.Vector3D;
import maths.coordinate.plane.ViewPlane;
import ui.renderer.LineRenderingTask;
import ui.renderer.RenderingTask;
import ui.renderer.SurfaceRenderingTask;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.awt.Color.BLACK;

public class Square {
    private Vector3D[] corners;
    private Vector3D normalDirection;
    private final Color color;

    public Square(Color c) {
        this.color = c;
    }

    public Vector3D getNormalDirection() {
        return normalDirection;
    }

    public void setNormalDirection(Vector3D v) {
        normalDirection = v;
    }

    public Vector3D[] getCorners() {
        return corners;
    }

    public void setCorners(Vector3D[] corners) {
        this.corners = corners;
    }

    public Color getColor() {
        return color;
    }

    public List<RenderingTask> getRenderingTasks(ViewPlane viewPlane) {
        Coordinates[] projectedCorners = calculateProjectedCorners(viewPlane);
        List<RenderingTask> renderingTasks = getLineRenderingTasks(projectedCorners);
        renderingTasks.add(getSurfaceRenderingTask(projectedCorners));
        return renderingTasks;
    }

    public Polygon toPolygon(Coordinates[] projectedCorners) {
        return new Polygon(
                xPoints(projectedCorners),
                yPoints(projectedCorners),
                projectedCorners.length);
    }

    public double cosAlpha(ViewPlane viewPlane) {
        return normalDirection.cosAlpha(viewPlane.getNormalVector());
    }

    boolean isLookingAt(ViewPlane viewPlane) {
        return normalDirection.scalarMultiply(viewPlane.getNormalVector()) > 0.00;
    }

    private Coordinates[] calculateProjectedCorners(ViewPlane viewPlane) {
        return Arrays.stream(corners)
                .map(vector -> viewPlane.projectPointToPlane(vector))
                .collect(Collectors.toList())
                .toArray(new Coordinates[corners.length]);
    }

    private List<RenderingTask> getLineRenderingTasks(Coordinates[] projectedCorners) {
        List<RenderingTask> tasks = new ArrayList<>();
        for (int left = 0; left < projectedCorners.length; left++) {
            int right = (left + 1) % projectedCorners.length;

            Line2D.Double line = new Line2D.Double(
                    projectedCorners[left].x,
                    projectedCorners[left].y,
                    projectedCorners[right].x,
                    projectedCorners[right].y
            );

            tasks.add(new LineRenderingTask(line));
        }
        return tasks;
    }

    private RenderingTask getSurfaceRenderingTask(Coordinates[] projectedCorners) {
        return new SurfaceRenderingTask(color, toPolygon(projectedCorners), this);
    }
    private int[] xPoints(Coordinates[] projectedCorners) {
        return coordinates(projectedCorners, corner -> (int) corner.x);
    }

    private int[] yPoints(Coordinates[] projectedCorners) {
        return coordinates(projectedCorners, corner -> (int) corner.y);
    }

    private int[] coordinates(Coordinates[] projectedCorners, Function<Coordinates, Integer> mapper) {
        int[] coords = new int[corners.length];
        int index = 0;
        for (Coordinates corner : projectedCorners) {
            coords[index++] = mapper.apply(corner);
        }
        return coords;
    }
}
