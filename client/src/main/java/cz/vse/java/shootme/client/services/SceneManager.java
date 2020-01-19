package cz.vse.java.shootme.client.services;


import cz.vse.java.shootme.common.util.Vector;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import cz.vse.java.shootme.client.gui.controllers.Controller;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class SceneManager {

    private static SceneManager instance;

    private Stage primaryStage;

    private HashMap<String, LazyScene> scenes;

    private Scene activeScene;

    private Controller activeController;

    public void newScreen(String name, String fxml, Controller controller) {
        scenes.put(name, new LazyScene(fxml, controller));
    }

    public void activate(String name) {
        if (activeController != null) {
            activeController.unmounted();
        }

        LazyScene scene = scenes.get(name);

        try {
            primaryStage.setScene(scene.get());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Vector windowSize = scene.controller.getWindowSize();
        primaryStage.setWidth(windowSize.x);
        primaryStage.setHeight(windowSize.y);

        activeScene = scene.scene;
        activeController = scene.controller;

        scene.controller.mounted();

        primaryStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public Scene getScene() {
        return activeScene;
    }

    private SceneManager() {
        scenes = new HashMap<>();
    }

    public static SceneManager get() {
        if (instance == null) {
            instance = new SceneManager();
        }

        return instance;
    }

    private class LazyScene {

        private Scene scene;

        private String fxml;

        private Controller controller;

        private Parent root;

        public LazyScene(String fxml, Controller controller) {
            this.scene = null;
            this.fxml = fxml;
            this.controller = controller;
            this.root = null;
        }

        public Scene get() throws IOException {
            if (scene != null) return scene;

            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);

            loader.setLocation(getClass().getClassLoader().getResource(fxml));
            root = loader.load();
            scene = new Scene(root);

            controller.created();

            return scene;
        }
    }
}
