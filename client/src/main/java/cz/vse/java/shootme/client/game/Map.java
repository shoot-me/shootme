package cz.vse.java.shootme.client.game;

import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.Configuration;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Configuration configuration;

    private StateUpdate stateUpdate;

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

        if(!stateUpdate.running) {
            Label label = new Label("Game over!");
            label.setFont(Font.font(24));
            label.setTextFill(Paint.valueOf("red"));
            label.setLayoutX(300);
            label.setLayoutY(100);
            pane.getChildren().add(label);
            return;
        }

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

    public void setStateUpdate(StateUpdate stateUpdate) {
        this.stateUpdate = stateUpdate;
    }
}
