package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.G;
import cz.vse.java.shootme.client.Util;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.net.requests.LoginRequest;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.net.responses.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SigninController extends Controller {

    @FXML
    public TextField address;

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @Override
    public void mounted() {
        username.requestFocus();
        username.setText("");
        password.setText("");
    }

    public void onLogin() throws IOException {
        Client.connect(address.getText(), 8080);

        LoginRequest loginRequest = new LoginRequest(username.getText(), password.getText());
        Response response = Client.get().send(loginRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        } else if (response instanceof LoginSuccessfulResponse) {
            G.connectionId = ((LoginSuccessfulResponse) response).connectionId;
            G.playerName = username.getText();

            SceneManager.get().activate("overview");
        }
    }

    public void onRegister() throws IOException {
        Client.connect(address.getText(), 8080);

        RegisterRequest registerRequest = new RegisterRequest(username.getText(), password.getText());
        Response response = Client.get().send(registerRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).message);
        } else if (response instanceof RegisterSuccessfulResponse) {
            Util.showSuccessMessage("Register successful.");
        }
    }

    @Override
    public Vector getWindowSize() {
        return new Vector(275, 275);
    }
}
