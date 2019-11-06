package model.factory;

import model.Player;
import model.enums.Characters;

public class SingleplayerFactory implements GameModeFactory {

    @Override
    public Player makePlayerOne(Characters character) {
        return null;
    }

    @Override
    public Player makePlayerTwo(Characters character) {
        return null;
    }
}
