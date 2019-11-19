package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.client.net.Client;
import cz.vse.java.shootme.common.requests.Registration;
import cz.vse.java.shootme.common.responses.Error;
import cz.vse.java.shootme.common.responses.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationController implements Controller {

    @FXML
    public TextField name;

    @FXML
    public TextField password;

    @Override
    public void initialize() {

    }

    public void onRegister() throws IOException {
        Registration registration = new Registration(name.getText(), password.getText());
        Response response = Client.get().sendSync(registration);

        Alert alert = new Alert(response instanceof Error ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(response.getMessage());
        alert.setHeaderText(response.getMessage());
        alert.setContentText(response.getMessage());
        alert.showAndWait();
    }
}
