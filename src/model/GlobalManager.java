package model;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.baseObjs.Background;
import model.baseObjs.GameObject;
import model.baseObjs.Player;
import model.enums.Characters;
import model.factory.GameFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

public class GlobalManager extends AnimationTimer {

    private static GlobalManager instance;
    private final long FPS = 60L;
    private final long SPRITE_FPS = 16L;
    private GameFactory gameFactory = GameFactory.getInstance();

    private Properties spriteMapping;
    private Group root;
    private Group background;
    private Group players;
    private Group auraAttacks;
    private Group UI;
    private Scene scene;
    private Stage stage;
    private LinkedList<GameObject> gameObjects;
    private LinkedList<GameObject> toRemoveObjects;
    private LinkedList<GameObject> toAddObjects;
    private HashMap<Integer,Characters> playerSelection;
    private double lastFrame;
    private double lastSpriteFrame;

    private GlobalManager() {
        lastFrame = System.nanoTime();
        lastSpriteFrame = lastFrame;
        playerSelection = new HashMap<>();
        try {
            spriteMapping = new Properties();
            spriteMapping.load(getClass().getClassLoader().getResourceAsStream("resources/spriteMapping.properties"));
        } catch (IOException e) {
            System.err.println("Unable to load sprite mapping properties");
            System.exit(-1);
        }
    }
    private void init(int width, int height) {
        this.stop();
        root = new Group();
        background = new Group();
        players = new Group();
        auraAttacks = new Group();
        UI = new Group();
        root.getChildren().add(0,background);
        root.getChildren().add(1,players);
        root.getChildren().add(2,auraAttacks);
        root.getChildren().add(3,UI);
        scene = new Scene(root,width,height);
        stage.setScene(scene);
        stage.centerOnScreen();
        gameObjects = new LinkedList<>();
        toRemoveObjects = new LinkedList<>();
        toAddObjects = new LinkedList<>();
    }
    private void gameScene() {
        init(1280,720);
        Player playerOne = gameFactory.makePlayerOne(playerSelection.get(1));
        Player playerTwo = gameFactory.makePlayerTwo(playerSelection.get(2));
        Background background = gameFactory.makeBackground();

        playerOne.setTarget(playerTwo);
        playerTwo.setTarget(playerOne);

        scene.setOnKeyPressed(event -> {
            playerOne.pressedKeyEvent(event);
            playerTwo.pressedKeyEvent(event);
        });

        scene.setOnKeyReleased(event -> {
            playerOne.releasedKeyEvent(event);
            playerTwo.releasedKeyEvent(event);
        });

        this.start();
    }
    private void playerSelection() {
        init(600,800);
        Button goku = new Button("goku");
        Button vegeta = new Button("vegeta");
        VBox layout = new VBox();
        UI.getChildren().add(layout);
        layout.getChildren().addAll(goku,vegeta);
        goku.setOnAction(ev -> {
            playerSelection.put(1,Characters.GOKU);
            playerSelection.put(2,Characters.VEGETA);
            gameScene();
        });
        vegeta.setOnAction(ev -> {
            playerSelection.put(1,Characters.VEGETA);
            playerSelection.put(2,Characters.GOKU);
            gameScene();
        });

    }
    public void startGame(Stage s) {
        stage = s;
        stage.setTitle("DragonFight");
        stage.show();
        init(600,800);
        VBox layout = new VBox();
        UI.getChildren().add(layout);
        Button singleplayer = new Button("Singleplayer");
        Button multiplayer = new Button("Multiplayer");
        Button exit = new Button("Close Game");
        layout.getChildren().addAll(singleplayer,multiplayer,exit);
        singleplayer.setOnAction(event -> {
            gameFactory.setSingleplayerGameMode();
            playerSelection();
        });
        multiplayer.setOnAction(event -> {
            gameFactory.setMultiplayerGameMode();
            playerSelection();
        });
        exit.setOnAction(event -> stage.close());
    }

    public static GlobalManager getInstance() {
        if (instance == null) instance = new GlobalManager();
        return instance;
    }

    public Properties getSpriteMapping() {
        return spriteMapping;
    }
    public Group getBackground() {
        return background;
    }
    public Group getPlayers() {
        return players;
    }
    public Group getAuraAttacks() {
        return auraAttacks;
    }
    public Group getUI() {
        return UI;
    }
    public LinkedList<GameObject> getToRemoveObjects() {
        return toRemoveObjects;
    }
    public LinkedList<GameObject> getToAddObjects() {
        return toAddObjects;
    }

    @Override
    public void handle(long now) {
        if ((int) Math.floor((now - lastFrame) / (double)(1000000000L / FPS)) >= 1) {
            lastFrame = now;
            gameObjects.removeAll(toRemoveObjects);
            toRemoveObjects.clear();
            gameObjects.addAll(toAddObjects);
            toAddObjects.clear();
            gameObjects.forEach(GameObject::move);
            if ((int) Math.floor((now - lastSpriteFrame) / (double)(1000000000L / SPRITE_FPS)) >= 1) {
                lastSpriteFrame = now;
                gameObjects.forEach(GameObject::draw);
            }
        }
    }
}
