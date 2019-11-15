package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        gameFactory.makeMenuBackground();
        Button singleplayer = new Button("Singleplayer");
        Button multiplayer = new Button("Multiplayer");
        Button exit = new Button("Close Game");
        UI.getChildren().addAll(singleplayer,multiplayer,exit);

        singleplayer.setLayoutX(50);
        singleplayer.setLayoutY(200);
        singleplayer.setPrefSize(300,80);

        multiplayer.setLayoutX(50);
        multiplayer.setLayoutY(300);
        multiplayer.setPrefSize(300,80);

        exit.setLayoutX(50);
        exit.setLayoutY(400);
        exit.setPrefSize(300,80);

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
        gameFactory.makeSelectionBackground();

        Rectangle overlayGoku = new Rectangle(0,0,640,720);
        overlayGoku.setFill(new Color(0.3,0.3,0.3,0.5));
        Rectangle overlayVegeta = new Rectangle(640,0,640,720);
        overlayVegeta.setFill(new Color(0.3,0.3,0.3,0.5));
        Rectangle gokuButton = new Rectangle(0,70,640,650);
        gokuButton.setFill(new Color(0,0,0,0));
        Rectangle vegetaButton = new Rectangle(640,70,640,650);
        vegetaButton.setFill(new Color(0,0,0,0));

        Button backButton = new Button("Back");
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        backButton.setPrefSize(80,50);

        UI.getChildren().addAll(gokuButton,vegetaButton,overlayGoku,overlayVegeta,backButton);

        overlayGoku.setOnMouseEntered(event -> {
            overlayGoku.setX(-640);
            overlayVegeta.setX(640);
        });

        overlayVegeta.setOnMouseEntered(event -> {
            overlayGoku.setX(0);
            overlayVegeta.setX(1280);
        });

        gokuButton.setOnMouseClicked( ev -> {
            selection[0] = Characters.GOKU;
            selection[1] = Characters.VEGETA;
            gameScene();
        });

        vegetaButton.setOnMouseClicked(ev -> {
            selection[0] = Characters.VEGETA;
            selection[1] = Characters.GOKU;
            gameScene();
        });
        backButton.setOnAction(ev -> mainMenu());

    }
    private void gameScene() {
        clearAll();

        Player playerOne = gameFactory.makePlayerOne(selection[0]);
        Player playerTwo = gameFactory.makePlayerTwo(selection[1]);

        playerOne.setTarget(playerTwo);
        playerTwo.setTarget(playerOne);

        gameFactory.makeGameBackground();
        gameFactory.makeGameUI(playerOne,playerTwo);

        globalManager.start();
        inPause = false;

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (playerOne.getLife() < 0 || playerTwo.getLife() < 0) {
                    endGame();
                    this.stop();
                }
            }
        }.start();

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
        UI.getChildren().addAll(overlay,resume,back,exit);

        resume.setLayoutX(490);
        resume.setLayoutY(200);
        resume.setPrefSize(300,80);

        back.setLayoutX(490);
        back.setLayoutY(300);
        back.setPrefSize(300,80);

        exit.setLayoutX(490);
        exit.setLayoutY(400);
        exit.setPrefSize(300,80);

        resume.setOnAction(ev -> {
            UI.getChildren().removeAll(overlay,resume,back,exit);
            inPause = false;
            globalManager.start();
        });

        back.setOnAction(ev -> selectionMenu());
        exit.setOnAction(ev -> Platform.exit());

    }
    private void endGame() {
        globalManager.stop();
        inPause = true;
        Rectangle overlay = new Rectangle(0,0,1280,720);
        overlay.setFill(new Color(1,1,1,0.5));

        Text gameOver = new Text("GameOver");
        gameOver.setFont(new Font(78));
        gameOver.setX(450);
        gameOver.setY(100);

        Button back = new Button("Back");
        Button exit = new Button("Close Game");
        UI.getChildren().addAll(overlay,gameOver,back,exit);

        back.setLayoutX(490);
        back.setLayoutY(200);
        back.setPrefSize(300,80);

        exit.setLayoutX(490);
        exit.setLayoutY(300);
        exit.setPrefSize(300,80);

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
