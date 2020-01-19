package cz.vse.java.shootme.common.util;

import java.io.Serializable;

public class Vector implements Serializable {

    public final double x;

    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    public Vector times(Vector vector) {
        return new Vector(x * vector.x, y * vector.y);
    }

    public Vector clampMin(Vector vector) {
        double clampedX = x;
        double clampledY = y;

        if (x < vector.x) {
            clampedX = vector.x;
        }

        if (y < vector.y) {
            clampledY = vector.y;
        }

        return new Vector(clampedX, clampledY);
    }

    public Vector clampMax(Vector vector) {
        double clampedX = x;
        double clampledY = y;

        if (x > vector.x) {
            clampedX = vector.x;
        }

        if (y > vector.y) {
            clampledY = vector.y;
        }

        return new Vector(clampedX, clampledY);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
