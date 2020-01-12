package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.common.game.State;
import cz.vse.java.shootme.common.game.entities.Entity;
import cz.vse.java.shootme.common.game.entities.Player;
import cz.vse.java.shootme.common.requests.GameUpdateRequest;
import cz.vse.java.shootme.common.requests.JoinGameRequest;
import cz.vse.java.shootme.common.responses.GameUpdateResponse;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class GameController implements Controller {

    @FXML
    public Pane pane;

    private final String playerName = UUID.randomUUID().toString();

    private State state;

    private Map map;

    private boolean initialized = false;

    @Override
    public void initialize() {
        if (initialized) return;
        initialized = true;

        try {
            GameUpdateResponse response = (GameUpdateResponse) Client.get().sendSync(new JoinGameRequest("default", playerName));

            state = response.state;
        } catch (IOException e) {
            e.printStackTrace();

            return;
        }

        map = new Map(10, 10);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), this::update));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void update(ActionEvent actionEvent) {
        try {
            GameUpdateResponse response = (GameUpdateResponse) Client.get().sendSync(new GameUpdateRequest("default", playerName, new ArrayList<>()));

            System.out.println(response.id);

            state = response.state;
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.clearEntities();

        for (Entity entity : state.getEntities()) {
            if (entity instanceof Player && ((Player) entity).name.equals(playerName)) {
                map.setPlayer((Player) entity);
            } else {
                map.addEntity(entity);
            }
        }

        pane.getChildren().clear();

        map.render(pane);
    }
}
