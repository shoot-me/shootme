package cz.vse.java.shootme.client.game;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends Entity {

    private final String name;

    private final double speed;

    public Player(Map map, Image image, String name) {
        super(map, image);

        this.name = name;
        this.speed = 5;

        setX(10);
        setY(10);
    }

    @Override
    public void update() {
        if (map.isActiveKey(KeyCode.A)) {
            setX(getX() - speed);
        }

        if (map.isActiveKey(KeyCode.D)) {
            setX(getX() + speed);
        }

        if (map.isActiveKey(KeyCode.W)) {
            setY(getY() - speed);
        }

        if (map.isActiveKey(KeyCode.S)) {
            setY(getY() + speed);
        }
    }
}
