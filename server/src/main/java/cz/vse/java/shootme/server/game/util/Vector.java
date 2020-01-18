package cz.vse.java.shootme.server.game.util;

import java.io.Serializable;
import java.util.UUID;

public class Vector implements Serializable {

    public final String id = UUID.randomUUID().toString();

    public double x;

    public double y;

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

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
