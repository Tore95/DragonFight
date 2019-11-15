package model.factory;

import view.Background;
import model.Player;
import model.enums.Characters;
import view.UIPlayerOne;
import view.UIPlayerTwo;

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

    public void makeGameBackground() {
        new Background("resources/gameBack.png");
    }
    public void makeMenuBackground() {
        new Background("resources/menuBack.png");
    }
    public void makeSelectionBackground() {
        new Background("resources/selectionBack.png");
    }

    public void makeGameUI(Player one, Player two) {
        new UIPlayerOne(one);
        new UIPlayerTwo(two);
    }

}
