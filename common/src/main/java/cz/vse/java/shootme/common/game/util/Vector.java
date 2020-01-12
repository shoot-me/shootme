package cz.vse.java.shootme.common.game.util;

import java.io.Serializable;

public class Vector implements Serializable {

    public final double x;

    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
