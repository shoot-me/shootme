package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.Util;
import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.common.requests.LoginRequest;
import cz.vse.java.shootme.common.requests.RegisterRequest;
import cz.vse.java.shootme.common.responses.ErrorResponse;
import cz.vse.java.shootme.common.responses.Response;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SigninController implements Controller {

    @FXML
    public TextField address;

    @FXML
    public TextField username;

    @FXML
    public TextField password;

    @Override
    public void initialize() {

    }

    public void onLogin() throws IOException {
        Client.connect(address.getText(), 8080);

        LoginRequest loginRequest = new LoginRequest(username.getText(), password.getText());
        Response response = Client.get().sendSync(loginRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(response.getMessage());
            return;
        }
    }

    public void onRegister() throws IOException {
        Client.connect(address.getText(), 8080);

        RegisterRequest registerRequest = new RegisterRequest(username.getText(), password.getText());
        Response response = Client.get().sendSync(registerRequest);

        if (response instanceof ErrorResponse) {
            Util.showErrorMessage(response.getMessage());
            return;
        }
    }
}
