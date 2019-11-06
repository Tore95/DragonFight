package view.player;

import javafx.scene.input.KeyEvent;
import view.aura.MultiAuraFinal;
import view.aura.MultiAuraOne;
import view.aura.MultiAuraTwo;
import model.Player;
import model.Point;
import model.enums.Characters;
import model.enums.Turned;

public class MultiPlayerTwo extends Player {

    public MultiPlayerTwo(Characters character) {
        super(1064d, Turned.LEFT, character);
    }

    @Override
    protected void launchAttack() {
        switch (getPlayerAction()) {
            case PUNCH:
                if (getTarget().isCollide(
                        new Point(getX() + hashPunch.get("offsetx"), getY() + hashPunch.get("offsety"))
                )) getTarget().hitted(getDamage());
                break;
            case KICK:
                if (getTarget().isCollide(
                        new Point(getX() + hashKick.get("offsetx"), getY() + hashKick.get("offsety"))
                )) getTarget().hitted(getDamage() * 2);
                break;
            case AURA1: new MultiAuraOne(this); break;
            case AURA2: new MultiAuraTwo(this); break;
            case FINAL: new MultiAuraFinal(this); break;
        }
    }

    @Override
    public void pressedKeyEvent(KeyEvent ev) {
        switch (ev.getCode()) {
            case Y: goUp(true); break;
            case H: goDown(true); break;
            case J: goRight(true); break;
            case G: goLeft(true); break;
        }
    }

    @Override
    public void releasedKeyEvent(KeyEvent ev) {
        switch (ev.getCode()) {
            case Y: goUp(false); break;
            case H: goDown(false); break;
            case J: goRight(false); break;
            case G: goLeft(false); break;
            case U: punch(); break;
            case T: kick(); break;
            case I: auraOne(); break;
            case K: auraTwo(); break;
            case M: auraFinal(); break;
        }

    }
}
