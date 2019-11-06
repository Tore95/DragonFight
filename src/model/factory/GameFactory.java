package model.factory;

import model.baseObjs.Background;
import model.baseObjs.Player;
import model.enums.Characters;

public class GameFactory implements GameModeFactory {

    private static GameFactory instance;
    private GameModeFactory currGameMode;
    private GameFactory() {}

    public static GameFactory getInstance() {
        if (instance == null) instance = new GameFactory();
        return instance;
    }

    public void setSingleplayerGameMode() {
        currGameMode = new SingleplayerFactory();
    }
    public void setMultiplayerGameMode() {
        currGameMode = new MultiplayerFactory();
    }

    @Override
    public Player makePlayerOne(Characters character) {
        return currGameMode.makePlayerOne(character);
    }

    @Override
    public Player makePlayerTwo(Characters character) {
        return currGameMode.makePlayerTwo(character);
    }

    public Background makeBackground() {
        return new Background();
    }
}
