package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {

    public final String id = UUID.randomUUID().toString();

    public String image;

    public Vector pos;

    public Vector dir;

    public Vector speed;

    public Entity(String image, Vector pos, Vector dir, Vector speed) {
        this.image = image;
        this.pos = pos;
        this.dir = dir;
        this.speed = speed;
    }

    public void update(Game game) {
        dir.clampMin(new Vector(-1, -1));
        dir.clampMax(new Vector(1, 1));

        pos = pos.add(dir.times(speed));
    }

    public ImageView render() {
        ImageView imageView = new ImageView(getImage());

        imageView.setX(pos.x);
        imageView.setY(pos.y);

        return imageView;
    }

    public boolean intersects(Entity another) {
        if (pos.x >= another.pos.x && pos.x <= another.pos.x + another.getSize().x) {
            if (pos.y >= another.pos.y && pos.y <= another.pos.y + another.getSize().y) {
                return true;
            }
        }

        return false;
    }

    public void collide(Game game, Entity another) {

    }

    public Image getImage() {
        return new Image(image);
    }

    public Vector getSize() {
        return new Vector(64, 64);
    }

    public Vector getCenter() {
        return new Vector(pos.x + getSize().x / 2, pos.y + getSize().y / 2);
    }
}
