package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.Util;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.client.services.SceneManager;
import cz.vse.java.shootme.common.requests.LoginRequest;
import cz.vse.java.shootme.common.requests.RegisterRequest;
import cz.vse.java.shootme.common.responses.ErrorResponse;
import cz.vse.java.shootme.common.responses.LoginSuccessfulResponse;
import cz.vse.java.shootme.common.responses.OkResponse;
import cz.vse.java.shootme.common.responses.Response;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SigninController implements Controller {

    @FXML
    public TextField address;

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @Override
    public void initialize() {
        username.requestFocus();
        username.setText("");
        password.setText("");
    }

    public void onLogin() throws IOException {
        Client.connect(address.getText(), 8080);

        LoginRequest loginRequest = new LoginRequest(username.getText(), password.getText());
        Response response = Client.get().sendSync(loginRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).getMessage());
            return;
        } else if (response instanceof LoginSuccessfulResponse) {
            String token = ((LoginSuccessfulResponse) response).token;
            Client.get().setToken(token);

            SceneManager.get().activate("overview");
        }
    }

    public void onRegister() throws IOException {
        Client.connect(address.getText(), 8080);

        RegisterRequest registerRequest = new RegisterRequest(username.getText(), password.getText());
        Response response = Client.get().sendSync(registerRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(((ErrorResponse) response).getMessage());
            return;
        } else if (response instanceof OkResponse) {
            Util.showSuccessMessage("Register successful.");
        }
    }
}
