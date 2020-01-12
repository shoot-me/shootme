package cz.vse.java.shootme.common.game.util;

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

    @Override
    public Vector clone() {
        return new Vector(x, y);
    }

    public void add(Vector vector) {
        x += vector.x;
        y += vector.y;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
