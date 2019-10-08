package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import model.GameObject;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class GameStatus {

    private static GameStatus instance;

    private Properties spriteMapping;

    private Group root;
    private Group background;
    private Group players;
    private Group auraAttacks;
    private Group UI;
    private Scene scene;
    private LinkedList<GameObject> gameObjects;
    private Image gokuImage;
    private Image vegetaImage;
    private LinkedList<GameObject> toRemoveObjects;
    private LinkedList<GameObject> toAddObjects;

    private long FPS;
    private long spriteFPS;

    public long getSpriteFPS() {
        return spriteFPS;
    }
    public long getFPS() {
        return FPS;
    }
    public LinkedList<GameObject> getToRemoveObjects() {
        return toRemoveObjects;
    }
    public LinkedList<GameObject> getToAddObjects() {
        return toAddObjects;
    }
    public LinkedList<GameObject> getGameObjects() {
        return gameObjects;
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
    public Scene getScene() {
        return scene;
    }
    public Image getGokuImage() {
        return gokuImage;
    }
    public Image getVegetaImage() {
        return vegetaImage;
    }
    public Properties getSpriteMapping() {
        return spriteMapping;
    }
    public static GameStatus getInstance() {
        if (instance == null) instance = new GameStatus();
        return instance;
    }

    private GameStatus() {
        root = new Group();
        background = new Group();
        players = new Group();
        auraAttacks = new Group();
        UI = new Group();

        root.getChildren().add(0,background);
        root.getChildren().add(1,players);
        root.getChildren().add(2,auraAttacks);
        root.getChildren().add(3,UI);

        scene = new Scene(root,1280,720);

        gameObjects = new LinkedList<>();
        toRemoveObjects = new LinkedList<>();
        toAddObjects = new LinkedList<>();

        gokuImage = new Image("resources/Goku.png");
        vegetaImage = new Image("resources/Vegeta.png");

        FPS = 60;
        spriteFPS = 16;

        try {
            spriteMapping = new Properties();
            spriteMapping.load(getClass().getClassLoader().getResourceAsStream("controller/spriteMapping.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void update() {
        gameObjects.removeAll(toRemoveObjects);
        toRemoveObjects.clear();
        gameObjects.addAll(toAddObjects);
        toAddObjects.clear();
        gameObjects.forEach(GameObject::update);
    }

}
