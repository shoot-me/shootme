package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.server.game.State;
import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.net.requests.GameUpdateRequest;
import cz.vse.java.shootme.server.net.requests.JoinGameRequest;
import cz.vse.java.shootme.server.net.responses.GameUpdateResponse;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController extends Controller {

    @FXML
    public Pane pane;

    private State state;

    private Map map;

    @Override
    public void mounted() {
        try {
            GameUpdateResponse response = (GameUpdateResponse) Client.get().send(new JoinGameRequest("default"));

            state = response.state;
        } catch (IOException e) {
            e.printStackTrace();

            return;
        }

        map = new Map(10, 10);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), this::update));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void update(ActionEvent actionEvent) {
        List<Action> actions = new ArrayList<>();

        if (map.isActiveKey(KeyCode.W)) {
            actions.add(new KeyPressAction("W"));
        }

        if (map.isActiveKey(KeyCode.A)) {
            actions.add(new KeyPressAction("A"));
        }

        if (map.isActiveKey(KeyCode.S)) {
            actions.add(new KeyPressAction("S"));
        }

        if (map.isActiveKey(KeyCode.D)) {
            actions.add(new KeyPressAction("D"));
        }

        try {
            GameUpdateResponse response = (GameUpdateResponse) Client.get().send(new GameUpdateRequest(actions));

            state = response.state;
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.clearEntities();

        for (Entity entity : state.getEntities()) {
            if (entity instanceof Player && ((Player) entity).name.equals("a")) {
                map.setPlayer((Player) entity);
            } else {
                map.addEntity(entity);
            }
        }

        pane.getChildren().clear();

        map.render(pane);
    }
}
