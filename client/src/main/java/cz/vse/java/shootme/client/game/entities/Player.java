package cz.vse.java.shootme.client.game.entities;

import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.game.entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player extends Entity {

    private final String name;

    private final double speed;

    private int daggerCooldown;

    private boolean facingRight;

    public Player(Map map, Image image, String name) {
        super(map, image);

        this.name = name;
        this.speed = 5;
        this.daggerCooldown = 0;
        this.facingRight = true;

        setX(10);
        setY(10);
    }

    @Override
    public void update() {
        super.update();

        daggerCooldown++;

        if (map.isActiveKey(KeyCode.A)) {
            facingRight = false;
            setX(getX() - speed);
        }

        if (map.isActiveKey(KeyCode.D)) {
            facingRight = true;
            setX(getX() + speed);
        }

        if (map.isActiveKey(KeyCode.W)) {
            setY(getY() - speed);
        }

        if (map.isActiveKey(KeyCode.S)) {
            setY(getY() + speed);
        }

        if (map.isActiveKey(KeyCode.RIGHT)) {
            throwDagger(1, 0);
        }

        if (map.isActiveKey(KeyCode.LEFT)) {
            throwDagger(-1, 0);
        }

        if (map.isActiveKey(KeyCode.UP)) {
            throwDagger(0, -1);
        }

        if (map.isActiveKey(KeyCode.DOWN)) {
            throwDagger(0, 1);
        }
    }

    @Override
    public ImageView render() {
        ImageView imageView = super.render();

        if (!facingRight) {
            imageView.setScaleX(-1);
        }

        return imageView;
    }

    private void throwDagger(double x, double y) {
        if (daggerCooldown < 20) return;

        daggerCooldown = 0;

        map.addEntity(new Dagger(map, getCenterX(), getCenterY(), 10 * x, 10 * y));
    }
}
