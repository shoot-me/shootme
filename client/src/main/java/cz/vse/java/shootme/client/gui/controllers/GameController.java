package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.G;
import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.State;
import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.net.requests.GameUpdateRequest;
import cz.vse.java.shootme.server.net.requests.JoinGameRequest;
import cz.vse.java.shootme.server.net.responses.GameUpdateResponse;
import cz.vse.java.shootme.server.net.responses.JoinGameResponse;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController extends Controller {

    @FXML
    public Pane pane;

    private Configuration configuration;

    private State state;

    private Map map;

    private final Set<String> pressedKeys = new HashSet<>();

    private final List<Action> actions = new ArrayList<>();

    @Override
    public void mounted() {
        SceneManager.get().getScene().setOnKeyPressed(keyEvent -> {
            String key = keyEvent.getCode().getName();

            if (!pressedKeys.contains(key)) {
                actions.add(new KeyPressAction(key));
                pressedKeys.add(key);
            }
        });

        SceneManager.get().getScene().setOnKeyReleased(keyEvent -> {
            String key = keyEvent.getCode().getName();

            if (pressedKeys.contains(key)) {
                actions.add(new KeyReleaseAction(key));
                pressedKeys.remove(key);
            }
        });

        try {
            JoinGameResponse response = (JoinGameResponse) Client.get().send(new JoinGameRequest(G.gameName));

            configuration = response.configuration;
            state = response.state;
        } catch (IOException e) {
            e.printStackTrace();

            return;
        }

        map = new Map(configuration);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), this::update));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void update(ActionEvent actionEvent) {
        try {
            GameUpdateResponse response = (GameUpdateResponse) Client.get().send(new GameUpdateRequest(actions));

            state = response.state;
        } catch (IOException e) {
            e.printStackTrace();
        }

        actions.clear();

        map.clearEntities();

        for (Entity entity : state.getEntities()) {
            if (entity instanceof Player && ((Player) entity).name.equals(G.playerName)) {
                map.setPlayer((Player) entity);
            } else {
                map.addEntity(entity);
            }
        }

        pane.getChildren().clear();

        map.render(pane);
    }
}
