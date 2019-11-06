package model.factory;

import model.Player;
import model.enums.Characters;
import view.player.MultiPlayerOne;
import view.player.MultiPlayerTwo;

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
