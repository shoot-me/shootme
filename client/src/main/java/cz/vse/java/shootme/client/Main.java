package cz.vse.java.shootme.client;

import cz.vse.java.shootme.client.gui.controllers.RegistrationController;
import cz.vse.java.shootme.client.services.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Pane());
        SceneManager.get().setScene(scene);
        SceneManager.get().newScreen("registration", "gui/fxml/registration.fxml", new RegistrationController());
        SceneManager.get().activate("registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
