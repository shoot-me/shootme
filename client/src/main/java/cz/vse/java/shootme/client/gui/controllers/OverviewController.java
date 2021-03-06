package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.G;
import cz.vse.java.shootme.client.Util;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.net.requests.*;
import cz.vse.java.shootme.server.net.responses.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverviewController extends Controller {

    @FXML
    public TextField friendUser;

    @FXML
    public TextField username;

    @FXML
    public TextField avatar;

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

    @FXML
    public TextField currentPassword;

    @FXML
    public TextField newPassword1;

    @FXML
    public TextField newPassword2;

    @FXML
    public ComboBox<String> newAvatar;

    @FXML
    public ListView<String> statistics;

    @FXML
    public TextField configName;

    @FXML
    public TextField configDuration;

    @FXML
    public ListView<String> configList;

    @FXML
    public ListView<String> friendlist;

    public List<Configuration> configurations = new ArrayList<>();

    public Map<String, String> skins = new HashMap<>();

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
            skins = response.skins;
            G.avatar = response.avatar;
        } catch (Exception e) {
            e.printStackTrace();
            Util.showErrorMessage("Error");
            SceneManager.get().activate("signin");
        }

        gameview.getItems().clear();

        for (Configuration configuration : configurations) {
            gameview.getItems().add(configuration.getName());
        }

        newAvatar.getItems().clear();
        newAvatar.getItems().add("");

        for (Map.Entry<String, String> skin : skins.entrySet()) {
            newAvatar.getItems().add(skin.getValue());

            if (G.avatar.equals(skin.getKey())) {
                avatar.setText(skin.getValue());
            }
        }
    }

    public void onGameSelect(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() != 2) return;

        String name = gameview.getSelectionModel().getSelectedItem();

        if (name == null || name.equals("")) return;

        G.gameName = name;

        SceneManager.get().activate("game");
    }

    public void onAvatarSelect() {
        String name = newAvatar.getSelectionModel().getSelectedItem();

        if (name == null) return;

        Response response = Client.get().send(new UpdateSkinRequest(name));
        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        } else if (response instanceof SuccessResponse) {
            Util.showSuccessMessage(((SuccessResponse) response).message);
        } else {
            Util.showErrorMessage("Bad response from server!");
        }

        Platform.runLater(this::onRefresh);
    }

    public void onLogout() throws IOException {
        SceneManager.get().activate("signin");
    }

    public void onNewUsername() {

        if (newUsername.getText().equals("")) {
            Util.showErrorMessage("Username can not be empty.");
            return;
        }

        try {
            ChangeUsernameRequest changeUsernameRequest = new ChangeUsernameRequest(newUsername.getText());
            Response response = Client.get().send(changeUsernameRequest);

            if (response instanceof ErrorResponse) {
                Util.showErrorMessage("Could not change username.");
            } else if (response instanceof SuccessResponse) {
                Util.showSuccessMessage("Username changed successfully.");
                username.setText(newUsername.getText());
                G.playerName = newUsername.getText();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onNewPassword() {
        if (currentPassword.getText().equals("")) {
            Util.showErrorMessage("Incorrect password.");
            return;
        }

        if (newPassword1.getText().equals("")) {
            Util.showErrorMessage("Passwords do not match or empty");
            return;
        }

        if (newPassword2.getText().equals("")) {
            Util.showErrorMessage("Passwords do not match or empty");
            return;
        }

        if (!newPassword1.getText().equals(newPassword2.getText())) {
            Util.showErrorMessage("Passwords do not match or empty");
            return;
        }

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(currentPassword.getText(), newPassword2.getText());
        Response response = Client.get().send(changePasswordRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        } else if (response instanceof SuccessResponse) {
            Util.showSuccessMessage("Password changed succesfully.");
        }
    }

    public void onSelectStatistics() {
        System.out.println("Getting statistics");

        Response response = Client.get().send(new GetStatisticsRequest());
        if (response instanceof GetStatisticsResponse) {
            statistics.getItems().setAll(((GetStatisticsResponse) response).statistics);
        } else if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        }
    }

    public void onConfigCreate() {
        String name = configName.getText();
        int duration = 0;

        try {
            duration = Integer.parseInt(configDuration.getText());
        } catch (Exception e) {
            Util.showErrorMessage("Invalid duration!");
            return;
        }

        Response response = Client.get().send(new CreateConfigRequest(name, duration));
        if (response instanceof SuccessResponse) {
            Util.showSuccessMessage(((SuccessResponse) response).message);
        } else if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        } else {
            Util.showErrorMessage("Error");
        }

        GetConfigurationsResponse configurationsResponse = (GetConfigurationsResponse) Client.get().send(new GetConfigurationsRequest());

        configList.getItems().clear();

        for (Map.Entry<Integer, String> configuration : configurationsResponse.configurations.entrySet()) {
            configList.getItems().add(configuration.getValue());
        }
    }

    public void onNewest() {
        Response response = Client.get().send(new GetStatisticsRequest("ASC"));
        if (response instanceof GetStatisticsResponse) {
            statistics.getItems().setAll(((GetStatisticsResponse) response).statistics);
        } else if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        }
    }

    public void onOldest() {
        Response response = Client.get().send(new GetStatisticsRequest("DESC"));
        if (response instanceof GetStatisticsResponse) {
            statistics.getItems().setAll(((GetStatisticsResponse) response).statistics);
        } else if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        }
    }



    public void onFindUser(){

        Response response = Client.get().send(new SetFriendRequest(friendUser.getText()));

        if(response instanceof SuccessResponse){
            Util.showSuccessMessage("User added as a friend");
        } else if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        }

        Response response1 = Client.get().send(new GetFriendsRequest());

        if(response1 instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response1).message);
        } else if(response1 instanceof GetFriendsResponse) {
            friendlist.getItems().clear();

            for (Map.Entry<Integer, String> friend : ((GetFriendsResponse) response1).friends.entrySet()){
                friendlist.getItems().add(friend.getValue());
            }
        }




    }

    public void onRefreshFriendlist(){
        Response response1 = Client.get().send(new GetFriendsRequest());

        if(response1 instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response1).message);
        } else if(response1 instanceof GetFriendsResponse) {
            friendlist.getItems().clear();

            for (Map.Entry<Integer, String> friend : ((GetFriendsResponse) response1).friends.entrySet()){
                friendlist.getItems().add(friend.getValue());
            }
        }


    }



}
