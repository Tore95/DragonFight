package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.Controls;
import model.Player;
import model.enums.Characters;
import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;
import view.goku.Genkidama;
import view.goku.Kamehameha;
import view.goku.Onda;
import view.vegeta.BigBang;
import view.vegeta.SpecialCannon;

import java.util.Properties;

public class Character extends Player implements Controls {

    private final int frameSize = 128;

    private ImageView imageView;
    private Characters character;
    private String currCharacter;
    private Properties sm;

    private long lastFrame;
    private int currCol;
    private int currRow;
    private int rowFrames;
    private int launchAttackFrame;
    private int lockedFrames;

    public Character(int number, Characters character) {
        super(number);
        this.character = character;
        currCharacter = character.toString().toLowerCase() + '.';
        sm = gs.getSpriteMapping();
        imageView = new ImageView(sm.getProperty(this.currCharacter + "imgUrl"));
        lastFrame = System.nanoTime();
        currCol = 0;
        currRow = 0;
        rowFrames = 0;
        launchAttackFrame = -1;
        lockedFrames = -1;
        direction();
        gs.getPlayers().getChildren().add(imageView);
    }

    public void direction() {
        if (getPlayerAction() != PlayerAction.REST) return;
        String currDirection = getDirection().toString().toLowerCase() + '.';
        currRow = Integer.parseInt(sm.getProperty(currCharacter + currDirection + "row"));
        rowFrames = Integer.parseInt(sm.getProperty(currCharacter + currDirection + "frames"));
        currCol = 0;
        currRow = getTurned() == Turned.RIGHT ? currRow : currRow + 1;
    }

    public void action() {
        if (lockedFrames != -1) return;
        String currAction = getPlayerAction().toString().toLowerCase() + '.';
        currRow = Integer.parseInt(sm.getProperty(currCharacter + currAction + "row"));
        rowFrames = Integer.parseInt(sm.getProperty(currCharacter + currAction + "frames"));
        launchAttackFrame = Integer.parseInt(sm.getProperty(currCharacter + currAction + "attackFrame"));
        currCol = 0;
        currRow = getTurned() == Turned.RIGHT ? currRow : currRow + 1;
        lockedFrames = rowFrames - 1;
    }

    @Override
    public void draw() {
        imageView.setX(getX() - getWidth());
        imageView.setY(getY() - (getHeight() / 2d));

        int frameJump = (int) Math.floor((double) (System.nanoTime() - lastFrame) / (double)(1000000000L / gs.getSpriteFPS()));
        if (frameJump >= 1) {
            lastFrame = System.nanoTime();
            imageView.setViewport(new Rectangle2D(currCol * frameSize, currRow * frameSize, frameSize, frameSize));
            if (lockedFrames < 0) {
                currCol = (currCol + 1) % rowFrames;
            } else if (lockedFrames > 0) {
                if (currCol == launchAttackFrame) launchAttack();
                currCol += 1;
                lockedFrames -= 1;
            } else {
                lockedFrames -= 1;
                setPlayerAction(PlayerAction.REST);
                direction();
            }
        }
    }

    private void launchAttack() {
        switch (character) {
            case GOKU:
                switch (getPlayerAction()) {
                    case PUNCH:
                    case KICK: if (isCollide(getTarget())) getTarget().hitted(getDamage()); break;
                    case AURA1: new Onda(getTurned() == Turned.RIGHT ? getX() + 84 : getX() - 84,getY() - 6, getDamage() * 3, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
                    case AURA2: new Kamehameha(getTurned() == Turned.RIGHT ? getX() + 84 : getX() - 84,getY() - 6, getDamage() * 4, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
                    case FINAL: new Genkidama(getX(),getY() - 64 - 128,getDamage() * 5, getTurned() == Turned.RIGHT ? Direction.DOWN_RIGHT : Direction.DOWN_LEFT, getTarget()); break;
                } break;
            case VEGETA:
                switch (getPlayerAction()) {
                    case PUNCH:
                    case KICK: if (isCollide(getTarget())) getTarget().hitted(getDamage()); break;
                    case AURA1: new view.vegeta.Onda(getTurned() == Turned.RIGHT ? getX() + 84 : getX() - 84,getY() - 6, getDamage() * 3, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
                    case AURA2: new SpecialCannon(getTurned() == Turned.RIGHT ? getX() + 80 : getX() - 80,getY(), getDamage() * 4, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
                    case FINAL: new BigBang(getTurned() == Turned.RIGHT ? getX() + 32 + 128 : getX() - 32 - 128,getY() - 16,getDamage() * 5, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
                } break;
        }
    }

    @Override
    public boolean goUp(boolean val) {
        return setTop(val);
    }

    @Override
    public boolean goDown(boolean val) {
        return setBottom(val);
    }

    @Override
    public boolean goRight(boolean val) {
        return setRight(val);
    }

    @Override
    public boolean goLeft(boolean val) {
        return setLeft(val);
    }

    @Override
    public void punch() {
        if (setPlayerAction(PlayerAction.PUNCH)) action();
    }

    @Override
    public void kick() {
        if (setPlayerAction(PlayerAction.KICK)) action();
    }

    @Override
    public void auraOne() {
        if (setPlayerAction(PlayerAction.AURA1)) action();
    }

    @Override
    public void auraTwo() {
        if (setPlayerAction(PlayerAction.AURA2)) action();
    }

    @Override
    public void auraFinal() {
        if (setPlayerAction(PlayerAction.FINAL)) action();
    }

    @Override
    public void hitted(int damage) {
        super.hitted(damage);
        action();
    }
}
