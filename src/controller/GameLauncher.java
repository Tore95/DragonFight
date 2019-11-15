package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Player;
import model.enums.Characters;
import model.enums.GameMode;
import model.factory.GameFactory;
import view.GlobalManager;
import view.UIPlayerOne;
import view.UIPlayerTwo;

public class GameLauncher extends Application {

    private final GlobalManager globalManager = GlobalManager.getInstance();
    private final GameFactory gameFactory = GameFactory.getInstance();

    private Group background;
    private Group players;
    private Group auraAttacks;
    private Group UI;
    private Scene scene;
    private GameMode gameMode;
    private Characters[] selection;
    private boolean inPause = false;

    private void clearAll() {
        background.getChildren().clear();
        players.getChildren().clear();
        auraAttacks.getChildren().clear();
        UI.getChildren().clear();
    }

    private void startGame() {
        mainMenu();
    }

    private void mainMenu() {
        clearAll();
        VBox layout = new VBox();
        UI.getChildren().add(layout);
        Button singleplayer = new Button("Singleplayer");
        Button multiplayer = new Button("Multiplayer");
        Button exit = new Button("Close Game");
        layout.getChildren().addAll(singleplayer,multiplayer,exit);

        singleplayer.setOnAction(event -> {
            gameFactory.setSingleplayerGameMode();
            gameMode = GameMode.SINGLEPLAYER;
            selectionMenu();
        });
        multiplayer.setOnAction(event -> {
            gameFactory.setMultiplayerGameMode();
            gameMode = GameMode.MULTIPLAYER;
            selectionMenu();
        });
        exit.setOnAction(event -> Platform.exit());
    }

    private void selectionMenu() {
        clearAll();
        globalManager.stop();
        globalManager.setPlayerOne(null);
        selection = new Characters[2];
        Button goku = new Button("goku");
        Button vegeta = new Button("vegeta");
        Button back = new Button("back");
        VBox layout = new VBox();
        UI.getChildren().add(layout);
        layout.getChildren().addAll(goku,vegeta,back);

        goku.setOnAction(ev -> {
            selection[0] = Characters.GOKU;
            selection[1] = Characters.VEGETA;
            gameScene();
        });
        vegeta.setOnAction(ev -> {
            selection[0] = Characters.VEGETA;
            selection[1] = Characters.GOKU;
            gameScene();
        });
        back.setOnAction(ev -> mainMenu());

    }
    private void gameScene() {
        clearAll();

        Player playerOne = gameFactory.makePlayerOne(selection[0]);
        Player playerTwo = gameFactory.makePlayerTwo(selection[1]);
        gameFactory.makeBackground();

        playerOne.setTarget(playerTwo);
        playerTwo.setTarget(playerOne);

        new UIPlayerOne(playerOne);
        new UIPlayerTwo(playerTwo);

        globalManager.start();
        inPause = false;

        scene.setOnKeyPressed(event -> {
            playerOne.pressedKeyEvent(event);
            if (gameMode.equals(GameMode.MULTIPLAYER)) playerTwo.pressedKeyEvent(event);
        });

        scene.setOnKeyReleased(event -> {
            playerOne.releasedKeyEvent(event);
            if (gameMode.equals(GameMode.MULTIPLAYER)) playerTwo.releasedKeyEvent(event);
            if (event.getCode().equals(KeyCode.ESCAPE) && !inPause) pauseScene();
        });
    }

    private void pauseScene() {
        globalManager.stop();
        inPause = true;
        Rectangle overlay = new Rectangle(0,0,1280,720);
        overlay.setFill(new Color(1,1,1,0.5));

        Button resume = new Button("resume");
        Button back = new Button("Back");
        Button exit = new Button("Close Game");
        VBox layout = new VBox(resume,back,exit);
        UI.getChildren().addAll(overlay,layout);

        resume.setOnAction(ev -> {
            UI.getChildren().removeAll(overlay,layout);
            inPause = false;
            globalManager.start();
        });

        back.setOnAction(ev -> selectionMenu());
        exit.setOnAction(ev -> Platform.exit());

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DragonFight");
        background = new Group();
        players = new Group();
        auraAttacks = new Group();
        UI = new Group();
        Group root = new Group(background,players,auraAttacks,UI);
        globalManager.initGroups(root);
        scene = new Scene(root,1280,720);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        startGame();
    }
}
