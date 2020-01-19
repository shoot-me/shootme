package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.common.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tile {

    public static final int SIZE = 64;

    private final ImageView imageView;

    private final Vector coords;

    public Tile(String image, Vector coords) {
        this.imageView = new ImageView(new Image(image));
        this.coords = coords;

        imageView.setX(coords.x * SIZE);
        imageView.setY(coords.y * SIZE);
    }

    public void render(Pane pane) {
        pane.getChildren().add(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }

}
