package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.util.Vector;

public class Player extends Entity {

    public final String name;

    public Player(String image, Vector pos, Vector speed, String name) {
        super(image, pos, speed);

        this.name = name;
    }

}
