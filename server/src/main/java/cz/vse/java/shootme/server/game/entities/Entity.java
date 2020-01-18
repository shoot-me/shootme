package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {

    public final String id = UUID.randomUUID().toString();

    public String image;

    public Vector pos;

    public Vector speed;

    public Entity(String image, Vector pos, Vector speed) {
        this.image = image;
        this.pos = pos;
        this.speed = speed;
    }

    public ImageView render() {
        ImageView imageView = new ImageView(getImage());

        imageView.setX(pos.x);
        imageView.setY(pos.y);

        return imageView;
    }

    public Image getImage() {
        return new Image(image);
    }

}
