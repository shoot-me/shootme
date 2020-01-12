package cz.vse.java.shootme.client.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.UUID;

public abstract class Entity {

    protected final String id = UUID.randomUUID().toString();

    protected final Map map;

    protected final Image image;

    private double x;

    private double y;

    public Entity(Map map, Image image) {
        this.map = map;
        this.image = image;
    }

    public void update() {

    }

    public ImageView render() {
        ImageView imageView = new ImageView(image);

        imageView.setX(x);
        imageView.setY(y);

        return imageView;
    }

    public String getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if (x < 0) {
            this.x = 0;
        } else if (x > map.getWidth() * Map.TILE_SIZE - image.getWidth()) {
            this.x = map.getWidth() * Map.TILE_SIZE - image.getWidth();
        } else {
            this.x = x;
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        if (y < 0) {
            this.y = 0;
        } else if (y > map.getHeight() * Map.TILE_SIZE - image.getHeight()) {
            this.y = map.getHeight() * Map.TILE_SIZE - image.getHeight();
        } else {
            this.y = y;
        }
    }
}
