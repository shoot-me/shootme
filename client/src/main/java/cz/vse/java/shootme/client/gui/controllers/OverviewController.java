package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.G;
import cz.vse.java.shootme.client.Util;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.net.requests.ChangeUsernameRequest;
import cz.vse.java.shootme.server.net.requests.NewGameRequest;
import cz.vse.java.shootme.server.net.requests.OverviewRequest;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.net.responses.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OverviewController extends Controller {

    @FXML
    public TextField username;

    @FXML
    public ComboBox<String> avatar;

    @FXML
    public TextField newGame;

    @FXML
    public ListView<String> gameview;

    @FXML
    public TabPane tabPane;

    @FXML
    public Tab gameListTab;

    @FXML
    public TextField newUsername;

    public List<Configuration> configurations = new ArrayList<>();

    @Override
    public void created() {
        avatar.getItems().add("knight_orange");
        avatar.getItems().add("knight_pink");

        avatar.getSelectionModel().select(G.avatar);
    }

    @Override
    public void mounted() {
        onRefresh();

        username.setText(G.playerName);
    }

    public void onNewGame() {
        try {
            NewGameResponse response = (NewGameResponse) Client.get().send(new NewGameRequest(newGame.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            Util.showErrorMessage("Error");
            SceneManager.get().activate("signin");
        }

        newGame.setText("");

        onRefresh();

        tabPane.getSelectionModel().select(gameListTab);
    }

    public void onRefresh() {
        try {
            OverviewResponse response = (OverviewResponse) Client.get().send(new OverviewRequest());

            configurations = response.configurations;
        } catch (Exception e) {
            e.printStackTrace();
            Util.showErrorMessage("Error");
            SceneManager.get().activate("signin");
        }

        gameview.getItems().clear();

        for (Configuration configuration : configurations) {
            gameview.getItems().add(configuration.getName());
        }
    }

    public void onGameSelect(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() != 2) return;

        G.gameName = gameview.getSelectionModel().getSelectedItem();

        SceneManager.get().activate("game");
    }

    public void onAvatarSelect() {
        G.avatar = avatar.getSelectionModel().getSelectedItem();
    }

    public void onLogout() throws IOException {
        SceneManager.get().activate("signin");
    }

    public void onNewUsername(){
        try {
            ChangeUsernameRequest changeUsernameRequest = new ChangeUsernameRequest(newUsername.getText());
            Response response = Client.get().send(changeUsernameRequest);

            if (response instanceof ErrorResponse) {
                Util.showErrorMessage(((ErrorResponse) response).message);
            } else if (response instanceof ChangeUsernameResponse) {
                Util.showSuccessMessage("Username changed successfully.");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
