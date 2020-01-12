package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.client.services.SceneManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {

    public static final double TILE_SIZE = 64;

    private final Set<KeyCode> activeKeys;

    private final int width;

    private final int height;

    private Player player;

    private List<Tile> tiles;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        this.activeKeys = new HashSet<>();
        this.tiles = new ArrayList<>();

        SceneManager.get().getScene().setOnKeyPressed(keyEvent -> activeKeys.add(keyEvent.getCode()));
        SceneManager.get().getScene().setOnKeyReleased(keyEvent -> activeKeys.remove(keyEvent.getCode()));

        for (int i = 0; i < height * width; i++) {
            tiles.add(new Tile(new Image("img/tiles/floor_1.png")));
        }
    }

    public void update() {
        player.update();
    }

    public void render(Pane pane) {
        double offsetX = 0;
        double offsetY = 0;

        double sceneWidth = SceneManager.get().getScene().getWidth();
        double sceneHeight = SceneManager.get().getScene().getHeight();

        if (player.getX() + player.getImage().getWidth() / 2 > sceneWidth / 2) {
            offsetX = (player.getX() + player.getImage().getWidth() / 2) - (sceneWidth / 2);
        }

        if (offsetX > width * TILE_SIZE - sceneWidth) {
            offsetX = width * TILE_SIZE - sceneWidth;
        }

        if (player.getY() + player.getImage().getHeight() / 2 > sceneHeight / 2) {
            offsetY = (player.getY() + player.getImage().getHeight() / 2) - (sceneHeight / 2);
        }

        if (offsetY > height * TILE_SIZE - sceneHeight) {
            offsetY = height * TILE_SIZE - sceneHeight;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Image image = getTile(x, y).getImage();
                ImageView imageView = new ImageView(image);

                imageView.setX((x * image.getHeight()) - offsetX);
                imageView.setY((y * image.getWidth()) - offsetY);

                pane.getChildren().add(imageView);
            }
        }

        ImageView playerImageView = player.render();
        playerImageView.setX(playerImageView.getX() - offsetX);
        playerImageView.setY(playerImageView.getY() - offsetY);

        pane.getChildren().add(playerImageView);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Tile getTile(int x, int y) {
        return tiles.get(y * width + x);
    }

    public boolean isActiveKey(KeyCode keyCode) {
        return activeKeys.contains(keyCode);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
