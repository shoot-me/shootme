package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite {

    public final String id;

    protected String image;

    protected ImageView imageView;

    protected Vector pos;

    protected Vector dir;

    protected Vector speed;

    protected Vector size;

    public Sprite(String id, String image, Vector pos, Vector dir, Vector speed, Vector size) {
        this.id = id;
        this.image = image;
        this.imageView = new ImageView(new Image(image));
        this.pos = pos;
        this.dir = dir;
        this.speed = speed;
        this.size = size;
    }

    public Sprite(EntityUpdate entityUpdate) {
        this(entityUpdate.id, entityUpdate.image, entityUpdate.pos, entityUpdate.dir, entityUpdate.speed, entityUpdate.size);
    }

    public void updateFrom(EntityUpdate entityUpdate) {
        pos = entityUpdate.pos;
        dir = entityUpdate.dir;
        speed = entityUpdate.speed;
    }

    public void render(Pane pane) {
        imageView.setX(pos.x);
        imageView.setY(pos.y);

        pane.getChildren().add(imageView);
    }

}
