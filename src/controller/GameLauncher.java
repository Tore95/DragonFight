package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.GlobalManager;
import model.enums.Characters;
import model.player.MultiPlayerOne;
import model.player.MultiPlayerTwo;
import old.view.Map;

public class GameLauncher extends Application {

    private GlobalManager globalManager = GlobalManager.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DragonFight");
        primaryStage.setScene(globalManager.getScene());

        MultiPlayerOne goku = new MultiPlayerOne(Characters.GOKU);
        MultiPlayerTwo vegeta = new MultiPlayerTwo(Characters.VEGETA);

        goku.setTarget(vegeta);
        vegeta.setTarget(goku);

        globalManager.getScene().setOnKeyPressed(ev -> {
            goku.pressedKeyEvent(ev);
            vegeta.pressedKeyEvent(ev);
        });

        globalManager.getScene().setOnKeyReleased(ev -> {
            goku.releasedKeyEvent(ev);
            vegeta.releasedKeyEvent(ev);
        });

        primaryStage.show();
    }
}
