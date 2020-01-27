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
        address.setText(G.server);
        username.requestFocus();
        username.setText("");
        password.setText("");
    }

    public void onLogin() {
        G.server = address.getText();

        boolean ok = Client.connect(G.server, 8080);
        if (!ok) {
            Util.showErrorMessage("Could not connect to a server");
            return;
        }

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

        if (username.getText().equals("")) {
            Util.showErrorMessage("Username or password can not be empty ");
            return;
        }

        if (password.getText().equals("")) {
            Util.showErrorMessage("Username or password can not be empty ");
            return;
        }

        G.server = address.getText();

        System.out.println(G.server);

        boolean ok = Client.connect(G.server, 8080);
        if (!ok) {
            Util.showErrorMessage("Could not connect to a server");
        }

        RegisterRequest registerRequest = new RegisterRequest(username.getText(), password.getText());
        Response response = Client.get().send(registerRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage("Could not register user!");
        } else if (response instanceof SuccessResponse) {
            Util.showSuccessMessage("Register successful.");
        }
    }

    @Override
    public Vector getWindowSize() {
        return new Vector(275, 275);
    }
}
