package model.factory;

import model.baseObjs.Player;
import model.enums.Characters;

public interface GameModeFactory {
    Player makePlayerOne(Characters character);
    Player makePlayerTwo(Characters character);
}
