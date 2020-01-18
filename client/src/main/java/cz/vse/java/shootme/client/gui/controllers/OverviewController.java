package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.Util;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.server.net.requests.OverviewRequest;
import cz.vse.java.shootme.server.net.responses.OverviewResponse;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class OverviewController extends Controller {

    @FXML
    public TextField username;

    @Override
    public void mounted() {
        OverviewResponse response;

        try {
            response = (OverviewResponse) Client.get().sendSync(new OverviewRequest(Client.get().getToken()));
        } catch (IOException e) {
            e.printStackTrace();
            Util.showErrorMessage("Error");
            SceneManager.get().activate("signin");
            return;
        }

        username.setText(response.username);
    }

    public void onNewGame() {
        SceneManager.get().activate("game");
    }

    public void onRefresh() {
    }

    public void onLogout() throws IOException {
        SceneManager.get().activate("signin");
    }

}
