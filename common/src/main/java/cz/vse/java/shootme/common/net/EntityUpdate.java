package cz.vse.java.shootme.common.net;

import cz.vse.java.shootme.common.util.Vector;

import java.io.Serializable;

public class EntityUpdate implements Serializable {

    public final String id;

    public final String type;

    public final Vector pos;

    public final Vector dir;

    public final Vector speed;

    public final Vector size;

    public final String image;

    public EntityUpdate(String type, String id, Vector pos, Vector dir, Vector speed, Vector size, String image) {
        this.type = type;
        this.id = id;
        this.pos = pos;
        this.dir = dir;
        this.speed = speed;
        this.size = size;
        this.image = image;
    }
}
