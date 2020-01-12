package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.game.Player;
import cz.vse.java.shootme.client.game.State;
import cz.vse.java.shootme.client.services.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameController implements Controller {

    @FXML
    public Pane pane;

    private State state;

    private Map map;

    private Player player;

    @Override
    public void initialize() {
        map = new Map(30, 20);
        state = new State();
        player = new Player(map, new Image("img/players/player_1.png"), "Winty");

        map.setPlayer(player);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), this::update));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void update(ActionEvent actionEvent) {
        pane.getChildren().clear();

        map.update();

        map.render(pane);
    }
}
