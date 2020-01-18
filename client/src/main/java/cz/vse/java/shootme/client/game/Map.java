package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;
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

    private List<Entity> entities;

    private List<Entity> removedEntities;

    private List<Tile> tiles;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        this.activeKeys = new HashSet<>();
        this.entities = new ArrayList<>();
        this.removedEntities = new ArrayList<>();
        this.tiles = new ArrayList<>();

        SceneManager.get().getScene().setOnKeyPressed(keyEvent -> activeKeys.add(keyEvent.getCode()));
        SceneManager.get().getScene().setOnKeyReleased(keyEvent -> activeKeys.remove(keyEvent.getCode()));

        for (int i = 0; i < height * width; i++) {
            tiles.add(new Tile(new Image("img/tiles/floor_1.png")));
        }
    }

    public void update() {

    }

    public void render(Pane pane) {
        double offsetX = 0;
        double offsetY = 0;

        double sceneWidth = SceneManager.get().getScene().getWidth();
        double sceneHeight = SceneManager.get().getScene().getHeight();

        if (player != null) {
            if (player.pos.x + player.getImage().getWidth() / 2 > sceneWidth / 2) {
                offsetX = (player.pos.x + player.getImage().getWidth() / 2) - (sceneWidth / 2);
            }

            if (offsetX > width * TILE_SIZE - sceneWidth) {
                offsetX = width * TILE_SIZE - sceneWidth;
            }

            if (player.pos.y + player.getImage().getHeight() / 2 > sceneHeight / 2) {
                offsetY = (player.pos.y + player.getImage().getHeight() / 2) - (sceneHeight / 2);
            }

            if (offsetY > height * TILE_SIZE - sceneHeight) {
                offsetY = height * TILE_SIZE - sceneHeight;
            }

            if (sceneWidth > width * TILE_SIZE) {
                offsetX = offsetX / 2;
            }

            if (sceneHeight > height * TILE_SIZE) {
                offsetY = offsetY / 2;
            }
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

        if (player != null) {
            ImageView playerImageView = player.render();
            playerImageView.setX(playerImageView.getX() - offsetX);
            playerImageView.setY(playerImageView.getY() - offsetY);

            pane.getChildren().add(playerImageView);
        }


        for (Entity entity : entities) {
            ImageView entityImageView = entity.render();
            entityImageView.setX(entityImageView.getX() - offsetX);
            entityImageView.setY(entityImageView.getY() - offsetY);

            pane.getChildren().add(entityImageView);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void clearEntities() {
        entities.clear();
    }

    public void removeEntity(Entity entity) {
        removedEntities.add(entity);
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
