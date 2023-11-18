package maths.geometry;

import maths.coordinate.Vector3D;

import java.awt.*;

import static java.awt.Color.BLACK;

public class Square {
    private Vector3D[] corners;
    private Vector3D normalDirection;
    private Color color = BLACK;

    public Square(Color c) {
        this.color = c;
    }

    public void setNormalDirection(Vector3D v) {
        normalDirection = v;
    }

    public void setCorners(Vector3D[] corners) {
        this.corners = corners;
    }
}
