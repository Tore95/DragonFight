package view.player;

import javafx.scene.input.KeyEvent;
import view.aura.MultiAuraFinal;
import view.aura.MultiAuraOne;
import view.aura.MultiAuraTwo;
import model.Player;
import model.Point;
import model.enums.Characters;
import model.enums.Turned;

public class MultiPlayerOne extends Player {

    protected void launchAuraOne(Player target) {
        new MultiAuraOne(target);
    }
    protected void launchAuraTwo(Player target) {
        new MultiAuraTwo(target);
    }
    protected void launchAuraFinal(Player target) {
        new MultiAuraFinal(target);
    }

    public MultiPlayerOne(Characters character) {
        super(164d, Turned.RIGHT, character);
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
            case AURA1: launchAuraOne(this); break;
            case AURA2: launchAuraTwo(this); break;
            case FINAL: launchAuraFinal(this); break;
        }
    }

    @Override
    public void pressedKeyEvent(KeyEvent ev) {
        switch (ev.getCode()) {
            case W: goUp(true); break;
            case S: goDown(true); break;
            case D: goRight(true); break;
            case A: goLeft(true); break;
        }
    }

    @Override
    public void releasedKeyEvent(KeyEvent ev) {
        switch (ev.getCode()) {
            case W: goUp(false); break;
            case S: goDown(false); break;
            case D: goRight(false); break;
            case A: goLeft(false); break;
            case E: punch(); break;
            case Q: kick(); break;
            case R: auraOne(); break;
            case F: auraTwo(); break;
            case C: auraFinal(); break;
        }
    }
}
