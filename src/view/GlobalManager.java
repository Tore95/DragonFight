package view;

import controller.GameLauncher;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.GameObject;
import model.Player;
import model.enums.Characters;
import model.enums.MenuAction;
import model.enums.UserState;
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
    private MenuAction menuAction;
    private UserState userState;
    private double lastFrame;
    private double lastSpriteFrame;
    private boolean inPause;

    private GlobalManager() {
        lastFrame = System.nanoTime();
        lastSpriteFrame = lastFrame;
        menuAction = MenuAction.NONE;
        playerSelection = new HashMap<>();
        try {
            spriteMapping = new Properties();
            spriteMapping.load(getClass().getClassLoader().getResourceAsStream("resources/spriteMapping.properties"));
        } catch (IOException e) {
            System.err.println("Unable to load sprite mapping properties");
            Platform.exit();
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
    public void gameStart() {
        init(1280,720);
        userState = UserState.GAME;

        Player playerOne = gameFactory.makePlayerOne(playerSelection.get(1));
        Player playerTwo = gameFactory.makePlayerTwo(playerSelection.get(2));
        gameFactory.makeBackground();

        playerOne.setTarget(playerTwo);
        playerTwo.setTarget(playerOne);

        this.start();

        scene.setOnKeyPressed(event -> {
            playerOne.pressedKeyEvent(event);
            playerTwo.pressedKeyEvent(event);
        });

        scene.setOnKeyReleased(event -> {
            playerOne.releasedKeyEvent(event);
            playerTwo.releasedKeyEvent(event);
            if (event.getCode().equals(KeyCode.ESCAPE) && !inPause) pause();

        });
    }

    private void pause() {
        this.stop();
        Rectangle overlay = new Rectangle(0,0,1280,720);
        overlay.setFill(new Color(1,1,1,0.5));

        Button resume = new Button("resume");
        Button backOne = new Button("Back");
        Button exit = new Button("Close Game");
        VBox layout = new VBox(resume,backOne,exit);
        UI.getChildren().addAll(overlay,layout);

        resume.setOnAction(ev -> {
            UI.getChildren().removeAll(overlay,layout);
            this.start();
        });
        backOne.setOnAction(ev -> {
            menuAction = MenuAction.BACK;
            triggerGL();
        });

        exit.setOnAction(ev -> {
            menuAction = MenuAction.EXIT;
            triggerGL();
        });

    }

    public void playerSelection() {
        init(600,800);
        userState = UserState.SELECTION_MENU;

        Button goku = new Button("goku");
        Button vegeta = new Button("vegeta");
        Button back = new Button("back");
        VBox layout = new VBox();
        UI.getChildren().add(layout);
        layout.getChildren().addAll(goku,vegeta,back);

        goku.setOnAction(ev -> {
            playerSelection.put(1,Characters.GOKU);
            playerSelection.put(2,Characters.VEGETA);
            menuAction = MenuAction.PG_SELECTED;
            triggerGL();
        });
        vegeta.setOnAction(ev -> {
            playerSelection.put(1,Characters.VEGETA);
            playerSelection.put(2,Characters.GOKU);
            menuAction = MenuAction.PG_SELECTED;
            triggerGL();
        });
        back.setOnAction(ev -> {
            menuAction = MenuAction.BACK;
            triggerGL();
        });

    }
    public void mainMenu() {
        init(600,800);
        userState = UserState.MAIN_MENU;

        VBox layout = new VBox();
        UI.getChildren().add(layout);
        Button singleplayer = new Button("Singleplayer");
        Button multiplayer = new Button("Multiplayer");
        Button exit = new Button("Close Game");
        layout.getChildren().addAll(singleplayer,multiplayer,exit);

        singleplayer.setOnAction(event -> {
            gameFactory.setSingleplayerGameMode();
            menuAction = MenuAction.SINGLEPLAYER;
            triggerGL();
        });
        multiplayer.setOnAction(event -> {
            gameFactory.setMultiplayerGameMode();
            menuAction = MenuAction.MULTIPLAYER;
            triggerGL();
        });
        exit.setOnAction(event -> {
            menuAction = MenuAction.EXIT;
            triggerGL();
        });
    }

    public void initGame(Stage s) {
        stage = s;
        stage.setTitle("DragonFight");
        stage.show();
        mainMenu();
    }

    private void triggerGL() {
        GameLauncher.updateMenu();
    }

    public MenuAction getMenuAction() {
        return menuAction;
    }

    public UserState getUserState() {
        return userState;
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
    public void start() {
        super.start();
        inPause = false;
    }

    @Override
    public void stop() {
        super.stop();
        inPause = true;
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
