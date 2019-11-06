package model.factory;

import model.baseObjs.Player;
import model.enums.Characters;
import model.player.MultiPlayerOne;
import model.player.MultiPlayerTwo;

public class MultiplayerFactory implements GameModeFactory {

    @Override
    public Player makePlayerOne(Characters character) {
        return new MultiPlayerOne(character);
    }

    @Override
    public Player makePlayerTwo(Characters character) {
        return new MultiPlayerTwo(character);
    }
}
