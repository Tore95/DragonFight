package model;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import model.baseObjs.GameObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class GlobalManager extends AnimationTimer {

    private static GlobalManager instance;

    private Properties spriteMapping;
    private Group root;
    private Group background;
    private Group players;
    private Group auraAttacks;
    private Group UI;
    private Scene scene;
    private LinkedList<GameObject> gameObjects;
    private LinkedList<GameObject> toRemoveObjects;
    private LinkedList<GameObject> toAddObjects;
    private double lastFrame;
    private double lastSpriteFrame;


    private GlobalManager() {
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

        lastFrame = System.nanoTime();
        lastSpriteFrame = lastFrame;

        try {
            spriteMapping = new Properties();
            spriteMapping.load(getClass().getClassLoader().getResourceAsStream("resources/spriteMapping.properties"));
        } catch (IOException e) {
            System.err.println("Unable to load sprite mapping properties");
            System.exit(-1);
        }

        this.start();
    }

    public static GlobalManager getInstance() {
        if (instance == null) instance = new GlobalManager();
        return instance;
    }

    public Properties getSpriteMapping() {
        return spriteMapping;
    }

    public long getFPS() {
        return 60L;
    }

    public long getSpriteFPS() {
        return 16L;
    }

    public Group getRoot() {
        return root;
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

    public LinkedList<GameObject> getToRemoveObjects() {
        return toRemoveObjects;
    }

    public LinkedList<GameObject> getToAddObjects() {
        return toAddObjects;
    }

    @Override
    public void handle(long now) {
        if ((int) Math.floor((now - lastFrame) / (double)(1000000000L / getFPS())) >= 1) {
            lastFrame = now;
            gameObjects.removeAll(toRemoveObjects);
            toRemoveObjects.clear();
            gameObjects.addAll(toAddObjects);
            toAddObjects.clear();
            gameObjects.forEach(GameObject::move);
            if ((int) Math.floor((now - lastSpriteFrame) / (double)(1000000000L / getSpriteFPS())) >= 1) {
                lastSpriteFrame = now;
                gameObjects.forEach(GameObject::draw);
            }
        }
    }
}
