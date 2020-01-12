package cz.vse.java.shootme.common.game.entities;

import cz.vse.java.shootme.common.game.util.Vector;
import javafx.scene.image.Image;

public class Player extends Entity {

    public final String name;

    public Player(String image, Vector pos, Vector speed, String name) {
        super(image, pos, speed);

        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", pos=" + pos +
                ", speed=" + speed +
                '}';
    }
}
