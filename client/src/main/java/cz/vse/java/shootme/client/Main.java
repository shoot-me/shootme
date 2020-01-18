package cz.vse.java.shootme.client;

import cz.vse.java.shootme.client.gui.controllers.GameController;
import cz.vse.java.shootme.client.gui.controllers.OverviewController;
import cz.vse.java.shootme.client.gui.controllers.SigninController;
import cz.vse.java.shootme.client.services.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ShootMe!");
        primaryStage.setResizable(false);

        SceneManager.get().setPrimaryStage(primaryStage);

        SceneManager.get().newScreen("signin", "gui/fxml/signin.fxml", new SigninController());
        SceneManager.get().newScreen("overview", "gui/fxml/overview.fxml", new OverviewController());
        SceneManager.get().newScreen("game", "gui/fxml/game.fxml", new GameController());

        SceneManager.get().activate("signin");
    }
}
