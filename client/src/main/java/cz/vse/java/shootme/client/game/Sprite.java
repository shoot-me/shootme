package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.util.Vector;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Sprite {

    public final String id;

    protected String type;

    protected String label;

    protected Label labelView;

    protected String image;

    protected ImageView imageView;

    protected Vector pos;

    protected Vector dir;

    protected Vector speed;

    protected Vector size;

    public Sprite(String type, String id, String label, String image, Vector pos, Vector dir, Vector speed, Vector size) {
        this.type = type;
        this.id = id;
        this.label = label;
        this.labelView = new Label(label);
        this.image = image;
        this.imageView = new ImageView(new Image(image));
        this.pos = pos;
        this.dir = dir;
        this.speed = speed;
        this.size = size;

        labelView.setTextFill(Color.WHITE);
        labelView.setFont(new Font("Arial", 16));
    }

    public Sprite(EntityUpdate entityUpdate) {
        this(entityUpdate.type, entityUpdate.id, entityUpdate.label, entityUpdate.image, entityUpdate.pos, entityUpdate.dir, entityUpdate.speed, entityUpdate.size);
    }

    public void updateFrom(EntityUpdate entityUpdate) {
        pos = entityUpdate.pos;
        dir = entityUpdate.dir;
        speed = entityUpdate.speed;
    }

    public void render(Pane pane) {
        imageView.setX(pos.x);
        imageView.setY(pos.y);

        labelView.setLayoutX(pos.x + size.x / 2 - labelView.widthProperty().get() / 2);
        labelView.setLayoutY(pos.y - 30);

        switch (type) {
            case "r":
                if (dir.y < 0) {
                    imageView.setRotate(0);
                } else if (dir.x > 0) {
                    imageView.setRotate(90);
                } else if (dir.y > 0) {
                    imageView.setRotate(180);
                } else if (dir.x < 0) {
                    imageView.setRotate(270);
                }
                break;

            case "s":
                if (dir.x < 0) {
                    imageView.setScaleX(-1);
                } else if (dir.x > 0) {
                    imageView.setScaleX(1);
                }
                break;
        }

        pane.getChildren().add(imageView);
        pane.getChildren().add(labelView);
    }

}
