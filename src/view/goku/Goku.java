package view.goku;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.Controls;
import model.Player;
import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;

public class Goku extends Player implements Controls {

    private ImageView imageView;
    private final long spriteFramePerSecond = 16;
    private long lastFrame;
    private int frameSize = 128;
    private int currCol = 0;
    private int currRow = 0;
    private int rowFrames = 0;
    private int launchAttackFrame = -1;
    private int lockedFrames = -1;


    public Goku(int number) {
        super(number);
        imageView = new ImageView(gs.getGokuImage());
        lastFrame = System.nanoTime();
        direction();
        gs.getPlayers().getChildren().add(imageView);
    }

    public void direction() {
        if (getPlayerAction() != PlayerAction.REST) return;
        switch (getDirection()) {
            case DOWN_RIGHT:
            case UP_RIGHT:
            case RIGHT:
            case DOWN_LEFT:
            case UP_LEFT:
            case LEFT: currRow = 2; rowFrames = 4; break;
            case UP: currRow = 4; rowFrames = 1; break;
            case DOWN: currRow = 6; rowFrames = 1; break;
            case STOP: currRow = 0; rowFrames = 4; break;
        }
        currCol = 0;
        currRow = getTurned() == Turned.RIGHT ? currRow : currRow + 1;
    }

    public void action() {
        if (lockedFrames != -1) return;
        switch (getPlayerAction()) {
            case PUNCH:
                currRow = 8;
                rowFrames = 6;
                launchAttackFrame = 4;
                break;
            case KICK:
                currRow = 10;
                rowFrames = 6;
                launchAttackFrame = 4;
                break;
            case AURA1:
                currRow = 12;
                rowFrames = 5;
                launchAttackFrame = 3;
                break;
            case AURA2:
                currRow = 14;
                rowFrames = 19;
                launchAttackFrame = 6;
                break;
            case FINAL:
                currRow = 16;
                rowFrames = 19;
                launchAttackFrame = 4;
                break;
            case SOFT_HITTED:
                currRow = 18;
                rowFrames = 6;
                launchAttackFrame = -1;
                break;
            case HARD_HITTED:
                currRow = 20;
                rowFrames = 9;
                launchAttackFrame = -1;
                break;
        }
        currCol = 0;
        currRow = getTurned() == Turned.RIGHT ? currRow : currRow + 1;
        lockedFrames = rowFrames - 1;
    }


    @Override
    public void draw() {
        int frameJump = (int) Math.floor((double) (System.nanoTime() - lastFrame) / (double)(1000000000L / spriteFramePerSecond));
        if (frameJump >= 1) {
            lastFrame = System.nanoTime();
            imageView.setViewport(new Rectangle2D(currCol * frameSize, currRow * frameSize, frameSize, frameSize));
            if (lockedFrames < 0) {
                currCol = (currCol + 1) % rowFrames;
                imageView.setX(getX() - getWidth());
                imageView.setY(getY() - (getHeight() / 2d));
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
        switch (getPlayerAction()) {
            case PUNCH:
            case KICK: if (isCollide(getTarget())) getTarget().hitted(getDamage()); break;
            case AURA1: new Onda(getTurned() == Turned.RIGHT ? getX() + 32 : getX() - 32,getY() - 16, getDamage() * 3, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
            case AURA2: new Kamehameha(getTurned() == Turned.RIGHT ? getX() + 32 : getX() - 32,getY() - 16, getDamage() * 4, getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT, getTarget()); break;
            case FINAL: new Genkidama(getX(),getY() - 64 - 128,getDamage() * 5, getTurned() == Turned.RIGHT ? Direction.DOWN_RIGHT : Direction.DOWN_LEFT, getTarget()); break;
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
