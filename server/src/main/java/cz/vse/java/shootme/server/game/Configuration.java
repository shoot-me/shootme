package cz.vse.java.shootme.server.game;

import java.io.Serializable;
import java.util.UUID;

public class Configuration implements Serializable {

    public final String id = UUID.randomUUID().toString();

    protected String name;

    protected int width;

    protected int height;

    public Configuration(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
