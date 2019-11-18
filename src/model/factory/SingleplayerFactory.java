package model.factory;

import model.Player;
import model.enums.Characters;
import view.player.SinglePlayerOne;
import view.player.SinglePlayerTwo;

public class SingleplayerFactory implements GameModeFactory {

    @Override
    public Player makePlayerOne(Characters character) {
        return new SinglePlayerOne(character);
    }

    @Override
    public Player makePlayerTwo(Characters character) {
        return new SinglePlayerTwo(character);
    }
}
