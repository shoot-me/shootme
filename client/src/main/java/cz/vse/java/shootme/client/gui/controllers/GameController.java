package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.G;
import cz.vse.java.shootme.client.game.Map;
import cz.vse.java.shootme.client.game.Sprite;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.net.GameClient;
import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.State;
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
import java.util.List;

public class GameController extends Controller {

    @FXML
    public Pane pane;

    private Thread thread;

    private Timeline timeline;

    private int port;

    private Configuration configuration;

    private State state;

    private Map map;

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

        GameClient.connect("localhost", port, G.connectionId);

        System.out.println("Connected");

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
        for (EntityUpdate removed : stateUpdate.removedEntities) {
            map.getSprites().removeIf(e -> e.id.equals(removed.id));
        }

        for (EntityUpdate updated : stateUpdate.updatedEntities) {
            for (Sprite sprite : map.getSprites()) {
                if (sprite.id.equals(updated.id)) {
                    sprite.updateFrom(updated);
                }
            }
        }

        for (EntityUpdate added : stateUpdate.addedEntities) {
            map.getSprites().add(new Sprite(added));
        }
    }

    public synchronized void render(ActionEvent actionEvent) {
        map.render(pane);
    }

}
