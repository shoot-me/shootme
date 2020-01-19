package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.Configuration;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Configuration configuration;

    private List<Tile> tiles;

    private List<Sprite> sprites;

    public Map(Configuration configuration) {
        this.configuration = configuration;
        this.tiles = new ArrayList<>();
        this.sprites = new ArrayList<>();

        for (int x = 0; x < configuration.getWidth(); x++) {
            for (int y = 0; y < configuration.getHeight(); y++) {
                tiles.add(new Tile("img/tiles/floor_1.png", new Vector(x, y)));
            }
        }
    }

    public void render(Pane pane) {
        pane.getChildren().clear();

        for (Tile tile : tiles) {
            tile.render(pane);
        }

        for (Sprite sprite : sprites) {
            sprite.render(pane);
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public Sprite findSpriteById(String id) {
        for (Sprite sprite : sprites) {
            if (sprite.id.equals(id)) {
                return sprite;
            }
        }

        return null;
    }
}
