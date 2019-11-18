package view;

import controller.GameLauncher;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import model.GameObject;
import model.Subject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class GlobalManager extends AnimationTimer {

    private static GlobalManager instance;
    public static final long FPS = 60L;
    public static final long SPRITE_FPS = 16L;
    private static final long IA_FPS = 2L;

    private double lastFrame;
    private double lastSpriteFrame;
    private double lastIaTick;
    private Group background;
    private Group players;
    private Group auraAttacks;
    private Group UI;
    private Properties spriteMapping;
    private Subject playerOne;
    private LinkedList<GameObject> gameObjects;
    private LinkedList<GameObject> toRemoveObjects;
    private LinkedList<GameObject> toAddObjects;

    private GlobalManager() {
        lastFrame = System.nanoTime();
        lastSpriteFrame = lastFrame;
        lastIaTick = lastFrame;
        gameObjects = new LinkedList<>();
        toRemoveObjects = new LinkedList<>();
        toAddObjects = new LinkedList<>();
        try {
            spriteMapping = new Properties();
            spriteMapping.load(getClass().getClassLoader().getResourceAsStream("resources/spriteMapping.properties"));
        } catch (IOException e) {
            System.err.println("Unable to load sprite mapping properties");
            Platform.exit();
        }
    }

    public static GlobalManager getInstance() {
        if (instance == null) instance = new GlobalManager();
        return instance;
    }

    Group getBackground() {
        return background;
    }

    public void setPlayerOne(Subject playerOne) {
        this.playerOne = playerOne;
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
    public Properties getSpriteMapping() {
        return spriteMapping;
    }
    public LinkedList<GameObject> getToRemoveObjects() {
        return toRemoveObjects;
    }
    public LinkedList<GameObject> getToAddObjects() {
        return toAddObjects;
    }

    public void initGroups(Group root) {
        background = (Group) root.getChildren().get(0);
        players = (Group) root.getChildren().get(1);
        auraAttacks = (Group) root.getChildren().get(2);
        UI = (Group) root.getChildren().get(3);
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
                if (((int) Math.floor((now - lastIaTick) / (double)(1000000000L / IA_FPS)) >= 1) && playerOne != null) {
                    lastIaTick = now;
                    playerOne.notifyObserver();
                }
            }
        }
    }
}
