package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GlobalManager;

public class GameLauncher extends Application {

    private GlobalManager globalManager = GlobalManager.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        globalManager.startGame(primaryStage);
    }
}
