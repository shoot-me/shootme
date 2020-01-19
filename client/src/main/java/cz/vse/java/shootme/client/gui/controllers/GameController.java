package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.G;
import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.game.Sprite;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.net.GameClient;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.net.requests.JoinGameRequest;
import cz.vse.java.shootme.server.net.responses.JoinGameResponse;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameController extends Controller {

    @FXML
    public Pane pane;

    private Thread thread;

    private Timeline timeline;

    private int port;

    private Configuration configuration;

    private Map map;

    private Set<String> pressedKeys = new HashSet<>();

    @Override
    public void mounted() {
        try {
            JoinGameResponse response = (JoinGameResponse) Client.get().send(new JoinGameRequest(G.gameName));

            port = response.port;
            configuration = response.configuration;
        } catch (IOException e) {
            e.printStackTrace();

            return;
        }

        registerKeyHandlers();

        GameClient.connect("localhost", port, G.connectionId);

        System.out.println("Connected to a game");

        map = new Map(configuration);

        thread = new Thread(this::run);
        thread.start();

        timeline = new Timeline(new KeyFrame(Duration.millis(20), this::render));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void run() {
        while (true) {
            try {
                StateUpdate stateUpdate = GameClient.get().getStateUpdates().take();

                update(stateUpdate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void update(StateUpdate stateUpdate) {
        map.getSprites().removeIf(e -> !stateUpdate.entityIds().contains(e.id));

        for (EntityUpdate entityUpdate : stateUpdate.entityUpdates) {
            Sprite sprite = map.findSpriteById(entityUpdate.id);

            if (sprite != null) {
                sprite.updateFrom(entityUpdate);
            } else {
                map.getSprites().add(new Sprite(entityUpdate));
            }
        }
    }

    public synchronized void render(ActionEvent actionEvent) {
        map.render(pane);
    }

    private void registerKeyHandlers() {
        SceneManager.get().getScene().setOnKeyPressed(keyEvent -> {
            String code = keyEvent.getCode().getName().toUpperCase();

            if (!pressedKeys.contains(code)) {
                pressedKeys.add(code);

                try {
                    GameClient.get().getActions().put(new KeyPressAction(code));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SceneManager.get().getScene().setOnKeyReleased(keyEvent -> {
            String code = keyEvent.getCode().getName().toUpperCase();

            pressedKeys.remove(code);

            try {
                GameClient.get().getActions().put(new KeyReleaseAction(code));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
