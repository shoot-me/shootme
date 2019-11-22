package cz.vse.java.shootme.client;

import cz.vse.java.shootme.client.gui.controllers.OverviewController;
import cz.vse.java.shootme.client.gui.controllers.SigninController;
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
        SceneManager.get().newScreen("signin", "gui/fxml/signin.fxml", new SigninController());
        SceneManager.get().newScreen("overview", "gui/fxml/overview.fxml", new OverviewController());
        SceneManager.get().activate("signin");

        primaryStage.setResizable(false);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
